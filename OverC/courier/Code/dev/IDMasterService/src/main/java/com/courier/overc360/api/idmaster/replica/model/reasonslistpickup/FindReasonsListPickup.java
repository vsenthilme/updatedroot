package com.courier.overc360.api.idmaster.replica.model.reasonslistpickup;

import lombok.Data;

import java.util.List;

@Data
public class FindReasonsListPickup {
    private List<String> companyId;
    private List<String> languageId;
    private List<String> reasonsId;
    private List<String> statusId;
}
