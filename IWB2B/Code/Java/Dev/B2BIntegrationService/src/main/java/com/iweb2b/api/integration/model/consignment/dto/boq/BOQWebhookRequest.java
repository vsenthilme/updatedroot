package com.iweb2b.api.integration.model.consignment.dto.boq;

import java.util.List;

import lombok.Data;

@Data
public class BOQWebhookRequest {

	/*
	 * [
		 	{
			  "DSPCode": "ELITE",                //This one will be Hardcoded//
			  "WaybillNumber": "102318293", => reference_number
			  "UpdateCode": "Code033", => type   // Event Type like e.g reachedathub//
			  "UpdateDescription": "Delivery Attempts - 1/2/3", => event_description
			  "UpdateDateTime": "2020-01-10 14:12:16", => event_time
			  "UpdateLocation": "KW", => hub_code
			  "Comments": "test comments", keep null
			  "ProblemCode": "A00" keep null
			}
		]
	 */
	private List<WebhookData> data;
}
