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
package com.cimmyt.zk;


import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.ClientInfoEvent;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Center;
import org.zkoss.zul.Window;



	@SuppressWarnings("serial")
	public class ControlSetWidth extends Center {

		private void loadBorderLayout(boolean size, Borderlayout menuPrincipal){
				menuPrincipal.setWidth("100%");
				menuPrincipal.setHeight("100%");
				menuPrincipal.resize();
		}


		public void loadDiv(ClientInfoEvent evt){
			int width = evt.getDesktopWidth();   
			boolean size=false;
			if(width <= 1024){
				size=true;				
			}                     
			Borderlayout menuPrincipal= (Borderlayout)getFellow("intro");
			loadBorderLayout(size, menuPrincipal);
		}
		
		public void loadDivD(ClientInfoEvent evt){
			int width = evt.getDesktopWidth();   
			boolean size=false;
			if(width <= 1024){
				size=true;				
			}                     
			Borderlayout menuPrincipal= (Borderlayout)getFellow("intro");
			loadBorderLayout(size, menuPrincipal);
		}

		public void closeSession () throws InterruptedException{
			
		}

		public void homeSession() throws InterruptedException{
	
		}

		
	}