package com.tekclover.wms.api.mfg.service;

import com.tekclover.wms.api.mfg.controller.exception.BadRequestException;
import com.tekclover.wms.api.mfg.model.fgpacking.FgPacking;
import com.tekclover.wms.api.mfg.model.fgpacking.SearchFgPacking;
import com.tekclover.wms.api.mfg.repository.FgPackingRepository;
import com.tekclover.wms.api.mfg.repository.specification.FgPackingSpecification;
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
public class FgPackingService extends BaseService {

    @Autowired
    FgPackingRepository fgPackingRepository;

    @Autowired
    OperationConsumptionService operationConsumptionService;
    //------------------------------------------FgPackingService----------------------------------------------------

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
    public FgPacking getFgPacking(String languageId, String companyCodeId, String plantId, String warehouseId,
                                  String productionOrderNo, Long productionOrderLineNo, String receipeId, String operationNumber,
                                  String itemCode) {
        Optional<FgPacking> fgPacking =
                fgPackingRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndProductionOrderLineNoAndReceipeIdAndOperationNumberAndItemCodeAndDeletionIndicator(
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
        if (fgPacking.isEmpty()) {
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
        return fgPacking.get();
    }

    /**
     * Get BulkFgPacking
     *
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param productionOrderNo
     * @return
     */
    public List<FgPacking> getBulkFgPacking(String companyCodeId, String plantId, String languageId, String warehouseId, String productionOrderNo) {
        List<FgPacking> fgPackings = fgPackingRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndDeletionIndicator(
                languageId,
                companyCodeId,
                plantId,
                warehouseId,
                productionOrderNo,
                0L
        );
        if (fgPackings == null || fgPackings.isEmpty()) {
            throw new BadRequestException("The given values: languageId: " + languageId +
                    " ,companyCodeId: " + companyCodeId +
                    " ,plantId: " + plantId +
                    " ,warehouseId: " + warehouseId +
                    " ,productionOrderNo: " + productionOrderNo +
                    " doesn't exist");
        }
        return fgPackings;
    }

    /**
     * Create FgPacking
     *
     * @param addFgPacking
     * @param loginUserID
     * @return
     */
    public List<FgPacking> createFgPacking(List<FgPacking> addFgPacking, String loginUserID) {
        try {
            List<FgPacking> createdFgPacking = new ArrayList<>();
            if (addFgPacking != null && !addFgPacking.isEmpty()) {
                for (FgPacking newFgPacking : addFgPacking) {
                    if (newFgPacking.getProductionOrderNo() == null || newFgPacking.getProductionOrderLineNo() == null) {
                        throw new BadRequestException(" ProductionOrderNo & LineNo is must to create FgReceiving");
                    }
                    Optional<FgPacking> fgPacking = fgPackingRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndProductionOrderLineNoAndReceipeIdAndOperationNumberAndItemCodeAndDeletionIndicator(
                            newFgPacking.getLanguageId(),
                            newFgPacking.getCompanyCodeId(),
                            newFgPacking.getPlantId(),
                            newFgPacking.getWarehouseId(),
                            newFgPacking.getProductionOrderNo(),
                            newFgPacking.getProductionOrderLineNo(),
                            newFgPacking.getReceipeId(),
                            newFgPacking.getOperationNumber(),
                            newFgPacking.getItemCode(),
                            0L
                    );
                    if (fgPacking.isPresent()) {
                        throw new BadRequestException("Record is getting duplicated with the given values");
                    }
                    log.info("create FgPacking initiated : {}", newFgPacking);
                    FgPacking dbFgPacking = new FgPacking();
                    BeanUtils.copyProperties(newFgPacking, dbFgPacking, CommonUtils.getNullPropertyNames(newFgPacking));
                    if (dbFgPacking.getLanguageId() != null && dbFgPacking.getCompanyCodeId() != null &&
                            dbFgPacking.getPlantId() != null && dbFgPacking.getWarehouseId() != null) {
                        description = getDescription(dbFgPacking.getCompanyCodeId(), dbFgPacking.getPlantId(), dbFgPacking.getLanguageId(), dbFgPacking.getWarehouseId());
                        if (description != null) {
                            dbFgPacking.setCompanyDescription(description.getCompanyDescription());
                            dbFgPacking.setPlantDescription(description.getPlantDescription());
                            dbFgPacking.setWarehouseDescription(description.getWarehouseDescription());
                        }
                    }
                    if (dbFgPacking.getStatusId() != null && dbFgPacking.getLanguageId() != null) {
                        statusDescription = getStatusDescription(dbFgPacking.getStatusId(), dbFgPacking.getLanguageId());
                        if (statusDescription != null) {
                            dbFgPacking.setStatusDescription(statusDescription);
                        }
                    }
                    dbFgPacking.setDeletionIndicator(0L);
                    dbFgPacking.setCreatedBy(loginUserID);
                    dbFgPacking.setCreatedOn(new Date());
                    fgPackingRepository.save(dbFgPacking);
//                    OperationConsumption operationConsumption = new OperationConsumption();
//                    BeanUtils.copyProperties(dbFgPacking, operationConsumption, CommonUtils.getNullPropertyNames(dbFgPacking));
//                    operationConsumptionService.createOperationConsumption(operationConsumption, loginUserID);
                    createdFgPacking.add(dbFgPacking);
                }
            }
            return createdFgPacking;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * Update FgPacking
     *
     * @param languageId
     * @param plantId
     * @param companyCodeId
     * @param warehouseId
     * @param productionOrderNo
     * @param loginUserID
     * @param modifyFgPacking
     * @return
     */
    public List<FgPacking> updateFgPacking(String languageId, String plantId, String companyCodeId, String warehouseId,
                                           String productionOrderNo, String loginUserID, List<FgPacking> modifyFgPacking) {
        try {
            List<FgPacking> updatedFgPacking = new ArrayList<>();
            for (FgPacking newFgPacking : modifyFgPacking) {
                FgPacking dbFgPacking = getFgPacking(languageId, companyCodeId, plantId, warehouseId, productionOrderNo,
                        newFgPacking.getProductionOrderLineNo(), newFgPacking.getReceipeId(), newFgPacking.getOperationNumber(), newFgPacking.getItemCode());
                log.info("Update FgPacking initiated: {}", dbFgPacking);
                BeanUtils.copyProperties(newFgPacking, dbFgPacking, CommonUtils.getNullPropertyNames(dbFgPacking));
                if (dbFgPacking.getStatusId() != null && dbFgPacking.getLanguageId() != null) {
                    statusDescription = getStatusDescription(dbFgPacking.getStatusId(), dbFgPacking.getLanguageId());
                    if (statusDescription != null) {
                        dbFgPacking.setStatusDescription(statusDescription);
                    }
                }
                dbFgPacking.setUpdatedBy(loginUserID);
                dbFgPacking.setUpdatedOn(new Date());
                fgPackingRepository.save(dbFgPacking);
                updatedFgPacking.add(dbFgPacking);
            }
            return updatedFgPacking;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * Delete FgPacking
     *
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param productionOrderNo
     * @param loginUserID
     */
    public void deleteFgPacking(String companyCodeId, String plantId, String languageId, String warehouseId, String productionOrderNo, String loginUserID) {
        try {
            List<FgPacking> dbbulkFgPacking = getBulkFgPacking(companyCodeId, plantId, languageId, warehouseId, productionOrderNo);
            log.info("Deleting FgPacking Initiated: {}", dbbulkFgPacking);
            for (FgPacking dbFgPacking : dbbulkFgPacking) {
                dbFgPacking.setDeletionIndicator(1L);
                dbFgPacking.setUpdatedBy(loginUserID);
                dbFgPacking.setUpdatedOn(new Date());
                fgPackingRepository.save(dbFgPacking);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * Search FgPacking
     *
     * @param searchFgPacking
     * @return
     * @throws Exception
     */
    public Stream<FgPacking> findFgPacking(SearchFgPacking searchFgPacking) throws Exception {
        if (searchFgPacking.getStartCreatedOn() != null && searchFgPacking.getEndCreatedOn() != null) {
            Date[] dates = DateUtils.addTimeToDatesForSearch(searchFgPacking.getStartCreatedOn(), searchFgPacking.getEndCreatedOn());
            searchFgPacking.setStartCreatedOn(dates[0]);
            searchFgPacking.setEndCreatedOn(dates[1]);
        }
        if (searchFgPacking.getStartConfirmedOn() != null && searchFgPacking.getEndConfirmedOn() != null) {
            Date[] dates = DateUtils.addTimeToDatesForSearch(searchFgPacking.getStartConfirmedOn(), searchFgPacking.getEndConfirmedOn());
            searchFgPacking.setStartConfirmedOn(dates[0]);
            searchFgPacking.setEndConfirmedOn(dates[1]);
        }
        log.info("searchFgPacking Input: {}", searchFgPacking);
        FgPackingSpecification spec = new FgPackingSpecification(searchFgPacking);
        Stream<FgPacking> results = fgPackingRepository.stream(spec, FgPacking.class);
        return results;
    }
}