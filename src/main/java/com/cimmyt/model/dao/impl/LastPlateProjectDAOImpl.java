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

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.cimmyt.model.bean.LabStudy;
import com.cimmyt.model.bean.LastPlateProject;
import com.cimmyt.model.dao.LastPlateProjectDAO;

/**
 * @author CIMMYT 
 */
public class LastPlateProjectDAOImpl extends AbstractDAO<LastPlateProject, Integer> implements LastPlateProjectDAO{

	public LastPlateProjectDAOImpl() {
		super(LastPlateProject.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void buildCriteria(DetachedCriteria criteria,
			LastPlateProject filter) {
		// TODO Auto-generated method stub

	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public LastPlateProject getLastPlateNumberOfStudyLab(final LabStudy labstudy){
		// first define a queryString
		LastPlateProject lastPlateProjectToStudy=null;
		final String queryString = "from LastPlateProject as lpp "
				+ " where lpp.projectid.projectid = :PROJECTID "
				+ " and lpp.organismid.organismid = :ORGANISMID "
				+ " and lpp.investigatorid.investigatorid= :INVESTIGATORID";

		lastPlateProjectToStudy = (LastPlateProject) getHibernateTemplate().execute(
				new HibernateCallback() {
					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						LastPlateProject lpp=new LastPlateProject();
						lpp.setInvestigatorid(labstudy.getInvestigatorid());
						lpp.setProjectid( labstudy.getProject());
						lpp.setOrganismid(labstudy.getOrganismid());
						lpp.setPlatenumber(0);
						
						Query query = session.createQuery(queryString);
						query.setParameter("PROJECTID", labstudy.getProject().getProjectid());
						query.setParameter("ORGANISMID", labstudy.getOrganismid().getOrganismid());
						query.setParameter("INVESTIGATORID", labstudy.getInvestigatorid().getInvestigatorid());

						if (query.uniqueResult() != null) {
							lpp = (LastPlateProject) query
									.uniqueResult();
						}
						return lpp;
					}
				});
		return lastPlateProjectToStudy;
	}
	/**
	 * 
	 * @param projectId
	 * @param organismId
	 * @param investigatorId
	 * @return 0 if not found, plate number if found
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Integer getNextPlateNumber(final Integer projectId,
			final Integer organismId, final Integer investigatorId) {
		Integer plateNumber = 0;

		// first define a queryString
		final String queryString = "from LastPlateProject as lpp "
				+ " where lpp.projectid.projectid = :PROJECTID "
				+ " and lpp.organismid.organismid = :ORGANISMID "
				+ " and lpp.investigatorid.investigatorid= :INVESTIGATORID";

		plateNumber = (Integer) getHibernateTemplate().execute(
				new HibernateCallback() {

					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Integer plateNum =0;
						Query query = session.createQuery(queryString);
						query.setParameter("PROJECTID", projectId);
						query.setParameter("ORGANISMID", organismId);
						query.setParameter("INVESTIGATORID", investigatorId);

						if (query.uniqueResult() != null) {
							LastPlateProject lpp = (LastPlateProject) query
									.uniqueResult();
							plateNum=lpp.getPlatenumber();
						}

					
						return plateNum;
					}
				});

		return plateNumber;
	}
	/**
	 * Method that save or update the object Last plate Project
	 * @param LastPlateProject lastplateProject 
	 */
	public void addOrUpdateLastPlateProjectNumber(LastPlateProject lastplateProject){
		super.update(lastplateProject);
	}

	@Override
	protected void buildCriteria(DetachedCriteria criteria,
			LastPlateProject filter, Integer id) {
		// TODO Auto-generated method stub
	}
}
