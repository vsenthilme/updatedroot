package com.tekclover.wms.api.mfg.model.powder;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SearchPowder {

    private List<String> companyCodeId;
    private List<String> plantId;
    private List<String> languageId;
    private List<String> warehouseId;
    private List<String> operationNumber;
    private List<String> receipeId;
    private List<String> productionOrderNo;
    private List<Long> productionOrderLineNo;
    private List<String> itemCode;
    private List<String> statusId;

    private List<Powder> powder;
    private List<String> statusDescription;
    private List<String> updatedBy;
    private Date startUpdatedOn;
    private Date endUpdatedOn;
    private List<String> details;

    private Date startCreatedOn;
    private Date endCreatedOn;
}
