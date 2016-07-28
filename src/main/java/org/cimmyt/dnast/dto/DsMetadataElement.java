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

@XmlRootElement(name="metadata")
public class DsMetadataElement {

	private String element;
	private String qualifier;
	private Integer schemaID;
	private String value;
	private Integer id;

	public DsMetadataElement(){
		
	}
	
	public DsMetadataElement(String element, String qualifier, Integer schemaID, String value, Integer id){
		this.element = element;
		this.qualifier = qualifier;
		this.schemaID = schemaID;
		this.value = value;
		this.id = id;
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
}
