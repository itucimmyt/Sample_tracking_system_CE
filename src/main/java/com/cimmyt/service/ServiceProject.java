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

import com.cimmyt.bean.ProjectBean;
import com.cimmyt.model.bean.Program;
import com.cimmyt.model.bean.Purpose;

public interface ServiceProject {

	public List<ProjectBean> getProject(ProjectBean projectBean);
	public void saveOrUpdateProject(ProjectBean projectBean);
	public void deleteProject (ProjectBean projectBean);
	public ProjectBean getProjectWithName(final String name);
	public Integer getLastSampleID(final ProjectBean project);
	public void saveOrUpdateProgram (Program filter);
	public List<Program> getListProgram(Program filter, boolean areAll);
	public void saveOrUpdatePurpose (Purpose filter);
	public List<Purpose> getListPurpose(Purpose filter, boolean areAll);
}
