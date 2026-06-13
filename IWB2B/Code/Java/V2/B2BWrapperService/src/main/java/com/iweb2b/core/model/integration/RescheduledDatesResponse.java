package com.iweb2b.core.model.integration;

import java.util.List;

import lombok.Data;

@Data
public class RescheduledDatesResponse {

	/*
	 * "responseCode": "100",
	 * "responseMessage": "Success",
	 * "addressModificationSupport": true,
	 * "reschedulableDateList": [
	        "2024-03-04",
	        "2024-03-05",
	        "2024-03-06",
	        "2024-03-07",
	        "2024-03-09",
	        "2024-03-10",
	        "2024-03-11"
	    ],
    "timeZone": "GMT+03:00"
	 */
	private String responseCode;
	private String responseMessage;
	private Boolean addressModificationSupport;
	private List<String> reschedulableDateList;
	private String timeZone = "GMT+03:00";
}
