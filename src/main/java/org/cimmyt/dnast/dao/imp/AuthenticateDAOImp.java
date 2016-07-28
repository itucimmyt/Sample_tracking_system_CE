/*
Copyright 2013 International Maize and Wheat Improvement Center
   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at
       http://www.apache.org/licenses/LICENSE-2.0
   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package org.cimmyt.dnast.dao.imp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.cimmyt.dnast.dto.AuthUserBean;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.cimmyt.bean.InvestigatorBean;
import com.cimmyt.bean.RoleBean;
import com.cimmyt.bean.UserBean;
import com.cimmyt.exception.BackException;
import com.cimmyt.model.bean.UserFuntions;
import com.cimmyt.model.dao.AuthenticateDAO;

public class AuthenticateDAOImp  extends HibernateDaoSupport implements AuthenticateDAO{

	private final String ERROR_1 = "error1"; //invalid User
	private final String ERROR_2 = "error2"; //invalid password
	private final String ERROR_3 = "error3"; //invalid crop
	private boolean isValidOrganism = true;
	
	public UserBean validateUser(final String user, final String password, final int organismid)throws BackException{
		final UserBean userBean = new UserBean();
		AuthUserBean authUserBean =
		 (AuthUserBean)getHibernateTemplate().execute(new HibernateCallback<AuthUserBean>(){
			
			@Override
			public AuthUserBean doInHibernate(Session session) throws HibernateException, SQLException{
				AuthUserBean authUser;
				authUser = (AuthUserBean)session.createCriteria(AuthUserBean.class)
						.setMaxResults(1)
						.add(Restrictions.eq("userName", user))
						//.add(Restrictions.eq("password", password))
						//.add(Restrictions.eq("stOrganism.organismid",organismid ))
						.uniqueResult();
				
				if (authUser == null)
					return null;
				if (!authUser.getPassword().equals(password))
					return authUser;
				if (authUser.getStOrganism() == null || !authUser.getStOrganism().getOrganismid().equals(organismid)){
					isValidOrganism = false;
					return authUser;
				}
	
				//authUser.getInvestigator().setInvestPwd("");
				/*Para forzar la busqueda del investigador asociado y diferencias mayusculas*/
				/*
				if(authUser != null && authUser.getInvestigator() != null){
					String nameTmp = authUser.getUserName();
					String passTmp = authUser.getPassword();
					if(!nameTmp.trim().equals(user) || !passTmp.trim().equals(password)){
						authUser = null;
					}else{
						authUser.getInvestigator().setInvestPwd("");
					}
				}*/
				authUser.getInvestigator();
				authUser.getStOrganism();
				authUser.getStRole();
				authUser.setUserFuntionses(authUser.getUserFuntionses());
				List<String> listString = new ArrayList<String>();
				for (UserFuntions userF : authUser.getUserFuntionses()){
					listString.add(userF.getStFuntions().getFuntionKey());
				}
				userBean.setUserFuntionses(listString);
				isValidOrganism = true;
				userBean.setUserName(authUser.getUserName());	
				InvestigatorBean invest = new InvestigatorBean();
				invest.setInvest_abbreviation(authUser.getInvestigator().getInvestAbbreviation());
				//invest.setInvest_email(authUser.getInvestigator().getInvestEmail());
				invest.setInvest_name(authUser.getInvestigator().getInvestName());
				//invest.setInvest_phone(authUser.getInvestigator().getInvestPhone());
				invest.setInvestigatorid(authUser.getInvestigator().getInvestigatorid());
				userBean.setInvestigatorBean(invest);
				userBean.setOrganism(authUser.getStOrganism());
				userBean.setRole(new RoleBean(authUser.getStRole().getIdstRol(),authUser.getStRole().getName()));
				
				userBean.setResearcherName(authUser.getInvestName());
				userBean.setResearcherEMail(authUser.getEmail());
				return authUser;
			}
		});
		if (authUserBean == null)
			throw new BackException(ERROR_1);
		if (!authUserBean.getPassword().equals(password))
			throw new BackException(ERROR_2);
		if (!isValidOrganism)
			throw new BackException(ERROR_3);
		
		return userBean;
	}

	public AuthUserBean getAuthUserBeanById(final int id) throws BackException{
		AuthUserBean authUserBean =	 getHibernateTemplate().execute(new HibernateCallback<AuthUserBean>(){
					@Override
					public AuthUserBean doInHibernate(Session session) throws HibernateException, SQLException{
						AuthUserBean authUser;
						authUser = (AuthUserBean)session.createCriteria(AuthUserBean.class)
								.setMaxResults(1)
								.add(Restrictions.eq("idUser", id))
								//.add(Restrictions.eq("password", password))
								//.add(Restrictions.eq("stOrganism.organismid",organismid ))
								.uniqueResult();

						if (authUser == null)
							return null;
						authUser.getInvestigator();
						authUser.getStOrganism();
						authUser.getStRole();
						
						Set<UserFuntions> userFuntionses = new HashSet<UserFuntions>(0);
						for (UserFuntions userF : authUser.getUserFuntionses()){
							userFuntionses.add(userF);
						}
						authUser.setUserFuntionses(userFuntionses);
						return authUser;
						
					 }
				   }
				 );
		return authUserBean;
	}
}

