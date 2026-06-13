package com.iweb2b.api.integration.model.consignment.dto.shopify;

import lombok.Data;

@Data
public class Order {

	/*
	 *  "id": 5228146065605,
        "currency": "KWD",
        "note": null,
        "total_price": "17.480",
        "total_weight": 0,
        "address1": "Aladan block6 street 12 home16",
        "phone": "66115004",
        "city": "Adan",
        "zip": null,
        "province": "Mubarak Al-Kabeer",
        "country": "Kuwait",
        "address2": null,
        "name": "Maali Almutairi",

	 */
	private Long id;
	private String name;
	private String currency;
	private String note;
	private String total_price;
	private Long total_weight;
	private String address1;
	private String phone;
	private String city;
	private String zip;
	private String province;
	private String country;
	private String address2;
}
