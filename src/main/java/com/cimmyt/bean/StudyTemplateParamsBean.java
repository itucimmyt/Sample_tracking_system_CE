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

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.cimmyt.model.bean.SampleDetResult;
import com.cimmyt.model.bean.StudyTemplateParams;

public class StudyTemplateParamsBean {

		private Integer templateparamid;
	    private String parametername;
	    private String description;
	    private String datatype;
	    private Collection<SampleDetResultBean> imsSampleDetResultCollection;
	    private StudyTemplateBean studytemplateid;

		public Integer getTemplateparamid() {
			return templateparamid;
		}
		public void setTemplateparamid(Integer templateparamid) {
			this.templateparamid = templateparamid;
		}
		public String getParametername() {
			return parametername;
		}
		public void setParametername(String parametername) {
			this.parametername = parametername;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getDatatype() {
			return datatype;
		}
		public void setDatatype(String datatype) {
			this.datatype = datatype;
		}
		public Collection<SampleDetResultBean> getImsSampleDetResultCollection() {
			return imsSampleDetResultCollection;
		}
		public void setImsSampleDetResultCollection(
				Collection<SampleDetResultBean> imsSampleDetResultCollection) {
			this.imsSampleDetResultCollection = imsSampleDetResultCollection;
		}
		public StudyTemplateBean getStudytemplateid() {
			return studytemplateid;
		}
		public void setStudytemplateid(StudyTemplateBean studytemplateid) {
			this.studytemplateid = studytemplateid;
		}

		public StudyTemplateParamsBean(){}
		
		public StudyTemplateParamsBean(StudyTemplateParams bean){
			this.datatype = bean.getDatatype();
			this.description = bean.getDescription();
			
//			List<SampleDetResultBean> list = new ArrayList<SampleDetResultBean>();
//			for (SampleDetResult obj : bean.getImsSampleDetResultCollection()){
//				list.add(new SampleDetResultBean(obj));
//			}
//			this.imsSampleDetResultCollection = list;
			this.parametername = bean.getFactorname();
			this.studytemplateid =new StudyTemplateBean( bean.getStudytemplateid());
			this.templateparamid = bean.getTemplateparamid();
		}
	    
		public StudyTemplateParams getStudyTemplateParams(StudyTemplateParamsBean bean){
			StudyTemplateParams obj = new StudyTemplateParams();
			obj.setDatatype(bean.getDatatype());
			obj.setDescription(bean.getDescription());

			Set<SampleDetResult> list = new HashSet<SampleDetResult>();
			for (SampleDetResultBean obj1 : bean.getImsSampleDetResultCollection()){
				list.add(obj1.getSampleDetResult(obj1));
			}
			obj.setImsSampleDetResultCollection(list);
			obj.setFactorname(bean.getParametername());
			obj.setStudytemplateid(bean.getStudytemplateid().
					getStudyTemplate(bean.getStudytemplateid()));
			obj.setTemplateparamid(bean.getTemplateparamid());
			return obj;
		}
}
