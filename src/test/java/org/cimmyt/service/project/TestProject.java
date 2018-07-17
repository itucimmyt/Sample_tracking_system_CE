package org.cimmyt.service.project;

import java.util.List;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cimmyt.bean.ProjectBean;
import com.cimmyt.model.dao.ProgramDAO;
import com.cimmyt.model.dao.ProjectDAO;
import com.cimmyt.model.dao.PurposeDAO;
import com.cimmyt.service.ServiceProject;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/context.xml","/dataAccessTest.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestProject {

	@Autowired
	private ServiceProject serviceProyect;
	@Autowired
	private ProjectDAO projectDAO;
	@Autowired
	private ProgramDAO programDAO;
	@Autowired
	private PurposeDAO purposeDAO;

	@Test
	public void Test1GetAllProjects() {
		List<ProjectBean> listBean = serviceProyect.getProject(new ProjectBean());
		Assert.assertEquals(listBean.size(), listBean.size());
	}

	@Test
	public void Test2SaveProject() {
		serviceProyect.saveOrUpdateProject(getProjectBean());
		List<ProjectBean> listBean = serviceProyect.getProject(new ProjectBean());
		Assert.assertEquals(94, listBean.size());
	}

	@Test
	public void Test3GetProjectTest() {
		ProjectBean bean = serviceProyect.getProjectWithName(getProjectBean().getProjectname()+getProjectBean().getPurposename());
		Assert.assertNotNull(bean);
	}

	@Test 
	public void Test4QuantitySampleByProject() {
		ProjectBean bean = serviceProyect.getProjectWithName(getProjectBean().getProjectname()+getProjectBean().getPurposename());
		int lastSample = serviceProyect.getLastSampleID(bean);
		System.out.println("lastSample : "+lastSample);
		Assert.assertEquals(lastSample, 0);
	}
	
	@Test 
	public void Test5deleteProject() {
		ProjectBean bean = serviceProyect.getProjectWithName(getProjectBean().getProjectname()+getProjectBean().getPurposename());
		serviceProyect.deleteProject(bean);
		serviceProyect.saveOrUpdateProject(getProjectBean());
		List<ProjectBean> listBean = serviceProyect.getProject(new ProjectBean());
		Assert.assertEquals(94, listBean.size());
	}

	public static ProjectBean getProjectBean() {
		ProjectBean bean = new ProjectBean();
		bean.setProjectname("Test 1");
		bean.setProjectdescription("Test description 1");
		bean.setPurposename("purpose name 1");
		bean.setPurposedescription("Purpose description 1");
		bean.setLastsampleid(0);
		return bean;
	}

}
