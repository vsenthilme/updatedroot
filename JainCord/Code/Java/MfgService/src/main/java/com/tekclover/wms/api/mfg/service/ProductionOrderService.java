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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductionOrderService extends BaseService {

    @Autowired
    OperationHeaderRepository operationHeaderRepository;

    @Autowired
    OperationLineService operationLineService;

    @Autowired
    OperationHeaderService operationHeaderService;

    @Autowired
    OperationConsumptionService operationConsumptionService;
    //--------------------------------------------------------------------------

    /**
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param productionOrderNo
     * @return
     */
    public ProductionOrderOutput getProductionOrder(String companyCodeId, String plantId, String languageId, String warehouseId, String productionOrderNo) {
        OperationHeader operationHeader = operationHeaderService.getOperationHeader(companyCodeId, plantId, languageId, warehouseId, productionOrderNo);
        List<OperationLine> operationLines = operationLineService.getOperationLines(companyCodeId, plantId, languageId, warehouseId, productionOrderNo);

        ProductionOrderOutput dbProductionOrder = new ProductionOrderOutput();
        BeanUtils.copyProperties(operationHeader, dbProductionOrder, CommonUtils.getNullPropertyNames(operationHeader));
        dbProductionOrder.setProductionLines(operationLines);
        return dbProductionOrder;
    }

    /**
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param productionOrderNo
     * @return
     */
    public ProductionOrder getProductionOrder(String companyCodeId, String plantId, String languageId, String warehouseId, String productionOrderNo, String batchNumber) {
        OperationHeader operationHeader = operationHeaderService.getOperationHeader(companyCodeId, plantId, languageId, warehouseId, productionOrderNo);
        OperationLine operationLine = operationLineService.getOperationLinesForProductionOrder(companyCodeId, plantId, languageId, warehouseId, productionOrderNo, batchNumber);

        ProductionOrder dbProductionOrder = new ProductionOrder();
        BeanUtils.copyProperties(operationHeader, dbProductionOrder, CommonUtils.getNullPropertyNames(operationHeader));
        dbProductionOrder.setProductionLine(operationLine);
        return dbProductionOrder;
    }

    /**
     * @param searchOperationHeader
     * @return
     * @throws Exception
     */
    public List<ProductionOrderOutput> findProductionOrder(SearchOperationHeader searchOperationHeader) throws Exception {
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
        List<OperationHeader> results = operationHeaderRepository.stream(spec, OperationHeader.class).collect(Collectors.toList());
        log.info("Production order Find results: {}", results.size());
        List<ProductionOrderOutput> productionOrderList = new ArrayList<>();
        if (results != null && !results.isEmpty()) {
            for (OperationHeader operationHeader : results) {
                ProductionOrderOutput dbProductionOrder = new ProductionOrderOutput();
                BeanUtils.copyProperties(operationHeader, dbProductionOrder, CommonUtils.getNullPropertyNames(operationHeader));
                List<OperationLine> operationLines = operationLineService.getOperationLinesForFindProductionOrder(
                        operationHeader.getCompanyCodeId(), operationHeader.getPlantId(),
                        operationHeader.getLanguageId(), operationHeader.getWarehouseId(), operationHeader.getProductionOrderNo());
                if (operationLines != null && !operationLines.isEmpty()) {
                    dbProductionOrder.setProductionLines(operationLines);
                }
                productionOrderList.add(dbProductionOrder);
            }
        }
        return productionOrderList;
    }

    /**
     * @param newProductionOrderList
     * @param loginUserID
     * @return
     */
    @Transactional
    public List<ProductionOrderOutput> createProductionOrderBatch(List<ProductionOrder> newProductionOrderList, String loginUserID) {
        try {
            List<ProductionOrderOutput> createdProductionOrderList = new ArrayList<>();
            for (ProductionOrder dbProductionOrder : newProductionOrderList) {
                ProductionOrderOutput createOperationHeader = operationHeaderService.createProductionOrder(dbProductionOrder, loginUserID);
                createdProductionOrderList.add(createOperationHeader);
            }
        return createdProductionOrderList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    @Transactional
    public Boolean createSFGProductionOrderBatch(List<ProductionOrder> newProductionOrderList, String loginUserID) {
    try {
            operationHeaderService.createSFGProductionOrder(newProductionOrderList, loginUserID);
            return true;
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
     * @param loginUserID
     * @param productionOrderList
     * @return
     */
    public List<ProductionOrderOutput> updateProductionOrderBatch(String companyCodeId, String plantId, String languageId, String warehouseId,
                                                                  String loginUserID, List<ProductionOrder> productionOrderList) {
        try {
            List<ProductionOrderOutput> updatedProductionOrderList = new ArrayList<>();
//            for (ProductionOrder modifyProductionOrder : productionOrderList) {
//
//                ProductionOrder updateProductionOrder = new ProductionOrder();
//                OperationHeader updateOperationHeader = new OperationHeader();
//
//                BeanUtils.copyProperties(modifyProductionOrder, updateOperationHeader, CommonUtils.getNullPropertyNames(modifyProductionOrder));
//                log.info("Update OperationHeader Initiated : " + updateOperationHeader);
//                OperationHeader dbOperationHeader = operationHeaderService.updateOperationHeader(companyCodeId, plantId, languageId, warehouseId,
//                        modifyProductionOrder.getProductionOrderNo(), loginUserID, updateOperationHeader);
//
//                BeanUtils.copyProperties(dbOperationHeader, updateProductionOrder, CommonUtils.getNullPropertyNames(dbOperationHeader));
//
//                List<OperationLine> operationLines = new ArrayList<>();
//                OperationLine operationLine = modifyProductionOrder.getProductionLine();
//                operationLines.add(operationLine);
//                List<OperationLine> updatedOperationLines = operationLineService.updateOperationLine(companyCodeId, plantId, languageId, warehouseId,
//                        modifyProductionOrder.getProductionOrderNo(), loginUserID, operationLines);
//                if(updatedOperationLines != null && !updatedOperationLines.isEmpty()) {
//                    updateProductionOrder.setProductionLine(updatedOperationLines.get(0));
//                }
//                updatedProductionOrderList.add(updateProductionOrder);
//            }
            if (productionOrderList != null && !productionOrderList.isEmpty()) {
                String productionOrderNo = productionOrderList.get(0).getProductionOrderNo();
                operationHeaderService.deleteOperationHeaderProductionOrder(companyCodeId, plantId, languageId, warehouseId, productionOrderNo);
                operationLineService.deleteOperationLineProductionOrder(companyCodeId, plantId, languageId, warehouseId, productionOrderNo);
                operationConsumptionService.deleteOperationConsumptionProductionOrder(companyCodeId, plantId, languageId, warehouseId, productionOrderNo);

                for (ProductionOrder dbProductionOrder : productionOrderList) {
                    ProductionOrderOutput createOperationHeader = operationHeaderService.createProductionOrder(dbProductionOrder, loginUserID);
                    updatedProductionOrderList.add(createOperationHeader);
                }
            }
            return updatedProductionOrderList;
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
    public void deleteProductionOrder(String companyCodeId, String plantId, String languageId, String warehouseId, String productionOrderNo, String loginUserID) {
        try {
            OperationHeader dbOperationHeader = operationHeaderService.getOperationHeader(companyCodeId, plantId, languageId, warehouseId, productionOrderNo);
            log.info("Delete ProductionOrder Initiated : {}", dbOperationHeader);
            dbOperationHeader.setDeletionIndicator(1L);
            dbOperationHeader.setUpdatedBy(loginUserID);
            dbOperationHeader.setUpdatedOn(new Date());
            operationHeaderRepository.save(dbOperationHeader);
            operationLineService.deleteOperationLine(companyCodeId, plantId, languageId, warehouseId, productionOrderNo, loginUserID);
            operationConsumptionService.deleteOperationConsumption(companyCodeId, plantId, languageId, warehouseId, productionOrderNo, loginUserID);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }
}