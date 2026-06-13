package com.courier.overc360.api.model.lastmile;


import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

@Data
public class LMDInvoiceLine {

    private Long invoiceLineId;

    private String languageId;

    private String companyId;

    private String invoiceNo;

    private String customerId;

    private Double invoiceAmount;

    private String currencyUnit;

    private String customerName;

    private Long lineNumber;

    private Date fromDate;

    private Date toDate;

    private String noOfShipment;

    private String chargeDescription;

    private Double chargeAmount;

    private String remarks;

    private Long deletionIndicator = 0L;

    private String referenceField1;

    private String referenceField2;

    private String referenceField3;

    private String referenceField4;

    private String referenceField5;

}
