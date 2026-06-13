package com.tekclover.wms.api.transaction.service;

import com.tekclover.wms.api.transaction.controller.exception.BadRequestException;
import com.tekclover.wms.api.transaction.model.warehouse.inbound.v2.InboundOrderV2;
import com.tekclover.wms.api.transaction.repository.InboundOrderV2Repository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderSaveService {

	@Autowired
	TransactionService transactionService;

	@Autowired
	private InboundOrderV2Repository inboundOrderV2Repository;

	public InboundOrderV2 createInboundOrdersV2(InboundOrderV2 newInboundOrderV2) {
		InboundOrderV2 dbInboundOrder = inboundOrderV2Repository.
				findByRefDocumentNoAndInboundOrderTypeId(newInboundOrderV2.getOrderId(), newInboundOrderV2.getInboundOrderTypeId());
		if(dbInboundOrder != null) {
			throw new BadRequestException("Order is getting Duplicated");
		}
		InboundOrderV2 inboundOrderV2 = inboundOrderV2Repository.save(newInboundOrderV2);
		log.info("IB Order Saved ---> " + inboundOrderV2.getProcessedStatusId());

		//process the order
		if(inboundOrderV2.getProcessedStatusId() == 0) {
			try {
				log.info("trigger IB condition passed");
				triggerInboundOrderProcessing(inboundOrderV2.getInboundOrderHeaderId());
			} catch (Exception e) {
				log.error("Inbound Order Processing Exception..!");
				throw new BadRequestException(e.toString());
			}
		}
		return inboundOrderV2;
	}

	@Async
	public void triggerInboundOrderProcessing(Long inboundOrderHeaderId) throws Exception {
		log.info("Triggering InboundOrder Processing..! " + inboundOrderHeaderId);
		transactionService.processInboundOrder(inboundOrderHeaderId);
		log.info("Finished InboundOrder Processing..! " + inboundOrderHeaderId);
	}
}