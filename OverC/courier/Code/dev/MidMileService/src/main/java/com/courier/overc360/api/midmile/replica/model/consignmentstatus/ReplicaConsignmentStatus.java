package com.courier.overc360.api.midmile.replica.model.consignmentstatus;

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
 * `LANG_ID`, `C_ID`,`HOUSE_AIRWAY_BILL`, `PIECE_ID`
 */
@Table(name = "tblconsignmentstatus",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_key_consignmentstatus",
                        columnNames = {"LANG_ID", "C_ID", "HOUSE_AIRWAY_BILL", "PIECE_ID", "CON_STATUS_ID"}
                )
        }
)
@IdClass(ReplicaConsignmentStatusCompositeKey.class)
public class ReplicaConsignmentStatus {

    @Id
    @Column(name = "CON_STATUS_ID")
    private Long consignmentStatusId;

    @Id
    @Column(name = "LANG_ID", columnDefinition = "nvarchar(50)")
    private String languageId;

    @Id
    @Column(name = "C_ID", columnDefinition = "nvarchar(50)")
    private String companyId;

    @Id
    @Column(name = "PIECE_ID", columnDefinition = "nvarchar(500)")
    private String pieceId;

    @Id
    @Column(name = "HOUSE_AIRWAY_BILL", columnDefinition = "nvarchar(50)")
    private String houseAirwayBill;

    @Column(name = "PARTNER_HOUSE_AIRWAY_BILL", columnDefinition = "nvarchar(50)")
    private String partnerHouseAirwayBill;

    @Column(name = "PARTNER_MASTER_AIRWAY_BILL", columnDefinition = "nvarchar(50)")
    private String partnerMasterAirwayBill;

    @Column(name = "HUB_CODE", columnDefinition = "nvarchar(50)")
    private String hubCode;

    @Column(name = "HUB_NAME", columnDefinition = "nvarchar(50)")
    private String hubName;

//    @Id
//    @Column(name = "STATUS_ID", columnDefinition = "nvarchar(50)")
//    private String statusId;
//
//    @Id
//    @Column(name = "EVENT_CODE", columnDefinition = "nvarchar(50)")
//    private String eventCode;

    @Column(name = "MASTER_AIRWAY_BILL", columnDefinition = "nvarchar(50)")
    private String masterAirwayBill;

    @Column(name = "LANG_TEXT", columnDefinition = "nvarchar(50)")
    private String languageDescription;

    @Column(name = "C_NAME", columnDefinition = "nvarchar(50)")
    private String companyName;

//    @Column(name = "STATUS_TEXT", columnDefinition = "nvarchar(50)")
//    private String statusText;

    @Column(name = "BAG_ID", columnDefinition = "nvarchar(50)")
    private String bagId;

//    @Column(name = "PIECE_STATUS_ID", columnDefinition = "nvarchar(50)")
//    private String pieceStatusId;
//
//    @Column(name = "PIECE_STATUS_TEXT", columnDefinition = "nvarchar(50)")
//    private String pieceStatusText;

//    @Column(name = "EVENT_TEXT", columnDefinition = "nvarchar(50)")
//    private String eventText;
//
//    @Column(name = "PIECE_EVENT_CODE", columnDefinition = "nvarchar(50)")
//    private String pieceEventCode;
//
//    @Column(name = "PIECE_EVENT_TEXT", columnDefinition = "nvarchar(50)")
//    private String pieceEventText;

//    @Column(name = "PIECE_EVENT_TIMESTAMP", columnDefinition = "nvarchar(50)")
//    private Date pieceEventTimestamp = new Date();
//
//    @Column(name = "EVENT_TIMESTAMP", columnDefinition = "nvarchar(50)")
//    private Date eventTimestamp = new Date();
//
//    @Column(name = "STATUS_TIMESTAMP", columnDefinition = "nvarchar(50)")
//    private Date statusTimestamp = new Date();

    @Column(name = "HAWB_TYP", columnDefinition = "nvarchar(50)")
    private String hawbType;

    @Column(name = "HAWB_TYP_ID", columnDefinition = "nvarchar(50)")
    private String hawbTypeId;

    @Column(name = "HAWB_TYP_TXT", columnDefinition = "nvarchar(100)")
    private String hawbTypeDescription;

    @Column(name = "HAWB_TIMESTAMP")
    private Date hawbTimeStamp = new Date();

    @Column(name = "PIECE_TYP", columnDefinition = "nvarchar(50)")
    private String pieceType;

    @Column(name = "PIECE_TYP_ID", columnDefinition = "nvarchar(50)")
    private String pieceTypeId;

    @Column(name = "PIECE_TYP_TXT", columnDefinition = "nvarchar(100)")
    private String pieceTypeDescription;

    @Column(name = "PIECE_TIMESTAMP")
    private Date pieceTimeStamp = new Date();

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
