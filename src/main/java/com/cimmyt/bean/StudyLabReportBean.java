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

import java.util.Map;
import java.util.SortedMap;

import com.cimmyt.model.bean.SampleDetail;

public class StudyLabReportBean {

	// variable to management of name of rows
	private String [] nameRow;
	//variable to management of number of colums
	private int numberColumn = 0;
	//variable to management sample detail in map
	private SortedMap <Integer, Map<String , SampleDetail>> mapPlateSamples;
	//variable to management of prefix of samples
	private String prefix;
	// pattern to plate
	private String patternPlate;

	public String[] getNameRow() {
		return nameRow;
	}
	public void setNameRow(String[] nameRow) {
		this.nameRow = nameRow;
	}
	public int getNumberColumn() {
		return numberColumn;
	}
	public void setNumberColumn(int numberColumn) {
		this.numberColumn = numberColumn;
	}
	public SortedMap<Integer, Map<String , SampleDetail>> getMapPlateSamples() {
		return mapPlateSamples;
	}
	public void setMapPlateSamples(SortedMap<Integer, Map<String , SampleDetail>> mapPlateSamples) {
		this.mapPlateSamples = mapPlateSamples;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getPatternPlate() {
		return patternPlate;
	}
	public void setPatternPlate(String patternPlate) {
		this.patternPlate = patternPlate;
	}
}
