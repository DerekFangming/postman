package com.fmning.postman.model;

public class PostmanResponse {
	
	private String error;
	
	public PostmanResponse () {
		this.error = "";
	}
	
	public PostmanResponse (String error) {
		this.error = error;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
