package org.cimmyt.service.application;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cimmyt.model.bean.Organism;
import com.cimmyt.model.bean.Program;
import com.cimmyt.model.bean.Purpose;
import com.cimmyt.model.dao.ProgramDAO;
import com.cimmyt.model.dao.PurposeDAO;
import com.cimmyt.service.ServiceProject;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/context.xml","/dataAccessTest.xml"})
public class TestServiceProject {

	@Autowired
	private ServiceProject serviceProject;
	@Autowired
	private ProgramDAO program;
	@Autowired
	private PurposeDAO purpose;

	@Test
	public void getAllPrograms (){
		Program filter = new Program();
		Organism organism = new Organism();
		organism.setOrganismid(1);
		List<Program> list = serviceProject.getListProgram(filter, true);
		Assert.assertNotNull(list.size());
	}

	@Test
	public void saveProgramMaize (){
		Program filter = new Program();
		Organism organism = new Organism();
		organism.setOrganismid(1);
		filter.setOrganism(organism);
		List<Program> list = serviceProject.getListProgram(filter, true);
		int size = list.size();
		filter.setLetterCode("AA");
		filter.setProgramName("Test 1");
		filter.setDescription("Tests Description 1");
		filter.setStatus(true);
		serviceProject.saveOrUpdateProgram(filter);
		filter = new Program();
		organism.setOrganismid(1);
		filter.setOrganism(organism);
		list = serviceProject.getListProgram(filter, true);
		Assert.assertEquals(size+1, list.size());
	}

	@Test
	public void getAllPurpose(){
		Purpose filter = new Purpose();
		List<Purpose> list = serviceProject.getListPurpose(filter, true);
		Assert.assertNotNull(list.size());
	}

	@Test
	public void savePurposeMaize(){
		Purpose filter = new Purpose();
		Organism organism = new Organism();
		organism.setOrganismid(1);
		filter.setOrganism(organism);
		List<Purpose> list = serviceProject.getListPurpose(filter, true);
		int size = list.size();
		filter.setLetterCode("AA");
		filter.setPurposeName("Test 1");
		filter.setDescription("Tests Description 1");
		filter.setStatus(true);
		serviceProject.saveOrUpdatePurpose(filter);
		filter = new Purpose();
		filter.setOrganism(organism);
		list = serviceProject.getListPurpose(filter, true);
		Assert.assertEquals(size+1, list.size());
	}

	@Test
	public void getYearNowformToday(){
		Date date = new Date ();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		year =year- 2000;
		System.out.println(year);
		Assert.assertNotNull(year);
	}

	@Test
	public void setSubstringProgram(){
		String str = "PU16";
		if (str.length() == 4){
			str = str.replaceAll(str.substring(0, 2), "BB");
			System.out.println(str);
			Assert.assertEquals(4, str.length());
		}
	}
}
