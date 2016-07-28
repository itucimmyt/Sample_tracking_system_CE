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
package com.cimmyt.zk.companies;

import static com.cimmyt.utils.Constants.ATTRIBUTE_COMPANY_ITEM;
import static com.cimmyt.utils.Constants.ATTRIBUTE_PROJECT_ENABLED;
import static com.cimmyt.utils.Constants.COMPANY_SERVICE_BEAN_NAME;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_ERROR;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_FIELD_REQUIRED;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_INFORMATION;
import static com.cimmyt.utils.Constants.LBO_INVESTIGATOR_WIN_REGIS;
import static com.cimmyt.utils.Constants.LOCALE_LANGUAGE;

import org.apache.log4j.Logger;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.cimmyt.model.bean.Company;
import com.cimmyt.service.ServiceCompany;
import com.cimmyt.utils.PropertyHelper;
import com.cimmyt.zk.investigator.ControlWindowInvestigator;

@SuppressWarnings("serial")
public class ControlWindowCompany extends Window {

	private Intbox id;
	private Textbox idName;
	private Textbox idAd;
	private Textbox idEmail;
	private Textbox idCN;
	private Textbox idNum;
	private Label idL1;
	private Label idL2;
	private PropertyHelper pro=null;
	private static ServiceCompany serviceCompany = null;
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
	Logger logger= Logger.getLogger(ControlWindowInvestigator.class);
	/** 
	 * Close Window
	 */
	public void closeWindow(){
		Window idWindow = (Window)getFellow("idWindow");
		getDesktop().removeAttribute(ATTRIBUTE_COMPANY_ITEM);
		idWindow.onClose();
	}
	/**
	 * Load Item page
	 */
	public void loadContextAttribute(){
		Company bean = (Company)getDesktop().getAttribute(ATTRIBUTE_COMPANY_ITEM);
		boolean disabled = false;
		if (getDesktop().getAttribute(ATTRIBUTE_PROJECT_ENABLED) != null){
			disabled = (Boolean)getDesktop().getAttribute(ATTRIBUTE_PROJECT_ENABLED);
		}
		loadComponents();
		if(bean != null){
			 id.setValue(bean.getIdCompany());
			 idName.setValue(bean.getName());
			 //idName.setDisabled(disabled);
			 idAd.setValue(bean.getAddresss());
			 idEmail.setValue(bean.getEmail());
			 idCN.setValue(bean.getContactname());
			 idNum.setValue(bean.getPhone());
		}else {
			logger.error("Error in load Attribute Item Investigator");
		}
		idName.setFocus(true);
	}
	/**
	 * Add Company
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
			Company bean =  new Company();
			bean.setIdCompany(id.getValue());
			bean.setName(idName.getValue().trim());
			bean.setAddresss(idAd.getValue().trim());
			bean.setEmail(idEmail.getValue().trim());
			bean.setContactname(idCN.getValue().trim());
			bean.setPhone(idNum.getValue().trim());
			Company beanA = (Company)getDesktop().getAttribute(ATTRIBUTE_COMPANY_ITEM);
			if (beanA == null){
				Company sBean = serviceCompany.getCompanyByName(bean.getName());
				if (sBean == null){
					getDesktop().setAttribute(ATTRIBUTE_COMPANY_ITEM, bean);
					Window idWindow = (Window)getFellow("idWindow");
					idWindow.onClose();	
				}else {
					Messagebox.show(pro.getKey(LBO_INVESTIGATOR_WIN_REGIS), 
							pro.getKey(LBL_GENERIC_MESS_ERROR), 
							Messagebox.OK, Messagebox.ERROR);
				}
			}else {
				getDesktop().setAttribute(ATTRIBUTE_COMPANY_ITEM, bean);
				Window idWindow = (Window)getFellow("idWindow");
				idWindow.onClose();	
			}
			
			
		}
	}

	private void loadComponents(){
		id = (Intbox)getFellow("id");
		idName = (Textbox)getFellow("idName");
		idAd = (Textbox)getFellow("idAd");
		idEmail = (Textbox)getFellow("idEmail");
		idCN = (Textbox)getFellow("idCN");
		idNum = (Textbox)getFellow("idNum");
		idL1 = (Label)getFellow("idL1");
		idL2 = (Label)getFellow("idL2");
	}

	private void showLabelReq(boolean visible){
		idL1.setVisible(visible);
		idL2.setVisible(visible);
	}
	private void messageBox(String mess){
		Messagebox.show(mess,pro.getKey(LBL_GENERIC_MESS_INFORMATION), 
				Messagebox.OK, Messagebox.INFORMATION);
	}

	private boolean validateText(Textbox text){
		return text.getText().trim().equals("");
	}
}
