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

import static com.cimmyt.utils.Constants.ATTRIBUTE_MAIZE;
import static com.cimmyt.utils.Constants.ATTRIBUTE_WHEAT;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_INFORMATION;
import static com.cimmyt.utils.Constants.LBL_SEL_iTE_CORN;
import static com.cimmyt.utils.Constants.LBL_SEL_iTE_WHEAT;
import static com.cimmyt.utils.Constants.LBL_STUDIES_CONTROL_DART;
import static com.cimmyt.utils.Constants.LBL_STUDIES_CONTROL_KBIOSCIENCES;
import static com.cimmyt.utils.Constants.LBL_STUDIES_PLATE_ITEM_BLANK;
import static com.cimmyt.utils.Constants.LBL_STUDIES_PLATE_ITEM_CONTROL;
import static com.cimmyt.utils.Constants.LBL_STUDIES_RANDOM_TUBE;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
	private static final String PATTERN_SAMPLE = "(\\d+)$";
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

	public static Date getDateStandarFromStr(String date) throws ParseException{
		return dateFormatStandar.parse(date);
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
				+labStudy.getProject().getPurposename()+ (labStudy.isUsePadded() ? getPaddingCeros(sampleId) : sampleId);
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

	public static String getPaddingCeros(Integer value){
		final int Totalsize = 6;
		if (value == null || value.intValue() == 0)
			return "";
		int size = value.toString().length();
		StringBuffer strBuffer = new StringBuffer();
		for (int index = size; index < Totalsize; index++){
			strBuffer.append("0");
		}
		strBuffer.append(value.intValue());
		return strBuffer.toString();
	}

	/**
	 * Method that look for sample id in a string
	 * @param input
	 * @return
	 */
	public static int getSampleIDFindString(String input){
		 pattern = Pattern.compile( PATTERN_SAMPLE);
		 matcher = pattern.matcher(input);
		if (matcher.find())
			return Integer.parseInt(matcher.group());
		return 0;
	}

	/**
	 * Method that return a string look at pattern 
	 * @param input
	 * @return
	 */
	public static String getPrefixSampleFindString(String input){
		pattern = Pattern.compile( PATTERN_SAMPLE);
		matcher = pattern.matcher(input);
		if (matcher.find())
			return input.substring(0, input.indexOf(matcher.group()));
		return "";
	}

	public static int getYear(){
		Date date = new Date ();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		year =year- 2010;
		return year;
	}

	public static boolean isRoleAdminOrDataManager(int idRole){
		switch (idRole){
		case ConstantsDNA.ROLE_ADMINISTRATOR:
		case ConstantsDNA.ROLE_DATA_MANAGER:
			return true;
			default :
				return false;
		}
	}

	public static String getCrop(int typeCorp, PropertyHelper prop){
		switch (typeCorp){
			case ATTRIBUTE_MAIZE:
				return prop.getKey(LBL_SEL_iTE_CORN);	
			case ATTRIBUTE_WHEAT:
				return prop.getKey(LBL_SEL_iTE_WHEAT);
			default :
				return prop.getKey(LBL_SEL_iTE_WHEAT);
		}
	}
}
