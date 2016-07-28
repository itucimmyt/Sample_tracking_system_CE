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
/**
 * Class to container the values for control the plate
 * @author CIMMYT
 */
public class RowControlFile {

	// variable to put control type
	private StringBuilder controlType = new StringBuilder ();
	//
	private StringBuilder plateName = new StringBuilder ();
	//
	private StringBuilder row = new StringBuilder ();
	//
	private StringBuilder column = new StringBuilder ();
	//
	private StringBuilder gid = new StringBuilder();
	//
	private StringBuilder acc = new StringBuilder ();
	//
	private StringBuilder plateNo = new StringBuilder ();
	//
	private StringBuilder specie = new StringBuilder ();
	//
	private StringBuilder comment = new StringBuilder ();
	//
	private StringBuilder entryNo = new StringBuilder ();
	//
	private StringBuilder location = new StringBuilder ();
	//
	private StringBuilder season = new StringBuilder ();
	public StringBuilder getControlType() {
		return controlType;
	}
	public void setControlType(StringBuilder controlType) {
		this.controlType = controlType;
	}
	public StringBuilder getPlateName() {
		return plateName;
	}
	public void setPlateName(StringBuilder plateName) {
		this.plateName = plateName;
	}
	public StringBuilder getRow() {
		return row;
	}
	public void setRow(StringBuilder row) {
		this.row = row;
	}
	public StringBuilder getColumn() {
		return column;
	}
	public void setColumn(StringBuilder column) {
		this.column = column;
	}
	public StringBuilder getAcc() {
		return acc;
	}
	public void setAcc(StringBuilder acc) {
		this.acc = acc;
	}
	public StringBuilder getPlateNo() {
		return plateNo;
	}
	public void setPlateNo(StringBuilder plateNo) {
		this.plateNo = plateNo;
	}
	public StringBuilder getSpecie() {
		return specie;
	}
	public void setSpecie(StringBuilder specie) {
		this.specie = specie;
	}
	public StringBuilder getComment() {
		return comment;
	}
	public void setComment(StringBuilder comment) {
		this.comment = comment;
	}
	public StringBuilder getEntryNo() {
		return entryNo;
	}
	public void setEntryNo(StringBuilder entryNo) {
		this.entryNo = entryNo;
	}
	public StringBuilder getLocation() {
		return location;
	}
	public void setLocation(StringBuilder location) {
		this.location = location;
	}
	public StringBuilder getSeason() {
		return season;
	}
	public void setSeason(StringBuilder season) {
		this.season = season;
	}
	public StringBuilder getGid() {
		return gid;
	}
	public void setGid(StringBuilder gid) {
		this.gid = gid;
	}

	
}
