package com.courier.overc360.api.model.idmaster;

import lombok.Data;

import java.util.List;

@Data
public class FindAppUser {

    private List<String> companyId;
    private List<String> languageId;
    private List<String> appUserId;
    private List<String> statusId;



}
