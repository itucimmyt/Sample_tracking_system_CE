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

import com.cimmyt.model.bean.LocationCatalog;
import com.cimmyt.model.dao.LocationDAO;
/**
 * 
 */
public class LocationCatalogDAOImpl extends AbstractDAO<LocationCatalog, Integer> implements LocationDAO {

	public LocationCatalogDAOImpl() {
		super(LocationCatalog.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void buildCriteria(DetachedCriteria criteria, LocationCatalog filter) {
		// TODO Auto-generated method stub
		if (filter.getLocationid() != null) {
			criteria.add(Restrictions.eq("locationid", filter.getLocationid()));
		}
		
		if (filter.getLocation_name()!= null && ! filter.getLocation_name().isEmpty()) {
			criteria.add(Restrictions.like("location_name", "%"+filter.getLocation_name()+"%"));
		}
	}
	/**
	 * Get Location Catalog By registred 
	 * @param String Location Name
	 */
	public LocationCatalog IsLocationCatalogRegistred(final String locationname){
		LocationCatalog location;
		final String queryString = "from LocationCatalog as loc "
				+ " where loc.location_name = :LOCATIONNAME ";
		location = (LocationCatalog) getHibernateTemplate().execute(
				new HibernateCallback() {

					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						LocationCatalog loc=null;
						
						Query query = session.createQuery(queryString);
						query.setParameter("LOCATIONNAME", locationname);

						if (query.uniqueResult() != null) {
							loc = (LocationCatalog) query
									.uniqueResult();
						}
						return loc;
					}
				});
		
		return location;
	}
	/**
	 * Get List Location
	 * @param LocationCatalog
	 */
	@Override
	public List<LocationCatalog> getLocation(LocationCatalog bean) {
		return super.getListByFilter(bean);
	}
	/**
	 * Add new :location
	 * @param LocationCatalog
	 */
	@Override
	public void add(LocationCatalog bean) {
		super.update(bean);
		
	}
	/**
	 * Delete Location
	 * @param LocationCatalog
	 */
	@Override
	public void delete(LocationCatalog bean) {
		super.delete(bean);
		
	}

	@Override
	protected void buildCriteria(DetachedCriteria criteria,
			LocationCatalog filter, Integer id) {
		// TODO Auto-generated method stub
		
	}
}
