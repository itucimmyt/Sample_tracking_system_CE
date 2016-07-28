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
package org.cimmyt.dnast.bo.imp;

import java.util.List;

import org.cimmyt.dnast.bo.ProjectBO;
import org.cimmyt.dnast.dao.imp.StLabStudyHome;
import org.cimmyt.dnast.dao.imp.StProjectHome;
import org.cimmyt.dnast.dto.StLabStudy;
import org.cimmyt.dnast.dto.StProject;

public class ProjectImpBO implements ProjectBO {

	private StProjectHome projectHome;
	private StLabStudyHome studyHome;
	
	public List<StProject> getProjects(StProject project, Integer page) {	
		return projectHome.getListByBean(project, page);
	}


	public List<StLabStudy> getStudies(StLabStudy study, Integer page) {
		return studyHome.getListByBean(study, page);
	}
	
	/*
	public Set<StSampleDetail> getSamples(Integer studyId, Integer page) {
		//add paging
		StProject project = projectHome.getBeanById(4);

		StLabStudy ls = null;
		Set<StLabStudy> setls = project.getStLabStudies();
		System.out.println("studys: "+setls.size());
		
		for(StLabStudy l: setls){
			ls = l;
			if (l.getLabstudyid().intValue() == 140) break;
		}
		System.out.println("study: "+ls.getLabstudyid());
				
		return ls.getStSampleDetails();
	}
	
	*/
	
	
	public StProjectHome getProjectHome() {
		return projectHome;
	}

	public void setProjectHome(StProjectHome projectHome) {
		this.projectHome = projectHome;
	}

	public StLabStudyHome getStudyHome() {
		return studyHome;
	}

	public void setStudyHome(StLabStudyHome studyHome) {
		this.studyHome = studyHome;
	}


}
