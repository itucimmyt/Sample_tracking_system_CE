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
package com.cimmyt.model.bean;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.cimmyt.dnast.dto.AuthUserBean;

/**
 * 
 * @author 
 */
@Entity
@Table(name = "st_log")
public class Log implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idLog;
	private AuthUserBean stUserVersion;
	private LogTypeOperation logTyperOperation;
	private Date operationDate;
	private String description;
	private LogTypeEntity logTypeEntity;
	private Integer idRowEntity;
	
	public Log(){	
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "idst_log", unique = true, nullable = false)
	public Integer getIdLog() {
		return idLog;
	}

	public void setIdLog(Integer idLog) {
		this.idLog = idLog;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_version_id", nullable = false)
	public AuthUserBean getStUserVersion() {
		return stUserVersion;
	}

	public void setStUserVersion(AuthUserBean stUserVersion) {
		this.stUserVersion = stUserVersion;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idst_log_type_operation", nullable = false)
	public LogTypeOperation getLogTyperOperation() {
		return logTyperOperation;
	}

	public void setLogTyperOperation(LogTypeOperation logTyperOperation) {
		this.logTyperOperation = logTyperOperation;
	}

	@Column(name = "description", nullable = false, length = 250)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "operation_date", nullable = false)
	public Date getOperationDate() {
		return operationDate;
	}

	public void setOperationDate(Date operationDate) {
		this.operationDate = operationDate;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idst_log_type_entity", nullable = false)
	public LogTypeEntity getLogTypeEntity() {
		return logTypeEntity;
	}

	public void setLogTypeEntity(LogTypeEntity logTypeEntity) {
		this.logTypeEntity = logTypeEntity;
	}

	@Column(name = "id_row_entity", nullable = false)
	public Integer getIdRowEntity() {
		return idRowEntity;
	}

	public void setIdRowEntity(Integer idRowEntity) {
		this.idRowEntity = idRowEntity;
	}

}
