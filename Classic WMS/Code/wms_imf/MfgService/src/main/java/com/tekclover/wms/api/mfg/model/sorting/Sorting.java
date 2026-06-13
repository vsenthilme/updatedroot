package com.tekclover.wms.api.mfg.model.sorting;

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
 * `C_ID`,`PLANT_ID`, `WH_ID`, `LANG_ID`, `PROD_ORD_NO`, `ORD_LINE_NO`, `RECEIPE_ID`, `OPERATION_ID`, `ITM_CODE`
 */
@Table(
        name = "tblsorting",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_key_sorting",
                        columnNames = {"C_ID", "PLANT_ID", "LANG_ID", "WH_ID", "PROD_ORD_NO", "ORD_LINE_NO", "RECEIPE_ID", "OPERATION_ID", "ITM_CODE", "PHASE_ID", "CHL_ITEM_CODE", "BATCH_NO"})
        }
)
@IdClass(SortingCompositeKey.class)
public class Sorting {

    @Id
    @Column(name = "C_ID", columnDefinition = "nvarchar(50)")
    private String companyCodeId;

    @Id
    @Column(name = "LANG_ID", columnDefinition = "nvarchar(50)")
    private String languageId;

    @Id
    @Column(name = "PLANT_ID", columnDefinition = "nvarchar(50)")
    private String plantId;

    @Id
    @Column(name = "WH_ID", columnDefinition = "nvarchar(50)")
    private String warehouseId;

    @Id
    @Column(name = "PROD_ORD_NO", columnDefinition = "nvarchar(50)")
    private String productionOrderNo;

    @Id
    @Column(name = "ORD_LINE_NO", columnDefinition = "nvarchar(50)")
    private Long productionOrderLineNo;

    @Id
    @Column(name = "RECEIPE_ID", columnDefinition = "nvarchar(50)")
    private String receipeId;

    @Id
    @Column(name = "OPERATION_ID", columnDefinition = "nvarchar(50)")
    private String operationNumber;

    @Id
    @Column(name = "ITM_CODE", columnDefinition = "nvarchar(100)")
    private String itemCode;

    @Id
    @Column(name = "PHASE_ID", columnDefinition = "nvarchar(50)")
    private String phaseNumber;

    @Id
    @Column(name = "CHL_ITEM_CODE")
    private String bomItem;

    @Id
    @Column(name = "BATCH_NO", columnDefinition = "nvarchar(50)")
    private String batchNumber;

    @Column(name = "LANG_DESC", columnDefinition = "nvarchar(255)")
    private String languageDescription;

    @Column(name = "C_DESC", columnDefinition = "nvarchar(255)")
    private String companyDescription;

    @Column(name = "PLANT_DESC", columnDefinition = "nvarchar(255)")
    private String plantDescription;

    @Column(name = "WH_DESC", columnDefinition = "nvarchar(255)")
    private String warehouseDescription;

    @Column(name = "OPERATION_DETAIL", columnDefinition = "nvarchar(255)")
    private String operationDescription;

    @Column(name = "PHASE_DETAIL", columnDefinition = "nvarchar(255)")
    private String phaseDescription;

    @Column(name = "REF_ORD_NUMBER", columnDefinition = "nvarchar(50)")
    private String referenceOrderNumber;

    @Column(name = "REF_BATCH_NUMBER", columnDefinition = "nvarchar(50)")
    private String referenceBatchNumber;

    @Column(name = "ITM_TYP", columnDefinition = "nvarchar(50)")
    private String itemType;

    @Column(name = "ITM_TEXT", columnDefinition = "nvarchar(255)")
    private String itemDescription;

    @Column(name = "PROCESS_QTY")
    private Double processQty;

    @Column(name = "OUTPUT_QTY")
    private Double outputQty;

    @Column(name = "START_TIME")
    private Date startTime;

    @Column(name = "END_TIME")
    private Date endTime;

    @Column(name = "WORKER_NO", columnDefinition = "nvarchar(50)")
    private String noOfWorkers;

    @Column(name = "IMPURITIES", columnDefinition = "nvarchar(100)")
    private String impurities;

    @Column(name = "SUPERVISOR_NM",columnDefinition = "nvarchar(100)")
    private String supervisorName;

    @Column(name = "STATUS_ID", columnDefinition = "bigint default'0'")
    private Long statusId;

    @Column(name = "STATUS_TEXT", columnDefinition = "nvarchar(255)")
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

    @Column(name = "PROCESS_CONFIRMED_UI", columnDefinition = "bit default '0'")
    private boolean uiProcessConfirm;

    @Column(name = "PROCESS_CONFIRMED_BE", columnDefinition = "bit default '0'")
    private boolean beProcessConfirm;

}