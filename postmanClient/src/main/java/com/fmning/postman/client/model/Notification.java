package com.fmning.postman.client.model;

public class Notification {
	
	private String title;
	private String status;
	
	public Notification() {}
	
	public Notification (String status) {
		this("Poseman Service Status Report", status);
	}
	
	public Notification (String title, String status) {
		this.title = title;
		this.status = status;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

}
