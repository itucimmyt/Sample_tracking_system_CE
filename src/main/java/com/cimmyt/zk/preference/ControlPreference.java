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
package com.cimmyt.zk.preference;

import static com.cimmyt.utils.Constants.ATTRIBUTE_NAME_USER_BEAN;
import static com.cimmyt.utils.Constants.LOCALE_LANGUAGE;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Window;

import com.cimmyt.bean.UserBean;
import com.cimmyt.utils.PropertyHelper;
import com.cimmyt.utils.PropertyHelper.Languages;

@SuppressWarnings("serial")
public class ControlPreference extends Window{
	
	private void loadContextComponent(){
		PropertyHelper prop = (PropertyHelper)getDesktop().getSession().getAttribute(LOCALE_LANGUAGE); 
		Radio radio = (Radio)getFellow("idREng");
		
		prop.setMessagesBundle(radio.isSelected()? Languages.english : Languages.spanish);
		getDesktop().getSession().setAttribute(LOCALE_LANGUAGE, prop);
	}

	public void loadValuesuser(){
		UserBean bean = getDesktop().getSession().getAttribute(ATTRIBUTE_NAME_USER_BEAN) !=null ?
				(UserBean)getDesktop().getSession().getAttribute(ATTRIBUTE_NAME_USER_BEAN):
					null;
		if (bean != null){
			//idTCron.setText(bean.getCorp());
			//getDesktop().getSession().setAttribute(ATTRIBUTE_INVESTIGATOR_CREATE,bean.getInvestigatorBean());
			
			//this.getPage().setTitle(LBL_PREFS_TITLE +" - "+ bean.getUserName() + " ("+bean.getCorp()+")");
			this.setTitle("Preferences: "+ bean.getUserName() + " ("+bean.getProfile()+")");
		}
	}
	
	public void add(){
		loadContextComponent();
		Window idWindow = (Window)getFellow("idWindow2");
		idWindow.onClose();	
		Executions.sendRedirect(null);
	}
	/** 
	 * Close Window
	 */
	public void closeWindow(){
		Window idWindow = (Window)getFellow("idWindow2");
		idWindow.onClose();
	}
}
