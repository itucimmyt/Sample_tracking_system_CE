package com.cimmyt.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.cimmyt.model.bean.TemporalSample;
import com.cimmyt.model.dao.TempSampleDAO;
import com.cimmyt.service.ServiceTemporalSample;

public class ServiceTemporalSampleImpl implements ServiceTemporalSample{

	private TempSampleDAO temporalSampleDAO;
	@Override
	public List<TemporalSample> getListSampleByIdResearcher(TemporalSample temp) {
		// TODO Auto-generated method stub
		return temporalSampleDAO.getListTempSamples(temp);
	}

	@Override
	public List<TemporalSample> validateSampleDuplicate(List<TemporalSample> listTem) {
		List<TemporalSample> listRepeat = new ArrayList<TemporalSample>();
		for (TemporalSample tem: listTem){
			TemporalSample temSample = temporalSampleDAO.getTempsample(tem);
			if (temSample != null)
				listRepeat.add(temSample);
			}
		return listRepeat;
	}
	
	@Override
	public void addListTempSample(List<TemporalSample> listTem) {
		if (listTem != null && !listTem.isEmpty()){
			for (TemporalSample tem:listTem){
				temporalSampleDAO.saveOrUpdate(tem);
			}
		}	
	}
	

	public void addListTempSampleWithSesson(List<TemporalSample> listTem) {
		if (listTem != null && !listTem.isEmpty()){
			for (TemporalSample tem:listTem){
				temporalSampleDAO.saveWithSesson(tem);
			}
		}	
		
	}
	@Override
	public boolean areThereAnyTempSamplesByIdResercher(TemporalSample filter) {
		Integer value = temporalSampleDAO.getTotalRowsByIdResearch(filter);
		if (value.intValue() > 0)
			return true;
		else return false;
	}

	@Override
	public void deleteListTemSample(List<TemporalSample> listTem){
		if (listTem != null && !listTem.isEmpty()){
			for (TemporalSample tem:listTem){
				temporalSampleDAO.deleteTempSample(tem);
			}
		}
	}
	public void setTemporalSampleDAO(TempSampleDAO temporalSampleDAO) {
		this.temporalSampleDAO = temporalSampleDAO;
	}
}
