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
package com.cimmyt.zk.tissue;

import static com.cimmyt.utils.Constants.ATTRIBUTE_INVESTIGATOR_CREATE;
import static com.cimmyt.utils.Constants.ATTRIBUTE_PARAM_MAP_FUNTION;
import static com.cimmyt.utils.Constants.ATTRIBUTE_PROJECT_ENABLED;
import static com.cimmyt.utils.Constants.ATTRIBUTE_TISSUE_ITEM;
import static com.cimmyt.utils.Constants.LABSTUDY_SERVICE_BEAN_NAME;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_DELETE_ERROR;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_DELETE_RECORD;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_DELETE_SUCCESS;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_EDIT;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_ERROR;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_ERROR_ASSOCIATED;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_ERROR_CRITERIAL;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_INFORMATION;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_PLEASE_CONFIRM;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_SELECT_RECORD;
import static com.cimmyt.utils.Constants.LBL_GENERIC_TISSUES;
import static com.cimmyt.utils.Constants.LBL_TISSUE_WIN_ADD;
import static com.cimmyt.utils.Constants.LBL_TISSUE_WIN_EDIT;
import static com.cimmyt.utils.Constants.LOCALE_LANGUAGE;
import static com.cimmyt.utils.Constants.SHOW_ROWS_LIST;
import static com.cimmyt.utils.Constants.TISSUE_SERVICE_BEAN_NAME;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.cimmyt.bean.InvestigatorBean;
import com.cimmyt.bean.TissueBean;
import com.cimmyt.service.ServiceLabStudy;
import com.cimmyt.service.ServiceTissue;
import com.cimmyt.utils.PropertyHelper;
import com.cimmyt.zk.projects.ControlWindowProject;

@SuppressWarnings("serial")
public class ControlTissue extends Window {

	private static ServiceTissue serviceTissue = null;
	private static ServiceLabStudy serviceLabStudy = null;
	private PropertyHelper pro=null;
    private Logger logger= Logger.getLogger(ControlWindowProject.class);
    private Listbox idLisB;
    private final String ID_ADD = "tissues$idAdd";
    private final String ID_EDIT = "tissues$idEdit";
    private final String ID_DELETE = "tissues$idDelete";
    private Hbox idHboxTissue;
	static {
		if(serviceTissue == null)
        {
			try{
				serviceTissue = (ServiceTissue)SpringUtil.getBean(TISSUE_SERVICE_BEAN_NAME);
			serviceLabStudy = (ServiceLabStudy)SpringUtil.getBean(LABSTUDY_SERVICE_BEAN_NAME);
			}catch (Exception e){
				e.printStackTrace();
			}
        }
		
	}
	/**
	 * Create components of window 
	 */
	public void doAfterCompose (int size, TissueBean bean ){
		InvestigatorBean beanSession = new InvestigatorBean();
		beanSession.setInvestigatorid(2);
				getDesktop().getSession().setAttribute(ATTRIBUTE_INVESTIGATOR_CREATE,beanSession);
		List<TissueBean> listBean = null;
		if (bean != null ){
			listBean = serviceTissue.getTissue(bean);
		}else {
			listBean = serviceTissue.getTissue(new TissueBean());	
		}
		idLisB = (Listbox)getFellow("idLisB");
		Listhead idListHead = (Listhead)getFellow("idListHead");
		clearList(idLisB);
		if (listBean != null && !listBean.isEmpty()) {
			int index = 0;
			for (TissueBean beanI : listBean){
				if (size == 0){
					loatItem( beanI);
				}else {
					if (index < size){
						loatItem( beanI);
						index ++;
					}
				}
			}
			if (listBean.size()>SHOW_ROWS_LIST){
				idLisB.setMold("paging");
				idLisB.setPageSize(SHOW_ROWS_LIST);
			}
		}
		idLisB.appendChild(idListHead);
		loadFisheye();
	}

	@SuppressWarnings("unchecked")
	private void loadFisheye (){
		idHboxTissue = (Hbox)getFellow("idHboxTissue");
		Map <String, String> mapFuntions;
		if (getDesktop().getSession().getAttribute(ATTRIBUTE_PARAM_MAP_FUNTION) != null){
			mapFuntions = (Map<String, String>) getDesktop().getSession().getAttribute(ATTRIBUTE_PARAM_MAP_FUNTION);
			if (mapFuntions.get(ID_ADD) == null){
				Image id = (Image)getFellow(ID_ADD);
				idHboxTissue.removeChild(id);
			}
			if (mapFuntions.get(ID_EDIT) == null){
				Image id = (Image)getFellow(ID_EDIT);
				idHboxTissue.removeChild(id);
			}
			if (mapFuntions.get(ID_DELETE) == null){
				Image id = (Image)getFellow(ID_DELETE);
				idHboxTissue.removeChild(id);
			}
		}
	}

	private void loatItem (TissueBean bean){
		Listitem lIt = new Listitem ();
		/*Listcell cell1 = new Listcell(bean.getIdtissue().toString());
		lIt.appendChild(cell1);*/
		Listcell cell2 = new Listcell(bean.getTissueName());
		lIt.appendChild(cell2);
		lIt.setValue(bean);
		idLisB.appendChild(lIt);
	}

	private void clearList(Listbox list){
		if (list !=null && !list.getItems().isEmpty()) {
			while (!list.getItems().isEmpty()) {
				list.getChildren().remove(0);
			}
		}
	}
	/**
	 * Add new Projects
	 */
	public void add () {
			pro = (PropertyHelper)getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);
			showWindow(pro.getKey(LBL_TISSUE_WIN_ADD));
			TissueBean bean = (TissueBean)getDesktop().getAttribute(ATTRIBUTE_TISSUE_ITEM);
			if (bean != null){
				serviceTissue.add(bean);
				getDesktop().setAttribute(ATTRIBUTE_TISSUE_ITEM, null);
				doAfterCompose(0,null);
			}
	}

	private void showWindow(String title) {
		final Window win = (Window) Executions.createComponents(
    			"/tissues/window_tissue.zul", null, null);
		win.setTitle(title);	
    		win.doModal();
	}
	/**
	 * Edit
	 */
	public void edit (){
		pro = (PropertyHelper)getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);
		idLisB = (Listbox)getFellow("idLisB");
		if (idLisB.getSelectedIndex() != -1){
			Listitem item =idLisB.getSelectedItem();
			TissueBean beanI = (TissueBean)item.getValue();
				boolean enabled = serviceLabStudy.ThisTissueHasLabStudyRegistred(beanI);
				getDesktop().setAttribute(ATTRIBUTE_TISSUE_ITEM,beanI);
				if(!enabled){
				getDesktop().setAttribute(ATTRIBUTE_PROJECT_ENABLED,enabled);
				showWindow(pro.getKey(LBL_TISSUE_WIN_EDIT));
				TissueBean bean = (TissueBean)getDesktop().getAttribute(ATTRIBUTE_TISSUE_ITEM);
					if (bean != null){
						serviceTissue.add(bean);
						getDesktop().removeAttribute(ATTRIBUTE_TISSUE_ITEM);
						doAfterCompose(0,null);
					}
				}else {
					Messagebox.show(pro.getKey(LBL_GENERIC_MESS_ERROR_ASSOCIATED,
							new String []{pro.getKey(LBL_GENERIC_MESS_EDIT),
							pro.getKey(LBL_GENERIC_TISSUES)}), 
							pro.getKey(LBL_GENERIC_MESS_ERROR),
							Messagebox.OK, Messagebox.ERROR);
				}
			
		}else {
			messageBox(pro.getKey(LBL_GENERIC_MESS_SELECT_RECORD));
		}
		getDesktop().removeAttribute(ATTRIBUTE_PROJECT_ENABLED);
	}

	private void messageBox(String mess){
		Messagebox.show(mess,pro.getKey(LBL_GENERIC_MESS_INFORMATION), 
				Messagebox.OK, Messagebox.INFORMATION);
	}

	public void delete (){
		pro = (PropertyHelper)getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);
		idLisB = (Listbox)getFellow("idLisB");
		if (idLisB.getSelectedIndex() != -1){
			Listitem item =idLisB.getSelectedItem();
			TissueBean beanI = (TissueBean)item.getValue();
					if (Messagebox.show(pro.getKey(LBL_GENERIC_MESS_DELETE_RECORD), 
							pro.getKey(LBL_GENERIC_MESS_PLEASE_CONFIRM), 
							Messagebox.YES | Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
							try {
								serviceTissue.delete(beanI);
								Messagebox.show(pro.getKey(LBL_GENERIC_MESS_DELETE_SUCCESS), 
										pro.getKey(LBL_GENERIC_MESS_INFORMATION), 
										Messagebox.OK, Messagebox.INFORMATION);
								doAfterCompose(0,null);
							}catch (Exception sql){
								Messagebox.show(pro.getKey(LBL_GENERIC_MESS_DELETE_ERROR,new String []{pro.getKey(LBL_GENERIC_TISSUES)}), 
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
		TissueBean bean = new TissueBean();
		if(idSN.getValue()!=null && !idSN.getValue().trim().equals("")){
			notEmty = true;
			bean.setTissueName(idSN.getValue());
		}
		
		if(notEmty){
			doAfterCompose(0, bean);
		}else {
			messageBox(pro.getKey(LBL_GENERIC_MESS_ERROR_CRITERIAL));
		}
	}
}
