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
package com.cimmyt.service.impl;

import static com.cimmyt.utils.ConstantsDNA.LBL_RESULTS_DATA_PROBLEM_BODY;
import static com.cimmyt.utils.ConstantsDNA.LBL_RESULTS_DATA_PROBLEM_HEADER;
import static com.cimmyt.utils.ConstantsDNA.LBL_RESULTS_DATA_SIZE_SAMPLES;
import static com.cimmyt.utils.ConstantsDNA.LBL_RESULTS_DATA_ERR_FIRST_COLUM_SAMPID;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cimmyt.bean.FileSampleCSVBean;
import com.cimmyt.bean.ListErrorLoadFileResultDataBean;
import com.cimmyt.bean.RowCSV;
import com.cimmyt.exception.ResultDataException;
import com.cimmyt.model.bean.LabStudy;
import com.cimmyt.model.bean.SampleDetResult;
import com.cimmyt.model.bean.SampleDetail;
import com.cimmyt.model.bean.StudyTemplateParams;
import com.cimmyt.service.ServiceLabStudy;
import com.cimmyt.service.ServiceResultData;
import com.cimmyt.utils.PropertyHelper;
import com.cimmyt.utils.StrUtils;
public class ServiceResultDataImpl implements ServiceResultData{
	
	private List<Integer> listRowEmty = new ArrayList<Integer>();
	private List<Integer> listSampleIDFormat = new ArrayList<Integer>();
	private Map<Integer, SampleDetail> mapSampleDetail = new HashMap<Integer, SampleDetail>();
	private List<Integer> listErrorSampleIDNotFound = new ArrayList<Integer>();
	private List<Integer> listErrorSize = new ArrayList<Integer>();
	private List<String> listErrorHeader = new ArrayList<String>();
	private PropertyHelper pro=null;
	private LabStudy beanStudy;
	private static ServiceLabStudy serviceLabStudy;

	public void validateSampleID(FileSampleCSVBean fileSampleCSV, PropertyHelper pro,
			 Integer numberOfSamples, LabStudy beanStudy) throws ResultDataException {
		this.beanStudy = beanStudy;
		listRowEmty = new ArrayList<Integer>();
		listSampleIDFormat = new ArrayList<Integer>();
		mapSampleDetail = new HashMap<Integer, SampleDetail>();
		listErrorSampleIDNotFound = new ArrayList<Integer>();
		listErrorSize = new ArrayList<Integer>();
		listErrorHeader = new ArrayList<String>();
		createListIdSample ();
		if (fileSampleCSV.getListHeaders() == null || fileSampleCSV.getListHeaders().isEmpty()){
			throw new ResultDataException(pro.getKey(LBL_RESULTS_DATA_PROBLEM_HEADER),null);
		}
		if (fileSampleCSV.getListRowCSV() == null || fileSampleCSV.getListRowCSV().isEmpty()){
			throw new ResultDataException(pro.getKey(LBL_RESULTS_DATA_PROBLEM_BODY),null);
			
		}
		if (numberOfSamples.intValue()+1 <= fileSampleCSV.getListRowCSV().size()){
			throw new ResultDataException(pro.getKey(LBL_RESULTS_DATA_SIZE_SAMPLES),null);
		}
		if (!fileSampleCSV.getListHeaders().get(0).toString().equals("Studysampleid")){
			throw new ResultDataException(pro.getKey(LBL_RESULTS_DATA_ERR_FIRST_COLUM_SAMPID),null);
		}
		
		HashMap<String, StudyTemplateParams> storedParams = 
				new HashMap<String, StudyTemplateParams>();
		for (StudyTemplateParams param : beanStudy.getStudytemplateid()
					.getImsStudyTemplateParamsCollection()) {
				storedParams.put(param.getFactorname(), param);
		}
		boolean isFoundFields = false;
		for (StringBuilder strBul : fileSampleCSV.getListHeaders()){
				if (storedParams.get(strBul.toString())!= null)
				{
					isFoundFields = true;
					break;
				}
		}
		if (!isFoundFields){
			throw new ResultDataException(pro.getKey(LBL_RESULTS_DATA_PROBLEM_HEADER),null);
		}
		int indexRow = 2;
		ListErrorLoadFileResultDataBean listErrorLoadFileResultDataBean = new ListErrorLoadFileResultDataBean();
		for (RowCSV row :fileSampleCSV.getListRowCSV()){
			if (row.getListRow().isEmpty()){
				listRowEmty.add(indexRow);
			}else {
				int indexColumn = 0;
				for (StringBuilder builder: row.getListRow()){
					if (indexColumn == 0){
						if (!StrUtils.isNumeric(builder) ){
							listSampleIDFormat.add(indexRow);
						}else {
							Integer idSample = Integer.parseInt(builder.toString());
							if (!mapSampleDetail.containsKey(idSample))
								listErrorSampleIDNotFound.add(idSample);
						}
					}else {
						if (builder.length() > 255){
							listErrorSize.add(indexRow);
						}
					}
					indexColumn++;
				}
			}
			indexRow++;
		}
		listErrorLoadFileResultDataBean.setListRowEmty(listRowEmty);
		listErrorLoadFileResultDataBean.setListSampleIDFormat(listSampleIDFormat);
		listErrorLoadFileResultDataBean.setListErrorSampleIDNotFound(listErrorSampleIDNotFound);
		listErrorLoadFileResultDataBean.setListErrorHeader(listErrorHeader);
		listErrorLoadFileResultDataBean.setListErrorSize(listErrorSize);
		if (listErrorLoadFileResultDataBean.areThereAnyListEmty()){
		
			throw new ResultDataException(pro.getKey(LBL_RESULTS_DATA_PROBLEM_BODY),listErrorLoadFileResultDataBean);
		}
		StudyTemplateParams param;
		String result;
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		for (RowCSV row :fileSampleCSV.getListRowCSV()){
			int indexColumn = 0;
			int idSample = 0;
			SampleDetail sampleDetail = null;
			for (StringBuilder strBul : fileSampleCSV.getListHeaders()){
				param = storedParams.get(strBul.toString());
				if (row.getListRow().get(indexColumn)!= null){
					result = row.getListRow().get(indexColumn).toString();
					if (param != null){
						switch ((int) param.getDatatype().charAt(0)) {
						case (int) 'N':
							result = row.getListRow().get(indexColumn).toString();
							break;
						case (int) 'D':
							try {
									//result = String.valueOf(df
											//.format(row.getListRow().get(indexColumn).toString()));
									result = StrUtils.getDateFormat(StrUtils.getDateStandarFromStr(row.getListRow().get(indexColumn).toString()));
							} catch (Exception e) {
								result = null;
							}
							break;
						case (int) 'C':
							result = row.getListRow().get(indexColumn).toString();
							break;
						}
						SampleDetResult sampleDetResult = serviceLabStudy.getSampleDetResultBySampleDetailIdAndTemplateParamId(
								sampleDetail.getStudysampleid(), param.getTemplateparamid());
						sampleDetResult.setResult(result);
						serviceLabStudy.updateSampleDetResult(sampleDetResult);
					}
					if (indexColumn == 0){
						idSample = Integer.parseInt(result);
						sampleDetail = mapSampleDetail.get(idSample);
					}
				}
				indexColumn++;
			}
		}
	}

	/** Create List to compare the id's of samples*/
	private void createListIdSample (){
		for (SampleDetail detail :beanStudy.getSampleDetailCollection()){
			mapSampleDetail.put(detail.getStudysampleid(), detail);
		}	
	}

	public static void setServiceLabStudy(ServiceLabStudy serviceLabStudy) {
		ServiceResultDataImpl.serviceLabStudy = serviceLabStudy;
	}

	
}
