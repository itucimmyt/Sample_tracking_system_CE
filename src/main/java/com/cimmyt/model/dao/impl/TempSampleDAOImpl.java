package com.cimmyt.model.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.cimmyt.model.bean.TemporalSample;
import com.cimmyt.model.dao.TempSampleDAO;

public class TempSampleDAOImpl extends AbstractDAO<TemporalSample, Integer> implements TempSampleDAO{

	public TempSampleDAOImpl(){
		super(TemporalSample.class);
	}
	public TempSampleDAOImpl(Class<TemporalSample> type) {
		super(type);
	}

	@Override
	public List<TemporalSample> getListTempSamples(TemporalSample temSample) {
		return super.getListByFilter(temSample);
	}
	@Override
	public TemporalSample getTempsample(TemporalSample sample){
		List<TemporalSample>tem = super.getListByFilter(sample);
		if (tem != null && !tem.isEmpty())
			return tem.get(0);
		else
			return null;
	}
	@Override
	protected void buildCriteria(DetachedCriteria criteria,
			TemporalSample filter) {
		if(filter != null)
		if (filter.getResearcher() != null)
			criteria.add(Restrictions.eq("researcher.investigatorid",filter.getResearcher().getInvestigatorid()));
		if(filter.getAcc() != null && !filter.getAcc().trim().equals(""))
			criteria.add(Restrictions.eq("acc",filter.getAcc()));
		if(filter.getGid() != null)
			criteria.add(Restrictions.eq("gid",filter.getGid()));
		if(filter.getPlantNo() != null)
			criteria.add(Restrictions.eq("plantNo",filter.getPlantNo()));
		if(filter.getEntryNo() != null)
			criteria.add(Restrictions.eq("entryNo",filter.getEntryNo()));
		if (filter.getLocation() != null )
			criteria.add(Restrictions.eq("location.locationid", filter.getLocation().getLocationid()));
		if (filter.getSeason() != null)
			criteria.add(Restrictions.eq("season.seasonid", filter.getSeason().getSeasonid()));
		
		
	}

	@Override
	protected void buildCriteria(DetachedCriteria criteria,
			TemporalSample filter, Integer id) {
		if(filter != null)
			if (filter.getResearcher() != null)
				criteria.add(Restrictions.eq("researcher.investigatorid",filter.getResearcher().getInvestigatorid()));
			if(filter.getAcc() != null && filter.getAcc().trim().equals(""))
				criteria.add(Restrictions.eq("acc",filter.getAcc()));
			if(filter.getGid() != null)
				criteria.add(Restrictions.eq("gid",filter.getGid()));
			if(filter.getPlantNo() != null)
				criteria.add(Restrictions.eq("plantNo",filter.getPlantNo()));
			if(filter.getEntryNo() != null)
				criteria.add(Restrictions.eq("entryNo",filter.getEntryNo()));
			if (filter.getLocation() != null )
				criteria.add(Restrictions.eq("location.locationid", filter.getLocation().getLocationid()));
			if (filter.getSeason() != null)
				criteria.add(Restrictions.eq("season.seasonid", filter.getSeason().getSeasonid()));
			
	}

	@Override
	public void saveOrUpdate(TemporalSample temSample) {
		super.updateWithoutSession(temSample);
	}

	public void saveWithSesson(TemporalSample temSample){
		super.update(temSample);
	}
	@Override
	public void deleteTempSample(TemporalSample temSample) {
		super.deleteWithOutSession(temSample);
	}

	@Override
	public void deleteWithSesson (TemporalSample temSample){
		super.delete(temSample);
	}
	public Integer getTotalRowsByIdResearch(TemporalSample filter) {
		return super.getListByFilterTotalLong(filter, filter.getResearcher().getInvestigatorid());
	}
}
