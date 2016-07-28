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

import static com.cimmyt.utils.Constants.ATTRIBUTE_CORNELL_ITEM;
import static com.cimmyt.utils.Constants.LOCALE_LANGUAGE;
import static com.cimmyt.utils.Constants.SHIPMENT_SERVICE_DETAIL_BEAN_NAME;
import static com.cimmyt.utils.Constants.SHIPMENT_SERVICE_SET_BEAN_NAME;

import org.apache.log4j.Logger;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.cimmyt.bean.FileCornellBean;
import com.cimmyt.service.ServiceShipmentDetail;
import com.cimmyt.service.ServiceShipmentSet;
import com.cimmyt.utils.PropertyHelper;

@SuppressWarnings("serial")
public class ControlWindowCornell extends Window{
	
	private Textbox sourceLab;
	private Textbox sampleDNA;
	private Textbox sampleVol;
	private Textbox sampleMass;
	private Textbox preparer;
	private Textbox kingdom;
	private Textbox genus;
	
	private PropertyHelper pro=null;
	private static ServiceShipmentSet serviceShipmentSet;
	private static ServiceShipmentDetail serviceShipmentDetail;
	private FileCornellBean bean;
	
	static {
		if(serviceShipmentSet == null)
        {
			try{
				serviceShipmentSet = (ServiceShipmentSet) SpringUtil.getBean(SHIPMENT_SERVICE_SET_BEAN_NAME);
				}catch (Exception e){
				e.printStackTrace();
			}
        }
	}
	static {
		if(serviceShipmentDetail == null)
        {
			try{
				serviceShipmentDetail = (ServiceShipmentDetail) SpringUtil.getBean(SHIPMENT_SERVICE_DETAIL_BEAN_NAME);
				}catch (Exception e){
				e.printStackTrace();
			}
        }
	}
	Logger logger= Logger.getLogger(ControlWindowCornell.class);
	/** 
	 * Close Window
	 */
	public void closeWindow(){
		getDesktop().setAttribute(ATTRIBUTE_CORNELL_ITEM, null);
		this.onClose();
	}
	/**
	 * Load Window Components
	 */
	public void loadContextAttribute(){
		pro = (PropertyHelper)getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);
		loadComponents();
				bean = (getDesktop().getAttribute(ATTRIBUTE_CORNELL_ITEM) != null ?
						(FileCornellBean) getDesktop().getAttribute(ATTRIBUTE_CORNELL_ITEM) : new FileCornellBean());
		sourceLab.setText(bean.getSourceLab());
		sampleDNA.setText(bean.getSampleDNA());
		sampleVol.setText(bean.getSampleVol());
		sampleMass.setText(bean.getSampleMass());
		preparer.setText(bean.getPreparer());
		kingdom.setText(bean.getKingdom());
		genus.setText(bean.getGenus());
	}



	private void loadComponents(){
		sourceLab = (Textbox)getFellow("sourceLab");
		sampleDNA = (Textbox)getFellow("sampleDNA");
		sampleVol = (Textbox)getFellow("sampleVol");
		sampleMass = (Textbox)getFellow("sampleMass");
		preparer = (Textbox)getFellow("preparer");
		kingdom = (Textbox)getFellow("kingdom");
		genus = (Textbox)getFellow("genus");
	}

	public void nextPag(){
		bean.setSourceLab(sourceLab.getText().trim());
		bean.setSampleDNA(sampleDNA.getText().trim());
		bean.setSampleVol(sampleVol.getText().trim());
		bean.setSampleMass(sampleMass.getText().trim());
		bean.setPreparer(preparer.getText().trim());
		bean.setKingdom(kingdom.getText().trim());
		bean.setGenus(genus.getText().trim());
		getDesktop().setAttribute(ATTRIBUTE_CORNELL_ITEM, bean);
		this.onClose();

	}
}