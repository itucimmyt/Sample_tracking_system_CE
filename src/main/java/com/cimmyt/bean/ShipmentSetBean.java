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
import com.cimmyt.model.bean.ShipmentDetail;
import com.cimmyt.model.bean.ShipmentSet;

public class ShipmentSetBean {

	    private Integer idShipmentSet;
	    private Date datCreated;
	    private String comments;    
	    private Collection<ShipmentBean> shipmentCollectionBean;
	    private Collection<ShipmentdetailBean> imsShipmentdetailCollectionBean;
	    private InvestigatorBean investigatoridBean;
		public Integer getIdShipmentSet() {
			return idShipmentSet;
		}
		public void setIdShipmentSet(Integer idShipmentSet) {
			this.idShipmentSet = idShipmentSet;
		}
		public Date getDatCreated() {
			return datCreated;
		}
		public void setDatCreated(Date datCreated) {
			this.datCreated = datCreated;
		}
		public String getComments() {
			return comments;
		}
		public void setComments(String comments) {
			this.comments = comments;
		}
		public Collection<ShipmentBean> getShipmentCollectionBean() {
			return shipmentCollectionBean;
		}
		public void setShipmentCollectionBean(
				Collection<ShipmentBean> shipmentCollectionBean) {
			this.shipmentCollectionBean = shipmentCollectionBean;
		}
		public Collection<ShipmentdetailBean> getImsShipmentdetailCollectionBean() {
			return imsShipmentdetailCollectionBean;
		}
		public void setImsShipmentdetailCollectionBean(
				Collection<ShipmentdetailBean> imsShipmentdetailCollectionBean) {
			this.imsShipmentdetailCollectionBean = imsShipmentdetailCollectionBean;
		}
		public InvestigatorBean getInvestigatoridBean() {
			return investigatoridBean;
		}
		public void setInvestigatoridBean(InvestigatorBean investigatoridBean) {
			this.investigatoridBean = investigatoridBean;
		}
		public ShipmentSetBean() {
			super();
			// TODO Auto-generated constructor stub
		}
		public ShipmentSetBean(Integer idShipmentSet, Date datCreated,
				String comments,
				Collection<ShipmentBean> shipmentCollectionBean, Collection<ShipmentdetailBean> shipmentDetailCollectionBean,
				InvestigatorBean investigatoridBean) {
			super();
			this.idShipmentSet = idShipmentSet;
			this.datCreated = datCreated;
			this.comments = comments;
			this.shipmentCollectionBean = shipmentCollectionBean;
			this.imsShipmentdetailCollectionBean = shipmentDetailCollectionBean;
			this.investigatoridBean = investigatoridBean;
		}
		public ShipmentSetBean(ShipmentSet bean){
			List<ShipmentBean> list = new ArrayList<ShipmentBean>();
			List<ShipmentdetailBean> listDetail = new ArrayList<ShipmentdetailBean>();
			this.idShipmentSet = bean.getIdShipmentSet();
			this.datCreated = bean.getDatCreated();
			this.comments = bean.getComments();
			for(Shipment obj : bean.getStShipments()){
				list.add(new ShipmentBean(obj));
			}
			this.shipmentCollectionBean = list;
			for(ShipmentDetail obj : bean.getStShipmentDetails()){
				listDetail.add(new ShipmentdetailBean(obj));
			}
			this.imsShipmentdetailCollectionBean = listDetail;
			this.investigatoridBean = new InvestigatorBean(bean.getStInvestigator());
		}
		public ShipmentSet getShipmentSet(ShipmentSetBean bean){
			ShipmentSet obj = new ShipmentSet();
			Set<Shipment> list = new HashSet<Shipment>();
			Set<ShipmentDetail> listShipDetail = new HashSet<ShipmentDetail>();
			obj.setIdShipmentSet(bean.idShipmentSet);
			obj.setDatCreated(bean.getDatCreated());
			obj.setComments(bean.comments);
			for(ShipmentBean objShipment : bean.getShipmentCollectionBean()){
				list.add(objShipment.getShipment(objShipment));
			}
			obj.setStShipments(list);
			for(ShipmentdetailBean objShipDetail : bean.getImsShipmentdetailCollectionBean()){
				listShipDetail.add(objShipDetail.getShipmentdetail(objShipDetail));
			}
			obj.setStInvestigator(bean.getInvestigatoridBean().getInvestigator(investigatoridBean));
			return obj;
		}
}
