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
package com.cimmyt.zk.tissue;

import static com.cimmyt.utils.Constants.ATTRIBUTE_PROJECT_ENABLED;
import static com.cimmyt.utils.Constants.ATTRIBUTE_TISSUE_ITEM;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_ERROR;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_ER_DIF_REG;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_FIELD_REQUIRED;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_INFORMATION;
import static com.cimmyt.utils.Constants.LBO_INVESTIGATOR_NAME;
import static com.cimmyt.utils.Constants.LOCALE_LANGUAGE;
import static com.cimmyt.utils.Constants.TISSUE_SERVICE_BEAN_NAME;

import org.apache.log4j.Logger;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.cimmyt.bean.TissueBean;
import com.cimmyt.service.ServiceTissue;
import com.cimmyt.utils.PropertyHelper;
import com.cimmyt.zk.investigator.ControlWindowInvestigator;

@SuppressWarnings("serial")
public class ControlWindowTissue extends Window {

	private Intbox id;
	private Textbox idName;
	private Label idL1;
	private PropertyHelper pro=null;
	private TissueBean bean;
	Logger logger= Logger.getLogger(ControlWindowInvestigator.class);
	private static ServiceTissue serviceTissue = null;
	static {
		if(serviceTissue == null)
        {
			try{
				serviceTissue = (ServiceTissue)SpringUtil.getBean(TISSUE_SERVICE_BEAN_NAME);
			}catch (Exception e){
				e.printStackTrace();
			}
        }
		
	}
	/** 
	 * Close Window
	 */
	public void closeWindow(){
		Window idWindow = (Window)getFellow("idWindow");
		idWindow.onClose();
	}
	/**
	 * Load Context of window tissue
	 */
	public void loadContextAttribute(){
		bean = (TissueBean)getDesktop().getAttribute(ATTRIBUTE_TISSUE_ITEM);
		boolean disabled = false;
		if (getDesktop().getAttribute(ATTRIBUTE_PROJECT_ENABLED) != null){
			disabled = (Boolean)getDesktop().getAttribute(ATTRIBUTE_PROJECT_ENABLED);
		}
		loadComponents();
		if(bean != null){
			 id.setValue(bean.getIdtissue());
			 idName.setValue(bean.getTissueName());
			 idName.setDisabled(disabled);
		}else {
			logger.error("Error in load Attribute Item Investigator");
		}
		idName.setFocus(true);
	}
	/**
	 * Add new Tissue
	 */
	public void add(){
		pro = (PropertyHelper)getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);
		loadComponents();
		showLabelReq(false);
		boolean showMessage = false;
		if (validateText(idName)){
			showMessage = true;
			idL1.setVisible(true);
		}
		if (showMessage){
			messageBox(pro.getKey(LBL_GENERIC_MESS_FIELD_REQUIRED));	
		}else {
			TissueBean bean =  new TissueBean();
			bean.setIdtissue(id.getValue());
			bean.setTissueName(idName.getValue().trim());
			TissueBean sBean = this.bean == null ? serviceTissue.getTissueByName(bean.getTissueName()) : null;
			if (sBean == null ){
				getDesktop().setAttribute(ATTRIBUTE_TISSUE_ITEM, bean);
				Window idWindow = (Window)getFellow("idWindow");
				idWindow.onClose();				
			}else {
				Messagebox.show(pro.getKey(LBL_GENERIC_MESS_ER_DIF_REG,new String []{pro.getKey(LBO_INVESTIGATOR_NAME)}), 
						pro.getKey(LBL_GENERIC_MESS_ERROR), 
						Messagebox.OK, Messagebox.ERROR);
			}
		}
	}
	
	private void showLabelReq(boolean visible){
		idL1.setVisible(visible);
	}
	private void messageBox(String mess){
		Messagebox.show(mess,pro.getKey(LBL_GENERIC_MESS_INFORMATION), 
				Messagebox.OK, Messagebox.INFORMATION);
	}

	private boolean validateText(Textbox text){
		return text.getText().trim().equals("");
	}

	private void loadComponents(){
		id = (Intbox)getFellow("id");
		idName = (Textbox)getFellow("idName");
		idL1 = (Label)getFellow("idL1");
	}
}
