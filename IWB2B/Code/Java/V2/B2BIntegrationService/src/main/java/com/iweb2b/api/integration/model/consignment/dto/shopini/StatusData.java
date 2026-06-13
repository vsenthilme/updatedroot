package com.iweb2b.api.integration.model.consignment.dto.shopini;

import lombok.Data;

@Data
public class StatusData {

	private String track_number;
//	private List<ShipmentStatus> shipment_status;
	private String shipment_status;
	private String shipment_status_date;
}
