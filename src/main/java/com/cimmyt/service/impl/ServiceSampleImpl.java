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

import com.cimmyt.model.bean.SampleID;
import com.cimmyt.model.dao.SampleIDDAO;
import com.cimmyt.service.ServiceSample;

public class ServiceSampleImpl implements ServiceSample{

	private SampleIDDAO sampleIDDAO;
	@Override
	public Integer searchSampleIDProjectInSt_SampleID(Integer idproject,
			Integer gid, Integer nplanta, Integer locationid, Integer seasonid) {
		// TODO Auto-generated method stub
		return sampleIDDAO.searchSampleIDProjectInSt_SampleID(
				idproject, gid, nplanta, locationid, seasonid);
	}

	@Override
	public Integer getLastSampleIDFromProject(Integer projectID) {
		return sampleIDDAO.GetLastSampleIDFromProject(projectID);
	}

	@Override
	public Integer getLastNPlantFromGIDinSampleID(Integer projectID,
			Integer GID, Integer locationid, Integer seasonid) {
		return sampleIDDAO.GetLastNPlantFromGIDinSampleID(
				projectID, GID, locationid, seasonid);
	}

	@Override
	public boolean searchSampleIDforLocation(Integer locationid) {
		return sampleIDDAO.searchSampleIDforLocation(locationid);
	}

	@Override
	public boolean searchSampleIDforSeason(Integer seasonid) {
		return sampleIDDAO.searchSampleIDforSeason(seasonid);
	}

	public void setSampleIDDAO(SampleIDDAO sampleIDDAO) {
		this.sampleIDDAO = sampleIDDAO;
	}

	public void saveOrUpdateSample(SampleID sampleid){
		this.sampleIDDAO.saveOrUpdateSamplesId(sampleid);
	}
}
