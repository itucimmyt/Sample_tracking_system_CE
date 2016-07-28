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

import org.zkoss.zul.DefaultTreeNode;

import com.cimmyt.model.bean.StorageLocation;

public class StorageLocationTreeNode extends DefaultTreeNode<StorageLocation>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean open = false;
	
	public StorageLocationTreeNode(StorageLocation data, DefaultTreeNode<StorageLocation> [] children){
	super(data,children);
	}

	public StorageLocationTreeNode(StorageLocation data, DefaultTreeNode<StorageLocation> [] children, boolean open){
	super(data,children);
	setOpen(open);
	}

	public StorageLocationTreeNode (StorageLocation data){
		super (data);
	}

	public boolean isOpen(){
		return open;
	}

	public void setOpen(boolean open){
		this.open = open;
	}
}
