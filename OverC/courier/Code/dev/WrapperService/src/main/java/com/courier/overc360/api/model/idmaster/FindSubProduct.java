package com.courier.overc360.api.model.idmaster;

import lombok.Data;

import java.util.List;

@Data
public class FindSubProduct {

    private List<String> languageId;
    private List<String> companyId;
    private List<String> subProductId;
    private List<String> statusId;
    private List<String> subProductValue;

}
