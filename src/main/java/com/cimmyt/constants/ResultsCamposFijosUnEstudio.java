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
import static com.cimmyt.utils.ConstantsDNA.RESULT_FIELD_CONSTANT_NUMERIC;
import static com.cimmyt.utils.ConstantsDNA.RESULT_FIELD_CONSTANT_CHARACTER;

public enum ResultsCamposFijosUnEstudio {
	STUDYSAMPLEID(0,"Studysampleid",RESULT_FIELD_CONSTANT_NUMERIC,"studysampleid",true),
	ENTREYNO(1, "Entry Number",RESULT_FIELD_CONSTANT_NUMERIC,"entryNo",false),
	BREEDERGID(2,"GID",RESULT_FIELD_CONSTANT_NUMERIC,"breedergid",true),
	SAMPLEID(3,"SampleID",RESULT_FIELD_CONSTANT_CHARACTER,"samplegid",true),
	PLATENAME(4,"Plate Name",RESULT_FIELD_CONSTANT_CHARACTER,"platename",true),
	PLATELOCATION(5,"Plate Location",RESULT_FIELD_CONSTANT_CHARACTER,"plateloc",true),
	ROW(6,"Row",RESULT_FIELD_CONSTANT_CHARACTER,"",false),
	COLUMN(7,"Column",RESULT_FIELD_CONSTANT_NUMERIC,"",false),
	POS1(8,"Pos1",RESULT_FIELD_CONSTANT_CHARACTER,"",false),
	STATUSSELECTFORSEND(9,"Select for send status",RESULT_FIELD_CONSTANT_CHARACTER,"selforsend",false),
	PEDIGREE(10,"Pedigree",RESULT_FIELD_CONSTANT_CHARACTER,"nval",true),
	PLANTNUMBER(11,"Plant Number",RESULT_FIELD_CONSTANT_NUMERIC,"nplanta",true),
	SPECIE(12,"Species",RESULT_FIELD_CONSTANT_CHARACTER,"specie",false),
	COMMENTS(13,"Comments",RESULT_FIELD_CONSTANT_CHARACTER,"priority",false),
	DATATYPE(14,"Data Type",RESULT_FIELD_CONSTANT_CHARACTER,"controltype",false),
	CURRENTLOCATION(15,"Current Location",RESULT_FIELD_CONSTANT_CHARACTER,"storageLocation.lname",false),
	LOCATION(16,"Location",RESULT_FIELD_CONSTANT_CHARACTER,"locationid.location_name",true),
	SEASON(17,"Season",RESULT_FIELD_CONSTANT_CHARACTER,"seasonid.season_name",true);
	
	private Integer id;
	private String value;
	private String typeData;
	private String condicionestecampo;
	private boolean defaultChecked;

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

	private ResultsCamposFijosUnEstudio (Integer id, String value, 
			String datatype,String condicionestecampo, boolean defaultChecked) {
		this.id = id;
		this.value = value;
		this.typeData=datatype;
		this.condicionestecampo=condicionestecampo;
		this.defaultChecked=defaultChecked;
	}

	public String getValue() {
		return this.value;
	}

	public Integer getId() {
		return this.id;
	}
	public static ResultsCamposFijosUnEstudio lookupById(Integer id) {
		ResultsCamposFijosUnEstudio  result = null;

		for (ResultsCamposFijosUnEstudio  column : ResultsCamposFijosUnEstudio.values()) {
			if (id.equals(column.getId())) {
				result = column;
				break; 
			}
		}

		return result;
	}
	
	public static ResultsCamposFijosUnEstudio lookupByName(String name) {
		ResultsCamposFijosUnEstudio  result = null;

		for (ResultsCamposFijosUnEstudio  column : ResultsCamposFijosUnEstudio.values()) {
			if (name.equals(column.getValue())) {
				result = column;
				break;
			}
		}

		return result;
	}

	public boolean isDefaultChecked() {
		return defaultChecked;
	}

	public void setDefaultChecked(boolean defaultChecked) {
		this.defaultChecked = defaultChecked;
	}

}
