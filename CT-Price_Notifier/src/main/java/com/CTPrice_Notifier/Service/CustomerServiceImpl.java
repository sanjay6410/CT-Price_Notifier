package com.CTPrice_Notifier.Service;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CTPrice_Notifier.Dao.CustomerDao;
import com.commercetools.api.models.customer.Customer;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerDao customerDao;
	
	public Customer getCustomerByEmail(String email) throws RuntimeException, InterruptedException, ExecutionException {
		return customerDao.getCustomerByEmail(email).get().getResults()
	            .stream()
	            .findFirst()
	            .orElseThrow(() -> new RuntimeException("Customer not found for email: " + email));
	}
}
