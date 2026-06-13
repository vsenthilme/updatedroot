package com.courier.overc360.api.idmaster.replica.model.numberrange;

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
 * `LANG_ID`, `CLASS_ID`, `NUM_RAN_CODE`, `NUM_RAN_OBJ`
 */
@Table(
        name = "tblnumberrange",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_key_numberrange",
                        columnNames = {"LANG_ID", "NUM_RAN_CODE", "NUM_RAN_OBJ"})
        }
)
@IdClass(ReplicaNumberRangeCompositeKey.class)
public class ReplicaNumberRange {

    @Id
    @Column(name = "LANG_ID")
    private String languageId;

    @Id
    @Column(name = "NUM_RAN_CODE")
    private Long numberRangeCode;

    @Id
    @Column(name = "NUM_RAN_OBJ")
    private String numberRangeObject;

    @Column(name = "NUM_RAN_FROM")
    private String numberRangeFrom;

    @Column(name = "NUM_RAN_TO")
    private String numberRangeTo;

    @Column(name = "NUM_RAN_CURRENT")
    private String numberRangeCurrent;

    @Column(name = "NUM_RAN_STATUS")
    private String numberRangeStatus;

    @Column(name = "STATUS_TEXT", columnDefinition = "nvarchar(50)")
    private String statusDescription;

    @Column(name = "IS_DELETED")
    private Long deletionIndicator = 0L;

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

    @Column(name = "CTD_BY")
    private String createdBy;

    @Column(name = "CTD_ON")
    private Date createdOn = new Date();

    @Column(name = "UTD_BY")
    private String updatedBy;

    @Column(name = "UTD_ON")
    private Date updatedOn = new Date();
}
