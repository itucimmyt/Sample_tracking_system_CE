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
package com.cimmyt.model.dao;

import java.util.List;

import org.cimmyt.dnast.dto.DsSearchParam;

import com.cimmyt.model.bean.LabStudy;
import com.cimmyt.model.bean.Project;
import com.cimmyt.model.bean.StudyTemplate;
import com.cimmyt.model.bean.Tissue;

public interface LabStudyDAO {

	public LabStudy readStudyOnlySampleDetail(final Integer id);
	public LabStudy readStudyWithResults(final Integer id);
	public boolean ThisInvestigatorHasLabStudyRegistred(final Integer investigatorId);
	public boolean ThisProjectHasLabStudyRegistred(final Project project);
	public boolean ThisTissueHasLabStudyRegistred(final Tissue tissue);
	public boolean ThisTemplateHasLabStudyRegistred(final StudyTemplate template);
	public List<LabStudy> getLabStudys(LabStudy filter);
	public List<LabStudy> getLabStudysByIdResearch(LabStudy filter, Integer idResearch);
	public Integer getLabStudysByIdResearchTotal(LabStudy filter, Integer idResearch);
	public List<LabStudy> getLabStudysByIdResearch(LabStudy filter, Integer idResearch, int firstResult, int maxResults
			, String sortColumn, boolean ascending);
	public void createStudy(LabStudy newInstance);
	public List<LabStudy> getCustomQuery (List<DsSearchParam> params, final int firstResult, final int maxResults);
	public Integer getTotalRowsCustomQuery(final List<DsSearchParam> params);
	public Integer getTotalRowsByIdResearch(LabStudy filter, Integer idResearch);
}
