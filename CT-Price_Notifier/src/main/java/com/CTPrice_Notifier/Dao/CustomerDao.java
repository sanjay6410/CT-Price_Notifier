package com.CTPrice_Notifier.Dao;

import java.util.concurrent.CompletableFuture;

import com.CTPrice_Notifier.Model.CustomerModelSignUp;
import com.commercetools.api.models.customer.Customer;
import com.commercetools.api.models.customer.CustomerPagedQueryResponse;

public interface CustomerDao {

	CompletableFuture<Customer> getCustomerByEmail(String email);
	CustomerPagedQueryResponse getCustomerByEmailWithoutCompletableFuture(String email) ;
	
	String customerSignUp(CustomerModelSignUp customerSignUp);
	
	Customer getCustomerById(String custId);
	
}