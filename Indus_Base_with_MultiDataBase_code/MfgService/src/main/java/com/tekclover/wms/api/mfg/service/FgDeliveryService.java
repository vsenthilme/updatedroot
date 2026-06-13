package com.tekclover.wms.api.mfg.service;

import com.tekclover.wms.api.mfg.controller.exception.BadRequestException;
import com.tekclover.wms.api.mfg.model.fgDelivery.FgDelivery;
import com.tekclover.wms.api.mfg.model.fgDelivery.SearchFgDelivery;
import com.tekclover.wms.api.mfg.repository.FgDeliveryRepository;
import com.tekclover.wms.api.mfg.repository.specification.FgDeliverySpecification;
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
public class FgDeliveryService extends BaseService {

    @Autowired
    FgDeliveryRepository fgDeliveryRepository;

    @Autowired
    OperationConsumptionService operationConsumptionService;
    //--------------------------------------------------------------------

    /**
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param operationNumber
     * @param receipeId
     * @param productionOrderNo
     * @param productionOrderLineNo
     * @param itemCode
     * @return
     */
    public FgDelivery getFgDelivery(String companyCodeId, String plantId, String languageId, String warehouseId, String operationNumber, String receipeId, String productionOrderNo, Long productionOrderLineNo, String itemCode) {
        Optional<FgDelivery> fgDeliveryOperation = fgDeliveryRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndOperationNumberAndReceipeIdAndProductionOrderNoAndProductionOrderLineNoAndItemCodeAndDeletionIndicator(languageId, companyCodeId, plantId, warehouseId, operationNumber, receipeId, productionOrderNo, productionOrderLineNo, itemCode, 0L);
        if (fgDeliveryOperation.isEmpty()) {
            throw new BadRequestException("The given values: warehouseId: " + warehouseId + ", companyCodeId: " + companyCodeId + ", languageId: " + languageId + ", plantId: " + plantId + ", operationNumber: " + operationNumber + ", receipeId: " + receipeId + ", productionOrderNo: " + productionOrderNo + ", productionOrderLineNo: " + productionOrderLineNo + ", itemCode: " + itemCode + " doesn't exist.");
        }
        return fgDeliveryOperation.get();
    }

    /**
     * @param searchFgDelivery
     * @return
     * @throws Exception
     */
    public Stream<FgDelivery> findFgDelivery(SearchFgDelivery searchFgDelivery) throws Exception {
        if (searchFgDelivery.getStartCreatedOn() != null && searchFgDelivery.getEndCreatedOn() != null) {
            Date[] dates = DateUtils.addTimeToDatesForSearch(searchFgDelivery.getStartCreatedOn(), searchFgDelivery.getEndCreatedOn());
        }
        log.info("Search FgDelivery Initiated -> {}", searchFgDelivery);
        FgDeliverySpecification spec = new FgDeliverySpecification(searchFgDelivery);
        Stream<FgDelivery> results = fgDeliveryRepository.stream(spec, FgDelivery.class);
        return results;
    }

    /**
     * @param newFgDeliveryList
     * @param loginUserID
     * @return
     */
    public List<FgDelivery> createFgDeliveryBatch(List<FgDelivery> newFgDeliveryList, String loginUserID) {
        try {
            List<FgDelivery> createdFgDeliveryList = new ArrayList<>();
            for (FgDelivery newFgDelivery : newFgDeliveryList) {
                Optional<FgDelivery> fgDeliveryOperation =
                        fgDeliveryRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndOperationNumberAndReceipeIdAndProductionOrderNoAndProductionOrderLineNoAndItemCodeAndDeletionIndicator(
                                newFgDelivery.getLanguageId(), newFgDelivery.getCompanyCodeId(), newFgDelivery.getPlantId(), newFgDelivery.getWarehouseId(), newFgDelivery.getOperationNumber(),
                                newFgDelivery.getReceipeId(), newFgDelivery.getProductionOrderNo(), newFgDelivery.getProductionOrderLineNo(), newFgDelivery.getItemCode(), 0L);
                if (fgDeliveryOperation.isPresent()) {
                    throw new BadRequestException("Record is getting duplicated with the given values");
                }
                log.info("Create FgDelivery Initiated -> {}", newFgDelivery);
                FgDelivery dbFgDelivery = new FgDelivery();
                BeanUtils.copyProperties(newFgDelivery, dbFgDelivery, CommonUtils.getNullPropertyNames(newFgDelivery));
                if (dbFgDelivery.getCompanyCodeId() != null && dbFgDelivery.getPlantId() != null && dbFgDelivery.getLanguageId() != null && dbFgDelivery.getWarehouseId() != null) {
                    description = getDescription(dbFgDelivery.getCompanyCodeId(), dbFgDelivery.getPlantId(), dbFgDelivery.getLanguageId(), dbFgDelivery.getWarehouseId());
                    if (description != null) {
                        dbFgDelivery.setCompanyDescription(description.getCompanyDescription());
                        dbFgDelivery.setPlantDescription(description.getPlantDescription());
                        dbFgDelivery.setWarehouseDescription(description.getWarehouseDescription());
                    }
                }
                if (dbFgDelivery.getStatusId() != null && dbFgDelivery.getLanguageId() != null) {
                    statusDescription = getStatusDescription(dbFgDelivery.getStatusId(), dbFgDelivery.getLanguageId());
                    if (statusDescription != null) {
                        dbFgDelivery.setStatusDescription(statusDescription);
                    }
                }
                dbFgDelivery.setDeletionIndicator(0L);
                dbFgDelivery.setCreatedBy(loginUserID);
                dbFgDelivery.setCreatedOn(new Date());
                FgDelivery savedFgDelivery = fgDeliveryRepository.save(dbFgDelivery);
                createdFgDeliveryList.add(savedFgDelivery);
//                OperationConsumption operationConsumption = new OperationConsumption();
//                BeanUtils.copyProperties(dbFgDelivery, operationConsumption, CommonUtils.getNullPropertyNames(dbFgDelivery));
//                operationConsumptionService.createOperationConsumption(operationConsumption, loginUserID);
            }
            return createdFgDeliveryList;
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
     * @param operationNumber
     * @param receipeId
     * @param productionOrderNo
     * @param productionOrderLineNo
     * @param itemCode
     * @param loginUserID
     * @param modifyFgDeliveryList
     * @return
     */
    public List<FgDelivery> updateFgDeliveryBatch(String companyCodeId, String plantId, String languageId, String warehouseId, String operationNumber, String receipeId, String productionOrderNo, Long productionOrderLineNo, String itemCode, String loginUserID, List<FgDelivery> modifyFgDeliveryList) {
        try {
            List<FgDelivery> updatedFgDeliveryList = new ArrayList<>();
            for (FgDelivery modifyFgDelivery : modifyFgDeliveryList) {
                FgDelivery dbFgDelivery = getFgDelivery(companyCodeId, plantId, languageId, warehouseId, operationNumber, receipeId, productionOrderNo, productionOrderLineNo, itemCode);
                log.info("Update FgDelivery Initiated -> {}", dbFgDelivery);
                BeanUtils.copyProperties(modifyFgDelivery, dbFgDelivery, CommonUtils.getNullPropertyNames(modifyFgDelivery));
                if (dbFgDelivery.getStatusId() != null && dbFgDelivery.getLanguageId() != null) {
                    statusDescription = getStatusDescription(dbFgDelivery.getStatusId(), dbFgDelivery.getLanguageId());
                    if (statusDescription != null) {
                        dbFgDelivery.setStatusDescription(statusDescription);
                    }
                }
                dbFgDelivery.setUpdatedBy(loginUserID);
                dbFgDelivery.setUpdatedOn(new Date());
                FgDelivery updateFgDelivery = fgDeliveryRepository.save(dbFgDelivery);
                updatedFgDeliveryList.add(updateFgDelivery);
            }
            return updatedFgDeliveryList;
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
     * @param operationNumber
     * @param receipeId
     * @param productionOrderNo
     * @param productionOrderLineNo
     * @param itemCode
     * @param loginUserID
     */
    public void deleteFgDelivery(String companyCodeId, String plantId, String languageId, String warehouseId, String operationNumber, String receipeId, String productionOrderNo, Long productionOrderLineNo, String itemCode, String loginUserID) {
        try {
            FgDelivery dbFgDelivery = getFgDelivery(companyCodeId, plantId, languageId, warehouseId, operationNumber, receipeId, productionOrderNo, productionOrderLineNo, itemCode);
            log.info("Delete FgDelivery Initiated -> {}", dbFgDelivery);
            dbFgDelivery.setDeletionIndicator(1L);
            dbFgDelivery.setUpdatedBy(loginUserID);
            dbFgDelivery.setUpdatedOn(new Date());
            fgDeliveryRepository.save(dbFgDelivery);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }
}