package com.fmning.postman.service;

@SuppressWarnings("serial")
public class MailDeliveryException extends RuntimeException {

	public MailDeliveryException(String message) {
		super(message);
	}
}
