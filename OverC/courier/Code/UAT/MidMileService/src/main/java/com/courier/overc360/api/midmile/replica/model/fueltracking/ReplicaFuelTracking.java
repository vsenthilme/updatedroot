package com.courier.overc360.api.midmile.replica.model.fueltracking;

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
 * `C_ID`, `LANG_ID`, `VEHICLE_REG_NUMBER`
 */
@Table(
        name = "tblfueltracking",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_key_fueltracking",
                        columnNames = {"C_ID", "LANG_ID", "VEHICLE_REG_NUMBER"}
                )
        }
)
@IdClass(ReplicaFuelTrackingCompositeKey.class)
public class ReplicaFuelTracking {

    @Id
    @Column(name = "C_ID", columnDefinition = "nvarchar(50)")
    private String companyId;

    @Id
    @Column(name = "LANG_ID", columnDefinition = "nvarchar(50)")
    private String languageId;

    @Id
    @Column(name = "VEHICLE_REG_NUMBER", columnDefinition = "nvarchar(50)")
    private String vehicleRegNumber;

    @Column(name = "LANG_TEXT", columnDefinition = "nvarchar(100)")
    private String languageDescription;

    @Column(name = "C_NAME", columnDefinition = "nvarchar(100)")
    private String companyName;

    @Column(name = "RIDER_ID", columnDefinition = "nvarchar(50)")
    private String riderId;

    @Column(name = "RIDER_NAME", columnDefinition = "nvarchar(100)")
    private String riderName;

    @Column(name = "PICKUP_ID", columnDefinition = "nvarchar(50)")
    private String pickupId;

    @Column(name = "DELIVERY_TIME_SLOT_START")
    private Date deliveryTimeSlotStart = new Date();

    @Column(name = "DELIVERY_TIME_SLOT_END")
    private Date deliveryTimeSlotEnd = new Date();

    @Column(name = "TOTAL_KM_INPUT", columnDefinition = "nvarchar(50)")
    private String totalKmInput;

    @Column(name = "IMAGE_REF_LIST", columnDefinition = "nvarchar(50)")
    private String imageRefList;

    @Column(name = "ASSIGNED_HUB_CODE", columnDefinition = "nvarchar(50)")
    private String assignedHubCode;

    @Column(name = "ROUTE_ID", columnDefinition = "nvarchar(50)")
    private String routeId;

    @Column(name = "RIDER_TYPE", columnDefinition = "nvarchar(50)")
    private String riderType;

    @Column(name = "IS_DELETED")
    private Long deletionIndicator = 0L;

    @Column(name = "REMARK", columnDefinition = "nvarchar(50)")
    private String remark;

    @Column(name = "STATUS_ID", columnDefinition = "nvarchar(50)")
    private String statusId;

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

    @Column(name = "REF_FIELD_11", columnDefinition = "nvarchar(500)")
    private String referenceField11;

    @Column(name = "REF_FIELD_12", columnDefinition = "nvarchar(500)")
    private String referenceField12;

    @Column(name = "REF_FIELD_13", columnDefinition = "nvarchar(500)")
    private String referenceField13;

    @Column(name = "REF_FIELD_14", columnDefinition = "nvarchar(500)")
    private String referenceField14;

    @Column(name = "REF_FIELD_15", columnDefinition = "nvarchar(500)")
    private String referenceField15;

    @Column(name = "REF_FIELD_16", columnDefinition = "nvarchar(500)")
    private String referenceField16;

    @Column(name = "REF_FIELD_17", columnDefinition = "nvarchar(500)")
    private String referenceField17;

    @Column(name = "REF_FIELD_18", columnDefinition = "nvarchar(500)")
    private String referenceField18;

    @Column(name = "REF_FIELD_19", columnDefinition = "nvarchar(500)")
    private String referenceField19;

    @Column(name = "REF_FIELD_20", columnDefinition = "nvarchar(500)")
    private String referenceField20;

    @Column(name = "CTD_BY", columnDefinition = "nvarchar(50)")
    private String createdBy;

    @Column(name = "CTD_ON")
    private Date createdOn = new Date();

    @Column(name = "UTD_BY", columnDefinition = "nvarchar(50)")
    private String updatedBy;

    @Column(name = "UTD_ON")
    private Date updatedOn = new Date();
}
