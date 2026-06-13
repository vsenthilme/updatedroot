package com.tekclover.wms.core.model.idmaster;

import lombok.Data;

import java.util.List;

@Data
public class FindNumberRange {
<<<<<<< HEAD
    private String warehouseId;
=======
    private String companyCodeId;
    private String plantId;
    private String warehouseId;
    private List<String> languageId;
>>>>>>> 81dc64bb940937a238631ec1ef8d3af347b8ec9e
    private List<Long> numberRangeCode;
    private List<Long> fiscalYear;
}
