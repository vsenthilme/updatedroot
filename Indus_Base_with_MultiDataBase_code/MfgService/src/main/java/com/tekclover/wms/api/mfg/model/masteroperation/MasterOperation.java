package com.tekclover.wms.api.mfg.model.masteroperation;

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
 * `C_ID`,`PLANT_ID`, `WH_ID`, `LANG_ID`
 */
@Table(
        name = "tbloperationmaster",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_key_operationmaster",
                        columnNames = {"C_ID", "PLANT_ID", "LANG_ID", "WH_ID", "OPERATION_ID", "PHASE_ID"})
        }
)
@IdClass(MasterOperationCompositeKey.class)
public class MasterOperation {

    @Id
    @Column(name = "C_ID", columnDefinition = "nvarchar(50)")
    private String companyCodeId;

    @Id
    @Column(name = "PLANT_ID", columnDefinition = "nvarchar(50)")
    private String plantId;

    @Id
    @Column(name = "LANG_ID", columnDefinition = "nvarchar(50)")
    private String languageId;

    @Id
    @Column(name = "WH_ID", columnDefinition = "nvarchar(50)")
    private String warehouseId;

    @Id
    @Column(name = "OPERATION_ID", columnDefinition = "nvarchar(100)")
    private String operationNumber;

    @Id
    @Column(name = "PHASE_ID", columnDefinition = "nvarchar(100)")
    private String phaseNumber;

    @Column(name = "OPERATION_DETAIL", columnDefinition = "nvarchar(255)")
    private String operationDescription;

    @Column(name = "PHASE_DETAIL", columnDefinition = "nvarchar(255)")
    private String phaseDescription;

    @Column(name = "WORK_CENTER_ID", columnDefinition = "nvarchar(100)")
    private String workCenterId;

    @Column(name = "WORK_CENTER_DETAIL", columnDefinition = "nvarchar(255)")
    private String workCenterName;

    @Column(name = "SETUP_TIME")
    private String setupTime;

    @Column(name = "MACHINE_TIME")
    private String machineTime;

    @Column(name = "LOADING_TIME")
    private String loadingTime;

    @Column(name = "LEAD_TIME")
    private String leadTime;

    @Column(name = "TIME_UNIT", columnDefinition = "nvarchar(50)")
    private String timeUnit;

    @Column(name = "REMARK", columnDefinition = "nvarchar(255)")
    private String remarks;

    @Column(name = "STATUS_ID", columnDefinition = "bigint default'0'")
    private Long statusId;

    @Column(name = "STATUS_TEXT", columnDefinition = "nvarchar(150)")
    private String statusDescription;

    @Column(name = "IS_DELETED", columnDefinition = "bigint default'0'")
    private Long deletionIndicator;

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
    private Date updatedOn;

    @Column(name = "C_TEXT", columnDefinition = "nvarchar(200)")
    private String companyDescription;

    @Column(name = "PLANT_TEXT", columnDefinition = "nvarchar(200)")
    private String plantDescription;

    @Column(name = "WH_TEXT", columnDefinition = "nvarchar(200)")
    private String warehouseDescription;

}