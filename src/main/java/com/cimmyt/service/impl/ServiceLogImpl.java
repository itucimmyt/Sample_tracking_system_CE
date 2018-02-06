package com.cimmyt.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.cimmyt.dnast.dto.AuthUserBean;
import org.springframework.beans.BeanUtils;

import com.cimmyt.bean.CSVReportGenericBean;
import com.cimmyt.bean.RowCSV;
import com.cimmyt.model.bean.Log;
import com.cimmyt.model.bean.LogTypeEntity;
import com.cimmyt.model.bean.LogTypeOperation;
import com.cimmyt.model.dao.LogDAO;
import com.cimmyt.service.ServiceLog;
import com.cimmyt.utils.StrUtils;

public class ServiceLogImpl implements ServiceLog {

	private LogDAO logDAO;
	private Logger logger= Logger.getLogger(ServiceLogImpl.class);
	private HSSFFont plainStyle;
	private HSSFFont headerStyle;
	private HSSFWorkbook book = new HSSFWorkbook();
	private HSSFSheet sheet = book.createSheet();
	private HSSFCellStyle headerCellStyle;
	private HSSFCellStyle plainCellStyle;
	private int rowCount = 0;
	private int cellCount = 0;
	
	/**
	 * Method that save  
	 */
	@Override
	public void persistLog(int idTypeEntity, int idRow, int commandType, int idUser, String description) {
	
		Log model = new Log();
		LogTypeOperation logTyperOperation = new LogTypeOperation();
		logTyperOperation.setIdLogTypeOperation(commandType);
		model.setLogTyperOperation(logTyperOperation);
		try {
			model.setOperationDate(StrUtils.getDateStandar(new Date()));
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}
		AuthUserBean stUserVersion = new AuthUserBean();
		stUserVersion.setIdUser(idUser);
		model.setStUserVersion(stUserVersion);
		LogTypeEntity logTypeEntity = new LogTypeEntity();
		logTypeEntity.setIdLogTypeEntity(idTypeEntity);
		model.setLogTypeEntity(logTypeEntity);
		model.setDescription(description);
		model.setIdRowEntity(idRow);
		logDAO.saveOrUpdate(model);		
	}

	@Override
	public void delete(Log model) {
		logDAO.delete(model);
	}

	@Override
	public Log getLog(Log model) {
		return logDAO.getLog(model);
	}

	@Override
	public List<Log> getListLog(Log model, int firstResult,
			int maxResults, int sortColumn, boolean ascending,
			String dateInit, String dateEnd,int idRoll) {
		return logDAO.getListLog(model, firstResult, maxResults, 
				sortColumn, ascending, dateInit, dateEnd,idRoll);
	}

	public void setLogDAO(LogDAO logDAO) {
		this.logDAO = logDAO;
	}

	@Override
	public Integer getTotalRowsByFilter(Log filter, int idRoll,String initDate, String endDate) {
		return this.logDAO.getTotalRowsByFilter(filter, idRoll, initDate,  endDate);
	}

	@Override
	public CSVReportGenericBean getReportCSVLog(Log model, int firstResult,
			int maxResults, int sortColumn, boolean ascending,
			String dateInit, String dateEnd,int idRoll, List<StringBuilder> listHeaders) {
		CSVReportGenericBean reportCSV = new CSVReportGenericBean();
		reportCSV.setListHeaders(listHeaders);
			List<Log> listLog = logDAO.getListLog(model, firstResult, maxResults, 
				sortColumn, ascending, dateInit, dateEnd,idRoll);
			List <RowCSV> listRowCSV = new ArrayList<RowCSV>();
			if (listLog != null && !listLog.isEmpty()){
				for (Log log : listLog){
					RowCSV rowCSV = new RowCSV();
					List<StringBuilder> listRow = new ArrayList<StringBuilder>();
					listRow.add(new StringBuilder(log.getStUserVersion().getInvestName()));
					listRow.add(new StringBuilder(log.getLogTyperOperation().getDescription()));
					listRow.add(new StringBuilder(StrUtils.getDateFormat(log.getOperationDate())));
					listRow.add(new StringBuilder(log.getStUserVersion().getInvestName()));
					listRow.add(new StringBuilder(log.getDescription()));
					rowCSV.setListRow(listRow);
					listRowCSV.add(rowCSV);
				}
			}
			reportCSV.setListRowCSV(listRowCSV);
			return reportCSV;
	}

	@Override
	public byte[] getReportExcelLog(Log model, int firstResult, int maxResults,
			int sortColumn, boolean ascending, String dateInit, String dateEnd,
			int idRoll, List<StringBuilder> listHeaders) {
		initExcelcomponents();
		List<Log> listLog = logDAO.getListLog(model, firstResult, maxResults, 
				sortColumn, ascending, dateInit, dateEnd,idRoll);
		if (listLog != null && !listLog.isEmpty()){
			HSSFWorkbook book = createBookExcel(listHeaders, listLog);
			return getArryByte(book);
		}else
			return null;
	}

	private HSSFWorkbook createBookExcel(List<StringBuilder> listHeaders,List<Log> listLog  ){
		
		HSSFRow row = createNextRow();
		for(StringBuilder key : listHeaders){
			createCell(row, cellCount++, key.toString(), headerCellStyle);
		}
			
			for(Log log : listLog){
				row = createNextRow();
				createCell(row, cellCount++, log.getStUserVersion().getInvestName(), plainCellStyle);
				createCell(row, cellCount++, log.getLogTyperOperation().getDescription(), plainCellStyle);
				createCell(row, cellCount++, StrUtils.getDateFormat(log.getOperationDate()), plainCellStyle);
				createCell(row, cellCount++, log.getStUserVersion().getInvestigator().getInvestName(), plainCellStyle);
				createCell(row, cellCount++, log.getDescription(), plainCellStyle);
			}
		

		return book;
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

	private void initExcelcomponents (){
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
}
