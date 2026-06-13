package com.tekclover.wms.api.mfg.service;

import com.tekclover.wms.api.mfg.controller.exception.BadRequestException;
import com.tekclover.wms.api.mfg.model.dto.*;
import com.tekclover.wms.api.mfg.model.masterreceipe.MasterReceipe;
import com.tekclover.wms.api.mfg.model.operation.*;
import com.tekclover.wms.api.mfg.model.prodcutionorder.OperationConsumptionImpl;
import com.tekclover.wms.api.mfg.model.prodcutionorder.ProductionOrder;
import com.tekclover.wms.api.mfg.repository.OperationConsumptionRepository;
import com.tekclover.wms.api.mfg.repository.specification.OperationConsumptionSpecification;
import com.tekclover.wms.api.mfg.util.CommonUtils;
import com.tekclover.wms.api.mfg.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Stream;

@Slf4j
@Service
public class OperationConsumptionService extends BaseService {

    @Autowired
    OperationConsumptionRepository operationConsumptionRepository;

    @Autowired
    MasterReceipeService masterReceipeService;

    @Autowired
    BomLineService bomLineService;

    @Autowired
    TransactionService transactionService;
    //--------------------------------------------------------------------------

    /**
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param productionOrderNo
     * @param productionOrderLineNo
     * @param itemCode
     * @return
     */
    public OperationConsumption getOperationConsumption(String companyCodeId, String plantId, String languageId, String warehouseId,
                                                        String productionOrderNo, Long productionOrderLineNo, String itemCode) {
        Optional<OperationConsumption> operationConsumption =
                operationConsumptionRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndProductionOrderLineNoAndItemCodeAndDeletionIndicator(
                        languageId,
                        companyCodeId,
                        plantId,
                        warehouseId,
                        productionOrderNo,
                        productionOrderLineNo,
                        itemCode,
                        0L);
        if (operationConsumption.isEmpty()) {
            throw new BadRequestException("The given values: warehouseId:" + warehouseId +
                    ",companyCodeId: " + companyCodeId +
                    ",languageId: " + languageId +
                    ",plantId: " + plantId +
                    ",productionOrderNo: " + productionOrderNo +
                    ",productionOrderLineNo: " + productionOrderLineNo +
                    ",itemCode: " + itemCode +
                    " doesn't exist.");
        }
        return operationConsumption.get();
    }

    /**
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param productionOrderNo
     * @return
     */
    public List<OperationConsumption> getOperationConsumptions(String companyCodeId, String plantId, String languageId, String warehouseId, String productionOrderNo) {
        List<OperationConsumption> operationConsumption =
                operationConsumptionRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndDeletionIndicator(
                        languageId,
                        companyCodeId,
                        plantId,
                        warehouseId,
                        productionOrderNo,
                        0L);
        if (operationConsumption == null || operationConsumption.isEmpty()) {
            throw new BadRequestException("The given values: warehouseId:" + warehouseId +
                    ",companyCodeId: " + companyCodeId +
                    ",languageId: " + languageId +
                    ",plantId: " + plantId +
                    ",productionOrderNo: " + productionOrderNo +
                    " doesn't exist.");
        }
        return operationConsumption;
    }

    /**
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param productionOrderNo
     * @param batchNumber
     * @return
     */
    public List<OperationConsumption> getOperationConsumptions(String companyCodeId, String plantId, String languageId, String warehouseId, String productionOrderNo, String batchNumber) {
        List<OperationConsumption> operationConsumption =
                operationConsumptionRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndBatchNumberAndDeletionIndicator(
                        languageId,
                        companyCodeId,
                        plantId,
                        warehouseId,
                        productionOrderNo,
                        batchNumber,
                        0L);
        if (operationConsumption == null || operationConsumption.isEmpty()) {
            throw new BadRequestException("The given values: warehouseId:" + warehouseId +
                    ",companyCodeId: " + companyCodeId +
                    ",languageId: " + languageId +
                    ",plantId: " + plantId +
                    ",productionOrderNo: " + productionOrderNo +
                    ",batchNumber: " + batchNumber +
                    " doesn't exist.");
        }
        return operationConsumption;
    }

    /**
     * @param searchOperationConsumption
     * @return
     * @throws Exception
     */
    public Stream<OperationConsumption> findOperationConsumption(SearchOperationConsumption searchOperationConsumption) throws Exception {
        if (searchOperationConsumption.getStartCreatedOn() != null && searchOperationConsumption.getEndCreatedOn() != null) {
            Date[] dates = DateUtils.addTimeToDatesForSearch(searchOperationConsumption.getStartCreatedOn(), searchOperationConsumption.getEndCreatedOn());
            searchOperationConsumption.setStartCreatedOn(dates[0]);
            searchOperationConsumption.setEndCreatedOn(dates[1]);
        }
        if (searchOperationConsumption.getStartConfirmedOn() != null && searchOperationConsumption.getEndConfirmedOn() != null) {
            Date[] dates = DateUtils.addTimeToDatesForSearch(searchOperationConsumption.getStartConfirmedOn(), searchOperationConsumption.getEndConfirmedOn());
            searchOperationConsumption.setStartConfirmedOn(dates[0]);
            searchOperationConsumption.setEndConfirmedOn(dates[1]);
        }
        log.info("searchOperationConsumption Input: {}", searchOperationConsumption);
        OperationConsumptionSpecification spec = new OperationConsumptionSpecification(searchOperationConsumption);
        Stream<OperationConsumption> results = operationConsumptionRepository.stream(spec, OperationConsumption.class);
        return results;
    }

    /**
     * Create OperationConsumption
     *
     * @param newOperationConsumption
     * @param loginUserID
     * @return
     */
    public OperationConsumption createOperationConsumption(OperationConsumption newOperationConsumption, String loginUserID) {
        try {
            Optional<OperationConsumption> operationConsumption =
                    operationConsumptionRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndProductionOrderLineNoAndItemCodeAndOperationNumberAndPhaseNumberAndBomItemAndBatchNumberAndDeletionIndicator(
                            newOperationConsumption.getLanguageId(),
                            newOperationConsumption.getCompanyCodeId(),
                            newOperationConsumption.getPlantId(),
                            newOperationConsumption.getWarehouseId(),
                            newOperationConsumption.getProductionOrderNo(),
                            newOperationConsumption.getProductionOrderLineNo(),
                            newOperationConsumption.getItemCode(),
                            newOperationConsumption.getOperationNumber(),
                            newOperationConsumption.getPhaseNumber(),
                            newOperationConsumption.getBomItem(),
                            newOperationConsumption.getBatchNumber(),
                            0L);
            if (operationConsumption.isPresent()) {
                throw new BadRequestException("Record is getting duplicated with the given values");
            }
            log.info("create OperationConsumption Initiated: {}", newOperationConsumption);
            OperationConsumption dbOperationConsumption = new OperationConsumption();
            BeanUtils.copyProperties(newOperationConsumption, dbOperationConsumption, CommonUtils.getNullPropertyNames(newOperationConsumption));
            if (dbOperationConsumption.getCompanyCodeId() != null && dbOperationConsumption.getPlantId() != null &&
                    dbOperationConsumption.getLanguageId() != null && dbOperationConsumption.getWarehouseId() != null) {
                description = getDescription(dbOperationConsumption.getCompanyCodeId(), dbOperationConsumption.getPlantId(),
                        dbOperationConsumption.getLanguageId(), dbOperationConsumption.getWarehouseId());
                if (description != null) {
                    dbOperationConsumption.setCompanyDescription(description.getCompanyDescription());
                    dbOperationConsumption.setPlantDescription(description.getPlantDescription());
                    dbOperationConsumption.setWarehouseDescription(description.getWarehouseDescription());
                }
            }
            if (dbOperationConsumption.getStatusId() != null && dbOperationConsumption.getLanguageId() != null) {
                statusDescription = getStatusDescription(dbOperationConsumption.getStatusId(), dbOperationConsumption.getLanguageId());
                if (statusDescription != null) {
                    dbOperationConsumption.setStatusDescription(statusDescription);
                }
            }
            dbOperationConsumption.setDeletionIndicator(0L);
            dbOperationConsumption.setCreatedBy(loginUserID);
            dbOperationConsumption.setCreatedOn(new Date());
            return operationConsumptionRepository.save(dbOperationConsumption);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * Create Batch OperationConsumption
     *
     * @param newOperationConsumptions
     * @param loginUserID
     * @return
     */
    public List<OperationConsumption> createBatchOperationConsumption(List<OperationConsumption> newOperationConsumptions, String loginUserID) {
        try {
            List<OperationConsumption> createdOperationConsumptions = new ArrayList<>();
            if (newOperationConsumptions != null && !newOperationConsumptions.isEmpty()) {
                for (OperationConsumption newOperationConsumption : newOperationConsumptions) {
                    if (newOperationConsumption.getProductionOrderNo() == null || newOperationConsumption.getProductionOrderLineNo() == null) {
                        throw new BadRequestException("ProductionOrderNo & Line No is must to create OperationConsumption");
                    }
                    Optional<OperationConsumption> operationConsumption =
                            operationConsumptionRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndProductionOrderLineNoAndItemCodeAndOperationNumberAndPhaseNumberAndBomItemAndBatchNumberAndDeletionIndicator(
                                    newOperationConsumption.getLanguageId(),
                                    newOperationConsumption.getCompanyCodeId(),
                                    newOperationConsumption.getPlantId(),
                                    newOperationConsumption.getWarehouseId(),
                                    newOperationConsumption.getProductionOrderNo(),
                                    newOperationConsumption.getProductionOrderLineNo(),
                                    newOperationConsumption.getItemCode(),
                                    newOperationConsumption.getOperationNumber(),
                                    newOperationConsumption.getPhaseNumber(),
                                    newOperationConsumption.getBomItem(),
                                    newOperationConsumption.getBatchNumber(),
                                    0L);
                    if (operationConsumption.isPresent()) {
                        throw new BadRequestException("Record is getting duplicated with the given values");
                    }
                    log.info("create OperationConsumption Initiated: {}", newOperationConsumption);
                    OperationConsumption dbOperationConsumption = new OperationConsumption();
                    BeanUtils.copyProperties(newOperationConsumption, dbOperationConsumption, CommonUtils.getNullPropertyNames(newOperationConsumption));
                    if (dbOperationConsumption.getCompanyCodeId() != null && dbOperationConsumption.getPlantId() != null &&
                            dbOperationConsumption.getLanguageId() != null && dbOperationConsumption.getWarehouseId() != null) {
                        description = getDescription(dbOperationConsumption.getCompanyCodeId(), dbOperationConsumption.getPlantId(),
                                dbOperationConsumption.getLanguageId(), dbOperationConsumption.getWarehouseId());
                        if (description != null) {
                            dbOperationConsumption.setCompanyDescription(description.getCompanyDescription());
                            dbOperationConsumption.setPlantDescription(description.getPlantDescription());
                            dbOperationConsumption.setWarehouseDescription(description.getWarehouseDescription());
                        }
                    }
                    if (dbOperationConsumption.getStatusId() != null && dbOperationConsumption.getLanguageId() != null) {
                        statusDescription = getStatusDescription(dbOperationConsumption.getStatusId(), dbOperationConsumption.getLanguageId());
                        if (statusDescription != null) {
                            dbOperationConsumption.setStatusDescription(statusDescription);
                        }
                    }
                    dbOperationConsumption.setDeletionIndicator(0L);
                    dbOperationConsumption.setCreatedBy(loginUserID);
                    dbOperationConsumption.setCreatedOn(new Date());
                    operationConsumptionRepository.save(dbOperationConsumption);
                    createdOperationConsumptions.add(dbOperationConsumption);
                }
            }
            return createdOperationConsumptions;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param productionOrderNo
     * @param loginUserID
     * @param modifyOperationConsumptions
     * @return
     */
    public List<OperationConsumption> updateOperationConsumption(String companyCodeId, String plantId, String languageId, String warehouseId,
                                                                 String productionOrderNo, String loginUserID, List<OperationConsumption> modifyOperationConsumptions) {
        try {
            List<OperationConsumption> updatedOperationConsumptions = new ArrayList<>();

//            ASNHeaderV2 asnHeader = createInboundHeader(modifyOperationConsumptions.get(0));
            for (OperationConsumption modifyOperationConsumption : modifyOperationConsumptions) {
                Optional<OperationConsumption> operationConsumption =
                        operationConsumptionRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndProductionOrderLineNoAndItemCodeAndOperationNumberAndPhaseNumberAndBomItemAndBatchNumberAndDeletionIndicator(
                                languageId, companyCodeId, plantId, warehouseId, productionOrderNo,
                                modifyOperationConsumption.getProductionOrderLineNo(),
                                modifyOperationConsumption.getItemCode(),
                                modifyOperationConsumption.getOperationNumber(),
                                modifyOperationConsumption.getPhaseNumber(),
                                modifyOperationConsumption.getBomItem(),
                                modifyOperationConsumption.getBatchNumber(),
                                0L);
                if (operationConsumption.isEmpty()) {
                    throw new BadRequestException("The given values: warehouseId:" + warehouseId +
                            ",companyCodeId: " + companyCodeId +
                            ",languageId: " + languageId +
                            ",plantId: " + plantId +
                            ",productionOrderNo: " + productionOrderNo +
                            ",batchNumber: " + modifyOperationConsumption.getBatchNumber() +
                            " doesn't exist.");
                }
                OperationConsumption dbOperationConsumption = operationConsumption.get();
                log.info("Update OperationConsumption Initiated : {}", dbOperationConsumption);
                BeanUtils.copyProperties(modifyOperationConsumption, dbOperationConsumption, CommonUtils.getNullPropertyNames(modifyOperationConsumption));
                if (dbOperationConsumption.getStatusId() != null && dbOperationConsumption.getLanguageId() != null) {
                    statusDescription = getStatusDescription(dbOperationConsumption.getStatusId(), dbOperationConsumption.getLanguageId());
                    if (statusDescription != null) {
                        dbOperationConsumption.setStatusDescription(statusDescription);
                    }
                }
                dbOperationConsumption.setUpdatedBy(loginUserID);
                dbOperationConsumption.setUpdatedOn(new Date());
                if (modifyOperationConsumption.isUiProcessConfirm()) {
                    dbOperationConsumption.setBeProcessConfirm(true);
                }
                operationConsumptionRepository.save(dbOperationConsumption);
                updatedOperationConsumptions.add(dbOperationConsumption);
//                ASNLineV2 asnLine = createInboundLine(dbOperationConsumption);
//                asnLineList.add(asnLine);
            }
//            List<OperationConsumptionImpl> operationConsumptionList = operationConsumptionRepository.findOperationConsumptionIB(productionOrderNo);
//            List<ASNLineV2> asnLineList = createInboundLines(operationConsumptionList);
//            ASNV2 asn = new ASNV2();
//            asn.setAsnHeader(asnHeader);
//            asn.setAsnLine(asnLineList);
//            log.info("ASN : " + asn);
//            transactionService.postASNV2(asn);
            return updatedOperationConsumptions;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param productionOrderNo
     * @param batchNumber
     * @param parentProductionOrderNo
     * @param orderType
     */
    public void updateOperationConsumption(String companyCodeId, String plantId, String languageId,
                                           String warehouseId, String productionOrderNo, String batchNumber,
                                           String parentProductionOrderNo, String orderType) {
        try {
            List<OperationConsumption> operationConsumptions =
                    operationConsumptionRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndItemTypeAndBatchNumberAndParentProductionOrderNoAndDeletionIndicator(
                            languageId, companyCodeId, plantId, warehouseId, productionOrderNo, SFG_ITEM_TYPE_ID, batchNumber, parentProductionOrderNo, 0L);
            if (operationConsumptions != null && !operationConsumptions.isEmpty()) {
                if (orderType.equalsIgnoreCase("updateIssuedQty")) {
                    operationConsumptions.stream().filter(n -> !Objects.equals(n.getReceipeQuantity(), n.getIssuedQuantity())).forEach(n -> n.setIssuedQuantity(n.getReceipeQuantity()));
                }
                if (orderType.equalsIgnoreCase("updateConsumedQty")) {
                    operationConsumptions.stream().filter(n -> !Objects.equals(n.getReceipeQuantity(), n.getConsumedQuantity())).forEach(n -> n.setConsumedQuantity(n.getReceipeQuantity()));
                }
                operationConsumptionRepository.saveAll(operationConsumptions);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param productionOrderNo
     * @param loginUserID
     */
    public void deleteOperationConsumption(String companyCodeId, String plantId, String languageId, String warehouseId, String productionOrderNo, String loginUserID) {
        try {
            List<OperationConsumption> dbOperationConsumptions = getOperationConsumptions(companyCodeId, plantId, languageId, warehouseId, productionOrderNo);
            log.info("Delete OperationConsumption Initiated : {}", dbOperationConsumptions);
            for (OperationConsumption dbOperationConsumption : dbOperationConsumptions) {
                dbOperationConsumption.setDeletionIndicator(1L);
                dbOperationConsumption.setUpdatedBy(loginUserID);
                dbOperationConsumption.setUpdatedOn(new Date());
                operationConsumptionRepository.save(dbOperationConsumption);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * Hard delete
     *
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param productionOrderNo
     */
    public void deleteOperationConsumptionProductionOrder(String companyCodeId, String plantId, String languageId, String warehouseId, String productionOrderNo) {
        try {
            List<OperationConsumption> dbOperationConsumptions = getOperationConsumptions(companyCodeId, plantId, languageId, warehouseId, productionOrderNo);
            log.info("Delete OperationConsumption Initiated : {}", dbOperationConsumptions);
            operationConsumptionRepository.deleteAll(dbOperationConsumptions);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * @param newOperationConsumption
     * @param loginUserID
     * @return
     */
    public List<ProductionOrder> createProductionOrderOperationConsumption(OperationConsumption newOperationConsumption, OperationHeader operationHeader, String loginUserID) {
        try {
            Optional<OperationConsumption> operationConsumption =
                    operationConsumptionRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndProductionOrderLineNoAndItemCodeAndOperationNumberAndPhaseNumberAndBomItemAndBatchNumberAndDeletionIndicator(
                            newOperationConsumption.getLanguageId(),
                            newOperationConsumption.getCompanyCodeId(),
                            newOperationConsumption.getPlantId(),
                            newOperationConsumption.getWarehouseId(),
                            newOperationConsumption.getProductionOrderNo(),
                            newOperationConsumption.getProductionOrderLineNo(),
                            newOperationConsumption.getItemCode(),
                            newOperationConsumption.getOperationNumber(),
                            newOperationConsumption.getPhaseNumber(),
                            newOperationConsumption.getBomItem(),
                            newOperationConsumption.getBatchNumber(),
                            0L);
            if (operationConsumption.isPresent()) {
                throw new BadRequestException("Record is getting duplicated with the given values");
            }
            List<ProductionOrder> productionOrderList = new ArrayList<>();

            SalesOrderHeaderV2 salesOrderHeader = createSalesOrderHeader(newOperationConsumption);
            SalesOrderV2 salesOrder = new SalesOrderV2();
            salesOrder.setSalesOrderHeader(salesOrderHeader);
            List<MasterReceipe> masterReceipes = masterReceipeService.getMasterReceipeByItemCode(
                    newOperationConsumption.getCompanyCodeId(), newOperationConsumption.getPlantId(), newOperationConsumption.getLanguageId(),
                    newOperationConsumption.getWarehouseId(), newOperationConsumption.getItemCode());
            log.info("opc - Master receipe id, operation No: {}, {}, {}", masterReceipes.size(), newOperationConsumption.getReceipeId(), newOperationConsumption.getOperationNumber());
            if (masterReceipes != null && !masterReceipes.isEmpty()) {
                for (MasterReceipe masterReceipe : masterReceipes) {
                    log.info("MasterReceipe : {}", masterReceipe);
                    BomLine bomLine = bomLineService.getBomLine(
                            newOperationConsumption.getCompanyCodeId(), newOperationConsumption.getPlantId(), newOperationConsumption.getLanguageId(),
                            newOperationConsumption.getWarehouseId(), masterReceipe.getChildItemCode(), Long.valueOf(masterReceipe.getBomNumber()));
                    log.info("BOMLine : {}", bomLine);
                    if (bomLine.getReferenceField8().equalsIgnoreCase(RM_ITEM_TYPE_ID)) {
//                            log.info("create OperationConsumption Initiated: " + newOperationConsumption);
                        OperationConsumption dbOperationConsumption = new OperationConsumption();
                        BeanUtils.copyProperties(newOperationConsumption, dbOperationConsumption, CommonUtils.getNullPropertyNames(newOperationConsumption));
                        if (dbOperationConsumption.getCompanyCodeId() != null && dbOperationConsumption.getPlantId() != null &&
                                dbOperationConsumption.getLanguageId() != null && dbOperationConsumption.getWarehouseId() != null) {
                            description = getDescription(dbOperationConsumption.getCompanyCodeId(), dbOperationConsumption.getPlantId(),
                                    dbOperationConsumption.getLanguageId(), dbOperationConsumption.getWarehouseId());
                            if (description != null) {
                                dbOperationConsumption.setCompanyDescription(description.getCompanyDescription());
                                dbOperationConsumption.setPlantDescription(description.getPlantDescription());
                                dbOperationConsumption.setWarehouseDescription(description.getWarehouseDescription());
                            }
                        }
                        if (dbOperationConsumption.getStatusId() != null && dbOperationConsumption.getLanguageId() != null) {
                            statusDescription = getStatusDescription(dbOperationConsumption.getStatusId(), dbOperationConsumption.getLanguageId());
                            if (statusDescription != null) {
                                dbOperationConsumption.setStatusDescription(statusDescription);
                            }
                        }
                        if (masterReceipe.getRequiredQuantity() == null) {
                            dbOperationConsumption.setReceipeQuantity(null);
                        }
                        if (masterReceipe.getRequiredQuantity() != null && operationHeader.getReceipePercentage() != null && operationHeader.getTotalOrderQuantity() != null) {
                            Double receipeQty = (masterReceipe.getRequiredQuantity() * operationHeader.getReceipePercentage() * operationHeader.getTotalOrderQuantity()) / 100;
                            log.info("receipe Qty before round off : " + receipeQty);
                            dbOperationConsumption.setReceipeQuantity(round(receipeQty));
                            dbOperationConsumption.setIssuedQuantity(round(receipeQty));
                            log.info("receipe Qty after round off : " + dbOperationConsumption.getReceipeQuantity());
                        }
                        dbOperationConsumption.setItemDescription(masterReceipe.getRemarks());
                        dbOperationConsumption.setBomItem(bomLine.getChildItemCode());
                        dbOperationConsumption.setBomQuantity(bomLine.getChildItemQuantity());
                        dbOperationConsumption.setProductionOrderLineNo(bomLine.getSequenceNo());
                        dbOperationConsumption.setReferenceField1(bomLine.getReferenceField6());
//                        dbOperationConsumption.setReceipeQuantity(bomLine.getChildItemQuantity());
                        dbOperationConsumption.setItemType(bomLine.getReferenceField8());           //ItemType
                        dbOperationConsumption.setItemTypeDescription(bomLine.getReferenceField10());
                        dbOperationConsumption.setPhaseNumber(masterReceipe.getPhaseNumber());
                        dbOperationConsumption.setPhaseDescription(masterReceipe.getPhaseDescription());
                        dbOperationConsumption.setOperationDescription(masterReceipe.getOperationDescription());
                        dbOperationConsumption.setUom(masterReceipe.getUom());
                        dbOperationConsumption.setDeletionIndicator(0L);
                        dbOperationConsumption.setCreatedBy(loginUserID);
                        dbOperationConsumption.setCreatedOn(new Date());
                        operationConsumptionRepository.save(dbOperationConsumption);
//                            SalesOrderLineV2 salesOrderLineV2 = createSalesOrderLine(dbOperationConsumption);
//                            salesOrderLineList.add(salesOrderLineV2);
                        log.info("created OperationConsumption RM: {}", dbOperationConsumption);
                    }
                    if (bomLine.getReferenceField8().equalsIgnoreCase(SFG_ITEM_TYPE_ID)) {

                        log.info("create OperationConsumption Initiated: {}", newOperationConsumption);
                        OperationConsumption dbOperationConsumption = new OperationConsumption();
                        ProductionOrder newSFGProductionOrder = new ProductionOrder();
                        OperationLine newOperationLine = new OperationLine();

                        BeanUtils.copyProperties(newOperationConsumption, dbOperationConsumption, CommonUtils.getNullPropertyNames(newOperationConsumption));

                        //for SFG Create
                        BeanUtils.copyProperties(newOperationConsumption, newSFGProductionOrder, CommonUtils.getNullPropertyNames(newOperationConsumption));
                        BeanUtils.copyProperties(newOperationConsumption, newOperationLine, CommonUtils.getNullPropertyNames(newOperationConsumption));

                        Double sfgInventoryQty = operationConsumptionRepository.findSFGInventory(newOperationConsumption.getCompanyCodeId(),
                                newOperationConsumption.getPlantId(), newOperationConsumption.getLanguageId(), newOperationConsumption.getWarehouseId(),
                                bomLine.getChildItemCode(), 1L);
                        log.info("sfgInventoryQty : {}", sfgInventoryQty);
                        if (masterReceipe.getRequiredQuantity() == null) {
                            dbOperationConsumption.setReceipeQuantity(null);
                        }
                        if (masterReceipe.getRequiredQuantity() != null && operationHeader.getReceipePercentage() != null && operationHeader.getTotalOrderQuantity() != null) {
                            Double requiredReceipeQty = (masterReceipe.getRequiredQuantity() * operationHeader.getReceipePercentage() * operationHeader.getTotalOrderQuantity()) / 100;
                            Double receipeQty = 0D;
                            Double sfgOrderQty = 0D;
                            if (sfgInventoryQty == null) {
                                sfgInventoryQty = 0D;
                            }
                            if (sfgInventoryQty < requiredReceipeQty) {
                                receipeQty = sfgInventoryQty;
                                sfgOrderQty = requiredReceipeQty - receipeQty;
                            } else {
                                receipeQty = requiredReceipeQty;
                            }
                            log.info("receipe Qty before round off : " + receipeQty + ", " + requiredReceipeQty);
                            dbOperationConsumption.setReceipeQuantity(round(requiredReceipeQty));
                            dbOperationConsumption.setIssuedQuantity(round(receipeQty));
                            log.info("receipe Qty after round off : " + dbOperationConsumption.getReceipeQuantity() + ", " + dbOperationConsumption.getIssuedQuantity());
                            if (sfgOrderQty > 0) {
                                newOperationLine.setOrderQuantity(round(sfgOrderQty));
                                newSFGProductionOrder.setTotalOrderQuantity(round(sfgOrderQty));
//                                        newSFGProductionOrder.setTotalConfirmedQuantity(sfgOrderQty);
                                newSFGProductionOrder.setOrderStartDate(operationHeader.getOrderStartDate());
                                newSFGProductionOrder.setOrderEndDate(operationHeader.getOrderEndDate());
                                newSFGProductionOrder.setProductionOrderType(P_ORD_TYP_SFG);
                                newSFGProductionOrder.setReceipePercentage(operationHeader.getReceipePercentage());
                                newSFGProductionOrder.setNumberOfBatches(1L);
                                newOperationLine.setItemCode(masterReceipe.getChildItemCode());
                                newOperationLine.setItemDescription(bomLine.getReferenceField6());
                                newOperationLine.setItemType(bomLine.getReferenceField8());
                                newOperationLine.setProductionOrderType(P_ORD_TYP_SFG);
                                newOperationLine.setBatchQuantity(sfgOrderQty);
                                newOperationLine.setReceipePercentage(operationHeader.getReceipePercentage());
                                newSFGProductionOrder.setProductionLine(newOperationLine);
                                if (!productionOrderList.isEmpty()) {
                                    boolean itmPresent = productionOrderList.stream().anyMatch(n -> n != null && n.getProductionLine().getItemCode().equalsIgnoreCase(masterReceipe.getChildItemCode()));
                                    if (itmPresent) {
                                        for (ProductionOrder dbProduction : productionOrderList) {
                                            if (dbProduction.getProductionLine().getItemCode().equalsIgnoreCase(masterReceipe.getChildItemCode())) {
                                                Double sfgOrdQty = dbProduction.getTotalOrderQuantity() + sfgOrderQty;
                                                dbProduction.setTotalOrderQuantity(round(sfgOrdQty));
                                                dbProduction.getProductionLine().setOrderQuantity(round(sfgOrdQty));
                                            }
                                        }
                                    }
                                    if (!itmPresent) {
                                        productionOrderList.add(newSFGProductionOrder);
                                    }
                                } else {
                                    productionOrderList.add(newSFGProductionOrder);
                                }
                            }
                        }
                        if (dbOperationConsumption.getCompanyCodeId() != null && dbOperationConsumption.getPlantId() != null &&
                                dbOperationConsumption.getLanguageId() != null && dbOperationConsumption.getWarehouseId() != null) {
                            description = getDescription(dbOperationConsumption.getCompanyCodeId(), dbOperationConsumption.getPlantId(),
                                    dbOperationConsumption.getLanguageId(), dbOperationConsumption.getWarehouseId());
                            if (description != null) {
                                dbOperationConsumption.setCompanyDescription(description.getCompanyDescription());
                                dbOperationConsumption.setPlantDescription(description.getPlantDescription());
                                dbOperationConsumption.setWarehouseDescription(description.getWarehouseDescription());
                            }
                        }
                        if (dbOperationConsumption.getStatusId() != null && dbOperationConsumption.getLanguageId() != null) {
                            statusDescription = getStatusDescription(dbOperationConsumption.getStatusId(), dbOperationConsumption.getLanguageId());
                            if (statusDescription != null) {
                                dbOperationConsumption.setStatusDescription(statusDescription);
                            }
                        }
//                        dbOperationConsumption.setReceipeQuantity(bomLine.getChildItemQuantity());
                        dbOperationConsumption.setItemDescription(masterReceipe.getRemarks());
                        dbOperationConsumption.setBomItem(bomLine.getChildItemCode());
                        dbOperationConsumption.setBomQuantity(bomLine.getChildItemQuantity());
                        dbOperationConsumption.setProductionOrderLineNo(bomLine.getSequenceNo());
                        dbOperationConsumption.setReferenceField1(bomLine.getReferenceField6());
                        dbOperationConsumption.setItemType(bomLine.getReferenceField8());           //ItemType
                        dbOperationConsumption.setItemTypeDescription(bomLine.getReferenceField10());
                        dbOperationConsumption.setPhaseNumber(masterReceipe.getPhaseNumber());
                        dbOperationConsumption.setPhaseDescription(masterReceipe.getPhaseDescription());
                        dbOperationConsumption.setOperationDescription(masterReceipe.getOperationDescription());
                        dbOperationConsumption.setUom(masterReceipe.getUom());
                        dbOperationConsumption.setDeletionIndicator(0L);
                        dbOperationConsumption.setCreatedBy(loginUserID);
                        dbOperationConsumption.setCreatedOn(new Date());
                        operationConsumptionRepository.save(dbOperationConsumption);
//                            SalesOrderLineV2 salesOrderLineV2 = createSalesOrderLine(dbOperationConsumption);
//                            salesOrderLineList.add(salesOrderLineV2);
                        log.info("created OperationConsumption SFG: {}", dbOperationConsumption);
                    }
                }
            }
            List<OperationConsumptionImpl> operationConsumptionList = operationConsumptionRepository.
                    findOperationConsumption(operationHeader.getProductionOrderNo(), newOperationConsumption.getBatchNumber());
            List<SalesOrderLineV2> salesOrderLineList = createSalesOrderLines(operationConsumptionList);
            salesOrder.setSalesOrderLine(salesOrderLineList);
            log.info("Sales order: {}", salesOrder);
            transactionService.postSalesOrderV2(salesOrder);
            return productionOrderList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * @param newOperationConsumption
     * @param loginUserID
     * @return
     */
    public List<ProductionOrder> createSFGProductionOrderOperationConsumption(OperationHeader operationHeader, OperationConsumption newOperationConsumption, String loginUserID) {
        try {
            Optional<OperationConsumption> operationConsumption =
                    operationConsumptionRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndProductionOrderLineNoAndItemCodeAndOperationNumberAndPhaseNumberAndBomItemAndBatchNumberAndDeletionIndicator(
                            newOperationConsumption.getLanguageId(),
                            newOperationConsumption.getCompanyCodeId(),
                            newOperationConsumption.getPlantId(),
                            newOperationConsumption.getWarehouseId(),
                            newOperationConsumption.getProductionOrderNo(),
                            newOperationConsumption.getProductionOrderLineNo(),
                            newOperationConsumption.getItemCode(),
                            newOperationConsumption.getOperationNumber(),
                            newOperationConsumption.getPhaseNumber(),
                            newOperationConsumption.getBomItem(),
                            newOperationConsumption.getBatchNumber(),
                            0L);
            if (operationConsumption.isPresent()) {
                throw new BadRequestException("Record is getting duplicated with the given values");
            }
            List<ProductionOrder> productionOrderList = new ArrayList<>();

            SalesOrderHeaderV2 salesOrderHeader = createSalesOrderHeader(newOperationConsumption);
            SalesOrderV2 salesOrder = new SalesOrderV2();
            salesOrder.setSalesOrderHeader(salesOrderHeader);
            List<MasterReceipe> masterReceipes = masterReceipeService.getMasterReceipeByItemCode(
                    newOperationConsumption.getCompanyCodeId(), newOperationConsumption.getPlantId(), newOperationConsumption.getLanguageId(),
                    newOperationConsumption.getWarehouseId(), newOperationConsumption.getItemCode());
            log.info("opc - Master receipe Id, Operation No: {}, {}, {}", masterReceipes.size(), newOperationConsumption.getReceipeId(), newOperationConsumption.getOperationNumber());
            if (masterReceipes != null && !masterReceipes.isEmpty()) {
                for (MasterReceipe masterReceipe : masterReceipes) {
                    log.info("MasterReceipe : {}", masterReceipe);
                    BomLine bomLine = bomLineService.getBomLine(
                            newOperationConsumption.getCompanyCodeId(), newOperationConsumption.getPlantId(), newOperationConsumption.getLanguageId(),
                            newOperationConsumption.getWarehouseId(), masterReceipe.getChildItemCode(), Long.valueOf(masterReceipe.getBomNumber()));
                    log.info("bomline : {}", bomLine);

                    if (bomLine.getReferenceField8().equalsIgnoreCase(RM_ITEM_TYPE_ID)) {
//                        log.info("create OperationConsumption Initiated - RM: " + newOperationConsumption);
                        OperationConsumption dbOperationConsumption = new OperationConsumption();
                        BeanUtils.copyProperties(newOperationConsumption, dbOperationConsumption, CommonUtils.getNullPropertyNames(newOperationConsumption));
                        if (dbOperationConsumption.getCompanyCodeId() != null && dbOperationConsumption.getPlantId() != null &&
                                dbOperationConsumption.getLanguageId() != null && dbOperationConsumption.getWarehouseId() != null) {
                            description = getDescription(dbOperationConsumption.getCompanyCodeId(), dbOperationConsumption.getPlantId(),
                                    dbOperationConsumption.getLanguageId(), dbOperationConsumption.getWarehouseId());
                            if (description != null) {
                                dbOperationConsumption.setCompanyDescription(description.getCompanyDescription());
                                dbOperationConsumption.setPlantDescription(description.getPlantDescription());
                                dbOperationConsumption.setWarehouseDescription(description.getWarehouseDescription());
                            }
                        }
                        if (dbOperationConsumption.getStatusId() != null && dbOperationConsumption.getLanguageId() != null) {
                            statusDescription = getStatusDescription(dbOperationConsumption.getStatusId(), dbOperationConsumption.getLanguageId());
                            if (statusDescription != null) {
                                dbOperationConsumption.setStatusDescription(statusDescription);
                            }
                        }
                        if (masterReceipe.getRequiredQuantity() == null) {
                            dbOperationConsumption.setReceipeQuantity(null);
                        }
                        if (masterReceipe.getRequiredQuantity() != null && operationHeader.getReceipePercentage() != null && operationHeader.getTotalOrderQuantity() != null) {
                            Double receipeQty = (masterReceipe.getRequiredQuantity() * operationHeader.getReceipePercentage() * operationHeader.getTotalOrderQuantity()) / 100;
                            log.info("receipe Qty before round off : " + receipeQty);
                            dbOperationConsumption.setReceipeQuantity(round(receipeQty));
                            dbOperationConsumption.setIssuedQuantity(round(receipeQty));
                            log.info("receipe Qty after round off : " + dbOperationConsumption.getReceipeQuantity());
                        }
//                        dbOperationConsumption.setReceipeQuantity(bomLine.getChildItemQuantity());
                        dbOperationConsumption.setItemDescription(masterReceipe.getRemarks());
                        dbOperationConsumption.setBomItem(bomLine.getChildItemCode());
                        dbOperationConsumption.setBomQuantity(bomLine.getChildItemQuantity());
                        dbOperationConsumption.setProductionOrderLineNo(bomLine.getSequenceNo());
                        dbOperationConsumption.setReferenceField1(bomLine.getReferenceField6());
                        dbOperationConsumption.setItemType(bomLine.getReferenceField8());           //ItemType
                        dbOperationConsumption.setItemTypeDescription(bomLine.getReferenceField10());
                        dbOperationConsumption.setPhaseNumber(masterReceipe.getPhaseNumber());
                        dbOperationConsumption.setPhaseDescription(masterReceipe.getPhaseDescription());
                        dbOperationConsumption.setOperationDescription(masterReceipe.getOperationDescription());
                        dbOperationConsumption.setUom(masterReceipe.getUom());
                        dbOperationConsumption.setDeletionIndicator(0L);
                        dbOperationConsumption.setCreatedBy(loginUserID);
                        dbOperationConsumption.setCreatedOn(new Date());
                        operationConsumptionRepository.save(dbOperationConsumption);
//                        SalesOrderLineV2 salesOrderLineV2 = createSalesOrderLine(dbOperationConsumption);
//                        salesOrderLineList.add(salesOrderLineV2);
                        log.info("created OperationConsumption RM: {}", dbOperationConsumption);
                    }
                    if (bomLine.getReferenceField8().equalsIgnoreCase(SFG_ITEM_TYPE_ID)) {

                        log.info("create OperationConsumption Initiated - SFG: {}", newOperationConsumption);
                        OperationConsumption dbOperationConsumption = new OperationConsumption();
                        ProductionOrder newSFGProductionOrder = new ProductionOrder();
                        OperationLine operationLine = new OperationLine();

                        BeanUtils.copyProperties(newOperationConsumption, dbOperationConsumption, CommonUtils.getNullPropertyNames(newOperationConsumption));

                        //for SFG Create
                        BeanUtils.copyProperties(newOperationConsumption, newSFGProductionOrder, CommonUtils.getNullPropertyNames(newOperationConsumption));
                        BeanUtils.copyProperties(newOperationConsumption, operationLine, CommonUtils.getNullPropertyNames(newOperationConsumption));

                        Double sfgInventoryQty = operationConsumptionRepository.findSFGInventory(newOperationConsumption.getCompanyCodeId(),
                                newOperationConsumption.getPlantId(), newOperationConsumption.getLanguageId(), newOperationConsumption.getWarehouseId(),
                                bomLine.getChildItemCode(), 1L);
                        log.info("sfgInventoryQty : {}", sfgInventoryQty);
                        if (masterReceipe.getRequiredQuantity() == null) {
                            dbOperationConsumption.setReceipeQuantity(null);
                        }
                        if (masterReceipe.getRequiredQuantity() != null && operationHeader.getReceipePercentage() != null && operationHeader.getTotalOrderQuantity() != null) {
                            Double requiredReceipeQty = (masterReceipe.getRequiredQuantity() * operationHeader.getReceipePercentage() * operationHeader.getTotalOrderQuantity()) / 100;
                            Double receipeQty = 0D;
                            Double sfgOrderQty = 0D;
                            if (sfgInventoryQty == null) {
                                sfgInventoryQty = 0D;
                            }
                            if (sfgInventoryQty < requiredReceipeQty) {
                                receipeQty = sfgInventoryQty;
                                sfgOrderQty = requiredReceipeQty - receipeQty;
                            } else {
                                receipeQty = requiredReceipeQty;
                            }
                            log.info("receipe Qty before round off : " + receipeQty + ", " + requiredReceipeQty);
                            dbOperationConsumption.setReceipeQuantity(round(requiredReceipeQty));
                            dbOperationConsumption.setIssuedQuantity(round(receipeQty));
                            log.info("receipe Qty after round off : " + dbOperationConsumption.getReceipeQuantity() + ", " + dbOperationConsumption.getIssuedQuantity());
                            if (sfgOrderQty > 0) {
                                operationLine.setOrderQuantity(round(sfgOrderQty));
                                newSFGProductionOrder.setTotalOrderQuantity(round(sfgOrderQty));
                                newSFGProductionOrder.setOrderStartDate(operationHeader.getOrderStartDate());
                                newSFGProductionOrder.setOrderEndDate(operationHeader.getOrderEndDate());
                                newSFGProductionOrder.setReceipePercentage(operationHeader.getReceipePercentage());
                                newSFGProductionOrder.setNumberOfBatches(1L);
                                newSFGProductionOrder.setProductionOrderType(P_ORD_TYP_SFG);
                                operationLine.setItemCode(masterReceipe.getChildItemCode());
                                operationLine.setItemDescription(bomLine.getReferenceField6());
                                operationLine.setItemType(bomLine.getReferenceField8());
                                operationLine.setProductionOrderType(P_ORD_TYP_SFG);
                                operationLine.setBatchQuantity(round(sfgOrderQty));
                                operationLine.setReceipePercentage(operationHeader.getReceipePercentage());
                                newSFGProductionOrder.setProductionLine(operationLine);
                                productionOrderList.add(newSFGProductionOrder);
                            }
                        }
                        if (dbOperationConsumption.getCompanyCodeId() != null && dbOperationConsumption.getPlantId() != null &&
                                dbOperationConsumption.getLanguageId() != null && dbOperationConsumption.getWarehouseId() != null) {
                            description = getDescription(dbOperationConsumption.getCompanyCodeId(), dbOperationConsumption.getPlantId(),
                                    dbOperationConsumption.getLanguageId(), dbOperationConsumption.getWarehouseId());
                            if (description != null) {
                                dbOperationConsumption.setCompanyDescription(description.getCompanyDescription());
                                dbOperationConsumption.setPlantDescription(description.getPlantDescription());
                                dbOperationConsumption.setWarehouseDescription(description.getWarehouseDescription());
                            }
                        }
                        if (dbOperationConsumption.getStatusId() != null && dbOperationConsumption.getLanguageId() != null) {
                            statusDescription = getStatusDescription(dbOperationConsumption.getStatusId(), dbOperationConsumption.getLanguageId());
                            if (statusDescription != null) {
                                dbOperationConsumption.setStatusDescription(statusDescription);
                            }
                        }
//                        dbOperationConsumption.setReceipeQuantity(bomLine.getChildItemQuantity());
                        dbOperationConsumption.setItemDescription(masterReceipe.getRemarks());
                        dbOperationConsumption.setBomItem(bomLine.getChildItemCode());
                        dbOperationConsumption.setBomQuantity(bomLine.getChildItemQuantity());
                        dbOperationConsumption.setProductionOrderLineNo(bomLine.getSequenceNo());
                        dbOperationConsumption.setReferenceField1(bomLine.getReferenceField6());
                        dbOperationConsumption.setItemType(bomLine.getReferenceField8());           //ItemType
                        dbOperationConsumption.setItemTypeDescription(bomLine.getReferenceField10());
                        dbOperationConsumption.setPhaseNumber(masterReceipe.getPhaseNumber());
                        dbOperationConsumption.setPhaseDescription(masterReceipe.getPhaseDescription());
                        dbOperationConsumption.setOperationDescription(masterReceipe.getOperationDescription());
                        dbOperationConsumption.setUom(masterReceipe.getUom());
                        dbOperationConsumption.setDeletionIndicator(0L);
                        dbOperationConsumption.setCreatedBy(loginUserID);
                        dbOperationConsumption.setCreatedOn(new Date());
                        operationConsumptionRepository.save(dbOperationConsumption);
//                        SalesOrderLineV2 salesOrderLineV2 = createSalesOrderLine(dbOperationConsumption);
//                        salesOrderLineList.add(salesOrderLineV2);
                        log.info("created OperationConsumption SFG: {}", dbOperationConsumption);
                    }
                }
            }
            List<OperationConsumptionImpl> operationConsumptionList = operationConsumptionRepository.
                    findOperationConsumption(operationHeader.getProductionOrderNo(), newOperationConsumption.getBatchNumber());
            List<SalesOrderLineV2> salesOrderLineList = createSalesOrderLines(operationConsumptionList);
            salesOrder.setSalesOrderLine(salesOrderLineList);
            log.info("Sales order: {}", salesOrder);
            transactionService.postSalesOrderV2(salesOrder);
            return productionOrderList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * @param operationHeader
     * @return
     */
    private SalesOrderHeaderV2 createSalesOrderHeader(OperationConsumption operationHeader) {
        try {
            SalesOrderHeaderV2 salesOrderHeader = new SalesOrderHeaderV2();
            BeanUtils.copyProperties(operationHeader, salesOrderHeader, CommonUtils.getNullPropertyNames(operationHeader));
            salesOrderHeader.setCompanyCode(operationHeader.getCompanyCodeId());
            salesOrderHeader.setBranchCode(operationHeader.getPlantId());
            salesOrderHeader.setWareHouseId(operationHeader.getWarehouseId());
            salesOrderHeader.setLanguageId(operationHeader.getLanguageId());
            salesOrderHeader.setOrderReceivedOn(new Date());
            salesOrderHeader.setPickListNumber(operationHeader.getBatchNumber());
            salesOrderHeader.setSalesOrderNumber(operationHeader.getProductionOrderNo());
            salesOrderHeader.setTokenNumber(operationHeader.getParentProductionOrderNo());
            salesOrderHeader.setStoreID(operationHeader.getPlantId());
            salesOrderHeader.setStoreName(operationHeader.getPlantDescription());
            if (operationHeader.getProductionOrderType().equalsIgnoreCase(P_ORD_TYP_FG)) {
                salesOrderHeader.setOrderType(OB_IPL_ORD_TYP_ID_FG);
            }
            if (operationHeader.getProductionOrderType().equalsIgnoreCase(P_ORD_TYP_SFG)) {
                salesOrderHeader.setOrderType(OB_IPL_ORD_TYP_ID_SFG);
            }
            salesOrderHeader.setStatus("ACTIVE");
            String date = DateUtils.date2String_YYYYMMDD(new Date());
            log.info("Date : {}", date);
            salesOrderHeader.setRequiredDeliveryDate(date);
            return salesOrderHeader;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * @param operationConsumption
     * @return
     */
    private SalesOrderLineV2 createSalesOrderLine(OperationConsumption operationConsumption) {
        try {
            SalesOrderLineV2 salesOrderLine = new SalesOrderLineV2();
            salesOrderLine.setLineReference(operationConsumption.getProductionOrderLineNo());
            salesOrderLine.setSku(operationConsumption.getBomItem());
            salesOrderLine.setSkuDescription(operationConsumption.getReferenceField1());
            salesOrderLine.setOrderedQty(operationConsumption.getReceipeQuantity());
            if (operationConsumption.getUom() != null) {
                salesOrderLine.setUom(operationConsumption.getUom());
            } else {
                salesOrderLine.setUom("Kg");                //HardCode
            }
            salesOrderLine.setSalesOrderNo(operationConsumption.getProductionOrderNo());
            salesOrderLine.setPickListNo(operationConsumption.getBatchNumber());
            if (operationConsumption.getProductionOrderType().equalsIgnoreCase(P_ORD_TYP_FG)) {
                salesOrderLine.setOrderType(OB_IPL_ORD_TYP_ID_FG);

            }
            if (operationConsumption.getProductionOrderType().equalsIgnoreCase(P_ORD_TYP_SFG)) {
                salesOrderLine.setOrderType(OB_IPL_ORD_TYP_ID_SFG);
            }
            return salesOrderLine;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * @param operationConsumption
     * @return
     */
    private ASNHeaderV2 createInboundHeader(OperationConsumption operationConsumption) {
        try {
            ASNHeaderV2 asnHeader = new ASNHeaderV2();
            asnHeader.setCompanyCode(operationConsumption.getCompanyCodeId());
            asnHeader.setBranchCode(operationConsumption.getPlantId());
            asnHeader.setWarehouseId(operationConsumption.getWarehouseId());
            asnHeader.setLanguageId(operationConsumption.getLanguageId());
            asnHeader.setAsnNumber(operationConsumption.getBatchNumber());
            asnHeader.setPurchaseOrderNumber(operationConsumption.getProductionOrderNo());
            asnHeader.setParentProductionOrderNo(operationConsumption.getParentProductionOrderNo());
            asnHeader.setSupplierCode(operationConsumption.getOperationNumber());
            if (operationConsumption.getProductionOrderType().equalsIgnoreCase(P_ORD_TYP_FG)) {
                asnHeader.setInboundOrderTypeId(IB_ORD_TYP_ID_FG);
            }
            if (operationConsumption.getProductionOrderType().equalsIgnoreCase(P_ORD_TYP_SFG)) {
                asnHeader.setInboundOrderTypeId(IB_ORD_TYP_ID_SFG);
            }
            return asnHeader;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * @param operationConsumption
     * @return
     */
    private ASNLineV2 createInboundLine(OperationConsumption operationConsumption) {
        try {
            ASNLineV2 asnLine = new ASNLineV2();
            asnLine.setCompanyCode(operationConsumption.getCompanyCodeId());
            asnLine.setBranchCode(operationConsumption.getPlantId());
            asnLine.setLineReference(operationConsumption.getProductionOrderLineNo());
            asnLine.setSku(operationConsumption.getItemCode());
            asnLine.setSkuDescription(operationConsumption.getItemDescription());
            asnLine.setExpectedQty(operationConsumption.getReceipeQuantity());
            if (operationConsumption.getUom() != null) {
                asnLine.setUom(operationConsumption.getUom());
            } else {
                asnLine.setUom("Kg");                //HardCode
            }
            asnLine.setSupplierCode(operationConsumption.getOperationNumber());
            asnLine.setPurchaseOrderNumber(operationConsumption.getProductionOrderNo());
            asnLine.setSupplierInvoiceNo(operationConsumption.getParentProductionOrderNo());
            if (operationConsumption.getProductionOrderType().equalsIgnoreCase(P_ORD_TYP_FG)) {
                asnLine.setInboundOrderTypeId(IB_ORD_TYP_ID_FG);
                asnLine.setStorageSectionId(ST_SEC_ID_PFG);
            }
            if (operationConsumption.getProductionOrderType().equalsIgnoreCase(P_ORD_TYP_SFG)) {
                asnLine.setInboundOrderTypeId(IB_ORD_TYP_ID_SFG);
                asnLine.setStorageSectionId(ST_SEC_ID_PSFG);
            }
            asnLine.setReceivedQty(operationConsumption.getIssuedQuantity());
            asnLine.setPackQty(operationConsumption.getBomQuantity());
            String date = DateUtils.date2String_YYYYMMDD(new Date());
            asnLine.setExpectedDate(date);
            return asnLine;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * @param operationConsumptions
     * @return
     */
    private List<ASNLineV2> createInboundLines(List<OperationConsumptionImpl> operationConsumptions) {
        try {
            List<ASNLineV2> asnLineV2List = new ArrayList<>();
            for (OperationConsumptionImpl operationConsumption : operationConsumptions) {
                ASNLineV2 asnLine = new ASNLineV2();
                asnLine.setCompanyCode(operationConsumption.getCompanyCodeId());
                asnLine.setBranchCode(operationConsumption.getPlantId());
                asnLine.setLineReference(operationConsumption.getProductionOrderLineNo());
                asnLine.setSku(operationConsumption.getItemCode());
                asnLine.setSkuDescription(operationConsumption.getItemDescription());
                asnLine.setExpectedQty(operationConsumption.getReceipeQuantity());
                if (operationConsumption.getUom() != null) {
                    asnLine.setUom(operationConsumption.getUom());
                } else {
                    asnLine.setUom("Kg");                //HardCode
                }
                asnLine.setSupplierCode(operationConsumption.getOperationNumber());
                asnLine.setPurchaseOrderNumber(operationConsumption.getProductionOrderNo());
                asnLine.setSupplierInvoiceNo(operationConsumption.getParentProductionOrderNo());
                if (operationConsumption.getProductionOrderType().equalsIgnoreCase(P_ORD_TYP_FG)) {
                    asnLine.setInboundOrderTypeId(IB_ORD_TYP_ID_FG);
                    asnLine.setStorageSectionId(ST_SEC_ID_PFG);
                }
                if (operationConsumption.getProductionOrderType().equalsIgnoreCase(P_ORD_TYP_SFG)) {
                    asnLine.setInboundOrderTypeId(IB_ORD_TYP_ID_SFG);
                    asnLine.setStorageSectionId(ST_SEC_ID_PSFG);
                }
                Double issuedQty = operationConsumption.getIssuedQuantity() != null ? Double.parseDouble(operationConsumption.getIssuedQuantity()) : 0D;
                asnLine.setReceivedQty(issuedQty);
                asnLine.setPackQty(operationConsumption.getBomQuantity());
                String date = DateUtils.date2String_YYYYMMDD(new Date());
                asnLine.setExpectedDate(date);
                asnLineV2List.add(asnLine);
            }
            return asnLineV2List;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * @param operationConsumptions
     * @return
     */
    private List<SalesOrderLineV2> createSalesOrderLines(List<OperationConsumptionImpl> operationConsumptions) {
        try {
            List<SalesOrderLineV2> salesOrderLines = new ArrayList<>();
            for (OperationConsumptionImpl operationConsumption : operationConsumptions) {
                SalesOrderLineV2 salesOrderLine = new SalesOrderLineV2();
                salesOrderLine.setLineReference(operationConsumption.getProductionOrderLineNo());
                salesOrderLine.setSku(operationConsumption.getItemCode());
                salesOrderLine.setSkuDescription(operationConsumption.getItemDescription());
                salesOrderLine.setOrderedQty(operationConsumption.getReceipeQuantity());
                if (operationConsumption.getUom() != null) {
                    salesOrderLine.setUom(operationConsumption.getUom());
                } else {
                    salesOrderLine.setUom("Kg");                //HardCode
                }
                salesOrderLine.setSalesOrderNo(operationConsumption.getProductionOrderNo());
                salesOrderLine.setPickListNo(operationConsumption.getBatchNumber());
                if (operationConsumption.getProductionOrderType().equalsIgnoreCase(P_ORD_TYP_FG)) {
                    salesOrderLine.setOrderType(OB_IPL_ORD_TYP_ID_FG);

                }
                if (operationConsumption.getProductionOrderType().equalsIgnoreCase(P_ORD_TYP_SFG)) {
                    salesOrderLine.setOrderType(OB_IPL_ORD_TYP_ID_SFG);
                }
                salesOrderLines.add(salesOrderLine);
            }
            return salesOrderLines;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }
}