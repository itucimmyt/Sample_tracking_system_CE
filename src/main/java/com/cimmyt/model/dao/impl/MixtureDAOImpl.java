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

import com.cimmyt.model.bean.SampleMixture;
import com.cimmyt.model.dao.MixtureDAO;

public class MixtureDAOImpl extends AbstractDAO<SampleMixture, Integer> implements MixtureDAO{

	public MixtureDAOImpl() {
		super(SampleMixture.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<SampleMixture> getListSampleMixture(SampleMixture filter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveOrUpdate(SampleMixture transientObject) {
	super.update(transientObject);
		
	}

	@Override
	public SampleMixture getSampleMixture(SampleMixture transientObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete (SampleMixture transientObject){
		super.delete(transientObject);
	}
	@Override
	protected void buildCriteria(DetachedCriteria criteria, SampleMixture filter) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void buildCriteria(DetachedCriteria criteria,
			SampleMixture filter, Integer id) {
		// TODO Auto-generated method stub
		
	}

}
