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
package com.cimmyt.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cimmyt.model.bean.SampleDetResult;
import com.cimmyt.model.bean.StudyTemplate;
import com.cimmyt.model.bean.StudyTemplateParams;
import com.cimmyt.model.dao.SampleDetResultDAO;
import com.cimmyt.model.dao.StudyTemplateDAO;
import com.cimmyt.service.SeriviceStudyTemplate;

public class ServiceStudyTemplateImpl implements SeriviceStudyTemplate{

	private StudyTemplateDAO studyTemplateDAO;
	private SampleDetResultDAO sampleDetResultDAO;
	/**
	 * Get Study Template
	 * @param bean
	 * @return
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW, readOnly=false)
	public List<StudyTemplate> getStudyTemplate (StudyTemplate bean){
		List<StudyTemplate> list = studyTemplateDAO.getStudyTemplate(bean);
		if(list !=null && !list.isEmpty()){
			return list;
		}
		return null;
	}
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW, readOnly=false)
	public SampleDetResult getSampleDetResultByGIDAndIDTemplate(Integer sampleId, Integer paramId){
		return sampleDetResultDAO.getSampleDetResultBySampleDetailIdAndTemplateParamId(sampleId, paramId);
	}
	/**
	 * Add new Study template
	 * @param bean
	 */
	@Override
	public void add (StudyTemplate bean,boolean hasStudies){
		studyTemplateDAO.add(bean, hasStudies);
	}
	/**
	 * Delete Study TemplateBean
	 * @param bean
	 */
	@Override
	public void delete (StudyTemplate bean){
		studyTemplateDAO.delete(bean);		
	}
	/**
	 * Set Object bean
	 * @param studyTemplateDAO
	 */
	public void setStudyTemplateDAO(StudyTemplateDAO studyTemplateDAO) {
		this.studyTemplateDAO = studyTemplateDAO;
	}
	@Override
	public StudyTemplate getStudyTemplateByNameregistred(String name) {
		return studyTemplateDAO.getStudyTemplateByNameregistred(name);
	}
	public void setSampleDetResultDAO(SampleDetResultDAO sampleDetResultDAO) {
		this.sampleDetResultDAO = sampleDetResultDAO;
	}
	@Override
	public void deleteStudyTemplateParams(Set<StudyTemplateParams> set, boolean hasStudies) {
		studyTemplateDAO.deleteStudyTemplateParamas(set, hasStudies);
	}
}
