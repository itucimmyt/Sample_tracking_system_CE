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

import com.cimmyt.bean.UserBean;
import com.cimmyt.model.bean.Company;
import com.cimmyt.model.bean.Shipment;
import com.cimmyt.model.bean.ShipmentDetail;
import com.cimmyt.model.bean.ShipmentSet;
import com.cimmyt.model.dao.ShipmentSetsDAO;
import com.cimmyt.utils.ConstantsDNA;

/**
 * TODO add class documentation here
 * @version $Revision$, $Date: 2010-08-11 17:38:20 -0500 (Wed, 11 Aug 2010) $
 */
public class ShipmentSetsDAOImpl extends AbstractDAO<ShipmentSet, Integer> implements ShipmentSetsDAO {

	public ShipmentSetsDAOImpl() {
		super(ShipmentSet.class);
	}
	
	/**
	 * Get List ShipmentSet by filter
	 * @param ShipmentSet
	 */
	@Override
	public List<ShipmentSet> getListByFilter(ShipmentSet bean){
		return super.getListByFilter(bean);
	}

	@Override
	protected void buildCriteria(DetachedCriteria criteria, ShipmentSet filter) {
		if(filter.getComments()!=null){
			criteria.add(Restrictions.like("comments", "%"+filter.getComments()+"%"));
		}
		if(filter.getStInvestigator()!=null && filter.getStInvestigator().getInvest_name()!=null && 
		!filter.getStInvestigator().getInvest_name().isEmpty()){
			criteria.createAlias("stInvestigator", "invest");
			criteria.add(Restrictions.like("invest.invest_name", "%"+filter.getStInvestigator().getInvest_name()+"%"));
		}
	}

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Shipment readShipment(final Integer id) {
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Shipment> listOfShipmentsToACompany(final Company company) {
		List<Shipment> shipments;
		final String queryString = "from Shipment as shi "
				+ " where shi.idcompany= :COMPANY";

		shipments= (List<Shipment>)getHibernateTemplate().execute(
				new HibernateCallback() {

					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						List<Shipment> ships = null;
						Query query = session.createQuery(queryString);
						query.setParameter("COMPANY",company);
						ships = query.list();
						return ships;
					}
				});
		return shipments;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<ShipmentSet> listOfShipmentsSets(UserBean bean) {
		List<ShipmentSet> shipments;
		String strSQL = "";
		switch (bean.getRole().getIdstRol()){
		case ConstantsDNA.ROLE_ADMINISTRATOR:
		case ConstantsDNA.ROLE_DATA_MANAGER:
			strSQL ="from ShipmentSet as shi";
			break;
		default:
			strSQL ="from ShipmentSet as shi Where shi.stInvestigator.investigatorid ="+bean.getInvestigatorBean().getInvestigatorid();
		}
		 final String queryString =strSQL+ " order by shi.datCreated desc";
		shipments= (List<ShipmentSet>)getHibernateTemplate().execute(
				new HibernateCallback() {

					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						List<Shipment> ships = null;
						Query query = session.createQuery(queryString);
						ships = query.list();
						return ships;
					}
				});
		return shipments;
	}

	@Override
	protected void buildCriteria(DetachedCriteria criteria, ShipmentSet filter,
			Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveOrUpdateShipments(ShipmentSet shipments) {
		super.update(shipments);
	}
	
	public ShipmentSet create(ShipmentSet shipmentSet){
		ShipmentSet shipmentRecord = null;
		shipmentRecord = super.create(shipmentSet);
		return shipmentRecord;
	}

	@Override
	public ShipmentSet read(int idShipment) {
		ShipmentSet shipment = null;
		shipment=super.read(idShipment);
		return shipment;
	}
	
	public void delete(ShipmentSet bean){
		super.delete(bean);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ShipmentSet getShipmentSet(final int idShipment) {
		ShipmentSet set = null;
		final String queryString = "from ShipmentSet as set "
				+ " where set.stShipments.idShipment = :SETS";
		set= (ShipmentSet)getHibernateTemplate().execute(
				new HibernateCallback() {

					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						List<Shipment> ships = null;
						Query query = session.createQuery(queryString);
						query.setParameter("SETS",idShipment);
						ships = query.list();
						return ships;
					}
				});
		return set;
	}

	
	
}
