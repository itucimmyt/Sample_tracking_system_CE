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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author 
 */
@Entity
@Table(name = "st_study_template_params")
@NamedQueries({
    @NamedQuery(name = "StudyTemplateParams.findAll", query = "SELECT i FROM StudyTemplateParams i ORDER BY i.templateparamid DESC")})
public class StudyTemplateParams implements Serializable, Comparable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "templateparamid")
    private Integer templateparamid;
    @Column(name = "parametername")
    private String parametername;
    @Column(name = "description")
    private String description;
    @Column(name = "datatype")
    private String datatype;
    @OneToMany(cascade = CascadeType.ALL, fetch= FetchType.LAZY, mappedBy = "templateparamid")
    private Set<SampleDetResult> imsSampleDetResultCollection = new HashSet<SampleDetResult>(0);
    @JoinColumn(name = "studytemplateid", nullable=false)
    @ManyToOne(fetch=FetchType.LAZY)
    private StudyTemplate studytemplateid;

    public StudyTemplateParams() {
    }

	public StudyTemplateParams(StudyTemplate stStudyTemplate) {
		this.studytemplateid = stStudyTemplate;
	}

	public StudyTemplateParams(StudyTemplate stStudyTemplate,
			String parametername, String description, String datatype,
			Set<SampleDetResult> stSampleDetResults) {
		this.studytemplateid = stStudyTemplate;
		this.parametername = parametername;
		this.description = description;
		this.datatype = datatype;
		this.imsSampleDetResultCollection = stSampleDetResults;
	}
    /**
	 * @param parametername
	 * @param description
	 * @param datatype
	 */
	public StudyTemplateParams(String factorname, String description,
			String datatype) {
		this.parametername = factorname;
		this.description = description;
		this.datatype = datatype;
	}

	public StudyTemplateParams(Integer templateparamid) {
        this.templateparamid = templateparamid;
    }

    public Integer getTemplateparamid() {
        return templateparamid;
    }

    public void setTemplateparamid(Integer templateparamid) {
        this.templateparamid = templateparamid;
    }

    public String getFactorname() {
        return parametername;
    }

    public void setFactorname(String factorname) {
        this.parametername = factorname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDatatype() {
        return datatype;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }

    public Set<SampleDetResult> getImsSampleDetResultCollection() {
        return imsSampleDetResultCollection;
    }

    public void setImsSampleDetResultCollection(Set<SampleDetResult> imsSampleDetResultCollection) {
        this.imsSampleDetResultCollection = imsSampleDetResultCollection;
    }

    public StudyTemplate getStudytemplateid() {
        return studytemplateid;
    }

    public void setStudytemplateid(StudyTemplate studytemplateid) {
        this.studytemplateid = studytemplateid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (templateparamid != null ? templateparamid.hashCode() : 0);
        return hash;
    }

//    @Override
//    public boolean equals(Object object) {
//        // TODO: Warning - this method won't work in the case the id fields are not set
//        if (!(object instanceof StudyTemplateParams)) {
//            return false;
//        }
//        StudyTemplateParams other = (StudyTemplateParams) object;
//        if ((this.templateparamid == null && other.templateparamid != null) || (this.templateparamid != null && !this.templateparamid.equals(other.templateparamid))) {
//            return false;
//        }
//        return true;
//    }

    @Override
    public String toString() {
    	return parametername;
    }

	@Override
	public int compareTo(Object obj) {
		/*StudyTemplateParams stTpa = (StudyTemplateParams)obj;
		
		if (stTpa.getTemplateparamid() != null && this.templateparamid != null)
		
		return this.templateparamid.compareTo(((StudyTemplateParams)obj).getTemplateparamid());
		else 
			return 0;*/
		
		return this.getFactorname().compareTo(((StudyTemplateParams)obj).getFactorname());
	}

}

