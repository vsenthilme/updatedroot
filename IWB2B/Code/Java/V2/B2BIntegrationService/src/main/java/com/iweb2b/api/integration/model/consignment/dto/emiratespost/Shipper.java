package com.iweb2b.api.integration.model.consignment.dto.emiratespost;

import lombok.Data;

@Data
public class Shipper{
	private Contact contact;
	private Address address;
	private String referenceNo1;
	private String referenceNo2;
}