package com.CTPrice_Notifier.Dao;

import com.commercetools.api.models.customer.Customer;
import com.commercetools.api.models.customer.CustomerSignInResult;

public interface CustomerLoginDao {

	CustomerSignInResult login(Customer customer);


}