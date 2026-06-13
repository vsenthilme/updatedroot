package com.iweb2b.api.integration.model.consignment.dto.emiratespost;

import lombok.Data;

@Data
public class Address{
	private String line1;
	private String line2;
	private String regionCode;
	private String regionName;
	private String city;
	private String cityCode;
	private String state;
	private String countryCode;
	private String zipCode;
	private Point point;
	private String category;
	private String streetName;
	private String landmark;
	private String buildingName;
	private String floorNo;
	private String apartmentNo;
	private String officeId;
	private String countryName;
}