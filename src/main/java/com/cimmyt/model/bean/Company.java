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
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 
 * @author 
 */
@Entity
@Table(name = "st_company")
@NamedQueries( { @NamedQuery(name = "Company.findAll", query = "SELECT i FROM Company i") })
public class Company implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "idCompany")
	private Integer idCompany;
	@Basic(optional = false)
	@Column(name = "name")
	private String name;
	@Column(name = "addresss")
	private String addresss;
	@Column(name = "email")
	private String email;
	@Column(name = "contactname")
	private String contactname;
	@Column(name = "phone")
	private String phone;
	@OneToMany(cascade = CascadeType.ALL,fetch= FetchType.LAZY, mappedBy = "stCompany")
	private Set<Shipment> imsShipmentCollection = new HashSet<Shipment>(0);
	
	@JoinColumn(name = "imslocid", referencedColumnName = "imslocid")
	@ManyToOne(optional = false)
	private StorageLocation storagelocation;

	public Company() {

		name = new String();
		addresss = new String();
		email = new String();
		contactname = new String();
		phone = new String();

	}

	public Company(Integer idCompany) {
		this.idCompany = idCompany;
	}

	public Company(Integer idCompany, String name) {
		this.idCompany = idCompany;
		this.name = name;
		addresss = new String();
		email = new String();
		contactname = new String();
		phone = new String();

	}

	public Integer getIdCompany() {
		return idCompany;
	}

	public void setIdCompany(Integer idCompany) {
		this.idCompany = idCompany;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddresss() {
		return addresss;
	}

	public void setAddresss(String addresss) {
		this.addresss = addresss;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContactname() {
		return contactname;
	}

	public void setContactname(String contactname) {
		this.contactname = contactname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Set<Shipment> getImsShipmentCollection() {
		return imsShipmentCollection;
	}

	public void setImsShipmentCollection(
			Set<Shipment> imsShipmentCollection) {
		this.imsShipmentCollection = imsShipmentCollection;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (idCompany != null ? idCompany.hashCode() : 0);
		return hash;
	}
	
	

	/**
	 * @return the storagelocation
	 */
	public StorageLocation getStoragelocation() {
		return storagelocation;
	}

	/**
	 * @param storagelocation the storagelocation to set
	 */
	public void setStoragelocation(StorageLocation storagelocation) {
		this.storagelocation = storagelocation;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof Company)) {
			return false;
		}
		Company other = (Company) object;
		if ((this.idCompany == null && other.idCompany != null)
				|| (this.idCompany != null && !this.idCompany
						.equals(other.idCompany))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return name;
	}

}


