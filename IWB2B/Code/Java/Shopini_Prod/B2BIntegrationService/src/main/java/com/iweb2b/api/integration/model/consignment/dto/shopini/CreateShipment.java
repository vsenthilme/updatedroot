package com.iweb2b.api.integration.model.consignment.dto.shopini;

import lombok.Data;

@Data
public class CreateShipment {

	private String secret_key; 
	private String marchantid;
	private Long from_country_id;
	private Long from_province_id;
	private Long from_city_id;
	private String receiver_code;
	private String receiver_name; // customer_name
	private Long receiver_phone;
	private String receiver_address;
	private Long receiver_country_id;
	private Long receiver_province_id;
	private Long receiver_city_id;
	private String package_notes;
	private String additional_number;
	private String supplier_name;
	private String supplier_phone;
	private String supplier_address;
	private String supplier_country;
	private Double package_price_main;
	private String xls_product_name;
	private Long xls_order_id;
	private String parcel_box;
	private Long track_number;
//	private List<String> parcel_box;
//	private List<ParcelBox> parcel_box;
}
