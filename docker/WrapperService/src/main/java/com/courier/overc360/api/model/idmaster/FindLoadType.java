package com.courier.overc360.api.model.idmaster;

import lombok.Data;

import java.util.List;

@Data
public class FindLoadType {

    private List<String> languageId;
    private List<String> companyId;
    private List<String> loadTypeId;
    private List<String> statusId;

}
