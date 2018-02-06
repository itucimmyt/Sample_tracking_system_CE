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
package com.cimmyt.model.dao;

import java.util.List;

import com.cimmyt.model.bean.Log;
public interface LogDAO {

	public void saveOrUpdate (Log model);
	public void delete (Log model);
	public Log getLog(Log model);
	public List<Log> getListLog (Log model ,int firstResult, int maxResults
			, int sortColumn, boolean ascending, String dateInit, String dateEnd,int idRoll);
	public Integer getTotalRowsByFilter(Log filter, int idRoll,String initDate, String endDate);
}
