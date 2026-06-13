package com.iweb2b.api.integration.model.consignment.dto;

import java.util.List;

import lombok.Data;

@Data
public class FlowLogConsignmentRequest {

    private List<Consignment> consignments;
}