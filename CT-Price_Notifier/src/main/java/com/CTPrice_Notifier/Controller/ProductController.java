package com.CTPrice_Notifier.Controller;

import java.util.Locale;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.CTPrice_Notifier.Config.ProjectApiConfig;
import com.CTPrice_Notifier.Model.ProductModel;
import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.models.common.LocalizedString;
import com.commercetools.api.models.product.Product;
import com.commercetools.api.models.product.ProductDraft;
import com.commercetools.api.models.product.ProductDraftBuilder;
import com.commercetools.api.models.product.ProductVariantDraft;
import com.commercetools.api.models.product.ProductVariantDraftBuilder;
import com.commercetools.api.models.product_type.ProductTypePagedQueryResponse;
import com.commercetools.api.models.product_type.ProductTypeResourceIdentifier;
import com.commercetools.api.models.product_type.ProductTypeResourceIdentifierBuilder;

import io.vrap.rmf.base.client.ApiHttpException;
import io.vrap.rmf.base.client.ApiHttpResponse;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ProductController {
	
	@Autowired
	private ProjectApiConfig apiConfig;
	
	@PostMapping("/addproduct")
	public ResponseEntity<Product> getProduct(@RequestBody ProductModel product) {
	    ProjectApiRoot par = apiConfig.createApiClient();

	    ProductTypeResourceIdentifier identifier = ProductTypeResourceIdentifierBuilder.of()
	            .id(product.getProductType().getId())
	            .build();

	    ProductVariantDraft productVariantDraft = ProductVariantDraftBuilder.of()
	            .sku(product.getSku())
	            .key(product.getVariantKey())
	            // .prices(priceDraft)
	            .build();

	    LocalizedString description = LocalizedString.of(Locale.ENGLISH, product.getDescription());
	    LocalizedString name = LocalizedString.of(Locale.ENGLISH, product.getName());
	    LocalizedString slug = LocalizedString.of(Locale.ENGLISH, product.getSlug());

	    ProductDraft productDraft = ProductDraftBuilder.of()
	            .productType(identifier)
	            .description(description)
	            .name(name)
	            .slug(slug)
	            .masterVariant(productVariantDraft)
	            .build();

	    try {
	        Product createdProduct = par.products().post(productDraft).executeBlocking().getBody();
	        return ResponseEntity.ok(createdProduct);
	    } catch (ApiHttpException exception) {
	        // Handle the exception and construct an appropriate error response
	        return ResponseEntity.status(exception.getStatusCode()).body(null);
	    }
	}

	
	@GetMapping("/selectProductTypes")
	public CompletableFuture<ApiHttpResponse<ProductTypePagedQueryResponse>> selectProductTypes() {
		ProjectApiRoot par=apiConfig.createApiClient();
		return par.productTypes().get().execute();
	}

}
