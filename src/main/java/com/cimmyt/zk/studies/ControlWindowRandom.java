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

import static com.cimmyt.utils.Constants.ATTRIBUTE_MAP_CELL_SAMPLE;
import static com.cimmyt.utils.Constants.ATTRIBUTE_MAP_NUM_ITEM_SELECT;
import static com.cimmyt.utils.Constants.ATTRIBUTE_NUMBER_SAMPLES_CONTROL;
import static com.cimmyt.utils.Constants.ATTRIBUTE_SAMPLE_ITEM;
import static com.cimmyt.utils.Constants.ATTRIBUTE_SIZE_COLUMNS;
import static com.cimmyt.utils.Constants.ATTRIBUTE_SIZE_PLATE_SELECT;
import static com.cimmyt.utils.Constants.LBL_STUDIES_RANDOM_SELECT_LEAST;
import static com.cimmyt.utils.Constants.LBL_STUDIES_RANDOM_TUBE;
import static com.cimmyt.utils.Constants.LOCALE_LANGUAGE;
import static com.cimmyt.utils.Constants.URL_IMAGES_CONTROL_RANDOM_TUBE;
import static com.cimmyt.utils.ConstantsDNA.DELIMITER_PIE_SAMPLE;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.zkoss.zul.Cell;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.cimmyt.study.PlateContentList;
import com.cimmyt.study.PlateRow;
import com.cimmyt.utils.PropertyHelper;

@SuppressWarnings("serial")
public class ControlWindowRandom extends Window{

	private Spinner idSpinnerSize;
	private Textbox idTextError;
	private int sizePlate=0;
	private Window idWindowRandom;
	private PropertyHelper pro=null;
	private Map<String, Object> mapCellSample = new HashMap<String, Object>();
	private Map<String, Integer> mapSizePlate = new HashMap<String, Integer>();
	private Map<String, String> mapRandomControls = new HashMap<String,String>();
	
	/** 
	 * Close Window
	 */
	public void closeWindow(){
		Window idWindow = (Window)getFellow("idWindowRandom");
		idWindow.onClose();
	}

	public void loadContext (){
		setComponents();
		loadSpinerSize();
		pro = (PropertyHelper)getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);
	}

	private void  loadSpinerSize (){
		sizePlate = (Integer)getDesktop().getAttribute(ATTRIBUTE_SIZE_PLATE_SELECT);
		idSpinnerSize.setConstraint("no empty, min 1 max "+(sizePlate-1));
	}
	private void setComponents(){
		idSpinnerSize = (Spinner)getFellow("idSpinnerSize");
		idTextError = (Textbox)getFellow("idTextError");
		idWindowRandom = (Window)getFellow("idWindowRandom");
	}

	@SuppressWarnings({ "rawtypes", "unchecked"})
	public void createControlRandom(){
		idTextError.setText("");
		int size = idSpinnerSize.getValue();
		if (size > 0){
		  mapSizePlate = (Map<String, Integer>) getDesktop().getAttribute(ATTRIBUTE_MAP_NUM_ITEM_SELECT);
		  Set set = mapSizePlate.entrySet();
	      Iterator it=set.iterator();
	      StringBuilder strB = new StringBuilder();
	      mapRandomControls = new HashMap<String,String>();
	      while(it.hasNext())
	      {
	          Map.Entry m =(Map.Entry)it.next();
	          Integer value=(Integer)m.getValue();
	          String key = (String)m.getKey();
	          if (size > value.intValue()){
	        	  strB.append(key);
	        	  strB.append("\n"); 
	          }
	      }
	      if (strB != null && !strB.toString().trim().equals("")){
	    	  strB.append(pro.getKey(LBL_STUDIES_RANDOM_SELECT_LEAST,
						new String []{(sizePlate-1)+""}));
	      	 
	       	 idTextError.setText(strB.toString());
	      }else {
	    	  loadRandomControlInGrid(size);
	    	  idWindowRandom.onClose();
	      }
		}
	}

	public void changeValueSpinner(){
		int value = idSpinnerSize.getValue();
		if (value < 1){
			idSpinnerSize.setValue(1);
		}
		if (value > sizePlate){
			idSpinnerSize.setValue(sizePlate-1);
		}
	}

	private String [] getArrRow (){
		if (sizePlate == 96){
			return PlateContentList.letters96;
		}
		
		return PlateContentList.letters384;
	}

	@SuppressWarnings("unchecked")
	private void loadRandomControlInGrid(int size){
		mapCellSample = (Map<String, Object>) getDesktop().getAttribute(ATTRIBUTE_MAP_CELL_SAMPLE);
		int sizeColumns = (Integer)getDesktop().getAttribute(ATTRIBUTE_SIZE_COLUMNS);
		String [] letterArr = getArrRow();
		@SuppressWarnings("rawtypes")
		Set set = mapSizePlate.entrySet();
	    @SuppressWarnings("rawtypes")
		Iterator it=set.iterator();
	    int numberControlRandom = 0;
	    while(it.hasNext())
	    {
	        @SuppressWarnings("rawtypes")
			Map.Entry m =(Map.Entry)it.next();
	        String key = (String)m.getKey();
	        int sizePlate = (Integer)m.getValue();
	        Random randomGeneratorNum = new Random();
	        Random randomGeneratorLetter = new Random();
	        for (int idxN = 1; idxN <= size;){
	        	boolean isUnic = false;
	        	int randomInt = 0;
	        	String letterRandom = "";
	        	int idLoopRandom = 1;
	        	while (!isUnic) {
	        	randomInt = randomGeneratorNum.nextInt(sizeColumns);
	        	int indexLetter = (int) (randomGeneratorLetter.nextDouble()*letterArr.length);
	        	letterRandom = letterArr[indexLetter];
	        	String cellStr = letterRandom+"|"+randomInt;
		        	if (idLoopRandom <= 96) {
		        		if (!mapRandomControls.containsValue(cellStr)) {
			        			Cell cell = (Cell)mapCellSample.get(key+DELIMITER_PIE_SAMPLE+letterRandom+randomInt);
			    	        	if (cell != null && cell.getAttribute(ATTRIBUTE_SAMPLE_ITEM) != null && 
			                			(Integer)cell.getAttribute(ATTRIBUTE_SAMPLE_ITEM) 
			                			== PlateRow.ASSIGNED_NUM) {
			    	        		mapRandomControls.put(cellStr, cellStr);
			    	        		changingPropertieCellRandom(cell);
			    	        		idLoopRandom ++;
			                    	idxN++;
			                    	sizePlate--;
			                    	numberControlRandom++;
			                    	isUnic = true;
			    	        	}else {
			    	        		isUnic = false;
			    	        		idLoopRandom++;
			    	        	}
			        			
		        			}else {
		        				isUnic = false;
		        				idLoopRandom++;
		        			}
		        	}else if (idLoopRandom > 96){
		        		Cell cell = (Cell)mapCellSample.get(key+DELIMITER_PIE_SAMPLE+letterRandom+randomInt);
	    	        	if (cell != null && cell.getAttribute(ATTRIBUTE_SAMPLE_ITEM) != null && 
	                			(Integer)cell.getAttribute(ATTRIBUTE_SAMPLE_ITEM) 
	                			== PlateRow.ASSIGNED_NUM) {
	    	        		mapRandomControls.put(cellStr, cellStr);
	    	        		idLoopRandom++;
	    	        		changingPropertieCellRandom(cell);
	                    	idxN++;
	                    	sizePlate--;
	                    	numberControlRandom++;
	                    	isUnic = true;
	    	        	}else {
	    	        		isUnic = false;
	    	        		idLoopRandom++;
	    	        	}
			        		
		        	}else {
		        		isUnic = false;
		        		idLoopRandom++;
		        	}
		        	idLoopRandom++;
	        	}
	      
	        }
	        System.out.println("Map Cell :::"+mapRandomControls);
	        mapSizePlate.put(key, sizePlate);
	      
	    }
	    getDesktop().setAttribute(ATTRIBUTE_MAP_NUM_ITEM_SELECT, mapSizePlate);
	    getDesktop().setAttribute(ATTRIBUTE_NUMBER_SAMPLES_CONTROL, numberControlRandom);
	    
	}

	private void changingPropertieCellRandom(Cell cell) {
		cell.setAttribute(ATTRIBUTE_SAMPLE_ITEM, PlateRow.RANDOM_CONTROL_ITEM_NUM);
    	Image image = (Image)cell.getChildren().get(0).getChildren().get(1);
    	image.setSrc(URL_IMAGES_CONTROL_RANDOM_TUBE);
    	Label label = (Label)cell.getChildren().get(0).getChildren().get(2);
    	label.setValue(pro.getKey(LBL_STUDIES_RANDOM_TUBE));
	}
}
