package com.cimmyt.bms.client;

import static com.cimmyt.utils.Constants.BMS_SERVICE_API;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.util.GenericType;

import com.cimmyt.bean.UserBean;
import com.cimmyt.bms.client.dto.GermplamsDetail;
import com.cimmyt.bms.client.dto.Germplasm;
import com.cimmyt.bms.client.dto.GermplasmEntries;
import com.cimmyt.bms.service.BMSService;
import com.cimmyt.model.bean.LocationCatalog;
import com.cimmyt.model.bean.Organism;
import com.cimmyt.model.bean.Season;
import com.cimmyt.model.bean.TemporalSample;
import com.cimmyt.service.ServiceTemporalSample;

public class RESTSpringClientBMSAPI implements BMSService{
	
	private String http = "http://";
	private String nameServiceBMSAPI = "bmsapi/germplasmList/";
	private ServiceTemporalSample serviceTemporalSample;
			
	@SuppressWarnings("rawtypes")
	public List<Germplasm>  getListGermplasm(String urlOrServiceName, String crop){
		
		try{
			List<Germplasm> listGermplasm = null;
			ClientRequest request = new ClientRequest(http+urlOrServiceName+nameServiceBMSAPI
					+ crop+"/all");
			System.out.println("URL :: "+http+urlOrServiceName+nameServiceBMSAPI
					+ crop+"/all" );
			ClientResponse<List> response = request.get(List.class);
			if (response.getStatus() != 200)
			{
				throw new RuntimeException("Failed : HTTP error code : "+
						response.getStatus());
			}
			listGermplasm = response.getEntity(new GenericType<List<Germplasm>>(){});
			return listGermplasm;
			
		}catch (ClientProtocolException ex){
			ex.printStackTrace();
		}catch (Exception exG){
			exG.printStackTrace();
		}
		return null;
	}

	
	public GermplamsDetail getListGermplamsDetail (String urlOrServiceName, String crop, int idList){
		try{
			GermplamsDetail germplamsDetail = null;
			ClientRequest request = new ClientRequest(http+urlOrServiceName+nameServiceBMSAPI
					+ crop+"/"+idList);
			ClientResponse<GermplamsDetail> response = request.get(GermplamsDetail.class);
			if (response.getStatus() != 200)
			{
				throw new RuntimeException("Failed : HTTP error code : "+
						response.getStatus());
			}
			germplamsDetail = response.getEntity(new GenericType<GermplamsDetail>(){});
			return germplamsDetail;
		}catch (ClientProtocolException ex){
			ex.printStackTrace();
		}catch (Exception exG){
			exG.printStackTrace();
		}
		return null;
	}

	
	public static void main (String ... args){
		RESTSpringClientBMSAPI client = new RESTSpringClientBMSAPI();
		int idlist= 18234;
		GermplamsDetail germplamsDetail =	client.getListGermplamsDetail("api.leafnode.io:10081/","wheat", idlist);
		
		for (GermplasmEntries entry : germplamsDetail.getGermplasmEntries()){
			System.out.println(entry);
		}
	}

	public int saveGermplasm (List<Germplasm> listGermplasmSelect ,UserBean userBean,
								Season season, LocationCatalog location ){
		int size = 0;
		 List<TemporalSample> listTemporalSample = new ArrayList<TemporalSample>();
		 for (Germplasm germplasm : listGermplasmSelect){
			 GermplamsDetail detail = getListGermplamsDetail(BMS_SERVICE_API, userBean.getCorp().toLowerCase(), germplasm.getListId());
			 if (detail != null && detail.getGermplasmEntries() != null
						&& !detail.getGermplasmEntries().isEmpty()){
				for (GermplasmEntries entries : detail.getGermplasmEntries()){
					if (!entries.getCross().equals("LOCAL CHECK")){
						TemporalSample temp =  new TemporalSample();
						temp.setGid(entries.getGid());
						temp.setAcc(entries.getCross());
						temp.setEntryNo(new Integer(1));
						temp.setLocation(location);
						temp.setSeason(season);
						temp.setPlantNo(new Integer(1));
						temp.setResearcher(userBean.getInvestigatorBean().getInvestigator(
								userBean.getInvestigatorBean()));
						temp.setSpecie(getOrganism(userBean));
						listTemporalSample.add(temp);
					}
				}
				if (!listTemporalSample.isEmpty()){
					serviceTemporalSample.addListTempSampleWithSesson(listTemporalSample);
					size = listTemporalSample.size();
				}else
					return 0;
			 }
		}
		 return size;
	}


	private Organism getOrganism(UserBean userBean){
		Organism organism = new Organism();
		organism.setOrganismid(userBean.getTypeCorp());
		organism.setOrganismname(userBean.getCorp().toUpperCase());
		return organism;
	}
	
	public void setServiceTemporalSample(ServiceTemporalSample serviceTemporalSample) {
		this.serviceTemporalSample = serviceTemporalSample;
	}

	
}
