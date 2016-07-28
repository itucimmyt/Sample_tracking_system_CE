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
package com.cimmyt.model.bean;


import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "st_results_preferences")
public class ResultsPreferences implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "resultpreferencesid")
	private Integer resultpreferencesid;
	@Column(name = "preferencesname")
	private String preferencesname;
	@Column(name = "columnscount")
	private Integer columnscount;
	@JoinColumn(name = "studytemplateid", referencedColumnName = "studytemplateid")
	@ManyToOne(optional = false)
	private StudyTemplate studytemplateid;
	
	@OneToMany(cascade = CascadeType.ALL,fetch= FetchType.LAZY, mappedBy = "resultpreferencesid")
	public Collection<ResultsPreferencesDetail> resultsPreferencesDetailCollection;
	
	/**
	 * @return the resultsPreferencesDetailCollection
	 */
	public Collection<ResultsPreferencesDetail> getResultsPreferencesDetailCollection() {
		return resultsPreferencesDetailCollection;
	}

	/**
	 * @param resultsPreferencesDetailCollection the resultsPreferencesDetailCollection to set
	 */
	public void setResultsPreferencesDetailCollection(
			Collection<ResultsPreferencesDetail> resultsPreferencesDetailCollection) {
		this.resultsPreferencesDetailCollection = resultsPreferencesDetailCollection;
	}

	/**
	 * @return the resultpreferencesid
	 */
	public Integer getResultpreferencesid() {
		return resultpreferencesid;
	}

	/**
	 * @return the preferencesname
	 */
	public String getPreferencesname() {
		return preferencesname;
	}
	/**
	 * @param preferencesname the preferencesname to set
	 */
	public void setPreferencesname(String preferencesname) {
		this.preferencesname = preferencesname;
	}
	/**
	 * @return the columnscount
	 */
	public Integer getColumnscount() {
		return columnscount;
	}
	/**
	 * @param columnscount the columnscount to set
	 */
	public void setColumnscount(Integer columnscount) {
		this.columnscount = columnscount;
	}
	/**
	 * @return the studytemplateid
	 */
	public StudyTemplate getStudytemplateid() {
		return studytemplateid;
	}
	/**
	 * @param studytemplateid the studytemplateid to set
	 */
	public void setStudytemplateid(StudyTemplate studytemplateid) {
		this.studytemplateid = studytemplateid;
	}
	
	@Override
	public String toString() {
		return preferencesname;
	}
	

}

