package com.courier.overc360.api.midmile.replica.model.delivery;

import lombok.Data;

import java.io.Serializable;


@Data
public class ReplicaDeliveryCompositeKey implements Serializable {

    private static final long serialVersionUID = -7617672247680004647L;

    private String languageId;
    private String companyId;
    private String pieceId;
    private String houseAirwayBill;
}
