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
 */
@Entity
@Table(name = "st_season")
public class Season implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="seasonid")
	private Integer seasonid;
	
	@Column(name = "season_name")
	private String season_name;
	
	@Column(name = "season_description")
	private String season_description;

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

	/**
	 * @return the season_name
	 */
	public String getSeason_name() {
		return season_name;
	}

	/**
	 * @param seasonName the season_name to set
	 */
	public void setSeason_name(String seasonName) {
		season_name = seasonName;
	}

	/**
	 * @return the season_description
	 */
	public String getSeason_description() {
		return season_description;
	}

	/**
	 * @param seasonDescription the season_description to set
	 */
	public void setSeason_description(String seasonDescription) {
		season_description = seasonDescription;
	}
	

	@Override
    public String toString() {
//        return "org.cimmyt.sampletracking.prototype.persistence.beans.ImsStudyTemplate[studytemplateid=" + studytemplateid + "]";
    	return season_name;
    }	
	
}

