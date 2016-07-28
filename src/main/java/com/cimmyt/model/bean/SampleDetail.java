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
import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author 
 */
@Entity
@Table(name = "st_sample_detail")
@NamedQueries({
    @NamedQuery(name = "SampleDetail.findAll", query = "SELECT i FROM SampleDetail i")})
public class SampleDetail implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "studysampleid")
    private Integer studysampleid;
    
    @Column(name = "breedergid")
    private Integer breedergid;
    
    @Column(name = "sampleid")
    private Integer samplegid;
    
    @Column(name = "entryNo")
    private Integer entryNo;
    
	@Column(name = "platename", length = 40)
	private String platename;
	
	@Column(name = "plateloc", length = 7)
	private String plateloc;
	
	@Column(name = "selforsend", length = 1)
	private String selforsend;
	
    @Column(name = "lastmovdate")
    @Temporal(TemporalType.DATE)
    private Date lastmovdate;	
    
  //		Added by NancyHN 21/10/2011 
    @Column(name = "nplanta")
    private Integer nplanta;
    
    @Column(name = "nval")
    private String nval;
    
    @Column(name = "specie")
    private String specie;
    
    @Column(name = "priority")
    private String priority;
    
    @Column(name = "controltype")
    private String controltype;
   

	@OneToMany(cascade = CascadeType.ALL,fetch= FetchType.LAZY, mappedBy = "stSampleDetail")
    private Collection<ShipmentDetail> imsShipmentdetailCollection;
    
    @JoinColumn(name = "labstudyid", referencedColumnName = "labstudyid")
    @ManyToOne(optional = false)
    private LabStudy labstudyid;
    
    @OneToMany(cascade = CascadeType.ALL,fetch= FetchType.LAZY, mappedBy = "studysampleid")
    private Collection<SampleDetResult> imsSampleDetResultCollection;
    
    @OneToMany(cascade = CascadeType.ALL,fetch= FetchType.LAZY, mappedBy = "sampleDetail")
    private Collection<StorageMov> storageMovCollection;
    
    @JoinColumn(name = "currentlocation", referencedColumnName = "imslocid")
    @ManyToOne
    private StorageLocation storageLocation;
    
    @JoinColumn(name = "locationid", referencedColumnName = "locationid")
    @ManyToOne
    private LocationCatalog locationid;
    
    @JoinColumn(name = "seasonid", referencedColumnName = "seasonid")
    @ManyToOne
    private Season seasonid;
    
    public SampleDetail() {
    }

    public SampleDetail(Integer studysampleid) {
        this.studysampleid = studysampleid;
    }

    public Integer getStudysampleid() {
        return studysampleid;
    }

    public void setStudysampleid(Integer studysampleid) {
        this.studysampleid = studysampleid;
    }

    public Integer getBreedergid() {
        return breedergid;
    }

    public void setBreedergid(Integer breedergid) {
        this.breedergid = breedergid;
    }

    public Integer getSamplegid() {
        return samplegid;
    }

    public void setSamplegid(Integer samplegid) {
        this.samplegid = samplegid;
    }

    public Integer getEntryNo() {
        return entryNo;
    }

    public void setEntryNo(Integer entryNo) {
        this.entryNo = entryNo;
    }

    public Collection<ShipmentDetail> getImsShipmentdetailCollection() {
        return imsShipmentdetailCollection;
    }

    public void setImsShipmentdetailCollection(Collection<ShipmentDetail> imsShipmentdetailCollection) {
        this.imsShipmentdetailCollection = imsShipmentdetailCollection;
    }

    public LabStudy getLabstudyid() {
        return labstudyid;
    }

    public void setLabstudyid(LabStudy labstudyid) {
        this.labstudyid = labstudyid;
    }

    public Collection<SampleDetResult> getImsSampleDetResultCollection() {
        return imsSampleDetResultCollection;
    }

    public void setImsSampleDetResultCollection(Collection<SampleDetResult> imsSampleDetResultCollection) {
        this.imsSampleDetResultCollection = imsSampleDetResultCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (studysampleid != null ? studysampleid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SampleDetail)) {
            return false;
        }
        SampleDetail other = (SampleDetail) object;
        if ((this.studysampleid == null && other.studysampleid != null) || (this.studysampleid != null && !this.studysampleid.equals(other.studysampleid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.cimmyt.sampletracking.prototype.persistence.beans.ImsSampleDetail[studysampleid=" + studysampleid + "]";
    }

	/**
	 * @return the platename
	 */
	public String getPlatename() {
		return platename;
	}

	/**
	 * @param platename the platename to set
	 */
	public void setPlatename(String platename) {
		this.platename = platename;
	}

	/**
	 * @return the plateloc
	 */
	public String getPlateloc() {
		return plateloc;
	}

	/**
	 * @param plateloc the plateloc to set
	 */
	public void setPlateloc(String plateloc) {
		this.plateloc = plateloc;
	}

	/**
	 * @return the selforsend
	 */
	public String getSelforsend() {
		return selforsend;
	}

	/**
	 * @param selforsend the selforsend to set
	 */
	public void setSelforsend(String selforsend) {
		this.selforsend = selforsend;
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
	 * @return the storageLocation
	 */
	public StorageLocation getStorageLocation() {
		return storageLocation;
	}

	/**
	 * @param storageLocation the storageLocation to set
	 */
	public void setStorageLocation(StorageLocation storageLocation) {
		this.storageLocation = storageLocation;
	}

	/**
	 * @return the lastmovdate
	 */
	public Date getLastmovdate() {
		return lastmovdate;
	}

	/**
	 * @param lastmovdate the lastmovdate to set
	 */
	public void setLastmovdate(Date lastmovdate) {
		this.lastmovdate = lastmovdate;
	}
//	Added by NancyHN 21/10/2011 
	public Integer getNplanta() {
		return nplanta;
	}

	public void setNplanta(Integer nplanta) {
		this.nplanta = nplanta;
	}
	
	  public String getNval() {
			return nval;
		}

		public void setNval(String nval) {
			this.nval = nval;
		}

		public String getSpecie() {
			return specie;
		}

		public void setSpecie(String specie) {
			this.specie = specie;
		}

		public String getPriority() {
			return priority;
		}

		public void setPriority(String priority) {
			this.priority = priority;
		}

		/**
		 * @return the controltype
		 */
		public String getControltype() {
			return controltype;
		}

		/**
		 * @param controltype the controltype to set
		 */
		public void setControltype(String controltype) {
			this.controltype = controltype;
		}

		/**
		 * @return the locationid
		 */
		public LocationCatalog getLocationid() {
			return locationid;
		}

		/**
		 * @param locationid the locationid to set
		 */
		public void setLocationid(LocationCatalog locationid) {
			this.locationid = locationid;
		}

		/**
		 * @return the seasonid
		 */
		public Season getSeasonid() {
			return seasonid;
		}

		/**
		 * @param seasonid the seasonid to set
		 */
		public void setSeasonid(Season seasonid) {
			this.seasonid = seasonid;
		}
		
}

