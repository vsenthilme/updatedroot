package com.courier.overc360.api.midmile.replica.model.ccr;

import lombok.Data;

@Data
public class UpdateCCR {

    private String customsCcrNo;
    private String ccrId;
    private String primaryDo;
    private String customsKd;
    private String hsCode;
    private Double totalDuty;
    private Double consignmentValue;
    private String loginUserID;
}