package com.CTPrice_Notifier.Controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.CTPrice_Notifier.Model.ProductModel;
import com.CTPrice_Notifier.Service.ProductService;
import com.commercetools.api.models.customer.Customer;
import com.commercetools.api.models.product.Product;
import com.commercetools.api.models.product.ProductPagedQueryResponse;
import com.commercetools.api.models.product.ProductProjectionPagedQueryResponse;
import com.commercetools.api.models.product.ProductVariant;
import com.commercetools.api.models.product_type.ProductTypePagedQueryResponse;
import com.commercetools.api.models.shopping_list.ShoppingList;
import com.commercetools.api.models.shopping_list.ShoppingListLineItem;

import io.vrap.rmf.base.client.ApiHttpResponse;

@RestController
public class ProductController {
	
	
	
	@Autowired
	private ProductService productService;
	
	@PostMapping("/addproduct")
	public ResponseEntity<Product> addProduct(@RequestBody ProductModel product) {
	  
	    return productService.addProduct(product);
	    
	    
	}

	
	@GetMapping("/selectProductTypes")
	public CompletableFuture<ApiHttpResponse<ProductTypePagedQueryResponse>> selectProductTypes() {
		return productService.selectProductTypes();
		
	}
	
	@GetMapping("/listProducts")
	public List<Product> getAllProducts(){
		return productService.getAllProducts();
		
	}
	@GetMapping("/listProductsBySku")
	public List<Product> getAllProductsBySku(@RequestParam String sku){
		return productService.getAllProductsBySku(sku);
		
	}
	@GetMapping("/getVariantsOfTheProduct")
	public List<ProductVariant> getVariants(@RequestParam String id){
		return productService.getVariants(id);
	}
	
	@GetMapping("/getAllShoppingListsByProductId")
	public ShoppingListLineItem getAllShoppingListsByProductId(@RequestParam String id,@RequestParam Long varId){
		return productService.getAllShoppingListsByProductId(id,varId);
	}
	
	@GetMapping("/getCustomerDetailsByProductId")
	public List<Customer> getCustomerDetailsByProductId(@RequestParam String id){
		return productService.getCustomerDetailsByProductId(id);
	}
}
