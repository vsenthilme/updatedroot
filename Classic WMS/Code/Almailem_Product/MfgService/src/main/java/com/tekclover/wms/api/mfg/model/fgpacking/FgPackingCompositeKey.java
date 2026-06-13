package com.tekclover.wms.api.mfg.model.fgpacking;

import lombok.Data;

import java.io.Serializable;

@Data
public class FgPackingCompositeKey implements Serializable {

    private static final long serialVersionUID = -7617672247680004647L;

    private String companyCodeId;
    private String languageId;
    private String plantId;
    private String warehouseId;
    private String productionOrderNo;
    private Long productionOrderLineNo;
    private String receipeId;
    private String operationNumber;
    private String itemCode;
}
