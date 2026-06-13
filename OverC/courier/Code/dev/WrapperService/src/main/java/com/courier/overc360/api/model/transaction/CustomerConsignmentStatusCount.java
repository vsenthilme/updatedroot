package com.courier.overc360.api.model.transaction;

import lombok.Data;

@Data
public class CustomerConsignmentStatusCount {

    private Long statusCount;
    private String statusId;
    private String statusText;
}
