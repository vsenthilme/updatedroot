package com.courier.overc360.api.midmile.replica.model.invoice;


import lombok.Data;

import java.util.Date;

@Data
public class LMDInvoice {

    private String customerId;
    private Date fromDate;
    private Date toDate;

}
