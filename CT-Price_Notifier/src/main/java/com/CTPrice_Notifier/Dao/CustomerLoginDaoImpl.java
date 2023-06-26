package com.CTPrice_Notifier.Dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CTPrice_Notifier.Config.ProjectApiConfig;
import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.models.customer.Customer;
import com.commercetools.api.models.customer.CustomerDraft;
import com.commercetools.api.models.customer.CustomerDraftBuilder;
import com.commercetools.api.models.customer.CustomerSignInResult;
import com.commercetools.api.models.customer.CustomerSignin;
import com.commercetools.api.models.customer.CustomerSigninBuilder;

@Service
public class CustomerLoginDaoImpl implements CustomerLoginDao {

	@Autowired
	private ProjectApiConfig apiConfig;
	
	@Override
	public CustomerSignInResult login(Customer customer) {
		ProjectApiRoot par=apiConfig.createApiClient();
		CustomerSignin customerSignin= CustomerSigninBuilder.of()
		.email(customer.getEmail())
		.password(customer.getPassword())
		.build();
		return par.login().post(customerSignin).executeBlocking().getBody();
	}

	
}
