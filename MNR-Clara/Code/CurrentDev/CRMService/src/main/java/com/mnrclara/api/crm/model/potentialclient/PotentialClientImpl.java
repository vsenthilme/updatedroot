package com.mnrclara.api.crm.model.potentialclient;


import java.util.Date;

public interface PotentialClientImpl {

     String getClassId();
     String getPotentialClientId();
     String getFirstNameLastName();
     String getAddressLine1();
     String getEmailId();
     String getContactNumber();
     Long getReferralId();
     String getReferralIdDesc();
     String getSocialSecurityNo();
     Long getStatusId();
     String getReferenceField2();	// Followup by
     String getReferenceField3();	// onboarding Status
     String getReferenceField4();	// Consulting Attorney
     Date getClientCreatedDate();
     Date getProspectiveFileDate();
     String getInquiryNumber();
     String getReferralDescription();

     String getDateString();

}
