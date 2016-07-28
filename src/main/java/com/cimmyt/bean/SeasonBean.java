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
package com.cimmyt.bean;

import com.cimmyt.model.bean.Season;


public class SeasonBean {

	private Integer seasonid;
	private String season_name;
	private String season_description;

	/**
	 * @return the seasonid
	 */
	public Integer getSeasonid() {
		return seasonid;
	}

	/**
	 * @param seasonid the seasonid to set
	 */
	public void setSeasonid(Integer seasonid) {
		this.seasonid = seasonid;
	}

	/**
	 * @return the season_name
	 */
	public String getSeason_name() {
		return season_name;
	}

	/**
	 * @param seasonName the season_name to set
	 */
	public void setSeason_name(String seasonName) {
		season_name = seasonName;
	}

	/**
	 * @return the season_description
	 */
	public String getSeason_description() {
		return season_description;
	}

	/**
	 * @param seasonDescription the season_description to set
	 */
	public void setSeason_description(String seasonDescription) {
		season_description = seasonDescription;
	}
	
	@Override
    public String toString() {
    	return season_name;
    }

	public SeasonBean (){
	}

	public SeasonBean (Season season){
		this.season_description = season.getSeason_description();
		this.season_name = season.getSeason_name();
		this.seasonid = season.getSeasonid();
	}

	public Season getSeason(SeasonBean bean){
		Season season = new Season ();
		season.setSeason_description(bean.getSeason_description());
		season.setSeason_name(bean.getSeason_name());
		season.setSeasonid(bean.getSeasonid());
		return season;
	}

	public SeasonBean getSeasonBean(Season bean){
		SeasonBean season = new SeasonBean ();
		season.setSeason_description(bean.getSeason_description());
		season.setSeason_name(bean.getSeason_name());
		season.setSeasonid(bean.getSeasonid());
		return season;
	}
}
