package com.courier.overc360.api.idmaster.primary.model.districtMapping;

import lombok.Data;

import java.io.Serializable;

@Data
public class DistrictMappingCompositeKey implements Serializable {

    private static final long serialVersionUID = -7617672247680004647L;

    private String districtId;
    private String languageId;
    private String companyId;
    private String partnerId;

}
