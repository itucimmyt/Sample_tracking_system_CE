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
import static com.cimmyt.utils.Constants.LBL_STUDIES_CONTROL_DART;
import static com.cimmyt.utils.Constants.LBL_STUDIES_CONTROL_KBIOSCIENCES;
import static com.cimmyt.utils.Constants.LBL_STUDIES_PLATE_ITEM_CONTROL;
import static com.cimmyt.utils.Constants.LBL_STUDIES_RANDOM_TUBE;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.zkoss.zhtml.Messagebox;
import org.zkoss.zul.Cell;

import com.cimmyt.bean.CSVReportGenericBean;
import com.cimmyt.bean.RowCSV;
import com.cimmyt.bean.RowControlFile;
import com.cimmyt.bean.RowResultDataBean;
import com.cimmyt.loadStudy.ServiceControlFile;
import com.cimmyt.model.bean.LabStudy;
import com.cimmyt.model.bean.ResultsPreferencesDetail;
import com.cimmyt.model.bean.SampleDetail;
import com.cimmyt.model.bean.Shipment;
import com.cimmyt.model.bean.ShipmentDetail;
import com.cimmyt.model.bean.ShipmentSet;
import com.cimmyt.study.PlateContentList;
import com.cimmyt.study.PlateRow;
import com.cimmyt.utils.Constants;
import com.cimmyt.utils.PropertyHelper;
import com.csvreader.CsvWriter;
/**
 * Class that implement the service to management of control files 
 * @author CIMMYT
 */
public class ServiceControlFileImpl implements ServiceControlFile {
	private PropertyHelper pro;
	
   /**
    * Method that load in the file the samples that are control  
    */
	@Override
	public List<RowControlFile> getControlSamples(PropertyHelper pro, PlateContentList plateContent,
			Map<String, Object> mapCellSample, String plateName) {
		this.pro = pro;
		int sizeColumns = plateContent.getPlateRows().get(0).getColsPerRow();
		List<RowControlFile> listRowControl = new ArrayList<RowControlFile>();
		for (int indexColumn = 1; indexColumn <=sizeColumns; indexColumn++){
			for (String letter : plateContent.letters){
				String keyItem = plateName+"|"+letter +indexColumn;
				Cell cell = (Cell)mapCellSample.get(keyItem);
				int index = (Integer)cell.getAttribute(ATTRIBUTE_SAMPLE_ITEM);
				if (index == PlateRow.DART_CONTROL_ITEM_NUM || index == PlateRow.BLANK_CONTROL_NUM
						|| index == PlateRow.KBIO_CONTROL_ITEM_NUM  || index == PlateRow.RANDOM_CONTROL_ITEM_NUM){
					RowControlFile rowControl = new RowControlFile();
					rowControl.setControlType(getTypeControl(index));
					rowControl.getPlateName().append(plateName);
					rowControl.getRow().append(letter);
					rowControl.getColumn().append(indexColumn);
					listRowControl.add(rowControl);
				}
			}
		}
		return listRowControl;
	}
	/**
	 * Method that return of type control
	 * @param index
	 * @return
	 */
	private StringBuilder getTypeControl (int index){
		StringBuilder strBuilder = new StringBuilder ();
	    switch (index){
	    case PlateRow.DART_CONTROL_ITEM_NUM :
	    	strBuilder.append(pro.getKey(LBL_STUDIES_CONTROL_DART));
	    	break;
	    case PlateRow.BLANK_CONTROL_NUM :
	    	strBuilder.append(pro.getKey(LBL_STUDIES_PLATE_ITEM_CONTROL));
	    	break;
	    case PlateRow.KBIO_CONTROL_ITEM_NUM :
	    	strBuilder.append(pro.getKey(LBL_STUDIES_CONTROL_KBIOSCIENCES));
	    	break;
	    case PlateRow.RANDOM_CONTROL_ITEM_NUM :
	    	strBuilder.append(pro.getKey(LBL_STUDIES_RANDOM_TUBE));
	    	break;
	    }	
	    return strBuilder;
	}
	/**
	 * Get the header to file control
	 * @param pro
	 * @return
	 */
	public List<String> getHeader(PropertyHelper pro){
		this.pro = pro;
		List<String> listHeader = new ArrayList<String>();
		listHeader.add(pro.getKey(Constants.LBL_GENERIC_MEN_COL_CONT_TYPE));
		listHeader.add(pro.getKey(Constants.LBL_GENERIC_MEN_PLATE_NAME));
		listHeader.add(pro.getKey(Constants.LBL_GENERIC_MEN_COL_ROW_ONE));
		listHeader.add(pro.getKey(Constants.LBL_GENERIC_MEN_COL_COL));
		listHeader.add(pro.getKey(Constants.LBL_GENERIC_MEN_COL_GID));
		listHeader.add(pro.getKey(Constants.LBL_GENERIC_MEN_COL_ACC));
		listHeader.add(pro.getKey(Constants.LBL_GENERIC_MEN_COL_PLANT_NO));
		listHeader.add(pro.getKey(Constants.LBL_GENERIC_MEN_COL_SPECIE));
		listHeader.add(pro.getKey(Constants.LBL_GENERIC_MEN_COMMENTS));
		listHeader.add(pro.getKey(Constants.LBL_GENERIC_MEN_COL_ENTRY_NO));
		listHeader.add(pro.getKey(Constants.LBL_GENERIC_MEN_LOCATION));
		listHeader.add(pro.getKey(Constants.LBL_GENERIC_MEN_COL_SEASON));
		return listHeader;
	}
	
	
}
