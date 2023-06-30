package com.CTPrice_Notifier.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.CTPrice_Notifier.Service.ProductPriceService;

@RestController
public class ProductPriceController {

	@Autowired
	private ProductPriceService productPriceService;  
	
	@PostMapping("/getPriceForProduct")
	public ResponseEntity<?> getPricesOfProductByCurrencyCode(String sku,String countryCode) {
		try {
			return ResponseEntity.ok(productPriceService.getPricesOfProductByCurrencyCode(sku, countryCode));
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error While feteching the price "+e.getMessage());
		}
	}
}
