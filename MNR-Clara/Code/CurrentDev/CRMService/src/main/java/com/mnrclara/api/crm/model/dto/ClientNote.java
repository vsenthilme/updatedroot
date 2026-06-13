package com.mnrclara.api.crm.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ClientNote {

	private String notesNumber;
	private String languageId;
	private Long classId;
	private String clientId;
	private String noteText;
	private String matterNumber;
	private Long noteTypeId;
	private Long statusId;
	private Long deletionIndicator;
	private String referenceField1;
	private String referenceField2;
	private String referenceField3;
	private String referenceField4;
	private String referenceField5;
	private String referenceField6;
	private String referenceField7;
	private String referenceField8;
	private String referenceField9;
	private String referenceField10;
	private String createdBy;
	private Date createdOn;
	private String updatedBy;
	private Date updatedOn;
	/*-------------------ADD_FIELDS------------------------------*/
	private String additionalFields1;
	private String additionalFields2;
	private String additionalFields3;
	private String additionalFields4;
	private String additionalFields5;
	private String additionalFields6;
	private String additionalFields7;
}