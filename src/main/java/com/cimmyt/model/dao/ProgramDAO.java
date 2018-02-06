package com.cimmyt.model.dao;

import java.util.List;

import com.cimmyt.model.bean.Program;

public interface ProgramDAO {

	public List<Program> getListProgram(Program filter, boolean areAll);
	public void saveOrUpdate (Program filter );
}
