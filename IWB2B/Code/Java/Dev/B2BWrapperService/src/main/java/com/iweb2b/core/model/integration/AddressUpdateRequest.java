package com.iweb2b.core.model.integration;

import lombok.Data;

@Data
public class AddressUpdateRequest {

	private String customerReferenceNumber;
	private String uniqueKey;
	private ReceiverAddress data;
}
