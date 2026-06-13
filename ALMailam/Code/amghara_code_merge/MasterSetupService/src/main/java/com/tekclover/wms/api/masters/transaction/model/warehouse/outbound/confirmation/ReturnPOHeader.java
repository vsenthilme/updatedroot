package com.tekclover.wms.api.masters.transaction.model.warehouse.outbound.confirmation;

import lombok.Data;

@Data
public class ReturnPOHeader { 
	
	private String poNumber;			// REF_DOC_NO
	private String supplierInvoice;
}