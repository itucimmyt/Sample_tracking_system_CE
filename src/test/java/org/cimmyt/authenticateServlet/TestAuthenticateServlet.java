package org.cimmyt.authenticateServlet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Assert;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zkoss.zk.ui.Executions;

import com.cimmyt.bean.UserBean;
import com.cimmyt.exception.BackException;
import com.cimmyt.model.dao.AuthenticateDAO;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/context.xml","/dataAccessTest.xml"})
public class TestAuthenticateServlet {

	
	@Autowired
	private AuthenticateDAO authDAO;
	
	@Test
	public void validateAuthentication() throws BackException{
		UserBean bean = authDAO.validateUser("Administrator", "Admin2012", 1);
		System.out.println(bean.getOrganism().getOrganismname());
		System.out.println("validate " +bean);
		/*System.out.println("User Funtions :: "+ bean.getUserFuntionses());
		for (UserFuntions funtion : bean.getUserFuntionses()){
			System.out.println("Funtion "+ funtion.getStFuntions().getFuntionKey());
		}
		*/
		
		Assert.assertTrue(true);
	}
	
	public void validateAuth(){
		try {
		UserBean userBean = null;
		HttpClient httpclient = new DefaultHttpClient();
		URIBuilder builder = new URIBuilder();
			builder
			.setScheme(Executions.getCurrent().getScheme())
			.setHost(Executions.getCurrent().getLocalName())
			.setPort(Executions.getCurrent().getLocalPort())
			.setPath(Executions.getCurrent().getContextPath()+"/login")
			.setParameter("user", "user")
			.setParameter("password", "pass");
			System.out.println("Builder :: "+builder.toString());
			URI uri = builder.build();
			HttpPost post = new HttpPost(uri);
			HttpResponse response = httpclient.execute(post);
			System.out.println(response.getEntity().getContent());
			ObjectInputStream ois = new ObjectInputStream( response.getEntity().getContent() );
			userBean = (UserBean)ois.readObject();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (Exception exg){
			exg.printStackTrace();
		}

		
	}
}
