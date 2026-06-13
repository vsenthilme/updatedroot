package com.courier.overc360.api.model.idmaster;

import lombok.Data;

import java.util.Date;

@Data
public class Customer {

    private String customerId;

    private String productId;

    private String subProductId;

    private String companyId;

    private String languageId;

    private String languageDescription;

    private String companyName;

    private String subProductName;

    private String subProductValue;

    private String productName;

    private String customerName;

    private String calculationType;

    private String statusId;

    private Long agingCount;

    private Long billGeneration;

    private String billingFrequency;

    private String customerType;

    private Date billingFrequencyDate1;

    private Date billingFrequencyDate2;

    private Date billingFrequencyDate3;

    private Date billingFrequencyDate4;

    private Date billingFrequencyDate5;

    private String statusDescription;

    private String remark;

    private Long pickupCount;

    private Long deliveryCount;

    private String hubCode;

    private String pickupAddressLine1;

    private String pickupAddressLine2;

    private String district;

    private String phone;

    private String alternatePhone;

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
