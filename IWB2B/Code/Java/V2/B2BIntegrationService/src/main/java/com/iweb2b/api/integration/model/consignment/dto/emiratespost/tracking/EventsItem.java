package com.iweb2b.api.integration.model.consignment.dto.emiratespost.tracking;

import lombok.Data;

@Data
public class EventsItem{
	private String timeStamp;
	private Status status;
	private String locationAr;
	private String locationEn;
	private String url;
}