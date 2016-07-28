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
import static com.cimmyt.utils.Constants.ATTRIBUTE_LABSTUDY_ITEM_ORIGINAL;
import static com.cimmyt.utils.Constants.ATTRIBUTE_NAME_USER_BEAN;
import static com.cimmyt.utils.Constants.ATTRIBUTE_PROJECT_ENABLED;
import static com.cimmyt.utils.Constants.ATTRIBUTE_STUDY_TEMPLATE_ITEM;
import static com.cimmyt.utils.Constants.INVESTIGATOR_SERVICE_BEAN_NAME;
import static com.cimmyt.utils.Constants.LABSTUDY_SERVICE_BEAN_NAME;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_ERROR;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_ER_DIF_REG;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_PURPOSE;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_STUDY_NAME;
import static com.cimmyt.utils.Constants.LOCALE_LANGUAGE;
import static com.cimmyt.utils.Constants.LOCATION_SERVICE_BEAN_NAME;
import static com.cimmyt.utils.Constants.MIX_SEASON_DEFAULT;
import static com.cimmyt.utils.Constants.MIX_LOCATION_DEFAULT;
import static com.cimmyt.utils.Constants.ORGANISM_SERVICE_BEAN_NAME;
import static com.cimmyt.utils.Constants.PROJECT_SERVICE_BEAN_NAME;
import static com.cimmyt.utils.Constants.SEASON_SERVICE_BEAN_NAME;
import static com.cimmyt.utils.Constants.STUDY_TEMPLATE_SERVICE_BEAN_NAME;
import static com.cimmyt.utils.Constants.TISSUE_SERVICE_BEAN_NAME;
import static com.cimmyt.utils.ConstantsDNA.ATTRIBUTE_SAMPLE_TEMPORAL;
import static com.cimmyt.utils.ConstantsDNA.LBL_TEMPORAL_SAMPLE_MESSAGE_ERR_LOCATION;
import static com.cimmyt.utils.ConstantsDNA.LBL_TEMPORAL_SAMPLE_MESSAGE_ERR_SEASON;

import java.util.List;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zkex.zul.Fisheye;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.cimmyt.bean.InvestigatorBean;
import com.cimmyt.bean.LocationBean;
import com.cimmyt.bean.ProjectBean;
import com.cimmyt.bean.SeasonBean;
import com.cimmyt.bean.TissueBean;
import com.cimmyt.bean.UserBean;
import com.cimmyt.model.bean.Investigator;
import com.cimmyt.model.bean.LabStudy;
import com.cimmyt.model.bean.LocationCatalog;
import com.cimmyt.model.bean.Organism;
import com.cimmyt.model.bean.Project;
import com.cimmyt.model.bean.Season;
import com.cimmyt.model.bean.StudyTemplate;
import com.cimmyt.model.bean.Tissue;
import com.cimmyt.service.SeriviceStudyTemplate;
import com.cimmyt.service.ServiceInvestigator;
import com.cimmyt.service.ServiceLabStudy;
import com.cimmyt.service.ServiceLocation;
import com.cimmyt.service.ServiceOrganism;
import com.cimmyt.service.ServiceProject;
import com.cimmyt.service.ServiceSeason;
import com.cimmyt.service.ServiceTissue;
import com.cimmyt.utils.PropertyHelper;

@SuppressWarnings("serial")
public class ControlWindowStudies extends Window{
	private Intbox id;
	private Combobox idComboProject;
	private Combobox idComboInvestigator;
	private Combobox idComboOrganism;
	private Combobox idComboTissue;
	private Combobox idComboLocation;
	private Combobox idComboSeason;
	private Combobox idDNTemplate;
	private Textbox idTTitle;
	private Textbox objetive;
	private Datebox idDBRegDate;
	private Datebox idDBRegDateEnd;
	private Fisheye idFisheyeNext;
	
	private PropertyHelper pro=null;
	private static SeriviceStudyTemplate seriviceStudyTemplate = null;
	private static ServiceProject seriviceProject;
	private static ServiceInvestigator serviceInvestigator;
	private static ServiceOrganism serviceOrganism;
	private static ServiceTissue serviceTissue;
	private static ServiceLocation serviceLocation;
	private static ServiceSeason serviceSeason;
	private static ServiceLabStudy serviceLabStudy;
	private UserBean userBean;
	private boolean disabled;
	//Object that contain the information of study and samples
	private LabStudy bean;
	//Object copy that contain the information of study and samples
	private LabStudy beanOriginal;
	static {
		if(seriviceStudyTemplate == null)
        {
			try{
				seriviceStudyTemplate = (SeriviceStudyTemplate)SpringUtil.getBean(STUDY_TEMPLATE_SERVICE_BEAN_NAME);
				}catch (Exception e){
				e.printStackTrace();
			}
        }
		
	}
	static {
		if(seriviceProject == null)
        {
			try{
				seriviceProject = (ServiceProject)SpringUtil.getBean(PROJECT_SERVICE_BEAN_NAME);
				}catch (Exception e){
				e.printStackTrace();
			}
        }
		
	}
	static {
		if(serviceInvestigator == null)
        {
			try{
				serviceInvestigator = (ServiceInvestigator)SpringUtil.getBean(INVESTIGATOR_SERVICE_BEAN_NAME);
				}catch (Exception e){
				e.printStackTrace();
			}
        }
		
	}
	static {
		if(serviceOrganism == null)
        {
			try{
				serviceOrganism = (ServiceOrganism)SpringUtil.getBean(ORGANISM_SERVICE_BEAN_NAME);
				}catch (Exception e){
				e.printStackTrace();
			}
        }
		
	}
	static {
		if(serviceTissue == null)
        {
			try{
				serviceTissue = (ServiceTissue)SpringUtil.getBean(TISSUE_SERVICE_BEAN_NAME);
				}catch (Exception e){
				e.printStackTrace();
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
	//Logger logger= Logger.getLogger(ControlWindowStudies.class);
	/** 
	 * Close Window
	 */
	public void closeWindow(){
		getDesktop().setAttribute(ATTRIBUTE_STUDY_TEMPLATE_ITEM, null);
		this.onClose();
	}
	/**
	 * Load Window Components
	 */
	public void loadContextAttribute(){
		pro = (PropertyHelper)getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);
		getIdInvestigator();
		loadComponents();
		disabled = getDesktop().getAttribute(ATTRIBUTE_PROJECT_ENABLED) != null ?
				(Boolean)getDesktop().getAttribute(ATTRIBUTE_PROJECT_ENABLED) : false;
				bean = getDesktop().getAttribute(ATTRIBUTE_LABSTUDY_ITEM) != null ?
				(LabStudy)getDesktop().getAttribute(ATTRIBUTE_LABSTUDY_ITEM) : new LabStudy();
				beanOriginal = (LabStudy) (getDesktop().getAttribute(ATTRIBUTE_EDIT_STUDIES) !=null ?
						bean.clone() : new LabStudy());
		int idProject = bean.getProject() != null ? bean.getProject().getProjectid() : 0;
		loadComboProject(idProject, disabled);
		int idInvestigator = 0;
		int idOrganism = 0; 
		if (disabled){
		 idInvestigator = bean.getInvestigatorid() != null ?
				idInvestigator = bean.getInvestigatorid().getInvestigatorid() :0;
				idOrganism = bean.getOrganismid() != null ? bean.getOrganismid().getOrganismid():0;
				loadComboInvestigator(idInvestigator, disabled);
				loadComboOrganism(idOrganism, disabled);
			}else { 
					idInvestigator = userBean.getInvestigatorBean().getInvestigatorid();
					idOrganism = userBean.getTypeCorp();
					loadComboInvestigator(idInvestigator, true);
					loadComboOrganism(idOrganism, true);
			}
		int idTissue = bean.getTissue() != null ? bean.getTissue().getIdtissue() : 0;
		loadComboTissue(idTissue);
		int idLocation = bean.getLocation() != null ? bean.getLocation().getLocationid() : 0;
		loadComboLocation(idLocation, disabled);
		int idSeason = bean.getSeason() != null ? bean.getSeason().getSeasonid() : 0;
		loadComboSeason(idSeason, disabled);
		int idStudyTem = bean.getStudytemplateid() != null ? 
				bean.getStudytemplateid().getStudytemplateid() != null ?
						bean.getStudytemplateid().getStudytemplateid():0 : 0;
		loadComboStudyTem(idStudyTem, disabled);
		idTTitle.setText(bean.getTitle());
		idTTitle.setDisabled(disabled);
		objetive.setText(bean.getObjective());
		idDBRegDate.setValue(bean.getStartdate());
		idDBRegDateEnd.setValue(bean.getStartdate());
		if(bean.getLabstudyid()!= null){
			id.setValue(bean.getLabstudyid());
			validateFields();
		}
		idComboProject.setFocus(true);
		loadIDsFromTempSamples();
	}

	private void getIdInvestigator(){
		if (getDesktop().getSession().getAttribute(ATTRIBUTE_NAME_USER_BEAN) != null)
			userBean = (UserBean)getDesktop().getSession().getAttribute(ATTRIBUTE_NAME_USER_BEAN);
	}

	private void loadComboStudyTem (int id, boolean disabled){
		List<StudyTemplate> beanList =seriviceStudyTemplate.getStudyTemplate(new StudyTemplate());
		if (beanList != null && !beanList.isEmpty()){
			for (StudyTemplate bean : beanList){
				Comboitem item = new Comboitem(bean.getTemplatename());
				item.setValue(bean);
				idDNTemplate.appendChild(item);
				if(bean.getStudytemplateid() == id){
					idDNTemplate.setSelectedItem(item);
				}
			}
			idDNTemplate.setDisabled(disabled);
		}
	}

	private void loadIDsFromTempSamples(){
		if (getDesktop().getAttribute(ATTRIBUTE_SAMPLE_TEMPORAL )!= null){
			Object [] obj = (Object[]) getDesktop().getAttribute(ATTRIBUTE_SAMPLE_TEMPORAL);
			if(obj[0] !=null){
				boolean isEqualsSeason = (Boolean)obj[2];
				int idSeason = (Integer) obj[1];
				if (isEqualsSeason){//equals samples
					if (bean != null && 
							bean.getSeason() != null && 
							bean.getSeason().getSeasonid()!= null ){ //there are objects 
						if(bean.getSeason().getSeasonid().intValue() == MIX_SEASON_DEFAULT)//validate if those sample are season mix
							loadComboSeason(MIX_SEASON_DEFAULT, true);
						else if(	bean.getSeason().getSeasonid().intValue() == idSeason) //all samples become 
							loadComboSeason(idSeason, true);
						else 
						{
							printErrorForLocationAndSeason(LBL_TEMPORAL_SAMPLE_MESSAGE_ERR_SEASON);
							closeWindow();
							return;
						}
					}else
						loadComboSeason(idSeason, true);
				}
				else{ 
					if (bean != null &&
							bean.getSeason() != null && 
							bean.getSeason().getSeasonid()!= null ){
						if (bean.getSeason().getSeasonid().intValue() == MIX_SEASON_DEFAULT){
							loadComboSeason(idSeason, true);
						}else {
							printErrorForLocationAndSeason(LBL_TEMPORAL_SAMPLE_MESSAGE_ERR_SEASON);
							closeWindow();
							return;
						}
					}else
					loadComboSeason(MIX_SEASON_DEFAULT, true);
				}
				boolean isEqualsLocation = (Boolean)obj[4];
				int idLocation = (Integer) obj[3];
				if (isEqualsLocation)
					if (bean != null &&
							bean.getLocation()!= null &&
							bean.getLocation().getLocationid() != null ){
						if (bean.getLocation().getLocationid() == MIX_LOCATION_DEFAULT)
							loadComboLocation(MIX_LOCATION_DEFAULT, true);
						else if( bean.getLocation().getLocationid().intValue() == idLocation)
							loadComboLocation(idLocation, true);
						else 
						{
							printErrorForLocationAndSeason(LBL_TEMPORAL_SAMPLE_MESSAGE_ERR_LOCATION);
							closeWindow();
							return;
						}
					}
					else
						loadComboLocation(idLocation, true);
				else 
					if (bean != null &&
							bean.getLocation()!= null &&
							bean.getLocation().getLocationid() != null ){
						if (bean.getLocation().getLocationid().intValue() == MIX_LOCATION_DEFAULT)
								loadComboLocation(MIX_LOCATION_DEFAULT, true);
						else 
						{
							printErrorForLocationAndSeason(LBL_TEMPORAL_SAMPLE_MESSAGE_ERR_LOCATION);
							closeWindow();
							return;
						}
					}
					else
						loadComboLocation(MIX_LOCATION_DEFAULT, true);
			}
		}
	}
	
	private void printErrorForLocationAndSeason(String menErr){
		Messagebox.show(pro.getKey(menErr), 
				pro.getKey(LBL_GENERIC_MESS_ERROR), 
				Messagebox.OK, Messagebox.ERROR);
	}
	private void loadComboSeason (int id, boolean disabled){
		List<SeasonBean> beanList =serviceSeason.getSeasons(new SeasonBean());
		if (beanList != null && !beanList.isEmpty()){
			for (SeasonBean bean : beanList){
				Comboitem item = new Comboitem(bean.getSeason_name());
				item.setValue(bean.getSeason(bean));
				idComboSeason.appendChild(item);
				if(bean.getSeasonid() == id){
					idComboSeason.setSelectedItem(item);
				}
			}
			idComboSeason.setDisabled(disabled);
		}
	}
	private void loadComboLocation (int id, boolean disabled){
		List<LocationBean> beanList =serviceLocation.getLocation(new LocationBean());
		if (beanList != null && !beanList.isEmpty()){
			for (LocationBean bean : beanList){
				Comboitem item = new Comboitem(bean.getLocation_name());
				item.setValue(bean.getLocationBean(bean));
				idComboLocation.appendChild(item);
				if(bean.getLocationid() == id){
					idComboLocation.setSelectedItem(item);
				}
			}
			idComboLocation.setDisabled(disabled);
		}
	}
	private void loadComboTissue (int id){
		List<TissueBean> beanList =serviceTissue.getTissue(new TissueBean());
		if (beanList != null && !beanList.isEmpty()){
			for (TissueBean bean : beanList){
				Comboitem item = new Comboitem(bean.getTissueName());
				item.setValue(bean.getTissue(bean));
				idComboTissue.appendChild(item);
				if(bean.getIdtissue() == id){
					idComboTissue.setSelectedItem(item);
				}
			}
		}
	}

	private void loadComboOrganism (int id,boolean disabled){
		List<Organism> beanList =serviceOrganism.getOrganisms(new Organism());
		if (beanList != null && !beanList.isEmpty()){
			for (Organism bean : beanList){
				Comboitem item = new Comboitem(bean.getOrganismname());
				item.setValue(bean);
				idComboOrganism.appendChild(item);
				if(bean.getOrganismid() == id){
					idComboOrganism.setSelectedItem(item);
				}
			}
			idComboOrganism.setDisabled(disabled);
		}
	}

	private void loadComboProject(int id,boolean disabled){
		List<ProjectBean> listProject = seriviceProject.getProject(new ProjectBean());
		if (listProject != null && !listProject.isEmpty()){
			for (ProjectBean bean : listProject){
				Comboitem item = new Comboitem(bean.getProjectname()+bean.getPurposename());
				item.setValue(bean.getProject(bean));
				idComboProject.appendChild(item);
				if (bean.getProjectid() == id){
					idComboProject.setSelectedItem(item);
				}
			}
			idComboProject.setDisabled(disabled);
		}
	}

	private void loadComboInvestigator(int id , boolean disabled){
		List<InvestigatorBean> beanList =serviceInvestigator.getInvestigator(new InvestigatorBean());
		if (beanList != null && !beanList.isEmpty()){
			for (InvestigatorBean bean : beanList){
				Comboitem item = new Comboitem(bean.getInvest_name());
				item.setValue(bean.getInvestigator(bean));
				idComboInvestigator.appendChild(item);
				if(bean.getInvestigatorid() == id){
					idComboInvestigator.setSelectedItem(item);
				}
			}
			idComboInvestigator.setDisabled(disabled);
		}
	}

	private void loadComponents(){
		id = (Intbox)getFellow("id");
		idComboProject = (Combobox)getFellow("idComboProject");
		idComboInvestigator = (Combobox)getFellow("idComboInvestigator");
		idComboOrganism = (Combobox)getFellow("idComboOrganism");
		idComboTissue = (Combobox)getFellow("idComboTissue");
		idComboLocation = (Combobox)getFellow("idComboLocation");
		idComboSeason = (Combobox)getFellow("idComboSeason");
		idDNTemplate = (Combobox)getFellow("idDNTemplate");
		idTTitle = (Textbox)getFellow("idTTitle");
		objetive = (Textbox)getFellow("objetive");
		idDBRegDate = (Datebox)getFellow("idDBRegDate");
		idDBRegDateEnd = (Datebox)getFellow("idDBRegDateEnd");
		idFisheyeNext = (Fisheye)getFellow("idFisheyeNext");
	}

	public void validateFields(){
		if(idComboProject.getSelectedIndex() == -1)
			return;
		bean.setProject((Project)idComboProject.getSelectedItem().getValue());
		if(idComboInvestigator.getSelectedIndex() == -1)
			return;
		bean.setInvestigatorid((Investigator)idComboInvestigator.getSelectedItem().getValue());
		if(idComboOrganism.getSelectedIndex() == -1)
			return;
		bean.setOrganismid((Organism)idComboOrganism.getSelectedItem().getValue());
		if(idComboTissue.getSelectedIndex() == -1)
			return;
		bean.setTissue((Tissue)idComboTissue.getSelectedItem().getValue());
		if(idComboLocation.getSelectedIndex() == -1)
			return;
		bean.setLocation((LocationCatalog)idComboLocation.getSelectedItem().getValue());
		if(idComboSeason.getSelectedIndex() == -1)
			return;
		bean.setSeason((Season)idComboSeason.getSelectedItem().getValue());
		if(idTTitle.getText().trim().equals(""))
			return;
		bean.setTitle(idTTitle.getText().trim());
		if (objetive.getText().trim().equals(""))
			return;
		bean.setObjective(objetive.getText().trim());
		bean.setStartdate(idDBRegDate.getValue());
		bean.setEnddate(idDBRegDateEnd.getValue());
		if(idDNTemplate.getSelectedIndex() == -1 )
            return;
		bean.setStudytemplateid((StudyTemplate)idDNTemplate.getSelectedItem().getValue());
		idFisheyeNext.setVisible(true);
	}

	public void nextPag(){
		if (!disabled){
			bean.setTitle(idTTitle.getText().trim());
			bean.setObjective(objetive.getText().trim());
		List<LabStudy> listStudies = serviceLabStudy.getLabStudys(bean);
			if (listStudies != null && !listStudies.isEmpty()){
				Messagebox.show(pro.getKey(LBL_GENERIC_MESS_ER_DIF_REG,new String []{pro.getKey(LBL_GENERIC_MESS_STUDY_NAME)+" + "+
						pro.getKey(LBL_GENERIC_MESS_PURPOSE)}), 
						pro.getKey(LBL_GENERIC_MESS_ERROR), 
						Messagebox.OK, Messagebox.ERROR);
				return;
			}
		}
		if (getDesktop().getAttribute(ATTRIBUTE_EDIT_STUDIES) != null){
			getDesktop().setAttribute(ATTRIBUTE_LABSTUDY_ITEM_ORIGINAL, beanOriginal);
		}
		getDesktop().setAttribute(ATTRIBUTE_LABSTUDY_ITEM, bean);
		
		if(this.hasFellow("idWindowPlate")){
			((Window)this.getFellow("idWindowPlate")).doModal();
		}else{
			final Window win = (Window) Executions.createComponents(
	    			"/studies/window_add_plate_information.zul", this, null);
    		win.doModal();
		}
	}
}


