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

public class PlateRow {
	public static final String KBIO_CONTROL_ITEM = "KBiosciences CONTROL";
	public static final String DART_CONTROL_ITEM = "DArT CONTROL";
	public static final String RANDOM_CONTROL_ITEM="RANDOM CONTROL";
	public static final String CONTROL_ITEM="CONTROL";
	public static final String BLANK_ITEM = "BLANK";
	public static final String ASSIGNED_ITEM = "A";
	public static final String PLATE_NAME_LABEL = "PLATE NAME";
	public static final int BLANK_ITEM_NUM = 0;
	public static final int ASSIGNED_NUM = 1;
	public static final int BLANK_CONTROL_NUM = 2;
	public static final int DART_CONTROL_ITEM_NUM = 3;
	public static final int KBIO_CONTROL_ITEM_NUM = 4;
	public static final int RANDOM_CONTROL_ITEM_NUM = 5;
	public static final int NOT_CONTROL = 6;
	//era public static final quite el final
	public static String PLATE_NAME_PREFIX = "PLATE";
	
//	public static final Color BLANK_COLOR = Display.getCurrent().getSystemColor(
//			SWT.COLOR_CYAN);	

//	public static final Color ASSIGNED_COLOR = Display.getCurrent().getSystemColor(
//			SWT.COLOR_YELLOW);	
	
//	public static final Color CONTROL_COLOR = Display.getCurrent().getSystemColor(
//			SWT.COLOR_GREEN);	
	
	public static final int COLS_PLATE_96 = 12;
	public static final int COLS_PLATE_384 = 24;
	
//	public static final Image ASSIGNED_TUBE =  ImageUtils.getImage(ImageUtils.ASSIGNED_TUBE);
//	public static final Image BLANK_TUBE =  ImageUtils.getImage(ImageUtils.BLANK_TUBE);
//	public static final Image DART_CONTROL_TUBE =  ImageUtils.getImage(ImageUtils.DART_CONTROL_TUBE);
//	public static final Image RANDOM_CONTROL_TUBE =  ImageUtils.getImage(ImageUtils.RANDOM_CONTROL_TUBE);
//	public static final Image CONTROL_TUBE =  ImageUtils.getImage(ImageUtils.CONTROL_TUBE);
//	public static final Image KBIO_CONTROL_TUBE =  ImageUtils.getImage(ImageUtils.KBIO_CONTROL_TUBE);
//	public static final Image SAMPLEID_REPEAT_ALERT=ImageUtils.getImage(ImageUtils.SAMPLEID_REPEAT_ALERT_TUBE);
	


	private String letter;
	private String[] values = new String[0];
	private int [] valuesInt = new int [0];
	private int plateSize;
	private int colsPerRow;

	public PlateRow(int plateSize) {
		this.plateSize =  plateSize;
		init();
	}

	private void init() {
		if (plateSize == PlateContentList.PLATE_SIZE_96) {
			colsPerRow = COLS_PLATE_96;
		} else {
			colsPerRow = COLS_PLATE_384;
		}
		values =  new String[colsPerRow];
		for (int i = 0; i < colsPerRow; i++) {
			values[i] = new String(BLANK_ITEM);
		}
		valuesInt = new int [colsPerRow];
		for (int i = 0 ; i < colsPerRow; i++)
		valuesInt [i] = BLANK_ITEM_NUM;
	}

	/**
	 * @return the letter
	 */
	public String getLetter() {
		return letter;
	}

	/**
	 * @param letter
	 *            the letter to set
	 */
	public void setLetter(String letter) {
		this.letter = letter;
	}

	/**
	 * @return the values
	 */
	public String[] getValues() {
		return values;
	}

	/**
	 * @param values
	 *            the values to set
	 */
	public void setValues(String[] values) {
		this.values = values;
	}

	public String getValue(int index) {
		return values[index];
	}

	/**
	 * @return the colsPerRow
	 */
	public int getColsPerRow() {
		return colsPerRow;
	}

	/**
	 * @param colsPerRow the colsPerRow to set
	 */
	public void setColsPerRow(int colsPerRow) {
		this.colsPerRow = colsPerRow;
	}

	public int[] getValuesInt() {
		return valuesInt;
	}

	public void setValuesInt(int[] valuesInt) {
		this.valuesInt = valuesInt;
	}

	
}
