package com.fmning.postman.service;


import com.fmning.postman.model.Email;

import net.sargue.mailgun.Configuration;
import net.sargue.mailgun.Mail;

public class MailGunSender implements MailSender {
	
	public MailGunSender () {}

	@Override
	public void sendEmail(Email email) throws MailDeliveryException {
		
		Configuration configuration = new Configuration()
				.domain("fmning.com")
			    .apiKey("")
			    .from("admin@fmning.com");
		
		//Check and add attachment
		if (email.getAttachmentName() != null && email.getAttachmentPath() != null) {
			
		}
		
		
		
		Mail.using(configuration)
	    .to("synfm123@gmail.com")
	    .subject("This is the subject from kudo!")
	    .text("Hello world!")
	    .build()
	    .send();
		
	}

}
