package com.fmning.postman.service;

import java.util.Properties;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.fmning.postman.model.Email;
import com.fmning.postman.util.Util;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;

public class GmailSender implements MailSender {
	
	private String gmailUsername;
	
	public GmailSender () throws MailDeliveryException{
		if (Util.gmailThreshold1.intValue() > 0) {
			Util.gmailThreshold1.decrementAndGet();
			gmailUsername = Util.gmailUsername1;
		} else if (Util.gmailThreshold2.intValue() > 0) {
			Util.gmailThreshold2.decrementAndGet();
			gmailUsername = Util.gmailUsername2;
		} else {
			throw new MailDeliveryException("Gmail reaches threshold. Cannot send any more.");
		}
	}

	@Override
	public void sendEmail(Email email) throws MailDeliveryException {
		Properties properties = new Properties(System.getProperties());
		String host = "smtp.gmail.com";
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.user", gmailUsername);
		properties.put("mail.smtp.password", Util.gmailPassword);
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");

		Session session = Session.getInstance(properties);
		MimeMessage message = new MimeMessage(session);

		try {
			message.setFrom(new InternetAddress(gmailUsername));
			if (email.getTo().contains(",")) {
				message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(email.getTo()));
			} else {
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(email.getTo()));
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
			
			Transport transport = session.getTransport("smtp");
			transport.connect(host, gmailUsername, Util.gmailPassword);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (Exception e) {
			throw new MailDeliveryException("Failed to send email with Gmail.");
		}
	}

}
