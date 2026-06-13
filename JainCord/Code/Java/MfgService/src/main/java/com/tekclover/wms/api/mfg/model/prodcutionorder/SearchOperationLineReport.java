package com.tekclover.wms.api.mfg.model.prodcutionorder;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SearchOperationLineReport {

    private List<String> languageId;
    private List<String> companyCodeId;
    private List<String> plantId;
    private List<String> warehouseId;
    private List<String> itemCode;
    private List<String> productionOrderNo;
    private List<String> batchNumber;
    private Date startOrderConfirmedOn;
    private Date endOrderConfirmedOn;

    private List<String> statusId;

    private Date startCreatedOn;
    private Date endCreatedOn;

}
