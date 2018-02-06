package com.cimmyt.rest.client.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.zkoss.json.JSONArray;
import org.zkoss.json.JSONObject;
import org.zkoss.json.parser.JSONParser;

import com.cimmyt.rest.client.ClientCatalogContry;
import com.cimmyt.rest.client.Container;
import com.cimmyt.rest.client.Result;
import com.cimmyt.utils.Constants;

public class ClientCatalogContryImpl implements ClientCatalogContry{

	public List<Result> getListContry(){
		ClientRequest request;
		ClientResponse<Container> response;
		List<Result> list = new ArrayList<Result>();
		try{
			
			request = new ClientRequest(Constants.URL_SERVICE_CONTRY);
			request.accept(MediaType.APPLICATION_JSON);
			
			response =	request.get(Container.class);
			 if (response.getStatus() != 200)
				{
					throw new RuntimeException("Failed : HTTP error code : "+
							response.getStatus());
				}
			 	JSONParser parser = new JSONParser();
			 	String str = response.getEntity(String.class);
			 	Object obj = parser.parse(str);
			 	 JSONObject jsonObject = (JSONObject) obj;
			 	JSONObject restResponse = (JSONObject) jsonObject.get("RestResponse");
			 	JSONArray msg = (JSONArray) restResponse.get("result");
			 
			 	@SuppressWarnings("rawtypes")
				Iterator  i = msg.iterator();
			 	while (i.hasNext()){
			 		Result contry = new Result ();
			 		JSONObject item  =(JSONObject) i.next();
			 		String name = (String)item.get("name");
			 		contry.setName(name);
			 		list.add(contry);
			 	}
			 return list;

		}catch (Exception ex){
				ex.printStackTrace();
		}finally {
			request = null;
			response = null;
			list = null;
		}
		
		return null;
	}

	public static void main (String ... args){
		ClientCatalogContryImpl  test = new ClientCatalogContryImpl();
		System.out.println(test.getListContry());
	}
}
