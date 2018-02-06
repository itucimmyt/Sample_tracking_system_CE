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
package com.cimmyt.zk.query;

import static com.cimmyt.utils.Constants.LIST_PROJECT_QUALIFIER1;
import static com.cimmyt.utils.Constants.LIST_PROJECT_QUALIFIER2;
import static com.cimmyt.utils.Constants.LIST_PROJECT_QUALIFIER3;
import static com.cimmyt.utils.Constants.LIST_PROJECT_QUALIFIER4;
import static com.cimmyt.utils.Constants.LIST_SAMPLE_QUALIFIER1;
import static com.cimmyt.utils.Constants.LIST_SAMPLE_QUALIFIER10;
import static com.cimmyt.utils.Constants.LIST_SAMPLE_QUALIFIER2;
import static com.cimmyt.utils.Constants.LIST_SAMPLE_QUALIFIER3;
import static com.cimmyt.utils.Constants.LIST_SAMPLE_QUALIFIER4;
import static com.cimmyt.utils.Constants.LIST_SAMPLE_QUALIFIER5;
import static com.cimmyt.utils.Constants.LIST_SAMPLE_QUALIFIER6;
import static com.cimmyt.utils.Constants.LIST_SAMPLE_QUALIFIER7;
import static com.cimmyt.utils.Constants.LIST_SAMPLE_QUALIFIER8;
import static com.cimmyt.utils.Constants.LIST_SAMPLE_QUALIFIER9;
import static com.cimmyt.utils.Constants.LIST_STUDY_QUALIFIER1;
import static com.cimmyt.utils.Constants.LIST_STUDY_QUALIFIER2;
import static com.cimmyt.utils.Constants.LIST_STUDY_QUALIFIER3;
import static com.cimmyt.utils.Constants.LIST_STUDY_QUALIFIER4;
import static com.cimmyt.utils.Constants.LIST_STUDY_QUALIFIER5;
import static com.cimmyt.utils.Constants.LIST_STUDY_QUALIFIER6;
import static com.cimmyt.utils.Constants.LIST_STUDY_QUALIFIER7;
import static com.cimmyt.utils.Constants.LIST_STUDY_QUALIFIER8;
import static com.cimmyt.utils.Constants.LIST_STUDY_RESULT_HEADER1;
import static com.cimmyt.utils.Constants.LIST_STUDY_RESULT_HEADER2;
import static com.cimmyt.utils.Constants.LIST_STUDY_RESULT_HEADER3;
import static com.cimmyt.utils.Constants.LIST_STUDY_RESULT_HEADER4;
import static com.cimmyt.utils.Constants.LIST_STUDY_RESULT_HEADER5;
import static com.cimmyt.utils.Constants.LIST_STUDY_RESULT_HEADER6;
import static com.cimmyt.utils.Constants.LIST_STUDY_RESULT_HEADER7;
import static com.cimmyt.utils.Constants.LIST_SAMPLE_RESUTL_HEADER1;
import static com.cimmyt.utils.Constants.LIST_SAMPLE_RESUTL_HEADER2;
import static com.cimmyt.utils.Constants.LIST_SAMPLE_RESUTL_HEADER3;
import static com.cimmyt.utils.Constants.LIST_SAMPLE_RESUTL_HEADER4;
import static com.cimmyt.utils.Constants.LIST_SAMPLE_RESUTL_HEADER5;
import static com.cimmyt.utils.Constants.LIST_SAMPLE_RESUTL_HEADER6;
import static com.cimmyt.utils.Constants.LIST_SAMPLE_RESUTL_HEADER7;
import static com.cimmyt.utils.Constants.LIST_SAMPLE_RESUTL_HEADER8;
import static com.cimmyt.utils.Constants.LIST_KBASE_RESULT_HEADER1;
import static com.cimmyt.utils.Constants.LIST_KBASE_RESULT_HEADER2;
import static com.cimmyt.utils.Constants.LIST_KBASE_RESULT_HEADER3;
import static com.cimmyt.utils.Constants.LIST_KBASE_RESULT_HEADER4;
import static com.cimmyt.utils.Constants.LIST_KBASE_RESULT_HEADER5;
import static com.cimmyt.utils.Constants.LIST_KBASE_RESULT_HEADER6;
import static com.cimmyt.utils.Constants.LIST_PARAM_HEADER1;
import static com.cimmyt.utils.Constants.LIST_PARAM_HEADER2;
import static com.cimmyt.utils.Constants.LIST_PARAM_HEADER3;
import static com.cimmyt.utils.Constants.LIST_PARAM_HEADER4;
import static com.cimmyt.utils.Constants.LIST_PARAM_HEADER5;
import static com.cimmyt.utils.Constants.LIST_PARAM_HEADER6;
import static com.cimmyt.utils.Constants.VALUE_PROJECT;
import static com.cimmyt.utils.Constants.VALUE_SAMPLE;
import static com.cimmyt.utils.Constants.VALUE_STUDY;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.cimmyt.bean.RowQualifier;
import com.cimmyt.bean.Operator.DataType;

/**
 * 
 * Definition of dynamic parts of the view.
 *
 */
public class QueryViewDefinition {

	public static final Map<String, String> HEADERS_KBASE;
	public static final Map<String, String> HEADERS_SAMPLE;
	public static final Map<String, String> HEADERS_STUDY;
	public static final Map<String, String> HEADERS_PARAMETERS_KBASE;
	public static final Map<String, String> HEADERS_PARAMETERS_DNAST;
	public static final Set<RowQualifier> QUALIFIERS_PROJECT;
	public static final Set<RowQualifier> QUALIFIERS_SAMPLE;
	public static final Set<RowQualifier> QUALIFIERS_STUDY;

	static {
		// headers for table parameters when doing local queries
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(LIST_PARAM_HEADER1, "20%");
		map.put(LIST_PARAM_HEADER2, "20%");
		map.put(LIST_PARAM_HEADER3, "20%");
		map.put(LIST_PARAM_HEADER4, "10%");
		map.put(LIST_PARAM_HEADER5, "20%");
		map.put(LIST_PARAM_HEADER6, "10%");
		HEADERS_PARAMETERS_DNAST = Collections.unmodifiableMap(map);

		// headers for table parameters when doing remote queries to Knowledge Base
		map = new LinkedHashMap<String, String>();
		map.put(LIST_PARAM_HEADER1, "25%");
		map.put(LIST_PARAM_HEADER2, "25%");
		map.put(LIST_PARAM_HEADER4, "20%");
		map.put(LIST_PARAM_HEADER5, "20%");
		map.put(LIST_PARAM_HEADER6, "10%");
		HEADERS_PARAMETERS_KBASE = Collections.unmodifiableMap(map);

		// headers of results table for studies
		map = new LinkedHashMap<String, String>();
		map.put(LIST_STUDY_RESULT_HEADER1, "20%");
		map.put(LIST_STUDY_RESULT_HEADER2, "25%");
		map.put(LIST_STUDY_RESULT_HEADER3, "10%");
		map.put(LIST_STUDY_RESULT_HEADER4, "10%");
		map.put(LIST_STUDY_RESULT_HEADER5, "10%");
		map.put(LIST_STUDY_RESULT_HEADER6, "10%");
		map.put(LIST_STUDY_RESULT_HEADER7, "15%");
		HEADERS_STUDY = Collections.unmodifiableMap(map);

		// headers of results table for samples
		map = new LinkedHashMap<String, String>();
		map.put(LIST_SAMPLE_RESUTL_HEADER1, "9%");
		map.put(LIST_SAMPLE_RESUTL_HEADER2, "9%");
		map.put(LIST_SAMPLE_RESUTL_HEADER3, "14%");
		map.put(LIST_SAMPLE_RESUTL_HEADER4, "9%");
		map.put(LIST_SAMPLE_RESUTL_HEADER5, "9%");
		map.put(LIST_SAMPLE_RESUTL_HEADER6, "14%");
		map.put(LIST_SAMPLE_RESUTL_HEADER7, "9%");
		map.put(LIST_SAMPLE_RESUTL_HEADER8, "19%");
		map.put(LIST_STUDY_QUALIFIER3, "9%");
		HEADERS_SAMPLE = Collections.unmodifiableMap(map);

		// headers of results table for files in KB
		map = new LinkedHashMap<String, String>();
		map.put(LIST_KBASE_RESULT_HEADER1, "10%");
		map.put(LIST_KBASE_RESULT_HEADER2, "20%");
		map.put(LIST_KBASE_RESULT_HEADER3, "15%");
		map.put(LIST_KBASE_RESULT_HEADER4, "15%");
		map.put(LIST_KBASE_RESULT_HEADER5, "10%");
		map.put(LIST_KBASE_RESULT_HEADER6, "30%");
		HEADERS_KBASE = Collections.unmodifiableMap(map);
		
		//Metadata of entity Project
		Set<RowQualifier> set = new LinkedHashSet<RowQualifier>();
		set.add(new RowQualifier(VALUE_PROJECT.concat(".projectdescription"), LIST_PROJECT_QUALIFIER1, DataType.STRING));
		set.add(new RowQualifier(VALUE_PROJECT.concat(".projectname"), LIST_PROJECT_QUALIFIER2, DataType.STRING));
		set.add(new RowQualifier(VALUE_PROJECT.concat(".purposename"), LIST_PROJECT_QUALIFIER3, DataType.STRING));
		set.add(new RowQualifier(VALUE_PROJECT.concat(".purposedescription"), LIST_PROJECT_QUALIFIER4, DataType.STRING));
		QUALIFIERS_PROJECT = set;

		//Metadata of entity Study
		set = new LinkedHashSet<RowQualifier>();
		set.add(new RowQualifier(VALUE_STUDY.concat(".title"), LIST_STUDY_QUALIFIER1, DataType.STRING));
		set.add(new RowQualifier(VALUE_STUDY.concat(".prefix"), LIST_STUDY_QUALIFIER2, DataType.STRING));
		set.add(new RowQualifier("investigator.invest_name", LIST_STUDY_QUALIFIER3, DataType.STRING));
		set.add(new RowQualifier("investigator.invest_abbreviation", LIST_STUDY_QUALIFIER4, DataType.STRING));
		set.add(new RowQualifier("location.location_name", LIST_STUDY_QUALIFIER5, DataType.STRING));
		set.add(new RowQualifier("location.location_description", LIST_STUDY_QUALIFIER6, DataType.STRING));
		set.add(new RowQualifier("season.season_name", LIST_STUDY_QUALIFIER7, DataType.STRING));
		set.add(new RowQualifier("season.season_description", LIST_STUDY_QUALIFIER8, DataType.STRING));
		QUALIFIERS_STUDY = set;
		
		//Metadata of entity Sample
		set = new LinkedHashSet<RowQualifier>();
		set.add(new RowQualifier(VALUE_SAMPLE.concat(".breedergid"), LIST_SAMPLE_QUALIFIER1, DataType.NUMBER));
		set.add(new RowQualifier(VALUE_SAMPLE.concat(".studysampleid"), LIST_SAMPLE_QUALIFIER2, DataType.STRING));//this field is composed of platename+sampleid
		set.add(new RowQualifier(VALUE_SAMPLE.concat(".entryNo"), LIST_SAMPLE_QUALIFIER3, DataType.NUMBER));
		set.add(new RowQualifier(VALUE_SAMPLE.concat(".platename"), LIST_SAMPLE_QUALIFIER4, DataType.STRING));
		set.add(new RowQualifier(VALUE_SAMPLE.concat(".nplanta"), LIST_SAMPLE_QUALIFIER5, DataType.NUMBER));
		set.add(new RowQualifier(VALUE_SAMPLE.concat(".nval"), LIST_SAMPLE_QUALIFIER6, DataType.STRING));
		set.add(new RowQualifier("location.location_name", LIST_SAMPLE_QUALIFIER7, DataType.STRING));
		set.add(new RowQualifier("location.location_description", LIST_SAMPLE_QUALIFIER8, DataType.STRING));
		set.add(new RowQualifier("season.season_name", LIST_SAMPLE_QUALIFIER9, DataType.STRING));
		set.add(new RowQualifier("season.season_description", LIST_SAMPLE_QUALIFIER10, DataType.STRING));
		QUALIFIERS_SAMPLE = set;
}

}
