package com.tekclover.wms.api.mfg.service;

import com.tekclover.wms.api.mfg.controller.exception.BadRequestException;
import com.tekclover.wms.api.mfg.model.dto.ASNHeaderV2;
import com.tekclover.wms.api.mfg.model.dto.ASNLineV2;
import com.tekclover.wms.api.mfg.model.dto.ASNV2;
import com.tekclover.wms.api.mfg.model.masterreceipe.MasterReceipe;
import com.tekclover.wms.api.mfg.model.operation.OperationConsumption;
import com.tekclover.wms.api.mfg.model.operation.OperationHeader;
import com.tekclover.wms.api.mfg.model.operation.OperationLine;
import com.tekclover.wms.api.mfg.model.operation.SearchOperationLine;
import com.tekclover.wms.api.mfg.model.prodcutionorder.ProductionOrder;
import com.tekclover.wms.api.mfg.repository.OperationLineRepository;
import com.tekclover.wms.api.mfg.repository.specification.OperationLineSpecification;
import com.tekclover.wms.api.mfg.util.CommonUtils;
import com.tekclover.wms.api.mfg.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@Service
public class OperationLineService extends BaseService {

    @Autowired
    OperationLineRepository operationLineRepository;

    @Autowired
    MasterReceipeService masterReceipeService;

    @Autowired
    MasterOperationService masterOperationService;

    @Autowired
    OperationConsumptionService operationConsumptionService;

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
    public OperationLine getOperationLine(String companyCodeId, String plantId, String languageId, String warehouseId,
                                          String productionOrderNo, Long productionOrderLineNo, String itemCode) {
        Optional<OperationLine> operationLine =
                operationLineRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndProductionOrderLineNoAndItemCodeAndDeletionIndicator(
                        languageId,
                        companyCodeId,
                        plantId,
                        warehouseId,
                        productionOrderNo,
                        productionOrderLineNo,
                        itemCode,
                        0L);
        if (operationLine.isEmpty()) {
            throw new BadRequestException("The given values: warehouseId:" + warehouseId +
                    ",companyCodeId: " + companyCodeId +
                    ",languageId: " + languageId +
                    ",plantId: " + plantId +
                    ",productionOrderNo: " + productionOrderNo +
                    ",productionOrderLineNo: " + productionOrderLineNo +
                    ",itemCode: " + itemCode +
                    " doesn't exist.");
        }
        return operationLine.get();
    }

    /**
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param productionOrderNo
     * @return
     */
    public List<OperationLine> getOperationLines(String companyCodeId, String plantId, String languageId, String warehouseId, String productionOrderNo) {
        List<OperationLine> operationLine =
                operationLineRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndDeletionIndicator(
                        languageId,
                        companyCodeId,
                        plantId,
                        warehouseId,
                        productionOrderNo,
                        0L);
        if (operationLine == null || operationLine.isEmpty()) {
            throw new BadRequestException("The given values: warehouseId:" + warehouseId +
                    ",companyCodeId: " + companyCodeId +
                    ",languageId: " + languageId +
                    ",plantId: " + plantId +
                    ",productionOrderNo: " + productionOrderNo +
                    " doesn't exist.");
        }
        return operationLine;
    }

    /**
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param productionOrderNo
     * @return
     */
    public List<OperationLine> getOperationLinesForFindProductionOrder(String companyCodeId, String plantId, String languageId, String warehouseId, String productionOrderNo) {
        List<OperationLine> operationLine =
                operationLineRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndDeletionIndicator(
                        languageId,
                        companyCodeId,
                        plantId,
                        warehouseId,
                        productionOrderNo,
                        0L);
        return operationLine;
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
    public OperationLine getOperationLinesForProductionOrder(String companyCodeId, String plantId, String languageId, String warehouseId, String productionOrderNo, String batchNumber) {
        OperationLine operationLine =
                operationLineRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndBatchNumberAndDeletionIndicator(
                        languageId,
                        companyCodeId,
                        plantId,
                        warehouseId,
                        productionOrderNo,
                        batchNumber,
                        0L);
        return operationLine;
    }

    /**
     * @param searchOperationLine
     * @return
     * @throws Exception
     */
    public Stream<OperationLine> findOperationLine(SearchOperationLine searchOperationLine) throws Exception {
        if (searchOperationLine.getStartCreatedOn() != null && searchOperationLine.getEndCreatedOn() != null) {
            Date[] dates = DateUtils.addTimeToDatesForSearch(searchOperationLine.getStartCreatedOn(), searchOperationLine.getEndCreatedOn());
            searchOperationLine.setStartCreatedOn(dates[0]);
            searchOperationLine.setEndCreatedOn(dates[1]);
        }
        if (searchOperationLine.getStartConfirmedOn() != null && searchOperationLine.getEndConfirmedOn() != null) {
            Date[] dates = DateUtils.addTimeToDatesForSearch(searchOperationLine.getStartConfirmedOn(), searchOperationLine.getEndConfirmedOn());
            searchOperationLine.setStartConfirmedOn(dates[0]);
            searchOperationLine.setEndConfirmedOn(dates[1]);
        }
        log.info("searchOperationLine Input: {}", searchOperationLine);
        OperationLineSpecification spec = new OperationLineSpecification(searchOperationLine);
        Stream<OperationLine> results = operationLineRepository.stream(spec, OperationLine.class);
        return results;
    }

    /**
     * @param newOperationLines
     * @param loginUserID
     * @return
     */
    public List<OperationLine> createOperationLine(List<OperationLine> newOperationLines, String loginUserID) {
        try {
            List<OperationLine> createdOperationLines = new ArrayList<>();
            if (newOperationLines != null && !newOperationLines.isEmpty()) {
                for (OperationLine newOperationLine : newOperationLines) {
                    if (newOperationLine.getProductionOrderNo() == null || newOperationLine.getProductionOrderLineNo() == null) {
                        throw new BadRequestException("ProductionOrderNo & Line No is must to create OperationLine");
                    }
                    Optional<OperationLine> operationLine =
                            operationLineRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndProductionOrderLineNoAndItemCodeAndDeletionIndicator(
                                    newOperationLine.getLanguageId(),
                                    newOperationLine.getCompanyCodeId(),
                                    newOperationLine.getPlantId(),
                                    newOperationLine.getWarehouseId(),
                                    newOperationLine.getProductionOrderNo(),
                                    newOperationLine.getProductionOrderLineNo(),
                                    newOperationLine.getItemCode(),
                                    0L);
                    if (operationLine.isPresent()) {
                        throw new BadRequestException("Record is getting duplicated with the given values");
                    }
                    log.info("create OperationLine Initiated: {}", newOperationLine);
                    OperationLine dbOperationLine = new OperationLine();
                    BeanUtils.copyProperties(newOperationLine, dbOperationLine, CommonUtils.getNullPropertyNames(newOperationLine));
                    if (dbOperationLine.getCompanyCodeId() != null && dbOperationLine.getPlantId() != null &&
                            dbOperationLine.getLanguageId() != null && dbOperationLine.getWarehouseId() != null) {
                        description = getDescription(dbOperationLine.getCompanyCodeId(), dbOperationLine.getPlantId(),
                                dbOperationLine.getLanguageId(), dbOperationLine.getWarehouseId());
                        if (description != null) {
                            dbOperationLine.setCompanyDescription(description.getCompanyDescription());
                            dbOperationLine.setPlantDescription(description.getPlantDescription());
                            dbOperationLine.setWarehouseDescription(description.getWarehouseDescription());
                        }
                    }
                    if (dbOperationLine.getStatusId() != null && dbOperationLine.getLanguageId() != null) {
                        statusDescription = getStatusDescription(dbOperationLine.getStatusId(), dbOperationLine.getLanguageId());
                        if (statusDescription != null) {
                            dbOperationLine.setStatusDescription(statusDescription);
                        }
                    }
                    dbOperationLine.setDeletionIndicator(0L);
                    dbOperationLine.setCreatedBy(loginUserID);
                    dbOperationLine.setCreatedOn(new Date());
                    operationLineRepository.save(dbOperationLine);
                    createdOperationLines.add(dbOperationLine);
                }
            }
            return createdOperationLines;
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
     * @param modifyOperationLines
     * @return
     */
    public List<OperationLine> updateOperationLine(String companyCodeId, String plantId, String languageId, String warehouseId,
                                                   String productionOrderNo, String loginUserID, List<OperationLine> modifyOperationLines) {
        try {
            List<OperationLine> updatedOperationLines = new ArrayList<>();
            for (OperationLine modifyOperationLine : modifyOperationLines) {
                OperationLine dbOperationLine = getOperationLine(companyCodeId, plantId, languageId, warehouseId, productionOrderNo,
                        modifyOperationLine.getProductionOrderLineNo(), modifyOperationLine.getItemCode());
                log.info("Update OperationLine Initiated : {}", dbOperationLine);
                BeanUtils.copyProperties(modifyOperationLine, dbOperationLine, CommonUtils.getNullPropertyNames(modifyOperationLine));
                if (dbOperationLine.getStatusId() != null && dbOperationLine.getLanguageId() != null) {
                    statusDescription = getStatusDescription(dbOperationLine.getStatusId(), dbOperationLine.getLanguageId());
                    if (statusDescription != null) {
                        dbOperationLine.setStatusDescription(statusDescription);
                    }
                }
                dbOperationLine.setUpdatedBy(loginUserID);
                dbOperationLine.setUpdatedOn(new Date());
                operationLineRepository.save(dbOperationLine);
                updatedOperationLines.add(dbOperationLine);
            }
            return updatedOperationLines;
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
    public void deleteOperationLine(String companyCodeId, String plantId, String languageId, String warehouseId, String productionOrderNo, String loginUserID) {
        try {
            List<OperationLine> dbOperationLines = getOperationLines(companyCodeId, plantId, languageId, warehouseId, productionOrderNo);
            log.info("Delete OperationLine Initiated : {}", dbOperationLines);
            for (OperationLine dbOperationLine : dbOperationLines) {
                dbOperationLine.setDeletionIndicator(1L);
                dbOperationLine.setUpdatedBy(loginUserID);
                dbOperationLine.setUpdatedOn(new Date());
                operationLineRepository.save(dbOperationLine);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * hard delete
     *
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param productionOrderNo
     */
    public void deleteOperationLineProductionOrder(String companyCodeId, String plantId, String languageId, String warehouseId, String productionOrderNo) {
        try {
            List<OperationLine> dbOperationLines = getOperationLines(companyCodeId, plantId, languageId, warehouseId, productionOrderNo);
            log.info("Delete OperationLine Initiated : {}", dbOperationLines);
            operationLineRepository.deleteAll(dbOperationLines);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * @param operationHeader
     * @param newOperationLine
     * @param loginUserID
     * @return
     */
    public List<ProductionOrder> createProductionOrderLine(OperationHeader operationHeader, OperationLine newOperationLine, String loginUserID) {
        try {
//        List<OperationLine> createdOperationLines = new ArrayList<>();
            Long lineNumber = 0L;
            List<ProductionOrder> productionOrderList = null;
            List<ProductionOrder> createSFGproductionOrderList = new ArrayList<>();
            if (newOperationLine != null) {
                for (int batch = 1; batch <= operationHeader.getNumberOfBatches(); batch++) {
                    OperationConsumption newOperationConsumption = new OperationConsumption();
                    Optional<OperationLine> operationLine =
                            operationLineRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndProductionOrderLineNoAndItemCodeAndDeletionIndicator(
                                    newOperationLine.getLanguageId(),
                                    newOperationLine.getCompanyCodeId(),
                                    newOperationLine.getPlantId(),
                                    newOperationLine.getWarehouseId(),
                                    newOperationLine.getProductionOrderNo(),
                                    newOperationLine.getProductionOrderLineNo(),
                                    newOperationLine.getItemCode(),
                                    0L);
                    if (operationLine.isPresent()) {
                        throw new BadRequestException("Record is getting duplicated with the given values");
                    }
                    lineNumber++;
                    log.info("create OperationLine Initiated: {}", newOperationLine);
                    OperationLine dbOperationLine = new OperationLine();
                    BeanUtils.copyProperties(newOperationLine, dbOperationLine, CommonUtils.getNullPropertyNames(newOperationLine));
                    dbOperationLine.setStatusId(97L);
                    if (dbOperationLine.getCompanyCodeId() != null && dbOperationLine.getPlantId() != null &&
                            dbOperationLine.getLanguageId() != null && dbOperationLine.getWarehouseId() != null) {
                        NUMBER_RANGE_CODE = 25L;
                        numberRangeId = getNextRangeNumber(NUMBER_RANGE_CODE, dbOperationLine.getCompanyCodeId(), dbOperationLine.getPlantId(),
                                dbOperationLine.getLanguageId(), dbOperationLine.getWarehouseId());
                        if (numberRangeId != null) {
                            dbOperationLine.setBatchNumber(numberRangeId);
                        }
                        description = getDescription(dbOperationLine.getCompanyCodeId(), dbOperationLine.getPlantId(),
                                dbOperationLine.getLanguageId(), dbOperationLine.getWarehouseId());
                        if (description != null) {
                            dbOperationLine.setCompanyDescription(description.getCompanyDescription());
                            dbOperationLine.setPlantDescription(description.getPlantDescription());
                            dbOperationLine.setWarehouseDescription(description.getWarehouseDescription());
                        }
                    }
                    if (dbOperationLine.getStatusId() != null && dbOperationLine.getLanguageId() != null) {
                        statusDescription = getStatusDescription(dbOperationLine.getStatusId(), dbOperationLine.getLanguageId());
                        if (statusDescription != null) {
                            dbOperationLine.setStatusDescription(statusDescription);
                        }
                    }
                    if (dbOperationLine.getItemCode() != null && !dbOperationLine.getItemCode().isBlank()) {
                        MasterReceipe masterReceipe = masterReceipeService.getMasterReceipe(dbOperationLine.getCompanyCodeId(),
                                dbOperationLine.getPlantId(), dbOperationLine.getLanguageId(), dbOperationLine.getWarehouseId(), dbOperationLine.getItemCode());
                        if (masterReceipe != null) {
                            dbOperationLine.setReceipeId(masterReceipe.getReceipeId());
                            dbOperationLine.setOperationNumber(masterReceipe.getOperationNumber());
                            dbOperationLine.setProductionOrderNo(operationHeader.getProductionOrderNo());
                            dbOperationLine.setItemCode(masterReceipe.getItemCode());
                            dbOperationLine.setItemDescription(masterReceipe.getRemarks());
                            dbOperationLine.setBatchDate(new Date());
                            dbOperationLine.setOperationDescription(masterReceipe.getOperationDescription());
                            dbOperationLine.setProductionOrderLineNo(lineNumber);
//                            dbOperationLine.setOrderQuantity(newOperationLine.getOrderQuantity());
//                            dbOperationLine.setBatchQuantity(newOperationLine.getBatchQuantity());
//                            dbOperationLine.setExpectedQuantity(newOperationLine.getExpectedQuantity());
                            dbOperationLine.setBomNumber(masterReceipe.getBomNumber());
                        }
                    }
                    if(dbOperationLine.getOrderQuantity() == null) {
                        dbOperationLine.setOrderQuantity(newOperationLine.getBatchQuantity());
                    }
                    dbOperationLine.setReceipePercentage(operationHeader.getReceipePercentage());
                    dbOperationLine.setProductionOrderType(P_ORD_TYP_FG);
                    dbOperationLine.setDeletionIndicator(0L);
                    dbOperationLine.setCreatedBy(loginUserID);
                    dbOperationLine.setCreatedOn(new Date());
                    operationLineRepository.save(dbOperationLine);
                    //                createdOperationLines.add(dbOperationLine);

                    BeanUtils.copyProperties(dbOperationLine, newOperationConsumption, CommonUtils.getNullPropertyNames(dbOperationLine));
                    newOperationConsumption.setReceipeQuantity(dbOperationLine.getBatchQuantity());
                    newOperationConsumption.setIssuedQuantity(dbOperationLine.getActualQuantity());
                    productionOrderList = operationConsumptionService.createProductionOrderOperationConsumption(newOperationConsumption, operationHeader, loginUserID);
                    createSFGproductionOrderList.addAll(productionOrderList);
                }
            }
            return createSFGproductionOrderList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * @param productionOrder
     * @param loginUserID
     * @return
     */
    public List<ProductionOrder> createFGSFGProductionOrderLine(ProductionOrder productionOrder, String loginUserID) {
        try {
            OperationLine newOperationLine = productionOrder.getProductionLine();
            OperationHeader newOperationHeader = new OperationHeader();
            BeanUtils.copyProperties(productionOrder, newOperationHeader, CommonUtils.getNullPropertyNames(productionOrder));
            OperationLine dbOperationLine = new OperationLine();
            if (newOperationLine != null) {
                log.info("create SFG OperationLine Initiated: {}", newOperationLine);
                BeanUtils.copyProperties(newOperationLine, dbOperationLine, CommonUtils.getNullPropertyNames(newOperationLine));
                dbOperationLine.setStatusId(97L);
                if (dbOperationLine.getCompanyCodeId() != null && dbOperationLine.getPlantId() != null &&
                        dbOperationLine.getLanguageId() != null && dbOperationLine.getWarehouseId() != null) {
                    NUMBER_RANGE_CODE = 26L;
                    numberRangeId = getNextRangeNumber(NUMBER_RANGE_CODE, dbOperationLine.getCompanyCodeId(), dbOperationLine.getPlantId(),
                            dbOperationLine.getLanguageId(), dbOperationLine.getWarehouseId());
                    if (numberRangeId != null) {
                        dbOperationLine.setBatchNumber(numberRangeId);
                    }
                    description = getDescription(dbOperationLine.getCompanyCodeId(), dbOperationLine.getPlantId(),
                            dbOperationLine.getLanguageId(), dbOperationLine.getWarehouseId());
                    if (description != null) {
                        dbOperationLine.setCompanyDescription(description.getCompanyDescription());
                        dbOperationLine.setPlantDescription(description.getPlantDescription());
                        dbOperationLine.setWarehouseDescription(description.getWarehouseDescription());
                    }
                }
                if (dbOperationLine.getStatusId() != null && dbOperationLine.getLanguageId() != null) {
                    statusDescription = getStatusDescription(dbOperationLine.getStatusId(), dbOperationLine.getLanguageId());
                    if (statusDescription != null) {
                        dbOperationLine.setStatusDescription(statusDescription);
                    }
                }
                MasterReceipe masterReceipe = masterReceipeService.getMasterReceipe(dbOperationLine.getCompanyCodeId(),
                        dbOperationLine.getPlantId(), dbOperationLine.getLanguageId(), dbOperationLine.getWarehouseId(), dbOperationLine.getItemCode());
                if (masterReceipe != null) {
                    dbOperationLine.setReceipeId(masterReceipe.getReceipeId());
                    dbOperationLine.setOperationNumber(masterReceipe.getOperationNumber());
                    dbOperationLine.setProductionOrderNo(newOperationHeader.getProductionOrderNo());
                    dbOperationLine.setItemCode(masterReceipe.getItemCode());
                    dbOperationLine.setItemDescription(masterReceipe.getRemarks());
                    dbOperationLine.setBatchDate(new Date());
                    dbOperationLine.setOperationDescription(masterReceipe.getOperationDescription());
                    dbOperationLine.setProductionOrderLineNo(1L);
                    dbOperationLine.setBomNumber(masterReceipe.getBomNumber());
                }
                if(dbOperationLine.getOrderQuantity() == null) {
                    dbOperationLine.setOrderQuantity(newOperationLine.getBatchQuantity());
                }
                dbOperationLine.setReceipePercentage(newOperationHeader.getReceipePercentage());
                dbOperationLine.setProductionOrderType(P_ORD_TYP_SFG);
                dbOperationLine.setDeletionIndicator(0L);
                dbOperationLine.setCreatedBy(loginUserID);
                dbOperationLine.setCreatedOn(new Date());
                operationLineRepository.save(dbOperationLine);
            }
            OperationConsumption newOperationConsumption = new OperationConsumption();
            BeanUtils.copyProperties(dbOperationLine, newOperationConsumption, CommonUtils.getNullPropertyNames(dbOperationLine));
            newOperationConsumption.setReceipeQuantity(dbOperationLine.getOrderQuantity());
            newOperationConsumption.setIssuedQuantity(dbOperationLine.getActualQuantity());
            List<ProductionOrder> productionOrderList = operationConsumptionService.createSFGProductionOrderOperationConsumption(newOperationHeader, newOperationConsumption, loginUserID);
            return productionOrderList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     *
     * @param productionOrder
     * @param loginUserID
     * @return
     */
    public List<ProductionOrder> createSFGProductionOrderLine(ProductionOrder productionOrder, String loginUserID) {
        try {
            OperationLine newOperationLine = productionOrder.getProductionLine();
            OperationHeader newOperationHeader = new OperationHeader();
            BeanUtils.copyProperties(productionOrder, newOperationHeader, CommonUtils.getNullPropertyNames(productionOrder));

            List<ProductionOrder> productionOrderList = null;
            if (newOperationLine != null) {
                Long lineNumber = 0L;
                for (int batch = 1; batch <= newOperationHeader.getNumberOfBatches(); batch++) {
                    Optional<OperationLine> operationLine =
                            operationLineRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndProductionOrderLineNoAndItemCodeAndDeletionIndicator(
                                    newOperationLine.getLanguageId(),
                                    newOperationLine.getCompanyCodeId(),
                                    newOperationLine.getPlantId(),
                                    newOperationLine.getWarehouseId(),
                                    newOperationLine.getProductionOrderNo(),
                                    newOperationLine.getProductionOrderLineNo(),
                                    newOperationLine.getItemCode(),
                                    0L);
                    if (operationLine.isPresent()) {
                        throw new BadRequestException("Record is getting duplicated with the given values");
                    }
                    lineNumber++;
                    OperationLine dbOperationLine = new OperationLine();
                    log.info("create SFG OperationLine Initiated: {}", newOperationLine);
                    BeanUtils.copyProperties(newOperationLine, dbOperationLine, CommonUtils.getNullPropertyNames(newOperationLine));
                    dbOperationLine.setStatusId(97L);
                    if (dbOperationLine.getCompanyCodeId() != null && dbOperationLine.getPlantId() != null &&
                            dbOperationLine.getLanguageId() != null && dbOperationLine.getWarehouseId() != null) {
                        NUMBER_RANGE_CODE = 26L;
                        numberRangeId = getNextRangeNumber(NUMBER_RANGE_CODE, dbOperationLine.getCompanyCodeId(), dbOperationLine.getPlantId(),
                                dbOperationLine.getLanguageId(), dbOperationLine.getWarehouseId());
                        if (numberRangeId != null) {
                            dbOperationLine.setBatchNumber(numberRangeId);
                        }
                        description = getDescription(dbOperationLine.getCompanyCodeId(), dbOperationLine.getPlantId(),
                                dbOperationLine.getLanguageId(), dbOperationLine.getWarehouseId());
                        if (description != null) {
                            dbOperationLine.setCompanyDescription(description.getCompanyDescription());
                            dbOperationLine.setPlantDescription(description.getPlantDescription());
                            dbOperationLine.setWarehouseDescription(description.getWarehouseDescription());
                        }
                    }
                    if (dbOperationLine.getStatusId() != null && dbOperationLine.getLanguageId() != null) {
                        statusDescription = getStatusDescription(dbOperationLine.getStatusId(), dbOperationLine.getLanguageId());
                        if (statusDescription != null) {
                            dbOperationLine.setStatusDescription(statusDescription);
                        }
                    }
                    MasterReceipe masterReceipe = masterReceipeService.getMasterReceipe(dbOperationLine.getCompanyCodeId(),
                            dbOperationLine.getPlantId(), dbOperationLine.getLanguageId(), dbOperationLine.getWarehouseId(), dbOperationLine.getItemCode());
                    if (masterReceipe != null) {
                        dbOperationLine.setReceipeId(masterReceipe.getReceipeId());
                        dbOperationLine.setOperationNumber(masterReceipe.getOperationNumber());
                        dbOperationLine.setProductionOrderNo(newOperationHeader.getProductionOrderNo());
                        dbOperationLine.setItemCode(masterReceipe.getItemCode());
                        dbOperationLine.setItemDescription(masterReceipe.getRemarks());
                        dbOperationLine.setBatchDate(new Date());
                        dbOperationLine.setOperationDescription(masterReceipe.getOperationDescription());
                        dbOperationLine.setProductionOrderLineNo(lineNumber);
                        dbOperationLine.setBomNumber(masterReceipe.getBomNumber());
                    }
                    if (dbOperationLine.getOrderQuantity() == null) {
                        dbOperationLine.setOrderQuantity(newOperationLine.getBatchQuantity());
                    }
                    dbOperationLine.setReceipePercentage(newOperationHeader.getReceipePercentage());
                    dbOperationLine.setProductionOrderType(P_ORD_TYP_SFG);
                    dbOperationLine.setDeletionIndicator(0L);
                    dbOperationLine.setCreatedBy(loginUserID);
                    dbOperationLine.setCreatedOn(new Date());
                    operationLineRepository.save(dbOperationLine);

                    OperationConsumption newOperationConsumption = new OperationConsumption();
                    BeanUtils.copyProperties(dbOperationLine, newOperationConsumption, CommonUtils.getNullPropertyNames(dbOperationLine));
                    newOperationConsumption.setReceipeQuantity(dbOperationLine.getBatchQuantity());
                    newOperationConsumption.setIssuedQuantity(dbOperationLine.getActualQuantity());
                    productionOrderList = operationConsumptionService.createSFGProductionOrderOperationConsumption(newOperationHeader, newOperationConsumption, loginUserID);
                }
            }
            return productionOrderList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     *
     * @param operationLine
     */
    public void createInboundOrder(OperationLine operationLine) {
            ASNV2 asn = new ASNV2();
            ASNHeaderV2 asnHeader = createInboundHeader(operationLine);
            ASNLineV2 asnLine = createInboundLine(operationLine);
            List<ASNLineV2> asnLineList = new ArrayList<>();
            asnLineList.add(asnLine);
            asn.setAsnHeader(asnHeader);
            asn.setAsnLine(asnLineList);
        log.info("ASN : {}", asn);
            transactionService.postASNV2(asn);
    }

    /**
     * @param operationConsumption
     * @return
     */
    private ASNHeaderV2 createInboundHeader(OperationLine operationConsumption) {
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
            if(operationConsumption.getProductionOrderType() != null && !operationConsumption.getProductionOrderType().isBlank()) {
                if (operationConsumption.getProductionOrderType().equalsIgnoreCase(P_ORD_TYP_FG)) {
                    asnHeader.setInboundOrderTypeId(IB_ORD_TYP_ID_FG);
                }
                if (operationConsumption.getProductionOrderType().equalsIgnoreCase(P_ORD_TYP_SFG)) {
                    asnHeader.setInboundOrderTypeId(IB_ORD_TYP_ID_SFG);
                }
            } else {
                throw new BadRequestException("Production Order type should be specified in operationLine to create a inbound order..!");
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
    private ASNLineV2 createInboundLine(OperationLine operationConsumption) {
        try {
            ASNLineV2 asnLine = new ASNLineV2();
            asnLine.setCompanyCode(operationConsumption.getCompanyCodeId());
            asnLine.setBranchCode(operationConsumption.getPlantId());
            asnLine.setLineReference(operationConsumption.getProductionOrderLineNo());
            asnLine.setSku(operationConsumption.getItemCode());
            asnLine.setSkuDescription(operationConsumption.getItemDescription());
            asnLine.setExpectedQty(operationConsumption.getActualQuantity());
            if (operationConsumption.getUom() != null) {
                asnLine.setUom(operationConsumption.getUom());
            } else {
                asnLine.setUom("Kg");                //HardCode
            }
            asnLine.setSupplierCode(operationConsumption.getOperationNumber());
            asnLine.setPurchaseOrderNumber(operationConsumption.getProductionOrderNo());
            asnLine.setSupplierInvoiceNo(operationConsumption.getParentProductionOrderNo());
            if (operationConsumption.getProductionOrderType() != null && !operationConsumption.getProductionOrderType().isBlank()) {
                if (operationConsumption.getProductionOrderType().equalsIgnoreCase(P_ORD_TYP_FG)) {
                    asnLine.setInboundOrderTypeId(IB_ORD_TYP_ID_FG);
                    asnLine.setStorageSectionId(ST_SEC_ID_PFG);
                }
                if (operationConsumption.getProductionOrderType().equalsIgnoreCase(P_ORD_TYP_SFG)) {
                    asnLine.setInboundOrderTypeId(IB_ORD_TYP_ID_SFG);
                    asnLine.setStorageSectionId(ST_SEC_ID_PSFG);
                }
            } else {
                throw new BadRequestException("Production Order type should be specified in operationLine to create a inbound order..!");
            }
            asnLine.setReceivedQty(operationConsumption.getOrderQuantity());
            asnLine.setPackQty(operationConsumption.getBatchQuantity());
            String date = DateUtils.date2String_YYYYMMDD(new Date());
            asnLine.setExpectedDate(date);
            return asnLine;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }
}