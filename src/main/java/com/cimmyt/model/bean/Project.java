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
package com.cimmyt.model.bean;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 
 * @author 
 *
 */
@Entity
@Table(name = "st_project")
public class Project implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 586602042666482320L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="projectid")
	private Integer projectid;
	
	@Column(name = "projectName")
	private String projectname;
	
	@Column(name = "projectDescription")
	private String projectdescription;
	
	@Column(name = "purposeName")
	private String purposename;
	
	@Column(name = "purposeDescription")
	private String purposedescription;
	
	@Column(name = "lastsampleid")
	private Integer lastsampleid;

	public Project() {
		super();
	}

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
}

