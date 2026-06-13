package com.courier.overc360.api.idmaster.replica.model.partnerhubmapping;

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
 * `C_ID`, `LANG_ID`, `HUB_CODE`, `PARTNER_TYPE`, `PARTNER_ID`
 */
@Table(name = "tblpartnerhubmapping",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_key_partnerhubmapping",
                        columnNames = {"C_ID", "LANG_ID", "HUB_CODE", "PARTNER_TYPE", "PARTNER_ID", "PRODUCT_CODE"}
                )
        }
)
@IdClass(ReplicaPartnerHubMappingCompositeKey.class)
public class ReplicaPartnerHubMapping {

    @Id
    @Column(name = "PARTNER_ID", columnDefinition = "nvarchar(50)")
    private String partnerId;

    @Id
    @Column(name = "PARTNER_TYPE", columnDefinition = "nvarchar(50)")
    private String partnerType;

    @Id
    @Column(name = "LANG_ID", columnDefinition = "nvarchar(50)")
    private String languageId;

    @Id
    @Column(name = "C_ID", columnDefinition = "nvarchar(50)")
    private String companyId;

    @Id
    @Column(name = "HUB_CODE", columnDefinition = "nvarchar(50)")
    private String hubCode;

    @Id
    @Column(name = "PRODUCT_CODE",columnDefinition = "nvarchar(50)")
    private String productCode;

    @Column(name = "DEFAULT_HUB_CODE",columnDefinition = "nvarchar(50)")
    private String defaultHubCode;

    @Column(name = "TRANSIT_DC",columnDefinition = "nvarchar(50)")
    private String transitDC;

    @Column(name = "FINAL_DC",columnDefinition = "nvarchar(50)")
    private String finalDC;

    @Column(name = "HUB_NAME", columnDefinition = "nvarchar(100)")
    private String hubName;

    @Column(name = "HUB_CATEGORY", columnDefinition = "nvarchar(100)")
    private String hubCategory;

    @Column(name = "PARTNER_NAME", columnDefinition = "nvarchar(100)")
    private String partnerName;

    @Column(name = "LANG_TEXT", columnDefinition = "nvarchar(100)")
    private String languageDescription;

    @Column(name = "C_NAME", columnDefinition = "nvarchar(100)")
    private String companyName;

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
