package com.courier.overc360.api.idmaster.primary.model.hub;

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
 * `C_ID`, `LANG_ID`, `HUB_CODE`
 */
@Table(name = "tblhub",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_key_hub",
                        columnNames = {"C_ID", "LANG_ID", "HUB_CODE"}
                )
        }
)
@IdClass(HubCompositeKey.class)
public class Hub {

    @Id
    @Column(name = "LANG_ID", columnDefinition = "nvarchar(50)")
    private String languageId;

    @Id
    @Column(name = "C_ID", columnDefinition = "nvarchar(50)")
    private String companyId;

    @Id
    @Column(name = "HUB_CODE", columnDefinition = "nvarchar(50)")
    private String hubCode;

    @Column(name = "HUB_NAME", columnDefinition = "nvarchar(100)")
    private String hubName;

    @Column(name = "HUB_CATEGORY", columnDefinition = "nvarchar(100)")
    private String hubCategory;

    @Column(name = "LANG_TEXT", columnDefinition = "nvarchar(100)")
    private String languageDescription;

    @Column(name = "C_NAME", columnDefinition = "nvarchar(100)")
    private String companyName;

    @Column(name = "COUNTRY_ID", columnDefinition = "nvarchar(50)")
    private String countryId;

    @Column(name = "PROVINCE_ID", columnDefinition = "nvarchar(50)")
    private String provinceId;

    @Column(name = "CITY_ID", columnDefinition = "nvarchar(50)")
    private String cityId;

    @Column(name = "COUNTRY_NAME", columnDefinition = "nvarchar(100)")
    private String countryName;

    @Column(name = "PROVINCE_NAME", columnDefinition = "nvarchar(100)")
    private String provinceName;

    @Column(name = "CITY_NAME", columnDefinition = "nvarchar(100)")
    private String cityName;

    @Column(name = "ADDRESS_LINE_1", columnDefinition = "nvarchar(2000)")
    private String addressLine1;

    @Column(name = "ADDRESS_LINE_2", columnDefinition = "nvarchar(2000)")
    private String addressLine2;

    @Column(name = "ADDRESS_LINE_3", columnDefinition = "nvarchar(2000)")
    private String addressLine3;

    @Column(name = "ADDRESS_LINE_4", columnDefinition = "nvarchar(2000)")
    private String addressLine4;

    @Column(name = "LATITUDE", columnDefinition = "nvarchar(100)")
    private String latitude;

    @Column(name = "LONGITUDE", columnDefinition = "nvarchar(100)")
    private String longitude;

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
