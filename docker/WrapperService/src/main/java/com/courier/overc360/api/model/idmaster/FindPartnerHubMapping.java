package com.courier.overc360.api.model.idmaster;

import lombok.Data;

import java.util.List;

@Data
public class FindPartnerHubMapping {

    private List<String> languageId;
    private List<String> companyId;
    private List<String> hubCode;
    private List<String> partnerId;
    private List<String> partnerType;
    private List<String> statusId;

}
