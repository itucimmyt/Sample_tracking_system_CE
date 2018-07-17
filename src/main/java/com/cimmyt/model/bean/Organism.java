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
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.cimmyt.dnast.dto.AuthUserBean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * 
 * @author 
 *
 */
@Entity
@Table(name = "st_organism")
public class Organism implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="organismid")
	private Integer organismid;
	
	@Column(name = "organismName")
	private String organismname;
	
	/*@JsonIgnoreProperties("stUserVersions")
	 @OneToMany(fetch = FetchType.LAZY, mappedBy = "stOrganism")
	private Set<AuthUserBean> stUserVersions = new HashSet<AuthUserBean>(0);
*/
	 public Organism(){

	 }

	 public Organism(Integer organismid, String  organismname,Set<AuthUserBean> stUserVersions ){
		 this.organismid = organismid;
		 this.organismname =  organismname;
		// this.stUserVersions = stUserVersions;
	 }

	public Integer getOrganismid() {
		return organismid;
	}

	public void setOrganismid(Integer organismid) {
		this.organismid = organismid;
	}

	public String getOrganismname() {
		return organismname;
	}

	public void setOrganismname(String organismname) {
		this.organismname = organismname;
	}
	
	 @Override
	    public String toString() {
	    	return organismname;
	    }

	 /*
	 @JsonProperty
		public Set<AuthUserBean> getStUserVersions() {
			return this.stUserVersions;
		}
		@JsonIgnore
		public void setStUserVersions(Set<AuthUserBean> stUserVersions) {
			this.stUserVersions = stUserVersions;
		}
	  */
}

