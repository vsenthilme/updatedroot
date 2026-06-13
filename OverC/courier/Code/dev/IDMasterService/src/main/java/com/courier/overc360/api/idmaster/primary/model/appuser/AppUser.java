package com.courier.overc360.api.idmaster.primary.model.appuser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tblappuser",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_key_appuser",
                        columnNames = {"C_ID", "LANG_ID", "APP_USER_ID"})
        }
)
@IdClass(AppUserCompositeKey.class)
public class AppUser {
    @Id
    @Column(name = "Lang_ID", columnDefinition = "nvarchar(50)")
    private String languageId;

    @Id
    @Column(name = "C_ID", columnDefinition = "nvarchar(50)")
    private String companyId;

    @Id
    @Column(name = "APP_USER_ID", columnDefinition = "nvarchar(50)")
    private String appUserId;

    @Column(name = "LANG_TEXT", columnDefinition = "nvarchar(100)")
    private String languageDescription;

    @Column(name = "C_NAME", columnDefinition = "nvarchar(100)")
    private String companyName;

    @Column(name = "APP_USER_NAME", columnDefinition = "nvarchar(100)")
    private String appUserName;

    @Column(name = "APP_USER_TYPE", columnDefinition = "nvarchar(50)")
    private String appUserType;

    @Column(name = "PASSWORD", columnDefinition = "nvarchar(300)")
    private String password;

    @Column(name = "ADDRESS", columnDefinition = "nvarchar(50)")
    private String address;

    @Column(name = "LATITUDE", columnDefinition = "nvarchar(50)")
    private Double latitude;

    @Column(name = "LONGITUDE", columnDefinition = "nvarchar(50)")
    private Double longitude;

    @Column(name = "MOBILE_NUMBER", columnDefinition = "nvarchar(50)")
    private String mobileNumber;

    @Column(name = "VEHICLE_REG_NUMBER", columnDefinition = "nvarchar(50)")
    private String vehicleRegNumber;

    @Column(name = "ROUTE_ID", columnDefinition = "nvarchar(50)")
    private String routeId;

    @Column(name = "ASSIGNED_HUB_CODE", columnDefinition = "nvarchar(50)")
    private String assignedHubCode;

    @Column(name = "STATUS_ID", columnDefinition = "nvarchar(50)")
    private String statusId;

    @Column(name = "STATUS_TEXT", columnDefinition = "nvarchar(50)")
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

    @Column(name = "UTD_ON")
    private Date updatedOn = new Date();

    @Column(name = "UTD_BY", columnDefinition = "nvarchar(500)")
    private String updatedBy;


}
