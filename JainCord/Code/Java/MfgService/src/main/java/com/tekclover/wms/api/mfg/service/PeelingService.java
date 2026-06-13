package com.tekclover.wms.api.mfg.service;

import com.tekclover.wms.api.mfg.controller.exception.BadRequestException;
import com.tekclover.wms.api.mfg.model.peeling.Peeling;
import com.tekclover.wms.api.mfg.model.peeling.SearchPeeling;
import com.tekclover.wms.api.mfg.model.prodcutionorder.SearchOperationLineReportProcess;
import com.tekclover.wms.api.mfg.repository.PeelingRepository;
import com.tekclover.wms.api.mfg.repository.specification.PeelingSpecification;
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
public class PeelingService extends BaseService {

    @Autowired
    PeelingRepository peelingRepository;

    @Autowired
    OperationConsumptionService operationConsumptionService;
    //--------------------------------------------------Peeling Service--------------------------------------------

    /**
     * Get Peeling
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
    public Peeling getPeeling(String languageId, String companyCodeId, String plantId, String warehouseId,
                              String productionOrderNo, Long productionOrderLineNo, String receipeId, String operationNumber, String itemCode) {
        Optional<Peeling> peeling =
                peelingRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndProductionOrderLineNoAndReceipeIdAndOperationNumberAndItemCodeAndDeletionIndicator(
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
        if (peeling.isEmpty()) {
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
        return peeling.get();
    }

    /**
     * Get BulkPeeling
     *
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param productionOrderNo
     * @return
     */
    public List<Peeling> getBulkPeeling(String companyCodeId, String plantId, String languageId, String warehouseId, String productionOrderNo) {
        List<Peeling> peeling =
                peelingRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndDeletionIndicator(
                        languageId,
                        companyCodeId,
                        plantId,
                        warehouseId,
                        productionOrderNo,
                        0L);
        if (peeling == null || peeling.isEmpty()) {
            throw new BadRequestException("The given values: warehouseId:" + warehouseId +
                    ",companyCodeId: " + companyCodeId +
                    ",languageId: " + languageId +
                    ",plantId: " + plantId +
                    ",productionOrderNo: " + productionOrderNo +
                    " doesn't exist.");
        }
        return peeling;
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
    public Peeling getPeeling(String companyCodeId, String plantId, String languageId, String warehouseId, String productionOrderNo, String batchNumber) {
        Peeling peeling =
                peelingRepository.findTopByCompanyCodeIdAndPlantIdAndLanguageIdAndWarehouseIdAndProductionOrderNoAndBatchNumberAndDeletionIndicator(
                        companyCodeId,
                        plantId,
                        languageId,
                        warehouseId,
                        productionOrderNo,
                        batchNumber,
                        0L);
        return peeling;
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
    public List<Peeling> getPeelingV2(String companyCodeId, String plantId, String languageId, String warehouseId, String productionOrderNo, String batchNumber) {
        List<Peeling> peeling =
                peelingRepository.findByCompanyCodeIdAndPlantIdAndLanguageIdAndWarehouseIdAndProductionOrderNoAndBatchNumberAndDeletionIndicator(
                        companyCodeId,
                        plantId,
                        languageId,
                        warehouseId,
                        productionOrderNo,
                        batchNumber,
                        0L);
        return peeling;
    }

    /**
     *
     * @param searchOperationLineReportProcess
     * @return
     */
    public List<Peeling> getPeelingV2(SearchOperationLineReportProcess searchOperationLineReportProcess) {
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
            List<Peeling> peeling =
                    peelingRepository.findPeeling(
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
            return peeling;
        } catch (Exception e) {
            throw new BadRequestException("Exception : " + e);
        }
    }
    /**
     * create Peeling
     *
     * @param addPeeling
     * @param loginUserID
     * @return
     */
    public List<Peeling> createPeeling(List<Peeling> addPeeling, String loginUserID) {
        try {
            List<Peeling> createdPeeling = new ArrayList<>();
            if (addPeeling != null && !addPeeling.isEmpty()) {
                for (Peeling newPeeling : addPeeling) {
                    if (newPeeling.getProductionOrderNo() == null || newPeeling.getProductionOrderLineNo() == null) {
                        throw new BadRequestException("ProductionOrderNo & Line No is must to create Peeling");
                    }
                    Optional<Peeling> peeling =
                            peelingRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndProductionOrderLineNoAndReceipeIdAndOperationNumberAndItemCodeAndDeletionIndicator(
                                    newPeeling.getLanguageId(),
                                    newPeeling.getCompanyCodeId(),
                                    newPeeling.getPlantId(),
                                    newPeeling.getWarehouseId(),
                                    newPeeling.getProductionOrderNo(),
                                    newPeeling.getProductionOrderLineNo(),
                                    newPeeling.getReceipeId(),
                                    newPeeling.getOperationNumber(),
                                    newPeeling.getItemCode(),
                                    0L
                            );
                    if (peeling.isPresent()) {
                        throw new BadRequestException("Record is getting duplicated with the given values");
                    }
                    log.info("Create Peeling initiated : {}", newPeeling);
                    Peeling dbPeeling = new Peeling();
                    BeanUtils.copyProperties(newPeeling, dbPeeling, CommonUtils.getNullPropertyNames(newPeeling));
                    if (dbPeeling.getLanguageId() != null && dbPeeling.getCompanyCodeId() != null &&
                            dbPeeling.getPlantId() != null && dbPeeling.getWarehouseId() != null) {
                        description = getDescription(dbPeeling.getCompanyCodeId(), dbPeeling.getPlantId(), dbPeeling.getLanguageId(), dbPeeling.getWarehouseId());
                        if (description != null) {
                            dbPeeling.setCompanyDescription(description.getCompanyDescription());
                            dbPeeling.setPlantDescription(description.getPlantDescription());
                            dbPeeling.setWarehouseDescription(description.getWarehouseDescription());
                        }
                    }
                    if (dbPeeling.getStatusId() != null && dbPeeling.getLanguageId() != null) {
                        statusDescription = getStatusDescription(dbPeeling.getStatusId(), dbPeeling.getLanguageId());
                        if (statusDescription != null) {
                            dbPeeling.setStatusDescription(statusDescription);
                        }
                    }
                    dbPeeling.setDeletionIndicator(0L);
                    dbPeeling.setCreatedBy(loginUserID);
                    dbPeeling.setCreatedOn(new Date());
                    peelingRepository.save(dbPeeling);
                    createdPeeling.add(dbPeeling);
//                    OperationConsumption operationConsumption = new OperationConsumption();
//                    BeanUtils.copyProperties(dbPeeling, operationConsumption, CommonUtils.getNullPropertyNames(dbPeeling));
//                    operationConsumptionService.createOperationConsumption(operationConsumption, loginUserID);
                }
            }
            return createdPeeling;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * @param newPeeling
     * @param loginUserID
     * @return
     */
    public Peeling createPeeling(Peeling newPeeling, String loginUserID) {
        try {
            if (newPeeling.getProductionOrderNo() == null || newPeeling.getProductionOrderLineNo() == null) {
                throw new BadRequestException("ProductionOrderNo & Line No is must to create Peeling");
            }
//            Optional<Peeling> peeling =
//                    peelingRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndProductionOrderLineNoAndReceipeIdAndOperationNumberAndItemCodeAndPhaseNumberAndBomItemAndBatchNumberAndDeletionIndicator(
//                            newPeeling.getLanguageId(),
//                            newPeeling.getCompanyCodeId(),
//                            newPeeling.getPlantId(),
//                            newPeeling.getWarehouseId(),
//                            newPeeling.getProductionOrderNo(),
//                            newPeeling.getProductionOrderLineNo(),
//                            newPeeling.getReceipeId(),
//                            newPeeling.getOperationNumber(),
//                            newPeeling.getItemCode(),
//                            newPeeling.getPhaseNumber(),
//                            newPeeling.getBomItem(),
//                            newPeeling.getBatchNumber(),
//                            0L);
//            if (peeling.isPresent()) {
//                throw new BadRequestException("Record is getting duplicated with the given values");
//            }
            log.info("Create Peeling initiated : {}", newPeeling);
            Peeling dbPeeling = new Peeling();
            BeanUtils.copyProperties(newPeeling, dbPeeling, CommonUtils.getNullPropertyNames(newPeeling));
            if (dbPeeling.getLanguageId() != null && dbPeeling.getCompanyCodeId() != null && dbPeeling.getPlantId() != null && dbPeeling.getWarehouseId() != null) {
                description = getDescription(dbPeeling.getCompanyCodeId(), dbPeeling.getPlantId(), dbPeeling.getLanguageId(), dbPeeling.getWarehouseId());
                if (description != null) {
                    dbPeeling.setCompanyDescription(description.getCompanyDescription());
                    dbPeeling.setPlantDescription(description.getPlantDescription());
                    dbPeeling.setWarehouseDescription(description.getWarehouseDescription());
                }
            }
            if (dbPeeling.getStatusId() != null && dbPeeling.getLanguageId() != null) {
                statusDescription = getStatusDescription(dbPeeling.getStatusId(), dbPeeling.getLanguageId());
                if (statusDescription != null) {
                    dbPeeling.setStatusDescription(statusDescription);
                }
            }
            dbPeeling.setDeletionIndicator(0L);
            dbPeeling.setCreatedBy(loginUserID);
            dbPeeling.setCreatedOn(new Date());
            Peeling createdPeeling = peelingRepository.save(dbPeeling);
            return createdPeeling;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * update Peeling
     *
     * @param languageId
     * @param plantId
     * @param companyCodeId
     * @param warehouseId
     * @param productionOrderNo
     * @param loginUserID
     * @param modifyPeeling
     * @return
     */
    public List<Peeling> updatePeeling(String languageId, String plantId, String companyCodeId, String warehouseId,
                                       String productionOrderNo, String loginUserID, List<Peeling> modifyPeeling) {
        try {
            List<Peeling> updatedPeeling = new ArrayList<>();
            for (Peeling newPeeling : modifyPeeling) {
                Peeling dbPeeling = getPeeling(languageId, companyCodeId, plantId, warehouseId, productionOrderNo,
                        newPeeling.getProductionOrderLineNo(), newPeeling.getReceipeId(), newPeeling.getOperationNumber(), newPeeling.getItemCode());
                log.info("Update Peeling Initiated : {}", dbPeeling);
                BeanUtils.copyProperties(newPeeling, dbPeeling, CommonUtils.getNullPropertyNames(newPeeling));
                if (dbPeeling.getStatusId() != null && dbPeeling.getLanguageId() != null) {
                    statusDescription = getStatusDescription(dbPeeling.getStatusId(), dbPeeling.getLanguageId());
                    if (statusDescription != null) {
                        dbPeeling.setStatusDescription(statusDescription);
                    }
                }
                dbPeeling.setUpdatedBy(loginUserID);
                dbPeeling.setUpdatedOn(new Date());
                peelingRepository.save(dbPeeling);
                updatedPeeling.add(dbPeeling);
            }
            return updatedPeeling;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * Delete Peeling
     *
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param productionOrderNo
     * @param loginUserID
     */
    public void deletePeeling(String companyCodeId, String plantId, String languageId, String warehouseId, String productionOrderNo, String loginUserID) {
        try {
            List<Peeling> dbulkPeeling = getBulkPeeling(companyCodeId, plantId, languageId, warehouseId, productionOrderNo);
            log.info("Delete Peeling Initiated : {}", dbulkPeeling);
            for (Peeling dbPeeling : dbulkPeeling) {
                dbPeeling.setDeletionIndicator(1L);
                dbPeeling.setUpdatedBy(loginUserID);
                dbPeeling.setUpdatedOn(new Date());
                peelingRepository.save(dbPeeling);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * Search Peeling
     *
     * @param searchPeeling
     * @return
     * @throws Exception
     */
    public Stream<Peeling> findPeeling(SearchPeeling searchPeeling) throws Exception {
        if (searchPeeling.getStartCreatedOn() != null && searchPeeling.getEndCreatedOn() != null) {
            Date[] dates = DateUtils.addTimeToDatesForSearch(searchPeeling.getStartCreatedOn(), searchPeeling.getEndCreatedOn());
            searchPeeling.setStartCreatedOn(dates[0]);
            searchPeeling.setEndCreatedOn(dates[1]);
        }
        if (searchPeeling.getStartConfirmedOn() != null && searchPeeling.getEndConfirmedOn() != null) {
            Date[] dates = DateUtils.addTimeToDatesForSearch(searchPeeling.getStartConfirmedOn(), searchPeeling.getEndConfirmedOn());
            searchPeeling.setStartConfirmedOn(dates[0]);
            searchPeeling.setEndConfirmedOn(dates[1]);
        }
        log.info("searchPeeling Input: {}", searchPeeling);
        PeelingSpecification spec = new PeelingSpecification(searchPeeling);
        Stream<Peeling> results = peelingRepository.stream(spec, Peeling.class);
        return results;
    }
}