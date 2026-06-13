package com.tekclover.wms.api.enterprise.transaction.model.packing;


import lombok.Data;

@Data
public class FindPackingInventory {

    private String languageId;
    private String companyId;
    private String plantId;
    private String warehouseId;
    private String packingMaterialNo;
}