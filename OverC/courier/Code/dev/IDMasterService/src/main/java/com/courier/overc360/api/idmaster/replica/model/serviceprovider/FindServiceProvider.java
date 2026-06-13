package com.courier.overc360.api.idmaster.replica.model.serviceprovider;

import lombok.Data;

import java.util.List;

@Data
public class FindServiceProvider {

    List<String> companyId;
    List<String> languageId;
    List<String> serviceProvidersId;
    List<String> statusId;
}
