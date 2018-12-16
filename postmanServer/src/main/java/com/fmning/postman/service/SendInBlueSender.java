package com.fmning.postman.service;

import java.util.Collections;

import com.fmning.postman.model.Email;
import com.fmning.postman.util.Util;

import sendinblue.ApiClient;
import sendinblue.ApiException;
import sendinblue.Configuration;
import sendinblue.auth.ApiKeyAuth;
import sibApi.SmtpApi;
import sibModel.CreateSmtpEmail;
import sibModel.SendSmtpEmail;
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
		sendSmtpEmail.setSender(new SendSmtpEmailSender().email("fning@fmning.com"));
		sendSmtpEmail.setTo(Collections.singletonList(new SendSmtpEmailTo().email("synfm123@gmail.com")));
		sendSmtpEmail.setSubject("from send in blue!");
		sendSmtpEmail.setTextContent("test content!");
		sendSmtpEmail.setReplyTo(new SendSmtpEmailReplyTo().email("fning@fmning.com"));
		
		try {
			CreateSmtpEmail result = apiInstance.sendTransacEmail(sendSmtpEmail);
			System.out.println(result);
		} catch (ApiException e) {
			System.err.println("Exception when calling SmtpApi#sendTransacEmail");
			e.printStackTrace();
		}
		
	}

}
