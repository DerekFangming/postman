package com.fmning.postman.client.model;

public class PostmanResponse {
	
	private String error;
	
	public PostmanResponse() {}
	
	public PostmanResponse(String error) {
		this.error = error;
	}
	
	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
