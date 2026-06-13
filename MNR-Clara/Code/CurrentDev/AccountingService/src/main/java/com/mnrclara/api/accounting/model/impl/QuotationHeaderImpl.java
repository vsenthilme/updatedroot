package com.mnrclara.api.accounting.model.impl;

import java.util.Date;

public interface QuotationHeaderImpl {

     String getLanguageId();
     Long getClassId();
     String getMatterNumber();
     String getClientId();
     Long getCaseCategoryId();
     Long getCaseSubCategoryId();
     String getQuotationNo();
     Long getQuotationRevisionNo();
     String getFirstNameLastName();
     String getCorporation();
     Date getQuotationDate();
     Double getQuotationAmount();
     String getCurrency();
     Date getDueDate();
     String getPaymentPlanNumber();
     String getTermDetails();
     Date getSentDate();
     Date getApprovedDate();
     Long getStatusId();
     Long getDeletionIndicator();
     String getReferenceField1();
     String getReferenceField2();
     String getReferenceField3();
     String getReferenceField4();
     String getReferenceField5();
     String getReferenceField6();
     String getReferenceField7();
     String getReferenceField8();
     String getReferenceField9();
     String getReferenceField10();
     String getCreatedBy();
     Date getCreatedOn();
     String getUpdatedBy();
     Date getUpdatedOn();
     Long getNotificationStatus();
}