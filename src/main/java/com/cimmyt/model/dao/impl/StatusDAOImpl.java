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
//$ Id: CompanyDAO.java, Jun 10, 2010 TMSANCHEZ $
package com.cimmyt.model.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.cimmyt.model.bean.Company;
import com.cimmyt.model.bean.ShipmentSet;
import com.cimmyt.model.bean.Status;
import com.cimmyt.model.bean.StorageLocation;
import com.cimmyt.model.dao.CompanyDAO;
import com.cimmyt.model.dao.StatusDAO;
import com.cimmyt.model.dao.StorageLocationDAO;

/**
 * Data Access Object class for Company management
 * @version $Revision$, $Date: 2010-08-27 16:44:59 -0500 (Fri, 27 Aug 2010) $
 */
public class StatusDAOImpl extends AbstractDAO<Status, String> implements StatusDAO{

	private StorageLocationDAO storageLocationDAO;
	
	public StatusDAOImpl() {
		super(Status.class);
	}

	/**
	 * 
	 * @param criteria
	 * @param companyFilter
	 */
	protected void buildCriteria(DetachedCriteria criteria, Status statusFilter ) {
		if (statusFilter.getIdStatus() != null) {
			criteria.add(Restrictions.eq("idStatus", statusFilter.getIdStatus()));
		}
		
		if (statusFilter.getStatusDescription() != null && statusFilter.getStatusDescription().trim().length() > 0) {
			criteria.add(Restrictions.like("name", "%"+statusFilter.getStatusDescription()+"%"));
		}
	}

	public boolean SearchIfAnyHaveThisLocations(final StorageLocation slocat){
		Status estatus;
		final String queryString = "from Company as com "
				+ " where com.storagelocation.imslocid = :LOCID ";
				

		estatus = (Status) getHibernateTemplate().execute(
				new HibernateCallback() {

					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Status statu= new Status();
						
						Query query = session.createQuery(queryString);
						query.setParameter("LOCID", slocat.getImslocid());
						
						if (query.uniqueResult() != null) {
							statu = (Status) query
									.uniqueResult();
						}

						return statu;
					}
				});
		
		if(estatus.getStatusDescription()==null)
			return false;
		else
			return true;
	}

	/**
	 * Get List Status by filter
	 * @param Status
	 */
	@Override
	public List<Status> getListByFilter(Status bean){
		return super.getListByFilter(bean);
	}
	/**
	 * Add Company
	 * @param Company
	 */
/*	@Override
	public void add(Status bean){
		StorageLocation storagecompany = new StorageLocation();
		storagecompany.setImslocidparent(1);
		storagecompany.setLname(bean.getName());
		storageLocationDAO.create(storagecompany);
		bean.setStoragelocation(storagecompany);
		super.update(bean);
	
	}*/
	/**
	 * Delete Company
	 * @param Company
	 */
/*	@Override
	public void delete (Company bean){
		super.delete(bean);
	}*/
	/**
	 * Get Company by name
	 * @param String name
	 */
	@Override
	public Status getStatusByName(String name){
		try{
			Status status = super.findSingleByValues(new Status(), new String []{"status_description"},new Object []{name});
					return status;
		}catch (Exception e){
			return null;
		}
	}

	public void setStorageLocationDAO(StorageLocationDAO storageLocationDAO) {
		this.storageLocationDAO = storageLocationDAO;
	}

	@Override
	protected void buildCriteria(DetachedCriteria criteria, Status filter,
			Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Status read(String idStatus) {
		Status status = null;
		status=super.read(idStatus);
		return status;
	}

	
}
