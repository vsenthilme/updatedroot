package com.tekclover.wms.api.mfg.model.prodcutionorder;

import java.util.Date;

public interface OperationLineImpl {

    String getLanguageId();
    String getCompanyCodeId();
    String getCompanyDescription();
    String getPlantId();
    String getPlantDescription();
    String getWarehouseId();
    String getWarehouseDescription();

    String getItemCode();
    String getItemDescription();
    String getUom();
    String getProductionOrderNo();
    String getBatchNumber();
    Double getOrderQuantity();
    Double getExpectedQuantity();
    Double getActualQuantity();
    Double getYieldPercentage();
    Double getLossPercentage();
    Double getLossQuantity();
    Date getCreatedOn();
    Date getOrderConfirmedOn();
    String getOrderConfirmedBy();
    String getStatusDescription();

    String getConsumption();
    String getProcess();

}