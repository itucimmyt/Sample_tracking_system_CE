package com.cimmyt.bms.client.dto;

import java.util.List;

public class GermplamsDetail {

	/*
	 * listId": 0,
  "listName": "",
  "description": "",
  "notes": "",
  "listSize": 0,
  "listDetailsUrl": "",
  "germplasmEntries":
	 * */

	private int listId = 0;
	private String listName;
	private String notes;
	private int listSize = 0;
	private String description;
	private String listDetailsUrl;

	private  List<GermplasmEntries> germplasmEntries;
	
	public int getListId() {
		return listId;
	}
	public void setListId(int listId) {
		this.listId = listId;
	}
	public String getListName() {
		return listName;
	}
	public void setListName(String listName) {
		this.listName = listName;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public int getListSize() {
		return listSize;
	}
	public void setListSize(int listSize) {
		this.listSize = listSize;
	}
	public String getListDetailsUrl() {
		return listDetailsUrl;
	}
	public void setListDetailsUrl(String listDetailsUrl) {
		this.listDetailsUrl = listDetailsUrl;
	}
	public List<GermplasmEntries> getGermplasmEntries() {
		return germplasmEntries;
	}
	public void setGermplasmEntries(List<GermplasmEntries> germplasmEntries) {
		this.germplasmEntries = germplasmEntries;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
