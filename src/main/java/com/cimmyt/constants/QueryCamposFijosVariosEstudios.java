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
package com.cimmyt.constants;

public enum QueryCamposFijosVariosEstudios {
	STUDYTITTLE(0,"Lab study tittle","C", "labstudyid.title"),
	PROJECT(1,"Project name","C", "labstudyid.project.projectname"),
	INVESTIGATOR(2,"Investigator ","C", "labstudyid.investigatorid.invest_name"),
	TISSUE(3,"Tissue ","C", "labstudyid.tissue.tissuename"),
	STUDYSAMPLEID(4,"Studysampleid","N","studysampleid"),
	ENTREYNO(5, "Entry Number","N","entryNo"),
	BREEDERGID(6,"GID","N","breedergid"),
	SAMPLEID(7,"SampleID","C","samplegid"),
	PLATENAME(8,"Plate Name","C","platename"),
	PLATELOCATION(9,"Plate Location","C","plateloc"),
	STATUSSELECTFORSEND(10,"Select for send status","C","selforsend"),
	PEDIGREE(11,"Pedigree","C","nval"),
	PLANTNUMBER(12,"Plant Number","N","nplanta"),
	SPECIE(13,"Specie","C","specie"),
	COMMENTS(14,"Comments","C","priority"),
	DATATYPE(15,"Data Type","C","controltype"),
	CURRENTLOCATION(16,"Current Location","C","storageLocation.lname"),
	LOCATION(17,"Location","C","locationid.location_name"),
	SEASON(18,"Season","C","seasonid.season_name");
	
	private Integer id;
	private String value;
	private String typeData;
	private String condicionestecampo;

	/**
	 * @return the typeData
	 */
	public String getTypeData() {
		return typeData;
	}

	public String getCondicionestecampo() {
		return condicionestecampo;
	}

	public void setCondicionestecampo(String condicionestecampo) {
		this.condicionestecampo = condicionestecampo;
	}

	/**
	 * @param typeData the typeData to set
	 */
	public void setTypeData(String typeData) {
		this.typeData = typeData;
	}

	private QueryCamposFijosVariosEstudios (Integer id, String value, 
			String datatype,String condicionestecampo) {
		this.id = id;
		this.value = value;
		this.typeData=datatype;
		this.condicionestecampo=condicionestecampo;
	}

	public String getValue() {
		return this.value;
	}

	public Integer getId() {
		return this.id;
	}
	public static QueryCamposFijosVariosEstudios lookupById(Integer id) {
		QueryCamposFijosVariosEstudios  result = null;

		for (QueryCamposFijosVariosEstudios  column : QueryCamposFijosVariosEstudios.values()) {
			if (id.equals(column.getId())) {
				result = column;
				break; 
			}
		}

		return result;
	}
	
	public static QueryCamposFijosVariosEstudios lookupByName(String name) {
		QueryCamposFijosVariosEstudios  result = null;

		for (QueryCamposFijosVariosEstudios  column : QueryCamposFijosVariosEstudios.values()) {
			if (name.equals(column.getValue())) {
				result = column;
				break;
			}
		}

		return result;
	}

}
