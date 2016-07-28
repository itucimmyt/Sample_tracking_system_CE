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
//Created October 2011
package com.cimmyt.model.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.cimmyt.model.bean.Project;
import com.cimmyt.model.dao.ProjectDAO;
/**
 * 
 */
public class ProjectDAOImpl  extends AbstractDAO<Project, Integer> implements ProjectDAO  {

	public ProjectDAOImpl() {
		
		super(Project.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void buildCriteria(DetachedCriteria criteria, Project filter) {
		if (filter.getProjectid() != null) {
			criteria.add(Restrictions.eq("projectid", filter.getProjectid()));
		}
		if (filter.getProjectname()!= null && ! filter.getProjectname().isEmpty()) {
			criteria.add(Restrictions.like("projectname", "%"+filter.getProjectname()+"%"));
		}
		if (filter.getProjectdescription()!= null && ! filter.getProjectdescription().isEmpty()) {
			criteria.add(Restrictions.like("projectdescription", "%"+filter.getProjectdescription()+"%"));
		}
		if (filter.getPurposename()!= null && ! filter.getPurposename().isEmpty()) {
			criteria.add(Restrictions.like("purposename", "%"+filter.getPurposename()+"%"));
		}
		if (filter.getPurposedescription()!= null && ! filter.getPurposedescription().isEmpty()) {
			criteria.add(Restrictions.like("purposedescription", "%"+filter.getPurposedescription()+"%"));
		}
	}
	
	public Integer getLastSampleID(final Project project){
		Project myproject;
		final String queryString = "from Project as pry "
				+ " where pry.projectid =:PROJECTID ";
		 myproject = (Project) getHibernateTemplate().execute(
				new HibernateCallback() {

					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Project pry=new Project();
						pry.setProjectid(project.getProjectid());
												
						Query query = session.createQuery(queryString);
						query.setParameter("PROJECTID", project.getProjectid());
						if (query.uniqueResult() != null) {
							pry = (Project) query
									.uniqueResult();
						}
						return pry;
					}
				});
		if (myproject.getLastsampleid()==null)
			return 0;
		else
			return myproject.getLastsampleid();
}
	
	public Project getProjectWithName(final String name) {
		Project project = null;
		List<Project> listaproj=getAllProjects();
		String nameprojcomplete=null;
		
		for (Project eachpro:listaproj){
			nameprojcomplete=eachpro.getProjectname()+eachpro.getPurposename();
			if (nameprojcomplete.equals(name))
				project=eachpro;
		}
		return project;
	}
	
	
	@SuppressWarnings("unchecked")
	private List<Project> getAllProjects(){
		List<Project> projects = null;
		final String queryString = "from Project ";
				
		projects = (List<Project>) getHibernateTemplate().execute(
				new HibernateCallback() {
					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createQuery(queryString);						
						List<Project> projs;
						projs=query.list();
						return projs;
					}
				});
		return projects;
	}
	
	public List<Project> getProjects(Project filter) {
		return super.getListByFilter(filter);
	}

	@Override
	public void saveOrUpdateProject(Project project) {
		super.update(project);
	}	

	public void deleteProject (Project project){
		super.delete(project);
	}

	@Override
	protected void buildCriteria(DetachedCriteria criteria, Project filter,
			Integer id) {
		// TODO Auto-generated method stub
		
	}
	
}