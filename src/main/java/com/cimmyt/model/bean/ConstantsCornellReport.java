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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 
 * @author 
 *
 */
@Entity
@Table(name = "st_constants_cornell_report")
public class ConstantsCornellReport implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idconstant")
	private Integer idconstant;
	
	@Column(name = "labstudyid")
	private Integer labstudyid;
	
	@Column(name = "sampleDNAconcentration")
	private String sampleDNAconcentration;
	
	@Column(name = "sampleVolume")
	private String sampleVolume;
	
	@Column(name = "sampleDNAMass")
	private String sampleDNAMass;
	
	@Column(name = "preparer")
	private String preparer;
	
	@Column(name = "kingdom")
	private String kingdom;
	
	@Column(name = "genus")
	private String genus;
	
	@Column(name = "sourcelab")
	private String sourcelab;

	/**
	 * @return the idconstant
	 */
	public Integer getIdconstant() {
		return idconstant;
	}

	/**
	 * @param idconstant the idconstant to set
	 */
	public void setIdconstant(Integer idconstant) {
		this.idconstant = idconstant;
	}

	/**
	 * @return the sampleDNAconcentration
	 */
	public String getSampleDNAconcentration() {
		return sampleDNAconcentration;
	}

	/**
	 * @param sampleDNAconcentration the sampleDNAconcentration to set
	 */
	public void setSampleDNAconcentration(String sampleDNAconcentration) {
		this.sampleDNAconcentration = sampleDNAconcentration;
	}

	/**
	 * @return the sampleVolume
	 */
	public String getSampleVolume() {
		return sampleVolume;
	}

	/**
	 * @param sampleVolume the sampleVolume to set
	 */
	public void setSampleVolume(String sampleVolume) {
		this.sampleVolume = sampleVolume;
	}

	/**
	 * @return the sampleDNAMass
	 */
	public String getSampleDNAMass() {
		return sampleDNAMass;
	}

	/**
	 * @param sampleDNAMass the sampleDNAMass to set
	 */
	public void setSampleDNAMass(String sampleDNAMass) {
		this.sampleDNAMass = sampleDNAMass;
	}

	/**
	 * @return the preparer
	 */
	public String getPreparer() {
		return preparer;
	}

	/**
	 * @param preparer the preparer to set
	 */
	public void setPreparer(String preparer) {
		this.preparer = preparer;
	}

	/**
	 * @return the kingdom
	 */
	public String getKingdom() {
		return kingdom;
	}

	/**
	 * @param kingdom the kingdom to set
	 */
	public void setKingdom(String kingdom) {
		this.kingdom = kingdom;
	}

	/**
	 * @return the genus
	 */
	public String getGenus() {
		return genus;
	}

	/**
	 * @param genus the genus to set
	 */
	public void setGenus(String genus) {
		this.genus = genus;
	}

	/**
	 * @return the labstudyid
	 */
	public Integer getLabstudyid() {
		return labstudyid;
	}

	/**
	 * @param labstudyid the labstudyid to set
	 */
	public void setLabstudyid(Integer labstudyid) {
		this.labstudyid = labstudyid;
	}

	/**
	 * @return the sourcelab
	 */
	public String getSourcelab() {
		return sourcelab;
	}

	/**
	 * @param sourcelab the sourcelab to set
	 */
	public void setSourcelab(String sourcelab) {
		this.sourcelab = sourcelab;
	}
	
	
	
	

}

