package com.iweb2b.api.integration.model.consignment.dto.emiratespost;

import lombok.Data;

@Data
public class CustomsDeclarations{
	private String reference;
	private String description;
	private String countryOfOrigin;
	private Double weight;
	private Dimensions dimensions;
	private Long quantity;
	private String hsCode;
	private Double value;
	private String currencyCode;
}