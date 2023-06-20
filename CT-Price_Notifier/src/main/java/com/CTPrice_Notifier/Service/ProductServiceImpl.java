package com.CTPrice_Notifier.Service;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.CTPrice_Notifier.Config.ProjectApiConfig;
import com.CTPrice_Notifier.Model.ProductModel;
import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.models.common.LocalizedString;
import com.commercetools.api.models.product.Product;
import com.commercetools.api.models.product.ProductDraft;
import com.commercetools.api.models.product.ProductDraftBuilder;
import com.commercetools.api.models.product.ProductPagedQueryResponse;
import com.commercetools.api.models.product.ProductProjectionPagedQueryResponse;
import com.commercetools.api.models.product.ProductVariant;
import com.commercetools.api.models.product.ProductVariantDraft;
import com.commercetools.api.models.product.ProductVariantDraftBuilder;
import com.commercetools.api.models.product_type.ProductTypePagedQueryResponse;
import com.commercetools.api.models.product_type.ProductTypeResourceIdentifier;
import com.commercetools.api.models.product_type.ProductTypeResourceIdentifierBuilder;

import io.vrap.rmf.base.client.ApiHttpException;
import io.vrap.rmf.base.client.ApiHttpResponse;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProjectApiConfig apiConfig;

	@Override
	public ResponseEntity<Product> addProduct(ProductModel product) {
		ProjectApiRoot par = apiConfig.createApiClient();
		ProductTypeResourceIdentifier identifier = ProductTypeResourceIdentifierBuilder.of()
				.id(product.getProductType().getId()).build();

		ProductVariantDraft productVariantDraft = ProductVariantDraftBuilder.of()
				.sku(product.getSku())
				.key(product.getVariantKey())
				// .prices(priceDraft)
				.build();

		LocalizedString description = LocalizedString.of(Locale.ENGLISH, product.getDescription());
		LocalizedString name = LocalizedString.of(Locale.ENGLISH, product.getName());
		LocalizedString slug = LocalizedString.of(Locale.ENGLISH, product.getSlug());

		ProductDraft productDraft = ProductDraftBuilder.of().productType(identifier).description(description).name(name)
				.slug(slug).masterVariant(productVariantDraft).build();

		try {
			Product createdProduct = par.products().post(productDraft).executeBlocking().getBody();
			return ResponseEntity.ok(createdProduct);
		} catch (ApiHttpException exception) {
			// Handle the exception and construct an appropriate error response
			return ResponseEntity.status(exception.getStatusCode()).body(null);
		}
	}

	@Override
	public CompletableFuture<ApiHttpResponse<ProductTypePagedQueryResponse>> selectProductTypes() {
		ProjectApiRoot par=apiConfig.createApiClient();
		return par.productTypes().get().execute();
	}

	@Override
	public List<Product> getAllProducts() {
		ProjectApiRoot par=apiConfig.createApiClient();
		return par.products().get().execute()  
				.thenApply(res -> res.getBody().getResults())
                .join();
	    
	}

	@Override
	public List<ProductVariant> getVariants(String id) {
		ProjectApiRoot par=apiConfig.createApiClient();
		return par.products().withId(id).get().execute()
				.thenApply(res->res.getBody().getMasterData().getCurrent().getAllVariants())
				.join();
	}

	@Override
	public List<Product> getAllProductsBySku(String sku) {
		ProjectApiRoot par=apiConfig.createApiClient();
		 List<Product> products = par.products()
                 .get()
                 .withWhere("masterData(current(masterVariant(sku=:sku)))")
                 .withPredicateVar("sku", sku)
                 .executeBlocking()
                 .getBody()
                 .getResults();
		 return products;
	}

}
