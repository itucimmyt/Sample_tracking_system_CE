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
// default package
// Generated Apr 23, 2013 11:57:22 PM by Hibernate Tools 3.4.0.CR1

import org.cimmyt.dnast.dao.AbstractDaoSupport;
import org.cimmyt.dnast.dto.StLabStudy;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class StLabStudyHome extends AbstractDaoSupport<StLabStudy>{

	public StLabStudyHome(Class<StLabStudy> type) {
		super(type);
	}
	
	public StLabStudyHome(){
		super(StLabStudy.class);
	}


	@Override
	protected void buildCriteria(Criteria criteria, StLabStudy filter) {
		
		criteria.add(Restrictions.isNull("objective"));
		
		if (filter.getProjectId()!= null && filter.getProjectId().intValue() > 0 ) {
			criteria.add(Restrictions.eq("stProject.projectid", filter.getProjectId()));
		}
	}



}
