package com.courier.overc360.api.model.transaction;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class InvoiceForm {

    private String orgName;
    private String originAddress;
    private String orgCity;
    private String orgCountry;
    private String orgPhone;
    private String destName;
    private String destinationAddress;
    private String destCity;
    private String destCountry;
    private String destPhone;

    private Long consignmentId;
    private String houseAirwayBill;
    private String quantity;
    private String currency;
    private String countryOfOrigin;
    private String incoTerms;
    private String pieces;
    private String weight;
    private String awb;
    private String totalCiv;
    private String prepaid;
    private Date createdOn;

    List<InvoiceFormLine> invoiceFormLines;
}