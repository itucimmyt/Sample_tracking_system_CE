package com.cimmyt.model.dao;

import java.util.List;

import org.cimmyt.dnast.dto.AuthUserBean;

public interface UserDAO {

	public List<AuthUserBean> getInvestigator (AuthUserBean filter);
	public Integer saveOrUpdate(AuthUserBean filter);
	public void delete (AuthUserBean filter);
	public AuthUserBean getInvestigatorNameAbbreviationRegistred(AuthUserBean filter);
	public AuthUserBean getAuthUserBeanByUserNameOrEmailOrName(AuthUserBean authUser);
	public List<AuthUserBean> getListAuthUserBeanByIDRole (AuthUserBean authUser);
	public void fisicaldelete(AuthUserBean filter) ;
}
