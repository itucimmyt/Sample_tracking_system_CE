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
package com.cimmyt.reports;

import java.util.List;
import java.util.SortedMap;

import com.cimmyt.bean.CSVReportGenericBean;
import com.cimmyt.bean.ResultDataExportDataBean;
import com.cimmyt.bean.StudyLabReportBean;
import com.cimmyt.model.bean.LabStudy;
import com.cimmyt.model.bean.ResultsPreferencesDetail;
import com.cimmyt.model.bean.StudyTemplateParams;
import com.cimmyt.utils.PropertyHelper;

public interface ServiceReportLaboratory {

	public byte [] getBytesReportLaboratoryPlate (StudyLabReportBean beanReport, List<Object> listFieldsObjets, List<StudyTemplateParams> listStudyTemParams);
	public byte [] getReportResultData (ResultDataExportDataBean resultDataExport, PropertyHelper pro
			, SortedMap<Integer, ResultsPreferencesDetail> sortedMap);
	public CSVReportGenericBean getReportCSVReuslData (ResultDataExportDataBean resultDataExport, PropertyHelper pro
			, SortedMap<Integer, ResultsPreferencesDetail> sortedMap);
}
