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

import static com.cimmyt.utils.Constants.ATTRIBUTE_PARAM_REPORT;
import static com.cimmyt.utils.Constants.ATTRIBUTE_SHIPMENTS_ITEM;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;

import com.cimmyt.bean.CSVReportGenericBean;
import com.cimmyt.bean.RowCSV;
import com.cimmyt.model.bean.SampleDetail;
import com.cimmyt.model.bean.Shipment;
import com.cimmyt.model.bean.ShipmentDetail;
import com.cimmyt.model.bean.ShipmentSet;
import com.cimmyt.servlet.RedirectServletReport;
import com.cimmyt.servlet.SessionReport;
import com.cimmyt.utils.ConstantsDNA;
import com.cimmyt.utils.StrUtils;

public class ShippmentDartButton extends ShipmentEventListener{
	
	public ShippmentDartButton(){
		super(null, null, null);
	}

	@Override
	public void onEvent(Event event) throws Exception {
		CSVReportGenericBean csvReportGenericBean;
		event.getTarget().getDesktop().setAttribute(ATTRIBUTE_SHIPMENTS_ITEM, getBean());
		String idShipment = event.getTarget().getAttribute("shipId").toString();
		Shipment shipment = getServiceShipment().read(Integer.valueOf(idShipment));
		csvReportGenericBean = generateCsvDARTFile(shipment);
		List<ShipmentDetail> shipsDet = getServiceShipmentDetail().getShipmentDetails(shipment.getStShipmentSet());
		ShipmentDetail shipDet = shipsDet.get(0);		
		SessionReport sessionReport = new SessionReport();
		sessionReport.setB(new byte[0]);
		sessionReport.setType(ConstantsDNA.FILE_TYPE_CSV_GENERIC);
		sessionReport.setName(shipDet.getStSampleDetail().getLabstudyid().getTitle());
		sessionReport.setCsvReportGenericBean(csvReportGenericBean);
		
		RedirectServletReport.export(sessionReport);
	}
	
	public CSVReportGenericBean generateCsvDARTFile(Shipment ship) {
		CSVReportGenericBean bean = new CSVReportGenericBean();
		List<StringBuilder> listHeaders = new ArrayList<StringBuilder>();
		listHeaders.add(0,new StringBuilder("PlateID"));
		listHeaders.add(1,new StringBuilder("Row"));
		listHeaders.add(2,new StringBuilder("Column"));
		listHeaders.add(3,new StringBuilder("Organism"));
		listHeaders.add(4,new StringBuilder("Crop"));
		listHeaders.add(5,new StringBuilder("Genotype"));
		listHeaders.add(6,new StringBuilder("Tissue"));
		listHeaders.add(7,new StringBuilder("Comments"));
		bean.setListHeaders(listHeaders);
		List <RowCSV> listRowCSV = new ArrayList<RowCSV>();
		ShipmentSet set = ship.getStShipmentSet();
		for (ShipmentDetail details : getServiceShipmentDetail().getShipmentDetails(set)){
			RowCSV rowCSV = new RowCSV();
			List<StringBuilder> listRow = new ArrayList<StringBuilder>();
			SampleDetail sampleDet = details.getStSampleDetail();
			if (sampleDet != null) {
				listRow.add(new StringBuilder(sampleDet.getPlatename()));
				listRow.add(new StringBuilder(sampleDet.getPlateloc().substring(0, 1)));
				listRow.add(new StringBuilder(sampleDet.getPlateloc().substring(1)));
				
				if(sampleDet.getSamplegid() == null){
					StringBuilder emptyString = new StringBuilder("");
					listRow.add(emptyString);
					listRow.add(emptyString);
					listRow.add(emptyString);
					listRow.add(emptyString);
					listRow.add(emptyString);
				}else{
					listRow.add(new StringBuilder(sampleDet.getLabstudyid().getOrganismid().getOrganismname()));
					listRow.add(new StringBuilder(sampleDet.getSpecie()==null?"":sampleDet.getSpecie()));
					listRow.add(new StringBuilder(sampleDet.getLabstudyid().getPrefix()+
							(sampleDet.getLabstudyid().isUsePadded() ? StrUtils.getPaddingCeros(sampleDet.getSamplegid()) :
								String.valueOf(sampleDet.getSamplegid()))));
					listRow.add(new StringBuilder(sampleDet.getLabstudyid().getTissue().getTissueName()));
					listRow.add(new StringBuilder(sampleDet.getPriority()==null?sampleDet.getBreedergid().toString():sampleDet.getPriority()));
				}
			}
			rowCSV.setListRow(listRow);
			listRowCSV.add(rowCSV);
		}
		bean.setListRowCSV(listRowCSV);
		return bean;
	}
	
}
