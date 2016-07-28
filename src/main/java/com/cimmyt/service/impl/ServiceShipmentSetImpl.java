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

import com.cimmyt.bean.UserBean;
import com.cimmyt.model.bean.ShipmentSet;
import com.cimmyt.model.dao.ShipmentSetsDAO;
import com.cimmyt.service.ServiceShipmentSet;

public class ServiceShipmentSetImpl implements ServiceShipmentSet{


	private ShipmentSetsDAO shipmentSetsDAO;

	@Override
	public List<ShipmentSet> getListByFilter(ShipmentSet bean) {
		return shipmentSetsDAO.getListByFilter(bean);
	}

/*	
	@Override
	protected void buildCriteria(DetachedCriteria criteria, ShipmentSet filter) {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void buildCriteria(DetachedCriteria criteria, ShipmentSet filter,
			Integer id) {
		// TODO Auto-generated method stub	
	}
*/


	@Override
	public List<ShipmentSet> getShipmentsSets(ShipmentSet filter, UserBean bean) {
		return shipmentSetsDAO.listOfShipmentsSets(bean);
	}


	public ShipmentSetsDAO getShipmentSetsDAO() {
		return shipmentSetsDAO;
	}


	public void setShipmentSetsDAO(ShipmentSetsDAO shipmentSetsDAO) {
		this.shipmentSetsDAO = shipmentSetsDAO;
	}

	/**
	 * Save or Update ShipmentSet Bean
	 * @param shipmentSetBean
	 */
	@Override
	public void saveOrUpdateShipmentSet(ShipmentSet bean) {
		shipmentSetsDAO.saveOrUpdateShipments(bean);
	}

	/**
	 * Create ShipmentSet Bean
	 * @param shipmentSetBean
	 */
	@Override
	public ShipmentSet create(ShipmentSet bean) {
		ShipmentSet newShipment = shipmentSetsDAO.create(bean);
		return newShipment;
	}

	@Override
	public ShipmentSet read(int idShipment) {
		ShipmentSet shipment = null;
		shipment = shipmentSetsDAO.read(idShipment);
		return shipment;
	}

	public void delete(ShipmentSet bean) {
		shipmentSetsDAO.delete(bean);
	}

	@Override
	public ShipmentSet getShipmentSet(Integer idShipment) {
		ShipmentSet set = null;
		set = shipmentSetsDAO.getShipmentSet(idShipment);
		return set;
	}

}
