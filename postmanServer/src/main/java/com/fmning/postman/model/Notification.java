package com.fmning.postman.model;

public class Notification {
	
	private String subject;
	private String content;
	
	/**
	 * Create an empty notification object
	 * A notification object is a email sent to myself with local sender
	 */
	public Notification() {}
	
	/**
	 * Create a notification object with content.
	 * Subject will be defaulted to "Postman notification"
	 * @param content the content of the notification
	 */
	public Notification (String content) {
		this("Postman Notification", content);
	}
	
	/**
	 * Create a notification object with subject and content
	 * @param subject the subject of the notification
	 * @param content the content of the notification
	 */
	public Notification (String subject, String content) {
		this.subject = subject;
		this.content = content;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
