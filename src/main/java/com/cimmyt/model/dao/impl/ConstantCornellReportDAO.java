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
package com.cimmyt.model.dao.impl;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.cimmyt.model.bean.ConstantsCornellReport;

public class ConstantCornellReportDAO extends AbstractDAO<ConstantsCornellReport, Integer>{

	public ConstantCornellReportDAO() {
		super(ConstantsCornellReport.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void buildCriteria(DetachedCriteria criteria,
			ConstantsCornellReport filter) {
		// TODO Auto-generated method stub
		
	}
	
	public ConstantsCornellReport searchConstantInst_constants_cornell_report(final Integer idlabstudy 
			){
		ConstantsCornellReport constantsCornellReport;
		final String queryString = "from ConstantsCornellReport as ccr "
				+ " where ccr.labstudyid = :LABSTUDYID";
		constantsCornellReport = (ConstantsCornellReport) getHibernateTemplate().execute(
				new HibernateCallback() {
					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						ConstantsCornellReport constantsCornellReport=null;
						Query query = session.createQuery(queryString);
						query.setParameter("LABSTUDYID", idlabstudy);
						if (query.uniqueResult() != null) {
							constantsCornellReport = (ConstantsCornellReport) query
									.uniqueResult();
						}
						return constantsCornellReport;
					}
				});
		return constantsCornellReport;
	}

	@Override
	protected void buildCriteria(DetachedCriteria criteria,
			ConstantsCornellReport filter, Integer id) {
		// TODO Auto-generated method stub
		
	}

}
