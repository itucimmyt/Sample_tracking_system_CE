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

public enum ConditionType {
	IGUAL("0","Equals","= "),
	DIFERENTE("1","Different","<>"),
	MAYOR("2","Greater than","> "),
	MAYOROIGUAL("3","Greater than or equal to",">="),
	MENOR("4","Less than","< "),
	MENOROIGUAL("5","Less than or equal to","<="),
	LIKE("6","Contains"," like ");
	
	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	private String id;
	private String value;
	private String symbol;
	
	
	private ConditionType(String id, String value,String symbol) {
		this.id = id;
		this.value = value;
		this.symbol=symbol;
	}

	public String getValue() {
		return this.value;
	}

	public String getId() {
		return this.id;
	}

	public static ConditionType lookupById(String id) {
		ConditionType result = null;

		for (ConditionType column : ConditionType.values()) {
			if (id.equals(column.getId())) {
				result = column;
				break; 
			}
		}

		return result;
	}
	
	public static ConditionType lookupByName(String name) {
		ConditionType result = null;

		for (ConditionType column : ConditionType.values()) {
			if (name.equals(column.getValue())) {
				result = column;
				break;
			}
		}

		return result;
	}
}
