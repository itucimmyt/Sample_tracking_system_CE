package org.cimmyt.brapi;

import org.junit.Test;
import org.wso2.client.api.ApiClient;
import org.wso2.client.api.ApiException;
import org.wso2.client.api.Brapi_Multicrop.StudiesApi;
import org.wso2.client.model.Brapi_Multicrop.BrapiPagedResponseBrEntryTO;
import org.wso2.client.model.Brapi_Multicrop.BrapiPagedResponseBrStudyTO;
import static com.cimmyt.utils.Constants.BMS_SERVICE_API;


public class TestBrapi {

	
	StudiesApi api = new StudiesApi();
	
	//@Test
	public void testApi(){
		try {
			
			ApiClient apicliente = new ApiClient();
			apicliente.setBasePath(BMS_SERVICE_API);
			//apicliente.setBasePath("http://172.17.61.7:9080");
			
			//apicliente.setAccessToken("0ffcacce-be5c-3405-9243-f562e72e0925");
			
			api.setApiClient(apicliente);
		
			BrapiPagedResponseBrStudyTO  brapiStudy = api.searchStudies(null);
			System.out.println(" list "+brapiStudy.getResult().getData().toString());
			BrapiPagedResponseBrEntryTO brapi =api.getGermplasmByStudyId(7, null);
			System.out.println("Api :"+brapi.getResult().getData().get(0).toString());
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testIdIntegerNegative(){
		Integer id = new Integer ("-1");
		if (id.toString().equals("-1")){
			System.out.println(id);
		}
		
	}
}
