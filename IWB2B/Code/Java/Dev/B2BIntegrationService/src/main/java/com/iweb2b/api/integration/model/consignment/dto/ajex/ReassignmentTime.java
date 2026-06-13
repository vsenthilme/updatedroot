package com.iweb2b.api.integration.model.consignment.dto.ajex;

import lombok.Data;

@Data
public class ReassignmentTime {
	/*
	 * "rescheduled_date": "2023-03-08",
	 * "rescheduled_timeslot_start": "0900",
	 * "rescheduled_timeslot_end": "1800",
	 * "timeZone": "GMT+03:00"
	 */
	private String rescheduled_date;
	private String rescheduled_timeslot_start;
	private String rescheduled_timeslot_end;
	private String timeZone = "GMT+03:00";
}
