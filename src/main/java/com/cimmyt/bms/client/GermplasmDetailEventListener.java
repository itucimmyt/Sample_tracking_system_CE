package com.cimmyt.bms.client;

import static com.cimmyt.utils.Constants.ATTRIBUTE_NAME_USER_BEAN;
import static com.cimmyt.utils.Constants.BMS_SERVICE_CLIENT;
import static com.cimmyt.utils.Constants.LBL_BMS_COL_LIST_HEAD_DETAIL_CROOS;
import static com.cimmyt.utils.Constants.LBL_BMS_COL_LIST_HEAD_DETAIL_DESIG;
import static com.cimmyt.utils.Constants.LBL_BMS_COL_LIST_HEAD_ENTRY_CODE;
import static com.cimmyt.utils.Constants.LBL_BMS_COL_LIST_HEAD_SEED_SOURCE;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MEN_COL_GID;
import static com.cimmyt.utils.Constants.LBL_GENERIC_SHOW_INFORMATION;
import static com.cimmyt.utils.Constants.LOCALE_LANGUAGE;
import static com.cimmyt.utils.Constants.BMS_SERVICE_API;

import java.util.List;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Row;

import com.cimmyt.bean.UserBean;
import com.cimmyt.bms.client.dto.GermplamsDetail;
import com.cimmyt.bms.client.dto.Germplasm;
import com.cimmyt.bms.client.dto.GermplasmEntries;
import com.cimmyt.bms.service.BMSService;
import com.cimmyt.utils.PropertyHelper;
import com.cimmyt.utils.StrUtils;


public class GermplasmDetailEventListener implements EventListener<Event>{

	private PropertyHelper pro=null;
	
	private static BMSService serviceBMSClient = null;

	static {
	if (serviceBMSClient == null ){
		 try{
			 serviceBMSClient = (BMSService)SpringUtil.getBean(BMS_SERVICE_CLIENT);
		 	}catch (Exception ex){
		 		ex.printStackTrace();
		 	}	
		}
	}
	@Override
	public void onEvent(Event event) throws Exception {
		if (event.getTarget().getChildren() == null || event.getTarget().getChildren().isEmpty()){
			pro = (PropertyHelper)event.getTarget().getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);
			UserBean userBean =(UserBean) event.getTarget().getDesktop().getSession().getAttribute(ATTRIBUTE_NAME_USER_BEAN);
			Row row = (Row)event.getTarget().getParent();
			Germplasm germplasm = (Germplasm)row.getValue();
			
			GermplamsDetail detail = serviceBMSClient.getListGermplamsDetail(BMS_SERVICE_API, userBean.getCorp().toLowerCase(), germplasm.getListId());
			if (detail != null && detail.getGermplasmEntries() != null
					&& !detail.getGermplasmEntries().isEmpty()){
				event.getTarget().appendChild(getListBox(detail.getGermplasmEntries()));
			}else 
				StrUtils.messageBox(pro.getKey(LBL_GENERIC_SHOW_INFORMATION), pro);
		}
	}

	private Listbox getListBox(List<GermplasmEntries> listgermplasmEntries){
		Listbox listbox = new Listbox();
		Listhead head = new Listhead();
		head.appendChild(getListheader(pro.getKey(LBL_GENERIC_MEN_COL_GID),"20%"));
		head.appendChild(getListheader(pro.getKey(LBL_BMS_COL_LIST_HEAD_DETAIL_DESIG),"20%"));
		head.appendChild(getListheader(pro.getKey(LBL_BMS_COL_LIST_HEAD_SEED_SOURCE),"20%"));
		head.appendChild(getListheader(pro.getKey(LBL_BMS_COL_LIST_HEAD_ENTRY_CODE),"10%"));
		head.appendChild(getListheader(pro.getKey(LBL_BMS_COL_LIST_HEAD_DETAIL_CROOS),"30%"));
		listbox.appendChild(head);
		
		for (GermplasmEntries germplasmEntries : listgermplasmEntries){
			Listitem item = new Listitem();
			item.appendChild(new Listcell(germplasmEntries.getGid()+""));
			item.appendChild(new Listcell(germplasmEntries.getDesignation()));
			item.appendChild(new Listcell(germplasmEntries.getSeedSource()));
			item.appendChild(new Listcell(germplasmEntries.getEntryCode()));
			item.appendChild(new Listcell(germplasmEntries.getCross()));
			listbox.appendChild(item);
		}
		listbox.setMold("paging");
		listbox.setPageSize(15);
		return listbox;
	}

	private Listheader getListheader(String label, String size){
		Listheader listheader = new Listheader(label);
		listheader.setWidth(size);
		return listheader;
	}
}
