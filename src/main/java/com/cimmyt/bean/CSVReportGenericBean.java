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
package com.cimmyt.bean;

import java.util.ArrayList;
import java.util.List;

public class CSVReportGenericBean {

	/**
	 * List of headers 
	 */
	private List<StringBuilder> listHeaders = new ArrayList<StringBuilder>();

	/**
	 * List of Samples in rows
	 */
	private List <RowCSV> listRowCSV = new ArrayList<RowCSV>();

	public List<StringBuilder> getListHeaders() {
		return listHeaders;
	}

	public void setListHeaders(List<StringBuilder> listHeaders) {
		this.listHeaders = listHeaders;
	}

	public List<RowCSV> getListRowCSV() {
		return listRowCSV;
	}

	public void setListRowCSV(List<RowCSV> listRowCSV) {
		this.listRowCSV = listRowCSV;
	}
	/**
	 * Method that create the header to file CVS
	 * @return
	 */
	public String getStringHeader(){
		StringBuilder strB = new StringBuilder ();
		for (int index = 0 ; index < listHeaders.size(); index ++){
			strB.append(listHeaders.get(index));
			if ((index+1) != listHeaders.size() )
			strB.append(",");
			else
				strB.append("\n");
		}
		return strB.toString(); 
	}
	/**
	 * Method that create a String to be publish in a file CSV
	 * @return
	 */
	public String getRowsStr (){
		StringBuilder strB = new StringBuilder ();
		for (int index = 0 ; index < listRowCSV.size(); index ++){
			RowCSV row = listRowCSV.get(index);
			int size = row.getListRow().size();
			int indexRow = 0;
			for (StringBuilder strBR : row.getListRow()){
				strB.append(strBR);
				if ((indexRow+1) < size){
					strB.append(",");
				}
				indexRow++;
			}
			strB.append("\n");
		}
		return strB.toString();
	}
	
}
