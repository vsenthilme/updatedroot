package com.courier.overc360.api.model.transaction;


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
