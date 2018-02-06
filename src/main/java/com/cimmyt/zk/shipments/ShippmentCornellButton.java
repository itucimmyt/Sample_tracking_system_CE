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

import static com.cimmyt.utils.Constants.ATTRIBUTE_CORNELL_ITEM;
import static com.cimmyt.utils.Constants.ATTRIBUTE_PARAM_REPORT;
import static com.cimmyt.utils.Constants.ATTRIBUTE_SHIPMENTS_ITEM;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Window;

import com.cimmyt.bean.CSVReportGenericBean;
import com.cimmyt.bean.FileCornellBean;
import com.cimmyt.bean.RowCSV;
import com.cimmyt.model.bean.SampleDetail;
import com.cimmyt.model.bean.Shipment;
import com.cimmyt.model.bean.ShipmentDetail;
import com.cimmyt.model.bean.ShipmentSet;
import com.cimmyt.servlet.RedirectServletReport;
import com.cimmyt.servlet.SessionReport;
import com.cimmyt.utils.ConstantsDNA;
import com.cimmyt.utils.StrUtils;

/**
 * @author CIMMYT
 * Class to management of report of CORNELL
 */
public class ShippmentCornellButton extends ShipmentEventListener{
	
	/**
	 * Default constructor
	 */
	public ShippmentCornellButton() {
		super(null,null,null);
	}

	private FileCornellBean beanFileCornel;
	private Desktop desktop;

	@Override
	public void onEvent(Event event) throws Exception {
		CSVReportGenericBean csvReportGenericBean;
		event.getTarget().getDesktop().setAttribute(ATTRIBUTE_SHIPMENTS_ITEM, getBean());
		String idShipment = event.getTarget().getAttribute("shipId").toString();
		Shipment shipment = getServiceShipment().read(Integer.valueOf(idShipment));
		showWindow();
		desktop = event.getTarget().getDesktop();
		if (event.getTarget().getDesktop().getAttribute(ATTRIBUTE_CORNELL_ITEM) != null){
			csvReportGenericBean = generateCsvCornellFile(shipment);
			List<ShipmentDetail> shipsDet = getServiceShipmentDetail().getShipmentDetails(shipment.getStShipmentSet());
			ShipmentDetail shipDet = shipsDet.get(0);		
			SessionReport sessionReport = new SessionReport();
			sessionReport.setB(new byte[0]);
			sessionReport.setType(ConstantsDNA.FILE_TYPE_CSV_GENERIC);
			sessionReport.setName(shipDet.getStShipmentSet().getComments());
			sessionReport.setCsvReportGenericBean(csvReportGenericBean);

			RedirectServletReport.export(sessionReport);
		}
	}
	
	
	private void showWindow() {
		final Window win = (Window) Executions.createComponents(
    			"/shipment_management/window_cornell_report.zul", null, null);
    		win.doModal();
	}
	
	/**
	 * TODO Change columns form DART TO CORNELL
	 * @param 
	 * @return
	 */
	public CSVReportGenericBean generateCsvCornellFile(Shipment ship) {
		CSVReportGenericBean bean = new CSVReportGenericBean();
		List<StringBuilder> listHeaders = new ArrayList<StringBuilder>();
		listHeaders.add(new StringBuilder("Project Details"));
		listHeaders.add(new StringBuilder(""));
		listHeaders.add(new StringBuilder("Sample Details"));
		listHeaders.add(new StringBuilder(""));
		listHeaders.add(new StringBuilder(""));
		listHeaders.add(new StringBuilder(""));
		listHeaders.add(new StringBuilder(""));
		listHeaders.add(new StringBuilder(""));
		listHeaders.add(new StringBuilder(""));
		listHeaders.add(new StringBuilder(""));
		listHeaders.add(new StringBuilder(""));
		listHeaders.add(new StringBuilder(""));
		listHeaders.add(new StringBuilder("Organism Details"));
		listHeaders.add(new StringBuilder(""));
		listHeaders.add(new StringBuilder(""));
		listHeaders.add(new StringBuilder(""));
		listHeaders.add(new StringBuilder(""));
		listHeaders.add(new StringBuilder(""));
		listHeaders.add(new StringBuilder(""));
		listHeaders.add(new StringBuilder(""));
		listHeaders.add(new StringBuilder(""));
		listHeaders.add(new StringBuilder("Origin Details"));
		listHeaders.add(new StringBuilder(""));
		listHeaders.add(new StringBuilder(""));
		listHeaders.add(new StringBuilder(""));
		listHeaders.add(new StringBuilder(""));
		listHeaders.add(new StringBuilder(""));
		listHeaders.add(new StringBuilder(""));
		bean.setListHeaders(listHeaders);
		beanFileCornel = (desktop.getAttribute(ATTRIBUTE_CORNELL_ITEM) != null ?
				(FileCornellBean) desktop.getAttribute(ATTRIBUTE_CORNELL_ITEM) : new FileCornellBean());
		List <RowCSV> listRowCSV = new ArrayList<RowCSV>();
		listRowCSV.add(getHeader2());
		ShipmentSet set = ship.getStShipmentSet();
		for (ShipmentDetail details : getServiceShipmentDetail().getShipmentDetails(set)){
			RowCSV rowCSV = new RowCSV();
			List<StringBuilder> listRow = new ArrayList<StringBuilder>();
			SampleDetail sampleDet = details.getStSampleDetail();
			if (sampleDet != null) {
				listRow.add(new StringBuilder(sampleDet.getLabstudyid().getProject().getProjectname()+
						sampleDet.getLabstudyid().getProject().getPurposename()));
				listRow.add(new StringBuilder(beanFileCornel.getSourceLab()));
				listRow.add(new StringBuilder(sampleDet.getPlatename()));
				listRow.add(new StringBuilder(sampleDet.getPlateloc()));
				if (sampleDet.getSamplegid() != null){
					listRow.add(new StringBuilder(sampleDet.getLabstudyid().getPrefix()+
							(sampleDet.getLabstudyid().isUsePadded() ? StrUtils.getPaddingCeros(sampleDet.getSamplegid()) :
								String.valueOf(sampleDet.getSamplegid()))));
					
					listRow.add(new StringBuilder(sampleDet.getNval()));
					listRow.add(new StringBuilder());
					listRow.add(new StringBuilder());
					listRow.add(new StringBuilder(beanFileCornel.getSampleDNA()));
					listRow.add(new StringBuilder(beanFileCornel.getSampleVol()));
					listRow.add(new StringBuilder(beanFileCornel.getSampleMass()));
					listRow.add(new StringBuilder(beanFileCornel.getPreparer()));
					listRow.add(new StringBuilder(beanFileCornel.getKingdom()));
					listRow.add(new StringBuilder());
					listRow.add(new StringBuilder());
					listRow.add(new StringBuilder());
					listRow.add(new StringBuilder());
					listRow.add(new StringBuilder(beanFileCornel.getGenus()));
					if (sampleDet.getSpecie() != null && !sampleDet.getSpecie().equals(""))
					listRow.add(new StringBuilder(sampleDet.getSpecie()));
					else
						listRow.add(new StringBuilder(""));
					listRow.addAll(getListRowEmpty(9));
				}else {
					listRow.add(new StringBuilder("blank"));
					listRow.addAll(getListRowEmpty(23));
				}
			}
			rowCSV.setListRow(listRow);
			listRowCSV.add(rowCSV);
		}
		bean.setListRowCSV(listRowCSV);
		return bean;
	}

	private List<StringBuilder> getListRowEmpty(int size){
		List<StringBuilder> listEmty = new ArrayList<StringBuilder>();
		for (int index = 0; index < size; index++){
			listEmty.add(new StringBuilder());
		}
		return listEmty;
	}
	private RowCSV getHeader2(){
		RowCSV rowCSVH2 = new RowCSV();
		List<StringBuilder> listRow = new ArrayList<StringBuilder>();
		listRow.add(new StringBuilder("Project Name"));
		listRow.add(new StringBuilder("Source Lab"));
		listRow.add(new StringBuilder("Plate Name"));
		listRow.add(new StringBuilder("Well"));
		listRow.add(new StringBuilder("Sample Name"));
		listRow.add(new StringBuilder("Pedigree"));
		listRow.add(new StringBuilder("Population"));
		listRow.add(new StringBuilder("Stock Number"));
		listRow.add(new StringBuilder("Sample DNA Concentration"));
		listRow.add(new StringBuilder("Sample Volume"));
		listRow.add(new StringBuilder("Sample DNA mass"));
		listRow.add(new StringBuilder("Preparer"));
		listRow.add(new StringBuilder("Kingdom"));
		listRow.add(new StringBuilder("Phylum"));
		listRow.add(new StringBuilder("Class"));
		listRow.add(new StringBuilder("Order"));
		listRow.add(new StringBuilder("Family"));
		listRow.add(new StringBuilder("Genus"));
		listRow.add(new StringBuilder("Species"));
		listRow.add(new StringBuilder("Subspecies"));
		listRow.add(new StringBuilder("Variety"));
		listRow.add(new StringBuilder("Location Name"));
		listRow.add(new StringBuilder("Country"));
		listRow.add(new StringBuilder("State or Province"));
		listRow.add(new StringBuilder("City"));
		listRow.add(new StringBuilder("Elevation"));
		listRow.add(new StringBuilder("Latitude"));
		listRow.add(new StringBuilder("Longitude"));
		rowCSVH2.setListRow(listRow);
		return rowCSVH2;
	}
}
