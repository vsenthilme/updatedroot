package com.courier.overc360.api.midmile.primary.model.customscosting;


import lombok.Data;

import java.util.List;

@Data
public class ApproveCustomCostingInput {

    private List<String> companyId;
    private List<String> languageId;
    private List<String> partnerMasterAirWayBill;
}