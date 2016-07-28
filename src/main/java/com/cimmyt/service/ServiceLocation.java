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

import java.util.Date;
import java.util.List;

import com.cimmyt.bean.LocationBean;
import com.cimmyt.constants.MovType;
import com.cimmyt.model.bean.SampleDetail;
import com.cimmyt.model.bean.StorageLocation;

public interface ServiceLocation {

	public List<LocationBean> getLocation (LocationBean bean);
	public void add (LocationBean bean);
	public void delete(LocationBean bean);
	public LocationBean getLocationCatalogRegistred(final String locationname);
	public void registerSampleMovement (Date movDate, MovType movType,
			StorageLocation destLocation, String comments,
			Integer quantity, String hour, String minute,
			List<SampleDetail> samples);
	
	
	public LocationBean isLocationCatalogRegistred(String locationName);
}
