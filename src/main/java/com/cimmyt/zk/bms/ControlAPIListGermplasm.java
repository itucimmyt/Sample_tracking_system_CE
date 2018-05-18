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
package com.cimmyt.zk.bms;

import static com.cimmyt.utils.Constants.ATTRIBUTE_NAME_USER_BEAN;
import static com.cimmyt.utils.Constants.BMS_SERVICE_API;
import static com.cimmyt.utils.Constants.BMS_SERVICE_CLIENT;
import static com.cimmyt.utils.Constants.LBL_BMS_ADD_SAMPLES;
import static com.cimmyt.utils.Constants.LBL_BMS_COL_LIST_HEAD_DETAIL_CROOS;
import static com.cimmyt.utils.Constants.LBL_BMS_COL_LIST_HEAD_SEED_SOURCE;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MEN_COL_GID;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_FILL_FIELD;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_INFORMATION;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_SELECT_RECORD;
import static com.cimmyt.utils.Constants.LBL_GENERIC_SHOW_INFORMATION;
import static com.cimmyt.utils.Constants.LOCALE_LANGUAGE;
import static com.cimmyt.utils.Constants.LOCATION_SERVICE_BEAN_NAME;
import static com.cimmyt.utils.Constants.SEASON_SERVICE_BEAN_NAME;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.wso2.client.api.ApiClient;
import org.wso2.client.api.ApiException;
import org.wso2.client.api.Brapi_Multicrop.StudiesApi;
import org.wso2.client.model.Brapi_Multicrop.BrEntryTO;
import org.wso2.client.model.Brapi_Multicrop.BrStudyTO;
import org.wso2.client.model.Brapi_Multicrop.BrapiPagedResponseBrEntryTO;
import org.wso2.client.model.Brapi_Multicrop.BrapiPagedResponseBrStudyTO;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Detail;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Window;

import com.cimmyt.bean.LocationBean;
import com.cimmyt.bean.SeasonBean;
import com.cimmyt.bean.UserBean;
import com.cimmyt.bms.client.GermplasmDetailEventListener;
import com.cimmyt.bms.client.dto.Germplasm;
import com.cimmyt.bms.service.BMSService;
import com.cimmyt.model.bean.LocationCatalog;
import com.cimmyt.model.bean.Season;
import com.cimmyt.service.ServiceLocation;
import com.cimmyt.service.ServiceSeason;
import com.cimmyt.utils.PropertyHelper;
import com.cimmyt.utils.StrUtils;
@SuppressWarnings("serial")
public class ControlAPIListGermplasm extends Window{
	
	private static BMSService serviceBMSClient = null;
	private PropertyHelper pro=null;
	private Grid  idGridGermplasm;
	private UserBean userBean;
	private Combobox idComboLocation;
	private Combobox idComboSeason;
	private static ServiceLocation serviceLocation;
	private static ServiceSeason serviceSeason;
	private StudiesApi api = new StudiesApi();
	ApiClient apicliente = new ApiClient();
	private List<Germplasm> listGermplasmSelect = new ArrayList<Germplasm>(); 
	private List<BrEntryTO> listgermplasmEntries = new ArrayList<BrEntryTO>();

	static {
	if (serviceBMSClient == null ){
		 try{
			 serviceBMSClient = (BMSService)SpringUtil.getBean(BMS_SERVICE_CLIENT);
		 	}catch (Exception ex){
		 		ex.printStackTrace();
		 	}	
		}
	}

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
	static {
		if(serviceSeason == null)
        {
			try{
				serviceSeason = (ServiceSeason)SpringUtil.getBean(SEASON_SERVICE_BEAN_NAME);
				}catch (Exception e){
				e.printStackTrace();
			}
        }
	}
	public void loadContext (){
		pro = (PropertyHelper)getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);
		apicliente.setBasePath(BMS_SERVICE_API);
		loadItem();
		if (getDesktop().getSession().getAttribute(ATTRIBUTE_NAME_USER_BEAN) != null){
			userBean = (UserBean)getDesktop().getSession().getAttribute(ATTRIBUTE_NAME_USER_BEAN);
		}
		api.setApiClient(apicliente);
		//loadDataFromListGermplams();
		loadDataFromListGermplamsBrapi();
		loadComboLocation();
		loadComboSeason();
	}

	
	private void loadDataFromListGermplamsBrapi(){
		try {
			BrapiPagedResponseBrStudyTO  brapiStudy = api.searchStudies(null);
			if (brapiStudy != null && brapiStudy.getResult()!= null && 
					brapiStudy.getResult().getData()!= null && !brapiStudy.getResult().getData().isEmpty()){
				Rows rows = new Rows();
				for (BrStudyTO sto : brapiStudy.getResult().getData()){
					Row row = new Row();
					
					//row.appendChild( getCellNamePlusCheckbox(germplasm.getListName(),row));
					row.appendChild( new Label(sto.getStudyName()));
					row.appendChild( new Label(sto.getTrialName()));
					row.appendChild( new Label(sto.getLocationName()));
					//row.setValue(germplasm);
					
					Detail detail = new Detail();
					detail.addEventListener("onClick", new GermplasmList(sto.getStudyDbId()) );
					row.appendChild(detail);
					rows.appendChild(row);
				}
				idGridGermplasm.appendChild(rows);
				idGridGermplasm.setMold("paging");
				idGridGermplasm.setPageSize(20);
				idGridGermplasm.setPagingPosition("both");
				idGridGermplasm.setSizedByContent(true);
				
			}else{
				StrUtils.messageBox(pro.getKey(LBL_GENERIC_SHOW_INFORMATION), pro);
			}
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void loadDataFromListGermplams(){
		List<Germplasm> listGermplams = serviceBMSClient.getListGermplasm(BMS_SERVICE_API, userBean.getCorp().toLowerCase());
		if (listGermplams != null && !listGermplams.isEmpty()){
			Rows rows = new Rows();
			for (Germplasm germplasm : listGermplams){
				Row row = new Row();
				
				row.appendChild( getCellNamePlusCheckbox(germplasm.getListName(),row));
				row.appendChild( new Label(germplasm.getDescription()));
				row.appendChild( new Label(germplasm.getNotes()));
				row.appendChild( new Label(germplasm.getListSize()+""));
				row.setValue(germplasm);
				
				Detail detail = new Detail();
				detail.addEventListener("onClick", new GermplasmDetailEventListener() );
				row.appendChild(detail);
				rows.appendChild(row);
				
			}
			idGridGermplasm.appendChild(rows);
			idGridGermplasm.setMold("paging");
			idGridGermplasm.setPageSize(20);
			idGridGermplasm.setPagingPosition("both");
			idGridGermplasm.setSizedByContent(true);
			
		}else
			StrUtils.messageBox(pro.getKey(LBL_GENERIC_SHOW_INFORMATION), pro);
	}

	 private Cell getCellNamePlusCheckbox(String str, final Row row){
		 Cell cell = new Cell();
		 final Checkbox checkbox = new Checkbox();
		 checkbox.addEventListener(Events.ON_CLICK, 
				 new EventListener<Event>() {
	             @Override
	             public void onEvent(Event event) throws Exception {
	            	 if (checkbox.isChecked())
	         	 			listGermplasmSelect.add((Germplasm)row.getValue());
	   	 			else
	     	 				listGermplasmSelect.remove((Germplasm)row.getValue());
	             	}});
		 cell.appendChild(checkbox);
		 cell.appendChild(new Label (str));
		 return cell;
	 }
	
	private void loadItem(){
		idGridGermplasm = (Grid)getFellow("idGridGermplasm");
		idComboLocation = (Combobox)getFellow("idComboLocation");
		idComboSeason = (Combobox)getFellow("idComboSeason");
	}

	private void loadComboLocation (){
		List<LocationBean> beanList =serviceLocation.getLocation(new LocationBean());
		if (beanList != null && !beanList.isEmpty()){
			for (LocationBean bean : beanList){
				Comboitem item = new Comboitem(bean.getLocation_name());
				item.setValue(bean.getLocationBean(bean));
				idComboLocation.appendChild(item);
			}
		}
	}

	private void loadComboSeason (){
		List<SeasonBean> beanList =serviceSeason.getSeasons(new SeasonBean());
		if (beanList != null && !beanList.isEmpty()){
			for (SeasonBean bean : beanList){
				Comboitem item = new Comboitem(bean.getSeason_name());
				item.setValue(bean.getSeason(bean));
				idComboSeason.appendChild(item);
			}
		}
	}

	public void addInformationToServices(){
		System.out.println("list ::  "+listgermplasmEntries.toString());
		if (listGermplasmSelect.isEmpty() && listgermplasmEntries.isEmpty()){
			messageBox(pro.getKey(LBL_GENERIC_MESS_SELECT_RECORD));
			return;
		}
		if (idComboLocation.getSelectedIndex() == -1){
			StrUtils.messageBox(pro.getKey(LBL_GENERIC_MESS_FILL_FIELD), pro);
			return;
		}
		if (idComboSeason.getSelectedIndex() == -1){
			StrUtils.messageBox(pro.getKey(LBL_GENERIC_MESS_FILL_FIELD), pro);
			return;
		}
		if (listGermplasmSelect.isEmpty()){
		int size = serviceBMSClient.saveGermplasm(listGermplasmSelect, userBean, 
				(Season)idComboSeason.getSelectedItem().getValue(), 
				(LocationCatalog)idComboLocation.getSelectedItem().getValue());
		messageBox(pro.getKey(LBL_BMS_ADD_SAMPLES,new String []{String.valueOf(size)}));
		}
		if (listGermplasmSelect.isEmpty()){
			int size = serviceBMSClient.saveGermplasmEntry(listgermplasmEntries, userBean, 
					(Season)idComboSeason.getSelectedItem().getValue(), 
					(LocationCatalog)idComboLocation.getSelectedItem().getValue());
			messageBox(pro.getKey(LBL_BMS_ADD_SAMPLES,new String []{String.valueOf(size)}));
			}
		
		Executions.sendRedirect("/");
	}

	private void messageBox(String mess){
		Messagebox.show(mess,pro.getKey(LBL_GENERIC_MESS_INFORMATION), 
				Messagebox.OK, Messagebox.INFORMATION);
	}
	class GermplasmList implements EventListener<Event>{
		int idStudy ;
		public GermplasmList(int _idStudy){
			idStudy = _idStudy;
		}
		@Override
		public void onEvent(Event event) throws Exception {
			// TODO Auto-generated method stub
			System.out.println("ID study ::::::: "+idStudy);
			BrapiPagedResponseBrEntryTO brapi =api.getGermplasmByStudyId(idStudy, null);
			if (brapi != null && brapi.getResult() != null && 
					brapi.getResult().getData() != null && !brapi.getResult().getData().isEmpty() ){
				event.getTarget().appendChild(getListBox(brapi.getResult().getData()));
			}
			//System.out.println("Api :"+brapi.getResult().getData().get(0).toString());
		}

		private Listbox getListBox(List<BrEntryTO> listgermplasmEntries){
			Listbox listbox = new Listbox();
			Listhead head = new Listhead();
			head.appendChild(getListheader("","5%"));
			head.appendChild(getListheader(pro.getKey(LBL_GENERIC_MEN_COL_GID),"20%"));
			head.appendChild(getListheader(pro.getKey(LBL_BMS_COL_LIST_HEAD_DETAIL_CROOS),"30%"));
			head.appendChild(getListheader(pro.getKey(LBL_BMS_COL_LIST_HEAD_SEED_SOURCE),"20%"));
			head.appendChild(getListheader("Synonyms","25%"));
			head.appendChild(getListheader("Acc. Num","5%"));
			
			listbox.appendChild(head);
			
			for (BrEntryTO germplasmEntries : listgermplasmEntries){
				Listitem item = new Listitem();
				Listcell listcell = new Listcell();
				int gid = getRandom(germplasmEntries.getGermplasmDbId());
				germplasmEntries.setGermplasmDbId(gid);
				listcell.appendChild(getNamePlusCheckbox(germplasmEntries));
				item.appendChild(listcell);
				
				
				item.appendChild(new Listcell(gid+""));
				item.appendChild(new Listcell(germplasmEntries.getPedigree()));
				item.appendChild(new Listcell(germplasmEntries.getSeedSource()));
				item.appendChild(new Listcell(germplasmEntries.getSynonyms().toString()));
				item.appendChild(new Listcell(germplasmEntries.getAccessionNumber()));
				listbox.appendChild(item);
			}
			listbox.setMold("paging");
			listbox.setPageSize(15);
			return listbox;
		}
		 private Checkbox getNamePlusCheckbox(BrEntryTO germ){
			 
			 final Checkbox checkbox = new Checkbox();
			 checkbox.setValue(germ);
			 checkbox.addEventListener(Events.ON_CLICK, 
					 new EventListener<Event>() {
		             @Override
		             public void onEvent(Event event) throws Exception {
		            	// System.out.println("value :: "+((Checkbox)event.getTarget()).getValue());
		            	 if (checkbox.isChecked())
		            		 listgermplasmEntries.add((BrEntryTO)((Checkbox)event.getTarget()).getValue());
		         	 			
		   	 			else
		   	 			listgermplasmEntries.remove((BrEntryTO)((Checkbox)event.getTarget()).getValue());
		     	 				
		             	}});
			
			 return checkbox;
		 }
		private int getRandom(Integer gid){
			if (gid.toString().equals("-1")){
			 Random randomGenerator = new Random();
			 return randomGenerator.nextInt(100000);
			 
			}else return gid;
			 
		}
		private Listheader getListheader(String label, String size){
			Listheader listheader = new Listheader(label);
			listheader.setWidth(size);
			return listheader;
		}
		
	}
}

