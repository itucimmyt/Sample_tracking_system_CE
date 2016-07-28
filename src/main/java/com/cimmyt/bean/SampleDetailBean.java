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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.cimmyt.model.bean.SampleDetResult;
import com.cimmyt.model.bean.SampleDetail;
import com.cimmyt.model.bean.ShipmentDetail;
import com.cimmyt.model.bean.StorageMov;

public class SampleDetailBean {

		private Integer studysampleid;
	    private Integer breedergid;
	    private Integer samplegid;
	    private Integer entryNo;
		private String platename;
		private String plateloc;
		private String selforsend;
	    private Date lastmovdate;	
	    private Integer nplanta;
	    private String nval;
	    private String specie;
	    private String priority;
	    private String controltype;
	    private Collection<ShipmentdetailBean> imsShipmentdetailCollectionBean;
	    private LabStudyBean labstudyidBean;
	    private Collection<SampleDetResultBean> imsSampleDetResultCollectionBean;
	    private Collection<StorageMovBean> storageMovCollectionBean;
	    private StorageLocationBean storageLocationBean;
	    private LocationBean locationidBean;
	    private SeasonBean seasonidBean;
	    
		public Integer getStudysampleid() {
			return studysampleid;
		}
		public void setStudysampleid(Integer studysampleid) {
			this.studysampleid = studysampleid;
		}
		public Integer getBreedergid() {
			return breedergid;
		}
		public void setBreedergid(Integer breedergid) {
			this.breedergid = breedergid;
		}
		public Integer getSamplegid() {
			return samplegid;
		}
		public void setSamplegid(Integer samplegid) {
			this.samplegid = samplegid;
		}
		public Integer getEntryNo() {
			return entryNo;
		}
		public void setEntryNo(Integer entryNo) {
			this.entryNo = entryNo;
		}
		public String getPlatename() {
			return platename;
		}
		public void setPlatename(String platename) {
			this.platename = platename;
		}
		public String getPlateloc() {
			return plateloc;
		}
		public void setPlateloc(String plateloc) {
			this.plateloc = plateloc;
		}
		public String getSelforsend() {
			return selforsend;
		}
		public void setSelforsend(String selforsend) {
			this.selforsend = selforsend;
		}
		public Date getLastmovdate() {
			return lastmovdate;
		}
		public void setLastmovdate(Date lastmovdate) {
			this.lastmovdate = lastmovdate;
		}
		public Integer getNplanta() {
			return nplanta;
		}
		public void setNplanta(Integer nplanta) {
			this.nplanta = nplanta;
		}
		public String getNval() {
			return nval;
		}
		public void setNval(String nval) {
			this.nval = nval;
		}
		public String getSpecie() {
			return specie;
		}
		public void setSpecie(String specie) {
			this.specie = specie;
		}
		public String getPriority() {
			return priority;
		}
		public void setPriority(String priority) {
			this.priority = priority;
		}
		public String getControltype() {
			return controltype;
		}
		public void setControltype(String controltype) {
			this.controltype = controltype;
		}
		public Collection<ShipmentdetailBean> getImsShipmentdetailCollectionBean() {
			return imsShipmentdetailCollectionBean;
		}
		public void setImsShipmentdetailCollectionBean(
				Collection<ShipmentdetailBean> imsShipmentdetailCollectionBean) {
			this.imsShipmentdetailCollectionBean = imsShipmentdetailCollectionBean;
		}
		public LabStudyBean getLabstudyidBean() {
			return labstudyidBean;
		}
		public void setLabstudyidBean(LabStudyBean labstudyidBean) {
			this.labstudyidBean = labstudyidBean;
		}
		public Collection<SampleDetResultBean> getImsSampleDetResultCollectionBean() {
			return imsSampleDetResultCollectionBean;
		}
		public void setImsSampleDetResultCollectionBean(
				Collection<SampleDetResultBean> imsSampleDetResultCollectionBean) {
			this.imsSampleDetResultCollectionBean = imsSampleDetResultCollectionBean;
		}
		public Collection<StorageMovBean> getStorageMovCollectionBean() {
			return storageMovCollectionBean;
		}
		public void setStorageMovCollectionBean(
				Collection<StorageMovBean> storageMovCollectionBean) {
			this.storageMovCollectionBean = storageMovCollectionBean;
		}
		public StorageLocationBean getStorageLocationBean() {
			return storageLocationBean;
		}
		public void setStorageLocationBean(StorageLocationBean storageLocationBean) {
			this.storageLocationBean = storageLocationBean;
		}
		public LocationBean getLocationidBean() {
			return locationidBean;
		}
		public void setLocationidBean(LocationBean locationidBean) {
			this.locationidBean = locationidBean;
		}
		public SeasonBean getSeasonidBean() {
			return seasonidBean;
		}
		public void setSeasonidBean(SeasonBean seasonidBean) {
			this.seasonidBean = seasonidBean;
		}

	public SampleDetailBean (){}
	
	public SampleDetailBean(SampleDetail bean){
		this.breedergid = bean.getBreedergid();
		this.controltype = bean.getControltype();
		this.entryNo = bean.getEntryNo();
		List<SampleDetResultBean> list1 = new ArrayList<SampleDetResultBean>();
		for (SampleDetResult obj1 : bean.getImsSampleDetResultCollection()){
			list1.add(new SampleDetResultBean(obj1));
		}
		this.imsSampleDetResultCollectionBean = list1;
		
		List<ShipmentdetailBean> list2 = new ArrayList<ShipmentdetailBean>();
		for (ShipmentDetail obj2 : bean.getImsShipmentdetailCollection()){
			list2.add(new ShipmentdetailBean(obj2));
		}
		this.imsShipmentdetailCollectionBean = list2;
		this.labstudyidBean = new LabStudyBean(bean.getLabstudyid());
		this.lastmovdate = bean.getLastmovdate();
		this.locationidBean = new LocationBean (bean.getLocationid());
		this.nplanta = bean.getNplanta();
		this.nval = bean.getNval();
		this.plateloc = bean.getPlateloc();
		this.platename = bean.getPlatename();
		this.priority = bean.getPriority();
		this.samplegid = bean.getSamplegid();
		this.seasonidBean = new SeasonBean (bean.getSeasonid());
		this.selforsend = bean.getSelforsend();
		this.specie = bean.getSpecie();
		this.storageLocationBean = new StorageLocationBean(bean.getStorageLocation());
		List<StorageMovBean> list3 = new ArrayList<StorageMovBean>();
		for (StorageMov obj3 : bean.getStorageMovCollection()){
			list3.add(new StorageMovBean(obj3));
		}
		this.storageMovCollectionBean = list3;
		this.studysampleid = bean.getStudysampleid();
		
	}
	
	public SampleDetail getSampleDetail(SampleDetailBean bean){
		SampleDetail obj = new SampleDetail();
		obj.setBreedergid(bean.getBreedergid());
		obj.setControltype(bean.getControltype());
		obj.setEntryNo(bean.getEntryNo());
		
		Set<SampleDetResult> list1 = new HashSet<SampleDetResult>();
		for (SampleDetResultBean obj1 : bean.getImsSampleDetResultCollectionBean()){
			list1.add(obj1.getSampleDetResult(obj1));
		}
		obj.setImsSampleDetResultCollection(list1);
		Set<ShipmentDetail> list2 = new HashSet<ShipmentDetail>();
		for (ShipmentdetailBean obj2 : bean.getImsShipmentdetailCollectionBean()){
			list2.add(obj2.getShipmentdetail(obj2));
		}
		obj.setImsShipmentdetailCollection(list2);
		obj.setLabstudyid(bean.getLabstudyidBean().getLabStudy(bean.getLabstudyidBean()));
		obj.setLastmovdate(bean.getLastmovdate());
		obj.setLocationid(bean.getLocationidBean().getLocationBean(bean.getLocationidBean()));
		obj.setNplanta(bean.getNplanta());
		obj.setNval(bean.getNval());
		obj.setPlateloc(bean.getPlateloc());
		obj.setPlatename(bean.getPlatename());
		obj.setPriority(bean.getPriority());
		obj.setSamplegid(bean.getSamplegid());
		obj.setSeasonid(bean.getSeasonidBean().getSeason(bean.getSeasonidBean()));
		obj.setSelforsend(bean.getSelforsend());
		obj.setSpecie(bean.getSpecie());
		obj.setStorageLocation(bean.getStorageLocationBean().getStorageLocation(bean.getStorageLocationBean()));
		Set<StorageMov> list3 = new HashSet<StorageMov>();
		for (StorageMovBean obj3 : bean.getStorageMovCollectionBean()){
			list3.add(obj3.getStorageMov(obj3));
		}
		obj.setStorageMovCollection(list3);
		obj.setStudysampleid(bean.getStudysampleid());
		return obj;
	}
}
