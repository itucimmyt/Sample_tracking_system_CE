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

import static com.cimmyt.utils.Constants.ATTRIBUTE_SHIPMENTS_ITEM;
import static com.cimmyt.utils.Constants.ATTRIBUTE_SHIPMENT_ITEM;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_FIELD_REQUIRED;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_INFORMATION;
import static com.cimmyt.utils.Constants.LBL_SHIPMENT_SAVE_SUCCESS;
import static com.cimmyt.utils.Constants.SAMPLE_DETAIL_SERVICE_BEAN_NAME;
import static com.cimmyt.utils.Constants.STATUS_SERVICE_BEAN_NAME;
import static com.cimmyt.utils.Constants.LOCALE_LANGUAGE;
import static com.cimmyt.utils.Constants.COMPANY_SERVICE_BEAN_NAME;
import static com.cimmyt.utils.Constants.SHIPMENT_SERVICE_BEAN_NAME;
import static com.cimmyt.utils.Constants.SHIPMENT_SERVICE_DETAIL_BEAN_NAME;
import static com.cimmyt.utils.Constants.INVESTIGATOR_SERVICE_BEAN_NAME;
import java.util.List;

import org.apache.log4j.Logger;
import org.cimmyt.dnast.service.FileRepositoryServiceClient;
import org.cimmyt.dnast.service.imp.FileRepositoryServiceClientImp;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zkex.zul.Fisheye;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.cimmyt.model.bean.Company;
import com.cimmyt.model.bean.Shipment;
import com.cimmyt.model.bean.ShipmentSet;
import com.cimmyt.model.bean.Status;
import com.cimmyt.service.ServiceCompany;
import com.cimmyt.service.ServiceInvestigator;
import com.cimmyt.service.ServiceSampleDetail;
import com.cimmyt.service.ServiceShipment;
import com.cimmyt.service.ServiceShipmentDetail;
import com.cimmyt.service.ServiceStatus;
import com.cimmyt.utils.PropertyHelper;

@SuppressWarnings("serial")
public class ControlWindowShipments extends Window{
	private Intbox id;
	private Datebox idDBRegDate;
	private Combobox idComboStatus;
	private Combobox idComboCompany;
	private Textbox compAddress;
	private Textbox compEmail;
	private Textbox compName;
	private Textbox comments;
	private Textbox trackingNumber;
	private Textbox trackingNumberDelivery;
	
	private Fisheye idFisheyeAdd;
	
	private PropertyHelper pro=null;
	private static ServiceCompany serviceCompany;
	private static ServiceStatus serviceStatus;
	private static ServiceShipment serviceShipment;
	private static ServiceSampleDetail serviceSampleDetail;
	private static ServiceShipmentDetail serviceShipmentDetail;
	private static ServiceInvestigator serviceInvestigator;
	private Shipment bean;
	
	static {
		if(serviceStatus == null)
        {
			try{
				serviceStatus = (ServiceStatus)SpringUtil.getBean(STATUS_SERVICE_BEAN_NAME);
				}catch (Exception e){
				e.printStackTrace();
			}
        }
		
	}
	static {
		if(serviceCompany == null)
        {
			try{
				serviceCompany = (ServiceCompany)SpringUtil.getBean(COMPANY_SERVICE_BEAN_NAME);
				}catch (Exception e){
				e.printStackTrace();
			}
        }
		
	}
	static {
		if(serviceShipment == null)
        {
			try{
				serviceShipment = (ServiceShipment)SpringUtil.getBean(SHIPMENT_SERVICE_BEAN_NAME);
				}catch (Exception e){
				e.printStackTrace();
			}
        }
		
	}
	static {
		if(serviceSampleDetail == null)
        {
			try{
				serviceSampleDetail = (ServiceSampleDetail)SpringUtil.getBean(SAMPLE_DETAIL_SERVICE_BEAN_NAME);
				}catch (Exception e){
				e.printStackTrace();
			}
        }
		
	}
	static {
		if(serviceShipmentDetail == null)
        {
			try{
				serviceShipmentDetail = (ServiceShipmentDetail)SpringUtil.getBean(SHIPMENT_SERVICE_DETAIL_BEAN_NAME);
				}catch (Exception e){
				e.printStackTrace();
			}
        }
		
	}
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

	Logger logger= Logger.getLogger(ControlWindowShipments.class);
	/** 
	 * Close Window
	 */
	public void closeWindow(){
		getDesktop().removeAttribute(ATTRIBUTE_SHIPMENTS_ITEM);
		getDesktop().removeAttribute(ATTRIBUTE_SHIPMENT_ITEM);
		this.onClose();
	}
	/**
	 * Load Window Components
	 */
	public void loadContextAttribute(){

		pro = (PropertyHelper)getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);
		loadComponents();
				
		bean = getDesktop().getAttribute(ATTRIBUTE_SHIPMENT_ITEM) != null ?
				(Shipment)getDesktop().getAttribute(ATTRIBUTE_SHIPMENT_ITEM) : new Shipment();

		int idCompany = (bean.getStCompany() == null ? 0 : bean.getStCompany().getIdCompany());
		boolean isDisabled = bean.getStCompany()!=null;

		loadComboCompany(idCompany, isDisabled);
		if(isDisabled){
			compAddress.setText(bean.getStCompany().getAddresss());
			compEmail.setText(bean.getStCompany().getEmail());
			compName.setText(bean.getStCompany().getName());
		}

		String idStatus = bean.getStStatus() == null ? "" : bean.getStStatus().getIdStatus();
		loadComboStatus(idStatus);

		comments.setText(bean.getComment());

		if(bean.getDatRegister() != null){
			idDBRegDate.setValue(bean.getDatRegister());
		}
		if(bean.getTrackingNumberDelivery()!= null && !bean.getTrackingNumberDelivery().isEmpty()){
			trackingNumberDelivery.setText(bean.getTrackingNumberDelivery());
		}
		if(bean.getTrackingNumberLocal()!= null && !bean.getTrackingNumberLocal().isEmpty()){
			trackingNumber.setText(bean.getTrackingNumberLocal());
		}
		if(bean.getIdShipment()!= null){
			id.setValue(bean.getIdShipment());
			validateFields(new Event(Events.ON_CHANGING, idComboCompany));
		}
		
	}

	private void loadComboCompany(int id,boolean disabled){
		List<Company> listCompany = serviceCompany.getListByFilter(new Company());
		if (listCompany != null && !listCompany.isEmpty()){
			for (Company bean : listCompany){
				Comboitem item = new Comboitem(bean.getName());
				item.setValue(bean);
				idComboCompany.appendChild(item);
				if (bean.getIdCompany() == id){
					idComboCompany.setSelectedItem(item);
				}
			}
			idComboCompany.setDisabled(disabled);
		}
	}

	private void loadComboStatus(String id){
		List<Status> beanList = serviceStatus.getListByFilter(new Status());
		if (beanList != null && !beanList.isEmpty()){
			for (Status bean : beanList){
				Comboitem item = new Comboitem(bean.getStatusDescription());
				item.setValue(bean);
				idComboStatus.appendChild(item);
				if(bean.getIdStatus() == id){
					idComboStatus.setSelectedItem(item);
				}
			}
			idComboStatus.setSelectedItem(idComboStatus.getItemAtIndex(0));
		}
	}

	private void loadComponents(){
		id = (Intbox)getFellow("id");
		idDBRegDate = (Datebox)getFellow("idDBRegDate");
		idComboStatus = (Combobox)getFellow("idComboStatus");
		idComboCompany = (Combobox)getFellow("idComboCompany");
		comments = (Textbox)getFellow("comments");
		trackingNumber = (Textbox)getFellow("trackingNumber");
		trackingNumberDelivery = (Textbox)getFellow("trackingNumberDelivery");
		compAddress = (Textbox)getFellow("compAddress");
		compEmail = (Textbox)getFellow("compEmail");
		compName = (Textbox)getFellow("compName");
		idFisheyeAdd = (Fisheye)getFellow("idFisheyeAdd");
	}

	public void validateFields(Event event){
		
		String target = event.getTarget().getId();
		boolean valid = true;

		if(idComboCompany.getSelectedIndex() == -1){
			valid = false;
		}else{
			Company selectedProvider = (Company)idComboCompany.getSelectedItem().getValue();
			compAddress.setText(selectedProvider.getAddresss());
			compEmail.setText(selectedProvider.getEmail());
			compName.setText(selectedProvider.getName());
		}

		if((!target.equals(comments.getId()) && comments.getValue().isEmpty() ) ||
			(target.equals(comments.getId())  && ((InputEvent)event).getValue().toString().isEmpty()) ){
			valid = false;
		}

		idFisheyeAdd.setVisible(valid);
		
		if(!valid) return;
		
		bean.setStCompany((Company) idComboCompany.getSelectedItem().getValue());
		bean.setStStatus((Status)idComboStatus.getSelectedItem().getValue());
		bean.setComment(comments.getText().trim());
		bean.setTrackingNumberLocal(trackingNumber.getText().trim());
		bean.setTrackingNumberDelivery(trackingNumberDelivery.getText().trim());
		bean.setDatRegister(idDBRegDate.getValue());
		
	}

	/**
	 * Add Shipment
	 */
	public void add(Event event){
		pro = (PropertyHelper)getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);
		ShipmentSet beanShipments = (ShipmentSet) getDesktop().getAttribute(ATTRIBUTE_SHIPMENTS_ITEM);
		loadComponents();

		if (validateForm()){
			messageBox(pro.getKey(LBL_GENERIC_MESS_FIELD_REQUIRED));	
		}else {
			ShipmentSet beanShipSet =  beanShipments;
			Shipment beanShipment = new Shipment();
			beanShipment.setIdShipment(id.getValue());
			beanShipment.setDatRegister(idDBRegDate.getValue());
			beanShipment.setStStatus((Status)idComboStatus.getSelectedItem().getValue());
			beanShipment.setStCompany((Company) idComboCompany.getSelectedItem().getValue());
			beanShipment.setComment(comments.getValue());
			beanShipment.setTrackingNumberLocal(trackingNumber.getValue());
			beanShipment.setTrackingNumberDelivery(trackingNumberDelivery.getValue());
			beanShipment.setIsSentToKB(false);
			beanShipment.setStShipmentSet(beanShipSet);
			
			serviceShipment.saveOrUpdateShipment(beanShipment);

			//try to send the shipment to KBase
			//FileRepositoryServiceClient serviceKB = new FileRepositoryServiceClientImp(serviceShipment,serviceSampleDetail,serviceInvestigator);
			//serviceKB.sendShipmentToKBase(beanShipment.getIdShipment());

			getDesktop().setAttribute(ATTRIBUTE_SHIPMENTS_ITEM, null);

			ControlShipments shipWindow = (ControlShipments)this.getParent();

			((Window)getFellow("idShipmentAdd")).onClose();

			shipWindow.doAfterCompose();

			Messagebox.show(pro.getKey(LBL_SHIPMENT_SAVE_SUCCESS), 
					pro.getKey(LBL_GENERIC_MESS_INFORMATION), 
					Messagebox.OK, Messagebox.INFORMATION);
		}
	}
	private void messageBox(String mess){
		Messagebox.show(mess,pro.getKey(LBL_GENERIC_MESS_INFORMATION), 
				Messagebox.OK, Messagebox.INFORMATION);
	}
	
	/**
	 * Check that all required fields are complete.
	 * @return {@code true} when there is some validation error, false otherwise. 
	 */
	private boolean validateForm(){
		return comments.getText().trim().equals("");
	}
	
}


