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
package com.cimmyt.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import com.cimmyt.model.bean.LabStudy;
import com.cimmyt.model.bean.StudyTemplate;
import com.cimmyt.model.bean.StudyTemplateParams;

public class StudyTemplateBean {

	 	private Integer studytemplateid;
	    private String templatename;
	    private String comments;    
	    private Collection<StudyTemplateParamsBean> imsStudyTemplateParamsCollectionBean;
	    private Collection<LabStudyBean> imsLabStudyCollectionBean;

		public Integer getStudytemplateid() {
			return studytemplateid;
		}
		public void setStudytemplateid(Integer studytemplateid) {
			this.studytemplateid = studytemplateid;
		}
		public String getTemplatename() {
			return templatename;
		}
		public void setTemplatename(String templatename) {
			this.templatename = templatename;
		}
		public String getComments() {
			return comments;
		}
		public void setComments(String comments) {
			this.comments = comments;
		}
		public Collection<StudyTemplateParamsBean> getImsStudyTemplateParamsCollectionBean() {
			return imsStudyTemplateParamsCollectionBean;
		}
		public void setImsStudyTemplateParamsCollectionBean(
				Collection<StudyTemplateParamsBean> imsStudyTemplateParamsCollectionBean) {
			this.imsStudyTemplateParamsCollectionBean = imsStudyTemplateParamsCollectionBean;
		}
		public Collection<LabStudyBean> getImsLabStudyCollectionBean() {
			return imsLabStudyCollectionBean;
		}
		public void setImsLabStudyCollectionBean(
				Collection<LabStudyBean> imsLabStudyCollectionBean) {
			this.imsLabStudyCollectionBean = imsLabStudyCollectionBean;
		}
	    
	 public StudyTemplateBean(){}
	 
	 public StudyTemplateBean(StudyTemplate bean){
		 this.comments = bean.getComments();
//		 if(bean != null && 
//				 bean.getImsLabStudyCollection() != null && 
//				 !bean.getImsLabStudyCollection().isEmpty()){
//			 List<LabStudyBean> list1 = new ArrayList<LabStudyBean>();
//			 for (LabStudy obj1 : bean.getImsLabStudyCollection()){
//				 list1.add(new LabStudyBean(obj1));
//			 }
//			 this.imsLabStudyCollectionBean =list1;
//		 }
		 if(bean!= null && 
				 bean.getImsStudyTemplateParamsCollection() !=null && 
				 !bean.getImsStudyTemplateParamsCollection().isEmpty()){
			 List<StudyTemplateParamsBean> list2 = new ArrayList<StudyTemplateParamsBean>();
			 for (StudyTemplateParams obj2 : bean.getImsStudyTemplateParamsCollection()){
				 obj2.setStudytemplateid(bean);
				 list2.add(new StudyTemplateParamsBean(obj2));
			 }
			 this.imsStudyTemplateParamsCollectionBean = list2;
		 }
		
		 this.studytemplateid = bean.getStudytemplateid();
		 this.templatename = bean.getTemplatename();
	 }

	 public StudyTemplate getStudyTemplate(StudyTemplateBean bean){
		 StudyTemplate obj = new StudyTemplate();
		 obj.setComments(bean.getComments());
		 if(bean!= null && 
				 bean.getImsLabStudyCollectionBean() != null
				 && !bean.getImsLabStudyCollectionBean().isEmpty()){
			 Set<LabStudy> list1 = new HashSet<LabStudy>();
			 for (LabStudyBean obj1 : bean.getImsLabStudyCollectionBean()){
				 list1.add(obj1.getLabStudy(obj1));
			 }
			 obj.setImsLabStudyCollection(list1);
		 }
		 if(bean!= null && 
				 bean.getImsStudyTemplateParamsCollectionBean() != null &&
				 !bean.getImsStudyTemplateParamsCollectionBean().isEmpty()){
			 SortedSet<StudyTemplateParams> list2 = new TreeSet<StudyTemplateParams>();
			 for (StudyTemplateParamsBean obj2 : bean.getImsStudyTemplateParamsCollectionBean()){
				 list2.add(obj2.getStudyTemplateParams(obj2));
			 }
			 obj.setImsStudyTemplateParamsCollection(list2);
		 }
		 obj.setStudytemplateid(bean.getStudytemplateid());
		 obj.setTemplatename(bean.getTemplatename());
		 return obj;
	 }
}
