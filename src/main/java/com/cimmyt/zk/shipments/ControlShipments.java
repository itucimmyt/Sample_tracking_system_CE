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

import static com.cimmyt.utils.Constants.ATTRIBUTE_EDIT_SHIPMENT;
import static com.cimmyt.utils.Constants.ATTRIBUTE_NAME_USER_BEAN;
import static com.cimmyt.utils.Constants.ATTRIBUTE_PARAM_MAP_FUNTION;
import static com.cimmyt.utils.Constants.ATTRIBUTE_SHIPMENTS_ITEM;
import static com.cimmyt.utils.Constants.ATTRIBUTE_SHIPMENT_ITEM;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_DELETE_RECORD;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_DELETE_SUCCESS;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_ERROR;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_ERROR_CRITERIAL;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_INFORMATION;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_INVESTIGATOR;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_PLEASE_CONFIRM;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_SELECT_RECORD;
import static com.cimmyt.utils.Constants.LBL_MENU_TOOL_CANCEL;
import static com.cimmyt.utils.Constants.LBL_MENU_TOOL_RECEIVED;
import static com.cimmyt.utils.Constants.LBL_MENU_TOOL_SEND;
import static com.cimmyt.utils.Constants.LBL_SHIPMENT_CORN_REPORT;
import static com.cimmyt.utils.Constants.LBL_SHIPMENT_DART_REPORT;
import static com.cimmyt.utils.Constants.LBL_SHIPMENT_KBIO_REPORT;
import static com.cimmyt.utils.Constants.LBL_SHIPMENT_KBIO_REPORT2;
import static com.cimmyt.utils.Constants.LBL_SHIPMENT_PROVIDER_CORNELL;
import static com.cimmyt.utils.Constants.LBL_SHIPMENT_PROVIDER_DART;
import static com.cimmyt.utils.Constants.LBL_SHIPMENT_PROVIDER_KBIOS;
import static com.cimmyt.utils.Constants.LBL_SHIPMENT_PROVIDER_SAGA;
import static com.cimmyt.utils.Constants.LBL_SHIPMENT_STATUS_FOR_SEND;
import static com.cimmyt.utils.Constants.LBL_SHIPMENT_STATUS_NO_SELECT;
import static com.cimmyt.utils.Constants.LBL_SHIPMENT_STATUS_RECEIVED;
import static com.cimmyt.utils.Constants.LBL_SHIPMENT_STATUS_SENT;
import static com.cimmyt.utils.Constants.LBL_SHIPMENT_STRING_FOR_SEND;
import static com.cimmyt.utils.Constants.LBL_SHIPMENT_STRING_RECEIVED;
import static com.cimmyt.utils.Constants.LBL_SHIPMENT_STRING_SENT;
import static com.cimmyt.utils.Constants.LBL_SHIPMENT_TITLE_SUB_ADD_S;
import static com.cimmyt.utils.Constants.LBL_SHIPMENT_TITLE_SUB_ADD_T;
import static com.cimmyt.utils.Constants.LBL_SHIPMENT_TITLE_SUB_EDIT_T;
import static com.cimmyt.utils.Constants.LOCALE_LANGUAGE;
import static com.cimmyt.utils.Constants.MSG_SHIPMENT_DELETE_ASSIGNED;
import static com.cimmyt.utils.Constants.MSG_SHIPMENT_DELETE_ERROR;
import static com.cimmyt.utils.Constants.MSG_SHIPMENT_DELETE_STATUS;
import static com.cimmyt.utils.Constants.SAMPLE_DETAIL_SERVICE_BEAN_NAME;
import static com.cimmyt.utils.Constants.SHIPMENT_SERVICE_BEAN_NAME;
import static com.cimmyt.utils.Constants.SHIPMENT_SERVICE_DETAIL_BEAN_NAME;
import static com.cimmyt.utils.Constants.SHIPMENT_SERVICE_SET_BEAN_NAME;
import static com.cimmyt.utils.Constants.URL_IMAGES_ADD_BUTTON;
import static com.cimmyt.utils.Constants.URL_IMAGES_CANC_BUTTON;
import static com.cimmyt.utils.Constants.URL_IMAGES_CSV_BUTTON;
import static com.cimmyt.utils.Constants.URL_IMAGES_RECE_BUTTON;
import static com.cimmyt.utils.Constants.URL_IMAGES_XLS_BUTTON;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listgroup;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.cimmyt.bean.UserBean;
import com.cimmyt.model.bean.Investigator;
import com.cimmyt.model.bean.Shipment;
import com.cimmyt.model.bean.ShipmentDetail;
import com.cimmyt.model.bean.ShipmentSet;
import com.cimmyt.service.ServiceSampleDetail;
import com.cimmyt.service.ServiceShipment;
import com.cimmyt.service.ServiceShipmentDetail;
import com.cimmyt.service.ServiceShipmentSet;
import com.cimmyt.utils.Constants;
import com.cimmyt.utils.PropertyHelper;
import com.cimmyt.utils.StrUtils;

@SuppressWarnings("serial")
public class ControlShipments extends Window {


	private static ServiceShipmentSet serviceShipmentSet = null;
	private static ServiceShipment serviceShipment = null;
	private static ServiceShipmentDetail serviceShipmentDetail = null;
	private static ServiceSampleDetail serviceSampleDetail;
	private PropertyHelper pro=null;
    private Logger logger= Logger.getLogger(ControlShipments.class);
    private Listbox idLisB;
    private final String ID_ADD = "shipmentManagment$idAdd";
    private final String ID_EDIT = "shipmentManagment$idEdit";
    private final String ID_DELETE = "shipmentManagment$idDelete";
    private Hbox idHBoxShipment;
    private UserBean userBean;

	static {
		if(serviceShipmentSet == null)
        {
			try{
				serviceShipmentSet = (ServiceShipmentSet)SpringUtil.getBean(SHIPMENT_SERVICE_SET_BEAN_NAME);
			}catch (Exception e){
				e.printStackTrace();
			}
        }
		
	}
	static {
		if(serviceShipment == null)
        {
			try{
				serviceShipment = (ServiceShipment)SpringUtil.getBean(SHIPMENT_SERVICE_BEAN_NAME);
			}catch (Exception e){
				e.printStackTrace();
			}
        }
		
	}

	static {
		if(serviceShipmentDetail == null)
        {
			try{
				serviceShipmentDetail = (ServiceShipmentDetail)SpringUtil.getBean(SHIPMENT_SERVICE_DETAIL_BEAN_NAME);
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

	/**
	 * Create components of window 
	 */
	public void doAfterCompose (){
		pro = (PropertyHelper)getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);
		if (getDesktop().getSession().getAttribute(ATTRIBUTE_NAME_USER_BEAN) != null){
			userBean = (UserBean)getDesktop().getSession().getAttribute(ATTRIBUTE_NAME_USER_BEAN);
		}
		List<ShipmentSet> listBean = null;
		listBean = serviceShipmentSet.getShipmentsSets(new ShipmentSet(),userBean);
		drawShipmentSets(listBean);
		loadFisheye();
		
	}
	

	@SuppressWarnings("unchecked")
	private void loadFisheye (){
		idHBoxShipment = (Hbox)getFellow("idHBoxShipment");
		Map <String, String> mapFuntions;
		if (getDesktop().getSession().getAttribute(ATTRIBUTE_PARAM_MAP_FUNTION) != null){
			mapFuntions = (Map<String, String>) getDesktop().getSession().getAttribute(ATTRIBUTE_PARAM_MAP_FUNTION);
			if (mapFuntions.get(ID_ADD) == null){
				Image id = (Image)getFellow(ID_ADD);
				idHBoxShipment.removeChild(id);
			}
			if (mapFuntions.get(ID_EDIT) == null){
				Image id = (Image)getFellow(ID_EDIT);
				idHBoxShipment.removeChild(id);
			}
			if (mapFuntions.get(ID_DELETE) == null){
				Image id = (Image)getFellow(ID_DELETE);
				idHBoxShipment.removeChild(id);
			}
		}
	}

	/**
	 * Draws the list of all SETs and its children
	 * @param listBean
	 */
	private void drawShipmentSets(List<ShipmentSet> listBean){
		idLisB = (Listbox)getFellow("idLisB");
		clearList(idLisB);


		if (listBean != null && !listBean.isEmpty()) {
			for (ShipmentSet beanI : listBean){
				loadItem( beanI);
			}
		}
	}

	/**
	 * Draw a SET and all its children.
	 * Load the list of ShipmentSet and configure the functions for status and export to every shipment
	 * @param bean Tghe Shipment Set to display.
	 */
	private void loadItem (ShipmentSet bean){
		Listgroup lGp = new Listgroup();
		String groupTitle = pro.getKey(LBL_GENERIC_MESS_INVESTIGATOR)
				.concat(": ")
				.concat(bean.getStInvestigator() != null && bean.getStInvestigator().getInvest_name() != null ? bean.getStInvestigator().getInvest_name():"Missing")
				.concat(" [ ")
				.concat(bean.getComments())
				.concat(" - ")
				.concat(StrUtils.getDateFormat(bean.getDatCreated()))
				.concat(" ]");

		Listcell cell1 = new Listcell(groupTitle);
		cell1.setSpan(6);
		lGp.appendChild(cell1);
		Listcell cell6 = new Listcell();
		cell6.appendChild(createAddBtn(URL_IMAGES_ADD_BUTTON, bean));
		lGp.appendChild(cell6);
		lGp.setValue(bean);
		lGp.setVflex("1");
		lGp.setCheckable(true);
		
		idLisB.appendChild(lGp);
		for(Shipment singleShip :  serviceShipment.getShipments(bean)){
			char idStatus=singleShip.getStStatus().getIdStatus().toCharArray()[0];
			
			Listitem lIt = new Listitem ();
			Listcell cell11 = new Listcell(singleShip.getDatRegister()!=null?singleShip.getDatRegister().toString():"");
			lIt.appendChild(cell11);
			Listcell cell12 = new Listcell(singleShip.getDatSend()!=null?singleShip.getDatSend().toString():"");
			lIt.appendChild(cell12);
			Listcell cell13 = new Listcell(singleShip.getDatReceive()!=null?singleShip.getDatReceive().toString():"");
			lIt.appendChild(cell13);
			Listcell cell17 = new Listcell(singleShip.getStCompany().getName());
			lIt.appendChild(cell17);
			Listcell cell14 = new Listcell(singleShip.getStStatus().getStatusDescription());
			lIt.appendChild(cell14);
			Listcell cell15 = new Listcell(singleShip.getComment());
			lIt.appendChild(cell15);
			Listcell cell16 = new Listcell();
			switch (idStatus){
				case LBL_SHIPMENT_STATUS_FOR_SEND:
					cell16.appendChild(createSendBtn(singleShip));
					break;
				case LBL_SHIPMENT_STATUS_SENT:
					cell16.appendChild(createReceBtn(singleShip));
					cell16.appendChild(createCancBtn(singleShip));
					break;
				case LBL_SHIPMENT_STATUS_RECEIVED:
					cell16.appendChild(createCancBtn(singleShip));
					break;
			}
			switch (singleShip.getStCompany().getIdCompany().intValue()){
				case LBL_SHIPMENT_PROVIDER_KBIOS:
					cell16.appendChild(createKbiosBtn(singleShip, 1));
					cell16.appendChild(createKbiosBtn(singleShip, 2));
					break;
				case LBL_SHIPMENT_PROVIDER_CORNELL:
					cell16.appendChild(createCornellBtn(singleShip));
					break;
				case LBL_SHIPMENT_PROVIDER_DART:
					cell16.appendChild(createDartBtn(singleShip));
					break;
				case LBL_SHIPMENT_PROVIDER_SAGA:
					cell16.appendChild(createDartBtn(singleShip));
					break;
				case Constants.LBL_SHIPMENT_PROVIDER_INTERTEK_FORMAT_1:
					cell16.appendChild(createIntertekBtn(singleShip));
					break;
				case Constants.LBL_SHIPMENT_PROVIDER_INTERTEK_FORMAT_2:
					cell16.appendChild(createIntertekBtn(singleShip));
					break;
				default :
					cell16.appendChild(createDartBtn(singleShip));
			}
			lIt.appendChild(cell16);
			lIt.setValue(singleShip);
			
			idLisB.appendChild(lIt);
		}
		idLisB.setVflex("1");
	}

	private void clearList(Listbox list){
		if (list !=null && !list.getItems().isEmpty()) {
			while (!list.getItems().isEmpty() && list.getChildren().size()>0) {
				list.removeItemAt(0);
				
			}
		}
	}
	/**
	 * Add new Shipment
	 */
	public void add () {
			showWindow(pro.getKey(LBL_SHIPMENT_TITLE_SUB_ADD_T));
			ShipmentSet bean = (ShipmentSet) getDesktop().getAttribute(ATTRIBUTE_SHIPMENTS_ITEM);
			if (bean != null){
				//getDesktop().setAttribute(ATTRIBUTE_SHIPMENTS_ITEM, null);
				cleanSessionShipSet();
				doAfterCompose();
			}
	}
	
	private void showWindow(String title) {
		final Window win = (Window) Executions.createComponents(
    			"/shipment_management/window_add_shipment.zul", this, null);
		win.setTitle(title);	
    		win.doModal();
	}

	/**
	 * Edit Studies load in the window
	 */
	public void edit (){
		
		idLisB = (Listbox)getFellow("idLisB");
		if (idLisB.getSelectedIndex() != -1){
			Listitem item =idLisB.getSelectedItem();
			if(isGroupSetSelected()){// edit a shipment set
				ShipmentSet beanShipmentSet = (ShipmentSet)item.getListgroup().getValue();
			    getDesktop().setAttribute(ATTRIBUTE_SHIPMENTS_ITEM ,beanShipmentSet );
			    getDesktop().setAttribute(ATTRIBUTE_EDIT_SHIPMENT, true);
				
			    List<Shipment> ships = serviceShipment.getShipments(beanShipmentSet);
			    if(ships== null || ships.size() == 0)
			    	showWindow(pro.getKey(LBL_SHIPMENT_TITLE_SUB_EDIT_T));
			    else
			    	Messagebox.show(pro.getKey(MSG_SHIPMENT_DELETE_ASSIGNED), pro.getKey(LBL_GENERIC_MESS_INFORMATION), 
			    			Messagebox.OK, Messagebox.EXCLAMATION);
				
			    cleanSessionShipSet();
			}else{//edit a shipment
				Shipment shipment = idLisB.getSelectedItem().getValue();
				if(shipment.getStStatus().getIdStatus().charAt(0) == LBL_SHIPMENT_STATUS_FOR_SEND){
					
				    getDesktop().setAttribute(ATTRIBUTE_SHIPMENT_ITEM ,idLisB.getSelectedItem().getValue() );
					
					Image fe = (Image)idLisB.getSelectedItem()
							.getListgroup().getLastChild().getFirstChild();
					MouseEvent ev = new MouseEvent(Events.ON_CLICK
							,fe);
					
					@SuppressWarnings("unchecked")
					//calls the action of new Shipment, but with a bean to modify
					EventListener<MouseEvent> listener =  (EventListener<MouseEvent>)fe.getEventListeners(Events.ON_CLICK).iterator().next();
					try {
						listener.onEvent(ev);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else{
					Messagebox.show(pro.getKey(MSG_SHIPMENT_DELETE_STATUS), pro.getKey(LBL_GENERIC_MESS_INFORMATION), Messagebox.OK, Messagebox.EXCLAMATION);//status error	
				}
			}

			}else {
				messageBox(pro.getKey(LBL_GENERIC_MESS_SELECT_RECORD));
			}
	}

	private void messageBox(String mess){
		Messagebox.show(mess,pro.getKey(LBL_GENERIC_MESS_INFORMATION), 
				Messagebox.OK, Messagebox.INFORMATION);
		
	}

	/**
	 * Deletes a Shipment/ Shipment Set. Send a message if the record cannot be deleted.
	 */
	public void delete(){

		int option = 0;
		
		if (idLisB.getSelectedItem() != null){
			
			if (Messagebox.show(pro.getKey(LBL_GENERIC_MESS_DELETE_RECORD), 
					pro.getKey(LBL_GENERIC_MESS_PLEASE_CONFIRM), 
					Messagebox.YES | Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
				try {
					if(!isGroupSetSelected()){  //user tries to delete a single Shipment
						Shipment shipment = (Shipment)idLisB.getSelectedItem().getValue();
						if(!shipment.getStStatus().getIdStatus().equals(LBL_SHIPMENT_STRING_FOR_SEND)){
							
							option = 1;
						}else{
						
							serviceShipment.deleteShipment(shipment);
							
						}
					}else if(serviceShipment.getShipments((ShipmentSet)idLisB.getSelectedItem().getValue()).size() > 0){//user tries to deelte a shipment set, but has shipments assigned
						option = 2;
					}else{ //proceeds to delete empty shipment set
						ShipmentSet shipSet = (ShipmentSet)idLisB.getSelectedItem().getValue();
						updateSampleDetailStatus(shipSet);
						serviceShipmentSet.delete(shipSet);
						
					}
					
				}catch (Exception sql){
					option = -1;
					logger.info("message->"+sql.getMessage());
					sql.printStackTrace();
				}
			}else{
				option = 4;
			}
		}else {
			option = 3;
		}
		
		switch(option){
			case -1: Messagebox.show(pro.getKey(MSG_SHIPMENT_DELETE_ERROR),pro.getKey(LBL_GENERIC_MESS_ERROR), Messagebox.OK, Messagebox.ERROR);
				break;
			case 1: Messagebox.show(pro.getKey(MSG_SHIPMENT_DELETE_STATUS), pro.getKey(LBL_GENERIC_MESS_INFORMATION), Messagebox.OK, Messagebox.EXCLAMATION);//status error
				break;
			case 2: Messagebox.show(pro.getKey(MSG_SHIPMENT_DELETE_ASSIGNED), pro.getKey(LBL_GENERIC_MESS_INFORMATION), Messagebox.OK, Messagebox.EXCLAMATION);
				break;
			case 3: Messagebox.show(pro.getKey(LBL_GENERIC_MESS_SELECT_RECORD), pro.getKey(LBL_GENERIC_MESS_INFORMATION), Messagebox.OK, Messagebox.EXCLAMATION);
				break;
			case 4: break;
			default:
				doAfterCompose();
				Messagebox.show(pro.getKey(LBL_GENERIC_MESS_DELETE_SUCCESS), pro.getKey(LBL_GENERIC_MESS_INFORMATION),Messagebox.OK,Messagebox.INFORMATION);
		}
	}

	public void search(){
		
		Textbox idSN = (Textbox)getFellow("idSN");
		boolean notEmpty = false;
		ShipmentSet bean = new ShipmentSet();
		bean.setStInvestigator(new Investigator());
		
		if(idSN.getValue()!=null && !idSN.getValue().trim().equals("")){
			notEmpty = true;
			bean.getStInvestigator().setInvest_name(idSN.getValue().trim());

			List<ShipmentSet> listBean = serviceShipmentSet.getListByFilter(bean);
			drawShipmentSets(listBean);
		}
		if(!notEmpty){
			messageBox(pro.getKey(LBL_GENERIC_MESS_ERROR_CRITERIAL));
		}
	}

	/**
	 * Button for add a new Shipment
	 * @param urlImg The URL of the icon for the button
	 * @param shipSetBean the set/template this shipment will be attached to.
	 * @return a Fisheye button for creating a new shipment.
	 */
	public Image createAddBtn(String urlImg, ShipmentSet shipSetBean){
		Image imageBtn = new Image();
		imageBtn.setId(String.valueOf(shipSetBean.getIdShipmentSet()));
		imageBtn.setSrc(urlImg);
		imageBtn.setWidth("32px");
		imageBtn.setHeight("32px");
		imageBtn.addEventListener("onClick", new ShippmentButton());
		return imageBtn;
	}

	/**
	 * Button for sending a Shipment
	 * @param shipBean The shipment to mark as Sent.
	 * @return a Fisheye button for sending a shipment
	 */
	public Image createSendBtn( Shipment shipBean){
		
		Image fEyeBtn = new Image();
		fEyeBtn.setAttribute("shipId",String.valueOf(shipBean.getIdShipment()));
		fEyeBtn.setSrc(Constants.URL_IMAGES_SEND_BUTTON);
		fEyeBtn.setWidth("32px");
		fEyeBtn.setHeight("32px");
		fEyeBtn.setTooltiptext(pro.getKey(LBL_MENU_TOOL_SEND));
		fEyeBtn.addEventListener("onClick",new ShipmentEventListener(LBL_SHIPMENT_STRING_SENT, LBL_MENU_TOOL_SEND,LBL_MENU_TOOL_SEND));
		fEyeBtn.setTooltiptext(pro.getKey(LBL_MENU_TOOL_RECEIVED));
		return fEyeBtn;
	}

	/**
	 * Button for receiving a Shipment
	 * @param shipBean The shipment to mark as Received.
	 * @return a Fisheye button for receiving a shipment
	 */
	public Image createReceBtn( Shipment shipBean){
		
		Image fEyeBtn = new Image();
		fEyeBtn.setAttribute("shipId",String.valueOf(shipBean.getIdShipment()));
		fEyeBtn.setSrc(URL_IMAGES_RECE_BUTTON);
		fEyeBtn.setWidth("32px");
		fEyeBtn.setHeight("32px");
		fEyeBtn.addEventListener("onClick",new ShipmentEventListener(LBL_SHIPMENT_STRING_RECEIVED,LBL_MENU_TOOL_RECEIVED,LBL_SHIPMENT_TITLE_SUB_ADD_S));
		fEyeBtn.setTooltiptext(pro.getKey(LBL_MENU_TOOL_RECEIVED));
		return fEyeBtn;
	}

	/**
	 * Button for cancel an action over a Shipment
	 * @param shipBean The shipment to cancel.
	 * @return a Fisheye button for canceling/reversing the status of a shipment
	 */
	public Image createCancBtn(Shipment shipBean){
		
		Image fEyeBtn = new Image();
		fEyeBtn.setAttribute("shipId",String.valueOf(shipBean.getIdShipment()));
		fEyeBtn.setSrc(URL_IMAGES_CANC_BUTTON);
		fEyeBtn.setWidth("32px");
		fEyeBtn.setHeight("32px");
		fEyeBtn.setTooltiptext(pro.getKey(LBL_MENU_TOOL_CANCEL));
		
		String cancelStatus;
		if(shipBean.getStStatus().getIdStatus().equals(LBL_SHIPMENT_STRING_SENT))
			cancelStatus = LBL_SHIPMENT_STRING_FOR_SEND;
		else
			cancelStatus = LBL_SHIPMENT_STRING_SENT;
		
		fEyeBtn.addEventListener("onClick",new ShipmentEventListener(cancelStatus, LBL_MENU_TOOL_CANCEL, LBL_SHIPMENT_TITLE_SUB_ADD_S));
		return fEyeBtn;
	}

	/**
	 * Create a .csv file with the format of K-Bioscience.
	 * @param shipBean The shipment which its information will be exported.
	 * @return A Fisheye button for creating a report
	 */
	public Image createKbiosBtn(Shipment shipBean,int numReport){
		
		Image fEyeBtn = new Image();
		fEyeBtn.setAttribute("shipId",String.valueOf(shipBean.getIdShipment()));
		fEyeBtn.setAttribute("numReport", numReport);
		fEyeBtn.setSrc(URL_IMAGES_XLS_BUTTON);
		fEyeBtn.setWidth("32px");
		fEyeBtn.setHeight("32px");
		if(numReport==1){
			fEyeBtn.setTooltiptext(pro.getKey(LBL_SHIPMENT_KBIO_REPORT));
		}else{
			fEyeBtn.setTooltiptext(pro.getKey(LBL_SHIPMENT_KBIO_REPORT2));
		}
		fEyeBtn.addEventListener("onClick", new ShippmentKbiosButton());
		return fEyeBtn;
	}
	

	/**
	 * Create a .csv file with the format of K-Bioscience.
	 * @param shipBean The shipment which its information will be exported.
	 * @return A Fisheye button for creating a report
	 */
	public Image createIntertekBtn(Shipment shipBean){
		Image fEyeBtn = new Image();
		fEyeBtn.setAttribute("shipId",String.valueOf(shipBean.getIdShipment()));
		fEyeBtn.setSrc(URL_IMAGES_XLS_BUTTON);
		fEyeBtn.setWidth("32px");
		fEyeBtn.setHeight("32px");
		fEyeBtn.setTooltiptext(pro.getKey(Constants.LBL_SHIPMENT_INTERTEK_REPORT));
		fEyeBtn.addEventListener("onClick", new ShipmentGenotypingService());
		return fEyeBtn;
	}

	/**
	 * Create a .csv file with the format of Cornell.
	 * @param shipBean The shipment which its information will be exported.
	 * @return A Fisheye button for creating a report
	 */
	public Image createCornellBtn(Shipment shipBean){
		Image fEyeBtn = new Image();
		fEyeBtn.setAttribute("shipId",String.valueOf(shipBean.getIdShipment()));
		fEyeBtn.setSrc(URL_IMAGES_CSV_BUTTON);
		fEyeBtn.setWidth("32px");
		fEyeBtn.setHeight("32px");
		fEyeBtn.setTooltiptext(pro.getKey(LBL_SHIPMENT_CORN_REPORT));
		fEyeBtn.addEventListener("onClick", new ShippmentCornellButton());
		return fEyeBtn;
	}

	/**
	 * Create a .csv file with the format of DArT.
	 * @param shipBean The shipment which its information will be exported.
	 * @return A Fisheye button for creating a report
	 */
	public Image createDartBtn(Shipment shipBean){
		
		Image fEyeBtn = new Image();
		fEyeBtn.setAttribute("shipId",String.valueOf(shipBean.getIdShipment()));
		fEyeBtn.setSrc(URL_IMAGES_CSV_BUTTON);
		fEyeBtn.setWidth("32px");
		fEyeBtn.setHeight("32px");
		fEyeBtn.setTooltiptext(pro.getKey(LBL_SHIPMENT_DART_REPORT));
		fEyeBtn.addEventListener("onClick", new ShippmentDartButton());
		return fEyeBtn;
	}
	/**
	 * Indicates the type of the last element that the user selects in the grid.
	 * @return {@code true} if the element is a Shpment Set, or false if it is a Shipment.
	 */
	private boolean isGroupSetSelected(){
		return idLisB.getSelectedItem().getClass().equals(Listgroup.class);
	}
	
	/**
	 * Removes the bean of type Shipment Set from the session.
	 */
	private void cleanSessionShipSet(){
		if (getDesktop().getAttribute(ATTRIBUTE_SHIPMENTS_ITEM) != null){

			getDesktop().removeAttribute(ATTRIBUTE_SHIPMENTS_ITEM);
			getDesktop().removeAttribute(ATTRIBUTE_EDIT_SHIPMENT);
			doAfterCompose();
		}

	}
/**
 * Marks samples that no longer belong to a shipment set, so the y can be selected for creating new shipment sets. 
 * @param shipSet The shipment set whose samples will change to the status NO_SELECT.
 */
	public void updateSampleDetailStatus(ShipmentSet shipSet){
		List<ShipmentDetail> shipDetails = serviceShipmentDetail.getShipmentDetails(shipSet);
		List<Integer> shipDetailIds = new ArrayList<Integer>();
		for(ShipmentDetail d : shipDetails){
			shipDetailIds.add(d.getStSampleDetail().getStudysampleid());
		}
		serviceSampleDetail.updateListStatus(shipDetailIds, LBL_SHIPMENT_STATUS_NO_SELECT);

	}
}


