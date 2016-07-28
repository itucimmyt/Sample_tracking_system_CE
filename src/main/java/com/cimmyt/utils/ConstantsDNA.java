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

import com.cimmyt.utils.PropertyHelper.Bundle;

public class ConstantsDNA {

	public static PropertyHelper propertyHelper = new PropertyHelper();
    private static Bundle conf = Bundle.conf;
	public static final String DELIMITER_PIE = "\\|";
	public static final String DELIMITER_PIE_SAMPLE = "|";
	public static final int SIZE_MIN_RANDOM = 2;
	public static final char DELIMITER_COMA = ',';
	public static final char FILE_SIZE_COLUMN_MIN = 7;

	public static final int ROLE_ADMINISTRATOR = 1;
	public static final int ROLE_DATA_MANAGER = 2;
	public static final int ROLE_RESEARCHER = 3;
	public static final int ROLE_RESEARCHER_ASSISTENT = 4;
	public static final int ROLE_ASSISTENT = 5;

	//Number to determinate the identifier to location mixture
	public static final int NUMBER_LOCATION_TO_MIXTURE = 1;
	//variable to indicate the position in the file to GID
	public static final int COLUMN_POSITION_GID = 0;// Integer.parseInt( propertyHelper.getKey( "column.position.file.gid", conf ).trim());
	// ACC
	public static final int COLUMN_POSITION_ACC = 1;
	//variable to indicate the position in the file to Plant number
	public final static int COLUMN_POSITION_PLANT_NUMBER = 2;//Integer.parseInt( propertyHelper.getKey( "column.position.file.plant.number", conf ).trim());
	// SPECIE
	public static final int COLUMN_POSITION_SPECIE = 3 ;
	// ENTRY NUMBER
		public static final int COLUMN_POSITION_COMMENT = 4 ;
	// ENTRY NUMBER
	public static final int COLUMN_POSITION_ENTRY_NUMBER = 5 ;
	//variable to indicate the position in the file to Location
	public final static int COLUMN_POSITION_LOCATION = 6; // Integer.parseInt( propertyHelper.getKey( "column.position.file.location", conf ).trim());
	//variable to indicate the position in the file to Season
	public final static int COLUMN_POSITION_SEASON = 7; //Integer.parseInt( propertyHelper.getKey( "column.position.file.season", conf ).trim());
	
	public final static int NUMBER_BY_DEFAULT_PLANT_NUMBER = 1 ;
	//variable to up load file control 
	public static final int CONTROL_TYPE_POSITION_FC = 0;
	//variable to up load plate name control 
	public static final int PLATE_NAME_POSITION_FC = 1;
	//variable to up load row control
	public static final int ROW_POSITION_FC = 2;
	//variable to up load column control
	public static final int COLUMN_POSITION_FC = 3;
	//variable to up load gid control
	public static final int GID_POSITION_FC = 4;
	//variable to up load acc control
	public static final int ACC_POSITION_FC = 5;
	//variable to up load plant no control
	public static final int PLANT_NO_POSITION_FC = 6;
	//variable to up load  specie control
	public static final int SPECIE_POSITION_FC = 7;
	//variable to up load comments control
	public static final int COMMENTS_POSITION_FC = 8;
	//variable to up load  entry no control
	public static final int ENTRY_NO_POSITION_FC = 9;
	//variable to up load  location control
	public static final int LOCATION_NO_POSITION_FC = 10;
	//variable to up load  season control
	public static final int SEASON_NO_POSITION_FC = 11;
	//Number to determinate the identifier to season mixture
	public static final int NUMBER_SEASON_TO_MIXTURE = 1;

	//prefix to put identifier at label in the cell
	public static final String PREFIX_LABEL = "idLabel";
	// prefix to put identifier at check box in the cell
	public static final String PREFIX_CHECK = "idCheck";
	// prefix to put identifier at   the cell
	public static final String PREFIX_IMAGE = "idImage";
	//prefix to put identifier at popup
	public static final String PREFIX_POPUP = "idPopup";
	//value to management of status id
	public static final String ID_STATUS = "N";
	//value to management of status description 
	public static final String DESCRIPTION_STATUS = "Active";
	// variable to up load the file only sample
	public static final int FILE_UP_LOAD_ONLY_SAMPLES = 1;
	// variable to up load the file whit samples and controls
	public static final int FILE_UP_LOAD_SAMPLES_CONTROL = 2;
	// variable to up load the file by columns
	public static final int FILE_UP_LOAD_SAMPLES_BY_COLUMNS = 1;
	// variable to up load the file by columns
	public static final int FILE_UP_LOAD_SAMPLES_BY_ROW = 2;
	// variable to up load the file by zig zag
	public static final int FILE_UP_LOAD_SAMPLES_BY_ZIGZAG = 3;
	//constant to put the size of plate 96
	public static final int SIZE_PLATE_96 = 96;
	//constant to put the size of plate 384
	public static final int SIZE_PLATE_384 = 384;
	//constants to put type file pdf
	public static final int FILE_TYPE_PDF = 0;
	//constants to put type file xls
	public static final int FILE_TYPE_XLS = 1;
	//constants to put type file CSV
	public static final int FILE_TYPE_CSV = 2;
	//constants to put type file CSV
	public static final int FILE_TYPE_CSV_GENERIC = 3;
	//constants to put type control in char to individual control
	public static final char INDIVIDUAL_CONTROL = 'C';
	//constants to put type control in char to random control
	public static final char RANDOM_CONTROL = 'R';
	//constants to put type control in char to DArT control
	public static final char DART_CONTROL = 'D';
	//constants to put type control in char to KBIOSCIENCIES control
	public static final char KBIOSCIENCIES_CONTROL = 'K';
	//constants to put type control in char to not control
	public static final char NOT_CONTROL = 'N';
	public static final char BANK_CONTROL = 'B';
	//constants to management of type data for fields results constants
	public static final String RESULT_FIELD_CONSTANT_NUMERIC = "N";
	public static final String RESULT_FIELD_CONSTANT_CHARACTER = "C";
	public static final String RESULT_FIELD_CONSTANT_STRING = "S";
	
	// result data
	public static final String ATTRIBUTE_RESULT_DATA_ITEM = "attribute.results.data.item";
	public static final String ATTRIBUTE_RESULT_DATA_INDEX = "attribute.results.data.index";
	public static final String LBL_MENU_RESULTS_ALL = "lbl.menu.results.all";
	public static final String LBL_RESULTS_DATA_SAVE = "lbl.result.data.configuration.save";
	public static final String LBL_RESULTS_SELECT_PLATE = "lbl.result.data.select.plate";
	public static final String ATTRIBUTE_RESULT_DATA_BEAN_REPORT = "attribute.results.data.bean.report";
	public static final String LBL_RESULTS_DATA_PROBLEM_HEADER = "lbl.result.data.header.problem";
	public static final String LBL_RESULTS_DATA_PROBLEM_BODY = "lbl.result.data.header.body";
	public static final String LBL_RESULTS_DATA_PROBLEM_ROW_EMPTY = "lbl.result.data.row.empty";
	public static final String LBL_RESULTS_DATA_SIZE_SAMPLES = "lbl.result.data.error.size.samples";
	public static final String LBL_RESULTS_DATA_MATCHE_SAMPLE = "lbl.result.data.error.matche.samples";
	public static final String LBL_RESULTS_DATA_MATCHE_HEADER = "lbl.result.data.error.matche.header";
	public static final String LBL_RESULTS_DATA_SIZE_FIELD = "lbl.result.data.error.size.file";
	public static final String LBL_RESULTS_DATA_ERR_FIRST_COLUM_SAMPID = "lbl.result.data.error.first.column.sample";

	//temporal sample
	public static final String LBL_TEMPORAL_SAMPLE_WINDOW_TITLE = "lbl.temporal.sample.window.title";
	public static final String LBL_TEMPORAL_SAMPLE_MESSAGE_ASSIGN = "lbl.temporal.sample.message.assign";
	public static final String ATTRIBUTE_SAMPLE_TEMPORAL = "attribute.sample.temporal";
	public static final String ATTRIBUTE_SAMPLE_TEMPORAL_ITEM = "attribute.sample.temporal.sample";
	public static final String LBL_TEMPORAL_SAMPLE_MESSAGE_ERR_LOCATION = "lbl.temporal.sample.message.error.location";
	public static final String LBL_TEMPORAL_SAMPLE_MESSAGE_ERR_SEASON = "lbl.temporal.sample.message.error.season";

	public static final String LBL_ROLE_DESCRPTION_ADMINISTRATOR = "lbl.role.description.administrator";
	public static final String LBL_ROLE_DESCRPTION_DATA_MANAGER = "lbl.role.description.datamanager";
	public static final String LBL_ROLE_DESCRPTION_RESEARCHER = "lbl.role.description.researcher";
	public static final String LBL_ROLE_DESCRPTION_RESEARCHER_ASSISTANT = "lbl.role.description.researcherAssistant";
	public static final String LBL_ROLE_DESCRPTION_ASSISTANT = "lbl.role.description.assistant";
	
	//Register User
	public static final String LBL_REGISTER_ERROR_EMAIL_INVALID = "lbl.register.user.email.invalid" ;
	public static final String LBL_REGISTER_ERROR_EMAIL_REPIT = "lbl.register.user.email.repit" ;
	public static final String LBL_REGISTER_ERROR_USER_REPIT = "lbl.register.user.name.repit" ;
	public static final String LBL_REGISTER_ERROR_EMAIL_NOT_EQUAL = "lbl.register.user.email.not.equals" ;
	public static final String LBL_REGISTER_ERROR_FILEDS_SIZE = "lbl.register.user.fields.size" ;
	public static final String LBL_REGISTER_ERROR_PASSWORD_NOT_EQUAL = "lbl.register.user.password.not.equals" ;
	public static final String  LBL_REGISTER_ERROR_AGREEMENT = "lbl.register.agreement.error";

	public static final String LBL_USER_MODULE_USER_NAME = "lbl.register.user.username";
	public static final String LBL_USER_MODULE_PASSWORD = "lbl.register.user.password";
	public static final String LBL_USER_MODULE_EMAIL = "lbl.generic.message.email";
	public static final String LBL_USER_ERROR_ALREADY_RECORDED = "lbl.investigators.sub.window.user.already.record";
	
}

