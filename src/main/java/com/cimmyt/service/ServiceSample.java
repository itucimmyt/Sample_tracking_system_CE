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

import com.cimmyt.model.bean.SampleID;

public interface ServiceSample {

	public Integer searchSampleIDProjectInSt_SampleID(final Integer idproject, 
			final Integer gid, final Integer nplanta, final Integer locationid, 
			final Integer seasonid);
	public Integer getLastSampleIDFromProject(final Integer projectID);
	public Integer getLastNPlantFromGIDinSampleID(final Integer projectID, final Integer GID,
			final Integer locationid, final Integer seasonid);
	public boolean searchSampleIDforLocation(final Integer locationid);
	public boolean searchSampleIDforSeason(final Integer seasonid );
	public void saveOrUpdateSample(SampleID sampleid);
}
