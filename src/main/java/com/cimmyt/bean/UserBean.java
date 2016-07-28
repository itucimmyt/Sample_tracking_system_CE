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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.cimmyt.dnast.dto.StInvestigator;

import com.cimmyt.model.bean.Organism;
import com.cimmyt.model.bean.Role;

public class UserBean implements Serializable{

	/*Serializable para ser enviado por el servlet de autentificacion*/
	private static final long serialVersionUID = -4371742770901563118L;

	private Integer idUser;
	private String userName;
	private String profile;
	private String corp;
	private InvestigatorBean investigatorBean;
	private Organism organism;
	private RoleBean role;
	private List<String>  userFuntionses;
	private String researcherName;
	private String researcherEMail;
	private String password;
	private String email;
	private  List<UserFuntionsBean> setFuntionsBean  = new ArrayList<UserFuntionsBean>();
	/*
	 *variable to management type of corp 
	 */
	private int typeCorp;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	
	public String getCorp() {
		return corp;
	}
	public void setCorp(String corp) {
		this.corp = corp;
	}
	public InvestigatorBean getInvestigatorBean() {
		return investigatorBean;
	}
	public void setInvestigatorBean(InvestigatorBean investigatorBean) {
		this.investigatorBean = investigatorBean;
	}

	public StInvestigator getStInvestigator (InvestigatorBean bean){
		StInvestigator res = new StInvestigator();
		res.setInvestAbbreviation(bean.getInvest_abbreviation());
		res.setInvestigatorid(bean.getInvestigatorid());
		res.setInvestName(bean.getInvest_name());
		return res;
	}
	public int getTypeCorp() {
		return typeCorp;
	}
	public void setTypeCorp(int typeCorp) {
		this.typeCorp = typeCorp;
	}
	public Organism getOrganism() {
		return organism;
	}
	public void setOrganism(Organism organism) {
		this.organism = organism;
	}
	public RoleBean getRole() {
		return role;
	}
	public void setRole(RoleBean role) {
		this.role = role;
	}

	public Role getStRole(RoleBean roleB){
		Role role = new Role();
		role.setIdstRol(roleB.getIdstRol());
		role.setName(roleB.getName());
		return role;
	}
	public List<String>  getUserFuntionses() {
		return userFuntionses;
	}
	public void setUserFuntionses(List<String> userFuntionses) {
		this.userFuntionses = userFuntionses;
	}
	public String getResearcherName() {
		return researcherName;
	}
	public void setResearcherName(String researcherName) {
		this.researcherName = researcherName;
	}
	public String getResearcherEMail() {
		return researcherEMail;
	}
	public void setResearcherEMail(String researcherEMail) {
		this.researcherEMail = researcherEMail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<UserFuntionsBean> getSetFuntionsBean() {
		return setFuntionsBean;
	}
	public void setSetFuntionsBean(List<UserFuntionsBean> setFuntionsBean) {
		this.setFuntionsBean = setFuntionsBean;
	}
	public Integer getIdUser() {
		return idUser;
	}
	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}

	public String toString (){
		return " idUser : "+idUser+" userName : "+userName+" profile : "+profile+ " corp : "+corp+" investigatorBean : "+investigatorBean+" organism : "+organism+
				" role : "+role+" userFuntionses : "+userFuntionses+" researcherName : "+researcherName+" researcherEMail : "+researcherEMail+" password : "+password+
				"email : "+email+ " set functions :"+ setFuntionsBean;
				
	}
	

}
