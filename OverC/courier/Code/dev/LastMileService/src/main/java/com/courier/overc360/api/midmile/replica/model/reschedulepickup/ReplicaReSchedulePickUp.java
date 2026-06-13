package com.courier.overc360.api.midmile.replica.model.reschedulepickup;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "tblreschedule_pickup")
public class ReplicaReSchedulePickUp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RESCHEDULE_NO")
    private Long rescheduleNo;

    @Column(name = "PICKUP_ENTITY_ID")
    private Long pickupEntityId;

    @NotBlank(message = "LanguageId is mandatory")
    @Column(name = "LANG_ID", columnDefinition = "nvarchar(50)", nullable = false)
    private String languageId;

    @Column(name = "LANG_TEXT", columnDefinition = "nvarchar(50)")
    private String languageDescription;

    @NotBlank(message = "CompanyId is mandatory")
    @Column(name = "C_ID", columnDefinition = "nvarchar(50)", nullable = false)
    private String companyId;

    @Column(name = "C_NAME", columnDefinition = "nvarchar(50)")
    private String companyName;

    @Column(name = "PARTNER_TYPE", columnDefinition = "nvarchar(50)")
    private String partnerType;

    @Column(name = "PARTNER_ID", columnDefinition = "nvarchar(50)")
    private String partnerId;

    @Column(name = "PARTNER_NAME", columnDefinition = "nvarchar(50)")
    private String partnerName;

    @Column(name = "CONSIGNMENT_BAG_ID", columnDefinition = "nvarchar(50)")
    private String consignmentBagId;

    @NotBlank(message = "PickupId is mandatory")
    @Column(name = "PICKUP_ID",columnDefinition = "nvarchar(50)",nullable = false)
    private String pickupId;

    @Column(name = "HOUSE_AIRWAY_BILL", columnDefinition = "nvarchar(50)")
    private String houseAirwayBill;

    @Column(name = "CONSIGNMENT_ID", columnDefinition = "nvarchar(50)")
    private String consignmentId;

    @Column(name = "REASON_ID", columnDefinition = "nvarchar(50)")
    private String reasonId;

    @Column(name = "REASON_DESCRIPTION", columnDefinition = "nvarchar(500)")
    private String reasonDescription;

    @Column(name = "RESCHEDULE_DATE")
    private Date rescheduleDate = new Date();

    @Column(name = "RESCHEDULE_START_TIME")
    private LocalTime rescheduleStartTime;

    @Column(name = "RESCHEDULE_END_TIME")
    private LocalTime rescheduleEndTime;

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
