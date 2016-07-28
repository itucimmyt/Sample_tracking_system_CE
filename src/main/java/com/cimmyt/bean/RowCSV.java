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

public class RowCSV {

	/**
	 * List to store the rows of file csv
	 */
	private List<StringBuilder> listRow = new ArrayList<StringBuilder>();

	/**
	 * Get list of Rows of file CSV
	 * @return List
	 */
	public List<StringBuilder> getListRow() {
		return listRow;
	}

	/**
	 * Set List rows of the samples
	 * @param listRow
	 */
	public void setListRow(List<StringBuilder> listRow) {
		this.listRow = listRow;
	}

}
