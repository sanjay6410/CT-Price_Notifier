package com.CTPrice_Notifier.Service;

import java.util.List;

import com.commercetools.api.models.common.Price;

public interface PriceService {

	List<Price> getPriceById(String id,String prodId);

}