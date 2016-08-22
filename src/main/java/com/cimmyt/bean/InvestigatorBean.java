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


public class InvestigatorBean implements Serializable{

	/*Serializable para ser enviado por el servlet de autentificacion*/
	private static final long serialVersionUID = -4953101329119104856L;

	private Integer investigatorid;
	private String invest_abbreviation;
	private String invest_name;

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

	@Override
	    public String toString() {
	    	return "investigatorid : "+investigatorid+" invest_name : "+invest_name +" invest_abbreviation : "+invest_abbreviation;
	    }
	public InvestigatorBean (){

	}
	/**
	 * Get Investigator
	 * @return
	 */
	public Investigator getInvestigator (InvestigatorBean bean){
		Investigator investigator = new Investigator();
		investigator.setInvest_abbreviation(bean.getInvest_abbreviation());
		investigator.setInvest_name(bean.getInvest_name());
		investigator.setInvestigatorid(bean.getInvestigatorid());
		return investigator;
	}

	public InvestigatorBean getInvestigatorBean (Investigator bean){
		InvestigatorBean investigator = new InvestigatorBean();
		investigator.setInvest_abbreviation(bean.getInvest_abbreviation());
		investigator.setInvest_name(bean.getInvest_name());
		investigator.setInvestigatorid(bean.getInvestigatorid());
		return investigator;
	}
	public InvestigatorBean (Investigator inv){
		this.invest_abbreviation = inv.getInvest_abbreviation();
		this.invest_name = inv.getInvest_name();
		this.investigatorid = inv.getInvestigatorid();
	}
}
