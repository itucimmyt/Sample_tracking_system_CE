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
package com.cimmyt.zk.studyTemplate;

import static com.cimmyt.utils.Constants.ATTRIBUTE_NAME_USER_BEAN;
import static com.cimmyt.utils.Constants.ATTRIBUTE_PARAM_MAP_FUNTION;
import static com.cimmyt.utils.Constants.ATTRIBUTE_PROJECT_ENABLED;
import static com.cimmyt.utils.Constants.ATTRIBUTE_SEASON_ITEM;
import static com.cimmyt.utils.Constants.ATTRIBUTE_STUDY_TEMPLATE_ITEM;
import static com.cimmyt.utils.Constants.LABSTUDY_SERVICE_BEAN_NAME;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_DELETE_ERROR;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_DELETE_RECORD;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_DELETE_SUCCESS;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_ERROR;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_ERROR_CRITERIAL;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_INFORMATION;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_PLEASE_CONFIRM;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_SELECT_RECORD;
import static com.cimmyt.utils.Constants.LBL_GENERIC_STUDY_TEMPLATE;
import static com.cimmyt.utils.Constants.LBL_STUDY_TEMPLATE_WIN_ADD;
import static com.cimmyt.utils.Constants.LBL_STUDY_TEMPLATE_WIN_EDIT;
import static com.cimmyt.utils.Constants.LOCALE_LANGUAGE;
import static com.cimmyt.utils.Constants.SHOW_ROWS_LIST;
import static com.cimmyt.utils.Constants.STUDY_TEMPLATE_SERVICE_BEAN_NAME;

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
import com.cimmyt.model.bean.StudyTemplate;
import com.cimmyt.service.SeriviceStudyTemplate;
import com.cimmyt.service.ServiceLabStudy;
import com.cimmyt.utils.ConstantsDNA;
import com.cimmyt.utils.PropertyHelper;
import com.cimmyt.zk.projects.ControlWindowProject;

@SuppressWarnings("serial")
public class ControlStudyTemplate extends Window {

	private static SeriviceStudyTemplate seriviceStudyTemplate = null;
	private static ServiceLabStudy serviceLabStudy = null;
	private PropertyHelper pro=null;
    private Logger logger= Logger.getLogger(ControlWindowProject.class);
    private Listbox idLisB;
    private Fisheyebar idFsbStudyTemplate;
    private UserBean userBean;
    private final String ID_STUDY_TEMPLATE_ADD = "studyTemplate$idAdd";
    private final String ID_STUDY_TEMPLATE_EDIT = "studyTemplate$idEdit";
    private final String ID_STUDY_TEMPLATE_DELETE = "studyTemplate$idDelete";
	static {
		if(seriviceStudyTemplate == null)
        {
			try{
				seriviceStudyTemplate = (SeriviceStudyTemplate)SpringUtil.getBean(STUDY_TEMPLATE_SERVICE_BEAN_NAME);
				serviceLabStudy = (ServiceLabStudy)SpringUtil.getBean(LABSTUDY_SERVICE_BEAN_NAME);
			}catch (Exception e){
				e.printStackTrace();
			}
        }
		
	}
	/**
	 * Create components of window 
	 */
	public void doAfterCompose (int size, StudyTemplate bean ){
		List<StudyTemplate> listBean = null;
		if (bean != null ){
			listBean = seriviceStudyTemplate.getStudyTemplate(bean);
		}else {
			listBean = seriviceStudyTemplate.getStudyTemplate(new StudyTemplate());	
		}
		if (getDesktop().getSession().getAttribute(ATTRIBUTE_NAME_USER_BEAN) != null){
			userBean = (UserBean)getDesktop().getSession().getAttribute(ATTRIBUTE_NAME_USER_BEAN);
		}
		idLisB = (Listbox)getFellow("idLisB");
		Listhead idListHead = (Listhead)getFellow("idListHead");
		clearList(idLisB);
		if (listBean != null && !listBean.isEmpty()) {
			int index = 0;
			for (StudyTemplate beanI : listBean){
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
		idFsbStudyTemplate = (Fisheyebar)getFellow("idFsbStudyTemplate");
		Map <String, String> mapFuntions;
		if (getDesktop().getSession().getAttribute(ATTRIBUTE_PARAM_MAP_FUNTION) != null){
			mapFuntions = (Map<String, String>) getDesktop().getSession().getAttribute(ATTRIBUTE_PARAM_MAP_FUNTION);
			if (mapFuntions.get(ID_STUDY_TEMPLATE_ADD) == null){
				Fisheye idAdd = (Fisheye)getFellow(ID_STUDY_TEMPLATE_ADD);
				idFsbStudyTemplate.removeChild(idAdd);
			}
			if (mapFuntions.get(ID_STUDY_TEMPLATE_EDIT) == null){
				Fisheye idAdd = (Fisheye)getFellow(ID_STUDY_TEMPLATE_EDIT);
				idFsbStudyTemplate.removeChild(idAdd);
			}
			if (mapFuntions.get(ID_STUDY_TEMPLATE_DELETE) == null){
				Fisheye idAdd = (Fisheye)getFellow(ID_STUDY_TEMPLATE_DELETE);
				idFsbStudyTemplate.removeChild(idAdd);
			}
		}
	}
	private void loatItem (StudyTemplate bean){
		Listitem lIt = new Listitem ();
		Listcell cell2 = new Listcell(bean.getTemplatename());
		lIt.appendChild(cell2);
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
			showWindow(pro.getKey(LBL_STUDY_TEMPLATE_WIN_ADD));
			StudyTemplate bean = (StudyTemplate)getDesktop().getAttribute(ATTRIBUTE_STUDY_TEMPLATE_ITEM);
			if (bean != null){
				seriviceStudyTemplate.add(bean);
				getDesktop().setAttribute(ATTRIBUTE_STUDY_TEMPLATE_ITEM, null);
				doAfterCompose(0,null);
			}
	}

	private void showWindow(String title) {
		final Window win = (Window) Executions.createComponents(
    			"/study_template/window_study_template.zul", null, null);
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
			StudyTemplate beanI = (StudyTemplate)item.getValue();
				boolean enabled = serviceLabStudy.ThisTemplateHasLabStudyRegistred(beanI);
				getDesktop().setAttribute(ATTRIBUTE_STUDY_TEMPLATE_ITEM,beanI);
				getDesktop().setAttribute(ATTRIBUTE_PROJECT_ENABLED,enabled);
				showWindow(pro.getKey(LBL_STUDY_TEMPLATE_WIN_EDIT));
				StudyTemplate bean = (StudyTemplate)getDesktop().getAttribute(ATTRIBUTE_STUDY_TEMPLATE_ITEM);
				if (bean != null){
					seriviceStudyTemplate.add(bean);
					getDesktop().removeAttribute(ATTRIBUTE_SEASON_ITEM);
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
			StudyTemplate beanI = (StudyTemplate)item.getValue();
					if (Messagebox.show(pro.getKey(LBL_GENERIC_MESS_DELETE_RECORD), 
							pro.getKey(LBL_GENERIC_MESS_PLEASE_CONFIRM), 
							Messagebox.YES | Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
							try {
								seriviceStudyTemplate.delete(beanI);
								Messagebox.show(pro.getKey(LBL_GENERIC_MESS_DELETE_SUCCESS), 
										pro.getKey(LBL_GENERIC_MESS_INFORMATION), 
										Messagebox.OK, Messagebox.INFORMATION);
								doAfterCompose(0,null);
							}catch (Exception sql){
								Messagebox.show(pro.getKey(LBL_GENERIC_MESS_DELETE_ERROR,new String []{pro.getKey(LBL_GENERIC_STUDY_TEMPLATE)}), 
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
		StudyTemplate bean = new StudyTemplate();
		if(idSN.getValue()!=null && !idSN.getValue().trim().equals("")){
			notEmty = true;
			bean.setTemplatename(idSN.getValue());
		}
		
		if(notEmty){
			doAfterCompose(0, bean);
		}else {
			messageBox(pro.getKey(LBL_GENERIC_MESS_ERROR_CRITERIAL));
		}
	}
}
