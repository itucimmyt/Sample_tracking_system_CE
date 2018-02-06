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
import static com.cimmyt.utils.Constants.ATTRIBUTE_PARAM_MAP_FUNTION;
import static com.cimmyt.utils.Constants.ATTRIBUTE_PROJECT_ENABLED;
import static com.cimmyt.utils.Constants.INVESTIGATOR_SERVICE_BEAN_NAME;
import static com.cimmyt.utils.Constants.LABSTUDY_SERVICE_BEAN_NAME;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_DELETE_ERROR;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_DELETE_RECORD;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_DELETE_SUCCESS;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_ERROR;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_ERROR_CRITERIAL;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_INFORMATION;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_INVESTIGATOR;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_PLEASE_CONFIRM;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_SELECT_RECORD;
import static com.cimmyt.utils.Constants.LBL_INVESTIGATORS_WIN_ADD;
import static com.cimmyt.utils.Constants.LBL_INVESTIGATORS_WIN_EDIT;
import static com.cimmyt.utils.Constants.LOCALE_LANGUAGE;
import static com.cimmyt.utils.Constants.SHOW_ROWS_LIST;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.A;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.cimmyt.bean.InvestigatorBean;
import com.cimmyt.bean.UserBean;
import com.cimmyt.exception.BackException;
import com.cimmyt.service.ServiceInvestigator;
import com.cimmyt.service.ServiceLabStudy;
import com.cimmyt.service.ServiceUser;
import com.cimmyt.utils.Constants;
import com.cimmyt.utils.ConstantsDNA;
import com.cimmyt.utils.PropertyHelper;
@SuppressWarnings("serial")
public class ControlInvestigator extends Window{

	private static ServiceInvestigator serviceInvestigator = null;
	private static ServiceLabStudy serviceLabStudy = null;
	private static ServiceUser serviceUser= null;
	private PropertyHelper pro=null;
    private Logger logger= Logger.getLogger(ControlInvestigator.class);
    private Listbox idLisB;
    private final String ID_ADD = "researchers$idAdd";
    private final String ID_EDIT = "researchers$idEdit";
    private final String ID_DELETE = "researchers$idDelete";
    private Hbox idHboxResearcher;
    private UserBean userBean;
	static {
		if(serviceInvestigator == null)
        {
			try{
			serviceInvestigator = (ServiceInvestigator)SpringUtil.getBean(INVESTIGATOR_SERVICE_BEAN_NAME);
			serviceLabStudy = (ServiceLabStudy)SpringUtil.getBean(LABSTUDY_SERVICE_BEAN_NAME);
			serviceUser = (ServiceUser)SpringUtil.getBean(Constants.USER_SERVICE_BEAN_NAME);
			}catch (Exception e){
				e.printStackTrace();
			}
        }
		
	}
	/**
	 * Create components of window 
	 * @throws BackException 
	 */
	public void doAfterCompose (int size, InvestigatorBean bean ) throws BackException{
		pro = (PropertyHelper)getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);
				if (getDesktop().getSession().getAttribute(ATTRIBUTE_NAME_USER_BEAN) != null){
					userBean = (UserBean)getDesktop().getSession().getAttribute(ATTRIBUTE_NAME_USER_BEAN);
				}
				List<UserBean> listBean = null;
		if (userBean != null ){
			listBean = serviceUser.getListUserBean(userBean);
		idLisB = (Listbox)getFellow("idLisB");
		Listhead idListHead = (Listhead)getFellow("idListHead");
		clearList(idLisB);
		if (listBean != null && !listBean.isEmpty()) {
			int index = 0;
			for (UserBean beanI : listBean){
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
		loadFisheye();
	  }
	}

	@SuppressWarnings("unchecked")
	private void loadFisheye (){
		idHboxResearcher = (Hbox)getFellow("idHboxResearcher");
		Map <String, String> mapFuntions;
		if (getDesktop().getSession().getAttribute(ATTRIBUTE_PARAM_MAP_FUNTION) != null){
			mapFuntions = (Map<String, String>) getDesktop().getSession().getAttribute(ATTRIBUTE_PARAM_MAP_FUNTION);
			if (mapFuntions.get(ID_ADD) == null){
				Image id = (Image)getFellow(ID_ADD);
				idHboxResearcher.removeChild(id);
			}
			if (mapFuntions.get(ID_EDIT) == null){
				Image id = (Image)getFellow(ID_EDIT);
				idHboxResearcher.removeChild(id);
			}
			if (mapFuntions.get(ID_DELETE) == null){
				Image id = (Image)getFellow(ID_DELETE);
				idHboxResearcher.removeChild(id);
			}
		}
	}

	private void loatItem (UserBean bean){
		Listitem lIt = new Listitem ();
		Listcell cell2 = new Listcell(bean.getUserName());
		lIt.appendChild(cell2);
		Listcell cell3 = new Listcell(bean.getResearcherName());
		lIt.appendChild(cell3);
		Listcell cell4 = new Listcell(bean.getEmail());
		lIt.appendChild(cell4);
		Listcell cell5 = new Listcell(bean.getRole().getName());
		lIt.appendChild(cell5);
		Listcell cell6 = new Listcell(bean.getInvestigatorBean().getInvest_name());
		lIt.appendChild(cell6);
		Listcell cell7 = new Listcell(bean.getInvestigatorBean().getInvest_abbreviation());
		lIt.appendChild(cell7);
		
		String active = bean.isStatus() ? pro.getKey(ConstantsDNA.LBL_USERS_GRID_INACTIVE) : 
			pro.getKey(ConstantsDNA.LBL_USERS_GRID_ACTIVE);
		A link = new A();
		link.setLabel(active);
		link.setStyle("color : blue; cursor:pointer;text-decoration: underline;");
		link.addEventListener(Events.ON_CLICK, new DisableObject(bean));
		Listcell cell10 = new Listcell ();
		cell10.appendChild(link);
		lIt.appendChild(cell10);

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
	 * Add new Projects
	 * @throws BackException 
	 */
	public void add () throws BackException {
			showWindow(pro.getKey(LBL_INVESTIGATORS_WIN_ADD));
				doAfterCompose(0,null);
	}

	private void showWindow(String title) {
		final Window win = (Window) Executions.createComponents(
    			"/investigators/window_investigator.zul", null, null);
		win.setTitle(title);	
    		win.doModal();
	}
	/**
	 * Edit Project
	 * @throws BackException 
	 */
	public void editProject () throws BackException{
		pro = (PropertyHelper)getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);
		idLisB = (Listbox)getFellow("idLisB");
		if (idLisB.getSelectedIndex() != -1){
			Listitem item =idLisB.getSelectedItem();
			UserBean bean = (UserBean)item.getValue();
				getDesktop().setAttribute(ATTRIBUTE_INVESTIGATOR_ITEM,bean);
				showWindow(pro.getKey(LBL_INVESTIGATORS_WIN_EDIT));
					doAfterCompose(0,null);
					getDesktop().removeAttribute(ATTRIBUTE_INVESTIGATOR_ITEM);
		}else {
			messageBox(pro.getKey(LBL_GENERIC_MESS_SELECT_RECORD));
		}
		getDesktop().removeAttribute(ATTRIBUTE_PROJECT_ENABLED);
	}

	private void messageBox(String mess){
		Messagebox.show(mess,pro.getKey(LBL_GENERIC_MESS_INFORMATION), 
				Messagebox.OK, Messagebox.INFORMATION);
	}
	/**
	 * Delete Project
	 * @throws BackException 
	 */
	public void deleteProject () {
		pro = (PropertyHelper)getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);
		idLisB = (Listbox)getFellow("idLisB");
		if (idLisB.getSelectedIndex() != -1){
			Listitem item =idLisB.getSelectedItem();
			UserBean bean = (UserBean)item.getValue();
					if (Messagebox.show(pro.getKey(LBL_GENERIC_MESS_DELETE_RECORD), 
							pro.getKey(LBL_GENERIC_MESS_PLEASE_CONFIRM), 
							Messagebox.YES | Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
							try {
								serviceUser.deleteUser(bean);
								Messagebox.show(pro.getKey(LBL_GENERIC_MESS_DELETE_SUCCESS), 
										pro.getKey(LBL_GENERIC_MESS_INFORMATION), 
										Messagebox.OK, Messagebox.INFORMATION);
								doAfterCompose(0,null);
							}catch (Exception sql){
								Messagebox.show(pro.getKey(LBL_GENERIC_MESS_DELETE_ERROR,new String []{pro.getKey(LBL_GENERIC_MESS_INVESTIGATOR)}), 
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
		InvestigatorBean bean = new InvestigatorBean();
		if(idSN.getValue()!=null && !idSN.getValue().trim().equals("")){
			notEmty = true;
			bean.setInvest_name(idSN.getValue());
		}
		
		if(notEmty){
			//doAfterCompose(0, bean);
		}else {
			messageBox(pro.getKey(LBL_GENERIC_MESS_ERROR_CRITERIAL));
		}
	}

	class DisableObject implements EventListener<Event>{

		private UserBean bean;
		public DisableObject(UserBean _bean){
			this.bean = _bean;
		}
		@Override
		public void onEvent(Event component) throws Exception {
			if (Messagebox.show(pro.getKey(ConstantsDNA.LBL_PROJECT_PROGRAMPURPOSE_MSN_CHANGE_STATUS), 
					pro.getKey(LBL_GENERIC_MESS_PLEASE_CONFIRM), 
					Messagebox.YES | Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
				try {
					bean.setStatus(!bean.isStatus());
					serviceUser.deleteUser(bean);
					Messagebox.show(pro.getKey(Constants.LBL_GENERIC_MESS_CHANGED_STATUS_SUCCESS), 
							pro.getKey(LBL_GENERIC_MESS_INFORMATION), 
							Messagebox.OK, Messagebox.INFORMATION);
					doAfterCompose(0,null);
				}catch (Exception sql){
					Messagebox.show(pro.getKey(LBL_GENERIC_MESS_DELETE_ERROR,new String []{pro.getKey(LBL_GENERIC_MESS_INVESTIGATOR)}), 
							pro.getKey(LBL_GENERIC_MESS_ERROR), 
							Messagebox.OK, Messagebox.ERROR);
					logger.info(sql.getMessage());
				 }
			}
			
		}
		
	}
	
}
