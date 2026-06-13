package com.courier.overc360.api.idmaster.primary.model.codpricelist;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

@Data
public class AddCodPriceList {

    private String languageId;

    private String companyId;

    private String partnerId;

    private Long lineNo;

    private String partnerType;

    private String partnerName;

    private String subPartnerId;

    private String subPartnerName;

    private String rateParameterId;

    private Double rangeFrom;

    private Double rangeTo;

    private Double rate;

    private String rateParameterUnit;

    private String rateUnit;

    private Double roundOff;

    private Double ceilingValue = 0.0;

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

    private String createdBy;

    private Date createdOn = new Date();

    private String updatedBy;

    private Date updatedOn = new Date();
}
