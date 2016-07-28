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
package com.cimmyt.bean;

import com.cimmyt.model.bean.Project;


public class ProjectBean {

	private Integer projectid;
	private String projectname;
	private String projectdescription;
	private String purposename;
	private String purposedescription;
	private Integer lastsampleid;

	

	public Integer getProjectid() {
		return projectid;
	}

	public void setProjectid(Integer projectid) {
		this.projectid = projectid;
	}

	public String getProjectname() {
		return projectname;
	}

	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}
	
	

	/**
	 * @return the projectdescription
	 */
	public String getProjectdescription() {
		return projectdescription;
	}

	/**
	 * @param projectdescription the projectdescription to set
	 */
	public void setProjectdescription(String projectdescription) {
		this.projectdescription = projectdescription;
	}

	/**
	 * @return the purposename
	 */
	public String getPurposename() {
		return purposename;
	}

	/**
	 * @param purposename the purposename to set
	 */
	public void setPurposename(String purposename) {
		this.purposename = purposename;
	}

	/**
	 * @return the purposedescription
	 */
	public String getPurposedescription() {
		return purposedescription;
	}

	/**
	 * @param purposedescription the purposedescription to set
	 */
	public void setPurposedescription(String purposedescription) {
		this.purposedescription = purposedescription;
	}
	
	
	
	 public Integer getLastsampleid() {
		return lastsampleid;
	}

	public void setLastsampleid(Integer lastsampleid) {
		this.lastsampleid = lastsampleid;
	}

	@Override
	    public String toString() {
	    	return projectname+purposename;
	    }

	public Project getProject(ProjectBean pbean){
		Project project = new Project();
		project.setProjectid(pbean.getProjectid());
		project.setLastsampleid(pbean.getLastsampleid());
		project.setProjectdescription(pbean.getProjectdescription());
		project.setProjectname(pbean.getProjectname());
		project.setPurposedescription(pbean.getPurposedescription());
		project.setPurposename(pbean.getPurposename());
		return project;
	}

	public ProjectBean getProjectBean(Project project){
		ProjectBean projectB = new ProjectBean();
		projectB.setProjectid(project.getProjectid());
		projectB.setLastsampleid(project.getLastsampleid());
		projectB.setProjectdescription(project.getProjectdescription());
		projectB.setProjectname(project.getProjectname());
		projectB.setPurposedescription(project.getPurposedescription());
		projectB.setPurposename(project.getPurposename());
		return projectB;
	}

	public ProjectBean (){
		
	} 
	public ProjectBean (Project project){
		this.projectid = project.getProjectid();
		this.projectname = project.getProjectname();
		this.projectdescription = project.getProjectdescription();
		this.purposename = project.getPurposename();
		this.purposedescription = project.getPurposedescription();
		this.lastsampleid = project.getLastsampleid();
	}
}
