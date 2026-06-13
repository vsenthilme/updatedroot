package com.mnrclara.wrapper.core.model.crm;

import lombok.Data;

import java.util.Date;

@Data
public class PotentialClientReport {

	private String classId;
	private String potentialClientId;
	private String firstNameLastName;
	private String addressLine1;
	private String emailId;
	private String contactNumber;
	private Long referralId;
	private String referralIdDesc;
	private String socialSecurityNo;
	private Long statusId;
	
	private String referenceField2;	// Followup by
	
	private String referenceField3;	// onboarding Status

	private String referenceField4;	// Consulting Attorney

	private Date clientCreatedDate;  // ClientCreateDate

	private Date prospectiveFileDate; // ProspectiveFileDate

	private String inquiryNumber;    // Inquiry Number

	private Date consulationDate;     // ConsulationDate

	private String objectiveOfVisit;   // Inquiry table referenceFiled-4
	private String statusText;			// statusText
	private String onBoardingStatusText;  // onboardStatusText

	private String sConsulationDate;
	private String sClientCreatedDate;
	private String sProspectiveFileDate;
}
