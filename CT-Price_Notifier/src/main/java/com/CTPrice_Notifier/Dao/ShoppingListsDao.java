package com.CTPrice_Notifier.Dao;

import java.util.concurrent.CompletableFuture;

import com.commercetools.api.models.shopping_list.ShoppingList;
import com.commercetools.api.models.shopping_list.ShoppingListPagedQueryResponse;

public interface ShoppingListsDao {

	CompletableFuture<ShoppingListPagedQueryResponse> getShoppingLists();

	ShoppingList getShoppingListByCustomerId(String customerId);

}