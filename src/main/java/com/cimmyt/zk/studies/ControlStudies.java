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

import static com.cimmyt.utils.Constants.ATTRIBUTE_EDIT_STUDIES;
import static com.cimmyt.utils.Constants.ATTRIBUTE_LABSTUDY_ITEM;
import static com.cimmyt.utils.Constants.ATTRIBUTE_LIST_FIELD_REPORT;
import static com.cimmyt.utils.Constants.ATTRIBUTE_NAME_USER_BEAN;
import static com.cimmyt.utils.Constants.ATTRIBUTE_PARAM_MAP_FUNTION;
import static com.cimmyt.utils.Constants.ATTRIBUTE_PROJECT_ENABLED;
import static com.cimmyt.utils.Constants.LABSTUDY_SERVICE_BEAN_NAME;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_DELETE_RECORD;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_DELETE_STUDY_SHIPMENT;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_DELETE_SUCCESS;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_ERROR;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_ERROR_CRITERIAL;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_INFORMATION;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_PLEASE_CONFIRM;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_SELECT_RECORD;
import static com.cimmyt.utils.Constants.LBL_GENERIC_WINDOW_QUESTION;
import static com.cimmyt.utils.Constants.LBL_STUDIES_TEMPORARY_SAMPLE_FOUND;
import static com.cimmyt.utils.Constants.LBL_STUDIES_TITLE_SUB_ADD_T;
import static com.cimmyt.utils.Constants.LBL_STUDY_TEMPLATE_WIN_EDIT;
import static com.cimmyt.utils.Constants.LOCALE_LANGUAGE;
import static com.cimmyt.utils.Constants.SHOW_ROWS_LIST;
import static com.cimmyt.utils.Constants.TEMPORAL_SAMPLE_SERVICE_BEAN_NAME;
import static com.cimmyt.utils.ConstantsDNA.LBL_TEMPORAL_SAMPLE_MESSAGE_ASSIGN;
import static com.cimmyt.utils.ConstantsDNA.LBL_TEMPORAL_SAMPLE_WINDOW_TITLE;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zkex.zul.Fisheye;
import org.zkoss.zkex.zul.Fisheyebar;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.PagingEvent;
import org.zkoss.zul.event.ZulEvents;

import com.cimmyt.bean.UserBean;
import com.cimmyt.model.bean.Investigator;
import com.cimmyt.model.bean.LabStudy;
import com.cimmyt.model.bean.StudyTemplateParams;
import com.cimmyt.model.bean.TemporalSample;
import com.cimmyt.service.ServiceLabStudy;
import com.cimmyt.service.ServiceTemporalSample;
import com.cimmyt.servlet.RedirectServletReport;
import com.cimmyt.servlet.SessionReport;
import com.cimmyt.utils.Constants;
import com.cimmyt.utils.ConstantsDNA;
import com.cimmyt.utils.PropertyHelper;
import com.cimmyt.utils.StrUtils;
import com.cimmyt.zk.projects.ControlWindowProject;

@SuppressWarnings("serial")
public class ControlStudies extends Window {

	private static ServiceLabStudy serviceLabStudy = null;
	private static ServiceTemporalSample serviceTemporalSample ;
	private PropertyHelper pro=null;
    private Logger logger= Logger.getLogger(ControlWindowProject.class);
    private Listbox idLisB;
    private UserBean userBean;
	private Paging idPaging;
	private Listhead idListHead;
	private boolean ascending = true;
	private String lastColumnSorted = null;
	private final String ID_ADD = "studies$idAdd";
	private final String ID_EDIT = "studies$idEdit";
	private final String ID_DELETE = "studies$idDelete";
	private final String ID_REPORT = "studies$idLaboratoryReport";
	private final String ID_RESULT_DATA = "studies$idResultData";
	private final String ID_UPDATE_RESULT = "studies$idUpdateResult";
	private Fisheyebar fisheyebar;

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
		if (serviceTemporalSample == null)
			try{
				serviceTemporalSample = (ServiceTemporalSample)SpringUtil.getBean(TEMPORAL_SAMPLE_SERVICE_BEAN_NAME);
			}catch (NullPointerException ex ){
				ex.printStackTrace();
			}
	}
	/**
	 * Create components of window 
	 */
	public void doAfterCompose (LabStudy bean ){
		
		pro = (PropertyHelper)getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);
		
		idListHead = (Listhead)getFellow("idListHead");
		
		if (getDesktop().getSession().getAttribute(ATTRIBUTE_NAME_USER_BEAN) != null){
			userBean = (UserBean)getDesktop().getSession().getAttribute(ATTRIBUTE_NAME_USER_BEAN);
		}

		if(idPaging == null){
			idPaging = (Paging)this.getFellow("idPaging");
			idPaging.addEventListener(ZulEvents.ON_PAGING, new EventListener<PagingEvent>() {
				@Override
				public void onEvent(PagingEvent event) throws Exception {

					List<LabStudy> listBean = serviceLabStudy.getLabStudysByIdResearch(new LabStudy(), userBean.getInvestigatorBean().getInvestigatorid()
									,event.getActivePage() * SHOW_ROWS_LIST, SHOW_ROWS_LIST, lastColumnSorted, ascending);
					clearList(idLisB);
	
					for(LabStudy study : listBean){
						loatItem(study);
					}
					idLisB.appendChild(idListHead);
				}
			});

		}
		if (userBean!= null && userBean.getRole()!= null && !userBean.getRole().getIdstRol().equals(ConstantsDNA.ROLE_ADMINISTRATOR))
			loadFisheye();

		getAndDrawStudies(bean, true);
		validateIfthereareSampletoProcess();
	}

	@SuppressWarnings("unchecked")
	private void loadFisheye (){
		fisheyebar = (Fisheyebar)getFellow("idFsbStudies");
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
			if (mapFuntions.get(ID_REPORT) == null){
				Fisheye id = (Fisheye)getFellow(ID_REPORT);
				fisheyebar.removeChild(id);
			}
			if (mapFuntions.get(ID_RESULT_DATA) == null){
				Fisheye id = (Fisheye)getFellow(ID_RESULT_DATA);
				fisheyebar.removeChild(id);
			}
			if (mapFuntions.get(ID_UPDATE_RESULT) == null){
				Fisheye id = (Fisheye)getFellow(ID_UPDATE_RESULT);
				fisheyebar.removeChild(id);
			}
		}
	}
	/**
	 * Method that look for in the temporary table samples from web service
	 */
	private void validateIfthereareSampletoProcess(){
		TemporalSample filter = new TemporalSample();
		Investigator researcher = new Investigator();
		researcher.setInvestigatorid(userBean.getInvestigatorBean().getInvestigatorid());
		filter.setResearcher(researcher);
		if (serviceTemporalSample.areThereAnyTempSamplesByIdResercher(filter))
			messageBox(pro.getKey(LBL_STUDIES_TEMPORARY_SAMPLE_FOUND));
	}

	private void getAndDrawStudies(LabStudy study, boolean recountTotal){
		getAndDrawStudies(study, recountTotal, 0);
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

		if (study != null ){
			listBean = serviceLabStudy.getLabStudysByIdResearch(study, 
					userBean.getInvestigatorBean().getInvestigatorid(),page * SHOW_ROWS_LIST,
					SHOW_ROWS_LIST, lastColumnSorted, ascending);
		}else {
			listBean = serviceLabStudy.getLabStudysByIdResearch(new LabStudy(), 
					userBean.getInvestigatorBean().getInvestigatorid(),page * SHOW_ROWS_LIST,
					SHOW_ROWS_LIST, lastColumnSorted, ascending);	
		}

		idLisB = (Listbox)getFellow("idLisB");
		clearList(idLisB);
		if (listBean != null && !listBean.isEmpty()) {
			for (LabStudy beanI : listBean){
				loatItem( beanI);
			}

		}

	}

	private void loatItem (LabStudy bean){
		Listitem lIt = new Listitem ();
		Listcell cell2 = new Listcell(bean.getProject().getProjectname()+
				bean.getProject().getPurposename());
		lIt.appendChild(cell2);
		Listcell cell3 = new Listcell(bean.getTitle());
		lIt.appendChild(cell3);
		Listcell cell4 = new Listcell(bean.getObjective());
		lIt.appendChild(cell4);
		Listcell cell8 = new Listcell(bean.getInvestigatorid().getInvest_name());
		lIt.appendChild(cell8);
		Listcell cell5 = new Listcell(StrUtils.getDateFormat(bean.getStartdate()));
		lIt.appendChild(cell5);
		Listcell cell6 = new Listcell(StrUtils.getDateFormat(bean.getEnddate()));
		lIt.appendChild(cell6);
		Listcell cell7 = new Listcell(bean.getStudytemplateid().getTemplatename());
		lIt.appendChild(cell7);
		Listcell cell9 = new Listcell(bean.getStatus().getStatusDescription());
		lIt.appendChild(cell9);
		lIt.setValue(bean);
		idLisB.appendChild(lIt);
	}

	private void clearList(Listbox list){
		if (list !=null && !list.getItems().isEmpty()) {
			while (list.getItems().size() >= 1) {
				list.getChildren().remove(1);//does not remove item 0(headers)
			}
		}
	}
	/**
	 * Add new Season
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void add () {
		TemporalSample filter = new TemporalSample();
		Investigator researcher = new Investigator();
		researcher.setInvestigatorid(userBean.getInvestigatorBean().getInvestigatorid());
		filter.setResearcher(researcher);
		if(serviceTemporalSample.areThereAnyTempSamplesByIdResercher(filter))
		Messagebox.show(pro.getKey(LBL_TEMPORAL_SAMPLE_MESSAGE_ASSIGN),pro.getKey(LBL_GENERIC_WINDOW_QUESTION),Messagebox.YES |Messagebox.NO |Messagebox.CANCEL, 
				Messagebox.QUESTION, 
				 new org.zkoss.zk.ui.event.EventListener(){
					@Override
					public void onEvent(Event e) throws Exception {
						if (Messagebox.ON_YES.equals(e.getName())){
							final Window win = (Window) Executions.createComponents(
					    			"/studies/window_select_sample_temp.zul", null, null);
							win.setTitle(pro.getKey(LBL_TEMPORAL_SAMPLE_WINDOW_TITLE));	
					    		win.doModal();
					    		LabStudy bean = (LabStudy)getDesktop().getAttribute(ATTRIBUTE_LABSTUDY_ITEM);
					    		if (bean != null){
					    			getDesktop().setAttribute(ATTRIBUTE_LABSTUDY_ITEM, null);
					    			getAndDrawStudies(null, true);
					    			}
						}else if (Messagebox.ON_NO.equals(e.getName()))
							showWindowNormalLoad();			
						}
					}
				);
		else 
			showWindowNormalLoad();
	}

	private void showWindowNormalLoad(){
		showWindow(pro.getKey(LBL_STUDIES_TITLE_SUB_ADD_T));
		LabStudy bean = (LabStudy)getDesktop().getAttribute(ATTRIBUTE_LABSTUDY_ITEM);
		if (bean != null){
			getDesktop().setAttribute(ATTRIBUTE_LABSTUDY_ITEM, null);
			getAndDrawStudies(null, true);
			}
	}
	private void showWindow(String title) {
		final Window win = (Window) Executions.createComponents(
    			"/studies/window_add_lab_study.zul", this, null);
		win.setTitle(title);	
    		win.doModal();
	}
	/**
	 * Edit Studies load in the window
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void edit (){
		idLisB = (Listbox)getFellow("idLisB");
		if (idLisB.getSelectedIndex() != -1){
			Listitem item =idLisB.getSelectedItem();
			LabStudy beanStudy = (LabStudy)item.getValue();
			beanStudy = serviceLabStudy.readStudyOnlySampleDetail(beanStudy.getLabstudyid());
			    getDesktop().setAttribute(ATTRIBUTE_LABSTUDY_ITEM ,beanStudy );
				getDesktop().setAttribute(ATTRIBUTE_PROJECT_ENABLED,true);
			    getDesktop().setAttribute(ATTRIBUTE_EDIT_STUDIES, true);
				TemporalSample filter = new TemporalSample();
				Investigator researcher = new Investigator();
				researcher.setInvestigatorid(userBean.getInvestigatorBean().getInvestigatorid());
				filter.setResearcher(researcher);
				if(serviceTemporalSample.areThereAnyTempSamplesByIdResercher(filter))
				Messagebox.show(pro.getKey(LBL_TEMPORAL_SAMPLE_MESSAGE_ASSIGN),pro.getKey(LBL_GENERIC_WINDOW_QUESTION),Messagebox.YES |Messagebox.NO |Messagebox.CANCEL, 
						Messagebox.QUESTION, 
						 new org.zkoss.zk.ui.event.EventListener(){
							@Override
							public void onEvent(Event e) throws Exception {
								if (Messagebox.ON_YES.equals(e.getName())){
									final Window win = (Window) Executions.createComponents(
							    			"/studies/window_select_sample_temp.zul", null, null);
									win.setTitle(pro.getKey(LBL_TEMPORAL_SAMPLE_WINDOW_TITLE));	
							    		win.doModal();
							    		LabStudy bean = (LabStudy)getDesktop().getAttribute(ATTRIBUTE_LABSTUDY_ITEM);
							    		if (bean != null){
							    			getDesktop().setAttribute(ATTRIBUTE_LABSTUDY_ITEM, null);
							    			getAndDrawStudies(null, true);
							    			}
								}else if (Messagebox.ON_NO.equals(e.getName()))
									showWindowEdit();			
								}
							}
						);
				else 
					showWindowEdit();
			}else {
				messageBox(pro.getKey(LBL_GENERIC_MESS_SELECT_RECORD));
			}
		getDesktop().removeAttribute(ATTRIBUTE_PROJECT_ENABLED);
	}

	private void showWindowEdit(){
		showWindow(pro.getKey(LBL_STUDY_TEMPLATE_WIN_EDIT));
		LabStudy bean = (LabStudy)getDesktop().getAttribute(ATTRIBUTE_LABSTUDY_ITEM);
		if (bean != null){
			//seriviceStudyTemplate.add(bean);
			getDesktop().removeAttribute(ATTRIBUTE_LABSTUDY_ITEM);
			getDesktop().removeAttribute(ATTRIBUTE_EDIT_STUDIES);
			getAndDrawStudies(null, true);
		}
	}

	public void loadWindowResultData(){
		idLisB = (Listbox)getFellow("idLisB");
		if (idLisB.getSelectedIndex() != -1){
			Listitem item =idLisB.getSelectedItem();
			LabStudy beanStudy = (LabStudy)item.getValue();
			beanStudy = serviceLabStudy.readStudyWithResults(beanStudy.getLabstudyid());
			    getDesktop().setAttribute(ATTRIBUTE_LABSTUDY_ITEM ,beanStudy );
			    final Window win = (Window) Executions.createComponents(
		    			"/resultData/result_data.zul", this, null);
		    		win.doModal();
					getDesktop().removeAttribute(ATTRIBUTE_LABSTUDY_ITEM);
			}else {
				messageBox(pro.getKey(LBL_GENERIC_MESS_SELECT_RECORD));
			}
	}

	public void loadWindowUpDateResultData(){
		idLisB = (Listbox)getFellow("idLisB");
		if (idLisB.getSelectedIndex() != -1){
			Listitem item =idLisB.getSelectedItem();
			LabStudy beanStudy = (LabStudy)item.getValue();
			beanStudy = serviceLabStudy.readStudyWithResults(beanStudy.getLabstudyid());
			    getDesktop().setAttribute(ATTRIBUTE_LABSTUDY_ITEM ,beanStudy );
			    final Window win = (Window) Executions.createComponents(
		    			"/resultData/file_upload_Result.zul", this, null);
		    		win.doModal();
					getDesktop().removeAttribute(ATTRIBUTE_LABSTUDY_ITEM);
			}else {
				messageBox(pro.getKey(LBL_GENERIC_MESS_SELECT_RECORD));
			}
	}

	/**Method that return message by key
	 * @param mess*/
	private void messageBox(String mess){
		Messagebox.show(mess,pro.getKey(LBL_GENERIC_MESS_INFORMATION), 
				Messagebox.OK, Messagebox.INFORMATION);
	}
	/**
	 * Delete studies that have not been sent to sequence
	 */
	public void delete (){
		idLisB = (Listbox)getFellow("idLisB");
		if (idLisB.getSelectedIndex() != -1){
			Listitem item =idLisB.getSelectedItem();
			LabStudy beanI = (LabStudy)item.getValue();
			beanI = serviceLabStudy.readStudyOnlySampleDetail(beanI.getLabstudyid());
					if (Messagebox.show(pro.getKey(LBL_GENERIC_MESS_DELETE_RECORD), 
							pro.getKey(LBL_GENERIC_MESS_PLEASE_CONFIRM), 
							Messagebox.YES | Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
							try {
								serviceLabStudy.deleteStudy(beanI.getLabstudyid());
								Messagebox.show(pro.getKey(LBL_GENERIC_MESS_DELETE_SUCCESS), 
										pro.getKey(LBL_GENERIC_MESS_INFORMATION), 
										Messagebox.OK, Messagebox.INFORMATION);
								getAndDrawStudies(null, true);
							}catch (Exception sql){
								Messagebox.show(pro.getKey(LBL_GENERIC_MESS_DELETE_STUDY_SHIPMENT), 
										pro.getKey(LBL_GENERIC_MESS_ERROR), 
										Messagebox.OK, Messagebox.ERROR);
								logger.info(sql.getMessage());
							 }
						}
		}else {
			messageBox(pro.getKey(LBL_GENERIC_MESS_SELECT_RECORD));
		}
	}

	public void search(){
		pro = (PropertyHelper)getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);
		Textbox idSN = (Textbox)getFellow("idSN");

		boolean notEmty =false;
		LabStudy bean = new LabStudy();
		if(idSN.getValue()!=null && !idSN.getValue().trim().equals("")){
			notEmty = true;

			bean.setPrefix(idSN.getValue().trim());
		}
		
		if(notEmty){
	
			getAndDrawStudies(bean, true);
		}else {
			messageBox(pro.getKey(LBL_GENERIC_MESS_ERROR_CRITERIAL));
		}
	}
	/**
	 * Method that create to report of laboratory 
	 */
	@SuppressWarnings("unchecked")
	public void createReportLaboratory(){
		idLisB = (Listbox)getFellow("idLisB");
		
		if (idLisB.getSelectedIndex() != -1){
			Listitem item =idLisB.getSelectedItem();
			LabStudy beanStudy = (LabStudy)item.getValue();
			beanStudy = serviceLabStudy.readStudyWithResults(beanStudy.getLabstudyid());
			getDesktop().setAttribute(Constants.ATTRIBUTE_FIELD_TEMPLATE, beanStudy);
			final Window win = (Window) Executions.createComponents(
	    			"/studies/window_select_field.zul", null, null);
	    		win.doModal();
	    		 List<Object> listFieldsObjets =	(List<Object>)getDesktop().getAttribute(ATTRIBUTE_LIST_FIELD_REPORT);
	    		 List<StudyTemplateParams> listStudyTemParams = (List<StudyTemplateParams>)getDesktop().getAttribute(Constants.ATTRIBUTE_LIST_FIELD_REPORT_TEMPLATE);
			if (listFieldsObjets != null || listStudyTemParams !=null){
				
				if (listFieldsObjets.isEmpty() && listStudyTemParams.isEmpty()){
					messageBox(pro.getKey(Constants.LBL_GENERIC_WINDOW_ERR_SELECT_FIELD));
					return;
				}
				SessionReport sessionReport = new SessionReport();
				sessionReport.setB(serviceLabStudy.getReportPlate(beanStudy, listFieldsObjets,listStudyTemParams));
				sessionReport.setType(ConstantsDNA.FILE_TYPE_XLS);
				sessionReport.setName(beanStudy.getTitle());
	
				RedirectServletReport.export(sessionReport);
				getDesktop().removeAttribute(ATTRIBUTE_LIST_FIELD_REPORT);
				getDesktop().removeAttribute(Constants.ATTRIBUTE_FIELD_TEMPLATE);
			}
		}else {
			messageBox(pro.getKey(LBL_GENERIC_MESS_SELECT_RECORD));
		}
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

}


