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
import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author 
 */
@Entity
@Table(name = "st_storage_mov")
@NamedQueries({
    @NamedQuery(name = "StorageMov.findAll", query = "SELECT s FROM StorageMov s"),
    @NamedQuery(name = "StorageMov.findByIdstoragemov", query = "SELECT s FROM StorageMov s WHERE s.idstoragemov = :idstoragemov"),
    @NamedQuery(name = "StorageMov.findByMovdate", query = "SELECT s FROM StorageMov s WHERE s.movdate = :movdate"),
    @NamedQuery(name = "StorageMov.findByMovtype", query = "SELECT s FROM StorageMov s WHERE s.movtype = :movtype"),
    @NamedQuery(name = "StorageMov.findByQuantity", query = "SELECT s FROM StorageMov s WHERE s.quantity = :quantity"),
    @NamedQuery(name = "StorageMov.findByComments", query = "SELECT s FROM StorageMov s WHERE s.comments = :comments")})
public class StorageMov implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idstoragemov")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idstoragemov;
    @Column(name = "movdate")
    @Temporal(TemporalType.DATE)
    private Date movdate;
    @Column(name = "movtype")
    private String movtype;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "comments")
    private String comments;
    @Column(name="hourmov")
    private String hourmov;
    @Column(name="minmov")
    private String minmov;
    
    @JoinColumn(name = "destlocation", referencedColumnName = "imslocid")
    @ManyToOne
    private StorageLocation storageLocation;
    @JoinColumn(name = "studysampleid", referencedColumnName = "studysampleid")
    @ManyToOne(optional = false)
    private SampleDetail sampleDetail;

    public StorageMov() {
    }

    public StorageMov(Integer idstoragemov) {
        this.idstoragemov = idstoragemov;
    }

    public Integer getIdstoragemov() {
        return idstoragemov;
    }

    public void setIdstoragemov(Integer idstoragemov) {
        this.idstoragemov = idstoragemov;
    }

    public Date getMovdate() {
        return movdate;
    }

    public void setMovdate(Date movdate) {
        this.movdate = movdate;
    }

    public String getMovtype() {
        return movtype;
    }

    public void setMovtype(String movtype) {
        this.movtype = movtype;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public StorageLocation getStorageLocation() {
        return storageLocation;
    }

    public void setStorageLocation(StorageLocation storageLocation) {
        this.storageLocation = storageLocation;
    }

    public SampleDetail getSampleDetail() {
        return sampleDetail;
    }

    public void setSampleDetail(SampleDetail sampleDetail) {
        this.sampleDetail = sampleDetail;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idstoragemov != null ? idstoragemov.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StorageMov)) {
            return false;
        }
        StorageMov other = (StorageMov) object;
        if ((this.idstoragemov == null && other.idstoragemov != null) || (this.idstoragemov != null && !this.idstoragemov.equals(other.idstoragemov))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.cimmyt.cril.apps.sampletracking.core.persistence.beans.StorageMov[idstoragemov=" + idstoragemov + "]";
    }

	/**
	 * @return the hourmov
	 */
	public String getHourmov() {
		return hourmov;
	}

	/**
	 * @param hourmov the hourmov to set
	 */
	public void setHourmov(String hourmov) {
		this.hourmov = hourmov;
	}

	/**
	 * @return the minmov
	 */
	public String getMinmov() {
		return minmov;
	}

	/**
	 * @param minmov the minmov to set
	 */
	public void setMinmov(String minmov) {
		this.minmov = minmov;
	}

}

