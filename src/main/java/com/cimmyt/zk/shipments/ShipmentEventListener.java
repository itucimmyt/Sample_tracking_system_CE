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
package com.cimmyt.zk.shipments;

import static com.cimmyt.utils.Constants.ATTRIBUTE_SHIPMENTS_ITEM;
import static com.cimmyt.utils.Constants.ATTRIBUTE_SHIPMENT_ITEM;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_ERROR;
import static com.cimmyt.utils.Constants.LOCALE_LANGUAGE;
import static com.cimmyt.utils.Constants.SHIPMENT_SERVICE_BEAN_NAME;
import static com.cimmyt.utils.Constants.SHIPMENT_SERVICE_DETAIL_BEAN_NAME;
import static com.cimmyt.utils.Constants.SHIPMENT_SERVICE_SET_BEAN_NAME;
import static com.cimmyt.utils.Constants.STATUS_SERVICE_BEAN_NAME;
import static com.cimmyt.utils.Constants.MSG_SHIPMENT_ERROR_SEND;

import org.zkoss.util.Dates;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Messagebox;

import com.cimmyt.model.bean.Shipment;
import com.cimmyt.model.bean.ShipmentSet;
import com.cimmyt.model.bean.Status;
import com.cimmyt.service.ServiceShipment;
import com.cimmyt.service.ServiceShipmentDetail;
import com.cimmyt.service.ServiceShipmentSet;
import com.cimmyt.service.ServiceStatus;
import com.cimmyt.utils.PropertyHelper;
import static com.cimmyt.utils.Constants.LBL_SHIPMENT_STRING_SENT;
import static com.cimmyt.utils.Constants.LBL_SHIPMENT_STRING_RECEIVED;
import static com.cimmyt.utils.Constants.LBL_SHIPMENT_STRING_FOR_SEND;

public class ShipmentEventListener implements EventListener<Event>{

	private static ServiceShipment serviceShipment;
	private static ServiceShipmentSet serviceShipmentSet;

	private static ServiceShipmentDetail serviceShipmentDetail;
	private static ServiceStatus serviceStatus;
	private static ShipmentSet bean;

	private PropertyHelper pro=null;

	private String statusKey, okKey, errorKey;

	static {
		try{
			if(serviceShipment == null)
				serviceShipment = (ServiceShipment) SpringUtil.getBean(SHIPMENT_SERVICE_BEAN_NAME);

	    	if (serviceShipmentDetail == null)
    			serviceShipmentDetail = (ServiceShipmentDetail)SpringUtil.getBean(SHIPMENT_SERVICE_DETAIL_BEAN_NAME); 

			if(serviceStatus == null)
				serviceStatus = (ServiceStatus) SpringUtil.getBean(STATUS_SERVICE_BEAN_NAME);
			
			if(serviceShipmentSet == null)
				serviceShipmentSet = (ServiceShipmentSet) SpringUtil.getBean(SHIPMENT_SERVICE_SET_BEAN_NAME);
		}catch (Exception e){
			e.printStackTrace();
		}
		
	}

	
	public ShipmentEventListener(String statusKey, String okKey, String errorKey){
		this.statusKey = statusKey;
		this.okKey = okKey;
		this.errorKey = errorKey;
	}

	public void onEvent(Event event) throws Exception {
		pro = (PropertyHelper)event.getTarget().getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);
		event.getTarget().getDesktop().setAttribute(ATTRIBUTE_SHIPMENTS_ITEM, bean);
		String idShipment = event.getTarget().getAttribute("shipId").toString();
			if (!idShipment.isEmpty()){
				
				Status stStatus = null;
				stStatus = serviceStatus.read(statusKey);
				Shipment shipment = serviceShipment.read(Integer.valueOf(idShipment));

				if(	shipment.getTrackingNumberLocal() ==null ||
						shipment.getTrackingNumberLocal().isEmpty() ||
						shipment.getTrackingNumberDelivery() == null ||
						shipment.getTrackingNumberDelivery().isEmpty())
				{
					Messagebox.show(pro.getKey(MSG_SHIPMENT_ERROR_SEND),pro.getKey(LBL_GENERIC_MESS_ERROR), 
							Messagebox.OK, Messagebox.EXCLAMATION);
				}else{
					event.getTarget().getDesktop().setAttribute(ATTRIBUTE_SHIPMENT_ITEM, shipment);
					shipment.setStStatus(stStatus);
					
					
					if(statusKey.equals(LBL_SHIPMENT_STRING_FOR_SEND)){
						shipment.setDatSend(null);
						shipment.setDatReceive(null);
					}
					if(statusKey.equals(LBL_SHIPMENT_STRING_SENT)){
						shipment.setDatSend(Dates.now());
						shipment.setDatReceive(null);
					}else if(statusKey.equals(LBL_SHIPMENT_STRING_RECEIVED)){
						shipment.setDatReceive(Dates.now());
					}
					
					
					serviceShipment.saveOrUpdateShipment(shipment);
	
					ControlShipments shipWindow = (ControlShipments)event.getTarget().getRoot().getFellow("idCenterMenu").getFirstChild().getFirstChild();
					shipWindow.doAfterCompose();
					
					Messagebox.show(pro.getKey(okKey)+" current shipment",pro.getKey(okKey), 
							Messagebox.OK, Messagebox.INFORMATION);
				}
			}else{
				Messagebox.show(pro.getKey(errorKey),pro.getKey(LBL_GENERIC_MESS_ERROR), 
						Messagebox.OK, Messagebox.ERROR);
			}
	}
	
	public  ServiceShipment getServiceShipment() {
		return serviceShipment;
	}

	public  ServiceShipmentDetail getServiceShipmentDetail() {
		return serviceShipmentDetail;
	}

	public ServiceStatus getServiceStatus() {
		return serviceStatus;
	}

	public static ShipmentSet getBean() {
		return bean;
	}

	public static ServiceShipmentSet getServiceShipmentSet() {
		return serviceShipmentSet;
	}
	
	public PropertyHelper getPro() {
		return pro;
	}

	public void setPro(PropertyHelper pro) {
		this.pro = pro;
	}

}
