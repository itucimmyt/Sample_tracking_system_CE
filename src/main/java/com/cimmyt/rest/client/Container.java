
package com.cimmyt.rest.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class Container implements java.io.Serializable{

	
	private RestResponse RestResponse = new RestResponse() ;

	@JsonIgnoreProperties(ignoreUnknown = true)
	public RestResponse getRestResponse() {
		return RestResponse;
	}

	public void setRestResponse(RestResponse RestResponse) {
		this.RestResponse = RestResponse;
	}
}
