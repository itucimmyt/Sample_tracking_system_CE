package org.cimmyt.service.application;

import java.util.Date;
import java.util.List;

import org.cimmyt.utilTest.UtilTest;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import com.cimmyt.model.bean.LabStudy;
import com.cimmyt.service.ServiceLabStudy;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={"classpath:/context.xml","/dataAccessTest.xml"})
public class TestStudy {

	@Autowired
	private ServiceLabStudy serviceLabStudy;


	//@Test
	public void updateStudyEntity(){
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

	@SuppressWarnings("deprecation")
	private LabStudy getLabStudyOriginal(){
		LabStudy bean = new LabStudy();
		bean.setTitle("Test_1");
		bean.setObjective("Objective 1");
		bean.setStartdate(new Date("11/04/2017"));
		bean.setEnddate(new Date ("11/04/2017"));
		bean.setPlatetype("R");
		bean.setPlatesize(96);
		bean.setNumindiv(282);
		bean.setNumofplates(21);
		bean.setPrefix("SEEDWAMI");
		bean.setNumcontrols(24);
		bean.setUsePadded(false);
		bean.setStudytemplateid(UtilTest.getStudyTemplate());
		bean.setProject(UtilTest.getProject(2));
		bean.setInvestigatorid(UtilTest.getInvestigator(1));
		bean.setOrganismid(UtilTest.getOrganism());
		bean.setTissue(UtilTest.getTissue());
		bean.setLocation(UtilTest.getLocationCatalog(2));
		bean.setSeason(UtilTest.getSeason(2));
		bean.setStatus(UtilTest.getStatus());
		bean.setLoadType(UtilTest.getLoadType());
		
		return bean;
	}
	
}
