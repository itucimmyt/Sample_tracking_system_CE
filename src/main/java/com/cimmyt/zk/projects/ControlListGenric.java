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

import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_INFORMATION;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_SELECT_RECORD;
import static com.cimmyt.utils.Constants.LOCALE_LANGUAGE;
import static com.cimmyt.utils.Constants.PROJECT_SERVICE_BEAN_NAME;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.A;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.cimmyt.model.bean.Program;
import com.cimmyt.model.bean.Purpose;
import com.cimmyt.service.ServiceProject;
import com.cimmyt.utils.Constants;
import com.cimmyt.utils.ConstantsDNA;
import com.cimmyt.utils.PropertyHelper;

@SuppressWarnings("serial")
public class ControlListGenric extends Window{

	private boolean typeObject;
	private Caption idCaptionList;
	private PropertyHelper pro=null;
	private static ServiceProject serviceProject = null;
	private Listbox idLisBGeneric;
	private List<Object> listObj = new ArrayList<Object>();
	private Window idWindowListGeneric;

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

	public void doAfterCompose(){
		pro = (PropertyHelper)getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);
		typeObject = (Boolean)getDesktop().getAttribute(Constants.ATTRIBUTE_TYPE_OBJECT);
		loadComponents();
		if (typeObject){
			idCaptionList.setImage(Constants.URL_IMAGES_PROGRAM);
			idCaptionList.setLabel(pro.getKey(ConstantsDNA.LBL_PROJECT_WINDOW_TITLE_PROGRAM));
			
		}else {
			idCaptionList.setImage(Constants.URL_IMAGES_PURPOSE);
			idCaptionList.setLabel(pro.getKey(ConstantsDNA.LBL_PROJECT_WINDOW_TITLE_PURPOSE));
		}
		loadData();
	}

	private void loadData(){
		listObj = new ArrayList<Object>();
		if (typeObject){
		List<Program> _listObj = serviceProject.getListProgram(new Program(), true);
		this.listObj.addAll(_listObj);
		}else 
		{
			List<Purpose> _listObj = serviceProject.getListPurpose(new Purpose(), true);
			this.listObj.addAll(_listObj);
		}
		if (listObj != null && !listObj.isEmpty()){
			fillList();
		}
	}

	private void fillList(){
		if (idLisBGeneric.getItems()!= null && !idLisBGeneric.getItems().isEmpty()){
			while (!idLisBGeneric.getItems().isEmpty())
				idLisBGeneric.getItems().remove(0);		
		}
		if (typeObject)
		for (Object obj : listObj){
			Program program = (Program)obj;
			idLisBGeneric.appendChild(getItem(program.getProgramName(), program.getLetterCode(),program.getDescription() , program.getOrganism().getOrganismname() ,program.isStatus(), obj));
		}
		else{
			for (Object obj : listObj){
				Purpose program = (Purpose)obj;
				idLisBGeneric.appendChild(getItem(program.getPurposeName(), program.getLetterCode(),program.getDescription() , program.getOrganism().getOrganismname(), program.isStatus(), obj));
			}
		}
	}

	private Listitem getItem(String name, String shortName, String description, String crop, boolean status, Object obj){
		Listitem item = new Listitem();
		Listcell cell1 = new Listcell(name);
		item.appendChild(cell1);
		Listcell cell2 = new Listcell(shortName);
		item.appendChild(cell2);
		Listcell cell3 = new Listcell(description);
		item.appendChild(cell3);
		Listcell cell5 = new Listcell(crop);
		item.appendChild(cell5);
		String active = status ? pro.getKey(ConstantsDNA.LBL_PROJECT_PROGRAMPURPOSE_DISABLE) : 
			pro.getKey(ConstantsDNA.LBL_PROJECT_PROGRAMPURPOSE_AVAILABLE);
		A link = new A();
		link.setLabel(active);
		link.setStyle("color : blue; cursor:pointer;text-decoration: underline;");
		link.addEventListener(Events.ON_CLICK, new DisableObject(obj));
		Listcell cell4 = new Listcell ();
		cell4.appendChild(link);
		item.appendChild(cell4);
		item.setValue(obj);
		return item;
	}
	private void loadComponents(){
		idCaptionList = (Caption)getFellow("idCaptionList");
		idLisBGeneric = (Listbox)getFellow ("idLisBGeneric");
		idWindowListGeneric = (Window)getFellow("idWindowListGeneric");
	}
	public void add(){
		getDesktop().setAttribute(Constants.ATTRIBUTE_TYPE_OBJECT, typeObject);
		final Window win = (Window) Executions.createComponents(
    			"/projects/window_program_purpose.zul", null, null);
    			win.doModal();
    	getDesktop().removeAttribute(Constants.ATTRIBUTE_TYPE_OBJECT);
    	if (getDesktop().getSession().getAttribute(Constants.ATTRIBUTE_RELOAD_OBJECT) != null){
    		loadData();
    		getDesktop().getSession().removeAttribute(Constants.ATTRIBUTE_RELOAD_OBJECT);
    	}
	}

	public void edit(){
		if (idLisBGeneric.getSelectedIndex() != -1){
			getDesktop().getSession().setAttribute(Constants.ATTRIBUTE_EDIT_OBJECT, idLisBGeneric.getSelectedItem().getValue());
			getDesktop().setAttribute(Constants.ATTRIBUTE_TYPE_OBJECT, typeObject);
			final Window win = (Window) Executions.createComponents(
	    			"/projects/window_program_purpose.zul", null, null);
	    			win.doModal();
	    	getDesktop().removeAttribute(Constants.ATTRIBUTE_TYPE_OBJECT);
	    	getDesktop().getSession().removeAttribute(Constants.ATTRIBUTE_EDIT_OBJECT);
	    	if (getDesktop().getSession().getAttribute(Constants.ATTRIBUTE_RELOAD_OBJECT) != null){
	    		loadData();
	    		getDesktop().getSession().removeAttribute(Constants.ATTRIBUTE_RELOAD_OBJECT);
	    	}
		}else{
			messageBox(pro.getKey(LBL_GENERIC_MESS_SELECT_RECORD));	
		}
	}

	private void messageBox(String mess){
		Messagebox.show(mess,pro.getKey(LBL_GENERIC_MESS_INFORMATION), 
				Messagebox.OK, Messagebox.INFORMATION);
	}

	public void close(){
		idWindowListGeneric.onClose();
	}

	class DisableObject implements EventListener<Event>{

		private Object obj;
		public DisableObject(Object _obj){
			this.obj = _obj;
		}
		@Override
		public void onEvent(Event component) throws Exception {
			if (Messagebox.show(pro.getKey(ConstantsDNA.LBL_PROJECT_PROGRAMPURPOSE_MSN_CHANGE_STATUS), 
					typeObject ? pro.getKey(ConstantsDNA.LBL_PROJECT_WINDOW_TITLE_PROGRAM):
						pro.getKey(ConstantsDNA.LBL_PROJECT_WINDOW_TITLE_PURPOSE), 
					Messagebox.YES | Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
				A a = (A)component.getTarget();
				if (typeObject){
					Program program = (Program)obj;
					program.setStatus(!program.isStatus());
					String active = program.isStatus() ? pro.getKey(ConstantsDNA.LBL_PROJECT_PROGRAMPURPOSE_DISABLE) : 
						pro.getKey(ConstantsDNA.LBL_PROJECT_PROGRAMPURPOSE_AVAILABLE);
					a.setLabel(active);
					serviceProject.saveOrUpdateProgram(program);
				}else {
					Purpose purpose = (Purpose)obj;
					purpose.setStatus(!purpose.isStatus());
					String active = purpose.isStatus() ? pro.getKey(ConstantsDNA.LBL_PROJECT_PROGRAMPURPOSE_DISABLE) : 
						pro.getKey(ConstantsDNA.LBL_PROJECT_PROGRAMPURPOSE_AVAILABLE);
					a.setLabel(active);
					serviceProject.saveOrUpdatePurpose(purpose);
				}
			}
			
		}
		
	}
}
