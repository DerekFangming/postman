package com.fmning.postman.model;

public class Email {
	
	private String from;
	private String to;
	private String subject;
	private String content;
	private EmailContentType emailContentType;
	private EmailSenderType emailSenderType;

	private String attachmentPath;
	private String attachmentName;
	
	public Email() {
		this.emailContentType = EmailContentType.PLAIN;
		this.emailSenderType = EmailSenderType.LOCAL;
	}
	
	public Email(String from, String to, String subject, String content) {
		this(from, to, subject, content, EmailContentType.PLAIN, EmailSenderType.LOCAL);
	}

	public Email(String from, String to, String subject, String content, EmailContentType emailContentType, EmailSenderType emailSenderType) {
		this.from = from;
		this.to = to;
		this.subject = subject;
		this.content = content;
		this.emailContentType = emailContentType;
		this.emailSenderType = emailSenderType;
	}
	
	public void setAttachment(String attachmentName, String attachmentPath) {
		this.attachmentName = attachmentName;
		this.attachmentPath = attachmentPath;
	}
	
	public String getFrom() {
		return from;
	}
	
	public void setFrom(String from) {
		this.from = from;
	}
	
	public String getTo() {
		return to;
	}
	
	public void setTo(String to) {
		this.to = to;
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
	
	public String getAttachmentPath() {
		return attachmentPath;
	}

	public void setAttachmentPath(String attachmentPath) {
		this.attachmentPath = attachmentPath;
	}

	public String getAttachmentName() {
		return attachmentName;
	}

	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}
	
	public EmailContentType getEmailContentType() {
		return emailContentType;
	}

	public void setEmailContentType(EmailContentType emailContentType) {
		this.emailContentType = emailContentType;
	}

	public EmailSenderType getEmailSenderType() {
		return emailSenderType;
	}

	public void setEmailSenderType(EmailSenderType emailSenderType) {
		this.emailSenderType = emailSenderType;
	}
	
	public boolean isHtml() {
		return emailContentType == EmailContentType.HTML;
	}

}
