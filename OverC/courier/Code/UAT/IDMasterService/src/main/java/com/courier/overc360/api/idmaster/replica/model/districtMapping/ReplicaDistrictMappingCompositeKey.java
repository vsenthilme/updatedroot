package com.courier.overc360.api.idmaster.replica.model.districtMapping;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReplicaDistrictMappingCompositeKey  implements Serializable {

    private static final long serialVersionUID = -7617672247680004647L;

    private String districtId;
    private String languageId;
    private String companyId;
    private String partnerId;
}
