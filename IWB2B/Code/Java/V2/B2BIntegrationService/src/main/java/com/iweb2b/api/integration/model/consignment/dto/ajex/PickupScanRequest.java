package com.iweb2b.api.integration.model.consignment.dto.ajex;

import lombok.Data;

@Data
public class PickupScanRequest {

	private String referenceNumber;
	private String waybillNumber;
	private String customerAccount;
	private Long eventDate;
	private String event;
	private String notes;
	private String eventTimeZone;
	private String eventCity;
	private String eventCode;
	private String eventCountry;
	private String eventProvince;
	private String eventDistrict;
	private Double eventLat;
	private Double eventLng;
	private String podUrl;
	private String signatureUrl;
	private Long reasonCode;
	private String reason;
	private String poe;
	private String pop;
	private Long rescheduleDate;
	private String reschueduleTimeZone;
	private String codAmount;
}
