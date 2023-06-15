package com.CTPrice_Notifier.Dao;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CTPrice_Notifier.Config.ProjectApiConfig;
import com.commercetools.api.models.customer.CustomerPagedQueryResponse;

@Service
public class CustomerDaoImpl implements CustomerDao {

	@Autowired
	private ProjectApiConfig apiConfig;
	
	public CompletableFuture<CustomerPagedQueryResponse> getCustomerByEmail(String email) {
		return 
				apiConfig.createApiClient().customers().get()
				.withWhere("email=\""+email+"\"").execute().thenApply(t ->t.getBody());
	}
}
