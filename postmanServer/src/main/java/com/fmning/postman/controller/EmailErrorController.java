package com.fmning.postman.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fmning.postman.model.PostmanResponse;

@RestController
public class EmailErrorController implements ErrorController {

	private static final String PATH = "/error";
	
	@RequestMapping(value = PATH)
    public PostmanResponse error(HttpServletResponse response) {
		if (response.getStatus() == 400) {
			String example="{\"to\" : \"name@example.com\", \"subject\": \"How's going\", \"content\": \"Just want to say hi. Best regards.\"}";
			return new PostmanResponse("Email parameters that cannot be parsed. Here is an valid example in JSON format: " + example);
		} else if (response.getStatus() == 404) {
			return new PostmanResponse("Request path does not exist");
		}
		
        return new PostmanResponse("Internal server error.");
    }
	
	@Override
	public String getErrorPath() {
		return PATH;
	}

}
