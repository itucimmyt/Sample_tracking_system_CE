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

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.cimmyt.model.bean.UserFuntions;
import com.cimmyt.model.dao.UserFunctionsDAO;

public class UserFunctionsDAOImpl extends AbstractDAO<UserFuntions, Integer> implements UserFunctionsDAO{

	private Logger logger= Logger.getLogger(UserFunctionsDAOImpl.class);
	public UserFunctionsDAOImpl (){
		super(UserFuntions.class);
	}
	@Override
	public void saveOrUpdate(UserFuntions userFuntions) {
		super.update(userFuntions);
	}

	@Override
	public void delete(UserFuntions userFuntions) {
		super.delete(userFuntions);
	}

	@Override
	public void deleteListUserFunctions(List<UserFuntions> listUserFuntions) {
		for (UserFuntions user : listUserFuntions){
			super.delete(user);
		}
	}

	@Override
	public void deleteSetUserFuntions(Set<UserFuntions> setUserFunctions) {
	Iterator<UserFuntions> iterator = setUserFunctions.iterator();
		while (iterator.hasNext()){
			super.delete((UserFuntions)iterator.next());
		}
	}
	@Override
	public void saveUserFunctions(List<UserFuntions> listUserFuntions) {
		for (UserFuntions user : listUserFuntions){
			super.update(user);
		}
	}

	@Override
	public void saveSetUserFunctions(Set<UserFuntions> setUserFunctions) {
	Iterator<UserFuntions> iterator = setUserFunctions.iterator();
		while (iterator.hasNext()){
			super.update((UserFuntions)iterator.next());
		}
	}
	@Override
	public UserFuntions getUserFunctions(UserFuntions userFuntions) {
		
		try{
			return super.findSingleByValues(new UserFuntions(), new String []{"stUserVersion.idUser","Funtions.idstFuntions"}, new Object []{userFuntions.getStUserVersion().getIdUser(),userFuntions.getStFuntions().getIdstFuntions()});
		}catch (Exception ex){
			logger.error(ex.getMessage(), ex);
			return null;
		}
	}

	@Override
	public List<UserFuntions> getListUserFunctions(UserFuntions userFuntions) {
		return super.getListByFilter(userFuntions);
	}
	@Override
	protected void buildCriteria(DetachedCriteria criteria, UserFuntions filter) {
		if (filter.getStUserVersion()!= null && filter.getStUserVersion().getIdUser()!= null && filter.getStUserVersion().getIdUser().intValue() > 0){
			criteria.add(Restrictions.eq("stUserVersion.idUser", filter.getStUserVersion().getIdUser()));
		}
		
	}
	@Override
	protected void buildCriteria(DetachedCriteria criteria,
			UserFuntions filter, Integer id) {
		// TODO Auto-generated method stub
		
	}
}
