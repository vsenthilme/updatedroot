package com.tekclover.wms.api.mfg.model.diceslicechop;

import lombok.Data;

import java.io.Serializable;

@Data
public class DiceSliceChopCompositeKey implements Serializable {

    private static final long serialVersionUID = -7617672247680004647L;

    private String companyCodeId;
    private String plantId;
    private String warehouseId;
    private String languageId;
    private String receipeId;
    private String operationNumber;
    private String productionOrderNo;
    private Long productionOrderLineNo;
    private String itemCode;
    private String phaseNumber;
    private String bomItem;
    private String batchNumber;
}