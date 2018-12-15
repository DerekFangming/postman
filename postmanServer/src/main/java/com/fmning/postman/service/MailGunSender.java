package com.fmning.postman.service;


import java.io.File;

import com.fmning.postman.model.Email;
import com.fmning.postman.util.Util;

import net.sargue.mailgun.Configuration;
import net.sargue.mailgun.Mail;
import net.sargue.mailgun.MailBuilder;
import net.sargue.mailgun.Response;
import net.sargue.mailgun.Response.ResponseType;

public class MailGunSender implements MailSender {
	
	public MailGunSender () {}

	@Override
	public void sendEmail(Email email) throws MailDeliveryException {
		
		Configuration configuration = new Configuration()
				.domain("fmning.com")
			    .apiKey(Util.mailGunApiKey)
			    .from("admin@fmning.com");
		
		MailBuilder builder = Mail.using(configuration);
		Response response;
		
		try {
			builder.to(email.getTo()).subject(email.getSubject());
			
			//Check for HTML content
			if (email.isHtml()) {
				builder.html(email.getContent());
			} else {
				builder.text(email.getContent());
			}
			
			//Check and add attachment
			if (email.getAttachmentName() != null && email.getAttachmentPath() != null) {
				File attachment = new File(email.getAttachmentPath());
				response = builder.multipart().attachment(attachment).build().send();
			} else {
				response = builder.build().send();
			}
			
		} catch (Exception e) {
			throw new MailDeliveryException(e.getMessage());
		}
		
		if (response.responseType() != ResponseType.OK) {
			throw new MailDeliveryException(response.responseMessage());
		}
	}

}
