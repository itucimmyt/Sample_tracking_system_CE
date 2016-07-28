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
package org.cimmyt.dnast.service;

import java.util.List;

import org.cimmyt.dnast.dto.StLabStudy;
import org.cimmyt.dnast.dto.StProject;
import org.cimmyt.dnast.dto.StSampleDetail;


public interface ProjectService {

	public List<StProject> getProjects(String name, Integer page);
	public List<StLabStudy> getStudies(Integer projectId, Integer page);
	public List<StSampleDetail> getSamples(Integer projectId, Integer page);
}
