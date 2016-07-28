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
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.cimmyt.model.bean.ResultsPreferences;
import com.cimmyt.model.bean.ResultsPreferencesDetail;
import com.cimmyt.model.dao.ResultsPreferencesDAO;

public class ResultsPreferencesDAOImpl extends AbstractDAO<ResultsPreferences, Integer> implements ResultsPreferencesDAO{

	public ResultsPreferencesDAOImpl() {
		super(ResultsPreferences.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void buildCriteria(DetachedCriteria criteria,
			ResultsPreferences filter) {
		// TODO Auto-generated method stub
		if(filter.getStudytemplateid()!=null){
			criteria.add(Restrictions.eq("studytemplateid", filter.getStudytemplateid()));
		}
	}

	
	public ResultsPreferences readResultsPreferences(final Integer id) {
		ResultsPreferences result = (ResultsPreferences) getHibernateTemplate().execute(
				new HibernateCallback() {
					ResultsPreferences result;

					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						result = (ResultsPreferences) session.get(ResultsPreferences.class,
								id);
						
						result.getColumnscount();
						result.getPreferencesname();
						result.getResultpreferencesid();
						result.getStudytemplateid();
						
						for (ResultsPreferencesDetail detail : result
								.getResultsPreferencesDetailCollection()) {
							detail.getHeader();
						}
						return result;
					}
				});
		return result;
	}
	
	public boolean isThisNameRegistredInResultsPreferences(final String namepref){
		
		ResultsPreferences respref;
		final String queryString = "from ResultsPreferences as rs "
				+ " where rs.preferencesname= :NAME";
		respref = (ResultsPreferences) getHibernateTemplate().execute(
				new HibernateCallback() {
					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						ResultsPreferences respre=null;//= new ResultsPreferences();
						Query query = session.createQuery(queryString);
						query.setParameter("NAME", namepref);
						
						if (query.uniqueResult() != null) {
							respre = (ResultsPreferences) query
									.uniqueResult();
						}
						return respre;
					}
				});
		if(respref==null)
			return false;
		else
			return true;
	}

	@Override
	protected void buildCriteria(DetachedCriteria criteria,
			ResultsPreferences filter, Integer id) {
		// TODO Auto-generated method stub
		
	}

	public List<ResultsPreferences> getListResultsPreferents(ResultsPreferences resultsPreferences){
		return super.getListByFilter(resultsPreferences);
	}

	public void saveOrUpdateResultsPreferences (ResultsPreferences resultsPreferences){
		super.update(resultsPreferences);
	}
}
