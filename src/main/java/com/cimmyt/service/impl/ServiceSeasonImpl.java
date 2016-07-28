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

import com.cimmyt.bean.SeasonBean;
import com.cimmyt.model.bean.Season;
import com.cimmyt.model.dao.SeasonDAO;
import com.cimmyt.service.ServiceSeason;

public class ServiceSeasonImpl implements ServiceSeason {

	private SeasonDAO seasonDAO;
	/**
	 * Get list Season
	 * @param Season
	 */
	@Override
	public List<SeasonBean> getSeasons(SeasonBean bean) {
		List<Season> list = seasonDAO.getSeason(bean.getSeason(bean));
		if (list != null && !list.isEmpty() ){
			List<SeasonBean> listBean = new ArrayList<SeasonBean>();
			for (Season obj : list){
				listBean.add(new SeasonBean(obj));
				}
			return listBean;
			}
		return null;
	}
	public void setSeasonDAO(SeasonDAO seasonDAO) {
		this.seasonDAO = seasonDAO;
	}
	/**
	 * Add new Season
	 * @param bean
	 */
	@Override
	public void add (SeasonBean bean){
		seasonDAO.add(bean.getSeason(bean));
	}
	/**
	 * Delete season
	 * @param SeasonBean
	 */
	@Override
	public void delete (SeasonBean bean){
		seasonDAO.delete(bean.getSeason(bean));
	}

	/**
	 * Get Season by Name
	 * @param String Name
	 */
	@Override
	public  SeasonBean getSeasonBeanByName(String name){
		try{
			Season season = seasonDAO.getSeasonByBame(name);
			return new SeasonBean(season);
		}catch (Exception e){
			
		}
		return null;
	}
}
