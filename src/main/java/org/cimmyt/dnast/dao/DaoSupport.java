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
package org.cimmyt.dnast.dao;

import java.util.List;

/**
 * Based on GenericDAO created by:
 * 
 * @author <a href="mailto:t.m.sanchez@cgiar.org">tmsanchez</a>
 * @author last edited by: $Author: tmsanchez $
 * 
 * @version $Revision$, $Date: 2010-08-11 17:38:20 -0500 (Wed, 11 Aug 2010) $
 */
public interface DaoSupport<T> {

	/**
	 * Retrieve an object that was previously persisted to the database using
	 * the indicated id as primary key
	 */
	T getBeanById(Integer id);
	List<T> getListByBean(final T filter, Integer pageNum);
}
