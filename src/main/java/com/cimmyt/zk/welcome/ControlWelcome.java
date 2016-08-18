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

import static com.cimmyt.utils.Constants.ATTRIBUTE_MAIZE;
import static com.cimmyt.utils.Constants.ATTRIBUTE_NAME_USER_BEAN;
import static com.cimmyt.utils.Constants.ATTRIBUTE_PARAM_MAP_FUNTION;
import static com.cimmyt.utils.Constants.LBL_BMS_MENU;
import static com.cimmyt.utils.Constants.LBL_GENERIC_COMPANIES;
import static com.cimmyt.utils.Constants.LBL_GENERIC_COMPANIES_DESC;
import static com.cimmyt.utils.Constants.LBL_GENERIC_INVESTIGATORS;
import static com.cimmyt.utils.Constants.LBL_GENERIC_INVESTIGATORS_DESC;
import static com.cimmyt.utils.Constants.LBL_GENERIC_LOCATION;
import static com.cimmyt.utils.Constants.LBL_GENERIC_LOCATION_DESC;
import static com.cimmyt.utils.Constants.LBL_GENERIC_PROJECTS;
import static com.cimmyt.utils.Constants.LBL_GENERIC_PROJECTS_DESC;
import static com.cimmyt.utils.Constants.LBL_GENERIC_QUERIES;
import static com.cimmyt.utils.Constants.LBL_GENERIC_QUERIES_DESC;
import static com.cimmyt.utils.Constants.LBL_GENERIC_SEASONS;
import static com.cimmyt.utils.Constants.LBL_GENERIC_SEASONS_DESC;
import static com.cimmyt.utils.Constants.LBL_GENERIC_SHIPMENTS;
import static com.cimmyt.utils.Constants.LBL_GENERIC_SHIPMENTS_DESC;
import static com.cimmyt.utils.Constants.LBL_GENERIC_STORAGE_LOCATION;
import static com.cimmyt.utils.Constants.LBL_GENERIC_STORAGE_LOCATION_DESC;
import static com.cimmyt.utils.Constants.LBL_GENERIC_STUDY;
import static com.cimmyt.utils.Constants.LBL_GENERIC_STUDY_DESC;
import static com.cimmyt.utils.Constants.LBL_GENERIC_STUDY_TEMPLATE;
import static com.cimmyt.utils.Constants.LBL_GENERIC_STUDY_TEMPLATE_DESC;
import static com.cimmyt.utils.Constants.LBL_GENERIC_TISSUES;
import static com.cimmyt.utils.Constants.LBL_GENERIC_TISSUES_DESC;
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

import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.A;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.West;
import org.zkoss.zul.Window;

import com.cimmyt.bean.InvestigatorBean;
import com.cimmyt.bean.UserBean;
import com.cimmyt.utils.PropertyHelper;
import com.cimmyt.utils.StrUtils;

@SuppressWarnings("serial")
public class ControlWelcome extends Borderlayout{

	private Vbox idContainerImages;
	private PropertyHelper prop;
	private Label idLabelSIU;
	private Label idLblProfile;
	private Label idLabelSIU2;
	private Label labelTitle;
	private Label labelText;
	private West idWest;
	private A idLogin;
	private A idPreference;
	private Image idLogout;
	private Image idHome;

	private final String STUDY_TEMPLATE = "studyTemplate";
	private final String STORE_LOCATION = "storeLocations";
	private final String STUDIES = "studies";
	private final String SERVICE_PROVIDER  = "serviceProvider";
	private final String SHIPMENT_MANAGMENT = "shipmentManagment";
	private final String PROJECTS = "projects";
	private final String RESEARCHERS = "researchers";
	private final String TISSUES = "tissues";
	private final String LOCATION = "location";
	private final String SEASON = "season";

	private void changeLabel(){
		idLabelSIU.setValue(prop.getKey(LBL_WELCOME_CIU));
		idLblProfile.setValue("");
		idLabelSIU2.setValue("");
	}


	public void init(){
		prop =  (PropertyHelper)getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);
		if(prop == null){
			prop = new PropertyHelper();
		}
		 
		loadContext();

		
		EventListener<Event> rolloverEvt = new EventListener<Event>() {
			@Override
			public void onEvent(Event evt) throws Exception {
				switch(idContainerImages.getChildren().indexOf(evt.getTarget())){
				case 0: labelTitle.setValue(prop.getKey(LBL_GENERIC_STUDY_TEMPLATE));
						labelText.setValue(prop.getKey(LBL_GENERIC_STUDY_TEMPLATE_DESC));
					break;
				case 1: labelTitle.setValue(prop.getKey(LBL_GENERIC_STORAGE_LOCATION));
						labelText.setValue(prop.getKey(LBL_GENERIC_STORAGE_LOCATION_DESC));
					break;
				case 2: labelTitle.setValue(prop.getKey(LBL_GENERIC_STUDY));
						labelText.setValue(prop.getKey(LBL_GENERIC_STUDY_DESC));
					break;
				case 3: labelTitle.setValue(prop.getKey(LBL_GENERIC_COMPANIES));
						labelText.setValue(prop.getKey(LBL_GENERIC_COMPANIES_DESC));
					break;
				case 4: labelTitle.setValue(prop.getKey(LBL_GENERIC_SHIPMENTS));
						labelText.setValue(prop.getKey(LBL_GENERIC_SHIPMENTS_DESC));
					break;
				case 5: labelTitle.setValue(prop.getKey(LBL_GENERIC_PROJECTS));
						labelText.setValue(prop.getKey(LBL_GENERIC_PROJECTS_DESC));
					break;
				case 6: labelTitle.setValue(prop.getKey(LBL_GENERIC_INVESTIGATORS));
						labelText.setValue(prop.getKey(LBL_GENERIC_INVESTIGATORS_DESC));
					break;
				case 7: labelTitle.setValue(prop.getKey(LBL_GENERIC_TISSUES));
						labelText.setValue(prop.getKey(LBL_GENERIC_TISSUES_DESC));
					break;
				case 8: labelTitle.setValue(prop.getKey(LBL_GENERIC_LOCATION));
						labelText.setValue(prop.getKey(LBL_GENERIC_LOCATION_DESC));
					break;
				case 9: labelTitle.setValue(prop.getKey(LBL_GENERIC_SEASONS));
						labelText.setValue(prop.getKey(LBL_GENERIC_SEASONS_DESC));
					break;
				case 10: labelTitle.setValue(prop.getKey(LBL_GENERIC_QUERIES));
						 labelText.setValue(prop.getKey(LBL_GENERIC_QUERIES_DESC));
					break;
				case 11: labelTitle.setValue(prop.getKey(LBL_BMS_MENU));
						 labelText.setValue(prop.getKey(LBL_BMS_MENU));
					break;
				}
				Image image =  (Image)evt.getTarget();
				image.setWidth("50px");
				image.setHeight("50px");
			}
		};
		
		EventListener<Event> rolloutEvt = new EventListener<Event>() {
			@Override
			public void onEvent(Event evt) throws Exception {
				 labelTitle.setValue(prop.getKey(LBL_WELCOME_TITLE));
				 labelText.setValue(prop.getKey(LBL_WELCOME_TEXT));
				 Image image =  (Image)evt.getTarget();
					image.setWidth("35px");
					image.setHeight("35px");
			}
			
		};
		
		
		for(Component image : idContainerImages.getChildren()){
			
			image.addEventListener("onMouseOver", rolloverEvt);
			image.addEventListener("onMouseOut", rolloutEvt);
		}
		
	
		getDesktop().getSession().setAttribute(LOCALE_LANGUAGE, prop);
		
		UserBean userBean = (UserBean)getDesktop().getSession().getAttribute(ATTRIBUTE_NAME_USER_BEAN);
		if(userBean == null){
			idWest.setOpen(true	);
			idLogin.setLabel(prop.getKey(LBL_WELCOME_LOGIN));
			idPreference.setLabel(prop.getKey(LBL_PREFS_TITLE));
			idPreference.setVisible(true);
			idLogout.setVisible(false);
			idHome.setVisible(false);
			changeLabel();
			System.out.println("session no");
		}else{
			
			idLogin.setLabel(prop.getKey(LBL_PREFS_TITLE));
			idPreference.setVisible(false);
			idLogout.setVisible(true);
			idHome.setVisible(false);
			System.out.println("session yes");
			idLabelSIU.setValue(prop.getKey(LBL_WELCOME_WELCOME)+", "+userBean.getResearcherName());
			idLblProfile.setValue(userBean.getRole().getName());
			idLabelSIU2.setValue(prop.getKey(LBL_PREFS_CROP)+": "+userBean.getCorp());
			this.getPage().setTitle(prop.getKey(LBL_WELCOME_DNAST));
			loadComponentsByFuntionMap();
		}
	}

	@SuppressWarnings("unchecked")
	private void loadComponentsByFuntionMap (){
		Map <String, String> mapFuntions;
		if (getDesktop().getSession().getAttribute(ATTRIBUTE_PARAM_MAP_FUNTION) != null){
			mapFuntions = (Map<String, String>) getDesktop().getSession().getAttribute(ATTRIBUTE_PARAM_MAP_FUNTION);
			if (mapFuntions.get(STUDY_TEMPLATE) == null){
				Image idAdd = (Image)getFellow(STUDY_TEMPLATE);
				idContainerImages.removeChild(idAdd);
			}
			if (mapFuntions.get(STORE_LOCATION) == null){
				Image idAdd = (Image)getFellow(STORE_LOCATION);
				idContainerImages.removeChild(idAdd);
			}
			if (mapFuntions.get(STUDIES) == null){
				Image idAdd = (Image)getFellow(STUDIES);
				idContainerImages.removeChild(idAdd);
			}
			if (mapFuntions.get(SERVICE_PROVIDER) == null){
				Image idAdd = (Image)getFellow(SERVICE_PROVIDER);
				idContainerImages.removeChild(idAdd);
			}
			if (mapFuntions.get(SHIPMENT_MANAGMENT) == null){
				Image idAdd = (Image)getFellow(SHIPMENT_MANAGMENT);
				idContainerImages.removeChild(idAdd);
			}
			if (mapFuntions.get(PROJECTS) == null){
				Image idAdd = (Image)getFellow(PROJECTS);
				idContainerImages.removeChild(idAdd);
			}
			if (mapFuntions.get(RESEARCHERS) == null){
				Image idAdd = (Image)getFellow(RESEARCHERS);
				idContainerImages.removeChild(idAdd);
			}
			if (mapFuntions.get(TISSUES) == null){
				Image idAdd = (Image)getFellow(TISSUES);
				idContainerImages.removeChild(idAdd);
			}
			if (mapFuntions.get(LOCATION) == null){
				Image idAdd = (Image)getFellow(LOCATION);
				idContainerImages.removeChild(idAdd);
			}
			if (mapFuntions.get(SEASON) == null){
				Image idAdd = (Image)getFellow(SEASON);
				idContainerImages.removeChild(idAdd);
			}
		}
	}
		
	private void loadContext(){
		idLabelSIU = (Label)getFellow("idLabelSIU");
		idLblProfile = (Label)getFellow("idLblProfile");
		idLabelSIU2 = (Label)getFellow("idLabelSIU2");
		idContainerImages = (Vbox)getFellow("idContainerImages");
		labelTitle     = (Label)getFellow("idHeader");
		labelText     = (Label)getFellow("idText");
		idWest = (West)getFellow("idWest");
		idLogin = (A)getFellow("idLogin");
		idPreference = (A)getFellow("idPreference");
		idLogout = (Image)getFellow("idLogout");
		idHome = (Image)getFellow("idHome");
	}
	/**
	 * validate user and password
	 */
	public void validateUserPass(){
		try{
		Textbox idTUser = (Textbox)getFellow("idTUser");
		Textbox idTPas = (Textbox)getFellow("idTPas");
		loadContext();
		PropertyHelper pro = (PropertyHelper)getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);
		if(idTUser.getText().trim().equals("") || idTPas.getText().trim().equals("")){
			StrUtils.messageBox(pro.getKey(LBL_WELCOME_MES_ERROR), pro);
		}else if (!validateUserBD(idTUser.getText().trim(), idTPas.getText().trim())){
			StrUtils.messageBox(pro.getKey(LBL_WELCOME_PASS_INCORRECT),pro);

		}else {
			UserBean bean = getUserBean();
			getDesktop().getSession().setAttribute(ATTRIBUTE_NAME_USER_BEAN, bean);
			Executions.sendRedirect("menu.zul");
		}
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	private UserBean getUserBean(){
		UserBean bean = new UserBean();
		bean.setUserName("User");
		bean.setCorp(LBL_SEL_iTE_CORN);
		bean.setProfile("Assistant");
		bean.setTypeCorp(ATTRIBUTE_MAIZE);
		InvestigatorBean inB = new InvestigatorBean();
		inB.setInvestigatorid(1);
		inB.setInvest_abbreviation("LA");
		//inB.setInvest_email("Email");
		return bean;
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

	private boolean validateUserBD(String user, String pass){
		if (!user.trim().equals("user") || !pass.trim().equals("password")){
			return false;
		}
		return true;
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

