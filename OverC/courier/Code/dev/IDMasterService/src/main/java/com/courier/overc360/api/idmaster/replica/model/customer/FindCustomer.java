package com.courier.overc360.api.idmaster.replica.model.customer;

import lombok.Data;

import java.util.List;

@Data
public class FindCustomer {

    private List<String> languageId;
    private List<String> companyId;
    private List<String> customerId;
    private List<String> productId;
    private List<String> subProductId;
    private List<String> subProductValue;
    private List<String> statusId;
    private List<String> hubCode;
    private List<String> referenceField2;

}
