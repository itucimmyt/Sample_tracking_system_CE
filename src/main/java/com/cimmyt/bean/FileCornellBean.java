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

public class FileCornellBean {

    private String sourceLab;
    private String sampleDNA;
    private String sampleVol;    
    private String sampleMass;
    private String preparer;
    private String kingdom;
    private String genus;
	public String getSourceLab() {
		return sourceLab;
	}
	public void setSourceLab(String sourceLab) {
		this.sourceLab = sourceLab;
	}
	public String getSampleDNA() {
		return sampleDNA;
	}
	public void setSampleDNA(String sampleDNA) {
		this.sampleDNA = sampleDNA;
	}
	public String getSampleVol() {
		return sampleVol;
	}
	public void setSampleVol(String sampleVol) {
		this.sampleVol = sampleVol;
	}
	public String getSampleMass() {
		return sampleMass;
	}
	public void setSampleMass(String sampleMass) {
		this.sampleMass = sampleMass;
	}
	public String getPreparer() {
		return preparer;
	}
	public void setPreparer(String preparer) {
		this.preparer = preparer;
	}
	public String getKingdom() {
		return kingdom;
	}
	public void setKingdom(String kingdom) {
		this.kingdom = kingdom;
	}
	public String getGenus() {
		return genus;
	}
	public void setGenus(String genus) {
		this.genus = genus;
	}
	public FileCornellBean(String sourceLab, String sampleDNA,
			String sampleVol, String sampleMass, String preparer,
			String kingdom, String genus) {
		super();
		this.sourceLab = sourceLab;
		this.sampleDNA = sampleDNA;
		this.sampleVol = sampleVol;
		this.sampleMass = sampleMass;
		this.preparer = preparer;
		this.kingdom = kingdom;
		this.genus = genus;
	}
	public FileCornellBean() {
		super();
		sourceLab = "CIMMYT-Mexico";
		sampleDNA = "100ng/ul";
		sampleVol = "100ul";
		sampleMass = "10ug";
		preparer = "";
		kingdom = "Plant";
		genus = "";
	}
}
