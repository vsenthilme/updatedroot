package com.courier.overc360.api.midmile.replica.model.customsclearanceinvoice;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReplicaCustomsClearanceInvoiceCompositeKey implements Serializable {

    private static final long serialVersionUID = -7617672247680004647L;

    /**
     * `LANG_ID`, `C_ID`, `PARTNER_HOUSE_AIRWAY_BILL`, `HOUSE_AIRWAY_BILL`,`INVOICE_NO`
     */

    private String languageId;
    private String companyId;
    private String partnerHouseAirwayBill;
    private String houseAirwayBill;
    private String invoiceNo;
}
