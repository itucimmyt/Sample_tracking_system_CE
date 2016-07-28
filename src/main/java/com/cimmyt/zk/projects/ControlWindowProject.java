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

import static com.cimmyt.utils.Constants.ATTRIBUTE_PROJECT_ENABLED;
import static com.cimmyt.utils.Constants.ATTRIBUTE_PROJECT_ITEM;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_ERROR;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_ER_DIF_REG;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_FILL_FIELD;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_INFORMATION;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_PURPOSE;
import static com.cimmyt.utils.Constants.LBO_INVESTIGATOR_NAME;
import static com.cimmyt.utils.Constants.LOCALE_LANGUAGE;
import static com.cimmyt.utils.Constants.PROJECT_SERVICE_BEAN_NAME;

import org.apache.log4j.Logger;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.cimmyt.bean.ProjectBean;
import com.cimmyt.service.ServiceProject;
import com.cimmyt.utils.PropertyHelper;

@SuppressWarnings("serial")
public class ControlWindowProject extends Window{

	private Intbox idProject;
	private Textbox idProjNam;
	private Textbox idProjDes;
	private Textbox idPurNam;
	private Textbox idPurNamD;
	private Logger logger= Logger.getLogger(ControlWindowProject.class);
	private static ServiceProject serviceProject = null;
	private ProjectBean projectBean;
	private PropertyHelper pro=null;
	static {
		if(serviceProject == null)
        {
			try{
			serviceProject = (ServiceProject)SpringUtil.getBean(PROJECT_SERVICE_BEAN_NAME);
			}catch (Exception e){
				e.printStackTrace();
			}
        }
		
	}
	/** 
	 * Close Window
	 */
	public void closeWindow(){
		Window idWindow = (Window)getFellow("idWindow");
		idWindow.onClose();
	}
	/**
	 * Add new Project control
	 */
	public void addProject (){
		pro = (PropertyHelper)getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);
		loadComponents();
		if (validateText(idProjNam)||validateText(idProjDes)
				|| validateText(idPurNam) || validateText(idPurNamD)){
			messageBox(pro.getKey(LBL_GENERIC_MESS_FILL_FIELD));
		}else{
			ProjectBean bean = new ProjectBean ();
			bean.setProjectid(idProject.getValue());
			bean.setProjectname(idProjNam.getValue().trim());
			bean.setProjectdescription(idProjDes.getValue().trim());
			bean.setPurposename(idPurNam.getValue().trim());
			bean.setPurposedescription(idPurNamD.getValue().trim());
			ProjectBean sBean  = projectBean == null ?  serviceProject.getProjectWithName(bean.getProjectname()+
					bean.getPurposename()): null;
			if (sBean == null){
				ProjectBean beanAttribute = (ProjectBean)getDesktop().getAttribute(ATTRIBUTE_PROJECT_ITEM);
				if (beanAttribute != null){
					bean.setLastsampleid(beanAttribute.getLastsampleid());
				}
				 getDesktop().setAttribute(ATTRIBUTE_PROJECT_ITEM,bean);
				 Window idWindow = (Window)getFellow("idWindow");
					idWindow.onClose();	
			}else{
				Messagebox.show(pro.getKey(LBL_GENERIC_MESS_ER_DIF_REG,new String []{pro.getKey(LBO_INVESTIGATOR_NAME)+" + "+
											pro.getKey(LBL_GENERIC_MESS_PURPOSE)}), 
						pro.getKey(LBL_GENERIC_MESS_ERROR), 
						Messagebox.OK, Messagebox.ERROR);
			}
		} 
	}

	private boolean validateText(Textbox text){
		return text.getText().trim().equals("");
	}

	private void messageBox(String mess){
		Messagebox.show(mess,pro.getKey(LBL_GENERIC_MESS_INFORMATION), 
				Messagebox.OK, Messagebox.INFORMATION);
	}

	private void loadComponents (){
		idProject = (Intbox)getFellow("idProject");
		idProjNam = (Textbox)getFellow("idProjNam");
		idProjDes = (Textbox)getFellow("idProjDes");
		idPurNam = (Textbox)getFellow("idPurNam");
		idPurNamD = (Textbox)getFellow("idPurNamD");
	}
	/**
	 * Load Context of Window 
	 */
	public void loadContextAttribute(){
		projectBean =	(ProjectBean)getDesktop().getAttribute(ATTRIBUTE_PROJECT_ITEM);
		boolean disabled =false;
		if(getDesktop().getAttribute(ATTRIBUTE_PROJECT_ENABLED) != null){
			disabled = (Boolean)getDesktop().getAttribute(ATTRIBUTE_PROJECT_ENABLED);	
		}
		loadComponents();
		if (projectBean != null){
			idProject.setValue(projectBean.getProjectid());
			idProjNam.setValue(projectBean.getProjectname());
			idProjNam.setDisabled(disabled);
			idProjDes.setValue(projectBean.getProjectdescription());
			idProjDes.setFocus(true);
			idPurNam.setValue(projectBean.getPurposename());
			idPurNam.setDisabled(disabled);
			idPurNamD.setValue(projectBean.getPurposedescription());
		}else {
			logger.error("Error in load Attribute Item Project");
		}
		idProjNam.setFocus(true);
	}
}
