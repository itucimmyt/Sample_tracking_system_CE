package com.cimmyt.service;

import java.util.List;

import com.cimmyt.model.bean.TemporalSample;

public interface ServiceTemporalSample {

	public List<TemporalSample> getListSampleByIdResearcher(TemporalSample temp);
	public List<TemporalSample> validateSampleDuplicate(List<TemporalSample> listTem);
	public void addListTempSample(List<TemporalSample> listTem);
	public void deleteListTemSample(List<TemporalSample> listTem);
	public boolean areThereAnyTempSamplesByIdResercher(TemporalSample filter);
	public void addListTempSampleWithSesson(List<TemporalSample> listTem);
}
