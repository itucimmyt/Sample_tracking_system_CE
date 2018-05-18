package com.cimmyt.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.cimmyt.dnast.dto.AuthUserBean;

import com.cimmyt.service.ServiceEmail;
import com.cimmyt.service.impl.ServiceUserImpl;

public class ManageEmail implements ServiceEmail{

	
	private Properties props = new Properties();
	private  String username = Constants.EMAIL_ACCOUNT_MANAGER;
	private	 String password = Constants.EMAIL_ACCOUNT_MANAGER_PASSWORD;
	private Address [] address;
	private String cropStr;
	private String url;
	private Logger logger= Logger.getLogger(ManageEmail.class);
	public void sendEmailCreationUser (int crop, String emailApplicant, String userName) {
		setProperties();
		   Session session = Session.getInstance(props,
			          new javax.mail.Authenticator() {
			            protected PasswordAuthentication getPasswordAuthentication() {
			                return new PasswordAuthentication(username, password);
			            }
			          });

	        try {

	        	setListMailByCrop(crop);
	            Message message = new MimeMessage(session);
	            message.setFrom(new InternetAddress(username));
	            message.setRecipients(Message.RecipientType.TO,
	  	              address);
	            
	           message.setRecipients(Message.RecipientType.CC,
	            		InternetAddress.parse(emailApplicant)  );
	            message.setSubject("DNA STS New User");
	            // This mail has 2 part, the BODY and the embedded image
	            MimeMultipart multipart = new MimeMultipart("related");

	            // first part (the html)
	            BodyPart messageBodyPart = new MimeBodyPart();
	            String htmlText = "<html dir=\"ltr\">" +
	            		"<meta http-equiv=\"Content-Type\" content=\"text/html; CHARSET=utf-8\">" +
	            		"<H1>DNA Sample Tracking System</H1>" +
	            		"<div id=\"divSn\" class=\"divSn divTx\">"+"Your request will be answered for the user : "+userName+" with the email : "+emailApplicant+" as soon as possible."+"</div>"+
	            		"<div class=\"divSn divTx\">"+"Crop : "+ cropStr+"</div>"+
	            		"<div class=\"divSn divTx\">"+"Thanks "+"</div>"+
	            		"<p/>"+
	            		"<div style=\"margin:0;\"><font face=\"Times New Roman,serif\" size=\"3\"><span style=\"font-size:12pt;\"><font face=\"Palatino Linotype,serif\" size=\"2\" color=\"#595959\"><span style=\"font-size:10pt;\"><b>International Maize and Wheat Improvement Center (CIMMYT)</b></span></font></span></font></div>" +
	            		"<div style=\"margin:0;\"><font face=\"Times New Roman,serif\" size=\"3\"><span style=\"font-size:12pt;\"><font face=\"Palatino Linotype,serif\" size=\"2\" color=\"#595959\"><span style=\"font-size:10pt;\" lang=\"es-MX\">Carretera M M&#233;xico-Veracruz Km. 45, El Bat&#225;n, Texcoco, M&#233;xico,C.P. 56237</span></font></span></font></div>" +
	            		"<img src=\"cid:image\">" +
	            		"</html>";
	            messageBodyPart.setContent(htmlText, "text/html");
	            // add it
	            multipart.addBodyPart(messageBodyPart);

	            // second part (the image)
	            messageBodyPart = new MimeBodyPart();
	            DataSource fds = null;
	            try{
	            File tempFile = File.createTempFile("image.jpg", "image.jpg");
	            tempFile.deleteOnExit();
	            
	            FileOutputStream out = new FileOutputStream(tempFile);
	            String rutaResumen="/com/cimmyt/reports/src/imageCIMMYT.jpg";
	    		InputStream isReportStream =getClass().getResourceAsStream(rutaResumen);
	            IOUtils.copy(isReportStream, out);
	             ///tempFile;
	            fds = new FileDataSource(tempFile);
	            }catch (Exception ex){
	            	ex.printStackTrace();
	            }

	            messageBodyPart.setDataHandler(new DataHandler(fds));
	            messageBodyPart.setHeader("Content-ID", "<image>");

	            // add image to the multipart
	            multipart.addBodyPart(messageBodyPart);
	            	
	            // put everything together
	            message.setContent(multipart);
	            // Send message
	            Transport.send(message);
	        } catch (MessagingException e) {
	        	logger.error(e.getMessage(), e);
	        }catch (Exception ex){
	        	logger.error(ex.getMessage(), ex);
	        }
	}


	public void sendEmailWithCredencial(AuthUserBean user){
		setProperties();
		   Session session = Session.getInstance(props,
			          new javax.mail.Authenticator() {
			            protected PasswordAuthentication getPasswordAuthentication() {
			                return new PasswordAuthentication(username, password);
			            }
			          });

	        try {

	        	setListMailByCrop(user.getStOrganism().getOrganismid());
	            Message message = new MimeMessage(session);
	            message.setFrom(new InternetAddress(username));
	            message.setRecipients(Message.RecipientType.TO,
	  	              address);
	            
	           message.setRecipients(Message.RecipientType.CC,
	            		InternetAddress.parse(user.getEmail()));
	            message.setSubject("DNA STS New User");
	            // This mail has 2 part, the BODY and the embedded image
	            MimeMultipart multipart = new MimeMultipart("related");

	            // first part (the html)
	            BodyPart messageBodyPart = new MimeBodyPart();
	            String htmlText = "<html dir=\"ltr\">" +
	            		"<meta http-equiv=\"Content-Type\" content=\"text/html; CHARSET=utf-8\">" +
	            		"<H1>DNA Sample Tracking System</H1>" +
	            		"<div id=\"divSn\" class=\"divSn divTx\">"+"Your request was answered for the user : "+user.getUserName()+" and password :"+user.getPassword() +" with the email : "+user.getEmail()+"</div>"+
	            		"<div class=\"divSn divTx\">"+"Crop : "+ cropStr+" URL : "+url +"</div>"+
	            		"<div class=\"divSn divTx\">"+"Thanks "+"</div>"+
	            		"<p/>"+
	            		"<div style=\"margin:0;\"><font face=\"Times New Roman,serif\" size=\"3\"><span style=\"font-size:12pt;\"><font face=\"Palatino Linotype,serif\" size=\"2\" color=\"#595959\"><span style=\"font-size:10pt;\"><b>International Maize and Wheat Improvement Center (CIMMYT)</b></span></font></span></font></div>" +
	            		"<div style=\"margin:0;\"><font face=\"Times New Roman,serif\" size=\"3\"><span style=\"font-size:12pt;\"><font face=\"Palatino Linotype,serif\" size=\"2\" color=\"#595959\"><span style=\"font-size:10pt;\" lang=\"es-MX\">Carretera M M&#233;xico-Veracruz Km. 45, El Bat&#225;n, Texcoco, M&#233;xico,C.P. 56237</span></font></span></font></div>" +
	            		"<img src=\"cid:image\">" +
	            		"</html>";
	            messageBodyPart.setContent(htmlText, "text/html");
	            // add it
	            multipart.addBodyPart(messageBodyPart);

	            // second part (the image)
	            messageBodyPart = new MimeBodyPart();
	            DataSource fds = null;
	            try{
	            File tempFile = File.createTempFile("image.jpg", "image.jpg");
	            tempFile.deleteOnExit();
	            
	            FileOutputStream out = new FileOutputStream(tempFile);
	            String rutaResumen="/com/cimmyt/reports/src/imageCIMMYT.jpg";
	    		InputStream isReportStream =getClass().getResourceAsStream(rutaResumen);
	            IOUtils.copy(isReportStream, out);
	             ///tempFile;
	            fds = new FileDataSource(tempFile);
	            }catch (Exception ex){
	            	ex.printStackTrace();
	            }

	            messageBodyPart.setDataHandler(new DataHandler(fds));
	            messageBodyPart.setHeader("Content-ID", "<image>");

	            // add image to the multipart
	            multipart.addBodyPart(messageBodyPart);
	            	
	            // put everything together
	            message.setContent(multipart);
	            // Send message
	            Transport.send(message);
	        } catch (MessagingException e) {
	        	logger.error(e.getMessage(), e);
	        }catch (Exception ex){
	        	logger.error(ex.getMessage(), ex);
	        }
	}
	private void setListMailByCrop(int crop){
		//List<Address> listAddress = new ArrayList<Address>();
		List<InternetAddress> internetAddresList = new ArrayList<InternetAddress>();  
		switch (crop){
		case 1:{ 
			cropStr = "Maize";
			String [] arrlist = Constants.EMAIL_ACCOUNT_RECEIVER_MAIZE.split(";");
			for (int i =0 ; i < arrlist.length; i++ ){
				InternetAddress addresint = new InternetAddress();
				addresint.setAddress(arrlist[i]);
				internetAddresList.add(addresint);
				
				 url = Constants.URL_MAIZE_PRODUCTION;
			}
				
			break;
			}
		case 2:{
			cropStr = "Wheat";
			String [] arrlist = Constants.EMAIL_ACCOUNT_RECEIVER_WHEAT.split(";");
			for (int i =0 ; i < arrlist.length; i++ ){
				InternetAddress addresint = new InternetAddress();
				addresint.setAddress(arrlist[i]);
				internetAddresList.add(addresint);
				url = Constants.URL_WHEAT_PRODUCTION;
			}
				
			break;
		}
			default:
			{
				cropStr = "Maize";
				InternetAddress addresint = new InternetAddress();
				addresint.setAddress(Constants.EMAIL_ACCOUNT_MANAGER);
				internetAddresList.add(addresint);
			}
				
		}
		address = new Address[internetAddresList.size()];
		for (int i = 0; i < internetAddresList.size();i++ ){
			address [i] =  internetAddresList.get(i);
		}
		
	}
	private void setProperties (){
		   props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.starttls.enable", "false");
	        props.put("mail.smtp.host", Constants.EMAIL_SMTP_HOST);
	        props.put("mail.smtp.port", Constants.EMAIL_SMTP_PORT);
	}
}