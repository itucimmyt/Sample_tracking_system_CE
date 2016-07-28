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
package com.cimmyt.zk.investigator;

import static com.cimmyt.utils.Constants.ATTRIBUTE_INVESTIGATOR_ITEM;
import static com.cimmyt.utils.Constants.ATTRIBUTE_NAME_USER_BEAN;
import static com.cimmyt.utils.Constants.INVESTIGATOR_SERVICE_BEAN_NAME;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_FIELD_REQUIRED;
import static com.cimmyt.utils.Constants.LOCALE_LANGUAGE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.A;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.cimmyt.bean.InvestigatorBean;
import com.cimmyt.bean.RoleBean;
import com.cimmyt.bean.UserBean;
import com.cimmyt.bean.UserFuntionsBean;
import com.cimmyt.exception.BackException;
import com.cimmyt.model.bean.Register;
import com.cimmyt.rest.context.SpringApplicationContext;
import com.cimmyt.service.ServiceInvestigator;
import com.cimmyt.service.ServiceRegister;
import com.cimmyt.service.ServiceUser;
import com.cimmyt.utils.Constants;
import com.cimmyt.utils.ConstantsDNA;
import com.cimmyt.utils.PropertyHelper;
import com.cimmyt.utils.StrUtils;

@SuppressWarnings("serial")
public class ControlWindowInvestigator extends Window {

	private Combobox idComboRole;
	private PropertyHelper pro=null;
	private UserBean userBean;
	private Label idLblUserNameManda;
	private Label idLblPasswordManda;
	private Label idLblNameManda;
	private Label idLblEmailManda;
	private Label idLblResearcherNanda;
	private Label idLblAbreviationManda;

	private Label idLblUserName;
	private Label idLblPassword;
	private Label idLblName;
	private Label idLblEmail;
	private Label idLblFromResearcher;
	private Label idLblResearcher;
	private Label idLblResearcherName;
	private Label idLblAbreviation;
	private Combobox idComRegister ;
	private Textbox idTxtUserName;
	private Textbox idTxtPassword;
	private Textbox idTxtName;
	private Textbox idTxtEmail;
	private Combobox idComResearcher;
	private Textbox idTxtResearcherName;
	private Textbox idTxtResearcherAbreviation;
	private Rows idRowsUser ;
	private int idRol;
	private Checkbox idChkBFuntions;
	private A idLinkFuntions;
	private static ServiceInvestigator serviceInvestigator = null;
	private static ServiceRegister serviceRegister;
	private static ServiceUser serviceUser= null;
	private Map <Integer, Integer> mapFunctions = new HashMap<Integer,Integer>();
	private UserBean newUserBean;
	private UserBean beanEdit;
	private Window idWindow ;
	static {
		if(serviceInvestigator == null)
        {
			try{
			serviceInvestigator = (ServiceInvestigator)SpringUtil.getBean(INVESTIGATOR_SERVICE_BEAN_NAME);
			}catch (Exception e){
				e.printStackTrace();
			}
        }
		try{
			if (serviceRegister == null){
				serviceRegister = (ServiceRegister)SpringApplicationContext.getBean(Constants.REGISTER_SERVICE_BEAN);
			}
			}catch (Exception ex){
				ex.printStackTrace();
			}
		try{
			if (serviceUser == null){
				serviceUser = (ServiceUser)SpringApplicationContext.getBean(Constants.USER_SERVICE_BEAN_NAME);
			}
		}catch (Exception ex){
			ex.printStackTrace();
		}
		
	}
	Logger logger= Logger.getLogger(ControlWindowInvestigator.class);
	/** 
	 * Close Window
	 */
	public void closeWindow(){
		getDesktop().setAttribute(ATTRIBUTE_INVESTIGATOR_ITEM, null);
		deleteAttributes();
		idWindow.onClose();
	}

	private void deleteAttributes(){
		getDesktop().getSession().removeAttribute(Constants.ATTRIBUTE_RESEARCHER_ROLE_MAP_FUNCTION);
		getDesktop().getSession().removeAttribute(Constants.ATTRIBUTE_RESEARCHER_ROLE_FUNCTION);
	}
	public void loadContextAttribute(){
		loadComponents();
		loadMap();
		pro = (PropertyHelper)getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);
		 beanEdit = (UserBean)getDesktop().getAttribute(ATTRIBUTE_INVESTIGATOR_ITEM);
		if (getDesktop().getSession().getAttribute(ATTRIBUTE_NAME_USER_BEAN) != null){
			userBean = (UserBean)getDesktop().getSession().getAttribute(ATTRIBUTE_NAME_USER_BEAN);
			loadComboProfile(userBean.getRole().getIdstRol());
			setEventComboboxRoles();
		}
		initComponent();
		if(beanEdit != null){
			for (org.zkoss.zk.ui.Component compo :idComboRole.getChildren()){
				Comboitem item = (Comboitem)compo;
				int id = item.getValue();
				if (beanEdit.getRole().getIdstRol().intValue() == id){
					idComboRole.setSelectedItem(item);
				}
			}
			fillFieldsfromUserEdit(beanEdit.getRole().getIdstRol().intValue());
			callBuildingComponets(beanEdit.getRole().getIdstRol().intValue());
			logger.debug("There is an object for stage edit..");
		}
	}

	private void initComponent (){
		 idWindow = (Window)getFellow("idWindow");
		initLabelsMandatory();
		loadlabel();
		loadTextFormat();
		fillComboListResearcher();
		setEventComboResearcher();
		fillComboUserRegister();
	}

	private void setEventComboboxRoles(){
		idComboRole.addEventListener(Events.ON_SELECT,
				new EventListener<Event>(){
					@Override
					public void onEvent(Event arg0) throws Exception {
						int id = idComboRole.getSelectedItem().getValue();
						initComponent();
						cleanRows();
						callBuildingComponets(id);
						if (beanEdit != null){
							fillFieldsfromUserEdit(id);
							if (beanEdit.getRole().getIdstRol().intValue() != id)
								mapFunctions.clear();
						}
						}
					}
				);
	}

	private void fillFieldsfromUserEdit (int id ){
		idTxtUserName.setText(beanEdit.getUserName());
		idTxtPassword.setText(beanEdit.getPassword());
		idTxtName.setText(beanEdit.getResearcherName());
		idTxtEmail.setText(beanEdit.getEmail());
		switch (id){
		case ConstantsDNA.ROLE_ADMINISTRATOR:
			idRol = id;
			break;
		case ConstantsDNA.ROLE_DATA_MANAGER:
			idRol = id;
			idTxtResearcherName.setText(beanEdit.getInvestigatorBean().getInvest_name());
			idTxtResearcherAbreviation.setText(beanEdit.getInvestigatorBean().getInvest_abbreviation());
			selectComboResearcher();
			break;
		case ConstantsDNA.ROLE_RESEARCHER:
			idTxtResearcherName.setText(beanEdit.getInvestigatorBean().getInvest_name());
			idTxtResearcherAbreviation.setText(beanEdit.getInvestigatorBean().getInvest_abbreviation());
			idRol = id;
			selectComboResearcher();
			break;
		case ConstantsDNA.ROLE_RESEARCHER_ASSISTENT:
			idTxtResearcherName.setText(beanEdit.getInvestigatorBean().getInvest_name());
			idTxtResearcherAbreviation.setText(beanEdit.getInvestigatorBean().getInvest_abbreviation());
			idRol = id;
			beanEdit.getSetFuntionsBean();
			loadMapFuntions();
			selectComboResearcher();
			break;
		case ConstantsDNA.ROLE_ASSISTENT:
			idTxtResearcherName.setText(beanEdit.getInvestigatorBean().getInvest_name());
			idTxtResearcherAbreviation.setText(beanEdit.getInvestigatorBean().getInvest_abbreviation());
			idRol = id;
			loadMapFuntions();
			selectComboResearcher();
			break;
		}
	}

	private void selectComboResearcher (){
		if (idComResearcher != null && idComResearcher.getChildren() !=  null && !idComResearcher.getChildren().isEmpty()){
			for (Component component : idComResearcher.getChildren()){
				Comboitem item = (Comboitem)component;
				InvestigatorBean beanResearcher = (InvestigatorBean)item.getValue();
				if (beanEdit.getInvestigatorBean().getInvestigatorid().equals(beanResearcher.getInvestigatorid())){
					idComResearcher.setSelectedItem(item);
					if (userBean.getRole().getIdstRol().intValue() == ConstantsDNA.ROLE_RESEARCHER)
					idComResearcher.setDisabled(true);	
					
				}
			}
		}
	}

	private void loadMapFuntions(){
		if (beanEdit != null && beanEdit.getSetFuntionsBean() != null && !beanEdit.getSetFuntionsBean().isEmpty()) {
			mapFunctions.clear();
			for (UserFuntionsBean userFunctions : beanEdit.getSetFuntionsBean()){
				mapFunctions.put(userFunctions.getFuntions().getIdstFuntions(), userFunctions.getFuntions().getIdstFuntions());
			}
			getDesktop().getSession().setAttribute(Constants.ATTRIBUTE_RESEARCHER_ROLE_MAP_FUNCTION, mapFunctions);
		}
	}
	private void callBuildingComponets(int id ){
		switch (id){
		case ConstantsDNA.ROLE_ADMINISTRATOR:
			buidSetUserBaseFilds();
			idRol = id;
			break;
		case ConstantsDNA.ROLE_DATA_MANAGER:
			idRowsUser.appendChild(crearteComponentComboFromRequest());
			buidSetUserBaseFilds();
			idRowsUser.appendChild(crearteComponentComboResearcher());
			idRol = id;
			loadResearcherData();
			break;
		case ConstantsDNA.ROLE_RESEARCHER:
			idRowsUser.appendChild(crearteComponentComboFromRequest());
			buidSetUserBaseFilds();
			idRol = id;
			idRowsUser.appendChild(createComponentsNameResearcherAbre());
			break;
		case ConstantsDNA.ROLE_RESEARCHER_ASSISTENT:
			idRowsUser.appendChild(crearteComponentComboFromRequest());
			buidSetUserBaseFilds();
			idRowsUser.appendChild(crearteComponentComboResearcher());
			idRowsUser.appendChild(createComponentFuntions());
			setMapByRoleResearcharAssistent();
			idRol = id;
			break;
		case ConstantsDNA.ROLE_ASSISTENT:
			idRowsUser.appendChild(crearteComponentComboFromRequest());
			buidSetUserBaseFilds();
			idRowsUser.appendChild(crearteComponentComboResearcher());
			idRowsUser.appendChild(createComponentFuntions());
			setMapByRoleResearcharAssistent();
			idRol = id;
			break;
		}
	}

	private void setMapByRoleResearcharAssistent(){
		mapFunctions.remove(new Integer(30));
		mapFunctions.remove(new Integer(31));
		mapFunctions.remove(new Integer(32));
		mapFunctions.remove(new Integer(33));
		if (idRol == ConstantsDNA.ROLE_ASSISTENT ){
			mapFunctions.remove(new Integer(4));
			mapFunctions.remove(new Integer(8));
			mapFunctions.remove(new Integer(14));
			mapFunctions.remove(new Integer(21));
			mapFunctions.remove(new Integer(25));
			mapFunctions.remove(new Integer(29));
			mapFunctions.remove(new Integer(33));
			mapFunctions.remove(new Integer(37));
			mapFunctions.remove(new Integer(41));
			mapFunctions.remove(new Integer(45));
		}
		
	}
	private void loadResearcherData (){
		idRowsUser.appendChild(createComponentsNameResearcherName());
		idRowsUser.appendChild(createComponentsNameResearcherAbre());
	}

	private Row createComponentFuntions(){
		Row row = new Row();
		Cell cel = new Cell ();
		row.appendChild(cel);
		Cell celTxt = new Cell();
		Hbox box = new Hbox();
		idChkBFuntions = new Checkbox(pro.getKey(Constants.LBO_INVESTIGATOR_WIN_REGIS_USER_DEFAULT_FUNCTIONS));
		box.appendChild(idChkBFuntions);
		Separator separtor = new Separator();
		box.appendChild(separtor);
		idLinkFuntions = new A(pro.getKey(Constants.LBO_INVESTIGATOR_WIN_REGIS_USER_SET_FUNCTIONS));
		idLinkFuntions.addEventListener(Events.ON_CLICK,
				new EventListener<Event>(){
			@SuppressWarnings("unchecked")
			@Override
			public void onEvent(Event arg0) throws Exception {
				getDesktop().getSession().setAttribute(Constants.ATTRIBUTE_RESEARCHER_ROLE_FUNCTION, idRol);
				final Window win = (Window) Executions.createComponents(
		    			"/investigators/window_functions.zul", null, null);
				win.setTitle(pro.getKey(Constants.LBO_INVESTIGATOR_WIN_REGIS_USER_SET_FUNCTIONS));	
		    		win.doModal();
		    		if (getDesktop().getSession().getAttribute(Constants.ATTRIBUTE_RESEARCHER_ROLE_MAP_FUNCTION) != null){
		    			mapFunctions = (Map<Integer,Integer>)getDesktop().getSession().getAttribute(Constants.ATTRIBUTE_RESEARCHER_ROLE_MAP_FUNCTION);
		    		}
			}
		}
		);
		box.appendChild(idLinkFuntions);
		celTxt.appendChild(box);
		row.appendChild(celTxt);
		return row;
	}

	private Row createComponentsNameResearcherName(){
		Row row = new Row();
		Cell cel = new Cell ();
		cel.appendChild(idLblResearcherNanda);
		cel.appendChild(idLblResearcherName);
		row.appendChild(cel);
		Cell celTxt = new Cell();
		celTxt.appendChild(idTxtResearcherName);
		row.appendChild(celTxt);
		return row;
	}

	private Row createComponentsNameResearcherAbre(){
		Row row = new Row();
		Cell cel = new Cell ();
		cel.appendChild(idLblAbreviationManda);
		cel.appendChild(idLblAbreviation);
		row.appendChild(cel);
		Cell celTxt = new Cell();
		celTxt.appendChild(idTxtResearcherAbreviation);
		row.appendChild(celTxt);
		return row;
	}

	private Row crearteComponentComboResearcher(){
		Row row = new Row();
		Cell cel = new Cell ();
		cel.appendChild(idLblResearcher);
		row.appendChild(cel);
		Cell celCompo = new Cell();
		celCompo.appendChild(idComResearcher);
		row.appendChild(celCompo);
		return row;
	}

	private Row crearteComponentComboFromRequest(){
		Row row = new Row();
		Cell cel = new Cell ();
		cel.appendChild(idLblFromResearcher);
		row.appendChild(cel);
		Cell celCompo = new Cell();
		celCompo.appendChild(idComRegister);
		row.appendChild(celCompo);
		return row;
	}
	private void buidSetUserBaseFilds(){
		idRowsUser.appendChild(createComponentsUser());
		idRowsUser.appendChild(createComponentsPassword());
		idRowsUser.appendChild(createComponentsUsrName());
		idRowsUser.appendChild(createComponentsEmail());
	}
	private Row createComponentsEmail(){
		Row row = new Row();
		Cell cel = new Cell ();
		cel.appendChild(idLblEmailManda);
		cel.appendChild(idLblEmail);
		row.appendChild(cel);
		Cell celTxt = new Cell();
		celTxt.appendChild(idTxtEmail);
		row.appendChild(celTxt);
		return row;
	}

	private Row createComponentsUsrName(){
		Row row = new Row();
		Cell cel = new Cell ();
		cel.appendChild(idLblNameManda);
		cel.appendChild(idLblName);
		row.appendChild(cel);
		Cell celTxt = new Cell();
		celTxt.appendChild(idTxtName);
		row.appendChild(celTxt);
		return row;
	}

	private Row createComponentsUser(){
		Row row = new Row();
		Cell cel = new Cell ();
		cel.appendChild(idLblUserNameManda);
		cel.appendChild(idLblUserName);
		row.appendChild(cel);
		Cell celTxt = new Cell();
		celTxt.appendChild(idTxtUserName);
		row.appendChild(celTxt);
		return row;
	}

	private Row createComponentsPassword(){
		Row row = new Row();
		Cell cel = new Cell ();
		cel.appendChild(idLblPasswordManda);
		cel.appendChild(idLblPassword);
		row.appendChild(cel);
		Cell celTxt = new Cell();
		celTxt.appendChild(idTxtPassword);
		row.appendChild(celTxt);
		return row;
	}
	private void cleanRows(){
		while (!idRowsUser.getChildren().isEmpty())
			idRowsUser.getChildren().remove(0);
	}
	private void setEventComboRegister(){
		idComRegister.addEventListener(Events.ON_SELECT,
				new EventListener<Event>(){
					@Override
					public void onEvent(Event arg0) throws Exception {
						Register bean = idComRegister.getSelectedItem().getValue();
						idTxtName.setText(bean.getFisrtName()+ " "+bean.getLastName());
						idTxtEmail.setText(bean.getEmail());
							
						}
					}
				);
	}

	private void fillComboUserRegister(){
		idComRegister = new Combobox ();
		idComRegister.setReadonly(true);
		idComRegister.setCols(100);
		idComRegister.setWidth("200px");
		Register register = new Register();
		register.setIsPending(true);
		List<Register> list  = serviceRegister.getListRegister(register);
		if (list != null && !list.isEmpty()){
			for (Register bean : list){
				Comboitem item = new Comboitem(bean.getFisrtName()+ " "+ bean.getLastName());
				item.setValue(bean);
				item.setDescription(bean.getEmail());
				idComRegister.appendChild(item);
				
			}
			
		}
		setEventComboRegister();
	}
	
	private void setEventComboResearcher(){
		idComResearcher.addEventListener(Events.ON_SELECT,
				new EventListener<Event>(){
					@Override
					public void onEvent(Event arg0) throws Exception {
						InvestigatorBean bean = idComResearcher.getSelectedItem().getValue();
						idTxtResearcherName.setText(bean.getInvest_name());
						idTxtResearcherAbreviation.setText(bean.getInvest_abbreviation());	
						}
					}
				);
	}
	private void fillComboListResearcher(){
		idComResearcher = new Combobox ();
		idComResearcher.setReadonly(true);
		idComResearcher.setWidth("200px");
		idComResearcher.setCols(80);
		List<InvestigatorBean> listBean = serviceInvestigator.getInvestigator(new InvestigatorBean());
		if (listBean != null && !listBean.isEmpty()){
			for (InvestigatorBean bean : listBean){
				Comboitem item = new Comboitem(bean.getInvest_name());
				item.setValue(bean);
				idComResearcher.appendChild(item);
				if (userBean != null && userBean.getRole() != null && 
						userBean.getRole().getIdstRol().intValue() == ConstantsDNA.ROLE_RESEARCHER){
					if (userBean.getInvestigatorBean() != null && userBean.getInvestigatorBean().getInvestigatorid().intValue() == bean.getInvestigatorid().intValue()){
						idComResearcher.setSelectedItem(item);
						idComResearcher.setDisabled(true);
					}
				}
			}
		}
	}

	private void initLabelsMandatory(){
		String character = "*";
		String sclass = "labelWindow";
		idLblUserNameManda = new Label (character);
		idLblUserNameManda.setSclass(sclass);
		idLblUserNameManda.setId("idLblUserNameManda");
		idLblPasswordManda = new Label (character);
		idLblPasswordManda.setSclass(sclass);
		idLblPasswordManda.setId("idLblPasswordManda");
		idLblNameManda = new Label (character);
		idLblNameManda.setSclass(sclass);
		idLblNameManda.setId("idLblNameManda");
		idLblEmailManda = new Label (character);
		idLblEmailManda.setSclass(sclass);
		idLblEmailManda.setId("idLblEmailManda");
		idLblResearcherNanda = new Label (character);
		idLblResearcherNanda.setSclass(sclass);
		idLblResearcherNanda.setId("idLblResearcherNanda");
		idLblAbreviationManda = new Label (character);
		idLblAbreviationManda.setSclass(sclass);
		idLblAbreviationManda.setId("idLblAbreviationManda");
	}

	private void loadTextFormat (){
		String sclass = "textIpnput";
		idTxtUserName = new Textbox ();
		idTxtUserName.setSclass(sclass);
		idTxtUserName.setMaxlength(15);
		idTxtUserName.setCols(25);
		idTxtPassword = new Textbox();
		idTxtPassword.setSclass(sclass);
		idTxtPassword.setMaxlength(15);
		idTxtPassword.setCols(25);
		idTxtName = new Textbox();
		idTxtName.setSclass(sclass);
		idTxtName.setMaxlength(50);
		idTxtName.setCols(50);
		idTxtEmail = new Textbox();
		idTxtEmail.setSclass(sclass);
		idTxtEmail.setMaxlength(70);
		idTxtEmail.setCols(70);
		idTxtResearcherName = new Textbox();
		idTxtResearcherName.setSclass(sclass);
		idTxtResearcherName.setMaxlength(70);
		idTxtResearcherName.setCols(120);
		idTxtResearcherAbreviation = new Textbox();
		idTxtResearcherAbreviation.setSclass(sclass);
		idTxtResearcherAbreviation.setMaxlength(2);
		idTxtResearcherAbreviation.setCols(2);
	}

	private void loadlabel(){
		idLblUserName = new Label (pro.getKey(ConstantsDNA.LBL_USER_MODULE_USER_NAME));
		idLblPassword = new Label (pro.getKey(ConstantsDNA.LBL_USER_MODULE_PASSWORD));
		idLblName = new Label (pro.getKey(Constants.LIST_PROJECT_QUALIFIER2));
		idLblEmail = new Label (pro.getKey(ConstantsDNA.LBL_USER_MODULE_EMAIL));
		idLblFromResearcher = new Label(pro.getKey(Constants.LBO_INVESTIGATOR_WIN_REGIS_USER));
		idLblResearcher = new Label (pro.getKey(Constants.LIST_STUDY_QUALIFIER3));
		idLblResearcherName = new Label (pro.getKey(Constants.LIST_PROJECT_QUALIFIER2));
		idLblAbreviation = new Label (pro.getKey(Constants.LIST_STUDY_QUALIFIER4));
	}

	private void loadComboProfile(int id){
		switch (id){
		case ConstantsDNA.ROLE_ADMINISTRATOR :
			setComboItem(pro.getKey(ConstantsDNA.LBL_ROLE_DESCRPTION_ADMINISTRATOR),ConstantsDNA.ROLE_ADMINISTRATOR);
			setComboItem(pro.getKey(ConstantsDNA.LBL_ROLE_DESCRPTION_DATA_MANAGER),ConstantsDNA.ROLE_DATA_MANAGER);
			setComboItem(pro.getKey(ConstantsDNA.LBL_ROLE_DESCRPTION_RESEARCHER),ConstantsDNA.ROLE_RESEARCHER);
			setComboItem(pro.getKey(ConstantsDNA.LBL_ROLE_DESCRPTION_RESEARCHER_ASSISTANT),ConstantsDNA.ROLE_RESEARCHER_ASSISTENT);
			setComboItem(pro.getKey(ConstantsDNA.LBL_ROLE_DESCRPTION_ASSISTANT),ConstantsDNA.ROLE_ASSISTENT);
			break;
		case ConstantsDNA.ROLE_DATA_MANAGER :
			setComboItem(pro.getKey(ConstantsDNA.LBL_ROLE_DESCRPTION_DATA_MANAGER),ConstantsDNA.ROLE_DATA_MANAGER);
			setComboItem(pro.getKey(ConstantsDNA.LBL_ROLE_DESCRPTION_RESEARCHER),ConstantsDNA.ROLE_RESEARCHER);
			setComboItem(pro.getKey(ConstantsDNA.LBL_ROLE_DESCRPTION_RESEARCHER_ASSISTANT),ConstantsDNA.ROLE_RESEARCHER_ASSISTENT);
			setComboItem(pro.getKey(ConstantsDNA.LBL_ROLE_DESCRPTION_ASSISTANT),ConstantsDNA.ROLE_ASSISTENT);
			break;
		case ConstantsDNA.ROLE_RESEARCHER :
			setComboItem(pro.getKey(ConstantsDNA.LBL_ROLE_DESCRPTION_RESEARCHER_ASSISTANT),ConstantsDNA.ROLE_RESEARCHER_ASSISTENT);
			setComboItem(pro.getKey(ConstantsDNA.LBL_ROLE_DESCRPTION_ASSISTANT),ConstantsDNA.ROLE_ASSISTENT);
			break;
		}
	}

	private void setComboItem(String label, int value){
		Comboitem item;
		item = new Comboitem(label);
		item.setValue(value);
		idComboRole.appendChild(item);
	}
	public void add() throws BackException{
		pro = (PropertyHelper)getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);
		loadComponents();
		newUserBean = new UserBean ();
		if (!validateFieldsRequirement(idRol)){
			return ;
		}else {
			if (beanEdit!= null ){
				UserBean userDB = serviceUser.getUser(beanEdit);
				if (userDB != null && userDB.getIdUser().intValue() != userDB.getIdUser().intValue()){
					StrUtils.messageBoxError(pro.getKey(ConstantsDNA.LBL_USER_ERROR_ALREADY_RECORDED),pro);
					return;
				}
			}else{
				if (serviceUser.validateUserBynameOrUserOrEmail(newUserBean)){
					StrUtils.messageBoxError(pro.getKey(ConstantsDNA.LBL_USER_ERROR_ALREADY_RECORDED),pro);
					return;
				}	
			}	
			int id  = beanEdit != null && beanEdit.getIdUser()!= null && beanEdit.getIdUser().intValue() > 0 ? beanEdit.getIdUser().intValue() : 0;
			try{
				serviceUser.saveOrUpdatetUser(newUserBean, getListMapFunctions(), id);
				if (idComRegister.getSelectedItem() != null ){
					Comboitem item =idComRegister.getSelectedItem();
					Register bean = (Register)item.getValue();
					serviceRegister.delete(bean);
				}
				deleteAttributes();
				idWindow.onClose();	
			}catch (Exception ex){
				logger.error(ex.getMessage(), ex);
				ex.printStackTrace();
				deleteAttributes();
				idWindow.onClose();
			}
		}
	}

	private boolean validateFieldsRequirement(int idRole){
		if (!validateBaseFields()){
			StrUtils.messageBoxError(pro.getKey(LBL_GENERIC_MESS_FIELD_REQUIRED),pro);
		return false;
		}
		if (!StrUtils.isValidEmail(idTxtEmail.getText())){
			StrUtils.messageBoxError(pro.getKey(ConstantsDNA.LBL_REGISTER_ERROR_EMAIL_INVALID),pro);
			return false;
		}
		newUserBean.setOrganism(userBean.getOrganism());
	    newUserBean.setEmail(idTxtEmail.getText());
	    newUserBean.setResearcherName(idTxtName.getText());
	    newUserBean.setUserName(idTxtUserName.getText());
	    newUserBean.setPassword(idTxtPassword.getText());
		switch (idRole){
			case ConstantsDNA.ROLE_ADMINISTRATOR:{
					InvestigatorBean researcher = new InvestigatorBean();
					if (userBean.getOrganism().getOrganismid().equals(new Integer(Constants.ATTRIBUTE_MAIZE)))
					    researcher.setInvestigatorid(new Integer (Constants.ATTRIBUTE_MAIZE_ADMINISTRATOR));
					    else 
					    	researcher.setInvestigatorid(new Integer (Constants.ATTRIBUTE_WHEAT_ADMINISTRATOR));
					    newUserBean.setInvestigatorBean(researcher);
					    RoleBean roleBean  = new RoleBean();
					    roleBean.setIdstRol(ConstantsDNA.ROLE_ADMINISTRATOR);
					    newUserBean.setRole(roleBean);
					    loadMap();
					break;
				}
			case ConstantsDNA.ROLE_DATA_MANAGER:{
				InvestigatorBean researcher = new InvestigatorBean();
				if (idComResearcher.getSelectedIndex() != -1){
					researcher = (InvestigatorBean) idComResearcher.getSelectedItem().getValue();
				}else if (isValidTextField(idTxtResearcherAbreviation) && isValidTextField(idTxtResearcherName)){
					researcher.setInvest_abbreviation(idTxtResearcherAbreviation.getText().toUpperCase());
					researcher.setInvest_name(idTxtResearcherName.getText());
					if (beanEdit != null && beanEdit.getInvestigatorBean() != null && beanEdit.getInvestigatorBean().getInvestigatorid()!= null && 
							beanEdit.getInvestigatorBean().getInvestigatorid().intValue() > 0 && beanEdit.getInvestigatorBean().getInvest_name().equals(researcher.getInvest_name())){
						researcher.setInvestigatorid(beanEdit.getInvestigatorBean().getInvestigatorid());
					}
				}else {
					StrUtils.messageBoxError(pro.getKey(LBL_GENERIC_MESS_FIELD_REQUIRED),pro);
					return false;
				   }
				newUserBean.setInvestigatorBean(researcher);
			    RoleBean roleBean  = new RoleBean();
			    roleBean.setIdstRol(ConstantsDNA.ROLE_DATA_MANAGER);
			    newUserBean.setRole(roleBean);
			    loadMap();
				break;
				}
			case ConstantsDNA.ROLE_RESEARCHER:{
				InvestigatorBean researcher = new InvestigatorBean();
				if (isValidTextField(idTxtResearcherAbreviation) && isValidTextField(idTxtName)){
					researcher.setInvest_abbreviation(idTxtResearcherAbreviation.getText().toUpperCase());
					researcher.setInvest_name(idTxtName.getText());
					if (beanEdit != null && beanEdit.getInvestigatorBean() != null && beanEdit.getInvestigatorBean().getInvestigatorid()!= null && 
							beanEdit.getInvestigatorBean().getInvestigatorid().intValue() > 0 && beanEdit.getInvestigatorBean().getInvest_name().equals(researcher.getInvest_name())){
						researcher.setInvestigatorid(beanEdit.getInvestigatorBean().getInvestigatorid());
					}
				}else {
					StrUtils.messageBoxError(pro.getKey(LBL_GENERIC_MESS_FIELD_REQUIRED),pro);
					return false;
				   }
				newUserBean.setInvestigatorBean(researcher);
			    RoleBean roleBean  = new RoleBean();
			    roleBean.setIdstRol(ConstantsDNA.ROLE_RESEARCHER);
			    newUserBean.setRole(roleBean);
			    loadMap();
				break;
			}
			case ConstantsDNA.ROLE_RESEARCHER_ASSISTENT:{
				InvestigatorBean researcher = new InvestigatorBean();
				if (idComResearcher.getSelectedIndex() != -1){
					researcher = (InvestigatorBean) idComResearcher.getSelectedItem().getValue();
				}else {
					StrUtils.messageBoxError(pro.getKey(Constants.LBO_INVESTIGATOR_WIN_REGIS_SELECT_RESEARCHER),pro);
					return false;
				}
				if (beanEdit != null && beanEdit.getRole() != null && beanEdit.getRole().getIdstRol() != null){
					if (beanEdit.getRole().getIdstRol().intValue() != idRole){
						if (idChkBFuntions.isChecked()){
							loadMap();
							setMapByRoleResearcharAssistent();
						}else {
							StrUtils.messageBoxError(pro.getKey(Constants.LBO_INVESTIGATOR_WIN_RESEARCHER_ADD_SET_FUNCTIONS),pro);
							return false;
						}
					}
				}else
				if (!idChkBFuntions.isChecked() && mapFunctions.size() == 0){
					StrUtils.messageBoxError(pro.getKey(Constants.LBO_INVESTIGATOR_WIN_RESEARCHER_ADD_SET_FUNCTIONS),pro);
					return false;
				}
				newUserBean.setInvestigatorBean(researcher);
			    RoleBean roleBean  = new RoleBean();
			    roleBean.setIdstRol(ConstantsDNA.ROLE_RESEARCHER_ASSISTENT);
			    newUserBean.setRole(roleBean);
				break;
			}
			case ConstantsDNA.ROLE_ASSISTENT:{
				InvestigatorBean researcher = new InvestigatorBean();
				if (idComResearcher.getSelectedIndex() != -1){
					researcher = (InvestigatorBean) idComResearcher.getSelectedItem().getValue();
				}else {
					StrUtils.messageBoxError(pro.getKey(Constants.LBO_INVESTIGATOR_WIN_REGIS_SELECT_RESEARCHER),pro);
					return false;
				}
				
				if (beanEdit != null && beanEdit.getRole() != null && beanEdit.getRole().getIdstRol() != null){
					if (beanEdit.getRole().getIdstRol().intValue() != idRole){
						if (idChkBFuntions.isChecked()){
							loadMap();
							setMapByRoleResearcharAssistent();
						}else {
							StrUtils.messageBoxError(pro.getKey(Constants.LBO_INVESTIGATOR_WIN_RESEARCHER_ADD_SET_FUNCTIONS),pro);
							return false;
						}
					}
				}else
				if (!idChkBFuntions.isChecked() && mapFunctions.size() == 0){
					StrUtils.messageBoxError(pro.getKey(Constants.LBO_INVESTIGATOR_WIN_RESEARCHER_ADD_SET_FUNCTIONS),pro);
					return false;
				}
				newUserBean.setInvestigatorBean(researcher);
			    RoleBean roleBean  = new RoleBean();
			    roleBean.setIdstRol(ConstantsDNA.ROLE_ASSISTENT);
			    newUserBean.setRole(roleBean);
				break;
				}
			}
		return true;
	}

	private boolean validateBaseFields(){
		boolean isValidAll = true;
		if (!isValidTextField(idTxtUserName))
			isValidAll = false;
		if (!isValidTextField(idTxtPassword))
			isValidAll = false;
		if (!isValidTextField(idTxtName))
			isValidAll = false;
		if (!isValidTextField(idTxtEmail))
			isValidAll = false;
		return isValidAll;
	}

	private boolean isValidTextField(Textbox box){
		if (box != null && box.getText() != null && !box.getText().trim().equals(""))
		return true;
		else return false;
	}
	private void loadComponents(){
		idComboRole = (Combobox)getFellow("idComboRole");
		idRowsUser = (Rows)getFellow("idRowsUser");
	}

	private List<Integer> getListMapFunctions(){
		 List<Integer>  listFunctions = new ArrayList<Integer>();
		Iterator<Integer> iterator = mapFunctions.keySet().iterator();
		while (iterator.hasNext()){
			listFunctions.add(iterator.next());
		}
		return listFunctions;
	}
	private void loadMap (){
		for (int i = 1 ; i <= 45; i++ ){
			Integer integer = new Integer(i);
			mapFunctions.put(integer, integer);
		}
	}
}
