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

import java.text.ParseException;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.cimmyt.model.bean.Log;
import com.cimmyt.model.dao.LogDAO;
import com.cimmyt.utils.ConstantsDNA;
import com.cimmyt.utils.StrUtils;

public class LogDAOImpl extends AbstractDAO<Log, Integer> implements LogDAO {

	private DetachedCriteria criteria;
	private String initDate, endDate = null;

	public LogDAOImpl() {
		super(Log.class);
	}

	@Override
	public void saveOrUpdate(Log model) {
		super.update(model);
	}

	@Override
	public void delete(Log model) {
		super.delete(model);	
	}

	@Override
	public Log getLog(Log model) {
		return super.getByFilterUnicValue(model);
	}

	@Override
	public List<Log> getListLog(Log model, int firstResult, int maxResults,
			int sortColumn, boolean ascending, String initDate, String endDate,int idRoll) {
		this.initDate = initDate;
		this.endDate =  endDate;
		String strSortColumn = null ;
		switch (sortColumn){
			case 1 : 
				strSortColumn = "stUserVersion.investName";
				break;
			case 2 :
				strSortColumn = "logTyperOperation.description";
				break;
			case 3 :
				strSortColumn = "operationDate";
				break;
			case 4 :
				strSortColumn = "stUserVersion.investigator.investName";
				break;
		}
			return super.getListByFilter(model, idRoll, firstResult, maxResults, strSortColumn, ascending);
	}

	@Override
	protected void buildCriteria(DetachedCriteria _criteria, Log filter) {
		this.criteria = _criteria;
		if (filter != null){
			if (filter.getIdLog() != null)
				criteria.add(Restrictions.eq("idLog",filter.getIdLog() ));
			if (filter.getLogTyperOperation() != null && filter.getLogTyperOperation().getIdLogTypeOperation() != null)
				criteria.add(Restrictions.eq("logTyperOperation.idLogTypeOperation",filter.getLogTyperOperation().getIdLogTypeOperation() ));
			if (filter.getStUserVersion() != null && filter.getStUserVersion().getIdUser() != null)
				criteria.add(Restrictions.eq("stUserVersion.idUser", filter.getStUserVersion().getIdUser()));
		}
		
	}

	@Override
	protected void buildCriteria(DetachedCriteria _criteria, Log filter,
			Integer id) {
		this.criteria = _criteria;
		try{
			if (initDate != null && endDate != null){
				criteria.add(Restrictions.between("operationDate", StrUtils.getDateStandarFromStr(initDate),
						StrUtils.getDateStandarFromStr(endDate)));
			}
			else if (initDate != null && endDate == null){
				criteria.add(Restrictions.ge("operationDate", StrUtils.getDateStandarFromStr( initDate)));
			}else if (endDate != null && initDate == null){
				criteria.add(Restrictions.le("operationDate", StrUtils.getDateStandarFromStr(endDate)));
			}
		}catch (ParseException pEx){
			pEx.printStackTrace();
		}

		switch(id){
		case ConstantsDNA.ROLE_ADMINISTRATOR :
			validateRoleResearcher(filter);
			break;
		case ConstantsDNA.ROLE_DATA_MANAGER :
			addCriteriaNotAdministrator();
			validateRoleResearcher(filter);
			break;
		case ConstantsDNA.ROLE_RESEARCHER :
			addCriteriaNotAdministrator();
			criteria.add(Restrictions.sqlRestriction(
					"this_.user_version_id not in (Select  u.user_version_id from st_user_version u where u.idst_rol ="
			+ ConstantsDNA.ROLE_DATA_MANAGER+")"));
			validateRoleResearcher(filter);
			break;
		} 
		if (filter.getLogTyperOperation() != null && filter.getLogTyperOperation().getIdLogTypeOperation() != null)
			criteria.add(Restrictions.eq("logTyperOperation.idLogTypeOperation",filter.getLogTyperOperation().getIdLogTypeOperation() ));
		criteria.addOrder(Order.asc("operationDate"));
		
	}

	private void addCriteriaNotAdministrator (){
		criteria.add(Restrictions.sqlRestriction(
				"this_.user_version_id not in (Select  u.user_version_id from st_user_version u where u.idst_rol ="
		+ ConstantsDNA.ROLE_ADMINISTRATOR+")"));
	}
	
	private void validateRoleResearcher (Log filter){
		if (filter.getStUserVersion() != null && filter.getStUserVersion().getStRole()!= null &&
				 filter.getStUserVersion().getStRole().getIdstRol().intValue() == ConstantsDNA.ROLE_RESEARCHER ){
			if (filter.getStUserVersion() != null && filter.getStUserVersion().getInvestigator() != null 
					&& filter.getStUserVersion().getInvestigator().getInvestigatorid().intValue() > 0 ){
			criteria.add(Restrictions.sqlRestriction("this_.user_version_id in (select u.user_version_id from st_user_version u where u.investigator_id ="+
					filter.getStUserVersion().getInvestigator().getInvestigatorid().intValue()+")"));
			}
		}else
			validateCriteria(filter);
	}
	private void validateCriteria (Log filter){
		if (filter != null){
			if (filter.getStUserVersion() != null && filter.getStUserVersion().getIdUser() != null)
				criteria.add(Restrictions.eq("stUserVersion.idUser", filter.getStUserVersion().getIdUser()));
			
		}
	}
	@Override
	public Integer getTotalRowsByFilter(Log filter, int idRoll,String initDate, String endDate) {
		try{
		  return super.getListByFilterTotal(filter, idRoll);
		}catch (Exception ex){
			ex.printStackTrace();
			return 0;
		}
	}

}
