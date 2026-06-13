package com.iweb2b.api.integration.model.consignment.dto.boq;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class BOQWebhookResponse {

	/*
	 * {
		"ResponseCode": 100,
		"ResponseMessage": "Successfully uploaded.",
		"ResponseMessageNew": ""
		}
	 */
	@JsonProperty(value = "ResponseCode")
	private String ResponseCode;
	
	@JsonProperty(value = "ResponseMessage")
	private String ResponseMessage;
	
	@JsonProperty(value = "ResponseMessageNew")
	private String ResponseMessageNew;
}
