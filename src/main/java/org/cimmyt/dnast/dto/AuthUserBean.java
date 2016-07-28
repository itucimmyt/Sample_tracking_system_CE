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
package org.cimmyt.dnast.dto;

import java.util.HashSet;
import java.util.Set;

import com.cimmyt.bean.InvestigatorBean;
import com.cimmyt.bean.RoleBean;
import com.cimmyt.bean.UserBean;
import com.cimmyt.model.bean.Organism;
import com.cimmyt.model.bean.Role;
import com.cimmyt.model.bean.UserFuntions;


public class AuthUserBean {

	private Integer idUser;
	private String version;
	private Organism stOrganism;
	private Role stRole;
	private String userName;
	private String email;
	private String investName;
	private String investEmail;
	private boolean status;
	private String password;
	private Set<UserFuntions> userFuntionses = new HashSet<UserFuntions>(0);
	
	private StInvestigator investigator;
	
	public Integer getIdUser() {
		return idUser;
	}
	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getInvestName() {
		return investName;
	}
	public void setInvestName(String investName) {
		this.investName = investName;
	}
	public String getInvestEmail() {
		return investEmail;
	}
	public void setInvestEmail(String investEmail) {
		this.investEmail = investEmail;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public boolean getStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public StInvestigator getInvestigator() {
		return investigator;
	}
	public void setInvestigator(StInvestigator investigator) {
		this.investigator = investigator;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public Organism getStOrganism() {
		return this.stOrganism;
	}

	public void setStOrganism(Organism stOrganism) {
		this.stOrganism = stOrganism;
	}

	public Role getStRole() {
		return this.stRole;
	}

	public void setStRole(Role stRole) {
		this.stRole = stRole;
	}
	public Set<UserFuntions> getUserFuntionses() {
		return userFuntionses;
	}
	public void setUserFuntionses(Set<UserFuntions> userFuntionses) {
		this.userFuntionses = userFuntionses;
	}

	public UserBean getUserBean(AuthUserBean auth){
		UserBean user = new UserBean();
		user.setIdUser(auth.getIdUser());
		user.setEmail(auth.email);
		user.setOrganism(auth.getStOrganism());
		user.setPassword(auth.password);
		InvestigatorBean investigatorBean = new InvestigatorBean();
		investigatorBean.setInvest_abbreviation(auth.getInvestigator().getInvestAbbreviation());
		investigatorBean.setInvest_name(auth.getInvestigator().getInvestName());
		investigatorBean.setInvestigatorid(auth.getInvestigator().getInvestigatorid());
		user.setInvestigatorBean(investigatorBean);
		user.setProfile(auth.getStRole().getName());
		user.setResearcherName(auth.getInvestName());
		RoleBean role = new RoleBean();
		role.setIdstRol(auth.getStRole().getIdstRol());
		role.setName(auth.getStRole().getName());
		user.setRole(role);
		user.setTypeCorp(auth.getStOrganism().getOrganismid());
		user.setOrganism(auth.getStOrganism());
		user.setUserName(userName);
		return user;
	}

}
