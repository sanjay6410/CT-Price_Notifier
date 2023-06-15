package com.CTPrice_Notifier.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.http.ResponseEntity;

import com.CTPrice_Notifier.Model.CustomerModelSignUp;
import com.commercetools.api.models.customer.Customer;

public interface CustomerService {

	CompletableFuture<Customer> getCustomerByEmail(String email) ;
	
	String  customerSignUp(CustomerModelSignUp customerModelSignUp);
}
