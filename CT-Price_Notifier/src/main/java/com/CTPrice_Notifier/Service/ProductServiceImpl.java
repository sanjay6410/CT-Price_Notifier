package com.CTPrice_Notifier.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.CTPrice_Notifier.Config.ProjectApiConfig;
import com.CTPrice_Notifier.Model.ProductModel;
import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.models.common.LocalizedString;
import com.commercetools.api.models.customer.Customer;
import com.commercetools.api.models.customer.CustomerReference;
import com.commercetools.api.models.product.Product;
import com.commercetools.api.models.product.ProductDraft;
import com.commercetools.api.models.product.ProductDraftBuilder;
import com.commercetools.api.models.product.ProductVariant;
import com.commercetools.api.models.product.ProductVariantDraft;
import com.commercetools.api.models.product.ProductVariantDraftBuilder;
import com.commercetools.api.models.product_type.ProductTypePagedQueryResponse;
import com.commercetools.api.models.product_type.ProductTypeResourceIdentifier;
import com.commercetools.api.models.product_type.ProductTypeResourceIdentifierBuilder;
import com.commercetools.api.models.shopping_list.ShoppingList;
import com.commercetools.api.models.shopping_list.ShoppingListLineItem;
import com.commercetools.api.models.shopping_list.ShoppingListPagedQueryResponse;
import com.commercetools.api.models.type.CustomFields;

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
	    ProjectApiRoot par = apiConfig.createApiClient();
	    List<Product> products = par.products()
	        .get()
	        .withWhere("masterData(current(masterVariant(sku = :sku)) or current(variants(sku = :sku)))")
	        .withPredicateVar("sku", sku)
	        .executeBlocking()
	        .getBody()
	        .getResults();
	    return products;
	}

	@Override
	public ShoppingListLineItem getAllShoppingListsByProductId(String prodId,Long variantId) {
		ProjectApiRoot par = apiConfig.createApiClient();
		ShoppingListLineItem foundLineItem = null;
		 ShoppingListPagedQueryResponse shoppingListResponse = par.shoppingLists().get()
	                .withWhere("lineItems(productId=:productId) and lineItems(variantId=:variantId)")
	                .withPredicateVar("productId", prodId)
	                .withPredicateVar("variantId", variantId)
	                .executeBlocking()
	                .getBody();
		// Iterate through each shopping list
		for (ShoppingList shoppingList : shoppingListResponse.getResults()) {
		    List<ShoppingListLineItem> lineItems = shoppingList.getLineItems();
		    for (ShoppingListLineItem lineItem : lineItems) {
		        // Check if the line item matches the productId and variantId
		        if (lineItem.getProductId().equals(prodId)&& lineItem.getVariantId()==variantId) {
		            foundLineItem = lineItem;
		            break; // Exit the loop if the line item is found
		        }
		        else {
		        	System.out.println("lineitem is not present");
		        }
		    }
		    
		    if (foundLineItem != null) {
		        break; // Exit the outer loop if the line item is found in any shopping list
		    }
		}
		 CustomFields customFields = foundLineItem.getCustom();

		    // Retrieve the "Percentage-Number" field
		     Map<String, Object> percent = customFields.getFields().values();
		    
		  System.out.println(percent);
		  return foundLineItem;
	}

	@Override
	public List<Customer> getCustomerDetailsByProductId(String id) {
		ProjectApiRoot par = apiConfig.createApiClient();
		List<ShoppingList> shoppinglists= par.shoppingLists()
			    .get()
			    .withWhere("lineItems(productId=:productId)")
	            .withPredicateVar("productId", id)
			    .executeBlocking()
			    .getBody().getResults();
		List<String> customers=new ArrayList<>();
		for (ShoppingList shoppingList : shoppinglists) {
			customers.add(shoppingList.getCustomer().getId());
		}
		List<Customer> customerDetails=new ArrayList<>();
		for(String customer:customers) {
			customerDetails.add(par.customers().withId(customer).get().executeBlocking().getBody().get());
		}
		return customerDetails;
	}

}
