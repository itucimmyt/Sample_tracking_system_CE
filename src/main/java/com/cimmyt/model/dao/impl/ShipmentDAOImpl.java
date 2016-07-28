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
//$ Id: ShipmentDAO.java, Jun 28, 2010 TMSANCHEZ $
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
import com.cimmyt.model.bean.Shipment;
import com.cimmyt.model.bean.ShipmentDetail;
import com.cimmyt.model.bean.ShipmentSet;
import com.cimmyt.model.dao.ShipmentDAO;

/**
 * TODO add class documentation here
 * @version $Revision$, $Date: 2010-08-11 17:38:20 -0500 (Wed, 11 Aug 2010) $
 */
public class ShipmentDAOImpl extends AbstractDAO<Shipment, Integer> implements ShipmentDAO {

	public ShipmentDAOImpl() {
		super(Shipment.class);
	}

	@Override
	protected void buildCriteria(DetachedCriteria criteria, Shipment filter) {
		
		if(filter.getComment()!=null){
			criteria.add(Restrictions.like("comment", "%"+filter.getComment()+"%"));
		}
		if(filter.getIsSentToKB() != null){
			criteria.add(Restrictions.eq("isSentToKB", filter.getIsSentToKB()));
		}
	}

	
	public Shipment readShipment(final Integer id) {
		@SuppressWarnings("unchecked")
		Shipment result = (Shipment) getHibernateTemplate().execute(
				new HibernateCallback() {
					Shipment result = null;

					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						result = (Shipment) session.get(Shipment.class,
								id);
						
						
						for (ShipmentDetail detail : result.getStShipmentSet()
								.getStShipmentDetails()) {
							detail.getStSampleDetail();
							
						}
						
						
						return result;
					}
				});
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Shipment> listOfShipmentsToACompany(final Company company) {
		List<Shipment> shipments;
		final String queryString = "from Shipment as shi "
				+ " where shi.stCompany.idCompany= :COMPANY";

		shipments= (List<Shipment>)getHibernateTemplate().execute(
				new HibernateCallback() {

					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						List<Shipment> ships = null;
						Query query = session.createQuery(queryString);
						query.setParameter("COMPANY",company.getIdCompany());
						ships = query.list();
						return ships;
					}
				});
		return shipments;
	}

	@Override
	protected void buildCriteria(DetachedCriteria criteria, Shipment filter,
			Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveOrUpdateShipments(Shipment shipment) {
		super.update(shipment);
	}
	
	@Override
	public Shipment read(final int idShipment) {
		Shipment shipment = null;
		shipment=super.read(idShipment);
		return shipment;
	}
	
	@SuppressWarnings("unchecked")
	public List<Shipment> getShipments(final ShipmentSet filter) {
		List<Shipment> shipments = null;
		final String queryString = "from Shipment as shi "
				+ " where shi.stShipmentSet.idShipmentSet = :SHIPS";

		shipments= (List<Shipment>)getHibernateTemplate().execute(
				new HibernateCallback() {

					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						List<Shipment> ships = null;
						Query query = session.createQuery(queryString);
						query.setParameter("SHIPS",filter.getIdShipmentSet());
						ships = query.list();
						return ships;
					}
				});
		return shipments;
	}

	public void deleteShipments(Shipment shipment) {
		super.delete(shipment);
		
	}

	@Override
	public List<Shipment> getShipments(Shipment shipment) {
		return super.getListByFilter(shipment);
	}
}
