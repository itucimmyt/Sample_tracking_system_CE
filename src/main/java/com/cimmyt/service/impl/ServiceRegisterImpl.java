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
package com.cimmyt.service.impl;

import java.util.List;

import org.cimmyt.dnast.dto.AuthUserBean;

import com.cimmyt.model.bean.Register;
import com.cimmyt.model.dao.RegisterDAO;
import com.cimmyt.model.dao.UserDAO;
import com.cimmyt.rest.client.ClientCatalogContry;
import com.cimmyt.rest.client.Result;
import com.cimmyt.service.ServiceEmail;
import com.cimmyt.service.ServiceRegister;

public class ServiceRegisterImpl implements ServiceRegister{

	private RegisterDAO registerDAO;
	private ClientCatalogContry serviceClientContry;
	private UserDAO userDAO; 
	private ServiceEmail serviceEmail;
	/**
	 * Method that return a register's list
	 * @param Register object from model
	 * @return List Register  
	 */
	@Override
	public List<Register> getListRegister(Register model) {
		List<Register> list =registerDAO.getListRegister(model);
		if (list!= null && !list.isEmpty())
			return list;
		else 
			return null;
		
	}
	/**
	 * Method  to save a new Register
	 * @param Register
	 */
	@Override
	public void add(Register model, int idCrop) {
		serviceEmail.sendEmailCreationUser(idCrop, model.getEmail(), model.getFisrtName()+" "+model.getLastName());
		registerDAO.saveOrUpdate(model);
		
	}

	/**
	 * Method that update a Register record with filed is pending false
	 * @param Register
	 */
	@Override
	public void delete(Register model) {
		registerDAO.delete(model);
		
	}

	public Register validateEmail (Register model){
		return registerDAO.getRegister(model);
	}
	/**
	 * Method that return to Register Object 
	 * @param Register 
	 * @return Register
	 */
	@Override
	public Register getRegister(Register model) {
		return registerDAO.getRegister(model);
	}

	@Override
	public boolean validateEmailAndUser(Register model){
		        boolean registerRecord = validateRegisterUer(model);
		        if (registerRecord)
		        	return true;
		        
		        boolean validaUser = validateInUser(model);
		        if (validaUser)
		        	return true;
		return false;
	}

	private boolean validateRegisterUer (Register model){
			Register newModel = new Register();
			newModel.setFisrtName(model.getFisrtName());
			newModel.setLastName(model.getLastName());
		 Register register = registerDAO.getRegister(newModel);
		 if (register != null && register.getIdRegister() != null)
			 return true;
		 	/*newModel = new Register();
			newModel.setLastName(model.getLastName());
			register = registerDAO.getRegister(newModel);
			 if (register != null && register.getIdRegister() != null)
			 return true;*/
			 newModel = new Register();
				newModel.setEmail(model.getEmail());
				register = registerDAO.getRegister(newModel);
				 if (register != null && register.getIdRegister() != null)
				 return true;
		return false;
	}
	private boolean validateInUser (Register model){
	    AuthUserBean researcher = new AuthUserBean ();
        researcher.setEmail(model.getEmail());
        researcher.setUserName(model.getFisrtName() + " "+model.getLastName());
        researcher.setInvestName((model.getFisrtName() + " "+model.getLastName()));
        
        AuthUserBean findResr = userDAO.getInvestigatorNameAbbreviationRegistred(researcher);
        if (findResr != null && findResr.getIdUser() != null)
        	return true;
        researcher = new AuthUserBean ();
        researcher.setUserName(model.getFisrtName() + " "+model.getLastName());
        findResr = userDAO.getInvestigatorNameAbbreviationRegistred(researcher);
        if (findResr != null && findResr.getIdUser() != null)
        	return true;
		return false;
	}
	public void setRegisterDAO(RegisterDAO registerDAO) {
		this.registerDAO = registerDAO;
	}

	public void setServiceClientContry(ClientCatalogContry serviceClientContry) {
		this.serviceClientContry = serviceClientContry;
	}
	@Override
	public List<Result> getContries() {
		return serviceClientContry.getListContry();
	}
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	public void setServiceEmail(ServiceEmail serviceEmail) {
		this.serviceEmail = serviceEmail;
	}

	
}
