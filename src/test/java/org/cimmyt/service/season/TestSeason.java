package org.cimmyt.service.season;

import java.util.List;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cimmyt.bean.SeasonBean;
import com.cimmyt.model.dao.SeasonDAO;
import com.cimmyt.service.ServiceSeason;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/context.xml","/dataAccessTest.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestSeason {

	@Autowired
	private ServiceSeason serviceSeason;
	@Autowired
	private SeasonDAO seasonDAO;

	@Test
	public void Test1GetAllSeason() {
		List<SeasonBean> list = serviceSeason.getSeasons(new SeasonBean());
		Assert.assertEquals(21, list.size());
	}

	@Test
	public void Test2SaveSeason() {
		serviceSeason.add(getSeasonBean());
		List<SeasonBean> list = serviceSeason.getSeasons(new SeasonBean());
		Assert.assertEquals(22, list.size());
	}

	@Test
	public void Test3getSeasonByName() {
		SeasonBean bean = serviceSeason.getSeasonBeanByName(getSeasonBean().getSeason_name());
		Assert.assertNotNull(bean);
	}
	
	@Test
	public void Test4getSeasonByName() {
		SeasonBean bean = serviceSeason.getSeasonBeanByName(getSeasonBean().getSeason_name());
		List<SeasonBean> list = serviceSeason.getSeasons(bean);
		Assert.assertNotNull(list);
	}

	@Test
	public void Test9DeleteSeason() {
		SeasonBean bean = serviceSeason.getSeasonBeanByName(getSeasonBean().getSeason_name());
		serviceSeason.delete(bean);
		List<SeasonBean> list = serviceSeason.getSeasons(new SeasonBean());
		Assert.assertEquals(21, list.size());
	}

	public static SeasonBean getSeasonBean() {
		SeasonBean bean = new SeasonBean();
		bean.setSeason_name("Season test1");
		bean.setSeason_description("Season description 1");
		return bean;
	}
}
