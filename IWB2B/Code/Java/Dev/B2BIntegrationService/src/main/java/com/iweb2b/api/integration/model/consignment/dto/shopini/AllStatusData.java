package com.iweb2b.api.integration.model.consignment.dto.shopini;

import lombok.Data;

import java.util.List;

@Data
public class AllStatusData {

	private String track_number;
	private List<AllShipmentStatus> shipment_status;
	private String shipment_status_date;
}
