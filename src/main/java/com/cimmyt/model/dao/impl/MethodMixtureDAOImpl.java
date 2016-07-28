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

import com.cimmyt.model.bean.MixtureMethod;
import com.cimmyt.model.dao.MethodMixtureDAO;

public class MethodMixtureDAOImpl extends AbstractDAO<MixtureMethod, Integer> implements MethodMixtureDAO {

	public MethodMixtureDAOImpl() {
		super(MixtureMethod.class);
	}

	@Override
	public List<MixtureMethod> getListMethodMixture(MixtureMethod filter) {
		return super.getListByFilter(filter);
	}

	@Override
	public void saveOrUpdate(MixtureMethod method) {
		super.update(method);
		
	}

	@Override
	public void delete(MixtureMethod method){
		super.delete(method);
	}
	@Override
	public MixtureMethod getMixtureMethod(MixtureMethod method) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void buildCriteria(DetachedCriteria criteria, MixtureMethod filter) {
		if(filter != null){
			if(filter.getDescription()!= null && !filter.getDescription().equals("")){
				criteria.add(Restrictions.eq("description", 
						filter.getDescription()));
			}
			if(filter.getMixtureMethodId()>0){
				criteria.add(Restrictions.eq("mixtureMethodId", 
						filter.getMixtureMethodId()));
			}
		}
		
	}

	@Override
	protected void buildCriteria(DetachedCriteria criteria,
			MixtureMethod filter, Integer id) {
		// TODO Auto-generated method stub
		
	}

	
}
