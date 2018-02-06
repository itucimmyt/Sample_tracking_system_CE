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
package com.cimmyt.zk.register;

import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_FIELD_REQUIRED;
import static com.cimmyt.utils.Constants.LBL_GENERIC_MESS_INFORMATION;
import static com.cimmyt.utils.Constants.LOCALE_LANGUAGE;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.cimmyt.model.bean.Register;
import com.cimmyt.rest.client.Result;
import com.cimmyt.rest.context.SpringApplicationContext;
import com.cimmyt.service.ServiceRegister;
import com.cimmyt.utils.Constants;
import com.cimmyt.utils.ConstantsDNA;
import com.cimmyt.utils.PropertyHelper;
import com.cimmyt.utils.StrUtils;
 
@SuppressWarnings("serial")
public class ControlRegister extends Window{
	
	private static ServiceRegister serviceRegister;
	private PropertyHelper pro=null;
	private Logger logger= Logger.getLogger(ControlRegister.class);
	
	
	private Textbox idTextEmail;
	private Textbox idTextCEmail;
	private Textbox idTextRegisterName;
	private Textbox idTxtSecondName;
	private Textbox idTxtInmediateBoss;
	private Textbox idTxtCity;
	private Combobox idCombContry;
	private Textbox idTxtIndutria;
	private Textbox idTxtProviderName;
	private Textbox idTxtPhone;
	private Checkbox idChckTerms;

	private Label lblEMail;
	private Label lblConfEmail;
	private Label lblFisrtName;
	private Label lblSecondName;
	private Label lblcontry;
	private Label lblindustry;
	private Label lblProvider;
	private Radiogroup idRGCornR;

	private Label idLblErrorEmail;
	private Label idLblErrorConEmail;
	private Window idWindow;
	static {
		try{
		if (serviceRegister == null){
			serviceRegister = (ServiceRegister)SpringApplicationContext.getBean(Constants.REGISTER_SERVICE_BEAN);
		}
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}

	public void doAfterCompose(){
		pro = (PropertyHelper)getDesktop().getSession().getAttribute(LOCALE_LANGUAGE);
		loadComponents();
		notVisible(false);
		fillComboBox();
		
	}

	private void fillComboBox(){
		List<Result> listResult = serviceRegister.getContries();
		if (listResult != null && !listResult.isEmpty()){
			for (Result result : listResult){
				Comboitem item = new Comboitem(result.getName());
				idCombContry.appendChild(item);
			}
		}else{
			Comboitem item = new Comboitem("EUA");
			idCombContry.appendChild(item);
			Comboitem itemMex = new Comboitem("Mexico");
			idCombContry.appendChild(itemMex);
		}
	}
	private void loadComponents(){
		idWindow = (Window)getFellow("idWindowRegister");
		idTextEmail = (Textbox)getFellow("idTextEmail");
		idTextCEmail = (Textbox)getFellow("idTextCEmail");
		idTextRegisterName = (Textbox)getFellow("idTextRegisterName");
		idTxtSecondName = (Textbox)getFellow("idTxtSecondName");
		idTxtInmediateBoss = (Textbox)getFellow("idTxtInmediateBoss");
		idTxtCity = (Textbox)getFellow("idTxtCity");
		idTxtIndutria = (Textbox)getFellow("idTxtIndutria");
		idTxtProviderName = (Textbox)getFellow("idTxtProviderName");
		idTxtPhone = (Textbox)getFellow("idTxtPhone");
		idCombContry = (Combobox)getFellow("idCombContry");
		idChckTerms = (Checkbox)getFellow("idChckTerms");
		lblEMail = (Label)getFellow("lblEMail");
		lblConfEmail = (Label)getFellow("lblConfEmail");
		lblFisrtName = (Label)getFellow("lblFisrtName");
		lblSecondName = (Label)getFellow("lblSecondName");
		lblcontry = (Label)getFellow("lblcontry");
		lblindustry = (Label)getFellow("lblindustry");
		lblProvider = (Label)getFellow("lblProvider");
		idLblErrorEmail = (Label)getFellow("idLblErrorEmail");
		idLblErrorConEmail = (Label)getFellow("idLblErrorConEmail");
		idRGCornR = (Radiogroup)getFellow("idRGCornR");
		
		
	}

	public void add() throws ParseException{
		
		notVisible(false);
		cleanAndHide();
		boolean allValids = validateAllRequieremetData();
		if (!allValids){
			messageBox(pro.getKey(LBL_GENERIC_MESS_FIELD_REQUIRED));
			return ;
		}
		if (!validateSizeFiels()){
			messageBox(pro.getKey(Constants.LBL_GENERIC_WINDOW_ERROR_FILL_FILDS));
			return;
		}
		if (!validatePasswordAndEmailEquals()){
			messageBox(pro.getKey(Constants.LBL_GENERIC_WINDOW_ERROR_FILL_FILDS));
		return;
		}
		if (!validateformatEmail()){
			messageBox(pro.getKey(Constants.LBL_GENERIC_WINDOW_ERROR_FILL_FILDS));
			return;	
		}
		if (serviceRegister.validateEmailAndUser(getRegisterFromView())){
			messageBox(pro.getKey(ConstantsDNA.LBL_REGISTER_ERROR_USER_REPIT));
			return;
		}
		if (!idChckTerms.isChecked()){
			messageBox(pro.getKey(ConstantsDNA.LBL_REGISTER_ERROR_AGREEMENT));
			return;
		}
			saveRegister();
			idWindow.onClose();
	}

	private void cleanAndHide (){
		cleanLabel(idLblErrorEmail);
		cleanLabel(idLblErrorConEmail);
	}
	private void cleanLabel(Label label){
		label.setValue("");
		label.setVisible(true);
	}
	
	private boolean validateformatEmail (){
		boolean emailsAreCorrect = true;
		if (!validateEmail(idTextEmail.getValue(), idLblErrorEmail))
			emailsAreCorrect = false;
		if (!validateEmail(idTextCEmail.getValue(), idLblErrorConEmail))
			emailsAreCorrect = false;
		return emailsAreCorrect;
	}

	private boolean validateEmail(String cadena, Label label){
		if (!StrUtils.isValidEmail(cadena)){
			if (label.getValue().length() > 0 )
				label.setValue(", "+ pro.getKey(ConstantsDNA.LBL_REGISTER_ERROR_EMAIL_INVALID));
			else
				label.setValue( pro.getKey(ConstantsDNA.LBL_REGISTER_ERROR_EMAIL_INVALID));
			label.setVisible(true);
			return false;
		}
		return true;
	}
	private boolean validatePasswordAndEmailEquals(){
		boolean areEquals = true;
		if (!printComponentAreNotEqual(idTextEmail, idTextCEmail, idLblErrorConEmail, pro.getKey(ConstantsDNA.LBL_REGISTER_ERROR_EMAIL_NOT_EQUAL)))
			areEquals = false;
		return areEquals;
	}

	private boolean printComponentAreNotEqual(Textbox text1, Textbox text2, Label label, String cadena){
		if (!text1.getValue().equals(text2.getValue())){
			if (label.getValue().length() > 0)
				label.setValue(", "+ cadena);
			else
				label.setValue( cadena);
			label.setVisible(true);
			return false;
		}
		return true;
	}
	private boolean validateSizeFiels(){
		boolean allhigert = true;

		if (!printComponentError(idTextEmail, idLblErrorEmail))
			allhigert = false;
		if (!printComponentError(idTextCEmail, idLblErrorConEmail))
			allhigert = false;
		return allhigert;
	}


	private boolean  printComponentError(Textbox textbox, Label label ){
		if (textbox.getValue().length() <= 6){
			if (label.getValue().length() > 0)
				label.setValue(", "+pro.getKey(ConstantsDNA.LBL_REGISTER_ERROR_FILEDS_SIZE));
			else
				label.setValue(pro.getKey(ConstantsDNA.LBL_REGISTER_ERROR_FILEDS_SIZE));
			label.setVisible(true);
			return false;
		}
		return true;
	}
	


	private boolean validateAllRequieremetData(){
		boolean isValidate= true;

			if (idTextEmail.getValue() == null || idTextEmail.getValue().isEmpty()){
				isValidate = false;
				lblEMail.setVisible(true);
			}
			if (idTextCEmail.getValue() == null || idTextCEmail.getValue().isEmpty()){
				isValidate = false;
				lblConfEmail.setVisible(true);
			}
			if (idTextRegisterName.getValue() == null ||  idTextRegisterName.getValue().isEmpty()){
				isValidate = false;
				lblFisrtName.setVisible(true);
			}
			if (idTxtSecondName.getValue() == null || idTxtSecondName.getValue().isEmpty()){
				lblSecondName.setVisible(true);
				isValidate = false;
			}
			if (idCombContry.getSelectedIndex() ==  -1){
				lblcontry.setVisible(true);
				isValidate = false;
			}
			/*
			if (idTxtIndutria.getValue() == null || idTxtIndutria.getValue().isEmpty()){
				isValidate = false;
				lblindustry.setVisible(true);
			}*/
			/*
			if (idTxtProviderName.getValue() == null || idTxtProviderName.getValue().isEmpty()){
				isValidate = false;
				lblProvider.setVisible(true);
			}*/
			return isValidate;
	}

	private void notVisible(boolean visible){

		lblEMail.setVisible(visible);
		lblConfEmail.setVisible(visible);
		lblFisrtName.setVisible(visible);
		lblSecondName.setVisible(visible);
		lblcontry.setVisible(visible);
		lblindustry.setVisible(visible);
		lblProvider.setVisible(visible);
	}

	private void saveRegister() throws ParseException{
		Register model =  getRegisterFromView();
		model.setRegisterDate(StrUtils.getDateStandar(new Date()));
		int idCrop = Integer.valueOf(idRGCornR.getSelectedItem().getValue().toString());
		serviceRegister.add(model, idCrop);
	}

	private Register getRegisterFromView() throws ParseException{
		Register model = new Register();
		model.setFisrtName(idTextRegisterName.getValue());
		model.setLastName(idTxtSecondName.getValue());
		model.setEmail(idTextEmail.getValue());
		model.setInmediateBoss(idTxtInmediateBoss.getValue() != null ? idTxtInmediateBoss.getValue() : null);
		model.setCity(idTxtCity.getValue() != null ? idTxtCity.getValue() : "" );
		model.setContry(idCombContry.getSelectedItem().getLabel());
		model.setIndustry(idTxtIndutria.getValue() != null ? idTxtIndutria.getValue(): "");
		model.setProviderName(idTxtProviderName.getValue() != null ? idTxtProviderName.getValue() : "");
		model.setPhoneNumber(idTxtPhone.getValue() != null ? idTxtPhone.getValue() : "");
		model.setIsPending(true);
		model.setRegisterDate(StrUtils.getDateStandar(new Date()));
		return model;
	}
	private void messageBox(String mess){
		Messagebox.show(mess,pro.getKey(LBL_GENERIC_MESS_INFORMATION), 
				Messagebox.OK, Messagebox.INFORMATION);
	}
	/** 
	 * Close Window
	 */
	public void closeWindow(){
		idWindow.onClose();
	}
}
