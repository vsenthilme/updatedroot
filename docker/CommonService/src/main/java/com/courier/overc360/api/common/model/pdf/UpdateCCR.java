package com.courier.overc360.api.common.model.pdf;

import lombok.Data;

@Data
public class UpdateCCR {

    private String customsCcrNo;
    private String ccrId;
    private String primaryDo;
    private String customsKd;
    private String hsCode;
    private String consignmentValue;
    private String totalDuty;
}