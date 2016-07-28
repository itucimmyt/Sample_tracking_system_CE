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

import java.util.List;

import javax.ws.rs.ext.Provider;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.jboss.resteasy.annotations.providers.jaxb.Wrapped;

@Provider
@XmlRootElement(name="item")
public class DsItem {

	private List<DsFile> fileList;
	private List<DsSchema> schemas;
	
	//needed for adding new items to KBase
	private Integer collections;
	private boolean addFiles;
	private String app;
	private String uid;
	private DsScientist scientist;

	//The return message of adding items to KBase
	private String message;

	@Wrapped
	@XmlElement(name="file")
	public List<DsFile> getFileList() {
		return fileList;
	}
	public void setFileList(List<DsFile> fileList) {
		this.fileList = fileList;
	}

	@Wrapped
	@XmlElement(name="schema")
	public List<DsSchema> getSchemas() {
		return schemas;
	}
	public void setSchemas(List<DsSchema> schemas) {
		this.schemas = schemas;
	}
	public Integer getCollections() {
		return collections;
	}
	public void setCollections(Integer collections) {
		this.collections = collections;
	}
	public boolean isAddFiles() {
		return addFiles;
	}
	public void setAddFiles(boolean addFiles) {
		this.addFiles = addFiles;
	}
	public String getApp() {
		return app;
	}
	public void setApp(String app) {
		this.app = app;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public DsScientist getScientist() {
		return scientist;
	}
	public void setScientist(DsScientist scientist) {
		this.scientist = scientist;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
