package com.courier.overc360.api.model.transaction;

import lombok.Data;

@Data
public class FindConsignmentOutScanResponse {

    private String storageDescription;

    private String zoneText;

    private String pieceId;

    private String consignmentNo;

    private String partnerId;

    private String masterAirwayBill;
}
