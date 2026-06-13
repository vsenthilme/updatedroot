package com.mnrclara.wrapper.core.model.management;

import java.util.List;

import lombok.Data;

@Data
public class MatterTimeTicketTransfer {

	private String fromMatterNumber;
	private String preBillNumber;
	private List<MatterTimeTicket> timeTickets;
	private String toMatterNumber;
}
