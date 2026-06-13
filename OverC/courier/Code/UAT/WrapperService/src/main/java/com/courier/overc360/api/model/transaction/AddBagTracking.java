package com.courier.overc360.api.model.transaction;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddBagTracking {

//    private Long consignmentBagId;

    @NotBlank(message = "LanguageId is mandatory")
    private String languageId;

    @NotBlank(message = "CompanyId is mandatory")
    private String companyId;

    private String statusId;

    private String pieceId;

    @NotBlank(message = "PartnerId is mandatory")
    private String partnerId;

    private String partnerType;

    private String partnerName;

    @NotBlank(message = "houseAirwayBill is mandatory")
    private String houseAirwayBill;

    private String partnerMasterAirwayBill;

    private String partnerHouseAirwayBill;

    private String productId;

    private String subProductId;

    private String productName;

    private String subProductName;

    private String hubName;

    private String serviceTypeId;

    private String serviceTypeText;

    private String hubCode;

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


}
