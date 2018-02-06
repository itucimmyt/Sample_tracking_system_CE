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

import static com.cimmyt.utils.Constants.VALUE_PROJECT;
import static com.cimmyt.utils.Constants.VALUE_STUDY;
import static com.cimmyt.utils.Constants.VALUE_SAMPLE;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
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
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.cimmyt.bean.Operator;
import com.cimmyt.bean.OperatorImp;
import com.cimmyt.bean.Operator.DataType;
import com.cimmyt.bean.Operator.TypeCondition;
import com.cimmyt.model.bean.LabStudy;
import com.cimmyt.model.bean.Project;
import com.cimmyt.model.bean.SampleDetResult;
import com.cimmyt.model.bean.SampleDetail;
import com.cimmyt.model.bean.StudyTemplate;
import com.cimmyt.model.bean.StudyTemplateParams;
import com.cimmyt.model.bean.Tissue;
import com.cimmyt.model.dao.LabStudyDAO;
import com.cimmyt.utils.ConstantsDNA;
import com.cimmyt.utils.StrUtils;
import com.cimmyt.zk.query.QueryViewDefinition;

/**
 * Data Access Object class for LabStudy management
 * @version $Revision$, $Date: 2010-09-09 15:34:47 -0500 (Thu, 09 Sep 2010) $
 */
public class LabStudyDAOImpl extends AbstractDAO<LabStudy, Integer> implements LabStudyDAO {

	public LabStudyDAOImpl() {
		super(LabStudy.class);
	}
	private Map<String, String> metadata;
	private Integer idstRol;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.cimmyt.cril.apps.sampletracking.core.persistence.dao.AbstractDAO#
	 * buildCriteria(org.hibernate.criterion.DetachedCriteria, java.lang.Object)
	 */
	@Override
	protected void buildCriteria(DetachedCriteria criteria, LabStudy filter) {
		if (filter.getLabstudyid() != null) {
			criteria.add(Restrictions.eq("labstudyid", filter.getLabstudyid()));
		}
		
		if(filter.getInvestigatorid()!=null){
			criteria.add(Restrictions.eq("investigatorid", filter.getInvestigatorid()));
		}
		
		if (StrUtils.notEmpty(filter.getTitle())) {
			criteria.add(Restrictions.like("title", "%"+filter.getTitle()+"%"));
		}
	}

	@Override
	protected void buildCriteria(DetachedCriteria criteria, LabStudy filter, Integer id) {
		criteria.createAlias("studytemplateid", "tmplate",CriteriaSpecification.LEFT_JOIN)
		.createAlias("status", "st",CriteriaSpecification.LEFT_JOIN)
		.createAlias("project", "proj",CriteriaSpecification.LEFT_JOIN)
		.createAlias("investigatorid", "invest",CriteriaSpecification.LEFT_JOIN);
		
		
		if (filter != null && filter.getLabstudyid() != null) {
			criteria.add(Restrictions.eq("labstudyid", filter.getLabstudyid()));
		}
		
		if (idstRol != null){
			switch (idstRol){
			case ConstantsDNA.ROLE_RESEARCHER:
			case ConstantsDNA.ROLE_RESEARCHER_ASSISTENT:
			case ConstantsDNA.ROLE_ASSISTENT:
				if(filter != null && filter.getInvestigatorid()!=null){
					criteria.add(Restrictions.eq("investigatorid.investigatorid", filter.getInvestigatorid()));
				}
				if (id != null){
					if (id.intValue() > 0 )
					criteria.add(Restrictions.eq("investigatorid.investigatorid", id));
				}
				break;
			}
		}else {
			if(filter != null && filter.getInvestigatorid()!=null){
				criteria.add(Restrictions.eq("investigatorid.investigatorid", filter.getInvestigatorid()));
			}
		}
		
		if (filter != null && StrUtils.notEmpty(filter.getTitle())) {
			criteria.add(Restrictions.like("title", "%"+filter.getTitle()+"%"));
		}
		
		if (filter != null && StrUtils.notEmpty(filter.getPrefix())) {
			criteria.add(Restrictions.like("prefix", filter.getPrefix(),MatchMode.ANYWHERE));
		}

		criteria.addOrder(Order.desc("labstudyid"));
	}

	@Override
	public LabStudy readStudyOnlySampleDetail(final Integer id) {
		@SuppressWarnings({ "unchecked", "rawtypes" })
		LabStudy result = (LabStudy) getHibernateTemplate().execute(
				new HibernateCallback() {
					LabStudy result = null;

					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						result = (LabStudy) session.get(LabStudy.class,
								id);
						result.getStudytemplateid().getTemplatename();
						result.getProject().getProjectname();
						result.getInvestigatorid().getInvest_name();
						result.getOrganismid().getOrganismname();
						result.getTissue().getTissueName();
						return result;
					}
				});
		result.setImsSampleDetailCollection(getNamePlateByStudyId(id));
		return result;
	}

	@SuppressWarnings("unchecked")
	private Collection<SampleDetail> getNamePlateByStudyId(final Integer id){
		@SuppressWarnings("rawtypes")
		Collection<SampleDetail> listPlate = (Collection<SampleDetail>)getHibernateTemplate().execute(
				new HibernateCallback(){

					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Criteria criteria = session.createCriteria(SampleDetail.class);
						criteria.add(Restrictions.eq("labstudyid.labstudyid", id));
						//criteria.add(Restrictions);
						//criteria.setProjection(Projections.distinct(Projections.property("platename")));
						criteria.addOrder(Order.asc("platename"));

						List results = criteria.list();
						if (results != null && !results.isEmpty()) {
							Iterator iterator = results.iterator(); 
							while (iterator.hasNext()){
								SampleDetail detail = (SampleDetail)iterator.next();
								detail.getLocationid();
								detail.getSeasonid();
							}
							Collection<SampleDetail> listPlate = new ArrayList<SampleDetail>(results); 
							return listPlate;
						}
						return null;
					}

				});
		return listPlate;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public LabStudy readStudyWithResults(final Integer id) {
		@SuppressWarnings("unchecked")
		LabStudy result = (LabStudy) getHibernateTemplate().execute(
				new HibernateCallback() {
					LabStudy result = null;

					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						result = (LabStudy) session.get(LabStudy.class,
								id);
						result.getStudytemplateid().getTemplatename();
						
						result.getProject().getProjectname();
						result.getInvestigatorid().getInvest_name();
						result.getOrganismid().getOrganismname();
						result.getTissue().getTissueName();
					
						
						for (StudyTemplateParams params: result.getStudytemplateid().getImsStudyTemplateParamsCollection() ) {
							params.getDescription();
						}
						
						for (SampleDetail detail : result
								.getSampleDetailCollection()) {
							detail.getEntryNo();
							for (SampleDetResult detResult : detail
									.getImsSampleDetResultCollection()) {
								detResult.getResult();
								detResult.getTemplateparamid();
							}
						}
						return result;
					}
				});
		return result;
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public boolean ThisInvestigatorHasLabStudyRegistred(final Integer investigatorId) {
		boolean tienestudy=false;
		List<LabStudy> labstudies = null;

		final String queryString = "from LabStudy as ls "
				+ " where ls.investigatorid.investigatorid = :INVESTIGATORID ";

		labstudies = (List<LabStudy>) getHibernateTemplate().execute(
				new HibernateCallback() {
					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						List<LabStudy> listlab = null;
						Query query = session.createQuery(queryString);
						query.setParameter("INVESTIGATORID", investigatorId);
						listlab = query.list();
						return listlab;
					}
				});

		if (labstudies.size()!=0)
			tienestudy=true;
		return tienestudy;
	}
	
	
	
	@Override
	@SuppressWarnings("unchecked")
	public boolean ThisProjectHasLabStudyRegistred(final Project project) {
		boolean tienestudy=false;
		List<LabStudy> labstudies = null;

		final String queryString = "from LabStudy as ls "
				+ " where ls.project.projectid = :PROJECTID ";

		labstudies = (List<LabStudy>) getHibernateTemplate().execute(
				new HibernateCallback() {
					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						List<LabStudy> listlab = null;

						Query query = session.createQuery(queryString);

						query.setParameter("PROJECTID", project.getProjectid());

						listlab = query.list();

						return listlab;
					}
				});

		if (labstudies.size()!=0)
			tienestudy=true;
		return tienestudy;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public boolean ThisTissueHasLabStudyRegistred(final Tissue tissue) {
		boolean tienestudy=false;
		List<LabStudy> labstudies = null;

		final String queryString = "from LabStudy as ls "
				+ " where ls.tissue.tissueid = :TISSUEID ";

		labstudies = (List<LabStudy>) getHibernateTemplate().execute(
				new HibernateCallback() {
					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						List<LabStudy> listlab = null;

						Query query = session.createQuery(queryString);

						query.setParameter("TISSUEID", tissue.getIdtissue());

						listlab = query.list();

						return listlab;
					}
				});

		if (labstudies.size()!=0)
			tienestudy=true;
		return tienestudy;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public boolean ThisTemplateHasLabStudyRegistred(final StudyTemplate template) {
		boolean tienestudy=false;
		List<LabStudy> labstudies = null;

		final String queryString = "from LabStudy as ls "
				+ " where ls.studytemplateid.studytemplateid= :TEMPLATEID ";

		labstudies = (List<LabStudy>) getHibernateTemplate().execute(
				new HibernateCallback() {
					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						List<LabStudy> listlab = null;
						Query query = session.createQuery(queryString);
						query.setParameter("TEMPLATEID", template.getStudytemplateid());
						listlab = query.list();
						return listlab;
					}
				});

		if (labstudies.size()!=0)
			tienestudy=true;
		return tienestudy;
	}
	/**
	 * Get list labStudy by filter
	 */
	@Override
	public List<LabStudy> getLabStudys(LabStudy filter){
		return super.getListByFilter(filter);
	}

	@Override
	public int createStudy(LabStudy newInstance, boolean edit){
		if (edit){
			super.update(newInstance);
			return newInstance.getLabstudyid();
		}
		else{ 
			super.create(newInstance);
			return newInstance.getLabstudyid();
		}
	}

	@Override
	public List<LabStudy> getLabStudysByIdResearch(LabStudy filter, Integer idResearch){
		return super.getListByFilter(filter, idResearch);
	}

	@Override
	public List<LabStudy> getLabStudysByIdResearch(LabStudy filter, Integer idResearch, int firstResult, int maxResults, String sortColumn, boolean ascending, Integer idstRol){
		this.idstRol = idstRol;
		return super.getListByFilter(filter, idResearch, firstResult, maxResults, sortColumn, ascending);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<LabStudy> getCustomQuery(final List<DsSearchParam> params, final int firstResult, final int maxResults) {
		final List<DsSearchParam> paramsTmp = new ArrayList<DsSearchParam>(params); //avoid changing original List

		final List<Integer> studyIdList = getListWithSampleFilters(paramsTmp);

		List<LabStudy> resultList = null; 
		resultList = this.getHibernateTemplate().execute(
				new HibernateCallback<List<LabStudy>>() {
					@Override
					public List<LabStudy> doInHibernate(Session session) throws HibernateException, SQLException {
						DetachedCriteria criteria = DetachedCriteria.forClass(LabStudy.class,VALUE_STUDY);
						addCriteria(criteria, paramsTmp, false);
						addCurrentSampleFilters(criteria, studyIdList);
						return (List<LabStudy>) getHibernateTemplate().findByCriteria(criteria, firstResult, maxResults);
					}
				});
		return resultList;
	}

	@Override
	public Integer getTotalRowsCustomQuery(final List<DsSearchParam> params) {
		final List<DsSearchParam> paramsTmp = new ArrayList<DsSearchParam>(params); //avoid changing original List

		final List<Integer> studyIdList = getListWithSampleFilters(paramsTmp);

		Integer resultList = 0; 
		resultList = this.getHibernateTemplate().execute(
				new HibernateCallback<Integer>() {
					@Override
					public Integer doInHibernate(Session session) throws HibernateException, SQLException {
						DetachedCriteria criteria = DetachedCriteria.forClass(LabStudy.class,VALUE_STUDY);
						addCriteria(criteria, paramsTmp, false);
						addCurrentSampleFilters(criteria, studyIdList);
						criteria.setProjection(Projections.rowCount());
						return (Integer)getHibernateTemplate().findByCriteria(criteria).get(0);
					}
				});
		return resultList;
	}

	/*add parameters for project, and study*/
	private void addCriteria(DetachedCriteria criteria, List<DsSearchParam> params, boolean useSampleDetailFilters){
		boolean hasAND = false;
		boolean hasOR = false;
		
		List<DsSearchParam> paramList = new ArrayList<DsSearchParam>(params);

		criteria.createAlias("project", VALUE_PROJECT)
		.createAlias("investigatorid", "investigator",CriteriaSpecification.LEFT_JOIN)
		.createAlias("season", "season",CriteriaSpecification.LEFT_JOIN)
		.createAlias("location", "location",CriteriaSpecification.LEFT_JOIN);
		
		if(useSampleDetailFilters) criteria.createAlias("sampleDetailCollection", VALUE_SAMPLE,CriteriaSpecification.INNER_JOIN);

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

	
	//contains ids of studies that meets sample filters.
	
	private List<Integer> getListWithSampleFilters(final List<DsSearchParam> params){

		final List<Integer> studyIdList = this.getHibernateTemplate().execute(
				new HibernateCallback<List<Integer>>() {
					@SuppressWarnings("unchecked")
					public List<Integer> doInHibernate(Session session) throws HibernateException, SQLException {
						List<DsSearchParam> sampleParams = new ArrayList<DsSearchParam>();
						for(DsSearchParam p : params){
							if(p.getElement().equals(VALUE_SAMPLE)){
								sampleParams.add(p);
							}
						}
						params.removeAll(sampleParams);
						
						DetachedCriteria criteria = DetachedCriteria.forClass(LabStudy.class,VALUE_STUDY);
						addCriteria(criteria, sampleParams, true);
						criteria.setProjection(Projections.distinct(Projections.property("labstudyid")));
						return (List<Integer>) (sampleParams.size() > 0 ? getHibernateTemplate().findByCriteria(criteria) : null);
					}
				});
		return studyIdList;
	}
	
	private void addCurrentSampleFilters(DetachedCriteria criteria, List<Integer> studyIdList){
		if(studyIdList!=null){
			if(studyIdList.size() > 0)
				criteria.add(Restrictions.in("labstudyid", studyIdList));
			else
				criteria.add(Restrictions.isNull("labstudyid"));
		}
	}

	public Integer getTotalRowsByIdResearch(LabStudy filter, Integer idResearch, Integer idstRol) {
		this.idstRol = idstRol;
		return super.getTotalRowsByFilter(filter, idResearch);
	}

	public Integer getLabStudysByIdResearchTotal(LabStudy filter,Integer id){
		return super.getListByFilterTotal(filter, id);
	}

}
