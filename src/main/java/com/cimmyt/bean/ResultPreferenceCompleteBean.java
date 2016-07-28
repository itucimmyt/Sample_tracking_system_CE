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

import com.cimmyt.model.bean.ResultsPreferences;

public class ResultPreferenceCompleteBean {

	//list of plate that will be load in the report
	private String listPlateName = "";
	//Result preference 
	private ResultsPreferences preference;
	//
	private Integer numberRows = 0;
		
	public String getListPlateName() {
		return listPlateName;
	}
	public void setListPlateName(String listPlateName) {
		this.listPlateName = listPlateName;
	}
	public ResultsPreferences getPreference() {
		return preference;
	}
	public void setPreference(ResultsPreferences preference) {
		this.preference = preference;
	}
	public Integer getNumberRows() {
		return numberRows;
	}
	public void setNumberRows(Integer numberRows) {
		this.numberRows = numberRows;
	}

	
}
