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
package com.cimmyt.loadStudy;

import java.util.List;
import java.util.Map;

import com.cimmyt.bean.CSVReportGenericBean;
import com.cimmyt.bean.RowControlFile;
import com.cimmyt.model.bean.LabStudy;
import com.cimmyt.model.bean.Shipment;
import com.cimmyt.study.PlateContentList;
import com.cimmyt.utils.PropertyHelper;

/**
 * Interface make to management of files of control and load the same in the plate
 * @author CIMMYT
 */
public interface ServiceControlFile {

	public List<RowControlFile> getControlSamples(PropertyHelper pro,PlateContentList plateContent, Map<String, Object> mapCellSample, String plateName);
	public List<String> getHeader(PropertyHelper pro);
}
