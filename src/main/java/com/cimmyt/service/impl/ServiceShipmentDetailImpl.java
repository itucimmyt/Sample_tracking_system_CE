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
package com.cimmyt.service.impl;

import java.util.List;
import com.cimmyt.model.bean.ShipmentDetail;
import com.cimmyt.model.bean.ShipmentSet;
import com.cimmyt.model.dao.ShipmentDetailDAO;
import com.cimmyt.service.ServiceShipmentDetail;

public class ServiceShipmentDetailImpl implements ServiceShipmentDetail{


	private ShipmentDetailDAO shipmentDetailDAO;



	public void setShipmentDetailDAO(ShipmentDetailDAO shipmentDetailDAO) {
		this.shipmentDetailDAO = shipmentDetailDAO;
	}

	public ShipmentDetailDAO getShipmentDetailDAO() {
		return shipmentDetailDAO;
	}

	/**
	 * Save or Update ShipmentSet Bean
	 * @param shipmentSetBean
	 */
	@Override
	public void saveOrUpdateShipmentDetail(ShipmentDetail bean) {
		shipmentDetailDAO.saveOrUpdateShipmentDetail(bean);
	}

	/**
	 * Create ShipmentSet Bean
	 * @param shipmentSetBean
	 */
	@Override
	public ShipmentDetail create(ShipmentDetail bean) {
		ShipmentDetail newShipment = shipmentDetailDAO.create(bean);
		return newShipment;
	}

	@Override
	public List<ShipmentDetail> getShipmentDetails(ShipmentSet set) {
		List<ShipmentDetail> details = null;
		details=shipmentDetailDAO.getShipmentDetails(set);
		return details;
	}

	@Override
	public void saveOrUpdateAll(List<ShipmentDetail> beans) {
		shipmentDetailDAO.saveOrUpdateAll(beans);
	}

	@Override
	public void deleteAll(List<ShipmentDetail> beans) {
		shipmentDetailDAO.deleteAll(beans);
		
	}
	
}
