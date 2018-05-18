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

import static com.cimmyt.utils.Constants.LBL_GENERIC_EXPORT;
import static com.cimmyt.utils.Constants.LBL_GENERIC_PLATE;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.cimmyt.bean.CSVReportGenericBean;
import com.cimmyt.bean.ResultDataExportDataBean;
import com.cimmyt.bean.RowCSV;
import com.cimmyt.bean.RowResultDataBean;
import com.cimmyt.bean.StudyLabReportBean;
import com.cimmyt.model.bean.ResultsPreferencesDetail;
import com.cimmyt.model.bean.SampleDetResult;
import com.cimmyt.model.bean.SampleDetail;
import com.cimmyt.model.bean.StudyTemplateParams;
import com.cimmyt.reports.ServiceReportLaboratory;
import com.cimmyt.service.SeriviceStudyTemplate;
import com.cimmyt.utils.ConstantsDNA;
import com.cimmyt.utils.PropertyHelper;
import com.cimmyt.utils.StrUtils;
import com.cimmyt.zk.studies.ControlWindowSelectField;

public class ServiceReportLaboratoryImpl implements ServiceReportLaboratory{

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
	private List<Object> listFieldsObjets;
	private List<StudyTemplateParams> listStudyTemParams;
	private SeriviceStudyTemplate seriviceStudyTemplate;
	private boolean isPrefix = false;
	
	@Override
	public byte[] getBytesReportLaboratoryPlate(StudyLabReportBean beanReport,List<Object> _listFieldsObjets, 
			List<StudyTemplateParams> _listStudyTemParams, boolean isprefix) {
		this.listFieldsObjets = _listFieldsObjets;
		this.listStudyTemParams = _listStudyTemParams;
		isPrefix = isprefix;
		HSSFWorkbook objWB = createBookPlateExcel(beanReport);
		return getArryByte(objWB);
	}

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

	private HSSFWorkbook createBookPlateExcel(StudyLabReportBean beanReport){
		HSSFWorkbook book = new HSSFWorkbook();
		// Create a new Sheet in book
		HSSFSheet sheet = book.createSheet();
		// Creates a new row for headers
		HSSFRow row = sheet.createRow(0);
		// Creates a new cell for title
		HSSFCell cell = row.createCell(0);
		// Create cell contents.
		HSSFRichTextString text = new HSSFRichTextString("");
		cell.setCellValue(text);
		// Creates a new
		HSSFRow rowHeaders = sheet.createRow(2);
		styleCellNormallyHeader = styleCellNormally(book, true);
		styleCellNormally = styleCellNormally(book, false);
		styleCellControl = getStyleCeldSolidForeground(book,cellControl );
		styleCellControlDART = getStyleCeldSolidForeground(book,cellControlDART );
		styleCellControlRandom = getStyleCeldSolidForeground(book,cellControlRandom );
		styleCellControlKBIo = getStyleCeldSolidForeground(book,cellKBiocontrolRandom );
		styleCellBlank = getStyleCeldSolidForeground(book,cellBlankForegroundColor );
		if (beanReport.getMapPlateSamples().size() > 0){
			Iterator iteratorMapFirst = beanReport.getMapPlateSamples().entrySet().iterator();
			int rowCounter = 1;
			while (iteratorMapFirst.hasNext()){
				Map.Entry entry = (Map.Entry)iteratorMapFirst.next();
				Map<String , SampleDetail> mapInner = (Map<String , SampleDetail>)entry.getValue();
				Integer key = (Integer)entry.getKey();
				sheet.createRow(rowCounter);
				rowCounter++;
				rowCounter = createHeaderPlate(sheet, rowCounter, beanReport.getNumberColumn(), styleCellNormallyHeader, beanReport.getPatternPlate()+key.toString());
				int rowCounterLabel = 0;
				
				for (int sizeRow  =0 ; sizeRow < beanReport.getNameRow().length; sizeRow++){
					HSSFRow rowData = sheet.createRow(rowCounter);
					
					for (int sizeColumn = 0 ; sizeColumn <= beanReport.getNumberColumn() ; sizeColumn ++){
						if (sizeColumn == 0){
							writeCell(rowData, sizeColumn, beanReport.getNameRow()[rowCounterLabel], styleCellNormallyHeader);
							rowCounterLabel++;		
						}else{
					      	SampleDetail detail = mapInner.get(beanReport.getPatternPlate()+key.toString()+beanReport.getNameRow()[sizeRow]+ (sizeColumn));
					      	if (detail != null){
					      		HSSFCellStyle style = null;
					      		String sampleName = ""; 
					      		if (detail.getControltype() != null && !detail.getControltype().equals(""))
					      			style = validateStatusSample(detail.getControltype());
					      		else 
					      			sampleName = getFieldsReport(beanReport, detail);
					      			String strDetail = getTemplateFiled(detail); 
					      			if(strDetail != null && !strDetail.isEmpty()){
					      				if (!sampleName.isEmpty()){
					      					sampleName = sampleName + "\n"+strDetail;
					      				}else {
					      					sampleName = sampleName + strDetail;		
					      				}
					      			}
					      		    
					      		writeCell(rowData, sizeColumn, sampleName, style);
					      	}
						}
					}
					rowCounter++;
				}
			}
			return book;
		}
		return null;
	}

	private String getTemplateFiled(SampleDetail detail){
		if(listStudyTemParams != null && !listStudyTemParams.isEmpty()){
	     StringBuilder fields = new StringBuilder();		
			for (StudyTemplateParams studyTemplateParams: listStudyTemParams){
				SampleDetResult sampleDetResult = seriviceStudyTemplate.getSampleDetResultByGIDAndIDTemplate(detail.getStudysampleid(), studyTemplateParams.getTemplateparamid());
				if (sampleDetResult != null && sampleDetResult.getResult()!= null && !sampleDetResult.getResult().isEmpty()){
					if (isPrefix)
					fields.append(studyTemplateParams.getFactorname() + " : ");
					fields.append(sampleDetResult.getResult());
					fields.append(" ");
					fields.append("\n");
				}
			}
			return fields.toString();
		}
		return "";
	}

	private String getFieldsReport(StudyLabReportBean beanReport, SampleDetail detail){
		if (listFieldsObjets != null && !listFieldsObjets.isEmpty()){
			StringBuilder fieldString = new StringBuilder();
			for (Object obj : listFieldsObjets ){
				fieldString.append(getReturnCharacter(fieldString));
				ControlWindowSelectField.Fields fields = (ControlWindowSelectField.Fields)obj;
				switch (fields){
				case SAMPLEID :
					fieldString.append(beanReport.getPrefix().toUpperCase()+ 
							(beanReport.isPadded() ? StrUtils.getPaddingCeros(detail.getSamplegid()): Integer.toString(detail.getSamplegid())));
					break;
				case GID :
					fieldString.append(isPrefix ?"GID : "+""+ detail.getBreedergid() : "" + detail.getBreedergid());
					break;
				case ACC:
					fieldString.append(isPrefix ? "Pedigree: "+  ""+ detail.getNval() : ""+ detail.getNval());
					break;
				case ENTRY_NUMBER:
				    int entryN = 1;
				    if (detail != null && detail.getEntryNo() != null){
				    	entryN = detail.getEntryNo();
				    }
					fieldString.append(isPrefix ? "Entry number : "+""+ entryN : ""+ entryN);
					break;
				case PLANT_NUMBER:
					int plantN = 1;
					if (detail != null && detail.getNplanta() != null ){
						plantN = detail.getNplanta();
					}
					fieldString.append(isPrefix ? "Plant Number : "+""+ plantN : ""+ plantN);
					break;
				case COMMENTS:
					if (detail != null && detail.getPriority() != null  && !detail.getPriority().trim().equals(""))
					fieldString.append(isPrefix ? "Comments : "+"" + detail.getPriority().trim() : "" + detail.getPriority().trim());
					break;
				default:
					break;
				}
			}
			return fieldString.toString();
		}
		return "";
	}

	private String getReturnCharacter(StringBuilder fieldString){
		if (!fieldString.toString().trim().equals("")){
			return  " " + "\n" ;
		}else return "";
	}
	private void writeCell(HSSFRow rowData, int sizeColumn, String strValue, HSSFCellStyle style){
		HSSFCell dataCell = rowData.createCell(sizeColumn);
      	HSSFRichTextString cellValue = new HSSFRichTextString(strValue);
      	if (style != null)
      		dataCell.setCellStyle(style);
		dataCell.setCellType(HSSFCell.CELL_TYPE_STRING);
		dataCell.setCellValue(cellValue);
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
		}
		return style;
	}

	private int createHeaderPlate (HSSFSheet sheet, int rowCounter, int numberColumn, HSSFCellStyle styleCellNormallyHeader, String plateName){
		HSSFRow rowData = sheet.createRow(rowCounter);
		loadDataCell(rowData,plateName, 0, styleCellNormallyHeader);
		for (int index = 1; index<= numberColumn; index++){
			loadDataCell(rowData,String.valueOf(index), index, styleCellNormallyHeader);
		}
		
		return ++rowCounter;
	}

	private void loadDataCell (HSSFRow rowData, String value, int index, HSSFCellStyle styleCellNormallyHeader){
		HSSFCell dataCell = rowData.createCell(index);
      	HSSFRichTextString cellValue = new HSSFRichTextString(value);
      	dataCell.setCellStyle(styleCellNormallyHeader);
		dataCell.setCellType(HSSFCell.CELL_TYPE_STRING);
		dataCell.setCellValue(cellValue);
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

	public byte [] getReportResultData (ResultDataExportDataBean resultDataExport, PropertyHelper pro
			, SortedMap<Integer, ResultsPreferencesDetail> sortedMap){
		
		HSSFWorkbook book = getBookResultData(resultDataExport, pro, sortedMap);
		return getArryByte(book);
	}

	private HSSFWorkbook getBookResultData(ResultDataExportDataBean resultDataExport, PropertyHelper pro
			, SortedMap<Integer, ResultsPreferencesDetail> sortedMap){
		HSSFWorkbook book = new HSSFWorkbook();
		HSSFSheet sheet = book.createSheet();
		HSSFRow filaDatGral;
		HSSFCell cellDatGral;
		HSSFRichTextString textDatGral;
		
		filaDatGral = sheet.createRow(0);
		cellDatGral = filaDatGral.createCell(0);
		textDatGral = new HSSFRichTextString(pro.getKey(LBL_GENERIC_PLATE));
		cellDatGral.setCellValue(textDatGral);
		cellDatGral = filaDatGral.createCell(1);
		textDatGral = new HSSFRichTextString(resultDataExport.getListPlate());
		cellDatGral.setCellValue(textDatGral);

		filaDatGral = sheet.createRow(1);
		cellDatGral = filaDatGral.createCell(0);
		textDatGral = new HSSFRichTextString(pro.getKey(LBL_GENERIC_EXPORT));
		cellDatGral.setCellValue(textDatGral);
		cellDatGral = filaDatGral.createCell(1);
		textDatGral = new HSSFRichTextString(resultDataExport.getNameExport());
		cellDatGral.setCellValue(textDatGral);
		filaDatGral = sheet.createRow(2);
		cellDatGral = filaDatGral.createCell(0);
		textDatGral = new HSSFRichTextString("Date");
		cellDatGral.setCellValue(textDatGral);
		cellDatGral = filaDatGral.createCell(1);
		textDatGral = new HSSFRichTextString(resultDataExport.getDateExport());
		cellDatGral.setCellValue(textDatGral);
		HSSFRow filaEncabezos = sheet.createRow(6);
		int colCounter = 0;
		for (ResultsPreferencesDetail resultsPreferencesDetail : sortedMap.values()) {
			HSSFCell headerCell = filaEncabezos.createCell(colCounter);
			HSSFRichTextString headerText = new HSSFRichTextString(resultsPreferencesDetail.getHeader());
			headerCell.setCellValue(headerText);
			colCounter++;
		}
		int rowCounter = 7;
		colCounter = 0;
		for (RowResultDataBean bean : resultDataExport.getListResults()){
			HSSFRow rowData = sheet.createRow(rowCounter);
			colCounter = 0;
			for (String str : bean.getListCell()){
				
				if (str != null) {
					HSSFCell dataCell = rowData.createCell(colCounter);
					HSSFRichTextString cellValue = new HSSFRichTextString(
							str);
					dataCell.setCellValue(cellValue);
				}
				colCounter++;
			}
			rowCounter++;
		}
		return book;
	}

	public CSVReportGenericBean getReportCSVReuslData (ResultDataExportDataBean resultDataExport, PropertyHelper pro
			, SortedMap<Integer, ResultsPreferencesDetail> sortedMap){

		CSVReportGenericBean bean = new CSVReportGenericBean();
		
		 List<StringBuilder> listHeaders = new ArrayList<StringBuilder>();
		for (ResultsPreferencesDetail resultsPreferencesDetail : sortedMap.values()) {
			listHeaders.add(new StringBuilder(resultsPreferencesDetail.getHeader()));
		
		}
		bean.setListHeaders(listHeaders);
		List <RowCSV> listRowCSV = new ArrayList<RowCSV>();
		for (RowResultDataBean beanR : resultDataExport.getListResults()){
			RowCSV rowCSV = new RowCSV();
			List<StringBuilder> listRow = new ArrayList<StringBuilder>();
		
			for (String str : beanR.getListCell()){
				
				if (str != null) {
					listRow.add(new StringBuilder(str));
					
				}else {
					listRow.add(new StringBuilder(""));
				}
		
			}
			rowCSV.setListRow(listRow);
			listRowCSV.add(rowCSV);
		
		}
		bean.setListRowCSV(listRowCSV);
		return bean;
	}

	public void setSeriviceStudyTemplate(SeriviceStudyTemplate seriviceStudyTemplate) {
		this.seriviceStudyTemplate = seriviceStudyTemplate;
	}

	
}
