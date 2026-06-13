package com.courier.overc360.api.model.lastmile;

import lombok.Data;

@Data
public class FindConsignmentOutScanInput {

    private String languageId;
    private String companyId;
    private String manifestNumber;
    private String statusId;
}
