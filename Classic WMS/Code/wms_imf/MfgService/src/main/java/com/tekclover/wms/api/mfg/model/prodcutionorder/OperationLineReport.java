package com.tekclover.wms.api.mfg.model.prodcutionorder;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OperationLineReport {

    private String companyCodeId;

    private String plantId;

    private String languageId;

    private String warehouseId;

    private String productionOrderNo;

    private Long productionOrderLineNo;

    private String itemCode;

    private String itemType;

    private String itemGroup;

    private String receipeId;

    private String itemDescription;

    private Double batchQuantity;

    private Double orderQuantity;

    private Double expectedQuantity;

    private Double actualQuantity;

    private String batchNumber;

    private Date batchDate;

    private String operationNumber;

    private String phaseNumber;

    private String operationDescription;

    private String phaseDescription;

    private Double yieldPercentage;

    private Double lossQuantity;

    private Double lossPercentage;

    private Double receipePercentage;

    private String bomNumber;

    private String remarks;

    private Long statusId;

    private String statusDescription;

    private Long deletionIndicator;

    private String referenceField1;

    private String referenceField2;

    private String referenceField3;

    private String referenceField4;

    private String referenceField5;

    private String referenceField6;

    private String referenceField7;

    private String referenceField8;

    private String referenceField9;

    private String referenceField10;

    private String createdBy;

    private Date createdOn;

    private String updatedBy;

    private Date updatedOn;

    private String orderConfirmedBy;

    private Date orderConfirmedOn;

    private String uom;

    List<OperationConsumptionImpl> operationConsumptionReports;

    private List<ProcessImpl> process;

}