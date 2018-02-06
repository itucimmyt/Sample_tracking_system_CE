package org.cimmyt.service.rest;

import static com.cimmyt.utils.Constants.LBL_STUDIES_TEMPORARY_SAMPLE_FOUND;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cimmyt.exception.BackException;
import com.cimmyt.utils.PropertyHelper;
import com.cimmyt.utils.PropertyHelper.Languages;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/context.xml","/dataAccessTest.xml"})
public class TestStudiesFront {

	
	private static PropertyHelper props = null;
	@BeforeClass
	public static void init() throws Exception{
		
		props = new PropertyHelper();
        props.setMessagesBundle(props.setLanguageToLocale());
	}
	@Test
	public void evaluateMessageThereAreSampleForLoadIntoSystemEnglish() throws BackException{
		String cadenaTest = null;
		cadenaTest = props.getKey(LBL_STUDIES_TEMPORARY_SAMPLE_FOUND);
		Assert.assertNotNull(cadenaTest);
	}
	@Test
	public void evaluateMessageThereAreSampleForLoadIntoSystemSpanish() throws BackException{
		String cadenaTest = null;
		props.setMessagesBundle(Languages.spanish);
		cadenaTest = props.getKey(LBL_STUDIES_TEMPORARY_SAMPLE_FOUND);
		Assert.assertNotNull(cadenaTest);
	}
	
	@org.junit.AfterClass
	public static void afterAction() throws BackException{
				
	}


}
