package com.tekclover.wms.api.mfg.model.sfgqi;

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
 * `C_ID`, `PLANT_ID`, `WH_ID`, `LANG_ID`
 */
@Table(
        name = "tblsfgqi",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_key_sfgqi",
                        columnNames = {"C_ID", "PLANT_ID", "LANG_ID", "WH_ID", "RECEIPE_ID", "PROD_ORD_NO", "ORD_LINE_NO", "OPERATION_ID", "ITM_CODE" }
                )
        }
)
@IdClass(SFGQICompositeKey.class)
public class SFGQI {

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
    @Column(name = "RECEIPE_ID", columnDefinition = "nvarchar(50)")
    private String receipeId;

    @Id
    @Column(name = "PROD_ORD_NO", columnDefinition = "nvarchar(50)")
    private String productionOrderNo;

    @Id
    @Column(name = "ORD_LINE_NO")
    private Long productionOrderLineNo;

    @Id
    @Column(name = "OPERATION_ID", columnDefinition = "nvarchar(50)")
    private String operationNumber;

    @Id
    @Column(name = "ITM_CODE", columnDefinition = "nvarchar(100)")
    private String itemCode;

    @Column(name = "OPERATION_DETAIL", columnDefinition = "nvarchar(100)")
    private String operationDescription;

    @Column(name = "PHASE_ID", columnDefinition = "nvarchar(50)")
    private String phaseNumber;

    @Column(name = "PHASE_DETAILS")
    private String phaseDescription;

    @Column(name = "REF_ORD_NUMBER", columnDefinition = "nvarchar(50)")
    private String referenceOrderNumber;

    @Column(name = "REF_BATCH_NUMBER", columnDefinition = "nvarchar(50)")
    private String referenceBatchNumber;

    @Column(name = "ITM_TYPE", columnDefinition = "nvarchar(50)")
    private String itemType;

    @Column(name = "ITM_TEXT", columnDefinition = "nvarchar(225)")
    private String itemDescription;

    @Column(name = "BST_WK_VAL", columnDefinition = "nvarchar(255)")
    private String bostwickValue;

    @Column(name = "BRIX", columnDefinition = "nvarchar(50)")
    private String brix;

    @Column(name = "SALT", columnDefinition = "nvarchar(50)")
    private String salt;

    @Column(name = "ACIDITY", columnDefinition = "nvarchar(50)")
    private String acidity;

    @Column(name = "MOISTURE", columnDefinition = "nvarchar(50)")
    private String moisture;

    @Column(name = "WORKER_NO", columnDefinition = "nvarchar(50)")
    private String numberOfWorkers;

    @Column(name = "SUPERVISOR_NM", columnDefinition = "nvarchar(50)")
    private String supervisorName;

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

    @Column(name = "PROCESS_CONFIRMED_UI", columnDefinition = "bit default '0'")
    private boolean uiProcessConfirm;

    @Column(name = "PROCESS_CONFIRMED_BE", columnDefinition = "bit default '0'")
    private boolean beProcessConfirm;

}