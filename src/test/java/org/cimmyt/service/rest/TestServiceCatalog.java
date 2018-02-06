package org.cimmyt.service.rest;

import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cimmyt.bean.InvestigatorBean;
import com.cimmyt.exception.BackException;
import com.cimmyt.rest.CatalogsServices;
import com.cimmyt.rest.dto.CatalogDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/context.xml","/dataAccessTest.xml"})
public class TestServiceCatalog {
	static final String ROOT_URL = "http://localhost:8080/dna_sample_tracking_mvn/rest/catalogsServices/getListResearchers";

	
	public void testGetListCatalogResearcher ()  throws Exception{
		ClientRequest request = new ClientRequest(ROOT_URL);
		request.accept(MediaType.APPLICATION_JSON);
		ClientResponse<CatalogDTO> dto =	request.get(CatalogDTO.class);
		CatalogDTO stoE = dto.getEntity();
		System.out.println(stoE.getInvestigatorBeanList().isEmpty());
		for (InvestigatorBean in: stoE.getInvestigatorBeanList())
			System.out.println(in);
		Assert.assertNotNull(stoE);	
		
	}
	@Test
	public void getListCatalogOrganims() throws BackException{
		CatalogsServices cat = new CatalogsServices();
		Assert.assertNotNull(cat.getListCrops());
	}
	@Test
	public void getListCatalogResearcher() throws BackException{
		CatalogsServices cat = new CatalogsServices();
		Assert.assertNotNull(cat.getListResearchers());
	}
	
	@Test(expected=BackException.class)
	public void getListCatalogResearcherWithException() throws Exception{
	CatalogsServices cat = Mockito.mock(CatalogsServices.class);
	Mockito.doThrow(new BackException()).when(cat).getListResearchers();
		cat.getListResearchers();
	}

	@Test
	public void getListCatalogLocation() throws Exception{
		CatalogsServices cat = new CatalogsServices();
		Assert.assertNotNull(cat.getListLocation());
	}
	@Test
	public void getListCatalogSeason() throws Exception{
		CatalogsServices cat = new CatalogsServices();
		Assert.assertNotNull(cat.getListSeason());
	}
}
