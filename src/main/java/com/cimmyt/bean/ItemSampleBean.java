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

import com.cimmyt.model.bean.SampleDetail;
import com.cimmyt.model.bean.TemporalSample;

public class ItemSampleBean {

	// variable to management gid
	private Integer gid = 0 ;
	// variable to management to acc
	private StringBuilder acc = new StringBuilder ("");
	// variable to management number of plant the value by default is 1
	private Integer plantNo = 1;
	//variable to management specie
	private StringBuilder specie = new StringBuilder ("");
	// variable to management 
	private StringBuilder comment = new StringBuilder ("");
	// variable to management to number of entry
	private Integer entryNo = 0;
	// variable to management location
	private LocationBean locationidBean;
	// variable to management season
    private SeasonBean seasonidBean;
    // variable to validate if is repeat the sample
    private boolean isRepeatSample;  
    //variable to set sample Id
    private int sampleID = 0;
    //variable to set study sample id of the table
    private Integer studysampleid;
    //variable to control type
    private StringBuilder controlType;
    //variable to plateName
    private StringBuilder plateName;
    //variable to row
    private StringBuilder row;
    //variable to column
    private int column;
    //variable to id temp sample
    private int idSampleTemp;
    //variable to load if the sample is a control "C" or is empty "B"
    private StringBuilder typeControl;
    
    private int idOrder = 0;

	public Integer getGid() {
		return gid;
	}
	public void setGid(Integer gid) {
		this.gid = gid;
	}
	public StringBuilder getAcc() {
		return acc;
	}
	public void setAcc(StringBuilder acc) {
		this.acc = acc;
	}
	public Integer getPlantNo() {
		return plantNo;
	}
	public void setPlantNo(Integer plantNo) {
		this.plantNo = plantNo;
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
	public Integer getEntryNo() {
		return entryNo;
	}
	public void setEntryNo(Integer entryNo) {
		this.entryNo = entryNo;
	}
	public LocationBean getLocationidBean() {
		return locationidBean;
	}
	public void setLocationidBean(LocationBean locationidBean) {
		this.locationidBean = locationidBean;
	}
	public SeasonBean getSeasonidBean() {
		return seasonidBean;
	}
	public void setSeasonidBean(SeasonBean seasonidBean) {
		this.seasonidBean = seasonidBean;
	}
	public boolean isRepeatSample() {
		return isRepeatSample;
	}
	public void setRepeatSample(boolean isRepeatSample) {
		this.isRepeatSample = isRepeatSample;
	}
	public int getSampleID() {
		return sampleID;
	}
	public void setSampleID(int sampleID) {
		this.sampleID = sampleID;
	}
	public Integer getStudysampleid() {
		return studysampleid;
	}
	public void setStudysampleid(Integer studysampleid) {
		this.studysampleid = studysampleid;
	}

	public ItemSampleBean copyItemSample(ItemSampleBean temp){
		ItemSampleBean item = new ItemSampleBean();
		item.setAcc(temp.getAcc());
		item.setComment(temp.getComment());
		item.setEntryNo(temp.getEntryNo());
		item.setGid(temp.getGid());
		item.setLocationidBean(temp.getLocationidBean());
		item.setPlantNo(temp.getPlantNo());
		item.setSampleID(temp.getSampleID());
		item.setSeasonidBean(temp.getSeasonidBean());
		item.setSpecie(temp.getSpecie());
		item.setIdOrder(temp.getIdOrder());
		return item;
	}
	public int getIdOrder() {
		return idOrder;
	}
	public void setIdOrder(int idOrder) {
		this.idOrder = idOrder;
	}

	/**
	 * Default constructor
	 */
	
	public ItemSampleBean(){
		
	}
	public ItemSampleBean(TemporalSample temp){
		setAcc(new StringBuilder(temp.getAcc()));
		setComment(new StringBuilder(temp.getComments()));
		setEntryNo(temp.getEntryNo());
		setGid(temp.getGid());
		setPlantNo(temp.getPlantNo());
		setLocationidBean(new LocationBean(temp.getLocation()));
		setSeasonidBean(new SeasonBean(temp.getSeason()));
		setSpecie(new StringBuilder(temp.getSpecie().getOrganismname()));
		setIdSampleTemp(temp.getIdTemSample());
	}
	/**
	 * Constructor that receive a sample Detail
	 */
	public ItemSampleBean(SampleDetail detail, boolean isRepeat){
		if (detail != null && detail.getBreedergid() != null )
		gid = detail.getBreedergid() ;
		if (detail != null && detail.getNval() != null )
		acc = new StringBuilder(detail.getNval());
		if (detail != null && detail.getNplanta() != null )
		plantNo = detail.getNplanta();
		if (detail != null && detail.getSpecie() != null  ){
			specie = new StringBuilder (detail.getSpecie());	
		}
		if (detail != null && detail.getEntryNo() != null  )
		entryNo = detail.getEntryNo();
		// variable to management location
		if (detail != null && detail.getLocationid() != null  )
		locationidBean = new LocationBean(detail.getLocationid());
		if (detail != null && detail.getSeasonid() != null  )
	    seasonidBean =  new SeasonBean(detail.getSeasonid());
	    isRepeatSample = isRepeat;
	    if (detail != null && detail.getSamplegid() != null  )
	    sampleID = detail.getSamplegid();
	    if (detail != null && detail.getStudysampleid() != null  )
	    studysampleid = detail.getStudysampleid();	
		
	}

	public boolean isOnlyIDSampleNotNull (ItemSampleBean bean){
		if (bean.getAcc() != null)return false;
		if (bean.getGid() != null )return false;
		if (bean.getPlantNo() != null)return false;
		if (bean.getLocationidBean() != null ) return false;
		if (bean.getSeasonidBean() != null)return false;
		return true;
	}
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
	public StringBuilder getTypeControl() {
		return typeControl;
	}
	public void setTypeControl(StringBuilder typeControl) {
		this.typeControl = typeControl;
	}
	public StringBuilder getRow() {
		return row;
	}
	public void setRow(StringBuilder row) {
		this.row = row;
	}
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	public int getIdSampleTemp() {
		return idSampleTemp;
	}
	public void setIdSampleTemp(int idSampleTemp) {
		this.idSampleTemp = idSampleTemp;
	}

	public String toString (){
		return "idOrder "+idOrder+" gid : "+gid+ " acc : "+acc + " plantNo : "+plantNo + " specie : "+specie +" comment : "+comment +" entryNo : "+entryNo +
				" locationidBean :"+ locationidBean + " seasonidBean : "+seasonidBean +" typeControl : "+typeControl;
	}
}
