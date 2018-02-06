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

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.HibernateException;
import org.hibernate.impl.SessionImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.cimmyt.model.dao.MyQLProcedureDAO;

public class MySQLProcedureDAOImpl implements MyQLProcedureDAO{

	private DataSource  dataSource;
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	private HibernateTemplate hibernateTemplate;
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
	 * Method that invokes the call a storage procedure to delete study
	 * @param Id Study identifier of study that will be delete
	 */
	public void exceuteInsertTemplateStudySP(int idStudy, int idTemplate) {
		jdbcTemplate = new JdbcTemplate(dataSource);
		StoragedInsertTemplateSamples storaged = new StoragedInsertTemplateSamples(jdbcTemplate.getDataSource());
		storaged.execute (idStudy,idTemplate);
		
	}

	/**
	 * Class load to management of store procedure
	 * @author root
	 *
	 */
	private class StoragedInsertTemplateSamples extends StoredProcedure
	{
		public StoragedInsertTemplateSamples(DataSource datasource ){
			super (jdbcTemplate.getDataSource() ,"sp_study_template_add" );
			declareParameter( new SqlParameter( "id_study", Types.INTEGER) );
			declareParameter( new SqlParameter( "id_template_params", Types.INTEGER) );
			compile();
		}
		public void execute (int idStudy, int idTemplate){
			super.execute(idStudy, idTemplate );
		}
	}

	/**
	 * Get count of parameter from template param ID
	 * */
	public int getExecuteCountParamData (int idTemplateParam){
		jdbcTemplate = new JdbcTemplate(dataSource);
		SotorageCountParamData store = new SotorageCountParamData();
		return store.execute(idTemplateParam);
	}

	/**
	 * Class to create store procedure and call it
	 * */
	private class SotorageCountParamData extends StoredProcedure
	{
		public SotorageCountParamData (){
			super(jdbcTemplate.getDataSource(), "sp_count_params_data");
			declareParameter(new SqlParameter ("id_template_paramas", Types.INTEGER));
			declareParameter (new SqlOutParameter("total", Types.INTEGER));
			compile();
		}
		/**
		 * Execute store procedure total
		 * @return total of records
		 * */
		public int execute (int idTemplateParamas ){
			Map<String, Object> map = super.execute( idTemplateParamas );
			if (map != null && map.size() > 0){
				return (Integer) map.get("total");
			}else
				return 0;
		}
		
	}

	/**
	 * Execute delete fields from template
	 * @param idTemplateParams
	 * @param studytemplateid
	 * */
	public void executeDeleteFieldTemplate (int idTemplateParams, int studytemplateid){
		jdbcTemplate = new JdbcTemplate(dataSource);
		StorageDeleteFieldTemplate store = new StorageDeleteFieldTemplate();
		store.execute( idTemplateParams,  studytemplateid);
	}

	/**
	 * Class to call delete storage template 
	 * */
	private class StorageDeleteFieldTemplate extends StoredProcedure{
		 
		public StorageDeleteFieldTemplate (){
			super(jdbcTemplate.getDataSource(), "sp_delete_fields_template");
			declareParameter(new SqlParameter ("id_template_params", Types.INTEGER));
			declareParameter(new SqlParameter ("studytemplateid", Types.INTEGER));
			compile();
		}

		/**
		 * Call storage for delete
		 * @param idTemplateParams
		 * @param studytemplateid
		 * */
		public void execute (int idTemplateParams, int studytemplateid){
			super.execute( idTemplateParams,  studytemplateid);
		}
		
	}

	/**
	 * Execute add fields in the template result
	 * @param idTemplateParams
	 * @param studytemplateid
	 * */
	public void executespAddFieldsTemplateResult (int idTemplateParams, int studytemplateid){

		try {
			SessionImpl session = (SessionImpl)hibernateTemplate.getSessionFactory().
					openSession();
			CallableStatement callableStatement = null;
			callableStatement = session.connection().prepareCall("Call sp_add_fields_template_result(?,?)");
			callableStatement.setInt(1, idTemplateParams);
			callableStatement.setInt(2, studytemplateid );
			 callableStatement.execute();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		/*
		jdbcTemplate = new JdbcTemplate(dataSource);
		StorageAddFieldTemplateResult stored = new StorageAddFieldTemplateResult();
		stored.execute(idTemplateParams, studytemplateid);
		*/
	}

	/**
	 * Class to call stored procedure to add new fields to table result
	 * */
	private class StorageAddFieldTemplateResult extends StoredProcedure{
		
		public StorageAddFieldTemplateResult(){
			super(jdbcTemplate.getDataSource(), "sp_add_fields_template_result");
			declareParameter(new SqlParameter ("id_template_params", Types.INTEGER));
			declareParameter(new SqlParameter ("studytemplateid", Types.INTEGER));
			compile();
		}

		/**
		 * Call storage for delete
		 * @param idTemplateParams
		 * @param studytemplateid
		 * */
		public void execute (int idTemplateParams, int studytemplateid){
			super.execute( idTemplateParams,  studytemplateid);
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
