package com.cimmyt.model.dao.impl;

import java.util.List;

import org.cimmyt.dnast.dto.AuthUserBean;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.cimmyt.model.dao.UserDAO;
import com.cimmyt.utils.ConstantsDNA;

public class UserDAOImpl extends AbstractDAO<AuthUserBean, Integer>  implements UserDAO{

	public UserDAOImpl(){
		super(AuthUserBean.class);
	}
	@Override
	public List<AuthUserBean> getInvestigator(AuthUserBean filter) {
		return super.getListByFilter(filter);
	}

	@Override
	public Integer saveOrUpdate(AuthUserBean filter) {
		super.update(filter);
		return filter.getIdUser();
	}

	@Override
	public void delete(AuthUserBean filter) {
		filter.setStatus(false);
		super.update(filter);
	}

	@Override
	public void fisicaldelete(AuthUserBean filter) {
		super.delete(filter);
	}
	
	@Override
	public AuthUserBean getInvestigatorNameAbbreviationRegistred(
			AuthUserBean filter) {
		return super.getByFilterUnicValue(filter);
	}
	
	@Override
	public List<AuthUserBean> getListAuthUserBeanByIDRole (AuthUserBean authUser){
		switch (authUser.getStRole().getIdstRol()){
		case ConstantsDNA.ROLE_DATA_MANAGER:{
				String sql= "FROM AuthUserBean where stRole.idstRol in ("+ConstantsDNA.ROLE_RESEARCHER +" ,"+ConstantsDNA.ROLE_DATA_MANAGER+","+ConstantsDNA.ROLE_RESEARCHER_ASSISTENT+","+ConstantsDNA.ROLE_ASSISTENT+") and status = true";
				return (List<AuthUserBean>)super.findListByQuery(new AuthUserBean(), sql);
			}
		case ConstantsDNA.ROLE_RESEARCHER:{
				String sql= "FROM AuthUserBean where investigator.investigatorid ="+authUser.getInvestigator().getInvestigatorid()+ " and stRole.idstRol <> 1 and status = true";
				return (List<AuthUserBean>)super.findListByQuery(new AuthUserBean(), sql);
			}
		}
		return null;
		
	}
	public AuthUserBean getAuthUserBeanByUserNameOrEmailOrName(AuthUserBean authUser){
		String sql= "FROM AuthUserBean where userName ='"+authUser.getUserName()+"' or email ='"+authUser.getEmail()+"' or investName ='"+authUser.getInvestName()+"' and status = true";
		return (AuthUserBean) super.findGenericByQuery(new AuthUserBean(), sql);
	}

	@Override
	protected void buildCriteria(DetachedCriteria criteria, AuthUserBean filter) {
		if (filter != null){
			
			if ( filter.getIdUser()!=  null){
				criteria.add(Restrictions.eq("idUser", filter.getIdUser()));
			}
			if (filter.getEmail() != null && !filter.getEmail().trim().equals("")){
				Criterion cri1 = Restrictions.eq("email", filter.getEmail()); 
				Criterion cri2 = Restrictions.eq("investEmail", filter.getInvestEmail());
				criteria.add(Restrictions.or(cri1,cri2));
				//criteria.add(Restrictions.eq("email", filter.getEmail()));
			}
			
			if (filter.getUserName() != null && !filter.getUserName().trim().equals("")){
				Criterion cri1 = Restrictions.eq("userName", filter.getUserName()); 
				Criterion cri2 = Restrictions.eq("investName", filter.getUserName());
				criteria.add(Restrictions.or(cri1,cri2));
			}
			/*
			if (filter.getInvestEmail() != null && !filter.getInvestEmail().trim().equals("")){
				criteria.add(Restrictions.eq("investEmail", filter.getInvestEmail()));
			}
			if (filter.getUserName() != null && !filter.getUserName().trim().equals("")){
				criteria.add(Restrictions.eq("userName", filter.getUserName()));
			}
			if (filter.getInvestEmail() != null && !filter.getInvestName().trim().equals("")){
				criteria.add(Restrictions.eq("investName", filter.getInvestName()));
			}*/
		}

	}

	@Override
	protected void buildCriteria(DetachedCriteria criteria,
			AuthUserBean filter, Integer id) {
		// TODO Auto-generated method stub
		
	}
}
