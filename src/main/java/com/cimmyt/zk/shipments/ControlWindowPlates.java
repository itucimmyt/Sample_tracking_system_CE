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

import static com.cimmyt.utils.Constants.ATTRIBUTE_NAME_USER_BEAN;
import static com.cimmyt.utils.Constants.ATTRIBUTE_SAMPLE_LOCATION_LIST;
import static com.cimmyt.utils.Constants.ATTRIBUTE_SHIPMENTS_ITEM;
import static com.cimmyt.utils.Constants.LABSTUDY_SERVICE_BEAN_NAME;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MEN_NOT_INF_M;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_INFORMATION;
import static com.cimmyt.utils.Constants.LBL_SHIPMENT_SAVE_SUCCESS;
import static com.cimmyt.utils.Constants.LBL_SHIPMENT_NO_SELECTION;
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
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.PagingEvent;
import org.zkoss.zul.event.ZulEvents;

import com.cimmyt.bean.UserBean;
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
public class ControlWindowPlates extends Window {

	private static ServiceLabStudy serviceLabStudy;
	private static ServiceSampleDetail serviceSampleDetail;
	private static ServiceShipmentSet serviceShipmentSet;
	private static ServiceShipmentDetail serviceShipmentDetail;
    private static UserBean userBean;
	private Textbox idTeSearh;
	/** List of the Studies */
	private Listbox idListBS;
	private Label idCountSize;
	/** List of the Samples in a Study */
	private Listbox idListBR;
	private Label idSelectSize;
	/** List of the selected Samples */
	private Listbox idListS;
	private Paging idPaging;
	private List<String> selectedPlates = new ArrayList<String>();
	private PropertyHelper pro=null;
	private ShipmentSet bean;
	
	boolean isEditMode;
	private boolean ascending = false;
	private String lastColumnSorted = "startdate";

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

		if (getDesktop().getSession().getAttribute(ATTRIBUTE_NAME_USER_BEAN) != null){
			userBean = (UserBean)getDesktop().getSession().getAttribute(ATTRIBUTE_NAME_USER_BEAN);
		}

		if(shipSet == null){
			
		}else{
			idListBR.getItems().clear();
			idListS.getItems().clear();
			if(isEditMode){
				selectedPlates = serviceSampleDetail.getPlatesInShipmentSet(shipSet);
				loadItemListPlates(selectedPlates, idListS);
			}
		}
		
		idPaging.addEventListener(ZulEvents.ON_PAGING, new EventListener<PagingEvent>() {
			@Override
			public void onEvent(PagingEvent event) throws Exception {
				idListBS.getItems().clear();
				searchLabStudies(event.getActivePage()*SHOW_ROWS_LIST,false);
			}
		});
		searchLabStudies(0,true);
	}

	private void loadContext(){
		idTeSearh = (Textbox)getFellow("idTeSearh");
		idListBS = (Listbox)getFellow("idListBS");
		idCountSize = (Label)getFellow("idCountSize");
		idListBR = (Listbox)getFellow("idListBR");
		idSelectSize = (Label)getFellow("idSelectSize");
		idListS = (Listbox)getFellow("idListS");
		idPaging = (Paging)this.getFellow("idPaging");
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
			int rows = serviceLabStudy.getLabStudysByIdResearchTotal(labStudyFilter,userBean.getInvestigatorBean().getInvestigatorid());
			idPaging.setPageSize(SHOW_ROWS_LIST);
			idPaging.setActivePage(0);
			idPaging.setTotalSize(rows);
		}
		List<LabStudy> listStudies = serviceLabStudy.getLabStudysByIdResearch(labStudyFilter,
				userBean.getInvestigatorBean().getInvestigatorid(), 
				firstResult, SHOW_ROWS_LIST, lastColumnSorted, ascending);
		loadItemLabStudies(listStudies);
	}

	/**
	 * Creates a row in the listBox for a given Study
	 * @param bean Tha Lab Study to be displayed.
	 */
	private void loadItemLabStudies (List<LabStudy> beans){
		if (beans != null && !beans.isEmpty()){
			idListBS.getItems().clear();
			for (LabStudy bean : beans){
				Listitem lIt = new Listitem ();
				Listcell cell2 = new Listcell(bean.getPrefix());
				lIt.appendChild(cell2);
				Listcell cell3 = new Listcell(bean.getTitle());
				lIt.appendChild(cell3);
				Listcell cell4 = new Listcell(StrUtils.getDateFormat(bean.getStartdate()));
				lIt.appendChild(cell4);
				Listcell cell5 = new Listcell(StrUtils.getDateFormat(bean.getEnddate()));
				lIt.appendChild(cell5);
				lIt.setValue(bean);
				idListBS.appendChild(lIt);
			}
		}
	}

	/**
	 * Loads plates in a Listbox
	 * @param plateList The list of Samples to display
	 * @param component The listbox that will contain the samples.
	 */
	private void loadItemListPlates (List<String> plateList, Listbox component){
		for(String plate : plateList){
			component.appendChild(new Listitem (plate));
		}
		setItemCounters();
	}

	public void movePlates(boolean isAdd, boolean isAll){
		Listbox source = (isAdd ? idListBR : idListS);
		Listbox target = (isAdd ? idListS : idListBR);
		
		if (isEmptyList(source)){
			return;
		}
		if(isAll){
			target.getItems().addAll(new ArrayList<Listitem>(source.getItems()));
			source.getItems().clear();
		}else{
			target.getItems().addAll(new ArrayList<Listitem>(source.getSelectedItems()) );
			source.getItems().remove(source.getSelectedItems());
		}

		selectedPlates.clear();
		for (Listitem item : idListS.getItems()){
			selectedPlates.add(item.getLabel());
		}

		loadAvailablePlates(true);
	}

	/**
	 * Loads the plates from a Study not assigned to a shipment yet. 
	 */
	public void loadAvailablePlates(boolean recountRows){
		idListBR.getItems().clear();
		
		LabStudy bean;
		if(idListBS.getSelectedItem() == null)
			bean = new LabStudy();
		else
			bean = idListBS.getSelectedItem().getValue();
		

		List<String> availablePlates = serviceSampleDetail.getPlatesNotInShipmentSet(bean.getLabstudyid(),selectedPlates);
		if (availablePlates != null && !availablePlates.isEmpty()){
			loadItemListPlates(availablePlates, idListBR);
			
		}
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
	public void addShipmentSet(Event event){
		getDesktop().setAttribute(ATTRIBUTE_SAMPLE_LOCATION_LIST,selectedPlates );
		bean = (ShipmentSet) getDesktop().getAttribute(ATTRIBUTE_SHIPMENTS_ITEM);
		if(selectedPlates!=null && selectedPlates.size()>0){

			if(isEditMode){
				//hibernate session lost, so cannot directly update.
				updateSampleDetailStatus(bean,LBL_SHIPMENT_STATUS_NO_SELECT);
				serviceShipmentDetail.deleteAll(serviceShipmentDetail.getShipmentDetails(bean));
				bean.setStShipmentDetails(null);
			}

			Set<ShipmentDetail> shipDets = new HashSet<ShipmentDetail>();
			for(String plateName : selectedPlates){
			List<SampleDetail> selectedSampleIds = serviceSampleDetail.getSamplesByPlate(plateName);
				for(SampleDetail sampleDetail : selectedSampleIds){
					ShipmentDetail shipDetail = new ShipmentDetail();
					shipDetail.setStSampleDetail(sampleDetail);
					shipDetail.setStShipmentSet(bean);
					shipDets.add(shipDetail);
				}
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
			
		}else{
			Messagebox.show(pro.getKey(LBL_SHIPMENT_NO_SELECTION), 
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
		idCountSize.setValue(String.valueOf(idListBR.getItemCount()));
		idSelectSize.setValue(String.valueOf(idListS.getItemCount()));
	}

	public void onSorting(Event event){
		Listheader header = (Listheader)event.getTarget();
		if(header.getValue().equals(lastColumnSorted)){
			ascending = !ascending;
		}else{
			ascending = true;
		}
		lastColumnSorted = header.getValue();
		
		getAndDrawStudies(null,false, idPaging.getActivePage());
		
	}
	private void getAndDrawStudies(LabStudy study, boolean recountTotal, int page){
		List<LabStudy> listBean;
		
		if(recountTotal){
			int rows = serviceLabStudy.getTotalRowsByIdResearch(study,userBean.getInvestigatorBean().getInvestigatorid());
			idPaging.setPageSize(SHOW_ROWS_LIST);
			idPaging.setActivePage(0);
			idPaging.setTotalSize(rows);
			//everytime a new search is done, reset sorting. Paging is not considered a new search.
			lastColumnSorted = null;
			ascending = true;
		}

			listBean = serviceLabStudy.getLabStudysByIdResearch( (study == null ? new LabStudy() : study), 
					userBean.getInvestigatorBean().getInvestigatorid(),page * SHOW_ROWS_LIST,
					SHOW_ROWS_LIST, lastColumnSorted, ascending);

		loadItemLabStudies(listBean);

	}

}
