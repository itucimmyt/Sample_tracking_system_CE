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

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.cimmyt.dnast.bo.imp.ProjectImpBO;
import org.cimmyt.dnast.dto.StLabStudy;
import org.cimmyt.dnast.dto.StProject;
import org.cimmyt.dnast.dto.StSampleDetail;
import org.cimmyt.dnast.service.ProjectService;
import org.jboss.resteasy.annotations.providers.jaxb.Wrapped;
import org.jboss.resteasy.spi.validation.ValidateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Path("/projects")
@ValidateRequest
public class ProjectServiceImp implements ProjectService{

	@Autowired
	private ProjectImpBO projectBO;
	

	public List<StProject> getProjects(StProject project) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Retrieves all projects in the DB, one page at a time
	 * @param page The page of the resultset to be retrieved
	 * @return A page in the resultset of projects
	 */
	@GET
	@Path("/")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Wrapped(element="projects")
	public List<StProject> getProjects( @QueryParam("name")String projectName,
										@HeaderParam("Range")Integer page) {
		StProject pr = new StProject();
		pr.setProjectName(projectName);
		return projectBO.getProjects(pr, page == null ?Integer.valueOf(0) : page);
	}
	
	/**
	 * Get all studies in a provided project.
	 */
	@GET
	@Path("/{idProj}/studies")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Wrapped(element="studies")
	public List<StLabStudy> getStudies(@PathParam("idProj")Integer projectId,
									  @HeaderParam("Range")Integer page) {
		StLabStudy study = new StLabStudy();
		study.setProjectId(projectId);
		return projectBO.getStudies(study, page == null ?Integer.valueOf(0) : page);
	}

	// pendiente
	/**
	 * Get all samples in a project.
	 */
	@GET
	@Path("/{idProj}/samples")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Wrapped(element="samples")
	public List<StSampleDetail> getSamples(@PathParam("idProj")Integer projectId,
									  @HeaderParam("Range")Integer page) {
		return null;
	}

	
	
	
	
	public ProjectImpBO getProjectBO() {
		return projectBO;
	}

	public void setProjectBO(ProjectImpBO projectBO) {
		this.projectBO = projectBO;
	}


}
