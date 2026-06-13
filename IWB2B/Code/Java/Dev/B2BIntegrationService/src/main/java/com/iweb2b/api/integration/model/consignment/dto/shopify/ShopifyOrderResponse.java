package com.iweb2b.api.integration.model.consignment.dto.shopify;

import java.util.List;

import lombok.Data;

@Data
public class ShopifyOrderResponse {
	private List<Order> orders;
}
