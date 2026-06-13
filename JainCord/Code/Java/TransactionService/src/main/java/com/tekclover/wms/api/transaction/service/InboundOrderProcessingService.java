package com.tekclover.wms.api.transaction.service;

import com.tekclover.wms.api.transaction.controller.exception.BadRequestException;
import com.tekclover.wms.api.transaction.model.IKeyValuePair;
import com.tekclover.wms.api.transaction.model.dto.BomHeader;
import com.tekclover.wms.api.transaction.model.dto.BomLine;
import com.tekclover.wms.api.transaction.model.dto.ImBasicData1;
import com.tekclover.wms.api.transaction.model.dto.ImBasicData1V2;
import com.tekclover.wms.api.transaction.model.inbound.gr.v2.GrHeaderV2;
import com.tekclover.wms.api.transaction.model.inbound.preinbound.InboundIntegrationHeader;
import com.tekclover.wms.api.transaction.model.inbound.preinbound.InboundIntegrationLine;
import com.tekclover.wms.api.transaction.model.inbound.preinbound.InboundIntegrationLog;
import com.tekclover.wms.api.transaction.model.inbound.preinbound.v2.PreInboundHeaderEntityV2;
import com.tekclover.wms.api.transaction.model.inbound.preinbound.v2.PreInboundLineEntityV2;
import com.tekclover.wms.api.transaction.model.inbound.staging.v2.StagingHeaderV2;
import com.tekclover.wms.api.transaction.model.inbound.staging.v2.StagingLineEntityV2;
import com.tekclover.wms.api.transaction.model.inbound.v2.InboundHeaderV2;
import com.tekclover.wms.api.transaction.model.inbound.v2.InboundLineV2;
import com.tekclover.wms.api.transaction.model.inbound.v2.InboundOrderProcess;
import com.tekclover.wms.api.transaction.repository.ImBasicData1V2Repository;
import com.tekclover.wms.api.transaction.repository.PreInboundHeaderV2Repository;
import com.tekclover.wms.api.transaction.util.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
public class InboundOrderProcessingService extends BaseService {

    @Autowired
    MastersService mastersService;

    @Autowired
    OrderProcessingService orderProcessingService;

    @Autowired
    StagingLineService stagingLineService;

    //--------------------------------------------------------------------------------------------------------------
    @Autowired
    ImBasicData1V2Repository imBasicData1V2Repository;

    @Autowired
    PreInboundHeaderV2Repository preInboundHeaderV2Repository;

    //========================================================================V2====================================================================

    /**
     * @param refDocNumber
     * @param inboundIntegrationHeader
     * @return
     * @throws Exception
     */
<<<<<<< HEAD
    public InboundHeaderV2 processInboundReceivedV2(String refDocNumber, InboundIntegrationHeader inboundIntegrationHeader, String pieceNo) throws Exception {
=======
    public InboundHeaderV2 processInboundReceivedV2(String refDocNumber, InboundIntegrationHeader inboundIntegrationHeader) throws Exception {
>>>>>>> 81dc64bb940937a238631ec1ef8d3af347b8ec9e

        InboundOrderProcess inboundOrderProcess = new InboundOrderProcess();
        String companyCodeId = inboundIntegrationHeader.getCompanyCode();
        String plantId = inboundIntegrationHeader.getBranchCode();
        String languageId = inboundIntegrationHeader.getLanguageId() != null ? inboundIntegrationHeader.getLanguageId() : LANG_ID;
        String warehouseId = inboundIntegrationHeader.getWarehouseID();
        Long inboundOrderTypeId = inboundIntegrationHeader.getInboundOrderTypeId();
<<<<<<< HEAD
        log.info("Inbound Process Initiated ------> : {}|{}|{}|{}|{}|{}|{}", companyCodeId, plantId, languageId, warehouseId, refDocNumber, inboundOrderTypeId, pieceNo);
=======
        log.info("Inbound Process Initiated ------> : {}|{}|{}|{}|{}|{}", companyCodeId, plantId, languageId, warehouseId, refDocNumber, inboundOrderTypeId);
>>>>>>> 81dc64bb940937a238631ec1ef8d3af347b8ec9e

        try {
            MW_AMS = inboundIntegrationHeader.getLoginUserId() != null ? inboundIntegrationHeader.getLoginUserId() : MW_AMS;

            //Checking whether received refDocNumber processed already.
<<<<<<< HEAD
//            Optional<PreInboundHeaderEntityV2> orderProcessedStatus = preInboundHeaderV2Repository.findByRefDocNumberAndInboundOrderTypeIdAndDeletionIndicator(refDocNumber, inboundIntegrationHeader.getInboundOrderTypeId(), 0L);
//            if (orderProcessedStatus.isPresent()) { throw new BadRequestException("Order :" + refDocNumber + " already processed. Reprocessing can't be allowed."); }
=======
            Optional<PreInboundHeaderEntityV2> orderProcessedStatus = preInboundHeaderV2Repository.findByRefDocNumberAndInboundOrderTypeIdAndDeletionIndicator(refDocNumber, inboundIntegrationHeader.getInboundOrderTypeId(), 0L);
            if (orderProcessedStatus.isPresent()) { throw new BadRequestException("Order :" + refDocNumber + " already processed. Reprocessing can't be allowed."); }
>>>>>>> 81dc64bb940937a238631ec1ef8d3af347b8ec9e

            String idMasterAuthToken = getIDMasterAuthToken();
            String masterAuthToken = getMasterAuthToken();
            Long statusId = 13L;

            // Getting PreInboundNo, StagingNo, CaseCode from NumberRangeTable
            String preInboundNo = getNextRangeNumber(2L, companyCodeId, plantId, languageId, warehouseId, idMasterAuthToken);
            String stagingNo = getNextRangeNumber(3L, companyCodeId, plantId, languageId, warehouseId, idMasterAuthToken);
            String caseCode = getNextRangeNumber(4L, companyCodeId, plantId, languageId, warehouseId, idMasterAuthToken);
            String grNumber = getNextRangeNumber(5L, companyCodeId, plantId, languageId, warehouseId, idMasterAuthToken);
            log.info("PreInboundNo, StagingNo, CaseCode, GrNumber : {}|{}|{}|{}", preInboundNo, stagingNo, caseCode, grNumber);

            statusDescription = getStatusDescription(statusId, languageId);
            description = getDescription(companyCodeId, plantId, languageId, warehouseId);

            inboundOrderProcess = createBOMPreInboundLines(companyCodeId, plantId, languageId, warehouseId, preInboundNo, description, masterAuthToken, statusId, statusDescription, inboundIntegrationHeader, MW_AMS);

            List<InboundIntegrationLine> inboundIntegrationLines = inboundOrderProcess.getInboundIntegrationLines();
            List<PreInboundLineEntityV2> overallCreatedPreInboundLineList = inboundOrderProcess.getPreInboundLines();

             //Append PREINBOUNDLINE table through below logic
            List<PreInboundLineEntityV2> finalToBeCreatedPreInboundLineList = new ArrayList<>();
            inboundIntegrationLines.stream().forEach(inboundIntegrationLine -> {
                try {
                    finalToBeCreatedPreInboundLineList.add(createPreInboundLineV2(companyCodeId, plantId, languageId, warehouseId, preInboundNo,
                            inboundIntegrationHeader, inboundIntegrationLine, MW_AMS,
<<<<<<< HEAD
                            description, statusId, statusDescription, masterAuthToken, pieceNo));
=======
                            description, statusId, statusDescription, masterAuthToken));
>>>>>>> 81dc64bb940937a238631ec1ef8d3af347b8ec9e
                } catch (Exception e) {
                    throw new BadRequestException("Exception While PreInboundLine Create" + e);
                }
            });
            log.info("toBeCreatedPreInboundLineList [API] : {}", finalToBeCreatedPreInboundLineList.size());

            // Batch Insert - PreInboundLines
            if (!finalToBeCreatedPreInboundLineList.isEmpty()) {
                overallCreatedPreInboundLineList.addAll(finalToBeCreatedPreInboundLineList);
            }

<<<<<<< HEAD
            PreInboundHeaderEntityV2 createPreInboundHeader = createPreInboundHeaderV2(companyCodeId, plantId, languageId, warehouseId,
                    preInboundNo, inboundIntegrationHeader, MW_AMS, description, statusId, statusDescription, pieceNo);
=======
            PreInboundHeaderEntityV2 createPreInboundHeader = createPreInboundHeaderV2(companyCodeId, plantId, languageId, warehouseId, preInboundNo, inboundIntegrationHeader, MW_AMS, description, statusId, statusDescription);
>>>>>>> 81dc64bb940937a238631ec1ef8d3af347b8ec9e

            InboundHeaderV2 createInboundHeader = createInboundHeaderV2(createPreInboundHeader, (long) overallCreatedPreInboundLineList.size());
            List<InboundLineV2> inboundLines = createInboundLines(statusId, statusDescription, overallCreatedPreInboundLineList);

            StagingHeaderV2 stagingHeader = createStagingHeaderV2(createPreInboundHeader, stagingNo);
<<<<<<< HEAD
            List<StagingLineEntityV2> stagingLines = createStagingLines(stagingNo, caseCode, statusId, statusDescription, overallCreatedPreInboundLineList, pieceNo);

            statusDescription = getStatusDescription(16L, languageId);
            GrHeaderV2 createGrHeader = createGrHeaderV2(stagingHeader, caseCode, grNumber, 16L, statusDescription, pieceNo);
=======
            List<StagingLineEntityV2> stagingLines = createStagingLines(stagingNo, caseCode, statusId, statusDescription, overallCreatedPreInboundLineList);

            statusDescription = getStatusDescription(16L, languageId);
            GrHeaderV2 createGrHeader = createGrHeaderV2(stagingHeader, caseCode, grNumber, 16L, statusDescription);
>>>>>>> 81dc64bb940937a238631ec1ef8d3af347b8ec9e

            //Log
            InboundIntegrationLog inboundIntegrationLog = createInboundIntegrationLogV2(createPreInboundHeader);

            // db save/create process
            inboundOrderProcess.setInboundIntegrationHeader(inboundIntegrationHeader);
            inboundOrderProcess.setLoginUserId(MW_AMS);
            inboundOrderProcess.setPreInboundHeader(createPreInboundHeader);
            inboundOrderProcess.setInboundHeader(createInboundHeader);
            inboundOrderProcess.setStagingHeader(stagingHeader);
            inboundOrderProcess.setGrHeader(createGrHeader);
            inboundOrderProcess.setPreInboundLines(overallCreatedPreInboundLineList);
            inboundOrderProcess.setInboundLines(inboundLines);
            inboundOrderProcess.setStagingLines(stagingLines);
            inboundOrderProcess.setInboundIntegrationLog(inboundIntegrationLog);
            InboundHeaderV2 inboundHeader = orderProcessingService.postInboundReceived(companyCodeId, plantId, languageId, warehouseId, refDocNumber, preInboundNo, inboundOrderTypeId, inboundOrderProcess);

            if (createGrHeader != null && createGrHeader.getInboundOrderTypeId() != null && createGrHeader.getInboundOrderTypeId() == 9) {  //ReturnOrderJainCord
               stagingLineService.createGrLineV5(createGrHeader, MW_AMS);
            }

            return inboundHeader;

        } catch (Exception e) {
            log.error("Inbound Order Processing Exception ----> " + e);
            throw e;
        }
    }

    /**
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param preInboundNo
     * @param inboundIntegrationHeader
     * @param inboundIntegrationLine
     * @param bomLine
     * @param loginUserId
     * @param description
     * @param statusDesc
     * @return
     * @throws Exception
     */
    private PreInboundLineEntityV2 createPreInboundLineBOMBasedV2(String companyCodeId, String plantId, String languageId, String warehouseId,
                                                                  String preInboundNo, InboundIntegrationHeader inboundIntegrationHeader,
                                                                  InboundIntegrationLine inboundIntegrationLine, BomLine bomLine, String loginUserId,
                                                                  IKeyValuePair description, Long statusId, String statusDesc, String masterAuthToken) throws Exception {
        try {
            PreInboundLineEntityV2 preInboundLine = new PreInboundLineEntityV2();
            BeanUtils.copyProperties(inboundIntegrationLine, preInboundLine, CommonUtils.getNullPropertyNames(inboundIntegrationLine));
            preInboundLine.setLanguageId(languageId);
            preInboundLine.setCompanyCode(companyCodeId);
            preInboundLine.setPlantId(plantId);
            preInboundLine.setWarehouseId(warehouseId);
            preInboundLine.setRefDocNumber(inboundIntegrationHeader.getRefDocumentNo());
            preInboundLine.setInboundOrderTypeId(inboundIntegrationHeader.getInboundOrderTypeId());
            preInboundLine.setParentProductionOrderNo(inboundIntegrationHeader.getParentProductionOrderNo());

            // PRE_IB_NO
            preInboundLine.setPreInboundNo(preInboundNo);

            // IB__LINE_NO
            preInboundLine.setLineNo(inboundIntegrationLine.getLineReference());

            // ITM_CODE
            preInboundLine.setItemCode(bomLine.getChildItemCode());

            // ITM_TEXT
            preInboundLine.setItemDescription(inboundIntegrationLine.getItemText());

            // MFR
            preInboundLine.setManufacturerPartNo(inboundIntegrationLine.getManufacturerName());

            // ORD_QTY
            double orderQuantity = inboundIntegrationLine.getOrderedQty() * bomLine.getChildItemQuantity();
            preInboundLine.setOrderQty(orderQuantity);

            // ORD_UOM
            preInboundLine.setOrderUom(inboundIntegrationLine.getUom());

            // EA_DATE
            preInboundLine.setExpectedArrivalDate(inboundIntegrationLine.getExpectedDate());

            // STCK_TYP_ID
            preInboundLine.setStockTypeId(1L);

            // SP_ST_IND_ID
            preInboundLine.setSpecialStockIndicatorId(1L);

            // STATUS_ID
            preInboundLine.setStatusId(statusId);
            preInboundLine.setStatusDescription(statusDesc);
            preInboundLine.setCompanyDescription(description.getCompanyDesc());
            preInboundLine.setPlantDescription(description.getPlantDesc());
            preInboundLine.setWarehouseDescription(description.getWarehouseDesc());

            preInboundLine.setOrigin(inboundIntegrationLine.getOrigin());
            preInboundLine.setBrandName(inboundIntegrationLine.getBrand());
            preInboundLine.setManufacturerCode(inboundIntegrationLine.getManufacturerName());
            preInboundLine.setManufacturerName(inboundIntegrationLine.getManufacturerName());
            preInboundLine.setPartnerItemNo(inboundIntegrationLine.getSupplierCode());
            preInboundLine.setContainerNo(inboundIntegrationLine.getContainerNumber());
            preInboundLine.setSupplierName(inboundIntegrationLine.getSupplierName());

            preInboundLine.setMiddlewareId(inboundIntegrationLine.getMiddlewareId());
            preInboundLine.setMiddlewareHeaderId(inboundIntegrationLine.getMiddlewareHeaderId());
            preInboundLine.setMiddlewareTable(inboundIntegrationLine.getMiddlewareTable());
            preInboundLine.setPurchaseOrderNumber(inboundIntegrationLine.getPurchaseOrderNumber());
            preInboundLine.setManufacturerFullName(inboundIntegrationLine.getManufacturerFullName());
            preInboundLine.setReferenceDocumentType(inboundIntegrationHeader.getRefDocumentType());

            preInboundLine.setBranchCode(inboundIntegrationLine.getBranchCode());
            preInboundLine.setTransferOrderNo(inboundIntegrationLine.getTransferOrderNo());
            preInboundLine.setIsCompleted(inboundIntegrationLine.getIsCompleted());
            preInboundLine.setBusinessPartnerCode(inboundIntegrationLine.getSupplierCode());

            // REF_FIELD_1
            preInboundLine.setReferenceField1("CHILD ITEM");

            // REF_FIELD_2
            preInboundLine.setReferenceField2("BOM ITEM");

            // REF_FIELD_4
            preInboundLine.setReferenceField4(inboundIntegrationLine.getSalesOrderReference());

            preInboundLine.setDeletionIndicator(0L);
            preInboundLine.setCreatedBy(loginUserId);
            preInboundLine.setCreatedOn(new Date());
            log.info("preInboundLine [BOM] : {}", preInboundLine);
            return preInboundLine;
        } catch (Exception e) {
            log.error("Exception while create preInboundLine - BOM : " + e);
            throw e;
        }
    }

    /**
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param preInboundNo
     * @param inboundIntegrationHeader
     * @param inboundIntegrationLine
     * @param loginUserId
     * @param description
     * @param statusDesc
     * @return
     * @throws Exception
     */
    private PreInboundLineEntityV2 createPreInboundLineV2(String companyCodeId, String plantId, String languageId, String warehouseId,
                                                          String preInboundNo, InboundIntegrationHeader inboundIntegrationHeader,
                                                          InboundIntegrationLine inboundIntegrationLine, String loginUserId,
<<<<<<< HEAD
                                                          IKeyValuePair description, Long statusId, String statusDesc, String masterAuthToken, String pieceNo) throws Exception {
=======
                                                          IKeyValuePair description, Long statusId, String statusDesc, String masterAuthToken) throws Exception {
>>>>>>> 81dc64bb940937a238631ec1ef8d3af347b8ec9e
        try {
            PreInboundLineEntityV2 preInboundLine = new PreInboundLineEntityV2();
            BeanUtils.copyProperties(inboundIntegrationLine, preInboundLine, CommonUtils.getNullPropertyNames(inboundIntegrationLine));
            preInboundLine.setLanguageId(languageId);
            preInboundLine.setCompanyCode(companyCodeId);
            preInboundLine.setPlantId(plantId);
            preInboundLine.setWarehouseId(warehouseId);
            preInboundLine.setRefDocNumber(inboundIntegrationHeader.getRefDocumentNo());
            preInboundLine.setInboundOrderTypeId(inboundIntegrationHeader.getInboundOrderTypeId());
            preInboundLine.setParentProductionOrderNo(inboundIntegrationHeader.getParentProductionOrderNo());

            // PRE_IB_NO
            preInboundLine.setPreInboundNo(preInboundNo);

            // IB__LINE_NO
            preInboundLine.setLineNo(inboundIntegrationLine.getLineReference());

            // ITM_CODE
            preInboundLine.setItemCode(inboundIntegrationLine.getItemCode());

            // ITEM_TEXT - Pass CHL_ITM_CODE as ITM_CODE in IMBASICDATA1 table and fetch ITEM_TEXT and insert
            preInboundLine.setItemDescription(inboundIntegrationLine.getItemText());

            // MFR_PART
            preInboundLine.setManufacturerPartNo(inboundIntegrationLine.getManufacturerPartNo());

            // PARTNER_CODE
            preInboundLine.setBusinessPartnerCode(inboundIntegrationLine.getSupplierCode());

            // ORD_QTY
            preInboundLine.setOrderQty(inboundIntegrationLine.getOrderedQty());

            // ORD_UOM
            preInboundLine.setOrderUom(inboundIntegrationLine.getUom());

            // STCK_TYP_ID
            preInboundLine.setStockTypeId(1L);

            // SP_ST_IND_ID
            preInboundLine.setSpecialStockIndicatorId(1L);

            // EA_DATE
            log.info("inboundIntegrationLine.getExpectedDate() : {}", inboundIntegrationLine.getExpectedDate());
            preInboundLine.setExpectedArrivalDate(inboundIntegrationLine.getExpectedDate());

            // ITM_CASE_QTY
            preInboundLine.setItemCaseQty(inboundIntegrationLine.getItemCaseQty());

            // REF_FIELD_4
            preInboundLine.setReferenceField4(inboundIntegrationLine.getSalesOrderReference());

            // Status ID - statusId changed to reduce one less step process and avoid deadlock while updating status
            preInboundLine.setStatusId(statusId);
            preInboundLine.setStatusDescription(statusDesc);
            preInboundLine.setCompanyDescription(description.getCompanyDesc());
            preInboundLine.setPlantDescription(description.getPlantDesc());
            preInboundLine.setWarehouseDescription(description.getWarehouseDesc());

            preInboundLine.setOrigin(inboundIntegrationLine.getOrigin());
            preInboundLine.setBrandName(inboundIntegrationLine.getBrand());
            preInboundLine.setManufacturerCode(inboundIntegrationLine.getManufacturerName());
            preInboundLine.setManufacturerName(inboundIntegrationLine.getManufacturerName());
            preInboundLine.setPartnerItemNo(inboundIntegrationLine.getSupplierCode());
            preInboundLine.setContainerNo(inboundIntegrationLine.getContainerNumber());
            preInboundLine.setSupplierName(inboundIntegrationLine.getSupplierName());

            preInboundLine.setMiddlewareId(inboundIntegrationLine.getMiddlewareId());
            preInboundLine.setMiddlewareHeaderId(inboundIntegrationLine.getMiddlewareHeaderId());
            preInboundLine.setMiddlewareTable(inboundIntegrationLine.getMiddlewareTable());
            preInboundLine.setPurchaseOrderNumber(inboundIntegrationLine.getPurchaseOrderNumber());
            preInboundLine.setReferenceDocumentType(inboundIntegrationHeader.getRefDocumentType());
            preInboundLine.setManufacturerFullName(inboundIntegrationLine.getManufacturerFullName());

            preInboundLine.setBranchCode(inboundIntegrationLine.getBranchCode());
            preInboundLine.setTransferOrderNo(inboundIntegrationLine.getTransferOrderNo());
            preInboundLine.setIsCompleted(inboundIntegrationLine.getIsCompleted());

<<<<<<< HEAD
            preInboundLine.setPieceNo(pieceNo);
=======
>>>>>>> 81dc64bb940937a238631ec1ef8d3af347b8ec9e
            preInboundLine.setDeletionIndicator(0L);
            preInboundLine.setCreatedBy(loginUserId);
            preInboundLine.setCreatedOn(new Date());

            log.info("preInboundLine : " + preInboundLine);
            return preInboundLine;
        } catch (Exception e) {
            log.error("PreInboundLine Create Exception: " + e);
            throw e;
        }
    }

    /**
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param preInboundNo
     * @param inboundIntegrationHeader
     * @param loginUserId
     * @return
     * @throws Exception
     */
    private PreInboundHeaderEntityV2 createPreInboundHeaderV2(String companyCodeId, String plantId, String languageId, String warehouseId,
                                                              String preInboundNo, InboundIntegrationHeader inboundIntegrationHeader,
<<<<<<< HEAD
                                                              String loginUserId, IKeyValuePair description, Long statusId, String statusDesc, String pieceNo) throws Exception {
=======
                                                              String loginUserId, IKeyValuePair description, Long statusId, String statusDesc) throws Exception {
>>>>>>> 81dc64bb940937a238631ec1ef8d3af347b8ec9e
        try {
            PreInboundHeaderEntityV2 preInboundHeader = new PreInboundHeaderEntityV2();
            BeanUtils.copyProperties(inboundIntegrationHeader, preInboundHeader, CommonUtils.getNullPropertyNames(inboundIntegrationHeader));
            preInboundHeader.setCompanyCode(companyCodeId);
            preInboundHeader.setPlantId(plantId);
            preInboundHeader.setLanguageId(languageId);                                    // LANG_ID
            preInboundHeader.setWarehouseId(warehouseId);
            preInboundHeader.setRefDocNumber(inboundIntegrationHeader.getRefDocumentNo());
            preInboundHeader.setPreInboundNo(preInboundNo);                                                // PRE_IB_NO
            preInboundHeader.setReferenceDocumentType(inboundIntegrationHeader.getRefDocumentType());    // REF_DOC_TYP - Hard Coded Value "ASN"
            preInboundHeader.setInboundOrderTypeId(inboundIntegrationHeader.getInboundOrderTypeId());    // IB_ORD_TYP_ID
            preInboundHeader.setRefDocDate(inboundIntegrationHeader.getOrderReceivedOn());                // REF_DOC_DATE
            // Status ID - statusId changed to reduce one less step process and avoid deadlock while updating status
            preInboundHeader.setStatusId(statusId);
            preInboundHeader.setStatusDescription(statusDesc);
            preInboundHeader.setCompanyDescription(description.getCompanyDesc());
            preInboundHeader.setPlantDescription(description.getPlantDesc());
            preInboundHeader.setWarehouseDescription(description.getWarehouseDesc());

            preInboundHeader.setMiddlewareId(inboundIntegrationHeader.getMiddlewareId());
            preInboundHeader.setMiddlewareTable(inboundIntegrationHeader.getMiddlewareTable());
            preInboundHeader.setContainerNo(inboundIntegrationHeader.getContainerNo());

            preInboundHeader.setTransferOrderDate(inboundIntegrationHeader.getTransferOrderDate());
            preInboundHeader.setSourceBranchCode(inboundIntegrationHeader.getSourceBranchCode());
            preInboundHeader.setSourceCompanyCode(inboundIntegrationHeader.getSourceCompanyCode());
            preInboundHeader.setIsCompleted(inboundIntegrationHeader.getIsCompleted());
            preInboundHeader.setIsCancelled(inboundIntegrationHeader.getIsCancelled());
            preInboundHeader.setMUpdatedOn(inboundIntegrationHeader.getUpdatedOn());

<<<<<<< HEAD
            preInboundHeader.setPieceNo(pieceNo);
=======
>>>>>>> 81dc64bb940937a238631ec1ef8d3af347b8ec9e
            preInboundHeader.setDeletionIndicator(0L);
            preInboundHeader.setCreatedBy(loginUserId);
            preInboundHeader.setCreatedOn(new Date());

            log.info("createdPreInboundHeader : " + preInboundHeader);
            return preInboundHeader;
        } catch (Exception e) {
            log.error("PreInboundHeader Create Exception : " + e);
            throw e;
        }
    }

    /**
     *
     * @param preInboundHeader
     * @param orderLinesCount
     * @return
     * @throws Exception
     */
    private InboundHeaderV2 createInboundHeaderV2(PreInboundHeaderEntityV2 preInboundHeader, Long orderLinesCount) throws Exception {
        try {
            InboundHeaderV2 inboundHeader = new InboundHeaderV2();
            BeanUtils.copyProperties(preInboundHeader, inboundHeader, CommonUtils.getNullPropertyNames(preInboundHeader));
            inboundHeader.setCountOfOrderLines(orderLinesCount);       //count of lines
            return inboundHeader;
        } catch (Exception e) {
            log.error("Exception while InboundHeader Create : " + e);
            throw e;
        }
    }

    /**
     * @param preInboundHeader
     * @param stagingNo
     * @return
     */
    public StagingHeaderV2 createStagingHeaderV2(PreInboundHeaderEntityV2 preInboundHeader, String stagingNo) throws Exception {
        try {
            StagingHeaderV2 stagingHeader = new StagingHeaderV2();
            BeanUtils.copyProperties(preInboundHeader, stagingHeader, CommonUtils.getNullPropertyNames(preInboundHeader));
            stagingHeader.setStagingNo(stagingNo);
            // GR_MTD
            stagingHeader.setGrMtd("INTEGRATION");
            return stagingHeader;
        } catch (Exception e) {
            log.error("Exception while StagingHeader Create : " + e);
            throw e;
        }
    }

    /**
     *
     * @param stagingHeader
     * @param caseCode
     * @param grNumber
     * @param statusId
     * @param statusDesc
     * @return
     * @throws Exception
     */
<<<<<<< HEAD
    public GrHeaderV2 createGrHeaderV2(StagingHeaderV2 stagingHeader, String caseCode, String grNumber, Long statusId, String statusDesc, String pieceNo) throws Exception {
=======
    public GrHeaderV2 createGrHeaderV2(StagingHeaderV2 stagingHeader, String caseCode, String grNumber, Long statusId, String statusDesc) throws Exception {
>>>>>>> 81dc64bb940937a238631ec1ef8d3af347b8ec9e
        try {
            GrHeaderV2 grHeader = new GrHeaderV2();
            BeanUtils.copyProperties(stagingHeader, grHeader, CommonUtils.getNullPropertyNames(stagingHeader));
            grHeader.setCaseCode(caseCode);
            grHeader.setPalletCode(caseCode);
            grHeader.setGoodsReceiptNo(grNumber);
            grHeader.setStatusId(statusId);
<<<<<<< HEAD
            grHeader.setPieceNo(pieceNo);
=======
>>>>>>> 81dc64bb940937a238631ec1ef8d3af347b8ec9e
            grHeader.setStatusDescription(statusDesc);
            return grHeader;
        } catch (Exception e) {
            log.error("Exception while GrHeader Create : " + e);
            throw e;
        }
    }

    /**
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param preInboundNo
     * @param description
     * @param masterAuthToken
     * @param statusId
     * @param inboundIntegrationHeader
     * @return
     * @throws Exception
     */
    public InboundOrderProcess createBOMPreInboundLines(String companyCodeId, String plantId, String languageId, String warehouseId, String preInboundNo,
                                                        IKeyValuePair description, String masterAuthToken, Long statusId, String statusDescription,
                                                        InboundIntegrationHeader inboundIntegrationHeader, String loginUserId) throws Exception {
        List<PreInboundLineEntityV2> overallCreatedPreInboundLineList = new ArrayList<>();
        List<InboundIntegrationLine> inboundIntegrationLines = new ArrayList<>();
        InboundOrderProcess inboundOrderProcess = new InboundOrderProcess();
        for (InboundIntegrationLine inboundIntegrationLine : inboundIntegrationHeader.getInboundIntegrationLine()) {
            ImBasicData1V2 imBasicData1 = imBasicData1V2Repository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndItemCodeAndManufacturerPartNoAndDeletionIndicator(
                    languageId, companyCodeId, plantId, warehouseId,
                    inboundIntegrationLine.getItemCode().trim(), inboundIntegrationLine.getManufacturerName(), 0L);
            log.info("imBasicData1 exists: {}", imBasicData1);

            if (inboundIntegrationLine.getItemText() == null && imBasicData1 != null) {
                inboundIntegrationLine.setItemText(imBasicData1.getDescription());
            }

            // If ITM_CODE value is Null, then insert a record in IMBASICDATA1 table as below
            if (imBasicData1 == null) {
                imBasicData1 = new ImBasicData1V2();
                BeanUtils.copyProperties(inboundIntegrationLine, imBasicData1, CommonUtils.getNullPropertyNames(inboundIntegrationLine));
                imBasicData1.setLanguageId(languageId);                                         // LANG_ID
                imBasicData1.setWarehouseId(warehouseId);                                       // WH_ID
                imBasicData1.setCompanyCodeId(companyCodeId);                                   // C_ID
                imBasicData1.setPlantId(plantId);                                               // PLANT_ID
                imBasicData1.setItemCode(inboundIntegrationLine.getItemCode());                 // ITM_CODE
                imBasicData1.setUomId(inboundIntegrationLine.getUom());                         // UOM_ID
                imBasicData1.setDescription(inboundIntegrationLine.getItemText());              // ITEM_TEXT
                imBasicData1.setManufacturerPartNo(inboundIntegrationLine.getManufacturerName());
                imBasicData1.setManufacturerName(inboundIntegrationLine.getManufacturerName());
                imBasicData1.setManufacturerCode(inboundIntegrationLine.getManufacturerCode());
                imBasicData1.setCapacityCheck(false);
                imBasicData1.setDeletionIndicator(0L);
                imBasicData1.setCompanyDescription(description.getCompanyDesc());
                imBasicData1.setPlantDescription(description.getPlantDesc());
                imBasicData1.setWarehouseDescription(description.getWarehouseDesc());

                imBasicData1.setStatusId(1L);                                                // STATUS_ID
                ImBasicData1 createdImBasicData1 = mastersService.createImBasicData1V2(imBasicData1, loginUserId, masterAuthToken);
                log.info("ImBasicData1 created: {}", createdImBasicData1);
            }

            /*-------------Insertion of BOM item in PREINBOUNDLINE table---------------------------------------------------------*/
            /*
             * Before inserting the record into Preinbound table, fetch ITM_CODE from InboundIntegrationHeader table and
             * pass into BOMHEADER table as PAR_ITM_CODE and validate record is Not Null
             */
            BomHeader bomHeader = mastersService.getBomHeader(inboundIntegrationLine.getItemCode(), warehouseId, companyCodeId, plantId, languageId, masterAuthToken);
            log.info("bomHeader [BOM] : {}", bomHeader);
            if (bomHeader != null) {
                BomLine[] bomLine = mastersService.getBomLine(bomHeader.getBomNumber(), companyCodeId, plantId, languageId, warehouseId, masterAuthToken);
                for (BomLine dbBomLine : bomLine) {
                    PreInboundLineEntityV2 preInboundLineEntity = createPreInboundLineBOMBasedV2(companyCodeId, plantId, languageId, warehouseId,
                            preInboundNo, inboundIntegrationHeader, inboundIntegrationLine,
                            dbBomLine, loginUserId, description, statusId, statusDescription, masterAuthToken);
                    overallCreatedPreInboundLineList.add(preInboundLineEntity);
                }
            }
            inboundIntegrationLines.add(inboundIntegrationLine);
        }
        log.info("preInboundLineEntity [BOM] : {}", overallCreatedPreInboundLineList.size());
        inboundOrderProcess.setPreInboundLines(overallCreatedPreInboundLineList);
        inboundOrderProcess.setInboundIntegrationLines(inboundIntegrationLines);
        return inboundOrderProcess;
    }

    /**
     *
     * @param stagingNo
     * @param caseCode
     * @param statusId
     * @param statusDesc
     * @param preInboundLines
     * @return
     */
    public List<StagingLineEntityV2> createStagingLines(String stagingNo, String caseCode, Long statusId, String statusDesc,
<<<<<<< HEAD
                                                        List<PreInboundLineEntityV2> preInboundLines, String pieceNo) {
=======
                                                        List<PreInboundLineEntityV2> preInboundLines) {
>>>>>>> 81dc64bb940937a238631ec1ef8d3af347b8ec9e
        try {
            return preInboundLines.stream().map(preInboundLine -> {
                StagingLineEntityV2 stagingLine = new StagingLineEntityV2();
                BeanUtils.copyProperties(preInboundLine, stagingLine, CommonUtils.getNullPropertyNames(preInboundLine));
                stagingLine.setStagingNo(stagingNo);
                stagingLine.setCaseCode(caseCode);
                stagingLine.setPalletCode(caseCode);
                stagingLine.setStatusId(statusId);
                stagingLine.setRec_accept_qty(0D);
                stagingLine.setRec_damage_qty(0D);
<<<<<<< HEAD
                stagingLine.setPieceNo(pieceNo);
=======
>>>>>>> 81dc64bb940937a238631ec1ef8d3af347b8ec9e
                stagingLine.setStatusDescription(statusDesc);
                return stagingLine;
            }).collect(toList());
        } catch (Exception e) {
            log.error("Exception while StagingLines Create : " + e);
            throw e;
        }
    }

    /**
     *
     * @param statusId
     * @param statusDesc
     * @param preInboundLines
     * @return
     * @throws Exception
     */
    public List<InboundLineV2> createInboundLines(Long statusId, String statusDesc, List<PreInboundLineEntityV2> preInboundLines) throws Exception {
        try {
            return preInboundLines.stream().map(preInboundLine -> {
                InboundLineV2 inboundLine = new InboundLineV2();
                BeanUtils.copyProperties(preInboundLine, inboundLine, CommonUtils.getNullPropertyNames(preInboundLine));
                inboundLine.setStatusId(statusId);
                inboundLine.setStatusDescription(statusDesc);
                return inboundLine;
            }).collect(toList());
        }  catch (Exception e) {
            log.error("Exception while InboundLines Create : " + e);
            throw e;
        }
    }

    /**
     * @param createdPreInboundHeader
     * @return
     * @throws Exception
     */
    public InboundIntegrationLog createInboundIntegrationLogV2(PreInboundHeaderEntityV2 createdPreInboundHeader) throws Exception {
        try {
            InboundIntegrationLog dbInboundIntegrationLog = new InboundIntegrationLog();
            BeanUtils.copyProperties(createdPreInboundHeader, dbInboundIntegrationLog, CommonUtils.getNullPropertyNames(createdPreInboundHeader));
            dbInboundIntegrationLog.setLanguageId(createdPreInboundHeader.getLanguageId());
            dbInboundIntegrationLog.setCompanyCodeId(createdPreInboundHeader.getCompanyCode());
            dbInboundIntegrationLog.setPlantId(createdPreInboundHeader.getPlantId());
            dbInboundIntegrationLog.setWarehouseId(createdPreInboundHeader.getWarehouseId());
            dbInboundIntegrationLog.setIntegrationLogNumber(createdPreInboundHeader.getPreInboundNo());
            dbInboundIntegrationLog.setRefDocNumber(createdPreInboundHeader.getRefDocNumber());
            dbInboundIntegrationLog.setOrderReceiptDate(createdPreInboundHeader.getCreatedOn());
            dbInboundIntegrationLog.setIntegrationStatus("SUCCESS");
            dbInboundIntegrationLog.setOrderReceiptDate(createdPreInboundHeader.getCreatedOn());
            dbInboundIntegrationLog.setDeletionIndicator(0L);
            dbInboundIntegrationLog.setCreatedBy(createdPreInboundHeader.getCreatedBy());
            dbInboundIntegrationLog.setCreatedOn(new Date());
            log.info("dbInboundIntegrationLog : {}", dbInboundIntegrationLog);
            return dbInboundIntegrationLog;
        } catch (Exception e) {
            log.error("InboundIntegrationLog Create[Success] Exception : " + e);
            throw e;
        }
    }
}