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
//$ Id: TemplateParamsColumns.java, Jun 1, 2010 TMSANCHEZ $
package com.cimmyt.constants;

/**
 * TODO add class documentation here
 * 
 */
public enum TemplateParamsColumns {
	NAME(0,"Field Name",100),
	DESCRIPTION(1,"Description",250),
	TYPE(2,"Data Type",200);
	
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
	
	
	private TemplateParamsColumns(int index, String caption, Integer width) {
		this.index = index;
		this.caption = caption;
		this.width = width;
	}
	
	/**
	 * Finds a StudyTableColumns by column index
	 * @param index Index number to find
	 * @return StudyTableColumns corresponding to columnIndex or null if not exit
	 */
	public static  TemplateParamsColumns lookupByIndex(Integer index){
		TemplateParamsColumns result= null;
		
		for (TemplateParamsColumns column: TemplateParamsColumns.values()) {
			if (index.equals(column.getIndex())) {
				result = column;
				break;
			}
		}
		
		return result;
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	public static  TemplateParamsColumns lookupByName(String name){
		TemplateParamsColumns result= null;
		
		for (TemplateParamsColumns column: TemplateParamsColumns.values()) {
			if (name.equals(column.name())) {
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
