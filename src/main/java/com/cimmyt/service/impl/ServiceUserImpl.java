package com.cimmyt.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.cimmyt.dnast.dto.AuthUserBean;
import org.cimmyt.dnast.dto.StInvestigator;

import com.cimmyt.bean.UserBean;
import com.cimmyt.bean.UserFuntionsBean;
import com.cimmyt.exception.BackException;
import com.cimmyt.model.bean.Funtions;
import com.cimmyt.model.bean.Investigator;
import com.cimmyt.model.bean.Organism;
import com.cimmyt.model.bean.Role;
import com.cimmyt.model.bean.UserFuntions;
import com.cimmyt.model.dao.AuthenticateDAO;
import com.cimmyt.model.dao.InvestigatorDAO;
import com.cimmyt.model.dao.UserDAO;
import com.cimmyt.model.dao.UserFunctionsDAO;
import com.cimmyt.service.ServiceEmail;
import com.cimmyt.service.ServiceUser;
import com.cimmyt.utils.ConstantsDNA;

public class ServiceUserImpl implements ServiceUser{

	private UserDAO userDAO;
	private AuthenticateDAO authenticateDAO;
	private UserFunctionsDAO userFunctionsDAO;
	private ServiceEmail serviceEmail;
	private InvestigatorDAO investigatorDAO;
	private Logger logger= Logger.getLogger(ServiceUserImpl.class);
	
	@Override
	public void saveOrUpdatetUser(UserBean userBean, List<Integer> listFuntions , int id)
			throws BackException {
		try{
			AuthUserBean autUser = new AuthUserBean();
			if (id > 0)
				autUser.setIdUser(id);
				autUser.setUserName(userBean.getUserName());
				autUser.setEmail(userBean.getEmail());
				autUser.setInvestName(userBean.getResearcherName());
				autUser.setVersion("2.0");
				autUser.setPassword(userBean.getPassword());
				autUser.setStatus(true);
				StInvestigator investigatorSt = new StInvestigator();
				if (userBean.getRole().getIdstRol() != ConstantsDNA.ROLE_ADMINISTRATOR){
					if (userBean.getInvestigatorBean()!= null ){
						Investigator investigator = new Investigator();
						investigator.setInvest_abbreviation(userBean.getInvestigatorBean().getInvest_abbreviation());
						investigator.setInvest_name(userBean.getInvestigatorBean().getInvest_name());
						if (userBean.getInvestigatorBean().getInvestigatorid() != null && userBean.getInvestigatorBean().getInvestigatorid().intValue() > 0)
							investigator.setInvestigatorid(userBean.getInvestigatorBean().getInvestigatorid());
						int idResearcher = investigatorDAO.saveOrUpdate(investigator);
						if (idResearcher > 0 ){
							userBean.getInvestigatorBean().setInvestigatorid(idResearcher);
						}
					}
				}
				investigatorSt.setInvestigatorid(userBean.getInvestigatorBean().getInvestigatorid());
				autUser.setInvestigator(investigatorSt);
				Role role  = new Role();
				role.setIdstRol(userBean.getRole().getIdstRol());
				autUser.setStRole(role);
					AuthUserBean authUserBean =	authenticateDAO.getAuthUserBeanById(id);
					if (authUserBean != null && authUserBean.getUserFuntionses()!= null && !authUserBean.getUserFuntionses().isEmpty()){
						userFunctionsDAO.deleteSetUserFuntions(authUserBean.getUserFuntionses());
					}
					Organism stOrganism = new Organism();
					stOrganism.setOrganismid(userBean.getOrganism().getOrganismid());
					autUser.setStOrganism(stOrganism);
				autUser.setUserFuntionses(getListFuntions(listFuntions, autUser));
				int idUser = userDAO.saveOrUpdate(autUser);
				autUser.setIdUser(idUser);
				userFunctionsDAO.saveSetUserFunctions(getListFuntions(listFuntions, autUser));
				if (userBean.getRole().getIdstRol() != ConstantsDNA.ROLE_ADMINISTRATOR)
				serviceEmail.sendEmailWithCredencial(autUser);
				logger.info("Saving user ...");
			}catch (Exception ex){
				logger.error(ex.getMessage(), ex);
			}
	}

	private  Set<UserFuntions> getListFuntions (List<Integer> listFuntions, AuthUserBean autUser){
		Set<UserFuntions> setFuntions = new HashSet<UserFuntions>();
		for (Integer id : listFuntions){
			UserFuntions functions = new UserFuntions();
			functions.setStUserVersion(autUser);
			Funtions stFuntions = new Funtions();
			stFuntions.setIdstFuntions(id);
			functions.setStFuntions(stFuntions);
			setFuntions.add(functions);
		}
		return setFuntions;
	}
	@Override
	public void deleteUser(UserBean userBean) throws BackException {
		AuthUserBean autUser = new AuthUserBean();
		autUser.setIdUser(userBean.getIdUser());
		autUser.setStatus(false);
		autUser.setUserName(userBean.getUserName());
		autUser.setEmail(userBean.getEmail());
		autUser.setInvestName(userBean.getResearcherName());
		autUser.setVersion("2.0");
		autUser.setPassword(userBean.getPassword());
		StInvestigator investigatorST = new StInvestigator();
		investigatorST.setInvestigatorid(userBean.getInvestigatorBean().getInvestigatorid());
		if (userBean.getRole().getIdstRol() == ConstantsDNA.ROLE_RESEARCHER) {
			
			investigatorST.setStatus(userBean.isStatus());
			}

		if (userBean.getRole().getIdstRol() == ConstantsDNA.ROLE_RESEARCHER) {
		Investigator investigator = new Investigator();
		investigator.setInvest_abbreviation(userBean.getInvestigatorBean().getInvest_abbreviation());
		investigator.setInvest_name(userBean.getInvestigatorBean().getInvest_name());
		investigator.setInvestigatorid(userBean.getInvestigatorBean().getInvestigatorid());
		investigator.setStatus(userBean.isStatus());
		investigatorDAO.saveOrUpdate(investigator);
		}
		
		autUser.setInvestigator(investigatorST);
		Role role  = new Role();
		role.setIdstRol(userBean.getRole().getIdstRol());
		autUser.setStRole(role);
		Organism stOrganism = new Organism();
		stOrganism.setOrganismid(userBean.getOrganism().getOrganismid());
		autUser.setStOrganism(stOrganism);
		autUser.setStatus(userBean.isStatus());
		userDAO.saveOrUpdate(autUser);
	}

	public boolean validateUserBynameOrUserOrEmail(UserBean userBean){
		AuthUserBean autUser = new AuthUserBean();
		autUser.setUserName(userBean.getUserName());
		autUser.setEmail(userBean.getEmail());
		autUser.setInvestName(userBean.getResearcherName());
		autUser.setStatus(true);
		AuthUserBean autUserDB = userDAO.getAuthUserBeanByUserNameOrEmailOrName(autUser);
		if ( autUserDB != null){
			return true;
		}
		return false;
	}
	@Override
	
	public UserBean getUser(UserBean userBean) throws BackException {
		AuthUserBean autUser = new AuthUserBean();
		autUser.setUserName(userBean.getUserName());
		autUser.setEmail(userBean.getEmail());
		autUser.setInvestName(userBean.getResearcherName());
		autUser.setStatus(true);
		AuthUserBean bean = userDAO.getAuthUserBeanByUserNameOrEmailOrName(autUser);
		if (bean != null){
			return bean.getUserBean(bean);
		}
		return null;
	}


	@Override
	public List<UserBean> getListUserBean(UserBean userBean)
			throws BackException {
		
		if (userBean != null && userBean.getRole() != null){
			List<UserBean> listUser = new ArrayList<UserBean>();
			switch (userBean.getRole().getIdstRol()){
			case ConstantsDNA.ROLE_ADMINISTRATOR:{
					List<AuthUserBean> list =  userDAO.getInvestigator( new AuthUserBean());
					if (list != null && !list.isEmpty()){
						for (AuthUserBean aut : list){
							listUser.add(getUserBeanByAutUser(aut));
						}
						return listUser;
					}
					break;
				}
			case ConstantsDNA.ROLE_DATA_MANAGER:{
				 AuthUserBean auth = new AuthUserBean();
				 auth.setStRole(userBean.getStRole(userBean.getRole())); 
				List<AuthUserBean> list =  userDAO.getListAuthUserBeanByIDRole(auth);
				if (list != null && !list.isEmpty()){
					for (AuthUserBean aut : list){
						listUser.add(getUserBeanByAutUser(aut));
					}
					return listUser;
				}
				break;
			}
			case ConstantsDNA.ROLE_RESEARCHER:{
					 AuthUserBean auth = new AuthUserBean();
					 auth.setStRole(userBean.getStRole(userBean.getRole()));
					 auth.setInvestigator(userBean.getStInvestigator(userBean.getInvestigatorBean()));
					List<AuthUserBean> list =  userDAO.getListAuthUserBeanByIDRole(auth);
					if (list != null && !list.isEmpty()){
						for (AuthUserBean aut : list){
							listUser.add(getUserBeanByAutUser(aut));
						}
						return listUser;
					}
					break;
				}
			}
		}
		return null;
	}

	@Override
	public void fisicalDeletetUser( UserBean userBean)
			throws BackException {
		
			AuthUserBean autUser = new AuthUserBean();
			autUser.setIdUser(userBean.getIdUser());
			autUser.setStatus(false);
			autUser.setUserName(userBean.getUserName());
			autUser.setEmail(userBean.getEmail());
			autUser.setInvestName(userBean.getResearcherName());
			autUser.setVersion("2.0");
			autUser.setPassword(userBean.getPassword());
			StInvestigator investigator = new StInvestigator();
			investigator.setInvestigatorid(userBean.getInvestigatorBean().getInvestigatorid());
			autUser.setInvestigator(investigator);
			Role role  = new Role();
			role.setIdstRol(userBean.getRole().getIdstRol());
			autUser.setStRole(role);
			Organism stOrganism = new Organism();
			stOrganism.setOrganismid(userBean.getOrganism().getOrganismid());
			autUser.setStOrganism(stOrganism);
			System.out.println("Delete fisical..... user.......");
			AuthUserBean authUserBeanDelete =	authenticateDAO.getAuthUserBeanById(userBean.getIdUser());
			if (authUserBeanDelete != null && authUserBeanDelete.getUserFuntionses()!= null && !authUserBeanDelete.getUserFuntionses().isEmpty()){
				userFunctionsDAO.deleteSetUserFuntions(authUserBeanDelete.getUserFuntionses());
			}
			userDAO.fisicaldelete(autUser);
		
	}

	public UserBean getUserBeanByAutUser(AuthUserBean aut){
		UserFuntions userFuntions = new UserFuntions();
		userFuntions.setStUserVersion(aut);
		List<UserFuntions> listUserFun =userFunctionsDAO.getListUserFunctions(userFuntions);
		UserBean userBeanAU = aut.getUserBean(aut);
		if (listUserFun != null && !listUserFun.isEmpty()){
			for (UserFuntions userFunct : listUserFun){
				userBeanAU.getSetFuntionsBean().add(new UserFuntionsBean(userFunct));
			}
			
		}
		return userBeanAU;
	}

	
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public void setAuthenticateDAO(AuthenticateDAO authenticateDAO) {
		this.authenticateDAO = authenticateDAO;
	}

	public void setUserFunctionsDAO(UserFunctionsDAO userFunctionsDAO) {
		this.userFunctionsDAO = userFunctionsDAO;
	}

	public void setServiceEmail(ServiceEmail serviceEmail) {
		this.serviceEmail = serviceEmail;
	}
	/**
	 * Set Investigator bean Spring
	 * @param investigatorDAO
	 */
	public void setInvestigatorDAO(InvestigatorDAO investigatorDAO) {
		this.investigatorDAO = investigatorDAO;
	}
	
}
