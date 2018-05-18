package org.cimmyt.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cimmyt.model.bean.Organism;
import com.cimmyt.model.bean.Purpose;
import com.cimmyt.model.dao.PurposeDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/context.xml","/dataAccessTest.xml"})
public class PurposeDAOTest {

	@Autowired
	private PurposeDAO purposeDAO;

	@Test
	public void getAllPurpose(){
		Purpose filter = new Purpose();
		List<Purpose> list = purposeDAO.getListPurpose(filter, true);
		Assert.assertEquals(list.size(), list.size());
	}

	@Test
	public void getAllPurposeByMaize(){
		Purpose filter = new Purpose();
		Organism organism = new Organism();
		organism.setOrganismid(1);
		filter.setOrganism(organism);
		List<Purpose> list = purposeDAO.getListPurpose(filter, true);
		Assert.assertEquals(list.size(), list.size());
	}

	@Test
	public void savePurposeMaize(){
		Purpose filter = new Purpose();
		Organism organism = new Organism();
		organism.setOrganismid(1);
		filter.setOrganism(organism);
		filter.setLetterCode("AA");
		filter.setPurposeName("Test 1");
		filter.setDescription("Tests Description 1");
		filter.setStatus(true);
		purposeDAO.saveOrUpdate(filter);
		filter = new Purpose();
		filter.setOrganism(organism);
		List<Purpose> list = purposeDAO.getListPurpose(filter, true);
		Assert.assertEquals(list.size(), list.size());
	}

	@Test
	public void updatePurposeMaize(){
		Purpose filter = new Purpose();
		Organism organism = new Organism();
		organism.setOrganismid(1);
		filter.setOrganism(organism);
		List<Purpose> list = purposeDAO.getListPurpose(filter, true);
		Purpose edit = list.get(0);
		edit.setStatus(false);
		purposeDAO.saveOrUpdate(edit);
		list = purposeDAO.getListPurpose(filter, false);
		Assert.assertEquals(list.size(),list.size());
	}

}
