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

import com.cimmyt.model.bean.StorageLocation;
import com.cimmyt.model.dao.StorageLocationDAO;
import com.cimmyt.service.ServiceStorageLocation;

public class ServiceStorageLocationImpl implements ServiceStorageLocation{

	private StorageLocationDAO storageLocationDAO;
	@Override
	public List<StorageLocation> getAllStorageLocations() {
		return storageLocationDAO.getAllStorageLocations();
	}

	@Override
	public StorageLocation getStorageLocationWithName(String name) {
		return storageLocationDAO.getStorageLocationWithName(name);
	}

	@Override
	public boolean ThisStorageLocationHaveChildrens(StorageLocation parent) {
		return storageLocationDAO.ThisStorageLocationHaveChildrens(parent);
	}

	@Override
	public StorageLocation create(StorageLocation parent) {
		return storageLocationDAO.create(parent);
	}

	@Override
	public List<StorageLocation> getListByFilter(StorageLocation bean) {
		return storageLocationDAO.getListByFilter(bean);
	}

	public void setStorageLocationDAO(StorageLocationDAO storageLocationDAO) {
		this.storageLocationDAO = storageLocationDAO;
	}

	@Override
	public void add(StorageLocation bean) {
		storageLocationDAO.add(bean);
		
	}

	@Override
	public void delete(StorageLocation bean) {
		storageLocationDAO.delete(bean);
	}

}
