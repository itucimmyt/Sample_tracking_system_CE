package org.cimmyt.service.query;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={"classpath:/context.xml","/dataAccessTest.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestQueryExpretion {

	@Test
	public void validateExpretion(){
		String expretion = "[\\W&&[^()/\\-\\s,\\*#\\+\\<]]";
		
		String value = "CLWN247<2(GUAT153)-56-3";
		
		value = value.replaceAll(expretion,"");
		System.out.println("value "+value);
		value = "CLWN247";
		System.out.println("value "+value);
		value = "CLWN247<2(GUAT153)";
		System.out.println("value "+value);
		value = "CLWN247<2(GUAT153";
		System.out.println("value "+value);
	}
	
}
