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
import static com.cimmyt.utils.Constants.LABSTUDY_SERVICE_BEAN_NAME;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MEN_NOT_INF_M;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_SELECT_RECORD;
import static com.cimmyt.utils.Constants.LOCALE_LANGUAGE;
import static com.cimmyt.utils.Constants.SAMPLE_DETAIL_SERVICE_BEAN_NAME;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.cimmyt.constants.PlateType;
import com.cimmyt.model.bean.LabStudy;
import com.cimmyt.model.bean.SampleDetail;
import com.cimmyt.service.ServiceLabStudy;
import com.cimmyt.service.ServiceSampleDetail;
import com.cimmyt.utils.PropertyHelper;
import com.cimmyt.utils.StrUtils;

@SuppressWarnings("serial")
public class ControlWindowSearch extends Window {

	private static ServiceLabStudy serviceLabStudy;
	private static ServiceSampleDetail serviceSampleDetail;
	private Textbox idTeSearh;
	private Listbox idListBS;
	private Label idCountSize;
	private Listbox idListBR;
	private Label idSelectSize;
	private Listbox idListS;
	private List<SampleDetail> selectedSamples = new ArrayList<SampleDetail>();
	private PropertyHelper pro=null;
	static {
		if(serviceLabStudy == null)
        {
			try{
				serviceLabStudy = (ServiceLabStudy)SpringUtil.getBean(LABSTUDY_SERVICE_BEAN_NAME);
				}catch (Exception e){
				e.printStackTrace();
			}
        }
		
	}
	static {
		if(serviceSampleDetail == null)
        {
			try{
				serviceSampleDetail = (ServiceSampleDetail)SpringUtil.getBean(SAMPLE_DETAIL_SERVICE_BEAN_NAME);
				}catch (Exception e){
				e.printStackTrace();
			}
        }
		
	}
	public void closeWindow(){
		getDesktop().removeAttribute(ATTRIBUTE_SAMPLE_LOCATION_LIST );
		Window idWindow = (Window)getFellow("idWindowS");
		idWindow.onClose();
	}

	/**
	 * Search by filter or by all row
	 */
	public void search (){
		pro = (PropertyHelper)getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);
		loadContext();
		processSearch(idTeSearh.getText());
	}
	
	private void processSearch (String str){
		LabStudy labStudyFilter = new LabStudy();
		labStudyFilter.setTitle(str);
		List<LabStudy> listStudies = serviceLabStudy.getLabStudys(labStudyFilter);
		if (listStudies != null && !listStudies.isEmpty()){
			while (!idListBS.getItems().isEmpty()){
				idListBS.getItems().remove(0);
			}
			for (LabStudy lab : listStudies){
				loatItem(lab);
			}
		}
	}

	private void loatItem (LabStudy bean){
		Listitem lIt = new Listitem ();
		Listcell cell1 = new Listcell(bean.getLabstudyid().toString());
		lIt.appendChild(cell1);
		Listcell cell2 = new Listcell(bean.getPrefix());
		lIt.appendChild(cell2);
		Listcell cell3 = new Listcell(bean.getTitle());
		lIt.appendChild(cell3);
		Listcell cell4 = new Listcell(StrUtils.getDateFormat(bean.getEnddate()));
		lIt.appendChild(cell4);
		Listcell cell5 = new Listcell(StrUtils.getDateFormat(bean.getStartdate()));
		lIt.appendChild(cell5);
		lIt.setValue(bean);
		idListBS.appendChild(lIt);
	}
	private void loadContext(){
		idTeSearh = (Textbox)getFellow("idTeSearh");
		idListBS = (Listbox)getFellow("idListBS");
		idCountSize = (Label)getFellow("idCountSize");
		idListBR = (Listbox)getFellow("idListBR");
		idSelectSize = (Label)getFellow("idSelectSize");
		idListS = (Listbox)getFellow("idListS");
	}

	public void loadListLeft(){
		pro = (PropertyHelper)getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);
		loadContext();
		clearList(idListBR);
		LabStudy bean = idListBS.getSelectedItem().getValue();

		List<Integer> currentSelection = new ArrayList<Integer>();
		if (selectedSamples != null && !selectedSamples.isEmpty()){
			for (SampleDetail detail : selectedSamples) {
				currentSelection.add(detail.getStudysampleid());
			}
		}
		List<SampleDetail> availableSamples = serviceSampleDetail.
				getSamplesByStudyUsedInStorageLocation(bean.getLabstudyid(),currentSelection,0,0);
		if (availableSamples != null && !availableSamples.isEmpty()){
			for (SampleDetail sample : availableSamples){
				loadItemListLeft(sample);
			}
			idCountSize.setValue(String.valueOf(availableSamples.size()));
		}else {
			idCountSize.setValue(String.valueOf(0));
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
					String.valueOf(bean.getSamplegid()));
			lIt.appendChild(cell5);
		}else {
			lIt.appendChild(new Listcell());
		}
		lIt.setValue(bean);
		idListBR.appendChild(lIt);
	}
	private void clearList(Listbox list){
		while (!list.getItems().isEmpty()){
			list.getItems().remove(0);
		}
	}

	public void moveSamles (){
		pro = (PropertyHelper)getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);
		loadContext();
		if (isEmptyList(idListBR)){
			return;
		}
		if (!idListBR.getSelectedItems().isEmpty()){
			move(idListBR, idListS);
			idCountSize.setValue(String.valueOf(idListBR.getItems().size()));
			idSelectSize.setValue(String.valueOf(idListS.getItems().size()));
		}else {
			StrUtils.messageBox(pro.getKey(LBL_GENERIC_MESS_SELECT_RECORD), pro);
		}
	}
	
	public void removeSamples (){
		pro = (PropertyHelper)getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);
		loadContext();
		if (isEmptyList(idListS)){
			return;
		}
		if (!idListS.getSelectedItems().isEmpty()){
			move(idListS, idListBR);
			idCountSize.setValue(String.valueOf(idListBR.getItems().size()));
			idSelectSize.setValue(String.valueOf(idListS.getItems().size()));
		}else {
			StrUtils.messageBox(pro.getKey(LBL_GENERIC_MESS_SELECT_RECORD), pro);
		}
	}
	private void move(Listbox list1 , Listbox list2){
			List<String> platenames= new ArrayList<String>();
			List<Listitem> samplesAdd= new ArrayList<Listitem>();
			List<Listitem> samplesRemove=new ArrayList<Listitem>();
			boolean platenameencontrado;
			for (Listitem itemR : list1.getSelectedItems()){
				SampleDetail sampledet = itemR.getValue();
				if (sampledet.getLabstudyid().getPlatetype().equals(PlateType.RACK.getId())){
					platenameencontrado=false;
					for(int i=0; i< platenames.size();i++){
						if(platenames.get(i).equals(sampledet.getPlatename())){
							platenameencontrado=true;
							break;
						}
					}
					if (platenameencontrado==false){
						platenames.add(sampledet.getPlatename());
						for(Listitem itemListRT:list1.getItems()){
							SampleDetail eachsample = itemListRT.getValue();
							if(eachsample.getPlatename().equals(sampledet.getPlatename())){
								samplesAdd.add(itemListRT);
								selectedSamples.add(eachsample);
								samplesRemove.add(itemListRT);
							}
						}
					}
				}else{// Tubos separados
					samplesAdd.add(itemR);
					selectedSamples.add(sampledet);
					samplesRemove.add(itemR);
				}
			}
				list2.getChildren().addAll(samplesAdd);
				list1.getChildren().removeAll(samplesRemove);
	}
	public void moveAll(){
		pro = (PropertyHelper)getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);
		loadContext();
		List<Listitem> listItem = new ArrayList<Listitem>();
		if (isEmptyList(idListBR)){
			return;
		}
		for (Listitem item : idListBR.getItems()){
			listItem.add(item);
			selectedSamples.add((SampleDetail)item.getValue());
		}
		idListS.getChildren().addAll(listItem);
		idListBR.getChildren().removeAll(listItem);
		idCountSize.setValue(String.valueOf(idListBR.getItems().size()));
		idSelectSize.setValue(String.valueOf(idListS.getItems().size()));
	}

	public void removeAll(){
		pro = (PropertyHelper)getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);
		loadContext();
		List<Listitem> listItem = new ArrayList<Listitem>();
		if (isEmptyList(idListS)){
			return;
		}
		for (Listitem item : idListS.getItems()){
			listItem.add(item);
			selectedSamples.remove((SampleDetail)item.getValue());
		}
		idListBR.getChildren().addAll(listItem);
		idListS.getChildren().removeAll(listItem);
		idCountSize.setValue(String.valueOf(idListBR.getItems().size()));
		idSelectSize.setValue(String.valueOf(idListS.getItems().size()));
	}

	public void loadContextComponent(){
		pro = (PropertyHelper)getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);
		if (getDesktop().getAttribute(ATTRIBUTE_SAMPLE_LOCATION_LIST) != null){
			 @SuppressWarnings("unchecked")
			List<SampleDetail> selectedSamples = (List<SampleDetail>) 
					 getDesktop().getAttribute(ATTRIBUTE_SAMPLE_LOCATION_LIST);
			 if (selectedSamples != null && !selectedSamples.isEmpty()){
				 this.selectedSamples = selectedSamples;
			 }
		}
	}

	private boolean isEmptyList(Listbox list){
		if (list.getItems().isEmpty()){
			StrUtils.messageBox(pro.getKey(LBL_GENERIC_MEN_NOT_INF_M), pro);
			return true;
		}
		return false;
	}

	public void addSampleDetail(){
		getDesktop().setAttribute(ATTRIBUTE_SAMPLE_LOCATION_LIST,selectedSamples );
		Window idWindow = (Window)getFellow("idWindowS");
		idWindow.onClose();
	}

	
}
