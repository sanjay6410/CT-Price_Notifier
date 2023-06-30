package com.CTPrice_Notifier.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CTPrice_Notifier.Dao.CustomerLoginDao;
import com.commercetools.api.models.customer.Customer;
import com.commercetools.api.models.customer.CustomerSignInResult;

@Service
public class CustomerLoginServiceImpl implements CustomerLoginService{


	
	@Autowired
	private CustomerLoginDao customerLoginDao;
	
	@Override
	public CustomerSignInResult login(Customer customer) {
		return customerLoginDao.login(customer);
		 
	}


	

}
