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
package com.cimmyt.service;

import java.util.List;

import com.cimmyt.model.bean.ShipmentDetail;
import com.cimmyt.model.bean.ShipmentSet;

public interface ServiceShipmentDetail {

	public void saveOrUpdateShipmentDetail(ShipmentDetail bean);
	public void saveOrUpdateAll(List<ShipmentDetail> beans);
	public void deleteAll(List<ShipmentDetail> beans);
	public ShipmentDetail create(ShipmentDetail bean);
	public List<ShipmentDetail> getShipmentDetails(ShipmentSet set);
}
