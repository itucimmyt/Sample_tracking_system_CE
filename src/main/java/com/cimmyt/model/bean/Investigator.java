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
/**
 * 
 * @author 
 *
 */
@Entity
@Table(name = "st_investigator")
public class Investigator implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="investigatorid")
	private Integer investigatorid;
	
	@Column(name = "invest_abbreviation")
	private String invest_abbreviation;
	
	@Column(name = "invest_name")
	private String invest_name;

	@Column(name = "status")
	private boolean status = true;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "investigator")
	private Set<AuthUserBean> setAuthUserBean = new HashSet<AuthUserBean>(0);
	
	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Integer getInvestigatorid() {
		return investigatorid;
	}

	public void setInvestigatorid(Integer investigatorid) {
		this.investigatorid = investigatorid;
	}

	public String getInvest_abbreviation() {
		return invest_abbreviation;
	}

	public void setInvest_abbreviation(String investAbbreviation) {
		invest_abbreviation = investAbbreviation;
	}

	public String getInvest_name() {
		return invest_name;
	}

	public void setInvest_name(String investName) {
		invest_name = investName;
	}
	

	public Set<AuthUserBean> getSetAuthUserBean() {
		return setAuthUserBean;
	}

	public void setSetAuthUserBean(Set<AuthUserBean> setAuthUserBean) {
		this.setAuthUserBean = setAuthUserBean;
	}

	@Override
	    public String toString() {
	    	return "investigatorid : "+investigatorid+" invest_name : "+ invest_name + " invest_abbreviation : "+invest_abbreviation ;
	    }
}

