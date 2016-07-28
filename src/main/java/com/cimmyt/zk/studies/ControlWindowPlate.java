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

import static com.cimmyt.utils.Constants.ATTRIBUTE_CONTROL_LAB;
import static com.cimmyt.utils.Constants.ATTRIBUTE_EDIT_STUDIES;
import static com.cimmyt.utils.Constants.ATTRIBUTE_LABSTUDY_ITEM;
import static com.cimmyt.utils.Constants.ATTRIBUTE_LIST_TEMP_SAMPLE;
import static com.cimmyt.utils.Constants.ATTRIBUTE_MAIZE;
import static com.cimmyt.utils.Constants.ATTRIBUTE_MAP_CELL_SAMPLE;
import static com.cimmyt.utils.Constants.ATTRIBUTE_MAP_NUM_ITEM_SELECT;
import static com.cimmyt.utils.Constants.ATTRIBUTE_MAP_SAMPLE_DELETE;
import static com.cimmyt.utils.Constants.ATTRIBUTE_MAP_SAMPLE_EDIT;
import static com.cimmyt.utils.Constants.ATTRIBUTE_NAME_USER_BEAN;
import static com.cimmyt.utils.Constants.ATTRIBUTE_NUMBER_SAMPLES_CONTROL;
import static com.cimmyt.utils.Constants.ATTRIBUTE_SAMPLE_ITEM;
import static com.cimmyt.utils.Constants.ATTRIBUTE_SAMPLE_REPEAT;
import static com.cimmyt.utils.Constants.ATTRIBUTE_SIZE_COLUMNS;
import static com.cimmyt.utils.Constants.ATTRIBUTE_SIZE_PLATE_SELECT;
import static com.cimmyt.utils.Constants.ATTRIBUTE_TYPE_FILE_CONTROL;
import static com.cimmyt.utils.Constants.ATTRIBUTE_TYPE_LOAD_FILE;
import static com.cimmyt.utils.Constants.ATTRIBUTE_TYPE_LOAD_PLATE;
import static com.cimmyt.utils.Constants.ATTRIBUTE_TYPE_SAMPLE_MIXTURE;
import static com.cimmyt.utils.Constants.ATTRIBUTE_WHEAT;
import static com.cimmyt.utils.Constants.CONTROL_FILE_SERVICE_MANAGEMENT;
import static com.cimmyt.utils.Constants.CREATE_PLATE_SERVICE_BEAN_NAME;
import static com.cimmyt.utils.Constants.LABSTUDY_SERVICE_BEAN_NAME;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MEN_FUNTION_AVAILABLE_ONLY_EDIT;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_ERROR;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_INFORMATION;
import static com.cimmyt.utils.Constants.LBL_STUDIES_CONTROL_DART;
import static com.cimmyt.utils.Constants.LBL_STUDIES_CONTROL_KBIOSCIENCES;
import static com.cimmyt.utils.Constants.LBL_STUDIES_CREATE_PLATE_LAYOUT;
import static com.cimmyt.utils.Constants.LBL_STUDIES_CREATE_PLATE_LAYOUT_QUESTION;
import static com.cimmyt.utils.Constants.LBL_STUDIES_FILE_EXP_NUM_CON_ERR;
import static com.cimmyt.utils.Constants.LBL_STUDIES_PLATE_EMTY_SELECT;
import static com.cimmyt.utils.Constants.LBL_STUDIES_PLATE_ITEM_ASSIGNED;
import static com.cimmyt.utils.Constants.LBL_STUDIES_PLATE_ITEM_BLANK;
import static com.cimmyt.utils.Constants.LBL_STUDIES_PLATE_ITEM_CONTROL;
import static com.cimmyt.utils.Constants.LBL_STUDIES_RANDOM_NOT_SAMP_ASSIG;
import static com.cimmyt.utils.Constants.LBL_STUDIES_RANDOM_SIZE_ASSIGNED;
import static com.cimmyt.utils.Constants.LBL_STUDIES_SAVE_SUCCESS;
import static com.cimmyt.utils.Constants.LBL_STUDIES_TYPE_RACKS;
import static com.cimmyt.utils.Constants.LBL_STUDIES_TYPE_SEP_TUBES;
import static com.cimmyt.utils.Constants.LOCALE_LANGUAGE;
import static com.cimmyt.utils.Constants.PROJECT_SERVICE_BEAN_NAME;
import static com.cimmyt.utils.Constants.SAMPLE_SERVICE_BEAN_NAME;
import static com.cimmyt.utils.Constants.URL_IMAGES_ASSIGNED_TUBE;
import static com.cimmyt.utils.Constants.URL_IMAGES_BLANK_TUBE;
import static com.cimmyt.utils.Constants.URL_IMAGES_CONTROL_TUBE;
import static com.cimmyt.utils.Constants.URL_IMAGES_DART_CONTROL_TUBE;
import static com.cimmyt.utils.Constants.URL_IMAGES_KBIO_CONTROL_TUBE;
import static com.cimmyt.utils.ConstantsDNA.FILE_UP_LOAD_ONLY_SAMPLES;
import static com.cimmyt.utils.ConstantsDNA.FILE_UP_LOAD_SAMPLES_CONTROL;
import static com.cimmyt.utils.ConstantsDNA.SIZE_MIN_RANDOM;
import static com.cimmyt.utils.Constants.LBL_STUDIES_TITLE_SUB_WIN_ADD_LOAD_COL;
import static com.cimmyt.utils.Constants.LBL_STUDIES_TITLE_SUB_WIN_ADD_LOAD_ROW;
import static com.cimmyt.utils.Constants.LBL_STUDIES_TITLE_SUB_WIN_ADD_LOAD_CIMMYT;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.zkoss.zhtml.Filedownload;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zkex.zul.Fisheye;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;
import org.zkoss.zul.Window;

import com.cimmyt.bean.ControlPlateBean;
import com.cimmyt.bean.FileCSVBean;
import com.cimmyt.bean.FileControlBean;
import com.cimmyt.bean.ProjectBean;
import com.cimmyt.bean.UserBean;
import com.cimmyt.loadStudy.ServiceControlFile;
import com.cimmyt.loadStudy.ServiceCreatePlate;
import com.cimmyt.model.bean.LabStudy;
import com.cimmyt.model.bean.LoadType;
import com.cimmyt.model.bean.Project;
import com.cimmyt.model.bean.SampleDetail;
import com.cimmyt.model.bean.TemporalSample;
import com.cimmyt.service.ServiceLabStudy;
import com.cimmyt.service.ServiceProject;
import com.cimmyt.service.ServiceSample;
import com.cimmyt.servlet.RedirectServletReport;
import com.cimmyt.servlet.SessionReport;
import com.cimmyt.study.PlateContentList;
import com.cimmyt.study.PlateList;
import com.cimmyt.study.PlateRow;
import com.cimmyt.utils.CommonUtils;
import com.cimmyt.utils.ConstantsDNA;
import com.cimmyt.utils.PropertyHelper;
import com.cimmyt.utils.StrUtils;

@SuppressWarnings("serial")
public class ControlWindowPlate extends Window{

	private LabStudy bean;
	//private LabStudy beanOriginal;
	private PropertyHelper pro=null;
	private PlateList plateList;
	private Spinner idSPS;
	private Spinner idSNP;
	private Tabbox idTabBox;
	private Combobox idComPT;
	//private Radiogroup idRGTC;
	//Object represent the cells create where string is name of plate plus cell
	private Map<String, Object> mapCellSample = new HashMap<String, Object>();
	//Object that represent the samples Assigned
	private Map<String, Object> mapAssignSample = new HashMap<String, Object>();
	//Object to add or delete elements at size of plate
	private Map<String, Integer> mapSizePlate = new HashMap<String, Integer>();
	private boolean isFirstClickDart = true;
	private boolean isFirstClickKBios = true;
	private String delimiter = "\\|";
	//Value to default by rack 
	private final int valueRack = 0;
	//Value to default by separate tube
	private final int valueSepTub = 1;
	//component to add study at BD
	private Fisheye idFisheyeNext;
	 //radio button load row
	//private Radio idRBRow;
	 //radio button load Column
	//private Radio idRBColumn;
	//Number of control random
	private Integer numberControlRandom = 0;
	// Number of control to provider 
	private Integer numberControlProvider = new Integer (0);
	// Number of control to blank 
	private Integer numberControlBlank = new Integer (0);
	//list of samples old
	//private List<SampleDetail> listoldSampleDetList = new ArrayList <SampleDetail>();
	//LABSTUDY_SERVICE_BEAN_NAME
	private static ServiceLabStudy serviceLabStudy = null;
	//Service to management the creation of plate 
	private static ServiceCreatePlate serviceCreatePlate = null;
	// service of samples
	private static ServiceSample serviceSample = null;
	// service of projects
	private static ServiceProject serviceProject = null;
	//service to management of control in the plate to export file
	private static ServiceControlFile serviceControlFile = null;
	// Logger to management of error
	private Logger logger= Logger.getLogger(ControlWindowPlate.class);
	//load bean Service Lab Study
	private final String patternNumber = "[0-9]+";
	private final String patternLetter = "[A-Z]";
	private Map<String, Object> mapSampleEdit ;
	private List <String> listStr = new ArrayList<String>();
	private List <String> listSampleNotRepeat = new ArrayList<String>();
	private List <String> listSampleRepeat = new ArrayList<String>();
	// Boolean variable to know if is a study to edit
	private Combobox idCBLoadWay;
	private boolean isEdit = false;
	
	static {
		if(serviceLabStudy == null)
			try{
				serviceLabStudy = (ServiceLabStudy)SpringUtil.getBean(LABSTUDY_SERVICE_BEAN_NAME);
			}catch (Exception e){e.printStackTrace();}
	}
	static {
		if(serviceCreatePlate == null)
			try{
				serviceCreatePlate = (ServiceCreatePlate)SpringUtil.getBean(CREATE_PLATE_SERVICE_BEAN_NAME);
			}catch (Exception e){e.printStackTrace();}
	}
    static {
    	if (serviceSample == null)
    		try{
    			serviceSample = (ServiceSample)SpringUtil.getBean(SAMPLE_SERVICE_BEAN_NAME); 
    		}catch (Exception e){e.printStackTrace();}
    }
    static {
    	if (serviceProject == null)
    		try{
    			serviceProject = (ServiceProject)SpringUtil.getBean(PROJECT_SERVICE_BEAN_NAME); 
    		}catch (Exception e){e.printStackTrace();}
    }
    static {
    	if (serviceControlFile == null)
    		try{
    			serviceControlFile = (ServiceControlFile)SpringUtil.getBean(CONTROL_FILE_SERVICE_MANAGEMENT); 
    		}catch (Exception e){e.printStackTrace();}
    }

	/**
	 * Method that clear all objects of this class to work correctly with load of samples
	 */
	private void clearObjects(){
		mapCellSample = new HashMap<String, Object>();
		mapAssignSample = new HashMap<String, Object>();
		mapSizePlate = new HashMap<String, Integer>();
		isFirstClickDart = true;
		isFirstClickKBios = true;
		numberControlRandom = 0;
		numberControlProvider = 0;
	}
	/** 
	 * Close all popup Windows
	 */
	public void closeWindow(){
		((Window)getParent()).onClose();
		this.onClose();
	}
	/**
	 * Method that load context of the study to new and edit
	 */
	public void loadContextComponent(){
		pro = (PropertyHelper)getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);
		loadContext();
		loadComboBoxTypePlate();
		bean = (LabStudy)getDesktop().getAttribute(ATTRIBUTE_LABSTUDY_ITEM);
		if (getDesktop().getAttribute(ATTRIBUTE_EDIT_STUDIES) != null){
			plateList = new PlateList(bean.getPlatesize(), 
					bean.getNumofplates(), bean,getPrefix());
			isEdit = true;
			loadTabbox(false);
		}

	}
	/**
	 * Method to initialize the combobox for type of plate
	 */
	private void loadComboBoxTypePlate(){
		//idComPT
		Comboitem itemRack = new Comboitem (pro.getKey(LBL_STUDIES_TYPE_RACKS)); 
		itemRack.setValue(valueRack);
		Comboitem itemSepTube = new Comboitem (pro.getKey(LBL_STUDIES_TYPE_SEP_TUBES));
		itemSepTube.setValue(valueSepTub);
		idComPT.appendChild(itemRack);
		idComPT.appendChild(itemSepTube);
		idComPT.setSelectedItem(itemRack);
		idComPT.setDisabled(true);
	}
	/**
	 * Method that create the plate 
	 */
	public void generatePlates(){
		if (Messagebox.show(pro.getKey(LBL_STUDIES_CREATE_PLATE_LAYOUT_QUESTION), 
				pro.getKey(LBL_STUDIES_CREATE_PLATE_LAYOUT), 
				Messagebox.YES | Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
			clearObjects();
			plateList = new PlateList(idSPS.getValue(), idSNP.getValue(), bean,getPrefix());
			loadTabbox(true);
		}
	}

	private void clearTabbox (){
		if (!idTabBox.getChildren().isEmpty())
			while (!idTabBox.getChildren().isEmpty()){
				idTabBox.getChildren().remove(0);
			}
	}
	private String getPrefix (){
		String prefix  = bean.getProject().getProjectname()+
				bean.getProject().getPurposename()+""+
				bean.getOrganismid().getOrganismname().substring(0, 1)+""+
				bean.getInvestigatorid().getInvest_abbreviation();
		return prefix.toUpperCase();
	}
	private void loadTabbox(boolean isNewStudy){
		clearTabbox();
		Tabs tabs = new Tabs();
		Tabpanels tabPanels = new Tabpanels();
		tabPanels.setHeight("100%");
		tabPanels.setWidth("100%");
		tabPanels.setStyle("padding:0px");
		if (isNewStudy){
			for (PlateContentList plateContent : plateList.getPlatesContents()){
				Tab tab = new Tab(plateContent.getPlateName());
				tabs.appendChild(tab);
				idTabBox.appendChild(tabs);
				Tabpanel tabPanelj = new Tabpanel();
				tabPanelj.setHeight("100%");
				tabPanelj.setWidth("100%");
				tabPanelj.setStyle("padding:0px");
				
				tabPanels.setParent(idTabBox);
				tabPanelj.setParent(tabPanels);
				ControlPlateBean controlPlateBean = new ControlPlateBean();
				tabPanelj.appendChild(serviceCreatePlate.addLoadGridByPlate(plateList.getPlatesContents().get(0).
						getPlateRows().get(0).getColsPerRow(), plateContent,mapCellSample ,pro, bean, isNewStudy,
						mapSampleEdit, "", tabPanelj, null, mapAssignSample, mapSizePlate, controlPlateBean));
				upDateControlPlate(controlPlateBean);
				tabPanels.appendChild(tabPanelj);
				idTabBox.appendChild(tabPanels);
			}
		}else {
			createMapByPlateToEdit();
			if (bean.getSampleDetailCollection().size() > 0){
				idSNP.setValue(bean.getNumofplates());
				idSPS.setValue(bean.getPlatesize());
				int indexPlate = 0;
				
				bubbleShort();
				for (String namePlate : listStr){
						Tab tab = new Tab(namePlate);
						tabs.appendChild(tab);
						Tabpanel tabPanelj = new Tabpanel();
						tabPanelj.setHeight("100%");
						tabPanelj.setWidth("100%");
						tabPanelj.setStyle("padding:0px");
						tabPanels.setParent(idTabBox);
						tabPanelj.setParent(tabPanels);
						try{
							ControlPlateBean controlPlateBean = new ControlPlateBean();
						tabPanelj.appendChild(serviceCreatePlate.addLoadGridByPlate(plateList.getPlatesContents().get(0).
								getPlateRows().get(0).getColsPerRow(), plateList.getPlatesContents().get(0),mapCellSample ,pro, bean, isNewStudy,
								mapSampleEdit, namePlate, tabPanelj, listSampleRepeat, mapAssignSample, mapSizePlate,controlPlateBean));
						upDateControlPlate(controlPlateBean);
						}catch (Exception ex){
							ex.printStackTrace();
						}
						tabPanelj.setId(namePlate);
						tabPanels.appendChild(tabPanelj);
						idTabBox.appendChild(tabs);
						idTabBox.appendChild(tabPanels);
						plateList.getPlatesContents().get(indexPlate).setPlateName(namePlate);
						indexPlate++;
				}
			}
			idFisheyeNext.setVisible(false);
			//index is idLoadType - 1
			loadValueForComboLoadWay(bean.getLoadType().getIdLoadType().intValue());
		}
	}

	private void loadValueForComboLoadWay (int value){
		List<Comboitem>listItems = idCBLoadWay.getItems();
		for (Comboitem item : listItems){
			int valueInt = (Integer)item.getValue();
			if (valueInt == value){
				idCBLoadWay.setSelectedItem(item);
				break;
			}
		}
		idCBLoadWay.setDisabled(true);
	}

	private void createMapByPlateToEdit(){
		mapSampleEdit = new HashMap <String, Object>();
		listStr = new ArrayList<String>();
		listSampleNotRepeat = new ArrayList<String>();
		listSampleRepeat = new ArrayList<String>();
		for (SampleDetail sampleDetail : bean.getSampleDetailCollection()){
			String samplePlateName =  sampleDetail.getPlatename();
			Object[] objStr = getLettherNumber(sampleDetail.getPlateloc());
			if (!listStr.contains(samplePlateName)){
				listStr.add(samplePlateName);
			}
			if (sampleDetail.getSamplegid() != null){
				String sampleId = StrUtils.getSampleIDKey(bean, sampleDetail.getSamplegid());
				if (!listSampleNotRepeat.contains(sampleId)){
					listSampleNotRepeat.add(sampleId);
				}else {
					listSampleRepeat.add(sampleId);
				}
			}
			mapSampleEdit.put(samplePlateName+"|"+(String)objStr[0]+ 
					((Integer)objStr[1]).toString(), sampleDetail);
		}
	}

	/**
	 * Method that read of String the location in the plate  in the first position is letter and second is
	 * number 
	 * @param plateLoc
	 * @return Object of String  
	 */
	private Object[] getLettherNumber (String plateLoc){
		Object [] objStr = new Object [2];
		Pattern resul1 = Pattern.compile(this.patternNumber);
		Matcher matcherLetter = resul1.matcher (plateLoc);
		String strResultLetter =  matcherLetter.replaceAll("");
		objStr [0] = strResultLetter;
		Pattern result2 = Pattern.compile(this.patternLetter);
		Matcher matcherNumber = result2.matcher (plateLoc);
		String strResultNumber = matcherNumber.replaceAll("");
		objStr [1] = Integer.parseInt(strResultNumber);
		return objStr;
	}	
	private void loadContext(){
		idSPS = (Spinner)getFellow("idSPS");
		idSNP = (Spinner)getFellow("idSNP");
		idTabBox = (Tabbox)getFellow("idTabBox");
		idComPT = (Combobox)getFellow("idComPT");
		idCBLoadWay = (Combobox)getFellow("idCBLoadWay");
		idFisheyeNext = (Fisheye)getFellow("idFisheyeNext");
		loadComboTypeLoad();
	}

	private void loadComboTypeLoad(){
		Comboitem itemColumns = new Comboitem();
		itemColumns.setLabel(pro.getKey(LBL_STUDIES_TITLE_SUB_WIN_ADD_LOAD_COL));
		itemColumns.setValue(ConstantsDNA.FILE_UP_LOAD_SAMPLES_BY_COLUMNS);
		idCBLoadWay.appendChild(itemColumns);
		Comboitem itemRows = new Comboitem();
		itemRows.setLabel(pro.getKey(LBL_STUDIES_TITLE_SUB_WIN_ADD_LOAD_ROW));
		itemRows.setValue(ConstantsDNA.FILE_UP_LOAD_SAMPLES_BY_ROW);
		idCBLoadWay.appendChild(itemRows);
		Comboitem itemCimmyt = new Comboitem();
		itemCimmyt.setLabel(pro.getKey(LBL_STUDIES_TITLE_SUB_WIN_ADD_LOAD_CIMMYT));
		itemCimmyt.setValue(ConstantsDNA.FILE_UP_LOAD_SAMPLES_BY_ZIGZAG);	
		idCBLoadWay.appendChild(itemCimmyt);
		idCBLoadWay.setSelectedItem(itemColumns);
	}
	@SuppressWarnings("rawtypes")
	public void selectAll(){
		if (thereAreInformation())
			return;
		Set s=mapCellSample.entrySet();
        Iterator it=s.iterator();
        while(it.hasNext())
        {
            Map.Entry m =(Map.Entry)it.next();
            Cell value=(Cell)m.getValue();
            Checkbox checkBox = getCheckbox(value);
            if (!checkBox.isDisabled())
            checkBox.setChecked(true);
        }
	}

	private Checkbox getCheckbox(Cell value){
		Hbox hBox = (Hbox)value.getChildren().get(0);
		Checkbox checkBox = (Checkbox)hBox.getChildren().get(0);
		return checkBox;
	}

	@SuppressWarnings("rawtypes")
	public void setAssign(){
		if (thereAreInformation())
			return;
		Set s=mapCellSample.entrySet();
        Iterator it=s.iterator();
        while(it.hasNext())
        {
            Map.Entry m =(Map.Entry)it.next();
            Cell value=(Cell)m.getValue();
            Checkbox checkBox = getCheckbox(value);
            if (checkBox.isChecked()){
            	if (value.getAttribute(ATTRIBUTE_SAMPLE_ITEM) != null && 
            			(Integer)value.getAttribute(ATTRIBUTE_SAMPLE_ITEM) 
            			!= PlateRow.ASSIGNED_NUM) {
            		loadMapAssigned((String)m.getKey());
                	value.setAttribute(ATTRIBUTE_SAMPLE_ITEM, PlateRow.ASSIGNED_NUM);
                	checkBox.setChecked(false);
                	Image image = (Image)value.getChildren().get(0).getChildren().get(1);
                	image.setSrc(URL_IMAGES_ASSIGNED_TUBE);
                	Label label = (Label)value.getChildren().get(0).getChildren().get(2);
                	label.setValue(pro.getKey(LBL_STUDIES_PLATE_ITEM_ASSIGNED));	
            	}else{
            		checkBox.setChecked(false);
            	}
            }
        }
        clearSelecctionControlDartAndKbios();
	}
	private void loadMapAssigned(String key){
		String [] arrSplit = key.split(delimiter);
		if (mapAssignSample.containsKey(arrSplit[0])){
			int value =(Integer) mapAssignSample.get(arrSplit[0]);
			++value;
			mapAssignSample.put(arrSplit[0], value);
		}else{
			mapAssignSample.put(arrSplit[0], 1);
		}
		loadMapSizePlate(true,arrSplit[0]);
	}

	@SuppressWarnings("rawtypes")
	public void blankSelecction(){
		if (thereAreInformation())
			return;
		Set s=mapCellSample.entrySet();
        Iterator it=s.iterator();
        while(it.hasNext())
        {
            Map.Entry m =(Map.Entry)it.next();
            Cell value=(Cell)m.getValue();
            Checkbox checkBox = getCheckbox(value);
            if (checkBox.isChecked()){
            	clearMapAssign(value.getAttribute(ATTRIBUTE_SAMPLE_ITEM), (String)m.getKey());
            	value.setAttribute(ATTRIBUTE_SAMPLE_ITEM, PlateRow.BLANK_ITEM_NUM);
            	checkBox.setChecked(false);
            	Image image = (Image)value.getChildren().get(0).getChildren().get(1);
            	image.setSrc(URL_IMAGES_BLANK_TUBE);
            	Label label = (Label)value.getChildren().get(0).getChildren().get(2);
            	label.setValue(pro.getKey(LBL_STUDIES_PLATE_ITEM_BLANK));
            }
        }
        clearSelecctionControlDartAndKbios();
	}
	private void clearMapAssign (Object value, String key){
		if (value != null){
			int valueInt = (Integer)value;
			String [] arrSplit = key.split(delimiter);
			if (mapAssignSample.containsKey(arrSplit[0])){
				switch (valueInt){
					case PlateRow.ASSIGNED_NUM :
						loadMapSizePlate(false,arrSplit[0]);
						break;
					case PlateRow.DART_CONTROL_ITEM_NUM :
						loadMapSizePlate(false,arrSplit[0]);
						break;
					case PlateRow.KBIO_CONTROL_ITEM_NUM :
						loadMapSizePlate(false,arrSplit[0]);
						break;
					default :
						break;
				}
			}
		}
	}
	@SuppressWarnings("rawtypes")
	public void clearSelecction(){
		if (thereAreInformation())
			return;
		Set s=mapCellSample.entrySet();
        Iterator it=s.iterator();
        while(it.hasNext())
        {
            Map.Entry m =(Map.Entry)it.next();
            Cell value=(Cell)m.getValue();
            Checkbox checkBox = getCheckbox(value);
            if (checkBox.isChecked()){
            	checkBox.setChecked(false);
            }
        }
        clearSelecctionControlDartAndKbios();
	}
	@SuppressWarnings("rawtypes")
	public void selectControl(){
		if (thereAreInformation())
			return;
		Set s=mapCellSample.entrySet();
        Iterator it=s.iterator();
        while(it.hasNext())
        {
            Map.Entry m =(Map.Entry)it.next();
            Cell value=(Cell)m.getValue();
            Checkbox checkBox = getCheckbox(value);
            if (checkBox.isChecked()){
            	clearMapAssign(value.getAttribute(ATTRIBUTE_SAMPLE_ITEM), (String)m.getKey());
            	value.setAttribute(ATTRIBUTE_SAMPLE_ITEM, PlateRow.BLANK_CONTROL_NUM);
            	checkBox.setChecked(false);
            	Image image = (Image)value.getChildren().get(0).getChildren().get(1);
            	image.setSrc(URL_IMAGES_CONTROL_TUBE);
            	Label label = (Label)value.getChildren().get(0).getChildren().get(2);
            	label.setValue(pro.getKey(LBL_STUDIES_PLATE_ITEM_CONTROL));
            }
        }
	}
	private boolean thereAreInformation(){
		if (mapCellSample.isEmpty()){
			getMessageEmtyList();
			return true;
		}
		
		return false; 
	}
	private void getMessageEmtyList(){
		Messagebox.show(pro.getKey(LBL_STUDIES_PLATE_EMTY_SELECT),pro.getKey(LBL_GENERIC_MESS_INFORMATION), 
				Messagebox.OK, Messagebox.INFORMATION);
	}
	@SuppressWarnings("rawtypes")
	public void loadControlDartAndKbios(boolean typeControl){
		if (thereAreInformation())
			return;
		Set s=mapCellSample.entrySet();
        Iterator it=s.iterator();
        while(it.hasNext())
        {
            Map.Entry m =(Map.Entry)it.next();
            Cell value=(Cell)m.getValue();
            if (value.getAttribute(ATTRIBUTE_CONTROL_LAB) != null){
            	int index = (Integer)value.getAttribute(ATTRIBUTE_CONTROL_LAB);
            	if (typeControl){
            		if (index == PlateRow.DART_CONTROL_ITEM_NUM ){
            			value.setAttribute(ATTRIBUTE_SAMPLE_ITEM, index);
            		loadComponentControl(value, PlateRow.DART_CONTROL_ITEM_NUM,(String)m.getKey());
            		}
            	}else {
            		if (index == PlateRow.KBIO_CONTROL_ITEM_NUM ){
            			value.setAttribute(ATTRIBUTE_SAMPLE_ITEM, index);
            			loadComponentControl(value, PlateRow.KBIO_CONTROL_ITEM_NUM,(String)m.getKey());	
            		}
            	}
            }
        }
        if (typeControl){
        isFirstClickDart = !isFirstClickDart;
        }else {
        	isFirstClickKBios = !isFirstClickKBios;
        }
	}

	public void loadControlCIMMYTEmpty(){
		if (thereAreInformation())
			return;
		Set s=mapCellSample.entrySet();
        Iterator it=s.iterator();
        while(it.hasNext())
        {
        	Map.Entry m =(Map.Entry)it.next();
            Cell value=(Cell)m.getValue();
            if (value.getAttribute(ATTRIBUTE_CONTROL_LAB) != null){
            	int index = (Integer)value.getAttribute(ATTRIBUTE_CONTROL_LAB);
            		if (index == PlateRow.BLANK_CONTROL_NUM ){
            		    Checkbox checkBox = getCheckbox(value);
                       	clearMapAssign(value.getAttribute(ATTRIBUTE_SAMPLE_ITEM), (String)m.getKey());
                     	value.setAttribute(ATTRIBUTE_SAMPLE_ITEM, PlateRow.BLANK_ITEM_NUM);
                     	checkBox.setChecked(false);
                     	Image image = (Image)value.getChildren().get(0).getChildren().get(1);
                     	image.setSrc(URL_IMAGES_BLANK_TUBE);
                     	Label label = (Label)value.getChildren().get(0).getChildren().get(2);
                     	label.setValue(pro.getKey(LBL_STUDIES_PLATE_ITEM_BLANK));
                     
            		
            		
            		}
            	
            }
        }
	}
	private void clearSelecctionControlDartAndKbios(){
		isFirstClickDart = true;
		isFirstClickKBios = true;
	}

	private void loadComponentControl (Cell cell, int value, String key){
		Checkbox checkBox = getCheckbox(cell);
		checkBox.setChecked(false);
		Image image = (Image)cell.getChildren().get(0).getChildren().get(1);
    	Label label = (Label)cell.getChildren().get(0).getChildren().get(2);
    	
		switch (value){
			case 3 :
				if (isFirstClickDart){
					clearMapAssign(cell.getAttribute(ATTRIBUTE_SAMPLE_ITEM), key);
					cell.setAttribute(ATTRIBUTE_SAMPLE_ITEM, PlateRow.DART_CONTROL_ITEM_NUM);
					image.setSrc(URL_IMAGES_DART_CONTROL_TUBE);
					label.setValue(pro.getKey(LBL_STUDIES_CONTROL_DART));
					numberControlProvider++;
					
				}else {
					cell.setAttribute(ATTRIBUTE_SAMPLE_ITEM, PlateRow.BLANK_ITEM_NUM);
					image.setSrc(URL_IMAGES_BLANK_TUBE);
					label.setValue(pro.getKey(LBL_STUDIES_PLATE_ITEM_BLANK));
					numberControlProvider--;
				}		
				break;
			case 4:
				
				if (isFirstClickKBios){
					clearMapAssign(cell.getAttribute(ATTRIBUTE_SAMPLE_ITEM), key);
					cell.setAttribute(ATTRIBUTE_SAMPLE_ITEM, PlateRow.KBIO_CONTROL_ITEM_NUM);
					image.setSrc(URL_IMAGES_KBIO_CONTROL_TUBE);
					label.setValue(pro.getKey(LBL_STUDIES_CONTROL_KBIOSCIENCES));
					numberControlProvider++;
				}else {
					cell.setAttribute(ATTRIBUTE_SAMPLE_ITEM, PlateRow.BLANK_ITEM_NUM);
					image.setSrc(URL_IMAGES_BLANK_TUBE);
					label.setValue(pro.getKey(LBL_STUDIES_PLATE_ITEM_BLANK));
					numberControlProvider--;
				}
				break;
			default :
				break;
		}
	}

	private void loadMapSizePlate(boolean inserOrDelete, String namePlate){
		if (inserOrDelete){
			if (mapSizePlate.size() == 0 ){
				mapSizePlate.put(namePlate, new Integer(1));
			}else {
				if (mapSizePlate.get(namePlate)!= null){
					Integer valueSize = mapSizePlate.get(namePlate);
					mapSizePlate.put(namePlate, ++valueSize);
				}else {
					mapSizePlate.put(namePlate, new Integer(1));
				}
			}	
		}else {
			if (mapSizePlate.size() > 0 ){
				Integer valueSize = mapSizePlate.get(namePlate);
				mapSizePlate.put(namePlate, --valueSize);
			}
		}
	}

/**
 * Create the controls random 
 */
	public void randomControl(){
		if (thereAreInformation())
			return;
		if (mapSizePlate.size() == 0){
			Messagebox.show(pro.getKey(LBL_STUDIES_RANDOM_NOT_SAMP_ASSIG), 
 					pro.getKey(LBL_GENERIC_MESS_INFORMATION), 
 					Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
		@SuppressWarnings("rawtypes")
		Set set = mapSizePlate.entrySet();
	      @SuppressWarnings("rawtypes")
		Iterator it=set.iterator();
	      while(it.hasNext())
	      {
	          @SuppressWarnings("rawtypes")
			Map.Entry m =(Map.Entry)it.next();
	          Integer value=(Integer)m.getValue();
	          if (   value.intValue() <= SIZE_MIN_RANDOM){
	        	 Messagebox.show(pro.getKey(LBL_STUDIES_RANDOM_SIZE_ASSIGNED), 
	 					pro.getKey(LBL_GENERIC_MESS_INFORMATION), 
	 					Messagebox.OK, Messagebox.INFORMATION);
	        	 return;
	          }     
	      }
			getDesktop().setAttribute(ATTRIBUTE_SIZE_PLATE_SELECT, idSPS.getValue());
			getDesktop().setAttribute(ATTRIBUTE_MAP_NUM_ITEM_SELECT, mapSizePlate);
			getDesktop().setAttribute(ATTRIBUTE_MAP_CELL_SAMPLE, mapCellSample);
			getDesktop().setAttribute(ATTRIBUTE_SIZE_COLUMNS, plateList.getPlatesContents().get(0).
					getPlateRows().get(0).getColsPerRow());
			final Window win = (Window) Executions.createComponents(
	    			"/studies/window_random.zul", null, null);
	    		win.doModal();
	    		
	    		if (getDesktop().getAttribute(ATTRIBUTE_NUMBER_SAMPLES_CONTROL) != null)
	    		numberControlRandom = (Integer)getDesktop().getAttribute(ATTRIBUTE_NUMBER_SAMPLES_CONTROL);
	}	
	/**
	 * Load data form file 
	 */
	public void loadDataFromFile(){
		if (thereAreInformation())
			return;
		if (mapSizePlate.size() == 0){
			Messagebox.show(pro.getKey(LBL_STUDIES_RANDOM_NOT_SAMP_ASSIG), 
 					pro.getKey(LBL_GENERIC_MESS_INFORMATION), 
 					Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
	  loadWindowToUpLoadFileSampleControl(FILE_UP_LOAD_ONLY_SAMPLES);
	}
	/**
	 * Method that can up load the file with control to be change a sample with identifier
	 */
	public void upLoadFileControl(){
		if (!isEdit){
			Messagebox.show(pro.getKey(LBL_GENERIC_MEN_FUNTION_AVAILABLE_ONLY_EDIT), 
 					pro.getKey(LBL_GENERIC_MESS_ERROR), 
 					Messagebox.OK, Messagebox.ERROR);
			return;
		}
		if (thereAreInformation())
			return;
		if (mapSizePlate.size() == 0){
			Messagebox.show(pro.getKey(LBL_STUDIES_RANDOM_NOT_SAMP_ASSIG), 
 					pro.getKey(LBL_GENERIC_MESS_INFORMATION), 
 					Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
		loadWindowToUpLoadFileSampleControl(FILE_UP_LOAD_SAMPLES_CONTROL);
	}

	public void downloadTemplate(){
		try {
			String rutaResumen="/com/cimmyt/reports/src/template.csv";
			InputStream isTemplateStream =getClass().getResourceAsStream(rutaResumen);
			Filedownload.save(isTemplateStream, "application/csv", "template.csv");
		}catch (Exception ex ){
			ex.printStackTrace();
		}
		//Filedownload.s
	}
	private void loadWindowToUpLoadFileSampleControl(int typeFile){
		getDesktop().setAttribute(ATTRIBUTE_MAP_CELL_SAMPLE, mapCellSample);
		// To this variable there is two values 0 is by columns and 1 by rows  
		getDesktop().setAttribute(ATTRIBUTE_TYPE_LOAD_FILE, ((Integer)idCBLoadWay.getSelectedItem().getValue()));
		getDesktop().setAttribute(ATTRIBUTE_TYPE_SAMPLE_MIXTURE, false);
		getDesktop().setAttribute(ATTRIBUTE_TYPE_LOAD_PLATE, plateList);
		getDesktop().setAttribute(ATTRIBUTE_MAP_NUM_ITEM_SELECT, mapSizePlate);
		getDesktop().setAttribute(ATTRIBUTE_TYPE_FILE_CONTROL, typeFile);
		final Window win = (Window) Executions.createComponents(
    			"/studies/file_upload.zul", null, null);
    		win.doModal();
    		UserBean userBean = (UserBean)getDesktop().getSession().getAttribute(ATTRIBUTE_NAME_USER_BEAN);
    		if(getDesktop().getAttribute(ATTRIBUTE_SAMPLE_REPEAT) != null ){
    		boolean thereAreAnySampleRepeat = (Boolean)getDesktop().getAttribute(ATTRIBUTE_SAMPLE_REPEAT);
    	  		idFisheyeNext.setVisible(false);
	    		switch (userBean.getTypeCorp()){
	    		case ATTRIBUTE_MAIZE :
	    			idFisheyeNext.setVisible(true);
	    			break;
	    		case ATTRIBUTE_WHEAT :
	    			if (!thereAreAnySampleRepeat)
	    				idFisheyeNext.setVisible(true);
	    			break;
	    		}
    		}
	}
	/**
	 * Method that export file with controls of the plate current only in form of edition 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void exportFileControl(){
		if (!isEdit){
			Messagebox.show(pro.getKey(LBL_GENERIC_MEN_FUNTION_AVAILABLE_ONLY_EDIT), 
 					pro.getKey(LBL_GENERIC_MESS_ERROR), 
 					Messagebox.OK, Messagebox.ERROR);
			return;
		}
		if (thereAreInformation())
			return;
		if ((numberControlRandom+getNumberControl()+numberControlProvider) == 0)
			{Messagebox.show(pro.getKey(LBL_STUDIES_FILE_EXP_NUM_CON_ERR), 
 					pro.getKey(LBL_GENERIC_MESS_ERROR), 
 					Messagebox.OK, Messagebox.ERROR);
			return;
			}else {
				
				FileCSVBean fileCSVBean = new FileControlBean();
				for (String namePlate : listStr){
					fileCSVBean.getListRows().addAll(serviceControlFile.getControlSamples(pro, plateList.getPlatesContents().get(0), mapCellSample, namePlate));
				}
				fileCSVBean.setListHeaders(serviceControlFile.getHeader(pro));
				SessionReport sessionReport = new SessionReport();
				sessionReport.setB(new byte[0]);
				sessionReport.setType(ConstantsDNA.FILE_TYPE_CSV);
				sessionReport.setName(bean.getTitle());
				sessionReport.setFileCSVBean(fileCSVBean);

				RedirectServletReport.export(sessionReport);			}
	}
	/**
	 * Method to save samples in the Data Base
	 */
	@SuppressWarnings("unchecked")
	public void saveSamples(){
		bean.setPlatetype(getPlateType());
		bean.setPlatesize(idSPS.getValue());
		bean.setNumofplates(idSNP.getValue());
		bean.setNumcontrols(numberControlRandom+getNumberControl()+numberControlProvider);
		bean.setLoadType(new LoadType( (Integer)(idCBLoadWay.getSelectedItem().getValue()))) ;
		bean.setPrefix(bean.getProject().getProjectname()+bean.getProject().getPurposename());
		if (bean.getStatus() == null)
			bean.setStatus(CommonUtils.getStatusNew());
		try {
			@SuppressWarnings("rawtypes")
			Map mapDelete = null;
			if (getDesktop().getAttribute(ATTRIBUTE_MAP_SAMPLE_DELETE)!= null)
				mapDelete = (Map <Integer , SampleDetail>)getDesktop().getAttribute(ATTRIBUTE_MAP_SAMPLE_DELETE);
			Map<Integer, SampleDetail> mapSampleDetResultEdit = null;
			if (getDesktop().getAttribute(ATTRIBUTE_MAP_SAMPLE_EDIT)!= null)
				mapSampleDetResultEdit = (Map <Integer , SampleDetail>)getDesktop().getAttribute(ATTRIBUTE_MAP_SAMPLE_EDIT);
			List<TemporalSample> listTempsample = null;
			if (getDesktop().getAttribute(ATTRIBUTE_LIST_TEMP_SAMPLE) != null )
			listTempsample = (List<TemporalSample>)getDesktop().getAttribute(ATTRIBUTE_LIST_TEMP_SAMPLE);
			
		serviceLabStudy.addLabStudy(bean, isEdit,mapDelete, mapSampleDetResultEdit,listTempsample);
		Integer idSampleLast = serviceSample.getLastSampleIDFromProject(bean.getProject().getProjectid());
		Project projectToUpdate = bean.getProject();
		projectToUpdate.setLastsampleid(idSampleLast);
		serviceProject.saveOrUpdateProject(new ProjectBean(projectToUpdate));
		closeWindow();
		Messagebox.show(pro.getKey(LBL_STUDIES_SAVE_SUCCESS), 
					pro.getKey(LBL_GENERIC_MESS_INFORMATION), 
					Messagebox.OK, Messagebox.INFORMATION);
		}catch (Exception exG){
			exG.printStackTrace();
			logger.error(exG.getMessage());
		}
	}

	/**
	 * Method to return the number of samples that be controls  
	 */
	private int getNumberControl(){
		int totalSamplesControlCustom = 0; 
		@SuppressWarnings("rawtypes")
		Set s=mapCellSample.entrySet();
        @SuppressWarnings("rawtypes")
		Iterator it=s.iterator();
        while(it.hasNext())
        {
            @SuppressWarnings("rawtypes")
			Map.Entry m =(Map.Entry)it.next();
            Cell value=(Cell)m.getValue();
            
            if (value.getAttribute(ATTRIBUTE_SAMPLE_ITEM) != null){
            	int index = (Integer)value.getAttribute(ATTRIBUTE_SAMPLE_ITEM);
            	if (index == PlateRow.BLANK_CONTROL_NUM ){
            		totalSamplesControlCustom ++;
            	}
            }
        }
        return totalSamplesControlCustom;
	}
	/**
	 * Method to get plate type
	 * @return
	 */
	private String getPlateType(){
		int value = idComPT.getSelectedItem().getValue();
		switch (value){
			case valueRack :
				return "R";
			case valueSepTub :
				return "T";
		}
		return "";
	}
	/**
	 * Method that update the control of the plate 
	 */
	private void upDateControlPlate(ControlPlateBean controlPlateBean){
		this.numberControlRandom = this.numberControlRandom + controlPlateBean.getNumberControlRandom();
		this.numberControlProvider = this.numberControlProvider + controlPlateBean.getNumberControlProvider();
		this.numberControlBlank = this.numberControlBlank + controlPlateBean.getNumberControlBlank();
	}

	private void bubbleShort(){
		for (int i=0; i<listStr.size(); i ++){
			for (int j=1; j<(listStr.size()); j ++){
				int indexFirst = getNumber(listStr.get(j-1));
				int indexSecond =  getNumber(listStr.get(j));
				if (indexFirst > indexSecond){
				    StringBuilder  temp = new StringBuilder (listStr.get(j-1));
				    listStr.set(j-1, listStr.get(j));
				    listStr.set(j, temp.toString());
				}
			}
		}
	}
	
	private int getNumber(String cadena){
		char [] cadenaArr = cadena.toCharArray();
		int indexStart = 0;
		for (int indexEnd = cadenaArr.length-1; indexEnd> 1; indexEnd --){
			if (!String.valueOf(cadenaArr[indexEnd]).matches("\\d*")){
				indexStart = indexEnd;
				break;
			}
		}
		int numberPlate = Integer.parseInt(cadena.substring(indexStart+1, cadena.length()));
		return numberPlate;
		
	}
}