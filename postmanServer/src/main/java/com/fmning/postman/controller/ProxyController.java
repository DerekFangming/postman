package com.fmning.postman.controller;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProxyController {
	
	@GetMapping("/proxy")
	public String sendGetEmail(@RequestParam("url") String url) {
		try {
			return Jsoup.connect(url).get().toString();
		} catch (IOException e) {
			return e.getMessage();
		}
	}

}
