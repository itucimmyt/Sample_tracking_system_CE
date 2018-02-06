package org.cimmyt.service.study;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.cimmyt.utilTest.UtilTest;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cimmyt.bean.ProjectBean;
import com.cimmyt.bean.TissueBean;
import com.cimmyt.model.bean.LabStudy;
import com.cimmyt.service.ServiceLabStudy;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/context.xml","/dataAccessTest.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestStudy {

	@Autowired
	private ServiceLabStudy serviceLabStudy;

	@Test
	public void Test001CreateStudyWithSamples() throws SQLException{
		LabStudy imsLabStudy = getLabStudyOriginal();
		imsLabStudy.setTitle("Test_1");
		imsLabStudy.setObjective("Objective 2");
		serviceLabStudy.addLabStudy(imsLabStudy, false, null, null, null,1 );
		List<LabStudy> listStudy = serviceLabStudy.getLabStudys(imsLabStudy);
		Assert.assertEquals(1, listStudy.size());
	}

	@Test
	public void Test002UpdateEntityTest_1() throws SQLException{
		LabStudy imsLabStudy = getLabStudyOriginal();
		imsLabStudy.setTitle("Test_1");
		imsLabStudy.setObjective("Objective 2");
		List<LabStudy> listStudy = serviceLabStudy.getLabStudys(imsLabStudy);
		LabStudy imsLabStudyResult = serviceLabStudy.readStudyOnlySampleDetail(listStudy.get(0).getLabstudyid());
		serviceLabStudy.addLabStudy(imsLabStudyResult, true, null, null, null,1 );
		Assert.assertEquals(1, listStudy.size());
	}

	@Test
	public void Test004CreatingNewStudySameProject() throws SQLException{
		LabStudy imsLabStudy = getLabStudyOriginal();
		imsLabStudy.setTitle("Test_3");
		imsLabStudy.setObjective("Objective 3");
		serviceLabStudy.addLabStudy(imsLabStudy, false, null, null, null,1 );
		List<LabStudy> listStudy = serviceLabStudy.getLabStudys(imsLabStudy);
		Assert.assertEquals(1, listStudy.size());
	}
	
	@Test
	public void Test005updateStudyEntity(){
		serviceLabStudy.addLabStudy(getLabStudyOriginal(), false);
		List<LabStudy> listStudy = serviceLabStudy.getLabStudys(getLabStudyOriginal());
		Assert.assertEquals(1, listStudy.size());
		LabStudy bean = listStudy.get(0);
		bean.setTitle("Test_2");
		serviceLabStudy.addLabStudy(bean, true);
		List<LabStudy> listStudyEdit = serviceLabStudy.getLabStudys(bean);
		Assert.assertEquals(1, listStudyEdit.size());
		LabStudy beanEdit = listStudyEdit.get(0);
		Assert.assertEquals("Test_2", beanEdit.getTitle());
	}

	@Test
	public void Test006ThisProjectHasStudiesRegistered(){
		Assert.assertTrue(serviceLabStudy.ThisProjectHasStudiesRegistred(new ProjectBean(UtilTest.getProject(3))));
	}

	@Test
	public void Test007ThisResearcherHasStudiesRegistered (){
		Assert.assertTrue(serviceLabStudy.ThisInvestigatorHasStudiesRegistred(1));
	}

	@Test
	public void Test008ThisTissuehasLasbStudiesRegistered(){
		Assert.assertTrue(serviceLabStudy.ThisTissueHasLabStudyRegistred(new TissueBean(UtilTest.getTissue())));
	}

	@Test
	public void Test009This(){
		Assert.assertTrue(serviceLabStudy.ThisTemplateHasLabStudyRegistred(UtilTest.getStudyTemplate()));
	}

	@Test
	public void Test010ReadStudiesWithResults(){
		LabStudy imsLabStudy = getLabStudyOriginal();
		imsLabStudy.setTitle("Test_1");
		imsLabStudy.setObjective("Objective 2");
		List<LabStudy> listStudy = serviceLabStudy.getLabStudys(imsLabStudy);
		LabStudy imsLabStudyResult = serviceLabStudy.readStudyWithResults(listStudy.get(0).getLabstudyid());
		Assert.assertTrue(imsLabStudyResult.getSampleDetailCollection().iterator().next().getImsSampleDetResultCollection().size() > 0);
	}

	@Test
	public void Test011GetlabStudyByResearcher (){
		Assert.assertEquals(3, serviceLabStudy.getLabStudysByIdResearch(null, UtilTest.getInvestigator(1).getInvestigatorid()).size());
	}

	public void Test012GetlabStudyByResearcherWithTitleandPrefix(){
		Assert.assertEquals(3, serviceLabStudy.getLabStudysByIdResearch(getLabStudyOriginal(), UtilTest.getInvestigator(1).getInvestigatorid()).size());
	}
	
	@SuppressWarnings("deprecation")
	private LabStudy getLabStudyOriginal(){
		LabStudy bean = new LabStudy();
		bean.setTitle("Test_2");
		bean.setObjective("Objective 2");
		bean.setStartdate(new Date("11/04/2017"));
		bean.setEnddate(new Date ("11/04/2017"));
		bean.setPlatetype("R");
		bean.setPlatesize(96);
		bean.setNumindiv(282);
		bean.setNumofplates(3);
		bean.setPrefix("SEEDWAMI");
		bean.setNumcontrols(6);
		bean.setUsePadded(false);
		bean.setStudytemplateid(UtilTest.getStudyTemplate());
		bean.setProject(UtilTest.getProject(3));
		bean.setInvestigatorid(UtilTest.getInvestigator(1));
		bean.setOrganismid(UtilTest.getOrganism());
		bean.setTissue(UtilTest.getTissue());
		bean.setLocation(UtilTest.getLocationCatalog(2));
		bean.setSeason(UtilTest.getSeason(2));
		bean.setStatus(UtilTest.getStatus());
		bean.setLoadType(UtilTest.getLoadType());
		bean.setImsSampleDetailCollection(UtilTest.generateSamplesOf96SamplesByRow(3,bean));
		
		return bean;
	}

	
}
