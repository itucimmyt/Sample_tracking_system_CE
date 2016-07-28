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
package org.cimmyt.dnast.servlet;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import org.springframework.web.HttpRequestHandler;

import com.cimmyt.bean.UserBean;
import com.cimmyt.exception.BackException;
import com.cimmyt.model.dao.AuthenticateDAO;

public class AuthenticateServlet implements HttpRequestHandler{
 
	private AuthenticateDAO authDAO;
	
	public AuthenticateDAO getAuthDAO() {
		return authDAO;
	}
	public void setAuthDAO(AuthenticateDAO authDAO) {
		this.authDAO = authDAO;
	}

	@Override
	public void handleRequest(HttpServletRequest req, HttpServletResponse response)
			throws ServletException, IOException {
		UserBean userBean;
		try {
			userBean = authDAO.validateUser(req.getParameter("user"),req.getParameter("password"),Integer.parseInt(req.getParameter("organismid")));
			req.setAttribute("authentication", userBean);
			 OutputStream outstr = response.getOutputStream();
		     ObjectOutputStream oos = new ObjectOutputStream(outstr);
		     oos.writeObject(userBean);
		     oos.flush();
		     oos.close();
		} catch (NumberFormatException | BackException e) {
			// TODO Auto-generated catch block
			//response.setStatus(500);
			response.sendError(500, e.getMessage());
			//response.setStatus(500, e.getMessage());
			//Response.status(500).entity(e.getMessage()).build();
		}
	}  

}