package com.tekclover.wms.api.mfg.model.sorting;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SearchSorting {

    private List<String> companyCodeId;
    private List<String> languageId;
    private List<String> plantId;
    private List<String> warehouseId;
    private List<String> productionOrderNo;
    private List<Long> productionOrderLineNo;
    private List<String> receipeId;
    private List<String> operationNumber;
    private List<String> itemCode;

    private List<Sorting> sorting;
    private List<String> statusDescription; // Add this field
    private List<String> updatedBy;

    private Date startUpdatedOn;
    private Date endUpdatedOn;

    private Date startCreatedOn;
    private Date endCreatedOn;
    private Date startConfirmedOn;
    private Date endConfirmedOn;

}
