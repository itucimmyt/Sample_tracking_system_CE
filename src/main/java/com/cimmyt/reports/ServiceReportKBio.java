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
package com.cimmyt.reports;

import java.util.List;

import com.cimmyt.bean.UserBean;
import com.cimmyt.model.bean.Shipment;
import com.cimmyt.model.bean.ShipmentDetail;
import com.cimmyt.utils.PropertyHelper;


public interface ServiceReportKBio {

	public byte[] getBytesReportKBiosList(List<ShipmentDetail> setShipmentDetail, PropertyHelper property, UserBean userBean, Shipment shipment);

	public byte[] getBytesReportKBiosGrid(List<ShipmentDetail> setShipmentDetail, PropertyHelper property, UserBean userBean
			, Shipment shipment);
	public byte[] getBytesReportIntertekGrid(List<ShipmentDetail> setShipmentDetail, PropertyHelper property, UserBean userBean
			, Shipment shipment);
	
	public byte [] getBytesReportGnotypingServices(List<ShipmentDetail> setShipmentDetail, PropertyHelper property, UserBean userBean
			, Shipment shipment);
}
