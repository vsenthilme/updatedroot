package com.courier.overc360.api.idmaster.replica.model.sortingmaster;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReplicaSortingMasterCompositeKey implements Serializable {

    private static final long serialVersionUID = -7617672247680004647L;

    private String languageId;
    private String companyId;
    private String sortingId;
    private String zoneType;
}
