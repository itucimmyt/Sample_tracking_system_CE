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
package org.cimmyt.dnast.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.cimmyt.dnast.dto.DsItem;
import org.cimmyt.dnast.dto.DsItemsRetrieved;
import org.cimmyt.dnast.dto.DsMetadataElements;
import org.cimmyt.dnast.dto.DsSchemas;
import org.cimmyt.dnast.dto.DsSearchParams;
import org.jboss.resteasy.annotations.ClientResponseType;
import org.jboss.resteasy.client.ClientResponse;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Defines the services provided by KB to search files.
 */
@Path("/kb")
public interface KBaseQueryService {

	public static final int ENDPOINT_LOCAL = 0;
	public static final int ENDPOINT_REMOTE = 1;
	
	@GET
	@Path("field/list")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public ClientResponse<DsMetadataElements> getFieldList(@QueryParam("endpoint")int endpoint);
	
	
	@GET
	@Path("field/list/schema/{idSchema}")
	@ClientResponseType(entityType=DsMetadataElements.class)
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public ClientResponse<DsMetadataElements> getFieldListBySchema(@QueryParam("endpoint")int endpoint
																,@PathParam("idSchema") Integer idSchema);

	@GET
	@Path("schema/list")
	@ClientResponseType(entityType=DsSchemas.class)
	@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
	public ClientResponse<DsSchemas> getSchemaList(@QueryParam("endpoint")int endpoint);

	@POST
	@Path("item/request")
	@ClientResponseType(entityType=DsItemsRetrieved.class)
	@Produces({MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_XML})
	public ClientResponse<DsItemsRetrieved> getItemRequest(	@QueryParam("endpoint")int endpoint,
															@RequestParam DsSearchParams params);

	@POST
	@Path("item/add")
	@ClientResponseType(entityType=DsItemsRetrieved.class)
	@Produces({MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_XML})
	public ClientResponse<DsItem> addItem(	@QueryParam("endpoint")int endpoint,
											@RequestParam DsItem item);

}
