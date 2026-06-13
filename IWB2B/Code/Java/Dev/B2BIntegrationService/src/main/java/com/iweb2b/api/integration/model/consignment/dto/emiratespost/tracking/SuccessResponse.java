package com.iweb2b.api.integration.model.consignment.dto.emiratespost.tracking;

import lombok.Data;

import java.util.List;

@Data
public class SuccessResponse {
	private String trackingNumber;
	private String trackingReferenceNo;
	private Sender sender;
	private Receiver receiver;
	private LastStatus lastStatus;
	private List<EventsItem> events;
}