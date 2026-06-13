package com.courier.overc360.api.midmile.replica.model.dto;


import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class FindInvoice {

    private Date fromDate;
    private Date toDate;
    private List<String> partnerMasterAirwayBill;
    private String partnerId;

}
