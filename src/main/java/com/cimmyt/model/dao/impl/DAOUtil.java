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
//$ Id: DAOUtil.java, Jan 31, 2011 TMSANCHEZ $
package com.cimmyt.model.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * TODO add class documentation here
 * @version $Revision$, $Date$
 */
public class DAOUtil {
	/**
	 * Checks if connection to database is Success 
	 * @param url
	 * @param user
	 * @param password
	 * @return true y connection is success or false if not
	 */
    public static boolean connectionSuccess(String url, String user, String password) {
    	boolean connected = false;
    	
    	try {
    		Class.forName("com.mysql.jdbc.Driver");
    		Connection cnn = DriverManager.getConnection(url,user,password);
    		cnn.close();    		
    		connected = true;
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	
    	return connected;
    }
    
    /**
     * 
     * @param url
     * @param user
     * @param password
     * @return
     */
    public static boolean tablesOk(String url, String user, String password) {
    	boolean tablesOk = false;
    	try {
    		Class.forName("com.mysql.jdbc.Driver");
    		Connection cnn = DriverManager.getConnection(url,user,password);
    		PreparedStatement statement = cnn.prepareStatement("SELECT * FROM st_company where 1 = 2 ");
    		statement.execute();
    		cnn.close();    		
    		tablesOk = true;
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return tablesOk;
    }
}
