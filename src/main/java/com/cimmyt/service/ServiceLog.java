package com.cimmyt.service;

import java.util.List;

import com.cimmyt.bean.CSVReportGenericBean;
import com.cimmyt.model.bean.Log;

public interface ServiceLog {

	public void persistLog(int idTypeEntity, int idRow, int commandType, int idUser, String description);
	public void delete (Log model);
	public Log getLog(Log model);
	public List<Log> getListLog (Log model ,int firstResult, int maxResults
			, int sortColumn, boolean ascending, String dateInit, String dateEnd,int idRoll);
	public Integer getTotalRowsByFilter(Log filter, int idRoll,String initDate, String endDate);
	public CSVReportGenericBean getReportCSVLog(Log model, int firstResult,
			int maxResults, int sortColumn, boolean ascending,
			String dateInit, String dateEnd,int idRoll,List<StringBuilder> listHeaders);
	public byte [] getReportExcelLog(Log model, int firstResult,
			int maxResults, int sortColumn, boolean ascending,
			String dateInit, String dateEnd,int idRoll,List<StringBuilder> listHeaders);
}
