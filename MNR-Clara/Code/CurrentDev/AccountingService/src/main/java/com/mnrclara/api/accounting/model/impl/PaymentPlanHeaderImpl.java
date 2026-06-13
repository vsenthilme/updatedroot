package com.mnrclara.api.accounting.model.impl;

import java.util.Date;

public interface PaymentPlanHeaderImpl {

     String getPaymentPlanNumber();
     Long getPaymentPlanRevisionNo();
     String getLanguageId();
     Long getClassId();
     String getMatterNumber();
     String getClientId();
     Date getPaymentPlanDate();
     String getQuotationNo();
     Date getPaymentPlanStartDate();
     Date getEndDate();
     Long getNoOfInstallment();
     Double getPaymentPlanTotalAmount();
     Double getDueAmount();
     Double getInstallmentAmount();
     String getCurrency();
     Long getPaymentCalculationDayDate();
     String getPaymentPlanText();
     Date getSentOn();
     Date getApprovedOn();
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
}