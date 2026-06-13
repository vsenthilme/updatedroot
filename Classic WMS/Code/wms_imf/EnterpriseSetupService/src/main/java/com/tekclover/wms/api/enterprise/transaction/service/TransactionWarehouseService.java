package com.tekclover.wms.api.enterprise.transaction.service;

import com.tekclover.wms.api.enterprise.controller.exception.BadRequestException;
import com.tekclover.wms.api.enterprise.model.warehouse.Warehouse;
import com.tekclover.wms.api.enterprise.repository.WarehouseRepository;
import com.tekclover.wms.api.enterprise.transaction.model.warehouse.inbound.v2.*;
import com.tekclover.wms.api.enterprise.transaction.util.CommonUtils;
import com.tekclover.wms.api.enterprise.transaction.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Slf4j
@Service
public class TransactionWarehouseService extends BaseService {

    @Autowired
    OrderService orderService;

    @Autowired
    WarehouseRepository warehouseRepository;

    /**
     * @return
     */
    private RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }

//==================================================Inbound V2===========================================================================

    /**
     * @param asnv2
     * @return
     */
    public InboundOrderV2 postWarehouseASNV2(ASNV2 asnv2) {
        log.info("ASNV2Header received from External: " + asnv2);
        InboundOrderV2 savedAsnV2Header = saveASNV2(asnv2);
        log.info("savedAsnV2Header: " + savedAsnV2Header);
        return savedAsnV2Header;
    }

    // POST ASNV2Header
    private InboundOrderV2 saveASNV2(ASNV2 asnv2) {
        try {
            ASNHeaderV2 asnV2Header = asnv2.getAsnHeader();
            List<ASNLineV2> asnLineV2s = asnv2.getAsnLine();
            InboundOrderV2 apiHeader = new InboundOrderV2();
            BeanUtils.copyProperties(asnV2Header, apiHeader, CommonUtils.getNullPropertyNames(asnV2Header));

            apiHeader.setOrderId(asnV2Header.getAsnNumber());
            apiHeader.setCompanyCode(asnV2Header.getCompanyCode());
            apiHeader.setBranchCode(asnV2Header.getBranchCode());
            apiHeader.setRefDocumentNo(asnV2Header.getAsnNumber());

            apiHeader.setOrderReceivedOn(new Date());
            apiHeader.setMiddlewareId(asnV2Header.getMiddlewareId());
            apiHeader.setMiddlewareTable(asnV2Header.getMiddlewareTable());

            apiHeader.setIsCancelled(asnV2Header.getIsCancelled());
            apiHeader.setIsCompleted(asnV2Header.getIsCompleted());
            apiHeader.setUpdatedOn(asnV2Header.getUpdatedOn());

            apiHeader.setLanguageId(asnV2Header.getLanguageId() != null ? asnV2Header.getLanguageId() : LANG_ID);
//            if (asnV2Header.getWarehouseId() != null && !asnV2Header.getWarehouseId().isBlank()) {
                apiHeader.setWarehouseID(asnV2Header.getWarehouseId());
//            } else {
                // Get Warehouse
//                Optional<Warehouse> dbWarehouse =
//                        warehouseRepository.findByCompanyCodeIdAndPlantIdAndLanguageIdAndDeletionIndicator(
//                                asnV2Header.getCompanyCode(),
//                                asnV2Header.getBranchCode(),
//                                asnV2Header.getLanguageId() != null ? asnV2Header.getLanguageId() : "EN",
//                                0L
//                        );
//                log.info("dbWarehouse : " + dbWarehouse);
//                apiHeader.setWarehouseID(dbWarehouse.get().getWarehouseId());
//            }

            if (asnV2Header.getInboundOrderTypeId() != null) {
                apiHeader.setInboundOrderTypeId(asnV2Header.getInboundOrderTypeId());
            } else {
                apiHeader.setInboundOrderTypeId(1L);                                            //Default
            }
            apiHeader.setRefDocumentType(getInboundOrderTypeDesc(apiHeader.getCompanyCode(), apiHeader.getBranchCode(),
                                                                 apiHeader.getLanguageId(), apiHeader.getWarehouseID(),
                                                                 apiHeader.getInboundOrderTypeId()));

            Set<InboundOrderLinesV2> orderLines = new HashSet<>();
            for (ASNLineV2 asnLineV2 : asnLineV2s) {
                InboundOrderLinesV2 apiLine = new InboundOrderLinesV2();
                BeanUtils.copyProperties(asnLineV2, apiLine, CommonUtils.getNullPropertyNames(asnLineV2));

                apiLine.setLineReference(asnLineV2.getLineReference());            // IB_LINE_NO
                apiLine.setItemCode(asnLineV2.getSku());                            // ITM_CODE
                apiLine.setItemText(asnLineV2.getSkuDescription());                // ITEM_TEXT
                apiLine.setContainerNumber(asnLineV2.getContainerNumber());            // CONT_NO
                if (asnLineV2.getSupplierCode() != null) {
                    apiLine.setSupplierCode(asnLineV2.getSupplierCode());                // PARTNER_CODE
                } else {
                    apiLine.setSupplierCode(asnV2Header.getSupplierCode());
                }
                apiLine.setSupplierPartNumber(asnLineV2.getSupplierPartNumber());  // PARTNER_ITM_CODE

                if (asnLineV2.getManufacturerName() != null) {
                    apiLine.setManufacturerName(asnLineV2.getManufacturerName());        // BRAND_NM
                } else {
                    if (apiHeader.getCompanyCode().equalsIgnoreCase(COMPANY_CODE)) {
                        apiLine.setManufacturerName(getMfrName(COMPANY_CODE));
                    }
                }
                if (asnLineV2.getManufacturerCode() != null) {
                    apiLine.setManufacturerCode(asnLineV2.getManufacturerCode());
                } else {
                    if (apiHeader.getCompanyCode().equalsIgnoreCase(COMPANY_CODE)) {
                        apiLine.setManufacturerCode(getMfrName(COMPANY_CODE));
                    }
                }
                apiLine.setOrigin(asnLineV2.getOrigin());
                apiLine.setCompanyCode(asnLineV2.getCompanyCode());
                apiLine.setBranchCode(asnLineV2.getBranchCode());
                apiLine.setExpectedQty(asnLineV2.getExpectedQty());
                apiLine.setSupplierName(asnLineV2.getSupplierName());
                apiLine.setBrand(asnLineV2.getBrand());
                apiLine.setOrderId(apiHeader.getOrderId());
//				apiLine.setInboundOrderHeaderId(apiHeader.getInboundOrderHeaderId());
                apiLine.setManufacturerFullName(asnLineV2.getManufacturerFullName());
                apiLine.setPurchaseOrderNumber(asnLineV2.getPurchaseOrderNumber());
                apiHeader.setPurchaseOrderNumber(asnLineV2.getPurchaseOrderNumber());

                if (asnV2Header.getInboundOrderTypeId() != null) {
                    apiLine.setInboundOrderTypeId(asnV2Header.getInboundOrderTypeId());
                } else {
                    apiLine.setInboundOrderTypeId(1L);                                            //Default
                }

                apiLine.setSupplierInvoiceNo(asnLineV2.getSupplierInvoiceNo());
                apiLine.setReceivedBy(asnLineV2.getReceivedBy());
                apiLine.setReceivedQty(asnLineV2.getReceivedQty());
                apiLine.setReceivedDate(asnLineV2.getReceivedDate());
                apiLine.setIsCancelled(asnLineV2.getIsCancelled());
                apiLine.setIsCompleted(asnLineV2.getIsCompleted());

                apiLine.setMiddlewareHeaderId(asnLineV2.getMiddlewareHeaderId());
                apiLine.setMiddlewareId(asnLineV2.getMiddlewareId());
                apiLine.setMiddlewareTable(asnLineV2.getMiddlewareTable());

                if (asnLineV2.getExpectedDate() != null) {
                    if (asnLineV2.getExpectedDate().contains("-")) {
                        // EA_DATE
                        try {
                            Date reqDelDate = new Date();
                            if (asnLineV2.getExpectedDate().length() > 10) {
                                reqDelDate = DateUtils.convertStringToDateWithTime(asnLineV2.getExpectedDate());
                            }
                            if (asnLineV2.getExpectedDate().length() == 10) {
                                reqDelDate = DateUtils.convertStringToDate2(asnLineV2.getExpectedDate());
                            }
                            apiLine.setExpectedDate(reqDelDate);
                        } catch (Exception e) {
                            e.printStackTrace();
                            throw new BadRequestException("Date format should be yyyy-MM-dd");
                        }
                    }
                    if (asnLineV2.getExpectedDate().contains("/")) {
                        // EA_DATE
                        try {
                            ZoneId defaultZoneId = ZoneId.systemDefault();
                            String sdate = asnLineV2.getExpectedDate();
                            String firstHalf = sdate.substring(0, sdate.lastIndexOf("/"));
                            String secondHalf = sdate.substring(sdate.lastIndexOf("/") + 1);
                            secondHalf = "/20" + secondHalf;
                            sdate = firstHalf + secondHalf;
                            log.info("sdate--------> : " + sdate);

                            LocalDate localDate = DateUtils.dateConv2(sdate);
                            log.info("localDate--------> : " + localDate);
                            Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
                            apiLine.setExpectedDate(date);
                        } catch (Exception e) {
                            e.printStackTrace();
                            throw new BadRequestException("Date format should be yyyy-MM-dd");
                        }
                    }
                }

                apiLine.setOrderedQty(asnLineV2.getExpectedQty());                // ORD_QTY
                apiLine.setUom(asnLineV2.getUom());                                // ORD_UOM
                apiLine.setPackQty(asnLineV2.getPackQty());                    // ITM_CASE_QTY
                orderLines.add(apiLine);
            }
            apiHeader.setLine(orderLines);
            apiHeader.setOrderProcessedOn(new Date());

            if (asnv2.getAsnLine() != null && !asnv2.getAsnLine().isEmpty()) {
                apiHeader.setProcessedStatusId(0L);
                log.info("apiHeader : " + apiHeader);
                InboundOrderV2 createdOrder = orderService.createInboundOrdersV2(apiHeader);
                log.info("ASNV2 Order Success : " + createdOrder);
                return createdOrder;
            } else if (asnv2.getAsnLine() == null || asnv2.getAsnLine().isEmpty()) {
                // throw the error as Lines are Empty and set the Indicator as '100'
                apiHeader.setProcessedStatusId(100L);
                log.info("apiHeader : " + apiHeader);
                InboundOrderV2 createdOrder = orderService.createInboundOrdersV2(apiHeader);
                log.info("ASNV2 Order Failed : " + createdOrder);
                throw new BadRequestException("ASNV2 Order doesn't contain any Lines.");
            }
        } catch (Exception e) {
            throw e;
        }
        return null;
    }
}