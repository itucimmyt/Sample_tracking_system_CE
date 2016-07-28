package org.cimmyt.userService;

import java.util.ArrayList;
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

import com.cimmyt.bean.InvestigatorBean;
import com.cimmyt.bean.RoleBean;
import com.cimmyt.bean.UserBean;
import com.cimmyt.bean.UserFuntionsBean;
import com.cimmyt.exception.BackException;
import com.cimmyt.model.bean.Organism;
import com.cimmyt.model.dao.AuthenticateDAO;
import com.cimmyt.model.dao.InvestigatorDAO;
import com.cimmyt.model.dao.UserFunctionsDAO;
import com.cimmyt.service.ServiceEmail;
import com.cimmyt.service.ServiceUser;
import com.cimmyt.utils.ConstantsDNA;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/context.xml","/dataAccessTest.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestUserVersiob {

	@Autowired
	private ServiceUser serviceUser;
	@Autowired	
	private AuthenticateDAO authenticateDAO;
	@Autowired
	private UserFunctionsDAO userFunctionsDAO;
	@Autowired
	private ServiceEmail serviceEmail;
	@Autowired
	private InvestigatorDAO investigatorDAO;

	private UserBean user = new UserBean();

	@Before
	public void beforeTest(){
		user.setUserName("luisZiou");
		user.setEmail("juve_upiicsa@hotmail.com");
		user.setResearcherName("Luis LUna");
		user.setPassword("test");
		
	}

	public void Test1ValidateUserNotRecord(){
		Assert.assertFalse(serviceUser.validateUserBynameOrUserOrEmail(user));
	}
	

	public void Test2ValidateUserName(){
		user.setUserName("luisPuebla");
		Assert.assertTrue(serviceUser.validateUserBynameOrUserOrEmail(user));
	}

	public void Test2ValidateEmail(){
		user.setUserName("luisPuebla1");
		user.setEmail("l.puebla@cgair.org");
		Assert.assertTrue(serviceUser.validateUserBynameOrUserOrEmail(user));
	}

	
	public void Test3ValidateName(){
		user.setUserName("luisPuebla1");
		user.setEmail("l.puebla@cgair1.org");
		user.setResearcherName("Sukwinder Sing");
		Assert.assertTrue(serviceUser.validateUserBynameOrUserOrEmail(user));
	}


	public void Test4GetListUserAdministrtor (){
		try {
			UserBean bean = new UserBean();
			RoleBean roleB = new RoleBean();
			roleB.setIdstRol(1);
			roleB.setName("");
			bean.setRole(roleB);
			List<UserBean> list  = serviceUser.getListUserBean(bean);
			System.out.println("List ... "+list);
			printRecord(list);
		} catch (BackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void Test5GetListUserDBManager (){
		try {
			UserBean bean = new UserBean();
			RoleBean roleB = new RoleBean();
			roleB.setIdstRol(2);
			roleB.setName("");
			bean.setRole(roleB);
			List<UserBean> list  = serviceUser.getListUserBean(bean);
			System.out.println("List ... "+list);
			printRecord(list);
		} catch (BackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public void Test6GetListUserDBResearcherr (){
		try {
			UserBean bean = new UserBean();
			RoleBean roleB = new RoleBean();
			roleB.setIdstRol(3);
			roleB.setName("");
			bean.setRole(roleB);
			InvestigatorBean invBean = new InvestigatorBean();
			invBean.setInvestigatorid(4);
			bean.setInvestigatorBean(invBean);
			List<UserBean> list  = serviceUser.getListUserBean(bean);
			System.out.println("List Researcher ... "+list);
			printRecord(list);
		} catch (BackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void Test7saveUserBean() throws BackException{
	
		user.setUserName("luisZiou");
		user.setEmail("juve_upiicsa@hotmail.com");
		user.setResearcherName("Luis LUna");
		user.setPassword("test");
		InvestigatorBean investigatorBean = new InvestigatorBean();
		investigatorBean.setInvestigatorid(4);
		user.setInvestigatorBean(investigatorBean);
		RoleBean roleBean = new RoleBean();
		roleBean.setIdstRol(4);
		user.setRole(roleBean);
		Organism stOrganism = new Organism();
		stOrganism.setOrganismid(2);
		user.setOrganism(stOrganism);
		serviceUser.saveOrUpdatetUser(user, listfuntions(), 0);
		Assert.assertTrue(true);
		
	}
	

	public void Test8getUserBeanSaved() throws BackException{
		user = serviceUser.getUser(user);
		System.out.println("Id user = "+user.getIdUser());
		Assert.assertNotNull(user);
		
	}
	

	public void Test9updateUserBean() throws BackException{
		user = serviceUser.getUser(user);
		user.setPassword("Password");
		user.setUserName("luisZiou");
		user.setEmail("juve_upiicsa@hotmail.com");
		user.setResearcherName("Luis LUna");
		InvestigatorBean investigatorBean = new InvestigatorBean();
		investigatorBean.setInvestigatorid(4);
		user.setInvestigatorBean(investigatorBean);
		RoleBean roleBean = new RoleBean();
		roleBean.setIdstRol(4);
		user.setRole(roleBean);
		Organism stOrganism = new Organism();
		stOrganism.setOrganismid(2);
		user.setOrganism(stOrganism);
		serviceUser.saveOrUpdatetUser(user, listfuntions(), user.getIdUser());
		System.out.println("Id user Update = "+user.getIdUser());
		Assert.assertNotNull(user);
		
	}

	
	public void Test10deleteUserBean() throws BackException{
		user = serviceUser.getUser(user);
		serviceUser.deleteUser(user);
		System.out.println("Id user delete = "+user.getIdUser());
		Assert.assertNotNull(user);
		
	}


	public void Test11fisicaldeleteUserBean() throws BackException{
		
		user = serviceUser.getUser(user);
		serviceUser.fisicalDeletetUser(user);
		System.out.println("Id user = "+user.getIdUser());
		Assert.assertNotNull(user);
		
	}
	public void Test12saveUserBeanWithNewResearcher() throws BackException{
		
		user.setUserName("luisLuna");
		user.setEmail("ronaldo_luis_brasil@hotmail.com");
		user.setResearcherName("Luis Puebla");
		user.setPassword("test");
		InvestigatorBean investigatorBean = new InvestigatorBean();
		investigatorBean.setInvest_abbreviation("PL");
		investigatorBean.setInvest_name(user.getResearcherName());
		user.setInvestigatorBean(investigatorBean);
		RoleBean roleBean = new RoleBean();
		roleBean.setIdstRol(ConstantsDNA.ROLE_RESEARCHER);
		user.setRole(roleBean);
		Organism stOrganism = new Organism();
		stOrganism.setOrganismid(2);
		user.setOrganism(stOrganism);
		serviceUser.saveOrUpdatetUser(user, listfuntions(), 0);
		user = serviceUser.getUser(user);
		serviceUser.fisicalDeletetUser(user);
		Assert.assertTrue(true);
		
	}
	@Test
	public void executionPlan() throws BackException{
		Test1ValidateUserNotRecord();
		Test2ValidateEmail();
		Test3ValidateName();
		Test4GetListUserAdministrtor();
		Test5GetListUserDBManager();
		Test6GetListUserDBResearcherr();
		Test7saveUserBean();
		Test8getUserBeanSaved();
		Test9updateUserBean();
		Test10deleteUserBean();
		Test11fisicaldeleteUserBean();
		Test12saveUserBeanWithNewResearcher();
		
	}
	private List<Integer> listfuntions(){
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 1; i <= 44 ; i++ )
			list.add(new Integer(i));
		return list;
	}
	private void printRecord(List<UserBean> list){
		try{
		if (list != null && !list.isEmpty()){
			for (UserBean user : list ){
				System.out.println("Name User : "+user.getResearcherName());
				System.out.println("Name researcher :"+user.getInvestigatorBean().getInvest_name());
				System.out.println("Crop :: "+user.getOrganism().getOrganismname());
				System.out.println("Profile : "+user.getRole().getName());
				if (user.getSetFuntionsBean() != null && !user.getSetFuntionsBean().isEmpty()){
					for (UserFuntionsBean funt: user.getSetFuntionsBean()){
						System.out.println("Funtion :"+funt.getFuntions().getFuntionKey());
					}
					
				}
			}
		}
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}
}
