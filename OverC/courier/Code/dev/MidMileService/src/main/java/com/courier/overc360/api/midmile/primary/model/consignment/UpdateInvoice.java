package com.courier.overc360.api.midmile.primary.model.consignment;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UpdateInvoice {

    String houseAirwayBill;
    String invoiceName;
    String invoiceUrl;
    String updatedBy;
    Date updatedOn;
}
