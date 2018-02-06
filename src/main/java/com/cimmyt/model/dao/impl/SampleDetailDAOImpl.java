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
//$ Id: SampleDetailDAO.java, Jun 28, 2010 TMSANCHEZ $
package com.cimmyt.model.dao.impl;

import static com.cimmyt.utils.Constants.VALUE_SAMPLE;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.cimmyt.dnast.dto.DsSearchParam;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.cimmyt.bean.Operator;
import com.cimmyt.bean.Operator.DataType;
import com.cimmyt.bean.Operator.TypeCondition;
import com.cimmyt.bean.OperatorImp;
import com.cimmyt.constants.ShipmentStatus;
import com.cimmyt.model.bean.SampleDetResult;
import com.cimmyt.model.bean.SampleDetail;
import com.cimmyt.model.bean.ShipmentSet;
import com.cimmyt.model.bean.StudyTemplateParams;
import com.cimmyt.model.dao.SampleDetailDAO;
import com.cimmyt.utils.Constants;

/**
 * Data Access Object class for SampleDetail management
 * @version $Revision$, $Date: 2010-08-27 16:44:59 -0500 (Fri, 27 Aug 2010) $
 */
public class SampleDetailDAOImpl extends AbstractDAO<SampleDetail, Integer> implements SampleDetailDAO{

	public SampleDetailDAOImpl() {
		super(SampleDetail.class);
	}

	private Map<String, String> metadata;
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.cimmyt.cril.apps.sampletracking.core.persistence.dao.AbstractDAO#
	 * buildCriteria(org.hibernate.criterion.DetachedCriteria, java.lang.Object)
	 */
	@Override
	protected void buildCriteria(DetachedCriteria criteria, SampleDetail filter) {
		// TODO Auto-generated method stub
		if(filter.getLabstudyid()!=null){
			if(filter.getLabstudyid().getProject()!=null)
				criteria.add(Restrictions.eq("labstudyid.project", 
						filter.getLabstudyid().getProject()));
		}
		
		if (filter.getPlatename()!=null)
			criteria.add(Restrictions.eq("platename", filter.getPlatename()));
		
		if (filter.getPlateloc()!=null)
			criteria.add(Restrictions.eq("plateloc", filter.getPlateloc()));
		
		if(filter.getSamplegid()!=null){
			criteria.add(Restrictions.eq("samplegid", filter.getSamplegid()));
		}
		
		if(filter.getBreedergid()!=null){
			criteria.add(Restrictions.eq("breedergid", filter.getBreedergid()));
		}
		
		if(filter.getNval()!=null){
			criteria.add(Restrictions.eq("nval", filter.getNval()));
		}
		
		if(filter.getNplanta()!=null){
			criteria.add(Restrictions.eq("nplanta", filter.getNplanta()));
		}
	
		if(filter.getSpecie()!=null){
			criteria.add(Restrictions.eq("specie", filter.getSpecie()));
		}
		
		if(filter.getPriority()!=null){
			criteria.add(Restrictions.eq("priority", filter.getPriority()));
		}
		
		if(filter.getEntryNo()!=null){
			criteria.add(Restrictions.eq("entryNo", filter.getEntryNo()));
		}
		
		if(filter.getLocationid()!=null){
			criteria.add(Restrictions.eq("locationid", filter.getLocationid()));
		}
		
		if(filter.getSeasonid()!=null){
			criteria.add(Restrictions.eq("seasonid", filter.getSeasonid()));
		}
	}

	/**
	 * 
	 * @param labStudyId
	 * @param entryNo
	 * @return
	 */
	@Override
	public SampleDetail getSampleDetailByLabIdAndEntry(
			final Integer labStudyId, final Integer entryNo) {
		SampleDetail sampleDetail = null;

		final String queryString = "from SampleDetail as sd "
				+ " where sd.labstudyid.labstudyid = :LABSTUDYID "
				+ " and sd.entryNo = :ENTRYNO";

		sampleDetail = (SampleDetail) getHibernateTemplate().execute(
				new HibernateCallback() {
					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						SampleDetail sampleDetailResult = null;

						Query query = session.createQuery(queryString);
						query.setParameter("LABSTUDYID", labStudyId);
						query.setParameter("ENTRYNO", entryNo);
						sampleDetailResult = (SampleDetail) query
								.uniqueResult();

						return sampleDetailResult;
					}
				});

		return sampleDetail;
	}
	
	@Override
	public SampleDetail getSampleDetailByStudysampleid(
			final Integer studysampleid) {
		SampleDetail sampleDetail = null;

		final String queryString = "from SampleDetail as sd "
				+ " where sd.studysampleid = :STUDYSAMPLEID ";

		sampleDetail = (SampleDetail) getHibernateTemplate().execute(
				new HibernateCallback() {
					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						SampleDetail sampleDetailResult = null;
						Query query = session.createQuery(queryString);
						query.setParameter("STUDYSAMPLEID", studysampleid);
						sampleDetailResult = (SampleDetail) query
								.uniqueResult();
						return sampleDetailResult;
					}
				});

		return sampleDetail;
	}
	
	@Override
	public SampleDetail getSampleDetailWithResults(final Integer studySampleID){
		SampleDetail sampleDetail = null;
		final String queryString = "from SampleDetail as sd "
				+ " where sd.studysampleid = :STUDYSAMPLEID ";
		sampleDetail = (SampleDetail) getHibernateTemplate().execute(
				new HibernateCallback() {
					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						SampleDetail sampleDetailResult = null;

						Query query = session.createQuery(queryString);
						query.setParameter("STUDYSAMPLEID", studySampleID);
						sampleDetailResult = (SampleDetail) query
								.uniqueResult();
						for (StudyTemplateParams params: sampleDetailResult.getLabstudyid()
								.getStudytemplateid().getImsStudyTemplateParamsCollection() ) {
							params.getDescription();
						}
						
						for (SampleDetResult detResult : sampleDetailResult
									.getImsSampleDetResultCollection()) {
							detResult.getResult();
							detResult.getTemplateparamid();
						}
						
						return sampleDetailResult;
					}
				});

		return sampleDetail;
	}
	

	/**
	 * 
	 * @param locationId
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<SampleDetail> getSamplesByLocation(final Integer locationId,final Integer firstResult, final Integer maxResults
			,final String sortColumn, boolean ascending) {
		List<SampleDetail> sampleDetails = null;

			
		String tmpQueryString = "from SampleDetail as sd "
				+ " where sd.storageLocation.imslocid = :LOCATIONID";

		if(!(sortColumn == null || sortColumn.isEmpty())){
			tmpQueryString = tmpQueryString.concat(" order by ").concat(sortColumn).concat(ascending?" asc":" desc");
		}
		final String queryString = tmpQueryString;
		
		sampleDetails = (List<SampleDetail>) getHibernateTemplate().execute(
				new HibernateCallback() {
					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						List<SampleDetail> samplesDetailResult = null;
						Query query = session.createQuery(queryString);
						query.setParameter("LOCATIONID", locationId)
						.setFirstResult(firstResult)
						.setMaxResults(maxResults);
						samplesDetailResult = query.list();
						return samplesDetailResult;
					}
				});

		return sampleDetails;
	}

	@Override
	public Long getCountSamplesByLocation(final Integer locationId) {
		Long sampleCount = null;

		final String queryString = "select count(studysampleid) from SampleDetail as sd "
				+ " where sd.storageLocation.imslocid = :LOCATIONID ";

		sampleCount = getHibernateTemplate().execute(
				new HibernateCallback<Long>() {
					@Override
					public Long doInHibernate(Session session)
							throws HibernateException, SQLException {
						Long samplesDetailResult = null;
						Query query = session.createQuery(queryString);
						query.setParameter("LOCATIONID", locationId);
						samplesDetailResult = (Long) query.uniqueResult();
						return samplesDetailResult;
					}
				});

		return sampleCount;
	}
	/**
	 * Retrieves all the samples placed originally in a plate
	 * 
	 * @param plateName
	 *            Name of the plate
	 * @return List of samples in the plate, empty list if there aren't samples
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<SampleDetail> getSamplesByPlate(final String plateName) {
		List<SampleDetail> sampleDetails = null;

		final String queryString = "from SampleDetail as sd "
				+ " where sd.platename = :PLATENAME ";

		sampleDetails = (List<SampleDetail>) getHibernateTemplate().execute(
				new HibernateCallback() {
					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						List<SampleDetail> samplesDetailResult = null;
						Query query = session.createQuery(queryString);
						query.setParameter("PLATENAME", plateName);
						samplesDetailResult = query.list();
						return samplesDetailResult;
					}
				});

		return sampleDetails;
	}

	/**
	 * Gets a list of samples from a Study by it ID, excluding samples selected
	 * for SENT to external company and excluding samples in excluedSamples
	 * parameter
	 * 
	 * @param labStudyId
	 *            Study ID
	 * @param excludedSamples
	 *            list of samples to exclude
	 * @return List of samples with matching ID, empty list if not matching ID
	 *         or all samples are in excluedSamples
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<SampleDetail> getSamplesByStudy(final Integer labStudyId,
			final List<Integer> excludedSamples) {
		List<SampleDetail> sampleDetails = null;
		DetachedCriteria criteria = DetachedCriteria
				.forClass(SampleDetail.class);
		criteria.add(Restrictions.eq("labstudyid.labstudyid", labStudyId));
		criteria.add(Restrictions.ne("selforsend", ShipmentStatus.FOR_SEND.getId()));
		if (excludedSamples.size() > 0) {
			criteria.add(Restrictions.not(Restrictions.in("studysampleid",
					excludedSamples)));
		}
		sampleDetails = (List<SampleDetail>) getHibernateTemplate().findByCriteria(criteria);
		return sampleDetails;
	}

	@SuppressWarnings("unchecked")
	public List<SampleDetail> getSamplesByStudyUsedInStorageLocation(final Integer labStudyId,
			final List<Integer> excludedSamples, int firstResult, int maxResults) {
		List<SampleDetail> sampleDetails = null;
		DetachedCriteria criteria = DetachedCriteria
				.forClass(SampleDetail.class);
		criteria.add(Restrictions.eq("labstudyid.labstudyid", labStudyId));
		criteria.add(Restrictions.eq("selforsend", ShipmentStatus.NO_SELECTED.getId()));
		if (excludedSamples.size() > 0) {
			criteria.add(Restrictions.not(Restrictions.in("studysampleid",
					excludedSamples)));
		}

		sampleDetails = (List<SampleDetail>) getHibernateTemplate().findByCriteria(criteria, firstResult, maxResults);
		return sampleDetails;
	}
	

	public Integer getSamplesByStudyUsedInStorageLocationTotal(
			Integer labStudyId, List<Integer> excludedSamples) {
		Integer numberSamples = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(SampleDetail.class);
		criteria.add(Restrictions.eq("labstudyid.labstudyid", labStudyId));
		criteria.add(Restrictions.eq("selforsend", ShipmentStatus.NO_SELECTED.getId()));
		if (excludedSamples.size() > 0) {
			criteria.add(Restrictions.not(Restrictions.in("studysampleid",
					excludedSamples)));
		}
		criteria.setProjection(Projections.rowCount());
		numberSamples = (Integer)getHibernateTemplate().findByCriteria(criteria).get(0);
		return numberSamples;
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<SampleDetail> getSamplesDetailsList(final SampleDetail filtersample) {
		List<SampleDetail> sampleDetails = null;
		boolean tienecriterio=false;
		String queryS = "select sd "+
				" from SampleDetail as sd inner join sd.labstudyid as sdlabstudy " +
				" inner join sdlabstudy.project as sdlabstudyproject" +
				" inner join sdlabstudy.investigatorid as sdlabstudyinvestigator  " +
				" inner join sdlabstudy.tissue as sdlabstudytissue ";
				
		//Construccion del query
		
		//checar si tiene algun criterio
		tienecriterio=Verifica_si_tiene_algun_criterio(filtersample);
		
		if(tienecriterio){
			queryS=queryS+ " where ";//sd.platename =:PLATENAME ";	
		
		if(filtersample.getLabstudyid()!=null){
			if (!filtersample.getLabstudyid().getTitle().equals(""))
				queryS=queryS+" sd.labstudyid.title=:TITLE and";
			if(filtersample.getLabstudyid().getProject()!=null)
				queryS=queryS+" sdlabstudyproject=:PROJECTID and";
			if(filtersample.getLabstudyid().getInvestigatorid()!=null)
				queryS=queryS+" sdlabstudyinvestigator=:INVESTIGATORID and";
			if(filtersample.getLabstudyid().getTissue()!=null)
				queryS=queryS+" sdlabstudytissue=:TISSUEID and";
		}
		
		if (filtersample.getPlatename()!=null)
			queryS=queryS+" sd.platename=:PLATENAME and";
	
		if (filtersample.getPlateloc()!=null)
			queryS=queryS+" sd.plateloc=:PLATELOC and";

		if(filtersample.getSamplegid()!=null)
			queryS=queryS+" sd.samplegid=:SAMPLEID and";

		if(filtersample.getBreedergid()!=null)
			queryS=queryS+" sd.breedergid=:BREEDERGID and";
		
		if(filtersample.getNval()!=null)
			queryS=queryS+" sd.nval=:NVAL and";
	
		if(filtersample.getNplanta()!=null)
			queryS=queryS+" sd.nplanta=:NPLANTA and";

		if(filtersample.getSpecie()!=null)
			queryS=queryS+" sd.specie=:SPECIE and";
	
		if(filtersample.getPriority()!=null)
			queryS=queryS+" sd.priority=:PRIORITY and";

		if(filtersample.getEntryNo()!=null)
			queryS=queryS+" sd.entryNo=:ENTRYNO and";

		if(filtersample.getLocationid()!=null)
			queryS=queryS+" sd.locationid=:LOCATIONID and";

		if(filtersample.getSeasonid()!=null)
			queryS=queryS+" sd.seasonid=:SEASONID and";
		
		if(filtersample.getSelforsend()!=null)
			queryS=queryS+" sd.selforsend=:SELFORSEND and";
		
		if(filtersample.getStorageLocation()!=null)
			queryS=queryS+" sd.storageLocation=:STORAGELOCATION and";
		
		if(filtersample.getControltype()!=null)
			queryS=queryS+" sd.controltype=:CONTROLTYPE and";
		
		queryS=queryS.substring(0,queryS.length()-3);
		}
		
		final String execquery;
		execquery=queryS;

		sampleDetails = (List<SampleDetail>) getHibernateTemplate().execute(
				new HibernateCallback() {
					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						List<SampleDetail> samplesDetailResult = null;

						Query query = session.createQuery(execquery);

						// COLOCAR PARAMETROS
							if (filtersample.getLabstudyid() != null) {
								if (!filtersample.getLabstudyid().getTitle()
										.equals(""))
									query.setParameter("TITLE", filtersample
											.getLabstudyid().getTitle());
								if (filtersample.getLabstudyid().getProject() != null)
									query.setParameter("PROJECTID",
											filtersample.getLabstudyid()
													.getProject());
								if (filtersample.getLabstudyid()
										.getInvestigatorid() != null)
									query.setParameter("INVESTIGATORID",
											filtersample.getLabstudyid()
													.getInvestigatorid());
								if (filtersample.getLabstudyid().getTissue() != null)
									query.setParameter("TISSUEID", filtersample
											.getLabstudyid().getTissue());
							}

							if (filtersample.getPlatename() != null)
								query.setParameter("PLATENAME", filtersample
										.getPlatename());

							if (filtersample.getPlateloc() != null)
								query.setParameter("PLATELOC", filtersample
										.getPlateloc());

							if (filtersample.getSamplegid() != null)
								query.setParameter("SAMPLEID", filtersample
										.getSamplegid());

							if (filtersample.getBreedergid() != null)
								query.setParameter("BREEDERGID", filtersample
										.getBreedergid());

							if (filtersample.getNval() != null)
								query.setParameter("NVAL", filtersample
										.getNval());

							if (filtersample.getNplanta() != null)
								query.setParameter("NPLANTA", filtersample
										.getNplanta());

							if (filtersample.getSpecie() != null)
								query.setParameter("SPECIE", filtersample
										.getSpecie());

							if (filtersample.getPriority() != null)
								query.setParameter("PRIORITY", filtersample
										.getPriority());

							if (filtersample.getEntryNo() != null)
								query.setParameter("ENTRYNO", filtersample
										.getEntryNo());

							if (filtersample.getLocationid() != null)
								query.setParameter("LOCATIONID", filtersample
										.getLocationid());

							if (filtersample.getSeasonid() != null)
								query.setParameter("SEASONID", filtersample
										.getSeasonid());

							if (filtersample.getSelforsend() != null)
								query.setParameter("SELFORSEND", filtersample
										.getSelforsend());

							if (filtersample.getStorageLocation() != null)
								query.setParameter("STORAGELOCATION",
										filtersample.getStorageLocation());

							if (filtersample.getControltype() != null)
								query.setParameter("CONTROLTYPE", filtersample
										.getControltype());
					

						samplesDetailResult = query.list();

						return samplesDetailResult;
					}
				});
		return sampleDetails;
	}

	
	private boolean Verifica_si_tiene_algun_criterio(SampleDetail sample){
		boolean res;
		res=false;
		
		if(sample.getLabstudyid()!=null||
				sample.getPlatename()!=null||
				sample.getPlateloc()!=null||
				sample.getSamplegid()!=null||
				sample.getBreedergid()!=null||
				sample.getNval()!=null||
				sample.getNplanta()!=null||
				sample.getSpecie()!=null||
				sample.getPriority()!=null||
				sample.getEntryNo()!=null||
				sample.getControltype()!=null||
				sample.getLocationid()!=null||
				sample.getSeasonid()!=null||
				sample.getStorageLocation()!=null||
				sample.getSelforsend()!=null
				)
			res=true;
		
		return res;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<String> getPlatesNamesFromLabStudy(final Integer labstudyID){
		final String queryString = "select distinct(sd.platename) " +
				" from SampleDetail as sd "
				+ " where sd.labstudyid.labstudyid = :LABSTUDYID order by sd.platename";
				
		List<String> result= (List<String>) getHibernateTemplate().execute(
				new HibernateCallback() {

					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						
						Query query = session.createQuery(queryString);
						query.setParameter("LABSTUDYID",labstudyID);
						
						List<String> datos = query.list();
						
	
						return datos;
					}
				});	
		
		return result;
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public List<String> getAllPlatesNames(){
		final String queryString = "select distinct(sd.platename) " +
				" from SampleDetail as sd order by sd.platename ";
				
		List<String> result= (List<String>) getHibernateTemplate().execute(
				new HibernateCallback() {

					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						
						Query query = session.createQuery(queryString);
						List<String> datos = query.list();
						
	
						return datos;
					}
				});	
		
		return result;
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public List<String> getDistinctsStringsFromSampleDetail(String field){
		final String queryString = "select distinct(sd."+field+") " +
				" from SampleDetail as sd order by sd."+field ;
				
		List<String> result= (List<String>) getHibernateTemplate().execute(
				new HibernateCallback() {

					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						
						Query query = session.createQuery(queryString);
						List<String> datos = query.list();
						
	
						return datos;
					}
				});	
		
		return result;
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Integer> getDistinctsIntegersFromSampleDetail(String field){
		final String queryString = "select distinct(sd."+field+") " +
				" from SampleDetail as sd order by sd."+field ;
				
		List<Integer> result= (List<Integer>) getHibernateTemplate().execute(
				new HibernateCallback() {

					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						
						Query query = session.createQuery(queryString);
						List<Integer> datos = query.list();
						
	
						return datos;
					}
				});	
		
		return result;
	}
	
	

	

	@Override
	@SuppressWarnings("unchecked")
	public List<SampleDetail> getSampleDetailWithCondition (final String queryString){
		List<SampleDetail> sampleDetails = null;
	sampleDetails = (List<SampleDetail>) getHibernateTemplate().execute(
			new HibernateCallback() {
				@Override
				public Object doInHibernate(Session session)
						throws HibernateException, SQLException {
					List<SampleDetail> samplesDetailResult = null;
					Query query = session.createQuery(queryString);

					samplesDetailResult = query.list();

					return samplesDetailResult;
				}
			});
		return sampleDetails;
	}

	@Override
	public void update(SampleDetail sampleDetail){
		super.update(sampleDetail);
	}
	@Override
	public void delete (SampleDetail sampleDetail){
		super.delete(sampleDetail);
	}

	@Override
	protected void buildCriteria(DetachedCriteria criteria,
			SampleDetail filter, Integer id) {
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<SampleDetail> getCustomQuery(final List<DsSearchParam> params, final int firstResult, final int maxResults) {
		final List<DsSearchParam> paramsTmp = new ArrayList<DsSearchParam>(params); //avoid changing original List
		
		List<SampleDetail> resultList = null; 
		resultList = (List<SampleDetail>) this.getHibernateTemplate().execute(
				new HibernateCallback<List<SampleDetail>>() {
					@Override
					public List<SampleDetail> doInHibernate(Session session) throws HibernateException, SQLException {
						DetachedCriteria criteria = DetachedCriteria.forClass(SampleDetail.class,VALUE_SAMPLE);
						addCriteria(criteria, paramsTmp);
						return (List<SampleDetail>) getHibernateTemplate().findByCriteria(criteria, firstResult, maxResults);
					}
				});
		return resultList;
	}

	@Override
	public Integer getTotalRowsCustomQuery(final List<DsSearchParam> params) {
		final List<DsSearchParam> paramsTmp = new ArrayList<DsSearchParam>(params); //avoid changing original List
		
		Integer resultList = 0; 
		resultList = (Integer)this.getHibernateTemplate().execute(
				new HibernateCallback<Integer>() {
					@Override
					public Integer doInHibernate(Session session) throws HibernateException, SQLException {
						DetachedCriteria criteria = DetachedCriteria.forClass(SampleDetail.class,VALUE_SAMPLE);
						addCriteria(criteria, paramsTmp);
						criteria.setProjection(Projections.rowCount());
						return (Integer)getHibernateTemplate().findByCriteria(criteria).get(0);
					}
				});
		return resultList;
	}

	/**
	 * add parameters for project, study and sample
	 * */
	private void addCriteria(DetachedCriteria criteria, List<DsSearchParam> params){
		boolean hasAND = false;
		boolean hasOR = false;
		
		List<DsSearchParam> paramList = new ArrayList<DsSearchParam>(params);
	
		criteria.createAlias("labstudyid", Constants.VALUE_STUDY)
		.createAlias("study.project", Constants.VALUE_PROJECT)
		.createAlias("locationid", "location",CriteriaSpecification.LEFT_JOIN)
		.createAlias("seasonid", "season",CriteriaSpecification.LEFT_JOIN)
		.createAlias("study.investigatorid", "investigator",CriteriaSpecification.LEFT_JOIN);

		//separates 'OR' from other operators, to use in disjunction
		List<DsSearchParam> orParams = new ArrayList<DsSearchParam>();
		for(DsSearchParam p : paramList){

			if(p.getOperator().equals(TypeCondition.OR)){
				orParams.add(p);
			}
		}
		paramList.removeAll(orParams);
		
		String qualifiedParam = null;
		Conjunction conjunction = Restrictions.conjunction();
		
		for(DsSearchParam param : paramList){
			hasAND = true;
	
			qualifiedParam = param.getQualifier();
			DataType dataType = OperatorImp.getType(param.getElement(), param.getQualifier());
			Operator condition = OperatorImp.valueOf(param.getCondition());
			addDynamicCriterion(conjunction, condition, dataType, qualifiedParam, param.getValue());

		}

		Disjunction disjunction = Restrictions.disjunction();
		for(DsSearchParam param : orParams){
			hasOR = true;

			qualifiedParam = param.getQualifier();
			DataType dataType = OperatorImp.getType(param.getElement(), param.getQualifier());
			Operator condition = OperatorImp.valueOf(param.getCondition());

			addDynamicCriterion(disjunction, condition, dataType, qualifiedParam, param.getValue());

		}
		
		if(hasAND){
			if(hasOR){
				criteria.add(Restrictions.or(conjunction, disjunction));
			}else{
				criteria.add(conjunction);
			}
		}else if(hasOR){
			criteria.add(disjunction);
		}		
	}

	public void updateListStatus(final List<Integer> sampleDetailIds, final char status) {
		final String queryString = "update SampleDetail sd set selforsend  = :status where sd.studysampleid in (:ids)";
		
		if(sampleDetailIds == null) return;
		
		sampleDetailIds.add(-1);
		
				
		getHibernateTemplate().execute(
				new HibernateCallback<Object>() {

					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						
						Query query = session.createQuery(queryString)
											.setCharacter("status", status)
											.setParameterList("ids", sampleDetailIds);
						
						return query.executeUpdate();
					}
				});	
		
	}

	public List<SampleDetail> getSamplesByShipmentSet(final ShipmentSet shipmentSet) {
		final String queryString = "select sd from SampleDetail as sd "
				+ "inner join sd.imsShipmentdetailCollection as shd "
				+ "where shd.stShipmentSet.idShipmentSet = :id";
		List<SampleDetail> sampleDetails = null;
		sampleDetails = (List<SampleDetail>) getHibernateTemplate().execute(
			new HibernateCallback() {
				@Override
				public Object doInHibernate(Session session)
						throws HibernateException, SQLException {
					List<SampleDetail> samplesDetailResult = null;
					Query query = session.createQuery(queryString);
					query.setInteger("id", shipmentSet.getIdShipmentSet());

					samplesDetailResult = query.list();

					return samplesDetailResult;
				}
			});
		return sampleDetails;
		
	}


	public List<String> getPlatesNotInShipmentSet(final Integer idLabStudy, final List<String> excludingPlates) {

		List<String> listPlate = (List<String>) getHibernateTemplate().execute(
				new HibernateCallback<List<String>>(){

					public List<String> doInHibernate(Session session)
							throws HibernateException, SQLException {
						Criteria criteria = session.createCriteria(SampleDetail.class);
						criteria.add(Restrictions.eq("labstudyid.labstudyid", idLabStudy));
						if(excludingPlates!=null && !excludingPlates.isEmpty())
							criteria.add(Restrictions.not(Restrictions.in("platename", excludingPlates)) );
						
						criteria.add(Restrictions.eq("selforsend", "N"));
						criteria.setProjection(Projections.distinct(Projections.property("platename")));
						criteria.addOrder(Order.asc("platename"));

						@SuppressWarnings("unchecked")
						List<String> results = criteria.list();
						return results;
					}

				});
		return listPlate;
	}

	public List<String> getPlatesInShipmentSet(final ShipmentSet shipSet) {
		final String queryString = "select distinct sd.platename from SampleDetail as sd "
				+ "inner join sd.imsShipmentdetailCollection as shd "
				+ "where shd.stShipmentSet.idShipmentSet = :id";
		List<String> sampleDetails = null;
		sampleDetails = (List<String>) getHibernateTemplate().execute(
			new HibernateCallback<List<String>>() {

				public List<String> doInHibernate(Session session)
						throws HibernateException, SQLException {

					Query query = session.createQuery(queryString);
					query.setInteger("id", shipSet.getIdShipmentSet());

					@SuppressWarnings("unchecked")
					List<String> samplesDetailResult = query.list();

					return samplesDetailResult;
				}
			});
		return sampleDetails;
	}
}
