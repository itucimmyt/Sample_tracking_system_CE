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
package com.cimmyt.zk.welcome;

import static com.cimmyt.utils.Constants.ATTRIBUTE_NAME_USER_BEAN;
import static com.cimmyt.utils.Constants.ATTRIBUTE_WHEAT;
import static com.cimmyt.utils.Constants.LBL_PREFS_CROP;
import static com.cimmyt.utils.Constants.LBL_PREFS_TITLE;
import static com.cimmyt.utils.Constants.LBL_SEL_iTE_CORN;
import static com.cimmyt.utils.Constants.LBL_WELCOME_CIU;
import static com.cimmyt.utils.Constants.LBL_WELCOME_DNAST;
import static com.cimmyt.utils.Constants.LBL_WELCOME_LOGIN;
import static com.cimmyt.utils.Constants.LBL_WELCOME_MES_ERROR;
import static com.cimmyt.utils.Constants.LBL_WELCOME_PASS_INCORRECT;
import static com.cimmyt.utils.Constants.LBL_WELCOME_TEXT;
import static com.cimmyt.utils.Constants.LBL_WELCOME_TITLE;
import static com.cimmyt.utils.Constants.LBL_WELCOME_WELCOME;
import static com.cimmyt.utils.Constants.LOCALE_LANGUAGE;
import static com.cimmyt.utils.Constants.LBL_TIMEOUT_MSG;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.A;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.cimmyt.bean.InvestigatorBean;
import com.cimmyt.bean.UserBean;
import com.cimmyt.utils.PropertyHelper;
import com.cimmyt.utils.StrUtils;

@SuppressWarnings("serial")
public class ControlTimeout extends Borderlayout{

	private PropertyHelper prop;
	private Label idLabelSIU;
	private Label idLabelSIU2;
	private Label labelTitle;
	private Label labelText;
	private A idLogin;
	private A idPreference;
	private Label idTimeout;

	private void changeLabel(){
		idLabelSIU.setValue(prop.getKey(LBL_WELCOME_CIU));
		idLabelSIU2.setValue("");
		idTimeout.setValue(prop.getKey(LBL_TIMEOUT_MSG));
	}


	public void init(){
		prop =  (PropertyHelper)getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);
		if(prop == null){
			prop = new PropertyHelper();
		}
		 
		
		idLabelSIU = (Label)getFellow("idLabelSIU");
		idLabelSIU2 = (Label)getFellow("idLabelSIU2");
		idLogin = (A)getFellow("idLogin");
		idPreference = (A)getFellow("idPreference");
		idTimeout = (Label) getFellow("idTimeout");
		
		EventListener<Event> rolloutEvt = new EventListener<Event>() {
			public void onEvent(Event evt) throws Exception {
				 labelTitle.setValue(prop.getKey(LBL_WELCOME_TITLE));
				 labelText.setValue(prop.getKey(LBL_WELCOME_TEXT));
			}
			
		};
		
		getDesktop().getSession().setAttribute(LOCALE_LANGUAGE, prop);
		
		UserBean userBean = (UserBean)getDesktop().getSession().getAttribute(ATTRIBUTE_NAME_USER_BEAN);
		if(userBean == null){
			idLogin.setLabel(prop.getKey(LBL_WELCOME_LOGIN));
			idPreference.setLabel(prop.getKey(LBL_PREFS_TITLE));
			idPreference.setVisible(true);
			changeLabel();
			System.out.println("session no");
		}else{
			idLogin.setLabel(prop.getKey(LBL_PREFS_TITLE));
			idPreference.setVisible(false);
			System.out.println("session yes");
			idLabelSIU.setValue(prop.getKey(LBL_WELCOME_WELCOME)+", "+userBean.getUserName());
			idLabelSIU2.setValue(prop.getKey(LBL_PREFS_CROP)+": "+userBean.getCorp());
			idTimeout.setValue(prop.getKey(LBL_TIMEOUT_MSG));
			this.getPage().setTitle(prop.getKey(LBL_WELCOME_DNAST));
		}
	}
		
		
	public void showLoginPrefs(){
		final Window win;
		
		if(getDesktop().getSession().getAttribute(ATTRIBUTE_NAME_USER_BEAN) == null){
			win = (Window) Executions.createComponents("/login.zul", null, null);
		}else{
			win = (Window) Executions.createComponents(
	    			"/preferences/preference.zul", null, null);
		}
		win.doModal();
		
	}

	public void showLogout(){
		System.out.println("closing session");
		Executions.getCurrent().getSession().invalidate();
		Executions.sendRedirect("/");
	}
	public void loadWindowPreferences(){
		final Window win = (Window) Executions.createComponents(
    			"/preferences/preference.zul", null, null);
    		win.doModal();
	}
}

