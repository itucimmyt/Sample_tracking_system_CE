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
package com.cimmyt.service;

import java.util.List;

import org.cimmyt.dnast.dto.DsSearchParam;

import com.cimmyt.model.bean.SampleDetail;
import com.cimmyt.model.bean.ShipmentSet;

public interface ServiceSampleDetail {
	public SampleDetail getSampleDetailByLabIdAndEntry(
			final Integer labStudyId, final Integer entryNo);
	public SampleDetail getSampleDetailByStudysampleid(
			final Integer studysampleid);
	public SampleDetail getSampleDetailWithResults(final Integer studySampleID);
	public List<SampleDetail> getSamplesByLocation(Integer locationId, Integer firstResult, Integer maxResults
			, String sortColumn, boolean ascending);
	public Long getCountSamplesByLocation(final Integer locationId);
	public List<SampleDetail> getSamplesByPlate(final String plateName);
	public List<SampleDetail> getSamplesByStudy(final Integer labStudyId,
			final List<Integer> excludedSamples);
	public List<SampleDetail> getSamplesByStudyUsedInStorageLocation(final Integer labStudyId,
			final List<Integer> excludedSamples, int firstResult, int maxResults);
	public Integer getSamplesByStudyUsedInStorageLocationTotal(final Integer labStudyId,
			final List<Integer> excludedSamples);
	public List<SampleDetail> getSamplesDetailsList(final SampleDetail filtersample);
	public List<String> getPlatesNamesFromLabStudy(final Integer labstudyID);
	public List<String> getAllPlatesNames();
	public List<String> getDistinctsStringsFromSampleDetail(String field);
	public List<Integer> getDistinctsIntegersFromSampleDetail(String field);
	public List<SampleDetail> getSampleDetailWithCondition (final String queryString);
	public Integer getTotalRowsCustomQuery(List<DsSearchParam> params);
	public List<SampleDetail> getCustomQuery(List<DsSearchParam> params, int firstResult, int maxResults);
	public void updateListStatus(List<Integer> sampleDetailIds, char newStatus);
	public List<SampleDetail> getSamplesByShipmentSet(ShipmentSet shipmentSet);
	public List<String> getPlatesNotInShipmentSet(Integer idLabStudy,  List<String> excludingPlates);
	public List<String> getPlatesInShipmentSet(ShipmentSet shipSet);
}
