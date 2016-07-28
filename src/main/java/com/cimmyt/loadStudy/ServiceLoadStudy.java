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

import org.zkoss.zk.ui.Desktop;

import com.cimmyt.bean.ItemSampleBean;
import com.cimmyt.model.bean.LabStudy;
import com.cimmyt.model.bean.SampleDetail;
import com.cimmyt.model.bean.TemporalSample;

public interface ServiceLoadStudy {

	/**
	 * Method that management the load of samples in the front
	 * @param mapCellSample list of cells and plate
	 * @param typeLoad this variable indicate if the load is in row or column
	 * @param plateList in this variable contain the description of plate  
	 */
	 
	public void loadStudyInGrid(Map<String, Object> mapCellSample, int typeLoad, Desktop desktop, Map <String, ItemSampleBean> mapItemSampleBean,
			LabStudy beanLabStudy, boolean isEdit, Map <Integer , SampleDetail> mapSamplesDelete, Map <Integer , SampleDetail> _mapSamplesEdit,List<TemporalSample> listTempsample);
}
