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
//Created October 2011
package com.cimmyt.model.dao.impl;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.cimmyt.model.bean.Investigator;
import com.cimmyt.model.dao.InvestigatorDAO;

/**
 *
 */
public class InvestigatorDAOImpl extends AbstractDAO<Investigator, Integer> implements InvestigatorDAO {

	public InvestigatorDAOImpl() {
		super(Investigator.class);
		// TODO Auto-generated constructor stub
	}
	

	@Override
	protected void buildCriteria(DetachedCriteria criteria, Investigator filter) {
		// TODO Auto-generated method stub
		if (filter.getInvestigatorid() != null) {
			criteria.add(Restrictions.eq("investigatorid", filter.getInvestigatorid()));
		}
		
		if (filter.getInvest_name()!= null && ! filter.getInvest_name().isEmpty()) {
			criteria.add(Restrictions.like("invest_name", "%"+filter.getInvest_name()+"%"));
		}
	}
	/**
	 * Get List Investigator
	 * @param Investigator
	 */
	public List<Investigator> getInvestigator (Investigator filter){
		return super.getListByFilter(filter);
	}
	/**
	 * Save or Update Investigator
	 */
	@Override
	public int saveOrUpdate(Investigator investigator) {
		super.update(investigator);
		return investigator.getInvestigatorid();
	}
	
	public void delete (Investigator investigator){
		super.delete( investigator);
	}
	
	public Investigator getInvestigatorNameAbbreviationRegistred(final String investname, final String abbreviation){
		Investigator invest;
		final String queryString = "from Investigator as inv "
				+ " where inv.invest_name = :INVESTNAME " +
				"or invest_abbreviation = :ABBREVIATION ";

		invest = (Investigator) getHibernateTemplate().execute(
				new HibernateCallback() {

					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Investigator inv=null;
						
						Query query = session.createQuery(queryString);
						query.setParameter("INVESTNAME", investname);
						query.setParameter("ABBREVIATION", abbreviation);

						if (query.uniqueResult() != null) {
							inv = (Investigator) query
									.uniqueResult();
						}
						return inv;
					}
				});
		return invest;		
		
	}


	@Override
	protected void buildCriteria(DetachedCriteria criteria,
			Investigator filter, Integer id) {
		// TODO Auto-generated method stub
		
	}
}
