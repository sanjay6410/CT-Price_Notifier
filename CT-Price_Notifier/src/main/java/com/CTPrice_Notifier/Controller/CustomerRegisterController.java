package com.CTPrice_Notifier.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.CTPrice_Notifier.Service.RegisterService;
import com.commercetools.api.models.customer.Customer;
import com.commercetools.api.models.customer.CustomerSignInResult;

@RestController
public class CustomerRegisterController {

	@Autowired
	private RegisterService registerService;
	
	@PostMapping("/login")
	public ResponseEntity<HttpStatusCode> login(@RequestBody Customer customer) {
		try {
			CustomerSignInResult customerSignInResult = registerService.login(customer);

			if (customerSignInResult != null) {
				// Login successful
				return ResponseEntity.status(HttpStatus.OK).build();
			} else {
				// Login failed
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
		} catch (Exception e) {
			// Handle internal server error
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
