package com.cimmyt.rest.dto;

import java.util.ArrayList;
import java.util.List;

import com.cimmyt.bean.InvestigatorBean;
import com.cimmyt.bean.LocationBean;
import com.cimmyt.bean.SeasonBean;
import com.cimmyt.model.bean.Organism;


public class CatalogDTO {

	private List<InvestigatorBean>  investigatorBeanList = new ArrayList<InvestigatorBean>();

	private List<Organism> listOrganism = new ArrayList<Organism>();
	
	private List<LocationBean> listLocation = new ArrayList<LocationBean>();

	private List<SeasonBean> listSeasonBean = new ArrayList<SeasonBean>();

	public List<InvestigatorBean> getInvestigatorBeanList() {
		return investigatorBeanList;
	}

	public void setInvestigatorBeanList(List<InvestigatorBean> investigatorBeanList) {
		this.investigatorBeanList = investigatorBeanList;
	}

	public List<Organism> getListOrganism() {
		return listOrganism;
	}

	public void setListOrganism(List<Organism> listOrganism) {
		this.listOrganism = listOrganism;
	}

	public List<LocationBean> getListLocation() {
		return listLocation;
	}

	public void setListLocation(List<LocationBean> listLocation) {
		this.listLocation = listLocation;
	}

	public List<SeasonBean> getListSeasonBean() {
		return listSeasonBean;
	}

	public void setListSeasonBean(List<SeasonBean> listSeasonBean) {
		this.listSeasonBean = listSeasonBean;
	}
	
}
