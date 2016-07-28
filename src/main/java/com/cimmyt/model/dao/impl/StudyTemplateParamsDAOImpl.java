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
//
// Created: April 2009
//
// Copyright 2009 International Rice Research Institute (IRRI) and 
// Centro Internacional de Mejoramiento de Maiz y Trigo (CIMMYT)
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//
//$ Id: StudyTemplateParamsDAO.java, Jun 28, 2010 TMSANCHEZ $
package com.cimmyt.model.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.cimmyt.model.bean.StudyTemplate;
import com.cimmyt.model.bean.StudyTemplateParams;
import com.cimmyt.model.dao.StudyTemplateParamsDAO;

/**
 * TODO add class documentation here
 * @version $Revision$, $Date: 2010-08-11 17:38:20 -0500 (Wed, 11 Aug 2010) $
 */
public class StudyTemplateParamsDAOImpl extends AbstractDAO<StudyTemplateParams, Integer> implements StudyTemplateParamsDAO{

	public StudyTemplateParamsDAOImpl() {
		super(StudyTemplateParams.class);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.cimmyt.cril.apps.sampletracking.core.persistence.dao.AbstractDAO#buildCriteria(org.hibernate.criterion.DetachedCriteria, java.lang.Object)
	 */
	@Override
	protected void buildCriteria(DetachedCriteria criteria,
			StudyTemplateParams filter) {
		// TODO Auto-generated method stub
		
	}
	public String getDataTypeFromParameter(final Integer paramid){
		
		final String queryString = "select stp.datatype from " +
				" StudyTemplateParams as stp "
				+ " where stp.templateparamid= :PARAM";
				
		String result= (String) getHibernateTemplate().execute(
				new HibernateCallback() {

					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						
						Query query = session.createQuery(queryString);
						query.setParameter("PARAM",paramid);
						String resultado=(String)query.uniqueResult();
						
						return resultado;
					}
				});
		
		return result;
	}
	
	
	public String getNameFromParameter(final Integer paramid){
		
		final String queryString = "select stp.parametername from " +
				" StudyTemplateParams as stp "
				+ " where stp.templateparamid= :PARAM";
				
		String result= (String) getHibernateTemplate().execute(
				new HibernateCallback() {

					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						
						Query query = session.createQuery(queryString);
						query.setParameter("PARAM",paramid);
						String resultado=(String)query.uniqueResult();
						
						return resultado;
					}
				});
		
		return result;
	}

	@Override
	public List<StudyTemplateParams> getStudyTemplateParams(StudyTemplate bean) {
		// TODO Auto-generated method stub
		return null;
	}

	public void delete(StudyTemplateParams params){
		super.delete(params);
	}

	@Override
	protected void buildCriteria(DetachedCriteria criteria,
			StudyTemplateParams filter, Integer id) {
		// TODO Auto-generated method stub
		
	}
}
