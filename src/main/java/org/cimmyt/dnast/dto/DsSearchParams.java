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

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.jboss.resteasy.annotations.providers.jaxb.Wrapped;

@Provider
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@XmlRootElement(name="collection")
public class DsSearchParams {
	
	private List<DsSearchParam> searchParams;
	
	public DsSearchParams() {
		searchParams = new ArrayList<DsSearchParam>();
	}

	@Wrapped
	@XmlElement(name="searchParam")
	public List<DsSearchParam> getSearchParams() {
		return searchParams;
	}

	public void setParams(List<DsSearchParam> parameters) {
		this.searchParams = parameters;
	}
	
	public boolean addSearchParam(DsSearchParam parameter){
		return this.searchParams.add(parameter);
	}

	public boolean removeSearchParam(DsSearchParam parameter){
		return this.searchParams.remove(parameter);
	}

}
