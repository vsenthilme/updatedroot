package com.courier.overc360.api.midmile.replica.model.consignment;

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
