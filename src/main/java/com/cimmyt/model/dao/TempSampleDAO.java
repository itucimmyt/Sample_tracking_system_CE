package com.cimmyt.model.dao;

import java.util.List;

import com.cimmyt.model.bean.TemporalSample;

public interface TempSampleDAO {

	public List<TemporalSample> getListTempSamples(TemporalSample temSample);
	public void saveOrUpdate(TemporalSample temSample);
	public void deleteTempSample (TemporalSample temSample);
	public Integer getTotalRowsByIdResearch(TemporalSample filter);
	public TemporalSample getTempsample(TemporalSample sample);
	public void deleteWithSesson (TemporalSample temSample);
	public void saveWithSesson(TemporalSample temSample);
}
