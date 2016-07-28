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
/**
 * Class that contain the control of plate
 * @author CIMMYT
 */
public class ControlPlateBean {
	//Number of control random 
	private Integer numberControlRandom = 0;
	// Number of control to provider 
	private Integer numberControlProvider = new Integer (0);
	//Number of control to blank
	private Integer numberControlBlank = new Integer (0);

	public Integer getNumberControlRandom() {
		return numberControlRandom;
	}
	public void setNumberControlRandom(Integer numberControlRandom) {
		this.numberControlRandom = numberControlRandom;
	}
	public Integer getNumberControlProvider() {
		return numberControlProvider;
	}
	public void setNumberControlProvider(Integer numberControlProvider) {
		this.numberControlProvider = numberControlProvider;
	}
	public Integer getNumberControlBlank() {
		return numberControlBlank;
	}
	public void setNumberControlBlank(Integer numberControlBlank) {
		this.numberControlBlank = numberControlBlank;
	}

	
}
