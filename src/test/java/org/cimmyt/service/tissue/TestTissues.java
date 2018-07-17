package org.cimmyt.service.tissue;

import java.util.List;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cimmyt.bean.TissueBean;
import com.cimmyt.model.dao.TissueDAO;
import com.cimmyt.service.ServiceTissue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/context.xml","/dataAccessTest.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestTissues {

	@Autowired
	private ServiceTissue serviceTissue;
	@Autowired
	private TissueDAO tissueDAO;

	@Test
	public void Test1GetAllTissue() {
		List<TissueBean> list = serviceTissue.getTissue(new TissueBean());
		Assert.assertEquals(2, list.size());
		
	}

	@Test
	public void Test2SaveTissue() {
		serviceTissue.add(getTissueBean());
		List<TissueBean> list = serviceTissue.getTissue(new TissueBean());
		Assert.assertEquals(3, list.size());
	}

	@Test
	public void Test3GetTissueByName() {
		TissueBean bean = serviceTissue.getTissueByName(getTissueBean().getTissueName());
		Assert.assertNotNull(bean);
	}

	@Test
	public void Test4Delete() {
		TissueBean bean = serviceTissue.getTissueByName(getTissueBean().getTissueName());
		serviceTissue.delete(bean);
		List<TissueBean> list = serviceTissue.getTissue(new TissueBean());
		Assert.assertEquals(2, list.size());
		
	}

	public static TissueBean getTissueBean() {
		TissueBean bean = new TissueBean();
		bean.setTissueName("Tissue name");
		return bean;
	} 

}
