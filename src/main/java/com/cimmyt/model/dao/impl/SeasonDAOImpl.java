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

import com.cimmyt.model.bean.Season;
import com.cimmyt.model.dao.SeasonDAO;

/**
 * 
 * @author 
 * Feb 14,2012
 * created by:  to assign a sampleid need season
 */
public class SeasonDAOImpl extends AbstractDAO<Season, Integer> implements SeasonDAO{
	
	public SeasonDAOImpl() {
		super(Season.class);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	protected void buildCriteria(DetachedCriteria criteria, Season filter) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		if (filter.getSeasonid() != null) {
			criteria.add(Restrictions.eq("seasonid", filter.getSeasonid()));
		}
		
		if (filter.getSeason_name()!= null && ! filter.getSeason_name().isEmpty()) {
			criteria.add(Restrictions.like("season_name", "%"+filter.getSeason_name()+"%"));
		}
	}
	
	
	public Season IsSeasonRegistred(final String seasonname){
		// first define a queryString
		Season season;
		final String queryString = "from Season as sea "
				+ " where sea.season_name = :SEASONNAME ";


		season = (Season) getHibernateTemplate().execute(
				new HibernateCallback() {

					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Season sea=null;
						
						Query query = session.createQuery(queryString);
						query.setParameter("SEASONNAME", seasonname);

						if (query.uniqueResult() != null) {
							sea = (Season) query
									.uniqueResult();
						}

						return sea;
					}
				});
		return season;		
		
	}

	/**
	 * Get List Seasons
	 * @param Season
	 */
	@Override
	public List<Season> getSeason(Season bean) {
		return super.getListByFilter(bean);
	}

	/**
	 * Add Season
	 * @param Season
	 */
	@Override
	public void add(Season bean){
		super.update(bean);
	}

	/**
	 * Delete Season
	 * @param Season
	 */
	@Override
	public void delete(Season bean){
		super.delete(bean);
	}
	/**
	 * Get Season by Name
	 * @param String Name
	 */
	@Override
	public Season getSeasonByBame(String name){
		try{
			Season season = super.findSingleByValues( new Season(), new String []{"season_name"},new Object []{name});
			return season;
			}catch (Exception e){
				e.printStackTrace();
				return null;
			}
	}


	@Override
	protected void buildCriteria(DetachedCriteria criteria, Season filter,
			Integer id) {
		// TODO Auto-generated method stub
		
	}
}
