package org.cimmyt.service.study;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cimmyt.model.bean.Investigator;
import com.cimmyt.model.bean.LabStudy;
import com.cimmyt.service.ServiceUser;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/context.xml","/dataAccessTest.xml"})
public class CreateStudy {

	
	private static ServiceUser serviceUser;
	
	public static LabStudy createNewStudy(){
		LabStudy bean = new LabStudy();
		
		return null;
	}

	public static Investigator getNewResearcher (){
		Investigator researcher = new Investigator();
		
		return null;
	}
}
