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
//$ Id: FieldType.java, Jun 1, 2010 TMSANCHEZ $
package com.cimmyt.constants;

/**
 * TODO add class documentation here
 */
public enum FieldType {
	NUMERIC("N", "NUMERIC"), CHARACTER("C", "CHARACTER"),
	DATE("D","DATE");

	private String id;
	private String value;

	private FieldType(String id, String value) {
		this.id = id;
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	public String getId() {
		return this.id;
	}

	public static FieldType lookupById(String id) {
		FieldType result = null;

		for (FieldType column : FieldType.values()) {
			if (id.equals(column.getId())) {
				result = column;
				break; 
			}
		}

		return result;
	}
	
	public static FieldType lookupByName(String name) {
		FieldType result = null;

		for (FieldType column : FieldType.values()) {
			if (name.equals(column.getValue())) {
				result = column;
				break;
			}
		}

		return result;
	}
	

}
