package com.iweb2b.api.integration.model.consignment.dto.ajex;

import lombok.Data;

@Data
public class AjxAuthTokenResponse {

	private String access_token;
	private Long expires_in;
	private String scope;
}
