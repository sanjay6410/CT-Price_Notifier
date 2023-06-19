package com.CTPrice_Notifier.Controller;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.CTPrice_Notifier.Model.ShoppingListModel;
import com.CTPrice_Notifier.Service.ShoppingListsService;
import com.commercetools.api.models.shopping_list.ShoppingList;
import com.commercetools.api.models.shopping_list.ShoppingListPagedQueryResponse;

@RestController
public class ShoppingListsController {

	@Autowired
	private ShoppingListsService shoppingListsService;
	
	@GetMapping("/getAllShoppingLists")
	public CompletableFuture<ShoppingListPagedQueryResponse> getAllShoppingLists(){
		return shoppingListsService.getAllShoppingLists();
	}
	
	@GetMapping("/getShoppingListByCustomerId")
	public ShoppingList getShoppingListByCustomerId(@RequestParam("customerId") String customerId) {
		return shoppingListsService.getShoppingListByCustomerId(customerId);
	}
	
	@PostMapping("/createShoppingLists")
	public ResponseEntity<?> createShoppingLists(@RequestParam("custId") String custId,@RequestParam("sku")  String sku,@RequestBody ShoppingListModel shoppingListModel){
		try {
			return ResponseEntity.ok(shoppingListsService.createShoppingLists(custId, sku, shoppingListModel));
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Shopping Lists Not Created "+e.getMessage());
		}
	}
	
	@PostMapping("/updateShoppingListAddLineItem")
	public ResponseEntity<?> updateShoppingListAddLineItem(String customerId,String sku,Long quantity,int percentageNumber){
		try {
			return ResponseEntity.ok(shoppingListsService.updateShoppingListAddLineItem(customerId, sku, quantity, percentageNumber));
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Product Not Added To Shopping Lists "+e.getMessage());
		}
	}
	
	@PostMapping("/updateShoppingListRemoveLineItem")
	public ResponseEntity<?> updateShoppingListRemoveLineItem(String customerId,String lineItemId){
		try {
			return ResponseEntity.ok(shoppingListsService.updateShoppingListRemoveLineItem(customerId, lineItemId));
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Product Not Removed To Shopping Lists "+e.getMessage());
		}
	}
	
}
