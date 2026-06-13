package com.tekclover.wms.api.enterprise.transaction.model.report;

import lombok.Data;

import java.util.List;

@Data
public class ReceiptConfimationReport {

	private ReceiptHeader receiptHeader;
	private List<Receipt> receiptList;
}