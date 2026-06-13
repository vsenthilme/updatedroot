package com.courier.overc360.api.idmaster.replica.model.iata;

import lombok.Data;

import java.util.List;

@Data
public class FindIata {

    private List<String> languageId;
    private List<String> companyId;
    private List<String> origin;
    private List<String> originCode;
    private List<String> statusId;

}
