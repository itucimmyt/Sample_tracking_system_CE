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

import com.cimmyt.model.bean.LocationCatalog;


public class LocationBean {

	private Integer locationid;
	private String location_name;
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

	public LocationBean(){
		
	} 
	
	public LocationBean (LocationCatalog bean){
		this.location_description = bean.getLocation_description();
		this.location_name = bean.getLocation_name();
		this.locationid = bean.getLocationid();
	}

	public LocationBean getLocationBean(LocationCatalog bean){
		LocationBean obj = new LocationBean();
		obj.setLocation_description(bean.getLocation_description());
		obj.setLocation_name(bean.getLocation_name());
		obj.setLocationid(bean.getLocationid());
		return obj;
	}

	public LocationCatalog getLocationBean(LocationBean bean){
		LocationCatalog obj = new LocationCatalog();
		obj.setLocation_description(bean.getLocation_description());
		obj.setLocation_name(bean.getLocation_name());
		obj.setLocationid(bean.getLocationid());
		return obj;
	}
}
