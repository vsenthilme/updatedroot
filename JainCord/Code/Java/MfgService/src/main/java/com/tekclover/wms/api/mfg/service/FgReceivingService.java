package com.tekclover.wms.api.mfg.service;

import com.tekclover.wms.api.mfg.controller.exception.BadRequestException;
import com.tekclover.wms.api.mfg.model.fgreceiving.FgReceiving;
import com.tekclover.wms.api.mfg.model.fgreceiving.SearchFgReceiving;
import com.tekclover.wms.api.mfg.repository.FgReceivingRepository;
import com.tekclover.wms.api.mfg.repository.specification.FgReceivingSpecification;
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
public class FgReceivingService extends BaseService {

    @Autowired
    FgReceivingRepository fgReceivingRepository;

    @Autowired
    OperationConsumptionService operationConsumptionService;
    //------------------------------------------FgReceivingService----------------------------------------------------

    /**
     * Get FgReceiving
     *
     * @param languageId
     * @param companyCodeId
     * @param plantId
     * @param warehouseId
     * @param productionOrderNo
     * @param productionOrderLineNo
     * @param receipeId
     * @param operationNumber
     * @param itemCode
     * @return
     */
    public FgReceiving getFgReceiving(String languageId, String companyCodeId, String plantId, String warehouseId,
                                      String productionOrderNo, Long productionOrderLineNo, String receipeId, String operationNumber,
                                      String itemCode) {
        Optional<FgReceiving> fgReceiving = fgReceivingRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndProductionOrderLineNoAndReceipeIdAndOperationNumberAndItemCodeAndDeletionIndicator(
                languageId,
                companyCodeId,
                plantId,
                warehouseId,
                productionOrderNo,
                productionOrderLineNo,
                receipeId,
                operationNumber,
                itemCode,
                0L
        );
        if (fgReceiving.isEmpty()) {
            throw new BadRequestException("The given values: languageId: " + languageId +
                    ", companyCodeId: " + companyCodeId +
                    ", plantId: " + plantId +
                    ", warehouseId: " + warehouseId +
                    ", productionOrderNo: " + productionOrderNo +
                    ", productionOrderLineNo: " + productionOrderLineNo +
                    " , receipeId: " + receipeId +
                    ", operationNumber: " + operationNumber +
                    " ,itemCode: " + itemCode +
                    " doesn't exist.");
        }
        return fgReceiving.get();
    }

    /**
     * Get BulkFgReceiving
     *
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param productionOrderNo
     * @return
     */
    public List<FgReceiving> getBulkFgReceiving(String companyCodeId, String plantId, String languageId, String warehouseId, String productionOrderNo) {
        List<FgReceiving> fgReceivings = fgReceivingRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndDeletionIndicator(
                languageId,
                companyCodeId,
                plantId,
                warehouseId,
                productionOrderNo,
                0L
        );
        if (fgReceivings == null || fgReceivings.isEmpty()) {
            throw new BadRequestException("The given values: languageId: " + languageId +
                    " ,companyCodeId: " + companyCodeId +
                    " ,plantId: " + plantId +
                    " ,warehouseId: " + warehouseId +
                    " ,productionOrderNo: " + productionOrderNo +
                    " doesn't exist");
        }
        return fgReceivings;
    }

    /**
     * Create FgReceiving
     *
     * @param addFgReceiving
     * @param loginUserID
     * @return
     */
    public List<FgReceiving> createFgReceiving(List<FgReceiving> addFgReceiving, String loginUserID) {
        try {
            List<FgReceiving> createdFgReceiving = new ArrayList<>();
            if (addFgReceiving != null && !addFgReceiving.isEmpty()) {
                for (FgReceiving newFgReceiving : addFgReceiving) {
                    if (newFgReceiving.getProductionOrderNo() == null || newFgReceiving.getProductionOrderLineNo() == null) {
                        throw new BadRequestException(" ProductionOrderNo & LineNo is must to create FgReceiving");
                    }
                    Optional<FgReceiving> fgReceiving = fgReceivingRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndProductionOrderLineNoAndReceipeIdAndOperationNumberAndItemCodeAndDeletionIndicator(
                            newFgReceiving.getLanguageId(),
                            newFgReceiving.getCompanyCodeId(),
                            newFgReceiving.getPlantId(),
                            newFgReceiving.getWarehouseId(),
                            newFgReceiving.getProductionOrderNo(),
                            newFgReceiving.getProductionOrderLineNo(),
                            newFgReceiving.getReceipeId(),
                            newFgReceiving.getOperationNumber(),
                            newFgReceiving.getItemCode(),
                            0L
                    );
                    if (fgReceiving.isPresent()) {
                        throw new BadRequestException("Record is getting duplicated with the given values");
                    }
                    log.info("create FgReceiving initiated : {}", newFgReceiving);
                    FgReceiving dbFgReceiving = new FgReceiving();
                    BeanUtils.copyProperties(newFgReceiving, dbFgReceiving, CommonUtils.getNullPropertyNames(newFgReceiving));
                    if (dbFgReceiving.getLanguageId() != null && dbFgReceiving.getCompanyCodeId() != null &&
                            dbFgReceiving.getPlantId() != null && dbFgReceiving.getWarehouseId() != null) {
                        description = getDescription(dbFgReceiving.getCompanyCodeId(), dbFgReceiving.getPlantId(), dbFgReceiving.getLanguageId(), dbFgReceiving.getWarehouseId());
                        if (description != null) {
                            dbFgReceiving.setCompanyDescription(description.getCompanyDescription());
                            dbFgReceiving.setPlantDescription(description.getPlantDescription());
                            dbFgReceiving.setWarehouseDescription(description.getWarehouseDescription());
                        }
                    }
                    if (dbFgReceiving.getStatusId() != null && dbFgReceiving.getLanguageId() != null) {
                        statusDescription = getStatusDescription(dbFgReceiving.getStatusId(), dbFgReceiving.getLanguageId());
                        if (statusDescription != null) {
                            dbFgReceiving.setStatusDescription(statusDescription);
                        }
                    }
                    dbFgReceiving.setDeletionIndicator(0L);
                    dbFgReceiving.setCreatedBy(loginUserID);
                    dbFgReceiving.setCreatedOn(new Date());
                    fgReceivingRepository.save(dbFgReceiving);
//                    OperationConsumption operationConsumption = new OperationConsumption();
//                    BeanUtils.copyProperties(dbFgReceiving, operationConsumption, CommonUtils.getNullPropertyNames(dbFgReceiving));
//                    operationConsumptionService.createOperationConsumption(operationConsumption, loginUserID);
                    createdFgReceiving.add(dbFgReceiving);
                }
            }
            return createdFgReceiving;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * Update FgReceiving
     *
     * @param languageId
     * @param plantId
     * @param companyCodeId
     * @param warehouseId
     * @param productionOrderNo
     * @param loginUserID
     * @param modifyFgReceiving
     * @return
     */
    public List<FgReceiving> updateFgReceiving(String languageId, String plantId, String companyCodeId, String warehouseId,
                                               String productionOrderNo, String loginUserID, List<FgReceiving> modifyFgReceiving) {
        try {
            List<FgReceiving> updatedFgReceiving = new ArrayList<>();
            for (FgReceiving newFgReceiving : modifyFgReceiving) {
                FgReceiving dbFgReceiving = getFgReceiving(languageId, companyCodeId, plantId, warehouseId, productionOrderNo,
                        newFgReceiving.getProductionOrderLineNo(), newFgReceiving.getReceipeId(), newFgReceiving.getOperationNumber(), newFgReceiving.getItemCode());
                log.info("Update FgReceiving initiated: {}", dbFgReceiving);
                BeanUtils.copyProperties(newFgReceiving, dbFgReceiving, CommonUtils.getNullPropertyNames(dbFgReceiving));
                if (dbFgReceiving.getStatusId() != null && dbFgReceiving.getLanguageId() != null) {
                    statusDescription = getStatusDescription(dbFgReceiving.getStatusId(), dbFgReceiving.getLanguageId());
                    if (statusDescription != null) {
                        dbFgReceiving.setStatusDescription(statusDescription);
                    }
                }
                dbFgReceiving.setUpdatedBy(loginUserID);
                dbFgReceiving.setUpdatedOn(new Date());
                fgReceivingRepository.save(dbFgReceiving);
                updatedFgReceiving.add(dbFgReceiving);
            }
            return updatedFgReceiving;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * Delete FgReceiving
     *
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param productionOrderNo
     * @param loginUserID
     */
    public void deleteFgReceiving(String companyCodeId, String plantId, String languageId, String warehouseId, String productionOrderNo, String loginUserID) {
        try {
            List<FgReceiving> dbbulkFgReceiving = getBulkFgReceiving(companyCodeId, plantId, languageId, warehouseId, productionOrderNo);
            log.info("Deleting FgReceiving Initiated: {}", dbbulkFgReceiving);
            for (FgReceiving dbFgReceiving : dbbulkFgReceiving) {
                dbFgReceiving.setDeletionIndicator(1L);
                dbFgReceiving.setUpdatedBy(loginUserID);
                dbFgReceiving.setUpdatedOn(new Date());
                fgReceivingRepository.save(dbFgReceiving);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * Search FgReceiving
     *
     * @param searchFgReceiving
     * @return
     * @throws Exception
     */
    public Stream<FgReceiving> findFgReceiving(SearchFgReceiving searchFgReceiving) throws Exception {
        if (searchFgReceiving.getStartCreatedOn() != null && searchFgReceiving.getEndCreatedOn() != null) {
            Date[] dates = DateUtils.addTimeToDatesForSearch(searchFgReceiving.getStartCreatedOn(), searchFgReceiving.getEndCreatedOn());
            searchFgReceiving.setStartCreatedOn(dates[0]);
            searchFgReceiving.setEndCreatedOn(dates[1]);
        }
        if (searchFgReceiving.getStartConfirmedOn() != null && searchFgReceiving.getEndConfirmedOn() != null) {
            Date[] dates = DateUtils.addTimeToDatesForSearch(searchFgReceiving.getStartConfirmedOn(), searchFgReceiving.getEndConfirmedOn());
            searchFgReceiving.setStartConfirmedOn(dates[0]);
            searchFgReceiving.setEndConfirmedOn(dates[1]);
        }
        log.info("searchFgReceiving Input: {}", searchFgReceiving);
        FgReceivingSpecification spec = new FgReceivingSpecification(searchFgReceiving);
        Stream<FgReceiving> results = fgReceivingRepository.stream(spec, FgReceiving.class);
        return results;
    }
}