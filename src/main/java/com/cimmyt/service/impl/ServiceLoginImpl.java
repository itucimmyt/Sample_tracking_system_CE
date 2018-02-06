package com.cimmyt.service.impl;

import com.cimmyt.bean.UserBean;
import com.cimmyt.exception.BackException;
import com.cimmyt.model.dao.AuthenticateDAO;
import com.cimmyt.service.ServiceLogin;
import com.cimmyt.utils.PropertyHelper;

public class ServiceLoginImpl implements ServiceLogin{

	private AuthenticateDAO authenticateDAO;
	@Override
	public UserBean validateUserBySystem(String user, String password, int organismid, PropertyHelper pro)
			throws BackException {
		return authenticateDAO.validateUser(user, password, organismid);
		
	}

	@Override
	public UserBean validateUserByActiveDirectory(String user, String password,
			int idCrop, PropertyHelper pro) throws BackException {
		// TODO Auto-generated method stub
		return null;
	}

	public void setAuthenticateDAO(AuthenticateDAO authenticateDAO) {
		this.authenticateDAO = authenticateDAO;
	}
	
}
