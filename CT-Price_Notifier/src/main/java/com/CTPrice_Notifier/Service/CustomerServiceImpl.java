package com.CTPrice_Notifier.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CTPrice_Notifier.Config.ProjectApiConfig;
import com.CTPrice_Notifier.Dao.CustomerDao;
import com.CTPrice_Notifier.Model.CustomerModelSignUp;
import com.commercetools.api.models.common.Address;
import com.commercetools.api.models.common.BaseAddress;
import com.commercetools.api.models.common.BaseAddressBuilder;
import com.commercetools.api.models.customer.Customer;
import com.commercetools.api.models.customer.CustomerChangePassword;
import com.commercetools.api.models.customer.CustomerChangePasswordBuilder;
import com.commercetools.api.models.customer.CustomerCreatePasswordResetToken;
import com.commercetools.api.models.customer.CustomerCreatePasswordResetTokenBuilder;
import com.commercetools.api.models.customer.CustomerResetPassword;
import com.commercetools.api.models.customer.CustomerResetPasswordBuilder;
import com.commercetools.api.models.customer.CustomerToken;
import com.commercetools.api.models.customer.CustomerUpdate;
import com.commercetools.api.models.customer.CustomerUpdateBuilder;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerDao customerDao;
	
	@Autowired
	private ProjectApiConfig apiConfig;
	
	public CompletableFuture<Customer> getCustomerByEmail(String email)  {
		return customerDao.getCustomerByEmail(email);
	}
	
	public String customerSignUp(CustomerModelSignUp customerModelSignUp) {
		return customerDao.customerSignUp(customerModelSignUp);
	}
	
	public String  customerChangePassword(String email,String newPassword,String oldPassword) throws InterruptedException, ExecutionException {
		CompletableFuture<Customer> customerCompletableFuture=customerDao.getCustomerByEmail(email);
		Customer customer=customerCompletableFuture.get();
		CustomerChangePassword changePassword=CustomerChangePasswordBuilder.of()
				.id(customer.getId())
				.version(customer.getVersion())
				.newPassword(newPassword)
				.currentPassword(oldPassword)
				.build();
		apiConfig.createApiClient().customers().password().post(changePassword).execute();
		return "Password changed successfully";
	}
	
	public String customerResetPassword(String email,String newPassword) {
		try {
		CompletableFuture<Customer> customerCompletableFuture=customerDao.getCustomerByEmail(email);
		Customer customer=customerCompletableFuture.get();
		
		CustomerCreatePasswordResetToken createPasswordResetToken=CustomerCreatePasswordResetTokenBuilder.of()
				.email(email)
				.build();
		
		CustomerToken tokenValue=apiConfig.createApiClient().customers().passwordToken().post(createPasswordResetToken).executeBlocking().getBody();
		
		CustomerResetPassword customerResetPassword=CustomerResetPasswordBuilder.of()
				.version(customer.getVersion())
				.tokenValue(tokenValue.getValue())
				.newPassword(newPassword)
				.build();
		
		apiConfig.createApiClient().customers().passwordReset().post(customerResetPassword).execute();
		return "Password reset Successfull";
		
		}catch(Exception e) {
			throw new RuntimeException("Reset Password For Customer Failed "+e.getMessage());
		}
		
	}
	
	public String customerInfoUpdate(String email,LocalDate dob,Address customerAddress) {
		try {
			CompletableFuture<Customer> customerCompletableFuture=customerDao.getCustomerByEmail(email);
			Customer customer=customerCompletableFuture.get();
			
			List<Address> custAddresses = customer.getAddresses();

		    for (Address address : custAddresses) {
		        if (isAddressMatch(address, customerAddress)) {
		            System.out.println("Matched");
		            return "Address already exists";
		        }
		    }

		    BaseAddress newAddress = BaseAddressBuilder.of()
		            .country(customerAddress.getCountry())
		            .streetName(customerAddress.getStreetName())
		            .streetNumber(customerAddress.getStreetNumber())
		            .building(customerAddress.getBuilding())
		            .postalCode(customerAddress.getPostalCode())
		            .city(customerAddress.getCity())
		            .region(customerAddress.getRegion())
		            .state(customerAddress.getState())
		            .company(customerAddress.getCompany())
		            .department(customerAddress.getDepartment())
		            .apartment(customerAddress.getApartment())
		            .pOBox(customerAddress.getPOBox())
		            .build();
			
			CustomerUpdate customerUpdate=CustomerUpdateBuilder.of()
					.version(customer.getVersion())
					.plusActions(t -> t.setDateOfBirthBuilder().dateOfBirth(dob))
					.plusActions(t -> t.addAddressBuilder().address(newAddress))
					.build();
			
			apiConfig.createApiClient().customers().withId(customer.getId()).post(customerUpdate).execute();
			
			return "Profile Updated Successfully";
			
		}catch(Exception e) {
			throw new RuntimeException("Customer Info Updation Failed"+e.getMessage());
		}
		
	}
	private boolean isAddressMatch(Address existingAddress, Address customerAddress) {
	    if (existingAddress.getStreetName() == null || customerAddress.getStreetName() == null) {
	        return false; // Return false if either street name is null
	    }

	    // Perform the rest of the field comparisons
	    return existingAddress.getStreetName().equals(customerAddress.getStreetName())
	            && existingAddress.getStreetNumber().equals(customerAddress.getStreetNumber())
	            && existingAddress.getBuilding().equals(customerAddress.getBuilding())
	            && existingAddress.getPostalCode().equals(customerAddress.getPostalCode())
	            && existingAddress.getCity().equals(customerAddress.getCity())
	            && existingAddress.getRegion().equals(customerAddress.getRegion())
	            && existingAddress.getState().equals(customerAddress.getState())
	            && existingAddress.getCompany().equals(customerAddress.getCompany())
	            && existingAddress.getDepartment().equals(customerAddress.getDepartment())
	            && existingAddress.getApartment().equals(customerAddress.getApartment())
	            && existingAddress.getPOBox().equals(customerAddress.getPOBox());
	}
	
	
}
