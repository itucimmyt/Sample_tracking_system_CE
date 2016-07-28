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

import static com.cimmyt.utils.Constants.VALUE_PROJECT;
import static com.cimmyt.utils.Constants.VALUE_STUDY;

import java.util.Set;

import com.cimmyt.zk.query.QueryViewDefinition;

public abstract class OperatorImp implements Operator {

	abstract public String key();

	abstract public Operator[] getComparators(DataType type);
	
	public static Operator valueOf(String stringOperator){
		Operator returnOp = null;
		for(Operator op : Operator.TypeString.values()){
			if (op.toString().equals(stringOperator)){
				returnOp = op;
				break;
			}
		}
		
		if(returnOp == null){
			for(Operator op : Operator.TypeNumber.values()){
				if (op.toString().equals(stringOperator)){
					returnOp = op;
					break;
				}
			}			
		}
		
		return returnOp;
		
	}
	
	/**
	 * Given an element name and its qualifier, returns its datatype.
	 * @param element Name of the element(project,study,sample)
	 * @param qualifier Qualifier of the element's attribute
	 * @return The datatype of the element
	 */
	public static DataType getType(String element, String qualifier){
		Set<RowQualifier> rows;
		if(element.equals(VALUE_PROJECT)){
			rows = QueryViewDefinition.QUALIFIERS_PROJECT;
		}else if(element.equals(VALUE_STUDY)){
			rows = QueryViewDefinition.QUALIFIERS_STUDY;
		}else{
			rows = QueryViewDefinition.QUALIFIERS_SAMPLE;
		}
		
		DataType type = null;
		
		for(RowQualifier row : rows){
			if(qualifier.equals(row.getKey())){
				type = row.getDataType();
			}
		}
		
		return type;
	}


}
