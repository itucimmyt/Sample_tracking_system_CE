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

import java.util.LinkedList;
import java.util.List;

/**
 * Convenience class for implementing  FileCSVBean.
 * Beans created based on this class just have to define the method that creates de body of the CSV file (getRowsStr)
 * 
 * @author jalberto
 *
 * @param <T> is the Bean Type defining a data for a row
 * 
 */
public abstract class AbstractFileCSVBean<T> implements FileCSVBean<T>{

	// variable to management of headers
	private List<String> listHeaders = new LinkedList<String>();
	//variable that contain the values of rows 
	private List<T> listRows = new LinkedList<T>();

	public List<String> getListHeaders() {
		return listHeaders;
	}
	@Override
	public void setListHeaders(List<String> listHeaders) {
		this.listHeaders = listHeaders;
	}
	@Override
	public List<T> getListRows() {
		return listRows;
	}
	@Override
	public void setListRows(List<T> listRows) {
		this.listRows = listRows;
	}

	@Override
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

}
