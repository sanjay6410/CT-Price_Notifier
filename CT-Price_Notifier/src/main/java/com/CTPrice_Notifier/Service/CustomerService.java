package com.CTPrice_Notifier.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.CTPrice_Notifier.Model.CustomerModelSignUp;
import com.commercetools.api.models.common.Address;
import com.commercetools.api.models.customer.Customer;

public interface CustomerService {

	CompletableFuture<Customer> getCustomerByEmail(String email) ;
	
	Customer getCustomerById(String custId);
		
	String  customerSignUp(CustomerModelSignUp customerModelSignUp);
	
	String  customerChangePassword(String email,String newPassword,String currentPassword) throws InterruptedException, ExecutionException;
	
	String customerResetPassword(String email,String newPassword);
	
	String customerInfoUpdate(String email,Address customerAddress) ;
}

