package com.cimmyt.model.dao;

import java.util.List;

import com.cimmyt.model.bean.Register;

public interface RegisterDAO {

	public List<Register> getListRegister(Register filter);
	public void saveOrUpdate(Register register);
	public void delete(Register register);
	public Register getRegister(Register register);
}
