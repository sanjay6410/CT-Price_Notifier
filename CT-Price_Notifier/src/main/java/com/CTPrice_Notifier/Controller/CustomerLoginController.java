package com.CTPrice_Notifier.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.CTPrice_Notifier.Service.CustomerLoginService;
import com.commercetools.api.models.customer.Customer;
import com.commercetools.api.models.customer.CustomerSignInResult;

@RestController
public class CustomerLoginController {

	@Autowired
	private CustomerLoginService customerLoginService;
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody Customer customer) {
		try {
			CustomerSignInResult customerSignInResult = customerLoginService.login(customer);

			if (customerSignInResult != null) {
				// Login successful
				return ResponseEntity.status(HttpStatus.OK).body(customerSignInResult.getCustomer().getId());
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
