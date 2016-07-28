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
package com.cimmyt.model.dao.impl;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import com.cimmyt.model.dao.MyQLProcedureDAO;

public class MySQLProcedureDAOImpl implements MyQLProcedureDAO{

	private DataSource  dataSource;
	private JdbcTemplate jdbcTemplate;  
	 
	/**
	 * Method that invokes the call a storage procedure to delete study
	 * @param Id Study identifier of study that will be delete
	 */
	public void exceuteDeleteStudySP(int idStudy) {
		jdbcTemplate = new JdbcTemplate(dataSource);
		Storaged storagedProcedure = new Storaged(jdbcTemplate.getDataSource());
		storagedProcedure.execute(idStudy);
		
	}
	/**
	 * Class load to management of store procedure
	 * @author root
	 *
	 */
	private class Storaged extends StoredProcedure
	{
		public Storaged(DataSource datasource ){
			super (jdbcTemplate.getDataSource() ,"sp_delete_study" );
			declareParameter( new SqlParameter( "id", Types.INTEGER) );
			compile();
		}
		public void execute (int id){
			super.execute(id);
		}
	}
	/**
	 * Bean load of context 
	 * @param dataSource
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}



}
