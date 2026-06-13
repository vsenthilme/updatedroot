package com.courier.overc360.api.midmile.primary.model.ndr;

import lombok.Data;

import java.util.Date;

@Data
public class UpdateNdr {

    private String languageId;

    private String languageDescription;

    private String companyId;

    private String companyName;

    private String deliveryId;

    private String ndrDescription;

    private String partnerType;

    private String partnerId;

    private String partnerName;

    private String consignmentBagId;

    private String houseAirwayBill;

    private String consignmentId;

    private Long deletionIndicator = 0L;

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

    private Date createdOn = new Date();

    private String updatedBy;

    private Date updatedOn = new Date();

}
