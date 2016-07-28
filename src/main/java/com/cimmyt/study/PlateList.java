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
package com.cimmyt.study;

import static com.cimmyt.utils.Constants.LAST_PLATE_PROJECT_SERVICE_BEAN_NAME;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zkplus.spring.SpringUtil;

import com.cimmyt.model.bean.LabStudy;
import com.cimmyt.service.ServiceLastPlateProject;

public class PlateList {
	public LabStudy labstudy;
	//public LastPlateProject lastPlateProject;
	public static final int PLATE_SIZE_96 = 96;
	public static final int PLATE_SIZE_384 = 384;	
	
	private int plateSize;
	private int numOfPlates;
	private String plateNamePrefix="";
	private List<PlateContentList> platesContents;
	private static ServiceLastPlateProject serviceLastPlateProject;
	static {
		if(serviceLastPlateProject == null)
        {
			try{
				serviceLastPlateProject = (ServiceLastPlateProject)SpringUtil.getBean(LAST_PLATE_PROJECT_SERVICE_BEAN_NAME);
				}catch (Exception e){
				System.out.println("Object initialaizer for down the scope");//e.printStackTrace();
			}
        }
		
	}
	public PlateList(int plateSize, int numOfPlates, LabStudy labstudyvar, 
			String plateNamePrefix) {
		this.plateSize = plateSize;
		this.numOfPlates = numOfPlates;
		labstudy=labstudyvar;
		this.plateNamePrefix = plateNamePrefix;
		generatePlates();
	}
	
	/**
	 * 
	 */
	
	private void generatePlates() {
		this.platesContents = new ArrayList<PlateContentList>();
		Integer sigplatenumber=0;
		if (labstudy==null){
			sigplatenumber=0;
		}else{
		sigplatenumber=serviceLastPlateProject.getNextPlateNumber(
				labstudy.getProject().getProjectid(),
				labstudy.getOrganismid().getOrganismid(),
				labstudy.getInvestigatorid().getInvestigatorid());
		}
		
		for (int platesCount = 0 ; platesCount < numOfPlates; platesCount++) {
			///////Agregar funcion para obtener el siguiente numero de plato 
			///////28/10/2011
			sigplatenumber=sigplatenumber+1;
			PlateContentList plateContentList = new PlateContentList(plateSize, sigplatenumber,plateNamePrefix);
			this.platesContents.add(plateContentList);
		}
		
	}
	

	/**
	 * @return the plateSize
	 */
	
	public int getPlateSize() {
		return plateSize;
	}

	/**
	 * @param plateSize the plateSize to set
	 */
	
	public void setPlateSize(int plateSize) {
		this.plateSize = plateSize;
	}

	/**
	 * @return the numOfPlates
	 */
	
	public int getNumOfPlates() {
		return numOfPlates;
	}

	/**
	 * @param numOfPlates the numOfPlates to set
	 */
	public void setNumOfPlates(int numOfPlates) {
		this.numOfPlates = numOfPlates;
	}

	/**
	 * @return the platesContents
	 */
	public List<PlateContentList> getPlatesContents() {
		return platesContents;
	}

	/**
	 * @param platesContents the platesContents to set
	 */
	public void setPlatesContents(List<PlateContentList> platesContents) {
		this.platesContents = platesContents;
	}
}
