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
@Table(name = "st_sampleid")
public class SampleID implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idsample")
	private Integer identificsample;
	
	//@Column(name = "projectid")
	@JoinColumn(name = "projectid", referencedColumnName = "projectid")
	@ManyToOne(optional = false)
	private Project projectid;
	
	@Column(name = "gid")
	private Integer gid;
	
	@Column(name = "nplant")
	private Integer nplant;
	
	//@Column(name = "locationid")
	@JoinColumn(name = "locationid", referencedColumnName = "locationid")
	@ManyToOne(optional = false)
	private LocationCatalog locationid;
	
	//@Column(name = "seasonid")
	@JoinColumn(name = "seasonid", referencedColumnName = "seasonid")
	@ManyToOne(optional = false)
	private Season seasonid;
	
	
	@Column(name = "sampleid")
	private Integer sampleid;

	public Integer getIdentificsample() {
		return identificsample;
	}

	public void setIdentificsample(Integer identificsample) {
		this.identificsample = identificsample;
	}

	public Project getProjectid() {
		return projectid;
	}

	public void setProjectid(Project projectid) {
		this.projectid = projectid;
	}

	public Integer getGid() {
		return gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

	public Integer getNplant() {
		return nplant;
	}

	public void setNplant(Integer nplant) {
		this.nplant = nplant;
	}
	
	

	/**
	 * @return the locationid
	 */
	public LocationCatalog getLocationid() {
		return locationid;
	}

	/**
	 * @param locationid the locationid to set
	 */
	public void setLocationid(LocationCatalog locationid) {
		this.locationid = locationid;
	}

	/**
	 * @return the seasonid
	 */
	public Season getSeasonid() {
		return seasonid;
	}

	/**
	 * @param seasonid the seasonid to set
	 */
	public void setSeasonid(Season seasonid) {
		this.seasonid = seasonid;
	}

	public Integer getSampleid() {
		return sampleid;
	}

	public void setSampleid(Integer sampleid) {
		this.sampleid = sampleid;
	}

	public String toString(){
		return " idsample: "+identificsample + " projectid: "+projectid+ " gid: "+gid ;
	}
	
}

