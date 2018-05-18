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

import static com.cimmyt.utils.Constants.ATTRIBUTE_GENERIC_NAME;
import static com.cimmyt.utils.Constants.ATTRIBUTE_LABSTUDY_ITEM;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_ERROR;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_INFORMATION;
import static com.cimmyt.utils.Constants.LBL_GENERIC_WINDOW_ERR_SELECT_FIELD;
import static com.cimmyt.utils.Constants.LOCALE_LANGUAGE;
import static com.cimmyt.utils.Constants.RESULT_PREFERENCE_SERVICE;
import static com.cimmyt.utils.Constants.SAMPLE_DETAIL_SERVICE_BEAN_NAME;
import static com.cimmyt.utils.ConstantsDNA.ATTRIBUTE_RESULT_DATA_BEAN_REPORT;
import static com.cimmyt.utils.ConstantsDNA.ATTRIBUTE_RESULT_DATA_INDEX;
import static com.cimmyt.utils.ConstantsDNA.ATTRIBUTE_RESULT_DATA_ITEM;
import static com.cimmyt.utils.ConstantsDNA.LBL_MENU_RESULTS_ALL;
import static com.cimmyt.utils.ConstantsDNA.LBL_RESULTS_DATA_SAVE;
import static com.cimmyt.utils.ConstantsDNA.LBL_RESULTS_SELECT_PLATE;
import static com.cimmyt.utils.ConstantsDNA.RESULT_FIELD_CONSTANT_CHARACTER;
import static com.cimmyt.utils.ConstantsDNA.RESULT_FIELD_CONSTANT_NUMERIC;
import static com.cimmyt.utils.ConstantsDNA.RESULT_FIELD_CONSTANT_STRING;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.cimmyt.bean.ResultPreferenceCompleteBean;
import com.cimmyt.constants.ResultsCamposFijosUnEstudio;
import com.cimmyt.model.bean.LabStudy;
import com.cimmyt.model.bean.ResultsPreferences;
import com.cimmyt.model.bean.ResultsPreferencesDetail;
import com.cimmyt.model.bean.StudyTemplateParams;
import com.cimmyt.service.ServiceResultPreference;
import com.cimmyt.service.ServiceSampleDetail;
import com.cimmyt.utils.PropertyHelper;

@SuppressWarnings("serial")
public class ControlPlateInformation extends Window{

	private Window idWindowR;
	private LabStudy beanStudy;
	private Combobox idCPlate;
	private Combobox idCConfSaved;
	private Grid idGridResults;

	private static ServiceSampleDetail serviceSampleDetail = null;
	private static ServiceResultPreference serviceResultPreference;
	private PropertyHelper pro=null;
    private Logger logger= Logger.getLogger(ControlPlateInformation.class);
    private String PREFIX_CHECKBOX = "idCBITem";
    private String PREFIX_TEXTBOX = "idTextBox";
    private int indexComponentCheck = 0;
    private Map<String, Component> mapComboBox ;
    private Map<String, Component> mapComboParamId ;
    private Map<String, ResultsPreferencesDetail> mapResultDataSelect ;
    private int indexOrder = 1;
    private ResultsPreferences respreferences= new ResultsPreferences();

	static {
		if (serviceSampleDetail == null){
			try{
				serviceSampleDetail = (ServiceSampleDetail)SpringUtil.getBean(SAMPLE_DETAIL_SERVICE_BEAN_NAME);
			}catch (Exception eC){
				eC.printStackTrace();
			}
		}
		if (serviceResultPreference == null){
			try{
				serviceResultPreference = (ServiceResultPreference)SpringUtil.getBean(RESULT_PREFERENCE_SERVICE);
			}catch (Exception exResults){
				exResults.printStackTrace();
			}
		}
	}
	public void loadContext (){
		pro = (PropertyHelper)getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);
		beanStudy = (LabStudy)getDesktop().getAttribute(ATTRIBUTE_LABSTUDY_ITEM );
		indexComponentCheck = 0;
		mapComboBox = new HashMap <String, Component>();
		mapComboParamId = new HashMap <String, Component>();
		mapResultDataSelect = new HashMap <String, ResultsPreferencesDetail>();
		indexOrder = 0;
		loadComponent();
		loadComboboxPlate();
		loadComboboxPreference();
		loadGridFieldsResults();
	}
	/**Method that load all results and preference in the form to will be in the form*/
	private void loadGridFieldsResults(){
		Rows rows = new  Rows ();
		Row row = new Row();
		int numberCell = 0;
		for (int index = 0; index < 19; index++){
			if (numberCell < 3){
				loadField(ResultsCamposFijosUnEstudio.lookupById(index),
						index,new ResultsPreferencesDetail(), row);
				numberCell++;
			}else {
				rows.appendChild(row);
				numberCell = 0;
				row = new Row();
				loadField(ResultsCamposFijosUnEstudio.lookupById(index),
						index,new ResultsPreferencesDetail(), row);
				numberCell++;
			}
		}
		rows.appendChild(row);
		numberCell = 0;
		if (beanStudy.getStudytemplateid() != null  && beanStudy.getStudytemplateid().getImsStudyTemplateParamsCollection()!= null){
			numberCell = 0;
			row = new Row();
			for(StudyTemplateParams param:beanStudy.getStudytemplateid()
					.getImsStudyTemplateParamsCollection()){
				if (numberCell < 3){
					loadField(param, param.getTemplateparamid(),new ResultsPreferencesDetail(), row);
					numberCell++;
				}else {
					rows.appendChild(row);
					numberCell = 0;
					row = new Row();
					loadField(param, param.getTemplateparamid(),new ResultsPreferencesDetail(), row);
					numberCell++;
				}
			}
			rows.appendChild(row);
			numberCell = 0;
		}
		
		idGridResults.appendChild(rows);
	}
	private void loadField(StudyTemplateParams param, Integer paramid, 
			ResultsPreferencesDetail prefdet, Row row){
	    loadCheckbox(param.getFactorname(), paramid, prefdet, row, param.getDatatype(), "N");
	}
	/**Method that load fields in the rows by field
	 * @param resultFields
	 * @param paramid
	 * @param prefdet
	 * @param row */
	private void loadField(ResultsCamposFijosUnEstudio resultFields, Integer paramid, 
			ResultsPreferencesDetail prefdet, Row row){
	    loadCheckbox(resultFields.getValue(), paramid, prefdet, row, resultFields.getTypeData(), "S"
	    				,resultFields.isDefaultChecked());
	}
	
	private void loadCheckbox (String labelCheck,  Integer paramid,
			ResultsPreferencesDetail prefdet, Row row,String strType, final String fijo){
		loadCheckbox (labelCheck, paramid, prefdet, row, strType, fijo, false);
	}
	@SuppressWarnings("unchecked")
	private void loadCheckbox (String labelCheck,  Integer paramid,
			ResultsPreferencesDetail prefdet, Row row,String strType, final String fijo, boolean isDefaultChecked){
		Cell cell = new Cell();
		final Checkbox checkbox = new Checkbox();
		checkbox.setLabel(labelCheck);
		checkbox.setId(PREFIX_CHECKBOX+indexComponentCheck+fijo);
		prefdet.setOrden(0);
	    prefdet.setParamid(paramid);
	    prefdet.setFijo(fijo);
	    checkbox.setChecked(isDefaultChecked);
	    checkbox.setAttribute(ATTRIBUTE_RESULT_DATA_ITEM, prefdet);
	    checkbox.setAttribute(ATTRIBUTE_RESULT_DATA_INDEX, indexComponentCheck+fijo);
	    checkbox.addEventListener("onCheck", new EventListener() {
      		 public void onEvent(Event event) throws Exception {
    			writeTextBox(checkbox.getLabel(), checkbox);
    			loadMapResulDataDetail(false, checkbox);
     	   }
  	  });	
	    checkbox.addEventListener("onClick", new EventListener() {
     		 public void onEvent(Event event) throws Exception {
     			 if (!checkbox.isChecked()){
     				writeTextBox("", checkbox);
     				loadMapResulDataDetail(true, checkbox);
     			 }
    	   }
 	  });	


	    //logger.info(" Load param ID : "+paramid + " index :: "+indexComponentCheck);
	    mapComboBox.put(indexComponentCheck+fijo, checkbox);
	    mapComboParamId.put(paramid+fijo, checkbox);
	    //cell.setAttribute(ATTRIBUTE_RESULT_DATA_ITEM, prefdet);
	    cell.appendChild(checkbox);
	    row.appendChild(cell);
	    Cell cellComponent = new Cell();
	    cellComponent.appendChild(getTypeTextComponent(RESULT_FIELD_CONSTANT_STRING, fijo));
	    row.appendChild(cellComponent);
	    indexComponentCheck ++;

	    //LOAD DEFAULTS
	    if(isDefaultChecked){
			((Textbox)cellComponent.getFirstChild()).setText(checkbox.getLabel());
			loadMapResulDataDetail(false, checkbox);
	    }

	}
	/** Method that create component of text to management of fields
	 * @param strTypel
	 * @return Component */
	private Component getTypeTextComponent(String strType ,String fijo){
		if (strType.equals(RESULT_FIELD_CONSTANT_CHARACTER) || 
				strType.equals(RESULT_FIELD_CONSTANT_STRING)){
			Textbox textBox = new Textbox ();
			textBox.setCols(50);
			textBox.setMaxlength(35);
			textBox.setId(PREFIX_TEXTBOX+indexComponentCheck+fijo);
			return textBox;
		}else if (strType.equals(RESULT_FIELD_CONSTANT_NUMERIC)){
			Intbox intBox = new Intbox ();
				intBox.setCols(50);
				intBox.setMaxlength(9);
				return intBox;
		}
		return null;
	}
	/**Method that create combo of configuration saved 	 */
	private void loadComboboxPreference (){
		ResultsPreferences filtro= new ResultsPreferences();
		filtro.setStudytemplateid(beanStudy.getStudytemplateid());
		List<ResultsPreferences> listResultsPreferents = serviceResultPreference.getListResultsPreferents(filtro);
		if (listResultsPreferents != null && !listResultsPreferents.isEmpty()){
			for (ResultsPreferences itemResults : listResultsPreferents){
				Comboitem itemTemp = new Comboitem ();
				itemTemp.setValue(itemResults);
				itemTemp.setLabel(itemResults.getPreferencesname());
				idCConfSaved.appendChild(itemTemp);
			}
		}
	}
	/** Method that load item combo by plate*/
	private void loadComboboxPlate (){
		List <String> listPlateName = serviceSampleDetail.getPlatesNamesFromLabStudy(beanStudy.getLabstudyid());
		if (listPlateName != null && !listPlateName.isEmpty()){
			Comboitem item = new Comboitem ();
			item.setValue(0);
			item.setLabel(pro.getKey(LBL_MENU_RESULTS_ALL));
			idCPlate.appendChild(item);
			int index = 1;
			for (String strPlate : listPlateName){
				Comboitem itemTemp = new Comboitem ();
				itemTemp.setValue(index);
				itemTemp.setLabel(strPlate);
				idCPlate.appendChild(itemTemp);
				index++;
			}
			idCPlate.setSelectedItem(item);
		}
	}
	/** Method that load the component
	 * s in the zul*/
	private void loadComponent (){
		idWindowR = (Window)getFellow("idWindowR");
		idCPlate = (Combobox)getFellow("idCPlate");
		idCConfSaved = (Combobox)getFellow("idCConfSaved");
		idGridResults = (Grid)getFellow("idGridResults");
	}
	/** Close Window*/
	public void closeWindow(){
		idWindowR.onClose();
	}
	/**Method that select all check box of the form*/
	public void selectAllItem(){
		int index = 0;
		for (Component check : mapComboBox.values()){
			Checkbox checkbox = (Checkbox)check;
			selectOrUnSelect(checkbox,false, index);
			index++;
		}
	}
	/** Method deselect all item */ 
	public void deSelectAllItem(){
		int index = 0;
		for (Component check : mapComboBox.values()){
			Checkbox checkbox = (Checkbox)check;
			selectOrUnSelect(checkbox,true, index);
			index++;
		}
	}

	private void selectOrUnSelect(Checkbox checkbox, boolean unSelect,int index){
		//ResultsPreferencesDetail prefdet = (ResultsPreferencesDetail)checkbox.getAttribute(ATTRIBUTE_RESULT_DATA_ITEM);
		String prefix = (String)checkbox.getAttribute(ATTRIBUTE_RESULT_DATA_INDEX);
		Textbox textBox = (Textbox)getFellow(PREFIX_TEXTBOX+prefix);
		if (unSelect){
			checkbox.setChecked(false);
			textBox.setValue("");
			textBox.setText("");
			loadMapResulDataDetail(true, checkbox);	
		}else {
			checkbox.setChecked(true);
			textBox.setValue(checkbox.getLabel());
			textBox.setText(checkbox.getLabel());
			loadMapResulDataDetail(false, checkbox);
		}
		
	}
	public void selectComboPreference (){
		ResultsPreferences itemResults = (ResultsPreferences)idCConfSaved.getSelectedItem().getValue();
		itemResults = serviceResultPreference.readResultsPreferences(itemResults.getResultpreferencesid());
		deSelectAllItem();
		for (ResultsPreferencesDetail detail : itemResults.getResultsPreferencesDetailCollection()){
			Checkbox checkbox = (Checkbox)mapComboParamId.get(detail.getParamid()+detail.getFijo());
			checkbox.setChecked(true);
			writeTextBox(detail.getHeader(), checkbox);
			loadMapResulDataDetail(false, checkbox);
		}
	}
	/**Method that write the content in the text box
	 * @param strLabel
	 * @param checkbox */
	private void writeTextBox (String strLabel, Checkbox checkbox){
		String prefix = (String)checkbox.getAttribute(ATTRIBUTE_RESULT_DATA_INDEX);
			Textbox textBox = (Textbox)getFellow(PREFIX_TEXTBOX+prefix);
			textBox.setValue(strLabel);
			textBox.setText(strLabel);
	}
	/**
	 * Method that load the map with component to will be save
	 */
	public void saveTemplateResulData(){
		if (mapResultDataSelect != null && mapResultDataSelect.size() > 0){
			final Window win = (Window) Executions.createComponents(
	    			"/general/add_name.zul", this, null);
	    		win.doModal();
	    		if (getDesktop().getAttribute(ATTRIBUTE_GENERIC_NAME) != null){
	    			loadTextOfResultData();
		    		String strtextName = (String)getDesktop().getAttribute(ATTRIBUTE_GENERIC_NAME);
		    		respreferences.setPreferencesname(strtextName);
		    		serviceResultPreference.saveOrUpdateResultsPreferences(respreferences);
		    		Messagebox.show(pro.getKey(LBL_RESULTS_DATA_SAVE), 
							pro.getKey(LBL_GENERIC_MESS_INFORMATION), 
							Messagebox.OK, Messagebox.INFORMATION);
		    		return;
	    		}
		}else {
			Messagebox.show(pro.getKey(LBL_GENERIC_WINDOW_ERR_SELECT_FIELD), 
					pro.getKey(LBL_GENERIC_MESS_ERROR), 
					Messagebox.OK, Messagebox.ERROR);
			return;
			
		}
	}

	private void loadTextOfResultData (){
		Iterator iterator = mapResultDataSelect.entrySet().iterator();
		respreferences.setColumnscount(mapResultDataSelect.size());
		respreferences.setStudytemplateid(beanStudy.getStudytemplateid());
		Collection<ResultsPreferencesDetail> detailResultCollection = new ArrayList<ResultsPreferencesDetail>();
		while(iterator.hasNext()){
			Map.Entry e = (Map.Entry)iterator.next();
			String integerV = (String)e.getKey();
			Textbox textBox = (Textbox)getFellow(PREFIX_TEXTBOX+integerV);
			ResultsPreferencesDetail resulPre = (ResultsPreferencesDetail) e.getValue();
			Checkbox checkbox = (Checkbox)getFellow(PREFIX_CHECKBOX+integerV);
			resulPre.setHeader(textBox.getText());
			resulPre.setParamname(checkbox.getLabel());
			resulPre.setResultpreferencesid(respreferences);
			//logger.info("Value order "+ resulPre.getOrden()+ " header :: "+ resulPre.getHeader()+ "  paramnane:: "+resulPre.getParamname() +
				//	" id :: "+resulPre.getParamid());
			detailResultCollection.add(resulPre);
			
		}
		respreferences.setResultsPreferencesDetailCollection(detailResultCollection);
	}
	/**Load Result data detail in map of result selected 
	 * @param remove
	 * @param checkbox*/
	private void loadMapResulDataDetail (boolean remove, Checkbox checkbox){
		ResultsPreferencesDetail prefdet = (ResultsPreferencesDetail)checkbox.getAttribute(ATTRIBUTE_RESULT_DATA_ITEM);
		String prefix = (String)checkbox.getAttribute(ATTRIBUTE_RESULT_DATA_INDEX);
		
		if (remove){
			mapResultDataSelect.remove(prefix);
		}else {
			prefdet.setOrden(indexOrder);
			mapResultDataSelect.put(prefix, prefdet);
			indexOrder++;
		}
	}
	/**
	 * Method that load all sample select and show in a table
	 */
	public void showResulDataTable(){
		if (mapResultDataSelect != null && mapResultDataSelect.size() > 0){
			loadTextOfResultData();
			ResultPreferenceCompleteBean resultBean = new ResultPreferenceCompleteBean();
			resultBean.setPreference(respreferences);
			String stringPlate = getListPlate();
			if (stringPlate != null){
					resultBean.setListPlateName(stringPlate);
					getDesktop().setAttribute(ATTRIBUTE_RESULT_DATA_BEAN_REPORT, resultBean);
					final Window win = (Window) Executions.createComponents(
			    			"/resultData/list_result_data.zul", this, null);
			    		win.doModal();
				}
				else {
					Messagebox.show(pro.getKey(LBL_RESULTS_SELECT_PLATE), 
							pro.getKey(LBL_GENERIC_MESS_ERROR), 
							Messagebox.OK, Messagebox.ERROR);					
				}
		}else {
			Messagebox.show(pro.getKey(LBL_GENERIC_WINDOW_ERR_SELECT_FIELD), 
					pro.getKey(LBL_GENERIC_MESS_ERROR), 
					Messagebox.OK, Messagebox.ERROR);
			return;
		}
	}

	/** Method that load the information about plates and field to report
	 * @return List String*/
	private String getListPlate(){
		Comboitem itemSelect = idCPlate.getSelectedItem();
		if (itemSelect != null && itemSelect.getValue() != null ){
			
			int valueSelect = (Integer)itemSelect.getValue();
			if (valueSelect != 0){
				return itemSelect.getLabel();
			}else{
				return "All";
				
			}
			
		}
		return null;
	}
}

