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

import static com.cimmyt.utils.Constants.ARR_DART_CONTROL;
import static com.cimmyt.utils.Constants.ARR_KBIOS_CONTROL;
import static com.cimmyt.utils.Constants.ARR_CIMMYT_CONTROL_EMPTY_96;
import static com.cimmyt.utils.Constants.ARR_CIMMYT_CONTROL_EMPTY_384;
import static com.cimmyt.utils.Constants.ATTRIBUTE_CONTROL_LAB;
import static com.cimmyt.utils.Constants.ATTRIBUTE_SAMPLE_ITEM;
import static com.cimmyt.utils.Constants.ATTRIBUTE_SAMPLE_ITEM_BEAN;
import static com.cimmyt.utils.Constants.LBL_STUDIES_CONTROL_DART;
import static com.cimmyt.utils.Constants.LBL_STUDIES_CONTROL_KBIOSCIENCES;
import static com.cimmyt.utils.Constants.LBL_STUDIES_FILE_UP_CELL_LOCATION;
import static com.cimmyt.utils.Constants.LBL_STUDIES_FILE_UP_CELL_PLANT_N;
import static com.cimmyt.utils.Constants.LBL_STUDIES_FILE_UP_CELL_SEASON;
import static com.cimmyt.utils.Constants.LBL_STUDIES_FILE_UP_CELL_T_GID;
import static com.cimmyt.utils.Constants.LBL_STUDIES_FILE_UP_CELL_T_SAMP;
import static com.cimmyt.utils.Constants.LBL_STUDIES_PLATE_ITEM_BLANK;
import static com.cimmyt.utils.Constants.LBL_STUDIES_PLATE_ITEM_CONTROL;
import static com.cimmyt.utils.Constants.LBL_STUDIES_RANDOM_TUBE;
import static com.cimmyt.utils.Constants.URL_IMAGES_ASSIGNED_TUBE;
import static com.cimmyt.utils.Constants.URL_IMAGES_BLANK_TUBE;
import static com.cimmyt.utils.Constants.URL_IMAGES_CONTROL_RANDOM_TUBE;
import static com.cimmyt.utils.Constants.URL_IMAGES_CONTROL_TUBE;
import static com.cimmyt.utils.Constants.URL_IMAGES_DART_CONTROL_TUBE;
import static com.cimmyt.utils.Constants.URL_IMAGES_KBIO_CONTROL_TUBE;
import static com.cimmyt.utils.Constants.URL_IMAGES_REPEAT_SAMPLE;
import static com.cimmyt.utils.ConstantsDNA.PREFIX_CHECK;
import static com.cimmyt.utils.ConstantsDNA.PREFIX_IMAGE;
import static com.cimmyt.utils.ConstantsDNA.PREFIX_LABEL;

import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Html;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Tabpanel;

import com.cimmyt.bean.ControlPlateBean;
import com.cimmyt.bean.ItemSampleBean;
import com.cimmyt.constants.ShipmentStatus;
import com.cimmyt.loadStudy.ServiceCreatePlate;
import com.cimmyt.model.bean.LabStudy;
import com.cimmyt.model.bean.SampleDetail;
import com.cimmyt.study.PlateContentList;
import com.cimmyt.study.PlateRow;
import com.cimmyt.utils.PropertyHelper;
import com.cimmyt.utils.StrUtils;

public class ServiceCreatePlateImpl  implements ServiceCreatePlate{

	private Map<String, Object> mapCellSample ;
	private Map<String, Object> mapSampleEdit ;
	@SuppressWarnings("unused")
	private PlateContentList plateContent;
	private PropertyHelper pro=null;
	private LabStudy beanLabStudy;
	private boolean isNewStudy;
	private String plateName = "";
	private List <String> listSampleRepeat;
	private String delimiter = "\\|";
	private Map<String, Object> mapAssignSample;
	//Object to add or delete elements at size of plate
	private Map<String, Integer> mapSizePlate;
	private Integer numberControlProvider = new Integer(0);
	private Integer numberControlRandom = new Integer(0);
	private Integer numberControlBlank = new Integer(0);
	private ControlPlateBean controlPlateBean;

	/**
	 * Load blank samples in the Grid
	 * @param numColum 
	 * @param PlateContentList
	 * @param mapCellSample
	 */
	@Override
	public Grid addLoadGridByPlate(int numColum, PlateContentList plateContent,
			Map<String, Object> mapCellSample, PropertyHelper pro, LabStudy _beanLabStudy,
			boolean _isNewStudy,Map<String, Object> _mapSampleEdit, String _plateName, 
			Tabpanel tabPanelj,List <String> _listSampleRepeat, Map<String, Object> _mapAssignSample,
			Map<String, Integer> _mapSizePlate, ControlPlateBean _controlPlateBean) {
		this.mapCellSample = mapCellSample;
		this.plateContent = plateContent;
		this.pro = pro;
		this.beanLabStudy = _beanLabStudy;
		this.isNewStudy = _isNewStudy;
		this.mapSampleEdit = _mapSampleEdit;
		this.plateName = _plateName;
		this.listSampleRepeat = _listSampleRepeat;
		this.mapAssignSample = _mapAssignSample;
		this.mapSizePlate = _mapSizePlate;
		this.controlPlateBean = _controlPlateBean;
		Grid grid = loadGridByPlate(numColum, plateContent, tabPanelj);
		controlPlateBean.setNumberControlBlank(controlPlateBean.getNumberControlBlank()+ numberControlBlank);
		controlPlateBean.setNumberControlProvider(controlPlateBean.getNumberControlProvider()+ numberControlProvider);
		controlPlateBean.setNumberControlRandom(controlPlateBean.getNumberControlRandom()+ numberControlRandom);
		return grid;
	}


	private Grid loadGridByPlate(int numColum, PlateContentList plateContent, Tabpanel tabPanelj){
		Grid grid = new Grid();
		grid.setParent(tabPanelj);
		Columns columns = new Columns();
		columns.setSizable(true);
		//Checkbox checkBox = new Checkbox();
		Column columnEmty = new Column("");
		columnEmty.setWidth("25px");
		//columnEmty.appendChild(checkBox);
		columns.appendChild(columnEmty);
		for (int column = 1; column <= numColum; column++){
			Column col = new Column(String.valueOf(column));
			col.setWidth("150px");
			columns.appendChild(col);
			
		}
		
		Rows rows = new Rows();
		rows.setParent(grid);
		if (isNewStudy){
		 for (PlateRow plateRow : plateContent.getPlateRows()){
				Row row = new Row ();
				row.setParent(rows);
				Cell celLetter = new Cell();
				Label labelLetter = new Label(plateRow.getLetter());
				celLetter.appendChild(labelLetter);
				row.appendChild(celLetter);
				
					int indexCol = 1;
					for (Integer intValue : plateRow.getValuesInt()){
						row.appendChild(getCellPlate(intValue, indexCol, plateRow.getLetter(),
								plateContent.getPlateName(), null, row));
						indexCol ++;
					}
					rows.appendChild(row);
				
			}//end of for to read plateRows
		}else {
					for (PlateRow plateRow : plateContent.getPlateRows()){
						Row row = new Row ();
						row.setParent(rows);
						Cell celLetter = new Cell();
						Label labelLetter = new Label(plateRow.getLetter());
						celLetter.appendChild(labelLetter);
						row.appendChild(celLetter);
						int indexCol = 1;
						for (Integer intValue : plateRow.getValuesInt()){
							SampleDetail detail = (SampleDetail)mapSampleEdit.get(plateName+"|"+plateRow.getLetter()+indexCol);
							int type = 1;
							if (detail != null && detail.getControltype() != null  && 
									!detail.getControltype().toString().equals("")){
								type = StrUtils.getTypeIdentifierToSample(detail.getControltype()); 
							}
							row.appendChild(getCellPlate(type, indexCol, plateRow.getLetter(),
									plateName, detail, row));
							indexCol ++;
							
						}
						rows.appendChild(row);
					}
	}
		grid.setHeight("100%");
		grid.setWidth("100%");
		grid.appendChild(columns);
		grid.appendChild(rows);
		return grid;
	}
	/**
	 * Create the cell to each sample
	 * @param index
	 * @param idRow
	 * @param letter
	 * @param plateName
	 * @return
	 */
	private Cell getCellPlate(int index, int idRow, String letter, 
			String plateName, SampleDetail sampleDetail, Component component){
		Cell cell = new Cell();
		cell.setParent(component);
		String keyItem = plateName+"|"+letter+idRow;
		//System.out.println("keyyyyyy ::: "+keyItem + "id indexxxx : "+index);
		boolean isSampleAssigned = false;
		Hbox hbox = new Hbox();
		Checkbox checkBox = new Checkbox();
		checkBox.setId(keyItem+PREFIX_CHECK);
		checkBox.setDisabled(false);
		Image image = new Image ();
		image.setId(keyItem+PREFIX_IMAGE);
		Label label = new Label();
		label.setId(keyItem+PREFIX_LABEL);
		switch (index){
		case PlateRow.BLANK_ITEM_NUM :
			image.setSrc(URL_IMAGES_BLANK_TUBE);
			label.setValue(pro.getKey(LBL_STUDIES_PLATE_ITEM_BLANK));
			cell.setTooltiptext("("+letter+","+idRow+")");
			break;
		case PlateRow.ASSIGNED_NUM :
			image.setSrc(URL_IMAGES_ASSIGNED_TUBE);
			isSampleAssigned = true;
			loadMapAssigned(keyItem);
			//createToolTip(sampleDetail, sampleId, cell);
			break;
		case PlateRow.BLANK_CONTROL_NUM :
			image.setSrc(URL_IMAGES_CONTROL_TUBE);
			label.setValue(pro.getKey(LBL_STUDIES_PLATE_ITEM_CONTROL));
			cell.setTooltiptext("("+letter+","+idRow+")");
			numberControlBlank  = numberControlBlank +1;
			break;
		case PlateRow.DART_CONTROL_ITEM_NUM :
			image.setSrc(URL_IMAGES_DART_CONTROL_TUBE);
			label.setValue(pro.getKey(LBL_STUDIES_CONTROL_DART));
			cell.setTooltiptext("("+letter+","+idRow+")");
			numberControlProvider = numberControlProvider + 1;
			break;
		case PlateRow.KBIO_CONTROL_ITEM_NUM :
			image.setSrc(URL_IMAGES_KBIO_CONTROL_TUBE);
			label.setValue(pro.getKey(LBL_STUDIES_CONTROL_KBIOSCIENCES));
			cell.setTooltiptext("("+letter+","+idRow+")");
			numberControlProvider = numberControlProvider + 1;
			break;
		case PlateRow.RANDOM_CONTROL_ITEM_NUM :
			image.setSrc(URL_IMAGES_CONTROL_RANDOM_TUBE);
			label.setValue(pro.getKey(LBL_STUDIES_RANDOM_TUBE));
			if (sampleDetail != null && sampleDetail.getSamplegid()!= null){
				isSampleAssigned = true;
				loadMapAssigned(keyItem);
			}else {
				isSampleAssigned = false;
				numberControlRandom = numberControlRandom + 1;
			}
			cell.setTooltiptext("("+letter+","+idRow+")");
			break;
		case PlateRow.NOT_CONTROL :
			image.setSrc(URL_IMAGES_BLANK_TUBE);
			label.setValue(pro.getKey(LBL_STUDIES_PLATE_ITEM_BLANK));
			cell.setTooltiptext("("+letter+","+idRow+")");
			break;
		default :
			break;
		}
		if (sampleDetail != null && sampleDetail.getSelforsend() != null && 
				!sampleDetail.getSelforsend().equals(ShipmentStatus.NO_SELECTED.getId() )){
			checkBox.setDisabled(true);
		}
		hbox.appendChild(checkBox);
		hbox.appendChild(image);
		hbox.appendChild(label);
		cell.appendChild(hbox);
		cell.setAttribute(ATTRIBUTE_SAMPLE_ITEM, index);
		if (isSampleAssigned){
			String sampleId = StrUtils.getSampleIDKey(beanLabStudy, sampleDetail.getSamplegid());
			boolean isSampleRepeat = false;
			if (listSampleRepeat.contains(sampleId)){
				image.setSrc(URL_IMAGES_REPEAT_SAMPLE);
				cell.setAttribute(ATTRIBUTE_SAMPLE_ITEM, PlateRow.ASSIGNED_NUM);
				isSampleRepeat = true;
			}
			label.setValue(sampleId.toUpperCase());
			if (sampleDetail != null && sampleDetail.getBreedergid() != null 
					&& sampleDetail.getNplanta() != null && sampleDetail.getLocationid() != null
					&& sampleDetail.getLocationid() != null){
			createToolTip(sampleDetail, sampleId, cell, isSampleRepeat);
			}
		}else {
			if (sampleDetail != null )
			cell.setAttribute(ATTRIBUTE_SAMPLE_ITEM_BEAN, new ItemSampleBean(sampleDetail,false ));
			cell.setTooltiptext("("+letter+","+idRow+")");
			}
		
		validateControlDartOrKbios(cell, letter+idRow);
		mapCellSample.put(keyItem,cell);
		return cell;
	}
	/**
	 * Method that load in map the samples assigned in the plate 
	 * @param key
	 */
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
	/**
	 *Method that create a map with the size of plate 
	 * @param inserOrDelete
	 * @param namePlate
	 */
	private void loadMapSizePlate(boolean inserOrDelete, String namePlate){
		if (inserOrDelete){
			if (mapSizePlate.size() == 0 ){
				mapSizePlate.put(namePlate, new Integer(1));
			}else {
				if (mapSizePlate.get(namePlate)!= null){
					Integer valueSize = (Integer)mapSizePlate.get(namePlate);
					mapSizePlate.put(namePlate, ++valueSize);
				}else {
					mapSizePlate.put(namePlate, new Integer(1));
				}
			}	
		}else {
			if (mapSizePlate.size() > 0 ){
				Integer valueSize = (Integer)mapSizePlate.get(namePlate);
				mapSizePlate.put(namePlate, --valueSize);
			}
		}
	}
	/** Method to load the tool tip by describe the contain of cell
	 * @param item
	 * @param sampleId
	 * @param cell
	 */
	private void createToolTip (SampleDetail sampleDetail, String sampleId, Cell cell, boolean isSampleRepeat){
	Popup popup = new Popup();
	//popup.setId(PREFIX_POPUP+sampleId);
	popup.setParent(cell);
	StringBuilder strBuilder = new StringBuilder ();
	strBuilder.append(pro.getKey(LBL_STUDIES_FILE_UP_CELL_T_SAMP,new String []{sampleId}));
	strBuilder.append("<br/>");
	strBuilder.append(pro.getKey(LBL_STUDIES_FILE_UP_CELL_T_GID, new String []{String.valueOf(sampleDetail.getBreedergid())}));
	strBuilder.append("<br/>");
	strBuilder.append(pro.getKey(LBL_STUDIES_FILE_UP_CELL_PLANT_N, new String []{String.valueOf(sampleDetail.getNplanta())}));
	strBuilder.append("<br/>");
	strBuilder.append(pro.getKey(LBL_STUDIES_FILE_UP_CELL_LOCATION, new String []{String.valueOf(sampleDetail.getLocationid().
			getLocation_name())}));
	strBuilder.append("<br/>");
	strBuilder.append(pro.getKey(LBL_STUDIES_FILE_UP_CELL_SEASON, new String []{String.valueOf(sampleDetail.getSeasonid().
			getSeason_name())}));
	strBuilder.append("<br/>");
	Html html = new Html (strBuilder.toString());
	popup.appendChild(html);
	cell.setTooltip(popup);
	cell.setTooltiptext("");
	cell.setAttribute(ATTRIBUTE_SAMPLE_ITEM_BEAN, new ItemSampleBean(sampleDetail,isSampleRepeat ));

	}
	
	private void validateControlDartOrKbios (Cell cell, String numberCell){
		for (String arrDart : ARR_DART_CONTROL){
			if (arrDart.equals(numberCell)){
				cell.setAttribute(ATTRIBUTE_CONTROL_LAB, PlateRow.DART_CONTROL_ITEM_NUM);
			}
		}
		for (String arrKBios : ARR_KBIOS_CONTROL){
			if (arrKBios.equals(numberCell)){
				cell.setAttribute(ATTRIBUTE_CONTROL_LAB, PlateRow.KBIO_CONTROL_ITEM_NUM);
			}
		}
		//CIMMHYT_CONTROL_EMTY
		String [] arrCimmyt= plateContent.getPlateSize()==PlateContentList.PLATE_SIZE_96?ARR_CIMMYT_CONTROL_EMPTY_96 :ARR_CIMMYT_CONTROL_EMPTY_384 ;
		for (String arrKBios : arrCimmyt){
			if (arrKBios.equals(numberCell)){
				cell.setAttribute(ATTRIBUTE_CONTROL_LAB, PlateRow.BLANK_CONTROL_NUM);
			}
		}
		
	}
}

