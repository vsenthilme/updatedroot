package com.courier.overc360.api.idmaster.primary.model.iata;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
/*
 * `LANG_ID`, `C_ID`, `ORIGIN` , `ORIGIN_CODE`
 */
@Table(
        name = "tbliata",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_key_iata",
                        columnNames = { "LANG_ID", "C_ID", "ORIGIN", "ORIGIN_CODE"}
                )
        }
)
@IdClass(IataCompositeKey.class)
public class Iata {

    @Id
    @NotBlank(message = "LanguageId is mandatory ")
    @Column(name = "LANG_ID",nullable = false, columnDefinition = "nvarchar(50)")
    private String languageId;

    @Id
    @NotBlank(message = "CompanyId is mandatory ")
    @Column(name = "C_ID",nullable = false, columnDefinition = "nvarchar(50)")
    private String companyId;

    @Id
    @NotBlank(message = "Origin is mandatory ")
    @Column(name = "ORIGIN",nullable = false, columnDefinition = "nvarchar(50)")
    private String origin;

    @Id
    @NotBlank(message = "OriginCode is mandatory ")
    @Column(name = "ORIGIN_CODE",nullable = false, columnDefinition = "nvarchar(50)")
    private String originCode;

    @Column(name = "IATA_KD")
    private Double iataKd;

    @Column(name = "C_NAME", columnDefinition = "nvarchar(50)")
    private String companyName;

    @Column(name = "LANG_TEXT", columnDefinition = "nvarchar(50)")
    private String languageDescription;

    @Column(name = "IATA_CHARGE", columnDefinition = "nvarchar(50)")
    private String iataCharge;

    @Column(name = "CURRENCY_ID", columnDefinition = "nvarchar(50)")
    private String currencyId;

    @Column(name = "CURRENCY_TEXT", columnDefinition = "nvarchar(50)")
    private String currencyDescription;

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

