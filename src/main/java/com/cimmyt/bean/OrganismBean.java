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

import com.cimmyt.model.bean.Organism;


public class OrganismBean {

	private Integer organismid;
	private String organismname;

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

	 public OrganismBean(){
		 
	 }

	 public OrganismBean (Organism bean){
		 this.organismid = bean.getOrganismid();
		 this.organismname = bean.getOrganismname();
	 }
	 
	 public Organism getOrganism(OrganismBean bean){
		 Organism obj = new Organism();
		 obj.setOrganismid(bean.getOrganismid());
		 obj.setOrganismname(bean.getOrganismname());
		 return obj;
	 }
}
