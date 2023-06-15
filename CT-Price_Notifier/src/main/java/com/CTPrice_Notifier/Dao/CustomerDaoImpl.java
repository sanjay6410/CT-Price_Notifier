package com.CTPrice_Notifier.Dao;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CTPrice_Notifier.Config.ProjectApiConfig;
import com.commercetools.api.models.customer.Customer;

@Service
public class CustomerDaoImpl implements CustomerDao {

	@Autowired
	private ProjectApiConfig apiConfig;
	
	public CompletableFuture<Customer> getCustomerByEmail(String email) {
		return apiConfig.createApiClient().customers().get().withWhere("email=\""+email +"\"").execute()
				.thenApply(t -> {
					Optional<Customer> customerOptional = Optional.ofNullable(t.getBody().getResults().get(0));
	                Customer customer = customerOptional.orElseThrow(() -> new RuntimeException("Customer not found"));
	                return customer;
				});
	}
}
