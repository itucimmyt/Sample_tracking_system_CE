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

import java.util.List;
import java.util.Map;

import org.cimmyt.dnast.dto.DsSearchParam;

public class CustomQueryReport {

	private String database;
	private List<DsSearchParam> params;
	private List<String> paramHeaders;
	private Map<String, List<String>> columns;

	public String getDatabase() {
		return database;
	}
	public void setDatabase(String database) {
		this.database = database;
	}
	public List<DsSearchParam> getParams() {
		return params;
	}
	public void setParams(List<DsSearchParam> params) {
		this.params = params;
	}
	public Map<String, List<String>> getColumns() {
		return columns;
	}
	public void setColumns(Map<String, List<String>> columns) {
		this.columns = columns;
	}
	public List<String> getParamHeaders() {
		return paramHeaders;
	}
	public void setParamHeaders(List<String> paramHeaders) {
		this.paramHeaders = paramHeaders;
	}

}
