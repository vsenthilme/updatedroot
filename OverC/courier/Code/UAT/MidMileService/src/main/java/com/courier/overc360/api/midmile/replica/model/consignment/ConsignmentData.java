package com.courier.overc360.api.midmile.replica.model.consignment;

import java.util.Date;

public interface ConsignmentData {
        String getConsignmentId();
        String getLanguageId();
        String getLanguageDescription();
        String getCompanyId();
        String getCompanyName();
        String getPartnerId();
        String getPartnerName();
        String getMasterAirwayBill();
        String getHouseAirwayBill();
        String getNoOfPieceHawb();
        String getPartnerMasterAirwayBill();
        String getPartnerHouseAirwayBill();
        String getProductId();
        String getProductName();
        String getSubProductId();
        String getSubProductName();
        String getServiceTypeId();
        String getServiceTypeText();
        String getIncoTerms();
        String getCustomerReferenceNumber();
        String getHawbType();
        String getHawbTypeId();
        Date getHawbTimeStamp();
        String getHawbTypeDescription();
        String getAddOriginDetails();
        String getAddDestinationDetails();
        String getPaymentType();
        String getCreatedBy();
        String getCreatedOn();
}
