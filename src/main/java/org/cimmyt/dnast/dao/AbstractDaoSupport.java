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
package org.cimmyt.dnast.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public abstract class AbstractDaoSupport <T> extends HibernateDaoSupport implements DaoSupport<T>{
	
	private int pageSize = 100;
	
	protected Class<T> type;
	
	public AbstractDaoSupport(Class<T> type) {
		this.type = type;
	}
	

	@SuppressWarnings("unchecked")
	public T getBeanById(final Integer id) {
		return (T)getHibernateTemplate().execute(new HibernateCallback<Object>() {

			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.get(type, id);
			}
			
		});
	}


	@SuppressWarnings("unchecked")
	public List<T> getListByBean(final T filter, final Integer pageNum){
		return (List<T>) getHibernateTemplate().execute(new HibernateCallback<Object>(){

			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(type)
				.setMaxResults(pageSize)
				.setFirstResult(pageSize * pageNum)
				.setFetchSize(pageSize);
				
				if(filter != null)
					buildCriteria(criteria, filter);
				
				return criteria.list();
			}
			
		});
	}
	
	protected abstract void buildCriteria(Criteria criteria, final T filter);

}

