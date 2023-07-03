package com.CTPrice_Notifier.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.CTPrice_Notifier.Service.PriceService;

@Controller
public class PriceController {

	@Autowired
	private PriceService priceService; 
	
	@GetMapping("/price/getPriceById")
	public void getPriceById(@RequestParam String id,@RequestParam String prodId) {
		priceService.getPriceById(id,prodId);
	}
}
