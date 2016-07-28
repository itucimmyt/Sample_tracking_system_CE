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
package com.cimmyt.bean;

import java.util.Date;

import com.cimmyt.model.bean.StorageMov;

public class StorageMovBean {

	private Integer idstoragemov;
    private Date movdate;
    private String movtype;
    private Integer quantity;
    private String comments;
    private String hourmov;
    private String minmov;
    private StorageLocationBean storageLocation;
    private SampleDetailBean sampleDetail;
    
	public Integer getIdstoragemov() {
		return idstoragemov;
	}
	public void setIdstoragemov(Integer idstoragemov) {
		this.idstoragemov = idstoragemov;
	}
	public Date getMovdate() {
		return movdate;
	}
	public void setMovdate(Date movdate) {
		this.movdate = movdate;
	}
	public String getMovtype() {
		return movtype;
	}
	public void setMovtype(String movtype) {
		this.movtype = movtype;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getHourmov() {
		return hourmov;
	}
	public void setHourmov(String hourmov) {
		this.hourmov = hourmov;
	}
	public String getMinmov() {
		return minmov;
	}
	public void setMinmov(String minmov) {
		this.minmov = minmov;
	}
	
	public StorageLocationBean getStorageLocation() {
		return storageLocation;
	}
	public void setStorageLocation(StorageLocationBean storageLocation) {
		this.storageLocation = storageLocation;
	}
	public SampleDetailBean getSampleDetail() {
		return sampleDetail;
	}
	public void setSampleDetail(SampleDetailBean sampleDetail) {
		this.sampleDetail = sampleDetail;
	}
	public StorageMovBean (){}
	
	public StorageMovBean(StorageMov bean){
		this.comments = bean.getComments();
		this.hourmov = bean.getHourmov();
		this.idstoragemov = bean.getIdstoragemov();
		this.minmov = bean.getMinmov();
		this.movdate = bean.getMovdate();
		this.movtype = bean.getMovtype();
		this.quantity = bean.getQuantity();
		this.storageLocation = new StorageLocationBean(bean.getStorageLocation());
		this.sampleDetail = new SampleDetailBean(bean.getSampleDetail());
	}

	public StorageMov getStorageMov(StorageMovBean bean){
		StorageMov obj = new StorageMov();
		obj.setComments(bean.getComments());
		obj.setHourmov(bean.getHourmov());
		obj.setIdstoragemov(bean.getIdstoragemov());
		obj.setMinmov(bean.getMinmov());
		obj.setMovdate(bean.getMovdate());
		obj.setMovtype(bean.getMovtype());
		obj.setQuantity(bean.getQuantity());
		obj.setSampleDetail(bean.getSampleDetail().getSampleDetail(
				bean.getSampleDetail()));
		obj.setStorageLocation(bean.getStorageLocation().getStorageLocation(
				bean.getStorageLocation()));
		return obj;
	}
    
}
