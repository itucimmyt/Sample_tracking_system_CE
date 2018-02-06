package com.cimmyt.model.dao;

import java.util.List;

import com.cimmyt.model.bean.Purpose;

public interface PurposeDAO {

	public List<Purpose> getListPurpose (Purpose filter, boolean areAll);
	public void saveOrUpdate(Purpose filter);
	
}
