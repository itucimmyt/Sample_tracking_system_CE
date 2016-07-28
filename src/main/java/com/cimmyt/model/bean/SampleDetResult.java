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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author 
 */
@Entity
@Table(name = "st_sample_det_result")
@NamedQueries({
    @NamedQuery(name = "SampleDetResult.findAll", query = "SELECT i FROM SampleDetResult i")})
public class SampleDetResult implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idsampledetresult")
    private Integer idsampledetresult;
    @Column(name = "result")
    private String result;
    @JoinColumn(name = "studysampleid", referencedColumnName = "studysampleid")
    @ManyToOne(optional = false)
    private SampleDetail studysampleid;
    @JoinColumn(name = "templateparamid", referencedColumnName = "templateparamid")
    @ManyToOne(optional = false)
    private StudyTemplateParams templateparamid;

    public SampleDetResult() {
    }

    public SampleDetResult(Integer idsampledetresult) {
        this.idsampledetresult = idsampledetresult;
    }

    public Integer getIdsampledetresult() {
        return idsampledetresult;
    }

    public void setIdsampledetresult(Integer idsampledetresult) {
        this.idsampledetresult = idsampledetresult;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public SampleDetail getStudysampleid() {
        return studysampleid;
    }

    public void setStudysampleid(SampleDetail studysampleid) {
        this.studysampleid = studysampleid;
    }

    public StudyTemplateParams getTemplateparamid() {
        return templateparamid;
    }

    public void setTemplateparamid(StudyTemplateParams templateparamid) {
        this.templateparamid = templateparamid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idsampledetresult != null ? idsampledetresult.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SampleDetResult)) {
            return false;
        }
        SampleDetResult other = (SampleDetResult) object;
        if ((this.idsampledetresult == null && other.idsampledetresult != null) || (this.idsampledetresult != null && !this.idsampledetresult.equals(other.idsampledetresult))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.cimmyt.sampletracking.prototype.persistence.beans.ImsSampleDetResult[idsampledetresult=" + idsampledetresult + "]";
    }

    public String getStrComplete(){
    	return "idsampledetresult: "+idsampledetresult+ " result: "+result+ " studysampleid: "+studysampleid;
    }

}

