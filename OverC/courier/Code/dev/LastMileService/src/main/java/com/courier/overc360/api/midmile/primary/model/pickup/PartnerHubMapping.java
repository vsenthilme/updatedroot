package com.courier.overc360.api.midmile.primary.model.pickup;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

@Data
public class PartnerHubMapping {


    private String partnerId;

    private String partnerType;

    private String languageId;

    private String companyId;

    private String hubCode;

    private String productCode;

    private String defaultHubCode;

    private String transitDC;

    private String finalDC;

    private String hubName;

    private String hubCategory;

    private String partnerName;

    private String languageDescription;

    private String companyName;

    private String statusId;

    private String statusDescription;

    private String remark;

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


}
