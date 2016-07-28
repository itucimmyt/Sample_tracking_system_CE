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

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.cimmyt.model.bean.Tissue;
import com.cimmyt.model.dao.TissueDAO;

/**+
 * 
 */
public class TissueDAOImpl extends AbstractDAO<Tissue, Integer> implements TissueDAO {

	public TissueDAOImpl() {
		super(Tissue.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void buildCriteria(DetachedCriteria criteria, Tissue filter) {
		// TODO Auto-generated method stub
		if (filter.getIdtissue() != null) {
			criteria.add(Restrictions.eq("idtissue", filter.getIdtissue()));
		}
		
		if (filter.getTissueName()!= null && ! filter.getTissueName().isEmpty()) {
			criteria.add(Restrictions.like("tissuename", "%"+filter.getTissueName()+"%"));
		}
	}

	/**
	 * Get List of Tissue
	 * @param Tissue
	 */
	@Override
	public List<Tissue> getTissue(Tissue tissue) {
		return super.getListByFilter(tissue);
	}
	/**
	 * Add Tissue
	 * @param tissue
	 */
	@Override
	public void add(Tissue tissue) {
		super.update(tissue);
	}
	/**
	 * Delete Tissue
	 * @param Tissue
	 */
	@Override
	public void delete(Tissue tissue) {
		super.delete(tissue);
	}
	/**
	 * Get Tissue by Name
	 * @param String Name
	 */
	public Tissue getTissueByName (String str) {
		try{
		Tissue tissue = super.findSingleByValues( new Tissue(), new String []{"tissuename"},new Object []{str});
		return tissue;
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected void buildCriteria(DetachedCriteria criteria, Tissue filter,
			Integer id) {
		// TODO Auto-generated method stub
		
	}

}
