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

import static com.cimmyt.utils.Constants.QUERY_OPERATOR_AND;
import static com.cimmyt.utils.Constants.QUERY_OPERATOR_EQUALS;
import static com.cimmyt.utils.Constants.QUERY_OPERATOR_GREATER;
import static com.cimmyt.utils.Constants.QUERY_OPERATOR_LESS;
import static com.cimmyt.utils.Constants.QUERY_OPERATOR_LIKE;
import static com.cimmyt.utils.Constants.QUERY_OPERATOR_NOT;
import static com.cimmyt.utils.Constants.QUERY_OPERATOR_NOT_EQUALS;
import static com.cimmyt.utils.Constants.QUERY_OPERATOR_NOT_LIKE;
import static com.cimmyt.utils.Constants.QUERY_OPERATOR_OR;
import static com.cimmyt.utils.Constants.QUERY_OPERATOR_IN;

public interface Operator {
	String key();
	Operator[] getComparators(DataType type);
	/**
	 * Operator that can be used to compare strings
	 * */
	public enum TypeString implements Operator{

		LIKE(QUERY_OPERATOR_LIKE)
		,EQUAL(QUERY_OPERATOR_EQUALS)
		,NOT_EQUAL(QUERY_OPERATOR_NOT_EQUALS)
		,NOT_LIKE(QUERY_OPERATOR_NOT_LIKE);

		private final String key;

		TypeString(String key){
			this.key = key;
		}
		TypeString(){
			this.key = null;
			
		}
		
		@Override
		public String key(){
			return key;
		}
		
		@Override
		public Operator[] getComparators(DataType type){
			return TypeString.values();
		}

	}

	/**
	 * Operator that can be used to compare numbers
	 * */
	public enum TypeNumber implements Operator{

		EQUALS(QUERY_OPERATOR_EQUALS)
		,GREATER(QUERY_OPERATOR_GREATER)
		,LESS(QUERY_OPERATOR_LESS)
		,NOT_EQUALS(QUERY_OPERATOR_NOT_EQUALS)
		,IN(QUERY_OPERATOR_IN);

		private final String key;

		TypeNumber(String key){
			this.key = key;
			
		}
		
		@Override
		public String key(){
			return key;
		}

		@Override
		public Operator[] getComparators(DataType type){
			return TypeNumber.values();
		}

	}
	/**
	 * Operator that joins several conditions in a search.
	 * */
	public enum TypeCondition {

		AND(QUERY_OPERATOR_AND)
		,NOT(QUERY_OPERATOR_NOT)
		,OR(QUERY_OPERATOR_OR)	;

		private final String key;

		TypeCondition(String key){
			this.key = key;
			
		}
		
		public String key(){
			return key;
		}

	}
	
	public enum DataType{
		 STRING
		,NUMBER
	}
	
	

}
