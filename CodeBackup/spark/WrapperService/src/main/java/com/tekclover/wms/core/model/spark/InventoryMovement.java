package com.tekclover.wms.core.model.spark;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class InventoryMovement {

    private String languageId;
    private String companyCodeId;
    private String plantId;
    private String warehouseId;
    private String palletCode;
    private String caseCode;
    private String itemCode;
    private Long specialStockIndicatorId;
    private Timestamp createdOn;
}
