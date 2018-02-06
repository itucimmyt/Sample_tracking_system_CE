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
import static com.cimmyt.utils.Constants.ATTRIBUTE_NAME_USER_BEAN;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MEN_NOT_INF_M;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_SELECT_RECORD;
import static com.cimmyt.utils.Constants.LBL_STUDIES_FILE_UP_MEN_ERR_NOT_INF;
import static com.cimmyt.utils.Constants.LBL_STUDIES_TITLE_SUB_ADD_T;
import static com.cimmyt.utils.Constants.LOCALE_LANGUAGE;
import static com.cimmyt.utils.Constants.TEMPORAL_SAMPLE_SERVICE_BEAN_NAME;
import static com.cimmyt.utils.ConstantsDNA.ATTRIBUTE_SAMPLE_TEMPORAL;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Window;

import com.cimmyt.bean.UserBean;
import com.cimmyt.model.bean.Investigator;
import com.cimmyt.model.bean.LabStudy;
import com.cimmyt.model.bean.TemporalSample;
import com.cimmyt.service.ServiceTemporalSample;
import com.cimmyt.utils.PropertyHelper;
import com.cimmyt.utils.StrUtils;

/**
 *@author CIMMYT
 *class to manage of samples temporally from other platforms  
 */
@SuppressWarnings("serial")
public class ControlTemporalSample extends Window{

	private static ServiceTemporalSample serviceTemporalSample ;
	private PropertyHelper pro=null;
	private Logger logger= Logger.getLogger(ControlTemporalSample.class);
	private UserBean userBean;
	private Listbox idListBST;
	private Listbox idListS;
	private Label idCountSize;
	private Label idSelectSize;
	protected boolean isEqualsSeason;
	protected boolean isEqualsLocation;
	protected int idSeason = 0;
	protected int idLocation = 0;

	static {
		if (serviceTemporalSample == null)
			try{
				serviceTemporalSample = (ServiceTemporalSample)SpringUtil.getBean(TEMPORAL_SAMPLE_SERVICE_BEAN_NAME);
			}catch (NullPointerException ex ){
				ex.printStackTrace();
			}
	}
/**
 *Initializer of objects into this class
 */
	public void doAfterCompose(){
		pro = (PropertyHelper)getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);
		if (getDesktop().getSession().getAttribute(ATTRIBUTE_NAME_USER_BEAN) != null)
			userBean = (UserBean)getDesktop().getSession().getAttribute(ATTRIBUTE_NAME_USER_BEAN);
		loadComponets();
		loadSampletsIntoList();
	}

	private void loadComponets(){
		idListBST = (Listbox)getFellow("idListBST");
		idCountSize = (Label)getFellow("idCountSize");
		idSelectSize = (Label)getFellow("idSelectSize");
		idListS = (Listbox)getFellow("idListS");
	}

	private void loadSampletsIntoList(){
		TemporalSample temp = new TemporalSample();
		Investigator invest = new Investigator();
		invest.setInvestigatorid(userBean.getInvestigatorBean().getInvestigatorid());
		temp.setResearcher(invest);
		clearList(idListBST);
		 List<TemporalSample> listTemp =serviceTemporalSample.getListSampleByIdResearcher(temp);
		 if (listTemp !=null && !listTemp.isEmpty()){
			 for (TemporalSample tem: listTemp)
			 loadItemList(tem);
			 idCountSize.setValue(Integer.toString(listTemp.size()));
		 }
		 idSelectSize.setValue("0");
	}
	private void loadItemList(TemporalSample temp){
		Listitem item = new Listitem();
		Listcell c1 = new Listcell(temp.getGid().toString());
		Listcell c2 = new Listcell(temp.getAcc());
		Listcell c3 = new Listcell(temp.getPlantNo().toString());
		Listcell c4 = new Listcell(temp.getComments());
		Listcell c5 = new Listcell(temp.getEntryNo().toString());
		Listcell c6 = new Listcell(temp.getSpecie().getOrganismname());
		Listcell c7 = new Listcell(temp.getLocation().getLocation_name());
		Listcell c8 = new Listcell(temp.getSeason().getSeason_name());
		item.appendChild(c1);item.appendChild(c2);item.appendChild(c3);item.appendChild(c4);
		item.appendChild(c5);item.appendChild(c6);item.appendChild(c7);item.appendChild(c8);
		item.setValue(temp);
		idListBST.appendChild(item);
	}
	private void clearList(Listbox list){
		if (list !=null && !list.getItems().isEmpty()) {
			while (list.getItems().size() >= 1) {
				list.getChildren().remove(1);
			}
		}
	}
	/**Method for move samples*/
	public void moveSamples(){
		if (isEmptyList(idListBST))
			return;
		if (!idListBST.getSelectedItems().isEmpty()){
			 move(idListBST, idListS);
			 idCountSize.setValue(String.valueOf(idListBST.getItems().size()));
				idSelectSize.setValue(String.valueOf(idListS.getItems().size()));
			}else
				StrUtils.messageBox(pro.getKey(LBL_GENERIC_MESS_SELECT_RECORD), pro);
		}
	
	public void removeSamples(){
		if (isEmptyList(idListS))
			return;
		if (!idListS.getSelectedItems().isEmpty()){
			move(idListS, idListBST);
			idCountSize.setValue(String.valueOf(idListBST.getItems().size()));
			idSelectSize.setValue(String.valueOf(idListS.getItems().size()));
		}else {
			StrUtils.messageBox(pro.getKey(LBL_GENERIC_MESS_SELECT_RECORD), pro);
		}
	}

	public void moveAll(){
		List<Listitem> listItem = new ArrayList<Listitem>();
		if (isEmptyList(idListBST))
			return;
		for (Listitem item : idListBST.getItems()){
			listItem.add(item);
		}
		idListS.getChildren().addAll(listItem);
		idListBST.getChildren().removeAll(listItem);
		idCountSize.setValue(String.valueOf(idListBST.getItems().size()));
		idSelectSize.setValue(String.valueOf(idListS.getItems().size()));
	}

	public void removeAll(){
		List<Listitem> listItem = new ArrayList<Listitem>();
		if (isEmptyList(idListS))
			return;
		for (Listitem item : idListS.getItems()){
			listItem.add(item);
		}
		idListBST.getChildren().addAll(listItem);
		idListS.getChildren().removeAll(listItem);
		idCountSize.setValue(String.valueOf(idListBST.getItems().size()));
		idSelectSize.setValue(String.valueOf(idListS.getItems().size()));
	}
	private void move(Listbox list1 , Listbox list2){
	List<Listitem> samplesAdd= new ArrayList<Listitem>();
	List<Listitem> samplesRemove=new ArrayList<Listitem>();
				for(Listitem itemListRT: list1.getSelectedItems()){
						samplesAdd.add(itemListRT);
						samplesRemove.add(itemListRT);
					}
		list2.getChildren().addAll(samplesAdd);
		list1.getChildren().removeAll(samplesRemove);
	}

	public void addTemporalySamples(){
		if (idListS.getItems().isEmpty())
			StrUtils.messageBox(pro.getKey(LBL_STUDIES_FILE_UP_MEN_ERR_NOT_INF), pro);
		List<TemporalSample> listTempSample = new ArrayList<TemporalSample>();
		for (Listitem item : idListS.getItems()){
			listTempSample.add((TemporalSample)item.getValue());
		}
		validateLocationSeason(listTempSample);
		Object [] obj = {listTempSample,idSeason, isEqualsSeason,idLocation,isEqualsLocation};
		getDesktop().setAttribute(ATTRIBUTE_SAMPLE_TEMPORAL,obj );
		showWindow(pro.getKey(LBL_STUDIES_TITLE_SUB_ADD_T));
		if (getDesktop().getAttribute(ATTRIBUTE_SAMPLE_TEMPORAL) != null)
			getDesktop().removeAttribute(ATTRIBUTE_SAMPLE_TEMPORAL);
		closeWindow();
	}

	private void showWindow(String title) {
		final Window win = (Window) Executions.createComponents(
    			"/studies/window_add_lab_study.zul", this, null);
		win.setTitle(title);	
    		win.doModal();
	}

	private boolean isEmptyList(Listbox list){
		if (list.getItems().isEmpty()){
			StrUtils.messageBox(pro.getKey(LBL_GENERIC_MEN_NOT_INF_M), pro);
			return true;
		}
		return false;
	}

	public void closeWindow(){
		getDesktop().removeAttribute(ATTRIBUTE_SAMPLE_TEMPORAL );
		Window idWindow = (Window)getFellow("idWindowSelectSampTemp");
		idWindow.onClose();
	}

	private void validateLocationSeason(List<TemporalSample> list){
		for (TemporalSample item : list){
			if (idSeason == 0)
				idSeason = item.getSeason().getSeasonid();
			else if (idSeason != item.getSeason().getSeasonid()){
				isEqualsSeason = false;
			break;
			}
			else
				isEqualsSeason = true;
		}
		for (TemporalSample item : list){
			if (idLocation == 0)
				idLocation = item.getLocation().getLocationid();
			else if (idLocation != item.getLocation().getLocationid()){
				isEqualsLocation = false;
			break;
			}
			else
				isEqualsLocation = true;
		}
	}
}
