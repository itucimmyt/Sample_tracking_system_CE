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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cimmyt.bean.LocationBean;
import com.cimmyt.constants.MovType;
import com.cimmyt.model.bean.LocationCatalog;
import com.cimmyt.model.bean.SampleDetail;
import com.cimmyt.model.bean.StorageLocation;
import com.cimmyt.model.bean.StorageMov;
import com.cimmyt.model.dao.LocationDAO;
import com.cimmyt.model.dao.SampleDetailDAO;
import com.cimmyt.model.dao.StorageMovDAO;
import com.cimmyt.service.ServiceLocation;

public class ServiceLocationImpl implements ServiceLocation{

	private LocationDAO locationDAO;
	private SampleDetailDAO sampleDetailDAO;
	private StorageMovDAO storageMovDAO;
	@Override
	public List<LocationBean> getLocation(LocationBean bean) {
		List<LocationCatalog> list = locationDAO.getLocation(bean.getLocationBean(bean));
		if (list != null && !list.isEmpty() ){
			List<LocationBean> listBean = new ArrayList<LocationBean>();
			for (LocationCatalog obj : list){
				listBean.add(new LocationBean(obj));
				}
			return listBean;
			}
		return null;
	}

	public void setLocationDAO(LocationDAO locationDAO) {
		this.locationDAO = locationDAO;
	}

	@Override
	public void add(LocationBean bean) {
		locationDAO.add(bean.getLocationBean(bean));
	}

	@Override
	public void delete(LocationBean bean) {
		locationDAO.delete(bean.getLocationBean(bean));
	}
	public LocationBean getLocationCatalogRegistred(final String locationname){
		LocationBean bean = null;
		LocationCatalog location = locationDAO.IsLocationCatalogRegistred(locationname);
		if (location != null){
			bean = new LocationBean();
			bean.setLocation_description(location.getLocation_description());
			bean.setLocation_name(location.getLocation_name());
			bean.setLocationid(location.getLocationid());
		}
		return bean;
	}

	@Override
	public void registerSampleMovement(Date movDate, MovType movType,
			StorageLocation destLocation, String comments, Integer quantity,
			String hour, String minute, List<SampleDetail> samples) {
		for (SampleDetail sampleDetail : samples) {
			// update info about location and last date
			sampleDetail.setLastmovdate(movDate);
			// si destLocation llega con null no se debe sambiar el storage
			// location del sampledetail
			if (destLocation != null)
				sampleDetail.setStorageLocation(destLocation);
			sampleDetailDAO.update(sampleDetail);
			// now register the movement
			StorageMov storageMov = new StorageMov();
			storageMov.setMovdate(movDate);
			storageMov.setMovtype(movType.getId());
			storageMov.setComments(comments);
			storageMov.setQuantity(quantity);
			storageMov.setStorageLocation(destLocation);
			storageMov.setSampleDetail(sampleDetail);
			storageMov.setHourmov(hour);
			storageMov.setMinmov(minute);
			storageMovDAO.create(storageMov);
		}
		
	}

	public void setSampleDetailDAO(SampleDetailDAO sampleDetailDAO) {
		this.sampleDetailDAO = sampleDetailDAO;
	}

	public void setStorageMovDAO(StorageMovDAO storageMovDAO) {
		this.storageMovDAO = storageMovDAO;
	}

	@Override
	public LocationBean isLocationCatalogRegistred(String locationName) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
