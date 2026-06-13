package com.courier.overc360.api.model.idmaster;

import lombok.Data;

import java.util.List;

@Data
public class FindProduct {

    private List<String> languageId;
    private List<String> companyId;
    private List<String> subProductId;
    private List<String> productId;
    private List<String> statusId;
    private List<String> subProductValue;

}
