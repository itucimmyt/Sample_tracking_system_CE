package com.cimmyt.service;

import java.util.List;

import com.cimmyt.bean.UserBean;
import com.cimmyt.exception.BackException;

public interface ServiceUser {

	public void saveOrUpdatetUser(UserBean userBean, List<Integer> listFuntions, int id) throws BackException;
	public void deleteUser(UserBean userBean) throws BackException;
	public UserBean getUser(UserBean userBean ) throws BackException;
	public List<UserBean> getListUserBean(UserBean userBean) throws BackException;
	public boolean validateUserBynameOrUserOrEmail(UserBean userBean);
	public void fisicalDeletetUser(UserBean user)	throws BackException;

}
