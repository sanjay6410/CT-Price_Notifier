package com.CTPrice_Notifier.Service;

import com.commercetools.api.models.customer.Customer;
import com.commercetools.api.models.customer.CustomerSignInResult;

public interface RegisterService {

	 CustomerSignInResult login(Customer customer);



}
