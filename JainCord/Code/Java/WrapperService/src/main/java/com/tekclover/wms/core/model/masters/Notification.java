package com.tekclover.wms.core.model.masters;

import lombok.Data;

import java.util.Date;

@Data
public class Notification {

	private Long notificationId;
	private String languageId;
	private String companyId;
	private String companyCodeId;
	private String plantId;
	private String warehouseId;
	private String processId;
	private String topic;
	private String message;
	private String userId;
	private String userType;
	private Boolean status = false;
	private Boolean deletionIndicator = false;
	private String createdBy;
    private Date createdOn;
    private String updatedBy;
	private Date updatedOn;

	private String storageBin;
	private boolean newCreated;
	private String documentNumber;
	private String referenceNumber;
}