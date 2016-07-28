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
package com.cimmyt.zk.shipments;

import static com.cimmyt.utils.Constants.ATTRIBUTE_SAMPLE_LOCATION_LIST;
import static com.cimmyt.utils.Constants.ATTRIBUTE_SHIPMENTS_ITEM;
import static com.cimmyt.utils.Constants.LABSTUDY_SERVICE_BEAN_NAME;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MEN_NOT_INF_M;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_INFORMATION;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_SELECT_RECORD;
import static com.cimmyt.utils.Constants.LBL_SHIPMENT_SAVE_SUCCESS;
import static com.cimmyt.utils.Constants.LBL_SHIPMENT_STATUS_FOR_SEND;
import static com.cimmyt.utils.Constants.LBL_SHIPMENT_STATUS_NO_SELECT;
import static com.cimmyt.utils.Constants.LOCALE_LANGUAGE;
import static com.cimmyt.utils.Constants.SAMPLE_DETAIL_SERVICE_BEAN_NAME;
import static com.cimmyt.utils.Constants.SHIPMENT_SERVICE_DETAIL_BEAN_NAME;
import static com.cimmyt.utils.Constants.SHIPMENT_SERVICE_SET_BEAN_NAME;
import static com.cimmyt.utils.Constants.ATTRIBUTE_EDIT_SHIPMENT;
import static com.cimmyt.utils.Constants.SHOW_ROWS_LIST;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.PagingEvent;
import org.zkoss.zul.event.ZulEvents;

import com.cimmyt.constants.PlateType;
import com.cimmyt.model.bean.LabStudy;
import com.cimmyt.model.bean.SampleDetail;
import com.cimmyt.model.bean.ShipmentDetail;
import com.cimmyt.model.bean.ShipmentSet;
import com.cimmyt.service.ServiceLabStudy;
import com.cimmyt.service.ServiceSampleDetail;
import com.cimmyt.service.ServiceShipmentDetail;
import com.cimmyt.service.ServiceShipmentSet;
import com.cimmyt.utils.PropertyHelper;
import com.cimmyt.utils.StrUtils;

@SuppressWarnings("serial")
public class ControlWindowSearch extends Window {

	private static ServiceLabStudy serviceLabStudy;
	private static ServiceSampleDetail serviceSampleDetail;
	private static ServiceShipmentSet serviceShipmentSet;
	private static ServiceShipmentDetail serviceShipmentDetail;
	private Textbox idTeSearh;
	/** List of the Studies */
	private Listbox idListBS;
	private Label idCountSize;
	/** List of the Samples in a Study */
	private Listbox idListBR;
	private Label idSelectSize;
	/** List of the selected Samples */
	private Listbox idListS;
	private Paging idPaging
		,idPagingSample1
		,idPagingSample2;
	private List<SampleDetail> selectedSamples = new ArrayList<SampleDetail>();
	private PropertyHelper pro=null;
	private ShipmentSet bean;
	private int SHOW_ROWS_LIST_SAMPLES = 384;
	
	boolean isEditMode;
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
	static {
		if(serviceShipmentSet == null)
        {
			try{
				serviceShipmentSet = (ServiceShipmentSet) SpringUtil.getBean(SHIPMENT_SERVICE_SET_BEAN_NAME);
				}catch (Exception e){
				e.printStackTrace();
			}
        }
	}
	static {
		if(serviceShipmentDetail == null)
        {
			try{
				serviceShipmentDetail = (ServiceShipmentDetail) SpringUtil.getBean(SHIPMENT_SERVICE_DETAIL_BEAN_NAME);
				}catch (Exception e){
				e.printStackTrace();
			}
        }
	}

	public void loadContextComponent(){
		pro = (PropertyHelper)getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);
		
		loadContext();

		ShipmentSet shipSet = (ShipmentSet)getDesktop().getAttribute(ATTRIBUTE_SHIPMENTS_ITEM);
		isEditMode = getDesktop().hasAttribute(ATTRIBUTE_EDIT_SHIPMENT) && 
				(Boolean)getDesktop().getAttribute(ATTRIBUTE_EDIT_SHIPMENT);
		
		if(shipSet == null){
			
		}else{
			idListBR.getItems().clear();
			idListS.getItems().clear();
			if(isEditMode){
				selectedSamples = serviceSampleDetail.getSamplesByShipmentSet(shipSet);
				loadItemListSamples(selectedSamples, idListS);
				loadListRight(0);
			}
		}
		
		idPaging.addEventListener(ZulEvents.ON_PAGING, new EventListener<PagingEvent>() {
			@Override
			public void onEvent(PagingEvent event) throws Exception {
				idListBS.getItems().clear();
				searchLabStudies(event.getActivePage()*SHOW_ROWS_LIST,false);
			}
		});

		idPagingSample1.addEventListener(ZulEvents.ON_PAGING, new EventListener<PagingEvent>() {
			@Override
			public void onEvent(PagingEvent event) throws Exception {
				idListBR.getItems().clear();
				loadListLeft(event.getActivePage()*SHOW_ROWS_LIST_SAMPLES,false);
			}
		});

		idPagingSample2.addEventListener(ZulEvents.ON_PAGING, new EventListener<PagingEvent>() {
			@Override
			public void onEvent(PagingEvent event) throws Exception {
				idListS.getItems().clear();
				loadListRight(event.getActivePage());
			}
		});
	}

	private void loadContext(){
		idTeSearh = (Textbox)getFellow("idTeSearh");
		idListBS = (Listbox)getFellow("idListBS");
		idCountSize = (Label)getFellow("idCountSize");
		idListBR = (Listbox)getFellow("idListBR");
		idSelectSize = (Label)getFellow("idSelectSize");
		idListS = (Listbox)getFellow("idListS");
		idPaging = (Paging)this.getFellow("idPaging");
		idPagingSample1 = (Paging)this.getFellow("idPagingSample1");
		idPagingSample2 = (Paging)this.getFellow("idPagingSample2");
	}

	public void closeWindow(){
		getDesktop().removeAttribute(ATTRIBUTE_SAMPLE_LOCATION_LIST );
		getDesktop().removeAttribute(ATTRIBUTE_SHIPMENTS_ITEM );
		getDesktop().setAttribute(ATTRIBUTE_EDIT_SHIPMENT, false);
		this.onClose();
	}
	
	/**
	 * Search studies by filter. If no text is captured, displays all studies. 
	 */
	public void searchLabStudies (int firstResult,boolean recountRows){
		LabStudy labStudyFilter = new LabStudy();
		labStudyFilter.setTitle(idTeSearh.getText().trim());
		
		if(recountRows){
			int rows = serviceLabStudy.getLabStudysByIdResearchTotal(labStudyFilter,null);
			idPaging.setPageSize(SHOW_ROWS_LIST);
			idPaging.setActivePage(0);
			idPaging.setTotalSize(rows);
		}
		List<LabStudy> listStudies = serviceLabStudy.getLabStudysByIdResearch(labStudyFilter, null, firstResult, SHOW_ROWS_LIST, null, true);

		if (listStudies != null && !listStudies.isEmpty()){
			idListBS.getItems().clear();
			for (LabStudy lab : listStudies){
				loadItemLabStudy(lab);
			}
		}
	}

	/**
	 * Creates a row in the listBox for a given Study
	 * @param bean Tha Lab Study to be displayed.
	 */
	private void loadItemLabStudy (LabStudy bean){
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

	/**
	 * Loads the samples from a Study
	 */
	public void loadListLeft(int firstResult, boolean recountRows){
		idListBR.getItems().clear();
		LabStudy bean = idListBS.getSelectedItem().getValue();

		List<Integer> currentSelection = new ArrayList<Integer>();
		if (selectedSamples != null && !selectedSamples.isEmpty()){
			for (SampleDetail detail : selectedSamples) {
				currentSelection.add(detail.getStudysampleid());
			}
		}

		if(recountRows){
			int rows = serviceSampleDetail.
						getSamplesByStudyUsedInStorageLocationTotal(bean.getLabstudyid(),currentSelection);
			idPagingSample1.setPageSize(SHOW_ROWS_LIST_SAMPLES);
			idPagingSample1.setActivePage(0);
			idPagingSample1.setTotalSize(rows);
		}
		
		
		List<SampleDetail> availableSamples = serviceSampleDetail.
				getSamplesByStudyUsedInStorageLocation(bean.getLabstudyid(),currentSelection,firstResult, SHOW_ROWS_LIST_SAMPLES);
		if (availableSamples != null && !availableSamples.isEmpty()){
			loadItemListSamples(availableSamples, idListBR);
			
		}
		setItemCounters();
	}
	/**
	 * Loads samples in a Listbox
	 * @param beanList The list of Samples to display
	 * @param component The listbox that will contain the samples.
	 */
	private void loadItemListSamples (List<SampleDetail> beanList, Listbox component){
		for(SampleDetail bean : beanList){
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
			component.appendChild(lIt);
		}
		setItemCounters();
	}

	public void moveSamles (){
		if (isEmptyList(idListBR)){
			return;
		}
		if (!idListBR.getSelectedItems().isEmpty()){
			move(idListBR, idListS);
		}else {
			StrUtils.messageBox(pro.getKey(LBL_GENERIC_MESS_SELECT_RECORD), pro);
		}
	}
	
	public void removeSamples (){
		if (isEmptyList(idListS)){
			return;
		}
		if (!idListS.getSelectedItems().isEmpty()){
			move(idListS, idListBR);
		}else {
			StrUtils.messageBox(pro.getKey(LBL_GENERIC_MESS_SELECT_RECORD), pro);
		}
	}
	/**
	 * Moves ListItems between Listboxes.
	 * @param source Source, where the items will be taken and removed.
	 * @param target Target, where the items will be added.
	 */
	private void move(Listbox source , Listbox target){
			List<String> platenames= new ArrayList<String>();
			List<Listitem> samplesAdd= new ArrayList<Listitem>();
			List<Listitem> samplesRemove=new ArrayList<Listitem>();
			boolean platenameencontrado;
			for (Listitem itemR : source.getSelectedItems()){
				SampleDetail sampledet = itemR.getValue();
				if (sampledet.getLabstudyid().getPlatetype().equals(PlateType.RACK.getId())){
					platenameencontrado=false;

					if (!platenameencontrado){
						platenames.add(sampledet.getPlatename());
						for(Listitem itemListRT:source.getItems()){
							SampleDetail eachsample = itemListRT.getValue();
							if(eachsample.getPlatename().equals(sampledet.getPlatename())){
								samplesAdd.add(itemListRT);
								if(source.getId().equals(idListS.getId())){
									selectedSamples.remove(eachsample);
								}else{
									selectedSamples.add(eachsample);
								}
								samplesRemove.add(itemListRT);
							}
						}
					}
				}else{// Tubos separados
					samplesAdd.add(itemR);
					if(source.getId().equals(idListS.getId())){
						selectedSamples.remove(sampledet);
					}else{
						selectedSamples.add(sampledet);
					}
						
					samplesRemove.add(itemR);
				}
			}
				target.getChildren().addAll(samplesAdd);
				source.getChildren().removeAll(samplesRemove);
				loadListLeft(0, true);
				loadListRight(0);
				
	}
	public void moveAll(){
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
		loadListLeft(0, true);
		loadListRight(0);
		setItemCounters();
	}

	public void removeAll(){
		List<Listitem> listItem = new ArrayList<Listitem>();
		if (isEmptyList(idListS)){
			return;
		}
		listItem.addAll(idListS.getItems());

		for (Listitem item : idListS.getItems()){
			selectedSamples.remove(item.getValue());
		}
		
		idListBR.getChildren().addAll(listItem);
		idListS.getChildren().removeAll(listItem);
		loadListLeft(0, true);
		loadListRight(0);
		setItemCounters();
	}


	private boolean isEmptyList(Listbox list){
		if (list.getItems().isEmpty()){
			StrUtils.messageBox(pro.getKey(LBL_GENERIC_MEN_NOT_INF_M), pro);
			return true;
		}
		return false;
	}

	/**
	 * Saves the Shipment Set along with the samples assigned to it. 
	 * @param event The event that triggers the method.
	 */
	public void addSampleDetail(Event event){
		getDesktop().setAttribute(ATTRIBUTE_SAMPLE_LOCATION_LIST,selectedSamples );
		bean = (ShipmentSet) getDesktop().getAttribute(ATTRIBUTE_SHIPMENTS_ITEM);
		if(selectedSamples!=null){

			if(isEditMode){
				//hibernate session lost, so cannot directly update.
				updateSampleDetailStatus(bean,LBL_SHIPMENT_STATUS_NO_SELECT);
				serviceShipmentDetail.deleteAll(serviceShipmentDetail.getShipmentDetails(bean));
				bean.setStShipmentDetails(null);
			}

			Set<ShipmentDetail> shipDets = new HashSet<ShipmentDetail>();
			
			for(SampleDetail stSampleDetail : selectedSamples){
				ShipmentDetail shipDetail = new ShipmentDetail();
				shipDetail.setStSampleDetail(stSampleDetail);
				shipDetail.setStShipmentSet(bean);
				shipDets.add(shipDetail);
			}

			bean.setStShipmentDetails(shipDets);
			serviceShipmentSet.saveOrUpdateShipmentSet(bean);
			updateSampleDetailStatus(bean,LBL_SHIPMENT_STATUS_FOR_SEND);
			
			ControlShipments shipWindow = (ControlShipments)event.getTarget().getRoot().getFellow("idCenterMenu").getFirstChild().getFirstChild();
			shipWindow.doAfterCompose();

			closeWindow();
			Messagebox.show(pro.getKey(LBL_SHIPMENT_SAVE_SUCCESS), 
					pro.getKey(LBL_GENERIC_MESS_INFORMATION), 
					Messagebox.OK, Messagebox.INFORMATION);
			
		}
		
	}
	
	private void updateSampleDetailStatus(ShipmentSet beanShipSet, char status){
		List<ShipmentDetail> shipDetails = serviceShipmentDetail.getShipmentDetails(beanShipSet);
		List<Integer> shipDetailIds = new ArrayList<Integer>();
		for(ShipmentDetail d : shipDetails){
			shipDetailIds.add(d.getStSampleDetail().getStudysampleid());
		}
		
		serviceSampleDetail.updateListStatus(shipDetailIds, status);

	}

	private void setItemCounters(){
		idCountSize.setValue(String.valueOf(idPagingSample1.getTotalSize()));
		idSelectSize.setValue(String.valueOf(idPagingSample2.getTotalSize()));
	}
	
	private void loadListRight(int pageNum){
		idListS.getItems().clear();
		List<SampleDetail> samples = new ArrayList<SampleDetail>();
		if(!selectedSamples.isEmpty())
			for(int i=SHOW_ROWS_LIST_SAMPLES*pageNum;
					i<SHOW_ROWS_LIST_SAMPLES*pageNum+SHOW_ROWS_LIST_SAMPLES;
					i++){
				if(i>=selectedSamples.size()) break;
				
				samples.add(selectedSamples.get(i));
			}
		
		idPagingSample2.setPageSize(SHOW_ROWS_LIST_SAMPLES);
		idPagingSample2.setActivePage(pageNum);
		idPagingSample2.setTotalSize(selectedSamples.size());
		
		loadItemListSamples(samples, idListS);
	}
}
