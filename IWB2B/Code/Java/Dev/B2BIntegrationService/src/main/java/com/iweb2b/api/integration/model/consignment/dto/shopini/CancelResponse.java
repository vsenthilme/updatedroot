package com.iweb2b.api.integration.model.consignment.dto.shopini;

import lombok.Data;

@Data
public class CancelResponse {
	/*
	 * {
		"success":true,
		"msg":"Exception has been successfully updated", "msg_ar":"XXXXXXXXXXXX",
		"status_code":"200", 
		}
	 */
	private Boolean success;
	private String msg;
	private String status_code;
}
