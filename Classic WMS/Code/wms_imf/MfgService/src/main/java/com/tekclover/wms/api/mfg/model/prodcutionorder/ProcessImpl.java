package com.tekclover.wms.api.mfg.model.prodcutionorder;

import java.util.Date;

public interface ProcessImpl {

    String getItemCode();
    String getBomItem();
    String getProductionOrderNo();
    String getBatchNumber();
    String getPhaseNumber();
    String getOperationNumber();
    String getPhaseDescription();
    Double getWaterQuantity();
    String getStatusDescription();
    String getNumberOfWorker();
    String getSupervisorName();
    String getStorageLocation();
    Double getOutputQuantity();
    String getUpdatedBy();
    Date getUpdatedOn();
    Date getStartTime();
    Date getEndTime();

}