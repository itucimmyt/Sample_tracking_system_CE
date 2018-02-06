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

public class ShipmentGenotypingService extends ShipmentEventListener{

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
		
	public ShipmentGenotypingService() {
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
			sessionReport.setB(serviceReportKBio.getBytesReportGnotypingServices(shipsDet, pro, userBean, shipment));
			sessionReport.setType(ConstantsDNA.FILE_TYPE_XLS);
			sessionReport.setName(shipment.getComment()+"_intertek");
			RedirectServletReport.export(sessionReport);
		}
}
