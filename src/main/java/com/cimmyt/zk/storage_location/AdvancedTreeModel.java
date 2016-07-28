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

import org.zkoss.zul.DefaultTreeModel;
import org.zkoss.zul.DefaultTreeNode;

import com.cimmyt.model.bean.StorageLocation;

public class AdvancedTreeModel extends DefaultTreeModel<StorageLocation> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	DefaultTreeNode<StorageLocation> _root;
	
	   public AdvancedTreeModel(StorageLocationTreeNode stotageLocationTreeNode) {
	        super(stotageLocationTreeNode);
	        _root = stotageLocationTreeNode;
	    }

	   public void remove(DefaultTreeNode<StorageLocation> parent, int indexFrom, int indexTo) throws IndexOutOfBoundsException {
	        DefaultTreeNode<StorageLocation> stn = parent;
	        for (int i = indexTo; i >= indexFrom; i--)
	            try {
	                stn.getChildren().remove(i);
	            } catch (Exception exp) {
	                exp.printStackTrace();
	            }
	    }

	   public void remove(DefaultTreeNode<StorageLocation> target) throws IndexOutOfBoundsException {
	        int index = 0;
	        DefaultTreeNode<StorageLocation> parent = null;
	        // find the parent and index of target
	        parent = dfSearchParent(_root, target);
	        for (index = 0; index < parent.getChildCount(); index++) {
	            if (parent.getChildAt(index).equals(target)) {
	                break;
	            }
	        }
	        remove(parent, index, index);
	    }

	   private DefaultTreeNode<StorageLocation> dfSearchParent(DefaultTreeNode<StorageLocation> node, DefaultTreeNode<StorageLocation> target) {
	        if (node.getChildren() != null && node.getChildren().contains(target)) {
	            return node;
	        } else {
	            int size = getChildCount(node);
	            for (int i = 0; i < size; i++) {
	                DefaultTreeNode<StorageLocation> parent = dfSearchParent((DefaultTreeNode<StorageLocation>) getChild(node, i), target);
	                if (parent != null) {
	                    return parent;
	                }
	            }
	        }
	        return null;
	    }
}
