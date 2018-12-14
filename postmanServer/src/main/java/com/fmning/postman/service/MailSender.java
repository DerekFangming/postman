package com.fmning.postman.service;

import com.fmning.postman.model.Email;

public interface MailSender {
	
	void sendEmail(Email email) throws MailDeliveryException;
}
