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
package com.cimmyt.zk.query;

import static com.cimmyt.utils.Constants.LABSTUDY_SERVICE_BEAN_NAME;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_EMPTY_RESULT;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_ERROR_CRITERIAL;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_FILL_FIELD;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_INFORMATION;
import static com.cimmyt.utils.Constants.LOCALE_LANGUAGE;
import static com.cimmyt.utils.Constants.MESSAGE_KB_NOT_AVAILABLE;
import static com.cimmyt.utils.Constants.MESSAGE_NO_DATA_EXPORT;
import static com.cimmyt.utils.Constants.REG_EXP_PEDIGREE;
import static com.cimmyt.utils.Constants.SAMPLE_DETAIL_SERVICE_BEAN_NAME;
import static com.cimmyt.utils.Constants.SHOW_ROWS_LIST;
import static com.cimmyt.utils.Constants.VALUE_DNAST;
import static com.cimmyt.utils.Constants.VALUE_KNOWLEDGE_BASE;
import static com.cimmyt.utils.Constants.VALUE_PROJECT;
import static com.cimmyt.utils.Constants.VALUE_SAMPLE;
import static com.cimmyt.utils.Constants.VALUE_STUDY;
import static com.cimmyt.zk.query.QueryViewDefinition.HEADERS_KBASE;
import static com.cimmyt.zk.query.QueryViewDefinition.HEADERS_PARAMETERS_DNAST;
import static com.cimmyt.zk.query.QueryViewDefinition.HEADERS_PARAMETERS_KBASE;
import static com.cimmyt.zk.query.QueryViewDefinition.HEADERS_SAMPLE;
import static com.cimmyt.zk.query.QueryViewDefinition.HEADERS_STUDY;

import java.util.AbstractSequentialList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.cimmyt.dnast.dto.DsFile;
import org.cimmyt.dnast.dto.DsMetadataElement;
import org.cimmyt.dnast.dto.DsSearchParam;
import org.cimmyt.dnast.dto.DsSearchParams;
import org.cimmyt.dnast.service.FileRepositoryServiceClient;
import org.cimmyt.dnast.service.KBaseQueryService;
import org.cimmyt.dnast.service.imp.FileRepositoryServiceClientImp;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.A;
import org.zkoss.zul.Column;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelArray;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.ListModelMap;
import org.zkoss.zul.ListModelSet;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.PagingEvent;
import org.zkoss.zul.event.ZulEvents;
import org.zkoss.zul.impl.InputElement;

import com.cimmyt.bean.FileQueryBean;
import com.cimmyt.bean.Operator;
import com.cimmyt.bean.Operator.DataType;
import com.cimmyt.bean.Operator.TypeCondition;
import com.cimmyt.bean.RowQualifier;
import com.cimmyt.model.bean.LabStudy;
import com.cimmyt.model.bean.SampleDetail;
import com.cimmyt.reports.ServiceReportCustomQuery;
import com.cimmyt.reports.bean.CustomQueryReport;
import com.cimmyt.reports.impl.ServiceReportCustomQueryImpl;
import com.cimmyt.service.ServiceLabStudy;
import com.cimmyt.service.ServiceSampleDetail;
import com.cimmyt.servlet.RedirectServletReport;
import com.cimmyt.servlet.SessionReport;
import com.cimmyt.utils.Constants;
import com.cimmyt.utils.ConstantsDNA;
import com.cimmyt.utils.PropertyHelper;
import com.cimmyt.utils.PropertyHelper.Bundle;
import com.cimmyt.utils.StrUtils;

public class UseControlQuery extends Window{

	private static final long serialVersionUID = 1L;

	private final String PROPS_PREFIX="lbl.query.";
	
	/*data from the model*/
	private Set<String> categories;
	/*data from the model*/
	private Set<String> categoriesDNA = new TreeSet<String>();
	/*search parameters send to KB for request files*/
	private Set<String> categoriesKB;
	/*search parameters send to KB for request files*/
	private List<DsSearchParam> searchParams = new ArrayList<DsSearchParam>();
	/*logical operators to be included in search params*/
	private static final Set<TypeCondition> operators = new TreeSet<TypeCondition>();
	/*logical operators to be included in search params for KB*/
	private static final Set<TypeCondition> operatorsKB = new TreeSet<TypeCondition>();
	/*contains the result files from queries to KBase*/
	private List<DsFile> fileList = new ArrayList<DsFile>();
	
	/*helper to get locale messages*/
	private PropertyHelper pro;
	/*Service to send queries to KBase and retrieve metadata elements*/
	private FileRepositoryServiceClient fileRepositoryService;
	/*service for searching samples*/
	private ServiceSampleDetail serviceSampleDetail;
	/*service for searching studies*/
	private ServiceLabStudy serviceLabStudy;
	
	/*components of the view*/
	private Grid idGridParams;
	private Grid idGridResults;
	private Combobox cboxSearchOn;
	private Combobox cboxSearchFor;
	private Label lblSearchFor;
	private Paging idPaging;
	private boolean isCorrecForConsitionIN =true;
	private boolean isKBaseSelected = true;
	
	public void init(){
		
		serviceSampleDetail = (ServiceSampleDetail)SpringUtil.getBean(SAMPLE_DETAIL_SERVICE_BEAN_NAME);
		serviceLabStudy = (ServiceLabStudy)SpringUtil.getBean(LABSTUDY_SERVICE_BEAN_NAME);
		
		pro = (PropertyHelper)Executions.getCurrent().getSession().getAttribute(LOCALE_LANGUAGE);

		categoriesDNA.add(VALUE_PROJECT);
		categoriesDNA.add(VALUE_STUDY);
		categoriesDNA.add(VALUE_SAMPLE);
		
		loadContext();

		operators.add(TypeCondition.AND);
		operators.add(TypeCondition.OR);

		operatorsKB.add(TypeCondition.AND);
		operatorsKB.add(TypeCondition.NOT);
		operatorsKB.add(TypeCondition.OR);

		ListModelSet<String> searchForModel = new ListModelSet<String>(categoriesDNA);
		searchForModel.remove(searchForModel.getElementAt(0));
		cboxSearchFor.setModel(searchForModel);
		cboxSearchFor.setItemRenderer(new ComboitemRenderer<String>() {
			@Override
			public void render(Comboitem comboItem, String key, int arg2)
					throws Exception {

				comboItem.setLabel(pro.getKey(PROPS_PREFIX.concat(key)));
				
			}
		});
		
		try{
			/*uncomment this two lines for enabling KBase as default option*/
			fileRepositoryService = null;//new FileRepositoryServiceClientImp();
			categoriesKB = null;//fileRepositoryService.getCategories();	
		
			if(categoriesKB == null){
				categories = categoriesDNA;
				cboxSearchOn.setSelectedIndex(1);
				cboxSearchOn.setDisabled(true);
				doChangeSearchTarget(VALUE_DNAST);
				createHeaders(HEADERS_PARAMETERS_DNAST,idGridParams);

				//do not show this message until KBase is enabled
				//messageBox(MESSAGE_KB_NOT_AVAILABLE);
			}else{
				categories = categoriesKB;
				cboxSearchOn.setSelectedIndex(0);
				createHeaders(HEADERS_PARAMETERS_KBASE,idGridParams);
				createHeaders(HEADERS_KBASE,idGridResults);
				doAddNewParam();
			}
		}catch(Exception e){
			messageBox(MESSAGE_KB_NOT_AVAILABLE);
		}

		idPaging.addEventListener(ZulEvents.ON_PAGING, new EventListener<PagingEvent>() {

			@Override
			public void onEvent(PagingEvent event) throws Exception {
				
				idGridResults.getRows().getChildren().clear();

				switch (cboxSearchFor.getSelectedIndex()){
				case 1:
					Collection<LabStudy> studies = serviceLabStudy.getCustomQuery(new ArrayList<DsSearchParam>(searchParams)
							,event.getActivePage()*SHOW_ROWS_LIST
							,SHOW_ROWS_LIST);

					for(LabStudy study : studies){
						idGridResults.getRows().getChildren().add( createResultRowDNAStudy(study) );
					}
				break;
				case 0:
					List<SampleDetail> samples = serviceSampleDetail.getCustomQuery(new ArrayList<DsSearchParam>(searchParams)
							,event.getActivePage()*SHOW_ROWS_LIST
							,SHOW_ROWS_LIST);
					for(SampleDetail sample : samples){
						idGridResults.getRows().getChildren().add( createResultRowDNASample(sample) );
					}
				break;
			}
				
			}
		});
		
	}
	/**
	 * Loads view components that will be used within this controller
	 */
	private void loadContext(){
		idGridParams = (Grid)this.getFellow("idGridParams");
		idGridResults = (Grid)this.getFellow("idGridResults");
		cboxSearchOn = (Combobox)this.getFellow("cboxSearchOn");
		cboxSearchFor = (Combobox)this.getFellow("cboxSearchFor");
		lblSearchFor = (Label)this.getFellow("lblSearchFor");
		idPaging = (Paging)this.getFellow("idPaging");
	}
	
	/**
	 * Add a new row for input a parameter.
	 * */
	public void doAddNewParam(){
		
		Map<String, String> categoriesMap = new HashMap<String, String>();

		if(isKBaseSelected){
			for(String s : categories){
				categoriesMap.put(s, s);
			}
		}else{
			for(String s : categoriesDNA){
				categoriesMap.put(s, pro.getKey(PROPS_PREFIX.concat(s)));
			}
		}
		idGridParams.getRows().getChildren().add(
				createParameterRow(categoriesMap, idGridParams.getRows().getChildren().size()==0));
	}
	
	/**
	 * Get the first results page of the search and display them in screen.
	 */
	public void doFindDataSets(){
		doFindDataSets(-1,true);
	}
	
	/**
	 * Search for items in the database selected, with current parameters.
	 * @param pageNumber - the page or set of results for large lists
	 * @param showError - indicates if dialog messages must be displayed o user.
	 * @return The collection of result items, or null if no results were found.
	 **/
	private List<? extends Object> doFindDataSets(int pageNumber, boolean showError){
		int pageNum = (pageNumber <=0 ? 0 : pageNumber);
		List<? extends Object> returnList = null;

		if(idGridParams.getRows().getChildren().isEmpty()){
			if(showError) messageBox(LBL_GENERIC_MESS_ERROR_CRITERIAL);
			
			return null;
		}else{
			isCorrecForConsitionIN = true;
			searchParams = createSearchParams();
			if (!isCorrecForConsitionIN){
				fileList.clear();
				idGridResults.getRows().getChildren().clear();
				return null;
			}
			if( searchParams == null){
				messageBox(LBL_GENERIC_MESS_FILL_FIELD);
				return null;
			}

			fileList.clear();
			idGridResults.getRows().getChildren().clear();

			if(isKBaseSelected){//search to kbase
		
				DsSearchParams sparams = new DsSearchParams();
				sparams.getSearchParams().addAll(searchParams);
				fileList = fileRepositoryService.requestItems(sparams);
				returnList = fileList;
				
					if(fileList != null && fileList.size() <= 0){
						if(showError)		messageBox(LBL_GENERIC_MESS_EMPTY_RESULT);
					}else{
						for(DsFile f : fileList)
						idGridResults.getRows().getChildren().add( createResultRowKBase(f) );
					}
					
			}else{//search to dnast
				Integer rows = null;
				switch (cboxSearchFor.getSelectedIndex()){
					case 1:
						rows = serviceLabStudy.getTotalRowsCustomQuery(searchParams);
						idPaging.setPageSize(SHOW_ROWS_LIST);
						idPaging.setActivePage(0);
						idPaging.setTotalSize(rows);

						List<LabStudy> studies = serviceLabStudy.getCustomQuery(searchParams, pageNum * SHOW_ROWS_LIST, SHOW_ROWS_LIST);
						returnList = studies;

						if(studies != null && studies.size() <= 0){
							if(showError)	messageBox(LBL_GENERIC_MESS_EMPTY_RESULT);
						}else{
							for(LabStudy study : studies){
								idGridResults.getRows().getChildren().add( createResultRowDNAStudy(study) );
							}
						}

					break;
					case 0:
						rows = serviceSampleDetail.getTotalRowsCustomQuery(searchParams);
						
						idPaging.setPageSize(SHOW_ROWS_LIST);
						idPaging.setActivePage(0);
						idPaging.setTotalSize(rows);

						List<SampleDetail> samples = serviceSampleDetail.getCustomQuery(searchParams, pageNum * SHOW_ROWS_LIST, SHOW_ROWS_LIST);
						returnList = samples;

						if(samples != null && samples.size() <= 0){
							if(showError)	messageBox(LBL_GENERIC_MESS_EMPTY_RESULT);
						}else{
							for(SampleDetail sample : samples){
								idGridResults.getRows().getChildren().add( createResultRowDNASample(sample) );
							}
						}

					break;
				}//swith

			}//search dnast
		}//params not empty
		
		return returnList;
	}
	
	/**
	 * Notifies that the user changes the destination of the search, from local to remote and vice versa.
	 * Modifies the view accordingly
	 * @param value The identifier of the destination (KB, DNA)
	 * 
	 */
	public void doChangeSearchTarget(String value){
		idGridParams.getRows().getChildren().clear();
		idGridResults.getRows().getChildren().clear();
		idPaging.setActivePage(0);
		idPaging.setTotalSize(0);
		
		isKBaseSelected =value.contains(VALUE_KNOWLEDGE_BASE);
		
		try{
			if(isKBaseSelected){
				if(value.equals("KB2")){
					fileRepositoryService.setEndpoint(KBaseQueryService.ENDPOINT_REMOTE);
				}else{
					fileRepositoryService.setEndpoint(KBaseQueryService.ENDPOINT_LOCAL);
				}
				categoriesKB = fileRepositoryService.getCategories();
	
				categories = categoriesKB;
				cboxSearchFor.setVisible(false);
				cboxSearchFor.setSelectedIndex(-1);
				lblSearchFor.setVisible(false);
				createHeaders(HEADERS_PARAMETERS_KBASE,idGridParams);
				createHeaders(HEADERS_KBASE,idGridResults);
				
			}else{
	
				categories = categoriesDNA;
				cboxSearchFor.setVisible(true);
				lblSearchFor.setVisible(true);
				createHeaders(HEADERS_PARAMETERS_DNAST,idGridParams);
	
				if(cboxSearchFor.getItemCount() > 0){
					cboxSearchFor.setSelectedIndex(0);
					createHeaders(HEADERS_SAMPLE,idGridResults);
				}else{
					cboxSearchFor.setSelectedIndex(-1);
				}
				
			}
			doAddNewParam();
		}catch(Exception e){
			messageBox(MESSAGE_KB_NOT_AVAILABLE);
		
		}
	}
	
	/**
	 * When searching locally, notifies that the user changes the type of results: studies or samples
	 */
	public void doChangeSearchFor(){
		switch(cboxSearchFor.getSelectedIndex() ){
			case 0 : createHeaders(HEADERS_SAMPLE,idGridResults); break;
			case 1 : createHeaders(HEADERS_STUDY,idGridResults); break;
			default: break;
		}
		idGridResults.getRows().getChildren().clear();
		idPaging.setActivePage(0);
		idPaging.setTotalSize(0);
	}

	/**
	 * Exports the result data to a file.
	 * @param fileFormat The type of output file: .xml, csv, etc.
	 * @see com.cimmyt.utils.ConstantsDNA
	 */
	public void doExportReport(int fileFormat){

		ServiceReportCustomQuery serviceReport = new ServiceReportCustomQueryImpl();
		//used to navigate through all pages
		int page = 0;
		//used to restore page visible when export occurs
		List<Component> rows = new ArrayList<Component>( idGridResults.getRows().getChildren() );
		
		Map<String, List<String>> columns = new LinkedHashMap<String, List<String>>();
		Component column;
		String columnName;
		//for each column
		for(int col=0;col<idGridResults.getColumns().getChildren().size();col++){
			column = idGridResults.getColumns().getChildren().get(col);
			columnName = ((Column)column).getLabel();
			columns.put( columnName, new LinkedList<String>());
		}
		
		if (idGridResults.getRows().getChildren().size()>0){
			CustomQueryReport beanReport = new CustomQueryReport();
			
			if(!isKBaseSelected) doFindDataSets(page,false);
			
			while(idGridResults.getRows().getChildren().size()>0){
				//for each column
				for(int col=0;col<idGridResults.getColumns().getChildren().size();col++){
					column = idGridResults.getColumns().getChildren().get(col);
					columnName = ((Column)column).getLabel();
		
					//get all values
					for(int row=0;row<idGridResults.getRows().getChildren().size();row++){
						Component item = idGridResults.getCell(row, col);
						if(item.getClass() == Label.class){
							columns.get(columnName).add(((Label)item).getValue());
						}else if(item.getClass() == A.class){
							columns.get(columnName).add(((A)item).getHref());
						}
					}
				}
				if(isKBaseSelected){
					idGridResults.getRows().getChildren().clear();
				}else{
					page++;
					doFindDataSets(page,false);
				}
			}
			
			idGridResults.getRows().getChildren().addAll(rows);
			
			List<String> paramHeaders = new ArrayList<String>();
			for(int col=0;col<idGridParams.getColumns().getChildren().size()-1;col++){
				column = idGridParams.getColumns().getChildren().get(col);
				paramHeaders.add(((Column)column).getLabel());
			}

			//fill bean
			beanReport.setColumns(columns);
			beanReport.setDatabase(cboxSearchOn.getText());
			beanReport.setParams(searchParams);
			beanReport.setParamHeaders(paramHeaders);

			//calls export servlet
			SessionReport sessionReport = new SessionReport();
			sessionReport.setName(beanReport.getDatabase());
			
			if(fileFormat == ConstantsDNA.FILE_TYPE_XLS){
				sessionReport.setType(ConstantsDNA.FILE_TYPE_XLS);
				sessionReport.setB(serviceReport.getBytesCustomQuery(beanReport));
			}else if(fileFormat == ConstantsDNA.FILE_TYPE_CSV){
				FileQueryBean fileCSVBean = new FileQueryBean();
				
				LinkedList<String> convertedRow;
				
				//convertir columnas en rows y pasar lista
				String key1 = beanReport.getColumns().keySet().toArray()[0].toString();
				for(int i = 0; i<beanReport.getColumns().get(key1).size(); i++){
					convertedRow = new LinkedList<String>();
					for(String colName : beanReport.getColumns().keySet()){
						convertedRow.add(beanReport.getColumns().get(colName).get(i));
					}
					fileCSVBean.getListRows().add(convertedRow);
				}

				fileCSVBean.setListHeaders(new LinkedList<String>(columns.keySet()) );
				
				sessionReport.setType(ConstantsDNA.FILE_TYPE_CSV);
				sessionReport.setB(new byte[0]);
				sessionReport.setFileCSVBean(fileCSVBean);
			}
			
			RedirectServletReport.export(sessionReport);

		
		}else {
			messageBox(MESSAGE_NO_DATA_EXPORT);
		}
	}

	/**
	 * Creates a list of the search parameters the user input. 
	 * This parameters will be sent to KBase search engine.
	 * @return The search parameter list for the query, or <b>null</b> if some field is missing.
	 * */
	private List<DsSearchParam> createSearchParams(){
		List<DsSearchParam> searchParams = new ArrayList<DsSearchParam>();
		
		for(Component r : idGridParams.getRows().getChildren()){
			DsSearchParam searchParam = new DsSearchParam(TypeCondition.AND, null, null, null, FileRepositoryServiceClientImp.ID_SCHEMA_DNAST);
			
			Combobox cboxElm = (Combobox)r.getFirstChild();//combo element
			if(cboxElm.getSelectedIndex() < 0){
				return null;
			}
			@SuppressWarnings("unchecked")
			Entry<String, String> elmntSelected = (Entry<String, String>)cboxElm.getModel().getElementAt(cboxElm.getSelectedIndex());
			searchParam.setElement( elmntSelected.getKey() );
			Combobox cboxQalif = (Combobox)cboxElm.getNextSibling();//combo qualifier
			if(!cboxQalif.isDisabled() && cboxQalif.getText().isEmpty()){
				return null;
			}
			Object qualifierSelected = cboxQalif.getModel().getElementAt(cboxQalif.getSelectedIndex());
			Operator comparator = null;
			if(isKBaseSelected){
				if(cboxQalif.isDisabled())
					searchParam.setMetadataElemet(fileRepositoryService.getNoQualifiedMetadata(cboxElm.getText()));
				else
					searchParam.setMetadataElemet((DsMetadataElement)qualifierSelected );
			}else{
				searchParam.setQualifier( ((RowQualifier)qualifierSelected).getKey() );
				cboxQalif = (Combobox)cboxQalif.getNextSibling();//combo comparator
				if(cboxQalif.getSelectedIndex() < 0){
					return null;
				}
				comparator = (Operator)cboxQalif.getModel().getElementAt(cboxQalif.getSelectedIndex());
				searchParam.setCondition(comparator.toString());
			}		
			InputElement textValue = (InputElement)cboxQalif.getNextSibling();
			if(textValue.getText().isEmpty()){
				return null;
			}
			String str = textValue.getText().replaceAll(pro.getKey(REG_EXP_PEDIGREE,Bundle.conf), "").trim();//removes non word chars, except /, (, ), -, and blanks
			if (comparator != null &&  comparator == Operator.TypeNumber.IN){
				if (str.startsWith(",") || str.endsWith(",")){
					StrUtils.messageBoxError(pro.getKey(Constants.MESSAGE_CHARACTER_NOT_VALID), pro);
					isCorrecForConsitionIN = false;
					return null;
				}
				String returnCharacter = "\n";
				if (str.contains("GID"))
					str = str.replace("GID", "");
			    if (str.contains("gid"))
			    	str = str.replace("gid", "");
			    if (str.contains(" "))
			    	str = str.replace(" ", "");
			    if (str.contains("	"))
			    	str = str.replace("	", "");
			    if (str.contains(returnCharacter))
			    	if (str.contains(","))
			    		str = str.replace(returnCharacter, "");
			    	else
			    		str = str.replace(returnCharacter, ",");
			    Pattern patron = Pattern.compile("[0-9]|([0-9,]*)");
			    Matcher encaja = patron.matcher(str);
			    if (!encaja.matches()){
			    	StrUtils.messageBoxError(pro.getKey(Constants.MESSAGE_CHARACTER_NOT_VALID), pro);
			    	isCorrecForConsitionIN = false;
			    	return null;
			    }
			}
			searchParam.setValue( str); 
			Combobox cboxOper = (Combobox)textValue.getNextSibling();//combo operator
			if(cboxOper.getSelectedIndex() < 0){
				return null;
			}
			searchParam.setOperator((TypeCondition)cboxOper.getModel().getElementAt(cboxOper.getSelectedIndex()));
			searchParams.add(searchParam);
		}
		
		
		return searchParams;
	}

	/**
	 * Generic message dialog
	 * @param messageKey The key in the properties file that references the message to display.
	 * */
	private void messageBox(String messageKey){
		Messagebox.show(pro.getKey(messageKey),pro.getKey(LBL_GENERIC_MESS_INFORMATION), 
				Messagebox.OK, Messagebox.INFORMATION);
	}
	
	/**
	 * Creates a row which can be added as a filter to the parameter table.
	 * @param Set<String> data - The initial model for the combobox of categories for filters.
	 * @return A filter row
	 * */
	private Row createParameterRow(Map<String,String> data, boolean isFirst){
		Combobox cboxElement = new Combobox();
		cboxElement.setModel(new ListModelMap<String,String>(data));
		cboxElement.setReadonly(true);
		
		//listener to change data of combo 2 when element in combo 1 is selected
		cboxElement.addEventListener(Events.ON_CHANGE, new EventListener<InputEvent>() {
			@Override
			public void onEvent(InputEvent event) throws Exception { //when change combo element
				Combobox qualifier = (Combobox)event.getTarget().getNextSibling();

				qualifier.setText("");
					
				if(isKBaseSelected){
					qualifier.setItemRenderer(new ComboitemRenderer<DsMetadataElement>() {

						@Override
						public void render(Comboitem comboItem, DsMetadataElement metadata, int index)
								throws Exception {
							comboItem.setLabel(metadata.getQualifier());
							
						}
					});

					List<DsMetadataElement> metadatas = fileRepositoryService.getElementsByCategory( event.getValue() );
					qualifier.setModel(new ListModelList<DsMetadataElement>( metadatas ));	
				}else{
					qualifier.setItemRenderer(new ComboitemRenderer<RowQualifier>() {
						@Override
						public void render(Comboitem comboItem, RowQualifier value, int index)
								throws Exception {
							comboItem.setLabel( pro.getKey(value.getLabelKey()) );
							
						}
					});
					
					int index = ((Combobox)event.getTarget()).getSelectedIndex();

					//selected item of Element
					@SuppressWarnings("unchecked")
					Entry<String,String> item = ((Entry<String, String>)((Combobox)event.getTarget()).getModel().getElementAt(index));

					qualifier.setModel(new ListModelSet<RowQualifier>( getQualifiersDNA(item.getKey()) ));
					
					qualifier.addEventListener(Events.ON_CHANGE, new EventListener<InputEvent>() {

						@Override
						public void onEvent(InputEvent event) throws Exception { //when change combo qualifier
							int index = ((Combobox)event.getTarget()).getSelectedIndex();
							final RowQualifier qualif = ((RowQualifier)((Combobox)event.getTarget()).getModel().getElementAt(index));							
							final Combobox comparator = (Combobox)event.getTarget().getNextSibling();
							comparator.setModel(new ListModelArray<Operator>(
									qualif.getDataType().equals(Operator.DataType.NUMBER) ? 
											Operator.TypeNumber.values() : 
											Operator.TypeString.values()));
							comparator.setSelectedIndex(-1);
							//InputElement input = createInputControl( qualif.getDataType().equals(DataType.NUMBER) );
							comparator.addEventListener(Events.ON_CHANGE, new EventListener<InputEvent>() {

								@Override
								public void onEvent(InputEvent event)
										throws Exception {
									int index = ((Combobox)event.getTarget()).getSelectedIndex();
									Operator operator = (Operator)((Combobox)event.getTarget()).getModel().getElementAt(index);
									if (operator == Operator.TypeNumber.IN){
										InputElement textValue = createInputControl(false);
										Textbox text = (Textbox)textValue;
										text.setRows(50);
										text.setMaxlength(30000);
										text.setHeight("150px");
										comparator.getParent().removeChild(comparator.getNextSibling());
										comparator.getParent().insertBefore(text, comparator.getNextSibling());
											//TODO
									}else {
										InputElement input = createInputControl( qualif.getDataType().equals(DataType.NUMBER) );
										comparator.getParent().removeChild(comparator.getNextSibling());
										comparator.getParent().insertBefore(input, comparator.getNextSibling());
									}
									
								}
								
							} );
							
							event.stopPropagation();
						}
						
					});
				}
				
				if(qualifier.getModel().getSize()-1 > 0){
					qualifier.setDisabled(false);
				}else{
					qualifier.setDisabled(true);
				}
			}
			
		});
		
		cboxElement.setItemRenderer(new ComboitemRenderer<Entry<String,String>>() {
			@Override
			public void render(Comboitem comboItem, Entry<String,String> value, int index)
					throws Exception {
				comboItem.setLabel(value.getValue());
				
			}
		});
		
		Combobox cboxQualifier = new Combobox();
		cboxQualifier.setReadonly(true);


		Combobox cboxOperator = new Combobox();

		cboxOperator.addEventListener("onAfterRender", new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				((Combobox)event.getTarget()).setSelectedIndex(0);
			}
		});

		cboxOperator.setWidth("100%");
		cboxOperator.setModel(new ListModelSet<TypeCondition>(isKBaseSelected ? operatorsKB : operators));
		cboxOperator.setReadonly(true);

		cboxOperator.setItemRenderer(new ComboitemRenderer<TypeCondition>() {
			@Override
			public void render(Comboitem comboItem, TypeCondition value, int index)
					throws Exception {
				
				comboItem.setLabel(pro.getKey(value.key()));
				
			}
		});
		

		cboxOperator.setVisible(!isFirst);

		InputElement textValue = createInputControl(false);
		
		Image imgDelete = new Image( Constants.URL_IMAGE_DELETE);
		imgDelete.setWidth("22px");
		imgDelete.setHeight("22px");

		//Listener to delete rows
		imgDelete.addEventListener(Events.ON_CLICK, new EventListener<MouseEvent>() {
			@Override
			public void onEvent(MouseEvent event)
					throws Exception {
				idGridParams.getRows().getChildren().remove(event.getTarget().getParent());
			}
		});
		imgDelete.setVisible(!isFirst);
		
		
		Row row = new Row();
		row.appendChild(cboxElement);
		row.appendChild(cboxQualifier);
		
		if(!isKBaseSelected){
			Combobox cboxComparator = new Combobox();
			cboxComparator.setWidth("100%");
			cboxComparator.setReadonly(true);

			cboxComparator.setItemRenderer(new ComboitemRenderer<Operator>() {
				@Override
				public void render(Comboitem comboItem, Operator value, int index)
						throws Exception {
					comboItem.setLabel(pro.getKey(value.key()));
					
				}
			});

			row.appendChild(cboxComparator);
		}

		row.appendChild(textValue);
		row.appendChild(cboxOperator);
		row.appendChild(imgDelete);
		
		return row;
	}

	/**
	 * Returns the metadata for the entities needed to create a query.
	 * @param element The identifier of the DNAST entity.
	 * @return A Set of qualifiers for the entity requested.
	 */
	private Set<RowQualifier> getQualifiersDNA(String element){

		Set<RowQualifier> qualifiers = new LinkedHashSet<RowQualifier>();
		if(element.equals(VALUE_PROJECT)){
			qualifiers.addAll(QueryViewDefinition.QUALIFIERS_PROJECT); 
		}else if(element.equals(VALUE_STUDY)){
			qualifiers.addAll(QueryViewDefinition.QUALIFIERS_STUDY); 
		}else{
			qualifiers.addAll(QueryViewDefinition.QUALIFIERS_SAMPLE); 
		}
		
		return qualifiers;
	}


	/**
	 * Creates a row which can be added as a result line to the results table.
	 * @param dsFile - The File result to be displayed in the row.
	 * @return A result row.
	 * */
	private Row createResultRowKBase(DsFile dsFile){

		AbstractSequentialList<String> rowLabels = new LinkedList<String>();
		rowLabels.add(dsFile.getProject());
		rowLabels.add(dsFile.getInvestigator());
		rowLabels.add(dsFile.getDataset());
		rowLabels.add(dsFile.getPopulation());
		rowLabels.add(dsFile.getSizeKB());

		Row r = createResultRow(rowLabels);
		A link = new A( dsFile.getUrl().substring(dsFile.getUrl().lastIndexOf("/")+1) );
		link.setHref(dsFile.getUrl());
		r.appendChild(link);
		
		return r;
	}

	/**
	 * Creates a row which can be added as a result line to the results table.
	 * @param study - The study to be displayed.
	 * @return A result row with the data of the study.
	 * */
	private Row createResultRowDNAStudy(LabStudy study){

		AbstractSequentialList<String> rowLabels = new LinkedList<String>();
		rowLabels.add(study.getTitle());
		rowLabels.add(study.getObjective());
		rowLabels.add(study.getPrefix());
		rowLabels.add(study.getNumcontrols().toString());
		rowLabels.add(study.getNumofplates().toString());
		if(study.getProject()!=null){
			rowLabels.add(study.getProject()== null ? "" : study.getProject().getProjectname() );
		}
		
		rowLabels.add(study.getInvestigatorid() == null ? "" : study.getInvestigatorid().getInvest_name());
		
		return createResultRow(rowLabels);
	}

	/**
	 * Creates a row which can be added as a result line to the results table.
	 * @param study - The sample to be displayed.
	 * @return A result row with the data of the sample.
	 * */
	private Row createResultRowDNASample(SampleDetail sample){
		AbstractSequentialList<String> rowLabels = new LinkedList<String>();
		rowLabels.add(sample.getLabstudyid().getPrefix()+
				(sample.getLabstudyid().isUsePadded() ? StrUtils.getPaddingCeros(sample.getSamplegid()) :
					sample.getSamplegid() != null? String.valueOf(sample.getSamplegid()) : ""));
		rowLabels.add(sample.getBreedergid() == null ? "" : sample.getBreedergid().toString());
		rowLabels.add(sample.getPlatename());
		rowLabels.add(sample.getPlateloc());
		rowLabels.add(sample.getEntryNo() == null ? "" : sample.getEntryNo().toString());
		rowLabels.add(sample.getLocationid() == null ? "" : sample.getLocationid().toString());
		rowLabels.add(sample.getSeasonid() == null ? "" : sample.getSeasonid().toString());
		rowLabels.add(sample.getNval());
		rowLabels.add(sample.getLabstudyid() != null && 
				sample.getLabstudyid().getInvestigatorid()!= null && 
				!sample.getLabstudyid().getInvestigatorid().getInvest_name().trim().equals("")?
				sample.getLabstudyid().getInvestigatorid().getInvest_name(): "");
		return createResultRow(rowLabels);
	}

	/**
	 * Creates headers for a Grid.
	 * @param headers A Map with the keyName and width of the headers
	 * @param grid The table where the headers must be created
	 * */
	private void createHeaders(Map<String, String> headers, Grid grid){
		
		grid.getColumns().getChildren().clear();
		
		for(String key : headers.keySet()){
			Column column = new Column(pro.getKey(key));
			column.setWidth(headers.get(key));
			grid.getColumns().getChildren().add(column);
		}
	}

	
	/**
	 * Creates rows for tables.
	 * @param labels A List of labels to be displayed aas cells in the row
	 * @return a Row that can be added to a table.
	 * */
	private Row createResultRow(AbstractSequentialList<String> labels){
		Row row = new Row();
		for(String label : labels){
			row.appendChild( new Label(label) );
		}
		return row;
	}

	/**
	 * Gets the categories that can be used for creating a local query.
	 * @return A Set with the categories for local queries to DNA base.
	 */
	public Set<String> getCategoriesDNA() {
		return categoriesDNA;
	}
	
	private InputElement createInputControl(boolean onlyNumbers){
		InputElement input = ( onlyNumbers ? new Intbox(): new Textbox());
		input.setWidth("100%");
		input.setMaxlength(onlyNumbers ? 8 : 50);
		input.addEventListener("onOK", new EventListener<Event>() {

			@Override
			public void onEvent(Event arg0) throws Exception {
				doFindDataSets();				
			}
		});
		
		return input;
	}

}
