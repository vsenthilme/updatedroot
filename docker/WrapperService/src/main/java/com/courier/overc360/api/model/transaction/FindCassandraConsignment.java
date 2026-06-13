package com.courier.overc360.api.model.transaction;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class FindCassandraConsignment {

    private List<String> masterAirwayBill;
    private List<Long> consignmentId;
    private Date startDate;
    private Date endDate;
}