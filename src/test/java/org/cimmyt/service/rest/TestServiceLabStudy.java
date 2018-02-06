package org.cimmyt.service.rest;

import static com.cimmyt.utils.Constants.SHOW_ROWS_LIST;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cimmyt.bean.ProjectBean;
import com.cimmyt.model.bean.LabStudy;
import com.cimmyt.service.ServiceLabStudy;
import com.cimmyt.utils.ConstantsDNA;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={"classpath:/context.xml","/dataAccessTest.xml"})
public class TestServiceLabStudy {

	@Autowired
	private ServiceLabStudy serviceLabStudy;
	private List<LabStudy> listLabStudies;
	private ProjectBean mockProject;
	private ProjectBean projectBean;
	@Before
	public void init(){
		mockProject = mock(ProjectBean.class);
		when (mockProject.getProjectid()).thenReturn(3);
		when (mockProject.getProjectname()).thenReturn("SEED");
		when (mockProject.getProjectdescription()).thenReturn("Seed Project");
		when (mockProject.getPurposename()).thenReturn("Map");
		when (mockProject.getPurposedescription()).thenReturn("Genetic mapping");
		when (mockProject.getLastsampleid()).thenReturn(1542);
		projectBean = getProjectBean();
		
		
	}

	//@Test
	public void validateIfThisProjecthasStudiesRegister(){
		Assert.assertTrue(serviceLabStudy.ThisProjectHasStudiesRegistred(projectBean));
	}

	//@Test
	public void validateIfThisResearcherHasStudiesRegister(){
		Assert.assertFalse(serviceLabStudy.ThisInvestigatorHasStudiesRegistred(1100));
	}
	
	//@Test
	public void getListEmtyLabStudies(){
		listLabStudies = serviceLabStudy.getLabStudysByIdResearch(new LabStudy(), null
				,1, SHOW_ROWS_LIST, null, false, ConstantsDNA.ROLE_DATA_MANAGER);
		Assert.assertTrue(listLabStudies.isEmpty());
	}

	

	private ProjectBean getProjectBean(){
		ProjectBean bean = new ProjectBean();
		bean.setLastsampleid(1542);
		bean.setProjectid(3);
		bean.setProjectname("SEED");
		bean.setProjectdescription("Seed Project");
		bean.setPurposename("Map");
		bean.setPurposedescription("Genetic mapping");	
		return bean;
	}
}
