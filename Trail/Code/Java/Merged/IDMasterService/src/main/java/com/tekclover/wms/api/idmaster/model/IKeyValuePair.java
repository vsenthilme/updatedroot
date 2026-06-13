package com.tekclover.wms.api.idmaster.model;


public interface IKeyValuePair {

    String getCompanyCodeId();
    String getDescription();
    String getPlantId();
    String getWarehouseId();
    String getFloorId();
    String getStateId();
    String getCountryId();
    String getModuleId();
    String getStorageClassId();
    String getStorageTypeId();
    String getStorageSectionId();
    String getAisleId();
    String getRowId();
    String getSpanId();
    String getStockTypeId();
    String getBarcodeTypeId();
    String getProcessId();
    String getApprovalProcessId();
    String getLevelId();
    String getItemTypeId();
    String getItemGroupId();
    String getMovementTypeId();

    String getLangDesc();
    String getCompanyDesc();
    String getPlantDesc();
    String getWarehouseDesc();

    Long getUserTypeId();

    String getUserTypeDescription();

    Long getRoleId();

    String getRoleDescription();

    String getVerticalId();

    Long getSubItemGroupId();
    String getStrategyNoText();
    String getVariantId();
    String getStorageBinTypeId();
    String getWarehouseTypeId();
    String getCompanyDescription();
    String getPlantDescription();
    String getWarehouseDescription();
    String getLevelDescription();
    String getVariantDescription();
}