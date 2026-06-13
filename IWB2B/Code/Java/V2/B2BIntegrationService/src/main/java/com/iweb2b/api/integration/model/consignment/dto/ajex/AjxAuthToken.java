package com.iweb2b.api.integration.model.consignment.dto.ajex;

import lombok.Data;

@Data
public class AjxAuthToken {

	private String grant_type;
	private String client_id;
	private String client_secret;
}
