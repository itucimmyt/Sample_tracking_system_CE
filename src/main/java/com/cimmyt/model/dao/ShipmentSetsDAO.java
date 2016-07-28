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
package com.cimmyt.model.dao;

import java.util.List;

import com.cimmyt.bean.UserBean;
import com.cimmyt.model.bean.Company;
import com.cimmyt.model.bean.Shipment;
import com.cimmyt.model.bean.ShipmentSet;

public interface ShipmentSetsDAO {

	public List<ShipmentSet> getListByFilter(ShipmentSet bean);
	public List<Shipment> listOfShipmentsToACompany(final Company company);
	public void saveOrUpdateShipments(ShipmentSet shipments);
	public ShipmentSet create(ShipmentSet shipmentSet);
	public List<ShipmentSet> listOfShipmentsSets(UserBean bean);
	public ShipmentSet read(int idShipment);
	public void delete (ShipmentSet bean);
	public ShipmentSet getShipmentSet(int idShipment);
	
}
