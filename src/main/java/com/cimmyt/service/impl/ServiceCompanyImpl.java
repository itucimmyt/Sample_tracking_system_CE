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

import java.util.List;

import com.cimmyt.model.bean.Company;
import com.cimmyt.model.bean.StorageLocation;
import com.cimmyt.model.dao.CompanyDAO;
import com.cimmyt.service.ServiceCompany;

public class ServiceCompanyImpl implements ServiceCompany {

	private CompanyDAO companyDAO;
	@Override
	public List<Company> getListByFilter(Company bean) {
		return companyDAO.getListByFilter(bean);
	}

	@Override
	public void add(Company bean) {
		companyDAO.add(bean);
	}

	@Override
	public void delete(Company bean) {
		companyDAO.delete(bean);
	}

	public void setCompanyDAO(CompanyDAO companyDAO) {
		this.companyDAO = companyDAO;
	}

	@Override
	public Company getCompanyByName(String name) {
		return companyDAO.getCompanyByName(name);
	}
	
	public boolean SearchIfAnyHaveThisLocations( StorageLocation slocat){
		return companyDAO.SearchIfAnyHaveThisLocations(slocat);
	}

}
