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
package com.cimmyt.loadStudy.impl;

import static com.cimmyt.utils.Constants.ATTRIBUTE_SAMPLE_ITEM;
import static com.cimmyt.utils.Constants.ATTRIBUTE_SAMPLE_ITEM_BEAN;
import static com.cimmyt.utils.Constants.ATTRIBUTE_TYPE_FILE_CONTROL;
import static com.cimmyt.utils.Constants.ATTRIBUTE_TYPE_LOAD_PLATE;
import static com.cimmyt.utils.Constants.LBL_STUDIES_FILE_UP_CELL_LOCATION;
import static com.cimmyt.utils.Constants.LBL_STUDIES_FILE_UP_CELL_PLANT_N;
import static com.cimmyt.utils.Constants.LBL_STUDIES_FILE_UP_CELL_SEASON;
import static com.cimmyt.utils.Constants.LBL_STUDIES_FILE_UP_CELL_T_GID;
import static com.cimmyt.utils.Constants.LBL_STUDIES_FILE_UP_CELL_T_SAMP;
import static com.cimmyt.utils.Constants.LBL_STUDIES_PLATE_ITEM_BLANK;
import static com.cimmyt.utils.Constants.LBL_STUDIES_PLATE_ITEM_CONTROL;
import static com.cimmyt.utils.Constants.LOCALE_LANGUAGE;
import static com.cimmyt.utils.Constants.URL_IMAGES_ASSIGNED_TUBE;
import static com.cimmyt.utils.Constants.URL_IMAGES_BLANK_TUBE;
import static com.cimmyt.utils.Constants.URL_IMAGES_CONTROL_TUBE;
import static com.cimmyt.utils.Constants.URL_IMAGES_REPEAT_SAMPLE;
import static com.cimmyt.utils.ConstantsDNA.FILE_UP_LOAD_ONLY_SAMPLES;
import static com.cimmyt.utils.ConstantsDNA.FILE_UP_LOAD_SAMPLES_CONTROL;
import static com.cimmyt.utils.ConstantsDNA.PREFIX_IMAGE;
import static com.cimmyt.utils.ConstantsDNA.PREFIX_LABEL;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Html;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Popup;

import com.cimmyt.bean.ItemSampleBean;
import com.cimmyt.loadStudy.ServiceLoadStudy;
import com.cimmyt.model.bean.LabStudy;
import com.cimmyt.model.bean.SampleDetail;
import com.cimmyt.model.bean.TemporalSample;
import com.cimmyt.study.PlateContentList;
import com.cimmyt.study.PlateList;
import com.cimmyt.study.PlateRow;
import com.cimmyt.utils.CommonUtils;
import com.cimmyt.utils.ConstantsDNA;
import com.cimmyt.utils.PropertyHelper;
import com.cimmyt.utils.StrUtils;
/**
 * @author CIMMYT
 * @version 1.0 
 * Class to load the samples that will be read of file CVS
 */
public class ServiceLoadStudyImpl implements ServiceLoadStudy{
	//Object to management of language 
	private PropertyHelper pro=null;
	//Variable to get the context
	private Desktop desktop;
	//Map that contains the cell of grid
	private Map<String, Object> mapCellSample;
	//To this variable there is two values 0 is by columns and 1 by rows
	private int typeLoad;
	private PlateList plateList;
	//Map that content key (Name of plate and coordinates in these )
	private Map <String, ItemSampleBean> mapItemSampleBean;
	//map to management the samples by id order
	private Map <Integer, ItemSampleBean> mapItemSampleOrder = new HashMap<Integer,ItemSampleBean >();
	//map to management the samples by id order
	private Map <String, ItemSampleBean> mapItemSampleControl = new HashMap<String,ItemSampleBean >();
	// Bean that content the information about of study
	private LabStudy beanLabStudy;
	// Bean copy that content the information about of study
	//private LabStudy beanLabStudyOriginal;
	// Is study for edit
	private boolean isEdit = false;
	//number of samples load successful
	private int numberSamplesSuccessful = 0;
	//variable to load samples that are to edit
	private Map <Integer,SampleDetail> mapSamplesCollection;
	// Logger to management of error
	private Logger logger= Logger.getLogger(ServiceLoadStudyImpl.class);
	// variable to samples delete
	private Map <Integer , SampleDetail> mapSamplesDelete;
	// variable to samples delete
	private Map <Integer , SampleDetail> mapSamplesEdit;
	private List<TemporalSample> listTempsample;
	//variable to specific the type of file to up load
	private int typeFile = 1;
	private int sizeRowSamples = 0;
	/**Method that validate the load of samples in the grid by plate
	 * @param _mapCellSample map of cells
	 * @param _typeLoad type load by columns or by rows
	 * @param _desktop object that management the session to load object or download 
	 * @param _mapItemSampleBean map the samples 
	 * @param _beanLabStudy object that management the information about study */
	@Override
	public void loadStudyInGrid(Map<String, Object> _mapCellSample,
			int _typeLoad, Desktop _desktop, Map <String, ItemSampleBean> _mapItemSampleBean
			,LabStudy _beanLabStudy, boolean _isEdit,
			Map <Integer , SampleDetail> _mapSamplesDelete, Map <Integer , SampleDetail> _mapSamplesEdit,
			List<TemporalSample> _listTempsample) {

		desktop = _desktop;
		pro = (PropertyHelper)desktop.getSession().getAttribute(LOCALE_LANGUAGE);
		mapCellSample = _mapCellSample;
		typeLoad = _typeLoad;		
		plateList =  (PlateList)desktop.getAttribute(ATTRIBUTE_TYPE_LOAD_PLATE);
		mapItemSampleBean = _mapItemSampleBean;
		beanLabStudy = _beanLabStudy;
		numberSamplesSuccessful = 0;
		isEdit = _isEdit;
		mapItemSampleOrder = new HashMap<Integer,ItemSampleBean >();
		mapItemSampleControl = new HashMap<String,ItemSampleBean >();
		this.mapSamplesDelete = _mapSamplesDelete;
		//this.mapSamplesDelete = new HashMap<Integer, SampleDetail>();
		this.mapSamplesEdit = _mapSamplesEdit;
		this.listTempsample = _listTempsample;
		//this.mapSamplesEdit = new HashMap<Integer, SampleDetail>();
		typeFile = (Integer)desktop.getAttribute(ATTRIBUTE_TYPE_FILE_CONTROL );
		switch (typeFile){
		case FILE_UP_LOAD_ONLY_SAMPLES :
			putMapInOrderByID();
			if (isEdit)
				loadMapSamplesToReadLoad();
			switch (typeLoad){
			case ConstantsDNA.FILE_UP_LOAD_SAMPLES_BY_COLUMNS :
				loadByColumns();
				break;
			case ConstantsDNA.FILE_UP_LOAD_SAMPLES_BY_ROW : 
				loadByRows();
				break;
			case ConstantsDNA.FILE_UP_LOAD_SAMPLES_BY_ZIGZAG:
				loadSampleZigZaging();
				break;
			}
			break;
		case FILE_UP_LOAD_SAMPLES_CONTROL :
			putMapSamplesControl();
			if (isEdit)
				loadMapSamplesToReadLoad();
			loadSamplesByControl();
			break;
		}
		beanLabStudy.setNumindiv(numberSamplesSuccessful);
	}
	/** Method that up load the samples of control put an identifier by sample*/
	private void loadSamplesByControl(){
		logger.info("Init load of samples from de file of controls");
		for (PlateContentList plateContent : plateList.getPlatesContents()){
			if (plateContent != null && plateContent.getPlateName()!= null)
			for (Integer sizeRow = 0 ; sizeRow < plateContent.getPlateRows().get(0).getValuesInt().length; sizeRow++){
				for (int sizeLetter = 0 ; sizeLetter <  plateContent.letters.length; sizeLetter++){
					String keyItem = plateContent.getPlateName()+"|"+plateContent.letters[sizeLetter]+(sizeRow+1);
					Cell cell = (Cell)mapCellSample.get(keyItem);
					Label label = (Label)cell.getFellow(keyItem+PREFIX_LABEL);
					Image image = (Image)cell.getFellow(keyItem+PREFIX_IMAGE);
					ItemSampleBean itemEdit = (ItemSampleBean) (cell.getAttribute(ATTRIBUTE_SAMPLE_ITEM_BEAN) != null?cell.getAttribute(ATTRIBUTE_SAMPLE_ITEM_BEAN) : null);
					if (mapItemSampleControl.get(keyItem) != null){
						ItemSampleBean item = mapItemSampleControl.get(keyItem);
						SampleDetail detail = changeValueOfItemSample(item, PlateRow.ASSIGNED_NUM, itemEdit.getStudysampleid());
						numberSamplesSuccessful ++;
						String sampleId = beanLabStudy.getProject().getProjectname()+beanLabStudy.getProject().getPurposename()
								+item.getSampleID();
						label.setValue(sampleId.toUpperCase());
						createToolTip(item, sampleId.toUpperCase(), cell);
						image.setSrc(URL_IMAGES_ASSIGNED_TUBE);
						if(item.isRepeatSample())
							image.setSrc(URL_IMAGES_REPEAT_SAMPLE);
						cell.setAttribute(ATTRIBUTE_SAMPLE_ITEM, PlateRow.ASSIGNED_NUM);
						validateSamplesToDelete(item.getSampleID(), detail, null);
					}
				}
			}
		}
	}

	private void loadSampleZigZaging(){
		logger.info("Init load of samples from de file of controls zig zaging");
		sizeRowSamples = 0;
		Collection<SampleDetail> imsSampleDetailCollection = new ArrayList<SampleDetail>();
		for (PlateContentList plateContent : plateList.getPlatesContents()){
			if (plateContent != null && plateContent.getPlateName()!= null)
			for (int sizeRow = 0 ; sizeRow <  plateContent.letters.length; ){
				for (Integer sizeColumn = 0 ; sizeColumn < plateContent.getPlateRows().get(0).getValuesInt().length; sizeColumn++){
					String keyItem = plateContent.getPlateName()+"|"+plateContent.letters[sizeRow]+(sizeColumn+1);
					callSetSampleInPlateGrid(keyItem, sizeColumn, plateContent, imsSampleDetailCollection, sizeRow);
					if (sizeRow+1 < plateContent.letters.length ){
					keyItem = plateContent.getPlateName()+"|"+plateContent.letters[sizeRow+1]+(sizeColumn+1);
					callSetSampleInPlateGrid(keyItem, sizeColumn, plateContent, imsSampleDetailCollection, sizeRow+1);
					}
				}
				sizeRow =sizeRow +2;
			}
		}
		beanLabStudy.setImsSampleDetailCollection(imsSampleDetailCollection);
	}
	/** Method that load the samples by Columns*/
	private void loadByColumns(){
		sizeRowSamples = 0;
		logger.info("Init load of samples from de file by columns");
		Collection<SampleDetail> imsSampleDetailCollection = new ArrayList<SampleDetail>();
		for (PlateContentList plateContent : plateList.getPlatesContents()){
			if (plateContent != null && plateContent.getPlateName()!= null)
			for (Integer sizeRow = 0 ; sizeRow < plateContent.getPlateRows().get(0).getValuesInt().length; sizeRow++){
				for (int sizeLetter = 0 ; sizeLetter <  plateContent.letters.length; sizeLetter++){
					String keyItem = plateContent.getPlateName()+"|"+plateContent.letters[sizeLetter]+(sizeRow+1);
					callSetSampleInPlateGrid(keyItem, sizeRow, plateContent, imsSampleDetailCollection, sizeLetter);
				}
			}
		}
		if (isEdit)
			beanLabStudy.getSampleDetailCollection().clear();
		beanLabStudy.setImsSampleDetailCollection(imsSampleDetailCollection);
	}
	/**
	 * Method that load the samples by Rows
	 */
	private void loadByRows (){
		sizeRowSamples = 0;
		Collection<SampleDetail> imsSampleDetailCollection = new ArrayList<SampleDetail>();
		for (PlateContentList plateContent : plateList.getPlatesContents()){
			if (plateContent != null && plateContent.getPlateName()!= null)
			for (int sizeLetter = 0 ; sizeLetter <  plateContent.letters.length; sizeLetter++){
				for (Integer sizeRow = 0 ; sizeRow < plateContent.getPlateRows().get(0).getValuesInt().length; sizeRow++){
					// This key is to do by plate name and coordinates (Letter and number of Row) 
					String keyItem = plateContent.getPlateName()+"|"+plateContent.letters[sizeLetter]+(sizeRow+1);
					callSetSampleInPlateGrid(keyItem, sizeRow, plateContent, imsSampleDetailCollection, sizeLetter);
				}
				
			}
		}
		beanLabStudy.setImsSampleDetailCollection(imsSampleDetailCollection);
	}

	private void callSetSampleInPlateGrid(String keyItem, Integer sizeRow, PlateContentList plateContent, Collection<SampleDetail> imsSampleDetailCollection, int sizeLetter){
		Cell cell = (Cell)mapCellSample.get(keyItem);
		Label label = (Label)cell.getFellow(keyItem+PREFIX_LABEL);
		Image image = (Image)cell.getFellow(keyItem+PREFIX_IMAGE);
		ItemSampleBean itemEdit = (ItemSampleBean) (cell.getAttribute(ATTRIBUTE_SAMPLE_ITEM_BEAN) != null?cell.getAttribute(ATTRIBUTE_SAMPLE_ITEM_BEAN) : null);
		int index = (Integer)cell.getAttribute(ATTRIBUTE_SAMPLE_ITEM);
		ItemSampleBean item = getItemSampleInMap(sizeRowSamples);
		setSamplesInPlateGrid(index, itemEdit, item, plateContent, sizeLetter, 
				sizeRow, label, image, cell, imsSampleDetailCollection);
	}
	/**
	 * Method that load in the page the information and show if exist samples repeated 
	 * @param index this variable represent the type the assignment by sample
	 * @param itemEdit Object that contain the item to edit its value 
	 * @param item Object that contain the new value
	 * @param plateContent represent the form logic the plate
	 * @param sizeLetter Contain the index the row of plate
	 * @param sizeRow Contain the index the column of plate
	 * @param label Its a container to print the sample Id
	 * @param image Its a container to print the image of sample
	 * @param cell Its a container all objects by cell
	 * @param imsSampleDetailCollection List that represent the information that should be persist
	 */
	private void setSamplesInPlateGrid (int index, ItemSampleBean itemEdit, ItemSampleBean item, PlateContentList plateContent
			,int sizeLetter, Integer sizeRow, Label label,Image image, Cell cell, Collection<SampleDetail> imsSampleDetailCollection){
		if ( index != PlateRow.ASSIGNED_NUM )
			item = null;
		//conditional for load samples New
		if (!isEdit){
			SampleDetail sampleDetail = CommonUtils.getSampleDetdail(plateContent.getPlateName(), plateContent.letters[sizeLetter]+(sizeRow+1), item, index,beanLabStudy);
			sampleDetail.setControltype(StrUtils.getTypeOfControl(index));
			//Validate if the cell is assigned by the variable  Index
			if (index == PlateRow.ASSIGNED_NUM){
				//validate if the new item is different that null to continue
				if(item != null  && item.getTypeControl() == null){
				String sampleId = beanLabStudy.getProject().getProjectname()+beanLabStudy.getProject().getPurposename()+
						(!beanLabStudy.isUsePadded()?Integer.toString(item.getSampleID()):StrUtils.getPaddingCeros(item.getSampleID()));
				label.setValue(sampleId);
				createToolTip(item, sampleId.toUpperCase(), cell);
				image.setSrc(URL_IMAGES_ASSIGNED_TUBE);
				numberSamplesSuccessful ++;
					if(item.isRepeatSample())
						image.setSrc(URL_IMAGES_REPEAT_SAMPLE);
				sizeRowSamples++;
				}else {
					//validating samples for historical information
					if (item != null && item.getTypeControl() != null && !item.getTypeControl().equals("")){
						loadCellControlHistorical(label, image, item);
						sampleDetail.setControltype(item.getTypeControl().toString());
						setDefaultValueForHistoricalControlSample(sampleDetail);
					}else
					// if is null item then put the cell blank
					sampleDetail.setControltype(StrUtils.getTypeOfControl(PlateRow.BLANK_ITEM_NUM));
				}
				loadToListTemporalySample(item);
				// add to collection the sample detail
				imsSampleDetailCollection.add(sampleDetail);
			}else {
				//sampleDetail.setControltype(StrUtils.getTypeOfControl(PlateRow.BLANK_ITEM_NUM));
				loadToListTemporalySample(item);
				// add to collection the sample detail
				imsSampleDetailCollection.add(sampleDetail);
			}
		}else {
			// if you want to edit the item assigned
			if (index == PlateRow.ASSIGNED_NUM){
				//the item to edit and the new item should be different null
				if (itemEdit != null && item != null){
					if (isEqualsSampleDetailVsSampleItem(itemEdit, item)){
						SampleDetail detail = getSampleDetailById(itemEdit.getStudysampleid());
						if (detail.getSamplegid() == null || detail.getSamplegid().equals("")){
							if (itemEdit.getSampleID() > 0){
								detail = changeValueOfItemSample(itemEdit, index, itemEdit.getStudysampleid());
								detail.setSamplegid(itemEdit.getSampleID());
							}else if(item.getSampleID() > 0){
								detail.setSamplegid(item.getSampleID());
							}
						}
						//add samples from service
						loadToListTemporalySample(item);
						// add to collection the sample detail
						imsSampleDetailCollection.add(detail);
						String sampleId = beanLabStudy.getProject().getProjectname()+beanLabStudy.getProject().getPurposename()
								+(!beanLabStudy.isUsePadded()?Integer.toString(item.getSampleID()):StrUtils.getPaddingCeros(item.getSampleID()));
						label.setValue(sampleId.toUpperCase());
						image.setSrc(URL_IMAGES_ASSIGNED_TUBE);
						if(item.isRepeatSample()){
							image.setSrc(URL_IMAGES_REPEAT_SAMPLE);
						}
						createToolTip(itemEdit, sampleId.toUpperCase(), cell);
						sizeRowSamples++;
					}else {
						SampleDetail detailOriginal = getSampleDetailById(itemEdit.getStudysampleid());
						detailOriginal = CommonUtils.copySampleDetail(detailOriginal);
						if (itemEdit.getSampleID() > 0)
							item.setSampleID(itemEdit.getSampleID());	
						
						item.setStudysampleid(itemEdit.getStudysampleid());
						
						SampleDetail detail = changeValueOfItemSample(item, index, itemEdit.getStudysampleid());
						String sampleId = beanLabStudy.getProject().getProjectname()+beanLabStudy.getProject().getPurposename()
								+(!beanLabStudy.isUsePadded()?Integer.toString(item.getSampleID()):StrUtils.getPaddingCeros(item.getSampleID()));
						label.setValue(sampleId.toUpperCase());
						
						createToolTip(item, sampleId.toUpperCase(), cell);
						//add samples from service
						loadToListTemporalySample(item);
						imsSampleDetailCollection.add(detail);
						if(item.isRepeatSample()){
							image.setSrc(URL_IMAGES_REPEAT_SAMPLE);
						}
						validateSamplesToDelete(item.getSampleID(), detail, detailOriginal);
						sizeRowSamples++;
					}
					numberSamplesSuccessful ++;
				}else if (item != null){
					//conditional for load new item in the cell assigned 
					SampleDetail sampleDetail = CommonUtils.getSampleDetdail(plateContent.getPlateName(), plateContent.letters[sizeLetter]+(sizeRow+1), item, index, beanLabStudy);
					if (item.getTypeControl() != null && !item.getTypeControl().equals("")){
						loadCellControlHistorical(label, image, item);
						sampleDetail.setControltype(item.getTypeControl().toString());
						setDefaultValueForHistoricalControlSample(sampleDetail);
					}else
					// if is null item then put the cell blank
					sampleDetail.setControltype(StrUtils.getTypeOfControl(index));
						String sampleId = beanLabStudy.getProject().getProjectname()+beanLabStudy.getProject().getPurposename()+
								(!beanLabStudy.isUsePadded()?Integer.toString(item.getSampleID()):StrUtils.getPaddingCeros(item.getSampleID()));
						label.setValue(sampleId);
						createToolTip(item, sampleId.toUpperCase(), cell);
						image.setSrc(URL_IMAGES_ASSIGNED_TUBE);
						numberSamplesSuccessful ++;
							if(item.isRepeatSample()){
								image.setSrc(URL_IMAGES_REPEAT_SAMPLE);
							}
						sizeRowSamples++;
						validateSamplesToDelete(item.getSampleID(), sampleDetail, null);
						//add samples from service
						loadToListTemporalySample(item);
						imsSampleDetailCollection.add(sampleDetail);
				}else {
					//conditional to delete off the plate when it was assigned 
					if (itemEdit != null){
						SampleDetail detail = getSampleDetailById(itemEdit.getStudysampleid());
						if (detail.getSamplegid() != null && detail.getSamplegid().intValue() > 0)
							mapSamplesDelete.put(detail.getSamplegid(), CommonUtils.copySampleDetail(detail));
						//detail.setSamplegid(null);
						clearSampleDetail(detail, PlateRow.BLANK_ITEM_NUM);
						imsSampleDetailCollection.add(detail);
						label.setValue(StrUtils.getStringByIndexLoad(PlateRow.BLANK_ITEM_NUM, pro));
						cell.setAttribute(ATTRIBUTE_SAMPLE_ITEM, PlateRow.BLANK_ITEM_NUM);
						clearCellPopup(cell);
						image.setSrc(URL_IMAGES_BLANK_TUBE);
					}
				}
			}else {
				//conditional to delete off the plate when it wasn't assigned 
				if (itemEdit != null){
					SampleDetail detail = getSampleDetailById(itemEdit.getStudysampleid());
					if (detail.getSamplegid() != null && detail.getSamplegid().intValue() > 0)
						mapSamplesDelete.put(detail.getSamplegid(), CommonUtils.copySampleDetail(detail));
					detail.setSamplegid(null);
					clearSampleDetail(detail, index);
					imsSampleDetailCollection.add(detail);
					label.setValue(StrUtils.getStringByIndexLoad(index, pro));
					clearCellPopup(cell);
				}else {
					SampleDetail sampleDetail = CommonUtils.getSampleDetdail(plateContent.getPlateName(), plateContent.letters[sizeLetter]+(sizeRow+1), item, index, beanLabStudy);
					if (item.getTypeControl() != null && !item.getTypeControl().equals("")){
						loadCellControlHistorical(label, image, item);
						sampleDetail.setControltype(item.getTypeControl().toString());
						setDefaultValueForHistoricalControlSample(sampleDetail);
					}else
					// if is null item then put the cell blank
					sampleDetail.setControltype(StrUtils.getTypeOfControl(index));
					//add samples from service
					loadToListTemporalySample(item);
					imsSampleDetailCollection.add(sampleDetail);
				}
			}
		}
	}

	private void setDefaultValueForHistoricalControlSample(SampleDetail sampleDetail){
		sampleDetail.setBreedergid(null);
		sampleDetail.setEntryNo(null);
		sampleDetail.setNplanta(null);
		sampleDetail.setNval(null);
		sampleDetail.setLocationid(null);
		sampleDetail.setSeasonid(null);
	}
	private void loadCellControlHistorical(Label label,Image image, ItemSampleBean item){
		if (item.getTypeControl().toString().equals(String.valueOf(ConstantsDNA.BANK_CONTROL))){
			image.setSrc(URL_IMAGES_BLANK_TUBE);
			label.setValue(pro.getKey(LBL_STUDIES_PLATE_ITEM_BLANK));
		}else{
			image.setSrc(URL_IMAGES_CONTROL_TUBE);
			label.setValue(pro.getKey(LBL_STUDIES_PLATE_ITEM_CONTROL));
		}
		sizeRowSamples++;
	}
	private void loadToListTemporalySample( ItemSampleBean item){
		if ( item != null &&item.getIdSampleTemp() > 0){
			TemporalSample temp = new TemporalSample();
			temp.setIdTemSample(new Integer(item.getIdSampleTemp()));
			listTempsample.add(temp);
		}
	}
	/**
	 * Method that validate if the samples can delete for the origin
	 * @param Sample ID
	 */
	private void validateSamplesToDelete (Integer sampleID, SampleDetail detail, SampleDetail detailOriginal){

		if (detailOriginal != null && detailOriginal.getSamplegid() != null){
			if (!mapSamplesEdit.containsKey(detailOriginal.getSamplegid()))
				 mapSamplesDelete.put(detailOriginal.getSamplegid(), detailOriginal);
		}

		if (mapSamplesDelete.containsKey(sampleID)){
			mapSamplesDelete.remove(sampleID);
		}else {
			mapSamplesEdit.put(sampleID, detail);
		}
	}
	/**
	 * Method that return a sample detail change value of fields
	 * @param item
	 * @param index
	 * @param sampleId
	 * @return Sample Detail
	 */
	private SampleDetail changeValueOfItemSample(ItemSampleBean item, int index, Integer studysampleid){
		SampleDetail detailResult = getSampleDetailById(studysampleid);
		detailResult.setSamplegid(item.getSampleID());
		detailResult.setBreedergid(item.getGid());
		detailResult.setEntryNo(item.getEntryNo());
		detailResult.setNplanta(item.getPlantNo());
		detailResult.setNval(item.getAcc().toString());
		detailResult.setLocationid(item.getLocationidBean().
				getLocationBean(item.getLocationidBean()));
		detailResult.setSeasonid(item.getSeasonidBean().
				getSeason(item.getSeasonidBean()));
		detailResult.setControltype(StrUtils.getTypeOfControl(index));
		detailResult.setPriority(item.getComment()!= null && 
				!item.getComment().toString().trim().equals("") ?item.getComment().toString(): 
					detailResult.getPriority() != null ? detailResult.getPriority().toString(): detailResult.getPriority());
		return detailResult;
	}
	/**
	 * Method that validate if two item of sample detail are equals
	 * @param edit
	 * @param item
	 * @return
	 */
	private boolean isEqualsSampleDetailVsSampleItem(ItemSampleBean edit, ItemSampleBean item){
		if (edit.isOnlyIDSampleNotNull(edit))
			return false;
		if (edit.getGid().equals(item.getGid()) && edit.getAcc().toString().equals(item.getAcc().toString()) &&
			edit.getLocationidBean().getLocationid().equals(item.getLocationidBean().getLocationid()) &&
				edit.getSeasonidBean().getSeasonid().equals(item.getSeasonidBean().getSeasonid())){
			if (edit.getPlantNo() != null && item.getPlantNo()!= null){
				if (edit.getPlantNo().equals(item.getPlantNo())) return true;
				else return false;
			}else if ((edit.getPlantNo() == null && item.getPlantNo()!= null)
			|| (item.getPlantNo() == null && edit.getPlantNo()!= null))	return false;
			else return true;
		}
		return false;
	}
	/**
	 * Method that clear the object Sample Detail
	 * @param detail
	 * @param index
	 */
	private void clearSampleDetail(SampleDetail detail, int index){
		detail.setBreedergid(null);
		detail.setEntryNo(null);
		detail.setNplanta(null);
		detail.setNval("");
		detail.setLocationid(null);
		detail.setSeasonid(null);
		detail.setControltype(StrUtils.getTypeOfControl(index));
	}
	/**
	 * Method that return Sample Detail by Id 
	 * @param studysampleid
	 * @return
	 */
	private SampleDetail getSampleDetailById(Integer studysampleid){
		return mapSamplesCollection.get(studysampleid);
	}
	/**
	 * Method that load once map with key 
	 */
	private void loadMapSamplesToReadLoad(){
		mapSamplesCollection = new HashMap <Integer, SampleDetail>();
		for (SampleDetail detail : beanLabStudy.getSampleDetailCollection()){
			mapSamplesCollection.put(detail.getStudysampleid(), detail);
		}
	}
	/**
	 * Method for load the tool tip by describe the content of cell
	 * @param item
	 * @param sampleId
	 * @param cell
	 */
	private void createToolTip (ItemSampleBean item, String sampleId, Cell cell){
		setPopupForToolTip(item, sampleId, cell);
		cell.setAttribute(ATTRIBUTE_SAMPLE_ITEM_BEAN, item);
	}
	/**
	 * Method that clear a pop up of cells
	 * @param cell
	 */
	private void clearCellPopup(Cell cell){
		Popup popup = null;
		List<Component> list = cell.getChildren();
		if (list !=null && !list.isEmpty()){
			for (Component component : list){
				if (component instanceof Popup)
					popup = (Popup)component;
			}
		}
		if (popup != null)
		cell.removeChild(popup);
	}
	/**Method that set pop up like tool tip
	 * @param item
	 * @param sampleId
	 * @param cell */
	private void setPopupForToolTip(ItemSampleBean item, String sampleId, Cell cell){
		clearCellPopup(cell);
		Popup popup = new Popup();
		//popup.setId(PREFIX_POPUP+sampleId);
		popup.setParent(cell);
		Html html = new Html (getTextToToolTip(item, sampleId));
		popup.appendChild(html);
		cell.setTooltip(popup);
		cell.setTooltiptext("");
	}
	/**Method that return a string with text to tool tip
	 * @param item
	 * @param sampleId
	 * @return String */
	private String getTextToToolTip(ItemSampleBean item, String sampleId){
		StringBuilder strBuilder = new StringBuilder ();
		strBuilder.append(pro.getKey(LBL_STUDIES_FILE_UP_CELL_T_SAMP,new String []{sampleId}));
		strBuilder.append("<br/>");
		strBuilder.append(pro.getKey(LBL_STUDIES_FILE_UP_CELL_T_GID, new String []{String.valueOf(item.getGid())}));
		strBuilder.append("<br/>");
		strBuilder.append(pro.getKey(LBL_STUDIES_FILE_UP_CELL_PLANT_N, new String []{String.valueOf(item.getPlantNo())}));
		strBuilder.append("<br/>");
		strBuilder.append(pro.getKey(LBL_STUDIES_FILE_UP_CELL_LOCATION, new String []{String.valueOf(item.
				getLocationidBean().getLocation_name())}));
		strBuilder.append("<br/>");
		strBuilder.append(pro.getKey(LBL_STUDIES_FILE_UP_CELL_SEASON, new String []{String.valueOf(item.
				getSeasonidBean().getSeason_name())}));
		strBuilder.append("<br/>");
		return strBuilder.toString();
	}
	/**Method that return the sample by the key
	 * @param index
	 * @return ItemSampleBean*/
	private ItemSampleBean getItemSampleInMap(int index){
		return (ItemSampleBean)mapItemSampleOrder.get(new Integer(index));
	}
	/** Method that create map to will be load in the plate */
	private void putMapInOrderByID(){
		@SuppressWarnings("rawtypes")
		Iterator it = mapItemSampleBean.entrySet().iterator();
		while (it.hasNext()){
				@SuppressWarnings("rawtypes")
				Map.Entry e = (Map.Entry)it.next();
				ItemSampleBean bean = (ItemSampleBean)e.getValue();
				mapItemSampleOrder.put(new Integer(bean.getIdOrder()), bean);
		}
	}
	/**Method that put in a map the samples by plate name like key */
	private void putMapSamplesControl(){
		@SuppressWarnings("rawtypes")
		Iterator it = mapItemSampleBean.entrySet().iterator();
		while (it.hasNext()){
				@SuppressWarnings("rawtypes")
				Map.Entry e = (Map.Entry)it.next();
				ItemSampleBean bean = (ItemSampleBean)e.getValue();
				mapItemSampleControl.put(bean.getPlateName()+"|"+bean.getRow()+bean.getColumn(), bean);
		}
	}
}
