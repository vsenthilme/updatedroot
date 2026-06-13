package com.tekclover.wms.api.mfg.service;

import com.tekclover.wms.api.mfg.controller.exception.BadRequestException;
import com.tekclover.wms.api.mfg.model.prodcutionorder.SearchOperationLineReportProcess;
import com.tekclover.wms.api.mfg.model.sorting.SearchSorting;
import com.tekclover.wms.api.mfg.model.sorting.Sorting;
import com.tekclover.wms.api.mfg.repository.SortingRepository;
import com.tekclover.wms.api.mfg.repository.specification.SortingSpecification;
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
public class SortingService extends BaseService {

    @Autowired
    SortingRepository sortingRepository;

    @Autowired
    OperationConsumptionService operationConsumptionService;

    //-----------------------------------------------------Sorting Service--------------------------------------------------

    /**
     * Get Sorting
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
    public Sorting getSorting(String languageId, String companyCodeId, String plantId, String warehouseId,
                              String productionOrderNo, Long productionOrderLineNo, String receipeId, String operationNumber, String itemCode) {
        Optional<Sorting> sorting =
                sortingRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndProductionOrderLineNoAndReceipeIdAndOperationNumberAndItemCodeAndDeletionIndicator(
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
        if (sorting.isEmpty()) {
            throw new BadRequestException("The given values: languageId: " + languageId +
                    ",companyCodeId: " + companyCodeId +
                    ",plantId: " + plantId +
                    ",warehouseId: " + warehouseId +
                    ",productionOrderNo: " + productionOrderNo +
                    ",productionOrderLineNo: " + productionOrderLineNo +
                    ",receipeId: " + receipeId +
                    ",operationNumber: " + operationNumber +
                    " and itemCode: " + itemCode +
                    "doesn't exist.");

        }
        return sorting.get();
    }

    /**
     * Get BulkSorting
     *
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param productionOrderNo
     * @return
     */
    public List<Sorting> getBulkSorting(String companyCodeId, String plantId, String languageId, String warehouseId, String productionOrderNo) {
        List<Sorting> sorting =
                sortingRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndDeletionIndicator(
                        languageId,
                        companyCodeId,
                        plantId,
                        warehouseId,
                        productionOrderNo,
                        0L);
        if (sorting == null || sorting.isEmpty()) {
            throw new BadRequestException("The given values: warehouseId:" + warehouseId +
                    ",companyCodeId: " + companyCodeId +
                    ",languageId: " + languageId +
                    ",plantId: " + plantId +
                    ",productionOrderNo: " + productionOrderNo +
                    " doesn't exist.");
        }
        return sorting;
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
    public Sorting getSorting(String companyCodeId, String plantId, String languageId, String warehouseId, String productionOrderNo, String batchNumber) {
        Sorting sorting =
                sortingRepository.findTopByCompanyCodeIdAndPlantIdAndLanguageIdAndWarehouseIdAndProductionOrderNoAndBatchNumberAndDeletionIndicator(
                        companyCodeId,
                        plantId,
                        languageId,
                        warehouseId,
                        productionOrderNo,
                        batchNumber,
                        0L);
        return sorting;
    }

    /**
     *
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param productionOrderNo
     * @param batchNumber
     * @return
     */
    public List<Sorting> getSortingV2(String companyCodeId, String plantId, String languageId, String warehouseId, String productionOrderNo, String batchNumber) {
        List<Sorting> sorting =
                sortingRepository.findByCompanyCodeIdAndPlantIdAndLanguageIdAndWarehouseIdAndProductionOrderNoAndBatchNumberAndDeletionIndicator(
                        companyCodeId,
                        plantId,
                        languageId,
                        warehouseId,
                        productionOrderNo,
                        batchNumber,
                        0L);
        return sorting;
    }

    /**
     *
     * @param searchOperationLineReportProcess
     * @return
     */
    public List<Sorting> getSortingV2(SearchOperationLineReportProcess searchOperationLineReportProcess) {
        try {
            if (searchOperationLineReportProcess.getStartCreatedOn() != null && searchOperationLineReportProcess.getEndCreatedOn() != null) {
                Date[] dates = DateUtils.addTimeToDatesForSearch(searchOperationLineReportProcess.getStartCreatedOn(), searchOperationLineReportProcess.getEndCreatedOn());
                searchOperationLineReportProcess.setStartCreatedOn(dates[0]);
                searchOperationLineReportProcess.setEndCreatedOn(dates[1]);
            }
            if(searchOperationLineReportProcess.getCompanyCodeId() != null && searchOperationLineReportProcess.getCompanyCodeId().isEmpty()) {
                searchOperationLineReportProcess.setCompanyCodeId(null);
            }
            if(searchOperationLineReportProcess.getPlantId() != null && searchOperationLineReportProcess.getPlantId().isEmpty()) {
                searchOperationLineReportProcess.setPlantId(null);
            }
            if(searchOperationLineReportProcess.getLanguageId() != null && searchOperationLineReportProcess.getLanguageId().isEmpty()) {
                searchOperationLineReportProcess.setLanguageId(null);
            }
            if(searchOperationLineReportProcess.getWarehouseId() != null && searchOperationLineReportProcess.getWarehouseId().isEmpty()) {
                searchOperationLineReportProcess.setWarehouseId(null);
            }
            if(searchOperationLineReportProcess.getItemCode() != null && searchOperationLineReportProcess.getItemCode().isEmpty()) {
                searchOperationLineReportProcess.setItemCode(null);
            }
            if(searchOperationLineReportProcess.getBatchNumber() != null && searchOperationLineReportProcess.getBatchNumber().isEmpty()) {
                searchOperationLineReportProcess.setBatchNumber(null);
            }
            if(searchOperationLineReportProcess.getBomItem() != null && searchOperationLineReportProcess.getBomItem().isEmpty()) {
                searchOperationLineReportProcess.setBomItem(null);
            }
            if(searchOperationLineReportProcess.getPhaseNumber() != null && searchOperationLineReportProcess.getPhaseNumber().isEmpty()) {
                searchOperationLineReportProcess.setPhaseNumber(null);
            }
            if(searchOperationLineReportProcess.getOperationNumber() != null && searchOperationLineReportProcess.getOperationNumber().isEmpty()) {
                searchOperationLineReportProcess.setOperationNumber(null);
            }
            if(searchOperationLineReportProcess.getReceipeId() != null && searchOperationLineReportProcess.getReceipeId().isEmpty()) {
                searchOperationLineReportProcess.setReceipeId(null);
            }
            if(searchOperationLineReportProcess.getProductionOrderNo() != null && searchOperationLineReportProcess.getProductionOrderNo().isEmpty()) {
                searchOperationLineReportProcess.setProductionOrderNo(null);
            }
            if(searchOperationLineReportProcess.getSupervisorName() != null && searchOperationLineReportProcess.getSupervisorName().isEmpty()) {
                searchOperationLineReportProcess.setSupervisorName(null);
            }
            if(searchOperationLineReportProcess.getStatusId() != null && searchOperationLineReportProcess.getStatusId().isEmpty()) {
                searchOperationLineReportProcess.setStatusId(null);
            }
            List<Sorting> sorting =
                    sortingRepository.findSorting(
                            searchOperationLineReportProcess.getCompanyCodeId(),
                            searchOperationLineReportProcess.getPlantId(),
                            searchOperationLineReportProcess.getLanguageId(),
                            searchOperationLineReportProcess.getWarehouseId(),
                            searchOperationLineReportProcess.getItemCode(),
                            searchOperationLineReportProcess.getBatchNumber(),
                            searchOperationLineReportProcess.getBomItem(),
                            searchOperationLineReportProcess.getPhaseNumber(),
                            searchOperationLineReportProcess.getOperationNumber(),
                            searchOperationLineReportProcess.getReceipeId(),
                            searchOperationLineReportProcess.getProductionOrderNo(),
                            searchOperationLineReportProcess.getStatusId(),
                            searchOperationLineReportProcess.getSupervisorName(),
                            searchOperationLineReportProcess.getStartCreatedOn(),
                            searchOperationLineReportProcess.getEndCreatedOn());
            return sorting;
        } catch (Exception e) {
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * create Sorting
     *
     * @param addSorting
     * @param loginUserID
     * @return
     */
    public List<Sorting> createSorting(List<Sorting> addSorting, String loginUserID) {
        try {
            List<Sorting> createdSorting = new ArrayList<>();
            if (addSorting != null && !addSorting.isEmpty()) {
                for (Sorting newSorting : addSorting) {
                    if (newSorting.getProductionOrderNo() == null || newSorting.getProductionOrderLineNo() == null) {
                        throw new BadRequestException("ProductionOrderNo & Line No is must to create Sorting");
                    }

                    Optional<Sorting> sorting =
                            sortingRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndProductionOrderLineNoAndReceipeIdAndOperationNumberAndItemCodeAndPhaseNumberAndBomItemAndBatchNumberAndDeletionIndicator(
                                    newSorting.getLanguageId(),
                                    newSorting.getCompanyCodeId(),
                                    newSorting.getPlantId(),
                                    newSorting.getWarehouseId(),
                                    newSorting.getProductionOrderNo(),
                                    newSorting.getProductionOrderLineNo(),
                                    newSorting.getReceipeId(),
                                    newSorting.getOperationNumber(),
                                    newSorting.getItemCode(),
                                    newSorting.getPhaseNumber(),
                                    newSorting.getBomItem(),
                                    newSorting.getBatchNumber(),
                                    0L
                            );
                    if (sorting.isPresent()) {
                        throw new BadRequestException("Record is getting duplicated with the given values");
                    }
                    log.info("Create Sorting initiated : {}", newSorting);
                    Sorting dbSorting = new Sorting();
                    BeanUtils.copyProperties(newSorting, dbSorting, CommonUtils.getNullPropertyNames(newSorting));

                    if (dbSorting.getLanguageId() != null && dbSorting.getCompanyCodeId() != null &&
                            dbSorting.getPlantId() != null && dbSorting.getWarehouseId() != null) {

                        description = getDescription(dbSorting.getCompanyCodeId(), dbSorting.getPlantId(), dbSorting.getLanguageId(), dbSorting.getWarehouseId());
                        if (description != null) {
                            dbSorting.setCompanyDescription(description.getCompanyDescription());
                            dbSorting.setPlantDescription(description.getPlantDescription());
                            dbSorting.setWarehouseDescription(description.getWarehouseDescription());
                        }
                    }

                    if (dbSorting.getStatusId() != null && dbSorting.getLanguageId() != null) {
                        statusDescription = getStatusDescription(dbSorting.getStatusId(), dbSorting.getLanguageId());
                        if (statusDescription != null) {
                            dbSorting.setStatusDescription(statusDescription);
                        }
                    }
                    dbSorting.setDeletionIndicator(0L);
                    dbSorting.setCreatedBy(loginUserID);
                    dbSorting.setCreatedOn(new Date());
                    sortingRepository.save(dbSorting);
                    createdSorting.add(dbSorting);

//                    OperationConsumption operationConsumption = new OperationConsumption();
//                    BeanUtils.copyProperties(dbSorting, operationConsumption, CommonUtils.getNullPropertyNames(dbSorting));
//                    operationConsumptionService.createOperationConsumption(operationConsumption, loginUserID);

                }
            }
            return createdSorting;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     *
     * @param newSorting
     * @param loginUserID
     * @return
     */
    public Sorting createSorting(Sorting newSorting, String loginUserID) {
        try {
            if (newSorting.getProductionOrderNo() == null || newSorting.getProductionOrderLineNo() == null) {
                throw new BadRequestException("ProductionOrderNo & Line No is must to create Sorting");
            }

//            Optional<Sorting> sorting =
//                    sortingRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndProductionOrderLineNoAndReceipeIdAndOperationNumberAndItemCodeAndPhaseNumberAndBomItemAndBatchNumberAndDeletionIndicator(
//                            newSorting.getLanguageId(),
//                            newSorting.getCompanyCodeId(),
//                            newSorting.getPlantId(),
//                            newSorting.getWarehouseId(),
//                            newSorting.getProductionOrderNo(),
//                            newSorting.getProductionOrderLineNo(),
//                            newSorting.getReceipeId(),
//                            newSorting.getOperationNumber(),
//                            newSorting.getItemCode(),
//                            newSorting.getPhaseNumber(),
//                            newSorting.getBomItem(),
//                            newSorting.getBatchNumber(),
//                            0L);
//            if (sorting.isPresent()) {
//                throw new BadRequestException("Record is getting duplicated with the given values");
//            }
            log.info("Create Sorting initiated : {}", newSorting);
            Sorting dbSorting = new Sorting();
            BeanUtils.copyProperties(newSorting, dbSorting, CommonUtils.getNullPropertyNames(newSorting));

            if (dbSorting.getLanguageId() != null && dbSorting.getCompanyCodeId() != null && dbSorting.getPlantId() != null && dbSorting.getWarehouseId() != null) {
                description = getDescription(dbSorting.getCompanyCodeId(), dbSorting.getPlantId(), dbSorting.getLanguageId(), dbSorting.getWarehouseId());
                if (description != null) {
                    dbSorting.setCompanyDescription(description.getCompanyDescription());
                    dbSorting.setPlantDescription(description.getPlantDescription());
                    dbSorting.setWarehouseDescription(description.getWarehouseDescription());
                }
            }
            if (dbSorting.getStatusId() != null && dbSorting.getLanguageId() != null) {
                statusDescription = getStatusDescription(dbSorting.getStatusId(), dbSorting.getLanguageId());
                if (statusDescription != null) {
                    dbSorting.setStatusDescription(statusDescription);
                }
            }
            dbSorting.setDeletionIndicator(0L);
            dbSorting.setCreatedBy(loginUserID);
            dbSorting.setCreatedOn(new Date());
            Sorting createdSorting = sortingRepository.save(dbSorting);
            return createdSorting;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * update Sorting
     *
     * @param languageId
     * @param plantId
     * @param companyCodeId
     * @param warehouseId
     * @param productionOrderNo
     * @param loginUserID
     * @param modifySorting
     * @return
     */
    public List<Sorting> updateSorting(String languageId, String plantId, String companyCodeId, String warehouseId,
                                       String productionOrderNo, String loginUserID, List<Sorting> modifySorting) {
        try {
            List<Sorting> updatedSorting = new ArrayList<>();
            for (Sorting newSorting : modifySorting) {
                Sorting dbSorting = getSorting(languageId, companyCodeId, plantId, warehouseId, productionOrderNo,
                        newSorting.getProductionOrderLineNo(), newSorting.getReceipeId(), newSorting.getOperationNumber(), newSorting.getItemCode());
                log.info("Update Sorting Initiated : {}", dbSorting);
                BeanUtils.copyProperties(newSorting, dbSorting, CommonUtils.getNullPropertyNames(newSorting));

                if (dbSorting.getStatusId() != null && dbSorting.getLanguageId() != null) {
                    statusDescription = getStatusDescription(dbSorting.getStatusId(), dbSorting.getLanguageId());
                    if (statusDescription != null) {
                        dbSorting.setStatusDescription(statusDescription);
                    }
                }
                dbSorting.setUpdatedBy(loginUserID);
                dbSorting.setUpdatedOn(new Date());
                sortingRepository.save(dbSorting);
                updatedSorting.add(dbSorting);
            }
            return updatedSorting;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * Delete Sorting
     *
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param productionOrderNo
     * @param loginUserID
     */
    public void deleteSorting(String companyCodeId, String plantId, String languageId, String warehouseId, String productionOrderNo, String loginUserID) {
        try {
            List<Sorting> dbulkSorting = getBulkSorting(companyCodeId, plantId, languageId, warehouseId, productionOrderNo);
            log.info("Delete Sorting Initiated : {}", dbulkSorting);
            for (Sorting dbSorting : dbulkSorting) {
                dbSorting.setDeletionIndicator(1L);
                dbSorting.setUpdatedBy(loginUserID);
                dbSorting.setUpdatedOn(new Date());
                sortingRepository.save(dbSorting);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * Search Sorting
     *
     * @param searchSorting
     * @return
     * @throws Exception
     */
    public Stream<Sorting> findSorting(SearchSorting searchSorting) throws Exception {
        if (searchSorting.getStartCreatedOn() != null && searchSorting.getEndCreatedOn() != null) {
            Date[] dates = DateUtils.addTimeToDatesForSearch(searchSorting.getStartCreatedOn(), searchSorting.getEndCreatedOn());
            searchSorting.setStartCreatedOn(dates[0]);
            searchSorting.setEndCreatedOn(dates[1]);
        }
        if (searchSorting.getStartConfirmedOn() != null && searchSorting.getEndConfirmedOn() != null) {
            Date[] dates = DateUtils.addTimeToDatesForSearch(searchSorting.getStartConfirmedOn(), searchSorting.getEndConfirmedOn());
            searchSorting.setStartConfirmedOn(dates[0]);
            searchSorting.setEndConfirmedOn(dates[1]);
        }
        log.info("searchSorting Input: {}", searchSorting);
        SortingSpecification spec = new SortingSpecification(searchSorting);
        Stream<Sorting> results = sortingRepository.stream(spec, Sorting.class);
        return results;
    }

}