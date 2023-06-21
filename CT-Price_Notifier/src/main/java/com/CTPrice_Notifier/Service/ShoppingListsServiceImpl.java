package com.CTPrice_Notifier.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CTPrice_Notifier.Config.ProjectApiConfig;
import com.CTPrice_Notifier.Dao.ShoppingListsDao;
import com.CTPrice_Notifier.Model.ShoppingListModel;
import com.commercetools.api.models.customer.CustomerResourceIdentifier;
import com.commercetools.api.models.customer.CustomerResourceIdentifierBuilder;
import com.commercetools.api.models.product.Product;
import com.commercetools.api.models.product.ProductPagedQueryResponse;
import com.commercetools.api.models.product.ProductVariant;
import com.commercetools.api.models.shopping_list.ShoppingList;
import com.commercetools.api.models.shopping_list.ShoppingListDraft;
import com.commercetools.api.models.shopping_list.ShoppingListDraftBuilder;
import com.commercetools.api.models.shopping_list.ShoppingListLineItem;
import com.commercetools.api.models.shopping_list.ShoppingListLineItemDraft;
import com.commercetools.api.models.shopping_list.ShoppingListLineItemDraftBuilder;
import com.commercetools.api.models.shopping_list.ShoppingListPagedQueryResponse;
import com.commercetools.api.models.shopping_list.ShoppingListUpdate;
import com.commercetools.api.models.shopping_list.ShoppingListUpdateBuilder;
import com.commercetools.api.models.type.CustomFieldsDraft;
import com.commercetools.api.models.type.CustomFieldsDraftBuilder;
import com.commercetools.api.models.type.TypeResourceIdentifier;
import com.commercetools.api.models.type.TypeResourceIdentifierBuilder;

@Service
public class ShoppingListsServiceImpl implements ShoppingListsService {

	@Autowired
	private ProjectApiConfig apiConfig;

	@Autowired
	private ShoppingListsDao shoppingListsDao;

	@Override
	public CompletableFuture<ShoppingListPagedQueryResponse> getAllShoppingLists() {
		return shoppingListsDao.getShoppingLists();
	}

	@Override
	public ShoppingList getShoppingListByCustomerId(String customerId) {
		return shoppingListsDao.getShoppingListByCustomerId(customerId);

	}

	@Override
	public String createShoppingLists(String customerId, String sku, ShoppingListModel shoppingListModel) {

		List<ShoppingList> customerShopping = apiConfig.createApiClient().shoppingLists().get()
				.withWhere("customer(id=\"" + customerId + "\")").executeBlocking().getBody().getResults();
		System.out.println(customerShopping.isEmpty());
		if (customerShopping.isEmpty()) {
			TypeResourceIdentifier typeResourceIdentifier = TypeResourceIdentifierBuilder.of().key("Percentage-Number")
					.build();

			CustomFieldsDraft customFieldsDraft = CustomFieldsDraftBuilder.of().type(typeResourceIdentifier)
					.fields(t -> t.addValue("Percentage-Number", shoppingListModel.getPercentageNumber()))

					.build();

			ShoppingListLineItemDraft shoppingListLineItemDraft = ShoppingListLineItemDraftBuilder.of().sku(sku)
					.quantity(shoppingListModel.getQuantity()).custom(customFieldsDraft).build();

			CustomerResourceIdentifier customerResourceIdentifier = CustomerResourceIdentifierBuilder.of()
					.id(customerId).build();

			ShoppingListDraft shoppingListDraft = ShoppingListDraftBuilder.of()
					.name(t -> t.addValue("en", shoppingListModel.getName()))
					.description(t -> t.addValue("en", shoppingListModel.getDescription()))
					.lineItems(shoppingListLineItemDraft).customer(customerResourceIdentifier)
					.key("my-shopping-list-" + customerId).build();
			apiConfig.createApiClient().shoppingLists().post(shoppingListDraft).executeBlocking().getBody();
			return "Shopping List Added";
		}
		return "Shopping List Already Exists";
		// apiConfig.createApiClient().shoppingLists().post(shoppingListDraft).executeBlocking().getBody();
	}

	@Override
	public List<String> updateShoppingListAddLineItem(String customerId, String sku, Long quantity,
			int percentageNumber) {
		ShoppingList customerShoppingList = shoppingListsDao.getShoppingListByCustomerId(customerId);
		List<String> statuses = new ArrayList<>();
		Product productData = null;

		ProductPagedQueryResponse productSku = apiConfig.createApiClient().products().get()
				.withWhere("masterData(current(masterVariant(sku=\"" + sku + "\")))").executeBlocking().getBody();

		if (productSku.getCount() > 0) {
			productData = productSku.getResults().get(0);
		} else {
			ProductPagedQueryResponse productSkuVariants = apiConfig.createApiClient().products().get()
					.withWhere("masterData(current(variants(sku=\"" + sku + "\")))").executeBlocking().getBody();

			if (productSkuVariants.getCount() > 0) {
				productData = productSkuVariants.getResults().get(0);
			} else {
				statuses.add("Product Not Found");
				return statuses;
			}
		}

		String productId = productData.getId();
		Long variantId = null;
		List<ProductVariant> productVariants = productData.getMasterData().getCurrent().getAllVariants();
		for (ProductVariant productVariant : productVariants) {
			if (productVariant.getSku().equals(sku)) {
				// System.out.println("variants id of input sku "+productVariant.getId());
				variantId = productVariant.getId();
			}
		}

		// System.out.println(variantId);
		List<ShoppingListLineItem> customerShoppingListLineItems = customerShoppingList.getLineItems();
		boolean productExists = false;

		for (ShoppingListLineItem lineItem : customerShoppingListLineItems) {
			if (lineItem.getProductId().equals(productId) && lineItem.getVariantId() == variantId) {
				statuses.add("Product Already Exists");
				productExists = true;
				break;
			}
		}

		if (!productExists) {
			TypeResourceIdentifier typeResourceIdentifier = TypeResourceIdentifierBuilder.of().key("Percentage-Number")
					.build();

			CustomFieldsDraft customFieldsDraft = CustomFieldsDraftBuilder.of().type(typeResourceIdentifier)
					.fields(t -> t.addValue("Percentage-Number", percentageNumber)).build();

			ShoppingListUpdate shoppingListUpdate = ShoppingListUpdateBuilder.of()
					.version(customerShoppingList.getVersion())
					.plusActions(t -> t.addLineItemBuilder().sku(sku).quantity(quantity).custom(customFieldsDraft))
					.build();

			apiConfig.createApiClient().shoppingLists().withId(customerShoppingList.getId()).post(shoppingListUpdate)
					.execute();
			statuses.add("Product Added To Shopping List");
		}

		return statuses;
	}

	@Override
	public String updateShoppingListRemoveLineItem(String customerId, String lineItemId) {
		ShoppingList customerShoppingList = shoppingListsDao.getShoppingListByCustomerId(customerId);

		ShoppingListUpdate shoppingListUpdate = ShoppingListUpdateBuilder.of()
				.version(customerShoppingList.getVersion())
				.plusActions(t -> t.removeLineItemBuilder().lineItemId(lineItemId)).build();
		apiConfig.createApiClient().shoppingLists().withId(customerShoppingList.getId()).post(shoppingListUpdate)
				.execute();
		return "Product Removed From Shopping Lists";

	}

	@Override
	public String updateShoppingListChangePercentageNumber(String customerId, String lineItemId, int percentageNumber) {

		ShoppingList customerShoppingList = shoppingListsDao.getShoppingListByCustomerId(customerId);

		ShoppingListUpdate shoppingListUpdate = ShoppingListUpdateBuilder.of()
				.version(customerShoppingList.getVersion()).plusActions(t -> t.setLineItemCustomFieldBuilder()
						.lineItemId(lineItemId).name("Percentage-Number").value(percentageNumber))
				.build();  

		apiConfig.createApiClient().shoppingLists().withId(customerShoppingList.getId()).post(shoppingListUpdate)
				.execute();
		return "percentage Number Changed Successfully ";
	}
	
	@Override
	public String customerShoppingListsExists(String customerId) {
		 List<ShoppingList> customerShoppingLists = apiConfig.createApiClient().shoppingLists()
		            .get().withWhere("customer(id=\"" + customerId + "\")")
		            .executeBlocking().getBody().getResults();
		if(customerShoppingLists.isEmpty()) {
			return "Not Exists";
		}else {
			return "Exists";
		}
	}


}
