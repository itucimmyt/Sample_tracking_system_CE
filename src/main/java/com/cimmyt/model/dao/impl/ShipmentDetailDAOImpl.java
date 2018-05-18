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
import java.util.Collection;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.cimmyt.model.bean.ShipmentDetail;
import com.cimmyt.model.bean.ShipmentSet;
import com.cimmyt.model.dao.ShipmentDetailDAO;


/**
 * TODO add class documentation here
 * @version $Revision$, $Date: 2010-08-11 17:38:20 -0500 (Wed, 11 Aug 2010) $
 */
public class ShipmentDetailDAOImpl extends AbstractDAO<ShipmentDetail, Integer> implements ShipmentDetailDAO {

	public ShipmentDetailDAOImpl() {
		super(ShipmentDetail.class);
	}

	@Override
	public void saveOrUpdateShipmentDetail(ShipmentDetail shipmentDet) {
		super.update(shipmentDet);
	}
	
	public ShipmentDetail create(ShipmentDetail shipmentDet){
		ShipmentDetail shipmentDetRecord = null;
		shipmentDetRecord = super.create(shipmentDet);
		return shipmentDetRecord;
	}

	@Override
	protected void buildCriteria(DetachedCriteria criteria,
			ShipmentDetail filter) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void buildCriteria(DetachedCriteria criteria,
			ShipmentDetail filter, Integer id) {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ShipmentDetail> getShipmentDetails(final ShipmentSet set) {
		List<ShipmentDetail> detail = null;
		final String queryString = "from ShipmentDetail as det "
				+ " where det.stShipmentSet.idShipmentSet = :SETS order by det.stSampleDetail.studysampleid ASC ";

		detail= (List<ShipmentDetail>)getHibernateTemplate().execute(
				new HibernateCallback() {

					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						List<ShipmentDetail> ships = null;
						Query query = session.createQuery(queryString);
						query.setParameter("SETS",set.getIdShipmentSet());
						ships = query.list();
						return ships;
					}
				});
		return detail;
	}

	@Override
	public void saveOrUpdateAll(Collection<ShipmentDetail> entities) {
		getHibernateTemplate().saveOrUpdate(entities);
		
	}

	@Override
	public void deleteAll(Collection<ShipmentDetail> entities) {
		getHibernateTemplate().deleteAll(entities);
	}
	
}
