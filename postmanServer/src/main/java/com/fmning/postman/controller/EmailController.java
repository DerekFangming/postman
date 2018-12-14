package com.fmning.postman.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fmning.postman.model.Notification;
import com.fmning.postman.model.Email;
import com.fmning.postman.model.PostmanResponse;
import com.fmning.postman.model.EmailSenderType;
import com.fmning.postman.service.GmailSender;
import com.fmning.postman.service.LocalSender;
import com.fmning.postman.service.MailDeliveryException;
import com.fmning.postman.service.MailFormatException;
import com.fmning.postman.service.MailSender;
import com.fmning.postman.service.SendGridSender;
import com.fmning.postman.util.Util;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class EmailController {
	
	@GetMapping("/send_email")
	public PostmanResponse sendGetEmail(Email email) {
		try {
			validateAndSendEmail(email);
		} catch (Exception e) {
			return new PostmanResponse(e.getMessage());
		}
		
		return new PostmanResponse();
	}
	
	//For post request
	@PostMapping("/send_email")
	public PostmanResponse sendPostEmail(@RequestBody Email email) {
		try {
			validateAndSendEmail(email);
		} catch (Exception e) {
			return new PostmanResponse(e.getMessage());
		}
		
		return new PostmanResponse();
	}
	
	//For form posting
	@PostMapping("/post_email")
	public PostmanResponse postEmail(@ModelAttribute Email email) {
		try {
			validateAndSendEmail(email);
		} catch (Exception e) {
			return new PostmanResponse(e.getMessage());
		}
		
		return new PostmanResponse();
	}
	
	private void validateAndSendEmail(Email email) throws MailFormatException, MailDeliveryException {
		Util.validateEmailFormat(email);
		
		MailSender sender = null;
		if (email.getEmailSenderType() == EmailSenderType.LOCAL) {
			sender = new LocalSender();
		} else {
			try {
				if (email.getEmailSenderType() == EmailSenderType.GMAIL) {
					sender = new GmailSender();
				} else if (email.getEmailSenderType() == EmailSenderType.SEND_GRID) {
					sender = new SendGridSender();
				}
			}catch (MailDeliveryException e) {
				sender = new LocalSender();
			}
		}
		
		try {
			sender.sendEmail(email);
		} catch (MailDeliveryException e) {
			if (email.getEmailSenderType() == EmailSenderType.LOCAL) {
				throw e;
			} else {
				try {
					sender = new LocalSender();
					sender.sendEmail(email);
					throw new MailDeliveryException("Failed to send email with " + email.getEmailSenderType() + ". Sent through local mail server.");
				} catch (MailDeliveryException ignored) {
					throw e;
				}
			}
		}
	}
	
	//For get request
	@GetMapping("/notification")
	public PostmanResponse sendGetNotification(Notification notification) {
		try {
			Util.notification(notification);
		} catch (Exception e) {
			return new PostmanResponse(e.getMessage());
		}
		
		return new PostmanResponse();
	}
	
	//For post request
	@PostMapping("/notification")
	public PostmanResponse sendPostNotification(@RequestBody Notification notification) {
		try {
			Util.notification(notification);
		} catch (Exception e) {
			return new PostmanResponse(e.getMessage());
		}
		
		return new PostmanResponse();
	}
	

}
