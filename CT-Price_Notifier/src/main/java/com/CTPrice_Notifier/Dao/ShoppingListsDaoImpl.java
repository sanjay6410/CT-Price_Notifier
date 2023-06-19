package com.CTPrice_Notifier.Dao;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.CTPrice_Notifier.Config.ProjectApiConfig;
import com.commercetools.api.models.shopping_list.ShoppingList;
import com.commercetools.api.models.shopping_list.ShoppingListPagedQueryResponse;

@Component
public class ShoppingListsDaoImpl implements ShoppingListsDao {

	@Autowired
	private ProjectApiConfig apiConfig;
	
	@Override
	public CompletableFuture<ShoppingListPagedQueryResponse> getShoppingLists(){
		return apiConfig.createApiClient().shoppingLists().get().execute().thenApply(t -> t.getBody());
	}
	@Override
	public ShoppingList getShoppingListByCustomerId(String customerId) {
		List<ShoppingList> customerShoppingLists=  apiConfig.createApiClient().shoppingLists().get().withWhere("customer(id=\"" + customerId + "\")").executeBlocking().getBody().getResults();
		Optional<ShoppingList> customerShoppingListOptional=Optional.ofNullable(customerShoppingLists.get(0));
		ShoppingList shoppingList=customerShoppingListOptional.orElseThrow(() -> new RuntimeException("Shopping list not found"));
		return shoppingList;
	}
	
	
}
