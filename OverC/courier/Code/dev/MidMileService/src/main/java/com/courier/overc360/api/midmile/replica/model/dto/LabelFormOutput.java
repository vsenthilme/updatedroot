package com.courier.overc360.api.midmile.replica.model.dto;

import java.util.Date;
public interface LabelFormOutput {

    Long getConsignmentId();
    String getCustomerReferenceNumber();
    String getHouseAirwayBill();
    String getCountryOfOrigin();
    String getPartnerName();
    String getIncoTerms();
    String getServiceTypeText();
    Double getTotalDuty();
    String getNoOfPieceHawb();
    String getConsignmentCurrency();
    String getPartnerHouseAirwayBill();
    String getGrossWeight();
    String getModeOfTransport();
    String getInsurance();
    String getLoadType();
    String getCod();
    String getDestinationName();
    String getDestinationPhone();
    String getDestinationAlternatePhone();
    String getDestinationAddress();
    String getDestinationCity();
    String getDestinationState();
    String getDestinationCountry();
    String getOriginName();
    String getOriginPhone();
    String getOriginAlternatePhone();
    String getOriginAddress();
    String getOriginCity();
    String getOriginState();
    String getOriginCountry();
    String getPieceId();
    String getPieceProductCode();
    String getPieceValue();
    String getTags();
    String getGoodsType();
    Date getCreatedOn();

}