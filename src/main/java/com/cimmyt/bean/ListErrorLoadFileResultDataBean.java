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


public class ListErrorLoadFileResultDataBean {

	//Variable to manage of row empty
	private List<Integer> listRowEmty = new ArrayList<Integer>();
	//Variable to manage of sample format
	private List<Integer> listSampleIDFormat = new ArrayList<Integer>();
	//list of samples not found in the same study
	private List<Integer> listErrorSampleIDNotFound = new ArrayList<Integer>();
	// List of errors to the headers
	private List<String> listErrorHeader = new ArrayList<String>();
	// list of rows that contain a size > 255
	private List<Integer> listErrorSize = new ArrayList<Integer>();

	public List<Integer> getListRowEmty() {
		return listRowEmty;
	}
	public void setListRowEmty(List<Integer> listRowEmty) {
		this.listRowEmty = listRowEmty;
	}
	public List<Integer> getListSampleIDFormat() {
		return listSampleIDFormat;
	}
	public void setListSampleIDFormat(List<Integer> listSampleIDFormat) {
		this.listSampleIDFormat = listSampleIDFormat;
	}
	public List<Integer> getListErrorSampleIDNotFound() {
		return listErrorSampleIDNotFound;
	}
	public void setListErrorSampleIDNotFound(List<Integer> listErrorSampleIDNotFound) {
		this.listErrorSampleIDNotFound = listErrorSampleIDNotFound;
	}
	public List<String> getListErrorHeader() {
		return listErrorHeader;
	}
	public void setListErrorHeader(List<String> listErrorHeader) {
		this.listErrorHeader = listErrorHeader;
	}
	public List<Integer> getListErrorSize() {
		return listErrorSize;
	}
	public void setListErrorSize(List<Integer> listErrorSize) {
		this.listErrorSize = listErrorSize;
	}
	public boolean areThereAnyListEmty(){
		if (listRowEmty != null && !listRowEmty.isEmpty()){
			return true;
		}
		if (listSampleIDFormat != null && !listSampleIDFormat.isEmpty()){
			return true;
		}
		if (listErrorSampleIDNotFound != null && !listErrorSampleIDNotFound.isEmpty()){
			return true;
		}
		if (listErrorHeader != null && !listErrorHeader.isEmpty()){
			return true;
		}
		if (listErrorSize != null && !listErrorSize.isEmpty()){
			return true;
		}
		return false;
	} 
}
