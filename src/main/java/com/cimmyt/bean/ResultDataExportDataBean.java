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

import java.util.List;

public class ResultDataExportDataBean {

	private List<RowResultDataBean> listResults;
	private String nameExport;
	private String dateExport;
	private String book;
	private String listPlate;
	public List<RowResultDataBean> getListResults() {
		return listResults;
	}
	public void setListResults(List<RowResultDataBean> listResults) {
		this.listResults = listResults;
	}
	public String getNameExport() {
		return nameExport;
	}
	public void setNameExport(String nameExport) {
		this.nameExport = nameExport;
	}
	public String getDateExport() {
		return dateExport;
	}
	public void setDateExport(String dateExport) {
		this.dateExport = dateExport;
	}
	public String getBook() {
		return book;
	}
	public void setBook(String book) {
		this.book = book;
	}
	public String getListPlate() {
		return listPlate;
	}
	public void setListPlate(String listPlate) {
		this.listPlate = listPlate;
	}

}
