package com.tekclover.wms.api.mfg.model.peeling;

import lombok.Data;

import java.io.Serializable;

@Data
public class PeelingCompositeKey implements Serializable {

    private static final long serialVersionUID = -7617672247680004647L;

    private String languageId;
    private String companyCodeId;
    private String plantId;
    private String warehouseId;
    private String productionOrderNo;
    private Long productionOrderLineNo;
    private String receipeId;
    private String operationNumber;
    private String itemCode;
    private String phaseNumber;
    private String bomItem;
    private String batchNumber;
}