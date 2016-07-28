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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.cimmyt.dnast.dto.DsSearchParam;
import org.springframework.beans.BeanUtils;

import com.cimmyt.bean.StudyLabReportBean;
import com.cimmyt.reports.ServiceReportCustomQuery;
import com.cimmyt.reports.bean.CustomQueryReport;

public class ServiceReportCustomQueryImpl implements ServiceReportCustomQuery{

	
	private HSSFFont plainStyle;
	private HSSFFont headerStyle;
	private HSSFWorkbook book = new HSSFWorkbook();
	private HSSFSheet sheet = book.createSheet();
	private HSSFCellStyle headerCellStyle;
	private HSSFCellStyle plainCellStyle;
	private int rowCount = 0;
	private int cellCount = 0;

	public ServiceReportCustomQueryImpl(){
		book = new HSSFWorkbook();
		sheet = book.createSheet();
		plainStyle = book.createFont();
		headerStyle = book.createFont();
		plainStyle.setFontHeightInPoints((short)11);
		plainStyle.setBoldweight((short)11);
		plainStyle.setFontName(HSSFFont.FONT_ARIAL);
		
		BeanUtils.copyProperties(plainStyle, headerStyle);
		headerStyle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);	

		headerCellStyle = book.createCellStyle();
		headerCellStyle.setWrapText(true);
		headerCellStyle.setAlignment(HSSFCellStyle. ALIGN_JUSTIFY);
		headerCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
		headerCellStyle.setFont(headerStyle);

		plainCellStyle = book.createCellStyle();
		plainCellStyle.setWrapText(true);
		plainCellStyle.setAlignment(HSSFCellStyle. ALIGN_JUSTIFY);
		plainCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
		plainCellStyle.setFont(plainStyle);

	}
	
	@Override
	public byte[] getBytesCustomQuery(CustomQueryReport bean) {
		HSSFWorkbook objWB = createBookExcel(bean);
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

	public byte[] inputStreamToBytes(InputStream in) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
		byte[] buffer = new byte[1024];
		int len;
		while((len = in.read(buffer)) >= 0)
		out.write(buffer, 0, len);
		in.close();
		out.close();
		return out.toByteArray();
	}

	private HSSFWorkbook createBookExcel(CustomQueryReport beanReport){
		
		HSSFRow row = createNextRow();
		for(String key : beanReport.getColumns().keySet()){
			createCell(row, cellCount++, key, headerCellStyle);
		}
		
		String key1 = beanReport.getColumns().keySet().toArray()[0].toString();
		for(int i = 0; i<beanReport.getColumns().get(key1).size(); i++){
			row = createNextRow();
			for(String colName : beanReport.getColumns().keySet()){
				createCell(row, cellCount++, beanReport.getColumns().get(colName).get(i), plainCellStyle);
			}
		}

		return book;
	}

	private HSSFRow createNextRow(){
		cellCount = 0;
		return sheet.createRow(rowCount++);
	}

	private void createCell(HSSFRow rowData, int sizeColumn, String strValue, HSSFCellStyle style){
		HSSFCell dataCell = rowData.createCell(sizeColumn);
      	HSSFRichTextString cellValue = new HSSFRichTextString(strValue);
      	if (style != null)
      		dataCell.setCellStyle(style);
		dataCell.setCellType(HSSFCell.CELL_TYPE_STRING);
		dataCell.setCellValue(cellValue);
	}

}
