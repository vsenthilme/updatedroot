package com.iweb2b.api.integration.model.consignment.dto.ajex;

import lombok.Data;

@Data
public class RescheduledDatesRequest {

	private String customerReferenceNumber;
	private ReceiverAddress receiverAddress;
}
