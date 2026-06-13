package com.tekclover.wms.api.transaction.model.warehouse.inbound.v2;

import com.tekclover.wms.api.transaction.model.warehouse.inbound.InboundOrder;

import lombok.Data;

import javax.persistence.*;

import java.util.Set;

@Entity
@Table
@Data
public class InboundOrderV2 extends InboundOrder{

    private String branchCode;
    private String companyCode;
	private String returnOrderReference;

    private String transferOrderNumber;

	@OneToMany(cascade = CascadeType.ALL,mappedBy = "orderId",fetch = FetchType.EAGER)
    private Set<InboundOrderLinesV2> line;
	
}