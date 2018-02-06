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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.zkoss.util.media.Media;

import com.cimmyt.bean.FileSampleCSVBean;
import com.cimmyt.bean.RowCSV;
import com.csvreader.CsvReader;
public class FileManagerCSVImpl implements FileManagerCSV {

	/**
	 * Put Logger to class
	 */
	private Logger logger= Logger.getLogger(FileManagerCSVImpl.class);
	/**
	 * Get Object file samples of format CSV
	 * @param name of the file
	 */
	@Override
	public FileSampleCSVBean getFileSampleCSV(Media media, int typeFile, boolean isValidateHeader) {
		FileSampleCSVBean fileSampleCSV = new FileSampleCSVBean();
		int numberColumn;
		CsvReader csvReader = null;
		logger.info("Into the methos class FileManagerCSVImpl.....");
		Reader reader = getReader(media);
			try {
				csvReader = new CsvReader (reader);
				//Read the first line of the file
				csvReader.readRecord();
				numberColumn = csvReader.getColumnCount();
				if (isValidateHeader)
				if (!validateTypeFile(numberColumn, typeFile))
					return null;
				//read header of file
				for (int sizeHeader = 0; sizeHeader < numberColumn; sizeHeader++){
					fileSampleCSV.getListHeaders().add
					(new StringBuilder(csvReader.get(sizeHeader)));
				}
				// read the body of file to samples
				while (csvReader.readRecord()){
					RowCSV row = new RowCSV();
					for (int sizeRow = 0; sizeRow < numberColumn; sizeRow++){
						row.getListRow().add(new StringBuilder(csvReader.get(sizeRow)));
					}
					fileSampleCSV.getListRowCSV().add(row);
				}
				return fileSampleCSV;
			} catch (FileNotFoundException e) {
				logger.error(e.getMessage());
				return null;
			}catch(Exception ex){
				ex.printStackTrace();
				logger.error(ex.getMessage());
				return null;
			}
			finally {
					if(csvReader != null)csvReader.close();
					try {
						reader.close();
						reader = null;
					} catch (IOException e) {
						logger.error(e.getMessage());
					}
					media = null;
			}
		//return null;
	}

	private boolean validateTypeFile(int numberColum, int typeFile){
		switch (typeFile){
		case 1:
			if (numberColum == 8 || numberColum == 9)
			return true;
		case 2:
			if (numberColum == 12)
			return true;
			default:
				return false;
		}
	}
	/**
	 * Method that load file in different type of browser 
	 * @param media
	 * @return
	 */
	private Reader getReader(Media media){
		Reader reader = null;
		try{
			reader = new InputStreamReader(IOUtils.toInputStream(media.getStringData()));
			return reader;
		}catch (IllegalStateException ex){
			logger.error(ex.getMessage());
		}
		try{
			reader = media.getReaderData();
			return reader;
			
		}catch (IllegalStateException ex){
			logger.error(ex.getMessage());;	
		}
		try{
			reader = new InputStreamReader(media.getStreamData());
			return reader;
		}catch (IllegalStateException ex){
		ex.printStackTrace();	
		}
	return reader;	
	}
}
