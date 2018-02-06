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

import com.cimmyt.model.bean.Company;
import com.cimmyt.model.bean.Shipment;
import com.cimmyt.model.bean.Status;
import com.cimmyt.model.bean.StorageLocation;

public interface ServiceStatus {

	public List<Status> getListByFilter(Status bean);
//	public void add(Status bean);
//	public void delete (Status bean);
	public Status getStatusByName(String name);
	public Status read(String pk);
}
