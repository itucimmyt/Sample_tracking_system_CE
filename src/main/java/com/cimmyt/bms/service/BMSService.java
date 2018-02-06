package com.cimmyt.bms.service;

import java.util.List;

import com.cimmyt.bean.UserBean;
import com.cimmyt.bms.client.dto.GermplamsDetail;
import com.cimmyt.bms.client.dto.Germplasm;
import com.cimmyt.model.bean.LocationCatalog;
import com.cimmyt.model.bean.Season;

public interface BMSService {

	public List<Germplasm>  getListGermplasm(String urlOrServiceName, String crop);
	
	public GermplamsDetail getListGermplamsDetail (String urlOrServiceName, String crop, int idList);
	public int saveGermplasm (List<Germplasm> listGermplasmSelect ,UserBean userBean,
			Season season, LocationCatalog location );
}
