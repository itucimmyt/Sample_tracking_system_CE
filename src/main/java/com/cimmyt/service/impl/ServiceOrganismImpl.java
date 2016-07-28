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

import com.cimmyt.model.bean.Organism;
import com.cimmyt.model.dao.OrganismDAO;
import com.cimmyt.service.ServiceOrganism;

public class ServiceOrganismImpl implements ServiceOrganism{
	private OrganismDAO organismDAO;
	
	@Override
	public List<Organism> getOrganisms(Organism bean) {
		return organismDAO.getOrganisms(bean);
	}

	public void setOrganismDAO(OrganismDAO organismDAO) {
		this.organismDAO = organismDAO;
	}

}
