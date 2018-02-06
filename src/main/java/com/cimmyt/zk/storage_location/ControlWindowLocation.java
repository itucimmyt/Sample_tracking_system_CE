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
package com.cimmyt.zk.storage_location;

import static com.cimmyt.utils.Constants.ATTRIBUTE_STORAGE_LOCATION_ITEM;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_FIELD_REQUIRED;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_INFORMATION;
import static com.cimmyt.utils.Constants.LOCALE_LANGUAGE;
import static com.cimmyt.utils.Constants.SEASON_SERVICE_BEAN_NAME;

import org.apache.log4j.Logger;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.cimmyt.model.bean.StorageLocation;
import com.cimmyt.service.ServiceSeason;
import com.cimmyt.utils.PropertyHelper;
import com.cimmyt.zk.investigator.ControlWindowInvestigator;

@SuppressWarnings("serial")
public class ControlWindowLocation extends Window{

	private Textbox idSN;
	private Textbox idName;
	private Textbox idCom;
	private Label idL0;
	private Label idL1;
	private Label idL2;
	private PropertyHelper pro=null;
	private static ServiceSeason serviceSeason = null;
	static {
		if(serviceSeason == null)
        {
			try{
				serviceSeason = (ServiceSeason)SpringUtil.getBean(SEASON_SERVICE_BEAN_NAME);
				}catch (Exception e){
				e.printStackTrace();
			}
        }
		
	}
	Logger logger= Logger.getLogger(ControlWindowLocation.class);
	/** 
	 * Close Window
	 */
	public void closeWindow(){
		Window idWindow = (Window)getFellow("idWindow");
		getDesktop().setAttribute(ATTRIBUTE_STORAGE_LOCATION_ITEM, null);
		idWindow.onClose();
	}
	/**
	 * Load Window Components
	 */
	public void loadContextAttribute(){
		StorageLocation bean = (StorageLocation)getDesktop().getAttribute(ATTRIBUTE_STORAGE_LOCATION_ITEM);
		loadComponents();
		if(bean != null){
			 idSN.setValue(bean.getLsname());
			 idName.setValue(bean.getLname());
			 idCom.setValue(bean.getComments());
		}
		idSN.setFocus(true);
	}
	/**
	 * Add new Season
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
		if (validateText(idCom)){
			showMessage = true;
			idL2.setVisible(true);
		}
		if (validateText(idSN)){
			showMessage = true;
			idL2.setVisible(true);
		}
		if (showMessage){
			messageBox(pro.getKey(LBL_GENERIC_MESS_FIELD_REQUIRED));	
		}else {
			StorageLocation bean = (StorageLocation)getDesktop().getAttribute(ATTRIBUTE_STORAGE_LOCATION_ITEM);
			System.out.println("Storage loation .. "+bean);
			if (bean == null){
				bean = new StorageLocation();
			}
			bean.setLsname(idSN.getValue());
			bean.setLname(idName.getValue());
			bean.setComments(idCom.getValue());
			
				getDesktop().setAttribute(ATTRIBUTE_STORAGE_LOCATION_ITEM, bean);
				Window idWindow = (Window)getFellow("idWindow");
				idWindow.onClose();	
		}
	}

	private void loadComponents(){
		idSN = (Textbox)getFellow("idSN");
		idName = (Textbox)getFellow("idName");
		idCom = (Textbox)getFellow("idCom");
		idL1 = (Label)getFellow("idL1");
		idL0 = (Label)getFellow("idL0");
		idL2 = (Label)getFellow("idL2");
	}

	private void showLabelReq(boolean visible){
		idL0.setVisible(visible);
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
