package com.cimmyt.rest;

import static com.cimmyt.utils.Constants.ERROR_MESSAGE_CREATION_BEAN;
import static com.cimmyt.utils.Constants.TEMPORAL_SAMPLE_SERVICE_BEAN_NAME;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;
import org.jboss.resteasy.annotations.ClientResponseType;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestParam;

import com.cimmyt.exception.BackException;
import com.cimmyt.model.bean.TemporalSample;
import com.cimmyt.rest.context.SpringApplicationContext;
import com.cimmyt.service.ServiceTemporalSample;
import com.cimmyt.utils.PropertyHelper;

@Path("/uploadSampleService")
public class UpLoadSampleService {
	private static Logger logger = Logger.getLogger(UpLoadSampleService.class);
	PropertyHelper pro= new PropertyHelper();
	private static ServiceTemporalSample serviceTemporalSample;
	static{
		try{
			serviceTemporalSample = (ServiceTemporalSample)SpringApplicationContext.getBean(TEMPORAL_SAMPLE_SERVICE_BEAN_NAME);
		}catch (NullPointerException e){
		logger.error(e.getMessage());	
		}
	}

	@POST
	@Path("/getTemSampleRepeat")
	@ClientResponseType(entityType=List.class)
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	public List<TemporalSample> getListTemSampleRepeat(@RequestParam List<TemporalSample> listTem) throws BackException{
		pro.setMessagesBundle(pro.setLanguageToLocale());
		try{
			return serviceTemporalSample.validateSampleDuplicate(listTem);
		}catch (Exception ex){
			logger.error(ex.getMessage());
			throw new BackException(pro.getKey(ERROR_MESSAGE_CREATION_BEAN));
		}
	}

	@PUT
	@Path("/setTemSampleRepeat")
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	public void setListTemSample(@RequestParam List<TemporalSample> listTem) throws BackException {
		pro.setMessagesBundle(pro.setLanguageToLocale());
		try{
			serviceTemporalSample.addListTempSample(listTem);
		}catch (Exception ex){
			ex.printStackTrace();
			logger.error(ex.getMessage());
			throw new BackException(pro.getKey(ERROR_MESSAGE_CREATION_BEAN));
		}
	}

	@POST
	@Path("/upDateListTemSampleRepeat")
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	public void upDateListTemSample(@RequestParam List<TemporalSample> listTem) throws BackException {
		pro.setMessagesBundle(pro.setLanguageToLocale());
		try{
			serviceTemporalSample.addListTempSample(listTem);
		}catch (Exception ex){
			ex.printStackTrace();
			logger.error(ex.getMessage());
			throw new BackException(pro.getKey(ERROR_MESSAGE_CREATION_BEAN));
		}
	}

	@DELETE
	@Path("/deleteListTemSamleRepeat")
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	public void deleteListTemSample(@RequestParam List<TemporalSample> listTem) throws BackException {
		pro.setMessagesBundle(pro.setLanguageToLocale());
		try{
			serviceTemporalSample.deleteListTemSample(listTem);
		}catch (Exception ex){
			ex.printStackTrace();
			logger.error(ex.getMessage());
			throw new BackException(pro.getKey(ERROR_MESSAGE_CREATION_BEAN));
		}
	}

	@POST
	@Path("/areThereAnySampleByIdResearcher")
	@ClientResponseType(entityType=Boolean.class)
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	public Boolean areThereAnySampleByIdResearcher(@RequestParam TemporalSample item) throws BackException{
		pro.setMessagesBundle(pro.setLanguageToLocale());
		try{
			return serviceTemporalSample.areThereAnyTempSamplesByIdResercher(item);
		}catch (Exception ex){
			ex.printStackTrace();
			logger.error(ex.getMessage());
			throw new BackException(pro.getKey(ERROR_MESSAGE_CREATION_BEAN));
		}
	}
	@POST
	@Path("/getListSampleByIdResearcher")
	@ClientResponseType(entityType=List.class)
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	public List<TemporalSample> getListSampleByIdResearcher(@RequestParam TemporalSample item) throws BackException{
		pro.setMessagesBundle(pro.setLanguageToLocale());
		try{
			return serviceTemporalSample.getListSampleByIdResearcher(item);
		}catch (Exception ex){
			ex.printStackTrace();
			logger.error(ex.getMessage());
			throw new BackException(pro.getKey(ERROR_MESSAGE_CREATION_BEAN));
		}
	}
}
