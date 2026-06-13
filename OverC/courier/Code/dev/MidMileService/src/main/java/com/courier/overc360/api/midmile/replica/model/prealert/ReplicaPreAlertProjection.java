package com.courier.overc360.api.midmile.replica.model.prealert;

import javax.persistence.Column;
import java.util.Date;

public interface ReplicaPreAlertProjection {

    String getCompanyId();

    String getLanguageId();

    String getPartnerId();

    String getMasterAirwayBill();

    String getHouseAirwayBill();

    String getPartnerHouseAirwayBill();

    String getPartnerMasterAirwayBill();

    String getCompanyName();

    String getLanguageDescription();

    Double getTotalWeight();

    String getFlightNo();

    String getConsoleIndicator();

    Double getConsignmentValueLocal();

    String getManifestIndicator();

    String getFlightName();

    Date getEstimatedTimeOfDeparture();

    Date getEstimatedTimeOfArrival();

    String getNoOfPieces();

    Double getConsignmentValue();

    Double getExchangeRate();

    String getIata();

    Double getCustomsInsurance();

    Double getDuty();

    Double getAddIata();

    Double getAddInsurance();

    Double getCustomsValue();

    Double getCalculatedTotalDuty();

    String getBayanHv();

    String getCurrency();

    String getDescription();

    String getConsigneeName();

    String getShipper();

    String getOrigin();

    String getOriginCode();

    String getHsCode();

    String getPartnerType();

    String getPartnerName();

    String getIncoTerm();

    String getHawbType();

    String getHawbTypeId();

    String getHawbTypeDescription();

    Date getHawbTimeStamp();

    String getConsignmentLocalId();

    String getHubCode();

    String getHubName();

    String getAddOriginDetails();

    String getAddDestinationDetails();

    String getConsigneePhoneNo();

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

    Long getCountHawb();  // Count of houseAirwayBill

    Double getLabours();

    Double getOtherCharges();

    Double getOthers();

    Double getCustomDuty();

    Double getSpecialApprovals();

    Double getApprovals();

    Double getTotalCostPerShipment();

    Double getTotalValueShipment();

    Double getClearanceCharge();

    Double getHandlingFees();

    Double getStampCharges();

    Double getHandlingFork();

    Double getGlobal();

    Double getNasDelivery();

    String getSubCustomerId();

    String getSubCustomerName();

    String getInvoice();

    String getOriginFlightCountry();

    String getDdpInvoiceNo();

}