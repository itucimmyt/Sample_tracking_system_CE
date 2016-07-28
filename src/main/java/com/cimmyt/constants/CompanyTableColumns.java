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
//$ Id: CompanyTableColumns.java, May 26, 2010 tmsanchez $
package com.cimmyt.constants;

/**
 * TODO add class documentation here
 */
public enum CompanyTableColumns {
	COMPANY_ID(0,"ID",100),
	COMPANY_NAME(1,"Name",250),
	EMAIL(2,"EMail",200),	
	ADDRESS(3,"Address",250),
	CONTACT_NAME(4,"Contact Name",150),
	PHONE(5,"Phone Number",100),
	STORAGE_LOCATIONS(6,"Storage Location",150);
	
	/**
	 * Index column number
	 */
	private int index;
	
	/**
	 * Column header caption
	 */
	private String caption;
	
	/**
	 * Column width
	 */
	private Integer width;
	
	
	private CompanyTableColumns(int index, String caption, Integer width) {
		this.index = index;
		this.caption = caption;
		this.width = width;
	}
	
	/**
	 * Finds a StudyTableColumns by column index
	 * @param index Index number to find
	 * @return StudyTableColumns corresponding to columnIndex or null if not exit
	 */
	public static  CompanyTableColumns lookupByIndex(Integer index){
		CompanyTableColumns result= null;
		
		for (CompanyTableColumns column: CompanyTableColumns.values()) {
			if (index.equals(column.getIndex())) {
				result = column;
				break;
			}
		}
		
		return result;
	}


	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}


	/**
	 * @return the caption
	 */
	public String getCaption() {
		return caption;
	}


	/**
	 * @return the width
	 */
	public Integer getWidth() {
		return width;
	}
}
