package com.courier.overc360.api.model.transaction;

import lombok.Data;

import java.util.List;

@Data
public class FindConsignmentOutScanMobApp {

    private List<String> languageId;

    private List<String> companyId;

    private List<String> hubCode;

    private List<String> statusId;
}
