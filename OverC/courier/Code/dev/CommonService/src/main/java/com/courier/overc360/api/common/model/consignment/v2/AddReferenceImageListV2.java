package com.courier.overc360.api.common.model.consignment.v2;

import lombok.Data;

import java.util.Date;

@Data
public class AddReferenceImageListV2 {

    private Long imageId;
    private String imageRefId;
    private String languageId;
    private String companyId;
    private String partnerId;
    private String masterAirwayBill;
    private String houseAirwayBill;
    private Long consignmentId;
    private String pieceId;
    private String pieceItemId;
    private String languageDescription;
    private String companyName;
    private String partnerType;
    private String partnerName;
    private String imageRef;
    private String partnerMasterAirwayBill;
    private String partnerHouseAirwayBill;
    private String referenceImageUrl;
    private Long deletionIndicator;
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
    private String createdBy;
    private Date createdOn;
    private String updatedBy;
    private Date updatedOn;
}