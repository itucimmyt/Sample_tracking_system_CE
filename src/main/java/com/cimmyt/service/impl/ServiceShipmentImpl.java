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

import com.cimmyt.model.bean.Company;
import com.cimmyt.model.bean.Shipment;
import com.cimmyt.model.bean.ShipmentSet;
import com.cimmyt.model.dao.ShipmentDAO;
import com.cimmyt.service.ServiceShipment;

public class ServiceShipmentImpl implements ServiceShipment{

	private ShipmentDAO shipmentDAO;
	@Override
	public List<Shipment> listOfShipmentsToACompany(Company company) {
		
		return shipmentDAO.listOfShipmentsToACompany(company);
	}
	public void setShipmentDAO(ShipmentDAO shipmentDAO) {
		this.shipmentDAO = shipmentDAO;
	}

	@Override
	public List<Shipment> getShipments(Shipment filter) {
		return shipmentDAO.getShipments(filter);

	}
	@Override
	public void saveOrUpdateShipment(Shipment beanShipment) {
		shipmentDAO.saveOrUpdateShipments(beanShipment);
	}
	
	@Override
	public Shipment read(int idShipment) {
		Shipment shipment = null;
		shipment = shipmentDAO.read(idShipment);
		return shipment;
	}
	
	@Override
	public List<Shipment> getShipments(ShipmentSet filter) {
		List<Shipment> shipment = null;
		shipment = shipmentDAO.getShipments(filter);
		return shipment;
	}
	@Override
	public void deleteShipment(Shipment beanShipment) {
		shipmentDAO.deleteShipments(beanShipment);
	}
}
