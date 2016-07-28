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
package com.cimmyt.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.cimmyt.model.bean.LabStudy;
import com.cimmyt.model.bean.SampleDetail;

public class LabStudyBean {

	private Integer labstudyid;
	private String title;
	private String objective;
	private Date startdate;
	private Date enddate;
	private String platetype;
	private Integer platesize;
	private Integer numindiv;
	private Integer numofplates;
	private String  prefix;
	private Integer numcontrols;
	private Collection<SampleDetailBean> sampleDetailCollectionBean;
	private StudyTemplateBean studytemplateidBean;
	private ProjectBean projectBean;
	private InvestigatorBean investigatoridBean;
	private OrganismBean organismidBean;
	private TissueBean tissueBean;
	private LocationBean locationBean;
	private SeasonBean seasonBean;
	
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

	public Collection<SampleDetailBean> getSampleDetailCollectionBean() {
		return sampleDetailCollectionBean;
	}

	public void setSampleDetailCollectionBean(
			Collection<SampleDetailBean> sampleDetailCollectionBean) {
		this.sampleDetailCollectionBean = sampleDetailCollectionBean;
	}

	public StudyTemplateBean getStudytemplateidBean() {
		return studytemplateidBean;
	}

	public void setStudytemplateidBean(StudyTemplateBean studytemplateidBean) {
		this.studytemplateidBean = studytemplateidBean;
	}

	public ProjectBean getProjectBean() {
		return projectBean;
	}

	public void setProjectBean(ProjectBean projectBean) {
		this.projectBean = projectBean;
	}

	public InvestigatorBean getInvestigatoridBean() {
		return investigatoridBean;
	}

	public void setInvestigatoridBean(InvestigatorBean investigatoridBean) {
		this.investigatoridBean = investigatoridBean;
	}

	public OrganismBean getOrganismidBean() {
		return organismidBean;
	}

	public void setOrganismidBean(OrganismBean organismidBean) {
		this.organismidBean = organismidBean;
	}

	public TissueBean getTissueBean() {
		return tissueBean;
	}

	public void setTissueBean(TissueBean tissueBean) {
		this.tissueBean = tissueBean;
	}

	public LocationBean getLocationBean() {
		return locationBean;
	}

	public void setLocationBean(LocationBean locationBean) {
		this.locationBean = locationBean;
	}

	public SeasonBean getSeasonBean() {
		return seasonBean;
	}

	public void setSeasonBean(SeasonBean seasonBean) {
		this.seasonBean = seasonBean;
	}
	
	public LabStudyBean(){

	}

	public LabStudyBean(LabStudy bean){
		this.enddate = bean.getEnddate();
		this.investigatoridBean = new InvestigatorBean(bean.getInvestigatorid());
		this.labstudyid= bean.getLabstudyid();
		this.locationBean = new LocationBean (bean.getLocation());
		this.numcontrols = bean.getNumcontrols();
		this.numindiv = bean.getNumindiv();
		this.numofplates = bean.getNumofplates();
		this.objective = bean.getObjective();
		this.organismidBean = new OrganismBean (bean.getOrganismid());
		this.platesize = bean.getPlatesize();
		this.platetype = bean.getPlatetype();
		this.prefix = bean.getPrefix();
		this.projectBean = new ProjectBean (bean.getProject());
		
		Collection<SampleDetailBean> sampleDetailCollectionBean = new ArrayList<SampleDetailBean>();
		for (SampleDetail obj : bean.getSampleDetailCollection()){
			sampleDetailCollectionBean.add(new SampleDetailBean(obj));
		}
		this.sampleDetailCollectionBean = sampleDetailCollectionBean;
		this.seasonBean = new SeasonBean (bean.getSeason());
		this.startdate = bean.getStartdate();
		this.studytemplateidBean = new StudyTemplateBean(bean.getStudytemplateid());
		this.tissueBean = new TissueBean (bean.getTissue());
		this.title = bean.getTitle();								
	}

	public LabStudy getLabStudy(LabStudyBean bean){
		LabStudy obj = new LabStudy();
		obj.setEnddate(bean.getEnddate());
		Set<SampleDetail> sampleDetailCollection = new HashSet<SampleDetail>();
		for (SampleDetailBean objL : bean.getSampleDetailCollectionBean()){
			sampleDetailCollection.add(objL.getSampleDetail(objL));
		}
		obj.setImsSampleDetailCollection(sampleDetailCollection);
		obj.setInvestigatorid(bean.getInvestigatoridBean().
				getInvestigator(bean.getInvestigatoridBean()));
		obj.setLabstudyid(bean.getLabstudyid());
		obj.setLocation(bean.getLocationBean().
				getLocationBean(bean.getLocationBean()));
		obj.setNumcontrols(bean.getNumcontrols());
		obj.setNumindiv(bean.getNumindiv());
		obj.setNumofplates(bean.getNumofplates());
		obj.setObjective(bean.getObjective());
		obj.setOrganismid(bean.getOrganismidBean()
				.getOrganism(bean.getOrganismidBean()));
		obj.setPlatesize(bean.getPlatesize());
		obj.setPlatetype(bean.getPlatetype());
		obj.setPrefix(bean.getPrefix());
		obj.setProject(bean.getProjectBean()
				.getProject(bean.getProjectBean()));
		obj.setSeason(bean.getSeasonBean()
				.getSeason(bean.getSeasonBean()));
		obj.setStartdate(bean.getStartdate());
		obj.setStudytemplateid(bean.getStudytemplateidBean()
				.getStudyTemplate(bean.getStudytemplateidBean()));
		obj.setTissue(bean.getTissueBean()
				.getTissue(bean.getTissueBean()));
		obj.setTitle(bean.getTitle());
		
		return obj;
	}
}
