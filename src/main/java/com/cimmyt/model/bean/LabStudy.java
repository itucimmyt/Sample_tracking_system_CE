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
import java.util.ArrayList;
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
@Table(name = "st_lab_study")
@NamedQueries( { @NamedQuery(name = "LabStudy.findAll", query = "SELECT i FROM LabStudy i") })
public class LabStudy implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "labstudyid")
	private Integer labstudyid;
	@Column(name = "title")
	private String title;
	@Column(name = "objective")
	private String objective;
	@Column(name = "startdate")
	@Temporal(TemporalType.DATE)
	private Date startdate;
	@Column(name = "enddate")
	private Date enddate;
	@Column(name = "platetype")
	private String platetype;
	@Column(name = "platesize")
	private Integer platesize;
	@Column(name = "numindiv")
	private Integer numindiv;
	
	@Column(name = "numofplates")
	private Integer numofplates;
	
	//Added by NancyHN 21/10/2011 
	@Column(name = "prefix")
	private String  prefix;
	
	@Column(name = "numcontrols")
	private Integer numcontrols;
	
	

	@OneToMany(cascade = CascadeType.ALL,fetch= FetchType.LAZY, mappedBy = "labstudyid")
	private Collection<SampleDetail> sampleDetailCollection ;
	@JoinColumn(name = "studytemplateid", referencedColumnName = "studytemplateid")
	@ManyToOne(optional = false)
	private StudyTemplate studytemplateid;

	//Added by   NancyHN 24/10/2011
	@JoinColumn(name = "projectid", referencedColumnName = "projectid")
	@ManyToOne(optional = false)
	private Project project;
	
	@JoinColumn(name = "investigatorid", referencedColumnName = "investigatorid")
	@ManyToOne(optional = false)
	private Investigator investigatorid;
	

	@JoinColumn(name = "organismid", referencedColumnName = "organismid")
	@ManyToOne(optional = false)
	private Organism organismid;
	
	//Added by   NancyHN 02/01/2012
	@JoinColumn(name = "tissueid", referencedColumnName = "tissueid")
	@ManyToOne(optional = false)
	private Tissue tissue;
	
	
	//Added by   NancyHN 16/02/2012
	@JoinColumn(name = "locationid", referencedColumnName = "locationid")
	@ManyToOne(optional = false)
	private LocationCatalog location;
	
	@JoinColumn(name = "seasonid", referencedColumnName = "seasonid")
	@ManyToOne(optional = false)
	private Season season;

	@JoinColumn(name = "fk_status", referencedColumnName = "id_status")
	@ManyToOne(optional = false)
	private Status status;

	@JoinColumn(name = "fk_load_type", referencedColumnName = "id_load_type")
	@ManyToOne(optional = false)
	private LoadType loadType;

	public LabStudy() {
		title = new String();
		objective = new String();
		startdate = new Date();
		enddate = new Date();
		platetype = new String();
		platesize = 96;
		numindiv = 1;
		sampleDetailCollection = new ArrayList<SampleDetail>();
		numofplates = 1;
		studytemplateid = new StudyTemplate();
	}

	public LabStudy(Integer labstudyid) {
		this.labstudyid = labstudyid;
	}

	public Integer getLabstudyid() {
		return labstudyid;
	}

	public void setLabstudyid(Integer labstudyid) {
		this.labstudyid = labstudyid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getObjective() {
		return objective;
	}

	public void setObjective(String objective) {
		this.objective = objective;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public String getPlatetype() {
		return platetype;
	}

	public void setPlatetype(String platetype) {
		this.platetype = platetype;
	}

	public Integer getPlatesize() {
		return platesize;
	}

	public void setPlatesize(Integer platesize) {
		this.platesize = platesize;
	}

	public Collection<SampleDetail> getSampleDetailCollection() {
		return sampleDetailCollection;
	}

	public void setImsSampleDetailCollection(
			Collection<SampleDetail> imsSampleDetailCollection) {
		this.sampleDetailCollection = imsSampleDetailCollection;
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
		hash += (labstudyid != null ? labstudyid.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof LabStudy)) {
			return false;
		}
		LabStudy other = (LabStudy) object;
		if ((this.labstudyid == null && other.labstudyid != null)
				|| (this.labstudyid != null && !this.labstudyid
						.equals(other.labstudyid))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return title;
	}

	/**
	 * @return the numindiv
	 */
	public Integer getNumindiv() {
		return numindiv;
	}

	/**
	 * @param numindiv the numindiv to set
	 */
	public void setNumindiv(Integer numindiv) {
		this.numindiv = numindiv;
	}

	/**
	 * @return the numofplates
	 */
	public Integer getNumofplates() {
		return numofplates;
	}

	/**
	 * @param numofplates the numofplates to set
	 */
	public void setNumofplates(Integer numofplates) {
		this.numofplates = numofplates;
	}
	
	//Added by NancyHN 21/10/2011 
	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public Organism getOrganismid() {
		return organismid;
	}

	public void setOrganismid(Organism organismid) {
		this.organismid = organismid;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Investigator getInvestigatorid() {
		return investigatorid;
	}

	public void setInvestigatorid(Investigator investigatorid) {
		this.investigatorid = investigatorid;
	}

	
	/**
	 * @return the numcontrols
	 */
	public Integer getNumcontrols() {
		return numcontrols;
	}

	/**
	 * @param numcontrols the numcontrols to set
	 */
	public void setNumcontrols(Integer numcontrols) {
		this.numcontrols = numcontrols;
	}
	
	/**
	 * @return the tissue
	 */
	public Tissue getTissue() {
		return tissue;
	}

	/**
	 * @param tissue the tissue to set
	 */
	public void setTissue(Tissue tissue) {
		this.tissue = tissue;
	}

	/**
	 * @return the location
	 */
	public LocationCatalog getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(LocationCatalog location) {
		this.location = location;
	}

	/**
	 * @return the season
	 */
	public Season getSeason() {
		return season;
	}

	/**
	 * @param season the season to set
	 */
	public void setSeason(Season season) {
		this.season = season;
	}

	public Object clone(){
		Object obj = null;
		try{
            obj=super.clone();
        }catch(CloneNotSupportedException ex){
            System.out.println(" no se puede duplicar");
        }
		return obj;
	}
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public LoadType getLoadType() {
		return loadType;
	}

	public void setLoadType(LoadType loadType) {
		this.loadType = loadType;
	}	
	
}

