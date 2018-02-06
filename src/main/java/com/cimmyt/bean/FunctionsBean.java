package com.cimmyt.bean;

import com.cimmyt.model.bean.Funtions;

public class FunctionsBean {

	private Integer idstFuntions;
	private String funtionKey;

	public FunctionsBean(){
		
	}

	public FunctionsBean(Funtions functions){
		idstFuntions = functions.getIdstFuntions();
		funtionKey = functions.getFuntionKey();
	}

	public Integer getIdstFuntions() {
		return idstFuntions;
	}
	public void setIdstFuntions(Integer idstFuntions) {
		this.idstFuntions = idstFuntions;
	}
	public String getFuntionKey() {
		return funtionKey;
	}
	public void setFuntionKey(String funtionKey) {
		this.funtionKey = funtionKey;
	}

	
}
