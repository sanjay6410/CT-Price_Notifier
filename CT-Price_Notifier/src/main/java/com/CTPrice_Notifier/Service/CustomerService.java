package com.CTPrice_Notifier.Service;

import java.util.concurrent.ExecutionException;

import com.commercetools.api.models.customer.Customer;

public interface CustomerService {

	Customer getCustomerByEmail(String email) throws RuntimeException, InterruptedException, ExecutionException;
}
