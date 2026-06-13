package com.tekclover.wms.api.mfg.service;

import com.tekclover.wms.api.mfg.controller.exception.BadRequestException;
import com.tekclover.wms.api.mfg.model.operation.*;
import com.tekclover.wms.api.mfg.model.prodcutionorder.ProductionOrder;
import com.tekclover.wms.api.mfg.model.prodcutionorder.ProductionOrderOutput;
import com.tekclover.wms.api.mfg.repository.OperationHeaderRepository;
import com.tekclover.wms.api.mfg.repository.specification.OperationHeaderSpecification;
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
public class OperationHeaderService extends BaseService {

    @Autowired
    OperationHeaderRepository operationHeaderRepository;

    @Autowired
    OperationLineService operationLineService;
    //--------------------------------------------------------------------------

    /**
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param productionOrderNo
     * @return
     */
    public OperationHeader getOperationHeader(String companyCodeId, String plantId, String languageId, String warehouseId, String productionOrderNo) {
        Optional<OperationHeader> operationHeader =
                operationHeaderRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndDeletionIndicator(
                        languageId,
                        companyCodeId,
                        plantId,
                        warehouseId,
                        productionOrderNo,
                        0L);
        if (operationHeader.isEmpty()) {
            throw new BadRequestException("The given values: warehouseId:" + warehouseId +
                    ",companyCodeId: " + companyCodeId +
                    ",languageId: " + languageId +
                    ",plantId: " + plantId +
                    ",productionOrderNo: " + productionOrderNo +
                    " doesn't exist.");
        }
        return operationHeader.get();
    }

    /**
     *
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param productionOrderNo
     * @return
     */
    public List<OperationHeader> getProductionOrderConfirm(String companyCodeId, String plantId, String languageId, String warehouseId, String productionOrderNo) {
        List<OperationHeader> operationHeader =
                operationHeaderRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndParentProductionOrderNoAndDeletionIndicator(
                        languageId,
                        companyCodeId,
                        plantId,
                        warehouseId,
                        productionOrderNo,
                        0L);
        return operationHeader;
    }

    /**
     * @param searchOperationHeader
     * @return
     * @throws Exception
     */
    public Stream<OperationHeader> findOperationHeader(SearchOperationHeader searchOperationHeader) throws Exception {
        if (searchOperationHeader.getStartCreatedOn() != null && searchOperationHeader.getEndCreatedOn() != null) {
            Date[] dates = DateUtils.addTimeToDatesForSearch(searchOperationHeader.getStartCreatedOn(), searchOperationHeader.getEndCreatedOn());
            searchOperationHeader.setStartCreatedOn(dates[0]);
            searchOperationHeader.setEndCreatedOn(dates[1]);
        }
        if (searchOperationHeader.getStartConfirmedOn() != null && searchOperationHeader.getEndConfirmedOn() != null) {
            Date[] dates = DateUtils.addTimeToDatesForSearch(searchOperationHeader.getStartConfirmedOn(), searchOperationHeader.getEndConfirmedOn());
            searchOperationHeader.setStartConfirmedOn(dates[0]);
            searchOperationHeader.setEndConfirmedOn(dates[1]);
        }
        log.info("searchOperationHeader Input: {}", searchOperationHeader);
        OperationHeaderSpecification spec = new OperationHeaderSpecification(searchOperationHeader);
        Stream<OperationHeader> results = operationHeaderRepository.stream(spec, OperationHeader.class);
        return results;
    }

    /**
     * @param newOperationHeader
     * @param loginUserID
     * @return
     */
    public OperationHeader createOperationHeader(OperationHeader newOperationHeader, String loginUserID) {
        try {
            Optional<OperationHeader> operationHeader =
                    operationHeaderRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndDeletionIndicator(
                            newOperationHeader.getLanguageId(),
                            newOperationHeader.getCompanyCodeId(),
                            newOperationHeader.getPlantId(),
                            newOperationHeader.getWarehouseId(),
                            newOperationHeader.getProductionOrderNo(),
                            0L);
            if (operationHeader.isPresent()) {
                throw new BadRequestException("Record is getting duplicated with the given values");
            }
            log.info("create OperationHeader Initiated: {}", newOperationHeader);
            OperationHeader dbOperationHeader = new OperationHeader();
            BeanUtils.copyProperties(newOperationHeader, dbOperationHeader, CommonUtils.getNullPropertyNames(newOperationHeader));
            if (dbOperationHeader.getCompanyCodeId() != null && dbOperationHeader.getPlantId() != null &&
                    dbOperationHeader.getLanguageId() != null && dbOperationHeader.getWarehouseId() != null) {
                NUMBER_RANGE_CODE = 24L;
                numberRangeId = getNextRangeNumber(NUMBER_RANGE_CODE, dbOperationHeader.getCompanyCodeId(), dbOperationHeader.getPlantId(),
                        dbOperationHeader.getLanguageId(), dbOperationHeader.getWarehouseId());
                if (numberRangeId != null) {
                    dbOperationHeader.setProductionOrderNo(numberRangeId);
                }
                description = getDescription(dbOperationHeader.getCompanyCodeId(), dbOperationHeader.getPlantId(),
                        dbOperationHeader.getLanguageId(), dbOperationHeader.getWarehouseId());
                if (description != null) {
                    dbOperationHeader.setCompanyDescription(description.getCompanyDescription());
                    dbOperationHeader.setPlantDescription(description.getPlantDescription());
                    dbOperationHeader.setWarehouseDescription(description.getWarehouseDescription());
                }
            } else {
                throw new BadRequestException("ProductionOrderNo cannot be Null. provide company, plant, language & warehouse");
            }
            if (dbOperationHeader.getStatusId() != null && dbOperationHeader.getLanguageId() != null) {
                statusDescription = getStatusDescription(dbOperationHeader.getStatusId(), dbOperationHeader.getLanguageId());
                if (statusDescription != null) {
                    dbOperationHeader.setStatusDescription(statusDescription);
                }
            }
            dbOperationHeader.setDeletionIndicator(0L);
            dbOperationHeader.setCreatedBy(loginUserID);
            dbOperationHeader.setCreatedOn(new Date());
            return operationHeaderRepository.save(dbOperationHeader);
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
     * @param modifyOperationHeader
     * @return
     */
    public OperationHeader updateOperationHeader(String companyCodeId, String plantId, String languageId, String warehouseId,
                                                 String productionOrderNo, String loginUserID, OperationHeader modifyOperationHeader) {
        try {
            OperationHeader dbOperationHeader = getOperationHeader(companyCodeId, plantId, languageId, warehouseId, productionOrderNo);
            log.info("Update OperationHeader Initiated : {}", dbOperationHeader);
            BeanUtils.copyProperties(modifyOperationHeader, dbOperationHeader, CommonUtils.getNullPropertyNames(modifyOperationHeader));
            if (dbOperationHeader.getStatusId() != null && dbOperationHeader.getLanguageId() != null) {
                statusDescription = getStatusDescription(dbOperationHeader.getStatusId(), dbOperationHeader.getLanguageId());
                if (statusDescription != null) {
                    dbOperationHeader.setStatusDescription(statusDescription);
                }
            }
            dbOperationHeader.setUpdatedBy(loginUserID);
            dbOperationHeader.setUpdatedOn(new Date());
            return operationHeaderRepository.save(dbOperationHeader);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * @param newOperationHeaderList
     * @param loginUserID
     * @return
     */
    public List<OperationHeader> createOperationHeaderBatch(List<OperationHeader> newOperationHeaderList, String loginUserID) {
        List<OperationHeader> createdOperationHeaderList = new ArrayList<>();
        for (OperationHeader dbOperationHeader : newOperationHeaderList) {
            OperationHeader createOperationHeader = createOperationHeader(dbOperationHeader, loginUserID);
            createdOperationHeaderList.add(createOperationHeader);
        }
        return createdOperationHeaderList;
    }

    /**
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param loginUserID
     * @param modifyOperationHeaderList
     * @return
     */
    public List<OperationHeader> updateOperationHeaderBatch(String companyCodeId, String plantId, String languageId, String warehouseId,
                                                            String loginUserID, List<OperationHeader> modifyOperationHeaderList) {
        try {
            List<OperationHeader> updatedOperationHeaderList = new ArrayList<>();
            for (OperationHeader modifyOperationHeader : modifyOperationHeaderList) {
                OperationHeader dbOperationHeader = getOperationHeader(companyCodeId, plantId, languageId, warehouseId, modifyOperationHeader.getProductionOrderNo());
                log.info("Update OperationHeader Initiated : {}", dbOperationHeader);
                BeanUtils.copyProperties(modifyOperationHeader, dbOperationHeader, CommonUtils.getNullPropertyNames(modifyOperationHeader));
                if (dbOperationHeader.getStatusId() != null && dbOperationHeader.getLanguageId() != null) {
                    statusDescription = getStatusDescription(dbOperationHeader.getStatusId(), dbOperationHeader.getLanguageId());
                    if (statusDescription != null) {
                        dbOperationHeader.setStatusDescription(statusDescription);
                    }
                }
                dbOperationHeader.setUpdatedBy(loginUserID);
                dbOperationHeader.setUpdatedOn(new Date());
                OperationHeader updatedOperationHeader = operationHeaderRepository.save(dbOperationHeader);
                updatedOperationHeaderList.add(updatedOperationHeader);
            }
            return updatedOperationHeaderList;
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
    public void deleteOperationHeader(String companyCodeId, String plantId, String languageId, String warehouseId, String productionOrderNo, String loginUserID) {
        try {
            OperationHeader dbOperationHeader = getOperationHeader(companyCodeId, plantId, languageId, warehouseId, productionOrderNo);
            log.info("Delete OperationHeader Initiated : {}", dbOperationHeader);
            dbOperationHeader.setDeletionIndicator(1L);
            dbOperationHeader.setUpdatedBy(loginUserID);
            dbOperationHeader.setUpdatedOn(new Date());
            operationHeaderRepository.save(dbOperationHeader);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * Hard delete
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param productionOrderNo
     */
    public void deleteOperationHeaderProductionOrder(String companyCodeId, String plantId, String languageId, String warehouseId, String productionOrderNo) {
        try {
            OperationHeader dbOperationHeader = getOperationHeader(companyCodeId, plantId, languageId, warehouseId, productionOrderNo);
            log.info("Delete OperationHeader Initiated : {}", dbOperationHeader);
            operationHeaderRepository.delete(dbOperationHeader);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }
    /**
     * @param newOperationHeader
     * @param loginUserID
     * @return
     */
    public ProductionOrderOutput createProductionOrder(ProductionOrder newOperationHeader, String loginUserID) {
        try {
            ProductionOrderOutput newProductionOrder = new ProductionOrderOutput();
            Optional<OperationHeader> operationHeader =
                    operationHeaderRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndDeletionIndicator(
                            newOperationHeader.getLanguageId(),
                            newOperationHeader.getCompanyCodeId(),
                            newOperationHeader.getPlantId(),
                            newOperationHeader.getWarehouseId(),
                            newOperationHeader.getProductionOrderNo(),
                            0L);
            if (operationHeader.isPresent()) {
                throw new BadRequestException("Record is getting duplicated with the given values");
            }
            if (newOperationHeader.getNumberOfBatches() == null || newOperationHeader.getTotalOrderQuantity() == null || newOperationHeader.getReceipePercentage() == null) {
                throw new BadRequestException("Number of Batch, Order Qty and receipe percentage cannot be null");
            }
            log.info("create ProductionOrderHeader Initiated: {}", newOperationHeader);
            OperationHeader dbOperationHeader = new OperationHeader();
            BeanUtils.copyProperties(newOperationHeader, dbOperationHeader, CommonUtils.getNullPropertyNames(newOperationHeader));
            dbOperationHeader.setStatusId(97L);
            if (dbOperationHeader.getCompanyCodeId() != null && dbOperationHeader.getPlantId() != null &&
                    dbOperationHeader.getLanguageId() != null && dbOperationHeader.getWarehouseId() != null) {
                if(newOperationHeader.getProductionOrderNo() != null && !newOperationHeader.getProductionOrderNo().isBlank()) {
                    dbOperationHeader.setProductionOrderNo(dbOperationHeader.getProductionOrderNo());
                } else {
                    NUMBER_RANGE_CODE = 24L;
                    numberRangeId = getNextRangeNumber(NUMBER_RANGE_CODE, dbOperationHeader.getCompanyCodeId(), dbOperationHeader.getPlantId(),
                            dbOperationHeader.getLanguageId(), dbOperationHeader.getWarehouseId());
                    if (numberRangeId != null) {
                        dbOperationHeader.setProductionOrderNo(numberRangeId);
                    }
                }
                description = getDescription(dbOperationHeader.getCompanyCodeId(), dbOperationHeader.getPlantId(),
                        dbOperationHeader.getLanguageId(), dbOperationHeader.getWarehouseId());
                if (description != null) {
                    dbOperationHeader.setCompanyDescription(description.getCompanyDescription());
                    dbOperationHeader.setPlantDescription(description.getPlantDescription());
                    dbOperationHeader.setWarehouseDescription(description.getWarehouseDescription());
                }
            } else {
                throw new BadRequestException("ProductionOrderNo cannot be Null. provide company, plant, language & warehouse");
            }
            if (dbOperationHeader.getStatusId() != null && dbOperationHeader.getLanguageId() != null) {
                statusDescription = getStatusDescription(dbOperationHeader.getStatusId(), dbOperationHeader.getLanguageId());
                if (statusDescription != null) {
                    dbOperationHeader.setStatusDescription(statusDescription);
                }
            }
            dbOperationHeader.setDeletionIndicator(0L);
            dbOperationHeader.setCreatedBy(loginUserID);
            dbOperationHeader.setCreatedOn(new Date());
            dbOperationHeader.setProductionOrderType(P_ORD_TYP_FG);
            OperationHeader createdOperationHeader = operationHeaderRepository.save(dbOperationHeader);
            BeanUtils.copyProperties(createdOperationHeader, newProductionOrder, CommonUtils.getNullPropertyNames(createdOperationHeader));
            List<ProductionOrder> productionOrderList = operationLineService.createProductionOrderLine(createdOperationHeader, newOperationHeader.getProductionLine(), loginUserID);
//        if (operationLines != null && !operationLines.isEmpty()) {
//            newProductionOrder.setProductionLines(operationLines);
//        }
            if(productionOrderList != null && !productionOrderList.isEmpty()) {
                createFGSFGProductionOrder(productionOrderList, loginUserID);
            }
            return newProductionOrder;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }
    /**
     * @param newSfgProductionOrderList
     * @param loginUserID
     * @return
     */
    public void createFGSFGProductionOrder(List<ProductionOrder> newSfgProductionOrderList, String loginUserID) {
        try {
            log.info("create SFG ProductionOrderHeader Initiated: {}", newSfgProductionOrderList);
            for(ProductionOrder newSfgProductionOrder : newSfgProductionOrderList) {
                OperationHeader dbOperationHeader = new OperationHeader();
                ProductionOrder createSFGProductionOrderLine = new ProductionOrder();
                BeanUtils.copyProperties(newSfgProductionOrder, dbOperationHeader, CommonUtils.getNullPropertyNames(newSfgProductionOrder));
                dbOperationHeader.setStatusId(97L);
                if (dbOperationHeader.getCompanyCodeId() != null && dbOperationHeader.getPlantId() != null &&
                        dbOperationHeader.getLanguageId() != null && dbOperationHeader.getWarehouseId() != null) {
                    NUMBER_RANGE_CODE = 27L;
                    numberRangeId = getNextRangeNumber(NUMBER_RANGE_CODE, dbOperationHeader.getCompanyCodeId(), dbOperationHeader.getPlantId(),
                            dbOperationHeader.getLanguageId(), dbOperationHeader.getWarehouseId());
                    if (numberRangeId != null) {
                        dbOperationHeader.setProductionOrderNo(numberRangeId);
                    }
                    description = getDescription(dbOperationHeader.getCompanyCodeId(), dbOperationHeader.getPlantId(),
                            dbOperationHeader.getLanguageId(), dbOperationHeader.getWarehouseId());
                    if (description != null) {
                        dbOperationHeader.setCompanyDescription(description.getCompanyDescription());
                        dbOperationHeader.setPlantDescription(description.getPlantDescription());
                        dbOperationHeader.setWarehouseDescription(description.getWarehouseDescription());
                    }
                }
                if (dbOperationHeader.getStatusId() != null && dbOperationHeader.getLanguageId() != null) {
                    statusDescription = getStatusDescription(dbOperationHeader.getStatusId(), dbOperationHeader.getLanguageId());
                    if (statusDescription != null) {
                        dbOperationHeader.setStatusDescription(statusDescription);
                    }
                }
                if (newSfgProductionOrder.getNumberOfBatches() != null && newSfgProductionOrder.getTotalOrderQuantity() != null) {
                    Double totalOrderQty = newSfgProductionOrder.getNumberOfBatches() * newSfgProductionOrder.getTotalOrderQuantity();
                    dbOperationHeader.setNumberOfBatches(1L);
                    dbOperationHeader.setTotalOrderQuantity(totalOrderQty);
                }
                dbOperationHeader.setParentProductionOrderNo(newSfgProductionOrder.getProductionOrderNo());
                dbOperationHeader.setProductionOrderType(P_ORD_TYP_SFG);
                dbOperationHeader.setDeletionIndicator(0L);
                dbOperationHeader.setCreatedBy(loginUserID);
                dbOperationHeader.setCreatedOn(new Date());
                OperationHeader createdSFGProductionOrder = operationHeaderRepository.save(dbOperationHeader);

                BeanUtils.copyProperties(createdSFGProductionOrder, createSFGProductionOrderLine, CommonUtils.getNullPropertyNames(createdSFGProductionOrder));

                if (newSfgProductionOrder.getProductionLine() != null) {
                    OperationLine dbOperationLine = newSfgProductionOrder.getProductionLine();
                    dbOperationLine.setParentProductionOrderNo(newSfgProductionOrder.getProductionOrderNo());
                    dbOperationLine.setProductionOrderNo(createdSFGProductionOrder.getProductionOrderNo());
                    dbOperationLine.setReceipePercentage(createdSFGProductionOrder.getReceipePercentage());
                    createSFGProductionOrderLine.setProductionOrderNo(createdSFGProductionOrder.getProductionOrderNo());
                    createSFGProductionOrderLine.setParentProductionOrderNo(createdSFGProductionOrder.getParentProductionOrderNo());
                    createSFGProductionOrderLine.setProductionLine(dbOperationLine);
                    List<ProductionOrder> productionOrderList = operationLineService.createFGSFGProductionOrderLine(createSFGProductionOrderLine, loginUserID);

                    if(productionOrderList != null && !productionOrderList.isEmpty()) {
                        createFGSFGProductionOrder(productionOrderList, loginUserID);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     *
     * @param newSfgProductionOrderList
     * @param loginUserID
     */
    public void createSFGProductionOrder(List<ProductionOrder> newSfgProductionOrderList, String loginUserID) {
        try {
            log.info("create SFG ProductionOrderHeader Initiated: {}", newSfgProductionOrderList);
            for(ProductionOrder newSfgProductionOrder : newSfgProductionOrderList) {
                OperationHeader dbOperationHeader = new OperationHeader();
                ProductionOrder createSFGProductionOrderLine = new ProductionOrder();
                BeanUtils.copyProperties(newSfgProductionOrder, dbOperationHeader, CommonUtils.getNullPropertyNames(newSfgProductionOrder));
                dbOperationHeader.setStatusId(97L);
                if (dbOperationHeader.getCompanyCodeId() != null && dbOperationHeader.getPlantId() != null &&
                        dbOperationHeader.getLanguageId() != null && dbOperationHeader.getWarehouseId() != null) {
                    NUMBER_RANGE_CODE = 27L;
                    numberRangeId = getNextRangeNumber(NUMBER_RANGE_CODE, dbOperationHeader.getCompanyCodeId(), dbOperationHeader.getPlantId(),
                            dbOperationHeader.getLanguageId(), dbOperationHeader.getWarehouseId());
                    if (numberRangeId != null) {
                        dbOperationHeader.setProductionOrderNo(numberRangeId);
                    }
                    description = getDescription(dbOperationHeader.getCompanyCodeId(), dbOperationHeader.getPlantId(),
                            dbOperationHeader.getLanguageId(), dbOperationHeader.getWarehouseId());
                    if (description != null) {
                        dbOperationHeader.setCompanyDescription(description.getCompanyDescription());
                        dbOperationHeader.setPlantDescription(description.getPlantDescription());
                        dbOperationHeader.setWarehouseDescription(description.getWarehouseDescription());
                    }
                }
                if (dbOperationHeader.getStatusId() != null && dbOperationHeader.getLanguageId() != null) {
                    statusDescription = getStatusDescription(dbOperationHeader.getStatusId(), dbOperationHeader.getLanguageId());
                    if (statusDescription != null) {
                        dbOperationHeader.setStatusDescription(statusDescription);
                    }
                }
//                if (newSfgProductionOrder.getNumberOfBatches() != null && newSfgProductionOrder.getTotalOrderQuantity() != null) {
//                    Double totalOrderQty = newSfgProductionOrder.getNumberOfBatches() * newSfgProductionOrder.getTotalOrderQuantity();
//                    dbOperationHeader.setNumberOfBatches(1L);
//                    dbOperationHeader.setTotalOrderQuantity(totalOrderQty);
//                }
                dbOperationHeader.setParentProductionOrderNo(newSfgProductionOrder.getProductionOrderNo());
                dbOperationHeader.setProductionOrderType(P_ORD_TYP_SFG);
                dbOperationHeader.setDeletionIndicator(0L);
                dbOperationHeader.setCreatedBy(loginUserID);
                dbOperationHeader.setCreatedOn(new Date());
                OperationHeader createdSFGProductionOrder = operationHeaderRepository.save(dbOperationHeader);

                BeanUtils.copyProperties(createdSFGProductionOrder, createSFGProductionOrderLine, CommonUtils.getNullPropertyNames(createdSFGProductionOrder));

                if (newSfgProductionOrder.getProductionLine() != null) {
                    OperationLine dbOperationLine = newSfgProductionOrder.getProductionLine();
                    dbOperationLine.setParentProductionOrderNo(newSfgProductionOrder.getProductionOrderNo());
                    dbOperationLine.setProductionOrderNo(createdSFGProductionOrder.getProductionOrderNo());
                    dbOperationLine.setReceipePercentage(createdSFGProductionOrder.getReceipePercentage());
                    createSFGProductionOrderLine.setProductionOrderNo(createdSFGProductionOrder.getProductionOrderNo());
                    createSFGProductionOrderLine.setParentProductionOrderNo(createdSFGProductionOrder.getParentProductionOrderNo());
                    createSFGProductionOrderLine.setProductionLine(dbOperationLine);
                    List<ProductionOrder> productionOrderList = operationLineService.createSFGProductionOrderLine(createSFGProductionOrderLine, loginUserID);

                    if(productionOrderList != null && !productionOrderList.isEmpty()) {
                        createFGSFGProductionOrder(productionOrderList, loginUserID);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }
}