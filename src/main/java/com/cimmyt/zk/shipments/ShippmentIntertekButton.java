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

import static com.cimmyt.utils.Constants.ATTRIBUTE_NAME_USER_BEAN;
import static com.cimmyt.utils.Constants.ATTRIBUTE_SHIPMENTS_ITEM;
import static com.cimmyt.utils.Constants.LOCALE_LANGUAGE;
import static com.cimmyt.utils.Constants.REPORT_KBIO_SERVICE;

import java.util.List;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zkplus.spring.SpringUtil;

import com.cimmyt.bean.UserBean;
import com.cimmyt.model.bean.Shipment;
import com.cimmyt.model.bean.ShipmentDetail;
import com.cimmyt.reports.ServiceReportKBio;
import com.cimmyt.servlet.RedirectServletReport;
import com.cimmyt.servlet.SessionReport;
import com.cimmyt.utils.ConstantsDNA;
import com.cimmyt.utils.PropertyHelper;

public class ShippmentIntertekButton extends ShipmentEventListener{

	//Variable to management of internationalization  
		private PropertyHelper pro=null;
	//Bean to management of KBio reports
	private static ServiceReportKBio serviceReportKBio = null;
	static {
		if(serviceReportKBio == null)
        {
			try{
				serviceReportKBio = (ServiceReportKBio)SpringUtil.getBean(REPORT_KBIO_SERVICE);
			}catch (Exception e){
				e.printStackTrace();
			}
        }
		
	}

	public ShippmentIntertekButton() {
		super(null, null, null);
	}

	/**
	 * Method that load the report List in XLS
	 */
	@Override
	public void onEvent(Event event) throws Exception {
		event.getTarget().getDesktop().setAttribute(ATTRIBUTE_SHIPMENTS_ITEM, getBean());
		String idShipment = event.getTarget().getAttribute("shipId").toString();
		Shipment shipment = getServiceShipment().read(Integer.valueOf(idShipment));
		UserBean userBean =(UserBean) event.getTarget().getDesktop().getSession().getAttribute(ATTRIBUTE_NAME_USER_BEAN);
		pro = (PropertyHelper)event.getTarget().getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);
		List<ShipmentDetail> shipsDet = getServiceShipmentDetail().getShipmentDetails(shipment.getStShipmentSet());
		
			SessionReport sessionReport = new SessionReport();
			sessionReport.setB(serviceReportKBio.getBytesReportIntertekGrid(shipsDet, pro, userBean, shipment));
			sessionReport.setType(ConstantsDNA.FILE_TYPE_XLS);
			sessionReport.setName(shipment.getComment()+"LIST");
			RedirectServletReport.export(sessionReport);
		}

}
