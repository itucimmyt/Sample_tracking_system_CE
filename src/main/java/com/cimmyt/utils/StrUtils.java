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
package com.cimmyt.utils;

import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_INFORMATION;
import static com.cimmyt.utils.Constants.LBL_STUDIES_CONTROL_DART;
import static com.cimmyt.utils.Constants.LBL_STUDIES_CONTROL_KBIOSCIENCES;
import static com.cimmyt.utils.Constants.LBL_STUDIES_PLATE_ITEM_BLANK;
import static com.cimmyt.utils.Constants.LBL_STUDIES_PLATE_ITEM_CONTROL;
import static com.cimmyt.utils.Constants.LBL_STUDIES_RANDOM_TUBE;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.zkoss.zul.Messagebox;

import com.cimmyt.model.bean.LabStudy;
import com.cimmyt.study.PlateRow;

public class StrUtils {
	
	
	private static Pattern pattern;
	private static  Matcher matcher;
	private static SimpleDateFormat dateFormatStandar = new SimpleDateFormat("yyyy-MM-dd");

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	/**
	 * 
	 */
	public static boolean isEmpty(String value) {
		boolean result = true;
		if (value != null && value.trim().length() > 0) {
			result = false;
		}
		return result;
	}
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	public static boolean notEmpty(String value) {
		return value!= null && ! isEmpty(value);
	}

	public static boolean isNotEmpty (StringBuilder strBuilder){
		if (strBuilder != null && !strBuilder.toString().trim().equals("")){
			return true;
		}
		return false;
	}
	/**
	 * @param strBuilder
	 * @return
	 */
	public static boolean isEmpty(StringBuilder strBuilder){
		if (strBuilder == null || strBuilder.toString().trim().equals("")){
			return true;
		}
		return false;
	}
	public static void messageBox(String mess,PropertyHelper pro){
		Messagebox.show(mess,pro.getKey(LBL_GENERIC_MESS_INFORMATION), 
				Messagebox.OK, Messagebox.INFORMATION);	
	}
	
	public static void messageBoxError(String mess,PropertyHelper pro){
		Messagebox.show(mess,pro.getKey(Constants.LBL_GENERIC_MESS_ERROR), 
				Messagebox.OK, Messagebox.ERROR);	
	}
	public static String getDateFormat(Date date){
		if (date != null){
	        return dateFormatStandar.format(date);	
		}
		return dateFormatStandar.format(new Date());
	}

	public static Date getDateStandar(Date date) throws ParseException{
		return dateFormatStandar.parse(dateFormatStandar.format(date));
	}
	public static boolean isNumeric (StringBuilder strBuilder){
		try{
			Integer.parseInt(strBuilder.toString().trim());
			return true;
		}catch (NumberFormatException exFormat){
			return false;
		}	
	}

	/**
	 * @param strBuilder
	 * @return
	 */
	public static boolean isNumericIntegerPositive (StringBuilder strBuilder){
		try {
			Integer integer;
			integer = Integer.parseInt(strBuilder.toString());
			if (integer < 0)
			return false;
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

	/**
	 * Method that return the type of the 
	 * @param strType
	 * @return
	 */
	public static String getTypeOfControl (int index){
		String type = "";
		switch (index){
		case PlateRow.NOT_CONTROL :
			type = "N";
			break;
		case PlateRow.BLANK_CONTROL_NUM :
			type = "C";
			break;
		case PlateRow.RANDOM_CONTROL_ITEM_NUM :
			type = "R";
			break;
		case PlateRow.DART_CONTROL_ITEM_NUM:
			type = "D";
			break;
		case PlateRow.BLANK_ITEM_NUM :
			type = "B";
			break;
		case PlateRow.KBIO_CONTROL_ITEM_NUM :
			type = "K";
			break;
			default:
				type = "";
				break;
		}
		return type;
	}

	/**
	 * Method that return the type of the 
	 * @param strType
	 * @return
	 */
	public static int getTypeIdentifierToSample(String strType){
		int type = 0;
		char charStr = strType.charAt(0);
		switch (charStr){
		case 'N':
			type = PlateRow.NOT_CONTROL;
			break;
		case 'C':
			type = PlateRow.BLANK_CONTROL_NUM;
			break;
		case 'R':
			type = PlateRow.RANDOM_CONTROL_ITEM_NUM;
			break;
		case 'D':
			type = PlateRow.DART_CONTROL_ITEM_NUM;
			break;
		case 'B':
			type = PlateRow.BLANK_ITEM_NUM;
			break;
		case 'K':
			type = PlateRow.KBIO_CONTROL_ITEM_NUM;
			break;
			default:
				type = PlateRow.ASSIGNED_NUM;
				break;
		}
		return type;
	}

	public static String getSampleIDKey(LabStudy labStudy, Integer sampleId){
		String strSampleId = labStudy.getProject().getProjectname()
				+labStudy.getProject().getPurposename()+sampleId;
				return strSampleId.toUpperCase();			
	}
	
	/**
	 * Method that return the label depend of the index sample
	 * @param index
	 * @param pro
	 * @return
	 */
	public static String getStringByIndexLoad (int index,  PropertyHelper pro){
		String type = "";
		switch (index){
		case PlateRow.NOT_CONTROL :
			type = "N";
			break;
		case PlateRow.BLANK_CONTROL_NUM :
			type = pro.getKey(LBL_STUDIES_PLATE_ITEM_CONTROL);
			break;
		case PlateRow.RANDOM_CONTROL_ITEM_NUM :
			type = pro.getKey(LBL_STUDIES_RANDOM_TUBE);
			break;
		case PlateRow.DART_CONTROL_ITEM_NUM:
			type = pro.getKey(LBL_STUDIES_CONTROL_DART);
			break;
		case PlateRow.BLANK_ITEM_NUM :
			type = pro.getKey(LBL_STUDIES_PLATE_ITEM_BLANK);
			break;
		case PlateRow.KBIO_CONTROL_ITEM_NUM :
			type = pro.getKey(LBL_STUDIES_CONTROL_KBIOSCIENCES);
			break;
			default:
				type = "";
				break;
		}
		return type;
	}

	public static boolean isValidEmail(String email){
		pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
	        return matcher.matches();
	}

	public static String getCropById(int id){
		if (id == 2)
			return "W";
		else 
			return "M";
	}
}
