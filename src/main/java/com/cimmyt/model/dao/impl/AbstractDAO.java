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
//$ Id: AbstractDAO.java, Jun 28, 2010 TMSANCHEZ $
package com.cimmyt.model.dao.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.cimmyt.bean.Operator;
import com.cimmyt.bean.Operator.DataType;

/**
 * TODO add class documentation here
 * 
 * @version $Revision$, $Date: 2010-08-11 17:38:20 -0500 (Wed, 11 Aug 2010) $
 */
public abstract class AbstractDAO <T,PK extends Serializable> extends HibernateDaoSupport implements GenericDAO<T,PK>{
	private Class<T> type;
	
	public AbstractDAO(Class<T> type) {
		this.type = type;
	}
	
	/* (non-Javadoc)
	 * @see org.cimmyt.cril.apps.sampletracking.core.persistence.dao.GenericDAO#create(java.lang.Object)
	 */
	@Override
	public T create(T newInstance) {
		getHibernateTemplate().save(newInstance);
		return newInstance;
	}

	/* (non-Javadoc)
	 * @see org.cimmyt.cril.apps.sampletracking.core.persistence.dao.GenericDAO#delete(java.lang.Object)
	 */
	@Override
	public void delete(T persistentObject) {
		getHibernateTemplate().delete(persistentObject);
	}

	public void deleteWithOutSession(T persistentObject){
		getHibernateTemplate().getSessionFactory().getCurrentSession().setFlushMode(FlushMode.COMMIT);
		getHibernateTemplate().delete(persistentObject);
		getHibernateTemplate().getSessionFactory().getCurrentSession().flush();
	}
	/* (non-Javadoc)
	 * @see org.cimmyt.cril.apps.sampletracking.core.persistence.dao.GenericDAO#read(java.io.Serializable)
	 */
	@Override
	@SuppressWarnings(value="unchecked")
	public T read(PK id) {
		T result = (T)getHibernateTemplate().get(type, id);
		getHibernateTemplate().initialize(result);
		//return (T)getHibernateTemplate().get(type, id);
		return result;
	}

	/* (non-Javadoc)
	 * @see org.cimmyt.cril.apps.sampletracking.core.persistence.dao.GenericDAO#update(java.lang.Object)
	 */
	@Override
	public void update(T transientObject) {
		getHibernateTemplate().saveOrUpdate(transientObject);
	}
	

	public void updateWithoutSession(T transientObject){
		getHibernateTemplate().getSessionFactory().getCurrentSession().setFlushMode(FlushMode.COMMIT);
		getHibernateTemplate().saveOrUpdate(transientObject);
		getHibernateTemplate().getSessionFactory().getCurrentSession().flush();
	}
	/**
	 * 
	 * @param filter
	 * @return
	 */
	@SuppressWarnings(value = "unchecked")
	public List<T> getListByFilter(final T filter) {
		List<T> resultList = null; 
		resultList = (List<T>) this.getHibernateTemplate().executeFind(
				new HibernateCallback() {

					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						DetachedCriteria criteria = DetachedCriteria.forClass(type);
						buildCriteria(criteria, filter);
						return getHibernateTemplate().findByCriteria(criteria);
					}

				});
		return resultList;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public T getByFilter(final T filter) {
		T resultList = null; 
		resultList = (T) this.getHibernateTemplate().execute(
				new HibernateCallback() {

					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						DetachedCriteria criteria = DetachedCriteria.forClass(type);
						buildCriteria(criteria, filter);
						return getHibernateTemplate().findByCriteria(criteria);
					}

				});
		return resultList;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public T getByFilterUnicValue(final T filter) {
		T resultList = null; 
		resultList = (T) this.getHibernateTemplate().execute(
				new HibernateCallback() {

					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						DetachedCriteria criteria = DetachedCriteria.forClass(type);
						buildCriteria(criteria, filter);
						List<?> list = getHibernateTemplate().findByCriteria(criteria);
						if (list !=  null && !list.isEmpty())
						return getHibernateTemplate().findByCriteria(criteria).get(0);
						else 
							return null;
					}

				});
		return resultList;
	}

	public List<T> getListByFilter(final T filter,final Integer id) {
		return getListByFilter(filter, id, 0,0,null, true);
	}

	@SuppressWarnings(value = "unchecked")
	public List<T> getListByFilter(final T filter,final Integer id,final int firstResult,final int maxResults
			, final String sortColumn, final boolean ascending) {
		List<T> resultList = null; 
		resultList = this.getHibernateTemplate().execute(
				new HibernateCallback<List<T>>() {

					public List<T> doInHibernate(Session session)
							throws HibernateException, SQLException {
						DetachedCriteria criteria = DetachedCriteria.forClass(type);
						
						if(!(sortColumn == null || sortColumn.isEmpty()))
							for(String sort : sortColumn.split("\\s*,\\s*"))
								criteria.addOrder( ascending ? Order.asc(sort) : Order.desc(sort));
						
						buildCriteria(criteria, filter, id);
						
						return (List<T>) getHibernateTemplate().findByCriteria(criteria, firstResult, maxResults);
					}

				});
		return resultList;
	}
	
	public Integer getListByFilterTotal(final T filter,final Integer id){
		Integer numResults = null; 
		numResults = this.getHibernateTemplate().execute(
				new HibernateCallback<Integer>() {
					@Override
					public Integer doInHibernate(Session session)
							throws HibernateException, SQLException {
						DetachedCriteria criteria = DetachedCriteria.forClass(type);
						buildCriteria(criteria, filter, id);
						criteria.setProjection(Projections.rowCount());
						return (Integer)getHibernateTemplate().findByCriteria(criteria).get(0);
					}

				});
		return numResults;
	}
	
	public Integer getTotalRowsByFilter(final T filter,final Integer id) {
		Integer resultList = this.getHibernateTemplate().execute(
				new HibernateCallback<Integer>() {
					@Override
					public Integer doInHibernate(Session session)
							throws HibernateException, SQLException {
						DetachedCriteria criteria = DetachedCriteria.forClass(type);
						buildCriteria(criteria, filter, id);
						criteria.setProjection(Projections.rowCount());
						Object obj = getHibernateTemplate().findByCriteria(criteria).get(0);
						if (obj instanceof Long)
						return ((Long)getHibernateTemplate().findByCriteria(criteria).get(0)).intValue();
						else if (obj instanceof Integer)
							return (Integer)getHibernateTemplate().findByCriteria(criteria).get(0);
						else 
							return 0;
					}
				});
		return resultList;
	}
	public Integer getListByFilterTotalLong(final T filter,final Integer id){
		Integer numResults = null; 
		numResults = this.getHibernateTemplate().execute(
				new HibernateCallback<Integer>() {
					@Override
					public Integer doInHibernate(Session session)
							throws HibernateException, SQLException {
						DetachedCriteria criteria = DetachedCriteria.forClass(type);
						buildCriteria(criteria, filter);
						criteria.setProjection(Projections.rowCount());
						Object obj = getHibernateTemplate().findByCriteria(criteria).get(0);
						if (obj instanceof Long)
						return ((Long)getHibernateTemplate().findByCriteria(criteria).get(0)).intValue();
						else if (obj instanceof Integer)
							return (Integer)getHibernateTemplate().findByCriteria(criteria).get(0);
						else 
							return 0;
					}

				});
		return numResults;
	}
	/**
	 * Find single by values
	 * @param clazz
	 * @param properties
	 * @param values
	 * @return
	 * @throws Exception
	 */
	public  T findSingleByValues(T clazz, Object[] properties, Object[] values) throws Exception
    {
        
        if(properties.length != values.length)
        {
            throw new Exception("The number of properties must be the same than the number of values");
        }
        String strClazz = type.getSimpleName();
        StringBuilder builder = new StringBuilder("SELECT DISTINCT a FROM "+strClazz+" a"+ " WHERE ");
        for( int i = 0; i < values.length; i++ )
        {
            if(values[i] instanceof String)
            {
                builder.append( "a."+properties[i]+" LIKE ?" );
            }
            else
            {
                builder.append( "a."+properties[i]+" = ?" );
            }
            builder.append( " AND " );
        }
        String query = builder.toString().trim();
        if(StringUtils.endsWith( query, "AND" ))
        {
            query = StringUtils.removeEnd( query, "AND" );
        }
        @SuppressWarnings("unchecked")
		List<T> list= (List<T>)getHibernateTemplate().find( query, values );
        if(list != null && !list.isEmpty() )
        {
            return list.get( 0 );
        }
        else
        {
            return null;
        }  
    }
	/**
	 * Find single by values
	 * @param clazz
	 * @param properties
	 * @param values
	 * @return
	 * @throws Exception
	 */
	public  List<T> findListByValues(T clazz, Object[] properties, Object[] values) throws Exception
    {
        
        if(properties.length != values.length)
        {
            throw new Exception("The number of properties must be the same than the number of values");
        }
        String strClazz = type.getSimpleName();
        StringBuilder builder = new StringBuilder("FROM "+strClazz+" as a"+ " WHERE ");
        for( int i = 0; i < values.length; i++ )
        {
            if(values[i] instanceof String)
            {
                builder.append( "a."+properties[i]+" LIKE ?" );
            }
            else
            {
                builder.append( "a."+properties[i]+" = ?" );
            }
            builder.append( " AND " );
        }
        String query = builder.toString().trim();
        if(StringUtils.endsWith( query, "AND" ))
        {
            query = StringUtils.removeEnd( query, "AND" );
        }
        @SuppressWarnings("unchecked")
		List<T> list= (List<T>)getHibernateTemplate().find( query, values );
        if(list != null && !list.isEmpty() )
        {
            return list;
        }
        else
        {
            return null;
        }  
    }
	

	/**
	 * 
	 * @param criteria
	 * @param filter
	 */
	protected abstract void buildCriteria(DetachedCriteria criteria, final T filter);

	protected abstract void buildCriteria(DetachedCriteria criteria, final T filter, Integer id);

	/**
	 * adds a criterion to a Junction(conjunction/disjunction), based on the meta-data passed.
	 * 
	 * @param junction The Junction to conditionally add a criterion
	 * @param condition The type of criterion: like, equals, etc.
	 * @param dataType Indicates if its a numeric or character value for a filter
	 * @param qualifiedParam the qualified parameter for a query
	 * @param value The string value to use in the criterion
	 */
	public void addDynamicCriterion(Junction junction, Operator condition, DataType dataType,
			String qualifiedParam, String value){
		if(!qualifiedParam.equals("sample.studysampleid")){

			if(dataType == DataType.STRING){
				if(condition == Operator.TypeString.LIKE){
					junction.add(Restrictions.like(qualifiedParam, "%"+value+"%"));
				}else if(condition == Operator.TypeString.EQUAL){
					junction.add(Restrictions.eq(qualifiedParam, value));
				}else if(condition == Operator.TypeString.NOT_EQUAL){
					junction.add(Restrictions.ne(qualifiedParam, value));
				}else if(condition == Operator.TypeString.NOT_LIKE){
					junction.add(Restrictions.not( Restrictions.like(qualifiedParam, "%"+value+"%")) );
				}
				
			}else if(dataType == DataType.NUMBER){
				if(condition == Operator.TypeNumber.EQUALS){
					junction.add(Restrictions.eq(qualifiedParam, Integer.valueOf( value) ) );
				}else if(condition == Operator.TypeNumber.GREATER){
					junction.add(Restrictions.ge(qualifiedParam, Integer.valueOf( value) ) );
				}if(condition == Operator.TypeNumber.LESS){
					junction.add(Restrictions.le(qualifiedParam, Integer.valueOf( value) ) );
				}else if(condition == Operator.TypeNumber.NOT_EQUALS){
					junction.add(Restrictions.ne(qualifiedParam, Integer.valueOf( value) ) );
				}else if(condition == Operator.TypeNumber.IN){
					List<Integer> listStr = new ArrayList<Integer>();
					String[] arr = value.split(",");
					for (int i =0 ; i < arr.length ; i++){
						try{
						if (!arr[i].trim().equals(""))
						listStr.add(Integer.parseInt(arr [i]));
						}catch (Exception ex){
							ex.printStackTrace();
						}
					}
					System.out.println("List ::: "+listStr);
					junction.add(Restrictions.in(qualifiedParam, listStr ) );
				}
				
			}
		}else{
			String prefix = parseSamplePrefix(value);
			int id = parseSampleId(value);
			
			if(condition == Operator.TypeString.LIKE){
				junction.add(Restrictions.like("study.prefix", prefix,MatchMode.ANYWHERE));
				junction.add(Restrictions.eq("sample.samplegid", id));
			}else if(condition == Operator.TypeString.EQUAL){
				junction.add(Restrictions.eq("study.prefix", prefix));
				junction.add(Restrictions.eq("sample.samplegid", id));
			}else if(condition == Operator.TypeString.NOT_EQUAL){
				junction.add(Restrictions.ne("study.prefix", prefix));
				junction.add(Restrictions.ne("sample.samplegid", id));
			}else if(condition == Operator.TypeString.NOT_LIKE){
				junction.add(Restrictions.not( Restrictions.like("study.prefix", prefix,MatchMode.ANYWHERE)) );
				junction.add(Restrictions.not( Restrictions.eq("sample.samplegid", id)) );
			}
		}
	}
	

	private int parseSampleId(String input){
		for(String s : input.split("\\D+")){
			if(!s.isEmpty()){
				return Integer.parseInt(s);
			}
		}
		return 0;
	}
	private String parseSamplePrefix(String input){
		for(String s : input.split("\\d+")){
			if(!s.isEmpty()){
				return s;
			}
		}
		return "";
	}

	@SuppressWarnings("unchecked")
	public  List<T> findListByQuery(T clazz, String queryString) 
    {
    
		try {
        	List<T> list = (List<T>)getHibernateTemplate().find(queryString);
        return list;
        }catch (HibernateException  ex){
        	
        	logger.error(ex.getMessage(), ex);
        	
        }catch(Exception exG){
        	exG.printStackTrace();
        	logger.error(exG.getMessage(), exG);
        	
        }finally {
        	
        } 
    	 return null;
    }

	@SuppressWarnings("unchecked")
	public T findGenericByQuery(T clazz, String queryString) 
    {
    
		try {
        	List<T> list = (List<T>)getHibernateTemplate().find(queryString);
        	if (list != null && !list.isEmpty()){
        		return list.get(0);
        	}
        	return null;
        }catch (HibernateException  ex){
        	
        	logger.error(ex.getMessage(), ex);
        	
        }catch(Exception exG){
        	exG.printStackTrace();
        	logger.error(exG.getMessage(), exG);
        	
        }finally {
        	
        } 
    	 return null;
    }
}
