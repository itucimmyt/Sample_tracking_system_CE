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
package com.cimmyt.model.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "st_register")
public class Register  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	      

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idst_register")
	private Integer idRegister;

	@Column (name = "first_name")
	private String fisrtName;

	@Column (name = "last_name")
	private String lastName;
	
	@Column (name = "email")
	private String email;

	@Column (name = "inmediate_boss")
	private String inmediateBoss;

	@Column (name = "city")
	private String city;

	@Column (name = "contry")
	private String contry;
	
	@Column (name = "industry")
	private String industry;

	@Column (name = "provider_name")
	private String providerName;

	@Column (name = "phone_number")
	private String phoneNumber;

	@Column (name = "isPending")
	private Boolean isPending;

	@Column (name = "register_date")
	private Date RegisterDate;

	public Integer getIdRegister() {
		return idRegister;
	}

	public void setIdRegister(Integer idRegister) {
		this.idRegister = idRegister;
	}

	public String getFisrtName() {
		return fisrtName;
	}

	public void setFisrtName(String fisrtName) {
		this.fisrtName = fisrtName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getInmediateBoss() {
		return inmediateBoss;
	}

	public void setInmediateBoss(String inmediateBoss) {
		this.inmediateBoss = inmediateBoss;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getContry() {
		return contry;
	}

	public void setContry(String contry) {
		this.contry = contry;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Boolean getIsPending() {
		return isPending;
	}

	public void setIsPending(Boolean isPending) {
		this.isPending = isPending;
	}

	public Date getRegisterDate() {
		return RegisterDate;
	}

	public void setRegisterDate(Date registerDate) {
		RegisterDate = registerDate;
	}	

}
