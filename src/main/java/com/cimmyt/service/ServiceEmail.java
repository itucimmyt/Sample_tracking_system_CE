package com.cimmyt.service;

import org.cimmyt.dnast.dto.AuthUserBean;

public interface ServiceEmail {

	public void sendEmailCreationUser (int crop, String emailApplicant, String userName);
	public void sendEmailWithCredencial(AuthUserBean user);
}
