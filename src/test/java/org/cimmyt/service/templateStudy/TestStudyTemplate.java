package org.cimmyt.service.templateStudy;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cimmyt.model.bean.StudyTemplate;
import com.cimmyt.model.bean.StudyTemplateParams;
import com.cimmyt.model.dao.MyQLProcedureDAO;
import com.cimmyt.service.SeriviceStudyTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/context.xml","/dataAccessTest.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestStudyTemplate {

	
	@Autowired
	private SeriviceStudyTemplate seriviceStudyTemplate;
	@Autowired
	private MyQLProcedureDAO mySqlProcedure;

	private StudyTemplate studyTemplate ;

	@Before
	public void beforeTest(){
		studyTemplate = getStudyTemplate();
	}
	
	@Test
	public void Test1ValidateName(){
		Assert.assertNull(seriviceStudyTemplate.getStudyTemplateByNameregistred(studyTemplate.getTemplatename()));
	}

	@Test
	public void Test2AddNewStudytemplate(){
		seriviceStudyTemplate.add(studyTemplate, false);
		Assert.assertNotNull(studyTemplate.getStudytemplateid());
		
	}

	@Test
	public void Test3getListStudytemplate(){
		List<StudyTemplate> list = seriviceStudyTemplate.getStudyTemplate(studyTemplate);
		Assert.assertTrue(list.size() > 0);
	}


	@Test
	public void Test4ValidateTemplateparamsDoesnotHaveLinkWithStudies(){
		List<StudyTemplate> list = seriviceStudyTemplate.getStudyTemplate(studyTemplate);
		StudyTemplate studyTemplate = list.get(0);
		StudyTemplateParams templ = null;
		for (StudyTemplateParams prm : studyTemplate.getImsStudyTemplateParamsCollection()){
			templ = prm;
			break;
		}
		int total = mySqlProcedure.getExecuteCountParamData(templ.getTemplateparamid());
		Assert.assertTrue(total == 0);
		
	}

	@Test
	public void Test5GetSampleDetResultByGIDandTemplate(){
		List<StudyTemplate> list = seriviceStudyTemplate.getStudyTemplate(studyTemplate);
		StudyTemplate studyTemplate = list.get(0);
		seriviceStudyTemplate.deleteStudyTemplateParams(studyTemplate.getImsStudyTemplateParamsCollection(), false);
		List<StudyTemplate> list2 = seriviceStudyTemplate.getStudyTemplate(studyTemplate);
		Assert.assertTrue(list2.get(0).getImsStudyTemplateParamsCollection().size() == 0);	
	}
	
	@Test
	public void Test6DeleteStudytemplate(){
		List<StudyTemplate> list = seriviceStudyTemplate.getStudyTemplate(studyTemplate);
		StudyTemplate studyTemplate = list.get(0);
		seriviceStudyTemplate.delete(studyTemplate);
		Assert.assertNull(seriviceStudyTemplate.getStudyTemplate(studyTemplate));
	}
	
	private StudyTemplate getStudyTemplate(){
		StudyTemplate bean = new StudyTemplate();
		bean.setTemplatename("Test 1");
		bean.setComments("Comments 1");
		bean.setImsStudyTemplateParamsCollection(getSetStudytemplateParams(bean));
		return bean;
	}

	private SortedSet<StudyTemplateParams> getSetStudytemplateParams(StudyTemplate studytemplateid){
		SortedSet<StudyTemplateParams> imsStudyTemplateParamsCollection  = new TreeSet<StudyTemplateParams>();
		imsStudyTemplateParamsCollection.add(getStudyTemplateParams("Test - 1", "Test - 1", "C", studytemplateid ));
		imsStudyTemplateParamsCollection.add(getStudyTemplateParams("Test - 2", "Test - 2", "N", studytemplateid));
		imsStudyTemplateParamsCollection.add(getStudyTemplateParams("Test - 3", "Test - 3", "D", studytemplateid ));
		imsStudyTemplateParamsCollection.add(getStudyTemplateParams("Test - 4", "Test - 4", "N", studytemplateid ));
		return imsStudyTemplateParamsCollection;
		
	}

	private StudyTemplateParams getStudyTemplateParams(String factorname,String description, String datatype,StudyTemplate studytemplateid){
		StudyTemplateParams bean = new StudyTemplateParams();
		bean.setFactorname(factorname);
		bean.setDescription(description);
		bean.setDatatype(datatype);
		bean.setStudytemplateid(studytemplateid);
		return bean;
	}
	
}

