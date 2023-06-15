package com.CTPrice_Notifier.Dao;

import java.util.concurrent.CompletableFuture;

import com.commercetools.api.models.customer.Customer;

public interface CustomerDao {

	CompletableFuture<Customer> getCustomerByEmail(String email);
}