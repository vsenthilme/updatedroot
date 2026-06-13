package com.courier.overc360.api.model.transaction;

import lombok.Data;

import java.util.Date;

@Data
public class FindHAWB {

    private String companyId;
    private String languageId;
    private String statusId;
    private Date fromDate;
    private Date toDate;
    private String partnerId;
}
