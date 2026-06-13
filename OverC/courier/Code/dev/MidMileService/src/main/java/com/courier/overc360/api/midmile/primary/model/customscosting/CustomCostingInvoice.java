package com.courier.overc360.api.midmile.primary.model.customscosting;

import java.util.Date;

public interface CustomCostingInvoice {

    public String getLanguageId();
    public String getCompanyId();
    public String getLanguageName();
    public Long getCashNumber();
    public String getCostCenter();
    public String getCompanyName();
    public String getPartnerId();
    public Long getLineNumber();
    public Date getDate();
    public String getDepartment();
    public String getCostDescription();
    public Double getCostAmount();
    public String getPartnerName();
    public Boolean getFinance();
    public Long getNoOfShipments();
    public String getInvoiceNumber();
    public String getStatusId();
    public String getStatusDescription();
    public String getSupplierName();

    public String getSubCustomerId();

    public String getSubCustomerName();
    public Date getInvoiceDate();
    public Double getTotalCostAmount();
    public String getReferenceField1();
    public String getReferenceField2();
    public String getReferenceField3();
    public String getReferenceField4();
    public String getReferenceField5();
    public String getApprovedBy();
    public String getCreatedBy();
    public Date getCreatedOn();
    public String getCheckField();
}
