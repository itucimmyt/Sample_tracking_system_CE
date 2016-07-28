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

/**
 * Class to management of file with samples 
 * @author root
 */
public class FileControlBean extends AbstractFileCSVBean<RowControlFile>{

	public String getRowsStr (){
		StringBuilder strB = new StringBuilder ();
		for (int index = 0 ; index < getListRows().size(); index ++){
			RowControlFile row = getListRows().get(index);
			strB.append(row.getControlType()+",");
			strB.append(row.getPlateName()+",");
			strB.append(row.getRow()+",");
			strB.append(row.getColumn()+",");
			strB.append(row.getGid()+",");
			strB.append(row.getAcc()+",");
			strB.append(row.getPlateNo()+",");
			strB.append(row.getSpecie()+",");
			strB.append(row.getComment()+",");
			strB.append(row.getEntryNo()+",");
			strB.append(row.getLocation()+",");
			strB.append(row.getSeason()+",");
			if ((index+1) != getListRows().size())
			strB.append("\n");
		}
		return strB.toString();
	}
}

