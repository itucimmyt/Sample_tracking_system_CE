package com.cimmyt.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

public class MessageApplicationDNA extends Application{

	private Set<Object> singletons = new HashSet<Object>();

	public MessageApplicationDNA (){
		singletons.add(new CatalogsServices());
		singletons.add(new UpLoadSampleService());
	}

	public Set<Object> getSingletons(){
		return singletons;
	}
}
