package com.courier.overc360.api.midmile.primary.model.consignment;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tblprocessedconsignment")
public class ProcessConsignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROCESSED_ID")
    private Long processedId;
    @Column(name = "CONSIGNMENT_ID")
    private String consignmentId;
    @Column(name = "PARTNER_ID")
    private String partnerId;
    @Column(name = "HOUSE_AIRWAY_BILL")
    private String houseAirwayBill;
    @Column(name = "MASTER_AIRWAY_BILL")
    private String masterAirwayBill;
    @Column(name = "STATUS")
    private String status; // SUCCESS or FAILED
    @Column(name = "STATUS_TEXT")
    private String statusText; // e.g., "Step1: Validation Success, Step2: DB Save Failed"
    @Column(name = "REF_FIELD_1")
    private String referenceField1;
    @Column(name = "REF_FIELD_2")
    private String referenceField2;
    @Column(name = "REF_FIELD_3")
    private String referenceField3;
    @Column(name = "REF_FIELD_4")
    private String referenceField4;
    @Column(name = "REF_FIELD_5")
    private String referenceField5;
    @Column(name = "REF_FIELD_6")
    private String referenceField6;
    @Column(name = "REF_FIELD_7")
    private String referenceField7;
    @Column(name = "REF_FIELD_8")
    private String referenceField8;
    @Column(name = "REF_FIELD_9")
    private String referenceField9;
    @Column(name = "REF_FIELD_10")
    private String referenceField10;
    @Column(name = "CTD_ON")
    private Date createdOn = new Date();
//    @Column(name = "CREATION_TIME")
//    private Time creationTime = Time.valueOf(String.valueOf(LocalDateTime.now()));

}
