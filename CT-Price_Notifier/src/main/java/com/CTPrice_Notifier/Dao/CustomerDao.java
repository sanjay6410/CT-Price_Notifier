package com.CTPrice_Notifier.Dao;

import java.util.concurrent.CompletableFuture;

import com.commercetools.api.models.customer.CustomerPagedQueryResponse;

public interface CustomerDao {

	CompletableFuture<CustomerPagedQueryResponse> getCustomerByEmail(String email);
}