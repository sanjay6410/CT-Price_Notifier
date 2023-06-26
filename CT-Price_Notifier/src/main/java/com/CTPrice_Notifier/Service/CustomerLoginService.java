package com.CTPrice_Notifier.Service;

import com.commercetools.api.models.customer.Customer;
import com.commercetools.api.models.customer.CustomerSignInResult;

public interface CustomerLoginService {

	 CustomerSignInResult login(Customer customer);
}
