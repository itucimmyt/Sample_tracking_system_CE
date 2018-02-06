package com.cimmyt.service;

import java.util.List;

import com.cimmyt.model.bean.Register;
import com.cimmyt.rest.client.Result;

public interface ServiceRegister {

	public List<Register> getListRegister(Register model);
	public void add (Register model, int idCrop);
	public void delete (Register model);
	public Register getRegister(Register model);
	public List<Result> getContries();
	public boolean validateEmailAndUser(Register model);
}
