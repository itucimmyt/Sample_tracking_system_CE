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
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_ERROR;
import static com.cimmyt.utils.Constants.LBL_SHIPMENT_TITLE_SUB_ADD_S;
import static com.cimmyt.utils.Constants.LOCALE_LANGUAGE;
import static com.cimmyt.utils.Constants.SHIPMENT_SERVICE_SET_BEAN_NAME;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.cimmyt.model.bean.ShipmentSet;
import com.cimmyt.service.ServiceShipmentSet;
import com.cimmyt.utils.PropertyHelper;

@SuppressWarnings("serial")
public class ShippmentButton extends ShipmentEventListener{

	public ShippmentButton() {
		super(null, null, null);
	}

	@Override
	public void onEvent(Event event) throws Exception {
		setPro( (PropertyHelper)event.getTarget().getDesktop().getSession().getAttribute(LOCALE_LANGUAGE));
		event.getTarget().getDesktop().setAttribute(ATTRIBUTE_SHIPMENTS_ITEM, getBean());
		String idShipment = event.getTarget().getId();
		ShipmentSet shipment = getServiceShipmentSet().read(Integer.valueOf(idShipment));
		event.getTarget().getDesktop().setAttribute(ATTRIBUTE_SHIPMENTS_ITEM, shipment);
			if (!idShipment.isEmpty()){
				final Window win = (Window) Executions.createComponents("/shipment_management/window_add_shipment_information.zul"
						, event.getTarget().getRoot().getFellow("idCenterMenu").getFirstChild().getFirstChild()
						, null);
		    	win.doModal();
			}else{
				Messagebox.show(getPro().getKey(LBL_SHIPMENT_TITLE_SUB_ADD_S),getPro().getKey(LBL_GENERIC_MESS_ERROR), 
						Messagebox.OK, Messagebox.ERROR);
			}
	}

}
