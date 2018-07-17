package com.cimmyt.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

public class MessageApplicationDNA extends Application{

	private Set<Object> singletons = new HashSet<Object>();

	public MessageApplicationDNA (){
		singletons.add(new CatalogsServices());
		singletons.add(new UpLoadSampleService());
		
		
		/*
		
		try {
			 System.out.println("Scanning ..................................");
			 
		      
		       BeanConfig beanConfig = new BeanConfig();
		       beanConfig.setTitle("Search engine");
		       beanConfig.setVersion("1.0");
		       beanConfig.setSchemes(new String[]{"http"});
		       beanConfig.setHost("localhost:8080");
		       beanConfig.setBasePath("/dna_sample_tracking");
		       beanConfig.setResourcePackage("com.cimmyt.rest");
		       beanConfig.setScan(true);
		       beanConfig.setDescription("Faster full text search engine");
		   
			}catch (Exception ex) {
				ex.printStackTrace();
			}*/
	}

	
	public Set<Object> getSingletons(){
		return singletons;
	}
}
