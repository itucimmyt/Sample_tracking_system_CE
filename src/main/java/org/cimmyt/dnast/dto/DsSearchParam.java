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

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.cimmyt.bean.Operator;
import com.cimmyt.bean.Operator.TypeCondition;

@XmlRootElement(name="searchParam")
public class DsSearchParam {

	private TypeCondition operator;
	private String value;
	private String element;
	private String qualifier;
	private Integer schemaID;
	private Integer id;
	private DsMetadataElement metadataElemet;
	private String condition;
	
	public DsSearchParam() {	}
	
	public DsSearchParam(TypeCondition operator, String value, String element, String qualifier, Integer schemaID){
		this.operator = operator;
		this.value = value;
		this.element = element;
		this.qualifier = qualifier;
		this.schemaID = schemaID;
		
	}

	public TypeCondition getOperator() {
		return operator;
	}

	public void setOperator(TypeCondition operator) {
		this.operator = operator;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@XmlAttribute
	public String getElement() {
		return element;
	}

	public void setElement(String element) {
		this.element = element;
	}

	@XmlAttribute
	public String getQualifier() {
		return qualifier;
	}

	public void setQualifier(String qualifier) {
		this.qualifier = qualifier;
	}

	@XmlAttribute
	public Integer getSchemaID() {
		return schemaID;
	}

	public void setSchemaID(Integer schemaID) {
		this.schemaID = schemaID;
	}

	@XmlAttribute
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@XmlTransient
	public DsMetadataElement getMetadataElemet() {
		return metadataElemet;
	}

	public void setMetadataElemet(DsMetadataElement metadataElemet) {
		this.metadataElemet = metadataElemet;
		this.id = metadataElemet.getId();
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}
	
	public String toString(){
		return " operator :"+operator + " Value :"+value+ " Element : "+element + " qualifier : "+qualifier + " schema : "+schemaID + " id : "+id+" condition : "+condition; 
	
	}
}
