package com.iweb2b.api.integration.model.consignment.dto.flow;

import java.util.List;

import com.iweb2b.api.integration.model.consignment.dto.Consignment;

import lombok.Data;

@Data
public class FlowLogConsignmentRequest {

    private List<Consignment> consignments;
}