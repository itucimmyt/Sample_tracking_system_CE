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

import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_ERROR;
import static com.cimmyt.utils.Constants.LBL_STUDIES_FILE_UPLOAD_ER_TYPE;
import static com.cimmyt.utils.Constants.MANAGER_FILE_CSV_SERVICE_BEAN_NAME;

import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Messagebox;

import com.cimmyt.bean.FileSampleCSVBean;
import com.cimmyt.csv.FileManagerCSV;
import com.cimmyt.utils.PropertyHelper;

@SuppressWarnings("serial")
public class FileUploadCSVButton extends Button{
	
	private static FileManagerCSV serviceManagerFileCSV;
	private PropertyHelper pro=null;
	static {
		if(serviceManagerFileCSV == null)
        {
			try{
				serviceManagerFileCSV = (FileManagerCSV)SpringUtil.getBean(MANAGER_FILE_CSV_SERVICE_BEAN_NAME);
			}catch (Exception e){
				e.printStackTrace();
			}
        }
		
	}
	 public void onUpload(UploadEvent event)
	    {
		 Media media = event.getMedia();
			if (media.getFormat().equals("csv")){
//				String strText = idTextError.getText() +pro.getKey(LBL_STUDIES_FILE_UPLOAD_FILE_LABEL) + " "+media.getName()+ "\n ";
//				idTextError.setText(strText);
//				strText = idTextError.getText() +pro.getKey(LBL_STUDIES_FILE_UP_FIL_SIZE)+"\n";
//				idTextError.setText(strText);
				System.out.println(media.getReaderData());
				System.out.println(media.getStringData());
				System.out.println(media.getStreamData());
				//FileSampleCSVBean fileSampleCSV = serviceManagerFileCSV.getFileSampleCSV(media);
				//System.out.println(fileSampleCSV);
				
			}else{
				Messagebox.show(pro.getKey(LBL_STUDIES_FILE_UPLOAD_ER_TYPE),pro.getKey(LBL_GENERIC_MESS_ERROR), 
						Messagebox.OK, Messagebox.ERROR);
			}
	    }

}
