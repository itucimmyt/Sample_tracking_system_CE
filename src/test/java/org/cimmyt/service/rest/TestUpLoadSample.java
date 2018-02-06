package org.cimmyt.service.rest;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cimmyt.exception.BackException;
import com.cimmyt.model.bean.Investigator;
import com.cimmyt.model.bean.LocationCatalog;
import com.cimmyt.model.bean.Organism;
import com.cimmyt.model.bean.Season;
import com.cimmyt.model.bean.TemporalSample;
import com.cimmyt.model.dao.TempSampleDAO;
import com.cimmyt.model.dao.impl.TempSampleDAOImpl;
import com.cimmyt.rest.UpLoadSampleService;
import com.cimmyt.service.ServiceTemporalSample;
import com.cimmyt.service.impl.ServiceTemporalSampleImpl;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/context.xml","/dataAccessTest.xml"})
public class TestUpLoadSample {

	
	private List<TemporalSample> listTempSample = new ArrayList<TemporalSample>();
	private UpLoadSampleService upLoadService;
	@Autowired
	private ServiceTemporalSample serviceTemporalSample;
	private TempSampleDAO mockTempSampleDAO;
	@Autowired
	private TempSampleDAO tempSampleDAO;
	
	private TemporalSample sampleTem;
	@Before
	public void init() throws Exception{
		listTempSample.add(getItem1());
		listTempSample.add(getItem2());
		upLoadService = new UpLoadSampleService();
		mockTempSampleDAO = Mockito.mock(TempSampleDAOImpl.class);
		sampleTem = new TemporalSample();
		sampleTem.setResearcher(getInvestigator());
	}

	
	@Test
	public void getListSampleNotRepeat() throws BackException{
	List <TemporalSample>listTem = upLoadService.getListTemSampleRepeat(listTempSample);
	Assert.assertTrue(listTem.isEmpty());
	}
	@Test
	public void getListSampleRepeat() throws BackException{
		Mockito.when(mockTempSampleDAO.getTempsample(getItem1())).thenReturn(getItem1()  );
		((ServiceTemporalSampleImpl)serviceTemporalSample).setTemporalSampleDAO(mockTempSampleDAO);
		List <TemporalSample>listTem = upLoadService.getListTemSampleRepeat(listTempSample);
		Assert.assertFalse(listTem.isEmpty());
	}

	@Test (expected = com.cimmyt.exception.BackException.class)
	public void getExceptionFromSelectRecord() throws BackException {
		Mockito.when(mockTempSampleDAO.getTempsample(getItem1())).thenThrow( new NullPointerException() );
		((ServiceTemporalSampleImpl)serviceTemporalSample).setTemporalSampleDAO(mockTempSampleDAO);
		upLoadService.getListTemSampleRepeat(listTempSample);
	}

	//@Test 
	public void testSetTemSamples() throws BackException{
		upLoadService = new UpLoadSampleService();
		upLoadService.setListTemSample(listTempSample);
		Assert.assertTrue(upLoadService.areThereAnySampleByIdResearcher(sampleTem));
	}

	//@Test
	public void upDateSample() throws BackException{
		upLoadService = new UpLoadSampleService();
		upLoadService.setListTemSample(listTempSample);
		List<TemporalSample> listemp = upLoadService.getListSampleByIdResearcher(sampleTem);
		System.out.println("listemp "+listemp);
		listemp.get(0).setAcc("CCC");
		
		upLoadService.upDateListTemSample(listemp);
		listemp = upLoadService.getListSampleByIdResearcher(sampleTem);
		Assert.assertTrue(listemp.get(0).getAcc().equals("CCC"));
	}

	//@Test
	public void deleteSamplesTemp()throws BackException{
		upLoadService = new UpLoadSampleService();
		List<TemporalSample> listemp = upLoadService.getListSampleByIdResearcher(sampleTem);
		upLoadService.deleteListTemSample(listemp);
		Assert.assertFalse(upLoadService.areThereAnySampleByIdResearcher(sampleTem));
	}
	@org.junit.After
	public void afterAction() throws BackException{
		upLoadService = new UpLoadSampleService();
		List<TemporalSample> listemp = upLoadService.getListSampleByIdResearcher(sampleTem);
		upLoadService.deleteListTemSample(listemp);
	}
	
	private TemporalSample getItem2(){
		TemporalSample item1 = new TemporalSample();
		item1.setGid(2);
		item1.setAcc("BB");
		item1.setEntryNo(1);
		item1.setLocation(getCatLoc());
		item1.setPlantNo(1);
		item1.setSeason(getSeason());
		item1.setSpecie(getOrganism());
		item1.setResearcher(getInvestigator());
		return item1;
	}

	private TemporalSample getItem1(){
		TemporalSample item1 = new TemporalSample();
		item1.setGid(1);
		item1.setAcc("AA");
		item1.setEntryNo(1);
		item1.setLocation(getCatLoc());
		item1.setPlantNo(1);
		item1.setSeason(getSeason());
		item1.setSpecie(getOrganism());
		item1.setResearcher(getInvestigator());
		return item1;
	}
	private LocationCatalog getCatLoc(){
		LocationCatalog location = new LocationCatalog();
		location.setLocation_description("des1");
		location.setLocation_name("Name1");
		location.setLocationid(1);
		return location;
	}
	private Season getSeason(){
		Season season = new Season ();
		season.setSeason_description("des1");
		season.setSeason_name("Sname1");
		season.setSeasonid(1);
		return season;
	}
	private Organism getOrganism(){
		Organism org = new Organism();
		org.setOrganismid(1);
		org.setOrganismname("MAIZE");
		return org;
	}
	private Investigator getInvestigator(){
		Investigator inv = new Investigator();
		inv.setInvest_abbreviation("AA");
		//inv.setInvest_email("email");
		inv.setInvest_name("NameINv");
		//inv.setInvest_phone("111222");
		//inv.setInvest_pwd("password");
		inv.setInvestigatorid(2);
		return inv;
	}
}
