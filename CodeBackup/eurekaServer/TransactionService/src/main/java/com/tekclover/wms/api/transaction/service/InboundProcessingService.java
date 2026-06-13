package com.tekclover.wms.api.transaction.service;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.tekclover.wms.api.transaction.controller.exception.BadRequestException;
import com.tekclover.wms.api.transaction.model.IKeyValuePair;
import com.tekclover.wms.api.transaction.model.auth.AuthToken;
import com.tekclover.wms.api.transaction.model.dto.BomHeader;
import com.tekclover.wms.api.transaction.model.dto.BomLine;
import com.tekclover.wms.api.transaction.model.dto.ImBasicData1;
import com.tekclover.wms.api.transaction.model.dto.ImBasicData1V2;
import com.tekclover.wms.api.transaction.model.errorlog.ErrorLog;
import com.tekclover.wms.api.transaction.model.inbound.preinbound.*;
import com.tekclover.wms.api.transaction.model.inbound.preinbound.v2.PreInboundHeaderEntityV2;
import com.tekclover.wms.api.transaction.model.inbound.preinbound.v2.PreInboundLineEntityV2;
import com.tekclover.wms.api.transaction.model.inbound.staging.v2.StagingHeaderV2;
import com.tekclover.wms.api.transaction.model.inbound.staging.v2.StagingLineEntityV2;
import com.tekclover.wms.api.transaction.model.inbound.v2.InboundHeaderV2;
import com.tekclover.wms.api.transaction.model.inbound.v2.InboundLineV2;
import com.tekclover.wms.api.transaction.model.warehouse.inbound.v2.InboundOrderV2;
import com.tekclover.wms.api.transaction.repository.*;
import com.tekclover.wms.api.transaction.util.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.LockAcquisitionException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class InboundProcessingService extends BaseService {
    @Autowired
    private ImBasicData1V2Repository imBasicData1V2Repository;

    private static String WAREHOUSEID_NUMBERRANGE = "110";

    @Autowired
    private AuthTokenService authTokenService;

    @Autowired
    private MastersService mastersService;

    @Autowired
    InboundIntegrationLogRepository inboundIntegrationLogRepository;

    //--------------------------------------------------------------------------------------------------------------
    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private StagingLineService stagingLineService;

    @Autowired
    private StagingHeaderV2Repository stagingHeaderV2Repository;

    @Autowired
    private InboundOrderV2Repository inboundOrderV2Repository;

    @Autowired
    private PreInboundHeaderV2Repository preInboundHeaderV2Repository;

    @Autowired
    private PreInboundLineV2Repository preInboundLineV2Repository;

    @Autowired
    private StagingLineV2Repository stagingLineV2Repository;

    @Autowired
    private InboundHeaderV2Repository inboundHeaderV2Repository;

    @Autowired
    private InboundLineV2Repository inboundLineV2Repository;

    @Autowired
    private ErrorLogRepository exceptionLogRepo;

    //========================================================================V2====================================================================

    /**
     * @param refDocNumber
     * @param inboundIntegrationHeader
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    @Retryable(value = {SQLException.class, SQLServerException.class, CannotAcquireLockException.class, LockAcquisitionException.class, UnexpectedRollbackException.class}, maxAttempts = 3, backoff = @Backoff(delay = 5000))
    public InboundHeaderV2 processInboundReceivedV2(String refDocNumber, InboundIntegrationHeader inboundIntegrationHeader) throws Exception {
        try {
            log.info("Inbound Process Initiated ------> " + refDocNumber + ", " + inboundIntegrationHeader.getInboundOrderTypeId());
            if (inboundIntegrationHeader.getLoginUserId() != null) {
                MW_AMS = inboundIntegrationHeader.getLoginUserId();
            }
            /*
             * Checking whether received refDocNumber processed already.
             */
            Optional<PreInboundHeaderEntityV2> orderProcessedStatus = preInboundHeaderV2Repository.
                    findByRefDocNumberAndInboundOrderTypeIdAndDeletionIndicator(refDocNumber, inboundIntegrationHeader.getInboundOrderTypeId(), 0L);
            if (!orderProcessedStatus.isEmpty()) {
                throw new BadRequestException("Order :" + refDocNumber + " already processed. Reprocessing can't be allowed.");
            }

            String companyCodeId = inboundIntegrationHeader.getCompanyCode();
            String plantId = inboundIntegrationHeader.getBranchCode();
            String languageId = inboundIntegrationHeader.getLanguageId() != null ? inboundIntegrationHeader.getLanguageId() : "EN";
            String warehouseId = inboundIntegrationHeader.getWarehouseID();
            log.info("CompanyCodeId, plantId, languageId, warehouseId : " + companyCodeId + ", " + plantId + ", " + languageId + ", " + warehouseId);

            String idMasterAuthToken = getIDMasterAuthToken();
            String masterAuthToken = getMasterAuthToken();

            // Fetch ITM_CODE inserted in INBOUNDINTEGRATION table and pass the ITM_CODE in IMBASICDATA1 table and
            // validate the ITM_CODE result is Not Null
            InboundOrderV2 inboundOrder = inboundOrderV2Repository.findByRefDocumentNoAndInboundOrderTypeId(refDocNumber, inboundIntegrationHeader.getInboundOrderTypeId());
            log.info("inboundOrder : " + inboundOrder);

            // Getting PreInboundNo, StagingNo, CaseCode from NumberRangeTable
            String preInboundNo = getNextRangeNumber(2L, companyCodeId, plantId, languageId, warehouseId, idMasterAuthToken);
            String stagingNo = getNextRangeNumber(3L, companyCodeId, plantId, languageId, warehouseId, idMasterAuthToken);
            String caseCode = getNextRangeNumber(4L, companyCodeId, plantId, languageId, warehouseId, idMasterAuthToken);
            log.info("PreInboundNo, StagingNo : " + preInboundNo + ", " + stagingNo);

            statusDescription = getStatusDescription(13L, languageId);

            IKeyValuePair description = getDescription(companyCodeId, plantId, languageId, warehouseId);

            List<PreInboundLineEntityV2> overallCreatedPreInboundLineList = new ArrayList<>();
            for (InboundIntegrationLine inboundIntegrationLine : inboundIntegrationHeader.getInboundIntegrationLine()) {
                log.info("inboundIntegrationLine : " + inboundIntegrationLine);

                ImBasicData1V2 imBasicData1 =
                        imBasicData1V2Repository.findTopByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndItemCodeAndDeletionIndicator(
                                languageId,
                                companyCodeId,
                                plantId,
                                warehouseId,
                                inboundIntegrationLine.getItemCode().trim(),
//                            inboundIntegrationLine.getManufacturerName(),
                                0L);
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
                    List<PreInboundLineEntityV2> toBeCreatedPreInboundLineList = new ArrayList<>();
                    for (BomLine dbBomLine : bomLine) {
                        PreInboundLineEntityV2 preInboundLineEntity = createPreInboundLineBOMBasedV2(companyCodeId, plantId, languageId, warehouseId,
                                preInboundNo, inboundIntegrationHeader, inboundIntegrationLine, dbBomLine, MW_AMS);
                        log.info("preInboundLineEntity [BOM] : " + preInboundLineEntity);
                        toBeCreatedPreInboundLineList.add(preInboundLineEntity);
                    }

                    // Batch Insert - PreInboundLines
                    if (!toBeCreatedPreInboundLineList.isEmpty()) {
                        List<PreInboundLineEntityV2> createdPreInboundLine = preInboundLineV2Repository.saveAll(toBeCreatedPreInboundLineList);
                        log.info("createdPreInboundLine [BOM] : " + createdPreInboundLine);
                        overallCreatedPreInboundLineList.addAll(createdPreInboundLine);
                    }
                }
            }

            /*
             * Append PREINBOUNDLINE table through below logic
             */
            List<PreInboundLineEntityV2> toBeCreatedPreInboundLineList = new ArrayList<>();
            for (InboundIntegrationLine inboundIntegrationLine : inboundIntegrationHeader.getInboundIntegrationLine()) {
                toBeCreatedPreInboundLineList.add(createPreInboundLineV2(companyCodeId, plantId, languageId, warehouseId, preInboundNo, inboundIntegrationHeader, inboundIntegrationLine, MW_AMS));
            }

            log.info("toBeCreatedPreInboundLineList [API] : " + toBeCreatedPreInboundLineList);

            // Batch Insert - PreInboundLines
            List<PreInboundLineEntityV2> createdPreInboundLine = new ArrayList<>();
            if (!toBeCreatedPreInboundLineList.isEmpty()) {
                createdPreInboundLine = preInboundLineV2Repository.saveAll(toBeCreatedPreInboundLineList);
                log.info("createdPreInboundLine [API] : " + createdPreInboundLine);
                overallCreatedPreInboundLineList.addAll(createdPreInboundLine);
            }

            List<InboundLineV2> inboundLines = toBeCreatedPreInboundLineList.stream().map(preInboundLine -> {
                InboundLineV2 inboundLine = new InboundLineV2();
                BeanUtils.copyProperties(preInboundLine, inboundLine, CommonUtils.getNullPropertyNames(preInboundLine));
                return  inboundLine;
            }).collect(Collectors.toList());

            List<StagingLineEntityV2> stagingLines = toBeCreatedPreInboundLineList.stream().map(preInboundLine -> {
                StagingLineEntityV2 stagingLine = new StagingLineEntityV2();
                BeanUtils.copyProperties(preInboundLine, stagingLine, CommonUtils.getNullPropertyNames(preInboundLine));
                return  stagingLine;
            }).collect(Collectors.toList());

            //                List<PickupHeaderV2> pickupHeaderList = pickupHeaderTempList.stream().map(pickupHeaderTempV2 -> {
            //                    PickupHeaderV2 pickupHeader = new PickupHeaderV2();
            //                    BeanUtils.copyProperties(pickupHeaderTempV2, pickupHeader, CommonUtils.getNullPropertyNames(pickupHeaderTempV2));
            //                    return pickupHeader;
            //                }).collect(Collectors.toList());
            //                pickupHeaderV2Repository.saveAll(pickupHeaderList);

            /*------------------Insert into PreInboundHeader table-----------------------------*/
            PreInboundHeaderEntityV2 createdPreInboundHeader = createPreInboundHeaderV2(companyCodeId, plantId, languageId, warehouseId, preInboundNo, inboundIntegrationHeader, MW_AMS);
            log.info("preInboundHeader Created : " + createdPreInboundHeader);

            /*------------------Insert into Inbound Header And Line----------------------------*/
            InboundHeaderV2 createdInboundHeader = createInboundHeaderAndLineV2(createdPreInboundHeader, overallCreatedPreInboundLineList);

            // Inserting into InboundLog Table.
            InboundIntegrationLog createdInboundIntegrationLog = createInboundIntegrationLogV2(createdPreInboundHeader);

            // process ASN
            StagingHeaderV2 stagingHeader = processNewASNV2(createdPreInboundHeader, createdInboundHeader.getCreatedBy());
            log.info("StagingHeader Created : " + stagingHeader);

            List<StagingLineEntityV2> stagingLines =
                    stagingLineService.createStagingLineV2(createdPreInboundLine, stagingHeader.getStagingNo(), stagingHeader.getWarehouseId(),
                                                           stagingHeader.getCompanyCode(), stagingHeader.getPlantId(), stagingHeader.getLanguageId(),
                                                           createdInboundHeader.getCreatedBy());
            log.info("StagingLines Created : " + stagingLines);

            return createdInboundHeader;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Inbound Order Processing Bad Request Exception : " + e);
        }
    }

    /**
     *
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param preInboundNo
     * @param inboundIntegrationHeader
     * @param bomLine
     * @param inboundIntegrationLine
     * @return
     * @throws Exception
     */
    private PreInboundLineEntityV2 createPreInboundLineBOMBasedV2(String companyCodeId, String plantId, String languageId, String warehouseId,
                                                                  String preInboundNo, InboundIntegrationHeader inboundIntegrationHeader,
                                                                  InboundIntegrationLine inboundIntegrationLine, BomLine bomLine, String MW_AMS) throws Exception {
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

        // ITEM_TEXT - Pass CHL_ITM_CODE as ITM_CODE in IMBASICDATA1 table and fetch ITEM_TEXT and insert
        if (inboundIntegrationLine.getItemText() == null) {
            ImBasicData1 imBasicData1 =
                    imBasicData1V2Repository.findTopByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndItemCodeAndDeletionIndicator(
                            languageId, companyCodeId, plantId, warehouseId,
                            inboundIntegrationLine.getItemCode(),
//                            inboundIntegrationLine.getManufacturerName(),
                            0L);
            preInboundLine.setItemDescription(imBasicData1.getDescription());
            preInboundLine.setManufacturerPartNo(imBasicData1.getManufacturerPartNo());
        } else {
            preInboundLine.setItemDescription(inboundIntegrationLine.getItemText());
        }

        // MFR

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
        preInboundLine.setStatusId(6L);

        statusDescription = getStatusDescription(6L, languageId);
        preInboundLine.setStatusDescription(statusDescription);

        IKeyValuePair description = getDescription(companyCodeId, plantId, languageId, warehouseId);

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
        return preInboundLine;
    }

    /**
     *
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param preInboundNo
     * @param inboundIntegrationHeader
     * @param inboundIntegrationLine
     * @return
     * @throws Exception
     */
    private PreInboundLineEntityV2 createPreInboundLineV2(String companyCodeId, String plantId, String languageId, String warehouseId,
                                                          String preInboundNo, InboundIntegrationHeader inboundIntegrationHeader,
                                                          InboundIntegrationLine inboundIntegrationLine, String MW_AMS) throws Exception {
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
            if (inboundIntegrationLine.getItemText() == null) {
                ImBasicData1 imBasicData1 =
                        imBasicData1V2Repository.findTopByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndItemCodeAndDeletionIndicator(
                                languageId,
                                companyCodeId,
                                plantId,
                                warehouseId,
                                inboundIntegrationLine.getItemCode(),
    //                            inboundIntegrationLine.getManufacturerName(),
                                0L);
                if(imBasicData1 != null) {
                    preInboundLine.setItemDescription(imBasicData1.getDescription());
                }
            } else {
                preInboundLine.setItemDescription(inboundIntegrationLine.getItemText());
            }

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
            // STATUS_ID
//        preInboundLine.setStatusId(6L);
            preInboundLine.setStatusId(5L);
            statusDescription = getStatusDescription(5L, languageId);
            preInboundLine.setStatusDescription(statusDescription);

            IKeyValuePair description = getDescription(companyCodeId, plantId, languageId, warehouseId);

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
     *
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
                                                              String preInboundNo, InboundIntegrationHeader inboundIntegrationHeader,String MW_AMS) throws Exception {
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
            preInboundHeader.setStatusId(5L);
            statusDescription = getStatusDescription(5L, languageId);
            preInboundHeader.setStatusDescription(statusDescription);

            IKeyValuePair description = getDescription(companyCodeId, plantId, languageId, warehouseId);

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
            PreInboundHeaderEntityV2 createdPreInboundHeader = preInboundHeaderV2Repository.save(preInboundHeader);
            log.info("createdPreInboundHeader : " + createdPreInboundHeader);
            return createdPreInboundHeader;
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
    private InboundHeaderV2 createInboundHeaderAndLineV2(PreInboundHeaderEntityV2 preInboundHeader, List<PreInboundLineEntityV2> preInboundLine) {
        InboundHeaderV2 inboundHeader = new InboundHeaderV2();
        BeanUtils.copyProperties(preInboundHeader, inboundHeader, CommonUtils.getNullPropertyNames(preInboundHeader));

        // Status ID - statusId changed to reduce one less step process and avoid deadlock while updating status
//        inboundHeader.setStatusId(6L);
        inboundHeader.setStatusId(5L);
        statusDescription = stagingLineV2Repository.getStatusDescription(5L, preInboundHeader.getLanguageId());
        inboundHeader.setStatusDescription(statusDescription);

        IKeyValuePair description = stagingLineV2Repository.getDescription(preInboundHeader.getCompanyCode(),
                                                                           preInboundHeader.getLanguageId(),
                                                                           preInboundHeader.getPlantId(),
                                                                           preInboundHeader.getWarehouseId());

        inboundHeader.setCompanyDescription(description.getCompanyDesc());
        inboundHeader.setPlantDescription(description.getPlantDesc());
        inboundHeader.setWarehouseDescription(description.getWarehouseDesc());

        inboundHeader.setMiddlewareId(preInboundHeader.getMiddlewareId());
        inboundHeader.setMiddlewareTable(preInboundHeader.getMiddlewareTable());
        inboundHeader.setReferenceDocumentType(preInboundHeader.getReferenceDocumentType());
        inboundHeader.setManufacturerFullName(preInboundHeader.getManufacturerFullName());
        inboundHeader.setContainerNo(preInboundHeader.getContainerNo());

        inboundHeader.setTransferOrderDate(preInboundHeader.getTransferOrderDate());
        inboundHeader.setSourceBranchCode(preInboundHeader.getSourceBranchCode());
        inboundHeader.setSourceCompanyCode(preInboundHeader.getSourceCompanyCode());
        inboundHeader.setIsCompleted(preInboundHeader.getIsCompleted());
        inboundHeader.setIsCancelled(preInboundHeader.getIsCancelled());
        inboundHeader.setMUpdatedOn(preInboundHeader.getMUpdatedOn());
        inboundHeader.setCountOfOrderLines((long) preInboundLine.size());       //count of lines

        inboundHeader.setDeletionIndicator(0L);
        inboundHeader.setCreatedBy(preInboundHeader.getCreatedBy());
        inboundHeader.setCreatedOn(preInboundHeader.getCreatedOn());
        InboundHeaderV2 createdInboundHeader = inboundHeaderV2Repository.save(inboundHeader);
        log.info("createdInboundHeader : " + createdInboundHeader);

        /*
         * Inbound Line Table Insert
         */
        List<InboundLineV2> toBeCreatedInboundLineList = new ArrayList<>();
        for (PreInboundLineEntityV2 createdPreInboundLine : preInboundLine) {
            InboundLineV2 inboundLine = new InboundLineV2();
            BeanUtils.copyProperties(createdPreInboundLine, inboundLine, CommonUtils.getNullPropertyNames(createdPreInboundLine));

            inboundLine.setOrderQty(createdPreInboundLine.getOrderQty());
            inboundLine.setOrderUom(createdPreInboundLine.getOrderUom());
            inboundLine.setDescription(createdPreInboundLine.getItemDescription());
            inboundLine.setBusinessPartnerCode(createdPreInboundLine.getBusinessPartnerCode());
            inboundLine.setReferenceField4(createdPreInboundLine.getReferenceField4());

            inboundLine.setCompanyDescription(description.getCompanyDesc());
            inboundLine.setPlantDescription(description.getPlantDesc());
            inboundLine.setWarehouseDescription(description.getWarehouseDesc());
            inboundLine.setStatusDescription(statusDescription);
            inboundLine.setContainerNo(createdPreInboundLine.getContainerNo());
            inboundLine.setSupplierName(createdPreInboundLine.getSupplierName());

            inboundLine.setMiddlewareId(createdPreInboundLine.getMiddlewareId());
            inboundLine.setMiddlewareHeaderId(createdPreInboundLine.getMiddlewareHeaderId());
            inboundLine.setMiddlewareTable(createdPreInboundLine.getMiddlewareTable());
            inboundLine.setReferenceDocumentType(createdInboundHeader.getReferenceDocumentType());
            inboundLine.setManufacturerFullName(createdPreInboundLine.getManufacturerFullName());
            inboundLine.setPurchaseOrderNumber(createdPreInboundLine.getPurchaseOrderNumber());

            inboundLine.setManufacturerCode(createdPreInboundLine.getManufacturerName());
            inboundLine.setManufacturerName(createdPreInboundLine.getManufacturerName());
            inboundLine.setExpectedArrivalDate(createdPreInboundLine.getExpectedArrivalDate());
            inboundLine.setOrderQty(createdPreInboundLine.getOrderQty());
            inboundLine.setOrderUom(createdPreInboundLine.getOrderUom());

            inboundLine.setBusinessPartnerCode(createdPreInboundLine.getBusinessPartnerCode());
            inboundLine.setManufacturerPartNo(createdPreInboundLine.getManufacturerPartNo());

            inboundLine.setBranchCode(createdPreInboundLine.getBranchCode());
            inboundLine.setTransferOrderNo(createdPreInboundLine.getTransferOrderNo());
            inboundLine.setIsCompleted(createdPreInboundLine.getIsCompleted());

            inboundLine.setSourceCompanyCode(preInboundHeader.getSourceCompanyCode());
            inboundLine.setSourceBranchCode(preInboundHeader.getSourceBranchCode());

            inboundLine.setDeletionIndicator(0L);
            inboundLine.setCreatedBy(preInboundHeader.getCreatedBy());
            inboundLine.setCreatedOn(preInboundHeader.getCreatedOn());
            toBeCreatedInboundLineList.add(inboundLine);
        }

        List<InboundLineV2> createdInboundLine = inboundLineV2Repository.saveAll(toBeCreatedInboundLineList);
        log.info("createdInboundLine : " + createdInboundLine);

        return createdInboundHeader;
    }

    /**
     * To avoid Deadlock
     * @param preInboundHeader
     * @param loginUserID
     * @return
     */
    public StagingHeaderV2 processNewASNV2(PreInboundHeaderEntityV2 preInboundHeader, String loginUserID) {

        StagingHeaderV2 stagingHeader = new StagingHeaderV2();
        BeanUtils.copyProperties(preInboundHeader, stagingHeader, CommonUtils.getNullPropertyNames(preInboundHeader));

        // STG_NO
        AuthToken authTokenForIDMasterService = authTokenService.getIDMasterServiceAuthToken();

        long NUMBER_RANGE_CODE = 3;
        WAREHOUSEID_NUMBERRANGE = preInboundHeader.getWarehouseId();
        String nextRangeNumber = getNextRangeNumber(NUMBER_RANGE_CODE,
                                                    preInboundHeader.getCompanyCode(), preInboundHeader.getPlantId(), preInboundHeader.getLanguageId(), WAREHOUSEID_NUMBERRANGE,
                                                    authTokenForIDMasterService.getAccess_token());
        stagingHeader.setStagingNo(nextRangeNumber);

        // GR_MTD
        stagingHeader.setGrMtd("INTEGRATION");

        // STATUS_ID
        stagingHeader.setStatusId(12L);
        statusDescription = stagingLineV2Repository.getStatusDescription(12L, preInboundHeader.getLanguageId());
        stagingHeader.setStatusDescription(statusDescription);
        IKeyValuePair description = stagingLineV2Repository.getDescription(preInboundHeader.getCompanyCode(),
                                                                           preInboundHeader.getLanguageId(),
                                                                           preInboundHeader.getPlantId(),
                                                                           preInboundHeader.getWarehouseId());

        stagingHeader.setCompanyDescription(description.getCompanyDesc());
        stagingHeader.setPlantDescription(description.getPlantDesc());
        stagingHeader.setWarehouseDescription(description.getWarehouseDesc());

        stagingHeader.setContainerNo(preInboundHeader.getContainerNo());
        stagingHeader.setMiddlewareId(preInboundHeader.getMiddlewareId());
        stagingHeader.setMiddlewareTable(preInboundHeader.getMiddlewareTable());
        stagingHeader.setReferenceDocumentType(preInboundHeader.getReferenceDocumentType());
        stagingHeader.setManufacturerFullName(preInboundHeader.getManufacturerFullName());

        stagingHeader.setTransferOrderDate(preInboundHeader.getTransferOrderDate());
        stagingHeader.setSourceBranchCode(preInboundHeader.getSourceBranchCode());
        stagingHeader.setSourceCompanyCode(preInboundHeader.getSourceCompanyCode());
        stagingHeader.setIsCompleted(preInboundHeader.getIsCompleted());
        stagingHeader.setIsCancelled(preInboundHeader.getIsCancelled());
        stagingHeader.setMUpdatedOn(preInboundHeader.getMUpdatedOn());

        stagingHeader.setCreatedBy(preInboundHeader.getCreatedBy());
        stagingHeader.setCreatedOn(preInboundHeader.getCreatedOn());
        return stagingHeaderV2Repository.save(stagingHeader);
    }

    /**
     *
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
            dbInboundIntegrationLog = inboundIntegrationLogRepository.save(dbInboundIntegrationLog);
            log.info("dbInboundIntegrationLog : " + dbInboundIntegrationLog);
            return dbInboundIntegrationLog;
        } catch (Exception e) {
            log.error("InboundIntegrationLog Create[Success] Exception : " + e.toString());
            throw e;
        }
    }

    /**
     *
     * @param inbound
     * @param MW_AMS
     * @return
     * @throws Exception
     */
    public InboundIntegrationLog createInboundIntegrationLogV2(InboundIntegrationHeader inbound, String MW_AMS) throws Exception {

        try {
            InboundIntegrationLog dbInboundIntegrationLog = new InboundIntegrationLog();
            BeanUtils.copyProperties(inbound, dbInboundIntegrationLog, CommonUtils.getNullPropertyNames(inbound));
            dbInboundIntegrationLog.setCompanyCodeId(inbound.getCompanyCode());
            dbInboundIntegrationLog.setPlantId(inbound.getBranchCode());
            dbInboundIntegrationLog.setLanguageId(inbound.getLanguageId());
            dbInboundIntegrationLog.setWarehouseId(inbound.getWarehouseID());
            dbInboundIntegrationLog.setIntegrationLogNumber(inbound.getRefDocumentNo());
            dbInboundIntegrationLog.setRefDocNumber(inbound.getRefDocumentNo());
            dbInboundIntegrationLog.setOrderReceiptDate(inbound.getOrderProcessedOn());
            dbInboundIntegrationLog.setIntegrationStatus("FAILED");
            dbInboundIntegrationLog.setOrderReceiptDate(inbound.getOrderProcessedOn());
            dbInboundIntegrationLog.setDeletionIndicator(0L);
            dbInboundIntegrationLog.setCreatedBy(MW_AMS);

            dbInboundIntegrationLog.setCreatedOn(new Date());
            dbInboundIntegrationLog = inboundIntegrationLogRepository.save(dbInboundIntegrationLog);
            log.info("dbInboundIntegrationLog : " + dbInboundIntegrationLog);
            return dbInboundIntegrationLog;
        } catch (Exception e) {
            log.error("Inbound Integration Log Create Exception : " + e.toString());
            throw e;
        }
    }

    //=========================================PreInboundHeader_ExceptionLog===========================================
    private void createPreInboundHeaderLog1(String languageId, String companyCode, String plantId, String warehouseId,
                                            String refDocNumber, String preInboundNo, String error) {

        ErrorLog dbErrorLog = new ErrorLog();
        dbErrorLog.setOrderTypeId(refDocNumber);
        dbErrorLog.setOrderDate(new Date());
        dbErrorLog.setLanguageId(languageId);
        dbErrorLog.setCompanyCodeId(companyCode);
        dbErrorLog.setPlantId(plantId);
        dbErrorLog.setWarehouseId(warehouseId);
        dbErrorLog.setRefDocNumber(refDocNumber);
        dbErrorLog.setReferenceField1(preInboundNo);
        dbErrorLog.setErrorMessage(error);
        dbErrorLog.setCreatedBy("MSD_API");
        dbErrorLog.setCreatedOn(new Date());
        exceptionLogRepo.save(dbErrorLog);
    }

}