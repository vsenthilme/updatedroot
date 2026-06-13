package com.tekclover.wms.api.transaction.service;

import com.tekclover.wms.api.transaction.model.inbound.inventory.v2.InventoryV2;
import com.tekclover.wms.api.transaction.model.outbound.pickup.v2.PickupHeaderV2;
import com.tekclover.wms.api.transaction.model.outbound.pickup.v2.PickupLineV2;
<<<<<<< HEAD
import com.tekclover.wms.api.transaction.model.outbound.quality.v2.QualityHeaderV2;
import com.tekclover.wms.api.transaction.model.outbound.quality.v2.QualityLineV2;
=======
>>>>>>> 81dc64bb940937a238631ec1ef8d3af347b8ec9e
import com.tekclover.wms.api.transaction.model.warehouse.inbound.v2.ASNHeaderV2;
import com.tekclover.wms.api.transaction.model.warehouse.inbound.v2.ASNLineV2;
import com.tekclover.wms.api.transaction.model.warehouse.inbound.v2.ASNV2;
import com.tekclover.wms.api.transaction.repository.OutboundLineV2Repository;
import com.tekclover.wms.api.transaction.util.CommonUtils;
import com.tekclover.wms.api.transaction.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.Math.abs;

@Slf4j
@Service
public class ReturnInboundOrderService {

    @Autowired
    QualityHeaderService qualityHeaderService;

    @Autowired
    WarehouseService warehouseService;

    @Autowired
    OutboundLineService outboundLineService;

    @Autowired
    OutboundLineV2Repository outboundLineV2Repository;

    @Async
    public void triggerQualityHeaderLineCreate(String companyCodeId, String plantId, String languageId, String warehouseId,
                                               PickupLineV2 dbPickupLine, PickupHeaderV2 dbPickupHeader, List<InventoryV2> inventoryList, String loginUserID) throws Exception {
        log.info("Trigger QualityHeaderLine, inboundOrder Initiated : " + dbPickupLine.getVarianceQuantity());
        qualityHeaderService.createQualityHeaderV4(companyCodeId, plantId, languageId, warehouseId, dbPickupLine, dbPickupHeader, loginUserID);
<<<<<<< HEAD
//        if(inventoryList != null && !inventoryList.isEmpty()) {
//            createInboundOrder(companyCodeId, plantId, languageId, warehouseId, dbPickupLine, dbPickupHeader, inventoryList, loginUserID);
//        }
//        Long autoDeliveryConfirmationCheck = outboundLineV2Repository.doesMatchingRecordExist(companyCodeId, plantId, languageId, warehouseId, dbPickupHeader.getPreOutboundNo());
//        log.info("autoDeliveryConfirmationCheck: " + autoDeliveryConfirmationCheck);
//        if(autoDeliveryConfirmationCheck != null && autoDeliveryConfirmationCheck == 1) {
//            log.info("Automatic Delivery Confirmation Initiated..! " + dbPickupHeader.getRefDocNumber());
//            outboundLineService.deliveryConfirmationV5(companyCodeId, plantId, languageId, warehouseId,
//                    dbPickupHeader.getPreOutboundNo(), dbPickupHeader.getRefDocNumber(), loginUserID);
//        }
    }

    @Async
    public void triggerInboundOrder(String companyCodeId, String plantId, String languageId, String warehouseId,
                                    QualityLineV2 qualityLineV2, List<InventoryV2> inventoryList, String loginUserID) throws Exception {
        log.info("Trigger QualityHeaderLine, inboundOrder Initiated : " + qualityLineV2.getQualityQty());
        if(inventoryList != null && !inventoryList.isEmpty()) {
            createInboundOrderFromQualityLine(companyCodeId, plantId, languageId, warehouseId,
                    qualityLineV2, inventoryList, loginUserID);
        }
        Long autoDeliveryConfirmationCheck = outboundLineV2Repository.doesMatchingRecordExist(companyCodeId, plantId, languageId, warehouseId, qualityLineV2.getPreOutboundNo());
        log.info("autoDeliveryConfirmationCheck: " + autoDeliveryConfirmationCheck);
        if(autoDeliveryConfirmationCheck != null && autoDeliveryConfirmationCheck == 1) {
            log.info("Automatic Delivery Confirmation Initiated..! " + qualityLineV2.getRefDocNumber());
            outboundLineService.deliveryConfirmationV5(companyCodeId, plantId, languageId, warehouseId,
                    qualityLineV2.getPreOutboundNo(), qualityLineV2.getRefDocNumber(), loginUserID);
=======
        if(inventoryList != null && !inventoryList.isEmpty()) {
            createInboundOrder(companyCodeId, plantId, languageId, warehouseId, dbPickupLine, dbPickupHeader, inventoryList, loginUserID);
        }
        Long autoDeliveryConfirmationCheck = outboundLineV2Repository.doesMatchingRecordExist(companyCodeId, plantId, languageId, warehouseId, dbPickupHeader.getPreOutboundNo());
        log.info("autoDeliveryConfirmationCheck: " + autoDeliveryConfirmationCheck);
        if(autoDeliveryConfirmationCheck != null && autoDeliveryConfirmationCheck == 1) {
            log.info("Automatic Delivery Confirmation Initiated..! " + dbPickupHeader.getRefDocNumber());
            outboundLineService.deliveryConfirmationV5(companyCodeId, plantId, languageId, warehouseId,
                    dbPickupHeader.getPreOutboundNo(), dbPickupHeader.getRefDocNumber(), loginUserID);
>>>>>>> 81dc64bb940937a238631ec1ef8d3af347b8ec9e
        }
    }

    /**
     *
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param dbPickupLine
     * @param dbPickupHeader
     * @param loginUserID
     * @throws Exception
     */
    public void createInboundOrder (String companyCodeId, String plantId, String languageId, String warehouseId,
                                    PickupLineV2 dbPickupLine, PickupHeaderV2 dbPickupHeader, List<InventoryV2> inventoryList, String loginUserID) throws Exception {
        ASNV2 asn = new ASNV2();
        ASNHeaderV2 asnHeader = new ASNHeaderV2();
        List<ASNLineV2> asnLines = new ArrayList<>();

        BeanUtils.copyProperties(dbPickupHeader, asnHeader, CommonUtils.getNullPropertyNames(dbPickupHeader));
        asnHeader.setAsnNumber(dbPickupHeader.getPickupNumber());
        asnHeader.setCompanyCode(companyCodeId);
        asnHeader.setBranchCode(plantId);
        asnHeader.setInboundOrderTypeId(9L);
        asnHeader.setLoginUserId(loginUserID);
        asnHeader.setParentProductionOrderNo(dbPickupHeader.getPreOutboundNo());
        asnHeader.setPurchaseOrderNumber(dbPickupHeader.getRefDocNumber());

        long lineNumber = 1;
        if(inventoryList != null && !inventoryList.isEmpty()) {
            for (InventoryV2 inventory : inventoryList) {
                ASNLineV2 asnLine = new ASNLineV2();
                BeanUtils.copyProperties(dbPickupLine, asnLine, CommonUtils.getNullPropertyNames(dbPickupLine));
                BeanUtils.copyProperties(inventory, asnLine, CommonUtils.getNullPropertyNames(inventory));
                asnLine.setLineReference(lineNumber);
<<<<<<< HEAD
		        asnLine.setCompanyCode(companyCodeId);
		        asnLine.setBranchCode(plantId);
                asnLine.setSku(inventory.getItemCode());
                asnLine.setSkuDescription(inventory.getDescription());
                asnLine.setExpectedQty(inventory.getInventoryQuantity());
                asnLine.setUom(inventory.getInventoryUom());
		        asnLine.setExpectedDate(DateUtils.date2String_YYYYMMDD(new Date()));
        		asnLine.setPurchaseOrderNumber(dbPickupHeader.getRefDocNumber());
                asnLine.setMiddlewareTable(inventory.getStorageBin());
        		asnLine.setInboundOrderTypeId(9L);
        		asnLines.add(asnLine);
                lineNumber++;
            }
        }

        asn.setAsnHeader(asnHeader);
        asn.setAsnLine(asnLines);

        warehouseService.postWarehouseASNV2(asn);
    }

    /**
     *
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param qualityLineV2
     * @param inventoryList
     * @param loginUserID
     * @throws Exception
     */
    public void createInboundOrderFromQualityLine (String companyCodeId, String plantId, String languageId, String warehouseId,
                                     QualityLineV2 qualityLineV2, List<InventoryV2> inventoryList, String loginUserID) throws Exception {
        ASNV2 asn = new ASNV2();
        ASNHeaderV2 asnHeader = new ASNHeaderV2();
        List<ASNLineV2> asnLines = new ArrayList<>();

        BeanUtils.copyProperties(qualityLineV2, asnHeader, CommonUtils.getNullPropertyNames(qualityLineV2));
        asnHeader.setAsnNumber(qualityLineV2.getQualityInspectionNo());
        asnHeader.setCompanyCode(companyCodeId);
        asnHeader.setBranchCode(plantId);
        asnHeader.setInboundOrderTypeId(9L);
        asnHeader.setLoginUserId(loginUserID);
        asnHeader.setParentProductionOrderNo(qualityLineV2.getPreOutboundNo());
        asnHeader.setPurchaseOrderNumber(qualityLineV2.getRefDocNumber());

        long lineNumber = 1;
        if(inventoryList != null && !inventoryList.isEmpty()) {
            for (InventoryV2 inventory : inventoryList) {
                ASNLineV2 asnLine = new ASNLineV2();
                BeanUtils.copyProperties(qualityLineV2, asnLine, CommonUtils.getNullPropertyNames(qualityLineV2));
                BeanUtils.copyProperties(inventory, asnLine, CommonUtils.getNullPropertyNames(inventory));
                asnLine.setLineReference(lineNumber);
=======
>>>>>>> 81dc64bb940937a238631ec1ef8d3af347b8ec9e
                asnLine.setCompanyCode(companyCodeId);
                asnLine.setBranchCode(plantId);
                asnLine.setSku(inventory.getItemCode());
                asnLine.setSkuDescription(inventory.getDescription());
                asnLine.setExpectedQty(inventory.getInventoryQuantity());
                asnLine.setUom(inventory.getInventoryUom());
                asnLine.setExpectedDate(DateUtils.date2String_YYYYMMDD(new Date()));
<<<<<<< HEAD
                asnLine.setPurchaseOrderNumber(qualityLineV2.getRefDocNumber());
                asnLine.setMiddlewareTable(inventory.getStorageBin());
                asnHeader.setPieceNo(inventory.getPieceId());
=======
                asnLine.setPurchaseOrderNumber(dbPickupHeader.getRefDocNumber());
                asnLine.setMiddlewareTable(inventory.getStorageBin());
>>>>>>> 81dc64bb940937a238631ec1ef8d3af347b8ec9e
                asnLine.setInboundOrderTypeId(9L);
                asnLines.add(asnLine);
                lineNumber++;
            }
        }

        asn.setAsnHeader(asnHeader);
        asn.setAsnLine(asnLines);

        warehouseService.postWarehouseASNV2(asn);
    }
}