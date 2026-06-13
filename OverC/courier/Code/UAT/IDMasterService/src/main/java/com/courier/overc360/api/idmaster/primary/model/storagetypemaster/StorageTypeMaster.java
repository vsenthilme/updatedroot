package com.courier.overc360.api.idmaster.primary.model.storagetypemaster;

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
 * `C_ID`, `LANG_ID`, `SERVICE_PROVIDERS_ID`
 */
@Table(
        name = "tblstoragetypemaster",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_key_storagetypemaster",
                        columnNames = {"C_ID", "LANG_ID", "STORAGE_TYPE_ID"}
                )
        }
)
@IdClass(StorageTypeMasterCompositeKey.class)
public class StorageTypeMaster {

    @Id
    @Column(name = "C_ID", columnDefinition = "nvarchar(50)")
    private String companyId;

    @Id
    @Column(name = "LANG_ID", columnDefinition = "nvarchar(50)")
    private String languageId;

    @Id
    @Column(name = "STORAGE_TYPE_ID", columnDefinition = "nvarchar(50)")
    private String storageTypeId;

//    @Column(name = "CITY_ID", columnDefinition = "nvarchar(50)")
//    private String cityId;
//
//    @Column(name = "PROVINCE_ID", columnDefinition = "nvarchar(50)")
//    private String provinceId;
//
//    @Column(name = "DISTRICT_ID", columnDefinition = "nvarchar(50)")
//    private String districtId;

    @Column(name = "ZONE_TYPE_ID", columnDefinition = "nvarchar(50)")
    private String zoneTypeId;

    @Column(name = "HUB_CODE", columnDefinition = "nvarchar(50)")
    private String hubCode;

    @Column(name = "STATUS_ID", columnDefinition = "nvarchar(50)")
    private String statusId;

    @Column(name = "ZONE_ID", columnDefinition = "nvarchar(50)")
    private String zoneId;

    @Column(name = "STORAGE_TYPE", columnDefinition = "nvarchar(50)")
    private String storageType;

    @Column(name = "LANG_TEXT", columnDefinition = "nvarchar(100)")
    private String languageDescription;

    @Column(name = "C_NAME", columnDefinition = "nvarchar(100)")
    private String companyName;

    @Column(name = "STORAGE_TYPE_TEXT", columnDefinition = "nvarchar(50)")
    private String storageTypeText;

    @Column(name = "IS_DELETED")
    private Long deletionIndicator = 0L;

    @Column(name = "REMARK", columnDefinition = "nvarchar(50)")
    private String remark;

    @Column(name = "STATUS_TEXT", columnDefinition = "nvarchar(50)")
    private String statusDescription;

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
