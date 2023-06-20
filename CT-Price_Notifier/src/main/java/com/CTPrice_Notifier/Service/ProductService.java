package com.CTPrice_Notifier.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.ResponseEntity;

import com.CTPrice_Notifier.Model.ProductModel;
import com.commercetools.api.models.product.Product;
import com.commercetools.api.models.product_type.ProductTypePagedQueryResponse;

import io.vrap.rmf.base.client.ApiHttpResponse;

public interface ProductService {

	ResponseEntity<Product> AddProduct(ProductModel product);

	CompletableFuture<ApiHttpResponse<ProductTypePagedQueryResponse>> selectProductTypes();

	List<Product> getAllProducts();

}
