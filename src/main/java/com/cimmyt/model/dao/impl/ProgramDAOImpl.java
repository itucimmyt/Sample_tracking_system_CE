package com.cimmyt.model.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.cimmyt.model.bean.Program;
import com.cimmyt.model.dao.ProgramDAO;

public class ProgramDAOImpl extends AbstractDAO<Program, Integer> implements ProgramDAO{

	private boolean areAll;
	public ProgramDAOImpl() {
		super(Program.class);
	}

	@Override
	public List<Program> getListProgram(Program filter, boolean areAll) {
		this.areAll = areAll;
		return super.getListByFilter(filter);
	}

	@Override
	public void saveOrUpdate(Program filter) {
		super.update(filter);
	}

	@Override
	protected void buildCriteria(DetachedCriteria criteria, Program filter) {
		if (filter.getOrganism() != null && filter.getOrganism().getOrganismid() != null){
			criteria.add(Restrictions.eq("organism.organismid", filter.getOrganism().getOrganismid()));
		}
		if (filter.getProgramName() != null && !filter.getProgramName().trim().isEmpty()){
			criteria.add(Restrictions.eq("programName", filter.getProgramName()));
		}
		if (!areAll){
			criteria.add(Restrictions.eq("status", filter.isStatus()));
		}
		criteria.addOrder(Order.asc("organism.organismid"));
		criteria.addOrder(Order.desc("idstProgram"));
	}

	@Override
	protected void buildCriteria(DetachedCriteria criteria, Program filter,
			Integer id) {
		// TODO Auto-generated method stub
		
	}

}
