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
package com.cimmyt.servlet;

import org.zkoss.zhtml.Filedownload;

import com.cimmyt.bean.CSVReportGenericBean;
import com.cimmyt.bean.FileCSVBean;
import com.cimmyt.utils.ConstantsDNA;

public class RedirectServletReport{

    public static void export(SessionReport sessionreport){
		StringBuffer sb;
    	if (sessionreport != null){
    		try{
    		switch (sessionreport.getType()){
	    		case ConstantsDNA.FILE_TYPE_PDF :{
	    			Filedownload.save(sessionreport.getB(), "application/pdf", sessionreport.getName().trim()+".pdf");
	    			break;
	    		}
	    		case ConstantsDNA.FILE_TYPE_XLS :{
	    			Filedownload.save(sessionreport.getB(), "application/ms-excel", sessionreport.getName().trim()+".xls");
	    			break;
	    		}
	    		case ConstantsDNA.FILE_TYPE_CSV :
	    			sb = new StringBuffer();
	    			FileCSVBean<?> file = sessionreport.getFileCSVBean();
	    			
	    			sb.append(file.getStringHeader());
	    			sb.append(file.getRowsStr());
	    			Filedownload.save(sb.toString().getBytes(), "application/csv", sessionreport.getName().trim()+".csv");
	    			break;
	    		case ConstantsDNA.FILE_TYPE_CSV_GENERIC :
					sb = new StringBuffer();
					CSVReportGenericBean csvReportGenericBean = sessionreport.getCsvReportGenericBean();

					sb.append(csvReportGenericBean.getStringHeader());
					sb.append(csvReportGenericBean.getRowsStr());
					Filedownload.save(sb.toString().getBytes(), "application/csv", sessionreport.getName().trim()+".csv");

	    			break;
	    		default :
	    			return;
    		}
    		}catch (Exception ex){
    			ex.printStackTrace();
    		}finally {
    			sessionreport = null;
    			System.gc();
    		}
    	}
    }

}
