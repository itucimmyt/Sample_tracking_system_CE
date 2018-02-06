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
package com.cimmyt.bean;

import java.util.LinkedList;

/**
 * Class that contains query results to export to CSV file
 * @author root
 */
public class FileQueryBean extends AbstractFileCSVBean<LinkedList<String>>{

	public String getRowsStr (){
		StringBuilder strB = new StringBuilder ();
		for (int index = 0 ; index < getListRows().size(); index ++){
			LinkedList<String> row = getListRows().get(index);			
			for(String s : row){
				strB.append(s.concat(","));
			}
			strB.append("\n");
		}
		return strB.toString();
	}
}

