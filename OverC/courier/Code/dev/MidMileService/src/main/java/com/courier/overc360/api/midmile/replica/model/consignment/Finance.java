package com.courier.overc360.api.midmile.replica.model.consignment;

public interface Finance {

    String getHouseAirwayBill();
    String getCompanyId();
    String getLanguageId();
    String getPartnerId();
    String getAddOriginDetails();
    String getAddDestinationDetails();
    String getGoodsDescription();
    String getHsCode();
    String getNoOfPieceHawb();
    String getIncoTerms();
    Double getWeight();
    Double getCeilingValue();
    Double getChargeableWeight();
    Double getFrightCharge();
    Double getCodCharge();
    Double getFulfilmentCharge();
    Double getRtoCharge();
    Double getAsrCharge();
    Double getMovementCharge();
    Double getTruckCharge();
    Double getPaymentCollected();
    Double getTotalLmdCharges();
    Double getOutboundClearance();
}
