package com.courier.overc360.api.idmaster.replica.model.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
/*
 * `LANG_ID`, `C_ID`, `CUSTOMER_ID`, `PRODUCT_ID`, `SUB_PRODUCT_ID`, `SUB_PRODUCT_VALUE`
 */
@Table(name = "tblcustomer",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_key_customer",
                        columnNames = {"LANG_ID", "C_ID", "CUSTOMER_ID", "PRODUCT_ID", "SUB_PRODUCT_ID", "SUB_PRODUCT_VALUE"}
                )
        }
)
@IdClass(ReplicaCustomerCompositeKey.class)
public class ReplicaCustomer {

    @Id
    @Column(name = "LANG_ID", columnDefinition = "nvarchar(50)")
    private String languageId;

    @Column(name = "LANG_TEXT", columnDefinition = "nvarchar(50)")
    private String languageDescription;

    @Id
    @Column(name = "C_ID", columnDefinition = "nvarchar(50)")
    private String companyId;

    @Column(name = "C_NAME", columnDefinition = "nvarhcar(50)")
    private String companyName;

    @Id
    @Column(name = "CUSTOMER_ID", columnDefinition = "nvarchar(50)")
    private String customerId;

    @Column(name = "CUSTOMER_NAME", columnDefinition = "nvarchar(50)")
    private String customerName;

    @Id
    @Column(name = "PRODUCT_ID", columnDefinition = "nvarchar(50)")
    private String productId;

    @Column(name = "PRODUCT_NAME", columnDefinition = "nvarchar(50)")
    private String productName;

    @Column(name = "PRODUCT_TEXT", columnDefinition = "nvarchar(100)")
    private String productText;

    @Id
    @Column(name = "SUB_PRODUCT_ID", columnDefinition = "nvarchar(50)")
    private String subProductId;

    @Column(name = "SUB_PRODUCT_NAME", columnDefinition = "nvarchar(50)")
    private String subProductName;

    @Id
    @Column(name = "SUB_PRODUCT_VALUE", columnDefinition = "nvarchar(50)")
    private String subProductValue;

    @Column(name = "STATUS_ID", columnDefinition = "nvarchar(50)")
    private String statusId;

    @Column(name = "CALCULATION_TYPE", columnDefinition = "nvarchar(100)")
    private String calculationType;

    @Column(name = "BILL_GENERATION", columnDefinition = "nvarchar(50)")
    private Long billGeneration;

    @Column(name = "BILLING_FREQUENCY", columnDefinition = "nvarchar(50)")
    private String billingFrequency;

    @Column(name = "CUSTOMER_TYPE", columnDefinition = "nvarchar(50)")
    private String customerType;

    @Column(name = "BILLING_FREQUENCY_DATE_1")
    private Date billingFrequencyDate = new Date();

    @Column(name = "BILLING_FREQUENCY_DATE_2")
    private Date billingFrequencyDate2 = new Date();

    @Column(name = "BILLING_FREQUENCY_DATE_3")
    private Date billingFrequencyDate3 = new Date();

    @Column(name = "BILLING_FREQUENCY_DATE_4")
    private Date billingFrequencyDate4 = new Date();

    @Column(name = "BILLING_FREQUENCY_DATE_5")
    private Date billingFrequencyDate5 = new Date();

    @Column(name = "STATUS_TEXT", columnDefinition = "nvarchar(100)")
    private String statusDescription;

    @Column(name = "REMARK", columnDefinition = "nvarchar(50)")
    private String remark;

    @Column(name = "PICKUP_COUNT")
    private Long pickupCount = 0L;

    @Column(name = "HUB_CODE", columnDefinition = "nvarchar(50)")
    private String hubCode;

    @Column(name = "AGING_COUNT")
    private Long agingCount = 0L;

    @Column(name = "DELIVERY_COUNT")
    private Long deliveryCount = 0L;

    @Column(name = "IS_DELETED")
    private Long deletionIndicator = 0L;

    @Column(name = "PICKUP_ADDRESS_LINE_1", columnDefinition = "nvarchar(500)")
    private String pickupAddressLine1;

    @Column(name = "PICKUP_ADDRESS_LINE_2", columnDefinition = "nvarchar(500)")
    private String pickupAddressLine2;

    @Column(name = "DISTRICT", columnDefinition = "nvarchar(200)")
    private String district;

    @Column(name = "PHONE", columnDefinition = "nvarchar(100)")
    private String phone;

    @Column(name = "ALTERNATE_PHONE", columnDefinition = "nvarchar(100)")
    private String alternatePhone;

    @Column(name = "REF_FIELD_1", columnDefinition = "nvarchar(500)")
    private String referenceField1;

    @Column(name = "REF_FIELD_2", columnDefinition = "nvarchar(500)")
    private String referenceField2;

    @Column(name = "REF_FIELD_3", columnDefinition = "nvarchar(500)")
    private String referenceField3;

    @Column(name = "REF_FIELD_4", columnDefinition = "nvarchar(500)")
    private String referenceField4;

    @Column(name = "REF_FIELD_5", columnDefinition = "nvarchar(500)")
    private String referenceField5;

    @Column(name = "REF_FIELD_6", columnDefinition = "nvarchar(500)")
    private String referenceField6;

    @Column(name = "REF_FIELD_7", columnDefinition = "nvarchar(500)")
    private String referenceField7;

    @Column(name = "REF_FIELD_8", columnDefinition = "nvarchar(500)")
    private String referenceField8;

    @Column(name = "REF_FIELD_9", columnDefinition = "nvarchar(500)")
    private String referenceField9;

    @Column(name = "REF_FIELD_10", columnDefinition = "nvarchar(500)")
    private String referenceField10;

    @Column(name = "CTD_BY", columnDefinition = "nvarchar(50)")
    private String createdBy;

    @Column(name = "CTD_ON")
    private Date createdOn = new Date();

    @Column(name = "UTD_BY", columnDefinition = "nvarchar(50)")
    private String updatedBy;

    @Column(name = "UTD_ON")
    private Date updatedOn = new Date();
}
