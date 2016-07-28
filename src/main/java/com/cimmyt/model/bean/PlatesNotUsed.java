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
@Table(name = "st_plates_not_used")
public class PlatesNotUsed implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="plateid")
	private Integer plateid;
	
	@Column(name = "platename")
	private String platename;
	
	@Column(name = "labstudyid")
	private Integer labstudyid;


	public Integer getLabstudyid() {
		return labstudyid;
	}

	public void setLabstudyid(Integer labstudyid) {
		this.labstudyid = labstudyid;
	}

	public Integer getPlateid() {
		return plateid;
	}

	public void setPlateid(Integer plateid) {
		this.plateid = plateid;
	}

	public String getPlatename() {
		return platename;
	}

	public void setPlatename(String platename) {
		this.platename = platename;
	}
	
	
	
}

