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

import static com.cimmyt.utils.Constants.ATTRIBUTE_NAME_USER_BEAN;
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

import java.util.List;

import org.apache.log4j.Logger;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.cimmyt.bean.InvestigatorBean;
import com.cimmyt.bean.ProjectBean;
import com.cimmyt.bean.UserBean;
import com.cimmyt.model.bean.Program;
import com.cimmyt.model.bean.Purpose;
import com.cimmyt.service.ServiceInvestigator;
import com.cimmyt.service.ServiceProject;
import com.cimmyt.utils.Constants;
import com.cimmyt.utils.PropertyHelper;
import com.cimmyt.utils.StrUtils;

@SuppressWarnings("serial")
public class ControlWindowProject extends Window{

	private Intbox idProject;
	private Textbox idProjNam;
	private Textbox idProjDes;
	private Textbox idPurNam;
	private Textbox idPurNamD;
	private Logger logger= Logger.getLogger(ControlWindowProject.class);
	private static ServiceProject serviceProject = null;
	private static ServiceInvestigator serviceInvestigator = null;
	private ProjectBean projectBean;
	private PropertyHelper pro=null;
	private Combobox idCombProgram;
	private Combobox idCombYear;
	private UserBean userBean;
	private Combobox idCombPurpose;
	private Combobox idCombScientist;
	static {
		if(serviceProject == null)
        {
			try{
			serviceProject = (ServiceProject)SpringUtil.getBean(PROJECT_SERVICE_BEAN_NAME);
			}catch (Exception e){
				e.printStackTrace();
			}
        }
		if (serviceInvestigator == null){
			try {
				serviceInvestigator = (ServiceInvestigator)SpringUtil.getBean(Constants.INVESTIGATOR_SERVICE_BEAN_NAME);
			}catch (Exception ex){
				ex.printStackTrace();
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
		
		if (validateText(idProjNam)//||validateText(idProjDes)
				|| validateText(idPurNam)) {// || validateText(idPurNamD)){
			messageBox(pro.getKey(LBL_GENERIC_MESS_FILL_FIELD));
		}else{
			ProjectBean bean = new ProjectBean ();
			bean.setProjectid(idProject.getValue());
			bean.setProjectname(idProjNam.getValue().trim());
			bean.setProjectdescription(idProjDes.getValue() != null  && !idProjDes.getValue().trim().equals("") ? 
					idProjDes.getValue().trim() : "");
			bean.setPurposename(idPurNam.getValue().trim());
			bean.setPurposedescription(idPurNamD.getValue() != null && !idPurNamD.getValue().trim().equals("")
					? idPurNamD.getValue().trim() : "");
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
		idCombProgram = (Combobox)getFellow("idCombProgram");
		idCombYear = (Combobox)getFellow("idCombYear");
		idCombPurpose = (Combobox)getFellow("idCombPurpose");
		idCombScientist = (Combobox)getFellow("idCombScientist");
	}
	/**
	 * Load Context of Window 
	 */
	public void loadContextAttribute(){
		pro = (PropertyHelper)getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);
		if (getDesktop().getSession().getAttribute(ATTRIBUTE_NAME_USER_BEAN) != null){
			userBean = (UserBean)getDesktop().getSession().getAttribute(ATTRIBUTE_NAME_USER_BEAN);
		}
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
			idCombProgram.setDisabled(disabled);
			idCombScientist.setDisabled(disabled);
		}else {
			logger.error("Error in load Attribute Item Project");
		}
		idProjNam.setFocus(true);
		fillComboProgram();
		fillComboYear();
		fillComboPurpose();
		fillComboScientist();
	}
	
	private void fillComboProgram(){
		Program program = new Program();
		program.setOrganism(userBean.getOrganism());
		program.setStatus(true);
		List<Program> list = serviceProject.getListProgram(program, false);
		if (list != null && !list.isEmpty()){
			for (Program prog : list){
				Comboitem item = new Comboitem();
				item.setLabel(prog.getProgramName());
				item.setDescription(prog.getDescription());
				item.setValue(prog.getLetterCode());
				idCombProgram.appendChild(item);
			}
		}
	}

	private void fillComboPurpose(){
		Purpose purpose = new Purpose();
		purpose.setOrganism(userBean.getOrganism());
		purpose.setStatus(true);
		List<Purpose> list = serviceProject.getListPurpose(purpose, false);
		if (list != null && !list.isEmpty()){
			for (Purpose pur : list){
				Comboitem item = new Comboitem(pur.getPurposeName());
				item.setDescription(pur.getDescription());
				item.setValue(pur.getLetterCode());
				idCombPurpose.appendChild(item);
			}
			
		}
	}
	private void fillComboYear(){
		int year = StrUtils.getYear();
		for (int index = 10; index>= 0; index --){
			Comboitem item = new Comboitem();
			String strYear ="";
			if ((year+index) < 10)
				strYear = "0"+Integer.toString(year+index);
			else
			strYear =Integer.toString(year+index);
			item.setLabel(strYear);
			item.setValue(strYear);
			idCombYear.appendChild(item);
		}
		
	}

	private void fillComboScientist(){
		InvestigatorBean bean = new InvestigatorBean();
		List<InvestigatorBean> list = serviceInvestigator.getInvestigator(bean);
		if (list != null && !list.isEmpty()){
			for (InvestigatorBean invBean : list){
				Comboitem item = new Comboitem(invBean.getInvest_name());
				item.setValue(invBean.getInvest_abbreviation());
				idCombScientist.appendChild(item);
			}
		}
	}

	public void onSelctComboScientist(){
		if (idCombScientist.getSelectedIndex() != -1){
			String strValue = (String) idCombScientist.getSelectedItem().getValue();
			if (idPurNam.getText().length() == 4 || idPurNam.getText().length() == 3){
				idPurNam.setText(idPurNam.getText().replaceAll(idPurNam.getText().substring(2, idPurNam.getText().trim().length()), strValue));
			}else{
				idPurNam.setText(idPurNam.getText()+strValue);
			}
		}
	}
	
	public void onSelectComboYear (){
		if (idCombYear.getSelectedIndex() != -1){
			String strValue = (String)idCombYear.getSelectedItem().getValue();
			if (idProjNam.getText().length() == 4 || idProjNam.getText().length() == 3){
				idProjNam.setText(idProjNam.getText().replaceAll(idProjNam.getText().substring(2, idProjNam.getText().trim().length()), strValue));
			}else{
				idProjNam.setText(idProjNam.getText()+strValue);
			}
		}
	}
	public void onSelectComboProgram(){
		if (idCombProgram.getSelectedIndex() != -1){
			idCombYear.setDisabled(false);
			String strValue =(String)idCombProgram.getSelectedItem().getValue();
			if (idProjNam.getText().length() == 4 || idProjNam.getText().length() == 3){
				idProjNam.setText(idProjNam.getText().replaceAll(idProjNam.getText().substring(0, 2), strValue));
			}else{
				idProjNam.setText(strValue);
			}
		}
	}

	public void onSelectComboPurpose(){
		if (idCombPurpose.getSelectedIndex() != -1){
			idCombScientist.setDisabled(false);
			String strValue =(String)idCombPurpose.getSelectedItem().getValue();
			if (idPurNam.getText().length() == 4 || idPurNam.getText().length() == 3){
				idPurNam.setText(idPurNam.getText().replaceAll(idPurNam.getText().substring(0, 2), strValue));
			}else{
				idPurNam.setText(strValue);
			}
		}
	}
}
