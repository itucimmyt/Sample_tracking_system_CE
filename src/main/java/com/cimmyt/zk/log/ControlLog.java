package com.cimmyt.zk.log;

import static com.cimmyt.utils.Constants.ATTRIBUTE_NAME_USER_BEAN;
import static com.cimmyt.utils.Constants.LOCALE_LANGUAGE;
import static com.cimmyt.utils.Constants.SHOW_ROWS_LIST;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.cimmyt.dnast.dto.AuthUserBean;
import org.cimmyt.dnast.dto.StInvestigator;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.PagingEvent;
import org.zkoss.zul.event.ZulEvents;

import com.cimmyt.bean.UserBean;
import com.cimmyt.exception.BackException;
import com.cimmyt.model.bean.Log;
import com.cimmyt.model.bean.LogTypeEntity;
import com.cimmyt.model.bean.LogTypeOperation;
import com.cimmyt.model.bean.Role;
import com.cimmyt.service.ServiceLog;
import com.cimmyt.service.ServiceUser;
import com.cimmyt.servlet.RedirectServletReport;
import com.cimmyt.servlet.SessionReport;
import com.cimmyt.utils.Constants;
import com.cimmyt.utils.ConstantsDNA;
import com.cimmyt.utils.PropertyHelper;
import com.cimmyt.utils.StrUtils;

@SuppressWarnings("serial")
public class ControlLog extends Window{

	private UserBean userBean;
	private static ServiceUser serviceUser= null;
	private static ServiceLog serviceLog= null;
	private static  Logger logger= Logger.getLogger(ControlLog.class);
	private Combobox idCombUser, idCombEntity, idCombOperation;
	private Datebox idInitDate, idFinalDate;
	private Paging idPagingLog;
	private Listbox idListBLog;
	private int sorting;
	private boolean ascending = true;
	private int rows = 0;
	private PropertyHelper pro=null;
	static {
		if (serviceUser == null || serviceLog == null){
			try{
				serviceUser = (ServiceUser)SpringUtil.getBean(Constants.USER_SERVICE_BEAN_NAME);
				serviceLog = (ServiceLog)SpringUtil.getBean(Constants.LOG_SERVICE_BEAN);
			}catch (Exception e){
				logger.error(e.getMessage());
			}
		}
	}
	
	public void init() throws BackException{
		userBean = (UserBean)getDesktop().getSession().getAttribute(ATTRIBUTE_NAME_USER_BEAN);
		pro = (PropertyHelper)getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);
		initComponents();
		loadComboUsers();
		loadComboOperation();
		loadComboEntity();
		addListenerPaging();
		getAndDrawLog(getLogFilter(), true);	
	}

	private void addListenerPaging (){
		idPagingLog.addEventListener(ZulEvents.ON_PAGING, new EventListener<PagingEvent>() {
			@Override
			public void onEvent(PagingEvent event) throws Exception {
				List<Log> listBean = getLog(event.getActivePage());
				clearListBox();
				if (listBean != null)
				for(Log study : listBean){
					loatItem(study);
				}
			}
		});
	}

	private void getAndDrawLog(Log log, boolean recountTotal){
		getAndDrawLog(log, recountTotal, 0);
	}

	private List<Log> getLog(int activePage){
		List<Log> listBean = serviceLog.getListLog(getLogFilter(), 
				activePage * SHOW_ROWS_LIST,
				SHOW_ROWS_LIST, sorting, ascending,
				getformatDate(idInitDate),getformatDate(idFinalDate),
				userBean.getRole().getIdstRol());
		return listBean;
	}
	private void clearListBox(){
		if (idListBLog.getItems()!= null && !idListBLog.getItems().isEmpty()){
			while (idListBLog.getItems().size() >= 1){
				idListBLog.getItems().remove(0);
			}
		}
	}
	private void loadComboUsers () throws BackException{
		List<UserBean> listBean = null;
		if (userBean != null ){
			listBean = serviceUser.getListUserBean(userBean);
			if (listBean != null && !listBean.isEmpty()){
				Comboitem itemAll = new Comboitem (pro.getKey(ConstantsDNA.LBL_LOG_SELECT_ALL));
				itemAll.setValue(null);
				idCombUser.appendChild(itemAll);
				for (UserBean user : listBean){
					Comboitem item = new Comboitem(user.getResearcherName());
					item.setDescription(user.getRole().getName());
					item.setValue(user);
					idCombUser.appendChild(item);
				}
			}
		}
	}

	private void loadComboEntity(){
		Comboitem item = new Comboitem(pro.getKey(Constants.LBL_GENERIC_STUDY));
		item.setValue(ConstantsDNA.LOG_ENTITY_STUDY);
		idCombEntity.appendChild(item);
		idCombEntity.setSelectedItem(item);
	}
	private void loadComboOperation(){
		idCombOperation.appendChild(setComboItem(pro.getKey(ConstantsDNA.LBL_LOG_SELECT_ALL), null));
		idCombOperation.appendChild(setComboItem(pro.getKey(ConstantsDNA.LBL_LOG_OPERATION_ADD), ConstantsDNA.LOG_ADD));
		idCombOperation.appendChild(setComboItem(pro.getKey(ConstantsDNA.LBL_LOG_OPERATION_EDIT), ConstantsDNA.LOG_EDIT));
		idCombOperation.appendChild(setComboItem(pro.getKey(ConstantsDNA.LBL_LOG_OPERATION_DELETE), ConstantsDNA.LOG_DELETE));
	}

	private Comboitem setComboItem(String str, Object obj){
		Comboitem item = new Comboitem(str);
		item.setValue(obj);
		return item;
	}
	public void doExportReport(int fileFormat){
		if (rows ==0){
			StrUtils.messageBox(pro.getKey(ConstantsDNA.LBL_LOG_NOT_INFORMATION), pro);
			return;
		}

		SessionReport sessionReport = new SessionReport();
		
		switch (fileFormat){
			case ConstantsDNA.FILE_TYPE_XLS:
				sessionReport.setType(ConstantsDNA.FILE_TYPE_XLS);
				sessionReport.setName("log_"+StrUtils.getDateFormat(new Date()));
				sessionReport.setB(serviceLog.getReportExcelLog(getLogFilter(), 
						1,
						rows, sorting, ascending,
						getformatDate(idInitDate),getformatDate(idFinalDate),
						userBean.getRole().getIdstRol(), getListHeaders()));
				break;
			case ConstantsDNA.FILE_TYPE_CSV:
				sessionReport.setB(new byte[0]);
				sessionReport.setType(ConstantsDNA.FILE_TYPE_CSV_GENERIC);
				sessionReport.setName("log_"+StrUtils.getDateFormat(new Date()));
				sessionReport.setCsvReportGenericBean(serviceLog.getReportCSVLog(getLogFilter(), 
						1,
						rows, sorting, ascending,
						getformatDate(idInitDate),getformatDate(idFinalDate),
						userBean.getRole().getIdstRol(), getListHeaders()));		
				break;
		}
		if (sessionReport.getB()!= null || sessionReport.getCsvReportGenericBean()!=null)
		RedirectServletReport.export(sessionReport);
		else
			StrUtils.messageBox(pro.getKey(ConstantsDNA.LBL_LOG_NOT_INFORMATION), pro);
	}

	private List<StringBuilder> getListHeaders(){
		List<StringBuilder> listHeaders = new ArrayList<StringBuilder>() ;
		listHeaders.add(getHeader(pro.getKey(ConstantsDNA.LBL_INVESTIGATOR_SUB_USER_NAME)));
		listHeaders.add(getHeader(pro.getKey(ConstantsDNA.LBL_LOG_OPERATION_LABEL)));
		listHeaders.add(getHeader(pro.getKey(ConstantsDNA.LBL_LOG_OPERATION_DATE)));
		listHeaders.add(getHeader(pro.getKey(ConstantsDNA.LBL_ROLE_DESCRIPTION_RESEARCHER)));
		listHeaders.add(getHeader(pro.getKey(ConstantsDNA.LBL_LOG_DESCRPTION)));
		return listHeaders;
	}

	private StringBuilder getHeader(String str){
		return new StringBuilder(str);
	}
	public void doFindDataSets(){
		getAndDrawLog(getLogFilter(), true);
	}

	private void initComponents(){
		idCombUser = (Combobox)getFellow("idCombUser");
		idCombEntity = (Combobox)getFellow("idCombEntity");
		idCombOperation = (Combobox)getFellow("idCombOperation");
		idInitDate = (Datebox)getFellow("idInitDate");
		idFinalDate = (Datebox)getFellow("idFinalDate");
		idPagingLog = (Paging)getFellow("idPagingLog");
		idListBLog = (Listbox)getFellow("idListBLog");
	}

	public void onSorting(Event event){
		Listheader header = (Listheader)event.getTarget();
		if((Integer.parseInt((String)header.getValue())) == sorting)
			ascending = !ascending;
		else
			ascending = true;

		sorting =(Integer.parseInt((String)header.getValue()));
		getAndDrawLog(getLogFilter(),false, idPagingLog.getActivePage());
	}

	private Log getLogFilter (){
		Log log = new Log();
		if (idCombUser.getSelectedIndex() > 0 && idCombUser.getSelectedItem().getValue() != null){
			UserBean user = (UserBean)idCombUser.getSelectedItem().getValue();
			AuthUserBean stUserVersion = new AuthUserBean();
			stUserVersion.setIdUser(user.getIdUser());
			stUserVersion.setInvestName(user.getResearcherName());
			Role stRole = new Role();
			stRole.setIdstRol(user.getRole().getIdstRol());
			stUserVersion.setStRole(stRole);
			StInvestigator investigator = new StInvestigator();
			investigator.setInvestigatorid(user.getInvestigatorBean().getInvestigatorid());
			stUserVersion.setInvestigator(investigator);
			log.setStUserVersion(stUserVersion);
		}else if (userBean.getRole() != null && userBean.getRole().getIdstRol()!= null && 
				userBean.getRole().getIdstRol().intValue() == ConstantsDNA.ROLE_RESEARCHER){
			AuthUserBean stUserVersion = new AuthUserBean();
			Role stRole = new Role();
			stRole.setIdstRol(userBean.getRole().getIdstRol());
			stUserVersion.setStRole(stRole);
			StInvestigator investigator = new StInvestigator();
			investigator.setInvestigatorid(userBean.getInvestigatorBean().getInvestigatorid());
			stUserVersion.setInvestigator(investigator);
			log.setStUserVersion(stUserVersion);
		}
		if (idCombOperation.getSelectedIndex() > 0 && idCombOperation.getSelectedItem().getValue() != null){
			LogTypeOperation logTyperOperation = new LogTypeOperation();
			logTyperOperation.setIdLogTypeOperation((Integer)idCombOperation.getSelectedItem().getValue());
			log.setLogTyperOperation(logTyperOperation);
		}
		LogTypeEntity logTypeEntity = new LogTypeEntity();
		logTypeEntity.setIdLogTypeEntity((Integer)idCombEntity.getSelectedItem().getValue());
		log.setLogTypeEntity(logTypeEntity);			
		return log;
	}
	private void getAndDrawLog(Log log, boolean recountTotal, int page){
		clearListBox();
		idPagingLog.setActivePage(0);
		idPagingLog.setTotalSize(0);
		idPagingLog.setVisible(false);
		
		List<Log> listBean;
		if(recountTotal){
			rows = serviceLog.getTotalRowsByFilter(log, userBean.getRole().getIdstRol(),
					getformatDate(idInitDate),getformatDate(idFinalDate));
			idPagingLog.setPageSize(SHOW_ROWS_LIST);
			idPagingLog.setActivePage(0);
			idPagingLog.setTotalSize(rows);
			idPagingLog.setVisible(true);
			sorting = 0;
			ascending = true;
		}
		if (rows > 0){
				listBean = serviceLog.getListLog(log, 
						page * SHOW_ROWS_LIST,
						SHOW_ROWS_LIST, sorting, ascending, 
						getformatDate(idInitDate),getformatDate(idFinalDate),
										userBean.getRole().getIdstRol());
				
			if (listBean != null && !listBean.isEmpty()) {
				for (Log beanI : listBean){
					loatItem( beanI);
				}
	
			}
		}
	}

	private String getformatDate (Datebox date){
		if (date!= null  && date.getText() != null && !date.getText().equals("")){
			try {
				return StrUtils.getDateFormat(StrUtils.getDateStandarFromStr(date.getText()));
			} catch (WrongValueException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}
	private void loatItem (Log bean){
		Listitem lIt = new Listitem ();
		Listcell cell2 = new Listcell(bean.getStUserVersion().getInvestName());
		lIt.appendChild(cell2);
		Listcell cell3 = new Listcell(bean.getLogTyperOperation().getDescription());
		lIt.appendChild(cell3);
		Listcell cell4 = new Listcell(StrUtils.getDateFormat(bean.getOperationDate()));
		lIt.appendChild(cell4);
		Listcell cell8 = new Listcell(bean.getStUserVersion().getInvestigator().getInvestName());
		lIt.appendChild(cell8);
		Listcell cell9 = new Listcell(bean.getDescription());
		lIt.appendChild(cell9);
		lIt.setValue(bean);
		idListBLog.appendChild(lIt);
	}
}
