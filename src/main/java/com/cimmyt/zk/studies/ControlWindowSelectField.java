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
package com.cimmyt.zk.studies;

import static com.cimmyt.utils.Constants.ATTRIBUTE_LIST_FIELD_REPORT;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Window;

import com.cimmyt.model.bean.LabStudy;
import com.cimmyt.model.bean.StudyTemplateParams;
import com.cimmyt.utils.Constants;

@SuppressWarnings("serial")
public class ControlWindowSelectField extends Window{

	private List<Object> listFieldsObjets = new ArrayList<Object>();
	private Listbox idListBoxTemplate;
	private LabStudy beanStudy;
	private List<StudyTemplateParams> listStudyTemParams = new ArrayList<StudyTemplateParams>();

	public void oncreation(){
		if (getDesktop().getAttribute(Constants.ATTRIBUTE_FIELD_TEMPLATE) != null)
			beanStudy = (LabStudy)getDesktop().getAttribute(Constants.ATTRIBUTE_FIELD_TEMPLATE);
		loadContext();
		fillListBox();
	}

	private void fillListBox(){
		if (beanStudy != null && beanStudy.getStudytemplateid().getImsStudyTemplateParamsCollection() != null
				&& !beanStudy.getStudytemplateid().getImsStudyTemplateParamsCollection().isEmpty()){
			for (StudyTemplateParams temp : beanStudy.getStudytemplateid().getImsStudyTemplateParamsCollection()){
				Listitem  item = new Listitem();
				item.setLabel(temp.getFactorname());
				item.setValue(temp);
				idListBoxTemplate.appendChild(item);
			}
		}
	}
	private void loadContext(){
		idListBoxTemplate = (Listbox)getFellow("idListBoxTemplate");
	}

	public enum Fields {
		SAMPLEID, GID, ACC, PLANT_NUMBER, ENTRY_NUMBER
	} 

	public void loadListField(Checkbox check){
		int valueInt = Integer.parseInt(check.getValue().toString());
		switch (valueInt){
			case (0):
				loadList(Fields.SAMPLEID, check.isChecked());
				break;
			case (1):
				loadList(Fields.GID, check.isChecked());
				break;
			case (2):
				loadList(Fields.ACC, check.isChecked());
				break;
			case (3):
				loadList(Fields.PLANT_NUMBER, check.isChecked());
				break;
			case (4):
				loadList(Fields.ENTRY_NUMBER, check.isChecked());
			break;
		}
	}

	private void loadList(Object objEnum, boolean isChecked ){
		if (isChecked)
			listFieldsObjets.add(objEnum);
		else
			listFieldsObjets.remove(objEnum);
	}

	public void closeWindow(){
		getDesktop().removeAttribute(ATTRIBUTE_LIST_FIELD_REPORT);
		getDesktop().removeAttribute(Constants.ATTRIBUTE_LIST_FIELD_REPORT_TEMPLATE);
		Window idWindow = (Window)getFellow("idWindowSelectField");
		idWindow.onClose();
	}

	public void addFields(){
		getDesktop().setAttribute(ATTRIBUTE_LIST_FIELD_REPORT, listFieldsObjets);
		readTempParams();
		getDesktop().setAttribute(Constants.ATTRIBUTE_LIST_FIELD_REPORT_TEMPLATE, listStudyTemParams);
		Window idWindow = (Window)getFellow("idWindowSelectField");
		idWindow.onClose();
		
	}

	private void readTempParams(){
		Set<Listitem> list = idListBoxTemplate.getSelectedItems();
		if (list != null && !list.isEmpty()){
			for (Listitem item : list){
				listStudyTemParams.add((StudyTemplateParams)item.getValue());
			}
		}
	}
}
