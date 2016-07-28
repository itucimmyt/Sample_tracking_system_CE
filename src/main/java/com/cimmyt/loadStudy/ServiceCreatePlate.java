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

import org.zkoss.zul.Grid;
import org.zkoss.zul.Tabpanel;

import com.cimmyt.bean.ControlPlateBean;
import com.cimmyt.model.bean.LabStudy;
import com.cimmyt.study.PlateContentList;
import com.cimmyt.utils.PropertyHelper;

public interface ServiceCreatePlate {

	public Grid addLoadGridByPlate(int numColum, PlateContentList plateContent, 
			Map<String, Object> mapCellSample, PropertyHelper pro, LabStudy _beanLabStudy, boolean isNewStudy,
			Map<String, Object> mapSampleEdit,String plateName, Tabpanel tabPanelj, List <String> listSampleRepeat
			, Map<String, Object> mapAssignSample, Map<String, Integer> mapSizePlate, ControlPlateBean controlPlateBean);
}
