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
import static com.cimmyt.utils.Constants.ATTRIBUTE_NAME_USER_BEAN;
import static com.cimmyt.utils.Constants.ATTRIBUTE_PARAM_MAP_FUNTION;
import static com.cimmyt.utils.Constants.ATTRIBUTE_PROJECT_ENABLED;
import static com.cimmyt.utils.Constants.COMPANY_SERVICE_BEAN_NAME;
import static com.cimmyt.utils.Constants.LBL_COMPANY_WIN_ADD;
import static com.cimmyt.utils.Constants.LBL_COMPANY_WIN_EDIT;
import static com.cimmyt.utils.Constants.LBL_GENERIC_COMPANIES;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_DELETE_ERROR;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_DELETE_RECORD;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_DELETE_SUCCESS;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_ERROR;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_ERROR_CRITERIAL;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_INFORMATION;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_PLEASE_CONFIRM;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_SELECT_RECORD;
import static com.cimmyt.utils.Constants.LOCALE_LANGUAGE;
import static com.cimmyt.utils.Constants.SHIPMENT_SERVICE_BEAN_NAME;
import static com.cimmyt.utils.Constants.SHOW_ROWS_LIST;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zkex.zul.Fisheye;
import org.zkoss.zkex.zul.Fisheyebar;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.cimmyt.bean.UserBean;
import com.cimmyt.model.bean.Company;
import com.cimmyt.model.bean.Shipment;
import com.cimmyt.service.ServiceCompany;
import com.cimmyt.service.ServiceShipment;
import com.cimmyt.utils.ConstantsDNA;
import com.cimmyt.utils.PropertyHelper;
import com.cimmyt.zk.projects.ControlWindowProject;

@SuppressWarnings("serial")
public class ControlCompanies extends Window{

	private static ServiceCompany serviceCompany = null;
	private static ServiceShipment serviceShipment = null;
	private PropertyHelper pro=null;
    private Logger logger= Logger.getLogger(ControlWindowProject.class);
    private Listbox idLisB;
    private final String ID_ADD = "serviceProvider$idAdd";
    private final String ID_EDIT = "serviceProvider$idEdit";
    private final String ID_DELETE = "serviceProvider$idDelete";
    private Fisheyebar fisheyebar;
    private UserBean userBean;

	static {
		if(serviceCompany == null)
        {
			try{
				serviceCompany = (ServiceCompany)SpringUtil.getBean(COMPANY_SERVICE_BEAN_NAME);
				serviceShipment = (ServiceShipment)SpringUtil.getBean(SHIPMENT_SERVICE_BEAN_NAME);
			}catch (Exception e){
				e.printStackTrace();
			}
        }
		
	}
	/**
	 * Create components of window 
	 */
	public void doAfterCompose (int size, Company bean ){
		pro = (PropertyHelper)getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);
		if (getDesktop().getSession().getAttribute(ATTRIBUTE_NAME_USER_BEAN) != null){
			userBean = (UserBean)getDesktop().getSession().getAttribute(ATTRIBUTE_NAME_USER_BEAN);
		}
		List<Company> listBean = null;
		if (bean != null ){
			listBean = serviceCompany.getListByFilter(bean);
		}else {
			listBean = serviceCompany.getListByFilter(new Company());	
		}
		idLisB = (Listbox)getFellow("idLisB");
		Listhead idListHead = (Listhead)getFellow("idListHead");
		clearList(idLisB);
		if (listBean != null && !listBean.isEmpty()) {
			int index = 0;
			for (Company beanI : listBean){
				if (size == 0){
					loatItem( beanI);
				}else {
					if (index < size){
						loatItem( beanI);
						index ++;
					}
				}
			}
			if (listBean.size()>SHOW_ROWS_LIST){
				idLisB.setMold("paging");
				idLisB.setPageSize(SHOW_ROWS_LIST);
			}
		}
		idLisB.appendChild(idListHead);
		 if (userBean!= null && userBean.getRole()!= null && !userBean.getRole().getIdstRol().equals(ConstantsDNA.ROLE_ADMINISTRATOR))
			 loadFisheye();
	}

	@SuppressWarnings("unchecked")
	private void loadFisheye (){
		fisheyebar = (Fisheyebar)getFellow("idFsbCompanies");
		Map <String, String> mapFuntions;
		if (getDesktop().getSession().getAttribute(ATTRIBUTE_PARAM_MAP_FUNTION) != null){
			mapFuntions = (Map<String, String>) getDesktop().getSession().getAttribute(ATTRIBUTE_PARAM_MAP_FUNTION);
			if (mapFuntions.get(ID_ADD) == null){
				Fisheye id = (Fisheye)getFellow(ID_ADD);
				fisheyebar.removeChild(id);
			}
			if (mapFuntions.get(ID_EDIT) == null){
				Fisheye id = (Fisheye)getFellow(ID_EDIT);
				fisheyebar.removeChild(id);
			}
			if (mapFuntions.get(ID_DELETE) == null){
				Fisheye id = (Fisheye)getFellow(ID_DELETE);
				fisheyebar.removeChild(id);
			}
		}
	}
	
	private void loatItem (Company bean){
		Listitem lIt = new Listitem ();
		/*Listcell cell1 = new Listcell(bean.getIdCompany().toString());
		lIt.appendChild(cell1);*/
		Listcell cell2 = new Listcell(bean.getName());
		lIt.appendChild(cell2);
		Listcell cell3 = new Listcell(bean.getEmail());
		lIt.appendChild(cell3);
		Listcell cell4 = new Listcell(bean.getAddresss());
		lIt.appendChild(cell4);
		Listcell cell5 = new Listcell(bean.getContactname());
		lIt.appendChild(cell5);
		Listcell cell6 = new Listcell(bean.getPhone());
		lIt.appendChild(cell6);
		Listcell cell7 = new Listcell(bean.getStoragelocation().getLname());
		lIt.appendChild(cell7);
		lIt.setValue(bean);
		idLisB.appendChild(lIt);
	}

	private void clearList(Listbox list){
		if (list !=null && !list.getItems().isEmpty()) {
			while (!list.getItems().isEmpty()) {
				list.getChildren().remove(0);
			}
		}
	}
	/**
	 * Add new Season
	 */
	public void add () {
			pro = (PropertyHelper)getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);
			showWindow(pro.getKey(LBL_COMPANY_WIN_ADD));
			Company bean = (Company)getDesktop().getAttribute(ATTRIBUTE_COMPANY_ITEM);
			if (bean != null){
				serviceCompany.add(bean);
				getDesktop().setAttribute(ATTRIBUTE_COMPANY_ITEM, null);
				doAfterCompose(0,null);
			}
	}

	private void showWindow(String title) {
		final Window win = (Window) Executions.createComponents(
    			"/companies/window_company.zul", null, null);
		win.setTitle(title);	
    		win.doModal();
	}
	/**
	 * Edit Season
	 */
	public void edit (){
		pro = (PropertyHelper)getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);
		idLisB = (Listbox)getFellow("idLisB");
		if (idLisB.getSelectedIndex() != -1){
			Listitem item =idLisB.getSelectedItem();
			Company beanI = (Company)item.getValue();
			List<Shipment> hasItem = serviceShipment.listOfShipmentsToACompany(beanI);
			boolean disabled = false;
			if (hasItem != null && !hasItem.isEmpty()){
				disabled = true;
			}
				getDesktop().setAttribute(ATTRIBUTE_COMPANY_ITEM,beanI);
				getDesktop().setAttribute(ATTRIBUTE_PROJECT_ENABLED,disabled);
				showWindow(pro.getKey(LBL_COMPANY_WIN_EDIT));
				Company bean = (Company)getDesktop().getAttribute(ATTRIBUTE_COMPANY_ITEM);
				if (bean != null){
					serviceCompany.add(bean);
					getDesktop().removeAttribute(ATTRIBUTE_COMPANY_ITEM);
					doAfterCompose(0,null);
				}
		
				
			}else {
				messageBox(pro.getKey(LBL_GENERIC_MESS_SELECT_RECORD));
			}
		getDesktop().removeAttribute(ATTRIBUTE_PROJECT_ENABLED);
	}

	private void messageBox(String mess){
		Messagebox.show(mess,pro.getKey(LBL_GENERIC_MESS_INFORMATION), 
				Messagebox.OK, Messagebox.INFORMATION);
	}

	public void delete (){
		pro = (PropertyHelper)getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);
		idLisB = (Listbox)getFellow("idLisB");
		if (idLisB.getSelectedIndex() != -1){
			Listitem item =idLisB.getSelectedItem();
			Company beanI = (Company)item.getValue();
		
					if (Messagebox.show(pro.getKey(LBL_GENERIC_MESS_DELETE_RECORD), 
							pro.getKey(LBL_GENERIC_MESS_PLEASE_CONFIRM), 
							Messagebox.YES | Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
							try {
								serviceCompany.delete(beanI);
								Messagebox.show(pro.getKey(LBL_GENERIC_MESS_DELETE_SUCCESS), 
										pro.getKey(LBL_GENERIC_MESS_INFORMATION), 
										Messagebox.OK, Messagebox.INFORMATION);
								doAfterCompose(0,null);
							}catch (Exception sql){
								Messagebox.show(pro.getKey(LBL_GENERIC_MESS_DELETE_ERROR,new String []{pro.getKey(LBL_GENERIC_COMPANIES)}), 
										pro.getKey(LBL_GENERIC_MESS_ERROR), 
										Messagebox.OK, Messagebox.ERROR);
								logger.info(sql.getMessage());
							 }
						}
		
		}else {
			messageBox(pro.getKey(LBL_GENERIC_MESS_SELECT_RECORD));
		}
	}

	public void search(){
		pro = (PropertyHelper)getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);
		Textbox idSN = (Textbox)getFellow("idSN");

		boolean notEmty =false;
		Company bean = new Company();
		if(idSN.getValue()!=null && !idSN.getValue().trim().equals("")){
			notEmty = true;
			bean.setName(idSN.getValue());
		}
		
		if(notEmty){

			doAfterCompose(0, bean);
		}else {
			messageBox(pro.getKey(LBL_GENERIC_MESS_ERROR_CRITERIAL));
		}
	}	
}
