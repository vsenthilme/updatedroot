package com.courier.overc360.api.idmaster.replica.model.usertype;

import lombok.Data;

import java.util.List;

@Data
public class FindUserType {

    private String companyId;
    private List<Long> userTypeId;
    private List<String> languageId;
    private List<String> statusId;

}
