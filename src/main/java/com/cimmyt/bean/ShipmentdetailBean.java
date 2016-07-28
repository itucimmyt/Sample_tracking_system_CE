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

import com.cimmyt.model.bean.ShipmentDetail;


public class ShipmentdetailBean {

	 	private Integer shipmentdetid;
	    private ShipmentBean shipmentid;
	    private SampleDetailBean studysampleid;

		public Integer getShipmentdetid() {
			return shipmentdetid;
		}
		public void setShipmentdetid(Integer shipmentdetid) {
			this.shipmentdetid = shipmentdetid;
		}
		public ShipmentBean getShipmentid() {
			return shipmentid;
		}
		public void setShipmentid(ShipmentBean shipmentid) {
			this.shipmentid = shipmentid;
		}
		public SampleDetailBean getStudysampleid() {
			return studysampleid;
		}
		public void setStudysampleid(SampleDetailBean studysampleid) {
			this.studysampleid = studysampleid;
		}

	    public ShipmentdetailBean(){}
	    
	    public ShipmentdetailBean (ShipmentDetail bean){
	    	this.shipmentdetid = bean.getIdShipmentDetail();
//	    	this.shipmentid = new ShipmentBean(bean.getStShipmentSet().getStShipments());
	    	this.studysampleid = new SampleDetailBean(bean.getStSampleDetail());
	    }

	    public ShipmentDetail getShipmentdetail (ShipmentdetailBean bean){
	    	ShipmentDetail obj = new ShipmentDetail();
	    	obj.setIdShipmentDetail(bean.getShipmentdetid());
//	    	obj.setShipmentid(bean.getShipmentid().
	//    			getShipment(bean.getShipmentid()));
	    	obj.setStSampleDetail(bean.getStudysampleid().
	    			getSampleDetail(bean.getStudysampleid()));
	    	return obj;
	    }
}
