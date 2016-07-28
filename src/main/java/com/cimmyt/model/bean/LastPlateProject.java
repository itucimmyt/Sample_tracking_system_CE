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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * @author 
 *
 */

@Entity
@Table(name = "st_last_plate_project")
public class LastPlateProject implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="lastplateprojectid")
	private Integer lastplateprojectid;
	
	
	@JoinColumn(name = "projectid", referencedColumnName = "projectid")
	@ManyToOne(optional = false)
	private Project projectid;
	
	@JoinColumn(name = "organismid", referencedColumnName = "organismid")
	@ManyToOne(optional = false)
	private Organism organismid;
	
	@JoinColumn(name = "investigatorid", referencedColumnName = "investigatorid")
	@ManyToOne(optional = false)
	private Investigator investigatorid;
	
	@Column(name = "platenumber")
	private Integer platenumber;

	public Project getProjectid() {
		return projectid;
	}

	public void setProjectid(Project projectid) {
		this.projectid = projectid;
	}

	public Organism getOrganismid() {
		return organismid;
	}

	public void setOrganismid(Organism organismid) {
		this.organismid = organismid;
	}

	public Investigator getInvestigatorid() {
		return investigatorid;
	}

	public void setInvestigatorid(Investigator investigatorid) {
		this.investigatorid = investigatorid;
	}

	public Integer getPlatenumber() {
		return platenumber;
	}

	public void setPlatenumber(Integer platenumber) {
		this.platenumber = platenumber;
	}

	public Integer getLastplateprojectid() {
		return lastplateprojectid;
	}

	public void setLastplateprojectid(Integer lastplateproyectid) {
		this.lastplateprojectid = lastplateproyectid;
	}
	
	
	
	
	

}

