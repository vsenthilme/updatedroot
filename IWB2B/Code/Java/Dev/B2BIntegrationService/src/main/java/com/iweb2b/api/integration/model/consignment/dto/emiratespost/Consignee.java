package com.iweb2b.api.integration.model.consignment.dto.emiratespost;

import lombok.Data;

@Data
public class Consignee{
	private Address address;
	private Contact contact;
	private String referenceNo1;
	private String referenceNo2;
}