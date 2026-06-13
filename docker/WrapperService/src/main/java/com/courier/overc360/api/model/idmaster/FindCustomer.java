package com.courier.overc360.api.model.idmaster;

import lombok.Data;

import java.util.List;

@Data
public class FindCustomer {

    private List<String> languageId;
    private List<String> companyId;
    private List<String> subProductId;
    private List<String> productId;
    private List<String> customerId;
    private List<String> subProductValue;
    private List<String> statusId;

}
