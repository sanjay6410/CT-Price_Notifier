package com.CTPrice_Notifier.Dao;

import com.commercetools.api.models.customer.Customer;
import com.commercetools.api.models.customer.CustomerSignInResult;

public interface RegisterDao {

	CustomerSignInResult login(Customer customer);


}