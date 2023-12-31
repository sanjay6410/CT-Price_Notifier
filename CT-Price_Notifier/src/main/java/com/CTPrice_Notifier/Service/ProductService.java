package com.CTPrice_Notifier.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.ResponseEntity;

import com.CTPrice_Notifier.Model.ProductModel;
import com.commercetools.api.models.customer.Customer;
import com.commercetools.api.models.product.Product;
import com.commercetools.api.models.product.ProductPagedQueryResponse;
import com.commercetools.api.models.product.ProductProjectionPagedQueryResponse;
import com.commercetools.api.models.product.ProductVariant;
import com.commercetools.api.models.product_type.ProductTypePagedQueryResponse;
import com.commercetools.api.models.shopping_list.ShoppingList;
import com.commercetools.api.models.shopping_list.ShoppingListLineItem;

import io.vrap.rmf.base.client.ApiHttpResponse;

public interface ProductService {

	ResponseEntity<Product> addProduct(ProductModel product);

	CompletableFuture<ApiHttpResponse<ProductTypePagedQueryResponse>> selectProductTypes();

	List<Product> getAllProducts();

	List<ProductVariant> getVariants(String id);

	List<Product> getAllProductsBySku(String sku);

	ShoppingListLineItem getAllShoppingListsByProductId(String id,Long variantId);

	List<Customer> getCustomerDetailsByProductId(String id);


}
