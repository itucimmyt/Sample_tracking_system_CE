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
package com.cimmyt.constants;

public enum ControlType {
	NO_CONTROL("N","Not Control"),
	INDIVIDUAL("C","Individual Control"),
	RANDOM("R","Random Control"),
	DART("D","DArT Control"),
	BLANK("B","Blank"),
	KBIO("K","KBiosciences Control");
	
	private String id;
	private String value;

	private ControlType(String id, String value) {
		this.id = id;
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	public String getId() {
		return this.id;
	}

	public static ControlType lookupById(String id) {
		ControlType result = null;

		for (ControlType column : ControlType.values()) {
			if (id.equals(column.getId())) {
				result = column;
				break; 
			}
		}

		return result;
	}
	
	public static ControlType lookupByName(String name) {
		ControlType result = null;

		for (ControlType column : ControlType.values()) {
			if (name.equals(column.getValue())) {
				result = column;
				break;
			}
		}

		return result;
	}
}
