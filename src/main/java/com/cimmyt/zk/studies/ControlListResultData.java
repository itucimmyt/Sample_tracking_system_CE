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

import static com.cimmyt.utils.Constants.ATTRIBUTE_LABSTUDY_ITEM;
import static com.cimmyt.utils.Constants.LOCALE_LANGUAGE;
import static com.cimmyt.utils.Constants.RESULT_PREFERENCE_SERVICE;
import static com.cimmyt.utils.ConstantsDNA.ATTRIBUTE_RESULT_DATA_BEAN_REPORT;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Window;

import com.cimmyt.bean.CSVReportGenericBean;
import com.cimmyt.bean.ResultDataExportDataBean;
import com.cimmyt.bean.ResultPreferenceCompleteBean;
import com.cimmyt.bean.RowResultDataBean;
import com.cimmyt.constants.ResultsCamposFijosUnEstudio;
import com.cimmyt.model.bean.LabStudy;
import com.cimmyt.model.bean.ResultsPreferencesDetail;
import com.cimmyt.service.ServiceResultPreference;
import com.cimmyt.servlet.RedirectServletReport;
import com.cimmyt.servlet.SessionReport;
import com.cimmyt.utils.ConstantsDNA;
import com.cimmyt.utils.PropertyHelper;
import com.cimmyt.utils.StrUtils;

@SuppressWarnings("serial")
public class ControlListResultData extends Window{

	private Label idLblRN;
	private Listbox idGridResultsList;
	private PropertyHelper pro=null;
	private Window idWindowRL;
	private Logger logger= Logger.getLogger(ControlListResultData.class);
	private ResultPreferenceCompleteBean resultBean;
	private SortedMap<Integer, ResultsPreferencesDetail> sortedMap = new TreeMap<Integer, ResultsPreferencesDetail>();
	private LabStudy beanStudy;
	private static ServiceResultPreference serviceResultPreference;
	private List<RowResultDataBean> listResults;
	/**
	 * Method that load context of all components and objects in session
	 */
	static{
	if (serviceResultPreference == null){
			try{
				serviceResultPreference = (ServiceResultPreference)SpringUtil.getBean(RESULT_PREFERENCE_SERVICE);
			}catch (Exception exResults){
				exResults.printStackTrace();
			}
		}
	}
	public void loadContext (){
		loadComponent();
		pro = (PropertyHelper)getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);
		resultBean = (ResultPreferenceCompleteBean)getDesktop().getAttribute(ATTRIBUTE_RESULT_DATA_BEAN_REPORT);
		beanStudy = (LabStudy)getDesktop().getAttribute(ATTRIBUTE_LABSTUDY_ITEM );
		sortListInMap();
		loadColumns ();
		loadRows();
		
	}
	/** Load the components */
	private void loadComponent(){
		idGridResultsList = (Listbox)getFellow("idGridResultsList");
		idLblRN = (Label)getFellow("idLblRN");
		idWindowRL = (Window)getFellow("idWindowRL");
	}
	/**Load the map in way order by order field */
	private void sortListInMap(){
		int idxOrder = 5;
		
		for (ResultsPreferencesDetail detail : resultBean.getPreference().getResultsPreferencesDetailCollection()){
			if(detail.getParamname().equals(ResultsCamposFijosUnEstudio.STUDYSAMPLEID.getValue())){
				sortedMap.put(0, detail);
			}else if(detail.getParamname().equals(ResultsCamposFijosUnEstudio.PLATENAME.getValue())){
				sortedMap.put(1, detail);
			}else if(detail.getParamname().equals(ResultsCamposFijosUnEstudio.PLATELOCATION.getValue())){
				sortedMap.put(2, detail);
			}else if(detail.getParamname().equals(ResultsCamposFijosUnEstudio.SAMPLEID.getValue())){
				sortedMap.put(3, detail);
			}else if(detail.getParamname().equals(ResultsCamposFijosUnEstudio.BREEDERGID.getValue())){
				sortedMap.put(4, detail);
			}else if(detail.getParamname().equals(ResultsCamposFijosUnEstudio.PEDIGREE.getValue())){
				sortedMap.put(5, detail);
			}else if(detail.getParamname().equals(ResultsCamposFijosUnEstudio.LOCATION.getValue())){
				sortedMap.put(6, detail);
			}else if(detail.getParamname().equals(ResultsCamposFijosUnEstudio.SEASON.getValue())){
				sortedMap.put(7, detail);
			}else if(detail.getParamname().equals(ResultsCamposFijosUnEstudio.PLANTNUMBER.getValue())){
				sortedMap.put(8, detail);
			}else{
				sortedMap.put(9+detail.getOrden(), detail);
			}
		}
		Collection<ResultsPreferencesDetail> detailResultCollection = new ArrayList<ResultsPreferencesDetail>();
		for (ResultsPreferencesDetail detail : sortedMap.values()){
			detailResultCollection.add(detail);
		}
		resultBean.getPreference().setResultsPreferencesDetailCollection(detailResultCollection);
	}

	private void loadColumns (){
		Listhead columns = new Listhead ();
		for (ResultsPreferencesDetail detail : sortedMap.values()){
			Listheader column = new Listheader(detail.getHeader());
			columns.appendChild(column);
		}
		idGridResultsList.appendChild(columns);
	}
	/** Close Window*/
	public void closeWindow(){
		idWindowRL.onClose();
	}
	//Method that load the rows in the table
	private void loadRows(){
		
		
		listResults = serviceResultPreference.getListRowResultData(beanStudy, resultBean);
		for (RowResultDataBean bean : listResults){
			Listitem lIt = new Listitem ();
			for (String str : bean.getListCell()){
				Listcell cellID = new Listcell(str);
				lIt.appendChild(cellID);
			}
			idGridResultsList.appendChild(lIt);
		}
		String value = idLblRN.getValue() + " : "+ resultBean.getNumberRows();
		idLblRN.setValue("");
		idLblRN.setValue(value);

	}
	/**
	 * Method that create to report of laboratory 
	 */
	public void createReportListResultXLS(){
			ResultDataExportDataBean resultDataExport = new ResultDataExportDataBean();
			resultDataExport.setListResults(listResults);
			resultDataExport.setNameExport(beanStudy.getTitle());
			resultDataExport.setListPlate(resultBean.getListPlateName());
			resultDataExport.setDateExport(StrUtils.getDateFormat(new Date()));
			SessionReport sessionReport = new SessionReport();
			sessionReport.setB(serviceResultPreference.getReportResultData(resultDataExport, pro,sortedMap));
			sessionReport.setType(ConstantsDNA.FILE_TYPE_XLS);
			sessionReport.setName(beanStudy.getTitle());

			RedirectServletReport.export(sessionReport);

	}

	public void exportFileControl(){
		ResultDataExportDataBean resultDataExport = new ResultDataExportDataBean();
		resultDataExport.setListResults(listResults);
		resultDataExport.setNameExport(beanStudy.getTitle());
		resultDataExport.setListPlate(resultBean.getListPlateName());
		resultDataExport.setDateExport(StrUtils.getDateFormat(new Date()));
		CSVReportGenericBean csvReportGenericBean = serviceResultPreference.getReportCSVReuslData(resultDataExport, pro, sortedMap);

		SessionReport sessionReport = new SessionReport();
		sessionReport.setB(new byte[0]);
		sessionReport.setType(ConstantsDNA.FILE_TYPE_CSV_GENERIC);
		sessionReport.setName(beanStudy.getTitle());
		sessionReport.setCsvReportGenericBean(csvReportGenericBean);

		RedirectServletReport.export(sessionReport);
	}
}
