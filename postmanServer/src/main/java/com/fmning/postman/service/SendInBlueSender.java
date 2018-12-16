package com.fmning.postman.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;

import com.fmning.postman.model.Email;
import com.fmning.postman.util.Util;

import sendinblue.ApiClient;
import sendinblue.ApiException;
import sendinblue.Configuration;
import sendinblue.auth.ApiKeyAuth;
import sibApi.SmtpApi;
import sibModel.SendSmtpEmail;
import sibModel.SendSmtpEmailAttachment;
import sibModel.SendSmtpEmailReplyTo;
import sibModel.SendSmtpEmailSender;
import sibModel.SendSmtpEmailTo;

public class SendInBlueSender implements MailSender {
	
	public SendInBlueSender () {
		if (Util.sendInBlueThreshold.intValue() > 0) {
			Util.sendInBlueThreshold.decrementAndGet();
		} else {
			throw new MailDeliveryException("Send in blue reaches threshold. Cannot send any more.");
		}
	}

	@Override
	public void sendEmail(Email email) throws MailDeliveryException {
		
		ApiClient defaultClient = Configuration.getDefaultApiClient();
		ApiKeyAuth apiKey = (ApiKeyAuth) defaultClient.getAuthentication("api-key");
		apiKey.setApiKey(Util.sendInBlueApiKey);
		
		SmtpApi apiInstance = new SmtpApi();
		
		SendSmtpEmail sendSmtpEmail = new SendSmtpEmail();
		sendSmtpEmail.setSender(new SendSmtpEmailSender().email(email.getFrom()));
		sendSmtpEmail.setReplyTo(new SendSmtpEmailReplyTo().email(email.getFrom()));
		sendSmtpEmail.setTo(Collections.singletonList(new SendSmtpEmailTo().email(email.getTo())));
		sendSmtpEmail.setSubject(email.getSubject());
		
		//Check for HTML content
		if (email.isHtml()) {
			sendSmtpEmail.setHtmlContent(email.getContent());
		} else {
			sendSmtpEmail.setTextContent(email.getContent());
		}
		
		//Check and add attachment
		if (email.getAttachmentName() != null && email.getAttachmentPath() != null) {
			File attachment = new File(email.getAttachmentPath());
			try {
				sendSmtpEmail.setAttachment(Collections.singletonList(new SendSmtpEmailAttachment().name(email.getAttachmentName()).content(Files.readAllBytes(attachment.toPath()))));
			} catch (IOException e) {
				throw new MailDeliveryException("Attachment is not readable.");
			}
		}
		
		
		try {
			apiInstance.sendTransacEmail(sendSmtpEmail);
		} catch (ApiException e) {
			throw new MailDeliveryException(e.getMessage());
		}
		
	}

}
