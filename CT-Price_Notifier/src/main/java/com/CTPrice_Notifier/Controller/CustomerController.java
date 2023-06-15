package com.CTPrice_Notifier.Controller;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.CTPrice_Notifier.Model.CustomerModelSignUp;
import com.CTPrice_Notifier.Service.CustomerService;
import com.commercetools.api.models.customer.Customer;

@RestController
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/getCustomerByEmail")
	public CompletableFuture<Customer> getCustomerByEmail(@RequestParam("email") String email)  {
		return customerService.getCustomerByEmail(email);
	}
	
	@PostMapping("/customerSignUp")
	public ResponseEntity<?> customerSignUp(@RequestBody CustomerModelSignUp customerModelSignUp){
		try {
		String status= customerService.customerSignUp(customerModelSignUp);
		return ResponseEntity.ok(status);
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Customer Not Created" +e.getMessage());
		}
	}
}
