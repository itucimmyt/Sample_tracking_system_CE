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

import com.cimmyt.model.bean.LabStudy;
import com.cimmyt.model.bean.LastPlateProject;
import com.cimmyt.model.dao.LastPlateProjectDAO;
import com.cimmyt.service.ServiceLastPlateProject;

public class ServiceLastPlateProjectImpl implements ServiceLastPlateProject{

	private LastPlateProjectDAO lastPlateProjectDAO;
	
	public LastPlateProject getLastPlateNumberOfStudyLab(final LabStudy labstudy){
		return lastPlateProjectDAO.getLastPlateNumberOfStudyLab(labstudy);
	}
	public Integer getNextPlateNumber(final Integer projectId,
			final Integer organismId, final Integer investigatorId){
		return lastPlateProjectDAO.getNextPlateNumber(projectId, organismId, investigatorId);
	}
	public void addOrUpdateLastPlateProjectNumber(LastPlateProject lastplateProject){
		lastPlateProjectDAO.addOrUpdateLastPlateProjectNumber(lastplateProject);
	}
	public void setLastPlateProjectDAO(LastPlateProjectDAO lastPlateProjectDAO) {
		this.lastPlateProjectDAO = lastPlateProjectDAO;
	}

	
}
