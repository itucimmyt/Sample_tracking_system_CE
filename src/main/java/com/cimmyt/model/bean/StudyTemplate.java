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
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author 
 */
@Entity
@Table(name = "st_study_template")
@NamedQueries({
    @NamedQuery(name = "StudyTemplate.findAll", query = "SELECT i FROM StudyTemplate i")})
public class StudyTemplate implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "studytemplateid")
    private Integer studytemplateid;
    @Column(name = "templatename")
    private String templatename;
    @Column(name = "comments")
    private String comments;    
    @OneToMany(cascade = CascadeType.ALL, fetch= FetchType.LAZY, mappedBy = "studytemplateid")
    private Set<StudyTemplateParams> imsStudyTemplateParamsCollection = new HashSet<StudyTemplateParams>(0);
    @OneToMany(cascade = CascadeType.ALL,fetch= FetchType.LAZY, mappedBy = "studytemplateid")
    private Set<LabStudy> imsLabStudyCollection = new HashSet<LabStudy>();

    public StudyTemplate() {
    	studytemplateid= null;
        templatename = new String();
        comments = new String();
      //  imsStudyTemplateParamsCollection =  new ArrayList<StudyTemplateParams>();

    }

    public StudyTemplate(Integer studytemplateid) {
        this.studytemplateid = studytemplateid;
        templatename = new String();
        comments = new String();
        //imsStudyTemplateParamsCollection =  new ArrayList<StudyTemplateParams>();        
    }

    public Integer getStudytemplateid() {
        return studytemplateid;
    }

    public void setStudytemplateid(Integer studytemplateid) {
        this.studytemplateid = studytemplateid;
    }

    public String getTemplatename() {
        return templatename;
    }

    public void setTemplatename(String templatename) {
        this.templatename = templatename;
    }

    public Set<StudyTemplateParams> getImsStudyTemplateParamsCollection() {
        return imsStudyTemplateParamsCollection;
    }

 

    public Set<LabStudy> getImsLabStudyCollection() {
		return imsLabStudyCollection;
	}

	public void setImsLabStudyCollection(Set<LabStudy> imsLabStudyCollection) {
		this.imsLabStudyCollection = imsLabStudyCollection;
	}

	public void setImsStudyTemplateParamsCollection(
			Set<StudyTemplateParams> imsStudyTemplateParamsCollection) {
		this.imsStudyTemplateParamsCollection = imsStudyTemplateParamsCollection;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (studytemplateid != null ? studytemplateid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StudyTemplate)) {
            return false;
        }
        StudyTemplate other = (StudyTemplate) object;
        if ((this.studytemplateid == null && other.studytemplateid != null) || (this.studytemplateid != null && !this.studytemplateid.equals(other.studytemplateid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
    	return templatename;
    }

	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

}
