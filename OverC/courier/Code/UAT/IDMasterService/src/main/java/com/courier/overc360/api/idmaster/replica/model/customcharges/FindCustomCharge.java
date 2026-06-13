package com.courier.overc360.api.idmaster.replica.model.customcharges;


import lombok.Data;

import java.util.List;

@Data
public class FindCustomCharge {

    private List<String> languageId;
    private List<String> companyId;


}
