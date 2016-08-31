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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.cimmyt.dnast.dto.DsSearchParam;

import com.cimmyt.bean.ProjectBean;
import com.cimmyt.bean.StudyLabReportBean;
import com.cimmyt.bean.TissueBean;
import com.cimmyt.model.bean.LabStudy;
import com.cimmyt.model.bean.LastPlateProject;
import com.cimmyt.model.bean.SampleDetResult;
import com.cimmyt.model.bean.SampleDetail;
import com.cimmyt.model.bean.SampleID;
import com.cimmyt.model.bean.StudyTemplate;
import com.cimmyt.model.bean.StudyTemplateParams;
import com.cimmyt.model.bean.TemporalSample;
import com.cimmyt.model.dao.LabStudyDAO;
import com.cimmyt.model.dao.LastPlateProjectDAO;
import com.cimmyt.model.dao.MyQLProcedureDAO;
import com.cimmyt.model.dao.SampleDetResultDAO;
import com.cimmyt.model.dao.SampleIDDAO;
import com.cimmyt.model.dao.StudyTemplateDAO;
import com.cimmyt.model.dao.TempSampleDAO;
import com.cimmyt.reports.ServiceReportLaboratory;
import com.cimmyt.service.ServiceLabStudy;
import com.cimmyt.study.PlateContentList;
import com.cimmyt.study.PlateRow;
import com.cimmyt.utils.ConstantsDNA;
import com.cimmyt.utils.StrUtils;

public class ServiceLabStudyImpl implements ServiceLabStudy {

	private LabStudyDAO labStudyDAO;
	private  LastPlateProjectDAO lastPlateProjectDAO;
	private StudyTemplateDAO studyTemplateDAO;
	private SampleIDDAO sampleIDDAO;
	private SampleDetResultDAO sampleDetResultDAO;
	private MyQLProcedureDAO mySqlProcedure;
	private ServiceReportLaboratory serviceReportLaboratory;
	private TempSampleDAO tempSampleDAO;
	private Logger logger= Logger.getLogger(ServiceLabStudyImpl.class);
    
	@Override
	public boolean ThisProjectHasStudiesRegistred(ProjectBean projectBean) {
		return labStudyDAO.ThisProjectHasLabStudyRegistred(projectBean.getProject(projectBean));
	}

	public boolean ThisInvestigatorHasStudiesRegistred(Integer investid){
		return labStudyDAO.ThisInvestigatorHasLabStudyRegistred(investid);
	}

	public boolean ThisTissueHasLabStudyRegistred(TissueBean bean){
		return labStudyDAO.ThisTissueHasLabStudyRegistred(bean.getTissue(bean));
	}

	public void setLabStudyDAO(LabStudyDAO labStudyDAO) {
		this.labStudyDAO = labStudyDAO;
	}

	@Override
	public LabStudy readStudyOnlySampleDetail(Integer id) {
		return labStudyDAO.readStudyOnlySampleDetail(id);
	}

	@Override
	public LabStudy readStudyWithResults(Integer id) {
		return labStudyDAO.readStudyWithResults(id);
	}

	@Override
	public boolean ThisTemplateHasLabStudyRegistred(StudyTemplate template) {
		return labStudyDAO.ThisTemplateHasLabStudyRegistred(template);
	}

	@Override
	public List<LabStudy> getLabStudys(LabStudy filter) {
		return labStudyDAO.getLabStudys(filter);
	}

	public List<LabStudy> getLabStudysByIdResearch(LabStudy filter, Integer idResearch){
		return labStudyDAO.getLabStudysByIdResearch(filter,idResearch);
	}

	/**
	 * Method that save the samples with these are new, take the last number of plate for study, and save these in data base
	 * @param LabStudy is the object that save.
	 */
	@Override
	public void addLabStudy(LabStudy labStudy, boolean isEdit, 
			Map <Integer , SampleDetail> mapSamplesDelete, Map <Integer , SampleDetail> mapSamplesUpdate,
			List<TemporalSample> listTempsample) {
		logger.info("Init add laboratory study save project : "+ (new SimpleDateFormat("mm:ss:SSS")).format(new Date()));
		LastPlateProject lastpp = new LastPlateProject();
		lastpp = lastPlateProjectDAO.getLastPlateNumberOfStudyLab(
				labStudy);
		lastpp.setPlatenumber(lastpp.getPlatenumber()
				+ labStudy.getNumofplates());
		lastPlateProjectDAO.addOrUpdateLastPlateProjectNumber(lastpp);
		if (!isEdit){
			StudyTemplate template = studyTemplateDAO.read(labStudy.getStudytemplateid().getStudytemplateid());
			for (SampleDetail sampleDetail : labStudy.getSampleDetailCollection()) {
				sampleDetail.setLabstudyid(labStudy);
				Collection<SampleDetResult> imsSampleDetResultCollection = new ArrayList<SampleDetResult>();
				for (StudyTemplateParams params : template
						.getImsStudyTemplateParamsCollection()) {
					SampleDetResult detResult = new SampleDetResult();
					detResult.setResult("");
					detResult.setTemplateparamid(params);
					detResult.setStudysampleid(sampleDetail);
					imsSampleDetResultCollection.add(detResult);
				}
				sampleDetail
						.setImsSampleDetResultCollection(imsSampleDetResultCollection);
			}
		}else {
			updateSampleDetResult(mapSamplesDelete);
			updateSampleDetResult(mapSamplesUpdate);
			deleteSampleDetID(mapSamplesDelete, labStudy.getProject().getProjectid());
		}
		deleteSamplesTemporaly(listTempsample);
		labStudyDAO.createStudy(labStudy, isEdit);
		if (isEdit)
			saveLastSamples(labStudy);
	}
	private void deleteSamplesTemporaly(List<TemporalSample> listTempsample){
		if (listTempsample != null && !listTempsample.isEmpty()){
		for (TemporalSample temp : listTempsample){
			temp= tempSampleDAO.getTempsample(temp);
				tempSampleDAO.deleteWithSesson(temp);
				}
			}
				
		
	}
	@SuppressWarnings("rawtypes")
	private void updateSampleDetResult (Map <Integer , SampleDetail> mapSamplesUpdate){
		if (mapSamplesUpdate != null){
			Iterator iterator = mapSamplesUpdate.entrySet().iterator();
			while (iterator.hasNext()){
				Map.Entry e = (Map.Entry)iterator.next();
				SampleDetail sampleDetail = (SampleDetail)e.getValue();
				List<SampleDetResult> listSampleDetResult = 
						sampleDetResultDAO.getListSamplesDetResultByIdSampleID(sampleDetail.getStudysampleid());
				if (listSampleDetResult != null && !listSampleDetResult.isEmpty()){
					for (SampleDetResult result : listSampleDetResult){
						result.setResult("");
						sampleDetResultDAO.saveOrUpdate(result);
					}
				}	
			}	
		}
	}
	/**
	 * Method that delete the samples in the table sample detail result
	 * @param mapSamplesDelete
	 */
	@SuppressWarnings("rawtypes")
	private void deleteSampleDetID (Map <Integer , SampleDetail> mapSamplesDelete, Integer projectID){
		if (mapSamplesDelete != null){
			Iterator iterator = mapSamplesDelete.entrySet().iterator();
			while (iterator.hasNext()){
				Map.Entry e = (Map.Entry)iterator.next();
				SampleDetail newsampleid = (SampleDetail)e.getValue();
				SampleID sampleID = sampleIDDAO.getSearchSampleIDProjectInStSampleID(projectID,
						newsampleid.getBreedergid(), newsampleid.getNplanta(), 
						newsampleid.getLocationid().getLocationid(), 
						newsampleid.getSeasonid().getSeasonid());
				if (sampleID != null)
				sampleIDDAO.deleteSampleID(sampleID);
			}	
		}
	}

	/**
	 * method that save the last sample in table st_project
	 * @param lastPlateProjectDAO
	 */
	@SuppressWarnings("unused")
	private void saveLastSamples(LabStudy labStudy){
		for (SampleDetail detail : labStudy.getSampleDetailCollection()){
			if (detail.getNplanta() != null && detail.getBreedergid() != null){
				SampleID newsampleid = new SampleID();
				newsampleid.setProjectid(labStudy.getProject());
				newsampleid.setGid(detail.getBreedergid());
				newsampleid.setNplant(detail.getNplanta());
				newsampleid.setSampleid(detail.getSamplegid());
				if (detail.getSeasonid() != null)
					newsampleid.setSeasonid(detail.getSeasonid());
				if (detail.getLocationid() != null)
					newsampleid.setLocationid(detail.getLocationid());
				if (sampleIDDAO.searchSampleIDProjectInSt_SampleID(labStudy.getProject().getProjectid(),
						newsampleid.getGid(), newsampleid.getNplant(), 
						newsampleid.getLocationid().getLocationid(), 
						newsampleid.getSeasonid().getSeasonid()) == 0){
					sampleIDDAO.saveOrUpdateSamplesId(newsampleid);
				}
			} 
		}
	}
	/**
	 * Method that delete a study by id
	 * @param idStudy
	 */
	public void deleteStudy(Integer idStudy){
		mySqlProcedure.exceuteDeleteStudySP(idStudy);		
	}

	public void setMySqlProcedure(MyQLProcedureDAO mySqlProcedure) {
		this.mySqlProcedure = mySqlProcedure;
	}

	public void setLastPlateProjectDAO(LastPlateProjectDAO lastPlateProjectDAO) {
		this.lastPlateProjectDAO = lastPlateProjectDAO;
	}

	public void setStudyTemplateDAO(StudyTemplateDAO studyTemplateDAO) {
		this.studyTemplateDAO = studyTemplateDAO;
	}

	public void setSampleIDDAO(SampleIDDAO sampleIDDAO) {
		this.sampleIDDAO = sampleIDDAO;
	}

	public void setSampleDetResultDAO(SampleDetResultDAO sampleDetResultDAO) {
		this.sampleDetResultDAO = sampleDetResultDAO;
	}

	/**
	 * Method that create the object to management of information about plate
	 * @param Bean study 
	 * @return byte [] of object to will be put in report XLS
	 */
	public byte[] getReportPlate (LabStudy beanStudy, List<Object> _listFieldsObjets,List<StudyTemplateParams> listStudyTemParams){
		if (beanStudy.getSampleDetailCollection() != null 
				&& beanStudy.getSampleDetailCollection().size() > 0){
			SortedMap <Integer, Map<String , SampleDetail>> mapPlate = new TreeMap <Integer, Map<String , SampleDetail>>();
			Collection<SampleDetail> collection = beanStudy.getSampleDetailCollection();
			//Pattern intsOnly = Pattern.compile("\\d+");
			
			Iterator<SampleDetail>  iterator= collection.iterator();
			String prefixPlate ="";
			while (iterator.hasNext()){
				SampleDetail detail = iterator.next();
				//Matcher makeMatch = intsOnly.matcher(detail.getPlatename());
				//makeMatch.find();
				//String inputInt = makeMatch.group();
				 prefixPlate = beanStudy.getProject().getProjectname().toUpperCase()+beanStudy.getProject().getPurposename().toUpperCase()+StrUtils.getCropById(beanStudy.getOrganismid().getOrganismid())+beanStudy.getInvestigatorid().getInvest_abbreviation().toUpperCase();
				Integer numberPlate = Integer.parseInt(detail.getPlatename().substring(prefixPlate.length(), detail.getPlatename().length()));
					if (mapPlate.containsKey(numberPlate)){
						Map<String , SampleDetail> mapDetail =
						(Map<String , SampleDetail>)mapPlate.get(numberPlate);
						mapDetail.put(detail.getPlatename()+detail.getPlateloc(), detail);
					}else {
						Map<String , SampleDetail> mapDetail = new HashMap<String, SampleDetail>();
						mapDetail.put(detail.getPlatename()+detail.getPlateloc(), detail);
						mapPlate.put(numberPlate, mapDetail);
					}
				}
			StudyLabReportBean beanReport = new StudyLabReportBean ();
			beanReport.setMapPlateSamples(mapPlate);
			beanReport.setPrefix(beanStudy.getPrefix());
			beanReport.setPatternPlate(prefixPlate);
			if (beanStudy.getPlatesize().intValue() == ConstantsDNA.SIZE_PLATE_96){
				beanReport.setNameRow(PlateContentList.letters96);
				beanReport.setNumberColumn(PlateRow.COLS_PLATE_96);
			}
			if (beanStudy.getPlatesize().intValue() == ConstantsDNA.SIZE_PLATE_384){
				beanReport.setNameRow(PlateContentList.letters384);
				beanReport.setNumberColumn(PlateRow.COLS_PLATE_384);
			}
			return serviceReportLaboratory.getBytesReportLaboratoryPlate(beanReport, _listFieldsObjets, listStudyTemParams);
		}else {
			return null;
		}
	}

	/**
	 * Set bean of spring to services laboratory 
	 * @param serviceReportLaboratory
	 */
	public void setServiceReportLaboratory(
			ServiceReportLaboratory serviceReportLaboratory) {
		this.serviceReportLaboratory = serviceReportLaboratory;
	}

	@Override
	public Integer getTotalRowsByIdResearch(LabStudy filter, Integer idResearch) {
		return labStudyDAO.getTotalRowsByIdResearch(filter, idResearch);
	}

	public List<LabStudy> getLabStudysByIdResearch(LabStudy filter, Integer idResearch, int firstResult, int maxResults
			, String sortColumn, boolean ascending) {
		return labStudyDAO.getLabStudysByIdResearch(filter, idResearch, firstResult, maxResults, sortColumn, ascending);
	}
	public SampleDetResult getSampleDetResultBySampleDetailIdAndTemplateParamId(
			Integer sampleId, Integer paramId){
		return sampleDetResultDAO.getSampleDetResultBySampleDetailIdAndTemplateParamId(sampleId, paramId); 
	}

	public void updateSampleDetResult(SampleDetResult sampleDetResult){
		sampleDetResultDAO.saveOrUpdate(sampleDetResult);
	}

	public Integer getTotalRowsCustomQuery(List<DsSearchParam> params) {
		return labStudyDAO.getTotalRowsCustomQuery(params);
	}

	public List<LabStudy> getCustomQuery(List<DsSearchParam> params,
			int firstResult, int maxResults) {
		return labStudyDAO.getCustomQuery(params, firstResult, maxResults);
	}

	public Integer getLabStudysByIdResearchTotal(LabStudy filter,
			Integer idResearch) {
		return labStudyDAO.getLabStudysByIdResearchTotal(filter, idResearch);
	}

	public void setTempSampleDAO(TempSampleDAO tempSampleDAO) {
		this.tempSampleDAO = tempSampleDAO;
	}

	
}
