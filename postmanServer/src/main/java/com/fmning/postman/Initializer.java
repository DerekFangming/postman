package com.fmning.postman;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.fmning.postman.util.Util;

@Component
public class Initializer {

	@Value("${gmailUsername1}")
	private String gmailUsername1;
	
	@Value("${gmailUsername2}")
	private String gmailUsername2;
	
	@Value("${gmailPassword}")
	private String gmailPassword;

	@Value("${sendGridApiKey1}")
	private String sendGridApiKey1;

	@Value("${sendGridApiKey2}")
	private String sendGridApiKey2;
	
	@Value("${mailGunApiKey}")
	private String mailGunApiKey;
	
	@EventListener(ApplicationReadyEvent.class)
	public void initilizer(){
		Util.gmailUsername1 = gmailUsername1;
		Util.gmailUsername2 = gmailUsername2;
		Util.gmailPassword = gmailPassword;
		
		Util.sendGridApiKey1 = sendGridApiKey1;
		Util.sendGridApiKey2 = sendGridApiKey2;
		
		Util.mailGunApiKey = mailGunApiKey;
	}

}
