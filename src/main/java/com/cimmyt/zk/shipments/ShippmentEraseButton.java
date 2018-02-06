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
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_DELETE_ERROR;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_DELETE_RECORD;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_DELETE_SUCCESS;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_ERROR;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_INFORMATION;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_PLEASE_CONFIRM;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_SELECT_RECORD;
import static com.cimmyt.utils.Constants.LBL_GENERIC_STUDY_TEMPLATE;
import static com.cimmyt.utils.Constants.LOCALE_LANGUAGE;
import org.apache.log4j.Logger;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Messagebox;
import com.cimmyt.model.bean.ShipmentSet;
import com.cimmyt.utils.PropertyHelper;

@SuppressWarnings("serial")
public class ShippmentEraseButton extends ShipmentEventListener{
	
	public ShippmentEraseButton() {
		super(null, null, null);
	}

	private Logger logger= Logger.getLogger(ShippmentEraseButton.class);

	@Override
	public void onEvent(Event event) throws Exception {
		
		event.getTarget().getDesktop().setAttribute(ATTRIBUTE_SHIPMENTS_ITEM, getBean());
		setPro( (PropertyHelper) event.getTarget().getDesktop().getSession().getAttribute(LOCALE_LANGUAGE) );
		String idShipment = event.getTarget().getId();
		if (!idShipment.isEmpty()){
			ShipmentSet shipment = getServiceShipmentSet().read(Integer.valueOf(idShipment));
			if (Messagebox.show(getPro().getKey(LBL_GENERIC_MESS_DELETE_RECORD), 
				getPro().getKey(LBL_GENERIC_MESS_PLEASE_CONFIRM), 
				Messagebox.YES | Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
				try {
					if(getServiceShipment().getShipments(shipment).size() > 0){
						Messagebox.show(getPro().getKey(LBL_GENERIC_MESS_DELETE_ERROR));

					}else{
					getServiceShipmentSet().delete(shipment);
					Messagebox.show(getPro().getKey(LBL_GENERIC_MESS_DELETE_SUCCESS), 
							getPro().getKey(LBL_GENERIC_MESS_INFORMATION), 
							Messagebox.OK, Messagebox.INFORMATION);
					}

					ControlShipments shipWindow = (ControlShipments)event.getTarget().getRoot().getFellow("idCenterMenu").getFirstChild().getFirstChild();
					shipWindow.doAfterCompose();

				}catch (Exception sql){
					Messagebox.show(getPro().getKey(LBL_GENERIC_MESS_DELETE_ERROR,new String []{getPro().getKey(LBL_GENERIC_STUDY_TEMPLATE)}), 
					getPro().getKey(LBL_GENERIC_MESS_ERROR), 
					Messagebox.OK, Messagebox.ERROR);
					logger.info(sql.getMessage());
				}
			}	
		}else {
			Messagebox.show(LBL_GENERIC_MESS_SELECT_RECORD,getPro().getKey(LBL_GENERIC_MESS_INFORMATION), 
					Messagebox.OK, Messagebox.INFORMATION);
		}
	}

}
