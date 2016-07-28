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
package com.cimmyt.zk.storage_location;



import static com.cimmyt.utils.Constants.SAMPLE_DETAIL_SERVICE_BEAN_NAME;
import static com.cimmyt.utils.Constants.SHOW_ROWS_LIST;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;
import org.zkoss.zul.event.PagingEvent;
import org.zkoss.zul.event.ZulEvents;

import com.cimmyt.model.bean.LabStudy;
import com.cimmyt.model.bean.SampleDetail;
import com.cimmyt.model.bean.StorageLocation;
import com.cimmyt.service.ServiceSampleDetail;

@SuppressWarnings("serial")
public class TreeStorageLocation extends SelectorComposer<Component> {

	private static ServiceSampleDetail serviceSampleDetail = null;
	private Paging idPaging;
	Listbox idListStudy;
	private Integer idCurrentStorage;
	private boolean ascending = true;
	private String lastColumnSorted = null;

	static{
		if (serviceSampleDetail == null){
			try{
				serviceSampleDetail = (ServiceSampleDetail)SpringUtil.getBean(SAMPLE_DETAIL_SERVICE_BEAN_NAME);
			}catch (Exception eC){
				eC.printStackTrace();
			}
		}

	}
	  @Wire
	  private Tree tree;
	  private AdvancedTreeModel contactTreeModel;
	  
	  public void doAfterCompose(Component comp) throws Exception {
	        super.doAfterCompose(comp);    
	        contactTreeModel = new AdvancedTreeModel(new StorageLocationList().getRoot());
	        tree.setItemRenderer(new ContactTreeRenderer());
	        tree.setModel(contactTreeModel);
	    }
	  
	  private final class ContactTreeRenderer implements TreeitemRenderer<StorageLocationTreeNode> {
	        @Override
	        public void render(final Treeitem treeItem, final StorageLocationTreeNode treeNode, int index) throws Exception {
	        	StorageLocationTreeNode ctn = treeNode;
	        	StorageLocation stprage = (StorageLocation) ctn.getData();
	            Treerow dataRow = new Treerow();
	            dataRow.setParent(treeItem);
	            treeItem.setValue(ctn);
	            treeItem.setOpen(ctn.isOpen());
	            
	            Treecell cell = new  Treecell(stprage.getLname());
	            dataRow.appendChild(cell);

				Long counter = serviceSampleDetail.getCountSamplesByLocation(stprage.getImslocid());
				if(counter != null && counter > 0){
	            	treeItem.setImage("/images/documents.png");
		            dataRow.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
		                @Override
		                public void onEvent(Event event) throws Exception {

		                	if(idListStudy == null)
		                		idListStudy =(Listbox)treeItem.getParent().getParent().getFellow("idListStudy");
		                	
		                	idCurrentStorage = ((StorageLocation) treeNode.getData()).getImslocid();
		                	lastColumnSorted = null;
		                	ascending = true;
		                	loadItemLisBox(idListStudy, serviceSampleDetail.getSamplesByLocation(idCurrentStorage,0,SHOW_ROWS_LIST,null, true));
		                	getPagedData();
		                	
		                }
		            });
	            }else{
	            	treeItem.setImage("/images/Ico_Folder_Node.png");
		            dataRow.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

		                public void onEvent(Event event) throws Exception {
		                	if(idListStudy == null)
		                		idListStudy =(Listbox)treeItem.getParent().getParent().getFellow("idListStudy");
		                	getPagedData();
		                	clearList(idListStudy);

		    	    		idPaging.setActivePage(0);
		    	    		idPaging.setTotalSize(0);
		                }

		            });
	            } 
	        }
	  
	        private void getPagedData(){
	    		if(idPaging == null){
	    			idPaging = (Paging)tree.getParent().getFellow("idPaging");
	    			idPaging.addEventListener(ZulEvents.ON_PAGING, new EventListener<PagingEvent>() {
	    				@Override
	    				public void onEvent(PagingEvent event) throws Exception {

	    					loadItemLisBox(idListStudy, serviceSampleDetail.getSamplesByLocation(idCurrentStorage,event.getActivePage()*SHOW_ROWS_LIST,SHOW_ROWS_LIST
	    							,lastColumnSorted, ascending));
	    				}
	    			});

	    		}

	    		Long rows = serviceSampleDetail.getCountSamplesByLocation(idCurrentStorage);
	    		idPaging.setPageSize(SHOW_ROWS_LIST);
	    		idPaging.setActivePage(0);
	    		idPaging.setTotalSize(rows.intValue());

	        }
	        
	        
	    }

      private void loadItemLisBox(Listbox obj , List<SampleDetail> samples){
		clearList(idListStudy);
      	if (samples != null && !samples.isEmpty()){
      		for (SampleDetail sampleD : samples){
      			Listitem lIt = new Listitem ();
      			Listcell cell2 = new Listcell(sampleD.getLabstudyid().getTitle());
      			lIt.appendChild(cell2);
      			Listcell cell3 = new Listcell(sampleD.getPlatename());
      			lIt.appendChild(cell3);
      			Listcell cell4 = new Listcell(sampleD.getPlateloc());
      			lIt.appendChild(cell4);
      			if (sampleD.getSamplegid() != null){
      			Listcell cell5 = new Listcell(sampleD.getLabstudyid().getProject().getProjectname()+
      					sampleD.getLabstudyid().getProject().getPurposename() +
      					String.valueOf(sampleD.getSamplegid()));
      			lIt.appendChild(cell5);

      			}
      			obj.appendChild(lIt);
      		}
      	}
      }
 	 
    private void clearList(Listbox list){
  		if (list !=null && !list.getItems().isEmpty()) {
  			while (list.getItems().size()>=1) {
  				list.getChildren().remove(1);
  			}
  		}
  	}

    @Listen("onSort = south listheader")
  	public void onSorting(Event event){
		Listheader header = (Listheader)event.getTarget();
		if(header.getValue().equals(lastColumnSorted)){
			ascending = !ascending;
		}else{
			ascending = true;
		}
		lastColumnSorted = header.getValue();
		
		loadItemLisBox(idListStudy, 
				serviceSampleDetail.getSamplesByLocation(idCurrentStorage,idPaging.getActivePage()*SHOW_ROWS_LIST, SHOW_ROWS_LIST
														,lastColumnSorted, ascending));
		
	}
	
}

