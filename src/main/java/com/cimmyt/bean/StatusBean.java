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

import java.io.Serializable;

import com.cimmyt.model.bean.Investigator;
import com.cimmyt.model.bean.Status;


public class StatusBean implements Serializable{

	/*Serializable para ser enviado por el servlet de autentificacion*/
	private static final long serialVersionUID = 7022159322037021384L;
	
	private String idStatus;
	private String description;


	public String getIdStatus() {
		return idStatus;
	}

	public void setIdStatus(String idStatus) {
		this.idStatus = idStatus;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
    public String toString() {
    	return description;
    }
	public StatusBean (){

	}

	/**
	 * Get Investigator
	 * @return
	 */
	public Status getStatus (StatusBean bean){
		Status status = new Status();
		status.setIdStatus(bean.getIdStatus());
		status.setStatusDescription(bean.getDescription());
		return status;
	}

	public StatusBean getStatusBean (Status bean){
		StatusBean status = new StatusBean();
		status.setIdStatus(bean.getIdStatus());
		status.setDescription(bean.getStatusDescription());
		return status;
	}
	public StatusBean (Status stat){
		this.idStatus = stat.getIdStatus();
		this.description = stat.getStatusDescription();
	}
}
