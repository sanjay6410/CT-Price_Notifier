package com.CTPrice_Notifier.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CTPrice_Notifier.Config.ProjectApiConfig;
import com.CTPrice_Notifier.Dao.RegisterDao;
import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.models.customer.Customer;
import com.commercetools.api.models.customer.CustomerDraft;
import com.commercetools.api.models.customer.CustomerDraftBuilder;
import com.commercetools.api.models.customer.CustomerSignInResult;
import com.commercetools.api.models.customer.CustomerSignin;

@Service
public class RegisterServiceImpl implements RegisterService{


	
	@Autowired
	private RegisterDao registerDao;
	
	@Override
	public CustomerSignInResult login(Customer customer) {
		return registerDao.login(customer);
		 
	}


	

}
