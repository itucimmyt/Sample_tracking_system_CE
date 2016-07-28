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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 
 * @author NHNEGRETE
 *
 */

@Entity
@Table(name = "st_tissue")
public class Tissue implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Tissue (){
		
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="tissueid")
	private Integer tissueid;
	
	@Column(name = "tissuename")
	private String tissuename;

	/**
	 * @return the idtissue
	 */
	public Integer getIdtissue() {
		return tissueid;
	}

	/**
	 * @param idtissue the idtissue to set
	 */
	public void setIdtissue(Integer idtissue) {
		this.tissueid = idtissue;
	}

	/**
	 * @return the tissueName
	 */
	public String getTissueName() {
		return tissuename;
	}

	/**
	 * @param tissueName the tissueName to set
	 */
	public void setTissueName(String tissueName) {
		this.tissuename = tissueName;
	}
	
	@Override
    public String toString() {
    	return tissuename;
    }
}
