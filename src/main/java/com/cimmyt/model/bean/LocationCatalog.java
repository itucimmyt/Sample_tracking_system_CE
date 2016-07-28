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
@Table(name = "st_location")
public class LocationCatalog implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="locationid")
	private Integer locationid;
	
	@Column(name = "location_name")
	private String location_name;
	
	@Column(name = "location_description")
	private String location_description;

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
	 * @return the location_name
	 */
	public String getLocation_name() {
		return location_name;
	}

	/**
	 * @param locationName the location_name to set
	 */
	public void setLocation_name(String locationName) {
		location_name = locationName;
	}
	
	
	
	/**
	 * @return the location_description
	 */
	public String getLocation_description() {
		return location_description;
	}

	/**
	 * @param locationDescription the location_description to set
	 */
	public void setLocation_description(String locationDescription) {
		location_description = locationDescription;
	}

	@Override
    public String toString() {
    	return location_name;
    }
	
}

