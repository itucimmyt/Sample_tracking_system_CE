package com.cimmyt.bean;

import java.io.Serializable;

@SuppressWarnings("serial")
public class RoleBean implements Serializable{

	private Integer idstRol;
	private String name;

	public RoleBean(){	
	}

	public RoleBean(Integer id, String name){
		this.idstRol = id;
		this.name = name;
	}
	public Integer getIdstRol() {
		return idstRol;
	}
	public void setIdstRol(Integer idstRol) {
		this.idstRol = idstRol;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	
}
