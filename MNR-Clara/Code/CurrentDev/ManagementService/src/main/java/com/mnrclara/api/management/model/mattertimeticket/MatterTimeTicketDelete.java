package com.mnrclara.api.management.model.mattertimeticket;

import java.util.List;

import lombok.Data;

@Data
public class MatterTimeTicketDelete {

	private String fromMatterNumber;
	private String preBillNumber;
	private List<MatterTimeTicket> timeTickets;
}
