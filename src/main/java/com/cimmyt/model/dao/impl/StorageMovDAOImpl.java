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
//
// Created: April 2009
//
// Copyright 2009 International Rice Research Institute (IRRI) and 
// Centro Internacional de Mejoramiento de Maiz y Trigo (CIMMYT)
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//
//$ Id: StorageMovDAO.java, Aug 19, 2010 TMSANCHEZ $
package com.cimmyt.model.dao.impl;

import org.hibernate.criterion.DetachedCriteria;

import com.cimmyt.model.bean.StorageMov;
import com.cimmyt.model.dao.StorageMovDAO;

/**
 * TODO add class documentation here
 * @version $Revision$, $Date$
 */
public class StorageMovDAOImpl extends AbstractDAO<StorageMov, Integer> implements StorageMovDAO{
	public StorageMovDAOImpl() {
		super(StorageMov.class);
	}

	@Override
	protected void buildCriteria(DetachedCriteria criteria, StorageMov filter) {
		// TODO Auto-generated method stub
		
	}

	public StorageMov create(StorageMov storageMov){
		return super.create(storageMov);
	}

	@Override
	protected void buildCriteria(DetachedCriteria criteria, StorageMov filter,
			Integer id) {
		// TODO Auto-generated method stub
		
	}
}
