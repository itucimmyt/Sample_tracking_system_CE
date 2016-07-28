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
//$ Id: ShipmentDetailDAO.java, Jun 28, 2010 TMSANCHEZ $
package com.cimmyt.model.dao.impl;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.cimmyt.model.bean.ShipmentDetail;

/**
 * Data Access Object class for Shipment Detail management
 * @version $Revision$, $Date: 2010-08-31 09:15:33 -0500 (Tue, 31 Aug 2010) $
 */
public class ShipmentDetailDAO extends AbstractDAO<ShipmentDetail, Integer> {

	public ShipmentDetailDAO() {
		super(ShipmentDetail.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.cimmyt.cril.apps.sampletracking.core.persistence.dao.AbstractDAO#
	 * buildCriteria(org.hibernate.criterion.DetachedCriteria, java.lang.Object)
	 */
	@Override
	protected void buildCriteria(DetachedCriteria criteria,
			ShipmentDetail filter) {

	}

	/**
	 * Get all samples selected for sent from in a shipment
	 * @param shipmentId ShipmentID key
	 * @return List of all samples selected, empty list if there are not samples
	 * matching with shipmetnID
	 */
	@SuppressWarnings("unchecked")
	public List<ShipmentDetail> getSamplesFromShipment(final Integer shipmentId) {
		List<ShipmentDetail> sampleDetails = null;

		final String queryString = "from Shipmentdetail as sd "
				+ " where sd.shipmentid.shipmentid = :SHIPMENTID ";

		sampleDetails = (List<ShipmentDetail>) getHibernateTemplate().execute(
				new HibernateCallback() {
					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						List<ShipmentDetail> shipmentDetails = null;

						Query query = session.createQuery(queryString);

						query.setParameter("SHIPMENTID", shipmentId);

						shipmentDetails = query.list();

						return shipmentDetails;
					}
				});

		return sampleDetails;
	}

	@Override
	protected void buildCriteria(DetachedCriteria criteria,
			ShipmentDetail filter, Integer id) {
		// TODO Auto-generated method stub
		
	}
	
	public void saveOrUpdateAll(Collection<ShipmentDetail> entities){
		getHibernateTemplate().saveOrUpdate(entities);
	}
	

}
