package com.CTPrice_Notifier.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.CTPrice_Notifier.Exception.ProductNotFoundException;
import com.CTPrice_Notifier.Service.ProductPriceService;
import com.commercetools.api.models.common.Price;

@RestController
public class ProductPriceController {

	@Autowired
	private ProductPriceService productPriceService;  
	
	@PostMapping("/getPriceForProduct")
	public ResponseEntity<?> getPricesOfProductByCurrencyCode(@RequestParam("sku") String sku, @RequestParam("currencyCode") String currencyCode) {
	   Price productPrice;
		try {
	        productPrice=productPriceService.getPricesOfProductByCurrencyCode(sku, currencyCode);
	    } catch (ProductNotFoundException e) {
//	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error While fetching the price " + e.getMessage());
	    	throw e;
	    }
		return ResponseEntity.ok(productPrice);
	}

}
