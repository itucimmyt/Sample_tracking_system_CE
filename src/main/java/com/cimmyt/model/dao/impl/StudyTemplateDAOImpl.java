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
//$ Id: StudyTemplateDAO.java, Jun 28, 2010 TMSANCHEZ $
package com.cimmyt.model.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.transaction.annotation.Transactional;

import com.cimmyt.model.bean.StudyTemplate;
import com.cimmyt.model.bean.StudyTemplateParams;
import com.cimmyt.model.dao.StudyTemplateDAO;
import com.cimmyt.model.dao.StudyTemplateParamsDAO;
import com.cimmyt.utils.StrUtils;

/**
 * TODO add class documentation here
 * @version $Revision$, $Date: 2010-08-11 17:38:20 -0500 (Wed, 11 Aug 2010) $
 */

@Transactional
public class StudyTemplateDAOImpl extends AbstractDAO<StudyTemplate, Integer> implements StudyTemplateDAO{

	private StudyTemplateParamsDAO studyTemplateParamsDAO;
	public StudyTemplateDAOImpl() {
		super(StudyTemplate.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.cimmyt.cril.apps.sampletracking.core.persistence.dao.AbstractDAO#
	 * buildCriteria(org.hibernate.criterion.DetachedCriteria, java.lang.Object)
	 */
	@Override
	protected void buildCriteria(DetachedCriteria criteria,
			StudyTemplate filter) {
		if (filter.getStudytemplateid() != null) {
			criteria.add(Restrictions.eq("studytemplateid", filter.getStudytemplateid()));
		}
		
		if (StrUtils.notEmpty(filter.getTemplatename() )) {
			criteria.add(Restrictions.like("templatename", "%"+filter.getTemplatename()+"%"));
		}

	}
	/**
	 * Get Study Template by ID
	 * @param Intender id
	 */
	@Override
	public StudyTemplate read(final Integer id) {
		StudyTemplate result = (StudyTemplate) getHibernateTemplate()
				.execute(new HibernateCallback() {
					StudyTemplate result = null;
					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						result = (StudyTemplate)session.get(StudyTemplate.class, id);
						for (StudyTemplateParams params : result
								.getImsStudyTemplateParamsCollection()) {
							params.getDescription();
						}
						return result;
					}
				});
		return result;
	}

	/**
	 * Get List Study Template 
	 * @param StudyTemplate
	 */
	@Override
	public List<StudyTemplate> getStudyTemplate (StudyTemplate bean){
		List<StudyTemplate> list = super.getListByFilter(bean);
		if (list!= null && !list.isEmpty()){
			List<StudyTemplate> listCopy = new ArrayList<StudyTemplate>();
			for (StudyTemplate simple : list){
				StudyTemplate temp = read(simple.getStudytemplateid());
				listCopy.add(temp);
			}
			return listCopy;
		}
		return null;
	}

	/**
	 * Add new Study Template
	 * @param Study template
	 */
	@Override
	public void add (StudyTemplate bean){
		super.update(bean);
	}

	/**
	 * Delete Study template
	 * @param Study Template
	 */
	@Override
	public void delete (StudyTemplate bean){
		super.delete(bean);
	}
	/**
	 * Get Study template by name
	 * @param Study Template
	 */
	@Override
	public StudyTemplate getStudyTemplateByNameregistred( String name){
		try{
			StudyTemplate bean = super.findSingleByValues( new StudyTemplate(), new String []{"templatename"},new Object []{name});
			return bean;
			}catch (Exception e){
				e.printStackTrace();
				return null;
			}
	}

	public void setStudyTemplateParamsDAO(
			StudyTemplateParamsDAO studyTemplateParamsDAO) {
		this.studyTemplateParamsDAO = studyTemplateParamsDAO;
	}

	@Override
	protected void buildCriteria(DetachedCriteria criteria,
			StudyTemplate filter, Integer id) {
		// TODO Auto-generated method stub
		
	}

	
}
