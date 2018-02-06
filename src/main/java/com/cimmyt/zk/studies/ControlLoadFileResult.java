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
package com.cimmyt.zk.studies;

import static com.cimmyt.utils.Constants.ATTRIBUTE_LABSTUDY_ITEM;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_ERROR;
import static com.cimmyt.utils.Constants.LBL_STUDIES_FILE_UPLOAD_ER_TYPE;
import static com.cimmyt.utils.Constants.LBL_STUDIES_FILE_UPLOAD_FILE_CORRECT;
import static com.cimmyt.utils.Constants.LBL_STUDIES_FILE_UPLOAD_FILE_STRUCTURE;
import static com.cimmyt.utils.Constants.LBL_STUDIES_FILE_UPLOAD_TEXT_SUCCESSFULL;
import static com.cimmyt.utils.Constants.LOCALE_LANGUAGE;
import static com.cimmyt.utils.Constants.MANAGER_FILE_CSV_SERVICE_BEAN_NAME;
import static com.cimmyt.utils.Constants.RESULT_DATA_SERVICE;
import static com.cimmyt.utils.ConstantsDNA.LBL_RESULTS_DATA_MATCHE_SAMPLE;
import static com.cimmyt.utils.ConstantsDNA.LBL_RESULTS_DATA_PROBLEM_HEADER;
import static com.cimmyt.utils.ConstantsDNA.LBL_RESULTS_DATA_PROBLEM_ROW_EMPTY;
import static com.cimmyt.utils.ConstantsDNA.LBL_RESULTS_DATA_SIZE_FIELD;

import java.util.List;

import org.apache.log4j.Logger;
import org.zkoss.util.media.Media;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Image;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.cimmyt.bean.FileSampleCSVBean;
import com.cimmyt.csv.FileManagerCSV;
import com.cimmyt.exception.ResultDataException;
import com.cimmyt.model.bean.LabStudy;
import com.cimmyt.service.ServiceResultData;
import com.cimmyt.utils.PropertyHelper;

/**
 * @author CIMMYT
 * Class for validate business rule to load file of samples  
 */
@SuppressWarnings("serial")
public class ControlLoadFileResult extends Window{

	private Window idWindow;
	private Textbox idTextError;
	private Image idLoadFile;
	private PropertyHelper pro=null;
	private int typeFile = 1;
	private LabStudy beanStudy;
	private static FileManagerCSV serviceManagerFileCSV;
	private static ServiceResultData serviceResultData;
	// variable to set logger 
		private Logger logger= Logger.getLogger(ControlLoadFileResult.class);
		static {
			if(serviceManagerFileCSV == null)
				try{
					serviceManagerFileCSV = (FileManagerCSV)SpringUtil.getBean(MANAGER_FILE_CSV_SERVICE_BEAN_NAME);
				}catch (Exception e){
					e.printStackTrace();
				}
			if (serviceResultData == null){
				try{
					serviceResultData = (ServiceResultData)SpringUtil.getBean(RESULT_DATA_SERVICE);
				}catch (Exception exRD){
					exRD.printStackTrace();
				}
			}
		}
	/** Method that load the component of web page */
	public void loadContext(){
		pro = (PropertyHelper)getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);
		loadComponent();
	}

	/** Method to load file CSV of the samples
	 * @param media */
	public void upLoadFile(Media media) {
		
		beanStudy = (LabStudy)getDesktop().getAttribute(ATTRIBUTE_LABSTUDY_ITEM );
		logger.info(media.getName()+" Extention ::: "+media.getFormat());
		if (media.getName().indexOf("csv") != -1){
				FileSampleCSVBean fileSampleCSV = serviceManagerFileCSV.getFileSampleCSV(media, typeFile, false);
				if (fileSampleCSV == null){
	  		    	Messagebox.show(pro.getKey(LBL_STUDIES_FILE_UPLOAD_FILE_CORRECT),pro.getKey(LBL_GENERIC_MESS_ERROR), 
							Messagebox.OK, Messagebox.ERROR);
	  		    	return;
	  		    }
				if (fileSampleCSV.getListHeaders().size() < 0 ){
					Messagebox.show(pro.getKey(LBL_STUDIES_FILE_UPLOAD_FILE_STRUCTURE),pro.getKey(LBL_GENERIC_MESS_ERROR), 
							Messagebox.OK, Messagebox.ERROR);
					return;
				}
				logger.info("Read File by upload:::");
				try {
					idTextError.setText("");
					serviceResultData.validateSampleID(fileSampleCSV, pro, beanStudy.getNumofplates()*beanStudy.getPlatesize(), beanStudy);
					idTextError.setText(idTextError.getText()+ pro.getKey(LBL_STUDIES_FILE_UPLOAD_TEXT_SUCCESSFULL));
					idLoadFile.setVisible(true);
				}catch (ResultDataException resultException){
					if (resultException.getListErrorLoadFileResultDataBean()!= null && resultException.getListErrorLoadFileResultDataBean().areThereAnyListEmty()){
						printTextError(resultException);
					}else {
						idTextError.setText("");
						idTextError.setText(resultException.getMessage());
						idLoadFile.setVisible(false);
					}
				
				}
			}else{
			Messagebox.show(pro.getKey(LBL_STUDIES_FILE_UPLOAD_ER_TYPE),pro.getKey(LBL_GENERIC_MESS_ERROR), 
					Messagebox.OK, Messagebox.ERROR);
			return;
		}
	}

	private void printTextError (ResultDataException resultException){
		idTextError.setText("");
		idTextError.setValue("");
		String strOfError = "";
		
		if (!resultException.getListErrorLoadFileResultDataBean().getListRowEmty().isEmpty())
			strOfError = setListErrorToListInteger(resultException.getListErrorLoadFileResultDataBean().getListRowEmty(), 
					pro.getKey(LBL_RESULTS_DATA_PROBLEM_ROW_EMPTY), strOfError);
		if (!resultException.getListErrorLoadFileResultDataBean().getListSampleIDFormat().isEmpty())
			strOfError = setListErrorToListInteger(resultException.getListErrorLoadFileResultDataBean().getListSampleIDFormat(), 
					pro.getKey(LBL_RESULTS_DATA_MATCHE_SAMPLE), strOfError);
		if (!resultException.getListErrorLoadFileResultDataBean().getListErrorSampleIDNotFound().isEmpty())
			strOfError = setListErrorToListInteger(resultException.getListErrorLoadFileResultDataBean().getListErrorSampleIDNotFound(), 
					pro.getKey(LBL_RESULTS_DATA_MATCHE_SAMPLE), strOfError);
		if(!resultException.getListErrorLoadFileResultDataBean().getListErrorHeader().isEmpty())
			strOfError = setListErrorToList(resultException.getListErrorLoadFileResultDataBean().getListErrorHeader(), 
					pro.getKey(LBL_RESULTS_DATA_PROBLEM_HEADER), strOfError);
		
		if(!resultException.getListErrorLoadFileResultDataBean().getListErrorSize().isEmpty())
			strOfError = setListErrorToListInteger(resultException.getListErrorLoadFileResultDataBean().getListErrorSize(), 
					pro.getKey(LBL_RESULTS_DATA_SIZE_FIELD), strOfError);
		idTextError.setText( strOfError);
		idLoadFile.setVisible(false);
	}
	/**
	 * Method to create list of error in string
	 * @param listStr
	 * @param title
	 * @param strOfError
	 */
	private String setListErrorToListInteger (List<Integer> listStr , String title, String strOfError){
		strOfError = strOfError + title;
		String list = "";
		for (int size = 0; 
				size < listStr.size()
				; size++ ){
			if( (size+1) >= listStr.size()){
				list = list + listStr.get(size) + "\n";
			}else {
				list = list + listStr.get(size) + ", ";
			}
		}
		strOfError = strOfError+ list;
		return strOfError;
	}
	/**
	 * Method to create list of error in string
	 * @param listStr
	 * @param title
	 * @param strOfError
	 */
	private String setListErrorToList (List<String> listStr , String title, String strOfError){
		strOfError = strOfError + title;
		String list = "";
		for (int size = 0; 
				size < listStr.size()
				; size++ ){
			if( (size+1) >= listStr.size()){
				list = list + listStr.get(size) + "\n";
			}else {
				list = list + listStr.get(size) + ", ";
			}
		}
		strOfError = strOfError+ list;
		return strOfError;
	}
    /** Up load component in the code */
	private void loadComponent(){
		idWindow = (Window)getFellow("idWindowFileUpload");
		idTextError = (Textbox)getFellow("idTextError");
		idLoadFile = (Image)getFellow("idLoadFile");
	}
	/** Close Window */
	public void closeWindow(){
		idWindow.onClose();
	}
}
