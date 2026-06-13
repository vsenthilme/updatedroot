package com.mnrclara.spark.core.model.Almailem;

import lombok.Data;

<<<<<<< HEAD
import java.util.Date;
=======
>>>>>>> 81dc64bb940937a238631ec1ef8d3af347b8ec9e
import java.util.List;

@Data
public class FindGrLineV2 {

    private List<String> languageId;
    private List<String> companyCodeId;
    private List<String> plantId;
    private List<String> warehouseId;
    private List<String> manufacturerCode;
    private List<String> barcodeId;
    private List<String> manufacturerName;
    private List<String> origin;
    private List<String> brand;
    private List<String> preInboundNo;
    private List<String> refDocNumber;
    private List<String> caseCode;
    private List<Long> lineNo;
    private List<String> itemCode;
    private List<Long> statusId;
    private List<String> interimStorageBin;
    private List<String> rejectType;
    private List<String> rejectReason;
    private List<String> packBarcodes;
<<<<<<< HEAD

    private List<Long> inboundOrderTypeId;
    private Date startCreatedOn;
    private Date endCreatedOn;
=======
>>>>>>> 81dc64bb940937a238631ec1ef8d3af347b8ec9e
}
