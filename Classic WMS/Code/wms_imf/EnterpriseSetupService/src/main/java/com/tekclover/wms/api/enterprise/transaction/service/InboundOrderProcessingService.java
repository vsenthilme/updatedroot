package com.tekclover.wms.api.enterprise.transaction.service;

import com.tekclover.wms.api.enterprise.controller.exception.BadRequestException;
import com.tekclover.wms.api.enterprise.transaction.model.IKeyValuePair;
import com.tekclover.wms.api.enterprise.transaction.model.KeyValuePair;
import com.tekclover.wms.api.enterprise.transaction.model.dto.*;
import com.tekclover.wms.api.enterprise.transaction.model.inbound.gr.v2.GrHeaderV2;
import com.tekclover.wms.api.enterprise.transaction.model.inbound.preinbound.InboundIntegrationHeader;
import com.tekclover.wms.api.enterprise.transaction.model.inbound.preinbound.InboundIntegrationLine;
import com.tekclover.wms.api.enterprise.transaction.model.inbound.preinbound.v2.PreInboundHeaderEntityV2;
import com.tekclover.wms.api.enterprise.transaction.model.inbound.preinbound.v2.PreInboundLineEntityV2;
import com.tekclover.wms.api.enterprise.transaction.model.inbound.staging.v2.StagingHeaderV2;
import com.tekclover.wms.api.enterprise.transaction.model.inbound.staging.v2.StagingLineEntityV2;
import com.tekclover.wms.api.enterprise.transaction.model.inbound.v2.InboundHeaderV2;
import com.tekclover.wms.api.enterprise.transaction.model.inbound.v2.InboundLineV2;
import com.tekclover.wms.api.enterprise.transaction.model.inbound.v2.InboundOrderProcess;
import com.tekclover.wms.api.enterprise.transaction.model.warehouse.inbound.WarehouseApiResponse;
import com.tekclover.wms.api.enterprise.transaction.repository.PreInboundHeaderV2Repository;
import com.tekclover.wms.api.enterprise.transaction.repository.StagingLineV2Repository;
import com.tekclover.wms.api.enterprise.transaction.util.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class InboundOrderProcessingService extends BaseService {

    @Autowired
    private MastersService mastersService;

    @Autowired
    private TransactionService transactionService;

    //========================================================================V2====================================================================

    /**
     * @param refDocNumber
     * @param inboundIntegrationHeader
     * @return
     * @throws Exception
     */
    public InboundHeaderV2 processInboundReceivedV3(String refDocNumber, InboundIntegrationHeader inboundIntegrationHeader) throws Exception {

        InboundOrderProcess inboundOrderProcess = new InboundOrderProcess();
        String companyCodeId = inboundIntegrationHeader.getCompanyCode();
        String plantId = inboundIntegrationHeader.getBranchCode();
        String languageId = inboundIntegrationHeader.getLanguageId() != null ? inboundIntegrationHeader.getLanguageId() : "EN";
        String warehouseId = inboundIntegrationHeader.getWarehouseID();
        log.info("CompanyCodeId, plantId, languageId, warehouseId : " + companyCodeId + ", " + plantId + ", " + languageId + ", " + warehouseId);


        try {
            log.info("Inbound Process Initiated ------> " + refDocNumber + ", " + inboundIntegrationHeader.getInboundOrderTypeId());
            if (inboundIntegrationHeader.getLoginUserId() != null) {
                MW_AMS = inboundIntegrationHeader.getLoginUserId();
            }

            // save/create process
            inboundOrderProcess.setCompanyCodeId(companyCodeId);
            inboundOrderProcess.setPlantId(plantId);
            inboundOrderProcess.setLanguageId(languageId);
            inboundOrderProcess.setWarehouseId(warehouseId);
            inboundOrderProcess.setRefDocNumber(refDocNumber);
            inboundOrderProcess.setInboundOrderTypeId(inboundIntegrationHeader.getInboundOrderTypeId());
            inboundOrderProcess.setInboundIntegrationHeader(inboundIntegrationHeader);
            inboundOrderProcess.setLoginUserId(MW_AMS);

            String transactionAuthToken = getTransactionAuthToken();
            //Checking whether received refDocNumber processed already.
            PreInboundHeaderEntityV2 orderProcessedStatus = transactionService.
                    getPreInboundHeaderForDuplicateCheckV2(companyCodeId, plantId, languageId, warehouseId, refDocNumber,
                                                           inboundOrderProcess.getInboundOrderTypeId(), transactionAuthToken);
            if (orderProcessedStatus != null) {
                throw new BadRequestException("Order :" + refDocNumber + " already processed. Reprocessing can't be allowed.");
            }

            List<InboundIntegrationLine> inboundIntegrationLines = new ArrayList<>();

            String idMasterAuthToken = getIDMasterAuthToken();
            String masterAuthToken = getMasterAuthToken();
            Long statusId = 13L;

            // Getting PreInboundNo, StagingNo, CaseCode from NumberRangeTable
            String preInboundNo = getNextRangeNumber(2L, companyCodeId, plantId, languageId, warehouseId, idMasterAuthToken);
            String stagingNo = getNextRangeNumber(3L, companyCodeId, plantId, languageId, warehouseId, idMasterAuthToken);
            String caseCode = getNextRangeNumber(4L, companyCodeId, plantId, languageId, warehouseId, idMasterAuthToken);
            String grNumber = getNextRangeNumber(5L, companyCodeId, plantId, languageId, warehouseId, idMasterAuthToken);
            log.info("PreInboundNo, StagingNo, CaseCode, GrNumber : " + preInboundNo + ", " + stagingNo + ", " + caseCode + ", " + grNumber);

            statusDescription = getStatusDescription(statusId, languageId);

            KeyValuePair description = getDescription(companyCodeId, plantId, languageId, warehouseId, transactionAuthToken);

            List<PreInboundLineEntityV2> overallCreatedPreInboundLineList = new ArrayList<>();
            List<PreInboundLineEntityV2> toBeCreatedPreInboundLineList = new ArrayList<>();
            for (InboundIntegrationLine inboundIntegrationLine : inboundIntegrationHeader.getInboundIntegrationLine()) {
                log.info("inboundIntegrationLine : " + inboundIntegrationLine);

                ImBasicData imBasicData = new ImBasicData();
                imBasicData.setCompanyCodeId(companyCodeId);
                imBasicData.setPlantId(plantId);
                imBasicData.setLanguageId(languageId);
                imBasicData.setWarehouseId(warehouseId);
                imBasicData.setItemCode(inboundIntegrationLine.getItemCode().trim());
                imBasicData.setManufacturerName(inboundIntegrationLine.getManufacturerName());

                ImBasicData1V2 imBasicData1 = mastersService.getImBasicData1ByItemCodeV2(imBasicData, masterAuthToken);
                log.info("imBasicData1 exists: " + imBasicData1);

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
                    if (inboundIntegrationLine.getManufacturerName() != null) {
                        imBasicData1.setManufacturerPartNo(inboundIntegrationLine.getManufacturerName());
                        imBasicData1.setManufacturerName(inboundIntegrationLine.getManufacturerName());
                    } else {
                        imBasicData1.setManufacturerPartNo(COMPANY_CODE);
                        imBasicData1.setManufacturerName(COMPANY_CODE);
                    }
                    imBasicData1.setCapacityCheck(false);
                    imBasicData1.setDeletionIndicator(0L);
                    imBasicData1.setCompanyDescription(description.getCompanyDesc());
                    imBasicData1.setPlantDescription(description.getPlantDesc());
                    imBasicData1.setWarehouseDescription(description.getWarehouseDesc());

                    imBasicData1.setStatusId(1L);                                                // STATUS_ID
                    ImBasicData1 createdImBasicData1 = mastersService.createImBasicData1V2(imBasicData1, MW_AMS, masterAuthToken);
                    log.info("ImBasicData1 created: " + createdImBasicData1);
                }

                /*-------------Insertion of BOM item in PREINBOUNDLINE table---------------------------------------------------------*/
                /*
                 * Before inserting the record into Preinbound table, fetch ITM_CODE from InboundIntegrationHeader (MONGO) table and
                 * pass into BOMHEADER table as PAR_ITM_CODE and validate record is Not Null
                 */
                BomHeader bomHeader = mastersService.getBomHeader(inboundIntegrationLine.getItemCode(), warehouseId,
                                                                  companyCodeId, plantId, languageId, masterAuthToken);
                log.info("bomHeader [BOM] : " + bomHeader);
                if (bomHeader != null) {
                    BomLine[] bomLine = mastersService.getBomLine(bomHeader.getBomNumber(),
                                                                  companyCodeId, plantId, languageId, warehouseId, masterAuthToken);
                    for (BomLine dbBomLine : bomLine) {
                        PreInboundLineEntityV2 preInboundLineEntity = createPreInboundLineBOMBasedV2(companyCodeId, plantId, languageId, warehouseId,
                                                                                                     preInboundNo, inboundIntegrationHeader, inboundIntegrationLine,
                                                                                                     dbBomLine, MW_AMS, description, statusId, statusDescription);
                        toBeCreatedPreInboundLineList.add(preInboundLineEntity);
                        log.info("preInboundLineEntity [BOM] : " + toBeCreatedPreInboundLineList.size());
                    }

                    // Batch Insert - PreInboundLines
                    if (!toBeCreatedPreInboundLineList.isEmpty()) {
//                        List<PreInboundLineEntityV2> createdPreInboundLine = preInboundLineV2Repository.saveAll(toBeCreatedPreInboundLineList);
//                        log.info("createdPreInboundLine [BOM] : " + createdPreInboundLine);
//                        overallCreatedPreInboundLineList.addAll(createdPreInboundLine);

                        log.info("createdPreInboundLine [BOM] : " + toBeCreatedPreInboundLineList);
                        overallCreatedPreInboundLineList.addAll(toBeCreatedPreInboundLineList);
                    }
                }
                inboundIntegrationLines.add(inboundIntegrationLine);
            }

            /*
             * Append PREINBOUNDLINE table through below logic
             */
            List<PreInboundLineEntityV2> finalToBeCreatedPreInboundLineList = new ArrayList<>();
            inboundIntegrationLines.stream().forEach(inboundIntegrationLine -> {
                try {
                    finalToBeCreatedPreInboundLineList.add(createPreInboundLineV2(companyCodeId, plantId, languageId, warehouseId, preInboundNo,
                                                                                  inboundIntegrationHeader, inboundIntegrationLine, MW_AMS,
                                                                                  description, statusId, statusDescription));
                } catch (Exception e) {
                    throw new BadRequestException("Exception While PreInboundLine Create" + e.toString());
                }
            });
            log.info("toBeCreatedPreInboundLineList [API] : " + finalToBeCreatedPreInboundLineList.size());

            // Batch Insert - PreInboundLines
            if (!finalToBeCreatedPreInboundLineList.isEmpty()) {
//                List<PreInboundLineEntityV2> createdPreInboundLine = preInboundLineV2Repository.saveAll(finalToBeCreatedPreInboundLineList);
//                log.info("createdPreInboundLine [API] : " + createdPreInboundLine);
//                overallCreatedPreInboundLineList.addAll(createdPreInboundLine);

                log.info("createdPreInboundLine [API] : " + finalToBeCreatedPreInboundLineList);
                overallCreatedPreInboundLineList.addAll(finalToBeCreatedPreInboundLineList);
            }
            inboundOrderProcess.setPreInboundLines(overallCreatedPreInboundLineList);

            List<InboundLineV2> inboundLines = overallCreatedPreInboundLineList.stream().map(preInboundLine -> {
                InboundLineV2 inboundLine = new InboundLineV2();
                BeanUtils.copyProperties(preInboundLine, inboundLine, CommonUtils.getNullPropertyNames(preInboundLine));
                return inboundLine;
            }).collect(Collectors.toList());
//            inboundLineV2Repository.saveAll(inboundLines);
            inboundOrderProcess.setInboundLines(inboundLines);

            List<StagingLineEntityV2> stagingLines = overallCreatedPreInboundLineList.stream().map(preInboundLine -> {
                StagingLineEntityV2 stagingLine = new StagingLineEntityV2();
                BeanUtils.copyProperties(preInboundLine, stagingLine, CommonUtils.getNullPropertyNames(preInboundLine));
                stagingLine.setStagingNo(stagingNo);
                stagingLine.setCaseCode(caseCode);
                stagingLine.setPalletCode(caseCode);
                stagingLine.setStatusId(14L);
                stagingLine.setStatusDescription(getStatusDescription(14L, languageId));

//                if(preInboundLine.getBarcodeId() == null) {
//                    //Barcode
//                    List<String> barcode = stagingLineV2Repository.getPartnerItemBarcode(preInboundLine.getItemCode(),
//                                                                                         preInboundLine.getCompanyCode(),
//                                                                                         preInboundLine.getPlantId(),
//                                                                                         preInboundLine.getWarehouseId(),
//                                                                                         preInboundLine.getManufacturerName(),
//                                                                                         preInboundLine.getLanguageId());
//                    log.info("Barcode : " + barcode);
//                    if (barcode != null && !barcode.isEmpty()) {
//                        stagingLine.setBarcodeId(barcode.get(0));
//                    }
//                }

                //-----------------PROP_ST_BIN---------------------------------------------
//                StorageBin storageBin = null;
//                try {
//                    String referenceField1 = preInboundLine.getArticleNo().substring(0, 2);
//                    String referenceField2 = preInboundLine.getGender();
//                    log.info("referenceField1, referenceField2, companyCode, plantId, warehouseId, languageId: " + referenceField1 + "," + referenceField2 + "," + companyCodeId + "," + plantId + "," + warehouseId + "," + languageId);
//                    storageBin = mastersService.getStorageBinV3(referenceField1, referenceField2, companyCodeId, plantId, warehouseId, languageId, masterAuthToken);
//                    log.info("InterimStorageBin: " + storageBin);
//                    if (storageBin != null) {
//                        stagingLine.setReferenceField5(storageBin.getStorageBin());
//                    }
//                } catch (Exception e) {
//                    throw new BadRequestException("Invalid StorageBin");
//                }

                return stagingLine;
            }).collect(Collectors.toList());
//            stagingLineV2Repository.saveAll(stagingLines);
//            log.info("StagingLines Created : " + stagingLines);
            inboundOrderProcess.setStagingLines(stagingLines);

            /*------------------Insert into PreInboundHeader table-----------------------------*/
            PreInboundHeaderEntityV2 createPreInboundHeader = createPreInboundHeaderV2(companyCodeId, plantId, languageId, warehouseId, preInboundNo,
                                                                                       inboundIntegrationHeader, MW_AMS, description, statusId, statusDescription);
            inboundOrderProcess.setPreInboundHeader(createPreInboundHeader);
            /*------------------Insert into Inbound Header----------------------------*/
            InboundHeaderV2 createInboundHeader = createInboundHeaderV2(createPreInboundHeader, overallCreatedPreInboundLineList);
//            log.info("createdInboundHeader : " + createdInboundHeader);
            inboundOrderProcess.setInboundHeader(createInboundHeader);

            StagingHeaderV2 stagingHeader = createStagingHeaderV2(createPreInboundHeader, stagingNo);
//            log.info("StagingHeader Created : " + stagingHeader);
            inboundOrderProcess.setStagingHeader(stagingHeader);

            //Gr Header Creation
            GrHeaderV2 createGrHeader = createGrHeaderV2(stagingHeader, caseCode, grNumber, languageId);
//            log.info("Create GrHeader : " + createGrHeader);
            inboundOrderProcess.setGrHeader(createGrHeader);

//            PreInboundHeaderEntityV2 createdPreInboundHeader = preInboundHeaderV2Repository.save(createPreInboundHeader);
//            log.info("Created PreInboundHeader : " + createdPreInboundHeader);

            // Inserting into InboundLog Table.
//            createInboundIntegrationLogV2(createPreInboundHeader);
            WarehouseApiResponse response = transactionService.postInboundOrder(inboundOrderProcess, transactionAuthToken);

            if(response != null && response.getStatusCode().equalsIgnoreCase("200")) {
                return createInboundHeader;
            }
            return null;

        } catch (Exception e) {
//            createInboundIntegrationLogV2(inboundIntegrationHeader, MW_AMS);
            log.error("Inbound Order Processing Exception ----> ");
            e.printStackTrace();
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
     * @param MW_AMS
     * @param description
     * @param statusDesc
     * @return
     * @throws Exception
     */
    private PreInboundLineEntityV2 createPreInboundLineBOMBasedV2(String companyCodeId, String plantId, String languageId, String warehouseId,
                                                                  String preInboundNo, InboundIntegrationHeader inboundIntegrationHeader,
                                                                  InboundIntegrationLine inboundIntegrationLine, BomLine bomLine, String MW_AMS,
                                                                  KeyValuePair description, Long statusId, String statusDesc) throws Exception {
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
            preInboundLine.setCreatedBy(MW_AMS);
            preInboundLine.setCreatedOn(new Date());
            log.info("preInboundLine [BOM] : " + preInboundLine);
            return preInboundLine;
        } catch (Exception e) {
            log.error("Exception while create preInboundLine - BOM : " + e.toString());
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
     * @param MW_AMS
     * @param description
     * @param statusDesc
     * @return
     * @throws Exception
     */
    private PreInboundLineEntityV2 createPreInboundLineV2(String companyCodeId, String plantId, String languageId, String warehouseId,
                                                          String preInboundNo, InboundIntegrationHeader inboundIntegrationHeader,
                                                          InboundIntegrationLine inboundIntegrationLine, String MW_AMS,
                                                          KeyValuePair description, Long statusId, String statusDesc) throws Exception {
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
            log.info("inboundIntegrationLine.getExpectedDate() : " + inboundIntegrationLine.getExpectedDate());
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

            preInboundLine.setDeletionIndicator(0L);
            preInboundLine.setCreatedBy(MW_AMS);
            preInboundLine.setCreatedOn(new Date());

            log.info("preInboundLine : " + preInboundLine);
            return preInboundLine;
        } catch (Exception e) {
            log.error("PreInboundLine Create Exception: " + e.toString());
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
     * @param MW_AMS
     * @return
     * @throws Exception
     */
    private PreInboundHeaderEntityV2 createPreInboundHeaderV2(String companyCodeId, String plantId, String languageId, String warehouseId,
                                                              String preInboundNo, InboundIntegrationHeader inboundIntegrationHeader,
                                                              String MW_AMS, KeyValuePair description, Long statusId, String statusDesc) throws Exception {
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

            preInboundHeader.setDeletionIndicator(0L);
            preInboundHeader.setCreatedBy(MW_AMS);
            preInboundHeader.setCreatedOn(new Date());

            log.info("createdPreInboundHeader : " + preInboundHeader);
            return preInboundHeader;
        } catch (Exception e) {
            log.error("PreInboundHeader Create Exception : " + e.toString());
            throw e;
        }
    }

    /**
     * @param preInboundHeader
     * @param preInboundLine
     * @return
     */
    private InboundHeaderV2 createInboundHeaderV2(PreInboundHeaderEntityV2 preInboundHeader, List<PreInboundLineEntityV2> preInboundLine) throws Exception {
        try {
            InboundHeaderV2 inboundHeader = new InboundHeaderV2();
            BeanUtils.copyProperties(preInboundHeader, inboundHeader, CommonUtils.getNullPropertyNames(preInboundHeader));
            inboundHeader.setCountOfOrderLines((long) preInboundLine.size());       //count of lines
//            return inboundHeaderV2Repository.save(inboundHeader);
            return inboundHeader;
        } catch (Exception e) {
            log.error("Exception while InboundHeader Create : " + e.toString());
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
//            return stagingHeaderV2Repository.save(stagingHeader);
            return stagingHeader;
        } catch (Exception e) {
            log.error("Exception while StagingHeader Create : " + e.toString());
            throw e;
        }
    }

    /**
     *
     * @param stagingHeader
     * @param caseCode
     * @param grNumber
     * @param languageId
     * @return
     * @throws Exception
     */
    public GrHeaderV2 createGrHeaderV2(StagingHeaderV2 stagingHeader, String caseCode, String grNumber, String languageId) throws Exception {
        try {
            GrHeaderV2 grHeader = new GrHeaderV2();
            BeanUtils.copyProperties(stagingHeader, grHeader, CommonUtils.getNullPropertyNames(stagingHeader));
            grHeader.setCaseCode(caseCode);
            grHeader.setPalletCode(caseCode);
            grHeader.setGoodsReceiptNo(grNumber);
            grHeader.setStatusId(16L);
            grHeader.setStatusDescription(getStatusDescription(16L, languageId));
//            GrHeaderV2 createdGrHeader = grHeaderV2Repository.save(grHeader);
            return grHeader;
        } catch (Exception e) {
            log.error("Exception while GrHeader Create : " + e.toString());
            throw e;
        }
    }
}