package com.courier.overc360.api.midmile.replica.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class FindPreAlertManifest {

    private List<Long> consignmentId;
    private List<String> languageId;
    private List<String> companyId;
    private List<String> partnerId;
    private List<String> statusId;
    private List<Long> consoleIndicator;
    private List<Long> manifestIndicator;
}