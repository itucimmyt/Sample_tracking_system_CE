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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;

import com.cimmyt.bean.CSVReportGenericBean;
import com.cimmyt.bean.ResultDataExportDataBean;
import com.cimmyt.bean.ResultPreferenceCompleteBean;
import com.cimmyt.bean.RowResultDataBean;
import com.cimmyt.constants.ControlType;
import com.cimmyt.constants.ShipmentStatus;
import com.cimmyt.model.bean.LabStudy;
import com.cimmyt.model.bean.ResultsPreferences;
import com.cimmyt.model.bean.ResultsPreferencesDetail;
import com.cimmyt.model.bean.SampleDetResult;
import com.cimmyt.model.bean.SampleDetail;
import com.cimmyt.model.bean.StudyTemplate;
import com.cimmyt.model.bean.StudyTemplateParams;
import com.cimmyt.model.dao.ResultsPreferencesDAO;
import com.cimmyt.model.dao.StudyTemplateDAO;
import com.cimmyt.reports.ServiceReportLaboratory;
import com.cimmyt.service.ServiceResultPreference;
import com.cimmyt.utils.PropertyHelper;
import com.cimmyt.utils.StrUtils;
public class ServiceResultPreferenceImpl implements ServiceResultPreference{

	private ResultsPreferencesDAO resultsPreferencesDAO;
	private StudyTemplateDAO studyTemplateDAO;
	private ServiceReportLaboratory serviceReportLaboratory;
	@Override
	public ResultsPreferences readResultsPreferences(Integer id) {
		return resultsPreferencesDAO.readResultsPreferences(id);
	}

	@Override
	public boolean isThisNameRegistredInResultsPreferences(String namepref) {
		return resultsPreferencesDAO.isThisNameRegistredInResultsPreferences(namepref);
	}
	@Override
	public List<ResultsPreferences> getListResultsPreferents(
			ResultsPreferences resultsPreferences) {

		return resultsPreferencesDAO.getListResultsPreferents(resultsPreferences);
	}

	public void setResultsPreferencesDAO(ResultsPreferencesDAO resultsPreferencesDAO) {
		this.resultsPreferencesDAO = resultsPreferencesDAO;
	}

	public void saveOrUpdateResultsPreferences (ResultsPreferences resultsPreferences){
		resultsPreferencesDAO.saveOrUpdateResultsPreferences(resultsPreferences);
	}

	public void setStudyTemplateDAO(StudyTemplateDAO studyTemplateDAO) {
		this.studyTemplateDAO = studyTemplateDAO;
	}

	public List<RowResultDataBean> getListRowResultData(
		LabStudy imsLabStudy, 
		ResultPreferenceCompleteBean resulPreferenceComplete) {
		String platename = resulPreferenceComplete.getListPlateName();
	StudyTemplate studyTemplate = studyTemplateDAO.read(imsLabStudy
			.getStudytemplateid().getStudytemplateid());
	Set<StudyTemplateParams> params = studyTemplate
			.getImsStudyTemplateParamsCollection();
	int totalRows = 0;
	int totalColumns = resulPreferenceComplete.getPreference().getResultsPreferencesDetailCollection().size();
	totalRows = imsLabStudy.getSampleDetailCollection().size();
	Integer countrow = 0;
	if (!platename.equals("All")) {
		for (SampleDetail sampleDetail : imsLabStudy
				.getSampleDetailCollection()) {
			if (sampleDetail.getPlatename().equals(platename))
				countrow = countrow + 1;
		}
		totalRows = countrow;
	}

	resulPreferenceComplete.setNumberRows(totalRows);
	int currRow = 0;

	ArrayList<ResultsPreferencesDetail> detailspreferences = (ArrayList<ResultsPreferencesDetail>)resulPreferenceComplete.getPreference()
			.getResultsPreferencesDetailCollection();
	List<RowResultDataBean>  listRowData= new ArrayList <RowResultDataBean>();
	for (SampleDetail sampleDetail : imsLabStudy
			.getSampleDetailCollection()) {
		RowResultDataBean rowResultDataBean = new RowResultDataBean();
		List<String> listCell = new ArrayList<String>();
		if (platename.equals("All")
				|| sampleDetail.getPlatename().equals(platename)) {
			for (ResultsPreferencesDetail detailpref : detailspreferences) {
				// para los campos fijos
				if (detailpref.getFijo().equals("S")){
					String str1 = obtenerDatoDeCampoFijoParaResults(
							sampleDetail, detailpref.getParamid());
					listCell.add(str1);
				}else {
					for (SampleDetResult detResult : sampleDetail
							.getImsSampleDetResultCollection()) {
						if (detResult.getTemplateparamid()
								.getTemplateparamid() == detailpref
								.getParamid()){
							String str1 = detResult.getResult();
							listCell.add(str1);
							
						}

					}
				}
			}
			rowResultDataBean.setListCell(listCell);
			listRowData.add(rowResultDataBean);
			currRow++;
		}
	}

		return listRowData;
	}

	public String obtenerDatoDeCampoFijoParaResults(SampleDetail sampledet,
			final Integer NumcampoFijo) {
		String res = null;
		String row;
		String col;
		// Se toma en cuenta la numeracion de ResultsCamposFijosUnEstudio
		switch (NumcampoFijo) {
		case 0:
			res = sampledet.getStudysampleid().toString();
			break;
		case 1:
			if (sampledet.getEntryNo() != null)
				res = sampledet.getEntryNo().toString();
			break;
		case 2:
			if (sampledet.getBreedergid() != null)
				res = sampledet.getBreedergid().toString();
			break;
		case 3:
			if (sampledet.getSamplegid() != null)
				res = sampledet.getLabstudyid().getPrefix()
						+ ( sampledet.getLabstudyid().isUsePadded() ? StrUtils.getPaddingCeros(sampledet.getSamplegid()): 
							String.valueOf(sampledet.getSamplegid()));
			break;
		case 4:
			if (sampledet.getPlatename() != null)
				res = sampledet.getPlatename();
			break;
		case 5:
			if (sampledet.getPlateloc() != null)
				res = sampledet.getPlateloc();
			break;
		case 6:
			if (sampledet.getPlateloc() != null)
				res = sampledet.getPlateloc().substring(0, 1);
			break;
		case 7:
			if (sampledet.getPlateloc() != null)
				res = sampledet.getPlateloc().substring(1,
						sampledet.getPlateloc().length());
			break;
		case 8:
			if (sampledet.getPlateloc() != null) {
				row = sampledet.getPlateloc().substring(0, 1);
				col = sampledet.getPlateloc().substring(1,
						sampledet.getPlateloc().length());
				if (col.length() == 1)
					col = "0" + col;
				res = row + col;
			}
			break;
		case 9:
			if (sampledet.getSelforsend() != null) {
				if (sampledet.getSelforsend().equals(
						ShipmentStatus.NO_SELECTED.getId()))
					res = ShipmentStatus.NO_SELECTED.getValue();

				if (sampledet.getSelforsend().equals(
						ShipmentStatus.FOR_SEND.getId()))
					res = ShipmentStatus.FOR_SEND.getValue();

				if (sampledet.getSelforsend().equals(
						ShipmentStatus.RECEIVED.getId()))
					res = ShipmentStatus.RECEIVED.getValue();

				if (sampledet.getSelforsend().equals(
						ShipmentStatus.SENT.getId()))
					res = ShipmentStatus.SENT.getValue();
			}
			break;
		case 10:
			if (sampledet.getNval() != null)
				res = sampledet.getNval();
			break;
		case 11:
			if (sampledet.getNplanta() != null)
				res = sampledet.getNplanta().toString();
			break;
		case 12:
			if (sampledet.getSpecie() != null)
				res = sampledet.getSpecie();
			break;
		case 13:
			if (sampledet.getPriority() != null)
				res = sampledet.getPriority();
			break;
		case 14:
			res = ControlType.NO_CONTROL.getValue();
			if (sampledet.getControltype() != null) {
				if (sampledet.getControltype().equals(
						ControlType.INDIVIDUAL.getId()))
					res = ControlType.INDIVIDUAL.getValue();// "Individual Control";
				if (sampledet.getControltype().equals(
						ControlType.RANDOM.getId()))
					res = ControlType.RANDOM.getValue();// "Random Control";
				if (sampledet.getControltype().equals(ControlType.DART.getId()))
					res = ControlType.DART.getValue();// "Dart Control";
				if (sampledet.getControltype()
						.equals(ControlType.BLANK.getId()))
					res = ControlType.BLANK.getValue();// "Blank";
				if (sampledet.getControltype().equals(ControlType.KBIO.getId()))
					res = ControlType.KBIO.getValue();// "KBiosciences Control";
			}
			break;
		case 15:
			if (sampledet.getStorageLocation() != null)
				res = sampledet.getStorageLocation().getLname();
			break;
		case 16:
			if (sampledet.getLocationid() != null)
				res = sampledet.getLocationid().getLocation_name();
			break;
		case 17:
			if (sampledet.getSeasonid() != null)
				res = sampledet.getSeasonid().getSeason_name();
			break;

		default:
			break;
		}
		return res;
	}

	public byte [] getReportResultData (ResultDataExportDataBean resultDataExport, PropertyHelper pro
			, SortedMap<Integer, ResultsPreferencesDetail> sortedMap){
		return serviceReportLaboratory.getReportResultData(resultDataExport, pro, sortedMap);
		
	}

	public CSVReportGenericBean getReportCSVReuslData (ResultDataExportDataBean resultDataExport, PropertyHelper pro
			, SortedMap<Integer, ResultsPreferencesDetail> sortedMap){
		return serviceReportLaboratory.getReportCSVReuslData(resultDataExport, pro, sortedMap);
	}
	
	/**
	 * Set bean of spring to services laboratory 
	 * @param serviceReportLaboratory
	 */
	public void setServiceReportLaboratory(
			ServiceReportLaboratory serviceReportLaboratory) {
		this.serviceReportLaboratory = serviceReportLaboratory;
	}
}
