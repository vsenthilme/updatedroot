package com.courier.overc360.api.idmaster.replica.model.loadtype;

import lombok.Data;

import java.util.List;

@Data
public class FindLoadType {

    private List<String> loadTypeId;
    private List<String> languageId;
    private List<String> companyId;
    private List<String> statusId;

}
