package com.courier.overc360.api.midmile.primary.model.consignment;

import java.util.Date;

public interface ConsignmentInvoice {

     String getOrgName();

     String getOrgAddressLine1();

     String getOrgAddressLine2();

     String getOrgCity();

     String getOrgCountry();

     String getOrgPhone();

     String getDestName();

     String getDestAddressLine1();

     String getDestAddressLine2();

     String getDestCity();

     String getDestCountry();

     String getDestPhone();

     String getHsCode();

     String getGoodsDescription();

     String getItemWeight();

     String getUnitValue();

     String getTotalValue();

     String getQuantity();

     String getCurrency();

     String getCountryOfOrigin();

     String getIncoTerms();

     String getPieces();

     String getWeight();

     String getAwb();

     String getTotalCiv();

    public String getPrepaid();

     Date getCreatedOn();

     Long getConsignmentId();

     String getHouseAirwayBill();
     String getOriginAddress();
     String getDestinationAddress();
     String getMasterAirwayBill();

}