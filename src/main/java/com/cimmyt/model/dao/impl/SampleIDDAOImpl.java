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
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.cimmyt.model.bean.MixtureDetail;
import com.cimmyt.model.bean.SampleID;
import com.cimmyt.model.bean.SampleMixture;
import com.cimmyt.model.dao.SampleIDDAO;
/**
 */
public class SampleIDDAOImpl extends AbstractDAO<SampleID, Integer> implements SampleIDDAO{

	public SampleIDDAOImpl() {
		super(SampleID.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void buildCriteria(DetachedCriteria criteria, SampleID filter) {
		// TODO Auto-generated method stub
		
	}
	
	public Integer searchSampleIDProjectInSt_SampleID(final Integer idproject, 
			final Integer gid, final Integer nplanta, final Integer locationid, 
			final Integer seasonid){
		SampleID sampleID;
		final String queryString = "from SampleID as sid "
				+ " where sid.projectid.projectid = :PROJECTID "
				+ " and sid.gid = :GID "
				+ " and sid.nplant= :NPLANT"
				+ " and sid.locationid.locationid= :LOCATIONID"
				+ " and sid.seasonid.seasonid= :SEASONID";

		sampleID = (SampleID) getHibernateTemplate().execute(
				new HibernateCallback() {

					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						SampleID sid= new SampleID();
						
						Query query = session.createQuery(queryString);
						query.setParameter("PROJECTID", idproject);
						query.setParameter("GID", gid);
						query.setParameter("NPLANT", nplanta);
						query.setParameter("LOCATIONID",locationid);
						query.setParameter("SEASONID",seasonid);
						
						if (query.uniqueResult() != null) {
							sid = (SampleID) query
									.uniqueResult();
						}
						return sid;
					}
				});
		
		if(sampleID.getSampleid()==null)
			return 0;
		else
			return sampleID.getSampleid();
		
	}

	public void deleteSampleID (SampleID persistentObject){
		super.delete(persistentObject);
	}
	public SampleID getSearchSampleIDProjectInStSampleID(final Integer idproject, 
			final Integer gid, final Integer nplanta, final Integer locationid, 
			final Integer seasonid){
		SampleID sampleID;
		final String queryString = "from SampleID as sid "
				+ " where sid.projectid.projectid = :PROJECTID "
				+ " and sid.gid = :GID "
				+ " and sid.nplant= :NPLANT"
				+ " and sid.locationid.locationid= :LOCATIONID"
				+ " and sid.seasonid.seasonid= :SEASONID";

		sampleID = (SampleID) getHibernateTemplate().execute(
				new HibernateCallback() {

					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						SampleID sid= new SampleID();
						
						Query query = session.createQuery(queryString);
						query.setParameter("PROJECTID", idproject);
						query.setParameter("GID", gid);
						query.setParameter("NPLANT", nplanta);
						query.setParameter("LOCATIONID",locationid);
						query.setParameter("SEASONID",seasonid);
						
						if (query.uniqueResult() != null) {
							sid = (SampleID) query
									.uniqueResult();
						}
						return sid;
					}
				});

			return sampleID;
	}

	public Integer GetLastSampleIDFromProject(final Integer projectID){
		final String queryString = "select max(sid.sampleid)from SampleID as sid "
				+ " where sid.projectid.projectid = :PROJECTID ";
		Integer result= (Integer) getHibernateTemplate().execute(
				new HibernateCallback() {

					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						
						Query query = session.createQuery(queryString);
						query.setParameter("PROJECTID",projectID);
						
						Integer resultado=(Integer)query.uniqueResult();
						
						if (resultado==null)
							return 0;
						else
							return resultado;
					}
				});
		
		return result;
	}
	
	public Integer GetLastNPlantFromGIDinSampleID(final Integer projectID, final Integer GID,
			final Integer locationid, final Integer seasonid){
		
		final String queryString = "select max(sid.nplant) from SampleID as sid "
				+ " where sid.gid = :GID and sid.projectid = :PROJECTID  " +
						" and sid.locationid = :LOCATIONID " +
						" and sid.seasonid = :SEASONID";
				
		Integer result= (Integer) getHibernateTemplate().execute(
				new HibernateCallback() {

					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						
						Query query = session.createQuery(queryString);
						query.setParameter("PROJECTID",projectID);
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
	
	
	
	@SuppressWarnings("unchecked")
	public boolean searchSampleIDforLocation(final Integer locationid 
			){
		// first define a queryString
		List<SampleID> samplesid;
		final String queryString = "from SampleID as sid "
				+ " where sid.locationid.locationid = :LOCATIONID";

		samplesid= (List<SampleID>)getHibernateTemplate().execute(
				new HibernateCallback() {

					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						List<SampleID> samples = null;
						Query query = session.createQuery(queryString);
						query.setParameter("LOCATIONID",locationid);
						samples = query.list();
						return samples;
					}
				});
		if (samplesid.size()==0)
			return false;
		else
			return true;
		
	}
	
	
	@SuppressWarnings("unchecked")
	public boolean searchSampleIDforSeason(final Integer seasonid 
			){
		// first define a queryString
		List<SampleID> samplesid;
		final String queryString = "from SampleID as sid "
				+ " where sid.seasonid.seasonid= :SEASONID";
		samplesid= (List<SampleID>)getHibernateTemplate().execute(
				new HibernateCallback() {
					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						List<SampleID> samples = null;
						Query query = session.createQuery(queryString);
						query.setParameter("SEASONID",seasonid);
						samples = query.list();
						return samples;
					}
				});
		if (samplesid.size()==0)
			return false;
		else
			return true;
		
	}

	@SuppressWarnings("unchecked")
	public List<SampleID> searchListSampleMixture (final Integer idproject, 
			final Integer gid, final Integer nplanta, final Integer locationid, 
			final Integer seasonid){
		List<SampleID> samplesid = null;

		DetachedCriteria criteria = DetachedCriteria
		.forClass(SampleID.class);
		criteria.add(Restrictions.eq("projectid", idproject));
		criteria.add(Restrictions.eq("gid", gid));
		criteria.add(Restrictions.eq("locationid", locationid));
		criteria.add(Restrictions.eq("seasonid", seasonid));
		criteria.add(Restrictions.eq("nplant", nplanta));
		
		samplesid = (List<SampleID>) getHibernateTemplate().findByCriteria(criteria);
				
		return samplesid;
	}

	@SuppressWarnings("unchecked")
	public List<SampleMixture> getListMixtureDetailBySampleMixture (final SampleID sample){
			List<SampleMixture> listMixtureDetail = null;
			final String strQuery = "from SampleMixture where id.sampleid = :SAMPLEID " ;
			listMixtureDetail = (List<SampleMixture>) getHibernateTemplate().execute(
				new HibernateCallback() {
					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						List<SampleMixture> samples = null;
						Query query = session.createQuery(strQuery);
						query.setParameter("SAMPLEID", sample.getSampleid());
						samples = query.list();
						return samples;
					}
				}	
		);
			if (listMixtureDetail!= null && !listMixtureDetail.isEmpty()){
				for (SampleMixture sampleM : listMixtureDetail){
					sampleM.setMixtureDetail(getListMixtureDetailBySampleMixtureID (sampleM));
				}
			}
		return listMixtureDetail;
	}

	private MixtureDetail getListMixtureDetailBySampleMixtureID (final SampleMixture sampleMixture){
			MixtureDetail listMixtureDetail = null;
			final String strQuery = "from MixtureDetail where mixtureDetailId = :DETAILID " ;
			listMixtureDetail = (MixtureDetail) getHibernateTemplate().execute(
				new HibernateCallback() {
					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						MixtureDetail samples = null;
						Query query = session.createQuery(strQuery);
						query.setParameter("DETAILID", sampleMixture.getId().getMixtureDetailId());
						samples = (MixtureDetail) query.list().get(0);
						return samples;
					}
				}	
		);
		return listMixtureDetail;
	}

	@Override
	protected void buildCriteria(DetachedCriteria criteria, SampleID filter,
			Integer id) {
		// TODO Auto-generated method stub
		
	}

	public void saveOrUpdateSamplesId(SampleID sampleid){
		super.update(sampleid);
	}
}
