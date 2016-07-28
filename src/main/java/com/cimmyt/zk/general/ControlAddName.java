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
package com.cimmyt.zk.general;
import static com.cimmyt.utils.Constants.ATTRIBUTE_GENERIC_NAME;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_DELETE_ERROR;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_ERROR;
import static com.cimmyt.utils.Constants.LBL_GENERIC_WINDOW_ERR_EXIST;
import static com.cimmyt.utils.Constants.LOCALE_LANGUAGE;
import static com.cimmyt.utils.Constants.RESULT_PREFERENCE_SERVICE;
import static com.cimmyt.utils.Constants.WINDOW_ERR_DESKTOP;
import static com.cimmyt.utils.Constants.WINDOW_FIELD_ITEM;

import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.cimmyt.service.ServiceResultPreference;
import com.cimmyt.utils.PropertyHelper;

@SuppressWarnings("serial")
public class ControlAddName extends Window{

	private PropertyHelper pro=null;
	private static ServiceResultPreference serviceResultPreference;
	static {
		if (serviceResultPreference == null){
			try{
				serviceResultPreference = (ServiceResultPreference)SpringUtil.getBean(RESULT_PREFERENCE_SERVICE);
			}catch (Exception exResults){
				exResults.printStackTrace();
			}
		}
	}
	public void  loadContext (){
		pro=(PropertyHelper) getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);
	}
	
	public void changeItem() throws InterruptedException{
		String item = (String)getDesktop().getAttribute(ATTRIBUTE_GENERIC_NAME);
		if (item!=null && !item.trim().equals("")){
			Textbox idTexBoxRename= (Textbox)getFellow("idTexBoxRename");
			String text =idTexBoxRename.getText();
			if (text!=null && !text.trim().equals("")){
				text= changeCharacter(text);
				if(serviceResultPreference.isThisNameRegistredInResultsPreferences(text)){
					Messagebox.show(pro.getKey(LBL_GENERIC_WINDOW_ERR_EXIST), 
							pro.getKey(LBL_GENERIC_MESS_ERROR), 
							Messagebox.OK, Messagebox.ERROR);
					return;
				}else {
					getDesktop().setAttribute(ATTRIBUTE_GENERIC_NAME, text.toUpperCase());
					Window window= (Window)getFellow("idWindowRename");
					window.onClose();
				}
			}else{
				Messagebox.show(pro.getKey(WINDOW_FIELD_ITEM));
				return;	
			}
		}else{
			Messagebox.show(pro.getKey(WINDOW_ERR_DESKTOP));
			return;
		}
	}
	
	public void addItem() throws InterruptedException{
		String item = (String)getDesktop().getAttribute(ATTRIBUTE_GENERIC_NAME);
		if (item!=null && !item.trim().equals("")){
			getDesktop().removeAttribute(ATTRIBUTE_GENERIC_NAME);
		}else{
			Textbox idTexBoxRename= (Textbox)getFellow("idTexBoxRename");
			String text =idTexBoxRename.getText();
			if (text!=null && !text.trim().equals("")){
				text= changeCharacter(text);
				if(serviceResultPreference.isThisNameRegistredInResultsPreferences(text)){
					Messagebox.show(pro.getKey(LBL_GENERIC_WINDOW_ERR_EXIST), 
							pro.getKey(LBL_GENERIC_MESS_ERROR), 
							Messagebox.OK, Messagebox.ERROR);
					return;
				}else {
					getDesktop().setAttribute(ATTRIBUTE_GENERIC_NAME, text.toUpperCase());
					
					Window window= (Window)getFellow("idWindowRename");
					window.onClose();
				}
			}else{
				Messagebox.show(pro.getKey(WINDOW_FIELD_ITEM));
				return;	
			}
		}
		
	}

	private String changeCharacter(String text){
		if (text!=null && !text.trim().equals("")){
			text.replace(",", "");
			text.replace("-", "");
			text.replace(".", "");
			text.replace("|", "");
			}
		return text;
		
	}
}
