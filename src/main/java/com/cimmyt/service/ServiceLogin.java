package com.cimmyt.service;

import com.cimmyt.bean.UserBean;
import com.cimmyt.exception.BackException;
import com.cimmyt.utils.PropertyHelper;

public interface ServiceLogin {

	public UserBean validateUserBySystem(String user, String password, int idCrop, PropertyHelper pro) throws BackException;
	public UserBean validateUserByActiveDirectory(String user, String password, int idCrop, PropertyHelper pro) throws BackException;
}
