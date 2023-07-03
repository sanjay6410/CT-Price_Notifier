package com.CTPrice_Notifier.Service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CTPrice_Notifier.Config.ProjectApiConfig;
import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.models.common.Price;
import com.commercetools.api.models.product.Product;

@Service
public class PriceServiceImpl implements PriceService {

	@Autowired
	private ProjectApiConfig apiConfig;
	
	@Override
	public void getPriceById(String id, String prodId) {
		ProjectApiRoot par = apiConfig.createApiClient();
		Product product=par.products().withId(prodId).get().executeBlocking().getBody();
		System.out.println(product.getId());
		List<Price> prices = product.getMasterData().getCurrent().getMasterVariant().getPrices();
		for (Price price : prices) {
			if (price.getId().equals(id) && price.getCountry().equals("Any") && price.getCustomerGroup().getObj().equals("Any")) {
			   System.out.println("matched");
			}

			else {
				System.out.println("unmatched");
			}
		}

		
	}
}
