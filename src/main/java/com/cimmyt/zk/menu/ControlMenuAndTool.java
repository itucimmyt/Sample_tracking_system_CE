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
package com.cimmyt.zk.menu;

import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_INFORMATION;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_LOGIN;
import static com.cimmyt.utils.Constants.LBL_MENU_CONTRUCTION_SERVICE;
import static com.cimmyt.utils.Constants.LBL_WELCOME_NA_AP;
import static com.cimmyt.utils.Constants.LOCALE_LANGUAGE;
import static com.cimmyt.utils.Constants.SELECT_OPTION_MENU;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Center;
import org.zkoss.zul.Image;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;

import com.cimmyt.utils.Constants;
import com.cimmyt.utils.PropertyHelper;
public class ControlMenuAndTool {
	
	public static enum ListMenu{StudyTemplate, StoreLocation,Studies,Companies,
		ShipmentManagment, Proyects, Investigators, Tissues,Location,Seasons, Queries,History
		,Home, Exit, BMS, LOG}
	@SuppressWarnings("unused")
	private enum listToots {Add, Edit, Delete,Storesamples, Report,ViewResults,Update,
		Barcode,Barcode2d, Send,Goback,Received,DefQuery,ExportsXLs, ExportCSV}
	
	private static Label idSelectedAction;
	
	public static void selectMenu2(ListMenu option, Center border, Image fisheye) throws InterruptedException{
		 try
	        {
			 PropertyHelper pro=(PropertyHelper) border.getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);

			 if (option != ListMenu.Queries)
			 if(Executions.getCurrent().getSession().getAttribute(Constants.ATTRIBUTE_NAME_USER_BEAN) == null){
				 Messagebox.show(pro.getKey(LBL_GENERIC_MESS_LOGIN),pro.getKey(LBL_GENERIC_MESS_INFORMATION), 
							Messagebox.OK, Messagebox.INFORMATION);
				 return;
			 }

			 idSelectedAction = (Label)border.getParent().getFellow("idSelectedAction");
			  switch( option )
	            {
	                case StudyTemplate:
	                	includeOption("/study_template/study_template.zul",border );
	                	break;
	                case StoreLocation:
	                	includeOption("/storage_location/storage_location.zul",border );
	                	break;
	                case Studies:
	                	includeOption("/studies/studies.zul",border );
	                	break;
	                case Companies:
	                	includeOption("/companies/companies.zul",border );
	                	break;
	                case ShipmentManagment:
	                	includeOption("/shipment_management/shipments.zul",border );
	                	break;
					case Proyects:
						includeOption("/projects/projects.zul",border );
						break;
					case Investigators:
						includeOption("/investigators/investigators.zul",border );
						break;
					case Tissues:
						includeOption("/tissues/tissue.zul",border );
						break;
					case Location:
						includeOption("/location/location.zul",border );
						break;
					case Seasons:
						includeOption("/season/season.zul",border );
						break;
					case Queries:
						includeOption("/queries/queries.zul",border );
						break;
					case History:
						//messServiceConstruction(pro);
						break;
					case BMS:
						includeOption("/bms/bms_service.zul",border );
						break;
					case LOG:
						includeOption("/log/log.zul",border );
						break;
	                default:
	            }
			  	border.getDesktop().setAttribute(SELECT_OPTION_MENU, option); 
				idSelectedAction.setValue(pro.getKey(LBL_WELCOME_NA_AP)+" - "+fisheye.getTooltiptext());
				
	        }
	        catch( Exception e )
	        {
	          
	        }
	}


	private static void messServiceConstruction(PropertyHelper pro){
		Messagebox.show(pro.getKey(LBL_MENU_CONTRUCTION_SERVICE),pro.getKey(LBL_GENERIC_MESS_INFORMATION), 
				Messagebox.OK, Messagebox.INFORMATION);
	}
	private static void includeOption(String url, Center center){
		if (center != null && center.getChildren() != null
				&& !center.getChildren().isEmpty()) {
			while (!center.getChildren().isEmpty()) {
				center.getChildren().remove(0);
			}
		}
		
		if (center != null){
			Include include = new Include ();
			include.setSrc(url);
			center.appendChild(include);
			center.getParent().getFellow("idHome").setVisible(true);
		}
			
	}
}
