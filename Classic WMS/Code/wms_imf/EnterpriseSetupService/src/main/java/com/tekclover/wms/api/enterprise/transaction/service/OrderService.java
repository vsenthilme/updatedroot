package com.tekclover.wms.api.enterprise.transaction.service;

import com.tekclover.wms.api.enterprise.controller.exception.BadRequestException;
import com.tekclover.wms.api.enterprise.transaction.model.warehouse.inbound.v2.InboundOrderV2;
import com.tekclover.wms.api.enterprise.transaction.repository.InboundOrderV2Repository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;

@Slf4j
@Service
public class OrderService {

    @Autowired
    private InboundOrderV2Repository inboundOrderV2Repository;

    //------------------------------------------------------------------------------------------------
    /**
     * @param orderId
     * @param inboundOrderTypeId
     */
    public void updateProcessedIbOrderV2(String orderId, Long inboundOrderTypeId) {
        log.info("rollback - rerun - orderId : " + orderId + "," + inboundOrderTypeId);
        InboundOrderV2 dbInboundOrder = getOrderByIdV2(orderId, inboundOrderTypeId);
        if (dbInboundOrder != null) {
            Long numberOfAttemts = 0L;
            Long attempted = 0L;
            Long processStatusId = 0L;
            if (dbInboundOrder.getNumberOfAttempts() != null) {
                if (dbInboundOrder.getNumberOfAttempts().equals(0L)) {
                    numberOfAttemts = 1L;
                    processStatusId = 0L;
                }
                if (dbInboundOrder.getNumberOfAttempts().equals(1L)) {
                    numberOfAttemts = 2L;
                    processStatusId = 0L;
                }
                if (dbInboundOrder.getNumberOfAttempts().equals(2L)) {
                    numberOfAttemts = 3L;
                    processStatusId = 100L;
                }
                if (dbInboundOrder.getNumberOfAttempts().equals(3L)) {
                    numberOfAttemts = 3L;
                    processStatusId = 100L;
                }
            } else {
                numberOfAttemts = 1L;
                processStatusId = 0L;
            }
            dbInboundOrder.setProcessedStatusId(processStatusId);
            dbInboundOrder.setNumberOfAttempts(numberOfAttemts);
            dbInboundOrder.setOrderProcessedOn(new Date());
            InboundOrderV2 updatedInboundOrder = inboundOrderV2Repository.save(dbInboundOrder);
            log.info("rollback rerun - updatedInboundOrder : " + updatedInboundOrder);
        }
    }

    //-----------------------------V2-------------------------------------------

    /**
     * @param orderId
     * @return
     */
    public InboundOrderV2 getOrderByIdV2(String orderId, Long inboundOrderTypeId) {

        InboundOrderV2 dbInboundOrder = inboundOrderV2Repository.findByRefDocumentNoAndInboundOrderTypeId(orderId, inboundOrderTypeId);

        if (dbInboundOrder != null) {
            return dbInboundOrder;
        } else {
            return null;
        }
    }

    public InboundOrderV2 createInboundOrdersV2(InboundOrderV2 newInboundOrderV2) {
        InboundOrderV2 dbInboundOrder = inboundOrderV2Repository.
                findByCompanyCodeAndBranchCodeAndWarehouseIDAndRefDocumentNoAndInboundOrderTypeId(
                        newInboundOrderV2.getCompanyCode(), newInboundOrderV2.getBranchCode(), newInboundOrderV2.getWarehouseID(),
                        newInboundOrderV2.getOrderId(), newInboundOrderV2.getInboundOrderTypeId());
        if (dbInboundOrder != null) {
            throw new BadRequestException("Order is getting Duplicated");
        }
        InboundOrderV2 inboundOrderV2 = inboundOrderV2Repository.save(newInboundOrderV2);
        return inboundOrderV2;
    }

    /**
     * @param orderId
     * @return
     */
    public InboundOrderV2 updateProcessedInboundOrderV2(String orderId, Long inboundOrderTypeId, Long processStatusId) throws ParseException {
        InboundOrderV2 dbInboundOrder = getOrderByIdV2(orderId, inboundOrderTypeId);
        log.info("orderId : " + orderId);
        log.info("dbInboundOrder : " + dbInboundOrder);
        if (dbInboundOrder != null) {
            dbInboundOrder.setProcessedStatusId(processStatusId);
            dbInboundOrder.setOrderProcessedOn(new Date());
            InboundOrderV2 inboundOrder = inboundOrderV2Repository.save(dbInboundOrder);
            return inboundOrder;
        }
        return dbInboundOrder;
    }
}