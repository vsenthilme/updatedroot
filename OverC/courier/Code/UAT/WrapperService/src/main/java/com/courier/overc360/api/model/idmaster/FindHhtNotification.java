package com.courier.overc360.api.model.idmaster;

import lombok.Data;

import java.util.List;

@Data
public class FindHhtNotification {

    private List<String> languageId;
    private List<String> companyId;
    private List<String> userId;
    private List<String> deviceId;
    private List<String> tokenId;

}
