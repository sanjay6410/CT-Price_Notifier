package com.CTPrice_Notifier.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.CTPrice_Notifier.Config.ProjectApiConfig;
import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.models.product.ProductPagedQueryResponse;

@RestController
public class ProductController {
	
	@Autowired
	private ProjectApiConfig apiConfig;
	
	@GetMapping("/getproducts")
	public ProductPagedQueryResponse getProduct(@RequestParam String sku) {
		ProjectApiRoot par=apiConfig.createApiClient();
		return par.products().get()
        .withWhere("masterData(current(variants(sku=:sku)))")
        .withPredicateVar("sku", sku)
        .executeBlocking()
        .getBody();
      
		
	}

}
