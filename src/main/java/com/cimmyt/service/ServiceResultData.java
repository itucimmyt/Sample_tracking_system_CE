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

import com.cimmyt.bean.FileSampleCSVBean;
import com.cimmyt.exception.ResultDataException;
import com.cimmyt.model.bean.LabStudy;
import com.cimmyt.utils.PropertyHelper;

public interface ServiceResultData {

	public void validateSampleID(FileSampleCSVBean fileSampleCSV, PropertyHelper pro
			, Integer numberOfSamples, LabStudy beanStudy) throws ResultDataException;
}
