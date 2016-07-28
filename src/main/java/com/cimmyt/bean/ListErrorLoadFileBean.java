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

/**
 * Class to management of all errors in the up load file
 * @author root
 *
 */
public class ListErrorLoadFileBean {

	// List of errors with value is empty or null
	private List<Integer> listGidErrorEmpty = new ArrayList<Integer>();
	// List of Errors with value is not integer
	private List<Integer> listGidErrirInteger = new ArrayList<Integer>();
	//List of Errors with location not found in catalog
	private List<String> listLocationErrorRegistered = new ArrayList<String>();
	//List of Errors with location is empty or null
	private List<Integer> listLocationErrorEmpty = new ArrayList<Integer>();
	// List of Errors with season not found in catalog
	private List<String> listSeasonErrorRegistered = new ArrayList<String>();
	//List of Errors with Season is empty or null
	private List<Integer> listSeasonErrorEmpty = new ArrayList<Integer>();
	//List of Errors with plant is not a integer
	private List<String> listPlantInteger= new ArrayList<String>();
	//list of errors with control type 
	private List<String> listControlTypeError= new ArrayList<String>();
	//list of errors with plate name empty 
	private List<String> listPlateNameEmpty= new ArrayList<String>();
	//list of errors with row empty
	private List<String> listRowEmpty= new ArrayList<String>();
	//list of errors with column empty or invalid
	private List<String> listColumnEmptyOrInvalid= new ArrayList<String>();
	
	public List<Integer> getListGidErrorEmpty() {
		return listGidErrorEmpty;
	}
	public void setListGidErrorEmpty(List<Integer> listGidErrorEmpty) {
		this.listGidErrorEmpty = listGidErrorEmpty;
	}
	public List<Integer> getListGidErrirInteger() {
		return listGidErrirInteger;
	}
	public void setListGidErrirInteger(List<Integer> listGidErrirInteger) {
		this.listGidErrirInteger = listGidErrirInteger;
	}
	public List<String> getListLocationErrorRegistered() {
		return listLocationErrorRegistered;
	}
	public void setListLocationErrorRegistered(
			List<String> listLocationErrorRegistered) {
		this.listLocationErrorRegistered = listLocationErrorRegistered;
	}
	public List<Integer> getListLocationErrorEmpty() {
		return listLocationErrorEmpty;
	}
	public void setListLocationErrorEmpty(List<Integer> listLocationErrorEmpty) {
		this.listLocationErrorEmpty = listLocationErrorEmpty;
	}
	public List<String> getListSeasonErrorRegistered() {
		return listSeasonErrorRegistered;
	}
	public void setListSeasonErrorRegistered(List<String> listSeasonErrorRegistered) {
		this.listSeasonErrorRegistered = listSeasonErrorRegistered;
	}
	public List<Integer> getListSeasonErrorEmpty() {
		return listSeasonErrorEmpty;
	}
	public void setListSeasonErrorEmpty(List<Integer> listSeasonErrorEmpty) {
		this.listSeasonErrorEmpty = listSeasonErrorEmpty;
	}
	public List<String> getListPlantInteger() {
		return listPlantInteger;
	}
	public void setListPlantInteger(List<String> listPlantInteger) {
		this.listPlantInteger = listPlantInteger;
	}

	public List<String> getListControlTypeError() {
		return listControlTypeError;
	}
	public void setListControlTypeError(List<String> listControlTypeError) {
		this.listControlTypeError = listControlTypeError;
	}
	public List<String> getListPlateNameEmpty() {
		return listPlateNameEmpty;
	}
	public void setListPlateNameEmpty(List<String> listPlateNameEmpty) {
		this.listPlateNameEmpty = listPlateNameEmpty;
	}
	public List<String> getListRowEmpty() {
		return listRowEmpty;
	}
	public void setListRowEmpty(List<String> listRowEmpty) {
		this.listRowEmpty = listRowEmpty;
	}
	public List<String> getListColumnEmptyOrInvalid() {
		return listColumnEmptyOrInvalid;
	}
	public void setListColumnEmptyOrInvalid(List<String> listColumnEmptyOrInvalid) {
		this.listColumnEmptyOrInvalid = listColumnEmptyOrInvalid;
	}
	public boolean isAnyListNotEmpty(){
		if (listGidErrorEmpty != null && !listGidErrorEmpty.isEmpty()){
			return true;
		}
		if (listGidErrirInteger != null && !listGidErrirInteger.isEmpty()){
			return true;
		}
		if (listLocationErrorRegistered != null && !listLocationErrorRegistered.isEmpty()){
			return true;
		}
		if (listLocationErrorEmpty != null && !listLocationErrorEmpty.isEmpty()){
			return true;
		}
		if (listSeasonErrorRegistered != null && !listSeasonErrorRegistered.isEmpty()){
			return true;
		}
		if (listSeasonErrorEmpty != null && !listSeasonErrorEmpty.isEmpty()){
			return true;
		}
		if (listPlantInteger != null && !listPlantInteger.isEmpty()){
			return true;
		}
		if (listControlTypeError != null && !listControlTypeError.isEmpty()){
			return true;
		}
		if (listPlateNameEmpty != null && !listPlateNameEmpty.isEmpty()){
			return true;
		}
		if (listRowEmpty != null && !listRowEmpty.isEmpty()){
			return true;
		}
		if (listColumnEmptyOrInvalid != null && !listColumnEmptyOrInvalid.isEmpty()){
			return true;
		}
		return false;
	}
}
