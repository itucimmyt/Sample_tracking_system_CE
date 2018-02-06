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
package com.cimmyt.zk.storage_location;

import static com.cimmyt.utils.Constants.ATTRIBUTE_SAMPLE_LOCATION_LIST;
import static com.cimmyt.utils.Constants.ATTRIBUTE_STORAGE_LOCATION_ITEM;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_FIELD_REQUIRED;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_INFORMATION;
import static com.cimmyt.utils.Constants.LBL_STORAGE_LOCATION_SELECTED_LAST_SAMP;
import static com.cimmyt.utils.Constants.LOCALE_LANGUAGE;
import static com.cimmyt.utils.Constants.LOCATION_SERVICE_BEAN_NAME;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timebox;
import org.zkoss.zul.Window;

import com.cimmyt.constants.MovType;
import com.cimmyt.model.bean.SampleDetail;
import com.cimmyt.model.bean.StorageLocation;
import com.cimmyt.service.ServiceLocation;
import com.cimmyt.utils.PropertyHelper;
import com.cimmyt.utils.StrUtils;

@SuppressWarnings("serial")
public class ControlWindowSample extends Window{

	private Datebox idDBRegDate;
	private Timebox idTime;
	private Listbox idLisB;
	private Textbox idTexTarget;
	private Combobox idComboFrom;
	private Textbox idTexBarCode;
	private Textbox idTComments;
	private Label idLaCombo;
	private Label idLComment;
	private PropertyHelper pro=null;
	private static ServiceLocation serviceLocation = null;
	private List<SampleDetail> selectedSamples = new ArrayList<SampleDetail>();
	static {
		if(serviceLocation == null)
        {
			try{
				serviceLocation = (ServiceLocation)SpringUtil.getBean(LOCATION_SERVICE_BEAN_NAME);
				}catch (Exception e){
				e.printStackTrace();
			}
        }
		
	}
	Logger logger= Logger.getLogger(ControlWindowSample.class);
	/** 
	 * Close Window
	 */
	public void closeWindow(){
		Window idWindow = (Window)getFellow("idWindow");
		idWindow.onClose();
	}

	public void loadContextAttribute(){
		loadComponents();
		pro = (PropertyHelper)getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);
		StorageLocation storage = (StorageLocation)getDesktop().getAttribute(ATTRIBUTE_STORAGE_LOCATION_ITEM);
		idTexTarget.setText(storage.getLname());
		idComboFrom.setSelectedIndex(0);
	}

	public void add(){
		pro = (PropertyHelper)getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);
		loadComponents();
		showLabelReq(false);
		boolean showMessage = false;
		if (validateText(idTComments)){
			showMessage = true;
			idLComment.setVisible(true);
		}
		if (idComboFrom.getSelectedIndex() == -1){
			showMessage = true;
			idLaCombo.setVisible(true);
		}
		if (idLisB.getItems().isEmpty()){
			messageBox(pro.getKey(LBL_STORAGE_LOCATION_SELECTED_LAST_SAMP));
			return;
		}
		if (showMessage){
			messageBox(pro.getKey(LBL_GENERIC_MESS_FIELD_REQUIRED));
			return;
		}else {
			StorageLocation storage = (StorageLocation)getDesktop().getAttribute(ATTRIBUTE_STORAGE_LOCATION_ITEM);
			Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
			calendar.setTime(idTime.getValue());   // assigns calendar to given date 
			try{
			serviceLocation.registerSampleMovement(idDBRegDate.getValue(), 
					MovType.INITIAL_ASIGMENT,storage ,idTComments.getText() , 1,
					String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)) ,String.valueOf(calendar.get(Calendar.MINUTE)) , selectedSamples);
			//getDesktop().removeAttribute(ATTRIBUTE_SAMPLE_LOCATION_LIST);
			Window idWindow = (Window)getFellow("idWindow");
			idWindow.onClose();
			}catch (Exception e){
				logger.info(e.getMessage());
			}
		}
	}

	private void loadComponents(){
		idDBRegDate = (Datebox)getFellow("idDBRegDate");
		idTime = (Timebox)getFellow("idTime");
		idTexTarget = (Textbox)getFellow("idTexTarget");
		idComboFrom = (Combobox)getFellow("idComboFrom");
		idTexBarCode = (Textbox)getFellow("idTexBarCode");
		idTComments = (Textbox)getFellow("idTComments");
		idLisB = (Listbox)getFellow("idLisB");
		idLaCombo = (Label)getFellow("idLaCombo");
		idLComment = (Label)getFellow("idLComment");
	}

	private void showLabelReq(boolean visible){
		idLComment.setVisible(visible);
		idLaCombo.setVisible(visible);
	}
	private void messageBox(String mess){
		Messagebox.show(mess,pro.getKey(LBL_GENERIC_MESS_INFORMATION), 
				Messagebox.OK, Messagebox.INFORMATION);
	}

	private boolean validateText(Textbox text){
		return text.getText().trim().equals("");
	}

	@SuppressWarnings("unchecked")
	public void loadWindoeSearch(){
		pro = (PropertyHelper)getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);
		loadComponents();
		if (selectedSamples != null && !selectedSamples.isEmpty() ){
			getDesktop().setAttribute(ATTRIBUTE_SAMPLE_LOCATION_LIST, selectedSamples);
		}
		showWindowSample();
		if (getDesktop().getAttribute(ATTRIBUTE_SAMPLE_LOCATION_LIST) != null){
			selectedSamples = (List<SampleDetail>)getDesktop().getAttribute(ATTRIBUTE_SAMPLE_LOCATION_LIST);
			if (selectedSamples != null && !selectedSamples.isEmpty()){
				for (SampleDetail bean : selectedSamples){
					loadItemListLeft(bean);	
				}
			}
		}
	}

	private void loadItemListLeft (SampleDetail bean){
		Listitem lIt = new Listitem ();
		if (bean.getLocationid() != null){
			Listcell cell1 = new Listcell(bean.getLocationid().getLocation_name());
			lIt.appendChild(cell1);
		}else{
			lIt.appendChild(new Listcell());
		}
		Listcell cell2 = new Listcell(bean.getLabstudyid().getTitle());
		lIt.appendChild(cell2);
		Listcell cell3 = new Listcell(bean.getPlatename());
		lIt.appendChild(cell3);
		Listcell cell4 = new Listcell(bean.getPlateloc());
		lIt.appendChild(cell4);
		if (bean.getSamplegid() !=null){
			Listcell cell5 = new Listcell(bean.getLabstudyid().getProject().getProjectname()+
					bean.getLabstudyid().getProject().getPurposename()+
					(bean.getLabstudyid().isUsePadded() ? StrUtils.getPaddingCeros(bean.getSamplegid()) :
						String.valueOf(bean.getSamplegid())));
					
			lIt.appendChild(cell5);
		}else {
			lIt.appendChild(new Listcell());
		}
		lIt.setValue(bean);
		idLisB.appendChild(lIt);
	}
	private void showWindowSample() {
		final Window win = (Window) Executions.createComponents(
    			"/storage_location/window_search_loction.zul", null, null);
    		win.doModal();
	}
}
