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
import static com.cimmyt.utils.Constants.ATTRIBUTE_PROJECT_ENABLED;
import static com.cimmyt.utils.Constants.ATTRIBUTE_STUDY_TEMPLATE_ITEM;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_CARCATER;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_DATE;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_ERROR;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_ER_DIF_REG;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_FIELD_REQUIRED;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_INFORMATION;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_NUMERIC;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_SELECT_RECORD;
import static com.cimmyt.utils.Constants.LBL_STUDY_TEMPLATE_FIELDS_NOT_EQUALS;
import static com.cimmyt.utils.Constants.LBL_STUDY_TEMPLATE_FILL_FIELDS;
import static com.cimmyt.utils.Constants.LBL_STUDY_TEMPLATE_NEED_FIELD;
import static com.cimmyt.utils.Constants.LBO_INVESTIGATOR_NAME;
import static com.cimmyt.utils.Constants.LOCALE_LANGUAGE;
import static com.cimmyt.utils.Constants.SHOW_ROWS_LIST;
import static com.cimmyt.utils.Constants.STUDY_TEMPLATE_SERVICE_BEAN_NAME;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.cimmyt.bean.UserBean;
import com.cimmyt.constants.FieldType;
import com.cimmyt.model.bean.StudyTemplate;
import com.cimmyt.model.bean.StudyTemplateParams;
import com.cimmyt.model.dao.MyQLProcedureDAO;
import com.cimmyt.service.SeriviceStudyTemplate;
import com.cimmyt.utils.Constants;
import com.cimmyt.utils.PropertyHelper;
import com.cimmyt.utils.StrUtils;
import com.cimmyt.zk.investigator.ControlWindowInvestigator;

@SuppressWarnings("serial")
public class ControlWindowStudyTemplate extends Window{

	private Intbox id;
	private Textbox idName;
	private Textbox idDes;
	private Label idL1;
	private Label idL2;
	private Image idFieldDelete;
	private Image idFieldAdd;
	private Image idFishAdd;
	private PropertyHelper pro=null;
	private List<String> nameList = new ArrayList<String>();
	private static SeriviceStudyTemplate seriviceStudyTemplate = null;
	private Set<StudyTemplateParams> studyTemplateParamsDelete = new HashSet<StudyTemplateParams> ();
	private Set<StudyTemplateParams> studyTemplateParamsOriginal = new HashSet<StudyTemplateParams>();
	private boolean disabled = false;
	private Listbox idLisB;
	private UserBean userBean;
	private static MyQLProcedureDAO mySqlProcedure;
	static {
		if(seriviceStudyTemplate == null)
        {
			try{
				seriviceStudyTemplate = (SeriviceStudyTemplate)SpringUtil.getBean(STUDY_TEMPLATE_SERVICE_BEAN_NAME);
				}catch (Exception e){
				e.printStackTrace();
			}
        }
		if (mySqlProcedure == null )
			try{
				mySqlProcedure = (MyQLProcedureDAO)SpringUtil.getBean(Constants.MYSQLPROCEDURE_SERVICE_BEAN_NAME);
			}catch (Exception e){
				e.printStackTrace();
			}
		
	}
	Logger logger= Logger.getLogger(ControlWindowInvestigator.class);
	/** 
	 * Close Window
	 */
	public void closeWindow(){
		getDesktop().setAttribute(ATTRIBUTE_STUDY_TEMPLATE_ITEM, null);
		getDesktop().getSession().removeAttribute(Constants.ATTRIBUTE_STUDY_TEMPLATE_ITEM_DELETE);
		Window idWindow = (Window)getFellow("idWindow");
		idWindow.onClose();
	}
	/**
	 * Load Window Components
	 */
	public void loadContextAttribute(){
		pro = (PropertyHelper)getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);
		StudyTemplate bean = (StudyTemplate)getDesktop().getAttribute(ATTRIBUTE_STUDY_TEMPLATE_ITEM);
		
		if (getDesktop().getAttribute(ATTRIBUTE_PROJECT_ENABLED) != null){
			disabled = (Boolean)getDesktop().getAttribute(ATTRIBUTE_PROJECT_ENABLED);
		}
		if (getDesktop().getSession().getAttribute(ATTRIBUTE_NAME_USER_BEAN) != null){
			userBean = (UserBean)getDesktop().getSession().getAttribute(ATTRIBUTE_NAME_USER_BEAN);
		}
		loadComponents();
		if(bean != null){
			 id.setValue(bean.getStudytemplateid());
			 idName.setValue(bean.getTemplatename());
			 idName.setDisabled(disabled);
			 idDes.setValue(bean.getComments());
			 idDes.setFocus(true);
			 if (!StrUtils.isRoleAdminOrDataManager(userBean.getRole().getIdstRol())){
			 idFieldDelete.setVisible(false);
			 idFieldAdd.setVisible(false);
			 idFishAdd.setVisible(false);
			 }
			 studyTemplateParamsOriginal = bean.getImsStudyTemplateParamsCollection(); 
			 loadListContext(studyTemplateParamsOriginal,disabled);
		}
		idName.setFocus(true);
	}
	
	private void loadListContext(Set<StudyTemplateParams> imsStudyTemplateParamsCollection, boolean disable){
		
		int index = 0;
		while (!idLisB.getItems().isEmpty()){
			idLisB.getItems().remove(0);
		}
		
		for(StudyTemplateParams set : imsStudyTemplateParamsCollection){
			Listitem item = new Listitem();
			Listcell cell = new Listcell();
			Textbox textName = getTextBox( 20, 20,set.getFactorname());
			cell.appendChild(textName);
			Listcell cell1 = new Listcell();
			cell1.appendChild(getTextBox( 50, 50,set.getDescription()));
			Listcell cell2 = new Listcell();
			cell2.appendChild(getCombobox(set.getDatatype(), disable));
			item.appendChild(cell);
			item.appendChild(cell1);
			item.appendChild(cell2);
			item.setValue(set);
			//item.setValue(set);
			idLisB.appendChild(item);
			if (index > SHOW_ROWS_LIST){
				idLisB.setMold("paging");
				idLisB.setPageSize(SHOW_ROWS_LIST);
			}
			index++;
		}
	}
	/**
	 * Add new Season
	 */
	public void add(){
		showLabelReq(false);
		boolean showMessage = false;
		if (validateText(idName)){
			showMessage = true;
			idL1.setVisible(true);
		}
		if (validateText(idDes)){
			showMessage = true;
			idL2.setVisible(true);
		}
		if (showMessage){
			messageBox(pro.getKey(LBL_GENERIC_MESS_FIELD_REQUIRED));	
		}else if(idLisB.getItems() != null 
				&& idLisB.getItems().isEmpty()){
			messageBox(pro.getKey(LBL_STUDY_TEMPLATE_NEED_FIELD));
			
		}else {
			
			
			int index =searchRow(idLisB.getItems());
			if (index > 0){
				messageBox(pro.getKey(LBL_STUDY_TEMPLATE_FILL_FIELDS)+ " "+index);
			}else {
				StudyTemplate bean =  new StudyTemplate();
				bean.setStudytemplateid(id.getValue());
				bean.setTemplatename(idName.getValue());
				bean.setComments(idDes.getValue());
				SortedSet<StudyTemplateParams>  set = getSetStudyTemplate(bean);
				if(validateName()){
					messageBox(pro.getKey(LBL_STUDY_TEMPLATE_FIELDS_NOT_EQUALS));
					
				}else {
					bean.setImsStudyTemplateParamsCollection(set);
					StudyTemplate beanAttribute = (StudyTemplate)getDesktop().getAttribute(ATTRIBUTE_STUDY_TEMPLATE_ITEM);
					if(beanAttribute == null){
						StudyTemplate sBean = seriviceStudyTemplate.getStudyTemplateByNameregistred(bean.getTemplatename());
						if(sBean == null){
							getDesktop().setAttribute(ATTRIBUTE_STUDY_TEMPLATE_ITEM, bean);
							Window idWindow = (Window)getFellow("idWindow");
							idWindow.onClose();	
						}else {
							Messagebox.show(pro.getKey(LBL_GENERIC_MESS_ER_DIF_REG,new String []{pro.getKey(LBO_INVESTIGATOR_NAME)}), 
									pro.getKey(LBL_GENERIC_MESS_ERROR), 
									Messagebox.OK, Messagebox.ERROR);StudyTemplateParams item = (StudyTemplateParams)idLisB.getSelectedItem().getValue();
									studyTemplateParamsDelete.add(item);
									
						}
					}else {
						bean.setStudytemplateid(bean.getStudytemplateid());
						getDesktop().getSession().setAttribute(Constants.ATTRIBUTE_STUDY_TEMPLATE_ITEM_DELETE, studyTemplateParamsDelete);
						getDesktop().setAttribute(ATTRIBUTE_STUDY_TEMPLATE_ITEM, bean);
						Window idWindow = (Window)getFellow("idWindow");
						idWindow.onClose();	
					}
					
				}
			}
			
		}
	}

	private boolean validateName(){
		final Set<String> setToReturn = new HashSet<String>();
		final Set<String> set1 = new HashSet<String>();
		for (String str : nameList){
			if (!set1.add(str.trim())){
				setToReturn.add(str.trim());
			}
		}
		if (setToReturn.size() > 0){
			return true;
		}
		return false;
	}
	private int searchRow(List<Listitem> listItems){
		int i = 1;
		 for (Listitem item : listItems){
				Combobox combo = (Combobox)item.getChildren().get(2).getChildren().get(0);
				 boolean itsOkCombo = combo.getSelectedIndex() != -1 ? true :false;
				 boolean itsOkName = !((Textbox)item.getChildren().get(0).getChildren().get(0)).getText().trim().equals("") ?true :false;
				 boolean itsOkDes = !((Textbox)item.getChildren().get(1).getChildren().get(0)).getText().trim().equals("") ?true :false;
				if (!itsOkCombo  || !itsOkName || !itsOkDes){
					return ++i;
				}
		}
		return 0;
	}

	private SortedSet<StudyTemplateParams> getSetStudyTemplate(StudyTemplate bean){
		int index = idLisB.getItems().size();
		SortedSet<StudyTemplateParams> set = new TreeSet<StudyTemplateParams>();
		nameList = new ArrayList<String>();
		for (int i=0; i<index; i++){
			Listitem item = idLisB.getItems().get(i);
			
			StudyTemplateParams params = new StudyTemplateParams();
			Combobox combo = (Combobox)item.getChildren().get(2).getChildren().get(0);
			params.setDatatype(((FieldType)combo.getSelectedItem().getValue()).getId());
			
			Textbox textDes = (Textbox)item.getChildren().get(1).getChildren().get(0);
			params.setDescription(textDes.getText().trim());
			Textbox textName = (Textbox)item.getChildren().get(0).getChildren().get(0);
			params.setFactorname(textName.getText().trim());
			
			//StudyTemplateParams paramsV = (StudyTemplateParams)textName.getAttribute(ATTRIBUTE_ITEM_STUDYTEMPLATEPARAM);
			//Listitem item = (Listitem)getFellow("idItemParam"+i);
			if(item != null && item.getValue()!= null)
			params.setTemplateparamid(((StudyTemplateParams)item.getValue()).getTemplateparamid());
			params.setStudytemplateid(bean);
			nameList.add(params.getFactorname());
			set.add(params);
		}
		
		
		return set;
	}
	private void loadComponents(){
		id = (Intbox)getFellow("id");
		idName = (Textbox)getFellow("idName");
		idDes = (Textbox)getFellow("idDes");
		idL1 = (Label)getFellow("idL1");
		idL2 = (Label)getFellow("idL2");
		idFieldDelete = (Image)getFellow("idFieldDelete");
		idFieldAdd = (Image)getFellow("idFieldAdd");
		idLisB = (Listbox)getFellow("idLisB");
		idFishAdd = (Image)getFellow("idAdd");
	}

	private void showLabelReq(boolean visible){
		idL1.setVisible(visible);
		idL2.setVisible(visible);
	}
	private void messageBox(String mess){
		Messagebox.show(mess,pro.getKey(LBL_GENERIC_MESS_INFORMATION), 
				Messagebox.OK, Messagebox.INFORMATION);
	}

	private boolean validateText(Textbox text){
		return text.getText().trim().equals("");
	}
	/**
	 * Add fields
	 */
	public void addField(){
		int index = 0;
		if (idLisB.getItems() != null && !idLisB.getItems().isEmpty()){
			index = idLisB.getItems().size();
		}
		Listitem item = new Listitem();
		Listcell cell = new Listcell();
		cell.appendChild(getTextBox( 20, 20,""));
		Listcell cell1 = new Listcell();
		cell1.appendChild(getTextBox( 50, 50,""));
		Listcell cell2 = new Listcell();
		cell2.appendChild(getCombobox("",false));
		item.appendChild(cell);
		item.appendChild(cell1);
		item.appendChild(cell2);
		item.setId("idItemParam"+index);
		idLisB.appendChild(item);
		if (index > SHOW_ROWS_LIST){
			idLisB.setMold("paging");
			idLisB.setPageSize(SHOW_ROWS_LIST);
		}
	}
	/**
	 * Delete Row 
	 */
	public void deleteField(){
		if (idLisB.getSelectedIndex() !=-1){
				if (idLisB.getSelectedItem().getValue() != null ){
						StudyTemplateParams item = (StudyTemplateParams)idLisB.getSelectedItem().getValue();
						if (item.getTemplateparamid() != null  && item.getTemplateparamid().intValue() > 0){
						int total = mySqlProcedure.getExecuteCountParamData(item.getTemplateparamid());
						if (total > 0){
								if (Messagebox.show(pro.getKey(Constants.LBL_STUDY_TEMPLATE_FIELDS_HAS_INFORMATION), 
										pro.getKey(Constants.LBL_STUDY_TEMPLATE_WIN_EDIT), 
										Messagebox.YES | Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
									studyTemplateParamsDelete.add(item);
									studyTemplateParamsOriginal.remove(item);
									idLisB.getItems().remove(idLisB.getSelectedItem());
									return;
								}
								else
									return;
						}else{
								studyTemplateParamsDelete.add(item);
								studyTemplateParamsOriginal.remove(item);
								idLisB.getItems().remove(idLisB.getSelectedItem());
							}
								
					}else{
						studyTemplateParamsOriginal.remove(item);
						idLisB.getItems().remove(idLisB.getSelectedItem());
						return;
					}
					
				}else {
					idLisB.getItems().remove(idLisB.getSelectedItem());
			}
		}
			else 
			messageBox(pro.getKey(LBL_GENERIC_MESS_SELECT_RECORD));
	}
	
	private Textbox getTextBox( int cols, int maxlength, String str){
		Textbox text = new Textbox();
		//text.setId(id);
		text.setCols(cols);
		text.setSclass("textIpnput");
		text.setMaxlength(maxlength);
		if (!str.trim().equals("")){
			text.setText(str);
		}
		return text;
	}
	private Combobox getCombobox( String str, boolean disabled){
		Combobox combo = new Combobox();
		combo.setCols(15);
		combo.setReadonly(true);
		combo.setMaxlength(15);
		Comboitem itemNumeric = new Comboitem();
		itemNumeric.setLabel(pro.getKey(LBL_GENERIC_MESS_NUMERIC));
		itemNumeric.setValue(FieldType.NUMERIC);
		combo.appendChild(itemNumeric);
		
		Comboitem itemChart = new Comboitem();
		itemChart.setLabel(pro.getKey(LBL_GENERIC_MESS_CARCATER));
		itemChart.setValue(FieldType.CHARACTER);
		combo.appendChild(itemChart);
		
		Comboitem itemDate = new Comboitem();
		itemDate.setLabel(pro.getKey(LBL_GENERIC_MESS_DATE));
		itemDate.setValue(FieldType.DATE);
		combo.appendChild(itemDate);
		if (!str.trim().equals("")){
			for (Comboitem item :combo.getItems()){
				if(((FieldType)item.getValue()).getId().equals(str)){
					combo.setSelectedItem(item);
					break;
				}
			}
		}
		combo.setDisabled(disabled);
		return combo;
	}
}
