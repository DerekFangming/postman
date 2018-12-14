package com.fmning.postman.service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;

import com.fmning.postman.util.Util;
import com.sendgrid.Attachments;
import com.sendgrid.Content;
import com.sendgrid.Mail;
import com.sendgrid.Email;
import com.sendgrid.Method;
import com.sendgrid.Personalization;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

public class SendGridSender implements MailSender {
	
	private String sendGridApiKey;
	
	public SendGridSender () {
		if (Util.sendGridThreshold1.intValue() > 0) {
			Util.sendGridThreshold1.decrementAndGet();
			sendGridApiKey = Util.sendGridApiKey1;
		} else if (Util.sendGridThreshold2.intValue() > 0) {
			Util.sendGridThreshold2.decrementAndGet();
			sendGridApiKey = Util.sendGridApiKey2;
		} else {
			throw new MailDeliveryException("Gmail reaches threshold. Cannot send any more.");
		}
	}

	@Override
	public void sendEmail(com.fmning.postman.model.Email email) throws MailDeliveryException {
		
		Mail mail = new Mail();
		mail.setFrom(new Email(email.getFrom()));
		Personalization personalization = new Personalization();
		String[] recipents = email.getTo().toString().split(",");
		for (String recipent : recipents)
			personalization.addTo(new Email(recipent));
		
		mail.addPersonalization(personalization);
		mail.setSubject(email.getSubject());
		
		//Check for HTML content
		mail.addContent(new Content("text/" + (email.isHtml() ? "html" : "plain"), email.getContent()));
		
		//Check and add attachment
		if (email.getAttachmentName() != null && email.getAttachmentPath() != null) {
			try {
				File file = new File(email.getAttachmentPath());
			    byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
			    
				Attachments attachments = new Attachments();
				attachments.setContent(new String(encoded, StandardCharsets.US_ASCII));
				attachments.setType(Files.probeContentType(file.toPath()));
				attachments.setFilename(email.getAttachmentName());
				attachments.setDisposition("attachment");
	            mail.addAttachments(attachments);
			} catch (IOException e) {
				throw new MailDeliveryException("Failed to read email attachment");
			}
			
		}

		SendGrid sg = new SendGrid(sendGridApiKey);
		Request request = new Request();
		try {
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			Response response = sg.api(request);
			
			if (response.getStatusCode() != 202) {
				throw new MailDeliveryException("Failed to send email with Send Grid");
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			throw new MailDeliveryException("Failed to send email with Send Grid");
		}
	}

}
