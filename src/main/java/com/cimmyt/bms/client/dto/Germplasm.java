package com.cimmyt.bms.client.dto;

public class Germplasm {

	/*
	"listId": 0,
    "listName": "",
    "description": "",
    "notes": "",
    "listSize": 0,
    "listDetailsUrl":*/

	private int listId;
	private String listName;
	private String description;
	private String notes;
	private int listSize;
	private String listDetailsUrl;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	
	
	
}
