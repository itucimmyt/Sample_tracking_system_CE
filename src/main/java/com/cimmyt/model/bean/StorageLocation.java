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
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "st_storage_location")
@NamedQueries({
    @NamedQuery(name = "StorageLocation.findAll", query = "SELECT i FROM StorageLocation i")})
public class StorageLocation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "imslocid")
    private Integer imslocid;
    @Column(name = "lsname")
    private String lsname;
    @Column(name = "lname")
    private String lname;
    @Column(name = "comments")
    private String comments;
    @Column(name = "imslocidparent")
    private Integer imslocidparent;
    @OneToMany(mappedBy = "storageLocation")
    private Collection<StorageMov> storageMovCollection;
    @OneToMany(mappedBy = "storageLocation")
    private Collection<SampleDetail> sampleDetailCollection;

    public StorageLocation() {
    	lsname = new String();
    	lname  = new String();
    	comments  = new String();
    }

    /**
	 * @param imslocid
	 * @param lsname
	 * @param lname
	 * @param comments
	 * @param imslocidparent
	 */
	public StorageLocation(Integer imslocid, String lsname, String lname,
			String comments, Integer imslocidparent) {
		super();
		this.imslocid = imslocid;
		this.lsname = lsname;
		this.lname = lname;
		this.comments = comments;
		this.imslocidparent = imslocidparent;
	}

	public StorageLocation(Integer imslocid) {
        this.imslocid = imslocid;
    }

    public Integer getImslocid() {
        return imslocid;
    }

    public void setImslocid(Integer imslocid) {
        this.imslocid = imslocid;
    }

    public String getLsname() {
        return lsname;
    }

    public void setLsname(String lsname) {
        this.lsname = lsname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getImslocidparent() {
        return imslocidparent;
    }

    public void setImslocidparent(Integer imslocidparent) {
        this.imslocidparent = imslocidparent;
    }



    @Override
    public int hashCode() {
        int hash = 0;
        hash += (imslocid != null ? imslocid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StorageLocation)) {
            return false;
        }
        StorageLocation other = (StorageLocation) object;
        if ((this.imslocid == null && other.imslocid != null) || (this.imslocid != null && !this.imslocid.equals(other.imslocid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return lname;
    }

	/**
	 * @return the storageMovCollection
	 */
	public Collection<StorageMov> getStorageMovCollection() {
		return storageMovCollection;
	}

	/**
	 * @param storageMovCollection the storageMovCollection to set
	 */
	public void setStorageMovCollection(Collection<StorageMov> storageMovCollection) {
		this.storageMovCollection = storageMovCollection;
	}

	/**
	 * @return the sampleDetailCollection
	 */
	public Collection<SampleDetail> getSampleDetailCollection() {
		return sampleDetailCollection;
	}

	/**
	 * @param sampleDetailCollection the sampleDetailCollection to set
	 */
	public void setSampleDetailCollection(
			Collection<SampleDetail> sampleDetailCollection) {
		this.sampleDetailCollection = sampleDetailCollection;
	}

}
