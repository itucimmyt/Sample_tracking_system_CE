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

import static com.cimmyt.utils.Constants.ATTRIBUTE_NAME_USER_BEAN;
import static com.cimmyt.utils.Constants.ATTRIBUTE_PARAM_MAP_FUNTION;
import static com.cimmyt.utils.Constants.ATTRIBUTE_PARAM_REPORT;
import static com.cimmyt.utils.Constants.ATTRIBUTE_SAMPLE_LOCATION_LIST;
import static com.cimmyt.utils.Constants.ATTRIBUTE_STORAGE_LOCATION_ITEM;
import static com.cimmyt.utils.Constants.COMPANY_SERVICE_BEAN_NAME;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_DELETE_ERROR;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_DELETE_RECORD;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_DELETE_SUCCESS;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_ERROR;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_ERROR_CRITERIAL;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_INFORMATION;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_PLEASE_CONFIRM;
import static com.cimmyt.utils.Constants.LBL_GENERIC_SHOW_INFORMATION;
import static com.cimmyt.utils.Constants.LBL_GENERIC_STUDY_TEMPLATE;
import static com.cimmyt.utils.Constants.LBL_STORAGE_LOCATION_ADD_LOC;
import static com.cimmyt.utils.Constants.LBL_STORAGE_LOCATION_ASS_COM;
import static com.cimmyt.utils.Constants.LBL_STORAGE_LOCATION_BE_SAM_ASS;
import static com.cimmyt.utils.Constants.LBL_STORAGE_LOCATION_DEF_SYS;
import static com.cimmyt.utils.Constants.LBL_STORAGE_LOCATION_DELETE_LOC;
import static com.cimmyt.utils.Constants.LBL_STORAGE_LOCATION_EDIT_LOC;
import static com.cimmyt.utils.Constants.LBL_STORAGE_LOCATION_LOC_ASS;
import static com.cimmyt.utils.Constants.LBL_STORAGE_LOCATION_SAMPL_ERR_ASS_COM;
import static com.cimmyt.utils.Constants.LBL_STORAGE_LOCATION_SAMPL_ERR_ASS_LOC;
import static com.cimmyt.utils.Constants.LBL_STORAGE_LOCATION_SAMP_ASS;
import static com.cimmyt.utils.Constants.LBL_STORAGE_LOCATION_SELECT_LOC;
import static com.cimmyt.utils.Constants.LBL_STORAGE_LOCATION_WIN_Add;
import static com.cimmyt.utils.Constants.LBL_STORAGE_LOCATION_WIN_EDIT;
import static com.cimmyt.utils.Constants.LOCALE_LANGUAGE;
import static com.cimmyt.utils.Constants.LOCATION_ASSGINED_BY_SYSTEM;
import static com.cimmyt.utils.Constants.SAMPLE_DETAIL_SERVICE_BEAN_NAME;
import static com.cimmyt.utils.Constants.STORAGE_LOCATION_SERVICE_BEAN_NAME;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zkex.zul.Fisheye;
import org.zkoss.zkex.zul.Fisheyebar;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Window;

import com.cimmyt.bean.ReportBarCodeBean;
import com.cimmyt.bean.UserBean;
import com.cimmyt.model.bean.SampleDetail;
import com.cimmyt.model.bean.StorageLocation;
import com.cimmyt.model.bean.StudyTemplate;
import com.cimmyt.reports.bean.ExportReport;
import com.cimmyt.service.ServiceCompany;
import com.cimmyt.service.ServiceSampleDetail;
import com.cimmyt.service.ServiceStorageLocation;
import com.cimmyt.servlet.RedirectServletReport;
import com.cimmyt.servlet.SessionReport;
import com.cimmyt.utils.ConstantsDNA;
import com.cimmyt.utils.PropertyHelper;
import com.cimmyt.utils.StrUtils;

@SuppressWarnings("serial")
public class ControlStorageLocation extends Window {

	
	private Tree tree = null;
	private static ServiceStorageLocation serviceStorageLocation = null;
	private static ServiceCompany serviceCompany = null;
	private static ServiceSampleDetail serviceSampleDetail = null;
	private PropertyHelper pro=null;
    private Logger logger= Logger.getLogger(ControlStorageLocation.class);
    private final String ID_ADD = "storeLocations$idAdd";
    private final String ID_EDIT = "storeLocations$idEdit";
    private final String ID_DELETE = "storeLocations$idDelete";
    private final String ID_BAR_CODE = "storeLocations$idBarCode";
    private final String ID_STORAGE_SAMPLE = "storeLocations$idStorageSample";
    private Fisheyebar fisheyebar;
    private UserBean userBean;
    
	static {
		if(serviceStorageLocation == null)
        {
			try{
				serviceStorageLocation =  (ServiceStorageLocation)SpringUtil.getBean(STORAGE_LOCATION_SERVICE_BEAN_NAME);
			}catch (Exception e){
				e.printStackTrace();
			}
        }
		if (serviceCompany ==  null ){
			try{
				serviceCompany = (ServiceCompany)SpringUtil.getBean(COMPANY_SERVICE_BEAN_NAME);
			}catch (Exception eC){
				eC.printStackTrace();
			}
		}
		if (serviceSampleDetail == null){
			try{
				serviceSampleDetail = (ServiceSampleDetail)SpringUtil.getBean(SAMPLE_DETAIL_SERVICE_BEAN_NAME);
			}catch (Exception eC){
				eC.printStackTrace();
			}
		}
		
	}
	/**
	 * Create components of window 
	 */
	public void doAfterCompose (int size, StorageLocation bean ){
		pro = (PropertyHelper)getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);
		if (getDesktop().getSession().getAttribute(ATTRIBUTE_NAME_USER_BEAN) != null){
			userBean = (UserBean)getDesktop().getSession().getAttribute(ATTRIBUTE_NAME_USER_BEAN);
		}
		 if (userBean!= null && userBean.getRole()!= null && !userBean.getRole().getIdstRol().equals(ConstantsDNA.ROLE_ADMINISTRATOR))
			 loadFisheye();
	}

	@SuppressWarnings("unchecked")
	private void loadFisheye (){
		fisheyebar = (Fisheyebar)getFellow("idFsbStorageLocation");
		Map <String, String> mapFuntions;
		if (getDesktop().getSession().getAttribute(ATTRIBUTE_PARAM_MAP_FUNTION) != null){
			mapFuntions = (Map<String, String>) getDesktop().getSession().getAttribute(ATTRIBUTE_PARAM_MAP_FUNTION);
			if (mapFuntions.get(ID_ADD) == null){
				Fisheye id = (Fisheye)getFellow(ID_ADD);
				fisheyebar.removeChild(id);
			}
			if (mapFuntions.get(ID_EDIT) == null){
				Fisheye id = (Fisheye)getFellow(ID_EDIT);
				fisheyebar.removeChild(id);
			}
			if (mapFuntions.get(ID_DELETE) == null){
				Fisheye id = (Fisheye)getFellow(ID_DELETE);
				fisheyebar.removeChild(id);
			}
			if (mapFuntions.get(ID_BAR_CODE) == null){
				Fisheye id = (Fisheye)getFellow(ID_BAR_CODE);
				fisheyebar.removeChild(id);
			}
			if (mapFuntions.get(ID_STORAGE_SAMPLE) == null){
				Fisheye id = (Fisheye)getFellow(ID_STORAGE_SAMPLE);
				fisheyebar.removeChild(id);
			}
		}
	}
	/**
	 * Add new Season
	 */
	public void add () {
			pro = (PropertyHelper)getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);
			tree = (Tree)getFellow("tree");
			StorageLocationTreeNode storageLocationTreeNode = validateSelectItemTree();
			if (storageLocationTreeNode != null){
				StorageLocation storage = (StorageLocation) storageLocationTreeNode.getData();
				if (!isRulsOKAdd(storage))
					return ;
				showWindow(pro.getKey(LBL_STORAGE_LOCATION_WIN_Add)+ " "+ storage.getLname());
				StorageLocation bean = (StorageLocation)getDesktop().getAttribute(ATTRIBUTE_STORAGE_LOCATION_ITEM);
				if (bean != null){
					bean.setImslocidparent(storage.getImslocid());
			        serviceStorageLocation.add(bean);					
					AdvancedTreeModel contactTreeModel = new AdvancedTreeModel(new StorageLocationList().getRoot());
			        tree.setModel(contactTreeModel);
			        Treeitem item =tree.getSelectedItem();
			        tree.setSelectedItem(item);
					getDesktop().setAttribute(ATTRIBUTE_STORAGE_LOCATION_ITEM, null);
					doAfterCompose(0,null);
				}
			}
	}
	
	private StorageLocationTreeNode validateSelectItemTree(){
		
		if (tree != null && tree.getSelectedItem() != null){
			StorageLocationTreeNode storageLocationTreeNode = (StorageLocationTreeNode)tree.getSelectedItem().getValue();
			return	storageLocationTreeNode;
		}else {
			StrUtils.messageBox(pro.getKey(LBL_STORAGE_LOCATION_SELECT_LOC), pro);
			return null;
		}
	}

	private void showWindow(String title) {
		final Window win = (Window) Executions.createComponents(
    			"/storage_location/window_location.zul", null, null);
		win.setTitle(title);	
    		win.doModal();
	}
	/**
	 * Edit Season
	 */
	public void edit (){
		pro = (PropertyHelper)getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);
		tree = (Tree)getFellow("tree");
		StorageLocationTreeNode storageLocationTreeNode = validateSelectItemTree();
		if (storageLocationTreeNode != null){
			StorageLocation storage = (StorageLocation) storageLocationTreeNode.getData();
			if (!isRulsEdit(storage))
				return;
			getDesktop().setAttribute(ATTRIBUTE_STORAGE_LOCATION_ITEM,storage);
			showWindow(pro.getKey(LBL_STORAGE_LOCATION_WIN_EDIT));
			StorageLocation bean = (StorageLocation)getDesktop().getAttribute(ATTRIBUTE_STORAGE_LOCATION_ITEM);
			if (bean != null){
				serviceStorageLocation.add(bean);					
				AdvancedTreeModel contactTreeModel = new AdvancedTreeModel(new StorageLocationList().getRoot());
		        tree.setModel(contactTreeModel);
		        Treeitem item =tree.getSelectedItem();
		        tree.setSelectedItem(item);
				getDesktop().setAttribute(ATTRIBUTE_STORAGE_LOCATION_ITEM, null);
				doAfterCompose(0,null);
			}
		}
	}

	private boolean  isRulsEdit (StorageLocation storage){
		if (isHasAcompanyAssigned(storage)){
			Messagebox.show(pro.getKey(LBL_STORAGE_LOCATION_EDIT_LOC) +" "+pro.getKey(LBL_STORAGE_LOCATION_ASS_COM), 
					pro.getKey(LBL_GENERIC_MESS_ERROR), 
					Messagebox.OK, Messagebox.ERROR);
			return false;
		}
		if (isHasSamplesAssigned(storage)){
			Messagebox.show(pro.getKey(LBL_STORAGE_LOCATION_EDIT_LOC) +" "+pro.getKey(LBL_STORAGE_LOCATION_SAMP_ASS), 
					pro.getKey(LBL_GENERIC_MESS_ERROR), 
					Messagebox.OK, Messagebox.ERROR);
			return false;
		}
		if (isThisStorageLocationHasChildrens(storage)){
			Messagebox.show(pro.getKey(LBL_STORAGE_LOCATION_EDIT_LOC) +" "+pro.getKey(LBL_STORAGE_LOCATION_LOC_ASS), 
					pro.getKey(LBL_GENERIC_MESS_ERROR), 
					Messagebox.OK, Messagebox.ERROR);
			return false;
		}
		if (storage.getImslocid() == LOCATION_ASSGINED_BY_SYSTEM){
			Messagebox.show(pro.getKey(LBL_STORAGE_LOCATION_EDIT_LOC) +" "+pro.getKey(LBL_STORAGE_LOCATION_DEF_SYS), 
					pro.getKey(LBL_GENERIC_MESS_ERROR), 
					Messagebox.OK, Messagebox.ERROR);
			return false;
		}
		return true;
	}
	private void messageBox(String mess){
		Messagebox.show(mess,pro.getKey(LBL_GENERIC_MESS_INFORMATION), 
				Messagebox.OK, Messagebox.INFORMATION);
		
	}

	public void delete (){
		pro = (PropertyHelper)getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);
		tree = (Tree)getFellow("tree");
		StorageLocationTreeNode storageLocationTreeNode = validateSelectItemTree();
		if (storageLocationTreeNode != null){
			StorageLocation storage = (StorageLocation) storageLocationTreeNode.getData();
			if(!isRulsOkDelete(storage))
				return;
			if (Messagebox.show(pro.getKey(LBL_GENERIC_MESS_DELETE_RECORD), 
					pro.getKey(LBL_GENERIC_MESS_PLEASE_CONFIRM), 
					Messagebox.YES | Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
					try {
						serviceStorageLocation.delete(storage);	
						AdvancedTreeModel contactTreeModel = new AdvancedTreeModel(new StorageLocationList().getRoot());
				        tree.setModel(contactTreeModel);
				        Treeitem item =tree.getSelectedItem();
				        tree.setSelectedItem(item);
						getDesktop().setAttribute(ATTRIBUTE_STORAGE_LOCATION_ITEM, null);
						Messagebox.show(pro.getKey(LBL_GENERIC_MESS_DELETE_SUCCESS), 
								pro.getKey(LBL_GENERIC_MESS_INFORMATION), 
								Messagebox.OK, Messagebox.INFORMATION);
						doAfterCompose(0,null);
					}catch (Exception sql){
						Messagebox.show(pro.getKey(LBL_GENERIC_MESS_DELETE_ERROR,new String []{pro.getKey(LBL_GENERIC_STUDY_TEMPLATE)}), 
								pro.getKey(LBL_GENERIC_MESS_ERROR), 
								Messagebox.OK, Messagebox.ERROR);
						logger.info(sql.getMessage());
					 }
				}
			}
	}

	private boolean isRulsOkDelete(StorageLocation storage){
		if (isHasAcompanyAssigned(storage)){
			Messagebox.show(pro.getKey(LBL_STORAGE_LOCATION_DELETE_LOC) +" "+pro.getKey(LBL_STORAGE_LOCATION_ASS_COM), 
					pro.getKey(LBL_GENERIC_MESS_ERROR), 
					Messagebox.OK, Messagebox.ERROR);
			return false;
		}
		if (isHasSamplesAssigned(storage)){
			Messagebox.show(pro.getKey(LBL_STORAGE_LOCATION_DELETE_LOC) +" "+pro.getKey(LBL_STORAGE_LOCATION_BE_SAM_ASS), 
					pro.getKey(LBL_GENERIC_MESS_ERROR), 
					Messagebox.OK, Messagebox.ERROR);
			return false;
		}
		if (isThisStorageLocationHasChildrens(storage)){
			Messagebox.show(pro.getKey(LBL_STORAGE_LOCATION_DELETE_LOC) +" "+pro.getKey(LBL_STORAGE_LOCATION_LOC_ASS), 
					pro.getKey(LBL_GENERIC_MESS_ERROR), 
					Messagebox.OK, Messagebox.ERROR);
			return false;
		}
		if (storage.getImslocid() == LOCATION_ASSGINED_BY_SYSTEM){
			Messagebox.show(pro.getKey(LBL_STORAGE_LOCATION_DELETE_LOC) +" "+pro.getKey(LBL_STORAGE_LOCATION_DEF_SYS), 
					pro.getKey(LBL_GENERIC_MESS_ERROR), 
					Messagebox.OK, Messagebox.ERROR);
			return false;
		}
		return true;
	}
	private boolean isRulsOKAdd (StorageLocation storage){
		if (isHasAcompanyAssigned(storage)){
			Messagebox.show(pro.getKey(LBL_STORAGE_LOCATION_ADD_LOC) +" "+pro.getKey(LBL_STORAGE_LOCATION_ASS_COM), 
					pro.getKey(LBL_GENERIC_MESS_ERROR), 
					Messagebox.OK, Messagebox.ERROR);
			return false;
		}
		if (isHasSamplesAssigned(storage)){
			Messagebox.show(pro.getKey(LBL_STORAGE_LOCATION_ADD_LOC) +" "+pro.getKey(LBL_STORAGE_LOCATION_BE_SAM_ASS), 
					pro.getKey(LBL_GENERIC_MESS_ERROR), 
					Messagebox.OK, Messagebox.ERROR);
			return false;
		}
		return true;
	}
	
	private boolean isThisStorageLocationHasChildrens (StorageLocation storage){
		return serviceStorageLocation.ThisStorageLocationHaveChildrens(storage);
	}
	private boolean isHasSamplesAssigned (StorageLocation storage){
		Long numSamples = serviceSampleDetail.getCountSamplesByLocation(storage.getImslocid());
		if (numSamples != null && numSamples.intValue() > 0){
			return true;
		}
		return false;
	}
	
	private boolean isHasAcompanyAssigned (StorageLocation storage){
		return serviceCompany.SearchIfAnyHaveThisLocations(storage);
	}
	public void search(){
		pro = (PropertyHelper)getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);
		Textbox idSN = (Textbox)getFellow("idSN");

		boolean notEmty =false;
		StudyTemplate bean = new StudyTemplate();
		if(idSN.getValue()!=null && !idSN.getValue().trim().equals("")){
			notEmty = true;
			bean.setTemplatename(idSN.getValue());
		}
		
		if(notEmty){
			
		}else {
			messageBox(pro.getKey(LBL_GENERIC_MESS_ERROR_CRITERIAL));
		}
	}
	/**
	 * Print Report in Jasper 3.7.2
	 */
	public void printReport (){
		pro = (PropertyHelper)getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);
		ArrayList<ReportBarCodeBean> arr = getListStorageLocation();
		if (arr != null && !arr.isEmpty()){
			ExportReport export = new ExportReport();
			byte [] b1 = export.exportReport(pro, arr);
			SessionReport sessionReport = new SessionReport();
			sessionReport.setB(b1);
			sessionReport.setType(0);
			sessionReport.setName("Report_code");
			
			RedirectServletReport.export(sessionReport);
		}else {
			StrUtils.messageBox(pro.getKey(LBL_GENERIC_SHOW_INFORMATION), pro);
		}
		
		
	}
	private ArrayList<ReportBarCodeBean> getListStorageLocation(){
		ArrayList<ReportBarCodeBean> list = new ArrayList<ReportBarCodeBean>();
		List<StorageLocation> arr = serviceStorageLocation.getAllStorageLocations();
		if (arr != null && !arr.isEmpty()){
			for (StorageLocation stl: arr){
				ReportBarCodeBean report = new ReportBarCodeBean();
				if (stl.getLname() != null && !stl.getLname().trim().equals("")){
					report.setCodeBarCodes(stl.getLname()+stl.getImslocid().toString());
					report.setLname(stl.getLname());
					list.add(report);	
				}
			}
		}
		return list;
	}
	/**
	 * Add samples to Companies 
	 */
	public void sampleAssigned(){
		pro = (PropertyHelper)getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);
		tree = (Tree)getFellow("tree");
		StorageLocationTreeNode storageLocationTreeNode = validateSelectItemTree();
		if (storageLocationTreeNode != null){
			StorageLocation storage = (StorageLocation) storageLocationTreeNode.getData();
			if(!isOkAddSamples(storage))
				return;
			getDesktop().setAttribute(ATTRIBUTE_STORAGE_LOCATION_ITEM,storage);
			showWindowSample();
			if (getDesktop().getAttribute(ATTRIBUTE_SAMPLE_LOCATION_LIST) != null){
				AdvancedTreeModel contactTreeModel = new AdvancedTreeModel(new StorageLocationList().getRoot());
		        tree.setModel(contactTreeModel);
				getDesktop().removeAttribute(ATTRIBUTE_SAMPLE_LOCATION_LIST);
			}
			
			getDesktop().setAttribute(ATTRIBUTE_STORAGE_LOCATION_ITEM,null);
		}
		
	}

	private boolean isOkAddSamples(StorageLocation storage){
		if (isHasAcompanyAssigned(storage)){
			Messagebox.show(pro.getKey(LBL_STORAGE_LOCATION_SAMPL_ERR_ASS_COM), 
					pro.getKey(LBL_GENERIC_MESS_ERROR), 
					Messagebox.OK, Messagebox.ERROR);
			return false;
		}
		if (isThisStorageLocationHasChildrens(storage)){
			Messagebox.show(pro.getKey(LBL_STORAGE_LOCATION_SAMPL_ERR_ASS_LOC), 
					pro.getKey(LBL_GENERIC_MESS_ERROR), 
					Messagebox.OK, Messagebox.ERROR);
			return false;
		}
		return true;
	}
	private void showWindowSample() {
		final Window win = (Window) Executions.createComponents(
    			"/storage_location/window_sample_location.zul", null, null);
    		win.doModal();
	}
}
