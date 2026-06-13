package com.iweb2b.api.integration.model.consignment.dto.shopini;

import lombok.Data;

@Data
public class ShopiniAPIResponse {

	/*
	 * {
	 * 		success: true,
	 * 		msg: Shipment has been successfully updated
	 * 		status_code: 200
	 * 		data: {
	 * 			track_number: xxxx
	 * 		}
	 * 		ar_msg: تمت إضافة الشحنبنجاح
	 *	}
	 */
	private Boolean success;
	private String msg;
	private String ar_msg;
	private Long status_code;
	private ResponseData data;
}
