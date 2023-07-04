package com.CTPrice_Notifier.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.CTPrice_Notifier.Service.PriceService;
import com.commercetools.api.models.common.Price;

@RestController
public class PriceController {

	@Autowired
	private PriceService priceService; 
	
	@GetMapping("/price/getPriceById")
	public List<Price> getPriceById(@RequestParam String id,@RequestParam String prodId) {
		return priceService.getPriceById(id,prodId);
	}
}
