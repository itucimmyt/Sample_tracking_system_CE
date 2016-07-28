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
package com.cimmyt.service;

import java.util.List;
import java.util.Map;

import org.cimmyt.dnast.dto.DsSearchParam;

import com.cimmyt.bean.ProjectBean;
import com.cimmyt.bean.TissueBean;
import com.cimmyt.model.bean.LabStudy;
import com.cimmyt.model.bean.SampleDetResult;
import com.cimmyt.model.bean.SampleDetail;
import com.cimmyt.model.bean.StudyTemplate;
import com.cimmyt.model.bean.StudyTemplateParams;
import com.cimmyt.model.bean.TemporalSample;

public interface ServiceLabStudy {

	public boolean ThisProjectHasStudiesRegistred(ProjectBean project);
	public boolean ThisInvestigatorHasStudiesRegistred(Integer investid);
	public boolean ThisTissueHasLabStudyRegistred(TissueBean bean);
	public LabStudy readStudyOnlySampleDetail( Integer id);
	public LabStudy readStudyWithResults( Integer id);
	public boolean ThisTemplateHasLabStudyRegistred( StudyTemplate template);
	public List<LabStudy> getLabStudys(LabStudy filter);
	public List<LabStudy> getLabStudysByIdResearch(LabStudy filter, Integer idResearch);
	public Integer getLabStudysByIdResearchTotal(LabStudy filter, Integer idResearch);
	public List<LabStudy> getLabStudysByIdResearch(LabStudy filter, Integer idResearch, int firstResult, int maxResults
			, String sortColumn, boolean ascending);
	public void addLabStudy(LabStudy imsLabStudy, boolean isEdit, Map <Integer , SampleDetail> mapSamplesDelete, Map <Integer , SampleDetail> mapSamplesUpdate, List<TemporalSample> listTempsample);
	public void deleteStudy(Integer idStudy);
	public byte[] getReportPlate (LabStudy beanStudy, List<Object> listFieldsObjets, List<StudyTemplateParams> listStudyTemParams);
	public Integer getTotalRowsByIdResearch(LabStudy filter, Integer idResearch);
	public SampleDetResult getSampleDetResultBySampleDetailIdAndTemplateParamId(
			Integer sampleId, Integer paramId);
	public void updateSampleDetResult(SampleDetResult sampleDetResult);
	public Integer getTotalRowsCustomQuery(List<DsSearchParam> params);
	public List<LabStudy> getCustomQuery(List<DsSearchParam> params, int firstResult, int maxResults);
}
