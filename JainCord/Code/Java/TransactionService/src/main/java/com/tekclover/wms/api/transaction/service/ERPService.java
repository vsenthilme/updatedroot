package com.tekclover.wms.api.transaction.service;


import com.tekclover.wms.api.transaction.controller.exception.BadRequestException;
import com.tekclover.wms.api.transaction.model.warehouse.ERP.InboundEntity;
import com.tekclover.wms.api.transaction.model.warehouse.ERP.OutboundEntity;
import com.tekclover.wms.api.transaction.model.warehouse.inbound.v2.InboundOrderLinesV2;
import com.tekclover.wms.api.transaction.model.warehouse.inbound.v2.InboundOrderV2;
import com.tekclover.wms.api.transaction.model.warehouse.outbound.v2.OutboundOrderLineV2;
import com.tekclover.wms.api.transaction.model.warehouse.outbound.v2.OutboundOrderV2;
import com.tekclover.wms.api.transaction.repository.ERP.OutboundEntityRepository;
import com.tekclover.wms.api.transaction.repository.InboundOrderLinesV2Repository;
import com.tekclover.wms.api.transaction.repository.InboundOrderV2Repository;
import com.tekclover.wms.api.transaction.repository.ERP.InboundEntityRepository;
import com.tekclover.wms.api.transaction.repository.OutboundOrderV2Repository;
import com.tekclover.wms.api.transaction.util.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class ERPService extends BaseService {

    @Autowired
    InboundEntityRepository inboundEntityRepository;

    @Autowired
    InboundOrderV2Repository inboundOrderV2Repository;

    @Autowired
    OutboundOrderV2Repository outboundOrderV2Repository;

    @Autowired
    InboundOrderLinesV2Repository inboundOrderLinesV2Repository;


    // Create Inbound From ERP
    public List<InboundEntity> createInbound(List<InboundEntity> inboundEntities) {
        List<InboundEntity> inboundEntityList = new ArrayList<>();
        try {
            inboundEntities.stream().forEach(ib -> {
                InboundEntity newInbound = new InboundEntity();
                BeanUtils.copyProperties(ib, newInbound, CommonUtils.getNullPropertyNames(ib));
                newInbound.setCreatedOn(new Date());
                inboundEntityRepository.save(newInbound);
                inboundEntityList.add(newInbound);
            });
        } catch (Exception e) {
            throw new BadRequestException("Create Inbound Failed From ERP");
        }
        return inboundEntityList;
    }


    /**
     * @param inboundEntityList
     * @return
     */
    public List<InboundOrderV2> createIbOrder(List<InboundEntity> inboundEntityList) {
        List<InboundOrderV2> inboundOrderList = new ArrayList<>();
        try {
            Map<String, InboundOrderV2> orderMap = new HashMap<>();

            for (InboundEntity ib : inboundEntityList) {
                InboundOrderV2 inboundOrder;

                Optional<InboundOrderV2> duplicate = inboundOrderV2Repository.findByRefDocumentNoAndPieceNo(ib.getOrderRef(), ib.getPieceNo());
                if (duplicate.isPresent()) {
                    log.info("Record is Getting Duplicate RefDocNo {}, PieceNo {}", ib.getOrderRef(), ib.getPieceNo());
                    continue;
                }

                // Check if the order already exists in the map
                if (orderMap.containsKey(ib.getOrderRef())) {
                    inboundOrder = orderMap.get(ib.getOrderRef());
                } else {
                    // Create a new header if it doesn't exist
                    inboundOrder = new InboundOrderV2();
                    BeanUtils.copyProperties(ib, inboundOrder, CommonUtils.getNullPropertyNames(ib));
                    inboundOrder.setOrderId(ib.getOrderRef());
                    inboundOrder.setSystem(ib.getSystem());
                    inboundOrder.setCompanyCode("1200");
                    inboundOrder.setBranchCode("2200");
                    inboundOrder.setWarehouseID("3200");
                    inboundOrder.setRefDocumentType(ib.getOrderType());
                    inboundOrder.setRefDocumentNo(ib.getOrderRef());
                    inboundOrder.setTransferOrderNumber(ib.getOrderRef());
                    inboundOrder.setOrderReceivedOn(new Date());
                    inboundOrder.setInboundOrderTypeId(1L);
                    inboundOrder.setOrderReceivedOn(new Date());
                    inboundOrder.setProcessedStatusId(0L);
                    inboundOrder.setPieceNo(ib.getPieceNo());

                    // Initialize the lines set
                    inboundOrder.setLines(new HashSet<>());
                    orderMap.put(ib.getOrderRef(), inboundOrder);
                }

                // Create a new line
                InboundOrderLinesV2 line = new InboundOrderLinesV2();
                BeanUtils.copyProperties(ib, line, CommonUtils.getNullPropertyNames(ib));
                line.setCompanyCode("1200");
                line.setBranchCode("2200");
                line.setLineReference(ib.getLineNo());
                line.setGrade(ib.getGrade());
                line.setGsm(String.valueOf(ib.getGsm()));
                line.setItemText(ib.getItemDesc());
                line.setTransferOrderNumber(ib.getOrderRef());
                line.setInboundOrderTypeId(1L);
                line.setManufacturerCode(MFR_NAME);
                line.setManufacturerName(MFR_NAME);
                line.setUom(ib.getUom());
                line.setMeter(String.valueOf(ib.getMtr()));
                line.setOrderedQty(ib.getMtr());
                line.setExpectedQty(ib.getMtr());
                line.setReceivedQty(ib.getMtr());
//                line.setGrade(ib.getGrade());
//                line.setColor(ib.getColor());
                line.setPieceId(ib.getPieceNo());
                line.setExpectedDate(ib.getMrnDate());
                line.setReceivedDate(ib.getMrnDate());
                // Add the line to the header
                inboundOrder.getLines().add(line);
            }

            // Save all headers along with their lines
            for (InboundOrderV2 inboundOrder : orderMap.values()) {
                inboundOrderList.add(inboundOrderV2Repository.save(inboundOrder));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inboundOrderList;
    }


    /**
     * @param outboundEntityList
     * @return
     */
    public List<OutboundOrderV2> createOBOrder(List<OutboundEntity> outboundEntityList) {
        List<OutboundOrderV2> outboundOrderV2List = new ArrayList<>();
        try {
            Map<String, OutboundOrderV2> orderMap = new HashMap<>();
            for (OutboundEntity ob : outboundEntityList) {
                OutboundOrderV2 orderV2;

                // Duplicate
//                Optional<OutboundOrderV2> duplicate = outboundOrderV2Repository.findByRefDocumentNoAndPieceNo(ob.getOrderRef(), ob.getPieceNo());
//                if (duplicate.isPresent()) {
//                    log.info("Outbound Order RefDocNo {}, PieceNo {} is Getting Duplicate ", ob.getOrderRef(), ob.getPieceNo());
//                    continue;
//                }
                // Check if the order already exists in the map
                if (orderMap.containsKey(ob.getOrderRef())) {
                    orderV2 = orderMap.get(ob.getOrderRef());
                } else {
                    // Create a new header if it doesn't exist
                    orderV2 = new OutboundOrderV2();
                    BeanUtils.copyProperties(ob, orderV2, CommonUtils.getNullPropertyNames(ob));
                    orderV2.setOrderId(ob.getOrderRef());
                    orderV2.setSystem(ob.getSystem());
                    orderV2.setSalesOrderNumber(ob.getOrderRef());
                    orderV2.setCompanyCode("1200");
                    orderV2.setBranchCode("2200");
                    orderV2.setWarehouseID("3200");
                    orderV2.setPartnerCode("2200");
                    orderV2.setSalesInvoiceDate(ob.getOrderDate());
                    orderV2.setPieceNo(ob.getPieceNo());
                    orderV2.setCustomerType("INVOICE");

                    // Set RefDocumentType
                    orderV2.setRefDocumentType(getOutboundOrderTypeDesc("1200", "2200",
                            "EN", "3200", 3L));

                    orderV2.setRefDocumentNo(ob.getOrderRef());
                    orderV2.setOutboundOrderTypeID(3L);
                    orderV2.setOrderReceivedOn(new Date());
                    orderV2.setProcessedStatusId(0L);

                    // Initialize the lines set
                    orderV2.setLines(new HashSet<>());
                    orderMap.put(ob.getOrderRef(), orderV2);
                }

                // Create a new line
                OutboundOrderLineV2 line = new OutboundOrderLineV2();
                BeanUtils.copyProperties(ob, line, CommonUtils.getNullPropertyNames(ob));
                line.setFromCompanyCode("1200");
                line.setSourceBranchCode("2200");
                line.setLineReference(ob.getLineNo());
                line.setItemText(ob.getItemDesc());
                line.setTransferOrderNumber(ob.getOrderRef());
                line.setOutboundOrderTypeID(3L);
                line.setManufacturerCode(MFR_NAME);
                line.setManufacturerName(MFR_NAME);
                line.setExpectedQty(ob.getOrderQty());
                line.setOrderedQty(ob.getOrderQty());
                line.setPackQty(ob.getOrderQty());
                line.setPieceId(ob.getPieceNo());
//                line.setDa(ob.getOrderDate());
//                line.setReceivedDate(ob.getOrderDate());
                // Add the line to the header
                orderV2.getLines().add(line);
            }

            // Save all headers along with their lines
            for (OutboundOrderV2 orderV2 : orderMap.values()) {
                outboundOrderV2List.add(outboundOrderV2Repository.save(orderV2));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outboundOrderV2List;
    }

}
