package com.CTPrice_Notifier.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.CTPrice_Notifier.Model.ShoppingListModel;
import com.commercetools.api.models.shopping_list.ShoppingList;
import com.commercetools.api.models.shopping_list.ShoppingListPagedQueryResponse;

public interface ShoppingListsService {

	CompletableFuture<ShoppingListPagedQueryResponse> getAllShoppingLists();

    String createShoppingLists(String customerId, String sku,
			ShoppingListModel shoppingListModel);

	ShoppingList getShoppingListByCustomerId(String customerId);

	List<String> updateShoppingListAddLineItem(String customerId, String sku, Long quantity,int percentageNumber);

	String updateShoppingListRemoveLineItem(String customerId, String lineItemId);

	

}