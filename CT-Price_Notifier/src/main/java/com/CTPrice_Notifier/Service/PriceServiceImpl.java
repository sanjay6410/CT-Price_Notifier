package com.CTPrice_Notifier.Service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CTPrice_Notifier.Config.ProjectApiConfig;
import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.models.common.Price;
import com.commercetools.api.models.product.Product;
import com.commercetools.api.models.product.ProductVariant;

@Service
public class PriceServiceImpl implements PriceService {

	@Autowired
	private ProjectApiConfig apiConfig;
	
	@Override
	public List<Price> getPriceById(String priceid, String prodId) {
		ProjectApiRoot par = apiConfig.createApiClient();
		Product product=par.products().withId(prodId).get().executeBlocking().getBody();
		System.out.println(product.getId());
		  List<Price> productPricesMasterVariant = product.getMasterData().getCurrent().getMasterVariant().getPrices();
	        List<ProductVariant> productVariants = product.getMasterData().getCurrent().getAllVariants();
	        List<Price> variantPrices = new ArrayList<>();
	        for (ProductVariant productVariant : productVariants) {
	            variantPrices.addAll(productVariant.getPrices());
	        }
	        variantPrices.addAll(productPricesMasterVariant);

	     System.out.println("...."+variantPrices.get(0).getId());
	     List<Price> matchedVariants=new ArrayList<>();
	     for (Price price : variantPrices) {
	    	    if( price.getCountry()==null && price.getId().equals(priceid) && price.getCustomerGroup()==null) {
	    	        System.out.println("Matched!");
	    	        matchedVariants.add(price);
	    	       
	    	    } else {
	    	        System.out.println("Not matched!");
	    	        // Perform actions for the unmatched price
	    	    }
	    	}


	     return matchedVariants;

		
	}
}
