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
package com.cimmyt.csv;

import org.zkoss.zhtml.Messagebox;

import com.cimmyt.model.bean.LabStudy;
import com.cimmyt.model.bean.SampleDetail;
import com.csvreader.CsvWriter;

public class UtilCSVImpl implements UtilCSV {

	@Override
	public void generateCsvDARTFile(String fileName, LabStudy labstudy,
			String organism, String tissue) {
		int row = 0;
		String actrow;
		String actcol;

		String specie;
		String sid;

		String priority;
		CsvWriter csvFileWrite = new CsvWriter(fileName);

		try {
			// Agregar encabezados tal y como aparecen en el archivo de salida
			csvFileWrite.write("PlateID");
			csvFileWrite.write("Row");
			csvFileWrite.write("Column");
			csvFileWrite.write("Organism");
			csvFileWrite.write("Species");
			csvFileWrite.write("Genotype");
			csvFileWrite.write("Tissue");
			csvFileWrite.write("Comments");
			csvFileWrite.endRecord();
			
			for(SampleDetail sampledet:labstudy.getSampleDetailCollection()){
			// agregar datos
				
				if (sampledet.getSamplegid()!=null){
					csvFileWrite.write(sampledet.getPlatename());

				// plateloc
				actrow = sampledet.getPlateloc();
				actrow = actrow.substring(0, 1);
				csvFileWrite.write(actrow);

				actcol = sampledet.getPlateloc();
				actcol = actcol.substring(1);
				csvFileWrite.write(actcol);

				// organism llega como parametro
				csvFileWrite.write(organism);

				// specie
				specie = sampledet.getSpecie();
				csvFileWrite.write(specie);

				// sid
				sid = labstudy.getPrefix()+
					String.valueOf(sampledet.getSamplegid());
				csvFileWrite.write(sid);

				// tissue llega como parametro
				csvFileWrite.write(tissue);

				// priority
				priority = sampledet.getPriority();
				csvFileWrite.write(priority);

				csvFileWrite.endRecord();
				}
			}
			csvFileWrite.close();
		} catch (Exception ex) {
			Messagebox.show("Can not generate a file");
		}
	}

}
