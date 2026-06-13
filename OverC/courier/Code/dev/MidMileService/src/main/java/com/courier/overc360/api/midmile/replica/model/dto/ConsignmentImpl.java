package com.courier.overc360.api.midmile.replica.model.dto;

import java.util.Date;

public interface ConsignmentImpl {

//    @Column(name = "CONSIGNMENT_ID")
//    private Long consignmentId;
//
//    @Column(name = "NO_OF_PIECE_HAWB", columnDefinition = "nvarchar(50)")
//    private String noOfPieceHawb;
//
//    @Column(name = "PARTNER_HOUSE_AB", columnDefinition = "nvarchar(50)")
//    private String partnerHouseAirwayBill;
//
//    @Column(name = "NO_OF_PACKAGE_HAWB", columnDefinition = "nvarchar(50)")
//    private String noOfPackageHawb;
//
//    @Column(name = "DECLARED_VALUE", columnDefinition = "nvarchar(50)")
//    private String declaredValue;
//
//    @Column(name = "CONSIGNMENT_CURRENCY", columnDefinition = "nvarchar(50)")
//    private String consignmentCurrency;
//
//    @Column(name = "INCO_TERMS", columnDefinition = "nvarchar(50)")
//    private String incoTerms;
//
//    @Column(name = "COUNTRY_OF_ORIGIN", columnDefinition = "nvarchar(50)")
//    private String countryOfOrigin;
//
//    @Column(name = "GROSS_WEIGHT", columnDefinition = "nvarchar(50)")
//    private String grossWeight;
//
//    @Column(name = "PAYMENT_TYPE", columnDefinition = "nvarchar(50)")
//    private String paymentType;
//
//    @Column(name = "CTD_ON")
//    private Date createdOn;

    Long getConsignmentId();

    String getNoOfPieceHawb();

    String getPartnerHouseAirwayBill();

    String getNoOfPackageHawb();

    String getDeclaredValue();

    String getConsignmentCurrency();

    String getIncoTerms();

    String getCountryOfOrigin();

    String getGrossWeight();

    String getPaymentType();

    Date getCreatedOn();

    String getPartnerMasterAirwayBill();

    Long getPMawbCount();

    String getLanguageId();

    String getCompanyId();

}