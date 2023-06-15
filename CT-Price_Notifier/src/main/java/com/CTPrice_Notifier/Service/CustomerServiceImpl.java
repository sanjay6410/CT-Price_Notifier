package com.CTPrice_Notifier.Service;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CTPrice_Notifier.Dao.CustomerDao;
import com.commercetools.api.models.customer.Customer;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerDao customerDao;
	
	public CompletableFuture<Customer> getCustomerByEmail(String email)  {
		return customerDao.getCustomerByEmail(email);
	}
}
