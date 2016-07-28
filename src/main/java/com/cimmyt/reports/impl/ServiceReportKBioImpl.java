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
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.cimmyt.bean.UserBean;
import com.cimmyt.model.bean.SampleDetail;
import com.cimmyt.model.bean.Shipment;
import com.cimmyt.model.bean.ShipmentDetail;
import com.cimmyt.reports.ServiceReportKBio;
import com.cimmyt.utils.PropertyHelper;
import com.cimmyt.utils.PropertyHelper.Bundle;
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
						String.valueOf(sampledet.getSamplegid()));
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
							String.valueOf(sampledetail.getSamplegid()));
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
}
