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
package com.cimmyt.model.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.cimmyt.model.bean.Register;
import com.cimmyt.model.dao.RegisterDAO;

/**
 * Class to DAO for Register table
 * @author cimmyt
 *
 */
public class RegisterDAOImpl extends AbstractDAO<Register, Integer>  implements RegisterDAO{

	public RegisterDAOImpl() {
		super(Register.class);
	}

	/**
	 * Method that return a Register's List
	 * @param Register filter
	 * @return List Register
	 */
	@Override
	public List<Register> getListRegister(Register filter) {
		return super.getListByFilter(filter);
	}

	/**
	 * Method that save a object Register
	 * @param Register
	 */
	@Override
	public void saveOrUpdate(Register register) {
		super.update(register);
	}

	/**
	 * Method that update a object register in the field pending false
	 * @param  Register 
	 */
	@Override
	public void delete(Register register) {
		register.setIsPending(false);
		super.update(register);
		
	}

	/**
	 * Method that return a object Register
	 * @param Register
	 */
	@Override
	public Register getRegister(Register register) {
		return super.getByFilterUnicValue(register);
	}

	/**
	 * Method that create the filters to get a register object
	 */
	@Override
	protected void buildCriteria(DetachedCriteria criteria, Register filter) {
		if (filter.getIdRegister() != null)
			criteria.add(Restrictions.eq("idRegister", filter.getIdRegister()));
		
		if (filter.getIsPending() != null)
			criteria.add(Restrictions.eq("isPending", filter.getIsPending()));;
		if (filter.getFisrtName() !=  null && !filter.getFisrtName().isEmpty()) 
			criteria.add(Restrictions.eq("fisrtName", filter.getFisrtName()));
		if (filter.getLastName() != null && !filter.getLastName().isEmpty() )
			criteria.add(Restrictions.eq("lastName", filter.getLastName()));
		if (filter.getEmail() != null && !filter.getEmail().isEmpty() )
			criteria.add(Restrictions.eq("email", filter.getEmail()));
	}

	@Override
	protected void buildCriteria(DetachedCriteria criteria, Register filter,
			Integer id) {
		// TODO Auto-generated method stub
		
	}

}
