package com.CTPrice_Notifier.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CTPrice_Notifier.Config.ProjectApiConfig;
import com.commercetools.api.models.common.Price;
import com.commercetools.api.models.product.Product;
import com.commercetools.api.models.product.ProductPagedQueryResponse;
import com.commercetools.api.models.product.ProductVariant;

@Service
public class ProductPriceServiceImpl implements ProductPriceService {

	@Autowired
	private ProjectApiConfig apiRoot;
	
	@Override
	public Price getPricesOfProductByCurrencyCode(String sku, String currencyCode){
		try {
	        CompletableFuture<ProductPagedQueryResponse> listOfProducts = apiRoot.createApiClient()
	                .products().get().withWhere("masterData(current(masterVariant(sku = :sku)) or current(variants(sku = :sku)))").withPredicateVar("sku", sku)
	                .execute()
	                .thenApply(t -> t.getBody());

	        Optional<Product> productOptional = Optional.ofNullable(listOfProducts.get().getResults().get(0));
	        Product product = productOptional.orElseThrow(() -> new RuntimeException("Product Not Found"));
//	        System.out.println(product);

	        List<Price> productPricesMasterVariant = product.getMasterData().getCurrent().getMasterVariant().getPrices();
	        List<ProductVariant> productVariants = product.getMasterData().getCurrent().getAllVariants();
	        List<Price> variantPrices = new ArrayList<>();
	        List<Price> usPrices = new ArrayList<>();
	        List<Price> dePrices = new ArrayList<>();

	        for (ProductVariant productVariant : productVariants) {
	            variantPrices.add(productVariant.getPrice());
	        }
	        variantPrices.addAll(productPricesMasterVariant);

	        for (Price price : variantPrices) {
	            if (price != null) {
	                if (price.getValue().getCurrencyCode().equals("USD")) {
	                    usPrices.add(price);
	                } else if (price.getValue().getCurrencyCode().equals("EUR")) {
	                    dePrices.add(price);
	                }
	            }
	        }
//	        System.out.println(usPrices.get(0).getValue().getCurrencyCode());
	        Price usLastPrice=null;
	        Price deLastPrice=null;
	        for(Price usdPrices:usPrices) {
	        	//System.out.println(usdPrices.getValue());
	        	if(usdPrices.getCountry() != null) {
//	        		System.out.println(usdPrices.getCountry());
	        	if(usdPrices.getCountry().equals("US")) {
	        		usLastPrice=usdPrices;
	        		break;
	        	}
	        	}
	        }
	        for(Price eurPrices:dePrices) {
//	        	System.out.println(eurPrices.getValue());
	        	if(eurPrices.getCountry() != null) {
	        	if(eurPrices.getCountry().equals("DE")) {
	        		deLastPrice=eurPrices;
	        		break;
	        	}
	        	}
	        }
	        
	        if(currencyCode.equals("USD")) {
	        	return usLastPrice;
	        }else if(currencyCode.equals("EUR")) {
	        	return deLastPrice;
	        }else {
	        	return null;
	        }
		}catch(Exception e) {
			throw new RuntimeException("Excetion while retriving the price of the product "+e.getMessage());
		} 
	}

}
