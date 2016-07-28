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

import com.cimmyt.model.bean.SampleDetResult;


public class SampleDetResultBean {

	   	private Integer idsampledetresult;
	    private String result;
	    private SampleDetailBean studysampleid;
	    private StudyTemplateParamsBean templateparamid;

		public Integer getIdsampledetresult() {
			return idsampledetresult;
		}
		public void setIdsampledetresult(Integer idsampledetresult) {
			this.idsampledetresult = idsampledetresult;
		}
		public String getResult() {
			return result;
		}
		public void setResult(String result) {
			this.result = result;
		}
		public SampleDetailBean getStudysampleid() {
			return studysampleid;
		}
		public void setStudysampleid(SampleDetailBean studysampleid) {
			this.studysampleid = studysampleid;
		}
		public StudyTemplateParamsBean getTemplateparamid() {
			return templateparamid;
		}
		public void setTemplateparamid(StudyTemplateParamsBean templateparamid) {
			this.templateparamid = templateparamid;
		}

	 public SampleDetResultBean(){}

	 public SampleDetResultBean(SampleDetResult bean){
		 this.idsampledetresult = bean.getIdsampledetresult();
		 this.result = bean.getResult();
		 this.studysampleid = new SampleDetailBean(bean.getStudysampleid());
		 this.templateparamid = new  StudyTemplateParamsBean(bean.getTemplateparamid());
	 }

	 public SampleDetResult getSampleDetResult(SampleDetResultBean bean){
		 SampleDetResult obj = new SampleDetResult();
		 obj.setIdsampledetresult(bean.getIdsampledetresult());
		 obj.setResult(bean.getResult());
		 obj.setStudysampleid(bean.getStudysampleid().getSampleDetail(bean.getStudysampleid()));
		 obj.setTemplateparamid(bean.getTemplateparamid().getStudyTemplateParams(
				 bean.getTemplateparamid()));
		 return obj;
	 }
}
