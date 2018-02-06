package com.cimmyt.model.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.cimmyt.model.bean.Purpose;
import com.cimmyt.model.dao.PurposeDAO;

public class PurposeDAOImpl extends AbstractDAO<Purpose, Integer> implements PurposeDAO{

	private boolean areAll;
	public PurposeDAOImpl() {
		super(Purpose.class);
	}

	@Override
	public List<Purpose> getListPurpose(Purpose filter, boolean areAll) {
		this.areAll = areAll;
		return super.getListByFilter(filter);
	}

	@Override
	public void saveOrUpdate(Purpose filter) {
		super.update(filter);
		
	}

	@Override
	protected void buildCriteria(DetachedCriteria criteria, Purpose filter) {
		if (filter.getOrganism() != null && filter.getOrganism().getOrganismid() != null){
			criteria.add(Restrictions.eq("organism.organismid", filter.getOrganism().getOrganismid()));
		}
		if (filter.getPurposeName() != null && !filter.getPurposeName().trim().isEmpty()){
			criteria.add(Restrictions.like("purposeName", filter.getPurposeName()));
		}
		if (!areAll){
			criteria.add(Restrictions.eq("status", filter.isStatus()));
		}

		criteria.addOrder(Order.asc("organism.organismid"));
		criteria.addOrder(Order.desc("idstPurpose"));
	}

	@Override
	protected void buildCriteria(DetachedCriteria criteria, Purpose filter,
			Integer id) {
		// TODO Auto-generated method stub
		
	}

}
