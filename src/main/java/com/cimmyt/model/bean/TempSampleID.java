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
@Table(name = "st_temp_sampleid")
public class TempSampleID implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idconsec")
	private Integer idconsec;
	
	@Column(name = "gid")
	private Integer gid;
	
	@Column(name = "nplant")
	private Integer nplant;
	
	@Column(name = "locationid")
	private Integer locationid;
	
	@Column(name = "seasonid")
	private Integer seasonid;
	
	@Column(name = "sampleid")
	private Integer sampleid;

	public Integer getIdconsec() {
		return idconsec;
	}

	public void setIdconsec(Integer idconsec) {
		this.idconsec = idconsec;
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

	public Integer getSampleid() {
		return sampleid;
	}

	public void setSampleid(Integer sampleid) {
		this.sampleid = sampleid;
	}

	/**
	 * @return the locationid
	 */
	public Integer getLocationid() {
		return locationid;
	}

	/**
	 * @param locationid the locationid to set
	 */
	public void setLocationid(Integer locationid) {
		this.locationid = locationid;
	}

	/**
	 * @return the seasonid
	 */
	public Integer getSeasonid() {
		return seasonid;
	}

	/**
	 * @param seasonid the seasonid to set
	 */
	public void setSeasonid(Integer seasonid) {
		this.seasonid = seasonid;
	}
	
	
	
	

}
