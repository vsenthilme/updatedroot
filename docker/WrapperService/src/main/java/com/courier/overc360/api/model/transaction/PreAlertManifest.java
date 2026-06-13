package com.courier.overc360.api.model.transaction;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PreAlertManifest {

    private String languageId;
    private Long consignmentId;

    private String companyId;

    private String partnerId;

    private String quantity;

    private String unitValue;

    private String currency;

    private String masterAirwayBill;

    private String houseAirwayBill;

    private String pieceId;

    private String pieceItemId;

    private String partnerMasterAirwayBill;

    private String partnerHouseAirwayBill;

    private String partnerType;

    private String partnerName;

    private String itemCode;

    private String hsCode;

    private String declaredValue;

    private String codAmount;

    private String length;

    private String dimensionUnit;

    private String width;

    private String height;

    private String weight;

    private String weightUnit;

    private String volume;

    private String volumeUnit;

    private String imageReferenceId;

    private String description;

    private String referenceField1;

    private String referenceField2;

    private String referenceField3;

    private String referenceField4;

    private String referenceField5;

    private String referenceField6;

    private String referenceField7;

    private String referenceField8;

    private String referenceField9;

    private String referenceField10;

    private String referenceField11;

    private String referenceField12;

    private String referenceField13;

    private String referenceField14;

    private String referenceField15;

    private String referenceField16;

    private String referenceField17;

    private String referenceField18;

    private String referenceField19;

    private String referenceField20;

    private String eventCode;
    private String eventText;
    private String statusId;
    private String statusDescription;
    private String incoTerms;
    private String paymentType;

    private Long consoleIndicator;
    private Long manifestIndicator;
    private Long preAlertValidationIndicator;
    private String createdBy;
    private Date createdOn;
    private String updatedBy;
    private Date updatedOn;

}
