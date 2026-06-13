package com.courier.overc360.api.model.transaction;

import lombok.Data;

import java.util.Date;

@Data
public class ConsignmentInvoice {

    private String orgName;

    private String orgAddressLine1;

    private String orgAddressLine2;

    private String orgCity;

    private String orgCountry;

    private String orgPhone;

    private String destName;

    private String destAddressLine1;

    private String destAddressLine2;

    private String destCity;

    private String destCountry;

    private String destPhone;

    private String quantity;

    private String hsCode;

    private String goodsDescription;

    private String itemWeight;

    private String unitValue;

    private String totalValue;

    private String currency;

    private String countryOfOrigin;

    private String incoTerms;

    private String pieces;

    private String weight;

    private String awb;

    private String totalCiv;

    private String getPrepaid;

    private Date createdOn;

}
