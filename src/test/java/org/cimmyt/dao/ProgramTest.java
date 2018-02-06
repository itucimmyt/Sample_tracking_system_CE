package org.cimmyt.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cimmyt.model.bean.Organism;
import com.cimmyt.model.bean.Program;
import com.cimmyt.model.dao.ProgramDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/context.xml","/dataAccessTest.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProgramTest {

	@Autowired
	private ProgramDAO program;

	@Before
	public void initData(){
		saveSomeRecordForMaizeAndWheat();
	}
	
	@Test
	public void Test1getAllPrograms (){
		Program filter = new Program();
		Organism organism = new Organism();
		organism.setOrganismid(1);
		List<Program> list = program.getListProgram(filter, true);
		Assert.assertEquals(27, list.size());
	}
	@Test
	public void Test2getAllProgramsMaize (){
		Program filter = new Program();
		Organism organism = new Organism();
		organism.setOrganismid(1);
		filter.setOrganism(organism);
		List<Program> list = program.getListProgram(filter, true);
		Assert.assertEquals(14, list.size());
	}
	@Test
	public void saveProgramMaize (){
		Program filter = new Program();
		Organism organism = new Organism();
		organism.setOrganismid(1);
		filter.setOrganism(organism);
		filter.setLetterCode("AA");
		filter.setProgramName("Test 1");
		filter.setDescription("Tests Description 1");
		filter.setStatus(true);
		program.saveOrUpdate(filter);
		filter = new Program();
		organism.setOrganismid(1);
		filter.setOrganism(organism);
		List<Program> list = program.getListProgram(filter, true);
		Assert.assertEquals(15, list.size());
	}
	@Test
	public void updateProgramMaize (){
		Program filter = new Program();
		Organism organism = new Organism();
		organism.setOrganismid(1);
		filter.setOrganism(organism);
		List<Program> list = program.getListProgram(filter, true);
		Program edit = list.get(0);
		edit.setStatus(false);
		program.saveOrUpdate(edit);
		list = program.getListProgram(filter, false);
		Assert.assertEquals(1, list.size());
	}

	private void saveSomeRecordForMaizeAndWheat(){
		Program filter = new Program();
		Organism organism = new Organism();
		organism.setOrganismid(1);
		filter.setOrganism(organism);
		filter.setLetterCode("BB");
		filter.setProgramName("Test B");
		filter.setDescription("Tests Description B");
		filter.setStatus(true);
		program.saveOrUpdate(filter);
		organism.setOrganismid(2);
		filter.setOrganism(organism);
		filter.setLetterCode("CC");
		filter.setProgramName("Test C");
		filter.setDescription("Tests Description C");
		filter.setStatus(true);
		program.saveOrUpdate(filter);
	}
}
