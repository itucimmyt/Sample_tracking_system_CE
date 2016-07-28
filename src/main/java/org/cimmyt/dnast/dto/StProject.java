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
package org.cimmyt.dnast.dto;
// default package
// Generated Apr 23, 2013 11:57:22 PM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ext.Provider;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Provider
@XmlRootElement(name="project")
public class StProject implements java.io.Serializable {

	private Integer projectid;
	private String projectName;
	private String projectDescription;
	private String purposeName;
	private String purposeDescription;
	private Integer lastsampleid;
/*	@XmlTransient
	private Set<StLastPlateProject> stLastPlateProjects = new HashSet<StLastPlateProject>(0);*/
	private Set<StLabStudy> stLabStudies = new HashSet<StLabStudy>(0);

	public StProject() {
	}

	public StProject(String projectName, String projectDescription,
			String purposeName, String purposeDescription,
			Integer lastsampleid,
			Set<StLabStudy> stLabStudies) {
		this.projectName = projectName;
		this.projectDescription = projectDescription;
		this.purposeName = purposeName;
		this.purposeDescription = purposeDescription;
		this.lastsampleid = lastsampleid;
//		this.stLastPlateProjects = stLastPlateProjects;
		this.stLabStudies = stLabStudies;
	}

@XmlElement(name="idProject")
	public Integer getProjectid() {
		return this.projectid;
	}

	public void setProjectid(Integer projectid) {
		this.projectid = projectid;
	}

	public String getProjectName() {
		return this.projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectDescription() {
		return this.projectDescription;
	}

	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}

	@XmlTransient
	public String getPurposeName() {
		return this.purposeName;
	}

	public void setPurposeName(String purposeName) {
		this.purposeName = purposeName;
	}

	@XmlTransient
	public String getPurposeDescription() {
		return this.purposeDescription;
	}

	public void setPurposeDescription(String purposeDescription) {
		this.purposeDescription = purposeDescription;
	}

	@XmlTransient
	public Integer getLastsampleid() {
		return this.lastsampleid;
	}

	public void setLastsampleid(Integer lastsampleid) {
		this.lastsampleid = lastsampleid;
	}

	@XmlTransient
	public Set<StLabStudy> getStLabStudies() {
		return this.stLabStudies;
	}

	public void setStLabStudies(Set<StLabStudy> stLabStudies) {
		this.stLabStudies = stLabStudies;
	}

}
