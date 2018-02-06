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

import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_FILL_FIELD;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_INFORMATION;
import static com.cimmyt.utils.Constants.LOCALE_LANGUAGE;
import static com.cimmyt.utils.Constants.PROJECT_SERVICE_BEAN_NAME;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.cimmyt.model.bean.Organism;
import com.cimmyt.model.bean.Program;
import com.cimmyt.model.bean.Purpose;
import com.cimmyt.service.ServiceOrganism;
import com.cimmyt.service.ServiceProject;
import com.cimmyt.utils.Constants;
import com.cimmyt.utils.ConstantsDNA;
import com.cimmyt.utils.PropertyHelper;

public class ControlProgramPurpose extends Window{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static ServiceProject serviceProject = null;
	private static ServiceOrganism serviceOrganism;
	private Window idWindowProgramPurpos;
	private boolean typeObject;
	private Caption idCaptionPP;
	private PropertyHelper pro=null;
	private Textbox idNamePP;
	private Textbox idCode;
	private Textbox idDescription;
	private Combobox idCrop;
	private Program program;
	private Purpose purpose;
	
	static {
		if(serviceProject == null)
        {
			try{
			serviceProject = (ServiceProject)SpringUtil.getBean(PROJECT_SERVICE_BEAN_NAME);
			serviceOrganism = (ServiceOrganism)SpringUtil.getBean(Constants.ORGANISM_SERVICE_BEAN_NAME);
			}catch (Exception e){
				e.printStackTrace();
			}
        }
		
	}

	public void doAfterCompose(){
		pro = (PropertyHelper)getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);
		typeObject = (Boolean)getDesktop().getAttribute(Constants.ATTRIBUTE_TYPE_OBJECT);
		Object obj = (Object)getDesktop().getSession().getAttribute(Constants.ATTRIBUTE_EDIT_OBJECT);
		loadComponents();
		loadComboCrop();
		if (typeObject){
			idCaptionPP.setImage(Constants.URL_IMAGES_PROGRAM);
			idCaptionPP.setLabel(pro.getKey(ConstantsDNA.LBL_PROJECT_WINDOW_TITLE_PROGRAM));
			if (obj != null){
				this.program = (Program)obj;
				loadObjToEdit(this.program.getProgramName(), this.program.getLetterCode(), this.program.getDescription(),this.program.getOrganism().getOrganismid() );
			}
		}else {
			idCaptionPP.setImage(Constants.URL_IMAGES_PURPOSE);
			idCaptionPP.setLabel(pro.getKey(ConstantsDNA.LBL_PROJECT_WINDOW_TITLE_PURPOSE));
			if (obj != null){
				this.purpose = (Purpose)obj;
				loadObjToEdit(this.purpose.getPurposeName(), this.purpose.getLetterCode(), this.purpose.getDescription(),this.purpose.getOrganism().getOrganismid() );
			}
		}
		
	}

	private void loadObjToEdit(String name, String code, String description, int idCrop){
		idNamePP.setText(name.trim());
		idCode.setText(code.trim());
		idDescription.setText(description.trim());
		for (Component co : this.idCrop.getChildren()){
			Comboitem item = (Comboitem)co;
			if (idCrop == ((Organism)item.getValue()).getOrganismid().intValue()){
				this.idCrop.setSelectedItem(item);
				break;
			}
		}
		
	}
	
	private void loadComboCrop (){
		List<Organism> list = serviceOrganism.getOrganisms(new Organism());
		if (list != null && !list.isEmpty()){
			for (Organism org : list){
				Comboitem item = new Comboitem ();
				item.setLabel(org.getOrganismname());
				item.setValue(org);
				idCrop.appendChild(item);
			}
			
		}
	}
	
	private void loadComponents(){
		idWindowProgramPurpos = (Window)getFellow("idWindowProgramPurpos");
		idCaptionPP = (Caption)getFellow("idCaptionPP");
		idNamePP = (Textbox)getFellow("idNamePP");
		idCode = (Textbox)getFellow("idCode");
		idDescription = (Textbox)getFellow("idDescription");
		idCrop = (Combobox)getFellow("idCrop");

	}
	public void close(){
		idWindowProgramPurpos.onClose();
	}

	public void addObject (){
		if (validateText(idNamePP) || validateText(idCode) ||
				validateText(idDescription) || idCrop.getSelectedIndex() == -1 )
			messageBox(pro.getKey(LBL_GENERIC_MESS_FILL_FIELD));
		else {
			if (isValidName()){
					getDesktop().getSession().setAttribute(Constants.ATTRIBUTE_RELOAD_OBJECT, true);
					if (typeObject)
						serviceProject.saveOrUpdateProgram(getProgram());
					else 
						serviceProject.saveOrUpdatePurpose(getPurpose());
					close();
				}else{
					messageBox(pro.getKey(Constants.LBL_GENERIC_WINDOW_ERR_EXIST));
					return;
				}
		}
	}

	private boolean isValidName(){
		if (typeObject){
				if (this.program != null  && this.program.getProgramName().equals(idNamePP.getText().trim()))
					return true;
				else{
					List<Program> list =serviceProject.getListProgram(getProgram(), true);
					if (list != null && list.size() >0)
						return false;
					else 
						return true;
				}
			}else{
				if (this.purpose != null  && this.purpose.getPurposeName().equals(idNamePP.getText().trim()))
					return true;
				else{
					List<Purpose> list =serviceProject.getListPurpose(getPurpose(), true);
					if (list != null && list.size() >0)
						return false;
					else 
						return true;
				}
			}
	}
	private Program getProgram(){
		Program obj = new Program();
		obj.setProgramName(idNamePP.getText().trim());
		obj.setLetterCode(idCode.getText().trim());
		obj.setDescription(idDescription.getText().trim());
		obj.setOrganism((Organism)idCrop.getSelectedItem().getValue());
		obj.setStatus(true);
		if (this.program != null){
			obj.setIdstProgram(program.getIdstProgram());
			obj.setStatus(program.isStatus());
		}
		return obj;
	}

	private Purpose getPurpose(){
		Purpose obj = new Purpose();
		obj.setPurposeName(idNamePP.getText().trim());
		obj.setLetterCode(idCode.getText().trim());
		obj.setDescription(idDescription.getText().trim());
		obj.setOrganism((Organism)idCrop.getSelectedItem().getValue());
		obj.setStatus(true);
		if (this.purpose != null){
			obj.setIdstPurpose(purpose.getIdstPurpose());
			obj.setStatus(purpose.isStatus());
		}
		return obj;
	}
	private void messageBox(String mess){
		Messagebox.show(mess,pro.getKey(LBL_GENERIC_MESS_INFORMATION), 
				Messagebox.OK, Messagebox.INFORMATION);
	}

	private boolean validateText(Textbox text){
		return text.getText().trim().equals("");
	}
}
