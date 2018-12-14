package com.fmning.postman.client.service;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fmning.postman.client.model.PostmanResponse;
import com.fmning.postman.client.model.Email;
import com.fmning.postman.client.model.Email.EmailContentType;
import com.fmning.postman.client.model.Email.EmailSenderType;
import com.fmning.postman.client.model.Notification;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Component
public class EmailService {
	
	private static final String RETROFIT_IO_EXCEPTION = "Cannot reach postman service endpoint.";
	
	private RetrofitEmailService service;
	
	public EmailService () {
		this("http://localhost:8080/postman/api/");
	}
	
	public EmailService (String endpointUrl) {
		Retrofit retrofit = new Retrofit.Builder()
    		    .baseUrl(endpointUrl)
    		    .addConverterFactory(JacksonConverterFactory.create())
    		    .build();
    	service = retrofit.create(RetrofitEmailService.class);
	}
	
	/**
	 * Send notifications (local email) to myself
	 * @param title the optional notification content
	 * @param status the notification content
	 * @param notification the full notification
	 * @return response with error message, if any
	 */
	public PostmanResponse sendNotification(String status) {
		return sendNotification(new Notification(status));
	}
	
	public PostmanResponse sendNotification(String title, String status) {
		return sendNotification(new Notification(title, status));
	}
	
	public PostmanResponse sendNotification(Notification notification) {
		try {
			return service.sendNotification(notification).execute().body();
		} catch (IOException ignored) {
			return new PostmanResponse(RETROFIT_IO_EXCEPTION);
		}
	}
	
	/**
	 * Send email as described by Email object
	 * @param from the email from. Gmail sender will ignore this
	 * @param to the email recipient
	 * @param subject the subject of the email
	 * @param content the content of the email
	 * @param emailContentType the optional content type. Could be plain of HTML
	 * @param emailSenderType the optional sender type. Could be local, Gmail or Send Grid
	 * @param email the optional full email object
	 * @return response with error message, if any
	 */
	public PostmanResponse sendEmail(String from, String to, String subject, String content) {
		return sendEmail(new Email(from, to, subject, content, EmailContentType.PLAIN, EmailSenderType.LOCAL));
	}
	
	public PostmanResponse sendEmail(String from, String to, String subject, String content, EmailContentType emailContentType, EmailSenderType emailSenderType) {
		return sendEmail(new Email(from, to, subject, content, emailContentType, emailSenderType));
	}
	
	public PostmanResponse sendEmail(Email email) {
		try {
			return service.sendEmail(email).execute().body();
		} catch (IOException ignored) {
			return new PostmanResponse(RETROFIT_IO_EXCEPTION);
		}
	}

}
