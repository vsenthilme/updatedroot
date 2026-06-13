package com.courier.overc360.api.midmile.replica.model.debrief;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbldebrief",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_key_debrief",
                        columnNames = {"LANG_ID", "C_ID", "COURIER_ID"}
                )
        }
)
@IdClass(ReplicaDebriefCompositeKey.class)
public class ReplicaDebrief {

    @Id
    @Column(name = "LANG_ID", columnDefinition = "nvarchar(50)")
    private String languageId;

    @Id
    @Column(name = "C_ID", columnDefinition = "nvarchar(50)")
    private String companyId;

    @Id
    @Column(name = "COURIER_ID", columnDefinition = "nvarchar(200)")
    private String courierId;

    @Column(name = "NO_OF_ASSIGNED", columnDefinition = "nvarchar(50)")
    private String noOfAssigned;

    @Column(name = "DATE_CTD_ON")
    private Date date;

    @Column(name = "TIME_OF_DEPARTURE")
    private Date timeOfDeparture;

    @Column(name = "NO_OF_DELIVERED", columnDefinition = "nvarchar(50)")
    private String noOfDelivered;

    @Column(name = "NO_OF_RETURNED", columnDefinition = "nvarchar(50)")
    private String noOfReturned;

    @Column(name = "NO_OF_ATTEMPTED", columnDefinition = "nvarchar(50)")
    private String noOfAttempted;

    @Column(name = "NO_OF_PAID_CASH", columnDefinition = "nvarchar(50)")
    private String noOfPaidCash;

    @Column(name = "TIME_OF_FIRST_STOP")
    private Date timeOfFirstStop;

    @Column(name = "TIME_OF_LAST_STOP")
    private Date timeOfLastStop;

    @Column(name = "TIME_OF_ARRIVAL")
    private Date timeOfArrival;

    @Column(name = "NO_OF_PAID_LINK")
    private String noOfPaidLink;

    @Column(name = "TOTAL", columnDefinition = "nvarchar(50)")
    private String total;

    @Column(name = "IS_DELETED")
    private Long deletionIndicator = 0L;

    @Column(name = "REF_FIELD_1", columnDefinition = "nvarchar(200)")
    private String referenceField1;

    @Column(name = "REF_FIELD_2", columnDefinition = "nvarchar(200)")
    private String referenceField2;

    @Column(name = "REF_FIELD_3", columnDefinition = "nvarchar(200)")
    private String referenceField3;

    @Column(name = "REF_FIELD_4", columnDefinition = "nvarchar(200)")
    private String referenceField4;

    @Column(name = "REF_FIELD_5", columnDefinition = "nvarchar(200)")
    private String referenceField5;

    @Column(name = "REF_FIELD_6", columnDefinition = "nvarchar(200)")
    private String referenceField6;

    @Column(name = "REF_FIELD_7", columnDefinition = "nvarchar(200)")
    private String referenceField7;

    @Column(name = "REF_FIELD_8", columnDefinition = "nvarchar(200)")
    private String referenceField8;

    @Column(name = "REF_FIELD_9", columnDefinition = "nvarchar(200)")
    private String referenceField9;

    @Column(name = "REF_FIELD_10", columnDefinition = "nvarchar(200)")
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
