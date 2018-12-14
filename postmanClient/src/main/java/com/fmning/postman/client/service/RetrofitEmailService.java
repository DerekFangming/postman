package com.fmning.postman.client.service;

import com.fmning.postman.client.model.PostmanResponse;
import com.fmning.postman.client.model.Email;
import com.fmning.postman.client.model.Notification;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitEmailService {
	
	@POST("notification")
	Call<PostmanResponse> sendNotification(@Body Notification notification);
	
	@POST("send_email")
	Call<PostmanResponse> sendEmail(@Body Email email);

}
