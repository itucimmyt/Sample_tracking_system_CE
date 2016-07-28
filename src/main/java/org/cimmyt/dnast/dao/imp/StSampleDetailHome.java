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
import org.cimmyt.dnast.dto.StSampleDetail;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 * Home object for domain model class StSampleDetail.
 * @see .StSampleDetail
 * @author Hibernate Tools
 */
public class StSampleDetailHome extends AbstractDaoSupport<StSampleDetail>{

	public StSampleDetailHome(Class<StSampleDetail> type) {
		super(type);
	}
	
	public StSampleDetailHome(){
		super(StSampleDetail.class);
	}


	@Override
	protected void buildCriteria(Criteria criteria, StSampleDetail filter) {
		
		if (filter.getStudyId()!= null && filter.getStudyId().intValue() > 0 ) {
			criteria.add(Restrictions.eq("stLabStudy.labstudyid", filter.getStudyId()));
		}
		
	}

}
