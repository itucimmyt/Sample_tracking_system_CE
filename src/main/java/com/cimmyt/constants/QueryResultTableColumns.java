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

public enum QueryResultTableColumns {
	LABSTUDYID(0,"Study ID",60),
	LABSTUDYTITLE(1,"Title Study",150),
	PROJECTNAME(2,"Project",100),
	INVESTIGATORNAME(3,"Investigator",100),
	TISSUENAME(4,"Tissue",100),
	PLATENAME(5,"Plate Name",120),
	PLATELOCATION(6,"Plate Location",100),
	CONTROLTYPE(7,"Control Type",100),
	SAMPLEID(8,"SampleID",150),
	GID(9,"GID",100),
	ACC(10,"Acc",150),
	PLANTNO(11,"PlantNo",150),
	SPECIE(12,"Specie",150),
	COMMENTS(13,"Comments",150),
	ENTRYNO(14,"EntryNo",150),
	LOCATIONNAME(15,"Location",150),
	SEASONNAME(16,"Season",100),
	SELFORSEND(17,"Selected for send",100),
	CURRENTLOCATION(18,"Current Location",100)
	;
	
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
	
	
	private QueryResultTableColumns(int index, String caption, Integer width) {
		this.index = index;
		this.caption = caption;
		this.width = width;
	}
	
	/**
	 * Finds a StudyTableColumns by column index
	 * @param index Index number to find
	 * @return StudyTableColumns corresponding to columnIndex or null if not exit
	 */
	public static  QueryResultTableColumns  lookupByIndex(Integer index){
		QueryResultTableColumns result= null;
		
		for (QueryResultTableColumns column: QueryResultTableColumns.values()) {
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
