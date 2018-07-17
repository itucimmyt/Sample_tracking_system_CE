package org.cimmyt.service.location;

import java.util.List;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cimmyt.bean.LocationBean;
import com.cimmyt.model.dao.LocationDAO;
import com.cimmyt.model.dao.SampleDetailDAO;
import com.cimmyt.model.dao.StorageMovDAO;
import com.cimmyt.service.ServiceLocation;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/context.xml","/dataAccessTest.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestLocation {
	
	@Autowired
	private ServiceLocation serviceLocation;
	@Autowired
	private LocationDAO locationDAO;
	@Autowired
	private SampleDetailDAO sampleDetailDAO;
	@Autowired
	private StorageMovDAO storageMovDAO;
	
	@Test
	public void Test1GetAllLocation() {
		List<LocationBean> list = serviceLocation.getLocation(new LocationBean());
		Assert.assertEquals(14,list.size());
	}

	@Test
	public void Test2SaveLocation() {
		serviceLocation.add(getLocationBean());
		List<LocationBean> list = serviceLocation.getLocation(new LocationBean());
		Assert.assertEquals(15,list.size());
	}

	@Test
	public void Test3GetLocatiobyName() {
		LocationBean bean = serviceLocation.getLocationCatalogRegistred(getLocationBean().getLocation_name());
		Assert.assertNotNull(bean);
	}

	@Test
	public void Test4Detele() {
		LocationBean bean = serviceLocation.getLocationCatalogRegistred(getLocationBean().getLocation_name());
		serviceLocation.delete(bean);
		List<LocationBean> list = serviceLocation.getLocation(new LocationBean());
		Assert.assertEquals(14,list.size());
	}

	public static LocationBean getLocationBean() {
		LocationBean bean = new LocationBean();
		bean.setLocation_name("Test Location 1");
		bean.setLocation_description("Test description");
		return bean;
	}
}
