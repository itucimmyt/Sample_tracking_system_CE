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
package com.cimmyt.zk.projects;

import static com.cimmyt.utils.Constants.ATTRIBUTE_PARAM_MAP_FUNTION;
import static com.cimmyt.utils.Constants.ATTRIBUTE_PROJECT_ENABLED;
import static com.cimmyt.utils.Constants.ATTRIBUTE_PROJECT_ITEM;
import static com.cimmyt.utils.Constants.LABSTUDY_SERVICE_BEAN_NAME;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_DELETE_ERROR;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_DELETE_RECORD;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_DELETE_SUCCESS;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_ERROR;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_ERROR_CRITERIAL;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_INFORMATION;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_PLEASE_CONFIRM;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_PROJECT;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_SELECT_RECORD;
import static com.cimmyt.utils.Constants.LBL_PROJECT_TITLE_ADD;
import static com.cimmyt.utils.Constants.LBL_PROJECT_TITLE_EDIT;
import static com.cimmyt.utils.Constants.LOCALE_LANGUAGE;
import static com.cimmyt.utils.Constants.PROJECT_SERVICE_BEAN_NAME;
import static com.cimmyt.utils.Constants.SHOW_ROWS_LIST;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.cimmyt.bean.ProjectBean;
import com.cimmyt.service.ServiceLabStudy;
import com.cimmyt.service.ServiceProject;
import com.cimmyt.utils.PropertyHelper;
@SuppressWarnings("serial")
public class ControlProject extends Window{
	
	private static ServiceProject serviceProject = null;
	private static ServiceLabStudy serviceLabStudy = null;
    private PropertyHelper pro=null;
    private Logger logger= Logger.getLogger(ControlWindowProject.class);
    private Listbox idLisBProjects;
    private final String ID_ADD = "projects$idAdd";
    private final String ID_EDIT = "projects$idEdit";
    private final String ID_DELETE = "projects$idDelete";
    private Hbox idHboxProjects;
	static {
		if(serviceProject == null)
        {
			try{
			serviceProject = (ServiceProject)SpringUtil.getBean(PROJECT_SERVICE_BEAN_NAME);
			serviceLabStudy = (ServiceLabStudy)SpringUtil.getBean(LABSTUDY_SERVICE_BEAN_NAME);
			}catch (Exception e){
				e.printStackTrace();
			}
        }
		
	}
	/**
	 * Create components of window 
	 */
	public void doAfterCompose (int size, ProjectBean bean ){
		List<ProjectBean> listBean = null;
		if (bean != null ){
			listBean = serviceProject.getProject(bean);
		}else {
			listBean = serviceProject.getProject(new ProjectBean());	
		}
		Listhead idListHead = (Listhead)getFellow("idListHead");
		idLisBProjects = (Listbox)getFellow("idLisBProjects");
		clearList(idLisBProjects);
		if (listBean != null && !listBean.isEmpty()) {
			int index = 0;
			for (ProjectBean projectBean : listBean){
				if (size == 0){
					loatItem( projectBean);
				}else {
					if (index < size){
						loatItem( projectBean);
						index ++;
					}
				}
			}
			if (listBean.size()>SHOW_ROWS_LIST){
			idLisBProjects.setMold("paging");
			idLisBProjects.setPageSize(SHOW_ROWS_LIST);
			}
		}
		idLisBProjects.appendChild(idListHead);
		loadImage ();
	}

	@SuppressWarnings("unchecked")
	private void loadImage (){
		idHboxProjects = (Hbox)getFellow("idHboxProjects");
		Map <String, String> mapFuntions;
		if (getDesktop().getSession().getAttribute(ATTRIBUTE_PARAM_MAP_FUNTION) != null){
			mapFuntions = (Map<String, String>) getDesktop().getSession().getAttribute(ATTRIBUTE_PARAM_MAP_FUNTION);
			if (mapFuntions.get(ID_ADD) == null){
				Image id = (Image)getFellow(ID_ADD);
				idHboxProjects.removeChild(id);
			}
			if (mapFuntions.get(ID_EDIT) == null){
				Image id = (Image)getFellow(ID_EDIT);
				idHboxProjects.removeChild(id);
			}
			if (mapFuntions.get(ID_DELETE) == null){
				Image id = (Image)getFellow(ID_DELETE);
				idHboxProjects.removeChild(id);
			}
		}
	}

	private void loatItem (ProjectBean projectBean){
		Listitem lIt = new Listitem ();
		/*Listcell cellID = new Listcell(projectBean.getProjectid().toString());
		lIt.appendChild(cellID);*/
		Listcell cellPN = new Listcell(projectBean.getProjectname());
		lIt.appendChild(cellPN);
		Listcell cellPD = new Listcell(projectBean.getProjectdescription());
		lIt.appendChild(cellPD);
		Listcell cellPuN = new Listcell(projectBean.getPurposename());
		lIt.appendChild(cellPuN);
		Listcell cellPuD = new Listcell(projectBean.getPurposedescription());
		lIt.appendChild(cellPuD);
		lIt.setValue(projectBean);
		idLisBProjects.appendChild(lIt);
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
	 */
	public void addProject () {
			pro = (PropertyHelper)getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);
			showWindow(pro.getKey(LBL_PROJECT_TITLE_ADD));
			ProjectBean bean = (ProjectBean)getDesktop().getAttribute(ATTRIBUTE_PROJECT_ITEM);
			if (bean != null){
				serviceProject.saveOrUpdateProject(bean);
				getDesktop().setAttribute(ATTRIBUTE_PROJECT_ITEM, null);
				doAfterCompose(0,null);
			}
	}

	private void showWindow(String title) {
		final Window win = (Window) Executions.createComponents(
    			"/projects/window_project.zul", null, null);
		win.setTitle(title);	
    		win.doModal();
	}
	/**
	 * Edit Project
	 */
	public void editProject (){
		pro = (PropertyHelper)getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);
		Listbox idLisBProjects = (Listbox)getFellow("idLisBProjects");
		if (idLisBProjects.getSelectedIndex() != -1){
			Listitem item =idLisBProjects.getSelectedItem();
			boolean enabled = serviceLabStudy.ThisProjectHasStudiesRegistred((ProjectBean)item.getValue());
			getDesktop().setAttribute(ATTRIBUTE_PROJECT_ITEM,item.getValue());
			getDesktop().setAttribute(ATTRIBUTE_PROJECT_ENABLED,enabled);
			showWindow(pro.getKey(LBL_PROJECT_TITLE_EDIT));
			ProjectBean bean = (ProjectBean)getDesktop().getAttribute(ATTRIBUTE_PROJECT_ITEM);
			if (bean != null){
				serviceProject.saveOrUpdateProject(bean);
				getDesktop().setAttribute(ATTRIBUTE_PROJECT_ITEM, null);
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

	public void deleteProject (){
		pro = (PropertyHelper)getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);
		Listbox idLisBProjects = (Listbox)getFellow("idLisBProjects");
		if (idLisBProjects.getSelectedIndex() != -1){
			if (Messagebox.show(pro.getKey(LBL_GENERIC_MESS_DELETE_RECORD), 
					pro.getKey(LBL_GENERIC_MESS_PLEASE_CONFIRM), 
					Messagebox.YES | Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
					Listitem item =idLisBProjects.getSelectedItem();
					try {
						serviceProject.deleteProject((ProjectBean)item.getValue());
						Messagebox.show(pro.getKey(LBL_GENERIC_MESS_DELETE_SUCCESS), 
								pro.getKey(LBL_GENERIC_MESS_INFORMATION), 
								Messagebox.OK, Messagebox.INFORMATION);
						doAfterCompose(0,null);
					}catch (Exception sql){
						Messagebox.show(pro.getKey(LBL_GENERIC_MESS_DELETE_ERROR, 
								new String []{pro.getKey(LBL_GENERIC_MESS_PROJECT)}), 
								pro.getKey(LBL_GENERIC_MESS_ERROR), 
								Messagebox.OK, Messagebox.ERROR);
						logger.info(sql.getMessage());
					 }
				} 
		}else {
			messageBox(pro.getKey(LBL_GENERIC_MESS_SELECT_RECORD));
		}
	}

	public void searchProject(){
		pro = (PropertyHelper)getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);
		Textbox idSPN = (Textbox)getFellow("idSPN");
		Textbox idSPD = (Textbox)getFellow("idSPD");
		Textbox idPuN = (Textbox)getFellow("idPuN");
		Textbox idSPuD = (Textbox)getFellow("idSPuD");
		boolean notEmty =false;
		ProjectBean bean = new ProjectBean();
		if(idSPN.getValue()!=null && !idSPN.getValue().trim().equals("")){
			notEmty = true;
			bean.setProjectname(idSPN.getValue());
		}
		if(idSPD.getValue()!=null && !idSPD.getValue().trim().equals("")){
			notEmty = true;
			bean.setProjectdescription(idSPD.getValue());
		}
		if(idPuN.getValue()!=null && !idPuN.getValue().trim().equals("")){
			notEmty = true;
			bean.setPurposename(idPuN.getValue());
		}
		if(idSPuD.getValue()!=null && !idSPuD.getValue().trim().equals("")){
			notEmty = true;
			bean.setPurposedescription(idSPuD.getValue());
		}
		if(notEmty){
		
			doAfterCompose(0, bean);
		}else {
			messageBox(pro.getKey(LBL_GENERIC_MESS_ERROR_CRITERIAL));
		}
	}
}
