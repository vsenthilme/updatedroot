package com.courier.overc360.api.idmaster.replica.model.reasonslistdelivery;

import lombok.Data;

import java.util.List;
@Data
public class FindReasonsListDelivery {
    private List<String> companyId;
    private List<String> languageId;
    private List<String> reasonsId;
    private List<String> statusId;

}
