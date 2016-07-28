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

import com.cimmyt.model.bean.TempSampleID;

public class TempSampleIDDAO extends AbstractDAO<TempSampleID, Integer> {

	public TempSampleIDDAO() {
		super(TempSampleID.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void buildCriteria(DetachedCriteria criteria, TempSampleID filter) {
		// TODO Auto-generated method stub
		
	}
	
	public Integer searchSampleIDProjectInSt_TempSampleID(final Integer idproject, 
			final Integer gid, final Integer nplanta, final Integer locationid, 
			final Integer seasonid){
		TempSampleID tempSampleID;
		final String queryString = "from TempSampleID as sid "
				+ " where "
				+ " sid.gid = :GID "
				+ " and sid.nplant= :NPLANT"
				+ " and sid.locationid= :LOCATIONID"
				+ " and sid.seasonid= :SEASONID";

		tempSampleID = (TempSampleID) getHibernateTemplate().execute(
				new HibernateCallback() {

					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						TempSampleID sid= new TempSampleID();
						
						Query query = session.createQuery(queryString);
						
						query.setParameter("GID", gid);
						query.setParameter("NPLANT", nplanta);
						query.setParameter("LOCATIONID",locationid);
						query.setParameter("SEASONID",seasonid);

						if (query.uniqueResult() != null) {
							sid = (TempSampleID) query
									.uniqueResult();
						}
						return sid;
					}
				});
		
		if(tempSampleID.getSampleid()==null)
			return 0;
		else
			return tempSampleID.getSampleid();
		
	}
	
	
	public Integer GetLastNPlantFromGIDinTempSampleid(final Integer GID,
			final Integer locationid, final Integer seasonid){
		
		final String queryString = "select max(tsid.nplant) from TempSampleID as tsid "
				+ " where tsid.gid = :GID " +
						" and tsid.locationid = :LOCATIONID " +
						" and tsid.seasonid = :SEASONID";
				
		Integer result= (Integer) getHibernateTemplate().execute(
				new HibernateCallback() {

					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						
						Query query = session.createQuery(queryString);
						query.setParameter("GID", GID);
						query.setParameter("LOCATIONID", locationid);
						query.setParameter("SEASONID", seasonid);
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
			TempSampleID filter, Integer id) {
		// TODO Auto-generated method stub
		
	}
	
	
}


