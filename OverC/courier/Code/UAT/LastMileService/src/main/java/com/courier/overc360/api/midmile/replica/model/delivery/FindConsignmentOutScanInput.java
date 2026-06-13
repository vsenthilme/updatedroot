package com.courier.overc360.api.midmile.replica.model.delivery;

import lombok.Data;

@Data
public class FindConsignmentOutScanInput {

    private String languageId;
    private String companyId;
    private String manifestNumber;
    private String statusId;
}
