package com.courier.overc360.api.idmaster.replica.model.city;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReplicaCityCompositeKey implements Serializable {

    private static final long serialVersionUID = -7617672247680004647L;

    /*
    'LANG_ID','C_ID','COUNTRY_ID','PROVINCE_ID','DISTRICT_ID',CITY_ID'
     */
    private String languageId;
    private String companyId;
    private String countryId;
    private String provinceId;
    private String districtId;
    private String cityId;

}
