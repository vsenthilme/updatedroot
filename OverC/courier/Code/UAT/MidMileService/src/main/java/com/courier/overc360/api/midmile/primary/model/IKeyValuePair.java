package com.courier.overc360.api.midmile.primary.model;

import java.util.Date;

public interface IKeyValuePair {

    String getLangId();

    String getLangDesc();

    String getCompanyDesc();

    String getProductId();

    String getProductName();

    String getSubProductId();

    String getSubProductName();

    String getSubCustomerId();

    String getSubCustomerName();

    String getHubDesc();

    String getStatusDesc();

    String getCurrencyValue();

    String getCurrencyId();

    Double getIataKd();

    String getStatusId();

    String getConsignorName();

    String getCustomerName();

    String getStatusText();

    String getEventCode();

    String getEventText();

    String getLoadType();

    String getServiceTypeText();

    String getPieceId();

    String getInvoiceType();

    String getInvoiceNumber();

    Date getInvoiceDate();

    String getType();

    String getTypeText();

    String getNotificationText();

    String getUserName();

    String getUserRole();

    Long getShipment();

    Double getConsignmentValue();

    String getPartnerName();
    String getPartnerId();
    String getAddDest();
    String getAddOrigin();

    Long getCountHawb();

    String getPartnerMasterAB();

    String getCompanyId();

    Long getConsignmentId();

    String getPaymentType();

    Double getSpecialApprovalCharge();

    String getCountryName();
    String getCityName();
    String getDistrictName();
    String getProvinceName();

}
