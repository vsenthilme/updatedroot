package com.courier.overc360.api.idmaster.replica.model.uom;

import lombok.Data;

import java.util.List;

@Data
public class FindUom {

    private List<String> languageId;
    private List<String> companyId;
    private List<String> uomId;
    private List<String> statusId;
}
