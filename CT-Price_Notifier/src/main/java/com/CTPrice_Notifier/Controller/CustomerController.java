package com.CTPrice_Notifier.Controller;

import java.time.LocalDate;
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

import com.CTPrice_Notifier.Model.CustomerModelChangePassword;
import com.CTPrice_Notifier.Model.CustomerModelResetPassword;
import com.CTPrice_Notifier.Model.CustomerModelSignUp;
import com.CTPrice_Notifier.Service.CustomerService;
import com.commercetools.api.models.common.Address;
import com.commercetools.api.models.customer.Customer;

@RestController
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/getCustomerByEmail")
	public CompletableFuture<Customer> getCustomerByEmail(@RequestParam("email") String email)  {
		return customerService.getCustomerByEmail(email);
	}
	
	@GetMapping("/getCustomerById")
	public ResponseEntity<?> getCustomerById(@RequestParam("custId") String custId){
		try {
		return ResponseEntity.ok(customerService.getCustomerById(custId));
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Not Found "+e.getMessage());
		}
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
	
	@PostMapping("/customerChangePassword")
	public ResponseEntity<?> customerChangePassword(@RequestBody CustomerModelChangePassword customerModelChangePassword) throws InterruptedException, ExecutionException{
		try {
		String status=customerService.customerChangePassword(customerModelChangePassword.getEmail(),
				 customerModelChangePassword.getNewPassword(),customerModelChangePassword.getOldPassword());
		return ResponseEntity.ok(status);
		}catch(Exception e){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Customer Password Not Changed "+e.getMessage());
		}
	}
	
	@PostMapping("/customerResetPassword")
	public ResponseEntity<?> customerResetPassword(@RequestBody CustomerModelResetPassword customerModelResetPassword){
		try {
			String status=customerService.customerResetPassword(customerModelResetPassword.getEmail(), customerModelResetPassword.getNewPassword());
			return ResponseEntity.ok(status);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Password Reset Failed "+e.getMessage());
		}
	}
	
	@PostMapping("/customerProfileUpdate")
	public ResponseEntity<?> customerProfileUpdate(@RequestParam("email") String email,@RequestParam("dob") LocalDate dob,
			@RequestBody Address customerAddress){
		try {
			String status=customerService.customerInfoUpdate(email, dob, customerAddress);
			return ResponseEntity.ok(status);
			
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Profile Updation Failed   "+e.getMessage());
		}
	}
	
	
}
