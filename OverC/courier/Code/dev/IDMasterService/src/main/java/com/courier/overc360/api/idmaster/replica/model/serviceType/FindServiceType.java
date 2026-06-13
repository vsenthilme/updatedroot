package com.courier.overc360.api.idmaster.replica.model.serviceType;

import lombok.Data;

import java.util.List;

@Data
public class FindServiceType {

    private List<String> companyId;
    private List<String> languageId;
    private List<String> serviceTypeId;
    private List<String> statusId;

}
