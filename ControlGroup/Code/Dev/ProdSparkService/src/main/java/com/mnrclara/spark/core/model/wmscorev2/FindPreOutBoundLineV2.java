package com.mnrclara.spark.core.model.wmscorev2;

import lombok.Data;

import java.util.List;

@Data
public class FindPreOutBoundLineV2 {

    private List<String> languageId;
    private List<String> companyCodeId;
    private List<String> plantId;
    private List<String> warehouseId;
    private List<String> refDocNumber;
    private List<String> preOutboundNo;
    private List<String> partnerCode;
    private List<Long> lineNumber;
<<<<<<< HEAD
    private List<Long> statusId;
=======
>>>>>>> 81dc64bb940937a238631ec1ef8d3af347b8ec9e
    private List<String> itemCode;
}
