package com.cimmyt.rest;

import static com.cimmyt.utils.Constants.ERROR_MESSAGE_CREATION_BEAN;
import static com.cimmyt.utils.Constants.INVESTIGATOR_SERVICE_BEAN_NAME;
import static com.cimmyt.utils.Constants.LOCATION_SERVICE_BEAN_NAME;
import static com.cimmyt.utils.Constants.ORGANISM_SERVICE_BEAN_NAME;
import static com.cimmyt.utils.Constants.SEASON_SERVICE_BEAN_NAME;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;
import org.jboss.resteasy.annotations.ClientResponseType;
import org.springframework.http.MediaType;

import com.cimmyt.bean.InvestigatorBean;
import com.cimmyt.bean.LocationBean;
import com.cimmyt.bean.SeasonBean;
import com.cimmyt.exception.BackException;
import com.cimmyt.model.bean.Organism;
import com.cimmyt.rest.context.SpringApplicationContext;
import com.cimmyt.rest.dto.CatalogDTO;
import com.cimmyt.service.ServiceInvestigator;
import com.cimmyt.service.ServiceLocation;
import com.cimmyt.service.ServiceOrganism;
import com.cimmyt.service.ServiceSeason;
import com.cimmyt.utils.PropertyHelper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
@Api(value = "/app")
@Path("/catalogsServices")
public class CatalogsServices {

	private static Logger logger = Logger.getLogger(CatalogsServices.class);
	PropertyHelper pro= new PropertyHelper();
	private static ServiceInvestigator serviceInvestigator;
	private static ServiceOrganism serviceOrganism;
	private static ServiceLocation serviceLocation;
	private static ServiceSeason serviceSeason;
	static{
		try{
			serviceInvestigator = (ServiceInvestigator)SpringApplicationContext.getBean(INVESTIGATOR_SERVICE_BEAN_NAME);
			serviceOrganism = (ServiceOrganism)SpringApplicationContext.getBean(ORGANISM_SERVICE_BEAN_NAME);
			serviceLocation = (ServiceLocation)SpringApplicationContext.getBean(LOCATION_SERVICE_BEAN_NAME);
			serviceSeason = (ServiceSeason)SpringApplicationContext.getBean(SEASON_SERVICE_BEAN_NAME);
		}catch (NullPointerException e){
		logger.error(e.getMessage());	
		}
	}
	@GET
	@Path ("/getListOrganism")
	@ApiOperation(value = "Get list of example resources", 
    notes = "Note test", 
    response = CatalogDTO.class
    //responseContainer = "object"
			)
	@ClientResponseType(entityType=CatalogDTO.class)
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public CatalogDTO getListCrops() throws BackException{
		pro.setMessagesBundle(pro.setLanguageToLocale());
		CatalogDTO catalog = new CatalogDTO();
		try{
			catalog.setListOrganism(serviceOrganism.getOrganisms(new Organism()));
		return catalog;
		}catch (Exception ex){
			logger.error(ex.getMessage());
			throw new BackException(pro.getKey(ERROR_MESSAGE_CREATION_BEAN));
		}
	}

	@GET
	@Path ("/getListResearchers")
	@ClientResponseType(entityType=CatalogDTO.class)
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public CatalogDTO getListResearchers() throws BackException{
		pro.setMessagesBundle(pro.setLanguageToLocale());
		CatalogDTO catalog = new CatalogDTO();
		try{
			catalog.setInvestigatorBeanList(serviceInvestigator.getInvestigator(new InvestigatorBean()));
		return catalog;
		}catch (Exception ex){
			logger.error(ex.getMessage());
			throw new BackException(pro.getKey(ERROR_MESSAGE_CREATION_BEAN));
		}
	}

	@GET
	@Path ("/getListLocation")
	@ClientResponseType(entityType=CatalogDTO.class)
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public CatalogDTO getListLocation() throws BackException{
		pro.setMessagesBundle(pro.setLanguageToLocale());
		CatalogDTO catalog = new CatalogDTO();
		try{
			catalog.setListLocation(serviceLocation.getLocation(new LocationBean()));
		return catalog;
		}catch (Exception ex){
			logger.error(ex.getMessage());
			throw new BackException(pro.getKey(ERROR_MESSAGE_CREATION_BEAN));
		}
	}
	@GET
	@Path ("/getListSeason")
	@ClientResponseType(entityType=CatalogDTO.class)
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public CatalogDTO getListSeason() throws BackException{
		pro.setMessagesBundle(pro.setLanguageToLocale());
		CatalogDTO catalog = new CatalogDTO();
		try{
			catalog.setListSeasonBean(serviceSeason.getSeasons(new SeasonBean()));
		return catalog;
		}catch (Exception ex){
			logger.error(ex.getMessage());
			throw new BackException(pro.getKey(ERROR_MESSAGE_CREATION_BEAN));
		}
	}
}
