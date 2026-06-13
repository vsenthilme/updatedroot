package com.iweb2b.core.model.integration;

import lombok.Data;

@Data
public class RescheduledDatesRequest {

	private String customerReferenceNumber;
	private ReceiverAddress receiverAddress;
}
