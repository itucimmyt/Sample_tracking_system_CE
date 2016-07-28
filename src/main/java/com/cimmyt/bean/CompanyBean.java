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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.cimmyt.model.bean.Company;
import com.cimmyt.model.bean.Shipment;

public class CompanyBean {

	private Integer idCompany;
	private String name;
	private String addresss;
	private String email;
	private String contactname;
	private String phone;
	private Collection<ShipmentBean> imsShipmentCollection;
	private StorageLocationBean storagelocation;
	
	public Integer getIdCompany() {
		return idCompany;
	}
	public void setIdCompany(Integer idCompany) {
		this.idCompany = idCompany;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddresss() {
		return addresss;
	}
	public void setAddresss(String addresss) {
		this.addresss = addresss;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContactname() {
		return contactname;
	}
	public void setContactname(String contactname) {
		this.contactname = contactname;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Collection<ShipmentBean> getImsShipmentCollection() {
		return imsShipmentCollection;
	}
	public void setImsShipmentCollection(
			Collection<ShipmentBean> imsShipmentCollection) {
		this.imsShipmentCollection = imsShipmentCollection;
	}
	public StorageLocationBean getStoragelocation() {
		return storagelocation;
	}
	public void setStoragelocation(StorageLocationBean storagelocation) {
		this.storagelocation = storagelocation;
	}
	
	public CompanyBean (){
		
	}

	public CompanyBean (Company bean){
		this.addresss = bean.getAddresss();
		this.contactname = bean.getContactname();
		this.email = bean.getEmail();
		this.idCompany = bean.getIdCompany();
		
		List<ShipmentBean> imsShipmentCollection = new ArrayList<ShipmentBean>();
		for (Shipment shipment : bean.getImsShipmentCollection()){
			imsShipmentCollection.add(new ShipmentBean(shipment));
		}
		this.imsShipmentCollection = imsShipmentCollection;
		this.name = bean.getName();
		this.phone = bean.getPhone();
		this.storagelocation = new StorageLocationBean (bean.getStoragelocation());

	}

	public Company getCompany(CompanyBean bean){
		Company obj = new Company();
		obj.setAddresss(bean.getAddresss());
		obj.setContactname(bean.getContactname());
		obj.setEmail(bean.getEmail());
		obj.setIdCompany(bean.getIdCompany());
		
		Set<Shipment> imsShipmentCollection = new HashSet<Shipment>();
		for (ShipmentBean shipment : bean.getImsShipmentCollection()){
			imsShipmentCollection.add(shipment.getShipment(shipment));
		}
		obj.setImsShipmentCollection(imsShipmentCollection);
		obj.setName(bean.getName());
		obj.setPhone(bean.getPhone());
		obj.setStoragelocation(bean.getStoragelocation().
				getStorageLocation(bean.getStoragelocation()));
		
		return obj;
	}
	
}
