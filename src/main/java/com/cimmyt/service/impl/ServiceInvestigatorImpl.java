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
package com.cimmyt.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.cimmyt.bean.InvestigatorBean;
import com.cimmyt.model.bean.Investigator;
import com.cimmyt.model.dao.InvestigatorDAO;
import com.cimmyt.rest.client.ClientCatalogContry;
import com.cimmyt.rest.client.Result;
import com.cimmyt.service.ServiceInvestigator;

public class ServiceInvestigatorImpl implements  ServiceInvestigator{

	private InvestigatorDAO investigatorDAO;

	/**
	 * Add New Investigator
	 * @param InvestigatorBean
	 */
	@Override
	public void add(InvestigatorBean investigatorBean) {
		investigatorDAO.saveOrUpdate(
				investigatorBean.getInvestigator(investigatorBean));
	}
	/**
	 * Set Investigator bean Spring
	 * @param investigatorDAO
	 */
	public void setInvestigatorDAO(InvestigatorDAO investigatorDAO) {
		this.investigatorDAO = investigatorDAO;
	}
	/**
	 * Get Investigator 
	 * @param Investigator Bean
	 */
	@Override
	public List<InvestigatorBean> getInvestigator(InvestigatorBean bean) {
		List<Investigator> list = investigatorDAO.getInvestigator(bean.getInvestigator(bean));
		if (list != null && !list.isEmpty() ){
			List<InvestigatorBean> listBean = new ArrayList<InvestigatorBean>();
			for (Investigator investig : list){
				listBean.add(new InvestigatorBean(investig));
				}
			return listBean;
			}
		return null;
	}
	/**
	 * Delete Investigator
	 * @param Investigator Bean
	 */
	@Override
	public void delete(InvestigatorBean investigator) {
		investigatorDAO.delete(investigator.getInvestigator(investigator));
	}
	/**
	 * Get Investigator By name and Abbreviation registred
	 * @param String Investigator Name
	 * @param String Abbreviation
	 */
	public InvestigatorBean getInvestigatorNameAbbreviationRegistred(final String investname, final String abbreviation){
		Investigator investigator = investigatorDAO.getInvestigatorNameAbbreviationRegistred(investname, abbreviation);
		if (investigator != null){
			return new InvestigatorBean (investigator);
		}
		return null;
	}

}
