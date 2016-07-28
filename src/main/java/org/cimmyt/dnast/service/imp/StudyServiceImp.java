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
package org.cimmyt.dnast.service.imp;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.cimmyt.dnast.bo.StudyBO;
import org.cimmyt.dnast.dto.StLabStudy;
import org.cimmyt.dnast.dto.StSampleDetail;
import org.cimmyt.dnast.service.StudyService;
import org.jboss.resteasy.annotations.providers.jaxb.Wrapped;
import org.jboss.resteasy.spi.validation.ValidateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zkoss.zhtml.Object;

@Service
@Path("/studies")
@ValidateRequest
public class StudyServiceImp implements StudyService{

	@Autowired
	private StudyBO studyBO;
	

	/**
	 * Retrieves all studies in the DB, one page at a time
	 * @param projectId The id of the project
	 * @param page The number of the page with data to retrieve
	 * @return A page in the resultset of studies
	 */
	@GET
	@Path("/")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Wrapped(element="studies")
	public List<StLabStudy> getStudies(	@HeaderParam("Range")Integer page) {
		return studyBO.getStudies(new StLabStudy(), page == null ?Integer.valueOf(0) : page);
	}

	/**
	 * Retrieves all studies in a project, one page at a time
	 * @param projectId The id of the project
	 * @param page The number of the page with data to retrieve
	 * @return A page in the resultset of studies
	 */
/*	@GET
	@Path("/project/{idProj}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Wrapped(element="studies")
	public List<StLabStudy> getStudies(@PathParam("idProj") Integer projectId, 
										@HeaderParam("Range")Integer page) {
		StLabStudy study = new StLabStudy();
		study.setProjectId(projectId);
		return studyBO.getStudies(study, page == null ?Integer.valueOf(0) : page);
	}
*/
	/**
	 * Retrieves all samples in a study, one page at a time
	 * @param studyId The id of the study
	 * @param page The number of the page with data to retrieve
	 * @return A page in the resultset of samples
	 */
	@GET
	@Path("/{idStudy}/samples")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Wrapped(element="samples")
	public List<StSampleDetail> getSamples(@PathParam("idStudy")Integer studyId, 
										@HeaderParam("Range")Integer page) {
		StSampleDetail sample = new StSampleDetail();
		sample.setStudyId(studyId);
		return studyBO.getSamples(sample, page == null ?Integer.valueOf(0) : page);
	}

	
	public StudyBO getProjectBO() {
		return studyBO;
	}

	public void setProjectBO(StudyBO projectBO) {
		this.studyBO = projectBO;
	}
}
