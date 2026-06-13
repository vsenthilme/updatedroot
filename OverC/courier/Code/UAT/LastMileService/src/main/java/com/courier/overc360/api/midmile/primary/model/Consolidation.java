package com.courier.overc360.api.midmile.primary.model;

import java.util.Date;

public interface Consolidation {

    Long getConsignmentId();
    Long getConsignmentBagId();
    String getLanguageId();
    String getLanguageDescription();
    String getCompanyId();
    String getCompanyName();
    String getPartnerType();
    String getPartnerId();
    String getPartnerName();
    String getStatusId();
    String getStatusDescription();
    Date getStatusTimeStamp();
    String getPaymentType();
    Double getTotalShipmentWeight();
    String getProductId();
    String getProductName();
    String getServiceTypeId();
    String getServiceTypeText();
    String getHouseAirwayBill();
    String getRemark();
    String getCustomerCode();
    String getCustomerReferenceNumber();
    String getConsignmentType();
    String getMovementType();
    String getLoadType();
    String getDescription();
    Double getCodAmount();
    String getCodFavorOf();
    String getCodCollectionMode();
    String getPriority();
    String getPickupOtp();
    String getRtoOtp();
    String getCurrency();
    String getPickupServiceTime();
    String getPickupTimeSlotStart();
    String getPickupTimeSlotEnd();
    String getHubCode();
    String getInvoiceAmount();
    String getInvoiceUrl();
    String getProductCode();
    String getIsCustomsDeclarable();
    String getPieceId();
    String getPieceCount();
    String getAddress();
    String getLatitude();
    String getLongitude();
    String getSubProductId();
    String getSubProductName();
    String getPickupHubCode();
    String getEmailId();
    String getPickCompanyName();
    String getName();
    String getPhone();
    String getAlternatePhone();
    String getAddressLine1();
    String getAddressLine2();
    String getPincode();
    String getDistrict();
    String getCity();
    String getState();
    String getCountry();

    String getDestEmailId();
    String getDestCompanyName();
    String getDestName();
    String getDestPhone();
    String getDestAlternatePhone();
    String getDestAddressLine1();
    String getDestAddressLine2();
    String getDestPincode();
    String getDestDistrict();
    String getDestCity();
    String getDestState();
    String getDestCountry();
    String getDestLatitude();
    String getDestLongitude();
    String getAddOriginDetails();

    String getAddDestinationDetails();


















}
