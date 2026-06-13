package com.mnrclara.wrapper.core.model.report;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SearchMatterListing {

	private List<Long> classId;
	private List<String> clientId;
	private List<Long> caseCategory;
	private List<Long> caseSubCategory;
	private List<String> billingMode;
	private List<String> billingFrequency;
	private List<String> billingFormatCode;
	private List<String> matterNumber;
	private List<String> originatingTimeKeeper;
	private List<String> responsibleTimeKeeper;
	private List<String> assignedTimeKeeper;
	private List<String> legalAssistant;
	private List<String> paralegal;
	private List<String> petitionerName;
	private List<String> corporateName;
	private List<String> timeKeeper;
	private List<Long> statusId;
	private List<String> partner;
	private List<String> timeKeeperStatus;

	private Date fromCaseOpenDate;
	private Date toCaseOpenDate;

	private Date fromCaseFiledDate;
	private Date toCaseFiledDate;

	private String sFromCaseOpenDate;
	private String sToCaseOpenDate;

	private String sFromCaseFiledDate;
	private String sToCaseFiledDate;
}
