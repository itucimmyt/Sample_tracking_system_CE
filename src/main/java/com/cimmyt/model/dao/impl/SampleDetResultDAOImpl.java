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
//$ Id: SampleDetResultDAO.java, Jun 28, 2010 TMSANCHEZ $
package com.cimmyt.model.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.cimmyt.model.bean.SampleDetResult;
import com.cimmyt.model.dao.SampleDetResultDAO;

/**
 * TODO add class documentation here
 * @version $Revision$, $Date: 2010-09-09 13:38:49 -0500 (Thu, 09 Sep 2010) $
 */
public class SampleDetResultDAOImpl extends AbstractDAO<SampleDetResult, Integer> implements SampleDetResultDAO {

	public SampleDetResultDAOImpl() {
		super(SampleDetResult.class);
	}

	/* (non-Javadoc)
	 * @see org.cimmyt.cril.apps.sampletracking.core.persistence.dao.AbstractDAO#buildCriteria(org.hibernate.criterion.DetachedCriteria, java.lang.Object)
	 */
	@Override
	protected void buildCriteria(DetachedCriteria criteria,
			SampleDetResult filter) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * 
	 * @param labStudyId
	 * @param entryNo
	 * @return
	 */
	public SampleDetResult getSampleDetResultBySampleDetailIdAndTemplateParamId(
			final Integer sampleId, final Integer paramId) {
		SampleDetResult sampleDetResult = null;

		final String queryString = "from SampleDetResult as sdr "
				+ " where  sdr.studysampleid.studysampleid = :SAMPLEID "
				+ " and sdr.templateparamid.templateparamid = :PARAMID";

		sampleDetResult = (SampleDetResult) getHibernateTemplate().execute(
				new HibernateCallback() {
					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						SampleDetResult sampleDetailResult = null;

						Query query = session.createQuery(queryString);

						query.setParameter("SAMPLEID", sampleId);
						query.setParameter("PARAMID", paramId);

						sampleDetailResult = (SampleDetResult) query
								.uniqueResult();

						return sampleDetailResult;
					}
				});

		return sampleDetResult;
	}

	@Override
	protected void buildCriteria(DetachedCriteria criteria,
			SampleDetResult filter, Integer id) {
		// TODO Auto-generated method stub
		
	}

	public List<SampleDetResult> getListSamplesDetResultByIdSampleID(Integer sampleId){
		try{
			return super.findListByValues( new SampleDetResult(), new String []{"studysampleid.studysampleid"},new Object []{sampleId});
		}catch (Exception ex){
			ex.printStackTrace();
		}
		return null;
	}

	public void delteSampleDetResult(SampleDetResult sampleDetResult){
		super.delete(sampleDetResult);
	}

	public void saveOrUpdate(SampleDetResult sampleDetResult){
		super.update(sampleDetResult);
	}
}
