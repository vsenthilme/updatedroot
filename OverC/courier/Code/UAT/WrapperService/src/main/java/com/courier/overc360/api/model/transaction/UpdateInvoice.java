package com.courier.overc360.api.model.transaction;

import lombok.Data;

import java.util.Date;

@Data
public class UpdateInvoice {

    String houseAirwayBill;
    String invoiceName;
    String invoiceUrl;
    String updatedBy;
    Date updatedOn;
}
