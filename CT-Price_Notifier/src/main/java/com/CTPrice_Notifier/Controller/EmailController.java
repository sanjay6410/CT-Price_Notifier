package com.CTPrice_Notifier.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.CTPrice_Notifier.Service.MailServiceImpl;

@RestController
public class EmailController {

	@Autowired
	private MailServiceImpl mailService;
	
	@PostMapping("/sendSampleEmail")
	public void sendSampleEmail() {
		mailService.sendSampleEmail("sanjayguptha13065@gmail.com", "This is from sprring boot application mail sender", 
				"SampleEmail");
	}
}
