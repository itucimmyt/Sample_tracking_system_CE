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
package com.cimmyt.zk.storage_location;

import static com.cimmyt.utils.Constants.SAMPLE_DETAIL_SERVICE_BEAN_NAME;
import static com.cimmyt.utils.Constants.STORAGE_LOCATION_SERVICE_BEAN_NAME;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zkplus.spring.SpringUtil;

import com.cimmyt.model.bean.SampleDetail;
import com.cimmyt.model.bean.StorageLocation;
import com.cimmyt.service.ServiceSampleDetail;
import com.cimmyt.service.ServiceStorageLocation;

public class StorageLocationList {
	
	private StorageLocationTreeNode root;
	private static ServiceStorageLocation serviceStorageLocation = null;
	private static ServiceSampleDetail serviceSampleDetail = null;
	static {
		if(serviceStorageLocation == null)
        {
			try{
				serviceStorageLocation = (ServiceStorageLocation)SpringUtil.getBean(STORAGE_LOCATION_SERVICE_BEAN_NAME);
				serviceSampleDetail = (ServiceSampleDetail)SpringUtil.getBean(SAMPLE_DETAIL_SERVICE_BEAN_NAME);
			}catch (Exception e){
				e.printStackTrace();
			}
        }
		
	}
	
	public StorageLocationList (){
		root = getStorageLocationTreeNode();
	}
	
	public StorageLocationTreeNode getRoot(){
		return root;
	}
	private StorageLocationTreeNode getStorageLocationTreeNode(){
		return new StorageLocationTreeNode(null, getListItemStorageLocation(null),true);
		
	}
	
	private StorageLocationTreeNode[] getListItemStorageLocation(StorageLocation str){
		StorageLocationTreeNode[]  arr = null;
		List<StorageLocationTreeNode> listArr = new ArrayList<StorageLocationTreeNode>();
		StorageLocationTreeNode  storage= null;
		List<StorageLocation> storageList;
		if (str == null){
			storageList = getStorageLocation(new StorageLocation());
		}else{
			storageList = getStorageLocation(str);
		} 
		
		
		if (storageList != null && !storageList.isEmpty()){
			for (StorageLocation s :storageList ){

				if (s.getLsname() != null && !s.getLsname().equals("")){
					s.setImslocidparent(s.getImslocid());
					
					storage =new StorageLocationTreeNode(s,getListItemStorageLocation(s),true);
					s.setSampleDetailCollection(null);
					listArr.add(storage);
				}
			}
		}
		arr = new StorageLocationTreeNode[listArr.size()];
		return listArr.toArray(arr);
	}
	
	private List<StorageLocation> getStorageLocation(StorageLocation storage){
		List<StorageLocation> storageL = serviceStorageLocation.getListByFilter(storage);
		return storageL;
	}
	
}
