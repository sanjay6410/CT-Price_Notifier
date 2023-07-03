package com.CTPrice_Notifier.Service;

import com.commercetools.api.models.common.Price;

public interface ProductPriceService {

	Price getPricesOfProductByCurrencyCode(String sku, String currencyCode);

}