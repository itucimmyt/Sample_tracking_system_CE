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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cimmyt.bean.ProjectBean;
import com.cimmyt.model.bean.Program;
import com.cimmyt.model.bean.Project;
import com.cimmyt.model.bean.Purpose;
import com.cimmyt.model.dao.ProgramDAO;
import com.cimmyt.model.dao.ProjectDAO;
import com.cimmyt.model.dao.PurposeDAO;
import com.cimmyt.service.ServiceProject;

public class ServiceProyectImpl implements ServiceProject {

	//Bean project DAO
	private ProjectDAO projectDAO;
	//Bean program DAO	
	private ProgramDAO programDAO;
	//Bean purpose DAO
	private PurposeDAO purposeDAO;

	/**
	 * Get List Projects exist in the table
	 * @param ProjectBean projectBean
	 */
	@Override
	public List<ProjectBean> getProject(ProjectBean projectBean) {
	List<Project> listProject =	projectDAO.getProjects(projectBean.getProject(projectBean));
		if (listProject != null && !listProject.isEmpty() ){
			List<ProjectBean> listProjectBean = new ArrayList<ProjectBean>();
			for (Project project : listProject){
				listProjectBean.add(new ProjectBean(project));
				}
			return listProjectBean;
			}
		return null;
	}
	/**
	 * Save or Update Project Bean
	 * @param projectBean
	 */
	@Override
	public void saveOrUpdateProject (ProjectBean projectBean){
		projectDAO.saveOrUpdateProject(projectBean.getProject(projectBean));
	}

	public void setProjectDAO(ProjectDAO projectDAO) {
		this.projectDAO = projectDAO;
	}
	/**
	 * Delete Project
	 *@param ProjectBean projectBean
	 *@throws SQLException
	 */
	@Override
	public void deleteProject(ProjectBean projectBean) {
		projectDAO.deleteProject(projectBean.getProject(projectBean));
	} 
	/**
	 *Get Last sample Id 
	 * @param project
	 * @return
	 */
	public Integer getLastSampleID(final ProjectBean project){
		return projectDAO.getLastSampleID(project.getProject(project));
	}
	/**
	 * Get Project With name 
	 * @param name
	 * @return
	 */
	public ProjectBean getProjectWithName(final String name){
		Project project = projectDAO.getProjectWithName(name);
		if (project != null){
			return new  ProjectBean(project);
		}
		return null; 
	}
	@Override
	public void saveOrUpdateProgram(Program filter) {
		programDAO.saveOrUpdate(filter);
	}
	@Override
	public List<Program> getListProgram(Program filter, boolean areAll) {
		return programDAO.getListProgram(filter, areAll);
	}
	@Override
	public void saveOrUpdatePurpose(Purpose filter) {
		purposeDAO.saveOrUpdate(filter);
	}
	@Override
	public List<Purpose> getListPurpose(Purpose filter, boolean areAll) {
		return purposeDAO.getListPurpose(filter, areAll);
	}

	public void setProgramDAO(ProgramDAO programDAO) {
		this.programDAO = programDAO;
	}
	public void setPurposeDAO(PurposeDAO purposeDAO) {
		this.purposeDAO = purposeDAO;
	}
	
}
