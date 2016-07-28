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
//
// Created: April 2009
//
// Copyright 2009 International Rice Research Institute (IRRI) and 
// Centro Internacional de Mejoramiento de Maiz y Trigo (CIMMYT)
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//
//$ Id: StorageLocationDAO.java, Jun 28, 2010 TMSANCHEZ $
package com.cimmyt.model.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.cimmyt.model.bean.SampleDetail;
import com.cimmyt.model.bean.StorageLocation;
import com.cimmyt.model.dao.StorageLocationDAO;

/**
 * TODO add class documentation here
 * @version $Revision$, $Date: 2010-08-11 17:38:20 -0500 (Wed, 11 Aug 2010) $
 */
public class StorageLocationDAOImpl extends AbstractDAO<StorageLocation, Integer> implements StorageLocationDAO{

	public StorageLocationDAOImpl() {
		super(StorageLocation.class);
	}

	/* (non-Javadoc)
	 * @see org.cimmyt.cril.apps.sampletracking.core.persistence.dao.AbstractDAO#buildCriteria(org.hibernate.criterion.DetachedCriteria, java.lang.Object)
	 */
	@Override
	protected void buildCriteria(DetachedCriteria criteria,
			StorageLocation filter) {
		
		if (filter.getImslocid() != null) {
			criteria.add(Restrictions.eq("imslocidparent", filter.getImslocid()));	
		} else if (filter.getImslocid() == null) {
			criteria.add(Restrictions.isNull("imslocidparent"));
		}
		
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<StorageLocation> getAllStorageLocations() {
		List<StorageLocation> locationlist = null;

		final String queryString = "from StorageLocation ";

		locationlist = (List<StorageLocation>) getHibernateTemplate().execute(
				new HibernateCallback() {
					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						List<SampleDetail> samplesDetailResult = null;
						Query query = session.createQuery(queryString);
						samplesDetailResult = query.list();
						return samplesDetailResult;
					}
				});

		return locationlist;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public StorageLocation getStorageLocationWithName(final String name) {
		StorageLocation location = new StorageLocation();
		final String queryString = "from StorageLocation as sl "
				+ " where sl.lname = :NAME ";

		location = (StorageLocation) getHibernateTemplate().execute(
				new HibernateCallback() {
					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createQuery(queryString);
						query.setParameter("NAME", name);
						StorageLocation slocation=null;
						if (query.uniqueResult()!=null){
							slocation=(StorageLocation) query.uniqueResult();
						}
						return slocation;
					}
				});

		return location;
	}	
	@Override
	@SuppressWarnings("unchecked")
	public boolean ThisStorageLocationHaveChildrens(final StorageLocation parent) {
		List<StorageLocation> lista;

		final String queryString = "from StorageLocation as sl "
				+ " where sl.imslocidparent = :LOCATIONIDPARENT ";

		lista = (List<StorageLocation>) getHibernateTemplate().execute(
				new HibernateCallback() {
					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						List<StorageLocation> storagelocationlist = null;
						Query query = session.createQuery(queryString);
						query.setParameter("LOCATIONIDPARENT", parent.getImslocid());
						storagelocationlist = query.list();
						return storagelocationlist;
					}
				});

		if (lista.size()==0)
			return false;
		else
			return true;
	}
	@Override
	public StorageLocation create(final StorageLocation parent){
		return super.create(parent);
	}
	@Override
	public List<StorageLocation> getListByFilter(StorageLocation bean){
		return super.getListByFilter(bean);
	}
	public void add (StorageLocation bean){
		super.update(bean);
	}
	
	public void delete (StorageLocation bean){
		super.delete(bean);
	}

	@Override
	protected void buildCriteria(DetachedCriteria criteria,
			StorageLocation filter, Integer id) {
		// TODO Auto-generated method stub
		
	}
}
