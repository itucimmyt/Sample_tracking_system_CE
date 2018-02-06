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
package com.cimmyt.reports.bean;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import com.cimmyt.utils.PropertyHelper;

public class ExportReport {
	PropertyHelper pro= null;
	@SuppressWarnings("rawtypes")
	public byte[] exportReport(PropertyHelper properties ,
			ArrayList list){
		pro = properties;
		byte[] b1 = null;
		HashMap  map = loadReport(list);
		b1 = showPDFFinalReport(list, map);
		return b1;
	}
	
	@SuppressWarnings("rawtypes")
	private HashMap loadReport (ArrayList list){
		HashMap<String, Object> hmpParams1 = new HashMap<String, Object>();
		hmpParams1.put("bean_codebar", list.toArray());
		
		return hmpParams1;
	}
	
	private byte [] showPDFFinalReport (List<Object> list, HashMap hmpParams){
		byte []b1 = null;
		try {
			String rutaResumen="/com/cimmyt/reports/src/location_barcodes.jasper";
			InputStream isReportStream =getClass().getResourceAsStream(rutaResumen);
			if (isReportStream!=null){
				//System.out.println("Objects ....... "+isReportStream+" params "+hmpParams+" List "+ list);
				JasperPrint jasperPrint = JasperFillManager.fillReport(isReportStream,hmpParams, new JRBeanCollectionDataSource(list));
				
				
			
				b1=JasperExportManager.exportReportToPdf(jasperPrint);
			}	
		}catch (JRException jre){
			jre.printStackTrace();
		}catch (Exception ex){
			ex.printStackTrace();
		}
		return b1;
	
	}
}
