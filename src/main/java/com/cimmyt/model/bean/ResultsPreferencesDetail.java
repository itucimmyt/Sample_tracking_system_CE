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

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "st_results_preferences_detail")
public class ResultsPreferencesDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Basic(optional = false)
	    @Column(name = "detailresprefid")
	    private Integer detailresprefid;
	 
	    @JoinColumn(name = "resultpreferencesid", referencedColumnName = "resultpreferencesid")
	    @ManyToOne(optional = false)
	    private ResultsPreferences resultpreferencesid;
	    
	    @Column(name = "fijo")
	    private String fijo;
	    
		@Column(name = "paramid")
		private Integer paramid;
		
		@Column(name = "paramname")
		private String paramname;
		
	    @Column(name = "header")
	    private String header;
	    
		@Column(name = "orden")
		private Integer orden;

		/**
		 * @return the detailresprefid
		 */
		public Integer getDetailresprefid() {
			return detailresprefid;
		}

		/**
		 * @return the resultpreferencesid
		 */
		public ResultsPreferences getResultpreferencesid() {
			return resultpreferencesid;
		}

		/**
		 * @param resultpreferencesid the resultpreferencesid to set
		 */
		public void setResultpreferencesid(ResultsPreferences resultpreferencesid) {
			this.resultpreferencesid = resultpreferencesid;
		}

		/**
		 * @return the fijo
		 */
		public String getFijo() {
			return fijo;
		}

		/**
		 * @param fijo the fijo to set
		 */
		public void setFijo(String fijo) {
			this.fijo = fijo;
		}

		/**
		 * @return the paramid
		 */
		public Integer getParamid() {
			return paramid;
		}

		/**
		 * @param paramid the paramid to set
		 */
		public void setParamid(Integer paramid) {
			this.paramid = paramid;
		}

		/**
		 * @return the header
		 */
		public String getHeader() {
			return header;
		}

		/**
		 * @param header the header to set
		 */
		public void setHeader(String header) {
			this.header = header;
		}

		/**
		 * @return the orden
		 */
		public Integer getOrden() {
			return orden;
		}

		/**
		 * @param orden the orden to set
		 */
		public void setOrden(Integer orden) {
			this.orden = orden;
		}

		/**
		 * @return the paramname
		 */
		public String getParamname() {
			return paramname;
		}

		/**
		 * @param paramname the paramname to set
		 */
		public void setParamname(String paramname) {
			this.paramname = paramname;
		}
		
		
	    
}

