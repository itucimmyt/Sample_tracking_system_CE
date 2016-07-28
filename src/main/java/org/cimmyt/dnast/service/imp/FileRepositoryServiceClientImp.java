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
package org.cimmyt.dnast.service.imp;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.cimmyt.dnast.dto.DsFile;
import org.cimmyt.dnast.dto.DsItem;
import org.cimmyt.dnast.dto.DsItemsRetrieved;
import org.cimmyt.dnast.dto.DsMetadataElement;
import org.cimmyt.dnast.dto.DsMetadataElements;
import org.cimmyt.dnast.dto.DsSchema;
import org.cimmyt.dnast.dto.DsSchemas;
import org.cimmyt.dnast.dto.DsScientist;
import org.cimmyt.dnast.dto.DsSearchParam;
import org.cimmyt.dnast.dto.DsSearchParams;
import org.cimmyt.dnast.service.FileRepositoryServiceClient;
import org.cimmyt.dnast.service.KBaseQueryService;
import org.jboss.resteasy.client.ClientExecutor;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.client.core.executors.ApacheHttpClient4Executor;
import org.zkoss.util.Dates;

import com.cimmyt.bean.InvestigatorBean;
import com.cimmyt.model.bean.Investigator;
import com.cimmyt.model.bean.LabStudy;
import com.cimmyt.model.bean.SampleDetail;
import com.cimmyt.model.bean.Shipment;
import com.cimmyt.service.ServiceInvestigator;
import com.cimmyt.service.ServiceSampleDetail;
import com.cimmyt.service.ServiceShipment;
import com.cimmyt.utils.CommonUtils;
import com.cimmyt.utils.PropertyHelper;
import com.cimmyt.utils.PropertyHelper.Bundle;


public class FileRepositoryServiceClientImp implements	FileRepositoryServiceClient {

	
	/*The id of the schema defined in Knowledge Base for the metadata of the DNA Sample Tracking*/
	public static int ID_SCHEMA_DNAST = 0;
	/*The name of the schema defined in Knowledge Base for the metadata of the DNA Sample Tracking*/
	private static final String SIU_SCHEMA = "cimmyt";
	private static final String DUBLIN_CORE_SCHEMA = "dc";
	/*A flag used to point the services in the KB to a local/remote base*/
	private int endpoint = KBaseQueryService.ENDPOINT_LOCAL;
	/*Contains the definition of the query created by the user*/
	 private DsSearchParams searchParams;
	 /*The proxy to create a client for the KB services*/
	 private KBaseQueryService proxy;
	 /*Contains the metadata fields displayed in the combobox*/
	 private Map<String, List<DsMetadataElement>> metadata = null;
	 /*Helper to get labels/messages from .properties files*/
	 private PropertyHelper prop;
	 /*Bean of service Shipment*/
	 private ServiceShipment serviceShipment;
	 private ServiceSampleDetail serviceSampleDet;
	 private ServiceInvestigator serviceInvestigator;
	 
		private enum META{
			 DC_DAT_ACCCNED("date","accessioned")
			,PROJ_NAME("project", "name")
			,PROJ_SRC("project","fundingsource")
			,AUTH_PRINC("authorship","principal")
			,AUTH_INTERN("authorship","internalcolab")
			,AUTH_EXTERN("authorship","externalcolab")
			,AUTH_PROGM("authorship","program")
			,AUTH_SUBPROG("authorship","subprog")
			,DATA_SETNAME("data","datasetname")
			,DATA_SETUPL("data","datasetupload")
			,DATA_TYP("data","datatype")
			,INV_POPNAME("inventory","popname")
			,INV_GID("inventory","gid")
			,INV_SAMPID("inventory","sampleid")
			,INV_PEDIGREE("inventory","pedigree")
			,STDY_DESC("study","description")
			,STDY_OBJ("study","objectives")
			,STDY_LOCA("study","location")
			,STDY_SESN("study","season")
			,STDY_NAME("study","studyname")
			,STDY_RESAR("study","researcharea")
			,STDY_GERM("study","germplasm")
			,STDY_CROP("study","crop")
			,STDY_CTRY("study","country")
			,STDY_YEAR("study","year")
			,IP_DTUSES("ipstatus","datauses")
			,STDYDOC_LANG("studydoc","language")
			,METHD_OTHR("method","others");
			 

				private final String key;
				private final String qualif;

			META(String key, String qualif){
				this.key = key;
				this.qualif = qualif;
			}
		}

	 
	 /*Contains the metadata fields with no qualifiers displayed in the combobox*/
	 private Map<String,DsMetadataElement> metadataListNoQualifiers;

	public FileRepositoryServiceClientImp(){
		searchParams = new DsSearchParams();
		prop = new PropertyHelper();

		metadataListNoQualifiers = new HashMap<String,DsMetadataElement>();
		
		

		isServiceAvailable();
	}
	 
	public FileRepositoryServiceClientImp(ServiceShipment serviceShipment,ServiceSampleDetail serviceSampleDet
			,ServiceInvestigator serviceInvestigator) {
		this();
		this.serviceShipment = serviceShipment;
		this.serviceSampleDet = serviceSampleDet;
		this.serviceInvestigator = serviceInvestigator;
		
	}
	
	@Override
	public FileRepositoryServiceClientImp addSearchParam(DsSearchParam param){
		searchParams.getSearchParams().add(param);
		return this;
	}

	@Override
	public FileRepositoryServiceClientImp addSearchParams(List<DsSearchParam> params){
		searchParams.getSearchParams().addAll(params);
		return this;
	}
	
	@Override
	public List<DsSchema> getSchemaList() {

		ClientResponse<DsSchemas> response = proxy.getSchemaList(endpoint);
		System.out.println("resp. status: "+response.getStatus());
		DsSchemas schemas = response.getEntity();
		response.releaseConnection();
		return schemas.getSchemas();
	}

	@Override
	public List<DsMetadataElement> getMetadataList() {
		List<DsMetadataElement> metadataList = null;
		if(metadataList == null && isServiceAvailable()){
			ClientResponse<DsMetadataElements> response = proxy.getFieldList(endpoint);
			DsMetadataElements metadataElements = response.getEntity();
			metadataList = metadataElements.getMetadatas();
			metadataListNoQualifiers = filterNoQualifiedElements(metadataList);
			response.releaseConnection();
		}
		return metadataList;
	}
	
	@Override
	public List<DsMetadataElement> getMetadataListBySchema(Integer idSchema) {
		if(!isServiceAvailable()){
			return null;
		}

		ClientResponse<DsMetadataElements> response = proxy.getFieldListBySchema(endpoint, idSchema);
		DsMetadataElements metadataElements = response.getEntity();
		metadataListNoQualifiers = filterNoQualifiedElements(metadataElements.getMetadatas());
		response.releaseConnection();

		return metadataElements.getMetadatas();
	}

	
	@Override
	public Set<String> getCategories(){
		List<DsMetadataElement> metadataList = null;
		metadata = new HashMap<String, List<DsMetadataElement>>();
		
		if(metadataList == null){
			try{
				List<DsSchema> schemas = getSchemaList();
				for(DsSchema s : schemas){
					if(s.getShortName().equals(SIU_SCHEMA)){
						ID_SCHEMA_DNAST = s.getId();
					}
			}
			}catch(Exception e){
				return null;
			}

			metadataList = getMetadataListBySchema(ID_SCHEMA_DNAST);
		}
		
		for(DsMetadataElement m : metadataList){
			if(metadata.containsKey(m.getElement())){
				metadata.get(m.getElement()).add(m);
			}else{
				metadata.put(m.getElement(), new ArrayList<DsMetadataElement>());
				metadata.get(m.getElement()).add(m);
			}
		}
		
		return metadata.keySet();
	}

	@Override
	public List<DsMetadataElement> getElementsByCategory(String category) {
		if(metadata == null){
			getCategories();
		}
		
		return metadata.get(category);
	}
	
	/**
	 * Returns a list of items containing the elements that match some creiteria.
	 * <br/>
	 * @param searchParams The object containing a list of filters to look up files.
	 * @return The list of files matching the filters given, or an empty list if there are no coincidences
	 */
	@Override
	public List<DsFile> requestItems(DsSearchParams searchParams) {

		if(!isServiceAvailable()){
			return null;
		}
		CommonUtils.marshallXMLInConsole(searchParams);
		ClientResponse<DsItemsRetrieved> response = proxy.getItemRequest(endpoint, searchParams);

		DsItemsRetrieved items = null;
		
		List<DsFile>fileList = new ArrayList<DsFile>();

		if(response.getResponseStatus().getStatusCode() == 200){
			items = response.getEntity();

			
			if(items.getItems() != null){
				DsSchema schemaSIU = null;
				
				
				for(DsItem i: items.getItems()){
					for(DsSchema schema: i.getSchemas()){
						if(schema.getName().equals(SIU_SCHEMA)){
							schemaSIU = schema;
							break;
						}
					}
					
					if(i.getFileList() != null){
						for(DsFile f: i.getFileList()){
							prepareFileItem(schemaSIU, f);
						}
						
						fileList.addAll(i.getFileList());
						
					}else{
						DsFile emptyFile = new DsFile();
						emptyFile.setSizeKB("0");
						emptyFile.setUrl("-");
						fileList.add(prepareFileItem(schemaSIU, emptyFile));
					}
				}
			}else{
				//null getItems
			}
		}else{
			//bad response
		}
		response.releaseConnection();
		return fileList;
	}
	
	/**
	 * Sets attibutes of the response of a query into a DSFile bean.
	 * @param schema the schema with the information from the service
	 * @param file The file to set.
	 * @return the object passed as parameter, with its properties set.
	 */
	private DsFile prepareFileItem(DsSchema schema, DsFile file){
		for(DsMetadataElement meta: schema.getMetadatas()){
			if(meta.getElement().equals(META.PROJ_NAME.key) && meta.getQualifier()!= null && 
				meta.getQualifier().equals(META.PROJ_NAME.qualif)){
				file.setProject(meta.getValue());
			}else if(meta.getElement().equals(META.AUTH_PRINC.key) && 
				meta.getQualifier()!= null && meta.getQualifier().equals(META.AUTH_PRINC.qualif)){
				file.setInvestigator(meta.getValue());
			}else if(meta.getElement().equals(META.DATA_SETNAME.key) && 
				meta.getQualifier()!= null && meta.getQualifier().equals(META.DATA_SETNAME.qualif)){
				file.setDataset(meta.getValue());
			}else if(meta.getElement().equals(META.INV_POPNAME.key) && 
				meta.getQualifier()!= null && meta.getQualifier().equals(META.INV_POPNAME.qualif)){
				file.setPopulation(meta.getValue());
			}
		}
		return file;
	}
	
	
	/**
	 * Sends a study tho the Knowledge Base. Invoke this method in the point where the application will send
	 * studies to the Knowledge Base, a batch proccess is recommended.
	 * <br/>
	 * @param idShipment The identifier of the study to register in the Knowledge Base
	 * @return a flag indicating if the operation was successful
	 */
	@Override
	public boolean sendShipmentToKBase(Integer idShipment){
		if(!isServiceAvailable()){
			return false;
		}
			
		DsItem responseItem;
		
		try{
			Shipment shipment =  serviceShipment.read(idShipment);
			List<SampleDetail> dets = serviceSampleDet.getSamplesByShipmentSet(shipment.getStShipmentSet());
			LabStudy study = dets.get(0).getLabstudyid();
		
			DsItem item = createIteminfo(shipment);
			addMetadata(item, study, shipment, dets);
			addScientist(item, study.getInvestigatorid());
			
System.out.println("item to send: ");
CommonUtils.marshallXMLInConsole(item);

			ClientResponse<DsItem> response = proxy.addItem(endpoint, item);
			responseItem = response.getEntity();
			response.releaseConnection();

CommonUtils.marshallXMLInConsole(responseItem);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}

		return responseItem.isAddFiles();
	}
		
	private DsItem createIteminfo(Shipment shipment){
		DsItem item = new DsItem();
			/*the id of the collection - GMP Genotypic Data, when using M/W,
			 * make this id dinamic using value from session*/
		item.setCollections(1);
		item.setAddFiles(true);
		item.setApp("dnast");
		item.setUid(shipment.getIdShipment().toString());
		return item;
	}
	
	
	/**
	 * Adds information to an item about a shipment.
	 * <br/>
	 * @param item The item to be sent to the Knowledge base
	 * @param study The study from which the information is taken;
	 * @param samples List of samples contained in the shipment to register
	 */
	private void addMetadata(DsItem item, LabStudy study, Shipment shipment, List<SampleDetail> samples){
		List<DsSchema> schemas = this.getSchemaList();
		DsSchema schema = null;
		DsSchema schemaDC = null;
		for(DsSchema s : schemas){
			if(s.getShortName().equals(SIU_SCHEMA)){//schema cimmyt
				schema = s;
			}else if(s.getShortName().equals(DUBLIN_CORE_SCHEMA)){
				schemaDC = s;
			}
		}

		schema.setMetadatas(this.getMetadataListBySchema(schema.getId()));
		schemaDC.setMetadatas(this.getMetadataListBySchema(schemaDC.getId()));

		StringBuilder gids = new StringBuilder();
		StringBuilder species = new StringBuilder();
		StringBuilder sampleIds = new StringBuilder();
		for(int i = 0;i<samples.size();i++){
			
			sampleIds.append(samples.get(i).getLabstudyid().getPrefix()+samples.get(i).getStudysampleid());
			if(i+1 < samples.size()){
				sampleIds.append(",");
			}

			if(samples.get(i).getBreedergid() != null){

				gids.append(samples.get(i).getBreedergid().toString());

				if(i+1 < samples.size()){
					gids.append(",");
				}
			}
			if(samples.get(i).getSpecie() != null){

				species.append(samples.get(i).getSpecie().toString());

				if(i+1 < samples.size()){
					species.append(",");
				}
			}
		}

		for (DsMetadataElement metadata : schema.getMetadatas()){
			if(metadata.getElement().equals(META.PROJ_NAME.key) && 
			metadata.getQualifier()!= null && metadata.getQualifier().equals(META.PROJ_NAME.qualif)){
				metadata.setValue( study.getProject().getProjectname() );
					
			}else if(metadata.getElement().equals(META.PROJ_SRC.key) && 
			metadata.getQualifier()!= null && metadata.getQualifier().equals(META.PROJ_SRC.qualif)){
					
				metadata.setValue( study.getProject().getPurposename() );
				
			}else if(metadata.getElement().equals(META.AUTH_PRINC.key) &&
			metadata.getQualifier()!= null && metadata.getQualifier().equals(META.AUTH_PRINC.qualif)){
				
				metadata.setValue( study.getInvestigatorid().getInvest_name() );
				
			}else if(metadata.getElement().equals(META.AUTH_INTERN.key) &&
			metadata.getQualifier()!= null && metadata.getQualifier().equals(META.AUTH_INTERN.qualif)){
				metadata.setValue("Not Available");
				
			}	
			else if(metadata.getElement().equals(META.INV_GID.key) && 
					metadata.getQualifier()!= null && metadata.getQualifier().equals(META.INV_GID.qualif)){
				metadata.setValue(gids.toString());
			}
			else if(metadata.getElement().equals(META.STDY_DESC.key) && 
					metadata.getQualifier()!= null && metadata.getQualifier().equals(META.STDY_DESC.qualif)){
				metadata.setValue(study.getObjective());
			}
			else if(metadata.getElement().equals(META.STDY_OBJ.key) && 
					metadata.getQualifier()!= null && metadata.getQualifier().equals(META.STDY_OBJ.qualif)){
				metadata.setValue(study.getObjective());
			}
			else if(metadata.getElement().equals(META.STDY_LOCA.key) && 
					metadata.getQualifier()!= null && metadata.getQualifier().equals(META.STDY_LOCA.qualif)){
				metadata.setValue(study.getLocation().toString());
			}
			else if(metadata.getElement().equals(META.STDY_SESN.key) && 
					metadata.getQualifier()!= null && metadata.getQualifier().equals(META.STDY_SESN.qualif)){
				metadata.setValue(study.getSeason().toString());
			}
			else if(metadata.getElement().equals(META.STDY_NAME.key) && 
					metadata.getQualifier()!= null && metadata.getQualifier().equals(META.STDY_NAME.qualif)){
				metadata.setValue(study.getTitle());
			}else if(metadata.getElement().equals(META.INV_POPNAME.key) && 
					metadata.getQualifier()!= null && metadata.getQualifier().equals(META.INV_POPNAME.qualif)){
				metadata.setValue("NA");
			}else if(metadata.getElement().equals(META.INV_SAMPID.key) && 
					metadata.getQualifier()!= null && metadata.getQualifier().equals(META.INV_SAMPID.qualif)){
				metadata.setValue(sampleIds.toString());
			}else if(metadata.getElement().equals(META.INV_PEDIGREE.key) && 
					metadata.getQualifier()!= null && metadata.getQualifier().equals(META.INV_PEDIGREE.qualif)){
				metadata.setValue("NA");
			}else if(metadata.getElement().equals(META.IP_DTUSES.key) && 
					metadata.getQualifier()!= null && metadata.getQualifier().equals(META.IP_DTUSES.qualif)){
				metadata.setValue("NA");
			}else if(metadata.getElement().equals(META.STDYDOC_LANG.key) && 
					metadata.getQualifier()!= null && metadata.getQualifier().equals(META.STDYDOC_LANG.qualif)){
				metadata.setValue("english");//take from session??
			}else if(metadata.getElement().equals(META.METHD_OTHR.key) && 
					metadata.getQualifier()!= null && metadata.getQualifier().equals(META.METHD_OTHR.qualif)){
				metadata.setValue(shipment.getStCompany().getName());
			}else if(metadata.getElement().equals(META.STDY_RESAR.key) && 
					metadata.getQualifier()!= null && metadata.getQualifier().equals(META.STDY_RESAR.qualif)){
				metadata.setValue("NA");
			}else if(metadata.getElement().equals(META.STDY_GERM.key) && 
					metadata.getQualifier()!= null && metadata.getQualifier().equals(META.STDY_GERM.qualif)){
				metadata.setValue("NA");
			}else if(metadata.getElement().equals(META.STDY_CROP.key) && 
					metadata.getQualifier()!= null && metadata.getQualifier().equals(META.STDY_CROP.qualif)){
				metadata.setValue("maize");//make dynamic
			}else if(metadata.getElement().equals(META.STDY_CTRY.key) && 
					metadata.getQualifier()!= null && metadata.getQualifier().equals(META.STDY_CTRY.qualif)){
				metadata.setValue("Mexico");//make dynamic
			}else if(metadata.getElement().equals(META.STDY_YEAR.key) && 
					metadata.getQualifier()!= null && metadata.getQualifier().equals(META.STDY_YEAR.qualif)){
				metadata.setValue(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
				
				Calendar myCal = Calendar.getInstance();
				myCal.setTime(study.getStartdate());
				metadata.setValue(String.valueOf(myCal.get(Calendar.YEAR)));
			}else if(metadata.getElement().equals(META.AUTH_EXTERN.key) && 
					metadata.getQualifier()!= null && metadata.getQualifier().equals(META.AUTH_EXTERN.qualif)){
				metadata.setValue("Not Available");
			}else if(metadata.getElement().equals(META.AUTH_PROGM.key) && 
					metadata.getQualifier()!= null && metadata.getQualifier().equals(META.AUTH_PROGM.qualif)){
				metadata.setValue("Not Available");
			}else if(metadata.getElement().equals(META.AUTH_SUBPROG.key) && 
					metadata.getQualifier()!= null && metadata.getQualifier().equals(META.AUTH_SUBPROG.qualif)){
				metadata.setValue("Not Available");
			}else if(metadata.getElement().equals(META.DATA_SETNAME.key) && 
					metadata.getQualifier()!= null && metadata.getQualifier().equals(META.DATA_SETNAME.qualif)){
				metadata.setValue(shipment.getComment());
			}else if(metadata.getElement().equals(META.DATA_SETUPL.key) && 
					metadata.getQualifier()!= null && metadata.getQualifier().equals(META.DATA_SETUPL.qualif)){
				metadata.setValue("true");
			}else if(metadata.getElement().equals(META.DATA_TYP.key) && 
					metadata.getQualifier()!= null && metadata.getQualifier().equals(META.DATA_TYP.qualif)){
				metadata.setValue("Genotypic data");
			}
			
		}
		
		for (DsMetadataElement metadata : schemaDC.getMetadatas()){
			if(metadata.getElement().equals(META.DC_DAT_ACCCNED.key) && 
			metadata.getQualifier()!= null && metadata.getQualifier().equals(META.DC_DAT_ACCCNED.qualif)){

				metadata.setValue(DateFormat.getInstance().format( GregorianCalendar.getInstance().getTime()) );
			}
		}
		List<DsMetadataElement> tmpMedatada = new ArrayList<DsMetadataElement>();
		
		for(DsMetadataElement metadata : schema.getMetadatas()){
			if(metadata.getValue()!= null && !metadata.getValue().isEmpty()){
				tmpMedatada.add(metadata);
			}
		}
		schema.setMetadatas(tmpMedatada);
		schema.setName(schema.getShortName());
		schema.setShortName(null);
		schema.setId(null);
		schema.setNameSpace(null);

		tmpMedatada = new ArrayList<DsMetadataElement>();

		for(DsMetadataElement metadata : schemaDC.getMetadatas()){
			if(metadata.getValue()!= null && !metadata.getValue().isEmpty()){
				tmpMedatada.add(metadata);
			}
		}
		schemaDC.setMetadatas(tmpMedatada);
		schemaDC.setName(schemaDC.getShortName());
		schemaDC.setShortName(null);
		schemaDC.setId(null);
		schemaDC.setNameSpace(null);
		
		
		schemas.clear();
		schemas.add(schema);
		schemas.add(schemaDC);
	
		item.setSchemas(schemas);
	}

	/**
	 * Adds information to an item about the scientist that created a study
	 * <br/>
	 * @param item The item to be sent to the Knowledge base
	 * @param study The study from which the scientist information is taken;
	 */
	private void addScientist(DsItem item, Investigator researcher){
		DsScientist scientist = new DsScientist();
		//uncomment when autentication is complete for kbase
		//scientist.setEmail(researcher.getInvest_email());
		//scientist.setName(researcher.getInvest_name());
		scientist.setEmail("R.Shrestha2@cgiar.org");
		scientist.setName("Rosemary");
		
		item.setScientist(scientist);
	}
	
	/**
	 * Checks if the connection to Knowledge Base is available, if there is, configures it.
	 * @return A flag indicating if there is communication with Knowledge Base services
	 */
	@Override
	public boolean isServiceAvailable() {
		if(proxy == null){
			try{
				HttpClient httpClient = new DefaultHttpClient();
				HttpParams params = httpClient.getParams();
				
				HttpConnectionParams.setConnectionTimeout(params, Integer.parseInt( prop.getKey("service.kbase.connectionTimeout",Bundle.conf)) );
				HttpConnectionParams.setSoTimeout(params, Integer.parseInt( prop.getKey("service.kbase.socketTimeout",Bundle.conf)) );

				ClientExecutor executor = new ApacheHttpClient4Executor(httpClient);
				
				proxy = ProxyFactory.create(KBaseQueryService.class, prop.getKey("service.kbase.url",Bundle.conf), executor);
				proxy.getSchemaList(endpoint).getEntity();
			}catch(Exception e){
				proxy = null;
				//e.printStackTrace();
				System.out.println("KBServices not available.");
			}
		}
		return proxy != null;
	}

	private Map<String, DsMetadataElement> filterNoQualifiedElements(List<DsMetadataElement> elements){
		Map<String, DsMetadataElement> noQualifiedElmts = new HashMap<String, DsMetadataElement>();
		for(DsMetadataElement l : elements){
			if(l.getQualifier() == null || l.getQualifier().isEmpty()){
				noQualifiedElmts.put(l.getElement(), l);
			}
		}
		return noQualifiedElmts;
	}
	
	@Override
	public DsMetadataElement getNoQualifiedMetadata(String key){
		return metadataListNoQualifiers.get(key);
	}

	@Override
	public void setEndpoint(int endpoint) {
		this.endpoint = endpoint;
	}

	@Override
	public void sendPendingShipmentsToKBase() {
		System.out.println("checking for pending shipments...");
		Shipment ship = new Shipment();
		ship.setIsSentToKB(false);
		List<Shipment> ships = serviceShipment.getShipments(ship);
		
		if(ships != null && !ships.isEmpty())
			for(Shipment s : ships){
				System.out.println("updating shipment with id: "+s.getIdShipment());
				
				if( sendShipmentToKBase(s.getIdShipment()) ){
					s.setIsSentToKB(true);
					serviceShipment.saveOrUpdateShipment(s);
				}
			}
	}
	
	public ServiceShipment getServiceShipment() {
		return serviceShipment;
	}
	public void setServiceShipment(ServiceShipment serviceShipment) {
		this.serviceShipment = serviceShipment;
	}
	public ServiceSampleDetail getServiceSampleDet() {
		return serviceSampleDet;
	}
	public void setServiceSampleDet(ServiceSampleDetail serviceSampleDet) {
		this.serviceSampleDet = serviceSampleDet;
	}
	public ServiceInvestigator getServiceInvestigator() {
		return serviceInvestigator;
	}
	public void setServiceInvestigator(ServiceInvestigator serviceInvestigator) {
		this.serviceInvestigator = serviceInvestigator;
	}

}
