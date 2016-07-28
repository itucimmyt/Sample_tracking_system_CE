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
package org.cimmyt.dnast.dao.imp;

import org.cimmyt.dnast.dao.AbstractDaoSupport;
import org.cimmyt.dnast.dto.StProject;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class StProjectHome extends AbstractDaoSupport<StProject>{


	public StProjectHome(Class<StProject> type) {
		super(type);
	}
	
	public StProjectHome(){
		super(StProject.class);
	}


	@Override
	protected void buildCriteria(Criteria criteria, StProject filter) {
		//TEMPORAL RESTRICTION
			criteria.add(Restrictions.isNotNull("fundingSource"));
		
		if(filter == null) return;
			
		if (filter.getProjectid()!= null && filter.getProjectid().intValue()> 0 ) {
			criteria.add(Restrictions.eq("projectid", filter.getProjectid()));
		}
		if (filter.getProjectName()!= null && !filter.getProjectName().trim().equals("") ) {
			criteria.add(Restrictions.like("projectName", "%"+filter.getProjectName()+"%"));
		}
		if (filter.getPurposeName()!= null && !filter.getPurposeName().trim().equals("") ) {
			criteria.add(Restrictions.like("purposeName", "%"+filter.getPurposeName()+"%"));
		}
	}


}
