package com.courier.overc360.api.idmaster.primary.model.consignor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
/*
 * `LANG_ID`, `C_ID`, `SUB_PRODUCT_ID`, `PRODUCT_ID`, `CUSTOMER_ID`, `CONSIGNOR_ID`, `SUB_PRODUCT_VALUE`
 */
@Table(name = "tblconsignor",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_key_consignor",
                        columnNames = {"LANG_ID", "C_ID", "SUB_PRODUCT_ID", "PRODUCT_ID", "CUSTOMER_ID", "CONSIGNOR_ID", "SUB_PRODUCT_VALUE"}
                )
        }
)
@IdClass(ConsignorCompositeKey.class)
public class Consignor {

    @Id
    @Column(name = "CONSIGNOR_ID", columnDefinition = "nvarchar(50)")
    private String consignorId;

    @Id
    @Column(name = "CUSTOMER_ID", columnDefinition = "nvarchar(50)")
    private String customerId;

    @Id
    @Column(name = "PRODUCT_ID", columnDefinition = "nvarchar(50)")
    private String productId;

    @Id
    @Column(name = "SUB_PRODUCT_ID", columnDefinition = "nvarchar(50)")
    private String subProductId;

    @Id
    @Column(name = "C_ID", columnDefinition = "nvarchar(50)")
    private String companyId;

    @Id
    @Column(name = "LANG_ID", columnDefinition = "nvarchar(50)")
    private String languageId;

    @Column(name = "LANG_TEXT", columnDefinition = "nvarchar(100)")
    private String languageDescription;

    @Column(name = "C_NAME", columnDefinition = "nvarchar(100)")
    private String companyName;

    @Column(name = "SUB_PRODUCT_NAME", columnDefinition = "nvarchar(100)")
    private String subProductName;

    @Id
    @Column(name = "SUB_PRODUCT_VALUE", columnDefinition = "nvarchar(50)")
    private String subProductValue;

    @Column(name = "PRODUCT_NAME", columnDefinition = "nvarchar(100)")
    private String productName;

    @Column(name = "PRODUCT_TEXT", columnDefinition = "nvarchar(100)")
    private String productText;

    @Column(name = "CUSTOMER_NAME", columnDefinition = "nvarchar(100)")
    private String customerName;

    @Column(name = "CONSIGNOR_NAME", columnDefinition = "nvarchar(100)")
    private String consignorName;

    @Column(name = "AGING_COUNT", columnDefinition = "nvarchar(50)")
    private String agingCount;

    @Column(name = "STATUS_ID", columnDefinition = "nvarchar(50)")
    private String statusId;

    @Column(name = "STATUS_TEXT", columnDefinition = "nvarchar(100)")
    private String statusDescription;

    @Column(name = "REMARK", columnDefinition = "nvarchar(2000)")
    private String remark;

    @Column(name = "IS_DELETED")
    private Long deletionIndicator = 0L;

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
