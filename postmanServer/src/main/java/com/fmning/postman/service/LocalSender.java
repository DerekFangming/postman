package com.fmning.postman.service;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.fmning.postman.model.Email;

public class LocalSender implements MailSender {
	
	public LocalSender () {}

	@Override
	public void sendEmail(Email email) throws MailDeliveryException {
		String host = "localhost";
		Properties properties = new Properties(System.getProperties());
		properties.setProperty("mail.smtp.host", host);
		properties.setProperty("mail.smtp.port", "25");

		Session session = Session.getInstance(properties);
		MimeMessage message = new MimeMessage(session);
		
		try {
			message.setFrom(new InternetAddress(email.getFrom()));
			if (email.getTo().contains(",")){
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email.getTo()));
			} else {
				message.setRecipient(Message.RecipientType.TO, new InternetAddress(email.getTo()));
			}
			
			message.setSubject(email.getSubject());
	
			//Check and add attachment
			if (email.getAttachmentName() != null && email.getAttachmentPath() != null) {
				Multipart multipart = new MimeMultipart();
				
				//Check for HTML content
				MimeBodyPart bodyPart = new MimeBodyPart();
				if (email.isHtml()) {
					bodyPart.setContent(email.getContent(), "text/html");
				} else {
					bodyPart.setText(email.getContent());
				}
				
				MimeBodyPart attachmentBodyPart= new MimeBodyPart();
		        DataSource source = new FileDataSource(email.getAttachmentPath());
		        attachmentBodyPart.setDataHandler(new DataHandler(source));
		        attachmentBodyPart.setFileName(email.getAttachmentName());

		        multipart.addBodyPart(bodyPart);
		        multipart.addBodyPart(attachmentBodyPart);
				message.setContent(multipart);
				
			} else {
				//Check for HTML content
				if (email.isHtml()) {
					message.setContent(email.getContent(), "text/html");
				} else {
					message.setText(email.getContent());
				}
			}
			
			Transport.send(message);
		} catch (Exception e) {
			throw new MailDeliveryException("Failed to send email with local postfix.");
		}
		
	}

}
