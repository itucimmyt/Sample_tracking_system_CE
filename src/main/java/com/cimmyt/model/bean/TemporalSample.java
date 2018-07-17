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
@Table(name = "st_temp_samples")
@NamedQueries( { @NamedQuery(name = "TemporalSample.findAll", query = "SELECT i FROM TemporalSample i") })
public class TemporalSample implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id_temp_sample")
	private Integer idTemSample;
	@Basic(optional = false)
	@Column(name = "gid")
	private Integer gid;
	@Basic(optional = false)
	@Column(name = "acc")
	private String acc;
	@Basic(optional = false)
	@Column(name = "plant_no")
	private Integer plantNo;
	@Basic(optional = false)
	@Column(name = "comments")
	private String comments;
	@Basic(optional = false)
	@Column(name = "entry_no")
	private Integer entryNo;
	@JoinColumn (name = "id_specie", referencedColumnName = "organismid" )
	@ManyToOne(optional = true)
	private Organism specie;
	@JoinColumn (name = "id_location", referencedColumnName = "locationid" )
	@ManyToOne(optional = true)
	private LocationCatalog location;
	@JoinColumn (name = "id_season", referencedColumnName = "seasonid" )
	@ManyToOne(optional = true)
	private Season season;
	@JoinColumn (name = "id_researcher", referencedColumnName = "investigatorid" )
	@ManyToOne(optional = true)
	private Investigator researcher;

	public TemporalSample() {
		
	}
	
	/*
	public TemporalSample(){
		acc = new String ();
		comments = new String();
	}*/

	public TemporalSample (Integer idTempSample){
	this.idTemSample = idTempSample;	
	}

	public Integer getIdTemSample() {
		return idTemSample;
	}
	public void setIdTemSample(Integer idTemSample) {
		this.idTemSample = idTemSample;
	}
	public Integer getGid() {
		return gid;
	}
	public void setGid(Integer gid) {
		this.gid = gid;
	}
	public String getAcc() {
		return acc;
	}
	public void setAcc(String acc) {
		this.acc = acc;
	}
	public Integer getPlantNo() {
		return plantNo;
	}
	public void setPlantNo(Integer plantNo) {
		this.plantNo = plantNo;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Integer getEntryNo() {
		return entryNo;
	}
	public void setEntryNo(Integer entryNo) {
		this.entryNo = entryNo;
	}
	public Organism getSpecie() {
		return specie;
	}
	public void setSpecie(Organism specie) {
		this.specie = specie;
	}
	public LocationCatalog getLocation() {
		return location;
	}
	public void setLocation(LocationCatalog location) {
		this.location = location;
	}
	public Season getSeason() {
		return season;
	}
	public void setSeason(Season season) {
		this.season = season;
	}
	public Investigator getResearcher() {
		return researcher;
	}
	public void setResearcher(Investigator researcher) {
		this.researcher = researcher;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof TemporalSample)) {
			return false;
		}
		TemporalSample other = (TemporalSample) object;
		if ((this.idTemSample == null && other.idTemSample != null)
				|| (this.idTemSample != null && !this.idTemSample
						.equals(other.idTemSample))) {
			return false;
		}
		return true;
	}
	@Override
	public String toString(){
		return "id :"+idTemSample+" "+gid.toString()+" comments :"+comments;
	}
}
