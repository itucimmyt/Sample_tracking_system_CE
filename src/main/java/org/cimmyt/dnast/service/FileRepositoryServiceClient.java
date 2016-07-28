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

import java.util.List;
import java.util.Set;

import org.cimmyt.dnast.dto.DsFile;
import org.cimmyt.dnast.dto.DsMetadataElement;
import org.cimmyt.dnast.dto.DsSchema;
import org.cimmyt.dnast.dto.DsSearchParam;
import org.cimmyt.dnast.dto.DsSearchParams;

public interface FileRepositoryServiceClient {
	//methods for testing(hardcoded) service
	public FileRepositoryServiceClient addSearchParam(DsSearchParam param);
	public FileRepositoryServiceClient addSearchParams(List<DsSearchParam> params);
	
	public List<DsMetadataElement> getMetadataList();
	public List<DsMetadataElement> getMetadataListBySchema(Integer idSchema);
	public Set<String> getCategories();
	
	//methods for real queries
	public List<DsSchema> getSchemaList();
	
	public List<DsMetadataElement> getElementsByCategory(String category);
	public List<DsFile> requestItems(DsSearchParams params);
	public boolean sendShipmentToKBase(Integer idShipment);
	public void sendPendingShipmentsToKBase();
	public boolean isServiceAvailable();
	public DsMetadataElement getNoQualifiedMetadata(String key);
	public void setEndpoint(int endpoint);

}
