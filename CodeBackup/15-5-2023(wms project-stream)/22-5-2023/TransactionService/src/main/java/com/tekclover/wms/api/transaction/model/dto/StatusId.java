package com.tekclover.wms.api.transaction.model.dto;

import lombok.Data;

import java.util.Date;

@Data

	public class StatusId { 

	private String languageId;
	private String companyCodeId;
	private String plantId;
	private String warehouseId;
	private Long statusId;
	private String status;
	private Long deletionIndicator;
	private String createdBy;
	private Date createdOn;
	private String updatedBy;
	private Date updatedOn;
}
