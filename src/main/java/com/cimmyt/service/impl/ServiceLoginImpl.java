package com.cimmyt.service.impl;

import com.cimmyt.bean.UserBean;
import com.cimmyt.exception.BackException;
import com.cimmyt.model.dao.AuthenticateDAO;
import com.cimmyt.service.ServiceLogin;
import com.cimmyt.utils.PropertyHelper;

public class ServiceLoginImpl implements ServiceLogin{

	private AuthenticateDAO authenticateDAO;
	private final String ERROR_1 = "error1"; //invalid User
	private final String ERROR_2 = "error2"; //invalid password
	private final String ERROR_3 = "error3"; //invalid crop
	@Override
	public UserBean validateUserBySystem(String user, String password, int organismid, PropertyHelper pro)
			throws BackException {
		
		try{
		return authenticateDAO.validateUser(user, password, organismid);
		}catch (BackException backException){
			switch (backException.getMessage()){
			case ERROR_1 : 
				return null;
			case ERROR_2:
				return null;
			case ERROR_3:
				return null;
			default :
				
			}
			
		}
		return null;
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
