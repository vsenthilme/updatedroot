package com.tekclover.wms.api.mfg.model.operation;

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
        name = "tbloperationconsumption",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_key_operationconsumption",
                        columnNames = {"C_ID", "PLANT_ID", "LANG_ID", "WH_ID", "PROD_ORD_NO", "ORD_LINE_NO", "ITM_CODE", "OPERATION_ID", "PHASE_ID", "BOM_ITM", "BATCH_NO"})
        }
)
@IdClass(OperationConsumptionCompositeKey.class)
public class OperationConsumption {

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
    @Column(name = "PROD_ORD_NO", columnDefinition = "nvarchar(100)")
    private String productionOrderNo;

    @Id
    @Column(name = "ORD_LINE_NO")
    private Long productionOrderLineNo;

    @Id
    @Column(name = "ITM_CODE", columnDefinition = "nvarchar(100)")
    private String itemCode;

    @Id
    @Column(name = "OPERATION_ID", columnDefinition = "nvarchar(100)")
    private String operationNumber;

    @Id
    @Column(name = "PHASE_ID", columnDefinition = "nvarchar(100)")
    private String phaseNumber;

    @Id
    @Column(name = "BOM_ITM", columnDefinition = "nvarchar(200)")
    private String bomItem;

    @Id
    @Column(name = "BATCH_NO", columnDefinition = "nvarchar(100)")
    private String batchNumber;

    @Column(name = "ITM_TYP", columnDefinition = "nvarchar(100)")
    private String itemType;

    @Column(name = "YLD_QTY")
    private Double yield;

    @Column(name = "LOSS_QTY")
    private Double loss;

    @Column(name = "ITM_GRP", columnDefinition = "nvarchar(100)")
    private String itemGroup;

    @Column(name = "ITM_TYP_DESC", columnDefinition = "nvarchar(100)")
    private String itemTypeDescription;

    @Column(name = "ITM_GRP_DESC", columnDefinition = "nvarchar(100)")
    private String itemGroupDescription;

    @Column(name = "RECEIPE_ID", columnDefinition = "nvarchar(100)")
    private String receipeId;

    @Column(name = "OPERATION_DETAIL", columnDefinition = "nvarchar(255)")
    private String operationDescription;

    @Column(name = "BOM_NO", columnDefinition = "nvarchar(100)")
    private String bomNumber;

    @Column(name = "PHASE_DETAIL", columnDefinition = "nvarchar(255)")
    private String phaseDescription;

    @Column(name = "ITM_TEXT", columnDefinition = "nvarchar(255)")
    private String itemDescription;

    @Column(name = "BOM_QTY")
    private Double bomQuantity;

    @Column(name = "RECEIPE_QTY")
    private Double receipeQuantity;

    @Column(name = "ISSUED_QTY")
    private Double issuedQuantity;

    @Column(name = "CONSUMED_QTY")
    private Double consumedQuantity;

    @Column(name = "UOM", columnDefinition = "nvarchar(50)")
    private String uom;

    @Column(name = "ISSUE_BY", columnDefinition = "nvarchar(50)")
    private String issuedBy;

    @Column(name = "ISSUE_ON")
    private Date issuedOn;

    @Column(name = "BATCH_DATE")
    private Date batchDate;

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

    @Column(name = "CNF_BY", columnDefinition = "nvarchar(50)")
    private String orderConfirmedBy;

    @Column(name = "CNF_ON")
    private Date orderConfirmedOn;

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

    @Column(name = "PAR_PROD_ORD_NO", columnDefinition = "nvarchar(100)")
    private String parentProductionOrderNo;

    @Column(name = "PROD_ORD_TYPE", columnDefinition = "nvarchar(50)")
    private String productionOrderType;

}