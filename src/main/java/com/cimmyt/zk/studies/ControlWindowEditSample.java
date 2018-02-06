package com.cimmyt.zk.studies;

import static com.cimmyt.utils.Constants.ATTRIBUTE_LABSTUDY_ITEM;
import static com.cimmyt.utils.Constants.ATTRIBUTE_NAME_USER_BEAN;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_INFORMATION;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_PLEASE_CONFIRM;
import static com.cimmyt.utils.Constants.LBL_STUDIES_CONTROL_DART;
import static com.cimmyt.utils.Constants.LBL_STUDIES_CONTROL_KBIOSCIENCES;
import static com.cimmyt.utils.Constants.LBL_STUDIES_PLATE_ITEM_BLANK;
import static com.cimmyt.utils.Constants.LBL_STUDIES_PLATE_ITEM_CONTROL;
import static com.cimmyt.utils.Constants.LBL_STUDIES_RANDOM_TUBE;
import static com.cimmyt.utils.Constants.LBL_STUDIES_SAVE_SUCCESS;
import static com.cimmyt.utils.Constants.LOCALE_LANGUAGE;
import static com.cimmyt.utils.Constants.PROJECT_SERVICE_BEAN_NAME;
import static com.cimmyt.utils.Constants.SAMPLE_SERVICE_BEAN_NAME;
import static com.cimmyt.utils.Constants.URL_IMAGES_ASSIGNED_TUBE;
import static com.cimmyt.utils.Constants.URL_IMAGES_BLANK_TUBE;
import static com.cimmyt.utils.Constants.URL_IMAGES_CONTROL_RANDOM_TUBE;
import static com.cimmyt.utils.Constants.URL_IMAGES_CONTROL_TUBE;
import static com.cimmyt.utils.Constants.URL_IMAGES_DART_CONTROL_TUBE;
import static com.cimmyt.utils.Constants.URL_IMAGES_KBIO_CONTROL_TUBE;

import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;

import com.cimmyt.bean.ItemSampleBean;
import com.cimmyt.bean.ProjectBean;
import com.cimmyt.bean.UserBean;
import com.cimmyt.model.bean.LabStudy;
import com.cimmyt.model.bean.Project;
import com.cimmyt.model.bean.SampleDetail;
import com.cimmyt.service.ServiceLog;
import com.cimmyt.service.ServiceProject;
import com.cimmyt.service.ServiceSample;
import com.cimmyt.service.ServiceSampleDetail;
import com.cimmyt.study.PlateRow;
import com.cimmyt.utils.Constants;
import com.cimmyt.utils.ConstantsDNA;
import com.cimmyt.utils.PropertyHelper;
import com.cimmyt.utils.StrUtils;

@SuppressWarnings("serial")
public class ControlWindowEditSample extends Window{


	private Textbox idTXTSampleID;
	private Intbox idTintGID;
	private Textbox idTXTACC;
	private Intbox idTxtColPlantNo;
	private Checkbox idChkBSESA;
	private int index;
	private Window idWindowEditsample;
	private ItemSampleBean itemSampleBean = null;
	private LabStudy beanLabStudy;
	private Vbox idVbox;
	private PropertyHelper pro=null;
	private Combobox combo ;
	private String style = "border-color: red; font-style: oblique;";
	private static ServiceSample serviceSample = null;
	private static ServiceSampleDetail serviceSampleDetail = null;
	private static ServiceProject serviceProject = null;
	private boolean isSampleRepeat = false;
	private UserBean userBean;
	private static ServiceLog serviceLog;
	// service of projects
	static {
		if(serviceSample == null)
			try{
				serviceSample = (ServiceSample)SpringUtil.getBean(SAMPLE_SERVICE_BEAN_NAME);
			}catch (Exception e){
				e.printStackTrace();
			}
		if(serviceSampleDetail == null)
			try{
				serviceSampleDetail = (ServiceSampleDetail)SpringUtil.getBean(Constants.SAMPLE_DETAIL_SERVICE_BEAN_NAME);
			}catch (Exception e){
				e.printStackTrace();
			}
		if(serviceProject == null)
			try{
			serviceProject = (ServiceProject)SpringUtil.getBean(PROJECT_SERVICE_BEAN_NAME);
			}catch (Exception e){
				e.printStackTrace();
			}
		try{
			serviceLog = (ServiceLog)SpringUtil.getBean(Constants.LOG_SERVICE_BEAN);
			}catch (Exception e){
				e.printStackTrace();
			}
	}

	public void init (){
		pro = (PropertyHelper)getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);
		initComponents();
		
		idVbox =  (Vbox)getFellow("idVbox");
		if (getDesktop().getSession().getAttribute(ATTRIBUTE_NAME_USER_BEAN) != null)
			userBean = (UserBean)getDesktop().getSession().getAttribute(ATTRIBUTE_NAME_USER_BEAN);
		
		if (getDesktop().getSession().getAttribute(Constants.ATTRIBUTE_SAMPLE_ITEM) != null)
		index = (Integer)getDesktop().getSession().getAttribute(Constants.ATTRIBUTE_SAMPLE_ITEM);
		beanLabStudy = (LabStudy)getDesktop().getAttribute(ATTRIBUTE_LABSTUDY_ITEM);
		if (index == PlateRow.ASSIGNED_NUM){
			if (getDesktop().getSession().getAttribute(Constants.ATTRIBUTE_SAMPLE_ITEM_BEAN) != null){
				itemSampleBean = (ItemSampleBean)getDesktop().getSession().getAttribute(Constants.ATTRIBUTE_SAMPLE_ITEM_BEAN);
				createGrid();
				idChkBSESA.setChecked(true);
				loadInformation();
			}
		}else {
			createGridControl(index);
		}
	}

	public void onCheckAssigned (){
		if (idVbox.getChildren() != null && !idVbox.getChildren().isEmpty()){
			for (Component component : idVbox.getChildren()){
				if (component.getId().equals("idGridInfo")){
					idVbox.removeChild(component);
					break;
				}
			}
		}

		if (getDesktop().getSession().getAttribute(Constants.ATTRIBUTE_SAMPLE_ITEM_BEAN) != null)
			itemSampleBean = (ItemSampleBean)getDesktop().getSession().getAttribute(Constants.ATTRIBUTE_SAMPLE_ITEM_BEAN);

		if (getDesktop().getSession().getAttribute(Constants.ATTRIBUTE_SAMPLE_ITEM) != null)
			index = (Integer)getDesktop().getSession().getAttribute(Constants.ATTRIBUTE_SAMPLE_ITEM);

		if (idChkBSESA.isChecked()){
				createGrid();
				idChkBSESA.setChecked(true);
				if (itemSampleBean != null )
				loadInformation();
		}else {
			createGridControl(index);
		}

	}

	public void validateAndSave (){
		
		if (idChkBSESA.isChecked()){
			boolean thereAreError = false;

			if (idTintGID.getValue()!= null && idTintGID.getValue().intValue() > 0){
				idTintGID.setStyle("");
			}else {
				idTintGID.setStyle(style);
				thereAreError= true;
			}
			/*
			if (idTXTACC.getValue() != null && !idTXTACC.getValue().toString().equals("")){
				idTXTACC.setStyle("");
			}else{
				idTXTACC.setStyle(style);
				thereAreError= true;
			}*/
			idTXTACC.setStyle("");
			if (idTxtColPlantNo.getValue()!= null){
				idTxtColPlantNo.setStyle("");
			}else {
				idTxtColPlantNo.setStyle(style);
				thereAreError= true;
			}
			if (thereAreError){
				StrUtils.messageBoxError(pro.getKey(Constants.LBL_GENERIC_WINDOW_ERROR_FILL_FILDS), pro);
			return ;
			
			}else {
				
			if (itemSampleBean != null && isEqualsItemSample()){
				getDesktop().getSession().setAttribute(Constants.ATTRIBUTE_SAMPLE_ITEM,null);
				close();
			} else{
				if (Messagebox.show(pro.getKey(Constants.LBL_GENERIC_WINDOW_CONFIRM_CONTINUE), 
						pro.getKey(LBL_GENERIC_MESS_PLEASE_CONFIRM), 
						Messagebox.YES | Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
							Integer sampleID = serviceSample.searchSampleIDProjectInSt_SampleID(
							beanLabStudy.getProject().getProjectid(), idTintGID.getValue(), idTxtColPlantNo.getValue(),
							itemSampleBean!= null && itemSampleBean.getLocationidBean() != null && 
							itemSampleBean.getLocationidBean().getLocationid() != null ? 
							itemSampleBean.getLocationidBean().getLocationid() :
							beanLabStudy.getLocation().getLocationid(), 
							itemSampleBean!= null && itemSampleBean.getSeasonidBean() != null && 
							itemSampleBean.getSeasonidBean().getSeasonid() != null ?
							itemSampleBean.getSeasonidBean().getSeasonid() :
							beanLabStudy.getSeason().getSeasonid());
							if (sampleID != null && sampleID.intValue() > 0 && 
									itemSampleBean.getSampleID() != sampleID.intValue()){
								if (beanLabStudy.getOrganismid().getOrganismid().equals(new Integer(Constants.ATTRIBUTE_MAIZE))){	
									if (Messagebox.show(pro.getKey(Constants.LBL_STUDIES_EDIT_SAMPLE_ERROR_DUPLICATE), 
											pro.getKey(LBL_GENERIC_MESS_PLEASE_CONFIRM), 
											Messagebox.YES | Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
											isSampleRepeat = true;
											serviceSampleDetail.updateSampleDetail(getSampleID(sampleID));
											saveLog();
											getDesktop().getSession().setAttribute(Constants.ATTRIBUTE_SAMPLE_ITEM,PlateRow.ASSIGNED_NUM);
											Messagebox.show(pro.getKey(LBL_STUDIES_SAVE_SUCCESS), 
													pro.getKey(LBL_GENERIC_MESS_INFORMATION), 
													Messagebox.OK, Messagebox.INFORMATION);
											close();
											}else{
												getDesktop().getSession().setAttribute(Constants.ATTRIBUTE_SAMPLE_ITEM,null);
												close();
											}
								}else{
									StrUtils.messageBoxError(pro.getKey(Constants.LBL_STUDIES_EDIT_SAMPLE_ERROR_DUPLICATE_STOP), pro);
									return;
								}
							}else {
									
									serviceSampleDetail.updateSampleDetail(getSampleID(sampleID != null && sampleID.intValue() > 0 ? sampleID :null));
									saveLog();
									getDesktop().getSession().setAttribute(Constants.ATTRIBUTE_SAMPLE_ITEM,PlateRow.ASSIGNED_NUM);
									Messagebox.show(pro.getKey(LBL_STUDIES_SAVE_SUCCESS), 
											pro.getKey(LBL_GENERIC_MESS_INFORMATION), 
											Messagebox.OK, Messagebox.INFORMATION);
									close();
							}
						}else {
							getDesktop().getSession().setAttribute(Constants.ATTRIBUTE_SAMPLE_ITEM,null);
							close();
						}
				}
			}
		}else {
			if (index == PlateRow.ASSIGNED_NUM && combo.getSelectedIndex() == -1){
				combo.setStyle(style);
				StrUtils.messageBoxError(pro.getKey(Constants.LBL_GENERIC_WINDOW_ERROR_FILL_FILDS), pro);
				return;
			}else{
				if (combo.getSelectedIndex() == -1){
					getDesktop().getSession().setAttribute(Constants.ATTRIBUTE_SAMPLE_ITEM,null);
					close();
				}else {
					if (Messagebox.show(pro.getKey(Constants.LBL_GENERIC_WINDOW_CONFIRM_CONTINUE), 
							pro.getKey(LBL_GENERIC_MESS_PLEASE_CONFIRM), 
							Messagebox.YES | Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
								serviceSampleDetail.updateSampleDetail(getControlSampleDetail());
								saveLog();
								getDesktop().getSession().setAttribute(Constants.ATTRIBUTE_SAMPLE_ITEM,(Integer)combo.getSelectedItem().getValue());
								Messagebox.show(pro.getKey(LBL_STUDIES_SAVE_SUCCESS), 
										pro.getKey(LBL_GENERIC_MESS_INFORMATION), 
										Messagebox.OK, Messagebox.INFORMATION);
								close();
							}else {
								getDesktop().getSession().setAttribute(Constants.ATTRIBUTE_SAMPLE_ITEM,null);
								close();
							}
				}
			}
		}
	}

	private void saveLog(){
		serviceLog.persistLog(ConstantsDNA.LOG_ENTITY_STUDY, beanLabStudy.getLabstudyid()
				, ConstantsDNA.LOG_EDIT , userBean.getIdUser(), beanLabStudy.getTitle());
	}
	private SampleDetail getSampleID (Integer sampleID){
		SampleDetail detail = serviceSampleDetail.getSampleDetailByStudysampleid(itemSampleBean.getStudysampleid());
		if ((detail.getSamplegid() == null || detail.getSamplegid().intValue() == 0) && sampleID == null){
			int lastSample = serviceProject.getLastSampleID(new ProjectBean (beanLabStudy.getProject()));
			++lastSample;
			detail.setSamplegid(lastSample);
			Project projectToUpdate = beanLabStudy.getProject();
			projectToUpdate.setLastsampleid(lastSample);
			serviceProject.saveOrUpdateProject(new ProjectBean(projectToUpdate));
		}else if (detail.getSamplegid() == null && sampleID != null && sampleID.intValue() > 0  ){
		detail.setSamplegid(sampleID);
		}
		detail.setBreedergid(idTintGID.getValue());
		detail.setNplanta(idTxtColPlantNo.getValue());
		detail.setNval(idTXTACC.getValue());
		if (detail.getEntryNo() == null)
			detail.setEntryNo(0);
		if (detail.getControltype() != null)
			detail.setControltype("");
		if (detail.getLocationid() == null)
			detail.setLocationid(beanLabStudy.getLocation());
		if (detail.getSeasonid() == null)
			detail.setSeasonid(beanLabStudy.getSeason());
		detail.setPriority("");
		detail.setSpecie(itemSampleBean.getSpecie().toString());

		getDesktop().getSession().setAttribute(Constants.ATTRIBUTE_SAMPLE_ITEM_BEAN, new ItemSampleBean(detail, isSampleRepeat));
		getDesktop().getSession().setAttribute(Constants.ATTRIBUTE_SAMPLE_ITEM_BEAN_EDIT, detail);
		return detail;
	}

	private SampleDetail getControlSampleDetail (){
		SampleDetail detail = serviceSampleDetail.getSampleDetailByStudysampleid(itemSampleBean.getStudysampleid());
		detail.setBreedergid(null);
		detail.setSamplegid(null);
		detail.setEntryNo(null);
		detail.setNplanta(null);
		detail.setNval(null);
		detail.setControltype(StrUtils.getTypeOfControl((Integer)combo.getSelectedItem().getValue()));
		detail.setLocationid(null);
		detail.setSeasonid(null);
		getDesktop().getSession().setAttribute(Constants.ATTRIBUTE_SAMPLE_ITEM_BEAN, new ItemSampleBean(detail, isSampleRepeat));
		getDesktop().getSession().setAttribute(Constants.ATTRIBUTE_SAMPLE_ITEM_BEAN_EDIT, detail);
		return detail;
	}
	private boolean isEqualsItemSample (){
		if (!idTintGID.getValue().equals(itemSampleBean.getGid()))
			return false;
		if (!idTXTACC.getValue().equals(itemSampleBean.getAcc().toString()))
			return false;
		if (!idTxtColPlantNo.getValue().equals(itemSampleBean.getPlantNo()))
			return false;
		return true;
	}
	
	public void createGridControl(int index){
		Grid idGridInfo = new Grid ();
		idGridInfo.setId("idGridInfo");
		 Rows rows = new Rows ();
			 Row row = new Row ();
			 	Cell cell = new Cell();
			 	Label label = new Label ();
			 	Image image = new Image();
		switch (index){
		case PlateRow.BLANK_ITEM_NUM :
			label.setValue(pro.getKey(LBL_STUDIES_PLATE_ITEM_BLANK));
			image.setSrc(URL_IMAGES_BLANK_TUBE);
			break;
		case PlateRow.ASSIGNED_NUM :
			label.setValue(pro.getKey(Constants.LBL_STUDIES_PLATE_ITEM_ASSIGNED));
			image.setSrc(URL_IMAGES_ASSIGNED_TUBE);
			break;
		case PlateRow.BLANK_CONTROL_NUM :
			label.setValue(pro.getKey(LBL_STUDIES_PLATE_ITEM_CONTROL));
			image.setSrc(URL_IMAGES_CONTROL_TUBE);
			break;
		case PlateRow.DART_CONTROL_ITEM_NUM :
			label.setValue(pro.getKey(LBL_STUDIES_CONTROL_DART));
			image.setSrc(URL_IMAGES_DART_CONTROL_TUBE);
			break;
		case PlateRow.KBIO_CONTROL_ITEM_NUM :
			label.setValue(pro.getKey(LBL_STUDIES_CONTROL_KBIOSCIENCES));
			image.setSrc(URL_IMAGES_KBIO_CONTROL_TUBE);
			break;
		case PlateRow.RANDOM_CONTROL_ITEM_NUM :
			label.setValue(pro.getKey(LBL_STUDIES_RANDOM_TUBE));
			image.setSrc(URL_IMAGES_CONTROL_RANDOM_TUBE);
			break;
		case PlateRow.NOT_CONTROL :
			label.setValue(pro.getKey(LBL_STUDIES_PLATE_ITEM_BLANK));
			image.setSrc(URL_IMAGES_BLANK_TUBE);
			break;
		default :
			break;
		}
		cell.appendChild(image);
		cell.appendChild(label);
		row.appendChild(cell);
		rows.appendChild(row);
		Row rowCombo = new Row();
		rowCombo.appendChild(getComboControls());
		rows.appendChild(rowCombo);
		idGridInfo.appendChild(rows);
		idVbox.appendChild(idGridInfo);
	}

	private Combobox getComboControls(){
		combo = new Combobox();
		combo.appendChild(getItemCombo(pro.getKey(LBL_STUDIES_PLATE_ITEM_BLANK), URL_IMAGES_BLANK_TUBE, PlateRow.BLANK_ITEM_NUM ));
		combo.appendChild(getItemCombo(pro.getKey(LBL_STUDIES_PLATE_ITEM_CONTROL), Constants.URL_IMAGES_CONTROL_TUBE, PlateRow.BLANK_CONTROL_NUM ));
		combo.appendChild(getItemCombo(pro.getKey(LBL_STUDIES_CONTROL_DART), Constants.URL_IMAGES_DART_CONTROL_TUBE, PlateRow.DART_CONTROL_ITEM_NUM ));
		combo.appendChild(getItemCombo(pro.getKey(LBL_STUDIES_CONTROL_KBIOSCIENCES), Constants.URL_IMAGES_KBIO_CONTROL_TUBE, PlateRow.KBIO_CONTROL_ITEM_NUM ));	
		combo.appendChild(getItemCombo(pro.getKey(LBL_STUDIES_RANDOM_TUBE), Constants.URL_IMAGES_CONTROL_RANDOM_TUBE, PlateRow.RANDOM_CONTROL_ITEM_NUM ));
		combo.setReadonly(true);
		return combo;
	}

	private Comboitem getItemCombo(String label , String imgSrc, int value){
		Comboitem item = new Comboitem (label);
		item.setImage(imgSrc);
		item.setValue(value);
		return item;
	}
	private void loadInformation(){
		String sampleId = itemSampleBean.getSampleID() >0 ? StrUtils.getSampleIDKey(beanLabStudy, itemSampleBean.getSampleID()) : "";
		idTXTSampleID.setText(sampleId);
		idTintGID.setText(Integer.toString(itemSampleBean.getGid()));
		idTXTACC.setText(itemSampleBean.getAcc().toString());
		idTxtColPlantNo.setText(Integer.toString(itemSampleBean.getPlantNo()));
	}
	
	private void initComponents(){
		idChkBSESA = (Checkbox)getFellow("idChkBSESA");
		idChkBSESA.setChecked(false);
		idWindowEditsample = (Window)getFellow("idWindowEditsample");
		
	}

	public void close(){
		idWindowEditsample.onClose();
	}

	private void createGrid (){
		Grid idGridInfo = new Grid ();
		idGridInfo.setId("idGridInfo");
		 Rows rows = new Rows ();
			 Row row = new Row ();
			 	Label labelSampleID = new Label ();
			 	labelSampleID.setValue(pro.getKey(ConstantsDNA.LBL_GENERIC_MESSAGE_SAMPLE_ID));
			 	row.appendChild(labelSampleID);
			 	idTXTSampleID = new Textbox();
			 	idTXTSampleID.setCols(12);
			 	idTXTSampleID.setMaxlength(14);
			 	idTXTSampleID.setWidth("150px");
			 	idTXTSampleID.setDisabled(true);
			 	row.appendChild(idTXTSampleID);
			 rows.appendChild(row);
			Row row1 = new Row();
				Label labelGID = new Label (pro.getKey(Constants.LBL_GENERIC_MEN_COL_GID));
				row1.appendChild(labelGID);
				idTintGID = new Intbox ();
				idTintGID.setId("idTintGID");
				idTintGID.setCols(11);
				idTintGID.setMaxlength(11);
				idTintGID.setWidth("150px");
				row1.appendChild(idTintGID);
			rows.appendChild(row1);
			
			Row row2 = new Row();
				Label labelACC = new Label (pro.getKey(Constants.LBL_GENERIC_MEN_COL_ACC));
				row2.appendChild(labelACC);
				idTXTACC = new Textbox ();
				idTXTACC.setId("idTXTACC");
				idTXTACC.setCols(12);
				idTXTACC.setMultiline(true);
				idTXTACC.setMaxlength(250);
				idTXTACC.setWidth("200px");
				idTXTACC.setHeight("50px");
				row2.appendChild(idTXTACC);
			rows.appendChild(row2);
			
			Row row3 = new Row();
				Label labelPlantN = new Label (pro.getKey(Constants.LBL_GENERIC_MEN_COL_PLANT_NO));
				row3.appendChild(labelPlantN);
				idTxtColPlantNo = new Intbox ();
				idTxtColPlantNo.setId("idTxtColPlantNo");
				idTxtColPlantNo.setCols(5);
				idTxtColPlantNo.setMaxlength(5);
				idTxtColPlantNo.setWidth("50px");
				
				row3.appendChild(idTxtColPlantNo);
			rows.appendChild(row3);
			idGridInfo.appendChild(rows);
			
			idVbox.appendChild(idGridInfo);
		 	
	}

}
