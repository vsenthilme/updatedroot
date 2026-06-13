package com.iweb2b.api.integration.model.consignment.dto.boq;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class WebhookData {

	@JsonProperty(value = "DSPCode")
	private String DSPCode;
	
	@JsonProperty(value = "WaybillNumber")
	private String WaybillNumber;
	
	@JsonProperty(value = "UpdateCode")
	private String UpdateCode;
	
	@JsonProperty(value = "UpdateDescription")
	private String UpdateDescription;
	
	@JsonProperty(value = "UpdateDateTime")
	private String UpdateDateTime;
	
	@JsonProperty(value = "UpdateLocation")
	private String UpdateLocation;
	
	@JsonProperty(value = "Comments")
	private String Comments;
	
	@JsonProperty(value = "ProblemCode")
	private String ProblemCode;
}
