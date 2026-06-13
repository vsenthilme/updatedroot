package com.courier.overc360.api.midmile.replica.model.dto;

import java.util.Date;

public interface PreAlertManifestImpl {

    String getLanguageId();
    String getCompanyId();
    String getPartnerId();
    String getMasterAirwayBill();
    String getHouseAirwayBill();
    String getPieceId();
    String getPieceItemId();
    String getQuantity();
    String getUnitValue();
    String getCurrency();
    String getImageReferenceId();
    String getLanguageDescription();
    String getCompanyName();
    String getPartnerType();
    String getPartnerName();
    String getPartnerMasterAirwayBill();
    String getPartnerHouseAirwayBill();
    String getDescription();
    Long getConsignmentId();
    String getItemCode();
    String getHsCode();
    String getDeclaredValue();
    String getCodAmount();
    String getLength();
    String getDimensionUnit();
    String getWidth();
    String getHeight();
    String getWeight();
    String getWeightUnit();
    String getVolume();
    String getVolumeUnit();
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
    String getReferenceField11();
    String getReferenceField12();
    String getReferenceField13();
    String getReferenceField14();
    String getReferenceField15();
    String getReferenceField16();
    String getReferenceField17();
    String getReferenceField18();
    String getReferenceField19();
    String getReferenceField20();
    Long getConsoleIndicator();
    Long getManifestIndicator();
    Long getPreAlertValidationIndicator();
    String getCreatedBy();
    Date getCreatedOn();
    String getUpdatedBy();
    Date getUpdatedOn();

    String getEventCode();
    String getEventText();
    String getStatusId();
    String getStatusDescription();
    String getIncoTerms();
    String getPaymentType();

}