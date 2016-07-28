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

import org.cimmyt.dnast.bo.StudyBO;
import org.cimmyt.dnast.dao.imp.StLabStudyHome;
import org.cimmyt.dnast.dao.imp.StSampleDetailHome;
import org.cimmyt.dnast.dto.StLabStudy;
import org.cimmyt.dnast.dto.StSampleDetail;

public class StudyImpBO implements StudyBO{

	private StLabStudyHome studyHome;
	private StSampleDetailHome sampleHome;
	
	@Override
	public List<StLabStudy> getStudies(StLabStudy study, Integer page) {
		return studyHome.getListByBean(study, page);
	}

	@Override
	public List<StSampleDetail> getSamples(StSampleDetail sample, Integer page) {
		return sampleHome.getListByBean(sample, page);
	}

	
	public StLabStudyHome getStudyHome() {
		return studyHome;
	}

	public void setStudyHome(StLabStudyHome studyHome) {
		this.studyHome = studyHome;
	}

	public StSampleDetailHome getSampleHome() {
		return sampleHome;
	}

	public void setSampleHome(StSampleDetailHome sampleHome) {
		this.sampleHome = sampleHome;
	}


	
	
	



}
