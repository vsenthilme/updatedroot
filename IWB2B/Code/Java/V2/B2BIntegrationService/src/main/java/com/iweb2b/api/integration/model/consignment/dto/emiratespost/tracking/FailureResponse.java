package com.iweb2b.api.integration.model.consignment.dto.emiratespost.tracking;

import lombok.Data;

@Data
public class FailureResponse{
	private String type;
	private String title;
	private Long status;
	private String detail;
	private String instance;
	private Errors errors;
	private String additionalProp1;
	private String additionalProp2;
	private String additionalProp3;
}