package com.iweb2b.api.integration.model.consignment.dto.shopini;

import java.util.List;

import lombok.Data;

@Data
public class ParcelBox {

	private String awb;
	private String package_description;
	private Double value_of_goods;
	private Long total_quantity;
	private Double weight;
	private String weight_unit;
	private Long length;
	private Long width;
	private Long height;
	private String dimensions_unit;
	private List<ParcelItems> parcel_items;
//	private List<String> parcel_items;
}
