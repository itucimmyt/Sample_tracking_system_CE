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
import com.cimmyt.model.bean.Project;
import com.cimmyt.model.dao.ProjectDAO;
import com.cimmyt.service.ServiceProject;

public class ServiceProyectImpl implements ServiceProject {

	private ProjectDAO projectDAO;

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
}
