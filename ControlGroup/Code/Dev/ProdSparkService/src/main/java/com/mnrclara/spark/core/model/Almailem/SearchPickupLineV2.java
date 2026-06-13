package com.mnrclara.spark.core.model.Almailem;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SearchPickupLineV2 {

    private List<String> languageId;
    private List<String> companyCodeId;
    private List<String> plantId;
    private List<String> warehouseId;
    private List<String> preOutboundNo;
    private List<String> refDocNumber;
    private List<String> partnerCode;
    private List<Long> lineNumber;
    private List<String> pickupNumber;
    private List<String> itemCode;
    private List<String> actualHeNo;
    private List<String> pickedStorageBin;
    private List<String> pickedPackCode;
    private List<String> assignedPickerId;
    private List<String> levelId;
    private List<Long> outboundOrderTypeId;
<<<<<<< HEAD
    private List<String> manufacturerName;
    private List<String> salesOrderNumber;

    private List<Long> statusId;
=======
    private List<Long> statusId;
    private List<String> salesOrderNumber;
>>>>>>> 81dc64bb940937a238631ec1ef8d3af347b8ec9e
    private Date fromPickConfirmedOn;
    private Date toPickConfirmedOn;

}