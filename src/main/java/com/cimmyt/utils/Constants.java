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

public class Constants {
	public static PropertyHelper propertyHelper = new PropertyHelper();
    private static Bundle conf = Bundle.conf;
    
    public final static String LBL_PREFS_TITLE = "lbl.menu.preferens";
    public final static String LBL_PREFS_CROP = "lbl.menu.crop";
	public final static int LOG_LEVEL = Integer.parseInt( propertyHelper.getKey( "log.level", conf ).trim());
	public final static String LocaleSpanish = "MX";
	public final static String LocaleEnglish = "US";
	public final static String LOCALE_LANGUAGE = "locale";
	public final static String DEFAULT_CROP = "default.crop";
	public final static String SELECT_OPTION_MENU = "select.option.menu";
	public final static int SHOW_ROWS_LIST = Integer.parseInt( propertyHelper.getKey( "show.rows.list", conf ).trim());
	public final static String ATTRIBUTE_NAME_USER_BEAN = "attribute.name.user.bean";
	public final static String ATTRIBUTE_PARAM_REPORT = "attribute.param.report";
	public final static String ATTRIBUTE_PARAM_MAP_FUNTION = "attribute.param.map.funtion.by.user";
	public final static String [] ARR_DART_CONTROL = propertyHelper.getKey("dart.controls",conf).trim().split(",");
	public final static String [] ARR_KBIOS_CONTROL = propertyHelper.getKey("kbioscience.controls",conf).trim().split(",");
	public final static String [] ARR_INTERTEK_CONTROL = propertyHelper.getKey("intertek.controls",conf).trim().split(",");
	public final static String [] ARR_CIMMYT_CONTROL_EMPTY_96 = propertyHelper.getKey("cimmyt.controls.empty.96",conf).trim().split(",");
	public final static String [] ARR_CIMMYT_CONTROL_EMPTY_384 = propertyHelper.getKey("cimmyt.controls.empty.384",conf).trim().split(",");
	public final static short ATTRIBUTE_MAIZE = 1;
	public final static short ATTRIBUTE_WHEAT = 2;
	public final static short ATTRIBUTE_MAIZE_ADMINISTRATOR = 1;
	public final static short ATTRIBUTE_WHEAT_ADMINISTRATOR = 4;
	public final static String NAME_COMPANY_SEQUENCE = "name.company.sequence";
	public final static String REPORT_PATH_KBIO = "report.path.kbio";
	public final static String REPORT_PATH_KBIO_G96 = "report.path.kbio.grid.96";
	public final static String REPORT_PATH_KBIO_G384 = "report.path.kbio.grid.384";
	public final static String REPORT_PATH_INTERTEK_G96 = "report.path.intertek.format.96";
	public final static String REPORT_PATH_GENOTYPING_SERVICES_96 = "report.path.genotyping.services.format.96";
	
	//images 
	public final static String URL_IMAGES_BLANK_TUBE = propertyHelper.getKey("img.url.tube.blank",Bundle.img);
	public final static String URL_IMAGES_ASSIGNED_TUBE = propertyHelper.getKey("img.url.tube.assigned",Bundle.img);
	public final static String URL_IMAGES_CONTROL_TUBE = propertyHelper.getKey("img.url.tube.control",Bundle.img);
	public final static String URL_IMAGES_DART_CONTROL_TUBE = propertyHelper.getKey("img.url.tube.control.dart",Bundle.img);
	public final static String URL_IMAGES_KBIO_CONTROL_TUBE = propertyHelper.getKey("img.url.tube.control.kbios",Bundle.img);
	public final static String URL_IMAGES_CONTROL_RANDOM_TUBE = propertyHelper.getKey("img.url.tube.random",Bundle.img);
	public final static String URL_IMAGES_REPEAT_SAMPLE = propertyHelper.getKey("img.url.tube.repeated",Bundle.img);
	public final static String URL_IMAGE_DELETE = propertyHelper.getKey("img.url.delete",Bundle.img);
	public final static String URL_IMAGES_ADD_BUTTON = propertyHelper.getKey("img.url.add",Bundle.img);
	public final static String URL_IMAGES_DEL_BUTTON = propertyHelper.getKey("img.url.deleteitem",Bundle.img);
	public final static String URL_IMAGES_SEND_BUTTON = propertyHelper.getKey("img.url.button.send",Bundle.img);
	public final static String URL_IMAGES_RECE_BUTTON = propertyHelper.getKey("img.url.button.receive",Bundle.img);
	public final static String URL_IMAGES_CANC_BUTTON = propertyHelper.getKey("img.url.button.cancel",Bundle.img);
	public final static String URL_IMAGES_CSV_BUTTON = propertyHelper.getKey("img.url.download.csv",Bundle.img);
	public final static String URL_IMAGES_XLS_BUTTON = propertyHelper.getKey("img.url.download.excel",Bundle.img);
	public final static String URL_IMAGES_OK_BUTTON = propertyHelper.getKey("img.url.ok",Bundle.img);

	public final static String URL_IMAGES_PROGRAM = propertyHelper.getKey("img.url.project.program",Bundle.img);
	public final static String URL_IMAGES_PURPOSE = propertyHelper.getKey("img.url.project.purpose",Bundle.img);
	
	//bean spring
	public static final String PROJECT_SERVICE_BEAN_NAME = "serviceProject";
	public static final String LABSTUDY_SERVICE_BEAN_NAME = "serviceLabStudy";
	public static final String INVESTIGATOR_SERVICE_BEAN_NAME = "serviceInvestigator";
	public static final String USER_SERVICE_BEAN_NAME = "serviceUser";
	public static final String TISSUE_SERVICE_BEAN_NAME = "serviceTissue";
	public static final String LOCATION_SERVICE_BEAN_NAME = "serviceLocation";
	public static final String SAMPLE_SERVICE_BEAN_NAME = "serviceSample";
	public static final String SEASON_SERVICE_BEAN_NAME = "serviceSeason";
	public static final String STUDY_TEMPLATE_SERVICE_BEAN_NAME = "seriviceStudyTemplate";
	public static final String COMPANY_SERVICE_BEAN_NAME = "serviceCompany";
	public static final String SHIPMENT_SERVICE_BEAN_NAME = "serviceShipment";
	public static final String SHIPMENT_SERVICE_SET_BEAN_NAME = "serviceShipmentSet";
	public static final String SHIPMENT_SERVICE_DETAIL_BEAN_NAME = "serviceShipmentDetail";
	public static final String STORAGE_LOCATION_SERVICE_BEAN_NAME = "serviceStorageLocation";
	public static final String SAMPLE_DETAIL_SERVICE_BEAN_NAME = "serviceSampleDetail";
	public static final String ORGANISM_SERVICE_BEAN_NAME = "serviceOrganism";
	public static final String LAST_PLATE_PROJECT_SERVICE_BEAN_NAME = "serviceLastPlateProject";
	public static final String MANAGER_FILE_CSV_SERVICE_BEAN_NAME = "serviceManagerFileCSV";
	public static final String LOAD_STUDY_SERVICE_BEAN_NAME = "serviceLoadStudy";
	public static final String CREATE_PLATE_SERVICE_BEAN_NAME = "serviceCreatePlate";
	public static final String CONTROL_FILE_SERVICE_MANAGEMENT = "serviceControlFile";
	public static final String RESULT_PREFERENCE_SERVICE = "serviceResultPreference";
	public static final String RESULT_DATA_SERVICE = "serviceResultData";
	public static final String REPORT_KBIO_SERVICE = "serviceReportKBio";
	public static String ERROR_MESSAGE_CREATION_BEAN = "error.message.creation.bean";
	public static final String TEMPORAL_SAMPLE_SERVICE_BEAN_NAME = "serviceTemporalSample";
	public static final String STATUS_SERVICE_BEAN_NAME = "serviceStatus";
	public static final String BMS_SERVICE_CLIENT = "serviceBMSClient";
	public static final String SERVICE_LOGIN = "serviceLogin";
	public static final String REGISTER_SERVICE_BEAN = "serviceRegister";
	public static final String LOG_SERVICE_BEAN = "serviceLog";
	public static final String MYSQLPROCEDURE_SERVICE_BEAN_NAME = "mySqlProcedure";
	
	//Constants Generic
	public static final String LBL_GENERIC_MESS_FILL_FIELD = "lbl.generic.message.fill.fields";
	public static final String LBL_GENERIC_MESS_SELECT_RECORD = "lbl.generic.message.select.item";
	public static final String LBL_GENERIC_MESS_PLEASE_CONFIRM = "lbl.generic.message.please.confirm";
	public static final String LBL_GENERIC_MESS_DELETE_RECORD = "lbl.generic.message.delete.record";
	public static final String LBL_GENERIC_MESS_DELETE_SUCCESS = "lbl.generic.message.delete.success";
	public static final String LBL_GENERIC_MESS_CHANGED_STATUS_SUCCESS = "lbl.generic.message.changes.status.success";
	
	public static final String LBL_GENERIC_MESS_DELETE_ERROR = "lbl.generic.message.delete.error";
	public static final String LBL_GENERIC_MESS_INFORMATION = "lbl.generic.message.information";
	public static final String LBL_GENERIC_MESS_LOGIN = "lbl.generic.message.login";
	public static final String LBL_GENERIC_MESS_ERROR = "lbl.generic.message.error";
	public static final String LBL_GENERIC_MESS_ERROR_CRITERIAL = "lbl.generic.message.criteria.search";
	public static final String LBL_GENERIC_MESS_EMPTY_RESULT = "lbl.generic.message.result.empty";
	public static final String LBL_GENERIC_MESS_FIELD_REQUIRED = "lbl.generic.message.filed.required";
	public static final String LBL_GENERIC_MESS_EDIT_REC_ERROR ="lbl.generic.message.edit.record.error";
	public static final String LBL_GENERIC_MESS_PROJECT =       "lbl.generic.message.project";
	public static final String LBL_GENERIC_MESS_INVESTIGATOR ="lbl.generic.message.investigator";
	public static final String LBL_GENERIC_MESS_ERROR_ASSOCIATED ="lbl.generic.message.error.associated";
	public static final String LBL_GENERIC_MESS_EDIT ="lbl.generic.message.edit";
	public static final String LBL_GENERIC_MESS_DELETE ="lbl.generic.message.delete";
	public static final String LBL_GENERIC_TISSUES ="lbl.menu.tissues";
	public static final String LBL_GENERIC_TISSUES_DESC ="lbl.menu.tissues.desc";
	public static final String LBL_GENERIC_LOCATION ="lbl.menu.locations";
	public static final String LBL_GENERIC_LOCATION_DESC ="lbl.menu.locations.desc";
	public static final String LBL_GENERIC_STUDY_TEMPLATE ="lbl.menu.study.templates";
	public static final String LBL_GENERIC_STUDY_TEMPLATE_DESC ="lbl.menu.study.templates.desc";
	public static final String LBL_GENERIC_STORAGE_LOCATION ="lbl.menu.storage.locations";
	public static final String LBL_GENERIC_STORAGE_LOCATION_DESC ="lbl.menu.storage.locations.desc";
	public static final String LBL_GENERIC_STUDY="lbl.menu.studies";
	public static final String LBL_GENERIC_STUDY_DESC ="lbl.menu.studies.desc";
	public static final String LBL_GENERIC_SEASONS ="lbl.menu.Seasons";
	public static final String LBL_GENERIC_SEASONS_DESC ="lbl.menu.Seasons.desc";
	public static final String LBL_GENERIC_QUERIES ="lbl.menu.queries";
	public static final String LBL_GENERIC_QUERIES_DESC ="lbl.menu.queries.desc";
	public static final String LBL_GENERIC_HISTORY_TITLE ="lbl.history.shipping.title";
	public static final String LBL_GENERIC_HISTORY_DESC ="lbl.history.shipping.desc";
	public static final String LBL_GENERIC_MESS_ER_DIF_REG ="lbl.generic.message.erros.dif.registered";
	public static final String LBL_GENERIC_MESS_PURPOSE ="lbl.generic.message.purpose";
	public static final String LBL_GENERIC_MESS_NUMERIC ="lbl.generic.message.numeric";
	public static final String LBL_GENERIC_MESS_CARCATER ="lbl.generic.message.caracter";
	public static final String LBL_GENERIC_MESS_DATE ="lbl.generic.message.date";
	public static final String LBL_GENERIC_COMPANIES = "lbl.menu.companies";
	public static final String LBL_GENERIC_COMPANIES_DESC= "lbl.menu.companies.desc";
	public static final String LBL_GENERIC_SHIPMENTS= "lbl.menu.shipment.management";
	public static final String LBL_GENERIC_SHIPMENTS_DESC= "lbl.menu.shipment.management.desc";
	public static final String LBL_GENERIC_PROJECTS="lbl.menu.projects";
	public static final String LBL_GENERIC_PROJECTS_DESC="lbl.menu.projects.desc";
	public static final String LBL_GENERIC_INVESTIGATORS="lbl.menu.investigators";
	public static final String LBL_GENERIC_INVESTIGATORS_DESC="lbl.menu.investigators.desc";
	public static final String LBL_GENERIC_SHOW_INFORMATION = "lbl.generic.message.show.information";
	public static final String LBL_GENERIC_MEN_NOT_INF_M = "lbl.generic.message.not.inform.move";
	public static final String LBL_GENERIC_MEN_COL_CONT_TYPE = "lbl.generic.message.column.control.type";
	public static final String LBL_GENERIC_MEN_PLATE_NAME = "lbl.generic.message.plate.name";
	public static final String LBL_GENERIC_MEN_COL_ROW_ONE = "lbl.generic.message.column.row.one";
	public static final String LBL_GENERIC_MEN_COL_COL = "lbl.generic.message.column.column";
	public static final String LBL_GENERIC_MEN_COL_GID = "lbl.generic.message.column.gid";
	public static final String LBL_GENERIC_MEN_COL_ACC = "lbl.generic.message.column.acc";
	public static final String LBL_GENERIC_MEN_COL_PLANT_NO = "lbl.generic.message.column.plant.no";
	public static final String LBL_GENERIC_MEN_COL_SPECIE = "lbl.generic.message.column.specie";
	public static final String LBL_GENERIC_MEN_COMMENTS = "lbl.generic.message.comments";
	public static final String LBL_GENERIC_MEN_COL_ENTRY_NO = "lbl.generic.message.column.entry.no";
	public static final String LBL_GENERIC_MEN_LOCATION = "lbl.generic.message.location";
	public static final String LBL_GENERIC_MEN_COL_SEASON = "lbl.generic.message.column.season";
	public static final String LBL_GENERIC_MEN_FUNTION_AVAILABLE_ONLY_EDIT = "lbl.generic.message.cant.edit.available";
	public static final String WINDOW_FIELD_ITEM="lbl.generic.window.err.item";
	public static final String ATTRIBUTE_GENERIC_NAME="attribute.generic,name";
	public static final String WINDOW_ERR_DESKTOP="lbl.generic.window.err.desktop";
	public static final String LBL_GENERIC_WINDOW_ERR_EXIST="lbl.generic.window.err.name.exist";
	public static final String LBL_GENERIC_WINDOW_ERR_SELECT_FIELD="lbl.generic.window.err.select.field";
	public static final String LBL_GENERIC_PLATE = "lbl.menu.plate";
	public static final String LBL_GENERIC_EXPORT = "lbl.menu.ressults.export";
	public static final String REG_EXP_PEDIGREE = "textinput.valid.regexp.simpleSymbols";
	public static final String REG_EXP_ALPHANUMERIC = "textinput.valid.regexp.noSymbols";
	public static final String LBL_GENERIC_MESS_DELETE_STUDY_SHIPMENT ="lbl.generic.message.delete.error.relation.shipment";
	public static final String LBL_GENERIC_MESS_STUDY_NAME ="lbl.generic.message.study.name";
	public static final String LBL_GENERIC_WINDOW_QUESTION = "lbl.generic.window.question";
	public static final String LBL_GENERIC_WINDOW_ERROR_FILL_FILDS="lbl.generic.window.error.fields";
	public static final String LBL_GENERIC_WINDOW_CONFIRM_CONTINUE ="lbl.generic.window.confirm.continue";
		
	//Constants to welcome
	public static final String LBL_WELCOME_DNAST = "lbl.welcome.dnast";
	public static final String LBL_WELCOME_WELCOME = "lbl.welcome.welcome";
	public static final String LBL_WELCOME_TITLE = "lbl.welcome.title";
	public static final String LBL_WELCOME_TEXT = "lbl.welcome.text";
	public static final String LBL_SELECT_CULTIVO = "lbl.welcome.select.cultivo";
	public static final String LBL_SELECT_lANGUAGE = "lbl.welcome.select.lenguage";
	public static final String LBL_SELECT_lAN_SPA = "lbl.welcome.item.lenguage.spanish";
	public static final String LBL_SELECT_lAN_ENG = "lbl.welcome.item.lenguage.english";
	public static final String LBL_SEL_iTE_CORN = "lbl.welcome.item.lenguage.corn";
	public static final String LBL_SEL_iTE_WHEAT = "lbl.welcome.item.lenguage.wheat";
	public static final String LBL_TOOLTIP_GO = "lbl.welcome.item.go";
	public static final String LBL_WELCOME_USER = "lbl.welcome.user";
	public static final String LBL_WELCOME_PASS = "lbl.welcome.password";
	public static final String LBL_WELCOME_CIU = "lbl.welcome.science.informatic.unit";
	public static final String LBL_WELCOME_NA_AP = "lbl.welcome.name.aplication";
	public static final String LBL_WELCOME_MES_ERROR = "lbl.welcome.mess.error.user.pass";
	public static final String LBL_WELCOME_PASS_INCORRECT = "lbl.welcome.mess.error.user.pass.incorrect";
	public static final String LBL_WELCOME_PASS_DISABLED = "lbl.welcome.mess.error.user.disabled";
	public static final String LBL_WELCOME_REGISTER = "lbl.welcome.register";
	public static final String LBL_WELCOME_FORGOT_PASS = "lbl.welcome.forgot.pass";
	public static final String LBL_WELCOME_HEADER = "lbl.welcome.header";
	public static final String LBL_WELCOME_LOGIN = "lbl.welcome.login";
	
	// Constants to Menu Toolts

	public static final String LBL_MENU_TOOL_ADD = "lbl.menu.tool.add";
	public static final String LBL_MENU_TOOL_EDIT = "lbl.menu.tool.edit";
	public static final String LBL_MENU_TOOL_DELETE = "lbl.menu.tool.delete";
	public static final String LBL_MENU_TOOL_BAR_CODE = "lbl.menu.tool.bar.code ";
	public static final String LBL_MENU_TOOL_ADD_SAMPLE_LOCA = "lbl.menu.tool.add.sample.location";
	public static final String LBL_MENU_TOOL_REPORT_MENU = "lbl.menu.tool.report.menu";
	public static final String LBL_MENU_TOOL_RESULT_DATA = "lbl.menu.tool.result.data";
	public static final String LBL_MENU_TOOL_UPDATE_RESULTS = "lbl.menu.tool.update.results";
	public static final String LBL_MENU_TOOL_REPORT_BAR_2D = "lbl.menu.tool.report.bar.2d";
	public static final String LBL_MENU_TOOL_SEND = "lbl.menu.tool.send";
	public static final String LBL_MENU_TOOL_CANCEL = "lbl.menu.tool.cancel";
	public static final String LBL_MENU_TOOL_RECEIVED = "lbl.menu.tool.received";
	public static final String LBL_MENU_TOOL_DEFINE_QUERY = "lbl.menu.tool.define.query";
	public static final String LBL_MENU_TOOL_EXPORT_XLS = "lbl.menu.tool.export.excel";
	public static final String LBL_MENU_TOOL_EXPORT_CSV = "lbl.menu.tool.export.csv";
	public static final String LBL_MENU_CONTRUCTION_SERVICE = "lbl.menu.construction.service";
	
	// Constants Projects
	public static final String ATTRIBUTE_PROJECT_ITEM = "attribute.project.item";
	public static final String ATTRIBUTE_PROJECT_ENABLED = "attribute.project.item.enabled";
	public static final String LBL_PROJECT_TITLE_ADD = "lbl.projects.sub.window.add.new.project";
	public static final String LBL_PROJECT_TITLE_EDIT = "lbl.projects.sub.window.add.edit.project";
	public static final String ATTRIBUTE_TYPE_OBJECT = "attribute.type.object.program.purpose";
	public static final String ATTRIBUTE_RELOAD_OBJECT = "attribute.reload.program.purpose";
	public static final String ATTRIBUTE_EDIT_OBJECT = "attribute.edit.object.program.purpose";
	
	// Investigators
	public static final String LBL_INVESTIGATORS_WIN_ADD = "lbl.investigators.sub.window.add.new.investigator";
	public static final String LBL_INVESTIGATORS_WIN_EDIT = "lbl.investigators.sub.window.edit.investigator";
	public static final String ATTRIBUTE_INVESTIGATOR_ITEM = "attribute.investigator.item";
	public static final String ATTRIBUTE_INVESTIGATOR_CREATE = "attribute.investigator.create";
	public static final String LBO_INVESTIGATOR_NAME = "lbl.investigators.sub.window.name";
	public static final String LBO_INVESTIGATOR_ABER_NAME = "lbl.investigators.sub.window.Abbre.name";
	public static final String LBO_INVESTIGATOR_WIN_REGIS = "lbl.investigators.sub.window.error.registred";
	public static final String LBO_INVESTIGATOR_WIN_REGIS_USER = "lbl.investigators.sub.window.select.user.preregistration";
	public static final String LBO_INVESTIGATOR_WIN_REGIS_USER_DEFAULT_FUNCTIONS = "lbl.investigators.sub.window.use.defaul.function";
	public static final String LBO_INVESTIGATOR_WIN_REGIS_USER_SET_FUNCTIONS = "lbl.investigators.sub.window.use.set.function";
	public static final String ATTRIBUTE_RESEARCHER_ROLE_FUNCTION = "attribute.researcher.role.functions";
	public static final String ATTRIBUTE_RESEARCHER_ROLE_MAP_FUNCTION = "attribute.researcher.map.role.functions";
	public static final String LBO_INVESTIGATOR_WIN_REGIS_SELECT_RESEARCHER = "lbl.investigators.sub.window.select.combo.researcher.error";
	public static final String LBO_INVESTIGATOR_WIN_RESEARCHER_ADD_SET_FUNCTIONS = "lbl.investigators.sub.window.check.functions.set";
	
	//tissue
	public static final String LBL_TISSUE_WIN_ADD = "lbl.tissue.title.sub.win.add.new.title";
	public static final String LBL_TISSUE_WIN_EDIT = "lbl.tissue.title.sub.win.edit.tissue";
	public static final String ATTRIBUTE_TISSUE_ITEM = "attribute.tissue.item";
	
	//locatoin
	public static final String ATTRIBUTE_LOCATION_ITEM = "attribute.location.item";
	public static final String LBL_LOCATION_WIN_ADD = "lbl.location.title.sub.win.add.new.title";
	public static final String LBL_LOCATION_WIN_EDIT = "lbl.location.title.sub.win.edit";
	public static final String LBL_LOCATION_MIXED_LOCATION = "lbl.location.mixed.location";
	public static final int MIX_LOCATION_DEFAULT = 1;
	
	// Seasons
	public static final String ATTRIBUTE_SEASON_ITEM = "attribute.season.item";
	public static final String LBL_SEASON_WIN_ADD = "lbl.seasons.title.sub.win.add.new.title";
	public static final String LBL_SEASON_WIN_EDIT = "lbl.seasons.title.sub.win.edit";
	public static final String LBL_SEASON_MIXED_LOCATION = "lbl.seasons.mixed.location";
	public static final int MIX_SEASON_DEFAULT = 1;

	// Study Template
	public static final String LBL_STUDY_TEMPLATE_WIN_ADD = "lbl.study.template.title.sub.win.add.new.title";
	public static final String LBL_STUDY_TEMPLATE_WIN_EDIT = "lbl.study.template.title.sub.win.edit.new.title";
	public static final String ATTRIBUTE_STUDY_TEMPLATE_ITEM = "attribute.study.template.item";
	public static final String ATTRIBUTE_STUDY_TEMPLATE_ITEM_DELETE = "attribute.study.template.item.delete";
	public static final String ATTRIBUTE_INDEX_FIELD = "attribute.idex.list.field";
	public static final String LBL_STUDY_TEMPLATE_NEED_FIELD = "lbl.study.template.need.fields";
	public static final String LBL_STUDY_TEMPLATE_FILL_FIELDS= "lbl.study.template.fill.fields";
	public static final String LBL_STUDY_TEMPLATE_FIELDS_NOT_EQUALS="lbl.study.template.fields.name.not.equals";
	public static final String LBL_STUDY_TEMPLATE_FIELDS_HAS_INFORMATION="lbl.study.template.fields.has.information";
	
	
	// Companies
	public static final String ATTRIBUTE_COMPANY_ITEM = "attribute.company.item";
	public static final String LBL_COMPANY_WIN_ADD = "lbl.companies.title.sub.win.add.new.title";
	public static final String LBL_COMPANY_WIN_EDIT = "lbl.companies.title.sub.win.edit.new.title";

	// Storage location
	public static final String LBL_STORAGE_LOCATION_WIN_Add = "lbl.storage.location.window.title.add";
	public static final String LBL_STORAGE_LOCATION_WIN_EDIT = "lbl.storage.location.window.title.edit";
	public static final String ATTRIBUTE_STORAGE_LOCATION_ITEM = "attribute.storage.location.item";
	public static final String LBL_STORAGE_LOCATION_DELETE_LOC = "lbl.storage.location.cant.delete.location";
	public static final String LBL_STORAGE_LOCATION_SELECT_LOC = "lbl.storage.location.select.location";
	public static final String LBL_STORAGE_LOCATION_EDIT_LOC = "lbl.storage.location.cant.edit.location";
	public static final String LBL_STORAGE_LOCATION_ADD_LOC = "lbl.storage.location.cant.add.location";
	public static final String LBL_STORAGE_LOCATION_ASS_COM = "lbl.storage.location.assigned.company";
	public static final String LBL_STORAGE_LOCATION_SAMP_ASS = "lbl.storage.location.samples.assigned";
	public static final String LBL_STORAGE_LOCATION_LOC_ASS = "lbl.storage.location.location.assigned";
	public static final String LBL_STORAGE_LOCATION_DEF_SYS = "lbl.storage.location.defined.system";
	public static final String LBL_STORAGE_LOCATION_BE_SAM_ASS = "lbl.storage.location.because.sample.assigned";
	public static final int LOCATION_ASSGINED_BY_SYSTEM = 1;
	public static final String LBL_STORAGE_LOCATION_SAMPL_ERR_ASS_COM = "lbl.storage.location.because.sample.error.ass.com";
	public static final String LBL_STORAGE_LOCATION_SAMPL_ERR_ASS_LOC = "lbl.storage.location.because.sample.error.ass.loc";
	public static final String ATTRIBUTE_SAMPLE_LOCATION_LIST = "attribute.sample.location.list";
	public static final String LBL_STORAGE_LOCATION_SELECTED_LAST_SAMP = "lbl.storage.location.selected.least.sample";
	
	// Studies
	public static final String LBL_STUDIES_TITLE_SUB_ADD_T = "lbl.studies.title.sub.win.add.new.title";
	public static final String ATTRIBUTE_LABSTUDY_ITEM = "attribute.labstudy.item";
	public static final String ATTRIBUTE_LABSTUDY_ITEM_ORIGINAL = "attribute.labstudy.item.original";
	public static final String ATTRIBUTE_LIST_SAMPLE_READ_IN_FILE = "attribute.list.samples.read";
	public static final String LBL_STUDIES_CREATE_PLATE_LAYOUT = "lbl.studies.create.plate.layout";
	public static final String LBL_STUDIES_CREATE_PLATE_LAYOUT_QUESTION = "lbl.studies.create.plate.question";
	public static String PLATE_NAME_PREFIX = "PLATE";
	public static final String LBL_STUDIES_PLATE_ITEM_BLANK = "lbl.studies.plate.item.blank";
	public static final String LBL_STUDIES_PLATE_ITEM_ASSIGNED = "lbl.studies.plate.item.assigned";
	public static final String LBL_STUDIES_PLATE_ITEM_CONTROL = "lbl.studies.plate.item.control";
	public static final String ATTRIBUTE_SAMPLE_ITEM = "attribute.sample.item";
	public static final String ATTRIBUTE_SAMPLE_ITEM_BEAN = "attribute.sample.item.bean";
	public static final String ATTRIBUTE_SAMPLE_ITEM_BEAN_EDIT = "attribute.sample.item.bean.edit";
	public static final String LBL_STUDIES_PLATE_EMTY_SELECT = "lbl.studies.plate.error.emty.plate";
	public static final String ATTRIBUTE_CONTROL_LAB = "attribute.control.lab";
	public static final String LBL_STUDIES_CONTROL_KBIOSCIENCES = "lbl.studies.control.kbiosciences";
	public static final String LBL_STUDIES_CONTROL_DART = "lbl.studies.control.dart";
	public static final String ATTRIBUTE_MAP_NUM_ITEM_SELECT = "attribute.map.num.item.select";
	public static final String ATTRIBUTE_NUMBER_SAMPLES_CONTROL = "attribute.number.sample.control";
	public static final String ATTRIBUTE_MAP_SAMPLES_SELEC_ASSIGNED = "attribute.map.num.item.select";
	public static final String ATTRIBUTE_SIZE_PLATE_SELECT = "attribute.size.plate.select";
	public static final String ATTRIBUTE_TYPE_LOAD_FILE = "attribute.type.load.file";
	public static final String ATTRIBUTE_TYPE_SAMPLE_MIXTURE = "attribute.type.sample.mixture";
	public static final String ATTRIBUTE_TYPE_LOAD_PLATE = "attribute.type.load.plate.list";
	public static final String ATTRIBUTE_SAMPLE_REPEAT = "attribute.declarate.sample.repeat1";
	public static final String ATTRIBUTE_MAP_SAMPLE_DELETE = "attribute.sample.detail.delite";
	public static final String ATTRIBUTE_MAP_SAMPLE_EDIT = "attribute.sample.detail.edit";
	public static final String ATTRIBUTE_LIST_TEMP_SAMPLE = "attribute.sample.list.temp";
	public static final String ATTRIBUTE_TYPE_FILE_CONTROL = "attribute.type.file.control";
	public static final String ATTRIBUTE_USE_PADDED_CEROS = "attribute.use.padded.ceros";
	public static final String ATTRIBUTE_LIST_FIELD_REPORT = "attribute.list.fields.report";
	public static final String ATTRIBUTE_USE_PREFIX_REPORT = "attribute.use.prefix.report";
	public static final String ATTRIBUTE_LIST_FIELD_REPORT_TEMPLATE = "attribute.list.fields.report.temp";
	public static final String ATTRIBUTE_FIELD_TEMPLATE = "attribute.fields.template";
	public static final String LBL_STUDIES_RANDOM_SIZE_ASSIGNED = "lbl.studies.random.size.assgined";
	public static final String LBL_STUDIES_RANDOM_NOT_SAMP_ASSIG = "lbl.studies.random.no.samples.assgined";
	public static final String LBL_STUDIES_TYPE_RACKS="lbl.studies.combo.item.r";
	public static final String LBL_STUDIES_TYPE_SEP_TUBES="lbl.studies.combo.item.s.t";
	public static final String LBL_STUDIES_RANDOM_SELECT_LEAST = "lbl.studies.random.selected.least";
	public static final String ATTRIBUTE_MAP_CELL_SAMPLE = "attribute.map.cell.sample";
	public static final String ATTRIBUTE_SIZE_COLUMNS = "attribute.size.columns";
	public static final String LBL_STUDIES_RANDOM_TUBE = "lbl.studies.title.random.tube";
	public static final String LBL_STUDIES_FILE_UPLOAD_ER_TYPE = "lbl.studies.file.upload.file.type";
	public static final String LBL_STUDIES_FILE_UP_FIL_SIZE = "lbl.studies.file.upload.file.c.s";
	public static final String LBL_STUDIES_FILE_UPLOAD_FILE_LABEL = "lbl.studies.file.upload.file.label";
	public static final String LBL_STUDIES_FILE_UPLOAD_FILE_CORRECT = "lbl.studies.file.upload.file.correct";
	public static final String LBL_STUDIES_FILE_UPLOAD_FILE_STRUCTURE = "lbl.studies.file.upload.file.structure";
	public static final String LBL_STUDIES_FILE_VALIDATE_STRUCTURE = "lbl.studies.file.upload.validate.struct.file";
	public static final String LBL_STUDIES_FILE_VALIDATE_STRUCTURE_ERROR = "lbl.studies.file.upload.validate.struct.file.error";
	public static final String LBL_STUDIES_FILE_VALIDATE_GID_MISSING = "lbl.studies.file.upload.validate.gid.missing";
	public static final String LBL_STUDIES_FILE_VALIDATE_GID_INCORRECT = "lbl.studies.file.upload.validate.gid.incorrect";
	public static final String LBL_STUDIES_FILE_VALIDATE_NPLANT_INCORRECT = "lbl.studies.file.upload.validate.nplant.incorrect";
	public static final String LBL_STUDIES_FILE_VALIDATE_LOCATION_MISSING = "lbl.studies.file.upload.validate.location.missing";
	public static final String LBL_STUDIES_FILE_VALIDATE_LOCATION_FOUND = "lbl.studies.file.upload.validate.location.found";
	public static final String LBL_STUDIES_FILE_VALIDATE_SEASON_MISSING = "lbl.studies.file.upload.validate.season.missing";
	public static final String LBL_STUDIES_FILE_VALIDATE_SEASON_FOUND = "lbl.studies.file.upload.validate.season.found";
	public static final String LBL_STUDIES_FILE_VALIDATE_PLATE_NAME_FOUND = "lbl.studies.file.upload.validate.plate.name.found";
	public static final String LBL_STUDIES_FILE_VALIDATE_CONTROL_NAME_FOUND = "lbl.studies.file.upload.validate.plate.control.found";
	public static final String LBL_STUDIES_FILE_VALIDATE_CONTROL_ROW_FOUND = "lbl.studies.file.upload.validate.plate.row.found";
	public static final String LBL_STUDIES_TEMPORARY_SAMPLE_FOUND="lbl.studies.temporary.sample.found";
	public static final String LBL_STUDIES_TITLE_SUB_WIN_ADD_LOAD_COL = "lbl.studies.title.sub.win.add.load.colums";
	public static final String LBL_STUDIES_TITLE_SUB_WIN_ADD_LOAD_ROW = "lbl.studies.title.sub.win.add.load.rows";
	public static final String LBL_STUDIES_TITLE_SUB_WIN_ADD_LOAD_CIMMYT = "lbl.studies.title.sub.win.add.load.cimmyt";
	
	public static final String LBL_STUDIES_FILE_UP_MEN_ERR_NOT_INF = "lbl.studies.file.upload.mess.error.not.information";
	public static final String LBL_STUDIES_FILE_UP_CELL_T_GID = "lbl.studies.file.upload.cell.label.tooltip.gid";
	public static final String LBL_STUDIES_FILE_UP_CELL_T_SAMP = "lbl.studies.file.upload.cell.label.tooltip.sampleid";
	public static final String LBL_STUDIES_FILE_UP_CELL_PLANT_N = "lbl.studies.file.upload.cell.label.tooltip.plant.n";
	public static final String LBL_STUDIES_FILE_UP_CELL_LOCATION = "lbl.studies.file.upload.cell.label.tooltip.location";
	public static final String LBL_STUDIES_FILE_UP_CELL_SEASON = "lbl.studies.file.upload.cell.label.tooltip.season";
	public static final String LBL_STUDIES_FILE_UPLOAD_ERROR_TITLE_WINDOW = "lbl.studies.file.upload.error.title.window";
	public static final String LBL_STUDIES_FILE_UPLOAD_ERROR_LESS_SAMPLE = "lbl.studies.file.upload.error.less.sample";
	public static final String LBL_STUDIES_FILE_UPLOAD_ERROR_GREATER_SAMPLE = "lbl.studies.file.upload.error.greater.sample";
	public static final String LBL_STUDIES_FILE_UPLOAD_SUCCESSFULL = "lbl.studies.file.upload.sucessfull";
	public static final String LBL_STUDIES_FILE_UPLOAD_TEXT_SUCCESSFULL = "lbl.studies.file.read.sucessfull";
	public static final String LBL_STUDIES_SAVE_SUCCESS = "lbl.studies.save.success";
	public static final String ATTRIBUTE_EDIT_STUDIES = "attribute.edit.studies";
	public static final String LBL_STUDIES_FILE_EXP_NUM_CON_ERR =  "lbl.studies.file.export.num.control.error";
	public static final String LBL_STUDIES_EDIT_SAMPLE_ERROR_DUPLICATE =  "lbl.studies.window.edit.sample.error.sample.duplicate";
	public static final String LBL_STUDIES_EDIT_SAMPLE_ERROR_DUPLICATE_STOP =  "lbl.studies.window.edit.sample.error.sample.duplicate.stop";
	public static final String LBL_STUDIES_SAMPLES_QUESTION_DUPLICATE =  "lbl.studies.window.edit.sample.error.samples.duplicates";
	public static final String LBL_STUDIES_SAMPLES_TITLE_DUPLICATE =  "lbl.studies.window.edit.sample.error.samples.duplicates.title";
	
	//TIMEOUT
	public static final String LBL_TIMEOUT_MSG = "lbl.timeout.window.message";

	public static final String VALUE_KNOWLEDGE_BASE = "KB";
	public static final String VALUE_DNAST = "DNA";
	public static final String VALUE_PROJECT = "project";
	public static final String VALUE_STUDY = "study";
	public static final String VALUE_SAMPLE = "sample";

	//QUERIES
	public static final String LIST_PARAM_HEADER1 = "lbl.query.column.paramgroup";
	public static final String LIST_PARAM_HEADER2 = "lbl.query.column.param";
	public static final String LIST_PARAM_HEADER3 = "lbl.query.column.comp";
	public static final String LIST_PARAM_HEADER4 = "lbl.query.column.paramVal";
	public static final String LIST_PARAM_HEADER5 = "lbl.query.column.oper";
	public static final String LIST_PARAM_HEADER6 = "lbl.query.column.paramDel";


	
	public static final String LIST_KBASE_RESULT_HEADER1 = "query.header1.kbase";
	public static final String LIST_KBASE_RESULT_HEADER2 = "query.header2.kbase";
	public static final String LIST_KBASE_RESULT_HEADER3 = "query.header3.kbase";
	public static final String LIST_KBASE_RESULT_HEADER4 = "query.header4.kbase";
	public static final String LIST_KBASE_RESULT_HEADER5 = "query.header5.kbase";
	public static final String LIST_KBASE_RESULT_HEADER6 = "query.header6.kbase";

	public static final String LIST_STUDY_RESULT_HEADER1 = "query.header1.study";
	public static final String LIST_STUDY_RESULT_HEADER2 = "query.header2.study";
	public static final String LIST_STUDY_RESULT_HEADER3 = "query.header3.study";
	public static final String LIST_STUDY_RESULT_HEADER4 = "query.header4.study";
	public static final String LIST_STUDY_RESULT_HEADER5 = "query.header5.study";
	public static final String LIST_STUDY_RESULT_HEADER6 = "query.header6.study";
	public static final String LIST_STUDY_RESULT_HEADER7 = "query.header7.study";

	public static final String LIST_SAMPLE_RESUTL_HEADER1 = "query.header1.sample";
	public static final String LIST_SAMPLE_RESUTL_HEADER2 = "query.header2.sample";
	public static final String LIST_SAMPLE_RESUTL_HEADER3 = "query.header3.sample";
	public static final String LIST_SAMPLE_RESUTL_HEADER4 = "query.header4.sample";
	public static final String LIST_SAMPLE_RESUTL_HEADER5 = "query.header5.sample";
	public static final String LIST_SAMPLE_RESUTL_HEADER6 = "query.header6.sample";
	public static final String LIST_SAMPLE_RESUTL_HEADER7 = "query.header7.sample";
	public static final String LIST_SAMPLE_RESUTL_HEADER8 = "query.header8.sample";

	public static final String LIST_STUDY_QUALIFIER1 = "query.qualifier1.study";
	public static final String LIST_STUDY_QUALIFIER2 = "query.qualifier2.study";
	public static final String LIST_STUDY_QUALIFIER3 = "query.qualifier3.study";
	public static final String LIST_STUDY_QUALIFIER4 = "query.qualifier4.study";
	public static final String LIST_STUDY_QUALIFIER5 = "query.qualifier5.study";
	public static final String LIST_STUDY_QUALIFIER6 = "query.qualifier6.study";
	public static final String LIST_STUDY_QUALIFIER7 = "query.qualifier7.study";
	public static final String LIST_STUDY_QUALIFIER8 = "query.qualifier8.study";

	public static final String LIST_SAMPLE_QUALIFIER1 = "query.qualifier1.sample";
	public static final String LIST_SAMPLE_QUALIFIER2 = "query.qualifier2.sample";
	public static final String LIST_SAMPLE_QUALIFIER3 = "query.qualifier3.sample";
	public static final String LIST_SAMPLE_QUALIFIER4 = "query.qualifier4.sample";
	public static final String LIST_SAMPLE_QUALIFIER5 = "query.qualifier5.sample";
	public static final String LIST_SAMPLE_QUALIFIER6 = "query.qualifier6.sample";
	public static final String LIST_SAMPLE_QUALIFIER7 = "query.qualifier7.sample";
	public static final String LIST_SAMPLE_QUALIFIER8 = "query.qualifier8.sample";
	public static final String LIST_SAMPLE_QUALIFIER9 = "query.qualifier9.sample";
	public static final String LIST_SAMPLE_QUALIFIER10 = "query.qualifier10.sample";

	public static final String LIST_PROJECT_QUALIFIER1 = "query.qualifier1.project";
	public static final String LIST_PROJECT_QUALIFIER2 = "query.qualifier2.project";
	public static final String LIST_PROJECT_QUALIFIER3 = "query.qualifier3.project";
	public static final String LIST_PROJECT_QUALIFIER4 = "query.qualifier4.project";

	public static final String QUERY_OPERATOR_EQUALS	= "query.operator.equals";
	public static final String QUERY_OPERATOR_NOT_EQUALS= "query.operator.notequals";
	public static final String QUERY_OPERATOR_LIKE		= "query.operator.like";
	public static final String QUERY_OPERATOR_NOT_LIKE	= "query.operator.notlike";
	public static final String QUERY_OPERATOR_GREATER	= "query.operator.greater";
	public static final String QUERY_OPERATOR_LESS		= "query.operator.less";
	public static final String QUERY_OPERATOR_AND		= "query.operator.and";
	public static final String QUERY_OPERATOR_OR		= "query.operator.or";
	public static final String QUERY_OPERATOR_NOT		= "query.operator.not";
	public static final String QUERY_OPERATOR_IN		= "query.operator.in";

	public static final String MESSAGE_KB_NOT_AVAILABLE = "lbl.query.message.unavailableKB";
	public static final String MESSAGE_NO_DATA_EXPORT = "lbl.query.message.noDataExport";
	public static final String MESSAGE_CHARACTER_NOT_VALID = "lbl.query.message.error.character.not.valid";

	
	//SHIPMENTS
	public static final String ATTRIBUTE_SHIPMENT_ITEM = "attribute.shipment.item";
	public static final String ATTRIBUTE_SHIPMENTS_ITEM = "attribute.shipments.item";
	public static final String ATTRIBUTE_EDIT_SHIPMENT = "attribute.edit.shipment";
	public static final String ATTRIBUTE_CORNELL_ITEM = "attribute.cornell.item";
	public static final String LBL_SHIPMENT_TITLE_SUB_ADD_T = "lbl.shipment.title.add";
	public static final String LBL_SHIPMENT_TITLE_SUB_ADD_S = "lbl.shipment.title.add.single";
	public static final String LBL_SHIPMENT_TITLE_SUB_EDIT_T = "lbl.shipmetn.title.edit";
	public static final String LBL_SHIPMENT_SENT_DATE = "lbl.shipment.send.date";
	public static final String LBL_SHIPMENT_COMPANY_INFO = "lbl.shipment.company.info";
	public static final String LBL_SHIPMENT_COMPANY_ADDRESS = "lbl.shipment.company.address";
	public static final String LBL_SHIPMENT_COMPANY_EMAIL = "lbl.shipment.company.email";
	public static final String LBL_SHIPMENT_CONTACT_NAME = "lbl.shipment.contact.name";
	public static final String LBL_SHIPMENT_INT_TRACK_NUMBER = "lbl.shipment.track.number";
	public static final String LBL_SHIPMENT_STATUS = "lbl.shipment.status";
	public static final String LBL_SHIPMENT_PROVIDER_NAME = "lbl.shipment.provider.name";
	public static final String LBL_SHIPMENT_SAVE_SUCCESS = "lbl.shipment.save.success";
	public static final String LBL_SHIPMENT_NO_SELECTION = "lbl.shipment.noSelection";
	public static final char LBL_SHIPMENT_STATUS_NO_SELECT = 'N';
	public static final char LBL_SHIPMENT_STATUS_SENT = 'S';
	public static final char LBL_SHIPMENT_STATUS_RECEIVED = 'R';
	public static final char LBL_SHIPMENT_STATUS_FOR_SEND = 'F';
	public static final String LBL_SHIPMENT_STRING_SENT = "S";
	public static final String LBL_SHIPMENT_STRING_RECEIVED = "R";
	public static final String LBL_SHIPMENT_STRING_FOR_SEND = "F";
	public static final String LBL_SHIPMENT_INTERTEK_REPORT = "lbl.shipment.intertek.report";
	public static final String LBL_SHIPMENT_KBIO_REPORT = "lbl.shipment.kbio.report";
	public static final String LBL_SHIPMENT_KBIO_REPORT2 = "lbl.shipment.kbio.report2";
	public static final String LBL_SHIPMENT_CORN_REPORT = "lbl.shipment.corn.report";
	public static final String LBL_SHIPMENT_DART_REPORT = "lbl.shipment.dart.report";
	public static final short LBL_SHIPMENT_PROVIDER_KBIOS = 1;
	public static final short LBL_SHIPMENT_PROVIDER_CORNELL = 2;
	public static final short LBL_SHIPMENT_PROVIDER_DART = 3;
	//public static final short LBL_SHIPMENT_PROVIDER_MACROGEN = 4;
	public static final short LBL_SHIPMENT_PROVIDER_SAGA = 4;
	public static final short LBL_SHIPMENT_PROVIDER_INTERTEK_FORMAT_1 = 5;
	public static final short LBL_SHIPMENT_PROVIDER_INTERTEK_FORMAT_2 = 6;
	public static final String MSG_SHIPMENT_DELETE_CONFIRM ="lbl.shipment.delete.shipment.confirm";
	public static final String MSG_SHIPMENTSET_DELETE_CONFIRM="lbl.shipment.delete.shipmentset.confirm";
	public static final String MSG_SHIPMENT_DELETE_ERROR="lbl.shipment.delete.error.generic";
	public static final String MSG_SHIPMENT_DELETE_STATUS="lbl.shipment.delete.error.status";
	public static final String MSG_SHIPMENT_DELETE_ASSIGNED="lbl.shipment.delete.error.shipassigned";
	public static final String MSG_SHIPMENT_ERROR_SEND="lbl.shipment.send.error.tracknumber";
	
	// services call
	public final static String BMS_SERVICE_API = propertyHelper.getKey( "bms.service.api", conf );
	public final static String LBL_BMS_COL_LIST_HEAD_DETAIL_DESIG =  "lbl.bms.col.list.header.detail.desigantion";
	public final static String LBL_BMS_COL_LIST_HEAD_SEED_SOURCE = "lbl.bms.col.list.header.detail.seed.source";
	public final static String LBL_BMS_COL_LIST_HEAD_ENTRY_CODE = "lbl.bms.col.list.header.detail.entry.code";
	public final static String LBL_BMS_COL_LIST_HEAD_DETAIL_CROOS = "lbl.bms.col.list.header.detail.cross";
	
	//BMS
	public final static String LBL_BMS_MENU = "lbl.bms.module";
	public final static String LBL_BMS_ADD_SAMPLES = "lbl.bms.mesage.add.samples";


	// Service contry
	public final static String URL_SERVICE_CONTRY = propertyHelper.getKey("webservice.contry.external", conf).trim();
	
	// service email
	public final static String  EMAIL_SMTP_HOST = propertyHelper.getKey( "mail.smtp.host", conf ).trim();
	public final static String  EMAIL_SMTP_PORT = propertyHelper.getKey( "mail.smtp.port", conf ).trim();
	public final static String  EMAIL_ACCOUNT_MANAGER = propertyHelper.getKey( "mail.account.manager", conf ).trim();
	public final static String  EMAIL_ACCOUNT_MANAGER_PASSWORD = propertyHelper.getKey( "mail.account.password", conf ).trim();
	public final static String EMAIL_ACCOUNT_RECEIVER_MAIZE = propertyHelper.getKey( "mail.recipients.emails.maize", conf ).trim();
	public final static String EMAIL_ACCOUNT_RECEIVER_WHEAT = propertyHelper.getKey( "mail.recipients.emails.wheat", conf ).trim();
	public final static String URL_MAIZE_PRODUCTION  = propertyHelper.getKey( "url.maize.production", conf ).trim();
	public final static String URL_WHEAT_PRODUCTION  = propertyHelper.getKey( "url.wheat.production", conf ).trim();

}
