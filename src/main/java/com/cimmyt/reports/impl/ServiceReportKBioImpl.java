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
package com.cimmyt.reports.impl;

import static com.cimmyt.utils.Constants.NAME_COMPANY_SEQUENCE;
import static com.cimmyt.utils.Constants.REPORT_PATH_KBIO;
import static com.cimmyt.utils.Constants.REPORT_PATH_KBIO_G384;
import static com.cimmyt.utils.Constants.REPORT_PATH_KBIO_G96;
import static com.cimmyt.utils.ConstantsDNA.SIZE_PLATE_384;
import static com.cimmyt.utils.ConstantsDNA.SIZE_PLATE_96;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.cimmyt.bean.UserBean;
import com.cimmyt.model.bean.SampleDetail;
import com.cimmyt.model.bean.Shipment;
import com.cimmyt.model.bean.ShipmentDetail;
import com.cimmyt.reports.ServiceReportKBio;
import com.cimmyt.utils.Constants;
import com.cimmyt.utils.ConstantsDNA;
import com.cimmyt.utils.PropertyHelper;
import com.cimmyt.utils.PropertyHelper.Bundle;
import com.cimmyt.utils.StrUtils;
/**
 * Class to management reports of KBIO
 * @author CIMMYT
 *
 */
public class ServiceReportKBioImpl implements ServiceReportKBio{

	
	private HSSFWorkbook listBook = null;
	private PropertyHelper property;
	private List<ShipmentDetail> setShipmentDetail;
	private Shipment shipment;
	private UserBean userBean;
	private Logger logger= Logger.getLogger(ServiceReportKBioImpl.class);
	private int rowsplate;
	private int colsplate;
	private short cellBlankForegroundColor = 48;
	private short cellKBiocontrolRandom = 52;
	private short cellControlRandom = 10;
	private short cellControlDART = 23;
	private short cellControl = 17;
	private	HSSFCellStyle styleCellNormally;
	private HSSFCellStyle styleCellControl;
	private HSSFCellStyle styleCellControlDART; 
	private HSSFCellStyle styleCellControlRandom; 
	private HSSFCellStyle styleCellControlKBIo;
	private HSSFCellStyle styleCellBlank;
	private HSSFCellStyle styleCellNormallyHeader;
	private TreeMap<Integer, String> mapPlate = new TreeMap<Integer, String>();
	
	private Map<Integer, String> mapBlank = new HashMap<Integer, String>();
	
	/**
	 * Method that build the report list to KBio
	 * @param List<ShipmentDetail> setShipmentDetail
	 * @param PropertyHelper property
	 * @param UserBean userBean
	 */
	@Override
	public byte[] getBytesReportKBiosList(List<ShipmentDetail> setShipmentDetail, PropertyHelper property, UserBean userBean
			, Shipment shipment){
	
		this.property =  property;
		this.setShipmentDetail = setShipmentDetail;
		this.userBean = userBean;
		this.shipment = shipment;
		listBook = null;
		loadExcelList();
		return getArryByte(listBook);
	}

	public byte[] getBytesReportKBiosGrid(List<ShipmentDetail> setShipmentDetail, PropertyHelper property, UserBean userBean
			, Shipment shipment){
		this.property =  property;
		this.setShipmentDetail = setShipmentDetail;
		this.userBean = userBean;
		this.shipment = shipment;
		listBook = null;
		loadListGrid();
		
		return getArryByte(listBook);
	}

	private void loadStyleCells(){
		styleCellNormally = styleCellNormally(listBook, false);
		styleCellControl = getStyleCeldSolidForeground(listBook,cellControl );
		styleCellControlDART = getStyleCeldSolidForeground(listBook,cellControlDART );
		styleCellControlRandom = getStyleCeldSolidForeground(listBook,cellControlRandom );
		styleCellControlKBIo = getStyleCeldSolidForeground(listBook,cellKBiocontrolRandom );
		styleCellBlank = getStyleCeldSolidForeground(listBook,cellBlankForegroundColor );
	}
	/**
	 * Method that return a HSSFWorkbook to byte []
	 * @param objWB
	 * @return
	 */
	private byte[] getArryByte (HSSFWorkbook objWB){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try{
			objWB.write(baos);
			baos.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		InputStream is = bais;	
		byte[] b = null;
		try {
			b = this.inputStreamToBytes(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}
	/**
	 * Method that return a byte []
	 * @param in
	 * @return
	 * @throws IOException
	 */
	private byte[] inputStreamToBytes(InputStream in) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
		byte[] buffer = new byte[1024];
		int len;
		while((len = in.read(buffer)) >= 0)
		out.write(buffer, 0, len);
		in.close();
		out.close();
		return out.toByteArray();
	}

	/**
	 * Method that load the sample detail into the HSSFWorkbook
	 */
	private void loadExcelList(){
		String rutaResumen= property.getKey(REPORT_PATH_KBIO, Bundle.conf);
		InputStream inputStream =getClass().getResourceAsStream(rutaResumen);
		try {
			listBook = new HSSFWorkbook(inputStream);
			HSSFSheet sheetDetails = listBook.getSheetAt(0);
			loadDetailSheet(sheetDetails);
			HSSFSheet sheetList = listBook.getSheetAt(1);
			HSSFSheet actsheetUsefull = listBook.getSheetAt(2);
			int inidatsheetList=2;
			int inidatsheetUsefull=1;
			String wellkbio;
			String platenameact="";
			Integer platevoy=0;
			Integer rowusefull=0;
			int mod = setShipmentDetail.iterator().next().getStSampleDetail().getLabstudyid().getPlatesize();
			int plates = setShipmentDetail.size() / mod;
			//Crear hojas usefull que necesitare dependiendo del numero de platos
			for(int j = 1; j < plates; j++){
				listBook.cloneSheet(2);
			}
			int i=0;
			for(ShipmentDetail shipmentDetail : setShipmentDetail){ 
					SampleDetail sampledet = shipmentDetail.getStSampleDetail();
				HSSFRow actrowsheetList;
				//****************    sheetList  **************************
				//
				if (sheetList.getRow(i+inidatsheetList)==null)
					sheetList.createRow(i+inidatsheetList);
				actrowsheetList=sheetList.getRow(i+inidatsheetList);
				if (actrowsheetList.getCell(0)==null) actrowsheetList.createCell(0);
				if(sampledet.getSamplegid()!=null)
				actrowsheetList.getCell(0).setCellValue(sampledet.getLabstudyid().getPrefix()+
						(sampledet.getLabstudyid().isUsePadded() ? StrUtils.getPaddingCeros(sampledet.getSamplegid()) :
							String.valueOf(sampledet.getSamplegid())));
				if (actrowsheetList.getCell(1)==null) actrowsheetList.createCell(1);
				actrowsheetList.getCell(1).setCellValue(sampledet.getPlatename());
				if (actrowsheetList.getCell(2)==null) actrowsheetList.createCell(2);
				wellkbio=sampledet.getPlateloc();
				if (wellkbio.length()==2)
					wellkbio=wellkbio.substring(0,1)+"0"+wellkbio.substring(1);
				actrowsheetList.getCell(2).setCellValue(wellkbio);
				if (actrowsheetList.getCell(3)==null) actrowsheetList.createCell(3);
				actrowsheetList.getCell(3).setCellValue("");
				//Por especificaciones de KBIo se pone C si es un control y nada si es ADN normal
				if(sampledet.getControltype()!=null)
					if (sampledet.getControltype().equals("K")||sampledet.getControltype().equals("B"))
							actrowsheetList.getCell(3).setCellValue("C");
				
				if (sampledet.getSamplegid()==null)
					actrowsheetList.getCell(3).setCellValue("C");

				if (actrowsheetList.getCell(4)==null) actrowsheetList.createCell(4);
				actrowsheetList.getCell(4).setCellValue(sampledet.getLabstudyid().getPlatesize());
				
				// ********************   sheetUsefull  ***********************

				if (!platenameact.equals(sampledet.getPlatename())){
					platenameact=sampledet.getPlatename();
					platevoy=platevoy+1;
					actsheetUsefull=listBook.getSheetAt(platevoy+1);
					rowusefull=inidatsheetUsefull;
				}
				HSSFRow actrowsheetUsefull;
				if (actsheetUsefull.getRow(rowusefull)==null)
					actsheetUsefull.createRow(rowusefull);
				actrowsheetUsefull=actsheetUsefull.getRow(rowusefull);

				if (actrowsheetUsefull.getCell(1)==null) actrowsheetUsefull.createCell(1);
				actrowsheetUsefull.getCell(1).setCellValue(wellkbio);

				if (actrowsheetUsefull.getCell(2)==null) actrowsheetUsefull.createCell(2);
				actrowsheetUsefull.getCell(2).setCellValue(i+1);
				rowusefull=rowusefull+1;
				i=i+1;
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	private void loadListGrid(){
		
		String rutaResumen;
		int size = setShipmentDetail.iterator().next().getStSampleDetail().getLabstudyid().getPlatesize();
		int plates = setShipmentDetail.size() / size;
		
		if (size == SIZE_PLATE_96){
			
			//PlateContentList.letters=PlateContentList.letters96;
			rutaResumen= property.getKey(REPORT_PATH_KBIO_G96, Bundle.conf);
			rowsplate=8;
			colsplate=12;
		}
		else{
			
			rowsplate=16;
			colsplate=24;
			rutaResumen= property.getKey(REPORT_PATH_KBIO_G384, Bundle.conf);
			//PlateContentList.letters=PlateContentList.letters384;
		}
		
		
		
		InputStream inputStream = null;
	
		String platenameac="";
		int rowplatename;
		
		Integer rowItemNo ;
		Integer rowinidatsamples=4;
		Integer actualrow=0;
		Integer actualcol=1;
		Integer cuanhojasvoy=1;
		
				
		try {
									
			inputStream =getClass().getResourceAsStream(rutaResumen);
			listBook = new HSSFWorkbook(inputStream);
			HSSFSheet sheetDetails = listBook.getSheetAt(0);
			loadDetailSheet(sheetDetails);
			
			//Crear las hojas que voy a necesitar dependiendo el numero de platos
			Integer cuanhojasnecesito=1;
			if (size == SIZE_PLATE_96){
				cuanhojasnecesito=plates/45;
				if(plates%45!=0)
					cuanhojasnecesito=cuanhojasnecesito+1;
			}
			
			if (size == SIZE_PLATE_384){
				cuanhojasnecesito=plates/26;
				if(plates%26!=0)
					cuanhojasnecesito=cuanhojasnecesito+1;
			}
			
			for (int i=1;i<cuanhojasnecesito;i++){
				listBook.cloneSheet(1);
			}
			
			HSSFSheet sheetGridActual;
			
			sheetGridActual=listBook.getSheetAt(cuanhojasvoy);
				
			int cuanplatosvoy=0;
			int i=0;
			
			for(ShipmentDetail shipmentDetail : setShipmentDetail){ 
				SampleDetail sampledetail = shipmentDetail.getStSampleDetail();
				HSSFRow actrowsheetGrid;
				
				if (sheetGridActual.getRow(i)==null)
					sheetGridActual.createRow(i);
				
				//poner nombre del plato
				if (!platenameac.equals(sampledetail.getPlatename())){
					platenameac=sampledetail.getPlatename();
					
					
					cuanplatosvoy=cuanplatosvoy+1;
					
					//si hay mas de 45 platos entonces hacer una nueva hoja 
					if(cuanplatosvoy%46==0 && size == SIZE_PLATE_96){
						cuanhojasvoy=cuanhojasvoy+1;
						actualrow=0;
						actualcol=1;
						cuanplatosvoy=1;
						sheetGridActual=listBook.getSheetAt(cuanhojasvoy);
					}
					
					//si hay mas de 26 platos entonces hacer una nueva hoja 
					if(cuanplatosvoy%26==0 && size == SIZE_PLATE_384){
						cuanhojasvoy=cuanhojasvoy+1;
						actualrow=0;
						actualcol=1;
						cuanplatosvoy=1;
						sheetGridActual=listBook.getSheetAt(cuanhojasvoy);
					}
					
					rowplatename=(cuanplatosvoy-1)*(rowsplate+2)+cuanplatosvoy;
					
					if (cuanplatosvoy>8){
						rowplatename=rowplatename+1;
					}
						
					
					//Indica que area se va a combinar (roiwini, rowfin, colini, colfin)
					
					if(sheetGridActual.getRow(rowplatename)==null)
						sheetGridActual.createRow(rowplatename);
					actrowsheetGrid=sheetGridActual.getRow(rowplatename);
					
					if (actrowsheetGrid.getCell(1)==null) actrowsheetGrid.createCell(1);
					actrowsheetGrid.getCell(1).setCellValue(platenameac);
					
					
					if (cuanplatosvoy==1)
						rowinidatsamples=2;
					else
						if (cuanplatosvoy==9 && size == SIZE_PLATE_96)
							rowinidatsamples=rowinidatsamples+rowsplate+4;
						else
							rowinidatsamples=rowinidatsamples+rowsplate+3;	
				}
				
				//sacar la siguiente posicion
				
				actualrow=actualrow+1;
				if (actualrow>rowsplate){
					actualcol=actualcol+1;
					actualrow=1;
				}
				
				if (actualcol>colsplate){
					actualrow=1;
					actualcol=1;
				}

				//PONER EL SAMPLEID
				rowItemNo = actualrow+rowinidatsamples;
				
				
				if(sheetGridActual.getRow(rowItemNo)==null)
					sheetGridActual.createRow(rowItemNo);
				actrowsheetGrid=sheetGridActual.getRow(rowItemNo);
				
				if (actrowsheetGrid.getCell(actualcol)==null) actrowsheetGrid.createCell(actualcol);
				
				//SI NO TIENE SAMPLEID COLOCAR "BLANK"
				if(sampledetail.getSamplegid()==null || (sampledetail.getControltype()!=null&&sampledetail.getControltype().equals("B")))
					actrowsheetGrid.getCell(actualcol).setCellValue("BLANK");
				else
					actrowsheetGrid.getCell(actualcol).setCellValue(sampledetail.getLabstudyid().getPrefix()+
							(sampledetail.getLabstudyid().isUsePadded() ? StrUtils.getPaddingCeros(sampledetail.getSamplegid()):
								String.valueOf(sampledetail.getSamplegid())));
				i=i+1;
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Method that set the first sheet of the information
	 * @param sheetDetails
	 */
	private void loadCustomerDetailSheet(HSSFSheet sheetDetails){
		sheetDetails.getRow(8).getCell(2).setCellValue(property.getKey(NAME_COMPANY_SEQUENCE, Bundle.conf));
		sheetDetails.getRow(8).getCell(4).setCellValue(shipment.getStShipmentSet().getStInvestigator().getInvest_name());
		sheetDetails.getRow(9).getCell(4).setCellValue(userBean.getResearcherEMail());
		sheetDetails.getRow(13).getCell(2).setCellValue(shipment.getStShipmentSet().getStInvestigator().getInvest_name());
		sheetDetails.getRow(14).getCell(2).setCellValue(userBean.getResearcherEMail());
		if (shipment.getTrackingNumberLocal() != null && !shipment.getTrackingNumberLocal().trim().equals(""))
		sheetDetails.getRow(35).getCell(2).setCellValue(shipment.getTrackingNumberLocal());
	}
	/**
	 * Method that set the first sheet of the information
	 * @param sheetDetails
	 */
	private void loadDetailSheet(HSSFSheet sheetDetails){
		sheetDetails.getRow(3).getCell(1).setCellValue(property.getKey(NAME_COMPANY_SEQUENCE, Bundle.conf));
		sheetDetails.getRow(4).getCell(1).setCellValue(shipment.getStShipmentSet().getStInvestigator().getInvest_name());
		//TODO
		//sheetDetails.getRow(5).getCell(1).setCellValue(shipment.getStShipmentSet().getStInvestigator().getInvest_email());
		//sheetDetails.getRow(6).getCell(1).setCellValue(shipment.getStShipmentSet().getStInvestigator().getInvest_phone());
		sheetDetails.getRow(7).getCell(1).setCellValue(shipment.getTrackingNumberDelivery());
		sheetDetails.getRow(8).getCell(1).setCellValue(shipment.getTrackingNumberLocal());
		sheetDetails.getRow(9).getCell(1).setCellValue(userBean.getCorp());
		sheetDetails.getRow(10).getCell(1).setCellValue("");
	}

	public byte [] getBytesReportGnotypingServices (	
			List<ShipmentDetail> setShipmentDetail, PropertyHelper property,
			UserBean userBean, Shipment shipment) {
		this.property =  property;
		this.setShipmentDetail = setShipmentDetail;
		this.userBean = userBean;
		this.shipment = shipment;
		this.mapPlate = new TreeMap<Integer, String>();
		this.mapBlank = new HashMap<Integer, String>();
		listBook = null;
		loadExcelListGenotypingService();
		return getArryByte(listBook);
		
		
	}
 	
	/**
	 * Method that load the sample detail into the HSSFWorkbook
	 */
	private void loadExcelListGenotypingService(){
		String rutaResumen= property.getKey(Constants.REPORT_PATH_GENOTYPING_SERVICES_96, Bundle.conf);
		InputStream inputStream =getClass().getResourceAsStream(rutaResumen);
		try {
			listBook = new HSSFWorkbook(inputStream);
			HSSFSheet sheetDetails = listBook.getSheetAt(1);
			loadCustomerDetailSheetGenotyping(sheetDetails);
			HSSFSheet sheetList = listBook.getSheetAt(4);
			loadListSampleIDGenotypingService(sheetList);
			loadPlateID(listBook.getSheetAt(3));
			loadStyleCells();
			loadListGenotypingSewrvices();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	private void loadListGenotypingSewrvices(){
		int size = setShipmentDetail.iterator().next().getStSampleDetail().getLabstudyid().getPlatesize();
		HSSFSheet sheetgrid = listBook.getSheetAt(5);
		HSSFCell cellStyle =sheetgrid.getRow(11).getCell(0);
		HSSFCellStyle style = cellStyle.getCellStyle();
		HSSFCell cellStyleIntertek =sheetgrid.getRow(19).getCell(11);
		HSSFCellStyle styleIntertek = cellStyleIntertek.getCellStyle();
		if (size == SIZE_PLATE_96){
			rowsplate=8;
			colsplate=12;
		}
		else{
			rowsplate=16;
			colsplate=24;
		}
		
		LinkedHashMap<String, Map<String, SampleDetail>> mapPlates = new LinkedHashMap<String, Map<String,SampleDetail>>();
		for(ShipmentDetail shipmentDetail : setShipmentDetail){ 
			
			SampleDetail detail = shipmentDetail.getStSampleDetail();
			if (mapPlates.containsKey(detail.getPlatename())){
				Map<String,SampleDetail> map = mapPlates.get(detail.getPlatename());
					String letter = detail.getPlateloc().substring(0, 1);
					String number = detail.getPlateloc().substring(1, detail.getPlateloc().length());
					map.put(getEquivalenceInInteger(letter)+"|" +number, detail);
			}else{
				Map<String, SampleDetail> map = new HashMap<String, SampleDetail>();
				String letter = detail.getPlateloc().substring(0, 1);
				String number = detail.getPlateloc().substring(1, detail.getPlateloc().length());
				map.put(getEquivalenceInInteger(letter)+"|" +number, detail);
				mapPlates.put(detail.getPlatename(), map);
			}
		}

		
		Iterator<Entry<String, Map<String, SampleDetail>>> it = mapPlates.entrySet().iterator();
		int row = 10;
		int column = 0 ;
		while (it.hasNext()){
			Map.Entry<String, Map<String,SampleDetail>> entry = (Entry<String, Map<String, SampleDetail>>) it.next();
			if (entry.getKey() != null){
				Map<String,SampleDetail> map = entry.getValue();
				validateRow(sheetgrid, row);
				HSSFCell cellPlateName =validateCell(sheetgrid, row, 0);
				cellPlateName.setCellValue("Plate");
				cellPlateName.setCellStyle(style);
				HSSFCell cellPlate =validateCell(sheetgrid, row, 1);
				cellPlate.setCellValue(entry.getKey());
				row = row + 1;
				drawingFormatPlate(style, sheetgrid, row);
					for (int indexRow = 1; indexRow <= rowsplate ; indexRow++){
						for (int indexCol = 1 ; indexCol<= colsplate; indexCol++){
							
							SampleDetail detail = map.get(indexRow+"|"+indexCol);
							validateRow(sheetgrid, row+indexRow);
							HSSFCellStyle styleCell = null;
							if (detail.getControltype() != null && !detail.getControltype().equals(""))
							styleCell = validateStatusSample(detail.getControltype());
							else {
								styleCell = styleCellNormally;
							}
							if (sheetgrid.getRow(row+indexRow).getCell(column+indexCol) == null)
								sheetgrid.getRow(row+indexRow).createCell(column+indexCol);
								HSSFCell cell = sheetgrid.getRow(row+indexRow).getCell(column+indexCol);
							if(detail.getSamplegid()==null || (detail.getControltype()!=null && !detail.getControltype().equals(""))){
								cell.setCellValue(mapBlank.get(detail.getStudysampleid()));
								cell.setCellStyle(styleCell);
							}
							else{
								styleCell = styleCellNormally;
								cell.setCellValue(detail.getLabstudyid().getPrefix()+
										(detail.getLabstudyid().isUsePadded() ? StrUtils.getPaddingCeros(detail.getSamplegid()):
											String.valueOf(detail.getSamplegid()))+"-"+detail.getStudysampleid());
							}
							detail.getPlateloc();
							if (detail.getPlateloc().equals("H11") || detail.getPlateloc().equals("H12")) {
								cell.setCellStyle(styleIntertek);
							}
						}
					}
					row = row +rowsplate;
					row = row+2;
				}
				
			}
	}
	
	
	
	private void loadPlateID (HSSFSheet sheetList) {
		if (mapPlate !=  null && !mapPlate.isEmpty()) {
			Iterator<Map.Entry<Integer,String>> it = mapPlate.entrySet().iterator();
			int inidatsheetList=12;
			int indexRow =  0;
			while (it.hasNext()) {
				HSSFRow actrowsheetList;
				if (sheetList.getRow(indexRow+inidatsheetList)==null)
					sheetList.createRow(indexRow+inidatsheetList);
				actrowsheetList=sheetList.getRow(indexRow+inidatsheetList);
				if (actrowsheetList.getCell(1)==null) 
					actrowsheetList.createCell(1);
				Map.Entry<Integer,String> pair = it.next();
				actrowsheetList.getCell(1).setCellValue(pair.getValue());
				indexRow ++;
			}
		}
	}
	
	private void loadListSampleIDGenotypingService(HSSFSheet sheetList){
		int inidatsheetList=15;
		String wellkbio;
		int i=0;
		int indexBlank = 1;
		for(ShipmentDetail shipmentDetail : setShipmentDetail){ 
				SampleDetail sampledet = shipmentDetail.getStSampleDetail();
			HSSFRow actrowsheetList;
			//****************    sheetList  **************************
			//
			if (sheetList.getRow(i+inidatsheetList)==null)
				sheetList.createRow(i+inidatsheetList);
			actrowsheetList=sheetList.getRow(i+inidatsheetList);
			if (actrowsheetList.getCell(1)==null) actrowsheetList.createCell(1);
			if(sampledet.getSamplegid()!=null)
			actrowsheetList.getCell(1).setCellValue(sampledet.getLabstudyid().getPrefix()+
					(sampledet.getLabstudyid().isUsePadded() ? StrUtils.getPaddingCeros(sampledet.getSamplegid()) :
						String.valueOf(sampledet.getSamplegid()))+ "-" + sampledet.getStudysampleid());
			if (actrowsheetList.getCell(2)==null) actrowsheetList.createCell(2);
			actrowsheetList.getCell(2).setCellValue(getPlate( sampledet.getPlatename(), sampledet.getLabstudyid().getPrefix()));
			if (actrowsheetList.getCell(3)==null) actrowsheetList.createCell(3);
			wellkbio=sampledet.getPlateloc();
			if (wellkbio.length()==2)
				wellkbio=wellkbio.substring(0,1)+"0"+wellkbio.substring(1);
			actrowsheetList.getCell(3).setCellValue(wellkbio);
			
			if (sampledet.getPlateloc().equals("H11") || sampledet.getPlateloc().equals("H12")) {
				actrowsheetList.getCell(1).setCellValue("");
			}else
				{
					//Por especificaciones de KBIo se pone C si es un control y nada si es ADN normal
					if(sampledet.getControltype()!=null) {
							if (!sampledet.getControltype().equals("")) {
									actrowsheetList.getCell(1).setCellValue(getPathEmpty(indexBlank, sampledet.getStudysampleid()));
									indexBlank++;
							}
					}else
						if (sampledet.getSamplegid()==null) {
							actrowsheetList.getCell(1).setCellValue(getPathEmpty(indexBlank, sampledet.getStudysampleid()));
							indexBlank++;
						}
					
					
				}
			if (actrowsheetList.getCell(7)==null) actrowsheetList.createCell(7);
			actrowsheetList.getCell(7).setCellValue(sampledet.getBreedergid() != null ? String.valueOf(sampledet.getBreedergid()):"");
			if (actrowsheetList.getCell(8)==null) actrowsheetList.createCell(8);
			actrowsheetList.getCell(8).setCellValue(sampledet.getNplanta() != null ? String.valueOf(sampledet.getNplanta()):"");
			i=i+1;
		}
	}

	private String getPathEmpty(int index, Integer idSample) {
		String strBlank = "ZZ-Blank-";
		String strIndex = String.valueOf(index);
		if (strIndex.length() == 1) {
			mapBlank.put(idSample, strBlank + "00"+ strIndex);
			return strBlank + "00"+ strIndex;
		}
		if (strIndex.length() == 2 ) {
			mapBlank.put(idSample, strBlank + "0"+ strIndex);
			return strBlank + "0"+ strIndex;
		}
		mapBlank.put(idSample, strBlank + strIndex);
		return strBlank+strIndex;
	}
	
	private String getPlate (String plate, String prefixPlate) {
		Integer id = Integer.parseInt(plate.substring(prefixPlate.length()+3, plate.length()));
		if (mapPlate.size() == 0) {
			mapPlate.put(id, plate);
			return plate;
		}
		if (!mapPlate.containsKey(id))
			mapPlate.put(id, plate);
			return plate;
	}
	
	
	/**
	 * Method that set the first sheet of the information
	 * @param sheetDetails
	 */
	private void loadCustomerDetailSheetGenotyping(HSSFSheet sheetDetails){
		sheetDetails.getRow(13).getCell(2).setCellValue(property.getKey(NAME_COMPANY_SEQUENCE, Bundle.conf));
		sheetDetails.getRow(13).getCell(4).setCellValue(shipment.getStShipmentSet().getStInvestigator().getInvest_name());
		sheetDetails.getRow(14).getCell(4).setCellValue(userBean.getResearcherEMail());
		sheetDetails.getRow(16).getCell(4).setCellValue(shipment.getComment());
		sheetDetails.getRow(18).getCell(2).setCellValue(userBean.getResearcherEMail()+";"+property.getKey(Constants.REPORT_PATH_GENOTYPING_SERVICES_EMAIL_INTERTEK, Bundle.conf));
		sheetDetails.getRow(20).getCell(2).setCellValue(userBean.getResearcherEMail()+";"+Constants.EMAIL_ACCOUNT_RECEIVER_MAIZE+property.getKey(Constants.REPORT_PATH_GENOTYPING_SERVICES_EMAIL_INTERTEK, Bundle.conf));
		sheetDetails.getRow(25).getCell(2).setCellValue(property.getKey(NAME_COMPANY_SEQUENCE, Bundle.conf));
		sheetDetails.getRow(25).getCell(4).setCellValue(shipment.getStShipmentSet().getStInvestigator().getInvest_name());
		sheetDetails.getRow(37).getCell(2).setCellValue(shipment.getTrackingNumberLocal());
		sheetDetails.getRow(41).getCell(2).setCellValue(StrUtils.getCrop(userBean.getTypeCorp(), property));
	}

	@Override
	public byte[] getBytesReportIntertekGrid(
			List<ShipmentDetail> setShipmentDetail, PropertyHelper property,
			UserBean userBean, Shipment shipment) {
		this.property =  property;
		this.setShipmentDetail = setShipmentDetail;
		this.userBean = userBean;
		this.shipment = shipment;
		listBook = null;
		loadExcelListIntertek();
		return getArryByte(listBook);
	}

	/**
	 * Method that load the sample detail into the HSSFWorkbook
	 */
	private void loadExcelListIntertek(){
		String rutaResumen= property.getKey(Constants.REPORT_PATH_INTERTEK_G96, Bundle.conf);
		InputStream inputStream =getClass().getResourceAsStream(rutaResumen);
		try {
			listBook = new HSSFWorkbook(inputStream);
			HSSFSheet sheetDetails = listBook.getSheetAt(0);
			loadCustomerDetailSheet(sheetDetails);
			HSSFSheet sheeorderForm = listBook.getSheetAt(1);
			loadOrderForm(sheeorderForm);
			HSSFSheet sheetList = listBook.getSheetAt(3);
			loadListSampleID(sheetList);
			loadStyleCells();
			loadListGridIntertek();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}


	private int getEquivalenceInInteger (String letter){
		
		switch (letter ){
		case "A" :
			return 1;
		case "B" :
			return 2;
		case "C" :
			return 3;
		case "D" :
			return 4;
		case "E" :
			return 5;
		case "F" :
			return 6;
		case "G" :
			return 7;
		case "H" :
			return 8;
		}
		return 0;
	}

	private void drawingFormatPlate (HSSFCellStyle style, HSSFSheet sheetgrid, int indexRow){
				validateRow(sheetgrid, indexRow);
				validateCell(sheetgrid, indexRow, 0).setCellStyle(style);
				//sheetgrid.getRow(indexRow).getCell(2).setCellStyle(style);
				
				
				loadStyleandText(validateCell(sheetgrid, indexRow, 1), style, 1);
				loadStyleandText(validateCell(sheetgrid, indexRow, 2), style, 2);
				loadStyleandText(validateCell(sheetgrid, indexRow, 3), style, 3);
				loadStyleandText(validateCell(sheetgrid, indexRow, 4), style, 4);
				loadStyleandText(validateCell(sheetgrid, indexRow, 5), style, 5);
				loadStyleandText(validateCell(sheetgrid, indexRow, 6), style, 6);
				loadStyleandText(validateCell(sheetgrid, indexRow, 7), style, 7);
				loadStyleandText(validateCell(sheetgrid, indexRow, 8), style, 8);
				loadStyleandText(validateCell(sheetgrid, indexRow, 9), style, 9);
				loadStyleandText(validateCell(sheetgrid, indexRow, 10), style, 10);
				loadStyleandText(validateCell(sheetgrid, indexRow, 11), style, 11);
				loadStyleandText(validateCell(sheetgrid, indexRow, 12), style, 12);
				for (int index =1 ; index <= 8 ; index ++){
					validateRow(sheetgrid, indexRow+index);
				}
				loadStyleandText(validateCell(sheetgrid,indexRow+1, 0), style, "A");
				loadStyleandText(validateCell(sheetgrid,indexRow+2, 0), style, "B");
				loadStyleandText(validateCell(sheetgrid,indexRow+3, 0), style, "C");
				loadStyleandText(validateCell(sheetgrid,indexRow+4, 0), style, "D");
				loadStyleandText(validateCell(sheetgrid,indexRow+5, 0), style, "E");
				loadStyleandText(validateCell(sheetgrid,indexRow+6, 0), style, "F");
				loadStyleandText(validateCell(sheetgrid,indexRow+7, 0), style, "G");
				loadStyleandText(validateCell(sheetgrid,indexRow+8, 0), style, "H");
			
			
		
	}

	private HSSFCell validateCell (HSSFSheet sheetgrid, int indexRow, int indexCell){
		if (sheetgrid.getRow(indexRow).getCell(indexCell) == null){
			sheetgrid.getRow(indexRow).createCell(indexCell);
			return sheetgrid.getRow(indexRow).getCell(indexCell);
		}else {
			return sheetgrid.getRow(indexRow).getCell(indexCell);
		}
	}
	private void loadStyleandText(HSSFCell cell,HSSFCellStyle style, String value ){
		cell.setCellStyle(style);
		cell.setCellValue(value);
	}
	private void loadStyleandText(HSSFCell cell,HSSFCellStyle style, int value ){
		cell.setCellStyle(style);
		cell.setCellValue(value);
	}

	private void validateRow(HSSFSheet sheetgrid, int indexRow){
		if(sheetgrid.getRow(indexRow)==null)
			sheetgrid.createRow(indexRow);
	}
	private void loadListGridIntertek(){
		int size = setShipmentDetail.iterator().next().getStSampleDetail().getLabstudyid().getPlatesize();
		HSSFSheet sheetgrid = listBook.getSheetAt(4);
		HSSFCell cellStyle =sheetgrid.getRow(5).getCell(2);
		HSSFCellStyle style = cellStyle.getCellStyle();
		if (size == SIZE_PLATE_96){
			rowsplate=8;
			colsplate=12;
		}
		else{
			rowsplate=16;
			colsplate=24;
		}
		
		LinkedHashMap<String, Map<String, SampleDetail>> mapPlates = new LinkedHashMap<String, Map<String,SampleDetail>>();
		for(ShipmentDetail shipmentDetail : setShipmentDetail){ 
			SampleDetail detail = shipmentDetail.getStSampleDetail();
			if (mapPlates.containsKey(detail.getPlatename())){
				Map<String,SampleDetail> map = mapPlates.get(detail.getPlatename());
					String letter = detail.getPlateloc().substring(0, 1);
					String number = detail.getPlateloc().substring(1, detail.getPlateloc().length());
					map.put(getEquivalenceInInteger(letter)+"|" +number, detail);
			}else{
				Map<String, SampleDetail> map = new HashMap<String, SampleDetail>();
				String letter = detail.getPlateloc().substring(0, 1);
				String number = detail.getPlateloc().substring(1, detail.getPlateloc().length());
				map.put(getEquivalenceInInteger(letter)+"|" +number, detail);
				mapPlates.put(detail.getPlatename(), map);
			}
		}

		Iterator<Entry<String, Map<String, SampleDetail>>> it = mapPlates.entrySet().iterator();
		int row = 4;
		int column = 2;
		while (it.hasNext()){
			Map.Entry<String, Map<String,SampleDetail>> entry = (Entry<String, Map<String, SampleDetail>>) it.next();
			if (entry.getKey() != null){
				
				Map<String,SampleDetail> map = entry.getValue();
				validateRow(sheetgrid, row);
				HSSFCell cellPlate =validateCell(sheetgrid, row, 1);
				cellPlate.setCellValue(entry.getKey());
				row = row + 1;
				drawingFormatPlate(style, sheetgrid, row);
					for (int indexRow = 1; indexRow <= rowsplate ; indexRow++){
						for (int indexCol = 1 ; indexCol<= colsplate; indexCol++){
							SampleDetail detail = map.get(indexRow+"|"+indexCol);
							validateRow(sheetgrid, row+indexRow);
							HSSFCellStyle styleCell = null;
							if (detail.getControltype() != null && !detail.getControltype().equals(""))
							styleCell = validateStatusSample(detail.getControltype());
							else {
								styleCell = styleCellNormally;
							}
							if (sheetgrid.getRow(row+indexRow).getCell(column+indexCol) == null)
								sheetgrid.getRow(row+indexRow).createCell(column+indexCol);
								HSSFCell cell = sheetgrid.getRow(row+indexRow).getCell(column+indexCol);
							if(detail.getSamplegid()==null || (detail.getControltype()!=null && !detail.getControltype().equals(""))){	
								cell.setCellValue("BLANK");
								cell.setCellStyle(styleCell);
							}
							else{
								styleCell = styleCellNormally;
								cell.setCellValue(detail.getLabstudyid().getPrefix()+
										(detail.getLabstudyid().isUsePadded() ? StrUtils.getPaddingCeros(detail.getSamplegid()):
											String.valueOf(detail.getSamplegid())));
							}
						}
					}
					row = row +rowsplate;
					row = row+2;
				}
				
			}
	}
	private void loadListSampleID(HSSFSheet sheetList){
		
		int inidatsheetList=2;
		String wellkbio;
		int mod = setShipmentDetail.iterator().next().getStSampleDetail().getLabstudyid().getPlatesize();
		int plates = setShipmentDetail.size() / mod;

		int i=0;
		for(ShipmentDetail shipmentDetail : setShipmentDetail){ 
				SampleDetail sampledet = shipmentDetail.getStSampleDetail();
			HSSFRow actrowsheetList;
			//****************    sheetList  **************************
			//
			if (sheetList.getRow(i+inidatsheetList)==null)
				sheetList.createRow(i+inidatsheetList);
			actrowsheetList=sheetList.getRow(i+inidatsheetList);
			if (actrowsheetList.getCell(0)==null) actrowsheetList.createCell(0);
			if(sampledet.getSamplegid()!=null)
			actrowsheetList.getCell(0).setCellValue(sampledet.getLabstudyid().getPrefix()+
					(sampledet.getLabstudyid().isUsePadded() ? StrUtils.getPaddingCeros(sampledet.getSamplegid()) :
						String.valueOf(sampledet.getSamplegid())));
			if (actrowsheetList.getCell(1)==null) actrowsheetList.createCell(1);
			actrowsheetList.getCell(1).setCellValue(sampledet.getPlatename());
			if (actrowsheetList.getCell(2)==null) actrowsheetList.createCell(2);
			wellkbio=sampledet.getPlateloc();
			if (wellkbio.length()==2)
				wellkbio=wellkbio.substring(0,1)+"0"+wellkbio.substring(1);
			actrowsheetList.getCell(2).setCellValue(wellkbio);
			if (actrowsheetList.getCell(3)==null) actrowsheetList.createCell(3);
			actrowsheetList.getCell(3).setCellValue("");
			//Por especificaciones de KBIo se pone C si es un control y nada si es ADN normal
			if(sampledet.getControltype()!=null)
				if (sampledet.getControltype().equals("K")||sampledet.getControltype().equals("B"))
						actrowsheetList.getCell(3).setCellValue("C");
			
			if (sampledet.getSamplegid()==null)
				actrowsheetList.getCell(3).setCellValue("C");

			if (actrowsheetList.getCell(4)==null) actrowsheetList.createCell(4);
			actrowsheetList.getCell(4).setCellValue(sampledet.getLabstudyid().getPlatesize());
			
			
			i=i+1;
		}
	}
	
	private void loadOrderForm(HSSFSheet sheet){
		sheet.getRow(3).getCell(4).setCellValue(property.getKey(NAME_COMPANY_SEQUENCE, Bundle.conf));
		sheet.getRow(6).getCell(4).setCellValue(userBean.getCorp());
		
	}

	/**
	 * Method to put style to header and normally cell
	 * @param objLibro
	 * @param isHeader 
	 * @return
	 */
	private HSSFCellStyle styleCellNormally(HSSFWorkbook objLibro, boolean isHeader){
		HSSFFont sourceStyle = objLibro.createFont();
		sourceStyle.setFontHeightInPoints((short)11);
		sourceStyle.setBoldweight((short)11);
		sourceStyle.setFontName(HSSFFont.FONT_ARIAL);
		if (isHeader){
			sourceStyle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		}
		HSSFCellStyle styleCell = objLibro.createCellStyle();
		styleCell.setWrapText(true);
		styleCell.setAlignment(HSSFCellStyle. ALIGN_JUSTIFY);
		
		styleCell.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
		styleCell.setFont(sourceStyle);
		return styleCell;
	}
	/**
	 * Method that pint cells in different style of foreground
	 * @param objBook
	 * @param foregroundColor
	 * @return
	 */
	private HSSFCellStyle getStyleCeldSolidForeground (HSSFWorkbook objBook, short foregroundColor){
		HSSFFont sourceStyle = objBook.createFont();
		sourceStyle.setFontHeightInPoints((short)11);
		sourceStyle.setFontName(HSSFFont.FONT_ARIAL);
		HSSFCellStyle stileCell = objBook.createCellStyle();
		stileCell.setWrapText(true);
		stileCell.setAlignment(HSSFCellStyle. ALIGN_JUSTIFY);
		stileCell.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
		stileCell.setFont(sourceStyle);
		stileCell.setFillForegroundColor(foregroundColor);
		stileCell.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		return stileCell;
	}

	private HSSFCellStyle validateStatusSample(String status){
		char c=status.toCharArray()[0];
		HSSFCellStyle style = styleCellNormally;
		switch (c){
		case ConstantsDNA.INDIVIDUAL_CONTROL :
			style = styleCellControl;
			break;
		case ConstantsDNA.RANDOM_CONTROL :
			style = styleCellControlRandom;	
			break;
		case ConstantsDNA.DART_CONTROL :
			style = styleCellControlDART;
			break;
		case ConstantsDNA.KBIOSCIENCIES_CONTROL :
			style = styleCellControlKBIo; 
			break;
		case ConstantsDNA.NOT_CONTROL :
			style = styleCellNormally;
			break;
		case ConstantsDNA.BANK_CONTROL:
			style = styleCellBlank;
			break;
			default  :
				style = styleCellNormally;
			break;
		}
		return style;
	}
}
