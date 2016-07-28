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

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.cimmyt.model.bean.MixtureDetail;
import com.cimmyt.model.bean.SampleMixture;
import com.cimmyt.model.dao.MixtureDetailDAO;

public class MixtureDetailDAOImpl extends AbstractDAO<MixtureDetail, Integer> implements MixtureDetailDAO{

	public MixtureDetailDAOImpl() {
		super(MixtureDetail.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<SampleMixture> getListMixtureDetail(MixtureDetail filter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveOrUpdate(MixtureDetail transientObject) {
		super.update(transientObject);		
	}

	@Override
	public void delete (MixtureDetail persistentObject){
		super.delete(persistentObject);
	}

	@Override
	public SampleMixture getMixtureDetail(MixtureDetail object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void buildCriteria(DetachedCriteria criteria, MixtureDetail filter) {

	}
		
	public Integer getLastMixtureDetailID(){
		final String queryString = "select max(mx.mixtureDetailId)from MixtureDetail as mx";
		Integer result= (Integer) getHibernateTemplate().execute(
				new HibernateCallback() {
					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createQuery(queryString);
						Integer resultado=(Integer)query.uniqueResult();
						
						if (resultado==null)
							return 0;
						else
							return resultado;
					}
				});
		
		return result;
	}

	@Override
	protected void buildCriteria(DetachedCriteria criteria,
			MixtureDetail filter, Integer id) {
		// TODO Auto-generated method stub
		
	}	

}
