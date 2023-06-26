package com.CTPrice_Notifier.Dao;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.CTPrice_Notifier.Config.ProjectApiConfig;
import com.CTPrice_Notifier.Model.CustomerModelSignUp;
import com.commercetools.api.models.customer.Customer;
import com.commercetools.api.models.customer.CustomerDraft;
import com.commercetools.api.models.customer.CustomerDraftBuilder;
import com.commercetools.api.models.customer.CustomerPagedQueryResponse;
import com.commercetools.api.models.type.CustomFieldsDraft;
import com.commercetools.api.models.type.CustomFieldsDraftBuilder;
import com.commercetools.api.models.type.TypeResourceIdentifier;
import com.commercetools.api.models.type.TypeResourceIdentifierBuilder;

@Component
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
	
	public CustomerPagedQueryResponse getCustomerByEmailWithoutCompletableFuture(String email) {
		return apiConfig.createApiClient().customers().get().withWhere("email=\""+email +"\"").executeBlocking().getBody();
	}
	
	
	public Customer getCustomerById(String custId) {
		return apiConfig.createApiClient().customers().withId(custId).get().executeBlocking().getBody();
	}
	
	public String customerSignUp(CustomerModelSignUp customerSignUp){
		
		TypeResourceIdentifier resourceIdentifier=TypeResourceIdentifierBuilder.of()
				.key("CustomerMobileNumber")
				.build();
		
		CustomFieldsDraft customFieldsDraft=CustomFieldsDraftBuilder.of()
				.type(resourceIdentifier)
				//FieldContainerBuilder
				.fields(t -> t.addValue("Customer-mobile-number", customerSignUp.getMobileNumber()))
				.build();
		
		CustomerDraft customerDraft=CustomerDraftBuilder.of()
				.firstName(customerSignUp.getFirstName())
				.lastName(customerSignUp.getLastName())
				.email(customerSignUp.getEmail())
				.password(customerSignUp.getPassword())
				.custom(customFieldsDraft)
				.build();
		apiConfig.createApiClient().customers().post(customerDraft).execute()
		   .thenApply(t -> {
			   return t.getBody();
		   });
		return "Customer Created";
		
	}
	
}
