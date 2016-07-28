package com.cimmyt.bean;

import org.cimmyt.dnast.dto.AuthUserBean;

import com.cimmyt.model.bean.UserFuntions;

public class UserFuntionsBean {

	private Integer idstUserFuntions;
	private AuthUserBean stUserVersion;
	private FunctionsBean Funtions;

	public UserFuntionsBean (){
		
	}

	public UserFuntionsBean (UserFuntions userFunctions){
				Funtions = new FunctionsBean(userFunctions.getStFuntions());
				idstUserFuntions = userFunctions.getIdstUserFuntions();
				stUserVersion = userFunctions.getStUserVersion();
	}
	public Integer getIdstUserFuntions() {
		return idstUserFuntions;
	}
	public void setIdstUserFuntions(Integer idstUserFuntions) {
		this.idstUserFuntions = idstUserFuntions;
	}
	public AuthUserBean getStUserVersion() {
		return stUserVersion;
	}
	public void setStUserVersion(AuthUserBean stUserVersion) {
		this.stUserVersion = stUserVersion;
	}
	public FunctionsBean getFuntions() {
		return Funtions;
	}
	public void setFuntions(FunctionsBean funtions) {
		Funtions = funtions;
	}

	
}
