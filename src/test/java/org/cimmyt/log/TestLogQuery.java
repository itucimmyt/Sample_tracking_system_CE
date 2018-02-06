package org.cimmyt.log;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cimmyt.model.dao.LogDAO;
import com.cimmyt.service.ServiceLog;
import com.cimmyt.service.ServiceUser;
import com.cimmyt.utils.ConstantsDNA;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={"classpath:/context.xml","/dataAccessTest.xml"})
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestLogQuery {

	@Autowired
	private ServiceUser serviceUser= null;
	@Autowired
	private ServiceLog serviceLog= null;
	@Autowired
	private LogDAO logDAO;

	//@Before
	public void beforeTest(){
		
		serviceLog.persistLog(ConstantsDNA.LOG_ENTITY_STUDY, 1
				,  ConstantsDNA.LOG_ADD, 18, "Test Add");
		serviceLog.persistLog(ConstantsDNA.LOG_ENTITY_STUDY, 1
				, ConstantsDNA.LOG_EDIT , 18, "Test Add");
	}
	
}
