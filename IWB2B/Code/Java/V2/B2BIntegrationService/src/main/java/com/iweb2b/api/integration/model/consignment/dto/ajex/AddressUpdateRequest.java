package com.iweb2b.api.integration.model.consignment.dto.ajex;

import lombok.Data;

@Data
public class AddressUpdateRequest {

	private String customerReferenceNumber;
	private String uniqueKey;
	private ReceiverAddress data;
}
