package com.cimmyt.model.bean;

import java.util.Comparator;

public class TemplateComparator  implements Comparator<StudyTemplateParams>{

	@Override
	public int compare(StudyTemplateParams o1, StudyTemplateParams o2) {
		
		return o2.getTemplateparamid().compareTo(o1.getTemplateparamid());
	}

}
