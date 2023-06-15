package com.CTPrice_Notifier.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.commercetools.api.models.customer.Customer;

public interface CustomerService {

	CompletableFuture<Customer> getCustomerByEmail(String email) ;
}
