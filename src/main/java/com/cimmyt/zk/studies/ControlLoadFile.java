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

import static com.cimmyt.utils.Constants.ATTRIBUTE_EDIT_STUDIES;
import static com.cimmyt.utils.Constants.ATTRIBUTE_LABSTUDY_ITEM;
import static com.cimmyt.utils.Constants.ATTRIBUTE_MAP_CELL_SAMPLE;
import static com.cimmyt.utils.Constants.ATTRIBUTE_MAP_NUM_ITEM_SELECT;
import static com.cimmyt.utils.Constants.ATTRIBUTE_MAP_SAMPLE_DELETE;
import static com.cimmyt.utils.Constants.ATTRIBUTE_MAP_SAMPLE_EDIT;
import static com.cimmyt.utils.Constants.ATTRIBUTE_LIST_TEMP_SAMPLE;
import static com.cimmyt.utils.Constants.ATTRIBUTE_SAMPLE_REPEAT;
import static com.cimmyt.utils.Constants.ATTRIBUTE_TYPE_FILE_CONTROL;
import static com.cimmyt.utils.Constants.ATTRIBUTE_TYPE_LOAD_FILE;
import static com.cimmyt.utils.Constants.ATTRIBUTE_TYPE_SAMPLE_MIXTURE;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_ERROR;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_INFORMATION;
import static com.cimmyt.utils.Constants.LBL_STUDIES_CONTROL_DART;
import static com.cimmyt.utils.Constants.LBL_STUDIES_CONTROL_KBIOSCIENCES;
import static com.cimmyt.utils.Constants.LBL_STUDIES_FILE_UPLOAD_ERROR_GREATER_SAMPLE;
import static com.cimmyt.utils.Constants.LBL_STUDIES_FILE_UPLOAD_ERROR_LESS_SAMPLE;
import static com.cimmyt.utils.Constants.LBL_STUDIES_FILE_UPLOAD_ERROR_TITLE_WINDOW;
import static com.cimmyt.utils.Constants.LBL_STUDIES_FILE_UPLOAD_ER_TYPE;
import static com.cimmyt.utils.Constants.LBL_STUDIES_FILE_UPLOAD_FILE_CORRECT;
import static com.cimmyt.utils.Constants.LBL_STUDIES_FILE_UPLOAD_FILE_STRUCTURE;
import static com.cimmyt.utils.Constants.LBL_STUDIES_FILE_UPLOAD_SUCCESSFULL;
import static com.cimmyt.utils.Constants.LBL_STUDIES_FILE_UPLOAD_TEXT_SUCCESSFULL;
import static com.cimmyt.utils.Constants.LBL_STUDIES_FILE_UP_MEN_ERR_NOT_INF;
import static com.cimmyt.utils.Constants.LBL_STUDIES_FILE_VALIDATE_CONTROL_NAME_FOUND;
import static com.cimmyt.utils.Constants.LBL_STUDIES_FILE_VALIDATE_CONTROL_ROW_FOUND;
import static com.cimmyt.utils.Constants.LBL_STUDIES_FILE_VALIDATE_GID_INCORRECT;
import static com.cimmyt.utils.Constants.LBL_STUDIES_FILE_VALIDATE_GID_MISSING;
import static com.cimmyt.utils.Constants.LBL_STUDIES_FILE_VALIDATE_LOCATION_FOUND;
import static com.cimmyt.utils.Constants.LBL_STUDIES_FILE_VALIDATE_LOCATION_MISSING;
import static com.cimmyt.utils.Constants.LBL_STUDIES_FILE_VALIDATE_NPLANT_INCORRECT;
import static com.cimmyt.utils.Constants.LBL_STUDIES_FILE_VALIDATE_PLATE_NAME_FOUND;
import static com.cimmyt.utils.Constants.LBL_STUDIES_FILE_VALIDATE_SEASON_FOUND;
import static com.cimmyt.utils.Constants.LBL_STUDIES_FILE_VALIDATE_SEASON_MISSING;
import static com.cimmyt.utils.Constants.LBL_STUDIES_PLATE_ITEM_CONTROL;
import static com.cimmyt.utils.Constants.LBL_STUDIES_RANDOM_TUBE;
import static com.cimmyt.utils.Constants.LOAD_STUDY_SERVICE_BEAN_NAME;
import static com.cimmyt.utils.Constants.LOCALE_LANGUAGE;
import static com.cimmyt.utils.Constants.LOCATION_SERVICE_BEAN_NAME;
import static com.cimmyt.utils.Constants.MANAGER_FILE_CSV_SERVICE_BEAN_NAME;
import static com.cimmyt.utils.Constants.PROJECT_SERVICE_BEAN_NAME;
import static com.cimmyt.utils.Constants.SAMPLE_SERVICE_BEAN_NAME;
import static com.cimmyt.utils.Constants.SEASON_SERVICE_BEAN_NAME;
import static com.cimmyt.utils.ConstantsDNA.ACC_POSITION_FC;
import static com.cimmyt.utils.ConstantsDNA.ATTRIBUTE_SAMPLE_TEMPORAL;
import static com.cimmyt.utils.ConstantsDNA.COLUMN_POSITION_ACC;
import static com.cimmyt.utils.ConstantsDNA.COLUMN_POSITION_COMMENT;
import static com.cimmyt.utils.ConstantsDNA.COLUMN_POSITION_ENTRY_NUMBER;
import static com.cimmyt.utils.ConstantsDNA.COLUMN_POSITION_FC;
import static com.cimmyt.utils.ConstantsDNA.COLUMN_POSITION_GID;
import static com.cimmyt.utils.ConstantsDNA.COLUMN_POSITION_LOCATION;
import static com.cimmyt.utils.ConstantsDNA.COLUMN_POSITION_PLANT_NUMBER;
import static com.cimmyt.utils.ConstantsDNA.COLUMN_POSITION_SEASON;
import static com.cimmyt.utils.ConstantsDNA.COLUMN_POSITION_SPECIE;
import static com.cimmyt.utils.ConstantsDNA.COMMENTS_POSITION_FC;
import static com.cimmyt.utils.ConstantsDNA.CONTROL_TYPE_POSITION_FC;
import static com.cimmyt.utils.ConstantsDNA.ENTRY_NO_POSITION_FC;
import static com.cimmyt.utils.ConstantsDNA.FILE_SIZE_COLUMN_MIN;
import static com.cimmyt.utils.ConstantsDNA.FILE_UP_LOAD_ONLY_SAMPLES;
import static com.cimmyt.utils.ConstantsDNA.FILE_UP_LOAD_SAMPLES_CONTROL;
import static com.cimmyt.utils.ConstantsDNA.GID_POSITION_FC;
import static com.cimmyt.utils.ConstantsDNA.LOCATION_NO_POSITION_FC;
import static com.cimmyt.utils.ConstantsDNA.NUMBER_BY_DEFAULT_PLANT_NUMBER;
import static com.cimmyt.utils.ConstantsDNA.NUMBER_LOCATION_TO_MIXTURE;
import static com.cimmyt.utils.ConstantsDNA.NUMBER_SEASON_TO_MIXTURE;
import static com.cimmyt.utils.ConstantsDNA.PLANT_NO_POSITION_FC;
import static com.cimmyt.utils.ConstantsDNA.PLATE_NAME_POSITION_FC;
import static com.cimmyt.utils.ConstantsDNA.ROW_POSITION_FC;
import static com.cimmyt.utils.ConstantsDNA.SEASON_NO_POSITION_FC;
import static com.cimmyt.utils.ConstantsDNA.SPECIE_POSITION_FC;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zkex.zul.Fisheye;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.cimmyt.bean.FileSampleCSVBean;
import com.cimmyt.bean.ItemSampleBean;
import com.cimmyt.bean.ListErrorLoadFileBean;
import com.cimmyt.bean.LocationBean;
import com.cimmyt.bean.ProjectBean;
import com.cimmyt.bean.SeasonBean;
import com.cimmyt.csv.FileManagerCSV;
import com.cimmyt.loadStudy.ServiceLoadStudy;
import com.cimmyt.model.bean.LabStudy;
import com.cimmyt.model.bean.SampleDetail;
import com.cimmyt.model.bean.TemporalSample;
import com.cimmyt.service.ServiceLocation;
import com.cimmyt.service.ServiceProject;
import com.cimmyt.service.ServiceSample;
import com.cimmyt.service.ServiceSeason;
import com.cimmyt.study.PlateContentList;
import com.cimmyt.study.PlateRow;
import com.cimmyt.utils.PropertyHelper;
import com.cimmyt.utils.StrUtils;

/**
 * @author CIMMYT
 * Class for validate business rule to load file of samples  
 */
@SuppressWarnings("serial")
public class ControlLoadFile extends Window{

	private static FileManagerCSV serviceManagerFileCSV;
	private static ServiceLocation serviceLocation = null;
	private Window idWindow;
	private Textbox idTextError;
	private Fisheye idLoadFile;
	private PropertyHelper pro=null;
	private LabStudy beanLabStudy;
	private static ServiceSeason serviceSeason = null;
	private ListErrorLoadFileBean listErrorLoadFileBean = new ListErrorLoadFileBean();
	private Map <String, ItemSampleBean> mapItemSampleBean = new HashMap <String, ItemSampleBean>();
	private Map <String, Integer> mapItemSampleDetail = new HashMap <String, Integer>();
	private Map<String, Object> mapCellSample = new HashMap<String, Object>();
	private Map<String, Integer> mapSizePlate = new HashMap<String, Integer>();
	private Map<String, String> mapNamePlate = new HashMap<String, String>();
	private List<TemporalSample> listTempsample = new ArrayList<TemporalSample>();
	private boolean thereAreAnySampleRepeat = false;
	private int typeLoad;
	private boolean isSampleMixture;
	private static ServiceProject serviceProject = null;
	private static ServiceSample serviceSample = null;
	private static ServiceLoadStudy serviceLoadStudy = null;
	private boolean isStudyEdit = false;
	private List<String> listValueRow = new ArrayList<String>();
	private int columSize = 0;
	//variable to specific the type of file to up load
	private int typeFile = 1;
	//copy of study original
	//private LabStudy beanOriginal;
	// variable to samples delete
	private Map <Integer , SampleDetail> mapSamplesDelete = new HashMap<Integer, SampleDetail>();
	//variable to management of the samples will be edit 
	private Map <Integer , SampleDetail> mapSamplesEdit = new HashMap<Integer, SampleDetail>();
	// variable to set logger 
	private Logger logger= Logger.getLogger(ControlLoadFile.class);
	private Button idButtonLFF;
	private Button idButtonLFS;
	static {
		if(serviceLoadStudy == null)
			try{
				serviceLoadStudy = (ServiceLoadStudy)SpringUtil.getBean(LOAD_STUDY_SERVICE_BEAN_NAME);
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	static {
		if(serviceManagerFileCSV == null)
			try{
				serviceManagerFileCSV = (FileManagerCSV)SpringUtil.getBean(MANAGER_FILE_CSV_SERVICE_BEAN_NAME);
			}catch (Exception e){
				e.printStackTrace();
			}
	}
	static {
		if(serviceSample == null)
			try{
				serviceSample = (ServiceSample)SpringUtil.getBean(SAMPLE_SERVICE_BEAN_NAME);
			}catch (Exception e){
				e.printStackTrace();
			}
	}
	static {
		if(serviceSeason == null)
			try{
				serviceSeason = (ServiceSeason)SpringUtil.getBean(SEASON_SERVICE_BEAN_NAME);
			}catch (Exception e){
				e.printStackTrace();
			}
	}
	static {
		if(serviceLocation == null)
			try{
				serviceLocation = (ServiceLocation)SpringUtil.getBean(LOCATION_SERVICE_BEAN_NAME);
				
			}catch (Exception e){
				e.printStackTrace();
			}
	}
	static {
		if(serviceProject == null)
			try{
			serviceProject = (ServiceProject)SpringUtil.getBean(PROJECT_SERVICE_BEAN_NAME);
			}catch (Exception e){
				e.printStackTrace();
			}
	}
	/** Method that load the component of web page */
	@SuppressWarnings("unchecked")
	public void loadContext(){
		pro = (PropertyHelper)getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);
		mapCellSample = (Map<String, Object>)getDesktop().getAttribute(ATTRIBUTE_MAP_CELL_SAMPLE);
		typeLoad = (Integer)getDesktop().getAttribute(ATTRIBUTE_TYPE_LOAD_FILE);
		isSampleMixture = (Boolean)getDesktop().getAttribute(ATTRIBUTE_TYPE_SAMPLE_MIXTURE);
		isStudyEdit = getDesktop().getAttribute(ATTRIBUTE_EDIT_STUDIES) != null ? true :false;
		beanLabStudy = (LabStudy)getDesktop().getAttribute(ATTRIBUTE_LABSTUDY_ITEM);
		typeFile = (Integer)getDesktop().getAttribute(ATTRIBUTE_TYPE_FILE_CONTROL );
		if (isStudyEdit)
			loadMapSamplesToEdit();
		loadComponent();
		if (getDesktop().getAttribute(ATTRIBUTE_SAMPLE_TEMPORAL ) !=null){
			idButtonLFF.setVisible(false);
			idButtonLFS.setVisible(true);
		}
	}
	/** Close Window */
	public void closeWindow(){
		idWindow.onClose();
	}
	/**Method that validate the for to load the samples and check if greater or lesser that samples select in 
	 * the window of plate */
	@SuppressWarnings("unchecked")
	public void addFile(){
		if(this.mapItemSampleBean.size() == 0){
			Messagebox.show(pro.getKey(LBL_STUDIES_FILE_UP_MEN_ERR_NOT_INF),pro.getKey(LBL_GENERIC_MESS_ERROR), 
					Messagebox.OK, Messagebox.ERROR);
			return;
		}
		serviceLoadStudy.loadStudyInGrid(mapCellSample, typeLoad, getDesktop(), mapItemSampleBean, beanLabStudy,
				isStudyEdit, mapSamplesDelete, mapSamplesEdit,listTempsample);
		getDesktop().setAttribute(ATTRIBUTE_MAP_SAMPLE_DELETE,mapSamplesDelete);
		getDesktop().setAttribute(ATTRIBUTE_MAP_SAMPLE_EDIT,mapSamplesEdit);
		getDesktop().setAttribute(ATTRIBUTE_LIST_TEMP_SAMPLE,listTempsample);
		mapSizePlate = (Map<String, Integer>)getDesktop().getAttribute(ATTRIBUTE_MAP_NUM_ITEM_SELECT);
		getDesktop().setAttribute(ATTRIBUTE_SAMPLE_REPEAT,thereAreAnySampleRepeat);
		int sizeAssignedSamples = getNumberSamplesAssigned();
		idWindow.onClose();
		if (mapItemSampleBean.size() > sizeAssignedSamples){
			Messagebox.show(pro.getKey(LBL_STUDIES_FILE_UPLOAD_ERROR_GREATER_SAMPLE), 
					pro.getKey(LBL_STUDIES_FILE_UPLOAD_ERROR_TITLE_WINDOW), 
					Messagebox.OK, Messagebox.ERROR);
		}else if(mapItemSampleBean.size() < sizeAssignedSamples){
			Messagebox.show(pro.getKey(LBL_STUDIES_FILE_UPLOAD_ERROR_LESS_SAMPLE), 
					pro.getKey(LBL_STUDIES_FILE_UPLOAD_ERROR_TITLE_WINDOW), 
					Messagebox.OK, Messagebox.ERROR);
		}
		Messagebox.show(pro.getKey(LBL_STUDIES_FILE_UPLOAD_SUCCESSFULL), 
				pro.getKey(LBL_GENERIC_MESS_INFORMATION), 
				Messagebox.OK, Messagebox.INFORMATION);
	}
	@SuppressWarnings("rawtypes")
	private int getNumberSamplesAssigned(){
		int sizeSamplesAssigned = 0;
		Set set = mapSizePlate.entrySet();
		Iterator it=set.iterator();
	      while(it.hasNext())
	      {
	          Map.Entry m =(Map.Entry)it.next();
	          Integer value=(Integer)m.getValue();
	          sizeSamplesAssigned = sizeSamplesAssigned + value.intValue();
	      }
		return sizeSamplesAssigned;
	}
    /** Up load component in the code */
	private void loadComponent(){
		idWindow = (Window)getFellow("idWindowFileUpload");
		idTextError = (Textbox)getFellow("idTextError");
		idLoadFile = (Fisheye)getFellow("idLoadFile");
		idButtonLFF = (Button)getFellow("idButtonLFF");
		idButtonLFS = (Button)getFellow("idButtonLFS");
	}

	public void upLoadFromService(){
		int lastSample = serviceProject.getLastSampleID(new ProjectBean (beanLabStudy.getProject()));
	  	  lastSample= lastSample+1;
	  	reseatValues();
	  	int numberBeginingRow = 2;
	  	Object [] obj = (Object [])getDesktop().getAttribute(ATTRIBUTE_SAMPLE_TEMPORAL);
	  	@SuppressWarnings("unchecked")
		List<TemporalSample> listTempSample = (List<TemporalSample>)obj[0];
	  	for (int sizeOfListSamples = 0 ;sizeOfListSamples < listTempSample.size();
				sizeOfListSamples ++){
			ItemSampleBean bean = new ItemSampleBean(listTempSample.get(sizeOfListSamples));
			bean.setIdOrder(sizeOfListSamples);
			lastSample = validateRepeatSamples(bean , numberBeginingRow, lastSample);
			numberBeginingRow++;
		}
	  	if (listErrorLoadFileBean.isAnyListNotEmpty()){
			printTextError();
		}else {
			idTextError.setText(idTextError.getText()+ pro.getKey(LBL_STUDIES_FILE_UPLOAD_TEXT_SUCCESSFULL));
			idLoadFile.setVisible(true);
			getDesktop().removeAttribute(ATTRIBUTE_SAMPLE_TEMPORAL);
		}
	}
	/** Method to load file CSV of the samples
	 * @param media */
	public void upLoadFile(Media media) {
		logger.info(media.getName()+" Extention ::: "+media.getFormat());
		if (media.getName().indexOf("csv") != -1){
  		    FileSampleCSVBean fileSampleCSV = serviceManagerFileCSV.getFileSampleCSV(media, typeFile, true);
  		  int lastSample = serviceProject.getLastSampleID(new ProjectBean (beanLabStudy.getProject()));
  		  	  lastSample= lastSample+1;
  		    if (fileSampleCSV == null){
  		    	Messagebox.show(pro.getKey(LBL_STUDIES_FILE_UPLOAD_FILE_CORRECT),pro.getKey(LBL_GENERIC_MESS_ERROR), 
						Messagebox.OK, Messagebox.ERROR);
  		    	return;
  		    }
  		    if (fileSampleCSV.getListHeaders().size() < FILE_SIZE_COLUMN_MIN ){
				Messagebox.show(pro.getKey(LBL_STUDIES_FILE_UPLOAD_FILE_STRUCTURE),pro.getKey(LBL_GENERIC_MESS_ERROR), 
						Messagebox.OK, Messagebox.ERROR);
				return;
			}
  		  reseatValues();
  		  logger.info("Read File by upload:::");
			final Desktop desktop = Executions.getCurrent().getDesktop();
			desktop.enableServerPush(true);
				int numberBeginingRow = 2;
				if (typeFile == FILE_UP_LOAD_SAMPLES_CONTROL ){
					loadMapNamePlate();
					createListNameRow();
				}
				for (int sizeOfListSamples = 0 ;sizeOfListSamples < fileSampleCSV.getListRowCSV().size();
						sizeOfListSamples ++){
					ItemSampleBean bean = new ItemSampleBean();
					for (int sizeOfCell = 0; 
							sizeOfCell < fileSampleCSV.getListRowCSV().get(sizeOfListSamples).getListRow().size();
							sizeOfCell ++){
						if(desktop == null || !desktop.isServerPushEnabled()){
							desktop.enableServerPush(false);
							return;
						}
						switch (typeFile){
						case FILE_UP_LOAD_ONLY_SAMPLES :
							bean = setListSampleBean(bean,sizeOfCell,fileSampleCSV.getListRowCSV().get(sizeOfListSamples).
									getListRow().get(sizeOfCell),numberBeginingRow );	
							break;
						case FILE_UP_LOAD_SAMPLES_CONTROL :
							bean = setListSampleBeanFileControl(bean,sizeOfCell,fileSampleCSV.getListRowCSV().get(sizeOfListSamples).
									getListRow().get(sizeOfCell),numberBeginingRow);
							break;
						}
					}
					bean.setIdOrder(sizeOfListSamples);
					lastSample = validateRepeatSamples(bean , numberBeginingRow, lastSample);
					numberBeginingRow++;
				}
				if (listErrorLoadFileBean.isAnyListNotEmpty()){
					printTextError();
				}else {
					idTextError.setText(idTextError.getText()+ pro.getKey(LBL_STUDIES_FILE_UPLOAD_TEXT_SUCCESSFULL));
					idLoadFile.setVisible(true);
				}
			}else{
			Messagebox.show(pro.getKey(LBL_STUDIES_FILE_UPLOAD_ER_TYPE),pro.getKey(LBL_GENERIC_MESS_ERROR), 
					Messagebox.OK, Messagebox.ERROR);
			return;
		}
	}
	/**
	 * Print errors of the load the file samples and controls in text of output 
	 */
	private void printTextError (){
		idTextError.setText("");
		String strOfError = "";
		if(listErrorLoadFileBean.getListGidErrorEmpty().size() > 0)
			strOfError = pro.getKey(LBL_STUDIES_FILE_VALIDATE_GID_MISSING, new String[]{listErrorLoadFileBean.getListGidErrorEmpty().size()+"\n"});
		if (listErrorLoadFileBean.getListGidErrirInteger().size() > 0)
			strOfError = strOfError + pro.getKey(LBL_STUDIES_FILE_VALIDATE_GID_INCORRECT, new String[]{listErrorLoadFileBean.getListGidErrirInteger().size()+"\n"});
		if (listErrorLoadFileBean.getListPlantInteger().size() > 0)
			strOfError = strOfError + pro.getKey(LBL_STUDIES_FILE_VALIDATE_NPLANT_INCORRECT, new String[] {listErrorLoadFileBean.getListPlantInteger().size()+"\n"});
		if (listErrorLoadFileBean.getListLocationErrorEmpty().size() > 0)
			strOfError = strOfError + pro.getKey(LBL_STUDIES_FILE_VALIDATE_LOCATION_MISSING, new String[]{listErrorLoadFileBean.getListLocationErrorEmpty().size()+"\n"});
		if (listErrorLoadFileBean.getListLocationErrorRegistered().size() > 0)
			strOfError = setListErrorToList(listErrorLoadFileBean.getListLocationErrorRegistered(), 
					pro.getKey(LBL_STUDIES_FILE_VALIDATE_LOCATION_FOUND), strOfError);
		if (listErrorLoadFileBean.getListSeasonErrorEmpty().size() > 0)
			strOfError = strOfError + pro.getKey(LBL_STUDIES_FILE_VALIDATE_SEASON_MISSING, new String[]{listErrorLoadFileBean.getListSeasonErrorEmpty().size()+"\n"});
		if (listErrorLoadFileBean.getListSeasonErrorRegistered().size() > 0)
			strOfError = setListErrorToList(listErrorLoadFileBean.getListSeasonErrorRegistered(), 
					pro.getKey(LBL_STUDIES_FILE_VALIDATE_SEASON_FOUND), strOfError);
		if (listErrorLoadFileBean.getListControlTypeError().size() > 0)
			strOfError = setListErrorToList(listErrorLoadFileBean.getListControlTypeError(), 
					pro.getKey(LBL_STUDIES_FILE_VALIDATE_CONTROL_NAME_FOUND), strOfError);
		if (listErrorLoadFileBean.getListPlateNameEmpty().size() > 0)
			strOfError = setListErrorToList(listErrorLoadFileBean.getListPlateNameEmpty(), 
					pro.getKey(LBL_STUDIES_FILE_VALIDATE_PLATE_NAME_FOUND), strOfError);
		if (listErrorLoadFileBean.getListRowEmpty().size() > 0)
			strOfError = setListErrorToList(listErrorLoadFileBean.getListRowEmpty(), 
					pro.getKey(LBL_STUDIES_FILE_VALIDATE_CONTROL_ROW_FOUND), strOfError);
		idTextError.setText(idTextError.getText()+ strOfError);
		reseatValues();
		idLoadFile.setVisible(false);
	}
	/**
	 * Method to create list of error in string
	 * @param listStr
	 * @param title
	 * @param strOfError
	 */
	private String setListErrorToList (List<String> listStr , String title, String strOfError){
		strOfError = strOfError + title;
		String list = "";
		for (int size = 0; 
				size < listStr.size()
				; size++ ){
			if( (size+1) >= listStr.size()){
				list = list + listStr.get(size) + "\n";
			}else {
				list = list + listStr.get(size) + ", ";
			}
		}
		strOfError = strOfError+ list;
		return strOfError;
	}
	/**
	 * reset the values of principal objects to management the maps, list errors and samples repeat 
	 */
	private void reseatValues (){
		listErrorLoadFileBean =  new ListErrorLoadFileBean(); 
		mapItemSampleBean = new HashMap <String, ItemSampleBean>();
		thereAreAnySampleRepeat = false;
		mapSamplesDelete = new HashMap<Integer, SampleDetail>();
	}
	/**
	 * Method that create map and specific if exist samples repeat
	 * @param bean
	 * @param numRow
	 * @param sampleID
	 * @return Sample Id
	 */
	private int validateRepeatSamples(ItemSampleBean bean, int numRow , int sampleID){
		if ((bean.getLocationidBean() == null || bean.getLocationidBean().getLocationid() == null)
				|| (bean.getSeasonidBean() == null || bean.getSeasonidBean().getSeasonid() == null)){
			return 0;
		}
			String keyDo = getKeyForSample(bean, this.beanLabStudy);
			if (isStudyEdit){
				if (mapItemSampleDetail.containsKey(keyDo)){
					Integer sampleIDInt = mapItemSampleDetail.get(keyDo);
					bean.setSampleID(sampleIDInt);
					
					if (mapItemSampleBean.containsKey(keyDo)){
						bean.setRepeatSample(true);
						bean.setSampleID(mapItemSampleBean.get(keyDo).getSampleID());
						mapItemSampleBean.get(keyDo).setRepeatSample(true);
						mapItemSampleBean.put(keyDo+"|"+numRow,bean );
						thereAreAnySampleRepeat = true;
						}else {
							mapItemSampleBean.put(keyDo,bean );
						}
				}else {
					if (mapItemSampleBean.containsKey(keyDo)){
						bean.setRepeatSample(true);
						bean.setSampleID(mapItemSampleBean.get(keyDo).getSampleID());
						mapItemSampleBean.get(keyDo).setRepeatSample(true);
						mapItemSampleBean.put(keyDo+"|"+numRow,bean );
						thereAreAnySampleRepeat = true;
					}
					else{
						Integer sampleIDSearch = serviceSample.searchSampleIDProjectInSt_SampleID(
								beanLabStudy.getProject().getProjectid(), bean.getGid(), bean.getPlantNo(),
								bean.getLocationidBean().getLocationid(), bean.getSeasonidBean().getSeasonid());
						if (sampleIDSearch != null && sampleIDSearch.intValue() > 0){
							bean.setSampleID(sampleIDSearch);
							bean.setRepeatSample(true);
							thereAreAnySampleRepeat = true;
						}else{
						bean.setSampleID(sampleID);
						bean.setRepeatSample(false);
						++sampleID;
						}
						mapItemSampleBean.put(keyDo,bean );
					}	
				}
			}else {
				if (mapItemSampleBean.containsKey(keyDo)){
					bean.setRepeatSample(true);
					bean.setSampleID(mapItemSampleBean.get(keyDo).getSampleID());
					mapItemSampleBean.get(keyDo).setRepeatSample(true);
					mapItemSampleBean.put(keyDo+"|"+numRow,bean );
					thereAreAnySampleRepeat = true;
				}
				else{
					Integer sampleIDSearch = serviceSample.searchSampleIDProjectInSt_SampleID(
							beanLabStudy.getProject().getProjectid(), bean.getGid(), bean.getPlantNo(),
							bean.getLocationidBean().getLocationid(), bean.getSeasonidBean().getSeasonid());
					if (sampleIDSearch != null && sampleIDSearch.intValue() > 0){
						bean.setSampleID(sampleIDSearch);
						bean.setRepeatSample(true);
						thereAreAnySampleRepeat = true;
					}else{
					bean.setSampleID(sampleID);
					bean.setRepeatSample(false);
					++sampleID;
					}
					mapItemSampleBean.put(keyDo,bean );
				}	
			}
			
			return sampleID;
	}
	/**
	 * Method that set all information about of Sample Bean with control
	 * @param bean
	 * @param col
	 * @param strBuilder
	 * @param numberBeginingRow
	 * @return
	 */
	private ItemSampleBean setListSampleBeanFileControl(ItemSampleBean bean, int col, StringBuilder strBuilder, int numberBeginingRow){
		switch (col){
		case CONTROL_TYPE_POSITION_FC:
			if (!validateNameOfControl(strBuilder.toString()))
				listErrorLoadFileBean.getListControlTypeError().add(strBuilder.toString());
			else  
				bean.setControlType(strBuilder);
			break;
		case PLATE_NAME_POSITION_FC :
			if (strBuilder == null || strBuilder.toString().trim().equals(""))
				listErrorLoadFileBean.getListPlateNameEmpty().add(String.valueOf(numberBeginingRow));
			else 
				if (mapNamePlate.containsKey(strBuilder.toString()))
				bean.setPlateName(strBuilder);
				else 
					listErrorLoadFileBean.getListPlateNameEmpty().add(String.valueOf(numberBeginingRow));
			break;
		case ROW_POSITION_FC : 
			if (StrUtils.isEmpty(strBuilder))
				listErrorLoadFileBean.getListRowEmpty().add(String.valueOf(numberBeginingRow));
			else if (!listValueRow.contains(strBuilder.toString()))
				listErrorLoadFileBean.getListRowEmpty().add(String.valueOf(numberBeginingRow));
			else 
				bean.setRow(strBuilder);
			break;
		case COLUMN_POSITION_FC :
			if (StrUtils.isEmpty(strBuilder))
				listErrorLoadFileBean.getListColumnEmptyOrInvalid().add(String.valueOf(numberBeginingRow));
			else if (!StrUtils.isNumeric(strBuilder))
				listErrorLoadFileBean.getListColumnEmptyOrInvalid().add(String.valueOf(numberBeginingRow));
			else 
				bean.setColumn(Integer.parseInt(strBuilder.toString()));
			break;
		case GID_POSITION_FC :
				setGID(numberBeginingRow, strBuilder, bean);
				break;
		case ACC_POSITION_FC :
				setAcc(strBuilder, bean);
				break;
		case PLANT_NO_POSITION_FC :
				setPlantNumber(strBuilder, bean);
				break;
		case SPECIE_POSITION_FC :
				setSpecie(strBuilder, bean);
				break;
		case COMMENTS_POSITION_FC :
				setComment(strBuilder, bean);
				break;
		case ENTRY_NO_POSITION_FC :
				setPositionEntryNumber(strBuilder, bean);
				break;
		case LOCATION_NO_POSITION_FC :
				setLocationItemSampleBean(numberBeginingRow, strBuilder, bean);
				break;
		case SEASON_NO_POSITION_FC :
				setSeasonInItemSampleBean(numberBeginingRow, strBuilder, bean);
				break ;
			default :
					break;
		}
		return bean;
	}
	/** Method that create list of Row Name */
	private void createListNameRow(){
		listValueRow = new ArrayList<String>();
		if (beanLabStudy.getPlatesize() == PlateContentList.PLATE_SIZE_96 ){
			for (String strRow : PlateContentList.letters96){
				listValueRow.add(strRow);
			}
			columSize = PlateRow.COLS_PLATE_96;
		}
		else if (beanLabStudy.getPlatesize() == PlateContentList.PLATE_SIZE_384 ){
			for (String strRow : PlateContentList.letters384){
				listValueRow.add(strRow);
			}
			columSize = PlateRow.COLS_PLATE_384;
		}
	}
	/**
	 * Method that validate if the control exist
	 * @param nameControl
	 * @return
	 */
	private boolean validateNameOfControl(String nameControl){
		if (nameControl.equals(pro.getKey(LBL_STUDIES_RANDOM_TUBE)) || nameControl.equals(pro.getKey(LBL_STUDIES_PLATE_ITEM_CONTROL))
				|| nameControl.equals(pro.getKey(LBL_STUDIES_CONTROL_KBIOSCIENCES)) || nameControl.equals(pro.getKey(LBL_STUDIES_CONTROL_DART)))
			return true;
		return false;
	}
	/**
	 * Method that load all attributes of sample to load
	 * @param bean
	 * @param col
	 * @param strBuilder
	 * @param numberBeginingRow
	 * @return
	 */
	private ItemSampleBean setListSampleBean(ItemSampleBean bean, int col, StringBuilder strBuilder, int numberBeginingRow){
		switch (col){
			case COLUMN_POSITION_GID :
				setGID(numberBeginingRow, strBuilder, bean);
				break;
			case COLUMN_POSITION_ACC :
				setAcc(strBuilder, bean);
				break;
			case COLUMN_POSITION_PLANT_NUMBER :
				setPlantNumber(strBuilder, bean);
				break;
			case COLUMN_POSITION_SPECIE :
				setSpecie(strBuilder, bean);
				break;
			case COLUMN_POSITION_COMMENT :
				setComment(strBuilder, bean);
				break;
			case COLUMN_POSITION_ENTRY_NUMBER :
				setPositionEntryNumber(strBuilder, bean);
				break;
			case COLUMN_POSITION_LOCATION :
				setLocationItemSampleBean(numberBeginingRow, strBuilder, bean);
				break;
			case COLUMN_POSITION_SEASON :
				setSeasonInItemSampleBean(numberBeginingRow, strBuilder, bean);
				break ;
			default :
					break;
		}
		return bean;
	}
	private void setGID (int numberBeginingRow,StringBuilder strBuilder, ItemSampleBean  bean){
		if (StrUtils.isEmpty(strBuilder))
			listErrorLoadFileBean.getListGidErrorEmpty().add(numberBeginingRow);
		else if (!StrUtils.isNumeric(strBuilder))
					listErrorLoadFileBean.getListGidErrirInteger().add(numberBeginingRow);
		else 
			bean.setGid(Integer.parseInt(strBuilder.toString()));
	}
	/**Method that set Acc to Sample Bean
	 * @param strBuilder
	 * @param bean */
	private void setAcc(StringBuilder strBuilder, ItemSampleBean  bean){
		if (strBuilder != null && !strBuilder.toString().equals(""))
			bean.setAcc(strBuilder);
	}
	/**Method that set Plant number to Sample Bean
	 * @param strBuilder
	 * @param bean */
	private void setPlantNumber (StringBuilder strBuilder, ItemSampleBean  bean){
		if(StrUtils.isNotEmpty(strBuilder))
			if (!StrUtils.isNumericIntegerPositive(strBuilder))
				listErrorLoadFileBean.getListPlantInteger().add(strBuilder.toString());
			else 
				bean.setPlantNo(Integer.parseInt(strBuilder.toString()));
		else 
			bean.setPlantNo(NUMBER_BY_DEFAULT_PLANT_NUMBER);
	}
	/**Method that set species to sample Bean 
	 * @param strBuilder
	 * @param bean*/
	private void setSpecie(StringBuilder strBuilder, ItemSampleBean  bean){
		if (strBuilder != null && !strBuilder.toString().equals(""))
			bean.setSpecie(strBuilder);
		else
			bean.setSpecie(new StringBuilder(beanLabStudy.getOrganismid().getOrganismname()));
	}
	/** Method that set comment in the sample Bean
	 * @param strBuilder
	 * @param bean*/
	private void setComment(StringBuilder strBuilder, ItemSampleBean  bean){
		if (strBuilder != null && !strBuilder.toString().equals(""))
			bean.setComment(strBuilder);
	}
	/**Set position in the sample Bean 
	 * @param strBuilder
	 * @param bean*/
	private void setPositionEntryNumber(StringBuilder strBuilder, ItemSampleBean  bean){
		if (strBuilder != null && !strBuilder.toString().equals(""))
			if (StrUtils.isNumericIntegerPositive(strBuilder))
				bean.setEntryNo(Integer.parseInt(strBuilder.toString()));
	}
	/**
	 * Method that set Location in Item Sample Bean
	 * @param numberBeginingRow
	 * @param strBuilder
	 * @param bean
	 */
	private void setLocationItemSampleBean  (int numberBeginingRow, StringBuilder strBuilder, ItemSampleBean  bean){
		LocationBean locationBean = new LocationBean();
		if(this.beanLabStudy.getLocation().getLocationid() ==  NUMBER_LOCATION_TO_MIXTURE){
			if(StrUtils.isEmpty(strBuilder)){
				listErrorLoadFileBean.getListLocationErrorEmpty().add(numberBeginingRow);
			}else{
				if (!listErrorLoadFileBean.getListLocationErrorRegistered().contains(strBuilder.toString()))
					{
					locationBean = serviceLocation.getLocationCatalogRegistred(strBuilder.toString());
						if (locationBean == null ){
							listErrorLoadFileBean.getListLocationErrorRegistered().add(strBuilder.toString());
						}else {
							bean.setLocationidBean(locationBean);
						}
					}
			}
		}else {
			locationBean.setLocationid(this.beanLabStudy.getLocation().getLocationid());
			locationBean.setLocation_name(this.beanLabStudy.getLocation().getLocation_name());
			bean.setLocationidBean(locationBean);
		}
	}
	/** Method that set in the Item sample bean season
	 * @param numberBeginingRow
	 * @param strBuilder
	 * @param bean
	 */
	private void setSeasonInItemSampleBean(int numberBeginingRow, StringBuilder strBuilder, ItemSampleBean  bean){
		SeasonBean seasonBean = new SeasonBean();
		if(this.beanLabStudy.getSeason().getSeasonid() ==  NUMBER_SEASON_TO_MIXTURE){
			if(StrUtils.isEmpty(strBuilder)){
				listErrorLoadFileBean.getListSeasonErrorEmpty().add(numberBeginingRow);
			}else {
				if(!listErrorLoadFileBean.getListSeasonErrorRegistered().contains(strBuilder.toString())){
					seasonBean = serviceSeason.getSeasonBeanByName(strBuilder.toString());
					if (seasonBean == null ){
						listErrorLoadFileBean.getListSeasonErrorRegistered().add(strBuilder.toString());
					}else {
						bean.setSeasonidBean(seasonBean);
					}
				}
			}
		}else {
			seasonBean.setSeasonid(this.beanLabStudy.getSeason().getSeasonid());
			seasonBean.setSeason_name(this.beanLabStudy.getSeason().getSeason_name());
			bean.setSeasonidBean(seasonBean);
		}
	}
	/**
	 * Method that create a key to put in the map like identifier of sample
	 * @param bean
	 * @param beanLabStudy
	 * @return
	 */
	private String getKeyForSample(ItemSampleBean bean, LabStudy beanLabStudy){
		StringBuilder strBuilderKey = new StringBuilder();
		strBuilderKey.append(beanLabStudy.getProject().getProjectid() + "|");
		strBuilderKey.append(bean.getGid() + "|" );
		strBuilderKey.append(bean.getPlantNo() + "|");
		if (bean.getLocationidBean()!= null && bean.getLocationidBean().getLocationid() != null)
			strBuilderKey.append(bean.getLocationidBean().getLocationid() + "|");
		else 
			strBuilderKey.append(beanLabStudy.getLocation().getLocationid() + "|");
		if (bean.getSeasonidBean() != null && bean.getSeasonidBean().getSeasonid() != null)
			strBuilderKey.append(bean.getSeasonidBean().getSeasonid());
		else
			strBuilderKey.append(beanLabStudy.getSeason().getSeasonid());
		return strBuilderKey.toString();
	}
	/** Create map with samples that will be edit in the plate */
	private void loadMapSamplesToEdit(){
		for (SampleDetail detail :this.beanLabStudy.getSampleDetailCollection()){
			StringBuilder strBuilderKey = new StringBuilder();
			strBuilderKey.append(this.beanLabStudy.getProject().getProjectid() + "|");
			strBuilderKey.append(detail.getBreedergid() + "|" );
			strBuilderKey.append(detail.getNplanta() + "|");
			if (detail.getLocationid()!= null && detail.getLocationid().getLocationid() != null)
				strBuilderKey.append(detail.getLocationid().getLocationid() + "|");
			else 
				strBuilderKey.append(this.beanLabStudy.getLocation().getLocationid() + "|");
			if (detail.getSeasonid() != null && detail.getSeasonid().getSeasonid() != null)
				strBuilderKey.append(detail.getSeasonid().getSeasonid());
			else
				strBuilderKey.append(this.beanLabStudy.getSeason().getSeasonid());

			mapItemSampleDetail.put(strBuilderKey.toString(), detail.getSamplegid());
		}
	}
	/** Method to load name of plate in a map */
	private void loadMapNamePlate (){
		if (beanLabStudy.getSampleDetailCollection() != null && beanLabStudy.getSampleDetailCollection().size() > 0){
			mapNamePlate = new HashMap<String, String>();
			for (SampleDetail detail : beanLabStudy.getSampleDetailCollection()){
				mapNamePlate.put(detail.getPlatename(), detail.getPlatename());
			}
		}
	}
}





