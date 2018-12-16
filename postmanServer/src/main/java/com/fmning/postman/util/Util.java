package com.fmning.postman.util;

import java.util.concurrent.atomic.AtomicInteger;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import com.fmning.postman.model.Email;
import com.fmning.postman.model.Notification;
import com.fmning.postman.service.LocalSender;
import com.fmning.postman.service.MailDeliveryException;
import com.fmning.postman.service.MailFormatException;

public class Util {
	//Gmail
	public static AtomicInteger gmailThreshold1 = new AtomicInteger(100);
	public static AtomicInteger gmailThreshold2 = new AtomicInteger(100);
	
	public static String gmailUsername1 = "";
	public static String gmailUsername2 = "";
	public static String gmailPassword = "";
	
	public static boolean gmailWarningSend = false;
	public static boolean gmailErrorSend = false;
	
	//Send Grid
	public static AtomicInteger sendGridThreshold1 = new AtomicInteger(100);
	public static AtomicInteger sendGridThreshold2 = new AtomicInteger(100);

	public static String sendGridApiKey1 = "";
	public static String sendGridApiKey2 = "";
	
	public static boolean sendGridWarningSend = false;
	public static boolean sendGridErrorSend = false;
	
	//Send in blue
	public static AtomicInteger sendInBlueThreshold = new AtomicInteger(300);
	public static String sendInBlueApiKey = "";
	public static boolean sendInBlueWarningSend = false;
	public static boolean sendInBlueErrorSend = false;
	
	//Mail gun
	public static AtomicInteger mailGunThreshold = new AtomicInteger(300);
	public static String mailGunApiKey = "";
	public static boolean mailGunWarningSend = false;
	public static boolean mailGunErrorSend = false;
	
	
	public static void validateEmailFormat (Email email) throws MailFormatException {
		//Subject
		if (email.getSubject() == null) {
			throw new MailFormatException("The email is missing subject");
		}
		
		//Content
		if (email.getContent() == null) {
			throw new MailFormatException("The email is missing content");
		}
		
		//Email from field
		if (email.getFrom() == null) {
			email.setFrom("admin@fmning.com");
		} else {
			try {
				InternetAddress emailAddr = new InternetAddress(email.getFrom());
				emailAddr.validate();
			} catch (AddressException e) {
				throw new MailFormatException("The from email " + email.getFrom() + " is not valid.");
			}
		}
		
		//Email to field
		if (email.getTo() == null) {
			throw new MailFormatException("The email is missing \"to\" address");
		} else {
			try {
				if (email.getTo().contains(",")) {
					InternetAddress[] emailAddresses = InternetAddress.parse(email.getTo());
					for (InternetAddress ia : emailAddresses) {
						ia.validate();
					}
				} else {
					InternetAddress emailAddr = new InternetAddress(email.getTo());
					emailAddr.validate();
				}
			} catch (AddressException e) {
				throw new MailFormatException("The to email " + email.getTo() + " is not valid.");
			}
		}
		
	}
	
	public static void notification(Notification notification) throws MailDeliveryException {
		if (notification.getSubject() == null || notification.getSubject().trim().equals("")) {
			notification.setSubject("Postman Notification");
		}
		
		if (notification.getContent() == null || notification.getContent().trim().equals("")) {
			notification.setSubject("Empty Message");
		}
		LocalSender localSender = new LocalSender();
		localSender.sendEmail(new Email("admin@fmning.com", "synfm123@gmail.com", notification.getSubject(), notification.getContent()));
	}

}
