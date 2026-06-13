package com.courier.overc360.api.idmaster.primary.model.customer;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class UpdateCustomer {

    @NotBlank(message = "LanguageId is mandatory")
    private String languageId;

    @NotBlank(message = "CompanyId is mandatory")
    private String companyId;

    @NotBlank(message = "SubProductId is mandatory")
    private String subProductId;

    @NotBlank(message = "SubProduct Value is mandatory")
    private String subProductValue;

    @NotBlank(message = "ProductId is mandatory")
    private String productId;

    @NotBlank(message = "CustomerId is mandatory")
    private String customerId;

    @NotBlank(message = "Customer Name is mandatory")
    private String customerName;

    private String calculationType;

    private Long agingCount;

    private Long billGeneration;

    private String billingFrequency;

    private String customerType;

    private Date billingFrequencyDate1;

    private Date billingFrequencyDate2;

    private Date billingFrequencyDate3;

    private Date billingFrequencyDate4;

    private Date billingFrequencyDate5;

    private String statusId;

    private String remark;

    private Long pickupCount;

    private Long deliveryCount;

    private String hubCode;

    private Long deletionIndicator;

    private String pickupAddressLine1;

    private String pickupAddressLine2;

    private String district;

    private String phone;

    private String alternatePhone;

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
