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
package com.cimmyt.study;

import java.util.ArrayList;
import java.util.List;

public class PlateContentList {

	private List<PlateRow> plateRows = new ArrayList<PlateRow>();
	private int increment;
	public static final int PLATE_SIZE_96 = 96;
	public static final int PLATE_SIZE_384 = 384;

	public static final String[] letters96 = { "A", "B", "C", "D", "E", "F", "G", "H" };
	public static final String[] letters384 = { "A", "B", "C", "D", "E", "F", "G", "H",
			"I", "J", "K", "L", "M", "N", "O","P" };
	
	public String[] letters = null;	
	
	private int plateSize;
	
	private String plateName;
	
	private int plateNumber;
	private String plateNamePrefix="";

	public PlateContentList(int plateSize, int plateNumber, String plateName) {
		this.plateSize = plateSize;
		this.plateNumber = plateNumber;
		this.plateNamePrefix = plateName; 
		init();
	}

	private void init() {
		
		plateName = new String(plateNamePrefix + plateNumber);
		
		if (plateSize == PLATE_SIZE_96) {
			increment = 8;
			letters = letters96;
		} else {
			increment = 16;
			letters =  letters384;			
		}
		
		int totalPlates = letters.length;
		int letter = 0;
		for (int i = 0; i < totalPlates; i++) {
			if (letter >= letters.length) {
				letter =0;
			}			
			PlateRow plateRow = new PlateRow(plateSize);
			plateRow.setLetter(letters[letter]);
			plateRows.add(plateRow);
			letter++;
			
		}
	}
	
	/**
	 * Returns the index of the letter in the array
	 * @param letter Letter to find in the array
	 * @return index position in array, -1 if not found
	 */
	public  int getColIndex(String letter) {
		int result = -1;
		int index = 0;
		for (String let : letters) {
			if (let.equals(letter)) {
				result = index;
				break;
			}
			index++;
		}
		return result;
	}

	/**
	 * @return the plateRows
	 */
	public List<PlateRow> getPlateRows() {
		return plateRows;
	}

	/**
	 * @param plateRows
	 *            the plateRows to set
	 */
	public void setPlateRows(List<PlateRow> plateRows) {
		this.plateRows = plateRows;
	}

	/**
	 * @return the plateSize
	 */
	public int getPlateSize() {
		return plateSize;
	}

	/**
	 * @param plateSize the plateSize to set
	 */
	public void setPlateSize(int plateSize) {
		this.plateSize = plateSize;
	}

	/**
	 * @return the plateName
	 */
	public String getPlateName() {
		return plateName;
	}

	/**
	 * @param plateName the plateName to set
	 */
	public void setPlateName(String plateName) {
		this.plateName = plateName;
	}
}
