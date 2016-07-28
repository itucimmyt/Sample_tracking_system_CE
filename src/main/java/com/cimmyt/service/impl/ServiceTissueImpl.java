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
package com.cimmyt.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.cimmyt.bean.TissueBean;
import com.cimmyt.model.bean.Tissue;
import com.cimmyt.model.dao.TissueDAO;
import com.cimmyt.service.ServiceTissue;

public class ServiceTissueImpl implements ServiceTissue{

	private TissueDAO tissueDAO;
	/**
	 * Get Tissue 
	 * @param List<TissueBean>
	 */
	@Override
	public List<TissueBean> getTissue(TissueBean tissueBean) {
		List<Tissue> list = tissueDAO.getTissue(tissueBean.getTissue(tissueBean));
		if (list != null && !list.isEmpty() ){
			List<TissueBean> listBean = new ArrayList<TissueBean>();
			for (Tissue tissue : list){
				listBean.add(new TissueBean(tissue));
				}
			return listBean;
			}
		return null;
	}
	/**
	 * Set Tissue
	 * @param tissueDAO
	 */
	public void setTissueDAO(TissueDAO tissueDAO) {
		this.tissueDAO = tissueDAO;
	}
	/**
	 * Add Tissue
	 * @param TissueBean
	 */
	@Override
	public void add(TissueBean bean) {
		tissueDAO.add(bean.getTissue(bean));
	}
	/**
	 * Delete Tissue
	 * @param TissueBean
	 */
	@Override
	public void delete(TissueBean bean) {
		tissueDAO.delete(bean.getTissue(bean));
	}
	/** 
	 * Find Tissue by Name
	 * @param String Name
	 */
	@Override
	public TissueBean getTissueByName (String str){
		Tissue tissue = tissueDAO.getTissueByName(str);
		if (tissue != null){
			return new TissueBean(tissue);
		}
		return null;
	}
}
