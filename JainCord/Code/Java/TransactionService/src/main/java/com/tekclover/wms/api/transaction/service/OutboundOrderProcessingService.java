package com.tekclover.wms.api.transaction.service;

import com.tekclover.wms.api.transaction.controller.exception.BadRequestException;
import com.tekclover.wms.api.transaction.model.IKeyValuePair;
import com.tekclover.wms.api.transaction.model.dto.BomHeader;
import com.tekclover.wms.api.transaction.model.dto.BomLine;
import com.tekclover.wms.api.transaction.model.dto.ImBasicData1V2;
import com.tekclover.wms.api.transaction.model.inbound.inventory.v2.IInventoryImpl;
import com.tekclover.wms.api.transaction.model.inbound.inventory.v2.InventoryV2;
import com.tekclover.wms.api.transaction.model.notification.NotificationSave;
import com.tekclover.wms.api.transaction.model.outbound.ordermangement.v2.OrderManagementHeaderV2;
import com.tekclover.wms.api.transaction.model.outbound.ordermangement.v2.OrderManagementLineV2;
import com.tekclover.wms.api.transaction.model.outbound.pickup.v2.PickupHeaderV2;
import com.tekclover.wms.api.transaction.model.outbound.preoutbound.v2.OutboundIntegrationHeaderV2;
import com.tekclover.wms.api.transaction.model.outbound.preoutbound.v2.OutboundIntegrationLineV2;
import com.tekclover.wms.api.transaction.model.outbound.preoutbound.v2.PreOutboundHeaderV2;
import com.tekclover.wms.api.transaction.model.outbound.preoutbound.v2.PreOutboundLineV2;
import com.tekclover.wms.api.transaction.model.outbound.v2.OutboundHeaderV2;
import com.tekclover.wms.api.transaction.model.outbound.v2.OutboundLineV2;
import com.tekclover.wms.api.transaction.model.warehouse.Warehouse;
import com.tekclover.wms.api.transaction.repository.*;
import com.tekclover.wms.api.transaction.util.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
public class OutboundOrderProcessingService extends BaseService {

    @Autowired
    MastersService mastersService;

    @Autowired
    OrderService orderService;

    @Autowired
    OutboundHeaderService outboundHeaderService;

    @Autowired
    OrderManagementLineService orderManagementLineService;

    @Autowired
    InventoryService inventoryService;

    @Autowired
    PushNotificationService pushNotificationService;

    //--------------------------------------------------------------------------------------------------------------
    @Autowired
    PreOutboundHeaderV2Repository preOutboundHeaderV2Repository;

    @Autowired
    PreOutboundLineV2Repository preOutboundLineV2Repository;

    @Autowired
    OrderManagementLineV2Repository orderManagementLineV2Repository;

    @Autowired
    OutboundLineV2Repository outboundLineV2Repository;

    @Autowired
    OrderManagementHeaderV2Repository orderManagementHeaderV2Repository;

    @Autowired
    OutboundHeaderV2Repository outboundHeaderV2Repository;

    @Autowired
    WarehouseRepository warehouseRepository;

    @Autowired
    ImBasicData1V2Repository imBasicData1V2Repository;

    @Autowired
    InventoryV2Repository inventoryV2Repository;

    @Autowired
    PickupHeaderV2Repository pickupHeaderV2Repository;

    //========================================================================V2====================================================================

    /**
     *
     * @param outboundIntegrationHeader
     * @return
     * @throws Exception
     */
    public OutboundHeaderV2 processOutboundReceivedV5(OutboundIntegrationHeaderV2 outboundIntegrationHeader) throws Exception {
        try {
            String companyCodeId = outboundIntegrationHeader.getCompanyCode();
            String plantId = outboundIntegrationHeader.getBranchCode();
            String languageId = outboundIntegrationHeader.getLanguageId() != null ? outboundIntegrationHeader.getLanguageId() : LANG_ID;
            String warehouseId = outboundIntegrationHeader.getWarehouseID();
            String refDocNumber = outboundIntegrationHeader.getRefDocumentNo();
            Long outboundOrderTypeId = outboundIntegrationHeader.getOutboundOrderTypeID();
            log.info("Outbound Process Initiated ------> : {}|{}|{}|{}|{}|{}", companyCodeId, plantId, languageId, warehouseId, refDocNumber, outboundOrderTypeId);
            MW_AMS = outboundIntegrationHeader.getLoginUserId() != null ? outboundIntegrationHeader.getLoginUserId() : MW_AMS;

<<<<<<< HEAD
//            Optional<PreOutboundHeaderV2> orderProcessedStatus = preOutboundHeaderV2Repository.findByRefDocNumberAndOutboundOrderTypeIdAndDeletionIndicator(refDocNumber, outboundOrderTypeId, 0L);
//            if (orderProcessedStatus.isPresent()) { throw new BadRequestException("Order :" + refDocNumber + " already processed. Reprocessing can't be allowed."); }
=======
            Optional<PreOutboundHeaderV2> orderProcessedStatus = preOutboundHeaderV2Repository.findByRefDocNumberAndOutboundOrderTypeIdAndDeletionIndicator(refDocNumber, outboundOrderTypeId, 0L);
            if (orderProcessedStatus.isPresent()) { throw new BadRequestException("Order :" + refDocNumber + " already processed. Reprocessing can't be allowed."); }
>>>>>>> 81dc64bb940937a238631ec1ef8d3af347b8ec9e

            String idMasterAuthToken = getIDMasterAuthToken();
            String masterAuthToken = getMasterAuthToken();

            if (warehouseId == null) {
                warehouseId = getWarehouse(companyCodeId, plantId, languageId);
            }

            if (outboundIntegrationHeader.getOutboundOrderTypeID() == 4 ||
                    (outboundIntegrationHeader.getReferenceDocumentType() != null && outboundIntegrationHeader.getReferenceDocumentType().equalsIgnoreCase("Sales Invoice"))) {
                OutboundHeaderV2 updateOutboundHeaderAndLine = outboundHeaderService.updateOutboundHeaderForSalesInvoice(outboundIntegrationHeader, warehouseId);
                log.info("SalesInvoice Updated in OutboundHeader and Line");
                return updateOutboundHeaderAndLine != null ? updateOutboundHeaderAndLine : new OutboundHeaderV2();
            }

            // Getting PreOutboundNo from NumberRangeTable
            String preOutboundNo = getNextRangeNumber(9L, companyCodeId, plantId, languageId, warehouseId, idMasterAuthToken);
            log.info("PreOutboundNo : {}", preOutboundNo);
            String refField1ForOrderType = null;

            description = getDescription(companyCodeId, plantId, languageId, warehouseId);

            Long statusId = 39L;
            statusDescription = getStatusDescription(statusId, languageId);

            List<PreOutboundLineV2> overallCreatedPreoutboundLineList = new ArrayList<>();
            for (OutboundIntegrationLineV2 outboundIntegrationLine : outboundIntegrationHeader.getOutboundIntegrationLines()) {
                log.info("outboundIntegrationLine : " + outboundIntegrationLine);

                /*-------------Insertion of BOM item in preOutboundLine table---------------------------------------------------------*/
                /*
                 * Before Insertion into PREOUTBOUNDLINE table , validate the Below
                 * Pass the WH_ID/ITM_CODE as PAR_ITM_CODE into BOMHEADER table and validate the records,
                 * If record is not Null then fetch BOM_NO Pass BOM_NO in BOMITEM table and fetch CHL_ITM_CODE and
                 * CHL_ITM_QTY values and insert along with PAR_ITM_CODE in PREOUTBOUNDLINE table as below
                 * Insertion of CHL_ITM_CODE values
                 */
                BomHeader bomHeader = mastersService.getBomHeader(outboundIntegrationLine.getItemCode(), companyCodeId, plantId, languageId, warehouseId, masterAuthToken);
                if (bomHeader != null) {
                    BomLine[] bomLine = mastersService.getBomLine(bomHeader.getBomNumber(), companyCodeId, plantId, languageId, warehouseId, masterAuthToken);
                    List<PreOutboundLineV2> toBeCreatedpreOutboundLineList = new ArrayList<>();
                    for (BomLine dbBomLine : bomLine) {
                        toBeCreatedpreOutboundLineList.add(createPreOutboundLineBOMBasedV2(companyCodeId, plantId, languageId, warehouseId, preOutboundNo,
                                outboundIntegrationHeader, dbBomLine, outboundIntegrationLine, description, statusId, statusDescription, MW_AMS));
                    }

                    // Batch Insert - preOutboundLines
                    if (!toBeCreatedpreOutboundLineList.isEmpty()) {
                        List<PreOutboundLineV2> createdpreOutboundLine = preOutboundLineV2Repository.saveAll(toBeCreatedpreOutboundLineList);
                        log.info("createdpreOutboundLine [BOM] : " + createdpreOutboundLine);
                        overallCreatedPreoutboundLineList.addAll(createdpreOutboundLine);
                    }
                }
                refField1ForOrderType = outboundIntegrationLine.getRefField1ForOrderType();
            }

            /*
             * Inserting BOM Line records in OutboundLine and OrderManagementLine
             */
            if (!overallCreatedPreoutboundLineList.isEmpty()) {
                for (PreOutboundLineV2 preOutboundLine : overallCreatedPreoutboundLineList) {
//                 OrderManagementLine
                    OrderManagementLineV2 orderManagementLine = createOrderManagementLineV2(companyCodeId, plantId, languageId, warehouseId, outboundIntegrationHeader, preOutboundLine, MW_AMS);
                    log.info("orderManagementLine created---BOM---> : " + orderManagementLine);
                }

                /*------------------Record Insertion in OUTBOUNDLINE table--for BOM---------*/
                List<OutboundLineV2> createOutboundLineListForBOM = createOutboundLineV2(overallCreatedPreoutboundLineList, outboundIntegrationHeader);
                log.info("createOutboundLine created : " + createOutboundLineListForBOM);
            }

            /*
             * Append PREOUTBOUNDLINE table through below logic
             */
            List<PreOutboundLineV2> createdPreOutboundLineList = new ArrayList<>();
            for (OutboundIntegrationLineV2 outboundIntegrationLine : outboundIntegrationHeader.getOutboundIntegrationLines()) {
                // PreOutboundLine
                try {
                    //=========================================================================================================//

                    PreOutboundLineV2 preOutboundLine = createPreOutboundLineV2(companyCodeId, plantId, languageId, warehouseId, preOutboundNo,
                            outboundIntegrationHeader, outboundIntegrationLine, statusId, statusDescription, description, MW_AMS);
                    PreOutboundLineV2 createdPreOutboundLine = preOutboundLineV2Repository.save(preOutboundLine);
                    log.info("preOutboundLine created---1---> : " + createdPreOutboundLine);
                    createdPreOutboundLineList.add(createdPreOutboundLine);

                } catch (Exception e) {
                    log.error("Error on processing PreOutboundLine : " + e.toString());
                    e.printStackTrace();
                }
            }

            createOrderManagementLine(companyCodeId, plantId, languageId, warehouseId, outboundIntegrationHeader, createdPreOutboundLineList, MW_AMS);
            fireBaseNotification(companyCodeId, plantId, languageId, warehouseId, preOutboundNo, refDocNumber, outboundIntegrationHeader.getReferenceDocumentType(),  MW_AMS);

            /*------------------Record Insertion in OUTBOUNDLINE tables-----------*/
            List<OutboundLineV2> createOutboundLineList = createOutboundLineV2(createdPreOutboundLineList, outboundIntegrationHeader);
            log.info("createOutboundLine created : " + createOutboundLineList);

            PreOutboundHeaderV2 createdPreOutboundHeader = createPreOutboundHeaderV2(companyCodeId, plantId, languageId, warehouseId, preOutboundNo, outboundIntegrationHeader, refField1ForOrderType, statusId, statusDescription, description, MW_AMS);
            log.info("preOutboundHeader Created : {}", createdPreOutboundHeader);

            statusId = 41L;
            statusDescription = getStatusDescription(statusId, languageId);
            OrderManagementHeaderV2 createdOrderManagementHeader = createOrderManagementHeaderV2(createdPreOutboundHeader, statusId, statusDescription, MW_AMS);
            log.info("OrderMangementHeader Created : {}", createdOrderManagementHeader);

            OutboundHeaderV2 outboundHeader = createOutboundHeaderV2(createdPreOutboundHeader, outboundIntegrationHeader, statusId, statusDescription);
            log.info("outboundHeader Created : {}", outboundHeader);

            //check the status of OrderManagementLine for NoStock update status of outbound header, preoutbound header, preoutboundline
            statusDescription = getStatusDescription(47L, languageId);
            orderManagementLineV2Repository.updateNostockStatusUpdateProc(companyCodeId, plantId, languageId, warehouseId, refDocNumber, preOutboundNo, 47L, statusDescription);
            log.info("No stock status updated in preinbound header and line, outbound header using stored procedure when condition is satisfied");

            return outboundHeader;
        } catch (Exception e) {
            e.printStackTrace();

            // Updating the Processed Status
            log.info("Rollback Initiated...!" + outboundIntegrationHeader.getRefDocumentNo());
            rollback(outboundIntegrationHeader);
            orderService.updateProcessedOrderV2(outboundIntegrationHeader.getRefDocumentNo(), outboundIntegrationHeader.getOutboundOrderTypeID());

            throw e;
        }
    }

    /**
     *
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @return
     */
    private String getWarehouse(String companyCodeId, String plantId, String languageId) {
        try {
            Optional<Warehouse> warehouse =
                    warehouseRepository.findByCompanyCodeIdAndPlantIdAndLanguageIdAndDeletionIndicator(companyCodeId, plantId, languageId, 0L);
            log.info("warehouse : " + warehouse);
            if (warehouse.isPresent()) {
                return warehouse.get().getWarehouseId();
            }
            throw new BadRequestException("Warehouse cannot be null.");
        } catch (Exception e) {
            log.error("Warehouse fetch exception : " + e);
            throw e;
        }
    }

    /**
     *
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param itemCode
     * @param manufacturerName
     * @return
     */
    private String getItemDescription (String companyCodeId, String plantId, String languageId, String warehouseId, String itemCode, String manufacturerName) {
        ImBasicData1V2 imBasicData1 = imBasicData1V2Repository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndItemCodeAndManufacturerPartNoAndDeletionIndicator(
                languageId, companyCodeId, plantId, warehouseId, itemCode.trim(), manufacturerName, 0L);
        return imBasicData1 != null ? imBasicData1.getDescription() : null;
    }

    /**
     *
     * @param createdPreOutboundHeader
     * @param loginUserId
     * @return
     */
    private OrderManagementHeaderV2 createOrderManagementHeaderV2(PreOutboundHeaderV2 createdPreOutboundHeader, Long statusId, String statusDesc, String loginUserId) throws Exception {
        try {
            OrderManagementHeaderV2 newOrderManagementHeader = new OrderManagementHeaderV2();
            BeanUtils.copyProperties(createdPreOutboundHeader, newOrderManagementHeader, CommonUtils.getNullPropertyNames(createdPreOutboundHeader));
            newOrderManagementHeader.setStatusId(statusId);
            newOrderManagementHeader.setStatusDescription(statusDesc);
            newOrderManagementHeader.setPickupCreatedBy(loginUserId);
            newOrderManagementHeader.setPickupCreatedOn(new Date());
            return orderManagementHeaderV2Repository.save(newOrderManagementHeader);
        } catch (Exception e) {
            log.error("Exception while creating OrderManagementHeader : " + e);
            throw e;
        }
    }

    /**
     *
     * @param createdPreOutboundHeader
     * @param outboundIntegrationHeader
     * @param statusId
     * @param statusDesc
     * @return
     * @throws Exception
     */
    private OutboundHeaderV2 createOutboundHeaderV2(PreOutboundHeaderV2 createdPreOutboundHeader, OutboundIntegrationHeaderV2 outboundIntegrationHeader,
                                                    Long statusId, String statusDesc) throws Exception {
        try {
            OutboundHeaderV2 outboundHeader = new OutboundHeaderV2();
            BeanUtils.copyProperties(createdPreOutboundHeader, outboundHeader, CommonUtils.getNullPropertyNames(createdPreOutboundHeader));
            outboundHeader.setRefDocDate(new Date());
            outboundHeader.setStatusId(statusId);
            outboundHeader.setStatusDescription(statusDesc);
            outboundHeader.setInvoiceDate(outboundIntegrationHeader.getRequiredDeliveryDate());

            if (outboundHeader.getOutboundOrderTypeId() == 3L) {
                outboundHeader.setCustomerType("INVOICE");
            }
            if (outboundHeader.getOutboundOrderTypeId() == 0L || outboundHeader.getOutboundOrderTypeId() == 1L) {
                outboundHeader.setCustomerType("TRANSVERSE");
            }
            return outboundHeaderV2Repository.save(outboundHeader);
        } catch (Exception e) {
            log.error("Exception While OutboundHeader create: " + e);
            throw e;
        }
    }

    /**
     *
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param preOutboundNo
     * @param outboundIntegrationHeader
     * @param refField1ForOrderType
     * @param statusId
     * @param statusDesc
     * @param desc
     * @param loginUserId
     * @return
     * @throws Exception
     */
    private PreOutboundHeaderV2 createPreOutboundHeaderV2(String companyCodeId, String plantId, String languageId, String warehouseId, String preOutboundNo,
                                                          OutboundIntegrationHeaderV2 outboundIntegrationHeader, String refField1ForOrderType,
                                                          Long statusId, String statusDesc, IKeyValuePair desc, String loginUserId) throws Exception {
        try {
            PreOutboundHeaderV2 preOutboundHeader = new PreOutboundHeaderV2();
            BeanUtils.copyProperties(outboundIntegrationHeader, preOutboundHeader, CommonUtils.getNullPropertyNames(outboundIntegrationHeader));
            preOutboundHeader.setCompanyCodeId(companyCodeId);
            preOutboundHeader.setPlantId(plantId);
            preOutboundHeader.setLanguageId(languageId);
            preOutboundHeader.setWarehouseId(warehouseId);
            preOutboundHeader.setRefDocNumber(outboundIntegrationHeader.getRefDocumentNo());
            preOutboundHeader.setPreOutboundNo(preOutboundNo);
            preOutboundHeader.setOutboundOrderTypeId(outboundIntegrationHeader.getOutboundOrderTypeID());
            preOutboundHeader.setRefDocDate(new Date());

            // REF_FIELD_1
            preOutboundHeader.setReferenceField1(refField1ForOrderType);
            preOutboundHeader.setStatusId(statusId);
            preOutboundHeader.setStatusDescription(statusDesc);
            preOutboundHeader.setCompanyDescription(desc.getCompanyDesc());
            preOutboundHeader.setPlantDescription(desc.getPlantDesc());
            preOutboundHeader.setWarehouseDescription(desc.getWarehouseDesc());

            preOutboundHeader.setDeletionIndicator(0L);
            preOutboundHeader.setCreatedBy(loginUserId);
            preOutboundHeader.setCreatedOn(new Date());
            return preOutboundHeaderV2Repository.save(preOutboundHeader);
        } catch (Exception e) {
            log.error("Exception While PreoutboundHeader create: " + e);
            throw e;
        }
    }

    /**
     *
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param preOutboundNo
     * @param outboundIntegrationHeader
     * @param dbBomLine
     * @param outboundIntegrationLine
     * @param desc
     * @param statusId
     * @param statusDesc
     * @param loginUserId
     * @return
     * @throws Exception
     */
    private PreOutboundLineV2 createPreOutboundLineBOMBasedV2(String companyCodeId, String plantId, String languageId, String warehouseId, String preOutboundNo,
                                                              OutboundIntegrationHeaderV2 outboundIntegrationHeader, BomLine dbBomLine, OutboundIntegrationLineV2 outboundIntegrationLine,
                                                              IKeyValuePair desc, Long statusId, String statusDesc, String loginUserId) throws Exception {
        try {
            PreOutboundLineV2 preOutboundLine = new PreOutboundLineV2();
            BeanUtils.copyProperties(outboundIntegrationLine, preOutboundLine, CommonUtils.getNullPropertyNames(outboundIntegrationLine));
            preOutboundLine.setCompanyCodeId(companyCodeId);
            preOutboundLine.setPlantId(plantId);
            preOutboundLine.setLanguageId(languageId);
            preOutboundLine.setWarehouseId(warehouseId);
            preOutboundLine.setPreOutboundNo(preOutboundNo);
            preOutboundLine.setCustomerId(outboundIntegrationHeader.getCustomerId());
            preOutboundLine.setCustomerName(outboundIntegrationHeader.getCustomerName());

            // REF_DOC_NO
            preOutboundLine.setRefDocNumber(outboundIntegrationHeader.getRefDocumentNo());
            preOutboundLine.setPartnerCode(outboundIntegrationHeader.getPartnerCode());

            // OB_ORD_TYP_ID
            preOutboundLine.setOutboundOrderTypeId(outboundIntegrationHeader.getOutboundOrderTypeID());

            // IB__LINE_NO
            preOutboundLine.setLineNumber(outboundIntegrationLine.getLineReference());

            // ITM_CODE
            preOutboundLine.setItemCode(dbBomLine.getChildItemCode());

            String itemText = outboundIntegrationLine.getItemText() != null ? outboundIntegrationLine.getItemText() :
                    getItemDescription(companyCodeId, plantId, languageId, warehouseId, dbBomLine.getChildItemCode(), outboundIntegrationLine.getManufacturerName());

            preOutboundLine.setDescription(itemText);

            // ORD_QTY
            double orderQuantity = outboundIntegrationLine.getOrderedQty() * dbBomLine.getChildItemQuantity();
            preOutboundLine.setOrderQty(orderQuantity);

            // ORD_UOM
            preOutboundLine.setOrderUom(outboundIntegrationLine.getUom());

            // REQ_DEL_DATE
            preOutboundLine.setRequiredDeliveryDate(outboundIntegrationHeader.getRequiredDeliveryDate());

            // STCK_TYP_ID
            preOutboundLine.setStockTypeId(1L);

            // SP_ST_IND_ID
            preOutboundLine.setSpecialStockIndicatorId(1L);

            // STATUS_ID
            preOutboundLine.setStatusId(statusId);
            preOutboundLine.setStatusDescription(statusDesc);
            preOutboundLine.setCompanyDescription(desc.getCompanyDesc());
            preOutboundLine.setPlantDescription(desc.getPlantDesc());
            preOutboundLine.setWarehouseDescription(desc.getWarehouseDesc());

            preOutboundLine.setSalesInvoiceNumber(outboundIntegrationLine.getSalesInvoiceNo());
            preOutboundLine.setPickListNumber(outboundIntegrationHeader.getPickListNumber());
            preOutboundLine.setTokenNumber(outboundIntegrationHeader.getTokenNumber());
            preOutboundLine.setTargetBranchCode(outboundIntegrationHeader.getTargetBranchCode());

            // REF_FIELD_1
            preOutboundLine.setReferenceField1(outboundIntegrationLine.getRefField1ForOrderType());

            // REF_FIELD_2
            preOutboundLine.setReferenceField2("BOM");

            preOutboundLine.setDeletionIndicator(0L);
            preOutboundLine.setCreatedBy(loginUserId);
            preOutboundLine.setCreatedOn(new Date());
            return preOutboundLine;
        } catch (Exception e) {
            log.error("Exception While PreoutboundLine[BOM] create: " + e);
            throw e;
        }
    }

    /**
     *
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param preOutboundNo
     * @param outboundIntegrationHeader
     * @param outboundIntegrationLine
     * @param statusId
     * @param statusDesc
     * @param desc
     * @param loginUserId
     * @return
     * @throws Exception
     */
    private PreOutboundLineV2 createPreOutboundLineV2(String companyCodeId, String plantId, String languageId, String warehouseId, String preOutboundNo,
                                                      OutboundIntegrationHeaderV2 outboundIntegrationHeader, OutboundIntegrationLineV2 outboundIntegrationLine,
                                                      Long statusId, String statusDesc, IKeyValuePair desc,  String loginUserId) throws Exception {
        try {
            PreOutboundLineV2 preOutboundLine = new PreOutboundLineV2();
            BeanUtils.copyProperties(outboundIntegrationLine, preOutboundLine, CommonUtils.getNullPropertyNames(outboundIntegrationLine));
            preOutboundLine.setCompanyCodeId(companyCodeId);
            preOutboundLine.setPlantId(plantId);
            preOutboundLine.setLanguageId(languageId);
            preOutboundLine.setWarehouseId(warehouseId);
            preOutboundLine.setPreOutboundNo(preOutboundNo);
            preOutboundLine.setCustomerId(outboundIntegrationHeader.getCustomerId());
            preOutboundLine.setCustomerName(outboundIntegrationHeader.getCustomerName());

            // REF DOC Number
            preOutboundLine.setRefDocNumber(outboundIntegrationHeader.getRefDocumentNo());

            // PARTNER_CODE
            preOutboundLine.setPartnerCode(outboundIntegrationHeader.getPartnerCode());

            // IB__LINE_NO
            preOutboundLine.setLineNumber(outboundIntegrationLine.getLineReference());

            // ITM_CODE
            preOutboundLine.setItemCode(outboundIntegrationLine.getItemCode());

            // OB_ORD_TYP_ID
            preOutboundLine.setOutboundOrderTypeId(outboundIntegrationHeader.getOutboundOrderTypeID());

            // STATUS_ID
            preOutboundLine.setStatusId(statusId);
            preOutboundLine.setStatusDescription(statusDesc);

            // STCK_TYP_ID
            preOutboundLine.setStockTypeId(1L);

            // SP_ST_IND_ID
            preOutboundLine.setSpecialStockIndicatorId(1L);

            preOutboundLine.setCompanyDescription(desc.getCompanyDesc());
            preOutboundLine.setPlantDescription(desc.getPlantDesc());
            preOutboundLine.setWarehouseDescription(desc.getWarehouseDesc());

            preOutboundLine.setSalesInvoiceNumber(outboundIntegrationLine.getSalesInvoiceNo());
            preOutboundLine.setPickListNumber(outboundIntegrationLine.getPickListNo());
            preOutboundLine.setTokenNumber(outboundIntegrationHeader.getTokenNumber());
            preOutboundLine.setTargetBranchCode(outboundIntegrationHeader.getTargetBranchCode());

            String itemText = outboundIntegrationLine.getItemText() != null ? outboundIntegrationLine.getItemText() :
                    getItemDescription(companyCodeId, plantId, languageId, warehouseId, outboundIntegrationLine.getItemCode(), outboundIntegrationLine.getManufacturerName());

            preOutboundLine.setDescription(itemText);

            // ORD_QTY
            preOutboundLine.setOrderQty(outboundIntegrationLine.getOrderedQty());

            // ORD_UOM
            preOutboundLine.setOrderUom(outboundIntegrationLine.getUom());

            // REQ_DEL_DATE
            preOutboundLine.setRequiredDeliveryDate(outboundIntegrationHeader.getRequiredDeliveryDate());

            // REF_FIELD_1
            preOutboundLine.setReferenceField1(outboundIntegrationLine.getRefField1ForOrderType());

            preOutboundLine.setDeletionIndicator(0L);
            preOutboundLine.setCreatedBy(loginUserId);
            preOutboundLine.setCreatedOn(new Date());

            log.info("preOutboundLine : " + preOutboundLine);
            return preOutboundLine;
        } catch (Exception e) {
            log.error("Exception While PreoutboundLine create: " + e);
            throw e;
        }
    }

    /**
     *
     * @param createdPreOutboundLine
     * @param outboundIntegrationHeader
     * @return
     */
    private List<OutboundLineV2> createOutboundLineV2(List<PreOutboundLineV2> createdPreOutboundLine, OutboundIntegrationHeaderV2 outboundIntegrationHeader) throws Exception {
        try {
            List<OutboundLineV2> outboundLines = new ArrayList<>();
            for (PreOutboundLineV2 preOutboundLine : createdPreOutboundLine) {
                List<OrderManagementLineV2> orderManagementLine = orderManagementLineService.getOrderManagementLineV2(
                        preOutboundLine.getCompanyCodeId(), preOutboundLine.getPlantId(),
                        preOutboundLine.getLanguageId(), preOutboundLine.getWarehouseId(),
                        preOutboundLine.getPreOutboundNo(), preOutboundLine.getLineNumber(),
                        preOutboundLine.getItemCode());
                for (OrderManagementLineV2 dbOrderManagementLine : orderManagementLine) {
                    OutboundLineV2 outboundLine = new OutboundLineV2();
                    BeanUtils.copyProperties(preOutboundLine, outboundLine, CommonUtils.getNullPropertyNames(preOutboundLine));
                    outboundLine.setDeliveryQty(0D);
                    outboundLine.setStatusId(dbOrderManagementLine.getStatusId());

                    if (preOutboundLine.getStatusId() != null) {
                        statusDescription = getStatusDescription(dbOrderManagementLine.getStatusId(), dbOrderManagementLine.getLanguageId());
                        outboundLine.setStatusDescription(statusDescription);
                    }
                    outboundLine.setInvoiceDate(outboundIntegrationHeader.getRequiredDeliveryDate());

                    if (outboundLine.getOutboundOrderTypeId() == 3L) {
                        outboundLine.setCustomerType("INVOICE");
                    }
                    if (outboundLine.getOutboundOrderTypeId() == 0L || outboundLine.getOutboundOrderTypeId() == 1L) {
                        outboundLine.setCustomerType("TRANSVERSE");
                    }
                    outboundLines.add(outboundLine);
                }
            }
            outboundLines = outboundLineV2Repository.saveAll(outboundLines);
            log.info("outboundLines created -----2------>: " + outboundLines);
            return outboundLines;
        } catch (Exception e) {
            log.error("Exception While OutboundLine create: " + e);
            throw e;
        }
    }

    @Transactional
    public void createOrderManagementLine(String companyCodeId, String plantId, String languageId, String warehouseId,
                                          OutboundIntegrationHeaderV2 outboundIntegrationHeader, List<PreOutboundLineV2> preOutboundLineList, String loginUserId) throws Exception {
        OrderManagementLineV2 orderManagementLine = null;
        try {
            for (PreOutboundLineV2 preOutboundLine : preOutboundLineList) {
                orderManagementLine = createOrderManagementLineV2(companyCodeId, plantId, languageId, warehouseId, outboundIntegrationHeader, preOutboundLine, loginUserId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        log.info("orderManagementLine created---1---> : " + orderManagementLine);

    }

    /**
     *
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param outboundIntegrationHeader
     * @param preOutboundLine
     * @return
     * @throws Exception
     */
    private OrderManagementLineV2 createOrderManagementLineV2(String companyCodeId, String plantId, String languageId, String warehouseId,
                                                              OutboundIntegrationHeaderV2 outboundIntegrationHeader, PreOutboundLineV2 preOutboundLine, String loginUserId) throws Exception {
        try {
            OrderManagementLineV2 orderManagementLine = new OrderManagementLineV2();
            BeanUtils.copyProperties(preOutboundLine, orderManagementLine, CommonUtils.getNullPropertyNames(preOutboundLine));
            log.info("orderManagementLine : " + orderManagementLine);

            /*
             * 1. If OB_ORD_TYP_ID = 0,1,3
             * Pass WH_ID/ITM_CODE in INVENTORY table and fetch ST_BIN.
             * Pass ST_BIN into STORAGEBIN table and filter ST_BIN values by ST_SEC_ID values of ZB,ZC,ZG,ZT and
             * PUTAWAY_BLOCK and PICK_BLOCK are false(Null).
             *
             * 2. If OB_ORD_TYP_ID = 2
             * Pass  WH_ID/ITM_CODE in INVENTORY table and fetch ST_BIN.
             * Pass ST_BIN into STORAGEBIN table and filter ST_BIN values by ST_SEC_ID values of ZD and PUTAWAY_BLOCK
             * and PICK_BLOCK are false(Null).
             *
             * Pass the filtered ST_BIN/WH_ID/ITM_CODE/BIN_CL_ID=01/STCK_TYP_ID=1 in Inventory table and fetch Sum(INV_QTY)"
             */

            Long OB_ORD_TYP_ID = outboundIntegrationHeader.getOutboundOrderTypeID();
            Long BIN_CLASS_ID;

            if (OB_ORD_TYP_ID == 0L || OB_ORD_TYP_ID == 1L || OB_ORD_TYP_ID == 3L ||
                    OB_ORD_TYP_ID.equals(OB_IPL_ORD_TYP_ID_SFG) || OB_ORD_TYP_ID.equals(OB_IPL_ORD_TYP_ID_FG)) {
                BIN_CLASS_ID = 1L;
                orderManagementLine = createOrderManagementV2(companyCodeId, plantId, languageId, BIN_CLASS_ID, orderManagementLine, warehouseId,
                        preOutboundLine.getItemCode(), preOutboundLine.getOrderQty(), loginUserId);
            }
            if (OB_ORD_TYP_ID == 2L) {
                BIN_CLASS_ID = 7L;
                orderManagementLine = createOrderManagementV2(companyCodeId, plantId, languageId, BIN_CLASS_ID, orderManagementLine, warehouseId,
                        preOutboundLine.getItemCode(), preOutboundLine.getOrderQty(), loginUserId);
            }
            return orderManagementLine;
        } catch (Exception e) {
            log.error("Exception While OrderManagementLine create: " + e);
            throw e;
        }
    }

    /**
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param binClassId
     * @param orderManagementLine
     * @param warehouseId
     * @param itemCode
     * @param ORD_QTY
     * @return
     */
    private OrderManagementLineV2 createOrderManagementV2(String companyCodeId, String plantId, String languageId,
                                                          Long binClassId, OrderManagementLineV2 orderManagementLine,
                                                          String warehouseId, String itemCode, Double ORD_QTY, String loginUserId) throws Exception {
        String manufacturerName = orderManagementLine.getManufacturerName();
        List<IInventoryImpl> stockType1InventoryList = inventoryService.
                getInventoryForOrderManagementV2(companyCodeId, plantId, languageId, warehouseId, itemCode, 1L, binClassId, manufacturerName);
        log.info("JainCord---Global---stockType1InventoryList-------> : " + stockType1InventoryList.size());
        if (stockType1InventoryList.isEmpty()) {
            return createEMPTYOrderManagementLineV2(orderManagementLine);
        }
        OrderManagementLineV2 createdOrderManagementLine = orderManagementLineService.updateAllocationV5(companyCodeId, plantId, languageId, warehouseId, itemCode, manufacturerName, binClassId, ORD_QTY, orderManagementLine, loginUserId);
        if(createdOrderManagementLine != null && createdOrderManagementLine.getStorageSectionId() != null && createdOrderManagementLine.getStorageSectionId().equalsIgnoreCase("1")) {
            createPickUpHeader(companyCodeId, plantId, languageId, warehouseId, createdOrderManagementLine.getPreOutboundNo(), createdOrderManagementLine.getRefDocNumber(), createdOrderManagementLine, loginUserId );
        }
        return createdOrderManagementLine;
    }

    /**
     * @param orderManagementLine
     * @return
     */
    private OrderManagementLineV2 createEMPTYOrderManagementLineV2(OrderManagementLineV2 orderManagementLine) throws Exception {
        try {
            orderManagementLine.setStatusId(47L);
            statusDescription = getStatusDescription(47L, orderManagementLine.getLanguageId());
            orderManagementLine.setStatusDescription(statusDescription);
            orderManagementLine.setReferenceField7(statusDescription);
            orderManagementLine.setProposedStorageBin("");
            orderManagementLine.setProposedPackBarCode("");
            orderManagementLine.setInventoryQty(0D);
            orderManagementLine.setAllocatedQty(0D);

            if(orderManagementLine.getCompanyDescription() == null) {
                description = getDescription(orderManagementLine.getCompanyCodeId(), orderManagementLine.getPlantId(), orderManagementLine.getLanguageId(), orderManagementLine.getWarehouseId());
                orderManagementLine.setCompanyDescription(description.getCompanyDesc());
                orderManagementLine.setPlantDescription(description.getPlantDesc());
                orderManagementLine.setWarehouseDescription(description.getWarehouseDesc());
            }

            orderManagementLine = orderManagementLineV2Repository.save(orderManagementLine);
            log.info("orderManagementLine created: " + orderManagementLine);
            return orderManagementLine;
        } catch (Exception e) {
            log.error("Exception While EmptyOrderManagementLine create: " + e);
            throw e;
        }
    }

    /**
     * @param outboundIntegrationHeader
     * @throws Exception
     */
    public void rollback(OutboundIntegrationHeaderV2 outboundIntegrationHeader) throws Exception {
        try {
            String companyCodeId = outboundIntegrationHeader.getCompanyCode();
            String plantId = outboundIntegrationHeader.getBranchCode();
            String languageId = outboundIntegrationHeader.getLanguageId() != null ? outboundIntegrationHeader.getLanguageId() : LANG_ID;
            String warehouseId = outboundIntegrationHeader.getWarehouseID();
            Long outboundOrderTypeId = outboundIntegrationHeader.getOutboundOrderTypeID();
            String refDocNo = outboundIntegrationHeader.getRefDocumentNo();
            initiateRollBack(companyCodeId, plantId, languageId, warehouseId, refDocNo, outboundOrderTypeId);
        } catch (Exception e) {
            log.error("Exception occurred : " + e.toString());
            throw e;
        }
    }

    /**
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param refDocNo
     * @param outboundOrderTypeId
     * @throws Exception
     */
    public void rollback(String companyCodeId, String plantId, String languageId, String warehouseId,
                         String refDocNo, Long outboundOrderTypeId) throws Exception {
        try {
            initiateRollBack(companyCodeId, plantId, languageId, warehouseId, refDocNo, outboundOrderTypeId);
            log.info("Rollback---> 3. rerun the order ----> " + refDocNo + ", " + outboundOrderTypeId);
            orderService.reRunProcessedOrderV2(refDocNo, outboundOrderTypeId);
        } catch (Exception e) {
            log.error("Exception occurred during Rollback : " + e.toString());
            throw e;
        }
    }

    /**
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param refDocNo
     * @param outboundOrderTypeId
     * @throws Exception
     */
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    @Retryable(value = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 5000, multiplier = 2))
    public void initiateRollBack(String companyCodeId, String plantId, String languageId, String warehouseId,
                                 String refDocNo, Long outboundOrderTypeId) throws Exception {
        try {

            List<OrderManagementLineV2> orderManagementLineV2List = orderManagementLineV2Repository.findAllByCompanyCodeIdAndPlantIdAndLanguageIdAndWarehouseIdAndRefDocNumberAndOutboundOrderTypeIdAndDeletionIndicator(
                    companyCodeId, plantId, languageId, warehouseId, refDocNo, outboundOrderTypeId, 0L);

            log.info("Rollback---> 1. Inventory restore ----> " + refDocNo + ", " + outboundOrderTypeId);
            //if order management line present do un allocation
            if (orderManagementLineV2List != null && !orderManagementLineV2List.isEmpty()) {
                for (OrderManagementLineV2 dbOrderManagementLine : orderManagementLineV2List) {
                    String packBarcodes = dbOrderManagementLine.getProposedPackBarCode();
                    String storageBin = dbOrderManagementLine.getProposedStorageBin();
                    InventoryV2 inventory =
                            inventoryService.getInventoryV2(dbOrderManagementLine.getCompanyCodeId(), dbOrderManagementLine.getPlantId(), dbOrderManagementLine.getLanguageId(),
                                    dbOrderManagementLine.getWarehouseId(), packBarcodes, dbOrderManagementLine.getItemCode(), storageBin,
                                    dbOrderManagementLine.getManufacturerName());
                    Double invQty = inventory.getInventoryQuantity() + dbOrderManagementLine.getAllocatedQty();

                    /*
                     * [Prod Fix: 17-08] - Discussed to make negative inventory to zero
                     */
                    if (invQty < 0D) {
                        invQty = 0D;
                    }

                    inventory.setInventoryQuantity(invQty);
                    log.info("Inventory invQty: " + invQty);

                    Double allocQty = inventory.getAllocatedQuantity() - dbOrderManagementLine.getAllocatedQty();
                    if (allocQty < 0D) {
                        allocQty = 0D;
                    }
                    inventory.setAllocatedQuantity(allocQty);
                    log.info("Inventory allocQty: " + allocQty);
                    Double totQty = invQty + allocQty;
                    inventory.setReferenceField4(totQty);
                    log.info("Inventory totQty: " + totQty);

                    // Create new Inventory Record
                    InventoryV2 inventoryV2 = new InventoryV2();
                    BeanUtils.copyProperties(inventory, inventoryV2, CommonUtils.getNullPropertyNames(inventory));
//                    inventoryV2.setInventoryId(Long.valueOf(System.currentTimeMillis() + "" + 2));
                    inventoryV2 = inventoryV2Repository.save(inventoryV2);
                    log.info("-----InventoryV2 created-------: " + inventoryV2);
                }
                log.info("Rollback---> 1.Inventory restoration Finished ----> " + refDocNo + ", " + outboundOrderTypeId);
            }

            //delete all records from respective tables
            log.info("Rollback---> 2. delete all record initiated ----> " + refDocNo + ", " + outboundOrderTypeId);
            orderManagementLineV2Repository.deleteOutboundProcessingProc(companyCodeId, plantId, languageId, warehouseId, refDocNo, outboundOrderTypeId);
            log.info("Rollback---> 2. delete all record finished ----> " + refDocNo + ", " + outboundOrderTypeId);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     *
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param preOutboundNo
     * @param refDocNumber
     * @param orderManagementLine
     * @param loginUserId
     * @throws Exception
     */
    private void createPickUpHeader(String companyCodeId, String plantId, String languageId, String warehouseId,
                                    String preOutboundNo, String refDocNumber, OrderManagementLineV2 orderManagementLine, String loginUserId) throws Exception {

        String assignPickerId = getHHTUser(companyCodeId, plantId, languageId, warehouseId);
        log.info("AssignPickerId : {}", assignPickerId);

        if (orderManagementLine != null && orderManagementLine.getStatusId() != 47L) {
            log.info("orderManagementLine: " + orderManagementLine);

            PickupHeaderV2 newPickupHeader = new PickupHeaderV2();
            BeanUtils.copyProperties(orderManagementLine, newPickupHeader, CommonUtils.getNullPropertyNames(orderManagementLine));
            long NUM_RAN_CODE = 10;
            String PU_NO = getNextRangeNumber(NUM_RAN_CODE, companyCodeId, plantId, languageId, warehouseId);
            log.info("PU_NO : " + PU_NO);

            newPickupHeader.setAssignedPickerId(assignPickerId);
            newPickupHeader.setPickupNumber(PU_NO);
            newPickupHeader.setPickToQty(orderManagementLine.getAllocatedQty());
            // PICK_UOM
            newPickupHeader.setPickUom(orderManagementLine.getOrderUom());

            // STATUS_ID
            newPickupHeader.setStatusId(48L);
            statusDescription = getStatusDescription(48L, languageId);
            newPickupHeader.setStatusDescription(statusDescription);

            newPickupHeader.setReferenceField5(orderManagementLine.getDescription());
            if(newPickupHeader.getCompanyDescription() == null) {
                description = getDescription(companyCodeId, plantId, languageId, warehouseId);
                newPickupHeader.setCompanyDescription(description.getCompanyDesc());
                newPickupHeader.setPlantDescription(description.getPlantDesc());
                newPickupHeader.setWarehouseDescription(description.getWarehouseDesc());
            }
            newPickupHeader.setDeletionIndicator(0L);
            newPickupHeader.setPickupCreatedBy(loginUserId);
            newPickupHeader.setPickUpdatedBy(loginUserId);
            newPickupHeader.setPickupCreatedOn(new Date());
            newPickupHeader.setPickUpdatedOn(new Date());

            PickupHeaderV2 createdPickupHeader = pickupHeaderV2Repository.save(newPickupHeader);
            log.info("pickupHeader created: " + createdPickupHeader);

            orderManagementLineV2Repository.updateOrderManagementLineV5(companyCodeId, plantId, languageId, warehouseId, refDocNumber, preOutboundNo,
                    PU_NO, assignPickerId, orderManagementLine.getLineNumber(), orderManagementLine.getItemCode(), 48L, statusDescription, new Date());

            outboundLineV2Repository.updateOutboundLineStatusV5(companyCodeId, plantId, languageId, warehouseId, refDocNumber, preOutboundNo,
                    48L, statusDescription, orderManagementLine.getLineNumber(), orderManagementLine.getItemCode(), assignPickerId);
        }

        // OutboundHeader Update
        outboundHeaderV2Repository.updateOutboundHeaderStatusV5(companyCodeId, plantId, languageId, warehouseId, refDocNumber, preOutboundNo, 48L, statusDescription);
        log.info("outboundHeader updated");

        // ORDERMANAGEMENTHEADER Update
        orderManagementHeaderV2Repository.updateOrderManagementHeaderStatusV5(companyCodeId, plantId, languageId, warehouseId, refDocNumber, preOutboundNo, 48L, statusDescription);
        log.info("orderManagementHeader updated");
    }

    /**
     *
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param preOutboundNo
     * @param refDocNumber
     * @param refDocType
     * @param loginUserId
     */
    private void fireBaseNotification(String companyCodeId, String plantId, String languageId, String warehouseId,
                                      String preOutboundNo, String refDocNumber, String refDocType, String loginUserId) {
        try {
            List<String> deviceToken = pickupHeaderV2Repository.getDeviceToken(companyCodeId, plantId, languageId, warehouseId);
            if (deviceToken != null && !deviceToken.isEmpty()) {
                String title = "PICKING";
                String message = refDocType + " ORDER - " + refDocNumber + " - IS RECEIVED";
                NotificationSave notificationInput = new NotificationSave();
                notificationInput.setUserId(Collections.singletonList(loginUserId));
                notificationInput.setUserType(null);
                notificationInput.setMessage(message);
                notificationInput.setTopic(title);
                notificationInput.setReferenceNumber(refDocNumber);
                notificationInput.setDocumentNumber(preOutboundNo);
                notificationInput.setCompanyCodeId(companyCodeId);
                notificationInput.setPlantId(plantId);
                notificationInput.setLanguageId(languageId);
                notificationInput.setWarehouseId(warehouseId);
                notificationInput.setCreatedOn(new Date());
                notificationInput.setCreatedBy(loginUserId);
                String response = pushNotificationService.sendPushNotification(deviceToken, notificationInput);
                if (response.equals("OK")) {
                    pickupHeaderV2Repository.updateNotificationStatus(refDocNumber, warehouseId);
                    log.info("status update successfully");
                }
            }
        } catch (Exception e) {
            log.error("Outbound fireBase notification error " + e.toString());
        }
    }

}