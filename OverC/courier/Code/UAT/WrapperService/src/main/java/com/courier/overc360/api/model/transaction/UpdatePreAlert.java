package com.courier.overc360.api.model.transaction;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class UpdatePreAlert {

    @NotBlank(message = "CompanyId is mandatory")
    private String companyId;

    @NotBlank(message = "LanguageId is mandatory")
    private String languageId;

    @NotBlank(message = "PartnerId is mandatory")
    private String partnerId;

    private String masterAirwayBill;

    @NotBlank(message = "Partner House Airway Bill is mandatory")
    private String partnerHouseAirwayBill;

    @NotBlank(message = "Partner Master Airway Bill is mandatory")
    private String partnerMasterAirwayBill;

    private String partnerName;

    private String houseAirwayBill;

    private String totalWeight;

    private String flightNo;

    private Long consoleIndicator;

    private Double consignmentValueLocal;

    private Long manifestIndicator;

    private String flightName;

    private Date estimatedTimeOfDeparture;

    private Date estimatedTimeOfArrival;

    private String noOfPieces;

    private String consignmentValue;

    private Double exchangeRate;

    private Double iata;

    private Double customsInsurance;

    private Double duty;

    private String subCustomerName;

    private String subCustomerId;

    private Double addIata;

    private Double addInsurance;

    private Double customsValue;

    private Double calculatedTotalDuty;

    private String bayanHv;

    private String currency;

    private String description;

    private String consigneeName;

    private String shipper;

    private String origin;

    private String originCode;

    private String consignmentValueKd;

    private String hsCode;

    private String partnerType;

    private String incoTerm;

    private String consignmentLocalId;

    private String hubCode;

    private String hubName;

    private String referenceField1;

    private String referenceField2;

    private String referenceField3;

    private String referenceField4;

    private String referenceField5;

    private String referenceField6;

    private String referenceField7;

    private String referenceField8;

    private String referenceField9;

    private String referenceField10;

}



