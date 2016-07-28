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

import com.cimmyt.model.bean.Shipment;
import com.cimmyt.model.bean.Status;

public class ShipmentBean {

	    private Integer idShipment;
	    private Date datRegister;
	    private Date datSend;
	    private Date datReceive;
	    private Status stStatus;
	    private String comment;
	    private String trackingNumberLocal;
	    private String trackingNumberDelivery;
	    private ShipmentSetBean stShipmentSet;
	    private CompanyBean stCompany;

	public Integer getIdShipment() {
			return idShipment;
		}

		public void setIdShipment(Integer idShipment) {
			this.idShipment = idShipment;
		}

		public Date getDatRegister() {
			return datRegister;
		}

		public void setDatRegister(Date datRegister) {
			this.datRegister = datRegister;
		}

		public Date getDatSend() {
			return datSend;
		}

		public void setDatSend(Date datSend) {
			this.datSend = datSend;
		}

		public Date getDatReceive() {
			return datReceive;
		}

		public void setDatReceive(Date datReceive) {
			this.datReceive = datReceive;
		}

		public Status getStStatus() {
			return stStatus;
		}

		public void setStStatus(Status stStatus) {
			this.stStatus = stStatus;
		}

		public String getComment() {
			return comment;
		}

		public void setComment(String comment) {
			this.comment = comment;
		}

		public String getTrackingNumberLocal() {
			return trackingNumberLocal;
		}

		public void setTrackingNumberLocal(String trackingNumberLocal) {
			this.trackingNumberLocal = trackingNumberLocal;
		}

		public String getTrackingNumberDelivery() {
			return trackingNumberDelivery;
		}

		public void setTrackingNumberDelivery(String trackingNumberDelivery) {
			this.trackingNumberDelivery = trackingNumberDelivery;
		}

		public ShipmentSetBean getStShipmentSet() {
			return stShipmentSet;
		}

		public void setStShipmentSet(ShipmentSetBean stShipmentSet) {
			this.stShipmentSet = stShipmentSet;
		}

		public CompanyBean getStCompany() {
			return stCompany;
		}

		public void setStCompany(CompanyBean stCompany) {
			this.stCompany = stCompany;
		}

	public ShipmentBean(){}
	
	public ShipmentBean(Shipment bean){
		this.comment = bean.getComment();
		this.stCompany = new CompanyBean(bean.getStCompany());
		this.datReceive = bean.getDatReceive();
		this.datRegister = bean.getDatRegister();
		this.datSend = bean.getDatSend();
		this.idShipment = bean.getIdShipment();
		this.stStatus = bean.getStStatus();
		this.trackingNumberLocal = bean.getTrackingNumberLocal();
		this.trackingNumberDelivery = bean.getTrackingNumberDelivery();
	}

	public Shipment getShipment(ShipmentBean bean){
		Shipment obj = new Shipment();
		obj.setComment(bean.getComment());
		obj.setStCompany(bean.getStCompany().getCompany(stCompany));
		obj.setDatReceive(bean.getDatReceive());
		obj.setDatRegister(bean.getDatRegister());
		obj.setDatSend(bean.getDatSend());
		obj.setIdShipment(bean.getIdShipment());
		obj.setStStatus(bean.getStStatus());
		obj.setTrackingNumberLocal(bean.getTrackingNumberLocal());
		obj.setTrackingNumberDelivery(bean.getTrackingNumberDelivery());
		return obj;
		}
}
