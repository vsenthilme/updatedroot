package com.courier.overc360.api.idmaster.replica.model.appuser;

import lombok.Data;

import java.util.List;
@Data
public class FindAppUser {

    private List<String> companyId;
    private List<String> languageId;
    private List<String> appUserId;
    private List<String> statusId;



}
