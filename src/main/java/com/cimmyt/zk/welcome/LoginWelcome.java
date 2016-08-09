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
package com.cimmyt.zk.welcome;

import static com.cimmyt.utils.Constants.ATTRIBUTE_MAIZE;
import static com.cimmyt.utils.Constants.ATTRIBUTE_NAME_USER_BEAN;
import static com.cimmyt.utils.Constants.ATTRIBUTE_PARAM_MAP_FUNTION;
import static com.cimmyt.utils.Constants.ATTRIBUTE_WHEAT;
import static com.cimmyt.utils.Constants.LBL_SELECT_CULTIVO;
import static com.cimmyt.utils.Constants.LBL_SELECT_lANGUAGE;
import static com.cimmyt.utils.Constants.LBL_SELECT_lAN_ENG;
import static com.cimmyt.utils.Constants.LBL_SELECT_lAN_SPA;
import static com.cimmyt.utils.Constants.LBL_SEL_iTE_CORN;
import static com.cimmyt.utils.Constants.LBL_SEL_iTE_WHEAT;
import static com.cimmyt.utils.Constants.LBL_WELCOME_CIU;
import static com.cimmyt.utils.Constants.LBL_WELCOME_FORGOT_PASS;
import static com.cimmyt.utils.Constants.LBL_WELCOME_HEADER;
import static com.cimmyt.utils.Constants.LBL_WELCOME_MES_ERROR;
import static com.cimmyt.utils.Constants.LBL_WELCOME_PASS;
import static com.cimmyt.utils.Constants.LBL_WELCOME_PASS_INCORRECT;
import static com.cimmyt.utils.Constants.LBL_WELCOME_REGISTER;
import static com.cimmyt.utils.Constants.LBL_WELCOME_USER;
import static com.cimmyt.utils.Constants.LOCALE_LANGUAGE;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.A;
import org.zkoss.zul.Label;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.cimmyt.bean.UserBean;
import com.cimmyt.service.ServiceLogin;
import com.cimmyt.utils.Constants;
import com.cimmyt.utils.PropertyHelper;
import com.cimmyt.utils.PropertyHelper.Languages;
import com.cimmyt.utils.StrUtils;

@SuppressWarnings("serial")
public class LoginWelcome extends Window{

	
	private Label idLblSelLan;
	private Label idLNUCI;
	private Label idLabSelCul;
	private Label idUser;
	private Label idHeader;
	private Label idPass;
	private Radio idREng;
	private Radio idRSpa;
	private Radio idRCorn;
	private Radio idRWheat;
	private Radiogroup idRG;
	private Radiogroup idRGCorn;
	private A idReg;
	private A idReset;
	PropertyHelper prop = new PropertyHelper();
	private static ServiceLogin serviceLogin;
	static {
		if (serviceLogin == null){
			try{
				serviceLogin = (ServiceLogin)SpringUtil.getBean(Constants.SERVICE_LOGIN);
			}catch (Exception ex){
				ex.printStackTrace();
			}
		}
	}
	/**
	 * Select option of radio button
	 */
	
	public void onChangeRadio(){
		loadContext();
		int value = Integer.parseInt(idRG.getSelectedItem().getValue().toString());
		switch (value){
			case 0:{
				prop.setMessagesBundle(Languages.english);
				break;
			}
			case 1:{
				prop.setMessagesBundle(Languages.spanish);
				break;
			}
			default :{
				prop.setMessagesBundle(Languages.english);
			}
		}
		changeLabel();
		getDesktop().getSession().setAttribute(LOCALE_LANGUAGE, prop);
	}
	private void changeLabel(){
		idLblSelLan.setValue(prop.getKey(LBL_SELECT_lANGUAGE));
		idLNUCI.setValue(prop.getKey(LBL_WELCOME_CIU));
		idLabSelCul.setValue(prop.getKey(LBL_SELECT_CULTIVO));
		idUser.setValue(prop.getKey(LBL_WELCOME_USER));
		idPass.setValue(prop.getKey(LBL_WELCOME_PASS));
		idREng.setLabel(prop.getKey(LBL_SELECT_lAN_ENG));
		idRSpa.setLabel(prop.getKey(LBL_SELECT_lAN_SPA));
		idRCorn.setLabel(prop.getKey(LBL_SEL_iTE_CORN));
		idRWheat.setLabel(prop.getKey(LBL_SEL_iTE_WHEAT));
		idReg.setLabel(prop.getKey(LBL_WELCOME_REGISTER));
		idReset.setLabel(prop.getKey(LBL_WELCOME_FORGOT_PASS));
		idHeader.setValue(prop.getKey(LBL_WELCOME_HEADER));
	}
	private void loadContext(){
/*		idLblSelLan = (Label)getFellow("idLblSelLan");
		idLNUCI = (Label)getFellow("idLNUCI");
		idLabSelCul = (Label)getFellow("idLabSelCul");
*/
		idUser = (Label)getFellow("idUser");
		idPass = (Label)getFellow("idPass");
/*		idREng = (Radio)getFellow("idREng");
		idRSpa = (Radio)getFellow("idRSpa");
*/		idRWheat = (Radio)getFellow("idRWheat");
		idRWheat.setValue(String.valueOf(ATTRIBUTE_WHEAT));
/*		idRG = (Radiogroup)getFellow("idRG");
*/		idRCorn = (Radio)getFellow("idRCorn");
		idRCorn.setValue(String.valueOf(ATTRIBUTE_MAIZE));
		idRGCorn = (Radiogroup)getFellow("idRGCorn");
		/*
		idReg = (A)getFellow("idReg");
		idReset = (A)getFellow("idReset");
		idHeader = (Label)getFellow("idHeader");
		*/
	}
	/**
	 * validate user and password
	 */
	public void validateUserPass(){
		try{
			String user = ((Textbox)getFellow("idTUser")).getText().trim();
			String password = ((Textbox)getFellow("idTPas")).getText().trim();
			UserBean userBean;
			
			loadContext();
			PropertyHelper pro = (PropertyHelper)getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);
			if(user.equals("") || password.equals("")){
				StrUtils.messageBox(pro.getKey(LBL_WELCOME_MES_ERROR), pro);
			}else if (( userBean = serviceLogin.validateUserBySystem(user, password, Integer.valueOf(idRGCorn.getSelectedItem().getValue().toString()),pro)) == null){
			//else if (( userBean = validateUserBD(user, password, (String)idRGCorn.getSelectedItem().getValue().toString())) == null){
				StrUtils.messageBox(pro.getKey(LBL_WELCOME_PASS_INCORRECT),pro);
			}else {
				
				userBean.setTypeCorp(Integer.parseInt(idRGCorn.getSelectedItem().getValue().toString()));
				userBean.setCorp(getCorp(userBean.getTypeCorp()));
				getDesktop().getSession().setAttribute(ATTRIBUTE_NAME_USER_BEAN, userBean);
				loadMapFuntions(userBean);
				Executions.sendRedirect("/");
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	private void loadMapFuntions (UserBean userBean){
		
		Map <String, String> mapFuntions = new HashMap<String, String>();
		if (userBean != null && userBean.getUserFuntionses()!= null && userBean.getUserFuntionses().size() > 0){
			for (String funtion : userBean.getUserFuntionses()){
				mapFuntions.put(funtion,funtion );
			}
			getDesktop().getSession().setAttribute(ATTRIBUTE_PARAM_MAP_FUNTION, mapFuntions);
		}
		
	}
	private String getCorp(int typeCorp){
		switch (typeCorp){
			case ATTRIBUTE_MAIZE:
				return prop.getKey(LBL_SEL_iTE_CORN);	
			case ATTRIBUTE_WHEAT:
				return prop.getKey(LBL_SEL_iTE_WHEAT);
			default :
				return prop.getKey(LBL_SEL_iTE_WHEAT);
		}
	}

	/**
	 * Devuelve un bean de usuario si la autentificacion es correcta,
	 * de lo contrario devuelve null.
	 * <br><b>Nota: Reemplazar con llamada a servicio de autentificacion</b>
	 * */
	private UserBean validateUserBD(String user, String pass, String idCrop){

		UserBean userBean = null;
		HttpClient httpclient = new DefaultHttpClient();
		URIBuilder builder = new URIBuilder();
			builder
			.setScheme(Executions.getCurrent().getScheme())
			.setHost(Executions.getCurrent().getLocalName())
			.setPort(Executions.getCurrent().getLocalPort())
			.setPath(Executions.getCurrent().getContextPath()+"/login")
			.setParameter("user", user)
			.setParameter("password", pass)
			.setParameter("organismid", idCrop);
			
			System.out.println("Builder :: "+builder.toString());
		
		try {
			URI uri = builder.build();
			HttpPost post = new HttpPost(uri);
			HttpResponse response = httpclient.execute(post);
			System.out.println("Status code :: "+response.getStatusLine().getStatusCode());
			
			response.getEntity();
			System.out.println(response.getEntity().getContent());
			InputStream input = response.getEntity().getContent();
			if (response.getStatusLine().getStatusCode() == 200){
			
			//UserBean userS = (UserBean)response.getEntity().getContent();
			//System.out.println("User :: "+ userS);
			ObjectInputStream ois = new ObjectInputStream(input  );
	        
			userBean = (UserBean)ois.readObject();
			}else if (response.getStatusLine().getStatusCode() == 500){
				
				System.out.println(input);
				System.out.println(getStringFromInputStream(input));
				response.getEntity();
				
			}
			
			
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

		return userBean;
	}

	private static String getStringFromInputStream(InputStream is) {

		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try {

			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}

	public void sendRegister(){
		final Window win = (Window) Executions.createComponents(
    			"/register/register_user.zul", null, null);
    		win.doModal();
	}
	
	public void sendReset(){
		final Window win = (Window) Executions.createComponents(
    			"/register/reset_password.zul", null, null);
    		win.doModal();
	}
	
	public void init(){
		this.setTitle("Login - DNA sample tracking");
	}
	
}
