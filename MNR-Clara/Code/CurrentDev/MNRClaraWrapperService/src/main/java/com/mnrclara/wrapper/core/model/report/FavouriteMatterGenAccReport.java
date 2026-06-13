package com.mnrclara.wrapper.core.model.report;

import lombok.Data;

@Data
public class FavouriteMatterGenAccReport {

	private String classId;
	private String classDescription;
	private String statusId;
	private String statusDesc;
	private String clientId;
	private String clientName;
	private String matterNumber;
	private String matterDescription;
	private String languageId;
	private String viewedDate;
	private String createdBy;
}