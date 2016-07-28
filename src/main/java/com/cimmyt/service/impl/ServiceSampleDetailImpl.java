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
package com.cimmyt.service.impl;

import java.util.List;

import org.cimmyt.dnast.dto.DsSearchParam;

import com.cimmyt.model.bean.SampleDetail;
import com.cimmyt.model.bean.ShipmentSet;
import com.cimmyt.model.dao.SampleDetailDAO;
import com.cimmyt.service.ServiceSampleDetail;

public class ServiceSampleDetailImpl implements ServiceSampleDetail{

	private SampleDetailDAO sampleDetailDAO;
	@Override
	public SampleDetail getSampleDetailByLabIdAndEntry(Integer labStudyId,
			Integer entryNo) {
		return sampleDetailDAO.getSampleDetailByLabIdAndEntry(labStudyId,entryNo);
	}

	@Override
	public SampleDetail getSampleDetailByStudysampleid(Integer studysampleid) {
		return sampleDetailDAO.getSampleDetailByStudysampleid(studysampleid);
	}

	@Override
	public SampleDetail getSampleDetailWithResults(Integer studySampleID) {
		return sampleDetailDAO.getSampleDetailWithResults(studySampleID);
	}

	@Override
	public List<SampleDetail> getSamplesByLocation(Integer locationId, Integer firstResult, Integer maxResults
			, String sortColumn, boolean ascending) {
		return sampleDetailDAO.getSamplesByLocation(locationId, firstResult, maxResults, sortColumn, ascending);
	}

	public Long getCountSamplesByLocation(Integer locationId) {
		return sampleDetailDAO.getCountSamplesByLocation(locationId);
	}
	@Override
	public List<SampleDetail> getSamplesByPlate(String plateName) {
		return sampleDetailDAO.getSamplesByPlate(plateName);
	}

	@Override
	public List<SampleDetail> getSamplesByStudy(Integer labStudyId,
			List<Integer> excludedSamples) {
		return sampleDetailDAO.getSamplesByStudy(labStudyId, excludedSamples);
	}

	@Override
	public List<SampleDetail> getSamplesByStudyUsedInStorageLocation(
			Integer labStudyId, List<Integer> excludedSamples, int firstResult,	int maxResults) {
		return sampleDetailDAO.getSamplesByStudyUsedInStorageLocation(labStudyId, excludedSamples, firstResult, maxResults);
	}

	public Integer getSamplesByStudyUsedInStorageLocationTotal(
			Integer labStudyId, List<Integer> excludedSamples) {
		return sampleDetailDAO.getSamplesByStudyUsedInStorageLocationTotal(	labStudyId, excludedSamples);
	}

	@Override
	public List<SampleDetail> getSamplesDetailsList(SampleDetail filtersample) {
		return sampleDetailDAO.getSamplesDetailsList(filtersample);
	}

	@Override
	public List<String> getPlatesNamesFromLabStudy(Integer labstudyID) {
		return sampleDetailDAO.getPlatesNamesFromLabStudy(labstudyID);
	}

	@Override
	public List<String> getAllPlatesNames() {
		return sampleDetailDAO.getAllPlatesNames();
	}

	@Override
	public List<String> getDistinctsStringsFromSampleDetail(String field) {
		return sampleDetailDAO.getDistinctsStringsFromSampleDetail(field);
	}

	@Override
	public List<Integer> getDistinctsIntegersFromSampleDetail(String field) {
		return sampleDetailDAO.getDistinctsIntegersFromSampleDetail(field);
	}

	@Override
	public List<SampleDetail> getSampleDetailWithCondition(String queryString) {
		return sampleDetailDAO.getSampleDetailWithCondition(queryString);
	}

	public void setSampleDetailDAO(SampleDetailDAO sampleDetailDAO) {
		this.sampleDetailDAO = sampleDetailDAO;
	}

	public Integer getTotalRowsCustomQuery(List<DsSearchParam> params) {
		return sampleDetailDAO.getTotalRowsCustomQuery(params);
	}

	public List<SampleDetail> getCustomQuery(List<DsSearchParam> params,
			int firstResult, int maxResults) {
		return sampleDetailDAO.getCustomQuery(params, firstResult, maxResults);
	}

	public void updateListStatus(List<Integer> sampleDetailIds, char newStatus) {
		sampleDetailDAO.updateListStatus(sampleDetailIds, newStatus);
	}
	public List<SampleDetail> getSamplesByShipmentSet(ShipmentSet shipmentSet){
		return sampleDetailDAO.getSamplesByShipmentSet(shipmentSet);
	}

	public List<String> getPlatesNotInShipmentSet(Integer idLabStudy, List<String> excludingPlates) {
		return sampleDetailDAO.getPlatesNotInShipmentSet(idLabStudy, excludingPlates);
	}

	public List<String> getPlatesInShipmentSet(ShipmentSet shipSet) {
		return sampleDetailDAO.getPlatesInShipmentSet(shipSet);
	}

}
