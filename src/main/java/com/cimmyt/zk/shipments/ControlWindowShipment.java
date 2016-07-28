/*
Copyright 2013 International Maize and Wheat Improvement Center
   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at
       http://www.apache.org/licenses/LICENSE-2.0
   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package com.cimmyt.zk.shipments;

import static com.cimmyt.utils.Constants.ATTRIBUTE_NAME_USER_BEAN;
import static com.cimmyt.utils.Constants.ATTRIBUTE_SHIPMENTS_ITEM;
import static com.cimmyt.utils.Constants.ATTRIBUTE_STUDY_TEMPLATE_ITEM;
import static com.cimmyt.utils.Constants.INVESTIGATOR_SERVICE_BEAN_NAME;
import static com.cimmyt.utils.Constants.LOCALE_LANGUAGE;
import java.util.List;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zkex.zul.Fisheye;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.cimmyt.bean.InvestigatorBean;
import com.cimmyt.bean.UserBean;
import com.cimmyt.model.bean.Investigator;
import com.cimmyt.model.bean.ShipmentSet;
import com.cimmyt.service.ServiceInvestigator;
import com.cimmyt.utils.PropertyHelper;

@SuppressWarnings("serial")
public class ControlWindowShipment extends Window{
	private Intbox id;
	private Datebox idDBRegDate;
	private Combobox idComboInvestigator;
	private Textbox comments;
	private Fisheye idFisheyeNext;
	
	private static ServiceInvestigator serviceInvestigator;
	private ShipmentSet bean;
	
	static {
		if(serviceInvestigator == null)
        {
			try{
				serviceInvestigator = (ServiceInvestigator)SpringUtil.getBean(INVESTIGATOR_SERVICE_BEAN_NAME);
				}catch (Exception e){
				e.printStackTrace();
			}
        }
		
	}
	Logger logger= Logger.getLogger(ControlWindowShipment.class);
	/** 
	 * Close Window
	 */
	public void closeWindow(){
		getDesktop().setAttribute(ATTRIBUTE_STUDY_TEMPLATE_ITEM, null);
		this.onClose();
	}
	/**
	 * Load Window Components
	 */
	public void loadContextAttribute(){
		loadComponents();

				bean = getDesktop().getAttribute(ATTRIBUTE_SHIPMENTS_ITEM) == null ? new ShipmentSet() :
					(ShipmentSet)getDesktop().getAttribute(ATTRIBUTE_SHIPMENTS_ITEM) ;
				
		int idInvestigator;
		if(bean.getStInvestigator() == null){//uses the investigator from the session
			UserBean userBean = (UserBean)getDesktop().getSession().getAttribute(ATTRIBUTE_NAME_USER_BEAN);
			idInvestigator = userBean==null ? 0 : userBean.getInvestigatorBean().getInvestigatorid();
		}else{//uses the investigator from the bean(Edit mode)
			idInvestigator = bean.getStInvestigator().getInvestigatorid();
		}

		loadComboInvestigator(idInvestigator, true);

		setFormValues(bean);
	}

	private void loadComboInvestigator(int id , boolean disabled){
		List<InvestigatorBean> beanList =serviceInvestigator.getInvestigator(new InvestigatorBean());
		if (beanList != null && !beanList.isEmpty()){
			for (InvestigatorBean bean : beanList){
				Comboitem item = new Comboitem(bean.getInvest_name());
				item.setValue(bean.getInvestigator(bean));
				idComboInvestigator.appendChild(item);
				if(bean.getInvestigatorid() == id){
					idComboInvestigator.setSelectedItem(item);
				}
			}
			idComboInvestigator.setDisabled(disabled);
		}
	}

	private void loadComponents(){
		id = (Intbox)getFellow("id");
		idDBRegDate = (Datebox)getFellow("idDBRegDate");
		idComboInvestigator = (Combobox)getFellow("idComboInvestigator");
		comments = (Textbox)getFellow("comments");
		idFisheyeNext = (Fisheye)getFellow("idFisheyeNext");
	}

	
	/**
	 * Loads the values from the bean selected in screen.
	 * @param shipSetBean The bean to display (edit mode).
	 */
	private void setFormValues(ShipmentSet shipSetBean){
		comments.setText(bean.getComments());

		if(bean.getDatCreated() != null)
			idDBRegDate.setValue(bean.getDatCreated());

		if(bean.getIdShipmentSet()!= null){
			id.setValue(bean.getIdShipmentSet());
			validateFields(null);
		}
	}

	/**
	 * Check if the form is complete. If so, displays the button to continue; hides it otherwise.
	 * @param event
	 */
	public void validateFields(Event event){

		if(idComboInvestigator.getSelectedIndex() == -1)
			return;
		bean.setStInvestigator((Investigator)idComboInvestigator.getSelectedItem().getValue());
		
		boolean isComments = event != null && event.getTarget().getId().equals("comments");
		if(isComments)
			bean.setComments(((InputEvent)event).getValue());
		else
			bean.setComments(comments.getText());
		
		if(bean.getComments()==null || bean.getComments().isEmpty()){
			idFisheyeNext.setVisible(false);
			return;
		}
		
		if(bean.getDatCreated() == null)
			bean.setDatCreated(idDBRegDate.getValue());
		
		idFisheyeNext.setVisible(true);
	}

	public void nextPag(){
		getDesktop().setAttribute(ATTRIBUTE_SHIPMENTS_ITEM, bean);

		final Window win = (Window) Executions.createComponents("/shipment_management/window_shipping_plates.zul", this, null);
    	win.doModal();
   		((Window)getFellow("idShipmentsAdd")).onClose();

	}

}